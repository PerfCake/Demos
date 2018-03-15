/*
 * -----------------------------------------------------------------------\
 * SilverWare
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
package org.perfcake.demos.muni.sut.routes;

import org.perfcake.demos.muni.sut.processors.BottleNeckProcessor;
import org.perfcake.demos.muni.sut.processors.MemoryLeakProcessor;
import org.perfcake.demos.muni.sut.processors.NopProcessor;
import org.perfcake.demos.muni.sut.processors.TouchProcessor;

import org.apache.camel.builder.RouteBuilder;

/**
 * @author <a href="mailto:pavel.macik@gmail.com">Pavel Macík</a>
 */
public class RestRouteBuilder extends RouteBuilder {

   private NopProcessor nopProcessor = new NopProcessor();
   private TouchProcessor touchProcessor = new TouchProcessor();
   private MemoryLeakProcessor memoryLeakProcessor = new MemoryLeakProcessor();
   private BottleNeckProcessor bottleNeckProcessor = new BottleNeckProcessor();

   @Override
   public void configure() throws Exception {

      from("jetty:http://0.0.0.0:8888/nop?httpMethodRestrict=POST").process(nopProcessor);

      from("jetty:http://0.0.0.0:8888/touch/processor?httpMethodRestrict=POST").process(touchProcessor);

      from("jetty:http://0.0.0.0:8888/touch/simple?httpMethodRestrict=POST").setBody(simple("Touched1: ${body}"));

      from("jetty:http://0.0.0.0:8888/memory-leak?httpMethodRestrict=POST").process(memoryLeakProcessor);

      from("jetty:http://0.0.0.0:8888/bottle-neck?httpMethodRestrict=POST").process(bottleNeckProcessor);
   }
}