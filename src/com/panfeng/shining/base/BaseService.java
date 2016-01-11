package com.panfeng.shining.base;

import com.panfeng.shining.service.AppShareService;
import com.panfeng.shining.service.DealService;
import com.panfeng.shining.service.MessageService;
import com.panfeng.shining.service.UserService;

public class BaseService {

	protected static BaseService baseService = new BaseService();
	private final static DealService dealService = new DealService();
	private final static UserService userService = new UserService();
	private final static AppShareService appShareService = new AppShareService();
	private final static MessageService messageService = new MessageService();

	protected final static int ENABLED = 1;
	protected final static int DISABLED = 0;

	public static BaseService getBaseService() {
		return baseService;
	}

	public UserService getUserService() {
		return userService;
	}

	public DealService getDealService() {
		return dealService;
	}

	public AppShareService getAppshareservice() {
		return appShareService;
	}

	public static enum Result {
		SUCCESS, NO_CHANGE,ERROR_PARAMETER, ERROR_DUPLICATION, ERROR_CONTENT_NOT_EXIST
	}

	public  MessageService getMessageService() {
		return messageService;
	}

}
