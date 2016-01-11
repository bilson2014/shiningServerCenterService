package com.panfeng.shining.controller;

import com.panfeng.shining.base.BaseController;
import com.panfeng.shining.base.BaseService.Result;
import com.panfeng.shining.utils.TyuServerUtils;

/**
 * 
 * @author dawn 2016年1月7日
 */
public class AppShareController extends BaseController {

	/**
	 * 用户获取分享码
	 */
	public void getAuthCode() {
		long uid = TyuServerUtils.getParamInt(this, "uid", -1);
		verifyID(uid);
		String code = getBaseService().getAppshareservice()
				.getUserAuthCode(uid);
		renderText(code);
	}

	/**
	 * 用户验证分享码
	 */
	public void verifyCode() {
		long uid = TyuServerUtils.getParamInt(this, "uid", -1);
		verifyID(uid);
		String code = TyuServerUtils
				.getParamStringDefault(this, "authcode", "");
		Result result = getBaseService().getAppshareservice().authCode(uid,
				code);
		renderText(result.toString());
	}
}
