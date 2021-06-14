package com.framework.base.po;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.hibernate.annotations.GenericGenerator;

/**
 * TloginTrole entity. @author MyEclipse Persistence Tools
 */
@Entity
public class TloginTrole implements java.io.Serializable {

	private String tlogintroleid;
	private String tloginid;
	private String troleid;
	private String serialnumber ;
	
	@Id 
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid2") 
	public String getTlogintroleid() {
		return tlogintroleid;
	}
	public void setTlogintroleid(String tlogintroleid) {
		this.tlogintroleid = tlogintroleid;
	}
	public String getTloginid() {
		return tloginid;
	}
	public void setTloginid(String tloginid) {
		this.tloginid = tloginid;
	}
	public String getTroleid() {
		return troleid;
	}
	public void setTroleid(String troleid) {
		this.troleid = troleid;
	}
	public String getSerialnumber() {
		return serialnumber;
	}
	public void setSerialnumber(String serialnumber) {
		this.serialnumber = serialnumber;
	}
	
}