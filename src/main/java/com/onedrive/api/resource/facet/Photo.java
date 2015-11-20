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
package com.onedrive.api.resource.facet;

import java.util.Date;

public class Photo {
	private Date takenDateTime;
	private String cameraMake;
	private String cameraModel;
	private Long fNumber;
	private Long exposureDenominator;
	private Long exposureNumerator;
	private Long focalLength;
	private Long iso;
	public Date getTakenDateTime() {
		return takenDateTime;
	}
	public void setTakenDateTime(Date takenDateTime) {
		this.takenDateTime = takenDateTime;
	}
	public String getCameraMake() {
		return cameraMake;
	}
	public void setCameraMake(String cameraMake) {
		this.cameraMake = cameraMake;
	}
	public String getCameraModel() {
		return cameraModel;
	}
	public void setCameraModel(String cameraModel) {
		this.cameraModel = cameraModel;
	}
	public Long getfNumber() {
		return fNumber;
	}
	public void setfNumber(Long fNumber) {
		this.fNumber = fNumber;
	}
	public Long getExposureDenominator() {
		return exposureDenominator;
	}
	public void setExposureDenominator(Long exposureDenominator) {
		this.exposureDenominator = exposureDenominator;
	}
	public Long getExposureNumerator() {
		return exposureNumerator;
	}
	public void setExposureNumerator(Long exposureNumerator) {
		this.exposureNumerator = exposureNumerator;
	}
	public Long getFocalLength() {
		return focalLength;
	}
	public void setFocalLength(Long focalLength) {
		this.focalLength = focalLength;
	}
	public Long getIso() {
		return iso;
	}
	public void setIso(Long iso) {
		this.iso = iso;
	}
}
