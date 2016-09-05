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
package com.onedrive.api.resource;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.onedrive.api.OneDrive;

@JsonInclude(Include.NON_NULL)
public class Resource {
	public static final String CONTENT_RANGE_HEADER = "Content-Range";
	
	public static final String CONFLICT_BEHAVIOR_FAIL = "fail";
	public static final String CONFLICT_BEHAVIOR_REPLACE = "replace";
	public static final String CONFLICT_BEHAVIOR_RENAME = "rename";
	
	private String id;
	private String contentRange;
	
	@JsonIgnore
	private OneDrive oneDrive;

	@JsonCreator
	public Resource(@JacksonInject OneDrive oneDrive) {
		this.oneDrive = oneDrive;
	}
	
	protected OneDrive getOneDrive() {
		return oneDrive;
	}
	
	
	public String getContentRange() {
		return contentRange;
	}
	
	public void setContentRange(String contentRange) {
		this.contentRange = contentRange;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		if (oneDrive != null){
			try {
				return getOneDrive().getObjectMapper().writeValueAsString(this);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}
		return super.toString();
	}
}
