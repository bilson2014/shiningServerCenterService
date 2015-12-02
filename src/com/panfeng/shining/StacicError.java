package com.panfeng.shining;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.panfeng.shining.entity.ErrorMessage;

public class StacicError {
	// 全局错误 列表
	public static List<ErrorMessage> ErrorList = new ArrayList<ErrorMessage>();

	/*
	 * errorName-->错误名字 Message-->错误信息 time --> 错误时间 level --> 级别 --->
	 * 级别为10的系统无法继续运行
	 */
	public static void putError(String errorName, String Message, Date time,
			int level,String ip) {
		ErrorMessage em = new ErrorMessage(errorName, Message, time, level,ip);
		ErrorList.add(em);
	}

	public static void removeItem(String ip) {
		System.out.println(ip);
		if(ErrorList==null||ErrorList.size()<=0)
			return;
		
		for (int i = 0; i <ErrorList.size(); i++) {
			ErrorMessage em=ErrorList.get(i);
			System.out.println(ErrorList.toString());
			if(em.getIp().equals(ip)&&em.getLevel()==5)
			{
				ErrorList.remove(i);
			}
		}
	}

}
