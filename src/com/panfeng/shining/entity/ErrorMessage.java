package com.panfeng.shining.entity;

import java.util.Date;

public class ErrorMessage {

	private String errorName;
	private String Message;
	private Date time;
	private int level;
	private String ip;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getErrorName() {
		return errorName;
	}

	public void setErrorName(String errorName) {
		this.errorName = errorName;
	}

	public String getMessage() {
		return Message;
	}

	public void setMessage(String message) {
		Message = message;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public ErrorMessage(String errorName, String message, Date time, int level,
			String ip) {
		super();
		this.errorName = errorName;
		Message = message;
		this.time = time;
		this.level = level;
		this.ip = ip;
	}

	public ErrorMessage() {
		super();
	}

}
