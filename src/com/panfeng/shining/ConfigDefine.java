package com.panfeng.shining;

public class ConfigDefine {
	public final static boolean DEBUG = true;

	// server
	public final static String HOST = DEBUG ? "http://192.168.1.110:8080/shiningCenterService/"
			: "http://123.59.86.227:8080/shiningCenterService/";
	/*
	 * db
	 */
	public static String DB_USER = "root";
	public static String DB_PWD = "passw0rd";
	public static String DB_NAME = "shining";
	public static String DB_IP = DEBUG ? "192.168.1.110" : "localhost";
	public static String DB_PORT = "3306";
	public static String DB_DRIVER_CLASS = "com.mysql.jdbc.Driver";
	/**
	 * 设置过滤器，如果要开启监控统计需要使用此方法或在构造方法中进行设置
	 * <p>
	 * 监控统计："stat" 防SQL注入："wall" 组合使用： "stat,wall"
	 * </p>
	 */
	public static String DB_FILTERS = "wall";

	public static String getSqlAddress() {
		return String
				.format("jdbc:mysql://%s:%s/%s?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull",
						new Object[] { DB_IP, DB_PORT, DB_NAME });
	}
}
