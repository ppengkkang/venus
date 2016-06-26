package com.venus.mq.factory;

import com.venus.mq.exception.MQException;
import com.venus.mq.producer.Producer;

public interface ProducerFactory {
	
	/**
	 * <p>Title: addProducer</p>
	 * <p>Description: 增加生产者</p>
	 *
	 * @param producer 生产者
	 * @throws MQException MQ异常
	 */
	public <T> void addProducer(Producer<T> producer) throws MQException;

	/**
	 * <p>Title: getProducer</p>
	 * <p>Description: 获得生产者</p>
	 *
	 * @param producerKey 生产者标识
	 * @return 生产者
	 * @throws MQException MQ异常
	 */
	public <T> Producer<T> getProducer(String producerKey) throws MQException;

	/**
	 * <p>Title: init</p>
	 * <p>Description: 初始化工厂</p>
	 *
	 * @throws MQException MQ异常
	 */
	public void init() throws MQException;
	
	/**
	 * <p>Title: destroy</p>
	 * <p>Description: 销毁工厂</p>
	 * 
	 * @throws MQException MQ异常
	 */
	public void destroy() throws MQException;
}
