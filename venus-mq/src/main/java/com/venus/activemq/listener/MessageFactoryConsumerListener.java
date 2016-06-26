package com.venus.activemq.listener;

import com.venus.mq.consumer.Consumer;
import com.venus.mq.exception.MQException;
import com.venus.mq.factory.ConsumerFactory;
import com.venus.mq.listener.MessageListener;
import com.venus.mq.util.RefleTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;

public class MessageFactoryConsumerListener<T> implements MessageListener<T> {

	/** logger */
	protected Logger logger = LoggerFactory.getLogger(MessageFactoryConsumerListener.class);
	
	/** consumerKeyField */
	private String consumerKeyField;
	
	/** consumerFactory */
	private ConsumerFactory consumerFactory;

	/** threadPool */
	private ExecutorService threadPool;

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

	/**
	 * @return the threadPool
	 */
	public ExecutorService getThreadPool() {
		return threadPool;
	}

	/**
	 * @param threadPool
	 *            the threadPool to set
	 */
	public void setThreadPool(ExecutorService threadPool) {
		this.threadPool = threadPool;
	}

	@Override
	public void onMessage(final T message) throws MQException {

		if (consumerFactory == null)	
			throw new MQException("ConsumerFactory is null !");

		if (consumerKeyField == null)	
			throw new MQException("ConsumerKeyField is null !");
		
		if (message == null)	
			throw new MQException("Message is null !");
		
		final String consumerKey = RefleTool.getFieldValue(message, consumerKeyField, String.class);
		
		if (consumerKey == null)	
			throw new MQException("Consumer Key is null !");
		
		final Consumer<T> consumer = consumerFactory.getConsumer(consumerKey);
		
		if (consumer == null)
			throw new MQException("Consumer is null !");
			
		if (threadPool == null)

			consumer.receive(message);
		
		else
			threadPool.execute(new Runnable() {

				@Override
				public void run() {

					try {
						consumer.receive(message);
					} catch (MQException e) {
						logger.error(e.getMessage());
					}
				}
			});
	}
}
