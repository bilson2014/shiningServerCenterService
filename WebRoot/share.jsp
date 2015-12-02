<%@page import="com.alibaba.fastjson.JSONObject"%>
<%@page import="com.alibaba.fastjson.JSONArray"%>
<%@page import="com.panfeng.shining.ConfigDefine"%>
<%@page import="com.panfeng.shining.utils.TyuServerUtils"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8" contentType="text/html;charset=utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String id = request.getParameter("id");
	JSONObject jobj = null;
	if (id != null && id.length() > 0) {
		String tmp = TyuServerUtils.getInfo(ConfigDefine.HOST
				+ "smc/get_media_base_by_id?id=" + id);
		if (tmp != null && !tmp.startsWith("error"))
			jobj = JSONObject.parseObject(tmp);
		else
			jobj=null;
	}else{
		String tmp = TyuServerUtils.getInfo(ConfigDefine.HOST
				+ "smc/get_media_base_by_id");
		if (tmp != null && !tmp.startsWith("error"))
			jobj = JSONObject.parseObject(tmp);
		else
			jobj=null;
	}
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="内容提交页面">
</head>
<body>
<%
String agent=request.getHeader("user-agent");
String[] mobiles={"Android","Mobile","Phone","iPad","Symbian","WebOS","BlackBerry"};
boolean isMobile=false;

for(String mobileAgent : mobiles){
	if(agent.contains(mobileAgent)){
		isMobile = true;
		break;
	}
}
out.append(agent);

String video_path ="";
String image_path ="";
String name="";
if (jobj != null) {

	if(id!=null&&!id.equals(""))
	{
		if(id.lastIndexOf('_')>0)
		{
		   name="用户自定义";
		   String videoName=jobj.getString("video_name");
		   image_path=ConfigDefine.HOST+"media_base_user/"+videoName.substring(0,videoName.indexOf('.'))+".jpg";
		   if(isMobile)
		   {
			   video_path=ConfigDefine.HOST+"media_base_user/"+videoName;
		   }else
		   {
			   video_path=ConfigDefine.HOST+"media_base_user/"+videoName;
		   }
		}else{
			name = jobj.getString("mb_name");
			String videoname=jobj.getString("mb_video_name");
			image_path=ConfigDefine.HOST+"media_base/"+videoname.substring(0,videoname.indexOf('.'))+".jpg";
			video_path=ConfigDefine.HOST+"smc/getVideo?id="+id;
		}
		
	}
	request.setAttribute("share_video", video_path);
	request.setAttribute("share_image", image_path);
	request.setAttribute("share_name", name);
	request.setAttribute("error", "null");
}else{
	request.setAttribute("error", "Noresponse");
}
if(agent.contains("iPad"))
	isMobile=false;
//if(isMobile){
	request.getRequestDispatcher("mobileShare.jsp").forward(request, response);
//}	
//else{
	request.getRequestDispatcher("webShare.jsp").forward(request, response);
//}
	
%>

</body>
</html>
