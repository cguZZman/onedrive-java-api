package com.onedrive.api.request;

import org.springframework.util.LinkedMultiValueMap;

import com.onedrive.api.OneDrive;
import com.onedrive.api.resource.support.ThumbnailSetCollection;

public class ThumbnailSetCollectionRequest extends FetchableRequest<ThumbnailSetCollection, ThumbnailSetCollectionRequest> {

	public ThumbnailSetCollectionRequest(OneDrive oneDrive, String url, LinkedMultiValueMap<String, String> queryParameters) {
		super(oneDrive, url, queryParameters);
	}
	
}
