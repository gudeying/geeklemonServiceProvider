package cn.geeklemon.entity;

public class ParamEntity {
	private int length;
	private Object[] params;

	public int getLength() {
		return length;
	}

	public void setLength() {
		this.length = params.length;
	}

	public Object[] getParams() {
		return params;
	}

	public void setParams(Object[] params) {
		this.params = params;
		this.length = params.length;
	}

}
