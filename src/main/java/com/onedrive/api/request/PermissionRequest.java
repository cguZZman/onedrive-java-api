package com.onedrive.api.request;

import org.springframework.util.LinkedMultiValueMap;

import com.onedrive.api.OneDrive;
import com.onedrive.api.resource.facet.Permission;

public class PermissionRequest extends FetchableRequest<Permission, PermissionRequest> {

	public PermissionRequest(OneDrive oneDrive, String url, LinkedMultiValueMap<String, String> queryParameters) {
		super(oneDrive, url, queryParameters);
	}
	
}
