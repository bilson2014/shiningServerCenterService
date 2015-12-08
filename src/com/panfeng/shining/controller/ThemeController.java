package com.panfeng.shining.controller;

import java.io.File;
import java.util.List;
import java.util.Random;

import com.jfinal.core.Controller;
import com.jfinal.kit.JsonKit;
import com.jfinal.upload.UploadFile;
import com.panfeng.shining.slw.utils.MD5Utils;
import com.panfeng.shining.tables.ThemeBase;
import com.panfeng.shining.utils.TyuServerUtils;

public class ThemeController extends Controller {

	public void getThemeList() {
		List<ThemeBase> themeBases = ThemeBase.dao
				.find("select *from theme_base where th_state ='1'");
		renderText(JsonKit.toJson(themeBases));
	}

	public void uploadTheme() {
		String realPath = getRequest().getServletContext().getRealPath("/");
		String dir_name = "themeimage/";
		String themeimage = realPath + dir_name;
		dir_name = "themezip/";
		String themezip = realPath + dir_name;

		try {
			UploadFile zip = getFile("zip");
			UploadFile img = getFile("img");
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
					filenameString.lastIndexOf('.')+1, filenameString.length());
			if(!filenameString.toLowerCase().equals("zip")){
				System.out.println(filenameString);
				renderText("只能上传zip的资源包");
				return;
			}
			
			if (img == null) {
				renderText("没有添加主题包展示图片");
				return;
			}

			String randomName = getRandomString();
			File localzip = new File(themezip, randomName + ".zip");
			File localimage = new File(themeimage, randomName + ".jpg");

			zip.getFile().renameTo(localzip);
			img.getFile().renameTo(localimage);

			ThemeBase tb = new ThemeBase();
			tb.set("th_name", name);
			tb.set("th_lntroduction", lntroduction);
			tb.set("th_sort", sort);
			tb.set("th_image", localimage.getName());
			tb.set("th_theme_zip", localzip.getName());
			tb.set("th_author", "slt");
			tb.set("th_md5", MD5Utils.getFileMD5String(localzip));
			tb.set("th_state", 1);
			tb.save();

			renderText("ok");
		} catch (Exception e) {
			e.printStackTrace();
			renderText(e.getMessage());
		}
	}

	public void hideTheme() {
		int id = TyuServerUtils.getParamInt(this, "id", -1);
		if (id < 0) {
			renderText("Request id error");
		} else {
			ThemeBase tb = ThemeBase.dao.findById(id);
			if (tb != null) {
				tb.set("th_state", 0);
				tb.update();
				renderText("ok");
			} else
				renderText("Request id not found");
		}
	}

	public void updateTheme() {
		String realPath = getRequest().getServletContext().getRealPath("/");
		String dir_name = "themeimage/";
		String themeimage = realPath + dir_name;
		dir_name = "themezip/";
		String themezip = realPath + dir_name;
		try {

			UploadFile zip = getFile("zip");
			UploadFile img = getFile("img");
			int id = TyuServerUtils.getParamInt(this, "id", 0);
			String name = TyuServerUtils.getParamString(this, "name", false);
			String lntroduction = TyuServerUtils.getParamString(this,
					"lntroduction", false);
			int sort = TyuServerUtils.getParamInt(this, "sort", 0);

			ThemeBase tb = ThemeBase.dao.findById(id);

			if (zip != null) {
				String randomName = getRandomString();
				File localzip = new File(themezip, randomName + ".zip");
				zip.getFile().renameTo(localzip);
				tb.set("th_theme_zip", localzip.getName());
				tb.set("th_md5", MD5Utils.getFileMD5String(localzip));
			}
			if (img != null) {
				String randomName = getRandomString();
				File localimage = new File(themeimage, randomName + ".jpg");
				img.getFile().renameTo(localimage);
				tb.set("th_image", localimage.getName());
			}
			tb.set("th_name", name);
			tb.set("th_lntroduction", lntroduction);
			tb.set("th_sort", sort);
			tb.set("th_state", 1);
			tb.update();
			renderText("ok");
		} catch (Exception e) {
			e.printStackTrace();
			renderText(e.getMessage());
		}

	}

	public String getRandomString() {
		StringBuffer sbBuffer = new StringBuffer();
		Random random = new Random();
		sbBuffer.append(random.nextInt(10000));
		sbBuffer.append(random.nextInt(10000));
		sbBuffer.append(random.nextInt(10000));
		sbBuffer.append(random.nextInt(10000));
		sbBuffer.append(random.nextInt(10000));
		sbBuffer.append(random.nextInt(10000));
		return sbBuffer.toString();
	}

}
