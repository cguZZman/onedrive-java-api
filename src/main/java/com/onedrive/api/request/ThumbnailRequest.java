package com.onedrive.api.request;

import org.springframework.util.LinkedMultiValueMap;

import com.onedrive.api.OneDrive;
import com.onedrive.api.resource.support.Thumbnail;

public class ThumbnailRequest extends FetchableRequest<Thumbnail, ThumbnailRequest> {

	public ThumbnailRequest(OneDrive oneDrive, String url, LinkedMultiValueMap<String, String> queryParameters) {
		super(oneDrive, url, queryParameters);
	}
	
	public BaseContentRequest content() {
        return new BaseContentRequest(getOneDrive(), getUrl() + "/content", null);
    }
}
