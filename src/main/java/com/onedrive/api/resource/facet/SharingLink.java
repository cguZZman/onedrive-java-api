package com.onedrive.api.resource.facet;

import com.onedrive.api.resource.support.Identity;

public class SharingLink {
	public static final String TYPE_VIEW = "view";
	public static final String TYPE_EDIT = "edit";
	private String token;
	private String webUrl;
	private String type;
	private Identity application;
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getWebUrl() {
		return webUrl;
	}
	public void setWebUrl(String webUrl) {
		this.webUrl = webUrl;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Identity getApplication() {
		return application;
	}
	public void setApplication(Identity application) {
		this.application = application;
	}
	
}
