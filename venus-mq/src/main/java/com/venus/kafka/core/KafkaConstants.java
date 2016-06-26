package com.venus.kafka.core;

public interface KafkaConstants {

	String DEFAULT_ZK_ROOT = "/brokers";
	String BROKER_LIST = "metadata.broker.list";
	String ZOOKEEPER_LIST = "zookeeper.connect";
	String PRODUCER_TYPE = "producer.type";
	String CLIENT_ID = "client.id";
	String SERIALIZER_CLASS = "serializer.class";
	String KEY_SERIALIZER_CLASS = "key.serializer.class";
	String AUTO_COMMIT_ENABLE ="auto.commit.enable";

	int DEFAULT_REFRESH_FRE_SEC = 60;
	int INIT_TIMEOUT_MIN = 2; // 2min
	int INIT_TIMEOUT_MS = 5000; // 5000ms

	int ZOOKEEPER_SESSION_TIMEOUT = 100; // in ms
	int INTERVAL_IN_MS = 100;
	int WAIT_TIME_MS = 2000;

	int BUFFER_SIZE = 64 * 1024;
	int FETCH_SIZE = 100000;
	int SO_TIMEOUT = 100000;
}
