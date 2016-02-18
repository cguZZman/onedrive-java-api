package com.onedrive.api.request;

import org.springframework.util.LinkedMultiValueMap;

import com.onedrive.api.OneDrive;
import com.onedrive.api.resource.support.Thumbnail;

public class ThumbnailSourceRequest extends FetchableRequest<Thumbnail, ThumbnailSourceRequest> {

	public ThumbnailSourceRequest(OneDrive oneDrive, String url, LinkedMultiValueMap<String, String> queryParameters) {
		super(oneDrive, url, queryParameters);
	}
	
	public ThumbnailSourceContentRequest content() {
        return new ThumbnailSourceContentRequest(getOneDrive(), getUrl() + "/content", null);
    }
	
	public void delete(){
		getOneDrive().getRestTemplate().delete(buildUri());
	}
}
