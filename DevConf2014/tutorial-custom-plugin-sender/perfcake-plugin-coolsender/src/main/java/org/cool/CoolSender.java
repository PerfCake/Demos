package org.cool;

import java.io.Serializable;
import java.util.Map;

import org.perfcake.PerfCakeException;
import org.perfcake.message.Message;
import org.perfcake.message.sender.AbstractSender;
import org.perfcake.reporting.MeasurementUnit;

public class CoolSender extends AbstractSender {

   private String coolProperty;

   @Override
   public void init() throws Exception {
      // TODO Auto-generated method stub
   }

   @Override
   public void close() throws PerfCakeException {
      // TODO Auto-generated method stub

   }

   @Override
   public Serializable doSend(Message message, Map<String, String> properties, MeasurementUnit mu) throws Exception {
      final String msg = message.getPayload().toString();
      System.out.println("CoolSender is sending '" + msg + "' with " + coolProperty + " out to " + getTarget() + "!");
      return msg;
   }

   public String getCoolProperty() {
      return coolProperty;
   }

   public void setCoolProperty(String coolProperty) {
      this.coolProperty = coolProperty;
   }

}
