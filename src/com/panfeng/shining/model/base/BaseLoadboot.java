package com.panfeng.shining.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseLoadboot<M extends BaseLoadboot<M>> extends Model<M> implements IBean {

	public void setLeadId(java.lang.Integer leadId) {
		set("lead_id", leadId);
	}

	public java.lang.Integer getLeadId() {
		return get("lead_id");
	}

	public void setLeadNum(java.lang.Integer leadNum) {
		set("lead_num", leadNum);
	}

	public java.lang.Integer getLeadNum() {
		return get("lead_num");
	}

	public void setLeadStep(java.lang.String leadStep) {
		set("lead_step", leadStep);
	}

	public java.lang.String getLeadStep() {
		return get("lead_step");
	}

	public void setLeadIntentFirst(java.lang.String leadIntentFirst) {
		set("lead_intent_first", leadIntentFirst);
	}

	public java.lang.String getLeadIntentFirst() {
		return get("lead_intent_first");
	}

	public void setLeadIntentLast(java.lang.String leadIntentLast) {
		set("lead_intent_last", leadIntentLast);
	}

	public java.lang.String getLeadIntentLast() {
		return get("lead_intent_last");
	}

	public void setLeadIntentWord(java.lang.String leadIntentWord) {
		set("lead_intent_word", leadIntentWord);
	}

	public java.lang.String getLeadIntentWord() {
		return get("lead_intent_word");
	}

	public void setLeadVideourl(java.lang.String leadVideourl) {
		set("lead_videourl", leadVideourl);
	}

	public java.lang.String getLeadVideourl() {
		return get("lead_videourl");
	}

	public void setLeadOs(java.lang.String leadOs) {
		set("lead_os", leadOs);
	}

	public java.lang.String getLeadOs() {
		return get("lead_os");
	}

	public void setLeadBrandDefault(java.lang.String leadBrandDefault) {
		set("lead_brand_default", leadBrandDefault);
	}

	public java.lang.String getLeadBrandDefault() {
		return get("lead_brand_default");
	}

}