package com.panfeng.shining.service;

import java.util.List;

import com.panfeng.shining.base.BaseService;
import com.panfeng.shining.model.Message;
import com.panfeng.shining.utils.ListUtils;

/**
 * 推送服务 所有消息储存在mysql内，公共消息消费记录 记录在内存里（ehcache）
 * 
 * @author dawn 2016年1月6日
 */
public class MessageService extends BaseService {

	public static final int COIN = 1;

	public List<Message> getPrivaeMessage(long uid) {

		/**
		 * 私有消息逻辑
		 * 
		 * 服务器创建消息储存至数据库
		 * <p>
		 * 客户端在特定事件触发时会请求服务器获取最近的私有消息
		 * <p>
		 * 消息返回后数据库消息状态置空
		 */
		List<Message> message = Message.dao.find(
				"SELECT * FROM message WHERE mg_uid= ? AND mg_state = ?", uid
						+ "", ENABLED);
		if (!ListUtils.isEmpty(message)) {
			return message;
		} else {
			return null;
		}
	}

	public void hideMsg(List<Message> list) {
		for (int i = 0; i < list.size(); i++) {
			hideMsg(list.get(i));
		}
	}

	public void hideMsg(Message msg) {
		msg.setMgState(DISABLED);
		msg.update();
	}
}
