package com.panfeng.shining.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BasePurchaseHistory<M extends BasePurchaseHistory<M>> extends Model<M> implements IBean {

	public void setPhId(java.lang.Long phId) {
		set("ph_id", phId);
	}

	public java.lang.Long getPhId() {
		return get("ph_id");
	}

	public void setPhUid(java.lang.Long phUid) {
		set("ph_uid", phUid);
	}

	public java.lang.Long getPhUid() {
		return get("ph_uid");
	}

	public void setPhType(java.lang.Long phType) {
		set("ph_type", phType);
	}

	public java.lang.Long getPhType() {
		return get("ph_type");
	}

	public void setPhTime(java.util.Date phTime) {
		set("ph_time", phTime);
	}

	public java.util.Date getPhTime() {
		return get("ph_time");
	}

	public void setPhState(java.lang.Long phState) {
		set("ph_state", phState);
	}

	public java.lang.Long getPhState() {
		return get("ph_state");
	}

	public void setPhBuyId(java.lang.Long phBuyId) {
		set("ph_buy_id", phBuyId);
	}

	public java.lang.Long getPhBuyId() {
		return get("ph_buy_id");
	}

}
