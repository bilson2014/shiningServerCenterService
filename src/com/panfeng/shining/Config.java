package com.panfeng.shining;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.render.ViewType;
import com.panfeng.shining.controller.GetStateController;
import com.panfeng.shining.controller.MainController;
import com.panfeng.shining.controller.ThemeController;
import com.panfeng.shining.tables.AudioBaseList;
import com.panfeng.shining.tables.AudioSortList;
import com.panfeng.shining.tables.LoadBoot;
import com.panfeng.shining.tables.MediaBaseUser;
import com.panfeng.shining.tables.MediaSortList;
import com.panfeng.shining.tables.SmbData;
import com.panfeng.shining.tables.UserData;
import com.panfeng.shining.utils.TyuServerUtils;

public class Config extends JFinalConfig {

	@Override
	public void configConstant(Constants me) {
		me.setEncoding("utf-8");
		me.setViewType(ViewType.JSP);
	}

	@Override
	public void configRoute(Routes me) {
		me.add("/State", GetStateController.class);
		me.add("/smc", MainController.class);
		me.add("/theme", ThemeController.class);
	}

	@Override
	public void configPlugin(Plugins me) {
		C3p0Plugin cp = new C3p0Plugin(ConfigDefine.getSqlAddress(), "root",
				"passw0rd");
		me.add(cp);
		ActiveRecordPlugin arp = new ActiveRecordPlugin(cp);
		me.add(arp);
		arp.setDialect(new MysqlDialect());
		arp.addMapping("media_base", "mb_id", SmbData.class);
		arp.addMapping("user_info", "ui_id", UserData.class);
		arp.addMapping("media_base_user", "id", MediaBaseUser.class);
		arp.addMapping("audio_sort_list", "id", AudioSortList.class);
		arp.addMapping("audio_base_list", "id", AudioBaseList.class);
		arp.addMapping("media_sort_list", "ms_id", MediaSortList.class);
		arp.addMapping("loadboot", "lead_id", LoadBoot.class);
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

}
