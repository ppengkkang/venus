package com.venus.mq.common;

import com.venus.mq.exception.MQException;
import com.venus.mq.factory.ProducerFactory;
import com.venus.mq.producer.Producer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;

public class MessageProducerFactory implements ProducerFactory {

	/** logger */
	protected Logger logger = LoggerFactory.getLogger(MessageProducerFactory.class);

	/** instance */
	private static MessageProducerFactory instance;

	/** producers */
	private Producer<?>[] producers;

	/** producerCache */
	private ConcurrentHashMap<String, Producer<?>> producerCache = new ConcurrentHashMap<String, Producer<?>>();

	/**
	 * @param producers
	 *            the producers to set
	 */
	public void setProducers(Producer<?>[] producers) {
		this.producers = producers;
	}

	/**
	 * private construction method
	 */
	private MessageProducerFactory() {
	}

	/**
	 * get singleton instance method
	 */
	public synchronized static ProducerFactory getInstance() {

		if (instance == null)
			instance = new MessageProducerFactory();
		return instance;
	}

	@Override
	public <T> void addProducer(Producer<T> producer) throws MQException {

		producerCache.put(producer.getProducerKey(), producer);

		logger.debug("Add Producer : " + producer.getProducerKey());

	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> Producer<T> getProducer(String producerKey) throws MQException {

		if (producerCache.containsKey(producerKey)) {

			logger.debug("Get Producer : " + producerKey);

			return (Producer<T>) producerCache.get(producerKey);

		} else {

			logger.warn("Unknown ProducerKey : " + producerKey);

			return null;
		}
	}

	@Override
	public void init() throws MQException {

		if (producers != null)

			for (int i = 0; i < producers.length; i++)

				producerCache.put(producers[i].getProducerKey(), producers[i]);

	}

	@Override
	public void destroy() throws MQException {

		if (producers != null)
			producers = null;

		if (instance != null)
			instance = null;

		if (producerCache != null)
			producerCache.clear();

		logger.debug("Destroyed!");
	}
}
