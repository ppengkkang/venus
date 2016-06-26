package com.venus.mq.codec;

import com.venus.mq.exception.MQException;

import java.util.List;

public interface MessageDecoder<T> {

	/**
	 * <p>Title: decode</p>
	 * <p>Description: 消息反序列化</p>
	 *
	 * @param bytes 消息
	 * @return 反序列化消息
	 * @throws MQException
	 */
	T decode(byte[] bytes) throws MQException;
	
	/**
	 * <p>Title: batchDecode</p>
	 * <p>Description: 批量反序列化</p>
	 *
	 * @param bytes 消息列表
	 * @return 反序列化列表
	 * @throws MQException
	 */
	List<T> batchDecode(List<byte[]> bytes) throws MQException;

}
