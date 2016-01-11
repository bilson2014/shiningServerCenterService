package com.panfeng.shining.service;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.panfeng.shining.base.BaseService;
import com.panfeng.shining.model.PurchaseHistory;
import com.panfeng.shining.model.UserInfo;
import com.panfeng.shining.utils.ListUtils;

/**
 * 交易服务
 * 
 * @author dawn 2016年1月5日
 */
public class DealService extends BaseService {
	/**
	 * 同步客户端列表到服务器
	 * 
	 * @param uid
	 *            用户ID
	 * @param jsondata
	 *            远程列表json数据
	 * @return result
	 */
	public Result syncPurchaseHistoryService(long uid, String jsondata) {
		List<PurchaseHistory> netlistPurchaseHistories = JSON.parseArray(
				jsondata, PurchaseHistory.class);
		if (!ListUtils.isEmpty(netlistPurchaseHistories)) {
			List<PurchaseHistory> dblistPurchaseHistories = PurchaseHistory.dao
					.find("select * from purchase_history where ph_uid= ? ",
							uid);
			List<PurchaseHistory> increment = findListDiff(
					dblistPurchaseHistories, netlistPurchaseHistories);
			if (!ListUtils.isEmpty(increment)) {
				for (int i = 0; i < increment.size(); i++) {
					increment.get(i).save();
				}
				// 增量添加完毕
				return Result.SUCCESS;
			} else {
				// 没有增量
				return Result.SUCCESS;
			}
		} else {
			return Result.ERROR_PARAMETER;
		}
	}

	public Result syncByAppend(long uid, String jsondata) {
		List<PurchaseHistory> netlistPurchaseHistories = JSON.parseArray(
				jsondata, PurchaseHistory.class);
		if (!ListUtils.isEmpty(netlistPurchaseHistories)) {
			for (int i = 0; i < netlistPurchaseHistories.size(); i++) {
				netlistPurchaseHistories.get(i).save();
			}
			return Result.SUCCESS;
		} else {
			return Result.ERROR_CONTENT_NOT_EXIST;
		}
	}

	/**
	 * 更新金币数
	 * 
	 * @param uid
	 *            用户id
	 * @param coin
	 *            金币数
	 * @param version
	 *            当前版本
	 * @return 处理结果
	 */
	public Result syncCoinService(long uid, int coin) {
		if (coin >= 0 && uid > 0) {
			UserInfo userInfo = UserInfo.dao.findFirst(
					"SELECT * FROM user_info WHERE ui_id= ? ", uid);
			if (userInfo != null) {
				userInfo.setUiMonies((long) coin);
				userInfo.update();
				return Result.SUCCESS;
			} else {
				return Result.ERROR_PARAMETER;
			}

		} else {
			return Result.ERROR_PARAMETER;
		}
	}

	public List<PurchaseHistory> getPurchaseHistoriesService(long uid) {
		List<PurchaseHistory> list = PurchaseHistory.dao.find(
				"SELECT * FROM purchase_history WHERE ph_uid  = ? ", uid);

		return ListUtils.isEmpty(list) ? new ArrayList<>() : list;
	}

	public UserInfo getCoin(long uid) {
		return baseService.getUserService().getUserInfo(uid);
	}

	/**
	 * 检查 list2 中是否包含list1 中不包含的数据 返回差异数据集
	 * 
	 * @param list1
	 *            源数据聚
	 * @param list2
	 *            带有增量的数据集
	 * @return 差异数据集
	 */
	private <T> List<T> findListDiff(List<T> list1, List<T> list2) {
		if (ListUtils.isEmpty(list1))
			return list2;
		if (ListUtils.isEmpty(list2))
			return null;

		List<T> diffList = new ArrayList<T>();
		T t;
		for (int i = 0; i < list2.size(); i++) {
			t = list2.get(i);
			if (!list1.contains(t)) {
				diffList.add(t);
			}
		}

		if (diffList.size() == 0)
			diffList = null;

		return diffList;
	}
}
