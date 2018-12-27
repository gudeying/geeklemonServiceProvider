package cn.geeklemon.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import cn.geeklemon.GeeklemonProviderApplication;
import cn.geeklemon.annotation.ExecutorBean;
import cn.geeklemon.thread.ProviderRegisterThread;
import cn.geeklemon.util.AnnoManageUtil;

public class Test {
	public static void main(String[] args) {
//		TestService service = new TestService();
//		String[] argsss = { "arg1", "arg2" };
//		try {
//			Method method = service.getClass().getMethod("getData", String.class);
//			String result = (String) method.invoke(service, argsss[0]);
//			System.out.println(result);
//		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
//				| InvocationTargetException e) {
//			e.printStackTrace();
//		}

		Map<String, List<Object>> map = AnnoManageUtil.getServicePath("cn.geeklemon.service");
		map = GeeklemonProviderApplication.map;
		Set<Entry<String, List<Object>>> set = map.entrySet();
		for (Entry<String, List<Object>> entry : set) {
			System.out.println(entry.getKey());
			List<Object> list = entry.getValue();
			System.out.println("class:" + (Class) list.get(0));
			System.out.println("method:" + (Method) list.get(1));
		}
//		
//		String[] testSplit = {"p1","p2","p3"};
//		System.out.println(formatParam(testSplit));
//		System.out.println(formatParam(testSplit).split("-").length);
		
		System.out.println("随机数");
		System.out.println((int)(1+Math.random()*(2)));
		
		String[] addresses = new ProviderRegisterThread().getPathString();
		for (String string : addresses) {
			System.out.println(string);
		}
		
		System.out.println("///////");
		List<Object> lObjects = map.get("/test2");
//		List<Object> lObjects = map.get("/");
		System.out.println("/test2  路径的类、方法和参数长度分别是：");
		System.out.println(lObjects.get(0));
		System.out.println(lObjects.get(1));
		System.out.println(lObjects.get(2));
		Object service;
		try {
			Class serviceclass = (Class) lObjects.get(0);
			Method method = (Method) lObjects.get(1);
			String data = "";
			//反射方法如果没有参数，可以不传也可以传null
			String string = (String)method.invoke(serviceclass.newInstance(), new Object[] {"param"});
			System.out.println(string);
			data = (String) method.invoke(serviceclass.newInstance(), "lalalala");
			System.out.println("反射取得的数据：" + data);
		} catch (IllegalArgumentException |  IllegalAccessException
				| InvocationTargetException | InstantiationException e) {
			e.printStackTrace();
		}
	}
	
	private static String formatParam(String [] params) {
		/*最后多加一个 - ，但是拆分之后长度是正确的不会多*/
		StringBuilder builder = new StringBuilder();
		for (String string : params) {
			builder.append(string).append("-");
		}
		return builder.toString();
	}
}
