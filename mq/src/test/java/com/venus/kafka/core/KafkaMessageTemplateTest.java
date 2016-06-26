package com.venus.kafka.core;

import com.venus.kafka.codec.MessageDecoderImpl;
import com.venus.kafka.codec.MessageEncoderImpl;
import com.venus.kafka.pool.KafkaMessageReceiverPool;
import com.venus.kafka.pool.KafkaMessageSenderPool;
import com.venus.mq.exception.MQException;
import com.venus.mq.message.MessageBeanImpl;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class KafkaMessageTemplateTest {

	
	@Test
	public void test() throws Exception {

		KafkaMessageTemplate<MessageBeanImpl> template = new KafkaMessageTemplate<MessageBeanImpl>();

		Assert.assertNull(template.getDecoder());
		template.setDecoder(new MessageDecoderImpl());

		Assert.assertNull(template.getEncoder());
		template.setEncoder(new MessageEncoderImpl());

		Assert.assertNull(template.getMessageSenderPool());
		template.setMessageSenderPool(new KafkaMessageSenderPoolImpl());

		MessageBeanImpl messageBean = new MessageBeanImpl();
		long date = System.currentTimeMillis();
		messageBean.setMessageNo("MessageNo");
		messageBean.setMessageType("MessageType");
		messageBean.setMessageAckNo("MessageAckNo");
		messageBean.setMessageDate(date);
		messageBean.setMessageContent("MessageContent".getBytes("UTF-8"));

		template.convertAndSend(new KafkaDestination("test"), messageBean);

		Assert.assertNull(template.getMessageReceiverPool());
		template.setMessageReceiverPool(new KafkaMessageReceiverPoolImpl());

		List<MessageBeanImpl> list = template.receiveAndConvert(new KafkaDestination("test"), 1, 0, 1);

        for(MessageBeanImpl m: list){
            System.out.println(m.getMessageContent());
        }
        System.out.println(list.size());

	}

	private class KafkaMessageSenderPoolImpl extends
			KafkaMessageSenderPool<byte[], byte[]> {

		@Override
		public KafkaMessageSender<byte[], byte[]> getSender(long waitTimeMillis) {

			return new KafkaMessageSender<byte[], byte[]>() {

				@Override
				public void shutDown() {

					System.out.println("shutDown");
				}

				@Override
				public void sendWithKey(String topic, byte[] key, byte[] value) {

					System.out.println("sendWithKey" + topic);
				}

				@Override
				public void send(String topic, byte[] value) {

					System.out.println("send" + topic);
				}

				@Override
				public void close() {

					System.out.println("close");
				}
			};
		}
	}

	private class KafkaMessageReceiverPoolImpl extends
			KafkaMessageReceiverPool<byte[], byte[]> {

		@Override
		public KafkaMessageReceiver<byte[], byte[]> getReceiver() {

			return new KafkaMessageReceiver<byte[], byte[]>() {

				@Override
				public Map<byte[], byte[]> receiveWithKey(String topic,
						int partition, long beginOffset, long readOffset) {

					System.out.println("receiveWithKey" + topic);

					return null;
				}

				@Override
				public List<byte[]> receive(String topic, int partition,
						long beginOffset, long readOffset) {

					System.out.println("receive" + topic);

					MessageBeanImpl messageBean = new MessageBeanImpl();
					
					try {
						return Arrays.asList(new MessageEncoderImpl().encode(messageBean));
					} catch (MQException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					return null;
				}

				@Override
				public long getLatestOffset(String topic, int partition) {

					System.out.println("getLatestOffset" + topic);

					return 0;
				}

				@Override
				public long getEarliestOffset(String topic, int partition) {

					System.out.println("getEarliestOffset" + topic);

					return 0;
				}

				@Override
				public void close() {

					System.out.println("close");
				}
			};
		}

	}
}
