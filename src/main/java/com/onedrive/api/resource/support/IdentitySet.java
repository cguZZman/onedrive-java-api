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
package com.onedrive.api.resource.support;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class IdentitySet {
	private Identity user;
	private Identity application;
	private Identity device;
	public Identity getUser() {
		return user;
	}
	public void setUser(Identity user) {
		this.user = user;
	}
	public Identity getApplication() {
		return application;
	}
	public void setApplication(Identity application) {
		this.application = application;
	}
	public Identity getDevice() {
		return device;
	}
	public void setDevice(Identity device) {
		this.device = device;
	}
	@Override
	public String toString() {
		return "IdentitySet [user=" + user + ", application=" + application + ", device=" + device + "]";
	}
}
