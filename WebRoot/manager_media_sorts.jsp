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
	String tmp = TyuServerUtils.getInfo(ConfigDefine.HOST
			+ "smc/get_sort_list");
	
	JSONArray jarray = JSONArray.parseArray(tmp);
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
	<center>
	<table border="1" cellspacing="1">
		<tbody>
			<%
				int count = jarray.size();
				for (int i = 0; i < count; i++) {
					JSONObject obj = jarray.getJSONObject(i);
					String name = obj.getString("ms_name");
					String img_path = obj.getString("ms_image_path");
					int sort = obj.getIntValue("ms_id");
					int position = obj.getIntValue("ms_position");
			%>
			<tr>
				<td width="100"><img align="middle" src=<%=img_path%>
					width="100"></td>
				<td>
					<form action="smc/update_sort?type=add" method="post"
						enctype="multipart/form-data">
						选择分类封面图片:&nbsp;&nbsp;<input type="file" name="file" /><br /> <hr>
						设置分类名字:&nbsp;&nbsp; <input type="text" name="name" value=<%=name%> /><br /><hr>
						分类id(只读): &nbsp;&nbsp;<input type="text" name="sort" value=<%=sort%>
							readonly="readonly" /><br /><hr> 设置视频优先级(只能输入数字，0表示最低):&nbsp;&nbsp; <input
							type="text" name="weight" value=<%=position%>
							onkeyup="this.value=this.value.replace(/[^\d]/g,'')" /><br /><hr>
						要执行的操作:&nbsp;&nbsp; <select name="type">
							<option value="mod">修改</option>
							<option value="del">删除</option>

						</select><br /><hr> <input type="submit" value="提交数据" /> <br />

					</form>
				</td>

			</tr>
			<%
				}
			%>
		</tbody>
	</table>
	</center>
</body>
</html>
