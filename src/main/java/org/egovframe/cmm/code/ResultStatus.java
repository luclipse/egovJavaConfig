package org.egovframe.cmm.code;

public enum ResultStatus {

	SUCCESS( 200, "S200", "Success" ),
	AUTH_ERROR(401, "E401","Access is Denied"),
	INPUT_CHECK_ERROR(900, "E900", "Invalid Input Value"),
	FAILED( 500, "E500-0000", "Failed" ),
	HTTP_ERROR( 400, "E400-0000", "HTTP ERROR" ),
	EMPTY(-1,"", "");

	private final int status;
	private final String code;
	private final String message;

	ResultStatus(final int status, final String code, final String message) {
		this.status = status;
		this.code = code;
		this.message = message;
	}

	public int status() {
		return status;
	}
	public String code() {
		return code;
	}
	public String getCode() {
		return code();
	}
	public String message() {
		return message;
	}
	public String getMessage() {
		return message();
	}
}
