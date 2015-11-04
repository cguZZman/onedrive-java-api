package com.onedrive.api.internal;

import java.util.Arrays;
import java.util.Map;

import org.springframework.util.LinkedMultiValueMap;

public class InternalQueryParameterMap extends LinkedMultiValueMap<String, String> {
	private static final long serialVersionUID = 4838283701938990030L;
	public InternalQueryParameterMap(Map<String,String> params){
		if (params != null) {
			for (Entry<String, String> entry : params.entrySet()){
				put(entry.getKey(), Arrays.asList(entry.getValue()));
			}
		}
	}
}
