package cn.geeklemon.cloud.zookeeper;

import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZookeeperRegServer {
	private String connectString = "127.0.0.1:2181";
	private int sessionTimeout = 2000;
	public static final CountDownLatch COUNT_DOWN_LATCH = new CountDownLatch(1);
	private ZooKeeper zooKeeper;
	private String uid;
	private String rootPath = "/geeklemoncloud/provider";
	String serviceName;
	private static final Logger LOGGER = LoggerFactory.getLogger(ZookeeperRegServer.class);
//	public static void main(String[] args) {
//		try {
//			zooKeeper = new ZooKeeper(connectString, sessionTimout, new CloudZkWatcher()) ;
//			COUNT_DOWN_LATCH.await();
//		} catch (IOException | InterruptedException e) {
//			e.printStackTrace();
//		}
//		
//		try {
//			System.out.println("lalall");
//			zooKeeper.create("/geeklemon", null, Ids.OPEN_ACL_UNSAFE,CreateMode.EPHEMERAL);
//		} catch (KeeperException | InterruptedException e) {
//			e.printStackTrace();
//		}
//		
//	}

	public ZookeeperRegServer(String connectString, int sessionTimeout, String serviceName, String uid) {
		this.connectString = connectString;
		this.sessionTimeout = sessionTimeout;
		this.uid = uid;
		this.serviceName = serviceName;
	}

	public void register() {
		LOGGER.info("尝试使用zookeeper注册：" + uid);
		String serviceZKPath = rootPath+"/"+serviceName;
		try {
			zooKeeper = new ZooKeeper(connectString, sessionTimeout, new CloudZkWatcher());
			COUNT_DOWN_LATCH.await();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (!pathexits(rootPath)) {
			throw new RuntimeException();
		}
		if (!pathexits(serviceZKPath)) {
			try {
				zooKeeper.create(serviceZKPath, "".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			} catch (KeeperException | InterruptedException e) {
				e.printStackTrace();
			}
		}
		try {
			zooKeeper.create(serviceZKPath + "/" + uid, null, Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info("zookeeper节点注册成功");
	}

	public void close() {
		try {
			zooKeeper.close();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public boolean pathexits(String path) {
		try {
			Stat stat = zooKeeper.exists(path, false);
			if (stat != null) {
				return true;
			}
		} catch (KeeperException | InterruptedException e) {
			e.printStackTrace();
		}
		return false;
	}

}
