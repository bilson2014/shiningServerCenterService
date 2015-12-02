package com.panfeng.shining;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.panfeng.shining.controller.MainController;
import com.panfeng.shining.entity.CheckNode;
import com.panfeng.shining.net.HttpUrlConnection;
import com.panfeng.shining.utils.GlobalProperties;
import com.panfeng.shining.utils.ReadFile;
import com.panfeng.shining.utils.TyuServerUtils;

public class ServiceList {

	// 可以使用的 service
	private static List<String> serviceList = new ArrayList<String>();

	// 原始 ip列表
	private List<String> ipList = new ArrayList<String>();

	// 宕机 service
	public static List<String> downserviceList = new ArrayList<String>();

	// 失效服务器 轮训检测时间
	private static long detectionThreadSleepTime = 5000;

	// 心跳 轮训 检测时间
	private static long checkServerSleepTime = 5000;

	// 保证效率读写所
	private static ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
	Lock lock = new ReentrantLock();
	Condition c = lock.newCondition();

	public ServiceList() {
		init();
		// 启动失效服务器 复活检测
		new Thread(new detectionThread()).start();
		// 启动心跳线程
		new Thread(new checkServer()).start();

	}

	/*
	 * 获取service List
	 */
	public static List<String> getServerList() {
		// 读锁
		rwl.readLock().lock();
		List<String> l = ServiceList.serviceList;
		rwl.readLock().unlock();
		return l;
	}

	public static String printServerList() {
		String str = "";
		for (int i = 0; i < ServiceList.serviceList.size(); i++) {
			str += ServiceList.serviceList.get(i) + " | ";
		}
		return str;

	}

	/*
	 * 删除 宕机 service
	 */
	private void removeItem(String ip) {
		// 写锁
		// synchronized (serviceList) {
		rwl.writeLock().lock();

		if (serviceList.contains(ip))
			serviceList.remove(ip);
		downserviceList.add(ip);
		rwl.writeLock().unlock();
		// serviceList.notifyAll();
		lock.lock();
		c.signalAll();
		lock.unlock();
		StacicError.putError("服务器失效", ip + "  服务器失效请尽快检测", new Date(), 5, ip);

		// }
	}

	private void addItem(String ip) {
		rwl.writeLock().lock();
		serviceList.add(ip);
		if (downserviceList.contains(ip))
			downserviceList.remove(ip);
		rwl.writeLock().unlock();
	}

	private void init() {
		// 初始化 config
		ipList = ReadFile.read(GlobalProperties.get().getProperty("ipconfig"));
		if (ipList == null || ipList.size() <= 0)
			return;
		serviceList.addAll(ipList);
		int i = 2;
		i = call();
		switch (i) {
		case 0:
			TyuServerUtils.logDeBug("初始化线程", "一切ok系统允许启动");
			break;
		case 5:
			TyuServerUtils.logDeBug("初始化线程", "系统允许启动但是有部分服务器失效，请快速检测！！");
			break;
		case 10:
			synchronized (serviceList) {
				serviceList.notifyAll();
			}
			TyuServerUtils.logDeBug("初始化线程", "系统服务器列表里无可服务主机，请快速检测！！");
		default:
			break;
		}

	}

	/*
	 * 检测失效service Tread
	 */

	class detectionThread implements Runnable {

		@Override
		public void run() {
			TyuServerUtils.logDeBug("服务器失效检测线程", "失效检测线程->启动");
			mainLoop();
		}

