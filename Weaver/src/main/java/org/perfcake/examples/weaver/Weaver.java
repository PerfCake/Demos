/*
 * -----------------------------------------------------------------------\
 * PerfCake
 *  
 * Copyright (C) 2010 - 2016 the original author or authors.
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * -----------------------------------------------------------------------/
 */
package org.perfcake.examples.weaver;

import org.perfcake.examples.weaver.worker.Worker;
import org.perfcake.util.ObjectFactory;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public final class Weaver {

   @Parameter(names = { "-t", "--threads" }, description = "Number of threads, 0 = automatic based on number of workers")
   private int threads = 0;

   @Parameter(names = { "-s", "--shuffle" }, description = "Shuffle the workers", arity = 1)
   private boolean shuffle = false;

   @Parameter(names = { "-c", "--config" }, description = "Configuration file", required = true)
   private String config = null;

   @Parameter(names = { "--help" }, description = "Prints out command usage")
   private boolean help = false;

   @Parameter(names = { "-p", "--port" }, description = "Network port to listen on")
   private int port = 8080;

   @Parameter(names = { "-h", "--host" }, description = "Network host to listen on")
   private String host = "localhost";

   private final Queue<Worker> workers = new ConcurrentLinkedQueue<>();

   private ThreadPoolExecutor executor;

   public static void main(String[] args) throws IOException {
      final Weaver weaver = new Weaver();
      final JCommander jCommander = new JCommander(weaver, args);

      if (weaver.config == null || weaver.help) {
         jCommander.usage("weaver");
         System.exit(1);
      }

      if (!(new File(weaver.config).exists())) {
         System.out.println("The specified configuration file '" + weaver.config + "' does not exists.");
         System.exit(2);
      }

      weaver.init();
      weaver.run();
   }

   private void init() throws IOException {
      int maxThreads = Files.lines(Paths.get(config)).collect(Collectors.summingInt(this::parseWorker));
      if (threads > maxThreads || threads == 0) {
         if (threads > maxThreads) {
            System.out.println("ERROR Maximum possible threads is " + maxThreads + ", while you requested " + threads + ". Using " + maxThreads + ".");
         }
         threads = maxThreads;
      }

      if (shuffle) {
         System.out.println("INFO Shuffling workers...");

         final List<Worker> workersList = workers.stream().collect(Collectors.toList());
         Collections.shuffle(workersList);
         workers.clear();
         workersList.forEach(workers::offer);
      }

      System.out.println("INFO Creating executor with " + threads + " threads.");

      executor = new ThreadPoolExecutor(threads, threads, 0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(), new ThreadFactoryBuilder().setDaemon(true).setNameFormat("worker-thread-%d").build());
   }

   private int parseWorker(final String configLine) {
      final String[] spaceSplit = configLine.split(" ", 2);
      final String[] equalsSplit = spaceSplit[1].split("=", 2);
      final int count = Integer.parseInt(StringUtils.strip(spaceSplit[0], " x"));
      String clazz = StringUtils.strip(equalsSplit[0]);
      clazz = clazz.contains(".") ? clazz : "org.perfcake.examples.weaver.worker." + clazz;
      final String[] propertiesConfig = StringUtils.stripAll(StringUtils.strip(equalsSplit[1]).split(","));
      final Properties properties = new Properties();
      for (final String property : propertiesConfig) {
         final String[] keyValue = StringUtils.stripAll(property.split(":", 2));
         properties.setProperty(keyValue[0], keyValue[1]);
      }

      try {
         System.out.println("INFO Summoning " + count + " instances of " + clazz + " with properties " + properties);
         for (int i = 0; i < count; i++) {
            workers.add((Worker) ObjectFactory.summonInstance(clazz, properties));
         }

         return count;
      } catch (ReflectiveOperationException e) {
         System.out.println("ERROR Unable to parse line: " + configLine);
         e.printStackTrace();
      }

      return 0;
   }

   private void run() throws IOException {
      final WeaverServer server = new WeaverServer(workers, executor, port, host);
      System.out.println("INFO Started server listening on " + host + ":" + port);
      System.out.println("INFO Press Ctrl+C to terminate...");
      System.in.read();
      server.close();
   }
}