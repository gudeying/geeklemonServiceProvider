package cn.geeklemon.entity;

import java.util.LinkedList;

public class ParamEntity {
	private int length;
	private LinkedList<String> paramList = new LinkedList<>();

	public int getLength() {
		return length;
	}

	public void setLength() {
		this.length = paramList.size();
	}
	
	public ParamEntity addParam(String param) {
		paramList.add(param);
		return this;
	}
	public Object[] getParamArr() {
		return paramList.toArray();
	}
	
	public ParamEntity getParamEntity(LinkedList<String> paramList) {
		this.paramList=paramList;
		this.setLength();
		return this;
	}
	
	public LinkedList<String> getParamList() {
		return paramList;
	}


}
