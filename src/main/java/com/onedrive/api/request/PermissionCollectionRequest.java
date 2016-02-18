package com.onedrive.api.request;

import org.springframework.util.LinkedMultiValueMap;

import com.onedrive.api.OneDrive;
import com.onedrive.api.resource.support.PermissionCollection;

public class PermissionCollectionRequest extends FetchableRequest<PermissionCollection, PermissionCollectionRequest> {

	public PermissionCollectionRequest(OneDrive oneDrive, String url, LinkedMultiValueMap<String, String> queryParameters) {
		super(oneDrive, url, queryParameters);
	}
	
}
