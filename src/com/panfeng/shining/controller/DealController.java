package com.panfeng.shining.controller;

import com.jfinal.kit.JsonKit;
import com.panfeng.shining.base.BaseController;
import com.panfeng.shining.base.BaseService.Result;
import com.panfeng.shining.model.UserInfo;
import com.panfeng.shining.utils.StringUtils;
import com.panfeng.shining.utils.TyuServerUtils;

public class DealController extends BaseController {

	public void sync() {
		String jsondata = TyuServerUtils
				.getParamStringDefault(this, "josn", "");
		long uid = TyuServerUtils.getParamLong(this, "uid", 0);
		int coin = TyuServerUtils.getParamInt(this, "coin", -1);
		String result = "";
		if (!StringUtils.isEmpty(jsondata)) {
			result = getBaseService().getDealService()
					.syncPurchaseHistoryService(uid, jsondata) + "|";
		}
		if (coin > 0) {
			result += getBaseService().getDealService().syncCoinService(uid,
					coin);
		}
		renderText(result.equals("") ? Result.NO_CHANGE.toString() : result);
	}

	public void syncAppend() {
		String jsondata = TyuServerUtils
				.getParamStringDefault(this, "josn", "");
		long uid = TyuServerUtils.getParamLong(this, "uid", 0);
		verifyID(uid);
		Result result = getBaseService().getDealService().syncByAppend(uid,
				jsondata);
		renderText(result.toString());
	}

	public void getPurchaseHistory() {
		long uid = TyuServerUtils.getParamLong(this, "uid", 0);
		verifyID(uid);
		renderText(JsonKit.toJson(getBaseService().getDealService()
				.getPurchaseHistoriesService(uid)));
	}

	public void getCoin() {
		long uid = TyuServerUtils.getParamLong(this, "uid", 0);
		verifyID(uid);
		UserInfo uiInfo = getBaseService().getDealService().getCoin(uid);
		String res = uiInfo == null ? "" : JsonKit.toJson(uiInfo);
		renderText(res);
	}
}
