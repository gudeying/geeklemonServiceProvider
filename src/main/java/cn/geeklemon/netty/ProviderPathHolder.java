package cn.geeklemon.netty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.geeklemon.util.AnnoManageUtil;

public class ProviderPathHolder {
	/**
	 * 路径名称对应一个list，0是类路径，1是方法，2是方法参数长度
	 * */
	public static Map<String, List<Object>> pathMap = new HashMap<>();
	
	public static void initService(String scanPackage) {
		pathMap = AnnoManageUtil.getServicePath(scanPackage);
	}
	
	public static List<Object> get(String path) {
		return pathMap.get(path);
	}
	
}
