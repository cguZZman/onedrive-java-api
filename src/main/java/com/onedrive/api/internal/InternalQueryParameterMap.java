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
package com.onedrive.api.internal;

import java.util.Arrays;
import java.util.Map;

import org.springframework.util.LinkedMultiValueMap;

public class InternalQueryParameterMap extends LinkedMultiValueMap<String, String> {
	private static final long serialVersionUID = 4838283701938990030L;
	public InternalQueryParameterMap(Map<String,String> params){
		if (params != null) {
			for (Entry<String, String> entry : params.entrySet()){
				put(entry.getKey(), Arrays.asList(entry.getValue()));
			}
		}
	}
}
