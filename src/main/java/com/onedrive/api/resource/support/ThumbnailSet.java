package com.onedrive.api.resource.support;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ThumbnailSet {
	private String id;
	private Thumbnail small;
	private Thumbnail medium;
	private Thumbnail large;
	private Thumbnail source;
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
}
