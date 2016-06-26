package com.venus.mq.factory;

import com.venus.mq.consumer.Consumer;
import com.venus.mq.exception.MQException;

public interface ConsumerFactory {

	/**
	 * <p>Title: addConsumer</p>
	 * <p>Description: 增加消费者</p>
	 *
	 * @param consumer 消费者
	 * @throws MQException MQ异常
	 */
	public <T> void addConsumer(Consumer<T> consumer) throws MQException;

	/**
	 * <p>Title: getConsumer</p>
	 * <p>Description: 获得消费者</p>
	 *
	 * @param consumerKey 消费者标识
	 * @return 消费者
	 * @throws MQException MQ异常
	 */
	public <T> Consumer<T> getConsumer(String consumerKey) throws MQException;

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
