package com.venus.kafka.listener;

import com.venus.mq.consumer.Consumer;
import com.venus.mq.exception.MQException;
import com.venus.mq.factory.ConsumerFactory;
import com.venus.mq.listener.MessageListener;
import com.venus.mq.util.RefleTool;

public class MessageFactoryConsumerListener<T> implements MessageListener<T> {

	/** consumerKeyField */
	private String consumerKeyField;
	
	/** consumerFactory */
	private ConsumerFactory consumerFactory;

	/**
	 * @return the consumerKeyField
	 */
	public String getConsumerKeyField() {
		return consumerKeyField;
	}

	/**
	 * @param consumerKeyField
	 *            the consumerKeyField to set
	 */
	public void setConsumerKeyField(String consumerKeyField) {
		this.consumerKeyField = consumerKeyField;
	}
	
	/**
	 * @return the consumerFactory
	 */
	public ConsumerFactory getConsumerFactory() {
		return consumerFactory;
	}

	/**
	 * @param consumerFactory
	 *            the consumerFactory to set
	 */
	public void setConsumerFactory(ConsumerFactory consumerFactory) {
		this.consumerFactory = consumerFactory;
	}

	@Override
	public void onMessage(T message) throws MQException {

		if (consumerFactory == null)	
			throw new MQException("ConsumerFactory is null !");

		if (consumerKeyField == null)	
			throw new MQException("ConsumerKeyField is null !");
		
		if (message == null)	
			throw new MQException("Message is null !");
		
		String consumerKey = RefleTool.getFieldValue(message, consumerKeyField, String.class);
		
		if (consumerKey == null)	
			throw new MQException("Consumer Key is null !");
		
		Consumer<T> consumer = consumerFactory.getConsumer(consumerKey);
		
		if (consumer == null)
			throw new MQException("Consumer is null !");
		
		consumer.receive(message);
	}
}
