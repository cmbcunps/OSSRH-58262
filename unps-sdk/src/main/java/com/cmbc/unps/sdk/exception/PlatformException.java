package com.cmbc.unps.sdk.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlatformException extends Exception {
	private static final long serialVersionUID = 1L;
	private Logger logger = LoggerFactory.getLogger(getClass());

	protected String errorCode;

	protected String errorMsg;

	public PlatformException(ErrorCodeEnum errorCode, String errorMsg) {
		super(errorMsg);
		this.errorCode = errorCode.getErrorCode();
		if (ErrorCodeEnum.UNPS_CORE_008.equals(errorCode)) {
			this.errorMsg = errorMsg + errorCode.getErrorMsg();
		} else if (ErrorCodeEnum.UNPS_CORE_021.equals(errorCode)) {
			this.errorMsg = errorMsg;
		} else {
			this.errorMsg = errorCode.getErrorMsg();
		}
		logger.error("errorCode={},errorMsg={}", this.errorCode, this.errorMsg);
	}

	public PlatformException(ErrorCodeEnum errorCode) {
		// super(errorMsg);
		this.errorCode = errorCode.getErrorCode();
		this.errorMsg = errorCode.getErrorMsg();
		logger.error("errorCode={},errorMsg={}", this.errorCode, this.errorMsg);
	}

	public PlatformException(ErrorCodeEnum errorCode, Exception e) {
		// super(errorMsg);
		logger.error("{}", e);
		this.errorCode = errorCode.getErrorCode();
		this.errorMsg = errorCode.getErrorMsg();
		logger.error("errorCode={},errorMsg={}", this.errorCode, this.errorMsg);
	}

	public String getErrorCode() {
		return errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	@Override
	public String toString() {
		return "PlatformException [errorCode=" + errorCode + ", errorMsg=" + errorMsg + "]";
	}

}
