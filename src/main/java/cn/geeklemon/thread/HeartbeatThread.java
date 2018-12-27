package cn.geeklemon.thread;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
import io.netty.handler.timeout.IdleStateHandler;

public class HeartbeatThread implements Runnable {
	private String currentServiceName="geeklemon";
	private int currentServicePort= 8891;
	private String heartBeatServiceIp = "127.0.0.1";
	private int heartBeatPort  = 8898;
	private EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
	
	private static final Logger LOGGER = LoggerFactory.getLogger(HeartbeatThread.class);
	
	public HeartbeatThread(String currentServiceName,int currentServicePort,
						String heartBeatServiceIp,int heartBeatPort) {
		this.currentServiceName=currentServiceName;
		this.currentServicePort=currentServicePort;
		this.heartBeatServiceIp=heartBeatServiceIp;
		this.heartBeatPort = heartBeatPort;
	}
	public HeartbeatThread() {
	}
	
	public HeartbeatThread(String currentServiceName,int currentServicePort) {
		this.currentServiceName=currentServiceName;
		this.currentServicePort=currentServicePort;
	}
	
	@Override
	public void run() {
		System.out.println("hert beat thread start...");
		
		try {
			Bootstrap bootstrap = new Bootstrap();
			bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class).handler(new ChannelInitializer<Channel>() {

				@Override
				protected void initChannel(Channel ch) throws Exception {
					ChannelPipeline p = ch.pipeline();
					p.addLast(new ObjectEncoder());
					p.addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(null)));
					p.addLast(new IdleStateHandler(5, 0, 0, TimeUnit.SECONDS));
				}
			});

			Channel channel = bootstrap.connect(heartBeatServiceIp, heartBeatPort).sync().channel();
			String heartBeatMesssage  = currentServiceName+":"+currentServicePort;
			for (;;) {
				channel.writeAndFlush(heartBeatMesssage);
				LOGGER.info("发送心跳包");
				Thread.sleep(60000);//80s
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			eventLoopGroup.shutdownGracefully();
		}
	}
	
	
	
	public void stopHeartBeat() {
		eventLoopGroup.shutdownGracefully();
	}
	
	public String getCurrentServiceName() {
		return currentServiceName;
	}
	public void setCurrentServiceName(String currentServiceName) {
		this.currentServiceName = currentServiceName;
	}
	public int getCurrentServicePort() {
		return currentServicePort;
	}
	public void setCurrentServicePort(int currentServicePort) {
		this.currentServicePort = currentServicePort;
	}
	public String getHeartBeatServiceIp() {
		return heartBeatServiceIp;
	}
	public void setHeartBeatServiceIp(String heartBeatServiceIp) {
		this.heartBeatServiceIp = heartBeatServiceIp;
	}
	public int getHeartBeatPort() {
		return heartBeatPort;
	}
	public void setHeartBeatPort(int heartBeatPort) {
		this.heartBeatPort = heartBeatPort;
	}

}
