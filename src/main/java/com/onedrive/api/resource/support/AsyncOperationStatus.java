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

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.onedrive.api.OneDrive;
import com.onedrive.api.resource.Resource;

@JsonInclude(Include.NON_NULL)
public class AsyncOperationStatus extends Resource {
	private String operation;
	private Double percentageComplete;
	private String status;
	private String monitorUrl;

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
	
	public String getMonitorUrl() {
		return monitorUrl;
	}

	public void setMonitorUrl(String monitorUrl) {
		this.monitorUrl = monitorUrl;
	}

	public AsyncOperationStatus status(){
		Assert.notNull(monitorUrl, "[monitorUrl] is required");
		URI uri = null;
		try {
			uri = new URI(monitorUrl);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		ResponseEntity<AsyncOperationStatus> response = getOneDrive().getRestTemplate().exchange(uri, HttpMethod.GET, null, AsyncOperationStatus.class);
		AsyncOperationStatus monitor = response.getBody();
		if (monitor == null){
			monitor = new AsyncOperationStatus(getOneDrive());
		}
		monitor.setMonitorUrl(monitorUrl);
		return monitor;
	}
	
	public void delete(){
		try {
			URI uri = new URI(getMonitorUrl());
			getOneDrive().getRestTemplate().delete(uri);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
}
