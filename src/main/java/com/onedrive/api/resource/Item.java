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
package com.onedrive.api.resource;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.onedrive.api.OneDrive;
import com.onedrive.api.resource.facet.Audio;
import com.onedrive.api.resource.facet.Deleted;
import com.onedrive.api.resource.facet.File;
import com.onedrive.api.resource.facet.FileSystemInfo;
import com.onedrive.api.resource.facet.Folder;
import com.onedrive.api.resource.facet.Hashes;
import com.onedrive.api.resource.facet.Image;
import com.onedrive.api.resource.facet.Location;
import com.onedrive.api.resource.facet.Permission;
import com.onedrive.api.resource.facet.Photo;
import com.onedrive.api.resource.facet.Quota;
import com.onedrive.api.resource.facet.SharingLink;
import com.onedrive.api.resource.facet.SpecialFolder;
import com.onedrive.api.resource.facet.Video;
import com.onedrive.api.resource.support.AsyncOperationStatus;
import com.onedrive.api.resource.support.ChangesItemList;
import com.onedrive.api.resource.support.Error;
import com.onedrive.api.resource.support.IdentitySet;
import com.onedrive.api.resource.support.ItemList;
import com.onedrive.api.resource.support.ItemReference;
import com.onedrive.api.resource.support.SearchItemList;
import com.onedrive.api.resource.support.Thumbnail;
import com.onedrive.api.resource.support.ThumbnailSet;
import com.onedrive.api.resource.support.ThumbnailSetList;
import com.onedrive.api.resource.support.UploadSession;

@JsonInclude(Include.NON_NULL)
public class Item extends Resource {
	private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;
	
	private String name;
	private String eTag;
	private String cTag;
	private IdentitySet createdBy;
	private IdentitySet lastModifiedBy;
	private Date createdDateTime;
	private Date lastModifiedDateTime;
	private Long size;
	private ItemReference parentReference;
	private String webUrl;
	private String description;
	
	/* Facets */
	private Folder folder;
	private File file;
	private FileSystemInfo fileSystemInfo;
	private Image image;
	private Photo photo;
	private Audio audio;
	private Video video;
	private Location location;
	private Deleted deleted;
	private Hashes hashes;
	private Permission permission;
	private Quota quota;
	private SharingLink sharingLink;
	private SpecialFolder specialFolder;
	
	/*Instance Attributes*/
	@JsonProperty("@name.conflictBehavior")
	private String conflictBehavior;
	@JsonProperty("@content.downloadUrl")
	private String downloadUrl;
	@JsonProperty("@content.sourceUrl")
	private String sourceUrl;
	@JsonProperty("@odata.context")
	private String context;
	
	@JsonIgnore
	private Drive drive;
	
