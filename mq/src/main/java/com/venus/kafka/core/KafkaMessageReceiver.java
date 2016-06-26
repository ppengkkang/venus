package com.venus.kafka.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public interface KafkaMessageReceiver<K, V> {

	/** logger */
	Logger logger = LoggerFactory.getLogger(KafkaMessageReceiver.class);
	
	/**
	 * <p>Title: receive</p>
	 * <p>Description: Receive the msg from Kafka</p>
	 *
	 * @param topic Topic name
	 * @param partition Partition number
	 * @param beginOffset Begin the offset index
	 * @param readOffset Number of read messages
	 * @return message
	 */
	List<V> receive(String topic, int partition, long beginOffset, long readOffset);
	
	/**
	 * <p>Title: receiveWithKey</p>
	 * <p>Description: Receive the msg from Kafka</p>
	 *
	 * @param topic Topic name
	 * @param partition Partition number
	 * @param beginOffset Begin the offset index
	 * @param readOffset Number of read messages
	 * @return message
	 */
	Map<K, V> receiveWithKey(String topic, int partition, long beginOffset, long readOffset);
	
	/**
	 * <p>Title: getLatestOffset</p>
	 * <p>Description: Get latest offset number</p>
	 *
	 * @param topic Topic name
	 * @param partition Partition number
	 * @return the latest offset
	 */
	long getLatestOffset(String topic, int partition);
	
	/**
	 * <p>Title: getEarliestOffset</p>
	 * <p>Description: Get earliest offset number</p>
	 *
	 * @param topic Topic name
	 * @param partition Partition number
	 * @return the earliest offset
	 */
	long getEarliestOffset(String topic, int partition);

	/**
	 * <p>Title: close</p>
	 * <p>Description: Close this receiver</p>
	 *
	 */
	void close();
}
