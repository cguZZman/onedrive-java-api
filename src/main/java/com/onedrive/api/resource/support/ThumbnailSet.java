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

import java.io.OutputStream;

import org.springframework.util.Assert;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.onedrive.api.OneDrive;
import com.onedrive.api.resource.Item;
import com.onedrive.api.resource.Resource;

@JsonInclude(Include.NON_NULL)
public class ThumbnailSet extends Resource {
	private String id;
	private Thumbnail small;
	private Thumbnail medium;
	private Thumbnail large;
	private Thumbnail source;
	private Item item;
	@JsonCreator
	public ThumbnailSet(@JacksonInject OneDrive oneDrive) {
		super(oneDrive);
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Thumbnail getSmall() {
		return small;
	}
	public void setSmall(Thumbnail small) {
		this.small = small;
	}
	public Thumbnail getMedium() {
		return medium;
	}
	public void setMedium(Thumbnail medium) {
		this.medium = medium;
	}
	public Thumbnail getLarge() {
		return large;
	}
	public void setLarge(Thumbnail large) {
		this.large = large;
	}
	public Thumbnail getSource() {
		return source;
	}
	public void setSource(Thumbnail source) {
		this.source = source;
	}
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
	public Thumbnail metadata(String size){
		Assert.notNull(id, "[this.id] is required");
		Assert.notNull(item, "[this.item] is required");
		Assert.notNull(item.getId(), "[this.item.id] is required");
		return item.thumbnail(id, size);
	}
	public Resource download(String size, OutputStream outputStream){
		Assert.notNull(id, "[this.id] is required");
		Assert.notNull(item, "[this.item] is required");
		Assert.notNull(item.getId(), "[this.item.id] is required");
		return item.thumbnail(id, size, outputStream);
	}
}
