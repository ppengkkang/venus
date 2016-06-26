/*
 * Copyright 2015-2016 Dark Phoenixs (Open-Source Organization).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.venus.kafka.listener;

import com.venus.mq.consumer.Consumer;
import com.venus.mq.exception.MQException;
import com.venus.mq.listener.MessageListener;

public class MessageConsumerListener<T> implements MessageListener<T> {

	/** messageConsumer */
	private Consumer<T> consumer;
	
	/**
	 * @return the consumer
	 */
	public Consumer<T> getConsumer() {
		return consumer;
	}

	/**
	 * @param consumer the consumer to set
	 */
	public void setConsumer(Consumer<T> consumer) {
		this.consumer = consumer;
	}

	@Override
	public void onMessage(T message) throws MQException {

		if (consumer != null)
			
			consumer.receive(message);
		else
			throw new MQException("Consumer is null !");

	}
}
