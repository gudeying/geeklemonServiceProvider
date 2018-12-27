package cn.geeklemon;

public class RegisterInitializer {
	private String centerAddress;
	private int centerPort;
	private String heartBeatAddress = centerAddress;
	private int heartBeatPort;
	/**
	 * 	zookeeper 连接字符串
	 */
	private String zkString;
	private boolean zk;
	/**
	 * 	zookeeper基础节点(serviceName)
	 */
	private String cloudPath;
	
	
	public String getCenterAddress() {
		return centerAddress;
	}

	public void setCenterAddress(String centerAddress) {
		this.centerAddress = centerAddress;
	}

	public int getCenterPort() {
		return centerPort;
	}

	public void setCenterPort(int centerPort) {
		this.centerPort = centerPort;
	}

	public String getHeartBeatAddress() {
		return heartBeatAddress;
	}

	public void setHeartBeatAddress(String heartBeatAddress) {
		this.heartBeatAddress = heartBeatAddress;
	}

	public int getHeartBeatPort() {
		return heartBeatPort;
	}

	public void setHeartBeatPort(int heartBeatPort) {
		this.heartBeatPort = heartBeatPort;
	}

	public String getZkString() {
		return zkString;
	}

	public void setZkString(String zkString) {
		this.zkString = zkString;
	}

	public boolean isZk() {
		return zk;
	}

	public void setZk(boolean zk) {
		this.zk = zk;
	}

	public String getCloudPath() {
		return cloudPath;
	}

	public void setCloudPath(String cloudPath) {
		this.cloudPath = cloudPath;
	}



}
