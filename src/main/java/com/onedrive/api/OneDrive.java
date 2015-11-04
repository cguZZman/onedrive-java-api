package com.onedrive.api;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
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
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.databind.InjectableValues;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.onedrive.api.internal.InternalDateDeserializer;
import com.onedrive.api.internal.InternalQueryParameterMap;
import com.onedrive.api.internal.InternalResourceDetails;
import com.onedrive.api.internal.InternalTokenServices;
import com.onedrive.api.internal.MultipartRelatedHttpMessageConverter;
import com.onedrive.api.resource.Drive;
import com.onedrive.api.resource.Item;
import com.onedrive.api.resource.support.AsyncOperationStatus;
import com.onedrive.api.resource.support.DriveList;
import com.onedrive.api.resource.support.UploadSession;
import com.onedrive.api.support.AccessToken;
import com.onedrive.api.support.AccessTokenListener;
import com.onedrive.api.support.ClientCredential;
import com.onedrive.api.support.QueryParameter;
import com.onedrive.api.support.Scope;
import com.onedrive.api.support.SerializatorAccessTokenListener;

public class OneDrive {
	public static final String ACCESS_TOKEN_URI = "https://login.live.com/oauth20_token.srf";
	public static final String AUTHORIZAION_URI = "https://login.live.com/oauth20_authorize.srf";
	public static final String MOBILE_REDIRECT_URI = "https://login.live.com/oauth20_desktop.srf";
	public static final String API_URL = "https://api.onedrive.com/v1.0";
	
	public static final String PATH_SEPARATOR = "/";

	private OAuth2RestTemplate restTemplate;
	
	private String id;
	@JsonIgnore 
	private ClientCredential clientCredential;
	@JsonIgnore 
	private String authorizationCode;
	@JsonIgnore 
	private List<String> scopes;
	@JsonIgnore 
	private Map<String, Object> additionalData;
	@JsonIgnore 
	private String redirectUri;
	@JsonIgnore 
	private AccessTokenListener accessTokenListener;
	@JsonIgnore 
	private AccessToken existingToken;
	
	@JsonIgnore 
	private ObjectMapper objectMapper;
	
	public OneDrive(String id, ClientCredential clientCredential, List<String> scopes, String redirectUri) {
		this(clientCredential, scopes, redirectUri);
		this.id = id;
	}
	
	public OneDrive(ClientCredential clientCredential, List<String> scopes, String redirectUri) {
		Assert.notEmpty(scopes, "At least one scope is required");
		this.clientCredential = clientCredential;
		this.scopes = scopes;
		this.redirectUri = redirectUri;
	}

	public OAuth2RestTemplate getRestTemplate() {
		if (restTemplate == null){
			DefaultAccessTokenRequest accessTokenRequest = new DefaultAccessTokenRequest();
			accessTokenRequest.setAuthorizationCode(authorizationCode);
			accessTokenRequest.setExistingToken(getOAuth2AccessToken());
			restTemplate = new OAuth2RestTemplate(getResourceDetails(), new DefaultOAuth2ClientContext(accessTokenRequest));
			AccessTokenProviderChain provider = new AccessTokenProviderChain(Arrays.asList(new AuthorizationCodeAccessTokenProvider()));
			provider.setClientTokenServices(new InternalTokenServices(this));
			restTemplate.setAccessTokenProvider(provider);
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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


	public Drive drive(){
		return drive(null);
	}
	public Drive drive(Map<String,String> queryParameters){
		Drive drive = getRestTemplate().getForObject(getUri(Drive.DEFAULT_DRIVE_PATH, queryParameters), Drive.class);
		return drive;
	}
	
	public List<Drive> drives(){
		return drives(QueryParameter.EMPTY_MAP);
	}
	
	public List<Drive> drives(Map<String,String> queryParameters){
		DriveList drives = getRestTemplate().getForObject(getUri(Drive.DRIVES_PATH, queryParameters), DriveList.class);
		return drives.getValue();
	}
	
	public Drive drives(String driveId){
		return drives(driveId, null);
	}
	
	public Drive drives(String driveId, Map<String,String> queryParameters){
		Assert.hasText(driveId, "[driveId] is required");
		Drive drive = getRestTemplate().getForObject(getUri(Drive.getDrivePath(driveId), queryParameters), Drive.class);
		return drive;
	}
	
	@JsonIgnore
	public URI getUri(String path, Map<String,String> queryParameters){
		UriComponentsBuilder ucb = UriComponentsBuilder.fromUriString(API_URL).path(path);
		if (queryParameters != null){
			ucb.queryParams(new InternalQueryParameterMap(queryParameters));
		}
		return ucb.build().toUri();
	}

	public static void main(String[] args) throws ParseException {
		OneDrive oneDrive = new OneDrive(new ClientCredential("0000000048145120"), 
				Arrays.asList(Scope.OFFLINE_ACCESS), 
				OneDrive.MOBILE_REDIRECT_URI);
		oneDrive.setAuthorizationCode("M8c0ce758-7759-49f8-b63d-4ed5e9ee6faf");
		oneDrive.setAccessTokenListener(new SerializatorAccessTokenListener());
		Map<String,String> params = new HashMap<String, String>();
		params.put(QueryParameter.SELECT, "id,name");
		//System.out.println(oneDrive.drives("me").metadata());
		//System.out.println(new Drive(oneDrive).metadata());
		//System.out.println(new Drive(oneDrive).root());
		//System.out.println(new Drive(oneDrive).specialFolder("documents"));
		//System.out.println(new Drive(oneDrive).root("/personal/correo"));
		//System.out.println(new Drive(oneDrive).items("C899E30C041941B5!197181", params));
		//System.out.println(new Item(oneDrive, "C899E30C041941B5!127").metadata());
		//System.out.println(new Item(oneDrive, "C899E30C041941B5!312603").metadata());
		//System.out.println(new Item(oneDrive, "C899E30C041941B5!312345").metadata());
		//System.out.println(new Item(oneDrive, "C899E30C041941B5!312603").move("/Pictures"));
		//System.out.println(new Drive(oneDrive).root().metadata());
		//System.out.println(new Drive(oneDrive).root().children(params));
		//System.out.println(new Drive(oneDrive).root().children(params));
		//Item item = new Drive(oneDrive).root().createFolder("cgg1");
		//System.out.println(item);
		/*
		try {
			System.out.println(new Item(oneDrive, "C899E30C041941B5!80220").upload("test.jpg", new FileInputStream("C:/Users/Carlos/Downloads/Conman5674dcd92584d815.jpg")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		*/
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
		AsyncOperationStatus status = new Item(oneDrive, "C899E30C041941B5!80220").uploadFromUrl("http://mirror.tcpdiag.net/ubuntu-releases/14.04.3/ubuntu-14.04.3-desktop-amd64.iso", "ubuntu.iso");
		System.out.println(status);
		status = status.status();
		System.out.println(status);
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
		/*
		Item item = new Item(oneDrive, "C899E30C041941B5!8060");
		try {
			System.out.println(item.download(new FileOutputStream("C:/Users/carlos/Downloads/download-onedrive.jpg"), "bytes=0-5"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		*/
		System.out.println(new Drive(oneDrive).root().search("java").nextPage());
	}
}
