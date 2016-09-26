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
package org.perfcake.examples.geecon;

import java.net.HttpURLConnection;
import java.net.URL;
import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * @author <a href="mailto:marvenec@gmail.com">Martin Večeřa</a>
 */
@MessageDriven(mappedName = "JmsService", activationConfig = {
      @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
      @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
      @ActivationConfigProperty(propertyName = "destination", propertyValue = "java:/jms/queue/TestQueue")
})
public class JmsServiceBean implements MessageListener {

   @Resource
   private MessageDrivenContext mdc;

   @Override
   public void onMessage(final Message message) {
      if (message instanceof TextMessage) {
         System.out.println("Processing incoming message: " + message.toString());
         final TextMessage textMessage = (TextMessage) message;
         try {
            final String target = textMessage.getStringProperty("targetUrl");
            final String correlationId = textMessage.getStringProperty("perfcake_correlation_id");
            System.out.println("corr " + correlationId);
            if (target != null) {
               final HttpURLConnection con = (HttpURLConnection) new URL(target).openConnection();
               con.setDoOutput(true);
               con.setDoInput(false);
               if (correlationId != null && !correlationId.isEmpty()) {
                  con.setRequestProperty("perfcake.correlation.id", correlationId);
               }
               con.setRequestMethod("POST");
               con.getOutputStream().write(textMessage.getText().getBytes("UTF-8"));
               con.getOutputStream().flush();
               con.getOutputStream().close();
               System.out.println("done");
            }
         } catch (Exception e) {
            e.printStackTrace();
         }
      } else {
         System.out.println("Unknown message type: " + message.toString());
      }
   }
}
