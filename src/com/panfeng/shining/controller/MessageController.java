package com.panfeng.shining.controller;

import java.util.List;

import com.jfinal.kit.JsonKit;
import com.panfeng.shining.base.BaseController;
import com.panfeng.shining.model.Message;
import com.panfeng.shining.utils.ListUtils;
import com.panfeng.shining.utils.TyuServerUtils;

public class MessageController extends BaseController {

	public void getPrivateMsg() {
		long uid = TyuServerUtils.getParamLong(this, "uid", -1);
		verifyID(uid);
		List<Message> list = getBaseService().getMessageService()
				.getPrivaeMessage(uid);
		if (!ListUtils.isEmpty(list)) {
			renderText(JsonKit.toJson(list));
			getBaseService().getMessageService().hideMsg(list);
		} else {
			renderText("");
		}

	}
}
