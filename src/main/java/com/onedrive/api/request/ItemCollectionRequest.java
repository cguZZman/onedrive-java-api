package com.onedrive.api.request;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import com.onedrive.api.OneDrive;
import com.onedrive.api.resource.Item;
import com.onedrive.api.resource.facet.File;
import com.onedrive.api.resource.facet.Folder;
import com.onedrive.api.resource.support.AsyncOperationStatus;
import com.onedrive.api.resource.support.ItemCollection;

public class ItemCollectionRequest extends FetchableRequest<ItemCollection, ItemCollectionRequest> {

	public ItemCollectionRequest(OneDrive oneDrive, String url, LinkedMultiValueMap<String, String> queryParameters) {
		super(oneDrive, url, queryParameters);
	}
	
	public Item upload(Item child, InputStream inputStream){
		Assert.notNull(child, "[child] is required");
		Assert.notNull(inputStream, "[inputStream] is required");
		child.setSourceUrl("cid:content");
		
		MultiValueMap<String, Object> parts = new LinkedMultiValueMap<String, Object>();
		parts.add("metadata", child);
		parts.add("content", new InputStreamResource(inputStream));
		
	    getHeaders().set(HttpHeaders.CONTENT_TYPE, "multipart/related");
		return getOneDrive().getRestTemplate().postForObject(buildUri(), new HttpEntity<MultiValueMap<String, Object>>(parts, getHeaders()), Item.class);
	}
	
	public AsyncOperationStatus upload(String itemName, String url){
		Assert.notNull(url, "[url] is required");
		Assert.notNull(itemName, "[itemName] is required");
		Item item = new Item(getOneDrive());
		item.setName(itemName);
		item.setSourceUrl(url);
		item.setFile(new File());
		getHeaders().set("Prefer", "respond-async");
		ResponseEntity<AsyncOperationStatus> response = getOneDrive().getRestTemplate().exchange(buildUri(), HttpMethod.POST, new HttpEntity<Item>(item, getHeaders()), AsyncOperationStatus.class);
		AsyncOperationStatus status = response.getBody();
		if (status == null){
			status = new AsyncOperationStatus(getOneDrive());
		}
		status.setMonitorUrl(response.getHeaders().getLocation().toString());
		return status;
	}
	
	public Item createFolder(String folderName){
		return createFolder(folderName, null);
	}
	
	public Item createFolder(String folderName, String conflictBehavior){
		Assert.notNull(folderName, "[folderName] is required");
		Map<String,Object> data = new HashMap<String, Object>();
		data.put("name", folderName);
		data.put("folder", new Folder());
		if (!StringUtils.isEmpty(conflictBehavior)){
			data.put("@name.conflictBehavior", conflictBehavior);
		}
		return getOneDrive().getRestTemplate().postForObject(buildUri(), data, Item.class);
	}
}
