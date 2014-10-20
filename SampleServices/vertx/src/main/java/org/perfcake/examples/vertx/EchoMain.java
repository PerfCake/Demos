/*
 * -----------------------------------------------------------------------\
 * PerfCake
 *  
 * Copyright (C) 2010 - 2013 the original author or authors.
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
package org.perfcake.examples.vertx;

import org.vertx.java.core.Vertx;
import org.vertx.java.core.VertxFactory;

import java.io.IOException;

/**
 * @author Martin Večeřa <marvenec@gmail.com>
 */
public class EchoMain {

   public static void main(String[] args) throws IOException {
      Vertx vertx = VertxFactory.newVertx();

      HTTPEchoVerticle javaEcho = new HTTPEchoVerticle();
      javaEcho.setVertx(vertx);
      javaEcho.start();

      GroovyHTTPVerticle groovyEcho = new GroovyHTTPVerticle();
      groovyEcho.setVertx(vertx);
      groovyEcho.start();

      System.out.println("Listening at :8080 (Java) and :8181 (Groovy). Hit Ctrl-C to stop...");

      System.in.read();
   }
}
