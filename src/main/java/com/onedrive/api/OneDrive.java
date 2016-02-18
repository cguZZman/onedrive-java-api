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
package com.onedrive.api;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.AccessTokenProviderChain;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeAccessTokenProvider;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.DefaultOAuth2RefreshToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.InjectableValues;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.onedrive.api.internal.InternalDateDeserializer;
import com.onedrive.api.internal.InternalResourceDetails;
import com.onedrive.api.internal.InternalTokenServices;
import com.onedrive.api.internal.MultipartRelatedHttpMessageConverter;
import com.onedrive.api.internal.OneDriveErrorHandler;
import com.onedrive.api.request.DriveRequest;
import com.onedrive.api.request.DriveCollectionRequest;
import com.onedrive.api.support.AccessToken;
import com.onedrive.api.support.AccessTokenListener;
import com.onedrive.api.support.ClientCredential;
import com.onedrive.api.support.Scope;
import com.onedrive.api.support.SerializatorAccessTokenListener;

public class OneDrive {
	public static final String ACCESS_TOKEN_URI = "https://login.live.com/oauth20_token.srf";
	public static final String AUTHORIZAION_URI = "https://login.live.com/oauth20_authorize.srf";
	public static final String MOBILE_REDIRECT_URI = "https://login.live.com/oauth20_desktop.srf";
	public static final String API_URL = "https://api.onedrive.com/v1.0";
	
	@JsonIgnore
	private RestTemplate restTemplate;
	
	private ClientCredential clientCredential;
	private String authorizationCode;
	private List<String> scopes;
	private Map<String, Object> additionalData;
	private String redirectUri;
	private AccessTokenListener accessTokenListener;
	private AccessToken existingToken;
	
	@JsonIgnore
	private ObjectMapper objectMapper;
	
	public OneDrive(ClientCredential clientCredential, List<String> scopes, String redirectUri) {
		Assert.notEmpty(scopes, "At least one scope is required");
		this.clientCredential = clientCredential;
		this.scopes = scopes;
		this.redirectUri = redirectUri;
	}
	
