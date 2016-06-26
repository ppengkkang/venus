package com.venus.mq.consumer;

import com.venus.mq.exception.MQException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractConsumer<T> implements Consumer<T> {

	/** logger */
	protected Logger logger = LoggerFactory.getLogger(getClass());

	/** consumerKey */
	private String consumerKey;
	
	/**
	 * @param consumerKey the consumerKey to set
	 */
	public void setConsumerKey(String consumerKey) {
		this.consumerKey = consumerKey;
	}
	
	@Override
	public void receive(T message) throws MQException {

		try {
			doReceive(message);

		} catch (Exception e) {

			throw new MQException(e);
		}

		logger.debug("Receive Success, ConsumerKey : " + this.getConsumerKey()
				+ " , Message : " + message);
	}

	@Override
	public String getConsumerKey() throws MQException {

		return this.consumerKey;
	}

	/**
	 * <p>Title: doReceive</p>
	 * <p>Description: 消息接收方法</p>
	 * 
	 * @param message 消息
	 * @throws MQException MQ异常
	 */
	protected abstract void doReceive(T message) throws MQException;

}
