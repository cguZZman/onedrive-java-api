package com.onedrive.api.resource.support;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class IdentitySet {
	private Identity user;
	private Identity application;
	private Identity device;
	public Identity getUser() {
		return user;
	}
	public void setUser(Identity user) {
		this.user = user;
	}
	public Identity getApplication() {
		return application;
	}
	public void setApplication(Identity application) {
		this.application = application;
	}
	public Identity getDevice() {
		return device;
	}
	public void setDevice(Identity device) {
		this.device = device;
	}
	@Override
	public String toString() {
		return "IdentitySet [user=" + user + ", application=" + application + ", device=" + device + "]";
	}
}
