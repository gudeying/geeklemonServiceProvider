package cn.geeklemon.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;

import cn.geeklemon.annotation.ExecutorBean;
import cn.geeklemon.annotation.GeeklemonService;
import cn.geeklemon.annotation.ServicePath;

public final class AnnoManageUtil {

	/**
	 * 获取指定文件下面的RequestMapping方法保存在mapp中
	 *	<访问路径，执行路径>,执行路径是list，0是类，1是method
	 * @param packageName
	 * @return
	 */
	public static Map<String, List<Object>> getServicePath(String packageName) {
		Reflections reflections = new Reflections(packageName);
		Set<Class<?>> classesList = reflections.getTypesAnnotatedWith(GeeklemonService.class);

		// 存放url和ExecutorBean的对应关系
		Map<String, List<Object>> mmmm = new HashMap<>();
		Map<String, ExecutorBean> mapp = new HashMap<String, ExecutorBean>();
		for (Class classes : classesList) {
			
			
			// 得到该类下面的所有方法
			Method[] methods = classes.getDeclaredMethods();
			ExecutorBean executorBean = null ;
			for (Method method : methods) {
				int paramSize = method.getParameterCount();
				List<Object> list = new ArrayList<>();
				// 得到该类下面的RequestMapping注解
				ServicePath servicePath = method.getAnnotation(ServicePath.class);
				if (null != servicePath) {
					 executorBean = new ExecutorBean();
					try {
						executorBean.setObject(classes.newInstance());
					} catch (InstantiationException | IllegalAccessException e) {
						e.printStackTrace();
					}
					
					
					list.add(classes);
					list.add(method);
					list.add(paramSize);
					mmmm.put(servicePath.value(), list);
					executorBean.setBeanMap(method,servicePath.value());
					executorBean.setObject(classes);
					executorBean.setBeanMap(method, servicePath.value());
				}
			}
			mapp.put(classes.getName(), executorBean);
		
		}
		return mmmm;
	}
}
