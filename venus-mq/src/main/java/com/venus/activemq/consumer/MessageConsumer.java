package com.venus.activemq.consumer;

import com.venus.mq.consumer.AbstractConsumer;
import com.venus.mq.exception.MQException;

public class MessageConsumer<T> extends AbstractConsumer<T> {

	@Override
	protected void doReceive(T message) throws MQException {
		
		System.out.println(message);
	}

}
