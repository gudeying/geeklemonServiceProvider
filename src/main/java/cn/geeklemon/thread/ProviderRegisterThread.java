
package cn.geeklemon.thread;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.zookeeper.server.ZKDatabase;

import java.util.Map.Entry;

import ch.qos.logback.classic.Logger;
import cn.geeklemon.GeeklemonProviderApplication;
import cn.geeklemon.cloud.zookeeper.ZookeeperRegServer;
import cn.geeklemon.registerparam.BaseMessage;
import cn.geeklemon.registerparam.MsgType;
import cn.geeklemon.registerparam.RegisterMessage;
import cn.geeklemon.util.AnnoManageUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

public class ProviderRegisterThread implements Runnable {
	private int discoveryPort = 8899;
	private String discoveryAddress = "127.0.0.1";
	private String serviceName = "geeklemon";
	private int currentServicePort = 8891;
	private String scanPackage = "cn.geeklemon.service";
	private boolean zk = false;
	private String zkString;
//	private String cloudPath;

	@Override
	public void run() {
		System.out.println("register thread start..");
		EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
		try {
			Bootstrap bootstrap = new Bootstrap();
			bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class).handler(new ChannelInitializer<Channel>() {

				@Override
				protected void initChannel(Channel ch) throws Exception {
					ChannelPipeline p = ch.pipeline();
					p.addLast(new ObjectEncoder());
					p.addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(null)));
				}
			});

			Channel channel = bootstrap.connect(discoveryAddress, discoveryPort).sync().channel();

			RegisterMessage message = new RegisterMessage();
			if (zk) {
				String uid = UUID.randomUUID().toString();
				message.setUid(uid);
				message.setNote("zookeeper");
				new ZookeeperRegServer(zkString, 3000, serviceName, uid).register();
			}
			message.setServiceName(serviceName);
			message.setPathArr(getPathString());
			message.setServicePort(currentServicePort);
			channel.writeAndFlush(message);
			channel.close();
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			eventLoopGroup.shutdownGracefully();
			System.out.println("registed !");
		}
	}

	public ProviderRegisterThread() {
	}

	public ProviderRegisterThread(String serviceName, String scanPackage, int servicePort) {
		this.serviceName = serviceName;
		this.currentServicePort = servicePort;
		this.scanPackage = scanPackage;
	}

	public ProviderRegisterThread(boolean zk, String serviceName, String scanPackage, int servicePort,
			String zkString) {
		this.serviceName = serviceName;
		this.currentServicePort = servicePort;
		this.scanPackage = scanPackage;
		this.zk = zk;
		this.zkString=zkString;
	}

	/**
	 * 获取指定包下所有的注解path
	 * 
	 * @return
	 */
	public String[] getPathString() {
		Map<String, List<Object>> map = AnnoManageUtil.getServicePath(scanPackage);
		Set<Entry<String, List<Object>>> sets = map.entrySet();
		String[] strArr = new String[sets.size()];
		Set<String> paramStrs = new HashSet<>();
		for (Entry<String, List<Object>> object : sets) {
			paramStrs.add(object.getKey());
		}
		strArr = paramStrs.toArray(strArr);
		return strArr;
	}

	public int getDiscoveryPort() {
		return discoveryPort;
	}

	public void setDiscoveryPort(int discoveryPort) {
		this.discoveryPort = discoveryPort;
	}

	public String getDiscoveryAddress() {
		return discoveryAddress;
	}

	public void setDiscoveryAddress(String discoveryAddress) {
		this.discoveryAddress = discoveryAddress;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public int getCurrentServicePort() {
		return currentServicePort;
	}

	public void setCurrentServicePort(int currentServicePort) {
		this.currentServicePort = currentServicePort;
	}

}
