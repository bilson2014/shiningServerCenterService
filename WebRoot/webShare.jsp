<%@ page language="java" import="java.util.*" pageEncoding="utf-8"  contentType="text/html;charset=utf-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="闪铃,shining,好玩的视频彩铃">
<meta http-equiv="description" content="分享视频铃声">
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>闪铃-shining 分享视频</title>
<link href="style.css" rel="stylesheet">
<script src="js/jquery-1.10.2.min.js"></script>
<link rel="shortcut icon" href="images/icon.png" >
 <link rel="stylesheet" href="css/minimalist.css">		
<link rel="stylesheet" href="skin/minimalist.css">
   <!-- include flowplayer -->
   <script src="js/flowplayer.min.js"></script>
<script>
function s1(i){
if(i=='1')
{
 $(".s1").attr("src","images/img_2_2_2.png");
}else
{
 $(".s1").attr("src","images/16.png");
}
}

function s2(i){
if(i=='1')
{
 $(".s2").attr("src","images/x2.png");
}else
{
 $(".s2").attr("src","images/x1.png");
}
}

</script>
</head>
<%
    String video_path="";
String image_path="";
    String errorMessage1="";
    String error=request.getAttribute("error").toString();
    if(error!=null&&error.equals("Noresponse"))
    {
    	errorMessage1="视频不能播放";
    }else{
    video_path=request.getAttribute("share_video").toString();
    image_path=request.getAttribute("share_image").toString();
	String name=request.getAttribute("share_name").toString();
	}
%>

<body>
	<div align="center" >
	   <div style="background-color:#ff4a3e; height:80px">
		<img  src="images/03.png">
		</div>
		<br/>
		<br/>
		<%=errorMessage1%>
		<div class="divbody">
			<div class="allvedioview">
				<div class="video" >
				<%=errorMessage1%>
		<div id="id1" >
			<video height="55%"  id="video"  src="<%=video_path%>" controls="controls"  >您的浏览器不支持视频，请升级浏览器</video>
		</div>
				</div>
			</div>
		</div>
		<div id="xz" style="background-image:url(images/11.jpg); background-repeat:no-repeat; background-size:100%; height:400px ">
		  <div style="padding-top:74%">
		  		<a href= "http://www.shiningmovie.com/shiningApp/shining.apk"><img class="s1" style="width:50%" onmouseover="s1('2')" onmouseout="s1('1')"src="images/img_2_2_2.png"></a>
		  </div>
                <div><a href= "http://www.shiningmovie.com"><img class="s2" style="width:99% " onmouseover="s2('1')" onmouseout="s2('2')" src="images/x1.png"></a></div>
		</div>
	</div>
	
</body>
</html>
