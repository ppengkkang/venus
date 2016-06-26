package com.venus.mq.common;

import com.venus.mq.message.MessageBeanImpl;
import com.venus.mq.consumer.AbstractConsumer;
import com.venus.mq.consumer.Consumer;
import com.venus.mq.exception.MQException;
import org.junit.Assert;
import org.junit.Test;

public class MessageConsumerFactoryTest {

	@Test
	public void test_0() throws Exception {

		MessageConsumerFactory factory = (MessageConsumerFactory) MessageConsumerFactory
				.getInstance();

		ConsumerTest consumer1 = new ConsumerTest();
		consumer1.setConsumerKey("ProtocolId1");

		ConsumerTest consumer2 = new ConsumerTest();
		consumer2.setConsumerKey("ProtocolId2");
		
		factory.setConsumers(new Consumer[] { consumer1 });
		factory.addConsumer(consumer2);
		factory.init();
		
		Assert.assertEquals(consumer1, factory.getConsumer("ProtocolId1"));
		Assert.assertEquals(consumer2, factory.getConsumer("ProtocolId2"));
		Assert.assertNull(factory.getConsumer("ProtocolId3"));

		
		factory.destroy();

	}

	private class ConsumerTest extends AbstractConsumer<MessageBeanImpl> {

		@Override
		protected void doReceive(MessageBeanImpl message) throws MQException {
			
			System.out.println(message);
		}
	}
}
