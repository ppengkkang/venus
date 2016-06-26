package com.venus.activemq.producer;

import com.venus.mq.exception.MQException;

public class MessageProducer<T> extends AbstractProducer<T> {

	@Override
	protected Object doSend(T message) throws MQException {

		return message;
	}
}
