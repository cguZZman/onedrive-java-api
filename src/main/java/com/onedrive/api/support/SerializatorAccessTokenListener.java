/*******************************************************************************
 * OneDrive Java API
 * Copyright (C) 2015 - Carlos Guzman
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Created on Aug 1, 2015
 * @author: Carlos Guzman (cguZZman) carlosguzmang@hotmail.com
 *******************************************************************************/
package com.onedrive.api.support;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.util.HashMap;

import org.springframework.util.SerializationUtils;

import com.onedrive.api.OneDrive;

public class SerializatorAccessTokenListener implements AccessTokenListener {
	protected static final String EMPTY_STRING = "";
	public static final String ACCESS_TOKEN_EXTENSION = ".atk";
	protected static final String DEFAULT_ACCESS_TOKEN_FILE_NAME = "default";
	private static final String DEFAULT_APPLICATION_FOLDER = System.getProperty("user.home", EMPTY_STRING)
			+ File.separator + ".onedrive";

	private HashMap<String, String> tokens = new HashMap<String, String>();
	
	protected String getFileName(OneDrive reference){
		return DEFAULT_ACCESS_TOKEN_FILE_NAME + ACCESS_TOKEN_EXTENSION;
	}
	public String getApplicationFolder(){
		return DEFAULT_APPLICATION_FOLDER;
	}
	public void onAccessTokenReceived(OneDrive reference, AccessToken accessToken) {
		File file = new File(getApplicationFolder(), getFileName(reference));
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
		File file = new File(getApplicationFolder(), getFileName(reference));
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
