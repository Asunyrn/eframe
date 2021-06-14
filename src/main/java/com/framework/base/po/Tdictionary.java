package com.framework.base.po;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Tdictionary implements java.io.Serializable {
	private Long tdictionaryid;
	private String type;
	private String code;
	private String value;
	private String serialnumber ;
	
	@Id
	@javax.persistence.SequenceGenerator(name="SEQ_TDICTIONARYID",sequenceName="SEQ_TDICTIONARYID",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_TDICTIONARYID")
	public Long getTdictionaryid() {
		return tdictionaryid;
	}
	public void setTdictionaryid(Long tdictionaryid) {
		this.tdictionaryid = tdictionaryid;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getSerialnumber() {
		return serialnumber;
	}
	public void setSerialnumber(String serialnumber) {
		this.serialnumber = serialnumber;
	}

}