	@JsonCreator
	public Item(@JacksonInject OneDrive oneDrive) {
		super(oneDrive);
		Assert.notNull(oneDrive, "[oneDrive] is required");
	}
	public Item(OneDrive oneDrive, String id) {
		super(oneDrive);
		setId(id);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String geteTag() {
		return eTag;
	}
	public void seteTag(String eTag) {
		this.eTag = eTag;
	}
	public String getcTag() {
		return cTag;
	}
	public void setcTag(String cTag) {
		this.cTag = cTag;
	}
	public IdentitySet getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(IdentitySet createdBy) {
		this.createdBy = createdBy;
	}
	public IdentitySet getLastModifiedBy() {
		return lastModifiedBy;
	}
	public void setLastModifiedBy(IdentitySet lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}
	public Date getCreatedDateTime() {
		return createdDateTime;
	}
	public void setCreatedDateTime(Date createdDateTime) {
		this.createdDateTime = createdDateTime;
	}
	public Date getLastModifiedDateTime() {
		return lastModifiedDateTime;
	}
	public void setLastModifiedDateTime(Date lastModifiedDateTime) {
		this.lastModifiedDateTime = lastModifiedDateTime;
	}
	public Long getSize() {
		return size;
	}
	public void setSize(Long size) {
		this.size = size;
	}
	public ItemReference getParentReference() {
		return parentReference;
	}
	public void setParentReference(ItemReference parentReference) {
		this.parentReference = parentReference;
	}
	public String getWebUrl() {
		return webUrl;
	}
	public void setWebUrl(String webUrl) {
		this.webUrl = webUrl;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Folder getFolder() {
		return folder;
	}
	public void setFolder(Folder folder) {
		this.folder = folder;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public FileSystemInfo getFileSystemInfo() {
		return fileSystemInfo;
	}
	public void setFileSystemInfo(FileSystemInfo fileSystemInfo) {
		this.fileSystemInfo = fileSystemInfo;
	}
	public Image getImage() {
		return image;
	}
	public void setImage(Image image) {
		this.image = image;
	}
	public Photo getPhoto() {
		return photo;
	}
	public void setPhoto(Photo photo) {
		this.photo = photo;
	}
	public Audio getAudio() {
		return audio;
	}
	public void setAudio(Audio audio) {
		this.audio = audio;
	}
	public Video getVideo() {
		return video;
	}
	public void setVideo(Video video) {
		this.video = video;
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	public Deleted getDeleted() {
		return deleted;
	}
	public void setDeleted(Deleted deleted) {
		this.deleted = deleted;
	}
	public Hashes getHashes() {
		return hashes;
	}
	public void setHashes(Hashes hashes) {
		this.hashes = hashes;
	}
	public Permission getPermission() {
		return permission;
	}
	public void setPermission(Permission permission) {
		this.permission = permission;
	}
	public Quota getQuota() {
		return quota;
	}
	public void setQuota(Quota quota) {
		this.quota = quota;
	}
	public SharingLink getSharingLink() {
		return sharingLink;
	}
	public void setSharingLink(SharingLink sharingLink) {
		this.sharingLink = sharingLink;
	}
	public SpecialFolder getSpecialFolder() {
		return specialFolder;
	}
	public void setSpecialFolder(SpecialFolder specialFolder) {
		this.specialFolder = specialFolder;
	}
	public String getConflictBehavior() {
		return conflictBehavior;
	}
	public void setConflictBehavior(String conflictBehavior) {
		this.conflictBehavior = conflictBehavior;
	}
	public String getDownloadUrl() {
		return downloadUrl;
	}
	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}
	public String getSourceUrl() {
		return sourceUrl;
	}
	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public Drive getDrive() {
		return drive;
	}
	public void setDrive(Drive drive) {
		this.drive = drive;
	}
	protected void initDrive(){
		if (drive == null){
			drive = new Drive(getOneDrive());
		}
	}
	public static String buildItemPath(String itemId){
		return OneDrive.PATH_SEPARATOR+"items"+OneDrive.PATH_SEPARATOR+itemId;
	}
	public String buildItemPath(){
		initDrive();
		return drive.buildDrivePath()+buildItemPath(getId());
	}
	public URI buildItemUri(Map<String,String> urlParams){
		return getOneDrive().getUri(buildItemPath(), urlParams);
	}
	public URI buildItemUri(){
		return buildItemUri(null);
	}
	public String buildActionPath(String action){
		return buildItemPath()+OneDrive.PATH_SEPARATOR+action;
	}
	public URI buildActionUri(String action, Map<String,String> urlParams){
		return getOneDrive().getUri(buildActionPath(action), urlParams);
	}
	public String buildChildrenPath(){
		return buildActionPath("children");
	}
	public URI buildChildrenUri(Map<String,String> urlParams){
		return getOneDrive().getUri(buildChildrenPath(), urlParams);
	}
	public URI buildChildrenUri(){
		return buildChildrenUri(null);
	}
	public Item metadata(){
		return metadata(null);
	}
	public Item metadata(Map<String,String> queryParameters){
		initDrive();
		return getDrive().items(getId(), queryParameters);
	}
	public ItemList children(){
		return children(null);
	}
	public ItemList children(Map<String,String> queryParameters){
		Assert.notNull(getId(), "[this.id] is required");
		return getOneDrive().getRestTemplate().getForObject(buildChildrenUri(queryParameters), ItemList.class);
	}
	public Item createFolder(String folderName){
		return createFolder(folderName, null);
	}
	public Item createFolder(String folderName, String conflictBehavior){
		Assert.notNull(folderName, "[folderName] is required");
		Map<String,Object> data = new HashMap<String, Object>();
		data.put("name", folderName);
		data.put("folder", new Folder());
		if (!StringUtils.isEmpty(conflictBehavior)){
			data.put("@name.conflictBehavior", conflictBehavior);
		}
		ResponseEntity<Item> responseItem = getOneDrive().getRestTemplate().postForEntity(buildChildrenUri(), data, Item.class);
		return responseItem.getBody();
	}
	public Item upload(String fileName, InputStream inputStream){
		return upload(fileName, inputStream, null);
	}
	public Item upload(String fileName, InputStream inputStream, String conflictBehavior){
		Assert.notNull(fileName, "[fileName] is required");
		Assert.notNull(inputStream, "[inputStream] is required");
		Map<String,String> params = new HashMap<String, String>();
		if (!StringUtils.isEmpty(conflictBehavior)){
			params.put("@name.conflictBehavior", conflictBehavior);
		}
		String path = buildChildrenPath() + OneDrive.PATH_SEPARATOR + fileName + OneDrive.PATH_SEPARATOR + "content";
		ResponseEntity<Item> responseItem = getOneDrive().getRestTemplate().exchange(getOneDrive().getUri(path, params), HttpMethod.PUT, new HttpEntity<InputStreamResource>(new InputStreamResource(inputStream)) , Item.class);
		return responseItem.getBody();
	}
	
	public Item upload(Item child, InputStream inputStream){
		Assert.notNull(child, "[child] is required");
		Assert.notNull(inputStream, "[inputStream] is required");
		child.setSourceUrl("cid:content");
		
		MultiValueMap<String, Object> parts = new LinkedMultiValueMap<String, Object>();
		parts.add("metadata", child);
		parts.add("content", new InputStreamResource(inputStream));
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
	    headers.add("Content-Type", "multipart/related");
		return getOneDrive().getRestTemplate().postForObject(buildChildrenUri(), new HttpEntity<MultiValueMap<String, Object>>(parts, headers), Item.class);
	}
	
	public UploadSession uploadSesion(Item child){
		Assert.notNull(child, "[child] is required");
		Assert.notNull(child.getName(), "[child.name] is required");
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		if (!StringUtils.isEmpty(child.geteTag())){
			headers.add("if-match", child.geteTag());
		}
		String path = buildItemPath()+":"+OneDrive.PATH_SEPARATOR+child.getName()+":/upload.createSession";
		UploadSession session = getOneDrive().getRestTemplate().postForObject(getOneDrive().getUri(path, null), new HttpEntity<Map<String, Item>>(Collections.singletonMap("item", child), headers), UploadSession.class);
		return session; 
	}
	public AsyncOperationStatus uploadFromUrl(String url, String name){
		Assert.notNull(url, "[url] is required");
		Assert.notNull(name, "[name] is required");
		Item item = new Item(getOneDrive());
		item.setName(name);
		item.setSourceUrl(url);
		item.setFile(new File());
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		headers.add("Prefer", "respond-async");
		ResponseEntity<AsyncOperationStatus> response = getOneDrive().getRestTemplate().exchange(buildChildrenUri(), HttpMethod.POST, new HttpEntity<Item>(item, headers), AsyncOperationStatus.class);
		AsyncOperationStatus status = response.getBody();
		if (status == null){
			status = new AsyncOperationStatus(getOneDrive());
		}
		status.setSourceUrl(response.getHeaders().getLocation().toString());
		return status;
	}
	public Optional<Error> delete(){
		Assert.notNull(getId(), "[this.id] is required");
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		if (!StringUtils.isEmpty(eTag)){
			headers.add("if-match", eTag);
		}
		ResponseEntity<Resource> response = getOneDrive().getRestTemplate().exchange(buildItemUri(), HttpMethod.DELETE, null, Resource.class);
		return response.getBody()!=null?Optional.ofNullable(response.getBody().getError()):Optional.empty();
	}
	public Item move(String parentPath){
		Assert.notNull(parentPath, "[parentPath] is required");
		Item request = new Item(getOneDrive());
		request.setName(getName());
		request.setParentReference(new ItemReference());
		request.getParentReference().setPath(Drive.DEFAULT_DRIVE_PATH + OneDrive.PATH_SEPARATOR + "root" + ":" + parentPath);
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		headers.add("X-HTTP-Method-Override", HttpMethod.PATCH.name());
		ResponseEntity<Item> response = getOneDrive().getRestTemplate().exchange(buildItemUri(), HttpMethod.POST, new HttpEntity<Item>(request,headers), Item.class);
		return response.getBody(); 
	}
	public Item update(){
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		headers.add("X-HTTP-Method-Override", HttpMethod.PATCH.name());
		if (!StringUtils.isEmpty(geteTag())){
			headers.add("if-match", geteTag());
		}
		ResponseEntity<Item> response = getOneDrive().getRestTemplate().exchange(buildItemUri(), HttpMethod.POST, new HttpEntity<Item>(this,headers), Item.class);
		return response.getBody(); 
	}
	private Item copy(Item parentItem, String parentPath, String newName){
		ItemReference parentReference = new ItemReference();
		if (parentItem != null){
			parentReference.setId(parentItem.getId());
		} else {
			parentReference.setPath(Drive.DEFAULT_DRIVE_PATH + OneDrive.PATH_SEPARATOR + "root" + ":" + parentPath);	
		}
		Item request = new Item(getOneDrive());
		request.setName(newName);
		request.setParentReference(parentReference);
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		headers.add("Prefer", "respond-async");
		ResponseEntity<AsyncOperationStatus> response = getOneDrive().getRestTemplate().exchange(buildActionUri("action.copy", null), HttpMethod.POST, new HttpEntity<Item>(request, headers), AsyncOperationStatus.class);
		AsyncOperationStatus status = response.getBody();
		if (status == null){
			status = new AsyncOperationStatus(getOneDrive());
		}
		status.setSourceUrl(response.getHeaders().getLocation().toString());
		return status;
	}
	public Item copy(String parentPath, String newName){
		Assert.notNull(parentPath, "[parentPath] is required");
		return copy(null, parentPath, newName);
	}
	public Item copy(String parentPath){
		return copy(parentPath, null);
	}
	public Item copy(Item parentItem, String newName){
		Assert.notNull(parentItem, "[parentItem] is required");
		Assert.notNull(parentItem.getId(), "[parentItem.id] is required");
		return copy(parentItem, null, newName);
	}
	public Item copy(Item parentItem){
		return copy(parentItem, null);
	}
	public Resource download(OutputStream outputStream){
		return download(outputStream, null);
	}
	public Resource download(OutputStream outputStream, String rangeHeader){
		return getOneDrive().getRestTemplate().execute(buildActionUri("content", null), HttpMethod.GET,
			new RequestCallback() {
				@Override
				public void doWithRequest(ClientHttpRequest request) throws IOException {
					if (rangeHeader != null){
						request.getHeaders().add("Range", rangeHeader);
					}
				}
			}, new ResponseExtractor<Resource>() {
			@Override
			public Resource extractData(ClientHttpResponse response) throws IOException {
				Resource resource = null;
				if (response.getStatusCode().value() >= 400){
					HttpMessageConverterExtractor<Resource> extractor = new HttpMessageConverterExtractor<Resource>(Item.class, getOneDrive().getRestTemplate().getMessageConverters());
					resource = extractor.extractData(response);
				} else {
					copyStream(response.getBody(), outputStream);
					resource = new Resource(getOneDrive());
				}
				resource.setContentRange(response.getHeaders().getFirst(Resource.CONTENT_RANGE_HEADER));
				return resource;
			}
		});
	}
	private void copyStream(InputStream input, OutputStream output)
            throws IOException {
        int n = 0;
        byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
        while ((n = input.read(buffer)) != -1 ) {
            output.write(buffer, 0, n);
        }
    }
	public SearchItemList search(String query){
		return search(query, null);
	}
	public SearchItemList search(String query, Map<String,String> queryParameters){
		Assert.notNull(getId(), "[this.id] is required");
		if (query != null){
			if (queryParameters == null){
				queryParameters = new HashMap<String,String>();
			}
			queryParameters.put("q", query);
		}
		return getOneDrive().getRestTemplate().getForObject(buildActionUri("view.search", queryParameters), SearchItemList.class);
	}
	public ChangesItemList changes(){
		return changes(null, 0);
	}
	public ChangesItemList changes(String token, long top){
		Assert.notNull(getId(), "[this.id] is required");
		Map<String,String> queryParameters = new HashMap<String,String>();
		if (token != null){
			queryParameters.put("token", token);
		}
		if (top > 0){
			queryParameters.put("top", top+"");
		}
		return getOneDrive().getRestTemplate().getForObject(buildActionUri("view.delta", queryParameters), ChangesItemList.class);
	}
	public ThumbnailSetList thumbnails(){
		return thumbnails(null);
	}
	public ThumbnailSetList thumbnails(Map<String,String> queryParameters){
		Assert.notNull(getId(), "[this.id] is required");
		ThumbnailSetList list = getOneDrive().getRestTemplate().getForObject(buildActionUri("thumbnails", queryParameters), ThumbnailSetList.class);
		if (list != null && list.getValue() != null){
			for  (ThumbnailSet ts : list.getValue()){
				ts.setItem(this);
			}
		}
		return list;
	}
	public Thumbnail thumbnail(String thumbId, String size){
		Assert.notNull(getId(), "[this.id] is required");
		Assert.notNull(thumbId, "[thumbId] is required");
		Assert.notNull(size, "[size] is required");
		return getOneDrive().getRestTemplate().getForObject(buildActionUri("thumbnails"+OneDrive.PATH_SEPARATOR+thumbId+OneDrive.PATH_SEPARATOR+size, null), Thumbnail.class);
	}
	public Resource thumbnail(String thumbId, String size, OutputStream outputStream){
		Assert.notNull(getId(), "[this.id] is required");
		Assert.notNull(thumbId, "[thumbId] is required");
		Assert.notNull(size, "[size] is required");
		return getOneDrive().getRestTemplate().execute(buildActionUri("thumbnails"+OneDrive.PATH_SEPARATOR+thumbId+OneDrive.PATH_SEPARATOR+size+OneDrive.PATH_SEPARATOR+"content", null), HttpMethod.GET,
			null, 
			new ResponseExtractor<Resource>() {
				@Override
				public Resource extractData(ClientHttpResponse response) throws IOException {
					Resource resource = null;
					if (response.getStatusCode().value() >= 400){
						HttpMessageConverterExtractor<Resource> extractor = new HttpMessageConverterExtractor<Resource>(Item.class, getOneDrive().getRestTemplate().getMessageConverters());
						resource = extractor.extractData(response);
					} else {
						copyStream(response.getBody(), outputStream);
						resource = new Resource(getOneDrive());
					}
					return resource;
				}
			}
		);
	}
	public void thumbnailUpload(InputStream inputStream){
		Assert.notNull(getId(), "[this.id] is required");
		Assert.notNull(inputStream, "[inputStream] is required");
		getOneDrive().getRestTemplate().exchange(buildActionUri("thumbnails"+OneDrive.PATH_SEPARATOR+"0"+OneDrive.PATH_SEPARATOR+"source"+OneDrive.PATH_SEPARATOR+"content", null), HttpMethod.PUT, new HttpEntity<InputStreamResource>(new InputStreamResource(inputStream)), Object.class);
	}
	public Permission createLink(String type){
		Assert.notNull(type, "[type] is required");
		Map<String,String> request = new HashMap<String,String>();
		request.put("type", type);
		return getOneDrive().getRestTemplate().postForObject(buildActionUri("action.createLink", null), request, Permission.class);
	}
}
