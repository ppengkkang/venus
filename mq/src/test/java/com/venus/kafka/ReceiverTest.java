package com.venus.kafka;

import com.venus.kafka.codec.MessageDecoderImpl;
import kafka.admin.TopicCommand;
import kafka.server.KafkaConfig;
import kafka.server.KafkaServer;
import kafka.utils.*;
import kafka.zk.EmbeddedZookeeper;
import org.I0Itec.zkclient.ZkClient;
import com.venus.kafka.consumer.MessageConsumer;
import com.venus.kafka.core.KafkaDestination;
import com.venus.kafka.core.KafkaMessageAdapter;
import com.venus.kafka.listener.MessageConsumerListener;
import com.venus.kafka.pool.KafkaMessageReceiverPool;
import com.venus.mq.codec.MessageDecoder;
import com.venus.mq.message.MessageBeanImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ReceiverTest {

	private int brokerId = 0;
	private String topic = "QUEUE.TEST";
	private String zkConnect;
	private EmbeddedZookeeper zkServer;
	private ZkClient zkClient;
	private KafkaServer kafkaServer;
	private int port;
	private Properties kafkaProps;

	@Before
	public void before() {
		zkConnect = TestZKUtils.zookeeperConnect();
		zkServer = new EmbeddedZookeeper(zkConnect);
		zkClient = new ZkClient(zkServer.connectString(), 30000, 30000,
				ZKStringSerializer$.MODULE$);

		// setup Broker
		port = TestUtils.choosePort();
		kafkaProps = TestUtils.createBrokerConfig(brokerId, port, true);

		KafkaConfig config = new KafkaConfig(kafkaProps);
		Time mock = new MockTime();
		kafkaServer = TestUtils.createServer(config, mock);

		// create topic
		TopicCommand.TopicCommandOptions options = new TopicCommand.TopicCommandOptions(
				new String[] { "--create", "--topic", topic,
						"--replication-factor", "1", "--partitions", "1" });

		TopicCommand.createTopic(zkClient, options);

		List<KafkaServer> servers = new ArrayList<KafkaServer>();
		servers.add(kafkaServer);
		TestUtils.waitUntilMetadataIsPropagated(
				scala.collection.JavaConversions.asScalaBuffer(servers), topic,
				0, 5000);
	}

	@After
	public void after() {
		kafkaServer.shutdown();
		zkClient.close();
		zkServer.shutdown();
	}

	@Test
	public void test() throws Exception {

		MessageDecoder<MessageBeanImpl> messageDecoder = new MessageDecoderImpl();

		KafkaDestination kafkaDestination = new KafkaDestination(topic);

		MessageConsumer<MessageBeanImpl> MessageConsumer = new MessageConsumer<MessageBeanImpl>();

		MessageConsumerListener<MessageBeanImpl> messageConsumerListener = new MessageConsumerListener<MessageBeanImpl>();

		messageConsumerListener.setConsumer(MessageConsumer);

		KafkaMessageAdapter<MessageBeanImpl> messageAdapter = new KafkaMessageAdapter<MessageBeanImpl>();

		messageAdapter.setDecoder(messageDecoder);

		messageAdapter.setDestination(kafkaDestination);

		messageAdapter.setMessageListener(messageConsumerListener);

		KafkaMessageReceiverPool<byte[], byte[]> pool = new KafkaMessageReceiverPool<byte[], byte[]>();

		pool.setMessageAdapter(messageAdapter);

		pool.setProps(TestUtils.createConsumerProperties(
				zkServer.connectString(), "group_1", "consumer_id", 1000));

		pool.setPoolSize(10);

		pool.init();

		pool.destroy();
	}
}
