package com.framework.base.po;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

/**
 * TroleTpermission entity. @author MyEclipse Persistence Tools
 */

@Entity
public class TroleTpermission implements java.io.Serializable {

	private String troleTpermissionid;
	private String troleid;
	private String tpermissionid;
	private String serialnumber ;
	
	@Id 
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid2") 
	public String getTroleTpermissionid() {
		return troleTpermissionid;
	}
	public void setTroleTpermissionid(String troleTpermissionid) {
		this.troleTpermissionid = troleTpermissionid;
	}
	public String getTroleid() {
		return troleid;
	}
	public void setTroleid(String troleid) {
		this.troleid = troleid;
	}
	public String getTpermissionid() {
		return tpermissionid;
	}
	public void setTpermissionid(String tpermissionid) {
		this.tpermissionid = tpermissionid;
	}
	public String getSerialnumber() {
		return serialnumber;
	}
	public void setSerialnumber(String serialnumber) {
		this.serialnumber = serialnumber;
	}

	
}