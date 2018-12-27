package cn.geeklemon.registerparam;

public class RegisterMessage extends BaseMessage {
	private String methodPath;
	private String serviceName;
	private String serviceAddress;
	private int servicePort;
	private String note;
	private String[] pathArr;

	public RegisterMessage() {
		super();
		setMsgType(MsgType.REGISTER);
	}
	public String getMethodPath() {
		return methodPath;
	}

	public void setMethodPath(String methodPath) {
		this.methodPath = methodPath;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getServiceAddress() {
		return serviceAddress;
	}

	public void setServiceAddress(String serviceAddress) {
		this.serviceAddress = serviceAddress;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	public int getServicePort() {
		return servicePort;
	}
	public void setServicePort(int servicePort) {
		this.servicePort = servicePort;
	}

	public String[] getPathArr() {
		return pathArr;
	}
	public void setPathArr(String[] pathArr) {
		this.pathArr = pathArr;
	}

}
