package cn.geeklemon.thread;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.geeklemon.GeeklemonProviderApplication;
import cn.geeklemon.entity.ResultInfo;
import cn.geeklemon.registerparam.RequestServiceMessage;
import io.netty.channel.Channel;

public class ServiceThread implements Runnable {
	private static final Logger LOG = LoggerFactory.getLogger(ServiceThread.class);
	private volatile Channel channel;
	private volatile RequestServiceMessage message;

	public ServiceThread(Channel channel, RequestServiceMessage requestMessage) {
		this.channel = channel;
		this.message = requestMessage;
	}

	@Override
	public void run() {

		ResultInfo result = ResultInfo.getInstance();
		/* 请求路径 */
		String path = message.getServicePath();
		String param[] = message.getParams().split("-");
		/* 根据路径获取类和方法，因为建议一个程序一个服务，所以服务名不需要使用 */
		List toto = GeeklemonProviderApplication.map.get(path);
		Object[] params = null;

		if (toto.size() != 0) {
			try {
				Class class1 = (Class) toto.get(0);
				Method method = (Method) toto.get(1);
				int paramLength = (int) toto.get(2);
				
				if (paramLength != 0 && paramLength <= param.length) {
					params = new Object[paramLength];
					for (int i = 0; i < paramLength; i++) {
						params[i] = param[i];
					}
				}

				Object resultObj = (String) method.invoke(class1.newInstance(), params);
				LOG.info("{}--传入参数{}请求的结果是：{}", message.getServicePath(), param.toString(), resultObj.toString());
				result.setResult(resultObj);
				result.setStatus(1);
				result.setDateTime(new Date());
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
					| InstantiationException e) {
				e.printStackTrace();
			}
		} else {
			result.setResult(null);
			result.setStatus(0);
			result.setDateTime(new Date());
		}

		channel.writeAndFlush(result);
		channel.close();
	}

}
