package com.onedrive.api.request;

import java.util.Collections;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.util.LinkedMultiValueMap;

import com.onedrive.api.OneDrive;
import com.onedrive.api.resource.Item;
import com.onedrive.api.resource.support.UploadSession;

public class UploadSessionRequest extends BaseRequest<UploadSessionRequest> {
	
	public UploadSessionRequest(OneDrive oneDrive, String url, LinkedMultiValueMap<String, String> queryParameters) {
		super(oneDrive, url, queryParameters);
	}
	public UploadSession create() {
		return create(null, null);
	}
	public UploadSession create(String conflictBehavior) {
		return create(null, conflictBehavior);
	}
	public UploadSession create(String name, String conflictBehavior) {
		HttpEntity<?> httpEntity = null;
		if (name != null || conflictBehavior != null){
			Item item = new Item(getOneDrive());
			item.setName(name);
			item.setConflictBehavior(conflictBehavior);
			httpEntity = new HttpEntity<Map<String, Item>>(Collections.singletonMap("item", item), getHeaders());
		} else {
			httpEntity = new HttpEntity<>(getHeaders());
		}
        UploadSession session = getOneDrive().getRestTemplate().postForObject(buildUri(), httpEntity, UploadSession.class);
		return session; 
    }
	
	public UploadSessionRequest expand(String value){
		queryParameters.add("expand", value);
		return this;
	}
	public UploadSessionRequest select(String value){
		queryParameters.add("select", value);
		return this;
	}
	public UploadSessionRequest top(int value){
		queryParameters.add("top", value+"");
		return this;
	}
}
