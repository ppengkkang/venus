package com.venus.kafka.core;

import com.venus.kafka.codec.MessageDecoderImpl;
import com.venus.kafka.codec.MessageEncoderImpl;
import com.venus.activemq.listener.MessageConsumerListener;
import com.venus.mq.consumer.Consumer;
import com.venus.mq.exception.MQException;
import com.venus.mq.message.MessageBeanImpl;
import org.junit.Assert;
import org.junit.Test;

public class KafkaMessageAdapterTest {

	@Test
	public void test() throws Exception {

		KafkaMessageAdapter<MessageBeanImpl> adapter = new KafkaMessageAdapter<MessageBeanImpl>();

		Assert.assertNull(adapter.getDecoder());
		adapter.setDecoder(new MessageDecoderImpl());

		Assert.assertNull(adapter.getDestination());
		adapter.setDestination(new KafkaDestination("QUEUE.TEST"));

		Assert.assertNull(adapter.getMessageListener());

		MessageConsumerListener<MessageBeanImpl> listener = new MessageConsumerListener<MessageBeanImpl>();
		listener.setConsumer(new Consumer<MessageBeanImpl>() {
			
			@Override
			public void receive(MessageBeanImpl message) throws MQException {
				System.out.println(message);
			}
			
			@Override
			public String getConsumerKey() throws MQException {
				return "";
			}
		});
		
		adapter.setMessageListener(listener);

		MessageBeanImpl messageBean = new MessageBeanImpl();

		long date = System.currentTimeMillis();
		messageBean.setMessageNo("MessageNo");
		messageBean.setMessageType("MessageType");
		messageBean.setMessageAckNo("MessageAckNo");
		messageBean.setMessageDate(date);
		messageBean.setMessageContent("MessageContent".getBytes("UTF-8"));

		MessageEncoderImpl encoder = new MessageEncoderImpl();
		
		adapter.messageAdapter(null, encoder.encode(messageBean));
	}
}
