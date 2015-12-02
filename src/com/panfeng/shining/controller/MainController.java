package com.panfeng.shining.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang.StringUtils;

import com.jfinal.core.Controller;
import com.jfinal.kit.JsonKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.upload.UploadFile;
import com.panfeng.shining.ConfigDefine;
import com.panfeng.shining.ServiceList;
import com.panfeng.shining.entity.VideoInfo;
import com.panfeng.shining.net.HttpUrlConnection;
import com.panfeng.shining.slw.utils.MD5Utils;
import com.panfeng.shining.tables.AudioBaseList;
import com.panfeng.shining.tables.AudioSortList;
import com.panfeng.shining.tables.LoadBoot;
import com.panfeng.shining.tables.MediaBaseUser;
import com.panfeng.shining.tables.MediaSortList;
import com.panfeng.shining.tables.SmbData;
import com.panfeng.shining.tables.UserData;
import com.panfeng.shining.utils.GlobalProperties;
import com.panfeng.shining.utils.ReadFile;
import com.panfeng.shining.utils.TyuFileUtils;
import com.panfeng.shining.utils.TyuServerUtils;
import com.panfeng.shining.videotools.Mp4Box;
import com.panfeng.shining.videotools.VideoFirstThumbTaker;

public class MainController extends Controller {

	List<SmbData> allMediaDatas;

	int currentId = 0;

	String dir = GlobalProperties.get().get("mediaBasePath").toString()
			+ File.separator;
	File file2;

	public void test() {
		renderText("ok");
	}

	public void getVideoList() {
		List<VideoInfo> fileList = new ArrayList<VideoInfo>();
		String mediaPaht = GlobalProperties.get().get("mediaBasePath")
				.toString();

		File file = new File(mediaPaht);
		File[] array = file.listFiles();
		String fileName;
		String fileType;
		for (int i = 0; i < array.length; i++) {
			fileName = array[i].getName();

			int lastindex = fileName.lastIndexOf('.');
			if (lastindex >= 1) {
				fileType = fileName.substring(lastindex, fileName.length());
				if (fileType.equals(".mp4") && array[i].length() > 1) {
					VideoInfo vi = new VideoInfo();
					vi.setVideoName(fileName);
					vi.setSize(array[i].length());
					fileList.add(vi);
				}
			}

		}
		String json = JsonKit.toJson(fileList);
		// System.out.println(json);
		renderText(json);
	}

	public void getVideo() {
		String strid = getPara("id");
		// System.out.println(strid + ":id");
		if (strid != null && !strid.equals("")) {
			int id = Integer.parseInt(strid);
			// System.out.println(id + ":int id");
			if (id > 0) {
				int servceiCout = ServiceList.getServerList().size();
				int useServiceId = (int) (System.currentTimeMillis() % servceiCout);
				String videoName;
				videoName = SmbData.dao.findById(id).getStr("mb_video_name");
				add_plays(id);
				redirect("http://"
						+ ServiceList.getServerList().get(useServiceId)
						+ "/shiningSubService/media_base/" + videoName);
			}
		}
	}

	public void syncAllService() {
		String url;
		List<String> list = ReadFile.read(GlobalProperties.get().getProperty(
				"ipconfig"));

		if (list == null || list.size() == 0) {
			list = ServiceList.getServerList();
		} else {
			// System.out.println("读文件列表   ：" + list.toString());
		}
		for (int i = 0; i < list.size(); i++) {
			// System.out.println(list.get(i));
			url = String.format("http://%s/shiningSubService/smc/syncVideo",
					list.get(i));
			new Thread(new sync(url)).start();
		}
		// System.out.println(list.toString());
		renderText("ok");
	}

	public void getVideoLength() {
		int videoID = TyuServerUtils.getParamInt(this, "videoID", 0);
		SmbData sd = SmbData.dao.findById(videoID);
		String videoNameString = sd.get("mb_video_name");
		if (videoNameString == null || videoNameString.equals("")) {
			renderText("0");
			return;
		}
		File file = new File(dir, videoNameString);
		long fileLength = file.length();
		renderText(fileLength + "");
	}

