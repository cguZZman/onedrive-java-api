package com.onedrive.api;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.util.StringUtils;

import com.onedrive.api.resource.Item;
import com.onedrive.api.resource.support.AsyncOperationStatus;
import com.onedrive.api.resource.support.UploadSession;
import com.onedrive.api.support.ClientCredential;
import com.onedrive.api.support.Scope;
import com.onedrive.api.support.SerializatorAccessTokenListener;

public class ItemUploadTest {
	
	private static OneDrive oneDrive;
	
	@BeforeClass
	public static void setUp() {
		oneDrive = new OneDrive(new ClientCredential("0000000048145120"),
				Arrays.asList(Scope.OFFLINE_ACCESS, "wl.skydrive", "wl.signin", "onedrive.readwrite"),
				OneDrive.MOBILE_REDIRECT_URI);
		oneDrive.setAuthorizationCode("M62b3f60d-d2af-1aef-2716-d7be4ba4d2e3");
		oneDrive.setAccessTokenListener(new SerializatorAccessTokenListener());
	}
	@Test
	public void simpleUpload() throws IOException {
		Item item = oneDrive.drive().root().itemByPath("diploma-uploaded.png").content().upload(
			Files.newInputStream(Paths.get("c:/users/carlos/downloads/diploma.png"))
		);
		System.out.println(item);
	}
	
	@Test
	public void resumableUpload() throws IOException {
		UploadSession session = oneDrive.drive().root().itemByPath("text-uploaded-resumable.txt")
				.uploadCreateSession().create().uploadFragment(0, 2, 5, new byte[]{65,66,67});
		
		session = session.uploadFragment(3, 4, 5, new byte[]{68,69});
		Assert.assertTrue(session.isComplete());
		System.out.println(session);
	}
	
	@Test
	public void multipartUpload() throws IOException {
		Item item = new Item(oneDrive);
		item.setName("diploma-multipart.png");
		item = oneDrive.drive().root().children().upload(item, Files.newInputStream(Paths.get("c:/users/carlos/downloads/diploma.png")));
		System.out.println(item);
	}
	
	@Test
	public void uploadFromUrl() throws IOException {
		AsyncOperationStatus monitor = oneDrive.drive().items("C899E30C041941B5!365211").children()
			.upload("S04E14 - 056",
			"https://content-na.drive.amazonaws.com/cdproxy/templink/xce23y96SZTQ2_1YpW_h6YSJXYRCKVBeQxo69PGjH08E0Xnc3?download=TRUE");
		while (StringUtils.isEmpty(monitor.getId())){
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			monitor = monitor.status();
			System.out.println(monitor);
		}
		System.out.println(oneDrive.drive().items(monitor.getId()).fetch());
	}
	
}
