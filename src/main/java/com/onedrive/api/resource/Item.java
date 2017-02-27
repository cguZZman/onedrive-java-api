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

import java.util.Date;

import org.springframework.util.Assert;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.onedrive.api.OneDrive;
import com.onedrive.api.resource.facet.Audio;
import com.onedrive.api.resource.facet.Deleted;
import com.onedrive.api.resource.facet.File;
import com.onedrive.api.resource.facet.FileSystemInfo;
import com.onedrive.api.resource.facet.Folder;
import com.onedrive.api.resource.facet.Hashes;
import com.onedrive.api.resource.facet.Image;
import com.onedrive.api.resource.facet.Location;
import com.onedrive.api.resource.facet.Permission;
import com.onedrive.api.resource.facet.Photo;
import com.onedrive.api.resource.facet.Quota;
import com.onedrive.api.resource.facet.RemoteItem;
import com.onedrive.api.resource.facet.Shared;
import com.onedrive.api.resource.facet.SharingLink;
import com.onedrive.api.resource.facet.SpecialFolder;
import com.onedrive.api.resource.facet.Video;
import com.onedrive.api.resource.support.IdentitySet;
import com.onedrive.api.resource.support.ItemReference;

@JsonInclude(Include.NON_NULL)
public class Item extends Resource {
	private String name;
	private String eTag;
	private String cTag;
	private IdentitySet createdBy;
	private IdentitySet lastModifiedBy;
	private Date createdDateTime;
	private Date lastModifiedDateTime;
	private Long size;
	private ItemReference parentReference;
	private String webUrl;
	private String description;
	private String webDavUrl;
	
	/* Facets */
	private Folder folder;
	private File file;
	private FileSystemInfo fileSystemInfo;
	private Image image;
	private Photo photo;
	private Audio audio;
	private Video video;
	private Location location;
	private Deleted deleted;
	private Hashes hashes;
	private Permission permission;
	private Quota quota;
	private SharingLink sharingLink;
	private SpecialFolder specialFolder;
	private Shared shared;
	private RemoteItem remoteItem;
	
	/*Instance Attributes*/
	@JsonProperty("@name.conflictBehavior")
	private String conflictBehavior;
	@JsonProperty("@content.downloadUrl")
	private String downloadUrl;
	@JsonProperty("@content.sourceUrl")
	private String sourceUrl;
	@JsonProperty("@odata.context")
	private String context;
	
	@JsonIgnore
	private Drive drive;
	
	@JsonCreator
	public Item(@JacksonInject OneDrive oneDrive) {
		super(oneDrive);
		Assert.notNull(oneDrive, "[oneDrive] is required");
	}
	public Item(OneDrive oneDrive, String id) {
		super(oneDrive);
		setId(id);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String geteTag() {
		return eTag;
	}
	public void seteTag(String eTag) {
		this.eTag = eTag;
	}
	public String getcTag() {
		return cTag;
	}
	public void setcTag(String cTag) {
		this.cTag = cTag;
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
	public Date getCreatedDateTime() {
		return createdDateTime;
	}
	public void setCreatedDateTime(Date createdDateTime) {
		this.createdDateTime = createdDateTime;
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
	public String getWebUrl() {
		return webUrl;
	}
	public void setWebUrl(String webUrl) {
		this.webUrl = webUrl;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
	public Image getImage() {
		return image;
	}
	public void setImage(Image image) {
		this.image = image;
	}
	public Photo getPhoto() {
		return photo;
	}
	public void setPhoto(Photo photo) {
		this.photo = photo;
	}
	public Audio getAudio() {
		return audio;
	}
	public void setAudio(Audio audio) {
		this.audio = audio;
	}
	public Video getVideo() {
		return video;
	}
	public void setVideo(Video video) {
		this.video = video;
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	public Deleted getDeleted() {
		return deleted;
	}
	public void setDeleted(Deleted deleted) {
		this.deleted = deleted;
	}
	public Hashes getHashes() {
		return hashes;
	}
	public void setHashes(Hashes hashes) {
		this.hashes = hashes;
	}
	public Permission getPermission() {
		return permission;
	}
	public void setPermission(Permission permission) {
		this.permission = permission;
	}
	public Quota getQuota() {
		return quota;
	}
	public void setQuota(Quota quota) {
		this.quota = quota;
	}
	public SharingLink getSharingLink() {
		return sharingLink;
	}
	public void setSharingLink(SharingLink sharingLink) {
		this.sharingLink = sharingLink;
	}
	public SpecialFolder getSpecialFolder() {
		return specialFolder;
	}
	public void setSpecialFolder(SpecialFolder specialFolder) {
		this.specialFolder = specialFolder;
	}
	public String getConflictBehavior() {
		return conflictBehavior;
	}
	public void setConflictBehavior(String conflictBehavior) {
		this.conflictBehavior = conflictBehavior;
	}
	public String getDownloadUrl() {
		return downloadUrl;
	}
	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}
	public String getSourceUrl() {
		return sourceUrl;
	}
	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public String getWebDavUrl() {
		return webDavUrl;
	}
	public void setWebDavUrl(String webDavUrl) {
		this.webDavUrl = webDavUrl;
	}
	public Shared getShared() {
		return shared;
	}
	public void setShared(Shared shared) {
		this.shared = shared;
	}
	public RemoteItem getRemoteItem() {
		return remoteItem;
	}
	public void setRemoteItem(RemoteItem remoteItem) {
		this.remoteItem = remoteItem;
	}
}
