package cn.geeklemon.netty;

import cn.geeklemon.thread.HeartbeatThread;
import cn.geeklemon.thread.ProviderExecuteService;
import cn.geeklemon.thread.ProviderRegisterThread;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

public class ProviderBootServer implements Runnable {
	private int servicePort = 8891;
	private String serviceName = "geeklemon";
	private String scanPackage = "cn.geeklemon.service";
	private boolean useZk = false;

	@Override
	public void run() {
		EventLoopGroup workGroup = new NioEventLoopGroup();
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap serverBootstrap = new ServerBootstrap();
			serverBootstrap.group(bossGroup, workGroup).channel(NioServerSocketChannel.class)
					.childHandler(new ChannelInitializer<Channel>() {

						@Override
						protected void initChannel(Channel ch) throws Exception {
							ChannelPipeline pipeline = ch.pipeline();
							pipeline.addLast(new ObjectEncoder());
							pipeline.addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(null)));
							pipeline.addLast(new ProviderHandler());
							pipeline.addLast(new ProviderStringHandler());
						}
					});

			ChannelFuture future = serverBootstrap.bind(servicePort).sync();
//			Thread register = new Thread(new ProviderRegisterThread(serviceName,scanPackage,servicePort));
//			Thread heartBeat = new Thread(new HeartbeatThread(serviceName,servicePort));
//			
//			heartBeat.setDaemon(true);
//			register.start();
//			heartBeat.start();

			ProviderPathHolder.initService(scanPackage);
			if (!useZk) {
				ProviderExecuteService.registeAndHeartBeat();// 非zookeeper模式需要注册心跳和服务
			}
			future.channel().closeFuture().sync();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			workGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
			ProviderExecuteService.shutDownAll();
		}
	}

	public ProviderBootServer() {
	}

	public ProviderBootServer(String serviceName, String scanPackage, int port) {
		this.serviceName = serviceName;
		this.servicePort = port;
		this.scanPackage = scanPackage;

	}

	public ProviderBootServer(String serviceName, String scanPackage, int port, boolean useZK) {
		this.serviceName = serviceName;
		this.servicePort = port;
		this.scanPackage = scanPackage;
		this.useZk =useZK;

	}

	public ProviderBootServer(int port) {
		this.servicePort = port;
	}
	
	public ProviderBootServer(int port,boolean useZK) {
		this.servicePort = port;
		this.useZk = useZK;
	}

}
