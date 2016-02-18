package com.onedrive.api;

import java.io.IOException;
import java.util.Arrays;

import org.junit.BeforeClass;
import org.junit.Test;

import com.onedrive.api.resource.Item;
import com.onedrive.api.support.ClientCredential;
import com.onedrive.api.support.Scope;
import com.onedrive.api.support.SerializatorAccessTokenListener;

public class FolderTest {
	
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
	public void createFolder1() throws IOException {
		Item item = oneDrive.drive().root().children().createFolder("testcgg", "rename");
		System.out.println(item);
	}
	
	@Test
	public void createFolder2() throws IOException {
		Item item = oneDrive.drive().root().itemByPath("testcgg").createAsFolder("rename");
		System.out.println(item);
	}
}
