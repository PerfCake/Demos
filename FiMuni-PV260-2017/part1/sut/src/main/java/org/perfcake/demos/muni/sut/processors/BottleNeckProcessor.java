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
package org.perfcake.demos.muni.sut.processors;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;

/**
 * @author <a href="mailto:pavel.macik@gmail.com">Pavel Macík</a>
 */
public class BottleNeckProcessor implements Processor {

   @Override
   public void process(final Exchange exchange) throws Exception {
      final Message message = exchange.getIn();
      final String request = message.getBody(String.class);
      String response = "";
      if ("1".equals(message.getHeader("option", String.class))) {
         for (int i = 0; i < request.length(); i++) {
            response = response + request.charAt(i);
         }
      } else {
         response = request;
      }
      message.setBody("Touched: " + response);
   }
}
