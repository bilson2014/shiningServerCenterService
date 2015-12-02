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
<!--<center>增加分类</center>-->
     <center>
<form action="smc/update_sort?type=add" method="post"
		enctype="multipart/form-data">
		<table border=1px>
		<tr><td>
		选择分类封面图片:</td><td>
		<input type="file" name="file" /></td></tr>
		<tr><td>设置分类名字:</td><td>
		<input type="text" name="name" /></td></tr>
		<tr><td>设置视频优先级(只能输入数字，0表示最低):</td><td> <input type="text" name="weight"
			value="0" onkeyup="this.value=this.value.replace(/[^\d]/g,'')" /></td></tr>
		<tr><td><input type="submit" value="提交数据" /> </td><td><input type="reset" value="清空" /></td></tr>
		</table>
	</form>
	</center>
</body>
</html>
