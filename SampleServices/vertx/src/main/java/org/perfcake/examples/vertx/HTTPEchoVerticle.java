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

/**
 * @author Martin Večeřa <marvenec@gmail.com>
 */
public class HTTPEchoVerticle extends Verticle {

   public void start() {
      vertx.createHttpServer().requestHandler(new Handler<HttpServerRequest>() {
         public void handle(final HttpServerRequest req) {
            req.bodyHandler(new Handler<Buffer>() {
               public void handle(Buffer body) {
                  req.response().headers().set("Content-Type", "text/html; charset=UTF-8");
                  req.response().end("<html><body><h1>Hello " + ("GET".equals(req.method().toUpperCase()) ? req.path().substring(1) : body.toString()) + "!</h1></body></html>");
               }
            });
         }
      }).listen(8080);
   }
}
