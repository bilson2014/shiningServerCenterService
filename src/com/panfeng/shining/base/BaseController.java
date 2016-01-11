package com.panfeng.shining.base;

import com.jfinal.core.Controller;
import com.panfeng.shining.base.BaseService.Result;

public class BaseController extends Controller {

	private final BaseService baseService = BaseService.getBaseService();

	public BaseService getBaseService() {
		return baseService;
	}

	protected void verifyID(long id) {
      if(id<=0)
    	  renderText(Result.ERROR_PARAMETER.toString());
	}
}
