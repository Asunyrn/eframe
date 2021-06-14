package com.framework.base.exception;

import com.framework.base.exception.ErrCode;

@SuppressWarnings("serial")
public class BusinessException extends Exception{
	private String errMsg;
	private int errCode;
	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	public int getErrCode() {
		return errCode;
	}
	public void setErrCode(int errCode) {
		this.errCode = errCode;
	}
	public BusinessException() {

	}

	public BusinessException(String errMsg) {
		this.errMsg = errMsg;
		this.errCode = ErrCode.USER_OP_ERROR;
	}

	public BusinessException(String errMsg, int errCode) {
		this.errMsg = errMsg;
		this.errCode = errCode;
	}

}
