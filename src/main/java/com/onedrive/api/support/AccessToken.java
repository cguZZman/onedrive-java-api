package com.onedrive.api.support;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

public class AccessToken implements Serializable {
	private static final long serialVersionUID = -5433516870009456587L;
	
	private String tokenType;
	private Date expiration;
	private Set<String> scope;
	private String accessToken;
	private String refreshToken;
	
	public String getTokenType() {
		return tokenType;
	}
	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}
	public Date getExpiration() {
		return expiration;
	}
	public void setExpiration(Date expiration) {
		this.expiration = expiration;
	}
	public Set<String> getScope() {
		return scope;
	}
	public void setScope(Set<String> scope) {
		this.scope = scope;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getRefreshToken() {
		return refreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
}
