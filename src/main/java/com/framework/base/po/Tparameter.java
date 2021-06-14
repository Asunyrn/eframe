package com.framework.base.po;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.hibernate.annotations.GenericGenerator;

/**
 * Tparameter entity. @author MyEclipse Persistence Tools
 */
@Entity
public class Tparameter implements java.io.Serializable {
	
	private String tparameterid;
	private String area;
	private String type;
	private String value;
	private String remarks;
	private String serialnumber;
	
	@Id 
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid2") 
	public String getTparameterid() {
		return tparameterid;
	}
	public void setTparameterid(String tparameterid) {
		this.tparameterid = tparameterid;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getSerialnumber() {
		return serialnumber;
	}
	public void setSerialnumber(String serialnumber) {
		this.serialnumber = serialnumber;
	}

}
