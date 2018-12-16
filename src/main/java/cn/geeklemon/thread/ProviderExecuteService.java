package cn.geeklemon.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.geeklemon.netty.ProviderBootServer;
import cn.geeklemon.registerparam.RequestServiceMessage;
import io.netty.channel.Channel;

@Component
public class ProviderExecuteService {
	static final Logger logger = LoggerFactory.getLogger(ProviderExecuteService.class);
	static ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
	static ExecutorService servicePool = Executors.newSingleThreadExecutor();

	public static void addServiceTask(RequestServiceMessage requestServiceMessage, Channel channel) {
		logger.info("提交任务：{}",requestServiceMessage.getServicePath());
		cachedThreadPool.submit(new ServiceThread(channel, requestServiceMessage));
	}

	public static void add(Runnable command) {
		cachedThreadPool.submit(command);
	}

	public static void startServer() {
		servicePool.submit(new ProviderBootServer());
	}
}
