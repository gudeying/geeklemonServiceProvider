package cn.geeklemon;

import cn.geeklemon.netty.ProviderBootServer;
import cn.geeklemon.thread.HeartbeatThread;
import cn.geeklemon.thread.ProviderExecuteService;
import cn.geeklemon.thread.ProviderRegisterThread;

public class RegisterFactory {
	private ProviderBootServer bootServer;
	private ProviderRegisterThread registerThread;
	private HeartbeatThread heartbeatThread;

	public RegisterFactory(ProviderInitializer provider, RegisterInitializer register) {
		bootServer = new ProviderBootServer(provider.getServiceName(), provider.getServicePackage(),
				provider.getServicePort());
		registerThread = new ProviderRegisterThread(provider.getServiceName(), provider.getServicePackage(),
				provider.getServicePort());
		heartbeatThread = new HeartbeatThread(provider.getServiceName(), provider.getServicePort(),
				register.getHeartBeatAddress(), register.getHeartBeatPort());
		ProviderExecuteService.startServer(bootServer, registerThread, heartbeatThread);

	}

	/**
	 * zk注册方式
	 * 
	 * @param provider
	 * @param register
	 * @param          zk：是否使用zookeeper注册
	 */
	public RegisterFactory(ProviderInitializer provider, RegisterInitializer register, boolean zk) {
		if (zk) {
			bootServer = new ProviderBootServer(provider.getServicePort(),true);
			registerThread = new ProviderRegisterThread(zk, provider.getServiceName(), provider.getServicePackage(),
					provider.getServicePort(), register.getZkString());
			ProviderExecuteService.add(bootServer);
			ProviderExecuteService.add(registerThread);
			ProviderExecuteService.shutDownAllThreadPool();
		}
	}
}
