package com.onedrive.api;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.onedrive.api.exception.OneDriveRequestException;
import com.onedrive.api.resource.Drive;
import com.onedrive.api.resource.Item;
import com.onedrive.api.resource.support.DriveCollection;
import com.onedrive.api.support.ClientCredential;
import com.onedrive.api.support.Scope;
import com.onedrive.api.support.SerializatorAccessTokenListener;

public class DriveTest {
	
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
	public void drive() {
		Drive drive = oneDrive.drive().fetch();
		Assert.assertNotNull(drive);
		Drive me = oneDrive.drives("me").fetch();
		Assert.assertNotNull(me);
		Assert.assertEquals(drive.getId(), me.getId());
		System.out.println(me);
		
		drive = oneDrive.drive().select("id").fetch();
		System.out.println(drive);
		Assert.assertNotNull(drive);
		Assert.assertNotNull(drive.getId());
		Assert.assertNull(drive.getDriveType());
		Assert.assertNull(drive.getOwner());
	}
	
	@Test
	public void drives() {
		DriveCollection drives = oneDrive.drives().fetch();
		Assert.assertNotNull(drives);
		Assert.assertTrue(drives.getValue().size() > 0);
		System.out.println(drives.getValue());
	}
	
	@Test
	public void badRequestException() {
		OneDriveRequestException exeption = null;
		try {
			oneDrive.drives("mex").fetch();
		} catch (OneDriveRequestException e){
			exeption = e;
		}
		Assert.assertNotNull(exeption);
		System.out.println(exeption.getMessage());
	}
	
	@Test
	public void root() {
		Item item = oneDrive.drive().root().fetch();
		System.out.println(item);
		Assert.assertNotNull(item);
		Assert.assertNotNull(item.getId());
		
		item = oneDrive.drive().root().select("id").select("name").fetch();
		System.out.println(item);
		Assert.assertNotNull(item);
		Assert.assertNotNull(item.getId());
		Assert.assertNotNull(item.getName());
		Assert.assertNull(item.getCreatedBy());
	}
}
