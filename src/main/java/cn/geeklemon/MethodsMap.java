package cn.geeklemon;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MethodsMap {
	private static Map<String, String[]> map = new ConcurrentHashMap<>();

	public static void add(String servicePath, String[] classAndMethod) {
		map.put(servicePath, classAndMethod);
	}

	public static void remove(String servicePath) {
		map.remove(servicePath);
	}

	private static Map<String, String[]> getMethodsMap() {
		return map;
	}
}
