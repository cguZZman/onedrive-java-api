package com.onedrive.api.support;

public class ClientCredential {
	private String clientId;
	private String clientSecret;
	
	public ClientCredential(String clientId) {
		this.clientId = clientId;
	}
	
	public ClientCredential(String clientId, String clientSecret) {
		this.clientId = clientId;
		this.clientSecret = clientSecret;
	}

	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getClientSecret() {
		return clientSecret;
	}
	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}
}
