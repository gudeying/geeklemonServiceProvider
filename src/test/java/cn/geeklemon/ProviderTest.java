package cn.geeklemon;

import cn.geeklemon.thread.ProviderRegisterThread;

public class ProviderTest {
	public static void main(String[] args) {
		ProviderInitializer initializer = new ProviderInitializer();
		initializer.setServiceName("testService");
		initializer.setServicePackage("cn.geeklemon.service");
		initializer.setServicePort(9988);
		
		RegisterInitializer registerInitializer  = new RegisterInitializer();
		registerInitializer.setCenterAddress("127.0.0.1");
		registerInitializer.setCenterPort(8899);
		registerInitializer.setHeartBeatAddress("127.0.0.1");
		registerInitializer.setHeartBeatPort(8898);
		
		RegisterFactory factory = new RegisterFactory(initializer, registerInitializer);
		
//		ProviderRegisterThread target = new ProviderRegisterThread();
//		new Thread(target).start();
	}
}
