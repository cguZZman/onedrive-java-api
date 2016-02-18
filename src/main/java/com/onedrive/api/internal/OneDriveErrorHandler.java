package com.onedrive.api.internal;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import com.onedrive.api.exception.OneDriveRequestException;
import com.onedrive.api.exception.OneDriveServiceException;
import com.onedrive.api.resource.support.ErrorResponse;

public class OneDriveErrorHandler implements ResponseErrorHandler{
	
	private List<HttpMessageConverter<?>> messageConverters = new RestTemplate().getMessageConverters();
	private final ResponseErrorHandler errorHandler;
	
	public OneDriveErrorHandler(List<HttpMessageConverter<?>> messageConverters) {
		this.messageConverters = messageConverters;
		this.errorHandler = new DefaultResponseErrorHandler();
	}

	@Override
	public boolean hasError(ClientHttpResponse response) throws IOException {
		return response.getStatusCode().series().equals(HttpStatus.Series.CLIENT_ERROR)
				|| response.getStatusCode().series().equals(HttpStatus.Series.SERVER_ERROR);
	}

	@Override
	public void handleError(ClientHttpResponse response) throws IOException {
		ClientHttpResponse bufferedResponse = new ClientHttpResponse() {
			private byte[] lazyBody;
			public HttpStatus getStatusCode() throws IOException {
				return response.getStatusCode();
			}
			public synchronized InputStream getBody() throws IOException {
				if (lazyBody == null) {
					InputStream bodyStream = response.getBody();
					if (bodyStream != null) {
						lazyBody = FileCopyUtils.copyToByteArray(bodyStream);
					}
					else {
						lazyBody = new byte[0];
					}
				}
				return new ByteArrayInputStream(lazyBody);
			}
			public HttpHeaders getHeaders() {
				return response.getHeaders();
			}
			public String getStatusText() throws IOException {
				return response.getStatusText();
			}
			public void close() {
				response.close();
			}
			public int getRawStatusCode() throws IOException {
				return response.getRawStatusCode();
			}
		};

		HttpMessageConverterExtractor<ErrorResponse> extractor = new HttpMessageConverterExtractor<ErrorResponse>(ErrorResponse.class, messageConverters);
		ErrorResponse errorResponse = extractor.extractData(bufferedResponse);
		if (errorResponse != null) {
			if (response.getStatusCode().series().equals(HttpStatus.Series.CLIENT_ERROR))
				throw new OneDriveRequestException(errorResponse, bufferedResponse.getHeaders(), bufferedResponse.getStatusCode());
			else
				throw new OneDriveServiceException(errorResponse, bufferedResponse.getHeaders(), bufferedResponse.getStatusCode());
		}
		errorHandler.handleError(bufferedResponse);
	}

}
