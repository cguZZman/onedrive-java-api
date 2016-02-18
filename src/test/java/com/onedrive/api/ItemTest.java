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

public class ItemTest {
	
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
	public void byPath() {
		Item item = oneDrive.drive().root().itemByPath("music").itemByPath("kwi").fetch();
		System.out.println(item);
		Assert.assertNotNull(item);
		Assert.assertNotNull(item.getId());
	}
	
	@Test
	public void item() {
		Item item = oneDrive.drive().items("C899E30C041941B5!197482").fetch();
		System.out.println(item);
		Assert.assertNotNull(item);
		Assert.assertNotNull(item.getId());
	}
	
	@Test
	public void itemUnderRoot() {
		Item item = oneDrive.drive().root().children("public").fetch();
		System.out.println(item);
		Assert.assertNotNull(item);
		Assert.assertNotNull(item.getId());
	}
	
	@Test
	public void rootChildren() {
		ItemCollection collection = oneDrive.drive().root().children().fetch();
		System.out.println(collection);
		Assert.assertNotNull(collection);
		Assert.assertNotNull(collection.getValue());
	}
	
	@Test
	public void itemDelete() {
		Item item = oneDrive.drive().root().itemByPath("deleteMe").createAsFolder();
		System.out.println(item);
		oneDrive.drive().items(item.getId()).delete();
		System.out.println("deleted.");
	}
	
	@Test
	public void itemRename() {
		Item item = oneDrive.drive().root().itemByPath("renameMe").createAsFolder();
		System.out.println(item);
		oneDrive.drive().items(item.getId()).rename("newName");
		System.out.println("renamed.");
	}
	
	@Test
	public void itemMove1() {
		Item item = oneDrive.drive().root().itemByPath("moveMe1").createAsFolder();
		System.out.println(item);
		oneDrive.drive().items(item.getId()).move("/documents");
		System.out.println("moved to documents folder.");
	}
	@Test
	public void itemMove2() {
		Item item = oneDrive.drive().root().itemByPath("moveMe2").createAsFolder();
		System.out.println(item);
		oneDrive.drive().items(item.getId()).move("/documents", "movedAndRenamedFolder");
		System.out.println("moved to documents folder and renamed.");
	}
	
	@Test
	public void itemUpdate() {
		Item item = oneDrive.drive().root().itemByPath("updateMe").createAsFolder();
		System.out.println(item);
		Item newInfo = new Item(oneDrive);
		newInfo.setName("newName");
		newInfo.setDescription("some new description");
		oneDrive.drive().items(item.getId()).update(newInfo);
		System.out.println("item updated.");
	}
}
