package com.venus.mq.codec;

import com.venus.mq.exception.MQException;

import java.util.List;

public interface MessageEncoder<T> {

	/**
	 * <p>Title: encode</p>
	 * <p>Description: 消息序列化</p>
	 *
	 * @param message 消息
	 * @return 消息序列化
	 * @throws MQException
	 */
	byte[] encode(T message) throws MQException;
	
	/**
	 * <p>Title: batchEncode</p>
	 * <p>Description: 批量序列化</p>
	 *
	 * @param message 消息列表
	 * @return 消息序列化列表
	 * @throws MQException
	 */
	List<byte[]> batchEncode(List<T> message) throws MQException;
}
