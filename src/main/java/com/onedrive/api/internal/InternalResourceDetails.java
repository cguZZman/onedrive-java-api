package com.onedrive.api.internal;

import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;

public class InternalResourceDetails extends AuthorizationCodeResourceDetails {

	public InternalResourceDetails() {
		super();
	}

	@Override
	public boolean isClientOnly() {
		return true;
	}
}
