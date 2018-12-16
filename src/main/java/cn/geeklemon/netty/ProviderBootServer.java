package cn.geeklemon.netty;

import cn.geeklemon.thread.HeartbeatThread;
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


			ChannelFuture future = serverBootstrap.bind(8891).sync();
			Thread register = new Thread(new ProviderRegisterThread());
			Thread heartBeat = new Thread(new HeartbeatThread());
			
			register.start();
			heartBeat.start();
			
			future.channel().closeFuture().sync();


		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			workGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
	}

}
