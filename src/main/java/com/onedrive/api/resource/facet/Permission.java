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
}
