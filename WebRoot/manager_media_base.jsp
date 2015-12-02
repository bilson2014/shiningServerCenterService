<%@page import="com.alibaba.fastjson.JSONObject"%>
<%@page import="com.alibaba.fastjson.JSONArray"%>
<%@page import="com.panfeng.shining.ConfigDefine"%>
<%@page import="com.panfeng.shining.utils.TyuServerUtils"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";

	int page_id = 1;
	int page_size = 6;
	if (request.getParameter("page") != null) {
		page_id = Integer.parseInt(request.getParameter("page"));
		if (page_id < 1)
			page_id = 1;
	}
	String tmp = TyuServerUtils.getInfo(ConfigDefine.HOST
			+ "smc/get_media_base2nd?page_id=" + page_id + "&page_size="
			+ page_size);
	JSONArray jarray = JSONArray.parseArray(tmp);

	tmp = TyuServerUtils.getInfo(ConfigDefine.HOST + "smc/get_sort_list");
	JSONArray jarray2 = JSONArray.parseArray(tmp);
	String page_name = "manager_media_base.jsp";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="内容提交页面">



</head>
<body>
	<center>管理视频</center>
	<br />
	<a href=<%=page_name + "?page=" + (page_id - 1)%>>上一页</a> 第<%=page_id%>页
	<a href=<%=page_name + "?page=" + (page_id + 1)%>>下一页</a>

	<table border="1" cellspacing="1">
		<tbody>
			<%
				int count = jarray.size();
				for (int i = 0; i < count; i++) {
					JSONObject obj = jarray.getJSONObject(i);
					String name = obj.getString("mb_name");
					String content = obj.getString("mb_content");
					String img_path = obj.getString("mb_image_path");
					String mb_keys = obj.getString("mb_keys");
					String author = obj.getString("mb_author");
					int mb_state = obj.getIntValue("mb_state");
					int mb_id = obj.getIntValue("mb_id");
					int weight = obj.getIntValue("mb_weight");
			%>
			<tr>
				<td width="400"><img align="middle" src=<%=img_path%>
					width="400">
				</td>
				<td>
					<form action="smc/modify_media_base" method="post"
						enctype="multipart/form-data">
						视频编号(只读):<br /> <input type="text" name="mb_id" value=<%=mb_id%> /><br />
						设置视频名字:<br /> <input type="text" name="name" value=<%=name%> /><br />
						设置视频作者:<br /> <input type="text" name="author" value=<%=author%> /><br />
						
						设置视频内容描述:<br /> <input type="text" name="content"
							value=<%=content%> /><br /> 设置关键字:<br /> <input type="text"
							name="keyword" value=<%=mb_keys%> /><br /> 是否强推(编辑人员强烈推荐):<br />
						<input type="checkbox" name="recommend"
							<%if (mb_state == 10)
					out.append("checked=\"true\"");%> /><br />
						设置视频优先级(只能输入数字，0表示最低):<br /> <input type="text" name="weight"
							value=<%=weight%>
							onkeyup="this.value=this.value.replace(/[^\d]/g,'')" /><br /> </select><br />
						<input type="submit" value="提交数据" /> <br />

					</form> <br /> <a href=<%="smc/hide_media_base?id=" + mb_id%>>删除该条视频</a>
				</td>

			</tr>
			<%
				}
			%>
		</tbody>
	</table>
</body>
</html>
