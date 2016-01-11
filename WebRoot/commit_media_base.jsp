<%@page import="com.alibaba.fastjson.JSONObject"%>
<%@page import="com.alibaba.fastjson.JSONArray"%>
<%@page import="com.panfeng.shining.ConfigDefine"%>
<%@page import="com.panfeng.shining.utils.TyuServerUtils"%>
<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
<script src="js/jquery-1.10.2.min.js"></script>
<script src="js/jquery-form.js"></script>
<script type="text/javascript">
	function submitForms() {
		$("#show").show();
		$("#form_x").ajaxSubmit({
			url : "smc/post_media_base",
			success : function(data) {
				alert(data);
				if (data == 'xxxtrue') {
					$("#show").hide();
					location.reload(true);
				}
			}
		});
	}
</script>

</head>
<body>
	<center>
		<div>
			<form id="form_x" method="post" enctype="multipart/form-data">
				<span id="show" style="color: red; font-size: 40px; display: none;">正在上传。。。</span>
				<table border="1px">
					<tr>
						<td>选择视频文件:</td>
						<td><input type="file" name="file" /></td>
					</tr>

					<tr>
						<td>选择图片文件:</td>
						<td><input type="file" name="fileImage" /></td>
					</tr>

					<tr>
						<td>设置视频名字:</td>
						<td><input type="text" name="name" /></td>
					</tr>

					<tr>
						<td>设置视频作者:</td>
						<td><input type="text" name="author" /></td>
					</tr>

					<tr>
						<td>设置价格:</td>
						<td><input type="text" name="coin" /></td>
					</tr>



					<tr>
						<td>视频内容描述:</td>
						<td><textarea rows="10" cols="30" name="content"></textarea></td>
					</tr>

					<tr>
						<td>设置视频优先级<br>(只能输入数字，0表示最低):
						</td>
						<td><input type="text" name="weight" value="0"
							onkeyup="this.value=this.value.replace(/[^\d]/g,'')" /></td>
					</tr>

					<tr>
						<td>关键字(关键字之间用"|"<br>分隔,比如：运动|时尚|搞笑):
						</td>
						<td><input type="text" name="keyword" /></td>
					</tr>

					<tr>
						<td><input type="button" onclick="submitForms()" value="提交数据" />
						</td>
						<td><input type="reset" value="清空" /><br />
						</textarea></td>
					</tr>

				</table>
			</form>
		</div>
	</center>
</body>
</html>
