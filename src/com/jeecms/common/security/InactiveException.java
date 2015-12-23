package com.jeecms.common.security;

/**
 * 用户未激活异常
 */
@SuppressWarnings("serial")
public class InactiveException extends AccountStatusException {
	public InactiveException() {
	}

	public InactiveException(String msg) {
		super(msg);
	}

	public InactiveException(String msg, Object extraInformation) {
		super(msg, extraInformation);
	}
}
