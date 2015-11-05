package com.onedrive.api.resource.support;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.onedrive.api.OneDrive;

public class SearchItemList extends ItemList {
	
	@JsonProperty("@search.approximateCount")
	private long approximateCount;
	
	@JsonCreator
	public SearchItemList(@JacksonInject OneDrive oneDrive) {
		super(oneDrive);
	}
	public long getApproximateCount() {
		return approximateCount;
	}
	public void setApproximateCount(long approximateCount) {
		this.approximateCount = approximateCount;
	}
}
