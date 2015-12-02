package com.panfeng.shining.controller;

import java.util.List;

import com.jfinal.core.Controller;
import com.jfinal.kit.JsonKit;
import com.panfeng.shining.ServiceList;
import com.panfeng.shining.StacicError;

public class GetStateController extends Controller {

	public void getSurvival() {
		List<String> list = ServiceList.getServerList();
		String json = JsonKit.toJson(list);
		renderText(json);
	}
	
	public void getDie() {
		List<String> list = ServiceList.downserviceList;
		String json = JsonKit.toJson(list);
		renderText(json);
	}
	public void getError() {
		String json = JsonKit.toJson(StacicError.ErrorList);
		renderText(json);
	}

}
