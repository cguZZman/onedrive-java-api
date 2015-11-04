package com.onedrive.api.internal;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.ClientTokenServices;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.DefaultOAuth2RefreshToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

import com.onedrive.api.OneDrive;
import com.onedrive.api.support.AccessToken;

public class InternalTokenServices implements ClientTokenServices {
	private OneDrive reference;
	
	public InternalTokenServices(OneDrive reference) {
		this.reference = reference;
	}

	public OAuth2AccessToken getAccessToken(OAuth2ProtectedResourceDetails resource, Authentication authentication) {
		if (reference.getAccessTokenListener() != null){
			AccessToken internalAccessToken = reference.getAccessTokenListener().onAccessTokenRequired(reference);
			if (internalAccessToken != null){
				DefaultOAuth2AccessToken accessToken = new DefaultOAuth2AccessToken(internalAccessToken.getAccessToken());
				accessToken.setExpiration(internalAccessToken.getExpiration());
				accessToken.setRefreshToken(new DefaultOAuth2RefreshToken(internalAccessToken.getRefreshToken()));
				accessToken.setScope(internalAccessToken.getScope());
				accessToken.setTokenType(internalAccessToken.getTokenType());
				return accessToken;
			}
		}
		return null;
	}

	public void saveAccessToken(OAuth2ProtectedResourceDetails resource, Authentication authentication, OAuth2AccessToken accessToken) {
		if (reference.getAccessTokenListener() != null){
			AccessToken internalAccessToken = new AccessToken();
			internalAccessToken.setAccessToken(accessToken.getValue());
			internalAccessToken.setExpiration(accessToken.getExpiration());
			internalAccessToken.setRefreshToken(accessToken.getRefreshToken().getValue());
			internalAccessToken.setScope(accessToken.getScope());
			internalAccessToken.setTokenType(accessToken.getTokenType());
			reference.getAccessTokenListener().onAccessTokenReceived(reference, internalAccessToken);
		}
	}

	public void removeAccessToken(OAuth2ProtectedResourceDetails resource, Authentication authentication) {
	}
}
