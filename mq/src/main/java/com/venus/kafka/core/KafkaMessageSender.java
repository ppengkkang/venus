package com.venus.kafka.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface KafkaMessageSender<K,V> {

	/** logger */
	Logger logger = LoggerFactory.getLogger(KafkaMessageSender.class);
	
	/**
	 * <p>Title: send</p>
	 * <p>Description: Send the msg to Kafka</p>
	 *
	 * @param topic topic name
	 * @param value data to be sent
	 */
	void send(String topic, V value);
	
	/**
	 * <p>Title: sendWithKey</p>
	 * <p>Description: Send the msg to Kafka</p>
	 *
	 * @param topic topic name
	 * @param key the key of data
	 * @param value data to be sent
	 */
	void sendWithKey(String topic, K key, V value);
	
	/**
	 * <p>Title: close</p>
	 * <p>Description: The sender is not really closed but sent back into pool.</p>
	 *
	 */
	void close();
	
	/**
	 * <p>Title: shutDown</p>
	 * <p>Description: Shutdown this sender, so it could not be used again.</p>
	 *
	 */
	public void shutDown();

}
