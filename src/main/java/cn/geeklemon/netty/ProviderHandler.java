package cn.geeklemon.netty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.geeklemon.entity.ResultInfo;
import cn.geeklemon.registerparam.BaseMessage;
import cn.geeklemon.registerparam.RequestMessage;
import cn.geeklemon.registerparam.RequestServiceMessage;

import cn.geeklemon.thread.ProviderExecuteService;
import cn.geeklemon.thread.ServiceThread;
import cn.geeklemon.thread.ServiceWithParams;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ProviderHandler extends SimpleChannelInboundHandler<BaseMessage>{
	private static final Logger LOGGER = LoggerFactory.getLogger(ProviderHandler.class);

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, BaseMessage msg) throws Exception {
		if (msg instanceof RequestServiceMessage) {
			/*提供服务*/
			LOGGER.info("請求信息");
			RequestServiceMessage message = (RequestServiceMessage)msg;
			ProviderExecuteService.addServiceTask(message, ctx.channel());
		}else if (msg instanceof RequestMessage) {
			LOGGER.info("带参数请求");
			RequestMessage message = (RequestMessage)msg;
			ResultInfo resultInfo = ServiceWithParams.service(message);
			resultInfo.setUid(message.getUid());
			ctx.channel().writeAndFlush(resultInfo);
		}
	}
	
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		super.exceptionCaught(ctx, cause);
		String address = ctx.channel().remoteAddress().toString();
		LOGGER.error("{}发生错误：{}",address,cause.getMessage());
		ctx.channel().close();
	}

}
