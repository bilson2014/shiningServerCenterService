package com.panfeng.shining;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import com.jfinal.render.ViewType;
import com.panfeng.shining.controller.AppShareController;
import com.panfeng.shining.controller.DealController;
import com.panfeng.shining.controller.GetStateController;
import com.panfeng.shining.controller.MainController;
import com.panfeng.shining.controller.MessageController;
import com.panfeng.shining.controller.ThemeController;
import com.panfeng.shining.model._MappingKit;
import com.panfeng.shining.utils.TyuServerUtils;

/**
 * 
 * @author dawn 2016年1月6日
 */
public class Config extends JFinalConfig {

	private static DruidPlugin druidPlugin = null;

	@Override
	public void configConstant(Constants me) {
		me.setEncoding("utf-8");
		me.setViewType(ViewType.JSP);
		if (ConfigDefine.DEBUG) {
			me.setDevMode(true);
		}
	}

	@Override
	public void configRoute(Routes me) {
		me.add("/State", GetStateController.class);
		me.add("/smc", MainController.class);
		me.add("/theme", ThemeController.class);
		me.add("/share", AppShareController.class);
		me.add("/deal", DealController.class);
		me.add("/msg", MessageController.class);
	}

	public static DruidPlugin createDruidPlugin() {
		if (druidPlugin == null) {
			synchronized ("sync") {
				if (druidPlugin == null) {
					druidPlugin = new DruidPlugin(ConfigDefine.getSqlAddress(),
							ConfigDefine.DB_USER, ConfigDefine.DB_PWD,
							ConfigDefine.DB_DRIVER_CLASS,
							ConfigDefine.DB_FILTERS);
				}
			}
		}
		return druidPlugin;
	}

	/**
	 * 初始化插件组
	 */
	@Override
	public void configPlugin(Plugins me) {
		// 初始化 druid数据源
		me.add(createDruidPlugin());

		ActiveRecordPlugin arp = new ActiveRecordPlugin(createDruidPlugin());
		_MappingKit.mapping(arp);
		me.add(arp);
		me.add(new EhCachePlugin());

		if (ConfigDefine.DEBUG) {
			arp.setDevMode(true);
			arp.setShowSql(true);
		}
	}

	@Override
	public void configInterceptor(Interceptors me) {

	}

	@Override
	public void configHandler(Handlers me) {

	}

	// 系统启动时
	@Override
	public void afterJFinalStart() {
		super.afterJFinalStart();
		TyuServerUtils.logDeBug("jfinal", "系统启动完成，初始化ServiceList");
		new ServiceList();
	}

	// 系统关闭时
	@Override
	public void beforeJFinalStop() {
		super.beforeJFinalStop();
	}

}
