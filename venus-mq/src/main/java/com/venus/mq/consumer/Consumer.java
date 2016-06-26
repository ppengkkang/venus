package com.venus.mq.consumer;

import com.venus.mq.exception.MQException;

public interface Consumer<T> {
	
	/**
	 * <p>Title: receive</p>
	 * <p>Description: 接收消息</p>
	 *
	 * @param message 消息
	 * @throws MQException MQ异常
	 */
	public abstract void receive(T message) throws MQException;

	/**
	 * <p>Title: getConsumerKey</p>
	 * <p>Description: 消费者标识</p>
	 *
	 * @return 消费者标识
	 * @throws MQException MQ异常
	 */
	public abstract String getConsumerKey() throws MQException;
}
