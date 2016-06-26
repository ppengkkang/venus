package com.venus.kafka.producer;

import com.venus.kafka.core.KafkaDestination;
import com.venus.kafka.core.KafkaMessageTemplate;
import com.venus.mq.exception.MQException;
import org.junit.Assert;
import org.junit.Test;

public class MessageProducerTest {

	@Test
	public void test() throws Exception {

		MessageProducer<String> producer = new MessageProducer<String>();

		Assert.assertNull(producer.getDestination());
		KafkaDestination destination = new KafkaDestination("TempQueue");
		producer.setDestination(destination);

		Assert.assertNull(producer.getMessageTemplate());
		KafkaMessageTemplateImpl messageTemplate = new KafkaMessageTemplateImpl();
		producer.setMessageTemplate(messageTemplate);

		Assert.assertEquals("TempQueue", producer.getProducerKey());

		producer.send("test");

		KafkaMessageTemplateImpl2 messageTemplate2 = new KafkaMessageTemplateImpl2();
		producer.setMessageTemplate(messageTemplate2);

		try {
			producer.send("err");
		} catch (Exception e) {
			Assert.assertTrue(e instanceof MQException);
		}

		producer.setProducerKey("QUEUE.TEST");

		Assert.assertEquals("QUEUE.TEST", producer.getProducerKey());
	}

	private class KafkaMessageTemplateImpl extends KafkaMessageTemplate<String> {

		@Override
		public void convertAndSend(KafkaDestination destination, String message)
				throws MQException {
			System.out.println(destination + ":" + message);
		}
	}

	private class KafkaMessageTemplateImpl2 extends
			KafkaMessageTemplate<String> {

		@Override
		public void convertAndSend(KafkaDestination destination, String message)
				throws MQException {

			throw new MQException("test");
		}
	}
}
