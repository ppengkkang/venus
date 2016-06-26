package com.venus.kafka.pool;

import kafka.admin.TopicCommand;
import kafka.server.KafkaConfig;
import kafka.server.KafkaServer;
import kafka.utils.*;
import kafka.zk.EmbeddedZookeeper;
import org.I0Itec.zkclient.ZkClient;
import com.venus.kafka.core.KafkaMessageSender;
import com.venus.kafka.core.ZookeeperHosts;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.DefaultResourceLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class KafkaMessageSenderPoolTest {

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
	public void test_0() throws Exception {

		KafkaMessageSenderPool<byte[], byte[]> pool = new KafkaMessageSenderPool<byte[], byte[]>();

		Assert.assertNotNull(pool.getThreadFactory());
		pool.setThreadFactory(new KafkaPoolThreadFactory());

		Assert.assertNotNull(pool.getProps());
		pool.setProps(new Properties());

		Assert.assertEquals(0, pool.getPoolSize());
		pool.setPoolSize(10);

		Assert.assertNull(pool.getClientId());
		pool.setClientId("test");

		Assert.assertNull(pool.getBrokerStr());
		pool.setBrokerStr("");

		Assert.assertNull(pool.getConfig());
		pool.setConfig(new DefaultResourceLoader()
				.getResource("kafka/producer1.properties"));
		pool.setConfig(new DefaultResourceLoader()
				.getResource("kafka/producer.properties"));

		pool.setProps(TestUtils.getProducerConfig("localhost:" + port));

		pool.setZkhosts(new ZookeeperHosts(zkServer.connectString(), topic));

		Assert.assertNotNull(pool.getSender(-1));

		Assert.assertNotNull(pool.getSender(10000));

		pool.init();

		KafkaMessageSender<byte[], byte[]> sender = pool.getSender(0);

		pool.returnSender(sender);
		pool.returnSender(sender);

		pool.destroy();
	}

	@Test
	public void test_1() throws Exception {

		final KafkaMessageSenderPool<byte[], byte[]> pool = new KafkaMessageSenderPool<byte[], byte[]>();

		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				pool.init();
			}
		});

		thread.start();
		
		pool.destroy();
	}

}
