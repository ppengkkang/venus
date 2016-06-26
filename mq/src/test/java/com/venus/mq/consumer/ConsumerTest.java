package com.venus.mq.consumer;

import com.venus.mq.exception.MQException;
import org.junit.Assert;
import org.junit.Test;

public class ConsumerTest {

	@Test
	public void test() throws Exception {

		ConsumerImpl consumer = new ConsumerImpl();

		consumer.setConsumerKey("ProtocolId");

		consumer.receive(consumer.getConsumerKey());

		Assert.assertEquals(consumer.getConsumerKey(), consumer.getConsumerKey());
		
		ConsumerImpl2 consumer2 = new ConsumerImpl2();

		try {
			consumer2.receive(null);

		} catch (Exception e) {
			
			Assert.assertTrue(e instanceof MQException);
		} 
		
	}

	private class ConsumerImpl extends AbstractConsumer<String> {

		@Override
		protected void doReceive(String message) throws MQException {
			System.out.println(message);
		}
	}
	
	private class ConsumerImpl2 extends AbstractConsumer<String> {

		@Override
		protected void doReceive(String message) throws MQException {
			
			throw new MQException("Test");
		}
	}
}
