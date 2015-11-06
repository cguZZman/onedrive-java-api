package com.onedrive.api.resource.support;

import java.util.List;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.onedrive.api.OneDrive;
import com.onedrive.api.resource.Resource;

@JsonInclude(Include.NON_NULL)
public class ThumbnailSetList extends Resource {
	private List<ThumbnailSet> value;
	@JsonCreator
	public ThumbnailSetList(@JacksonInject OneDrive oneDrive) {
		super(oneDrive);
	}
	public List<ThumbnailSet> getValue() {
		return value;
	}
	public void setValue(List<ThumbnailSet> value) {
		this.value = value;
	}
}
