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

import java.util.Map;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.onedrive.api.OneDrive;
import com.onedrive.api.resource.facet.Quota;
import com.onedrive.api.resource.support.IdentitySet;

@JsonInclude(Include.NON_NULL)
public class Drive extends Resource {
	public static final String DRIVE_TYPE_PERSONAL = "personal";
	public static final String DRIVE_TYPE_BUSINESS = "business";
	
	public static final String DEFAULT_DRIVE_PATH = "/drive";
	public static final String DEFAULT_DRIVE_ID = "me";
	public static final String DRIVES_PATH = "/drives";
	
	private String driveType;
	private IdentitySet owner;
	private Quota quota;
	@JsonProperty("@odata.context")
	private String dataContext;
	
	@JsonCreator
	public Drive(@JacksonInject OneDrive oneDrive) {
		super(oneDrive);
		Assert.notNull(oneDrive, "[oneDrive] is required");
	}
	public String getDriveType() {
		return driveType;
	}
	public void setDriveType(String driveType) {
		this.driveType = driveType;
	}
	public IdentitySet getOwner() {
		return owner;
	}
	public void setOwner(IdentitySet owner) {
		this.owner = owner;
	}
	public Quota getQuota() {
		return quota;
	}
	public void setQuota(Quota quota) {
		this.quota = quota;
	}
	public String getDataContext() {
		return dataContext;
	}
	public void setDataContext(String dataContext) {
		this.dataContext = dataContext;
	}
	public static String buildDrivePath(String driveId){
		return DRIVES_PATH + OneDrive.PATH_SEPARATOR + (StringUtils.isEmpty(driveId)?DEFAULT_DRIVE_ID:driveId);
	}
	public String buildDrivePath(){
		return buildDrivePath(getId());
	}
	private Item itemInternal(String path, Map<String,String> queryParameters){
		if (path.charAt(0) != OneDrive.PATH_SEPARATOR.charAt(0)){
			path = OneDrive.PATH_SEPARATOR + path;
		}
		Item item = getOneDrive().getRestTemplate().getForObject(getOneDrive().getUri(buildDrivePath()+path, queryParameters), Item.class);
		item.setDrive(this);
		return item;
	}
	
	public Drive metadata(){
		return metadata(null);
	}
	public Drive metadata(Map<String,String> queryParameters){
		return getOneDrive().drive(StringUtils.isEmpty(getId())?DEFAULT_DRIVE_ID:getId(), queryParameters);
	}
	public Item root(){
		return root((Map<String,String>) null);
	}
	public Item root(Map<String,String> queryParameters){
		return itemInternal("root", queryParameters);
	}
	public Item root(String itemPath){
		return root(itemPath, null);
	}
	public Item root(String itemPath, Map<String,String> queryParameters){
		Assert.notNull(itemPath, "[itemPath] is required");
		return itemInternal("root:"+itemPath+":", queryParameters);
	}
	public Item specialFolder(String name){
		return specialFolder(name, null);
	}
	public Item specialFolder(String name, Map<String,String> queryParameters){
		Assert.notNull(name, "[name] is required");
		return itemInternal("special"+OneDrive.PATH_SEPARATOR+name, queryParameters);
	}
	public Item items(String itemId){
		return items(itemId, null);
	}
	public Item items(String itemId, Map<String,String> queryParameters){
		Assert.notNull(itemId, "[itemId] is required");
		return itemInternal(Item.buildItemPath(itemId), queryParameters);
	}
	@Override
	public String toString() {
		return "Drive [id=" + getId() + ", driveType=" + driveType + ", owner=" + owner + ", quota=" + quota
				+ ", dataContext=" + dataContext + "]";
	}
}
