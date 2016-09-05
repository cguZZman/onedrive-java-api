package com.onedrive.api.request;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.StringUtils;

import com.onedrive.api.OneDrive;
import com.onedrive.api.resource.Item;
import com.onedrive.api.resource.facet.Folder;
import com.onedrive.api.resource.facet.Permission;
import com.onedrive.api.resource.support.AsyncOperationStatus;
import com.onedrive.api.resource.support.ChangesItemCollection;
import com.onedrive.api.resource.support.ItemReference;
import com.onedrive.api.resource.support.SearchItemCollection;

public class ItemRequest extends FetchableRequest<Item, ItemRequest> {

	public static final String PARENT_REFERENCE_PATH_PREFIX = "/drive/root:";
	public ItemRequest(OneDrive oneDrive, String url, LinkedMultiValueMap<String, String> queryParameters) {
		super(oneDrive, url, queryParameters);
	}
	
	public ItemRequest itemByPath(String itemPath) {
		Assert.notNull(itemPath, "[itemPath] is required");
		String url = getUrl(); 
		if (url.endsWith(":")){
			url = url.substring(0, url.length()-1);
		} else {
			url += ":";
		}
		if (itemPath.charAt(0) == '/') {
			itemPath = itemPath.substring(1);
		}
        return new ItemRequest(getOneDrive(), url + "/" + itemPath + ":", null);
    }
	
	public ItemCollectionRequest children() {
        return new ItemCollectionRequest(getOneDrive(), getUrl() + "/children", null);
    }
	
	public ItemRequest children(String itemName) {
		Assert.hasText(itemName, "[itemName] is required");
        return new ItemRequest(getOneDrive(), getUrl() + "/children/" + itemName, null);
    }
	
	public PermissionCollectionRequest permissions(){
		return new PermissionCollectionRequest(getOneDrive(), getUrl() + "/permissions", null);
	}
	public PermissionRequest permissions(String permissionId){
		Assert.hasText(permissionId, "[permissionId] is required");
		return new PermissionRequest(getOneDrive(), getUrl() + "/permissions/" + permissionId, null);
	}
	
	public ThumbnailSetCollectionRequest thumbnails(){
		return new ThumbnailSetCollectionRequest(getOneDrive(), getUrl() + "/thumbnails", null);
	}
	public ThumbnailSetRequest thumbnails(String thumbnailId){
		Assert.hasText(thumbnailId, "[thumbnailId] is required");
		return new ThumbnailSetRequest(getOneDrive(), getUrl() + "/thumbnails/" + thumbnailId, null);
	}
	public ItemContentRequest content() {
        return new ItemContentRequest(getOneDrive(), getUrl() + "/content", null);
    }
	public UploadSessionRequest uploadCreateSession() {
        return new UploadSessionRequest(getOneDrive(), getUrl() + "/upload.createSession", null);
    }
	public Item createAsFolder(){
		return createAsFolder(null);
	}
	public Item createAsFolder(String conflictBehavior){
		Map<String,Object> data = new HashMap<String, Object>();
		data.put("folder", new Folder());
		if (!StringUtils.isEmpty(conflictBehavior)){
			data.put("@name.conflictBehavior", conflictBehavior);
		}
		ResponseEntity<Item> response = getOneDrive().getRestTemplate().exchange(buildUri(), HttpMethod.PUT, new HttpEntity<Map<String,Object>>(data, getHeaders()), Item.class);
		return response.getBody();
	}
	public void delete(){
		getOneDrive().getRestTemplate().exchange(buildUri(), HttpMethod.DELETE, new HttpEntity<>(getHeaders()), Void.class);
	}
	public Item move(String destinationFolder){
		return move(destinationFolder, null);
	}
	public Item move(String destinationFolder, String newName){
		Assert.notNull(destinationFolder, "[destinationFolder] is required");
		Item request = new Item(getOneDrive());
		request.setName(newName);
		request.setParentReference(new ItemReference());
		request.getParentReference().setPath(PARENT_REFERENCE_PATH_PREFIX + destinationFolder);
		
		return update(request); 
	}
	public Item rename(String newName){
		Assert.notNull(newName, "[newName] is required");
		Item request = new Item(getOneDrive());
		request.setName(newName);
		return update(request); 
	}
	public Item update(Item item){
		getHeaders().add("X-HTTP-Method-Override", HttpMethod.PATCH.name());
		ResponseEntity<Item> response = getOneDrive().getRestTemplate().exchange(buildUri(), HttpMethod.POST, new HttpEntity<Item>(item, getHeaders()), Item.class);
		return response.getBody(); 
	}
	
	public AsyncOperationStatus copy(ItemReference parentReference, String newName){
		Item request = new Item(getOneDrive());
		request.setName(newName);
		request.setParentReference(parentReference);
		if (!StringUtils.isEmpty(parentReference.getPath()) && !parentReference.getPath().startsWith(PARENT_REFERENCE_PATH_PREFIX)){
			if (parentReference.getPath().equals("/")){
				parentReference.setPath("");
			}
			parentReference.setPath(PARENT_REFERENCE_PATH_PREFIX + parentReference.getPath());
		}
		getHeaders().add("Prefer", "respond-async");
		ResponseEntity<AsyncOperationStatus> response = getOneDrive().getRestTemplate().exchange(buildUri("/action.copy"), HttpMethod.POST, new HttpEntity<Item>(request, getHeaders()), AsyncOperationStatus.class);
		AsyncOperationStatus status = response.getBody();
		if (status == null){
			status = new AsyncOperationStatus(getOneDrive());
		}
		status.setMonitorUrl(response.getHeaders().getLocation().toString());
		return status;
	}
	public SearchItemCollection search(String query){
		getQueryParameters().add("q", query);
		return getOneDrive().getRestTemplate().getForObject(buildUri("/view.search"), SearchItemCollection.class);
	}
	
	public Permission createLink(String type){
		Assert.notNull(type, "[type] is required");
		Map<String,String> request = new HashMap<String,String>();
		request.put("type", type);
		return getOneDrive().getRestTemplate().postForObject(buildUri("/action.createLink"), request, Permission.class);
	}
	
	public ChangesItemCollection changes(){
		return changes(null);
	}
	
	public ChangesItemCollection changes(String token){
		if (token != null){
			getQueryParameters().add("token", token);
		}
		return getOneDrive().getRestTemplate().getForObject(buildUri("/view.delta"), ChangesItemCollection.class);
	}
}
