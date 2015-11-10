/*******************************************************************************
 * OneDrive Java API
 * Copyright (C) 2015 - Carlos Guzman
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Created on Aug 1, 2015
 * @author: Carlos Guzman (cguZZman) carlosguzmang@hotmail.com
 *******************************************************************************/
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
