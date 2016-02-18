package com.onedrive.api.request;

import java.io.InputStream;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;

import com.onedrive.api.OneDrive;

public class ThumbnailSourceContentRequest extends BaseContentRequest {

	public ThumbnailSourceContentRequest(OneDrive oneDrive, String url, LinkedMultiValueMap<String, String> queryParameters) {
		super(oneDrive, url, queryParameters);
	}
	
	public String upload(InputStream inputStream){
		Assert.notNull(inputStream, "[inputStream] is required");
		ResponseEntity<Void> response = getOneDrive().getRestTemplate().exchange(buildUri(), HttpMethod.PUT, new HttpEntity<InputStreamResource>(new InputStreamResource(inputStream)), Void.class);
		return response.getHeaders().getFirst(HttpHeaders.CONTENT_LOCATION);
	}
	
}
