package com.venus.mq.listener;

import com.venus.mq.exception.MQException;

public interface MessageListener<T> {
	
	/**
	 * <p>Title: onMessage</p>
	 * <p>Description: 监听方法</p>
	 *
	 * @param message 消息
	 * @throws MQException MQ异常
	 */
	public abstract void onMessage(final T message) throws MQException;
}
