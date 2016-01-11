package com.panfeng.shining.service;

import java.util.Date;

import com.panfeng.shining.base.BaseService;
import com.panfeng.shining.model.Message;
import com.panfeng.shining.model.UserInfo;
import com.panfeng.shining.utils.StringUtils;

/**
 * 应用分享
 * 
 * @author dawn 2016年1月6日
 */
public class AppShareService extends BaseService {

	public String getUserAuthCode(long uid) {
		return idtohexstring(uid);
	}

	/**
	 * 验证分享码
	 * @param uid 用户id
	 * @param autocode 分享码
	 * @return {@link Result} 
	 */
	public Result authCode(long uid, String autocode) {
		/**
		 * 1.A --> B A分享给B，则B输入验证码
		 * <p>
		 * 2.检测B 是否已经绑定了分享者
		 * <p>
		 * 3.B输入authcode ，转换为uid，查找用户A
		 * <p>
		 * 4.检查用户A是否存在
		 * <p>
		 * 5.为用户B绑定用户A的authcode
		 * <p>
		 * 6.为各自增加100金币 存在事物问题
		 */

		UserInfo B = baseService.getUserService().getUserInfo(uid);
		String BAuthCodeString = B.getUiAuthcode();
		if (StringUtils.isEmpty(BAuthCodeString)) {
			// B 为未绑定用户
			long AUid = hexstringtoid(autocode);
			UserInfo A = baseService.getUserService().getUserInfo(AUid);
			if (A != null) {
				// 位B绑定A
				B.setUiAuthcode(autocode);
				B.update();

				// 创建私有消息
				Message ms1 = new Message();
				ms1.setMgState(ENABLED);
				ms1.setMgTime(new Date());
				ms1.setMgType((long) MessageService.COIN);
				ms1.setMgUid(uid + "");
				ms1.setMgValue("100");
				ms1.save();

				ms1 = new Message();
				ms1.setMgState(ENABLED);
				ms1.setMgTime(new Date());
				ms1.setMgType((long) MessageService.COIN);
				ms1.setMgUid(AUid + "");
				ms1.setMgValue("100");
				ms1.save();

				return Result.SUCCESS;
			} else {
				return Result.ERROR_CONTENT_NOT_EXIST;
			}
		} else {
			return Result.ERROR_DUPLICATION;
		}

	}

	static char[] chararay = { 'w', 'n', 'g', 'x' };

	// a%%97 b%%98 c%%99 d%%100 e%%101 f%%102
	// 范围1.0~999999 100w 16%=0~F423F
	// 针对第一条 码数为5位，10to16不够5位随机补大于f码
	private static String idtohexstring(long id) {
		String hexstring = Long.toHexString(id);
		char[] hexarray = hexstring.toCharArray();
		char[] res = null;

		if (hexarray.length >= 5) {
			res = hexarray;
		} else {
			res = new char[5];
			for (int i = 0; i < res.length; i++) {
				if (i > hexarray.length - 1) {
					res[res.length - i - 1] = chararay[i - 1];
				} else {
					res[res.length - hexarray.length + i] = hexarray[i];
				}
			}
		}
		return new String(res);
	}

	/**
	 * 分享码转换uid
	 * @param hex 分享码
	 * @return
	 */
	private static long hexstringtoid(String hex) {
		char[] res = hex.toCharArray();
		if (res.length > 5)
			return Long.parseLong(new String(res), 16);
		char[] replace = new char[5];
		char currchar;
		for (int i = 0; i < res.length; i++) {
			currchar = res[i];
			if (currchar > 102) {// 伪码 转换成0
				replace[i] = '0';
			} else {
				replace[i] = res[i];
			}
		}
		return Long.parseLong(new String(replace), 16);
	}

}