	public OneDrive(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public RestTemplate getRestTemplate() {
		if (restTemplate == null){
			DefaultAccessTokenRequest accessTokenRequest = new DefaultAccessTokenRequest();
			accessTokenRequest.setAuthorizationCode(authorizationCode);
			accessTokenRequest.setPreservedState(new Object());
			accessTokenRequest.setExistingToken(getOAuth2AccessToken());
			restTemplate = new OAuth2RestTemplate(getResourceDetails(), new DefaultOAuth2ClientContext(accessTokenRequest));
			restTemplate.setErrorHandler(new OneDriveErrorHandler(restTemplate.getMessageConverters()));
			AccessTokenProviderChain provider = new AccessTokenProviderChain(Arrays.asList(new AuthorizationCodeAccessTokenProvider()));
			provider.setClientTokenServices(new InternalTokenServices(this));
			((OAuth2RestTemplate) restTemplate).setAccessTokenProvider(provider);
			restTemplate.getMessageConverters().add(new MultipartRelatedHttpMessageConverter());
			for (HttpMessageConverter<?> mc : restTemplate.getMessageConverters()){
				if (mc instanceof MappingJackson2HttpMessageConverter){
					objectMapper = ((MappingJackson2HttpMessageConverter) mc).getObjectMapper();
					objectMapper.setInjectableValues(new InjectableValues.Std().addValue(OneDrive.class, this));
					objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
					SimpleModule module = new SimpleModule();
					module.addDeserializer(Date.class, new InternalDateDeserializer());
					objectMapper.registerModule(module);
				}
			}
		}
		return restTemplate;
	}

	private InternalResourceDetails getResourceDetails() {
		InternalResourceDetails resource = new InternalResourceDetails();
		resource.setClientId(clientCredential.getClientId());
		resource.setClientSecret(clientCredential.getClientSecret());
		resource.setAccessTokenUri(ACCESS_TOKEN_URI);
		resource.setUserAuthorizationUri(AUTHORIZAION_URI);
		resource.setScope(scopes);
		resource.setPreEstablishedRedirectUri(redirectUri);
		return resource;
	}
	
	private OAuth2AccessToken getOAuth2AccessToken(){
		if (existingToken != null){
			DefaultOAuth2AccessToken accessToken = new DefaultOAuth2AccessToken(existingToken.getAccessToken());
			if (existingToken.getRefreshToken() != null){
				accessToken.setRefreshToken(new DefaultOAuth2RefreshToken(existingToken.getRefreshToken()));
			}
			accessToken.setExpiration(existingToken.getExpiration());
			accessToken.setScope(existingToken.getScope());
			accessToken.setTokenType(existingToken.getTokenType());
			return accessToken;
		}
		return null;
	}

	public String getAuthorizationCode() {
		return authorizationCode;
	}

	public void setAuthorizationCode(String authorizationCode) {
		this.authorizationCode = authorizationCode;
	}

	public List<String> getScopes() {
		return scopes;
	}

	public Map<String, Object> getAdditionalData() {
		return additionalData;
	}

	public void setAdditionalData(Map<String, Object> additionalData) {
		this.additionalData = additionalData;
	}

	public String getRedirectUri() {
		return redirectUri;
	}

	public AccessTokenListener getAccessTokenListener() {
		return accessTokenListener;
	}

	public void setAccessTokenListener(AccessTokenListener accessTokenListener) {
		this.accessTokenListener = accessTokenListener;
	}
	public AccessToken getExistingToken() {
		return existingToken;
	}

	public void setExistingToken(AccessToken existingToken) {
		this.existingToken = existingToken;
	}
	
	public ClientCredential getClientCredential() {
		return clientCredential;
	}
	
	public ObjectMapper getObjectMapper() {
		return objectMapper;
	}

	public DriveRequest drive(){
		return new DriveRequest(this, API_URL + "/drive", null);
	}
	public DriveRequest drives(String driveId){
		Assert.hasText(driveId, "[driveId] is required");
		return new DriveRequest(this, API_URL + "/drives/" + driveId, null);
	}
	public DriveCollectionRequest drives(){
		return new DriveCollectionRequest(this, API_URL + "/drives", null);
	}

	public static void main(String[] args) throws ParseException {
		OneDrive oneDrive = new OneDrive(new ClientCredential("0000000048145120"), 
				Arrays.asList(Scope.OFFLINE_ACCESS, "wl.skydrive", "wl.signin", "onedrive.readwrite"), 
				OneDrive.MOBILE_REDIRECT_URI);
		oneDrive.setAuthorizationCode("Mcbeff780-5704-686a-3707-2ac01db5362c");
		oneDrive.setAccessTokenListener(new SerializatorAccessTokenListener());
		//System.out.println(new Item(oneDrive, "C899E30C041941B5!312603").move("/Pictures"));
		//Item item = new Drive(oneDrive).root().createFolder("cgg1");
		
		/*
		try {
			Item child = new Item(oneDrive);
			child.setName("test_multipart_ingon4.jpg");
			child.setDescription("desc 4");
			child.setConflictBehavior("rename");
			System.out.println(new Item(oneDrive, "C899E30C041941B5!80220").upload(child, new FileInputStream("C:/Users/Carlos/Downloads/5137OXGj6oL.jpg")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		*/
		/*
		Item child = new Item(oneDrive);
		child.setName("test_upload_session2.dat");
		child.setConflictBehavior("rename");
		System.out.println("Creating session...");
		UploadSession session = new Item(oneDrive, "C899E30C041941B5!80220").uploadSesion(child);
		System.out.println(session);
		System.out.println("Uploading fragment...");
		session = session.uploadFragment(0, 0, 2, new byte[]{'a'});
		System.out.println(session);
		System.out.println("Getting status...");
		System.out.println(session.status());
		System.out.println("Deleting fragment...");
		System.out.println(session.delete());
		*/
		/*
		AsyncOperationStatus status = new Item(oneDrive, "C899E30C041941B5!36935").uploadFromUrl("http://fc.rdeb.io/d/NSGOT65CFXQJQ/Wicker%20Park.2004.DVDRip.x264.AC3-VLiS.mkv", "Wicker%20Park.2004.DVDRip.x264.AC3-VLiS.mkv");
		System.out.println(status);
		status = status.status();
		System.out.println(status);
		*/
		/*
		AsyncOperationStatus status = new AsyncOperationStatus(oneDrive);
		try {
			status.setSourceUrl("https://api.onedrive.com/v1.0/monitor/3shkDm5Qob2-y9J8EJsBnjeMMymNJnXwFx0bt18kSBdqQ-12j_pvwmvG4LccnsFw9NfW5V3zgvio2qHuEW3j6q9MPvjDX7RV880j6SI2aEXVStmkzyUmtcXQ6CrQIytlWN");
			System.out.println(status.status());
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		/*
		AsyncOperationStatus status = new AsyncOperationStatus(oneDrive);
		try {
			status.setSourceUrl("https://api.onedrive.com/v1.0/monitor/3s36JZtH-Unxc9nNIhF9Yf8h4UlcTdtjq1Dj2pXlc3NrY51uK5MRvMbp-Nbf6ijS-ELhEMP9vUg9WaUdV6OPalNNmurHIZdkHPL472xI1-mtwYCJLQHQpLTH-Z8jnbh72D");
			System.out.println(status.status());
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		/*
		Item item = new Item(oneDrive, "C899E30C041941B5!312603");
		item.setName("foto-prueba.jpg");
		System.out.println(item.update());
		*/
		/*
		Item item = new Item(oneDrive, "C899E30C041941B5!311781");
		Item parent = new Item(oneDrive, "C899E30C041941B5!312345");
		System.out.println(item.copy(parent, "copiado.jpg"));
		*/
		/*
		Item item = new Item(oneDrive, "C899E30C041941B5!311781");
		System.out.println(item.copy("/Pictures", "copied-with-path.jpg"));
		*/
		
		//System.out.println(new Drive(oneDrive).root().search("java").nextPage());
		//System.out.println(new Drive(oneDrive).root().changes());

		//System.out.println(new Item(oneDrive, "C899E30C041941B5!312752").createLink(SharingLink.TYPE_VIEW));
	}
}
