package com.panfeng.shining.service;

import com.panfeng.shining.base.BaseService;
import com.panfeng.shining.model.UserInfo;

/**
 * 用户服务
 * 
 * @author dawn 2016年1月5日
 */
public class UserService extends BaseService {

	/**
	 * 根据id查询 用户
	 * 
	 * @param uid
	 *            用户id
	 * @return 数据表中的第一个数据
	 */
	public UserInfo getUserInfo(long uid) {
		UserInfo userInfo = UserInfo.dao.findFirst(
				"SELECT * FROM user_info WHERE ui_id = ? ",(int)uid);
		return userInfo;
	}
}
