package com.panfeng.shining.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseMessage<M extends BaseMessage<M>> extends Model<M> implements IBean {

	public void setMgId(java.lang.Long mgId) {
		set("mg_id", mgId);
	}

	public java.lang.Long getMgId() {
		return get("mg_id");
	}

	public void setMgType(java.lang.Long mgType) {
		set("mg_type", mgType);
	}

	public java.lang.Long getMgType() {
		return get("mg_type");
	}

	public void setMgTime(java.util.Date mgTime) {
		set("mg_time", mgTime);
	}

	public java.util.Date getMgTime() {
		return get("mg_time");
	}

	public void setMgValue(java.lang.String mgValue) {
		set("mg_value", mgValue);
	}

	public java.lang.String getMgValue() {
		return get("mg_value");
	}

	public void setMgUid(java.lang.String mgUid) {
		set("mg_uid", mgUid);
	}

	public java.lang.String getMgUid() {
		return get("mg_uid");
	}

	public void setMgState(java.lang.Integer mgState) {
		set("mg_state", mgState);
	}

	public java.lang.Integer getMgState() {
		return get("mg_state");
	}

}