package org.cool.sender;

import java.io.Serializable;
import java.util.Map;

import org.perfcake.PerfCakeException;
import org.perfcake.message.Message;
import org.perfcake.message.sender.AbstractSender;
import org.perfcake.reporting.MeasurementUnit;

public class CoolSender extends AbstractSender {

	@Override
	public void init() throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public void close() throws PerfCakeException {
		// TODO Auto-generated method stub

	}

	@Override
	public Serializable doSend(Message message, Map<String, String> properties,
			MeasurementUnit mu) throws Exception {
		final Serializable msg = message.getPayload();
		System.out.println("CoolSender: Sending " + msg + " to " + getTarget());
		return msg;
	}

}
