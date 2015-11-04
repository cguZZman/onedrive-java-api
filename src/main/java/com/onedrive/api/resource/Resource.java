package com.onedrive.api.resource;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.onedrive.api.OneDrive;
import com.onedrive.api.resource.support.Error;

@JsonInclude(Include.NON_NULL)
public class Resource {
	public static final String CONTENT_RANGE_HEADER = "Content-Range";
	@JsonIgnore
	private OneDrive oneDrive;
	private Error error;
	private String contentRange;
	

	public Resource(OneDrive oneDrive) {
		this.oneDrive = oneDrive;
	}
	public OneDrive getOneDrive() {
		return oneDrive;
	}
	
	public Error getError() {
		return error;
	}

	public void setError(Error error) {
		this.error = error;
	}
	
	public String getContentRange() {
		return contentRange;
	}
	
	public void setContentRange(String contentRange) {
		this.contentRange = contentRange;
	}
	
	@Override
	public String toString() {
		try {
			return getOneDrive().getObjectMapper().writeValueAsString(this);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return super.toString();
	}
}
