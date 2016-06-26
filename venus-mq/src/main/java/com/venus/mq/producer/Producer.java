package com.venus.mq.producer;

import com.venus.mq.exception.MQException;

public interface Producer<T> {

	/**
	 * <p>Title: send</p>
	 * <p>Description: 发送消息</p>
	 *
	 * @param message 消息
	 * @throws MQException MQ异常
	 */
	public abstract void send(T message) throws MQException;

	/**
	 * <p>Title: getProducerKey</p>
	 * <p>Description: 生产者标识</p>
	 *
	 * @return 生产者标识
	 * @throws MQException MQ异常
	 */
	public abstract String getProducerKey() throws MQException;

}
