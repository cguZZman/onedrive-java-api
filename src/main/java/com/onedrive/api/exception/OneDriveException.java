package com.onedrive.api.exception;

public class OneDriveException extends RuntimeException {
	private static final long serialVersionUID = 5052702544600327914L;

	public OneDriveException() {
	}

	public OneDriveException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public OneDriveException(String message, Throwable cause) {
		super(message, cause);
	}

	public OneDriveException(String message) {
		super(message);
	}

	public OneDriveException(Throwable cause) {
		super(cause);
	}
	
}
