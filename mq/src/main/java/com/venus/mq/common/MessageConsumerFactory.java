package com.venus.mq.common;

import com.venus.mq.consumer.Consumer;
import com.venus.mq.exception.MQException;
import com.venus.mq.factory.ConsumerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;


public class MessageConsumerFactory implements ConsumerFactory {

	/** logger */
	protected Logger logger = LoggerFactory.getLogger(MessageConsumerFactory.class);

	/** instance */
	private static MessageConsumerFactory instance;

	/** consumers */
	private Consumer<?>[] consumers;

	/** consumerCache */
	private ConcurrentHashMap<String, Consumer<?>> consumerCache = new ConcurrentHashMap<String, Consumer<?>>();

	/**
	 * @param consumers
	 *            the consumers to set
	 */
	public void setConsumers(Consumer<?>[] consumers) {
		this.consumers = consumers;
	}

	/**
	 * private construction method
	 */
	private MessageConsumerFactory() {
	}

	/**
	 * get singleton instance method
	 */
	public synchronized static ConsumerFactory getInstance() {

		if (instance == null)
			instance = new MessageConsumerFactory();
		return instance;
	}

	@Override
	public <T> void addConsumer(Consumer<T> consumer) throws MQException {

		consumerCache.put(consumer.getConsumerKey(), consumer);

		logger.debug("Add Consumer : " + consumer.getConsumerKey());
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> Consumer<T> getConsumer(String consumerKey) throws MQException {
		
		if (consumerCache.containsKey(consumerKey)) {
			
			logger.debug("Get Consumer : " + consumerKey);
			
			return (Consumer<T>) consumerCache.get(consumerKey);
			
		} else {
			
			logger.warn("Unknown ConsumerKey : " + consumerKey);

			return null;
		}
	}

	@Override
	public void init() throws MQException {

		if (consumers != null)

			for (int i = 0; i < consumers.length; i++)

				consumerCache.put(consumers[i].getConsumerKey(), consumers[i]);

		logger.debug("Initialized!");

	}

	@Override
	public void destroy() throws MQException {

		if (consumers != null)
			consumers = null;

		if (instance != null)
			instance = null;

		if (consumerCache != null)
			consumerCache.clear();

		logger.debug("Destroyed!");
	}

}
