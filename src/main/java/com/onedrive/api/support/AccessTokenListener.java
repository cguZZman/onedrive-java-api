package com.onedrive.api.support;

import com.onedrive.api.OneDrive;

public interface AccessTokenListener {
	void onAccessTokenReceived(OneDrive reference, AccessToken accessToken);
	AccessToken onAccessTokenRequired(OneDrive reference);
}
