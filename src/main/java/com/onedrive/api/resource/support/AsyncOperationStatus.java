package com.onedrive.api.resource.support;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.onedrive.api.OneDrive;
import com.onedrive.api.resource.Item;

@JsonInclude(Include.NON_NULL)
public class AsyncOperationStatus extends Item {
	private String operation;
	private Double percentageComplete;
	private String status;

	@JsonCreator
	public AsyncOperationStatus(@JacksonInject OneDrive oneDrive) {
		super(oneDrive);
		Assert.notNull(oneDrive, "[oneDrive] is required");
	}
	
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public Double getPercentageComplete() {
		return percentageComplete;
	}
	public void setPercentageComplete(Double percentageComplete) {
		this.percentageComplete = percentageComplete;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public AsyncOperationStatus status(){
		Assert.notNull(getSourceUrl(), "[this.sourceUrl] is required");
		URI uri = null;
		try {
			uri = new URI(getSourceUrl());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		ResponseEntity<AsyncOperationStatus> response = getOneDrive().getRestTemplate().exchange(uri, HttpMethod.GET, null, AsyncOperationStatus.class);
		AsyncOperationStatus status = response.getBody();
		if (status == null){
			status = new AsyncOperationStatus(getOneDrive());
		}
		return response.getBody();
	}
	
	public Optional<Error> delete(){
		initDrive();
		URI uri = null;
		try {
			uri = new URI(getSourceUrl());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		if (StringUtils.isEmpty(getId()) && !StringUtils.isEmpty(getSourceUrl())){
			ResponseEntity<AsyncOperationStatus> response = getOneDrive().getRestTemplate().exchange(uri, HttpMethod.DELETE, null, AsyncOperationStatus.class);
			return response.getBody()!=null?Optional.ofNullable(response.getBody().getError()):Optional.empty();
		} else {
			return super.delete();
		}
	}
}
