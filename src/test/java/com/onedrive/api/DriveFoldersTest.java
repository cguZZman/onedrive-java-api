package com.onedrive.api;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.onedrive.api.resource.Item;
import com.onedrive.api.resource.support.ItemCollection;
import com.onedrive.api.support.ClientCredential;
import com.onedrive.api.support.Scope;
import com.onedrive.api.support.SerializatorAccessTokenListener;

public class DriveFoldersTest {
	
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
	public void allPhotos() {
		ItemCollection collection = oneDrive.drive().allPhotos().fetch();
		System.out.println(collection);
		Assert.assertNotNull(collection);
		System.out.println(collection.getNextLink());
		Assert.assertNotNull(collection.getValue());
		System.out.println(collection.getValue().size());
	}
	
	@Test
	public void recent() {
//		ItemCollection collection = oneDrive.drive().recent().fetch();
//		System.out.println(collection);
//		Assert.assertNotNull(collection);
//		System.out.println(collection.getNextLink());
//		Assert.assertNotNull(collection.getValue());
//		System.out.println(collection.getValue().size());
	}
	
	@Test
	public void special() {
		Item item = oneDrive.drive().special("documents").fetch();
		System.out.println(item);
		Assert.assertNotNull(item);
		Assert.assertNotNull(item.getSpecialFolder());
		Assert.assertTrue(item.getSpecialFolder().getName().equals("documents"));
	}
}
