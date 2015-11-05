package com.onedrive.api.resource;

import java.util.Map;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.onedrive.api.OneDrive;
import com.onedrive.api.resource.facet.Quota;
import com.onedrive.api.resource.support.IdentitySet;

@JsonInclude(Include.NON_NULL)
public class Drive extends Resource {
	public static final String DRIVE_TYPE_PERSONAL = "personal";
	public static final String DRIVE_TYPE_BUSINESS = "business";
	
	public static final String DEFAULT_DRIVE_PATH = "/drive";
	public static final String DEFAULT_DRIVE_ID = "me";
	public static final String DRIVES_PATH = "/drives";
	
	private String id;
	private String driveType;
	private IdentitySet owner;
	private Quota quota;
	@JsonProperty("@odata.context")
	private String dataContext;
	
	@JsonCreator
	public Drive(@JacksonInject OneDrive oneDrive) {
		super(oneDrive);
		Assert.notNull(oneDrive, "[oneDrive] is required");
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDriveType() {
		return driveType;
	}
	public void setDriveType(String driveType) {
		this.driveType = driveType;
	}
	public IdentitySet getOwner() {
		return owner;
	}
	public void setOwner(IdentitySet owner) {
		this.owner = owner;
	}
	public Quota getQuota() {
		return quota;
	}
	public void setQuota(Quota quota) {
		this.quota = quota;
	}
	public String getDataContext() {
		return dataContext;
	}
	public void setDataContext(String dataContext) {
		this.dataContext = dataContext;
	}
	public static String buildDrivePath(String driveId){
		return DRIVES_PATH + OneDrive.PATH_SEPARATOR + (StringUtils.isEmpty(driveId)?DEFAULT_DRIVE_ID:driveId);
	}
	public String buildDrivePath(){
		return buildDrivePath(id);
	}
	private Item itemInternal(String path, Map<String,String> queryParameters){
		if (path.charAt(0) != OneDrive.PATH_SEPARATOR.charAt(0)){
			path = OneDrive.PATH_SEPARATOR + path;
		}
		Item item = getOneDrive().getRestTemplate().getForObject(getOneDrive().getUri(buildDrivePath()+path, queryParameters), Item.class);
		item.setDrive(this);
		return item;
	}
	
	public Drive metadata(){
		return metadata(null);
	}
	public Drive metadata(Map<String,String> queryParameters){
		return getOneDrive().drives(StringUtils.isEmpty(id)?DEFAULT_DRIVE_ID:id, queryParameters);
	}
	public Item root(){
		return root((Map<String,String>) null);
	}
	public Item root(Map<String,String> queryParameters){
		return itemInternal("root", queryParameters);
	}
	public Item root(String itemPath){
		return root(itemPath, null);
	}
	public Item root(String itemPath, Map<String,String> queryParameters){
		Assert.notNull(itemPath, "[itemPath] is required");
		return itemInternal("root:"+itemPath+":", queryParameters);
	}
	public Item specialFolder(String name){
		return specialFolder(name, null);
	}
	public Item specialFolder(String name, Map<String,String> queryParameters){
		Assert.notNull(name, "[name] is required");
		return itemInternal("special"+OneDrive.PATH_SEPARATOR+name, queryParameters);
	}
	public Item items(String itemId){
		return items(itemId, null);
	}
	public Item items(String itemId, Map<String,String> queryParameters){
		Assert.notNull(itemId, "[itemId] is required");
		return itemInternal(Item.buildItemPath(itemId), queryParameters);
	}
	@Override
	public String toString() {
		return "Drive [id=" + id + ", driveType=" + driveType + ", owner=" + owner + ", quota=" + quota
				+ ", dataContext=" + dataContext + "]";
	}
}
