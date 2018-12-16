package cn.geeklemon.netty;

import cn.geeklemon.entity.ResultInfo;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ProviderStringHandler extends SimpleChannelInboundHandler<String>{

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
		System.out.println(msg);
		ResultInfo resultInfo =ResultInfo.getInstance();
		resultInfo.setResult("請求結果是這個");
		resultInfo.setStatus(1);
		ctx.channel().writeAndFlush(resultInfo);
	}
	
}
