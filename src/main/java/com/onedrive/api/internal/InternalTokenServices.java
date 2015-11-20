/*******************************************************************************
 * OneDrive Java API
 * Copyright (C) 2015 - Carlos Guzman
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Created on Aug 1, 2015
 * @author: Carlos Guzman (cguZZman) carlosguzmang@hotmail.com
 *******************************************************************************/
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
