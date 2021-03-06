package com.onedrive.api.request;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRange;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import com.onedrive.api.OneDrive;

public class BaseRequest<T extends BaseRequest<?>> {
	
	private OneDrive oneDrive;
	private String url;
	protected LinkedMultiValueMap<String,String> queryParameters = new LinkedMultiValueMap<String,String>();
	private HttpHeaders headers = new HttpHeaders();
	
	public BaseRequest(OneDrive oneDrive, String url, LinkedMultiValueMap<String,String> queryParameters) {
		this.oneDrive = oneDrive;
		this.url = url;
		if (queryParameters != null){
			this.queryParameters.putAll(queryParameters);
		}
	}
	
	public OneDrive getOneDrive() {
		return oneDrive;
	}
	public String getUrl() {
		return url;
	}
	public LinkedMultiValueMap<String, String> getQueryParameters() {
		return queryParameters;
	}
	
	public HttpHeaders getHeaders() {
		return headers;
	}
	
	protected URI buildUri(){
		return buildUri("");
	}

	protected URI buildUri(String childResource){
		UriComponentsBuilder ucb = UriComponentsBuilder.fromUriString(url+childResource);
		ucb.queryParams(queryParameters);
		try {
			URI uri = new URI(ucb.build().encode().toString().replace("+", encodeChar('+')));
			System.out.println(uri);
			return uri;
		} catch (URISyntaxException ex) {
			throw new IllegalStateException("Could not create URI object: " + ex.getMessage(), ex);
		}
	}
	
	private String encodeChar(char c) {
		StringBuffer sb = new StringBuffer("%");
		byte b = (byte) c;
		char hex1 = Character.toUpperCase(Character.forDigit((b >> 4) & 0xF, 16));
		char hex2 = Character.toUpperCase(Character.forDigit(b & 0xF, 16));
		sb.append(hex1);
		sb.append(hex2);
		return sb.toString();
	}
	
	@SuppressWarnings("unchecked")
	public T header(String name, String value){
		headers.add(name, value);
		return (T) this;
	}
	@SuppressWarnings("unchecked")
	public T ifNoneMatch(String etag){
		headers.setIfNoneMatch(etag);
		return (T) this;
	}
	@SuppressWarnings("unchecked")
	public T range(long firstBytePos, long lastBytePos){
		List<HttpRange> list = new ArrayList<HttpRange>();
		list.add(HttpRange.createByteRange(firstBytePos, lastBytePos));
		headers.setRange(list);
		return (T) this;
	}
}
