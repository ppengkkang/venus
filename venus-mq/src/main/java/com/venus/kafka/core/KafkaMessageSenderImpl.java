package com.venus.kafka.core;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import com.venus.kafka.pool.KafkaMessageSenderPool;

import java.util.Properties;

public class KafkaMessageSenderImpl<K, V> implements KafkaMessageSender<K, V> {

	/** producer */
	private Producer<K, V> producer;

	/** pool */
	private KafkaMessageSenderPool<K, V> pool;

	/**
	 * @return the producer
	 */
	public Producer<K, V> getProducer() {
		return producer;
	}

	/**
	 * @param producer
	 *            the producer to set
	 */
	public void setProducer(Producer<K, V> producer) {
		this.producer = producer;
	}

	/**
	 * @return the pool
	 */
	public KafkaMessageSenderPool<K, V> getPool() {
		return pool;
	}

	/**
	 * @param pool
	 *            the pool to set
	 */
	public void setPool(KafkaMessageSenderPool<K, V> pool) {
		this.pool = pool;
	}

	/**
	 * Construction method.
	 * 
	 * @param props
	 *            param props
	 * @param pool
	 *            sender Pool
	 */
	public KafkaMessageSenderImpl(Properties props,
			KafkaMessageSenderPool<K, V> pool) {
		super();
		ProducerConfig config = new ProducerConfig(props);
		this.producer = new Producer<K, V>(config);
		this.pool = pool;
	}

	@Override
	public void send(String topic, V value) {
		KeyedMessage<K, V> data = new KeyedMessage<K, V>(topic, value);
		this.producer.send(data);
	}

	@Override
	public void sendWithKey(String topic, K key, V value) {
		KeyedMessage<K, V> data = new KeyedMessage<K, V>(topic, key, value);
		this.producer.send(data);
	}

	@Override
	public void close() {
		this.pool.returnSender(this);
	}

	@Override
	public void shutDown() {
		this.producer.close();
	}

    public static void main(String [] args) {
        Properties prop = new Properties();
        //ZookeeperHosts zookeeperHosts = new ZookeeperHosts();
        KafkaMessageSenderPool kafkaMessageSenderPool = new KafkaMessageSenderPool();
        KafkaMessageSenderImpl kafkaMessageSenderImpl = new KafkaMessageSenderImpl(prop, kafkaMessageSenderPool);

    }

}
