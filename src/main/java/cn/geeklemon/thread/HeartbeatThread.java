package cn.geeklemon.thread;

import java.util.concurrent.TimeUnit;

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

	@Override
	public void run() {
		System.out.println("hert beat thread start...");
		EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
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

			Channel channel = bootstrap.connect("127.0.0.1", 8898).sync().channel();

			for (;;) {
				channel.writeAndFlush("");
				Thread.sleep(60000*3);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			eventLoopGroup.shutdownGracefully();
		}
	}

}
