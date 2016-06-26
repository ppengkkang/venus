package com.venus.kafka.producer;

import com.venus.kafka.core.KafkaDestination;
import com.venus.kafka.core.KafkaMessageTemplate;
import com.venus.mq.exception.MQException;
import com.venus.mq.producer.Producer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractProducer<T> implements Producer<T>  {

	/** logger */
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	/** messageTemplate */
	private KafkaMessageTemplate<T> messageTemplate;
	
	/** destination */
	private KafkaDestination destination;
	
	/** @since 1.2.3 producerKey */
	private String producerKey;
	
	/**
	 * @return the messageTemplate
	 */
	public KafkaMessageTemplate<T> getMessageTemplate() {
		return messageTemplate;
	}

	/**
	 * @param messageTemplate the messageTemplate to set
	 */
	public void setMessageTemplate(KafkaMessageTemplate<T> messageTemplate) {
		this.messageTemplate = messageTemplate;
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
	 * @since 1.2.3
	 * @param producerKey the producerKey to set
	 */
	public void setProducerKey(String producerKey) {
		this.producerKey = producerKey;
	}
	
	@Override
	public void send(T message) throws MQException {

		try {
			T obj = doSend(message);

			messageTemplate.convertAndSend(destination, obj);

		} catch (Exception e) {

			throw new MQException(e);
		}

		logger.debug("Send Success, ProducerKey : " + this.getProducerKey()
				+ " , Message : " + message);

	}
	
	@Override
	public String getProducerKey() throws MQException {

		if (this.producerKey != null)
			
			return this.producerKey;
		
		return destination.getDestinationName();
	}
	
	/**
	 * <p>Title: doSend</p>
	 * <p>Description: 消息发送方法</p>
	 *
	 * @param message 消息
	 * @return 消息
	 * @throws MQException MQ异常
	 */
	protected abstract T doSend(T message) throws MQException;
}