		private void mainLoop() {
			try {
				while (true) {
					while (downserviceList.size() <= 0) {
						TyuServerUtils.logDeBug("服务器失效检测线程", "失效检测线程->休眠");
						lock.lock();
						c.await();
						lock.unlock();
						TyuServerUtils.logDeBug("服务器失效检测线程", "出现服务器失效->列表  -->"
								+ downserviceList.toString());
					}
					// http 请求检测 服务器是否起来
					// 如果检测起来了 则wait 线程 否则 5秒检测一次
					detection();
					Thread.sleep(detectionThreadSleepTime);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		// 检测
		private void detection() {
			for (int i = 0; i < downserviceList.size(); i++) {
				String Url = String.format(
						"Http://%s/shiningSubService/smc/test",
						downserviceList.get(i));
				// System.out.println("url  :" + Url);
				String result = HttpUrlConnection.sendGet(Url, "utf-8");
				// System.out.println("detection result=" + result + " ip  "
				// + downserviceList.get(i));

				if (result != null && result.trim().equals("ok")) {
					TyuServerUtils.logDeBug("服务器失效检测线程", "服务器 :  "
							+ downserviceList.get(i) + "   -->  复活");
					StacicError.removeItem(downserviceList.get(i));
					addItem(downserviceList.get(i));
					MainController mc = new MainController();
					mc.syncAllService();
				}
			}
		}
	}

	// 心跳 Thread
	class checkServer implements Runnable {
		private CheckNode cn;

		public checkServer() {
			cn = new CheckNode();
		}

		@Override
		public void run() {
			try {
				TyuServerUtils.logDeBug("心跳线程", "心跳线程启动");
				check();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		private void check() throws InterruptedException {
			while (true) {
				if (getServerList() != null && getServerList().size() > 0) {
					for (int i = 0; i < getServerList().size(); i++) {
						String Url = String.format(
								"Http://%s/shiningSubService/smc/test",
								getServerList().get(i));
						String result = HttpUrlConnection.sendGet(Url, "utf-8");
						// System.out.println("check result" + result + " ip  "
						// + serviceList.get(i) + "   serviceList size"
						// + serviceList.size());
						if (result == null || result.trim().equals("")) {
							// 服务器 请求失败
							if (cn.putItem(getServerList().get(i))) {
								TyuServerUtils.logDeBug("心跳线程", "服务器  ："
										+ getServerList().get(i)
										+ "   丢包数达到上限     在活跃列表中移除");
								removeItem(getServerList().get(i));
							}
						} else if (result.equals("pause")) {
							TyuServerUtils.logDeBug("心跳线程", "服务器  ："
									+ getServerList().get(i)
									+ "   在活跃列表中移除  ，因为同步视频");
							removeItem(getServerList().get(i));
						} else if (result.equals("ok")) {
							cn.resetItem(getServerList().get(i));
						}
					}
					TyuServerUtils.logDeBug("心跳线程", getServerList().size()
							+ " 个服务器在线， 列表：" + ServiceList.printServerList());
					// System.out.println("心跳线程"+ getServerList().size()
					// + " 个服务器在线， 列表：" + ServiceList.printServerList());
				}
				Thread.sleep(checkServerSleepTime);
			}
		}
	}

	/*
	 * 初始化检测方法
	 */
	private Integer call() {
		int level = 0;
		if (ServiceList.serviceList == null
				|| ServiceList.serviceList.size() <= 0)
			throw new NullPointerException("init fail serviceList is null");

		for (int i = 0; i < ServiceList.serviceList.size(); i++) {
//			String Url = String.format("Http://%s/shiningSubService/smc/test",
//					getServerList().get(i));
			// System.out.println(Url);
			// String result = HttpUrlConnection.sendGet(Url, "utf-8");
			String result = null;
			if (result == null || result.trim().equals("")) {
				i = 0;
				StacicError.putError("inint Error", "服务器配置文件中存在失效服务器",
						new Date(), 5, getServerList().get(0));
				level = 5;
				TyuServerUtils.logDeBug("init thread", "删除"
						+ ServiceList.serviceList.get(i));
				removeItem(ServiceList.serviceList.get(i));

			}
		}

		// System.out.println(getServerList().toString()+"  `~~  `~~  `~ `` ` ~   ");
		return level;
	}

}
