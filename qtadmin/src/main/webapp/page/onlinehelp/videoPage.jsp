<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>易宽 视频</title>
		<link rel="icon" href="" type="image/x-icon"/>  
		<link rel="stylesheet" type="text/css" href="<%=basePath%>/css/video-js.css"/>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>/css/reset.css"/>
		<link rel="stylesheet" href="<%=basePath%>/css/style.css" /> 
		<style>
			.videoPage{ width: 1202px; height: 600px; margin: auto;  color: #909090;margin-top: 79px;}
			.videoPage .header{ height: 70px; line-height: 70px; border-bottom: 1px solid #EBEBEB; } 
			.videoPage .videos{width:50%; height: 100%; margin:auto;margin-top: 30px;}
		    
		</style>
	</head>
	
	<body>
	    <!--导航部分-->
		<div class="navs">
			<div class="nav_wrap" id='mark_wrap'>
				<div class="w_1180"> 
					<iframe src="<%=basePath%>page/head.jsp" border="0" width="100%" height="130" frameborder="0" scrolling="no" ></iframe>
				</div>
			</div>
		</div>
		<div class="videoPage">
		      <div class="header">
			  	<span>易宽视频</span>
			  </div> 
			  <div class="videos">
			     <video id="my-video" class="video-js vjs-default-skin  vjs-big-play-centered" controls preload="none" width="601" height="450"
				      poster="http://video-js.zencoder.com/oceans-clip.png"
				      data-setup="{}">
				    <source src='' type='video/mp4' />
				     <p class="vjs-no-js"> To view this video please enable JavaScript, and consider upgrading to a web browser that <a href="http://videojs.com/html5-video-support/" target="_blank">supports HTML5 video</a> </p>
				    
				  </video> 
				  <%-- <video id="my_video_1" class="video-js vjs-default-skin" width="601" height="450"
                    controls preload="none" poster='http://video-js.zencoder.com/oceans-clip.jpg'
                    data-setup='{ "aspectRatio":"640:267", "playbackRates": [1, 1.5, 2] }'>
                    <source src="<%=basePath%>video/Yikuanintro.mp4" type='video/mp4' />
                    
                  </video>   --%>
				 
			  </div>
		</div>
		
		<div class="footer">
		     <div class="footerline"></div>
		     <div class="footer_cont"> 
					<iframe src="<%=basePath%>page/footer.jsp" border="0" width="100%" height="100%" frameborder="0" scrolling="no" ></iframe>
			 </div>
			 
		</div>
		<!-- <script src="http://html5media.googlecode.com/svn/trunk/src/html5media.min.js"></script> -->
		<script src="<%=basePath%>js/video.min.js" type="text/javascript"></script>
	    <script src="<%=basePath%>js/zh-CN.js" type="text/javascript"></script>
		<script src="<%=basePath%>js/videojs-ie8.min.js" type="text/javascript"></script> 
		<script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript"></script>
		<script type="text/javascript">
		    var videoUrl = sessionStorage.getItem('src');
			var sourceDom = $("<source src=\""+ videoUrl +"\">");
		        $("#my-video").append(sourceDom);
		     videojs.options.flash.swf = "video-js.swf";
			 var myPlayer = videojs('my-video');
			 videojs("my-video").ready(function(){
			        var myPlayer = this;
			        myPlayer.play();
			 });  
		</script>
	</body>
</html>
