package com.panfeng.shining.utils;

import com.panfeng.shining.ConfigDefine;

public class ConsoleLog {

	public static void printf(String str) {
		if (ConfigDefine.DEBUG) {
			System.out.println(str);
		}
	}
}
