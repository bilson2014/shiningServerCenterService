package com.panfeng.shining.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.locks.ReentrantLock;

public class GlobalProperties {
	private static Properties prop = null;
	private static ReentrantLock rl = new ReentrantLock();

	private GlobalProperties() {
	}

	/**
	 * 获取属性文件内的内容
	 * 
	 * @return
	 */
	public static Properties get() {
		if (prop == null) {
			rl.lock();
			if (prop == null) {
				try {
					InputStream is = GlobalProperties.class
							.getResourceAsStream("/Config.properties");
					prop = new Properties();
					prop.load(is);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			rl.unlock();
		}
		return prop;
	}
}
