package cn.geeklemon.thread;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.geeklemon.GeeklemonProviderApplication;
import cn.geeklemon.entity.ResultInfo;
import cn.geeklemon.registerparam.RequestMessage;

public class ServiceWithParams {
	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceWithParams.class);

	public static ResultInfo service(RequestMessage message) {
		ResultInfo result = ResultInfo.getInstance();
		String servicePath = message.getServicePath();
		Object[] params = message.getParamArr();
		List serviceInfo = GeeklemonProviderApplication.map.get(servicePath);
		if (null != serviceInfo && serviceInfo.size() != 0) {
			@SuppressWarnings("rawtypes")
			Class clazz = (Class) serviceInfo.get(0);
			Method method = (Method) serviceInfo.get(1);
			int paramLength = (int) serviceInfo.get(2);
			try {
				if (paramLength != 0 && paramLength == params.length) {

					Object resultObj = method.invoke(clazz.newInstance(), params);
					LOGGER.info("{}--传入参数请求的结果是：{}", message.getServicePath(), resultObj.toString());
					result.setResult(resultObj);
					result.setStatus(1);
					result.setDateTime(new Date());
				} else {
					Object[] noneParam = null;
					Object resultObj = method.invoke(clazz.newInstance(), noneParam);
					result.setResult(resultObj);
					result.setStatus(1);
					result.setDateTime(new Date());
				}
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
					| InstantiationException e) {
				e.printStackTrace();
				result.setResult(null);
				result.setUid(e.getMessage());
				result.setStatus(0);
			}
		}
		return result;
	}

}
