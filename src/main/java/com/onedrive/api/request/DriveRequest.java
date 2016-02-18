package com.onedrive.api.request;

import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;

import com.onedrive.api.OneDrive;
import com.onedrive.api.resource.Drive;

public class DriveRequest extends FetchableRequest<Drive, DriveRequest> {

	public DriveRequest(OneDrive oneDrive, String url, LinkedMultiValueMap<String, String> queryParameters) {
		super(oneDrive, url, queryParameters);
	}
	
	public ItemRequest root() {
		return new ItemRequest(getOneDrive(), getUrl() + "/root", null);
	}
	
	public ItemRequest special(String folderId) {
		Assert.hasText(folderId, "[folderId] is required");
		return new ItemRequest(getOneDrive(), getUrl() + "/special/"+folderId, null);
	}
	
	public ItemRequest items(String itemId) {
		Assert.hasText(itemId, "[itemId] is required");
		return new ItemRequest(getOneDrive(), getUrl() + "/items/"+itemId, null);
	}
	
	public ItemRequest shared(String folderId) {
		Assert.hasText(folderId, "[folderId] is required");
		return new ItemRequest(getOneDrive(), getUrl() + "/shared/"+folderId, null);
	}
	
	public ItemCollectionRequest allPhotos() {
		return new ItemCollectionRequest(getOneDrive(), getUrl() + "/view.allPhotos", null);
	}
	
	public ItemCollectionRequest recent() {
		return new ItemCollectionRequest(getOneDrive(), getUrl() + "/view.recent", null);
	}
}
