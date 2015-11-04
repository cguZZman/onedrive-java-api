package com.onedrive.api.resource.support;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Error {
	private String code;
	private String message;
	private Error innererror;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Error getInnererror() {
		return innererror;
	}
	public void setInnererror(Error innererror) {
		this.innererror = innererror;
	}
}
