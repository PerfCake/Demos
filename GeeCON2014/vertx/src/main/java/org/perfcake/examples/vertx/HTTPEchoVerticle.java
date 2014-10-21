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

import org.vertx.java.core.Handler;
import org.vertx.java.core.buffer.Buffer;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.platform.Verticle;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * @author Martin Večeřa <marvenec@gmail.com>
 */
public class HTTPEchoVerticle extends Verticle {

   static private Map<BadKey, Buffer> register = Collections.synchronizedMap(new HashMap<BadKey, Buffer>());
   private Semaphore getSemaphore = new Semaphore(100);
   private Semaphore deleteSemaphore = new Semaphore(100);
   private Random random = new Random();

   // Bad hash key is missing equals and hashCode
   static private class BadKey {
      public final String key;
      public BadKey(String key) {
         this.key = key;
      }
   }

   // Max 100 simultaneous requests
   private void get(final HttpServerRequest req, final Buffer body) {
      if (!getSemaphore.tryAcquire()) {
         try {
            try {
               Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
               // ignore
            }
         } finally {
            getSemaphore.release();
            req.response().end("Done GET.");
         }
      } else {
         req.response().setStatusCode(404);
         req.response().setStatusMessage("Too many connections.");
         req.response().end();
      }
   }

   // Just a polite answer
   private void post(final HttpServerRequest req, final Buffer body) {
      req.response().end("<html><body><h1>Hello " + body.toString() + "!</h1></body></html>");
   }

   // Hidden memory leak
   private void put(final HttpServerRequest req, final Buffer body) {
      register.put(new BadKey(req.localAddress().toString()), body);
      req.response().end("Registered body for: " + req.localAddress().toString());
   }

   // Combines GET and PUT = max requests + memory leak
   private void delete(final HttpServerRequest req, final Buffer body) {
      if (!deleteSemaphore.tryAcquire()) {
         try {
            try {
               register.put(new BadKey(req.localAddress().toString()), body);
               Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
               // ignore
            }
         } finally {
            deleteSemaphore.release();
            req.response().end("Deleted some more resources. Currently in register: " + register.size());
         }
      } else {
         req.response().setStatusCode(404);
         req.response().setStatusMessage("Too many connections.");
         req.response().end();
      }
   }

   public void start() {
      vertx.createHttpServer().requestHandler(new Handler<HttpServerRequest>() {
         public void handle(final HttpServerRequest req) {
            req.response().headers().set("Content-Type", "text/html; charset=UTF-8");
            req.bodyHandler(new Handler<Buffer>() {
               public void handle(Buffer body) {
                  if ("GET".equals(req.method().toUpperCase())) {
                     get(req, body);
                  } else if ("POST".equals(req.method().toUpperCase())) {
                     post(req, body);
                  } else if ("PUT".equals(req.method().toUpperCase())) {
                     put(req, body);
                  } else if ("DELETE".equals(req.method().toUpperCase())) {
                     delete(req, body);
                  } else {
                     req.response().end("<html><body><h1>Hello World!</h1></body></html>");
                  }
               }
            });
         }
      }).listen(8080);
   }
}
