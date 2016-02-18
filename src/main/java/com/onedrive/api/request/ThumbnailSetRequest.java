package com.onedrive.api.request;

import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;

import com.onedrive.api.OneDrive;
import com.onedrive.api.resource.support.ThumbnailSet;

public class ThumbnailSetRequest extends FetchableRequest<ThumbnailSet, ThumbnailSetRequest> {

	public ThumbnailSetRequest(OneDrive oneDrive, String url, LinkedMultiValueMap<String, String> queryParameters) {
		super(oneDrive, url, queryParameters);
	}
	
	public ThumbnailRequest size(String size){
		Assert.hasText(size, "[size] is required");
		return new ThumbnailRequest(getOneDrive(), getUrl() + "/" + size, null);
	}
	
	public ThumbnailSourceRequest source(){
		return new ThumbnailSourceRequest(getOneDrive(), getUrl() + "/source", null);
	}
}
