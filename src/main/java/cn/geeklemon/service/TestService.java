package cn.geeklemon.service;

import cn.geeklemon.annotation.GeeklemonService;
import cn.geeklemon.annotation.ServicePath;
import cn.geeklemon.annotation.ServiceVariable;

@GeeklemonService
public class TestService{
	
	@ServicePath(value="/test2")
	public String getData(@ServiceVariable("id")String param) {
		return param;
	}

	@ServicePath(value="/")
	public String getData() {
		return "无参数方法返回的值";
	}
}
