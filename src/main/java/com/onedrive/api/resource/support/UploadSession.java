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
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.onedrive.api.OneDrive;
import com.onedrive.api.resource.Item;
import com.onedrive.api.resource.Resource;

@JsonInclude(Include.NON_NULL)
public class UploadSession extends Item {
	private String uploadUrl;
	private Date expirationDateTime;
	private List<String> nextExpectedRanges;

	@JsonCreator
	public UploadSession(@JacksonInject OneDrive oneDrive) {
		super(oneDrive);
	}
	
	public String getUploadUrl() {
		return uploadUrl;
	}
	public void setUploadUrl(String uploadUrl) {
		this.uploadUrl = uploadUrl;
	}
	public Date getExpirationDateTime() {
		return expirationDateTime;
	}
	public void setExpirationDateTime(Date expirationDateTime) {
		this.expirationDateTime = expirationDateTime;
	}
	public List<String> getNextExpectedRanges() {
		return nextExpectedRanges;
	}
	public void setNextExpectedRanges(List<String> nextExpectedRanges) {
		this.nextExpectedRanges = nextExpectedRanges;
	}
	@JsonIgnore
	private URI getUploadUri(){
		try {
			return new URI(uploadUrl);
		} catch (URISyntaxException e) {
			throw new RuntimeException("UploadSession instance invalid. The uploadUrl is invalid.");
		}
	}
	public UploadSession uploadFragment(long startIndex, long endIndex, long totalSize, byte[] data){
		Assert.notNull(uploadUrl, "UploadSession instance invalid. The uploadUrl is not defined.");
		Assert.notNull(data, "The data is required.");
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		headers.add("Content-Range", "bytes " + startIndex+"-"+endIndex+"/"+totalSize);
		System.out.println(headers);
		ResponseEntity<UploadSession> response = getOneDrive().getRestTemplate().exchange(getUploadUri(), HttpMethod.PUT, new HttpEntity<ByteArrayResource>(new ByteArrayResource(data), headers), UploadSession.class);
		UploadSession session = response.getBody();
		session.setUploadUrl(uploadUrl);
		return session;
	}
	public Optional<Error> delete(){
		initDrive();
		if (StringUtils.isEmpty(getId()) && !StringUtils.isEmpty(uploadUrl)){
			ResponseEntity<Resource> response = getOneDrive().getRestTemplate().exchange(getUploadUri(), HttpMethod.DELETE, null, Resource.class);
			return response.getBody()!=null?Optional.ofNullable(response.getBody().getError()):Optional.empty();
		} else {
			return super.delete();
		}
	}
	public UploadSession status(){
		initDrive();
		UploadSession session = getOneDrive().getRestTemplate().getForObject(getUploadUri(), UploadSession.class);
		session.setUploadUrl(uploadUrl);
		return session;
	}
}