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

import org.perfcake.demos.muni.sut.model.BadKey;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author <a href="mailto:pavel.macik@gmail.com">Pavel Macík</a>
 */
public class MemoryLeakProcessor implements Processor {
   static private Map<BadKey, String> register = Collections.synchronizedMap(new HashMap<BadKey, String>());
   private static final Random random = new Random();

   @Override
   public void process(final Exchange exchange) throws Exception {
      final Message message = exchange.getIn();
      register.put(new BadKey(message.getHeader("key", String.class)), message.getBody(String.class));
      Thread.sleep(random.nextInt(300));
      message.setBody("Consumed some more resources. Currently in register:" + register.size());
   }
}
