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

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

import java.io.File;
import java.util.concurrent.Executors;

public final class Weaver {

   @Parameter(names = {"-t", "--threads"}, description = "Number of threads, 0 = unlimited")
   private int threads = 0;

   @Parameter(names = {"-s", "--shuffle"}, description = "Shuffle the workers", arity = 1)
   private boolean shuffle = false;

   @Parameter(names = {"-c", "--config"}, description = "Configuration file", required = true)
   private String config = null;

   @Parameter(names = {"--help"}, description = "Prints out command usage")
   private boolean help = false;

   @Parameter(names = {"-p", "--port"}, description = "Network port to listen on")
   private int port = 8080;

   @Parameter(names = {"-h", "--host"}, description = "Network host to listen on")
   private String host = "localhost";

   public static void main(String[] args) {
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
   }

   private void init() {
   }

   private void run() {

   }
}