package cn.geeklemon.service;

import cn.geeklemon.annotation.GeeklemonService;
import cn.geeklemon.annotation.ServicePath;

@GeeklemonService
public class UserService {
	@ServicePath("getuser")
	public String getUser(String id) {
		return "user with id="+id;
	}
	
	@ServicePath("getUserByIdAndName")
	public String getUserByIdAndName(String name,String id) {
		return name+" with id = "+id;
	}
}
