package cn.geeklemon.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.geeklemon.netty.ProviderBootServer;
import cn.geeklemon.registerparam.RequestServiceMessage;
import io.netty.channel.Channel;

public class ProviderExecuteService {
	private static final Logger logger = LoggerFactory.getLogger(ProviderExecuteService.class);
	private static ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
	private static ExecutorService servicePool = Executors.newSingleThreadExecutor();
	private static ProviderRegisterThread registThread ;
	private static HeartbeatThread heartBeatClient;
	public static void addServiceTask(RequestServiceMessage requestServiceMessage, Channel channel) {
		logger.info("提交任务：{}",requestServiceMessage.getServicePath());
		cachedThreadPool.submit(new ServiceThread(channel, requestServiceMessage));
	}

	public static void add(Runnable command) {
		cachedThreadPool.submit(command);
	}

	public static void startServer(ProviderBootServer server,ProviderRegisterThread registerThread,HeartbeatThread heartbeatThread) {
		ProviderExecuteService.registThread = registerThread;
		ProviderExecuteService.heartBeatClient = heartbeatThread;
		servicePool.submit(server);
		servicePool.shutdown();//shutDown do not wait for task complete
	}
	
	public static void registeAndHeartBeat() {
		
		cachedThreadPool.submit(registThread);
		cachedThreadPool.submit(heartBeatClient);
	}
	
	public static void registerWithZookeeper() {
		
	}
	
	public static void shutDownAll() {
		heartBeatClient.stopHeartBeat();
		cachedThreadPool.shutdown();
	}
	
	
	public static void shutDownAllThreadPool(){
		cachedThreadPool.shutdown();
		servicePool.shutdown();
	}
}
