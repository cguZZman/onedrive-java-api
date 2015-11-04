package com.onedrive.api.resource.support;

import java.util.List;

import org.springframework.util.Assert;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.onedrive.api.OneDrive;
import com.onedrive.api.resource.Item;
import com.onedrive.api.resource.Resource;

public class ItemList extends Resource {
	
	private List<Item> value;
	@JsonProperty("@odata.nextLink")
	private String nextLink;
	@JsonProperty("@search.approximateCount")
	private long approximateCount;
	
	@JsonCreator
	public ItemList(@JacksonInject OneDrive oneDrive) {
		super(oneDrive);
		Assert.notNull(oneDrive, "[oneDrive] is required");
	}
	public String getNextLink() {
		return nextLink;
	}
	public void setNextLink(String nextLink) {
		this.nextLink = nextLink;
	}
	public long getApproximateCount() {
		return approximateCount;
	}
	public void setApproximateCount(long approximateCount) {
		this.approximateCount = approximateCount;
	}
	public List<Item> getValue() {
		return value;
	}
	public void setValue(List<Item> value) {
		this.value = value;
	}
	
	public ItemList nextPage(){
		Assert.notNull(nextLink, "[nextLink] is required");
		return getOneDrive().getRestTemplate().getForObject(nextLink, ItemList.class);
	}
}
