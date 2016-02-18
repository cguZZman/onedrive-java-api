package com.onedrive.api;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseExtractor;

import com.onedrive.api.support.ClientCredential;
import com.onedrive.api.support.Scope;
import com.onedrive.api.support.SerializatorAccessTokenListener;

public class ItemDownloadTest {
	
	private static OneDrive oneDrive;
	
	@BeforeClass
	public static void setUp() {
		oneDrive = new OneDrive(new ClientCredential("0000000048145120"),
				Arrays.asList(Scope.OFFLINE_ACCESS, "wl.skydrive", "wl.signin", "onedrive.readwrite"),
				OneDrive.MOBILE_REDIRECT_URI);
		oneDrive.setAuthorizationCode("M89ab534a-f3fe-b39e-eebe-bf2d1386ffca");
		oneDrive.setAccessTokenListener(new SerializatorAccessTokenListener());
	}
	@Test
	public void downloadToFilePartial() throws IOException {
		String downloadedRange = oneDrive.drive().root().itemByPath("privacypolicy.htm").content().range(0, 2000).download(Paths.get("c:/users/carlos/privacypolicy-partial.htm"));
		System.out.println(downloadedRange);
	}
	@Test
	public void downloadToFile() throws IOException {
		oneDrive.drive().root().itemByPath("privacypolicy.htm").content().download(Paths.get("c:/users/carlos/privacypolicy-direct.htm"));
	}
	@Test
	public void downloadToOutputStream() throws IOException {
		OutputStream os = Files.newOutputStream(Paths.get("c:/users/carlos/privacypolicy-with-outputstram.htm"), StandardOpenOption.CREATE);
		oneDrive.drive().root().itemByPath("privacypolicy.htm").content().download(os);
		os.close();
	}
	@Test
	public void downloadWithExtractor() {
		oneDrive.drive().root().itemByPath("privacypolicy.htm").content().download(
			new ResponseExtractor<Void>() {
				@Override
				public Void extractData(ClientHttpResponse response) throws IOException {
					Files.copy(response.getBody(), Paths.get("c:/users/carlos/privacypolicy-with-extractor.htm"), StandardCopyOption.REPLACE_EXISTING);
					return null;
				}
			}
		);
	}
	
}
