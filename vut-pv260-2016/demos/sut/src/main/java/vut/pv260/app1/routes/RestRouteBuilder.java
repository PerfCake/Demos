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
package vut.pv260.app1.routes;

import org.apache.camel.builder.RouteBuilder;

import vut.pv260.app1.processors.MemoryLeakProcessor;
import vut.pv260.app1.processors.NopProcessor;
import vut.pv260.app1.processors.TouchProcessor;

/**
 * @author <a href="mailto:pavel.macik@gmail.com">Pavel Macík</a>
 */
public class RestRouteBuilder extends RouteBuilder {

   private NopProcessor nopProcessor = new NopProcessor();
   private TouchProcessor touchProcessor = new TouchProcessor();
   private MemoryLeakProcessor memoryLeakProcessor = new MemoryLeakProcessor();

   @Override
   public void configure() throws Exception {
      from("jetty:http://0.0.0.0:8888/nop?httpMethodRestrict=POST").process(nopProcessor);

      from("jetty:http://0.0.0.0:8888/touch/processor?httpMethodRestrict=POST").process(touchProcessor);

      from("jetty:http://0.0.0.0:8888/touch/simple?httpMethodRestrict=POST").setBody(simple("Touched: ${body}"));

      from("jetty:http://0.0.0.0:8888/memoryLeak?httpMethodRestrict=POST").process(memoryLeakProcessor);
   }
}