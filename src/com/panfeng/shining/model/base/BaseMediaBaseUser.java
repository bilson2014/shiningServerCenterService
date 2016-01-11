package com.panfeng.shining.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseMediaBaseUser<M extends BaseMediaBaseUser<M>> extends Model<M> implements IBean {

	public void setId(java.lang.Integer id) {
		set("id", id);
	}

	public java.lang.Integer getId() {
		return get("id");
	}

	public void setUserId(java.lang.Integer userId) {
		set("user_id", userId);
	}

	public java.lang.Integer getUserId() {
		return get("user_id");
	}

	public void setUploadTime(java.lang.String uploadTime) {
		set("upload_time", uploadTime);
	}

	public java.lang.String getUploadTime() {
		return get("upload_time");
	}

	public void setVideoName(java.lang.String videoName) {
		set("video_name", videoName);
	}

	public java.lang.String getVideoName() {
		return get("video_name");
	}

	public void setVideoState(java.lang.Long videoState) {
		set("video_state", videoState);
	}

	public java.lang.Long getVideoState() {
		return get("video_state");
	}

	public void setMbMd5(java.lang.String mbMd5) {
		set("mb_MD5", mbMd5);
	}

	public java.lang.String getMbMd5() {
		return get("mb_MD5");
	}

}