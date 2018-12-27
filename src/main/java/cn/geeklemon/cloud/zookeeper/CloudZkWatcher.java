package cn.geeklemon.cloud.zookeeper;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CloudZkWatcher implements Watcher{

	@Override
	public void process(WatchedEvent event) {
		System.out.println("zookeeper监听连接");
		KeeperState state = event.getState();
		EventType eventType  = event.getType();
		if (KeeperState.SyncConnected==state) {
			if (EventType.None == eventType) {
				ZookeeperRegServer.COUNT_DOWN_LATCH.countDown();
			}
		}
	}

}
