package com.panfeng.shining.entity;

import java.util.HashMap;
import java.util.Map;

public class CheckNode {
	// 服务器丢包次数记录
	private Map<String, Integer> selectMap = new HashMap<String, Integer>();
	// 最大丢包上限
	private int packetLossLimit = 5;

	/*
	 * 当有丢包发生时， 调用此方法。如果返回true 则服务器死亡。
	 */
	public boolean putItem(String ip) {
		if (ip == null || ip.equals(""))
			throw new NullPointerException("ip is null");
		boolean isDown = false;
		
		if (selectMap.containsKey(ip)) {
			int currentNumber = selectMap.get(ip);
			if (currentNumber + 1 >= packetLossLimit) {
				isDown = true;
			} else {
				selectMap.put(ip, selectMap.get(ip) + 1);
			}
		} else {
			selectMap.put(ip, 1);
		}
		System.out.println(selectMap.toString()+" " +isDown);
		return isDown;
	}

	// 重新收到包时，状态重置
	public void resetItem(String ip) {
		if (ip == null || equals(""))
			throw new NullPointerException("ip is null");
		selectMap.put(ip, 0);
	}

	

	public int getPacketLossLimit() {
		return packetLossLimit;
	}

	public void setPacketLossLimit(int packetLossLimit) {
		this.packetLossLimit = packetLossLimit;
	}

	public CheckNode(int packetLossLimit) {
		super();
		this.packetLossLimit = packetLossLimit;
	}

	public CheckNode() {
		super();
	}
	
	

}
