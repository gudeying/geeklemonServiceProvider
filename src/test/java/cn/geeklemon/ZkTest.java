package cn.geeklemon;

import static org.hamcrest.CoreMatchers.nullValue;

import java.awt.Label;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;

import cn.geeklemon.cloud.zookeeper.ZookeeperRegServer;

public class ZkTest {
	static CountDownLatch latch = new CountDownLatch(1);
	public static void main(String[] args) {
		try {
			ZooKeeper zooKeeper = new ZooKeeper("127.0.0.1:2181",3000,new Watcher() {
				@Override
				public void process(WatchedEvent event) {
					System.out.println("zookeeper监听连接");
					KeeperState state = event.getState();
					EventType eventType  = event.getType();
					if (KeeperState.SyncConnected==state) {
						if (EventType.None == eventType) {
							latch.countDown();
						}
					}
				}
				
			});
			latch.await();
			//主节点不能是零时节点，因为零时节点不能创建子节点
			zooKeeper.create("/test", null, Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
			zooKeeper.create("/test/provider", null, Ids.OPEN_ACL_UNSAFE,CreateMode.EPHEMERAL);
		} catch (IOException | InterruptedException | KeeperException e) {
			e.printStackTrace();
		}
	}
}
