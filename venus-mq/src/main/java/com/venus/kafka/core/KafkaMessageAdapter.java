package com.venus.kafka.core;

import com.venus.mq.codec.MessageDecoder;
import com.venus.mq.exception.MQException;
import com.venus.mq.listener.MessageListener;

public class KafkaMessageAdapter<T> {

	/** decoder */
	private MessageDecoder<T> decoder;
	
	/** consumerListener */
	private MessageListener<T> messageListener;

	/** destination */
	private KafkaDestination destination;
	
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
	 * @return the messageListener
	 */
	public MessageListener<T> getMessageListener() {
		return messageListener;
	}

	/**
	 * @param messageListener the messageListener to set
	 */
	public void setMessageListener(MessageListener<T> messageListener) {
		this.messageListener = messageListener;
	}
	
	/**
	 * @return the destination
	 */
	public KafkaDestination getDestination() {
		return destination;
	}

	/**
	 * @param destination the destination to set
	 */
	public void setDestination(KafkaDestination destination) {
		this.destination = destination;
	}

	/**
	 * <p>Title: messageAdapter</p>
	 * <p>Description: 消息适配方法</p>
	 *
	 * @param key 消息key
	 * @param value 消息 value
	 * @throws MQException
	 */
	public void messageAdapter(Object key, Object value) throws MQException{
		
		byte[] bytes = (byte[])value;
		
		T message = decoder.decode(bytes);
		
		messageListener.onMessage(message);
	}
}

