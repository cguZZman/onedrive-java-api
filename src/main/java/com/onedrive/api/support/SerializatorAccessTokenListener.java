package com.onedrive.api.support;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.util.HashMap;

import org.springframework.util.SerializationUtils;

import com.onedrive.api.OneDrive;

public class SerializatorAccessTokenListener implements AccessTokenListener {
	private static final String EMPTY_STRING = "";
	private static final String ACCESS_TOKEN_EXTENSION = ".atk";
	private static final String DEFAULT_ACCESS_TOKEN_FILE_NAME = "default";
	private static final String DEFAULT_APPLICATION_FOLDER = System.getProperty("user.home", EMPTY_STRING)
			+ File.separator + ".onedrive";

	private HashMap<String, String> tokens = new HashMap<String, String>();
	
	private String getFileName(OneDrive reference){
		return (reference.getId() != null?reference.getId():DEFAULT_ACCESS_TOKEN_FILE_NAME) + ACCESS_TOKEN_EXTENSION;
	}
	
	public void onAccessTokenReceived(OneDrive reference, AccessToken accessToken) {
		File file = new File(DEFAULT_APPLICATION_FOLDER, getFileName(reference));
		String existing = tokens.get(file.getName());
		if (existing == null || !existing.equals(accessToken.getAccessToken())){
			try {
				Files.createDirectories(file.getParentFile().toPath());
				FileOutputStream fos = new FileOutputStream(file, false);
				fos.write(SerializationUtils.serialize(accessToken));
				fos.close();
				tokens.put(file.getName(), accessToken.getAccessToken());
				System.out.println("Access Token Persisted: " + file.getName());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public AccessToken onAccessTokenRequired(OneDrive reference) {
		File file = new File(DEFAULT_APPLICATION_FOLDER, getFileName(reference));
		if (file.exists()){
			try {
				FileInputStream fis = new FileInputStream(file);
				byte[] b = new byte[(int) file.length()];
				fis.read(b, 0, b.length);
				fis.close();
				AccessToken accessToken = (AccessToken) SerializationUtils.deserialize(b);
				tokens.put(file.getName(), accessToken.getAccessToken());
				System.out.println("Access Token Retrieved: " + file.getName());
				return accessToken;
			} catch (Exception e){
				e.printStackTrace();
			}
		}
		return null;
	}
}
