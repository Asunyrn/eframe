package com.framework.base.po;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.hibernate.annotations.GenericGenerator;

/**
 * Tpermission entity. @author MyEclipse Persistence Tools
 */
@Entity
public class Tpermission implements java.io.Serializable {


	private String tpermissionid;
	private String permissionname;
	private Long   action;
	private String url;
	private String openicon;
	private String closeicon;
	private Long   isleaf;
	private String parentid;
	private Long   orders;
	private Long   levels;
	private String opentype;
	private String serialnumber ;
	
	@Id 
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid2") 
	public String getTpermissionid() {
		return tpermissionid;
	}
	public void setTpermissionid(String tpermissionid) {
		this.tpermissionid = tpermissionid;
	}
	public String getPermissionname() {
		return permissionname;
	}
	public void setPermissionname(String permissionname) {
		this.permissionname = permissionname;
	}
	public Long getAction() {
		return action;
	}
	public void setAction(Long action) {
		this.action = action;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getOpenicon() {
		return openicon;
	}
	public void setOpenicon(String openicon) {
		this.openicon = openicon;
	}
	public String getCloseicon() {
		return closeicon;
	}
	public void setCloseicon(String closeicon) {
		this.closeicon = closeicon;
	}
	public Long getIsleaf() {
		return isleaf;
	}
	public void setIsleaf(Long isleaf) {
		this.isleaf = isleaf;
	}
	public String getParentid() {
		return parentid;
	}
	public void setParentid(String parentid) {
		this.parentid = parentid;
	}
	public Long getOrders() {
		return orders;
	}
	public void setOrders(Long orders) {
		this.orders = orders;
	}
	public Long getLevels() {
		return levels;
	}
	public void setLevels(Long levels) {
		this.levels = levels;
	}
	public String getOpentype() {
		return opentype;
	}
	public void setOpentype(String opentype) {
		this.opentype = opentype;
	}
	public String getSerialnumber() {
		return serialnumber;
	}
	public void setSerialnumber(String serialnumber) {
		this.serialnumber = serialnumber;
	}
	
	

}