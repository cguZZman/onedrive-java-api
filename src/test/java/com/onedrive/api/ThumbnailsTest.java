package com.onedrive.api;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.BeforeClass;
import org.junit.Test;

import com.onedrive.api.resource.support.Thumbnail;
import com.onedrive.api.resource.support.ThumbnailSet;
import com.onedrive.api.resource.support.ThumbnailSetCollection;
import com.onedrive.api.support.ClientCredential;
import com.onedrive.api.support.Scope;
import com.onedrive.api.support.SerializatorAccessTokenListener;

public class ThumbnailsTest {
	
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
	public void thumbnails() {
		ThumbnailSetCollection collection = oneDrive.drive().items("C899E30C041941B5!324411").thumbnails().fetch();
		System.out.println(collection);
	}
	
	@Test
	public void thumbnailSet() {
		ThumbnailSet set = oneDrive.drive().items("C899E30C041941B5!324411").thumbnails("0").fetch();
		System.out.println(set);
	}
	
	@Test
	public void thumbnailSize() {
		Thumbnail obj = oneDrive.drive().items("C899E30C041941B5!324411").thumbnails("0").size("small").fetch();
		System.out.println(obj);
	}
	
	@Test
	public void thumbnailSource() {
		Thumbnail obj = oneDrive.drive().items("C899E30C041941B5!324411").thumbnails("0").source().fetch();
		System.out.println(obj);
	}
	@Test
	public void uploadThumbnail() throws IOException {
		String location = oneDrive.drive().items("C899E30C041941B5!324411").thumbnails("0").source().content().upload(Files.newInputStream(Paths.get("C:/Users/carlos/Downloads/Screenshot_2015-11-28-17-00-49.png")));;
		System.out.println(location);
	}
	@Test
	public void deleteThumbnail() {
		oneDrive.drive().items("C899E30C041941B5!324411").thumbnails("0").source().delete();
	}
}
