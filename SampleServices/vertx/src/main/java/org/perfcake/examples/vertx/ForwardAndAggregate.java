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
public class ForwardAndAggregate {

   public static void main(String[] args) throws IOException {
      Vertx vertx = VertxFactory.newVertx();

      if (args.length > 0 && "-c".equals(args[0])) {
         System.out.println("Display client listening on 9092.");
         HTTPTargetVerticle targetVerticle = new HTTPTargetVerticle();
         targetVerticle.setVertx(vertx);
         targetVerticle.start();
      }

      HTTPAggregatorVerticle aggregatorVerticle = new HTTPAggregatorVerticle();
      aggregatorVerticle.setVertx(vertx);
      aggregatorVerticle.start();

      HTTPForwardVerticle httpForwardVerticle = new HTTPForwardVerticle();
      httpForwardVerticle.setVertx(vertx);
      httpForwardVerticle.start();

      System.out.println("9090 --> 9091 - - ?>5kB - -> 9092. Hit Ctrl-C to stop...");

      System.in.read();
   }
}
