package com.panfeng.shining.controller;

import java.io.File;
import java.util.List;

import com.jfinal.core.Controller;
import com.jfinal.kit.JsonKit;
import com.jfinal.upload.UploadFile;
import com.panfeng.shining.base.BaseService.Result;
import com.panfeng.shining.model.ThemeBase;
import com.panfeng.shining.utils.MD5Utils;
import com.panfeng.shining.utils.StringUtils;
import com.panfeng.shining.utils.TyuServerUtils;

/**
 * 主题相关控制类
 * 
 * @author dawn 2016年12月2日
 */
public class ThemeController extends Controller {
	/**
	 * 获取主题列表
	 */
	public void getThemeList() {
		List<ThemeBase> themeBases = ThemeBase.dao
				.find("select *from theme_base where th_state ='1'");
		renderText(JsonKit.toJson(themeBases));
	}

	/**
	 * 上传主题
	 */
	public void uploadTheme() {
		String realPath = getRequest().getServletContext().getRealPath("/");
		String dir_name = "themeimage/";
		String themeimage = realPath + dir_name;
		dir_name = "themezip/";
		String themezip = realPath + dir_name;
		UploadFile zip = getFile("zip");
		UploadFile img = getFile("img");
		try {

			String name = TyuServerUtils.getParamString(this, "name", true);
			String lntroduction = TyuServerUtils.getParamString(this,
					"lntroduction", true);
			int sort = TyuServerUtils.getParamInt(this, "sort", 0);
			if (zip == null) {
				renderText("没有添加主题包");
				return;
			}
			String filenameString = zip.getOriginalFileName();
			filenameString = filenameString.substring(
					filenameString.lastIndexOf('.') + 1,
					filenameString.length());
			if (!filenameString.toLowerCase().equals("zip")) {
				System.out.println(filenameString);
				renderText("只能上传zip的资源包");
				return;
			}

			if (img == null) {
				renderText("没有添加主题包展示图片");
				return;
			}
			long coin = TyuServerUtils.getParamInt(this, "coin", 0);
			String randomName = StringUtils.getRandomString_1(6);
			File localzip = new File(themezip, randomName + ".zip");
			File localimage = new File(themeimage, randomName + ".jpg");

			zip.getFile().renameTo(localzip);
			img.getFile().renameTo(localimage);

			ThemeBase tb = new ThemeBase();

			tb.setThName(name);
			tb.setThLntroduction(lntroduction);
			tb.setThSort(sort);
			tb.setThImage(localimage.getName());
			tb.setThThemeZip(localzip.getName());
			tb.setThAuthor("slt");
			tb.setThMd5(MD5Utils.getFileMD5String(localzip));
			tb.setThSort(1);
			tb.setThPrice(coin);
			tb.save();
			renderText("ok");
		} catch (Exception e) {
			e.printStackTrace();
			renderText(e.getMessage());
		}
	}

	/**
	 * 隐藏主题
	 */
	public void hideTheme() {
		int id = TyuServerUtils.getParamInt(this, "id", -1);
		if (id < 0) {
			renderText(Result.ERROR_PARAMETER.toString());
		} else {
			ThemeBase tb = ThemeBase.dao.findById(id);
			if (tb != null) {
				tb.setThState(0);
				tb.update();
				renderText(Result.SUCCESS.toString());
			} else
				renderText(Result.ERROR_CONTENT_NOT_EXIST.toString());
		}
	}

	/**
	 * 更新主题
	 */
	public void updateTheme() {
		String realPath = getRequest().getServletContext().getRealPath("/");
		String dir_name = "themeimage/";
		String themeimage = realPath + dir_name;
		dir_name = "themezip/";
		String themezip = realPath + dir_name;
		UploadFile zip = getFile("zip");
		UploadFile img = getFile("img");
		int id = TyuServerUtils.getParamInt(this, "id", 0);
		String name = TyuServerUtils.getParamStringDefault(this, "name", "");
		String lntroduction = TyuServerUtils.getParamStringDefault(this,
				"lntroduction", "");
		int sort = TyuServerUtils.getParamInt(this, "sort", 0);

		ThemeBase themeBase = ThemeBase.dao.findById(id);
		if (themeBase == null)
			renderText(Result.ERROR_CONTENT_NOT_EXIST.toString());

		if (zip != null) {
			String randomName = StringUtils.getRandomString_1(6);
			File localzip = new File(themezip, randomName + ".zip");
			zip.getFile().renameTo(localzip);
			themeBase.setThThemeZip(localzip.getName());
			themeBase.setThMd5(MD5Utils.getFileMD5String(localzip));
		}
		if (img != null) {
			String randomName = StringUtils.getRandomString_1(6);
			File localimage = new File(themeimage, randomName + ".jpg");
			img.getFile().renameTo(localimage);
			themeBase.setThImage(localimage.getName());
		}

		long coin = TyuServerUtils.getParamInt(this, "coin", 0);
		themeBase.setThName(name);
		themeBase.setThLntroduction(lntroduction);
		themeBase.setThSort(sort);
		themeBase.setThState(1);
		themeBase.setThPrice(coin);
		if (themeBase.update()) {
			renderText(Result.SUCCESS.toString());
		} else {
			renderText(Result.ERROR_CONTENT_NOT_EXIST.toString());
		}

	}
}
