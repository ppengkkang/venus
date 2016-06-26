package com.venus.kafka.producer;

import com.venus.mq.exception.MQException;

public class MessageProducer<T> extends AbstractProducer<T> {

	@Override
	protected T doSend(T message) throws MQException {

		return message;
	}
}
