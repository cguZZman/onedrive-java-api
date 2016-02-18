package com.onedrive.api.request;

import java.io.InputStream;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;

import com.onedrive.api.OneDrive;
import com.onedrive.api.resource.Item;

public class ItemContentRequest extends BaseContentRequest {

	public ItemContentRequest(OneDrive oneDrive, String url, LinkedMultiValueMap<String, String> queryParameters) {
		super(oneDrive, url, queryParameters);
	}
	
	public ItemContentRequest conflictBehavior(String value){
		queryParameters.add("@name.conflictBehavior", value);
		return this;
	}
	
	public Item upload(InputStream inputStream){
		Assert.notNull(inputStream, "[inputStream] is required");
		ResponseEntity<Item> response = getOneDrive().getRestTemplate().exchange(buildUri(), HttpMethod.PUT, new HttpEntity<InputStreamResource>(new InputStreamResource(inputStream), getHeaders()), Item.class);
		return response.getBody();
	}
}
