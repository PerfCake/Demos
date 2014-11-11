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
import org.vertx.java.core.Vertx;
import org.vertx.java.core.buffer.Buffer;
import org.vertx.java.core.http.HttpClientResponse;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.platform.Verticle;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author Martin Večeřa <marvenec@gmail.com>
 */
public class HTTPAggregatorVerticle extends Verticle {

   private ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(100);

   private static class AggregatorHandler implements Runnable {

      private String body;
      private Vertx vertx;

      public AggregatorHandler(Vertx vertx, String body) {
         this.vertx = vertx;
         this.body = body;
      }

      @Override
      public void run() {
         vertx.createHttpClient().setPort(9092).post("/", new Handler<HttpClientResponse>() {
            @Override
            public void handle(HttpClientResponse httpClientResponse) {
               // ignore
            }
         }).end(body);
      }
   }

   public void start() {
      final StringBuilder builder = new StringBuilder();

      vertx.createHttpServer().requestHandler(new Handler<HttpServerRequest>() {

         public void handle(final HttpServerRequest req) {
            req.bodyHandler(new Handler<Buffer>() {

               public void handle(Buffer body) {
                  builder.append(body.toString());
                  if (builder.length() > 1024 * 5) {
                     executor.submit(new AggregatorHandler(vertx, builder.toString()));
                     builder.setLength(0);
                  }
                  req.response().end("ok");
               }
            });
         }
      }).listen(9091);
   }
}
