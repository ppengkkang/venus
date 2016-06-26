package com.venus.kafka.core;

public class KafkaDestination {

	/** destinationName */
	private String destinationName;
	
	/**
	 * <p>Title: KafkaDestination</p>
	 * <p>Description: KafkaDestination</p>
	 */
	public KafkaDestination() {
	}
	
	/**
	 * <p>Title: KafkaDestination</p>
	 * <p>Description: KafkaDestination</p>
	 *
	 * @param destinationName destinationName
	 */
	public KafkaDestination(String destinationName) {
		this.destinationName = destinationName;
	}

	/**
	 * @return the destinationName
	 */
	public String getDestinationName() {
		return destinationName;
	}

	/**
	 * @param destinationName the destinationName to set
	 */
	public void setDestinationName(String destinationName) {
		this.destinationName = destinationName;
	}

}
