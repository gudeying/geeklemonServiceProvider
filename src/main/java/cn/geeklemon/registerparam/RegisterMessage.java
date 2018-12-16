package cn.geeklemon.registerparam;

public class RegisterMessage extends BaseMessage {
	private String methodPath;
	private String serviceName;
	private String serviceAddress;
	private String note;

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

}
