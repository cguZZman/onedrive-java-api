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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Audio {
	private String album;
	private String albumArtist;
	private String artist;
	private String bitrate;
	private String composers;
	private String copyright;
	private Long disc;
	private Long discCount;
	private Long duration;
	private String genre;
	private Boolean hasDrm;
	private Boolean isVariableBitrate;
	private String title;
	private Long track;
	private Long trackCount;
	private Long year;
	public String getAlbum() {
		return album;
	}
	public void setAlbum(String album) {
		this.album = album;
	}
	public String getAlbumArtist() {
		return albumArtist;
	}
	public void setAlbumArtist(String albumArtist) {
		this.albumArtist = albumArtist;
	}
	public String getArtist() {
		return artist;
	}
	public void setArtist(String artist) {
		this.artist = artist;
	}
	public String getBitrate() {
		return bitrate;
	}
	public void setBitrate(String bitrate) {
		this.bitrate = bitrate;
	}
	public String getComposers() {
		return composers;
	}
	public void setComposers(String composers) {
		this.composers = composers;
	}
	public String getCopyright() {
		return copyright;
	}
	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}
	public Long getDisc() {
		return disc;
	}
	public void setDisc(Long disc) {
		this.disc = disc;
	}
	public Long getDiscCount() {
		return discCount;
	}
	public void setDiscCount(Long discCount) {
		this.discCount = discCount;
	}
	public Long getDuration() {
		return duration;
	}
	public void setDuration(Long duration) {
		this.duration = duration;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public Boolean getHasDrm() {
		return hasDrm;
	}
	public void setHasDrm(Boolean hasDrm) {
		this.hasDrm = hasDrm;
	}
	public Boolean getIsVariableBitrate() {
		return isVariableBitrate;
	}
	public void setIsVariableBitrate(Boolean isVariableBitrate) {
		this.isVariableBitrate = isVariableBitrate;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Long getTrack() {
		return track;
	}
	public void setTrack(Long track) {
		this.track = track;
	}
	public Long getTrackCount() {
		return trackCount;
	}
	public void setTrackCount(Long trackCount) {
		this.trackCount = trackCount;
	}
	public Long getYear() {
		return year;
	}
	public void setYear(Long year) {
		this.year = year;
	}
}
