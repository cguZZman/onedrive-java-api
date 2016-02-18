package com.onedrive.api.request;

import org.springframework.util.LinkedMultiValueMap;

import com.onedrive.api.OneDrive;
import com.onedrive.api.resource.support.DriveCollection;

public class DriveCollectionRequest extends FetchableRequest<DriveCollection, DriveCollectionRequest> {
	
	public DriveCollectionRequest(OneDrive oneDrive, String url, LinkedMultiValueMap<String, String> queryParameters) {
		super(oneDrive, url, queryParameters);
	}
	
	public DriveRequest me(){
		return new DriveRequest(getOneDrive(), getUrl() + "/me", null);
	}

}
