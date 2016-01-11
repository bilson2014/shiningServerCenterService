package com.panfeng.shining.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseAudioBaseList<M extends BaseAudioBaseList<M>> extends Model<M> implements IBean {

	public void setId(java.lang.Integer id) {
		set("id", id);
	}

	public java.lang.Integer getId() {
		return get("id");
	}

	public void setAudioName(java.lang.String audioName) {
		set("audio_name", audioName);
	}

	public java.lang.String getAudioName() {
		return get("audio_name");
	}

	public void setAudioUploadTime(java.lang.String audioUploadTime) {
		set("audio_upload_time", audioUploadTime);
	}

	public java.lang.String getAudioUploadTime() {
		return get("audio_upload_time");
	}

	public void setAudioAuthor(java.lang.String audioAuthor) {
		set("audio_author", audioAuthor);
	}

	public java.lang.String getAudioAuthor() {
		return get("audio_author");
	}

	public void setAudioContext(java.lang.String audioContext) {
		set("audio_context", audioContext);
	}

	public java.lang.String getAudioContext() {
		return get("audio_context");
	}

	public void setAudioAudioname(java.lang.String audioAudioname) {
		set("audio_audioname", audioAudioname);
	}

	public java.lang.String getAudioAudioname() {
		return get("audio_audioname");
	}

	public void setAudioSortId(java.lang.Long audioSortId) {
		set("audio_sort_id", audioSortId);
	}

	public java.lang.Long getAudioSortId() {
		return get("audio_sort_id");
	}

	public void setAudioState(java.lang.Integer audioState) {
		set("audio_state", audioState);
	}

	public java.lang.Integer getAudioState() {
		return get("audio_state");
	}

	public void setMbMd5(java.lang.String mbMd5) {
		set("mb_MD5", mbMd5);
	}

	public java.lang.String getMbMd5() {
		return get("mb_MD5");
	}

}
