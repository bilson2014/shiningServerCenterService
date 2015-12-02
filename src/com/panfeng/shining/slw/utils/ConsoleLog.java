package com.panfeng.shining.slw.utils;

import com.panfeng.shining.ConfigDefine;

public class ConsoleLog {

	public static void printf(String str) {
		if (ConfigDefine.DEBUG) {
			System.out.println(str);
		}
	}
}
