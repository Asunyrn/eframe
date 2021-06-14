package com.framework.base.po;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.hibernate.annotations.GenericGenerator;

/**
 * Tlogin entity. @author MyEclipse Persistence Tools
 */
@Entity
public class Tlogin implements java.io.Serializable {

	private String tloginid;
	private String loginname;
	private String password;
	private int status;
	private String username;
	private Date logindate;
	private Long ifonline;
	private String registrationuser;
	private Date registrationtime;
	private String serialnumber ;

	@Id 
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid2") 
	public String getTloginid() {
		return tloginid;
	}
	
	
	public void setTloginid(String tloginid) {
		this.tloginid = tloginid;
	}



	public String getLoginname() {
		return this.loginname;
	}




	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Long getIfonline() {
		return ifonline;
	}

	public void setIfonline(Long ifonline) {
		this.ifonline = ifonline;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getLogindate() {
		return this.logindate;
	}

	public void setLogindate(Date logindate) {
		this.logindate = logindate;
	}

	public String getRegistrationuser() {
		return registrationuser;
	}

	public void setRegistrationuser(String registrationuser) {
		this.registrationuser = registrationuser;
	}

	public Date getRegistrationtime() {
		return registrationtime;
	}

	public void setRegistrationtime(Date registrationtime) {
		this.registrationtime = registrationtime;
	}

	public String getSerialnumber() {
		return serialnumber;
	}

	public void setSerialnumber(String serialnumber) {
		this.serialnumber = serialnumber;
	}

}