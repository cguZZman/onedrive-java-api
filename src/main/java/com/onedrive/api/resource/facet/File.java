package com.onedrive.api.resource.facet;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class File {
	private String mimeType;
	private Hashes hashes;
	public String getMimeType() {
		return mimeType;
	}
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}
	public Hashes getHashes() {
		return hashes;
	}
	public void setHashes(Hashes hashes) {
		this.hashes = hashes;
	}
}
