package cn.geeklemon.annotation;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ExecutorBean {
	private Object object;

	private Map<String, Method> methadMap = new HashMap<>();

	public void setBeanMap(Method method, String param) {
		methadMap.put(param, method);
	}

	public Object getObject() {
		return object;
	}
	
	public Map<String, Method> getMethodMap() {
		return methadMap;
	}
	public void setObject(Object object) {
		this.object = object;
	}

}
