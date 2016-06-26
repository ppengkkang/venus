package com.venus.mq.exception;

public class MQException extends Exception {

	private static final long serialVersionUID = 3423882579546941370L;

	public MQException() {
		super();
	}

	public MQException(String message) {
		super(message);
	}

	public MQException(Throwable cause) {
		super(cause);
	}

	public MQException(String message, Throwable cause) {
		super(message, cause);
	}
}
