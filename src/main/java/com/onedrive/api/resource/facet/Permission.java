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
package com.onedrive.api.resource.facet;

import java.util.List;

import com.onedrive.api.resource.support.ItemReference;

public class Permission {
	public static final String ROLE_READ = "read";
	public static final String ROLE_WRITE = "write";		
	private String id;
	private List<String> role;
	private SharingLink link;
	private ItemReference inheritedFrom;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<String> getRole() {
		return role;
	}
	public void setRole(List<String> role) {
		this.role = role;
	}
	public SharingLink getLink() {
		return link;
	}
	public void setLink(SharingLink link) {
		this.link = link;
	}
	public ItemReference getInheritedFrom() {
		return inheritedFrom;
	}
	public void setInheritedFrom(ItemReference inheritedFrom) {
		this.inheritedFrom = inheritedFrom;
	}
	@Override
	public String toString() {
		return "Permission [id=" + id + ", role=" + role + ", link=" + link + ", inheritedFrom=" + inheritedFrom + "]";
	}
}
