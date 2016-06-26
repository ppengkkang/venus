package com.venus.kafka.listener;


import com.venus.kafka.consumer.MessageConsumer;
import com.venus.mq.exception.MQException;
import org.junit.Assert;
import org.junit.Test;

public class MessageConsumerListenerTest {

	@Test
	public void test() throws Exception {
		
		MessageConsumerListener<String> listener = new MessageConsumerListener<String>();
		
		try {
			listener.onMessage("test");
		} catch (Exception e) {
			Assert.assertTrue(e instanceof MQException);
		}

		MessageConsumer<String> consumer = new MessageConsumer<String>();

		consumer.setConsumerKey("ProtocolId");
		
		Assert.assertNull(listener.getConsumer());
		listener.setConsumer(consumer);
		
		listener.onMessage("test");
	}
	
}
