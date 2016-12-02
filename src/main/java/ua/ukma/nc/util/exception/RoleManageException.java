package ua.ukma.nc.util.exception;

public class RoleManageException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String errMsg;
	private Long userId;

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public RoleManageException(Long userId, String errMsg) {
		this.userId = userId;
		this.errMsg = errMsg;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