	/*
	 * 增加播放次数
	 */
	private void add_plays(int id) {
		// System.out.println(id + ":add_plays");
		try {
			int mb_id = id;
			if (mb_id == 0) {
				return;
			}
			SmbData data = (SmbData) SmbData.dao.findById(Integer
					.valueOf(mb_id));
			// System.out.println(data.getInt("mb_id") + "mb_id");
			if (data != null) {
				int weight = (data.getInt("mb_plays").intValue() + 1);
				data.set("mb_plays", Integer.valueOf(weight));
				data.update();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * 增加收藏次数
	 */
//	public void add_favtimes() {
//
//		try {
//			int mb_id = TyuServerUtils.getParamInt(this, "id", 0);
//			if (mb_id == 0) {
//				renderText("error");
//				return;
//			}
//			SmbData data = (SmbData) SmbData.dao.findById(Integer
//					.valueOf(mb_id));
//			if (data != null) {
//				int weight = data.getInt("mb_favtimes").intValue() + 1;
//				data.set("mb_favtimes", Integer.valueOf(weight));
//				data.update();
//				renderText("ok");
//			} else {
//				renderText("error");
//			}
//		} catch (Exception e) {
//			renderText("error:" + e.toString());
//		}
//		renderText("ok");
//	}

	/*
	 * 增加设置次数
	 */
	public void add_settingtimes() {

		try {
			int mb_id = TyuServerUtils.getParamInt(this, "id", 0);
			if (mb_id == 0) {
				renderText("error");
				return;
			}
			SmbData data = (SmbData) SmbData.dao.findById(Integer
					.valueOf(mb_id));
			if (data != null) {
				int weight = data.getInt("mb_settingtimes").intValue() + 1;
				data.set("mb_settingtimes", Integer.valueOf(weight));
				data.update();
				renderText("ok");
			} else {
				renderText("error");
			}
		} catch (Exception e) {
			renderText("error:" + e.toString());
		}
		renderText("ok");
	}

	// 1 为刚上传，为审核
	// 0 为审核为通过
	// 10为审核通过
	// 7为删除
	public void getUserVideo() {
		try {
			int page_id = TyuServerUtils.getParamInt(this, "page_id", 1);
			int page_size = TyuServerUtils.getParamInt(this, "page_size", 10);

			StringBuilder sql = new StringBuilder();
			sql.append("from media_base_user");
			@SuppressWarnings("rawtypes")
			Page res = Db.paginate(page_id, page_size, "select * ",
					sql.toString());
			renderText(JsonKit.toJson(res.getList(), 5));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/*
	 * 
	 */
	public void get_media_base() {
		try {
			int page_id = TyuServerUtils.getParamInt(this, "page_id", 0);
			int page_size = TyuServerUtils.getParamInt(this, "page_size", 10);
			int sort_id = TyuServerUtils.getParamInt(this, "sort", -1);
			int order_by_weight = TyuServerUtils
					.getParamInt(this, "weight", -1);
			StringBuilder sql = new StringBuilder();
			sql.append(" from media_base where (mb_state=10 or mb_state=1) ");

			if (sort_id > 0) {
				sql.append(" and mb_ms_id=" + sort_id);
			}
			if (order_by_weight == 1) {
				if (page_id * page_size >= 35) {
					page_id = Integer.MAX_VALUE;
				}
				sql.append(" order by mb_weight desc");
			} else
				sql.append(" order by mb_state desc,mb_id desc");

			Page res = Db.paginate(page_id, page_size, "select * ",
					sql.toString());

			renderText(JsonKit.toJson(res.getList(), 5));

		} catch (Exception e) {
			renderText("error:" + e.toString());
		}
	}

	public void get_media_base_new() {
		try {
			int page_id = TyuServerUtils.getParamInt(this, "page_id", 0);
			int page_size = TyuServerUtils.getParamInt(this, "page_size", 10);
			int video_id = TyuServerUtils.getParamInt(this, "vId", -1);
			int order_by_weight = TyuServerUtils
					.getParamInt(this, "weight", -1);
			StringBuilder sql = new StringBuilder();
			sql.append(" from media_base where (mb_state=10 or mb_state=1) ");

			if (video_id > 0) {
				sql.append(" and mb_id<=");
				sql.append(video_id);
			}
			if (order_by_weight == 1) {
				sql.append(" order by mb_weight desc");
			} else
				sql.append(" order by mb_state desc,mb_id desc");

			Page res = Db.paginate(page_id, page_size, "select * ",
					sql.toString());

			renderText(JsonKit.toJson(res.getList(), 5));

		} catch (Exception e) {
			renderText("error:" + e.toString());
		}
	}

	public void search_Media_Base() {
		try {
			int page_id = TyuServerUtils.getParamInt(this, "page_id", 1);
			int page_size = TyuServerUtils.getParamInt(this, "page_size", 5);
			String searchValue = TyuServerUtils.getParamString(this,
					"searchValue", false);
			if (searchValue == null || searchValue.equals("")) {
				renderText("参数错误");
				return;
			}

			StringBuilder sql = new StringBuilder();
			sql.append("from media_base where  mb_state=10 or mb_state=1  and concat(`mb_keys`,`mb_content`,`mb_author`,`mb_name`,`mb_id`) like '%"
					+ searchValue + "%'");
			Page res = Db.paginate(page_id, page_size, "select * ",
					sql.toString());
			renderText(JsonKit.toJson(res.getList(), 5));
			// System.out.println(searchValue);
		} catch (Exception e) {
			renderText("error:" + e.toString());
		}
	}

	public void get_media_base2nd() {
		try {
			int page_id = TyuServerUtils.getParamInt(this, "page_id", 0);
			int page_size = TyuServerUtils.getParamInt(this, "page_size", 10);
			int sort_id = TyuServerUtils.getParamInt(this, "sort", -1);
			int order_by_weight = TyuServerUtils
					.getParamInt(this, "weight", -1);
			StringBuilder sql = new StringBuilder();
			sql.append(" from media_base where (mb_state=10 or mb_state=1) ");

			if (sort_id > 0) {
				sql.append(" and mb_ms_id=" + sort_id);
			}
			if (order_by_weight == 1)
				sql.append(" order by mb_weight desc");
			else
				sql.append(" order by mb_state desc,mb_id desc");
			Page res = Db.paginate(page_id, page_size, "select * ",
					sql.toString());

			renderText(JsonKit.toJson(res.getList(), 5));
		} catch (Exception e) {
			renderText("error:" + e.toString());
		}
	}

	/*
	 * 
	 */
	public void get_media_base_by_date() {
		try {
			String tmp = TyuServerUtils.getParamString(this, "time", true);
			long ts = Long.parseLong(tmp);
			String value = TyuServerUtils.date_format.format(new Date(ts))
					+ "-00-00";
			String sql = String
					.format("select * from media_base where (mb_state=1 or mb_state=10) and mb_upload_time >= %s",
							new Object[] { value });
			List res = SmbData.dao.find(sql);

			renderText(JsonKit.toJson(res));
		} catch (Exception e) {
			renderText("error:" + e.toString());
		}
	}

	/*
	 * 
	 */
	public void search_media_base() {
		try {
			getFile();
			int page_id = TyuServerUtils.getParamInt(this, "page_id", 0);
			int page_size = TyuServerUtils.getParamInt(this, "page_size", 10);
			int sort_id = TyuServerUtils.getParamInt(this, "sort", -1);
			String key = TyuServerUtils.getParamString(this, "key", false);
			StringBuilder sql = new StringBuilder();
			sql.append("from media_base where (mb_state=1 or mb_state=10)");

			if (sort_id > 0) {
				sql.append(" and mb_ms_id=" + sort_id);
				sql.append(" order by mb_id desc,mb_weight desc");
			} else if (!StringUtils.isEmpty(key)) {
				String word = "'%" + key + "%'";
				sql.append(" and mb_keys like " + word);
				sql.append(" order by mb_id desc,mb_weight desc");
			} else {
				throw new Exception("media file not found!");
			}
			Page res = Db.paginate(page_id, page_size, "select * ",
					sql.toString());
			renderText(JsonKit.toJson(res.getList(), 5));
		} catch (Exception e) {
			renderText("error:" + e.toString());
		}
	}

	public void search_media_base_2() {
		try {
			int page_id = TyuServerUtils.getParamInt(this, "page_id", 0);
			int page_size = TyuServerUtils.getParamInt(this, "page_size", 10);
			int sort_id = TyuServerUtils.getParamInt(this, "sort", -1);
			String key = TyuServerUtils.getParamString(this, "key", false);
			StringBuilder sql = new StringBuilder();
			sql.append("from media_base where (mb_state=1 or mb_state=10)");

			if (sort_id > 0) {
				sql.append(" and mb_ms_id=" + sort_id);
				sql.append(" order by mb_id desc,mb_weight desc");
			} else if (!StringUtils.isEmpty(key)) {
				String word = "'%" + key + "%'";
				sql.append(" and mb_keys like " + word);
				sql.append(" order by mb_id desc,mb_weight desc");
			} else {
				throw new Exception("media file not found!");
			}
			Page res = Db.paginate(page_id, page_size, "select * ",
					sql.toString());
			renderText(JsonKit.toJson(res.getList(), 5));
		} catch (Exception e) {
			renderText("error:" + e.toString());
		}
	}

	public void get_media_base_by_id() {
		try {
			// int id = TyuServerUtils.getParamInt(this, "id", 0);
			String idsString = getPara("id");
			int id = 0;
			if (idsString != null && !idsString.equals("")
					&& idsString.indexOf('_') > -1) {
				// 用户分享
				id = Integer.parseInt(idsString.split("\\_")[0]);
				// System.out.println(id);
				if (id > 0) {
					MediaBaseUser mbu = MediaBaseUser.dao.findById(id);
					// System.out.println();
					if (mbu != null
							&& !mbu.get("video_state").toString().equals("0")) {
						renderText(JsonKit.toJson(mbu, 3));
						return;
					} else {
						renderText(null);
						return;
					}
				}
			} else {
				// 闪铃库分享
				if (idsString != null && !idsString.equals(""))
					id = Integer.parseInt(idsString);
				else
					id = -1;
				if (id > 0) {
					SmbData data = (SmbData) SmbData.dao.findById(id);
					if (data != null) {
						renderText(JsonKit.toJson(data, 3));
						return;
					}
					throw new Exception("no such video");
				}
				SmbData data = (SmbData) SmbData.dao
						.findFirst("select * from media_base where (mb_state=1 or mb_state=10) order by rand()");
				if (data != null) {
					renderText(JsonKit.toJson(data, 3));
					return;
				}
				throw new Exception("no such video");
			}
		} catch (Exception e) {
			renderText("error:" + e.toString());
			// e.printStackTrace();
		}
	}

	public void getUserVideoByUserID() {
		try {
			String realPath = getRequest().getServletContext().getRealPath("/");
			String dir_name = "media_base_user/";
			String dir = realPath + dir_name;
			// String videoName = getPara("videoName");
			int userId = TyuServerUtils.getParamInt(this, "userId", 0);
			// System.out.println("userId=" + userId);
			String sql = String
					.format("select * from media_base_user where (video_state='1'or video_state='10') and user_id='%d'",
							userId);
			// System.out.println("sql=" + sql);

			List<MediaBaseUser> mediaBaseUserList = MediaBaseUser.dao.find(sql);
			if (mediaBaseUserList != null) {
				// System.out.println("mediaBaseUserList"
				// + mediaBaseUserList.size());

				List<VideoInfo> listVideoInfos = new ArrayList<VideoInfo>();
				VideoInfo vInfo;
				File file;
				String fileName;
				MediaBaseUser mbu;
				for (int i = 0; i < mediaBaseUserList.size(); i++) {
					mbu = mediaBaseUserList.get(i);
					fileName = mbu.get("video_name");
					file = new File(dir, fileName);
					// System.out.println(mbu.get("id")+"");
					if (file.exists()) {
						vInfo = new VideoInfo(file.getName(), file.length());
						listVideoInfos.add(vInfo);
					} else {
						mbu.set("video_state", 0L);
						mbu.update();
					}
				}
				if (listVideoInfos != null && listVideoInfos.size() > 0)
					renderText(JsonKit.toJson(listVideoInfos));
			} else {
				renderText("");
			}
		} catch (Exception e) {
			renderText("error:" + e.toString());
		}
	}

	public void getUserVideoByFileName() {
		try {
			String realPath = getRequest().getServletContext().getRealPath("/");
			String dir_name = "media_base_user/";
			String dir = realPath + dir_name;
			// String videoName = getPara("videoName");
			int userId = TyuServerUtils.getParamInt(this, "userId", 0);
			String searchValue = TyuServerUtils.getParamString(this,
					"videoName", false);
			String sql = String
					.format("select id from media_base_user where video_name='%s' and (video_state='1'or video_state='10') and user_id='%d'",
							searchValue, userId);
			// System.out.println(sql + "%%");
			MediaBaseUser mediaBaseUser = MediaBaseUser.dao.findFirst(sql);
			if (mediaBaseUser != null) {
				renderText(mediaBaseUser.getLong("id") + "");
			}
		} catch (Exception e) {
			renderText("error:" + e.toString());
		}
	}

	public void deleteUserVideo() {
		try {
			int userId = TyuServerUtils.getParamInt(this, "userId", 0);
			String videoName = TyuServerUtils.getParamString(this, "videoName",
					false);
			// System.out.println(userId + "" + videoName);
			if (videoName != null && !videoName.equals("")) {
				String sql = String
						.format("select id from media_base_user where video_name='%s' and user_id='%d'",
								videoName, userId);
				// System.out.println(sql);
				MediaBaseUser mediaBaseUser = MediaBaseUser.dao.findFirst(sql);
				if (mediaBaseUser != null) {
					mediaBaseUser.set("video_state", 7);
					mediaBaseUser.update();
					renderText("delete succeed");
				} else {
					renderText("not find video");
				}
			}
		} catch (Exception e) {
			renderText("error:" + e.toString());
		}
	}

	/*
	 * 
	 */
	public void hide_media_base() {
		try {
			int id = TyuServerUtils.getParamInt(this, "id", 0);
			SmbData data = (SmbData) SmbData.dao.findById(Integer.valueOf(id));
			if (data != null) {
				data.set("mb_state", Integer.valueOf(0));
				data.update();
				renderText("删除成功");
				return;
			}
			throw new Exception("no such video");
		} catch (Exception e) {
			renderText("error:" + e.toString());
		}
	}

	/*
	 * 
	 */
	public void get_user_count() {
		try {
			UserData rec = (UserData) UserData.dao
					.findFirst("select count(*) from user_info");
			renderText(JsonKit.toJson(rec));
		} catch (Exception e) {
			renderText("error:" + e.toString());
		}
	}

	/*
	 * 
	 */
	public void swupdate() {
		try {
			String real_path = getRequest().getServletContext()
					.getRealPath("/");
			String file = real_path + "version.txt";
			String data = TyuFileUtils.loadFromFile(file);
			renderText(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * 
	 */
//	public void get_shinning_friends_2nd() {
//		try {
//			getRequest().setCharacterEncoding("UTF-8");
//			String tmp = TyuServerUtils.getPostStringZip(getRequest());
//
//			String raw = Coding.hex2bin(tmp);
//			JSONObject obj = JSONObject.parseObject(raw);
//			JSONArray array = obj.getJSONArray("phones");
//			StringBuilder sb = new StringBuilder();
//			sb.append("select * from user_info left join media_base on user_info.ui_mb_id = media_base.mb_id where ui_phone in (");
//
//			for (int i = 0; i < array.size(); ++i) {
//				String number = array.getString(i);
//				if (i == array.size() - 1)
//					sb.append(String.format("'%s'", new Object[] { number }));
//				else {
//					sb.append(String.format("'%s',", new Object[] { number }));
//				}
//			}
//			sb.append(")");
//
//			List<UserData> results = UserData.dao.find(sb.toString());
//			renderText(JsonKit.listToJson(results, 5));
//		} catch (Exception e) {
//			renderText("error:" + e.toString());
//		}
//	}

	/*
	 * 
	 */

	public void post_media_base_by_user() {
		UploadFile file = null;
		try {
			String realPath = getRequest().getServletContext().getRealPath("/");
			String dir_name = "media_base_user/";
			String dir = realPath + dir_name;
			new File(dir).mkdirs();

			String tmp_name = "tmp/";
			String dir_tmp = realPath + tmp_name;
			new File(dir_tmp).mkdirs();
			file = getFile("uploadfile", dir_tmp);
			if (file == null) {
				throw new Exception("media file not found!");
			}
			int user_id = TyuServerUtils.getParamInt(this, "userId_1", 0);
			String fileName = TyuServerUtils.getParamString(this, "filename",
					false);

			MediaBaseUser mbu;
			mbu = MediaBaseUser.dao
					.findFirst(String
							.format("select * from media_base_user where video_name='%s' and  user_id='%d'",
									fileName, user_id));
			File src = new File(dir, fileName);
			if (mbu != null) {
				if (src.length() != file.getFile().length()) {
					src.delete();
					file.getFile().renameTo(src);
				} else {
					file.getFile().delete();
				}
				mbu.set("video_state", 1);
				boolean res = mbu.update();
				if (res) {
					renderText(JsonKit.toJson(mbu));
				} else {
					renderText("error:upload fail");
				}
				return;
			}
			file.getFile().renameTo(src);

			String file_name = src.getAbsolutePath();
			VideoFirstThumbTaker tt = new VideoFirstThumbTaker("ffmpeg");
			String img_path = file_name
					.substring(0, file_name.lastIndexOf(".")) + ".jpg";
			tt.getThumb_2(file_name, img_path);

			mbu = new MediaBaseUser();
			mbu.set("user_id", user_id);
			mbu.set("upload_time",
					TyuServerUtils.date_format_mm.format(new Date()));
			mbu.set("video_name", file.getFile().getName());
			mbu.set("video_state", 1);

			boolean res = mbu.save();
			if (res)
				renderText(JsonKit.toJson(mbu));
			else {
				renderText("error:upload fail");
			}
		} catch (Exception e) {
			renderText("error:" + e.toString());
			e.printStackTrace();
			if (file.getFile().exists()) {
				file.getFile().delete();
			}
		}
	}

	/*
	 * 172.16.0.6
	 */
	public void modify_media_base() {
		List<UploadFile> imageList = null;
		try {
			imageList = getFiles();
			System.out.println(imageList.size() + " imageList size");
			String name = TyuServerUtils.getParamString(this, "name", true);
			String content = TyuServerUtils.getParamString(this, "content",
					true);
			int weight = TyuServerUtils.getParamInt(this, "weight", 0);
			String keyword = TyuServerUtils.getParamString(this, "keyword",
					true);
			String author = TyuServerUtils.getParamString(this, "author", true);
			String recommend = TyuServerUtils.getParamString(this, "recommend",
					false);
			boolean isrec = false;
			if ((recommend != null) && (recommend.equals("on"))) {
				isrec = true;
			}
			int mb_id = TyuServerUtils.getParamInt(this, "mb_id", 0);

			if (mb_id > 0) {
				SmbData ret = (SmbData) SmbData.dao.findById(Integer
						.valueOf(mb_id));
				if (ret != null) {
					if (imageList != null && imageList.size() > 0) {
						String videoFileName = ret.get("mb_video_name");
						// System.out.println(videoFileName + "videoFileName");
						String img_path = videoFileName.substring(0,
								videoFileName.lastIndexOf(".")) + ".jpg";
						// System.out.println(img_path + "img_path");
						File imageFile = new File(dir, img_path);
						// System.out.println(imageFile.getAbsolutePath());
						// System.out.println("yyyysize" + imageFile.length());
						// System.out.println("nnnnsize"
						// + imageList.get(0).getFile().length());
						// System.out.println(imageFile.exists()
						// + "imageFile.exists()");
						if (imageFile.exists()) {
							boolean b = imageFile.delete();
							// System.out.println(b + "imageFile.delete();");
						}

						imageList.get(0).getFile().renameTo(imageFile);
						// System.out.println(imageFile.length() + "");
					}

					ret.set("mb_keys", keyword);
					ret.set("mb_weight", Integer.valueOf(weight));
					ret.set("mb_name", name);
					ret.set("mb_author", author);
					if (isrec)
						ret.set("mb_state", Integer.valueOf(10));
					else {
						ret.set("mb_state", Integer.valueOf(1));
					}
					ret.set("mb_content", content);
					ret.update();
					renderText("提交完成");
					return;
				}
			}
			renderText("提交失败");
		} catch (Exception e) {
			if (imageList != null && imageList.get(0).getFile() != null)
				imageList.get(0).getFile().delete();
			renderText("error:" + e.toString());
		}
	}

	public void post_media_base() {
		File imageFile = null;
		File videoFile = null;

		File videorenameFile = null;
		File fileImageRenameFile = null;
		try {
			new File(dir).mkdirs();
			// System.out.println(dir + "dir2");
			List<UploadFile> fileList = getFiles();

			if (fileList == null || fileList.size() < 1) {
				throw new NullPointerException("未上传视频文件1");
			}

			if (fileList.size() == 1
					&& fileList.get(0).getFileName().lastIndexOf(".mp4") > -1) {
				videoFile = fileList.get(0).getFile();
				// System.out.println(videoFile.getName());
			} else if (fileList.size() == 2) {
				if (fileList.get(0).getFileName().lastIndexOf(".mp4") > -1) {
					videoFile = fileList.get(0).getFile();
					imageFile = fileList.get(1).getFile();
				} else {
					videoFile = fileList.get(1).getFile();
					imageFile = fileList.get(0).getFile();
				}
			} else {
				// System.out.println(fileList.get(0).getFileName()
				// .lastIndexOf(".mp4")
				// + " / " + fileList.size());
				throw new NullPointerException("参数错误0");
			}
			StringBuffer sbBuffer = new StringBuffer();
			Random random = new Random();
			sbBuffer.append(random.nextInt(10000));
			sbBuffer.append(random.nextInt(10000));
			sbBuffer.append(random.nextInt(10000));
			sbBuffer.append(random.nextInt(10000));
			sbBuffer.append(random.nextInt(10000));
			sbBuffer.append(random.nextInt(10000));
			// ///

			videorenameFile = new File(dir, System.currentTimeMillis()
					+ sbBuffer.toString() + ".mp4");
			videoFile.renameTo(videorenameFile);
			// System.out.println(videorenameFile.getName());

			String name = TyuServerUtils.getParamString(this, "name", true);
			// System.out.println("name" + name);
			String content = TyuServerUtils.getParamString(this, "content",
					true);
			// System.out.println("content" + content);
			// int sort = TyuServerUtils.getParamInt(this, "sort", -1);
			// System.out.println("sort" + sort);
			int weight = TyuServerUtils.getParamInt(this, "weight", 0);
			// System.out.println(weight + " weight");
			String keyword = TyuServerUtils.getParamString(this, "keyword",
					true);
			// System.out.println("keyword" + keyword);
			String author = TyuServerUtils.getParamString(this, "author", true);
			// System.out.println("author" + author);

			if (imageFile != null) {
				// System.out.println(imageFile.getName());
				String img_path = videorenameFile.getName().substring(0,
						videorenameFile.getName().lastIndexOf("."))
						+ ".jpg";
				fileImageRenameFile = new File(dir, img_path);
				imageFile.renameTo(fileImageRenameFile);
			} else {
				// linux 下开启
				Mp4Box.addhint(videorenameFile.getAbsolutePath(), null);
				String file_name = videorenameFile.getAbsolutePath();
				VideoFirstThumbTaker tt = new VideoFirstThumbTaker("ffmpeg");
				String img_path = file_name.substring(0,
						file_name.lastIndexOf("."))
						+ ".jpg";
				tt.getThumb(file_name, img_path, 360, 640);
			}
			SmbData data = new SmbData();
			data.set("mb_video_name", videorenameFile.getName());
			data.set("mb_name", name);
			data.set("mb_content", content);
			data.set("mb_keys", keyword);
			data.set("mb_upload_time",
					TyuServerUtils.date_format_mm.format(new Date()));
			data.set("mb_weight", Integer.valueOf(weight));
			data.set("mb_author", author);
			data.set("mb_favorite", 0);
			data.set("mb_md5", MD5Utils.getFileMD5String(videorenameFile));
			boolean res = data.save();
			if (res) {
				this.syncAllService();
			}
			renderText("upload" + res);
		} catch (Exception e) {
			// File imageFile=null;
			// File videoFile=null;
			if (imageFile != null)
				imageFile.delete();
			if (videoFile != null)
				videoFile.delete();

			if (videorenameFile != null)
				videorenameFile.delete();
			if (fileImageRenameFile != null)
				fileImageRenameFile.delete();
			renderText("error:" + e.toString());
			e.printStackTrace();
		}
	}

	public void post_user_data() {
		try {
			String realPath = getRequest().getServletContext().getRealPath("/");
			String dir_name = "user_image/";
			String dir = realPath + dir_name;
			new File(dir).mkdirs();
			UploadFile file = getFile("file", dir);

			String uuid = TyuServerUtils.getParamString(this, "uuid", true);
			String imei = TyuServerUtils.getParamString(this, "imei", true);
			int ui_id = TyuServerUtils.getParamInt(this, "id", 0);
			int ui_mb_id = TyuServerUtils.getParamInt(this, "mb_id", 0);
			String name = TyuServerUtils.getParamString(this, "name", false);
			String phone = TyuServerUtils.getParamString(this, "phone", false);

			UserData res = (UserData) UserData.dao
					.findFirst(String
							.format("select * from user_info where ui_uuid='%s' order by ui_id desc",
									new Object[] { uuid }));
			if (res == null) {
				res = new UserData();
				if (file != null) {
					String url = ConfigDefine.HOST + dir_name;
					res.set("ui_image", url + file.getFileName());
				}
				res.set("ui_uuid", uuid);
				res.set("ui_imei", imei);
				res.set("ui_mb_id", Integer.valueOf(ui_mb_id));
				res.set("ui_name", name);
				res.set("ui_phone", phone);
				res.save();
			} else {
				if (file != null) {
					String url = ConfigDefine.HOST + dir_name;
					res.set("ui_image", url + file.getFileName());
				}
				res.set("ui_mb_id", Integer.valueOf(ui_mb_id));
				if (!StringUtils.isEmpty(name))
					res.set("ui_name", name);
				if (!StringUtils.isEmpty(phone))
					res.set("ui_phone", phone);
				res.update();
			}
			renderText(JsonKit.toJson(res));
		} catch (Exception e) {
			renderText("error:" + e.toString());
		}
	}

	public void update_User_VideoState() {
		String tagString = getPara("tag");
		int page_id = TyuServerUtils.getParamInt(this, "id", -1);
		if (tagString != null && !tagString.equals("")) {
			switch (tagString) {
			case "through":
				if (page_id > 0) {
					MediaBaseUser mbu = (MediaBaseUser) MediaBaseUser.dao
							.findById(page_id);
					mbu.set("video_state", "10");
					mbu.update();
					renderText("ok");
				} else {
					renderText("fail");
				}
				break;
			case "noThrough":
				if (page_id > 0) {
					MediaBaseUser mbu = (MediaBaseUser) MediaBaseUser.dao
							.findById(page_id);
					mbu.set("video_state", "0");
					mbu.update();
					renderText("ok");
				} else {
					renderText("fail");
				}
				break;
			}
		} else {
			renderText("fail");
		}
	}

	// ////////////////////////////////////////////////////////////////////////
	// *******************************audio***********************************//
	// ////////////////////////////////////////////////////////////////////////

	public void update_audio_sort() {
		try {
			String idsString = getPara("id");
			String sortName = getPara("sortname");
			AudioSortList asl;
			if (idsString != null && !idsString.equals(""))// update
			{
				asl = AudioSortList.dao.findById(Integer.parseInt(idsString));
				asl.set("sort_name", sortName);
				asl.update();
				renderText("修改成功");
			} else {// add
				asl = new AudioSortList();
				asl.set("sort_name", sortName);
				asl.save();
				renderText("新增成功");
			}
		} catch (Exception e) {
			renderText("fail" + e.getMessage());
		}
	}

	public void get_audio_sort() {
		try {
			List<AudioSortList> asList = AudioSortList.dao
					.find("select * from  audio_sort_list");
			renderText(JsonKit.toJson(asList));
		} catch (Exception e) {
			renderText("fail" + e.getMessage());
		}
	}

	public void audio_sort_init() {
		try {
			List<AudioSortList> asList = AudioSortList.dao
					.find("select *from audio_sort_list");

			if (asList != null) {
				renderText(JsonKit.toJson(asList));
			}
		} catch (Exception e) {
			// e.printStackTrace();
		}
	}

	public void submitAudio() {
		try {
			UploadFile file;
			String realPath = getRequest().getServletContext().getRealPath("/");
			String dir_name = "audio_base_list/";
			String dir = realPath + dir_name;
			new File(dir).mkdirs();
			// System.out.println(dir);
			file = getFile("file", dir);
			file2 = new File(dir, System.currentTimeMillis() + ".m4a");
			file.getFile().renameTo(file2);
			AudioBaseList abl = null;
			String audioName = TyuServerUtils.getParamString(this, "audioName",
					false);
			String audioAuthor = TyuServerUtils.getParamString(this,
					"audioAuthor", false);
			String audioContext = TyuServerUtils.getParamString(this,
					"audioContext", false);
			int sort = TyuServerUtils.getParamInt(this, "sort", 0);
			// System.out.println(audioName + "12313 " + audioAuthor
			// + audioContext + sort);
			if (isEmpty(audioName) && isEmpty(audioContext)
					&& isEmpty(audioAuthor)) {
				abl = new AudioBaseList();
				abl.set("audio_name", audioName);
				abl.set("audio_author", audioAuthor);
				abl.set("audio_context", audioContext);
				abl.set("audio_sort_id", sort);
				abl.set("audio_state", 1);
				abl.set("audio_upload_time",
						TyuServerUtils.date_format_mm.format(new Date()));
				abl.set("audio_audioname", file2.getName());
				abl.save();
				renderText("提交完成");
				// System.out.println("1");
				return;
			} else {
				renderText("提交失败");
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			renderText("error:" + e.getMessage());
			return;
		}
	}

	public static boolean isEmpty(String str) {
		if (str != null && !str.equals(""))
			return true;
		else
			return false;
	}

	public void getAudioList() {
		List<AudioBaseList> abl = AudioBaseList.dao
				.find("SELECT * FROM audio_base_list where audio_state='1'");
		if (abl != null)
			renderText(JsonKit.toJson(abl));
		else
			renderText("null");
	}

	public void modify_audio_base_list() {
		try {
			String id = TyuServerUtils.getParamString(this, "id", false);
			String name = TyuServerUtils.getParamString(this, "name", false);
			String context = TyuServerUtils.getParamString(this, "context",
					false);
			String author = TyuServerUtils
					.getParamString(this, "author", false);
			String sotr_select = TyuServerUtils.getParamString(this,
					"sotr_select", false);

			if (isEmpty(id) && isEmpty(name) && isEmpty(context)
					&& isEmpty(author) && isEmpty(sotr_select)) {
				AudioBaseList sbl = AudioBaseList.dao.findById(Integer
						.parseInt(id));
				sbl.set("audio_name", name);
				sbl.set("audio_author", author);
				sbl.set("audio_context", context);
				sbl.set("audio_sort_id", Integer.parseInt(sotr_select));
				sbl.update();
				renderText("更新成功");
			} else {
				renderText("参数错误");
			}
		} catch (Exception e) {
			// e.printStackTrace();
			renderText("参数错误" + e.getMessage());
		}

	}

	public void deleteAudio() {
		try {
			String id = TyuServerUtils.getParamString(this, "id", false);
			if (isEmpty(id)) {
				AudioBaseList ab = AudioBaseList.dao.findById(Integer
						.parseInt(id));
				ab.set("audio_state", 0);
				ab.update();
				renderText("刪除成功");
			}

		} catch (Exception e) {
			// e.printStackTrace();
			renderText("刪除失败" + e.getMessage());
		}
	}

	//instantaneous obj
	public void computeMd5() {
		try {
			List<SmbData> mediaBase = SmbData.dao
					.find("select * from media_base where (mb_state='1'or mb_state='10')");
			Iterator<SmbData> iteratorSmbData = mediaBase.iterator();
			SmbData smbData;
			File videofile;
			while (iteratorSmbData.hasNext()) {
				smbData = iteratorSmbData.next();
				videofile = new File(dir, smbData.getStr("mb_video_name"));
				if (!videofile.exists())
					continue;
				smbData.set("mb_md5", MD5Utils.getFileMD5String(videofile));
				smbData.update();
			}
			renderText("ok");
		} catch (Exception e) {
			e.printStackTrace();
			renderText("error" + e.getMessage());
		}
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// ////////////////////////////////////////////视频分类代码////////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void addOrUpdateSortList() {
		try {
			String realPath = getRequest().getServletContext().getRealPath("/");
			String dir_name = "video_sort_image/";
			String dir = realPath + dir_name;
			new File(dir).mkdirs();
			File chang = null;
			File lue = null;

			UploadFile changupload = getFile("chang", dir);
			UploadFile lueupload = getFile("lue", dir);
			if (changupload != null) {
				chang = new File(dir, System.currentTimeMillis() + ".jpg");
				changupload.getFile().renameTo(chang);
			}
			if (lueupload != null) {
				lue = new File(dir, System.currentTimeMillis() + ".jpg");
				lueupload.getFile().renameTo(lue);
			}
			String sortNameString = TyuServerUtils.getParamString(this,
					"sortname", true);
			String remark = TyuServerUtils.getParamString(this, "remark", true);
			int sortState = TyuServerUtils.getParamInt(this, "sortstate", -1);
			// 判断新增或者修改
			if (sortState == -1) {
				if (sortNameString != null && !sortNameString.equals("")) {
					MediaSortList mslList = new MediaSortList();
					mslList.set("ms_name", sortNameString);
					mslList.set("ms_state", 1);
					if (chang != null)
						mslList.set("ms_changimageurl", chang.getName());
					if (lue != null)
						mslList.set("ms_lueimageurl", lue.getName());
					mslList.set("ms_remark", "normal");
					if (remark != null)
						mslList.set("ms_remark", remark);
					mslList.save();
				}
			} else {
				int sortID = TyuServerUtils.getParamInt(this, "sortid", -1);
				MediaSortList msl = MediaSortList.dao.findById(sortID);
				if (msl != null) {
					msl.set("ms_name", sortNameString);
					msl.set("ms_state", sortState);
					if (chang != null)
						msl.set("ms_changimageurl", chang.getName());
					if (lue != null)
						msl.set("ms_lueimageurl", lue.getName());
					if (remark != null)
						msl.set("ms_remark", remark);
					msl.update();
				}
			}
			renderText("ok");
		} catch (Exception e) {
			e.printStackTrace();
			renderText("新增失败！！");
		}
	}

	public void getVideoSortList() {
		List<MediaSortList> mslLis = MediaSortList.dao
				.find("select * from media_sort_list");
		if (mslLis != null && mslLis.size() > 0) {
			String json = JsonKit.toJson(mslLis);
			renderText(json);
		} else
			renderText("error");
	}

	public void getOnLineVideoSortList() {
		List<MediaSortList> mslLis = MediaSortList.dao
				.find("select * from media_sort_list where ms_state='1'");
		if (mslLis != null && mslLis.size() > 0) {
			String json = JsonKit.toJson(mslLis);
			renderText(json);
		} else
			renderText("error");
	}

	public void getStaticSortList() {
		List<MediaSortList> mslLis = MediaSortList.dao
				.find("select * from media_sort_list where ms_state='2'");
		if (mslLis != null && mslLis.size() > 0) {
			String json = JsonKit.toJson(mslLis);
			renderText(json);
		} else
			renderText("error");
	}

	// lead_id 引导id
	// lead_num 步骤编号
	// lead_step 引导步骤文字
	// lead_intent_first 引导跳转包名
	// lead_intent_last 引导跳转类名
	// lead_intent_word catch時提示文字
	// lead_videourl 引导视频url
	// lead_os 操作系统
	// lead_brand_default 默认品牌
	public void getLoadBoot() {
		try {
			String os = TyuServerUtils.getParamString(this, "lead_os", true);
			String brand = TyuServerUtils.getParamString(this, "lead_brand",
					true);
			List<LoadBoot> list = LoadBoot.dao.find(String.format(
					"select * from loadboot where lead_os='%s'", os));
			if (list != null && list.size() > 0) {
			} else {
				list = LoadBoot.dao.find(String.format(
						"select * from loadboot where lead_brand_default='%s'",
						brand));
				if (list == null || list.size() < 1) {
					renderText("Not found boot");
					return;
				}
			}
			renderText(JsonKit.toJson(list));
		} catch (Exception e) {
			e.printStackTrace();
			renderText("error" + e.getMessage());
		}

	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/*
	 * 有可能发送给第一台服务器同步信息时受到了阻塞,所以多线程启动多个urlhttpconnection
	 */
	class sync implements Runnable {
		private String url;

		public sync(String url) {
			super();
			this.url = url;
		}

		@Override
		public void run() {
			HttpUrlConnection.sendGet(url, "utf-8");
			TyuServerUtils.logDeBug("同步线程", "对" + url + "服务器发起同步请求!");
		}
	}
}
