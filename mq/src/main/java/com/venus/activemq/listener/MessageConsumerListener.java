package com.venus.activemq.listener;

import com.venus.mq.consumer.Consumer;
import com.venus.mq.exception.MQException;
import com.venus.mq.listener.MessageListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;

public class MessageConsumerListener<T> implements MessageListener<T> {

	/** logger */
	protected Logger logger = LoggerFactory.getLogger(MessageConsumerListener.class);

	/** messageConsumer */
	private Consumer<T> consumer;

	/** threadPool */
	private ExecutorService threadPool;

	/**
	 * @return the consumer
	 */
	public Consumer<T> getConsumer() {
		return consumer;
	}

	/**
	 * @param consumer
	 *            the consumer to set
	 */
	public void setConsumer(Consumer<T> consumer) {
		this.consumer = consumer;
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

		if (consumer != null)

			if (threadPool != null)

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
			else
				consumer.receive(message);
		else
			throw new MQException("Consumer is null !");

	}
}
