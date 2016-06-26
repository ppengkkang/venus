package com.venus.kafka.core;

import com.venus.kafka.pool.KafkaMessageReceiverPool;
import com.venus.kafka.pool.KafkaMessageSenderPool;
import com.venus.mq.codec.MessageDecoder;
import com.venus.mq.codec.MessageEncoder;
import com.venus.mq.exception.MQException;

import java.util.List;

public class KafkaMessageTemplate<T> {

	/** messageSenderPool */
	private KafkaMessageSenderPool<byte[], byte[]> messageSenderPool;
	
	/** messageReceiverPool */
	private KafkaMessageReceiverPool<byte[], byte[]> messageReceiverPool;
	
	/** encoder */
	private MessageEncoder<T> encoder;
	
	/** decoder */
	private MessageDecoder<T> decoder;
	
	/**
	 * @param messageSenderPool the messageSenderPool to set
	 */
	public void setMessageSenderPool(
			KafkaMessageSenderPool<byte[], byte[]> messageSenderPool) {
		this.messageSenderPool = messageSenderPool;
	}
	
	/**
	 * @return the messageSenderPool
	 */
	public KafkaMessageSenderPool<byte[], byte[]> getMessageSenderPool() {
		return messageSenderPool;
	}
	
	/**
	 * @return the messageReceiverPool
	 */
	public KafkaMessageReceiverPool<byte[], byte[]> getMessageReceiverPool() {
		return messageReceiverPool;
	}

	/**
	 * @param messageReceiverPool the messageReceiverPool to set
	 */
	public void setMessageReceiverPool(
			KafkaMessageReceiverPool<byte[], byte[]> messageReceiverPool) {
		this.messageReceiverPool = messageReceiverPool;
	}

	/**
	 * @param encoder the encoder to set
	 */
	public void setEncoder(MessageEncoder<T> encoder) {
		this.encoder = encoder;
	}
	
	/**
	 * @return the encoder
	 */
	public MessageEncoder<T> getEncoder() {
		return encoder;
	}
	
	/**
	 * @return the decoder
	 */
	public MessageDecoder<T> getDecoder() {
		return decoder;
	}

	/**
	 * @param decoder the decoder to set
	 */
	public void setDecoder(MessageDecoder<T> decoder) {
		this.decoder = decoder;
	}

	/**
	 * <p>Title: send</p>
	 * <p>Description: 发送消息</p>
	 *
	 * @param destination 队列
	 * @param message 消息
	 */
	public void send(KafkaDestination destination, byte[] message) throws MQException {
		
		KafkaMessageSender<byte[], byte[]> sender = messageSenderPool.getSender(KafkaConstants.WAIT_TIME_MS);

		sender.send(destination.getDestinationName(), message);

		sender.close();
	}
	
	/**
	 * <p>Title: convertAndSend</p>
	 * <p>Description: 转换并发送消息</p>
	 *
	 * @param destination 队列
	 * @param message 消息
	 * @throws MQException 
	 */
	public void convertAndSend(KafkaDestination destination, T message) throws MQException {
		
		byte[] encoded = encoder.encode(message);
		
		this.send(destination, encoded);
	}
	
	/**
	 * <p>Title: receive</p>
	 * <p>Description: 接收消息</p>
	 *
	 * @param destination 队列
	 * @param partition 分区编号
	 * @param beginOffset 起始位置
	 * @param readOffset 读取条数
	 * @return 消息列表
	 * @throws MQException
	 */
	public List<byte[]> receive(KafkaDestination destination, int partition, long beginOffset, long readOffset) throws MQException {
		
		KafkaMessageReceiver<byte[], byte[]> receiver = messageReceiverPool.getReceiver();
		
		List<byte[]> messages = receiver.receive(destination.getDestinationName(), partition, beginOffset, readOffset);
		
		receiver.close();
		
		return messages;
	}
	
	/**
	 * <p>Title: receiveAndConvert</p>
	 * <p>Description: 接收并转换消息</p>
	 *
	 * @param destination 队列
	 * @param partition 分区编号
	 * @param beginOffset 起始位置
	 * @param readOffset 读取条数
	 * @return 消息列表
	 * @throws MQException
	 */
	public List<T> receiveAndConvert(KafkaDestination destination, int partition, long beginOffset, long readOffset) throws MQException {
		
		List<byte[]> decoded = this.receive(destination, partition, beginOffset, readOffset);
		
		return decoder.batchDecode(decoded);
	}
}
