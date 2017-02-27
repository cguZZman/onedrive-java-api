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

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.onedrive.api.resource.support.IdentitySet;
import com.onedrive.api.resource.support.ItemReference;

@JsonInclude(Include.NON_NULL)
public class RemoteItem {
	private String id;
	private String name;
	private IdentitySet createdBy;
	private IdentitySet lastModifiedBy;
	private Date lastModifiedDateTime;
	private Long size;
	private ItemReference parentReference;
	
	/* Facets */
	private Folder folder;
	private File file;
	private FileSystemInfo fileSystemInfo;
	private Shared shared;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public IdentitySet getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(IdentitySet createdBy) {
		this.createdBy = createdBy;
	}
	public IdentitySet getLastModifiedBy() {
		return lastModifiedBy;
	}
	public void setLastModifiedBy(IdentitySet lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}
	public Date getLastModifiedDateTime() {
		return lastModifiedDateTime;
	}
	public void setLastModifiedDateTime(Date lastModifiedDateTime) {
		this.lastModifiedDateTime = lastModifiedDateTime;
	}
	public Long getSize() {
		return size;
	}
	public void setSize(Long size) {
		this.size = size;
	}
	public ItemReference getParentReference() {
		return parentReference;
	}
	public void setParentReference(ItemReference parentReference) {
		this.parentReference = parentReference;
	}
	public Folder getFolder() {
		return folder;
	}
	public void setFolder(Folder folder) {
		this.folder = folder;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public FileSystemInfo getFileSystemInfo() {
		return fileSystemInfo;
	}
	public void setFileSystemInfo(FileSystemInfo fileSystemInfo) {
		this.fileSystemInfo = fileSystemInfo;
	}
	public Shared getShared() {
		return shared;
	}
	public void setShared(Shared shared) {
		this.shared = shared;
	}	
}
