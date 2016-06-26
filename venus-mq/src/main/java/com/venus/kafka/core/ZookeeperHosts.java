package com.venus.kafka.core;

public class ZookeeperHosts {

	private String topic = null;;
	private String brokerZkStr = null;        
	private String brokerZkPath = null;
	private String DEFAULT_ZK_ROOT = KafkaConstants.DEFAULT_ZK_ROOT;
	private int refreshFreqSecs = KafkaConstants.DEFAULT_REFRESH_FRE_SEC;
    
	public ZookeeperHosts(String brokerZkStr, String topic) {
        this.topic = topic;
        this.brokerZkStr = brokerZkStr;
        this.brokerZkPath = DEFAULT_ZK_ROOT;
    }
	
    public ZookeeperHosts(String brokerZkStr, String brokerZkPath, String topic) {
    	this.topic = topic;
        this.brokerZkStr = brokerZkStr;
        this.brokerZkPath = brokerZkPath;
    }

	public String getBrokerZkStr() {
		return brokerZkStr;
	}

	public void setBrokerZkStr(String brokerZkStr) {
		this.brokerZkStr = brokerZkStr;
	}

	public String getBrokerZkPath() {
		return brokerZkPath;
	}

	public void setBrokerZkPath(String brokerZkPath) {
		this.brokerZkPath = brokerZkPath;
	}

	public int getRefreshFreqSecs() {
		return refreshFreqSecs;
	}

	public void setRefreshFreqSecs(int refreshFreqSecs) {
		this.refreshFreqSecs = refreshFreqSecs;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}
}
