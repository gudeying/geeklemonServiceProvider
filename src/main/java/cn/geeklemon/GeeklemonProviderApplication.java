package cn.geeklemon;

import java.util.List;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import cn.geeklemon.thread.ProviderExecuteService;
import cn.geeklemon.util.AnnoManageUtil;

@SpringBootApplication
public class GeeklemonProviderApplication {
	/**
	 * 路径名称对应一个list，0是类路径，1是方法，2是方法参数长度
	 * */
	public static Map<String, List<Object>> map = AnnoManageUtil.getServicePath("cn.geeklemon.service");
	
	public static void main(String[] args) {
		SpringApplication.run(GeeklemonProviderApplication.class, args);
		ProviderInitializer initializer = new ProviderInitializer();
		initializer.setServiceName("geeklemon");
		initializer.setServicePackage("cn.geeklemon.service");
		initializer.setServicePort(8884);
		
		RegisterInitializer registerInitializer  = new RegisterInitializer();
		registerInitializer.setCenterAddress("127.0.0.1");
		registerInitializer.setCenterPort(8899);
		registerInitializer.setHeartBeatAddress("127.0.0.1");
		registerInitializer.setHeartBeatPort(8898);
		
		registerInitializer.setZk(true);
		registerInitializer.setZkString("127.0.0.1:2181");
		
//		new RegisterFactory(initializer, registerInitializer);
		new RegisterFactory(initializer, registerInitializer,true);
	}
}

