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

import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.springframework.util.Assert;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.onedrive.api.OneDrive;
import com.onedrive.api.resource.Item;
import com.onedrive.api.resource.Resource;

public class BaseCollection<T extends BaseCollection<?>> extends Resource {
	
	private List<Item> value;
	@JsonProperty("@odata.nextLink")
	private String nextLink;
	
	private Class<T> type;
	
	@SuppressWarnings("unchecked")
	@JsonCreator
	public BaseCollection(@JacksonInject OneDrive oneDrive) {
		super(oneDrive);
		Assert.notNull(oneDrive, "[oneDrive] is required");
		this.type = (Class<T>) ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
	public String getNextLink() {
		return nextLink;
	}
	public void setNextLink(String nextLink) {
		this.nextLink = nextLink;
	}
	public List<Item> getValue() {
		return value;
	}
	public void setValue(List<Item> value) {
		this.value = value;
	}
	
	public T nextPage(){
		Assert.notNull(nextLink, "[nextLink] is required");
		return getOneDrive().getRestTemplate().getForObject(nextLink, type);
	}
}
