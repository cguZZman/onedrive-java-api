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
