
package cn.geeklemon.thread;

import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import ch.qos.logback.classic.Logger;
import cn.geeklemon.GeeklemonProviderApplication;
import cn.geeklemon.registerparam.BaseMessage;
import cn.geeklemon.registerparam.MsgType;
import cn.geeklemon.registerparam.RegisterMessage;
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
	private int discoveryPort=8899;
	private String discoveryAddress = "127.0.0.1";
	private String serviceName = "geeklemon";

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
			message.setServiceName(serviceName);
			message.setMethodPath(getPathString());

			channel.writeAndFlush(message);

		} catch (Exception e) {
		} finally {
			eventLoopGroup.shutdownGracefully();
		}
	}

	public String getPathString() {
		Map map = GeeklemonProviderApplication.map;
		StringBuilder builder = new StringBuilder();
		Set<Map.Entry<String, Object>> sets = map.entrySet();
		for (Entry<String, Object> object : sets) {
			builder.append(object.getKey()).append("-");
		}
		System.out.println(builder.toString());

		return builder.toString();
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

}
