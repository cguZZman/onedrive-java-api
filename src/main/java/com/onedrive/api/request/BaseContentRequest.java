package com.onedrive.api.request;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;

import com.onedrive.api.OneDrive;
import com.onedrive.api.resource.Resource;

public class BaseContentRequest extends BaseRequest<BaseContentRequest> {
	
	private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;
	
	public BaseContentRequest(OneDrive oneDrive, String url, LinkedMultiValueMap<String, String> queryParameters) {
		super(oneDrive, url, queryParameters);
	}
	
	protected RequestCallback getRequestCallback(){
		return new RequestCallback() {
			@Override
			public void doWithRequest(ClientHttpRequest request) throws IOException {
				request.getHeaders().putAll(getHeaders());
			}
		};
	}
	
	public String download(Path fileTarget, CopyOption... options){
		return getOneDrive().getRestTemplate().execute(buildUri(), HttpMethod.GET, getRequestCallback(), 
			new ResponseExtractor<String>() {
				@Override
				public String extractData(ClientHttpResponse response) throws IOException {
					Files.copy(response.getBody(), fileTarget, options);
					return response.getHeaders().getFirst(Resource.CONTENT_RANGE_HEADER);
				}
			}
		);
	}
	
	public String download(OutputStream outputStream){
		return getOneDrive().getRestTemplate().execute(buildUri(), HttpMethod.GET, getRequestCallback(), 
			new ResponseExtractor<String>() {
				@Override
				public String extractData(ClientHttpResponse response) throws IOException {
					copyStream(response.getBody(), outputStream);
					return response.getHeaders().getFirst(Resource.CONTENT_RANGE_HEADER);
				}
			}
		);
	}
	
	public void download(ResponseExtractor<?> responseExtractor){
		getOneDrive().getRestTemplate().execute(buildUri(), HttpMethod.GET, getRequestCallback(), responseExtractor);
	}
	
	private void copyStream(InputStream input, OutputStream output)
            throws IOException {
        int n = 0;
        byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
        while ((n = input.read(buffer)) != -1 ) {
            output.write(buffer, 0, n);
        }
    }
	
}
