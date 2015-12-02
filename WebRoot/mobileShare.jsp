<%@ page language="java" import="java.util.*" pageEncoding="utf-8"  contentType="text/html;charset=utf-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
    String video_path="";
    String errorMessage1="";
    String image_path="";
    String name="";
    String error=request.getAttribute("error").toString();
    if(error!=null&&error.equals("Noresponse"))
    {
    	errorMessage1="视频不能播放";
    }else{
    video_path=request.getAttribute("share_video").toString();
    image_path=request.getAttribute("share_image").toString();
    name=request.getAttribute("share_name").toString();
	}
%>
<html>
	<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="闪铃,shining,视频铃声">
<meta http-equiv="description" content="分享视频铃声">

<title>闪铃-shining 分享视频铃声</title>
		<style type="text/css">
		
 <link rel="stylesheet" href="css/minimalist.css">		
body {
	margin: 0px;
	padding: 0px;
}

.divhead {
	width: 100%;
	height: 11%;
	background-color: #f0f2f5;
}

.headimg {
	margin-left: 2%;
	margin-top: 2%;
}

.allvedioview {
	width: 100%;
	height: 40%;
}


.video {
	width: 100%;
	margin-top: 1%;
	align:center
}



.divbuttonall {
	width: 100%;
	height: 40%;

}

.button {
	widows: 80%;
	height: 40%;
}

.word{
	width:1000px;
        background-color:white;
        margin-left:2%
}       
        
        
.bottomimg{
	position:fixed;
	bottom:1%;
	width:100%;
    background-color:#fe5453;
}

.videoname{
	position:relative;
	align:left;
	top:30px;
	font-size:42px;
	color:#5A5F5E;
	width:100%;
	background:#f0f2f5;
}

#bg{
   display: none;
   position: absolute;
   top: 0px;
   width: 100%;
   height: 100%;
   background-color: #000000;
   z-index:1001;
   -moz-opacity: 0.7;
   opacity:.70;
   filter: alpha(opacity=70);}
   #show{
    margin:0px;
    padding:0px;
    display: none;
    position: absolute;  
    width: 100%; height: 15%; 
    z-index:1002; 
   }
}
</style>

<link rel="stylesheet" href="skin/minimalist.css">
 <script src="//code.jquery.com/jquery-1.11.0.min.js"></script>

   <!-- include flowplayer -->
   <script src="js/flowplayer.min.js"></script>

 <script language="javascript" type="text/javascript">
 function showdiv() { 
 document.getElementById("bg").style.display ="block";
 document.getElementById("show").style.display ="block";
 }
 function hidediv() {
 document.getElementById("bg").style.display ='none';
 document.getElementById("show").style.display ='none';
 }
 
function iswechat(){
		if (typeof WeixinJSBridge == "undefined") {
		window.open("http://www.shiningmovie.com/shiningApp/shining.apk");
		setTimeout("self.close()",50000);
		} else {
		showdiv();
		}
	}
 </script>
</head>
	<body style="background-color:#f0f2f5">
		<div id="show" style=" background-color: #2e2b33;" >
       			<img src="images/img_x.png"  style="width: 80%;float:right;">
      		</div> 
		<div class="divhead">
			<img class="headimg" src="images/title_1.png" >
		</div>
		<div class="divbody">
			<div class="allvedioview">
			<center>
				<div class="video" >
					<div class="flowplayer" >
					<%=errorMessage1%>
         					<video id="video"  width="63%" poster="<%=image_path%>" controls>
       							<source type="video/mp4" src="<%=video_path%>">
		 					您的浏览器不支持video标签,请尝试升级浏览器
		 			        </video>
   					</div>
				</div>
				</center>
				<div class="videoname"></div>
			</div>
		</div>
        <div class="bottomimg">
			<img style="width:37%;margin-left:30%;margin-top:2%;margin-bottom:2%" src="images/down_2.png" onclick="iswechat();"/>
		</div>
		<div id="bg" onclick="hidediv();"/>
	  </body>

</html>
