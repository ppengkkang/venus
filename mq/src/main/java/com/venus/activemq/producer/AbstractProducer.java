package com.venus.activemq.producer;

import com.venus.mq.exception.MQException;
import com.venus.mq.producer.Producer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.Destination;
import javax.jms.Queue;
import javax.jms.Topic;



public abstract class AbstractProducer<T> implements Producer<T> {

	/** logger */
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	/** jmsTemplate */
	private JmsTemplate jmsTemplate;

	/** destination */
	private Destination destination;
	
	/** @since 1.2.3 producerKey */
	private String producerKey;

	/**
	 * @return the jmsTemplate
	 */
	public JmsTemplate getJmsTemplate() {
		return jmsTemplate;
	}

	/**
	 * @param jmsTemplate
	 *            the jmsTemplate to set
	 */
	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	/**
	 * @return the destination
	 */
	public Destination getDestination() {
		return destination;
	}

	/**
	 * @param destination
	 *            the destination to set
	 */
	public void setDestination(Destination destination) {
		this.destination = destination;
	}

	/**
	 * @since 1.2.3
	 * @param producerKey
	 *            the producerKey to set
	 */
	public void setProducerKey(String producerKey) {
		this.producerKey = producerKey;
	}
	
	@Override
	public void send(T message) throws MQException {

		try {
			Object obj = doSend(message);

			jmsTemplate.convertAndSend(destination, obj);

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
		
		if (destination instanceof Queue)

			try {
				return ((Queue) destination).getQueueName();

			} catch (Exception e) {

				throw new MQException(e);
			}

		else if (destination instanceof Topic)

			try {
				return ((Topic) destination).getTopicName();

			} catch (Exception e) {

				throw new MQException(e);
			}

		else
			return destination.toString();
	}

	/**
	 * <p>Title: doSend</p>
	 * <p>Description: 消息发送方法</p>
	 *
	 * @param message 消息
	 * @return 消息
	 * @throws MQException MQ异常
	 */
	protected abstract Object doSend(T message) throws MQException;
}
