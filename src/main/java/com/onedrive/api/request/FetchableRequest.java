package com.onedrive.api.request;

import java.lang.reflect.ParameterizedType;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.util.LinkedMultiValueMap;

import com.onedrive.api.OneDrive;

public class FetchableRequest<T, S extends BaseRequest<?>> extends BaseRequest<S> {
	
	private Class<T> type;
	
	@SuppressWarnings("unchecked")
	public FetchableRequest(OneDrive oneDrive, String url, LinkedMultiValueMap<String,String> queryParameters) {
		super(oneDrive, url, queryParameters);
		this.type = (Class<T>) ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	public T fetch(){
		HttpEntity<T> response = getOneDrive().getRestTemplate().exchange(buildUri(), HttpMethod.GET, new HttpEntity<T>(getHeaders()), type);
		return response.getBody();
	}

	@SuppressWarnings("unchecked")
	private S extracted() {
		return (S) this;
	}
	
	public S expand(String value){
		queryParameters.add("expand", value);
		return extracted();
	}
	public S select(String value){
		queryParameters.add("select", value);
		return extracted();
	}
	public S filter(String value){
		queryParameters.add("filter", value);
		return extracted();
	}
	public S skipToken(String value){
		queryParameters.add("skipToken", value);
		return extracted();
	}
	public S top(int value){
		queryParameters.add("top", value+"");
		return extracted();
	}
	public S orderby(String value){
		queryParameters.add("orderby", value);
		return extracted();
	}
}
