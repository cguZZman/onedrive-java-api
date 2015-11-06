package com.onedrive.api.resource.support;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.onedrive.api.OneDrive;

public class ChangesItemList extends ItemList {
	
	@JsonProperty("@odata.deltaLink")
	private String deltaLink;
	@JsonProperty("@delta.token")
	private String token;
	
	@JsonCreator
	public ChangesItemList(@JacksonInject OneDrive oneDrive) {
		super(oneDrive);
	}
	public String getDeltaLink() {
		return deltaLink;
	}
	public void setDeltaLink(String deltaLink) {
		this.deltaLink = deltaLink;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
}
