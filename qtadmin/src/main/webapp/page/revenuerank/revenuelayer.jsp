<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>账户统计</title>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>/css/reset.css"/>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>/css/statisticsmain.css"/>
		<style>
			.accountmany ul{
				position:relative;
				top:-200px;
			}
			.accountmany{
				height:1px;
				line-height:1px;
			}
		</style>
	</head>
	<body>
		<div class="accountstatistics">
			  <div class="statcont">
			  	  <div class="navlist">
			  	  	 <ul>
			  	  	 </ul>
			  	  </div>
			  	  <div class="contright">
			  	  	   <div class="accountmany">
		  	  	         <ul></ul>
			  	  	   </div>
			  	  	   <div class="acountbody">
			  	  	   	  <iframe id='listiframe' name="listiframe" src="${pageContext.request.contextPath }/page/statisticalanaly/survy.jsp" width="945px" class="listiframe"  border="0" frameborder="0" scrolling="no" height="600px"></iframe>
			  	  	   </div>
			  	  </div>
			  </div>
		</div>
		<script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript"></script>
		<script>
			//请求左侧列表;
			$.ajax({
				url : '${pageContext.request.contextPath}/user/stasticMenu.do',
				type : 'post',
				dataType : 'json',
				contentType : 'application/json;charset=utf-8',
				async : false,
				success : function(data) {
					var str='';
					 for(var k=0;k<data.length;k++){
		  				 str+="<li href=${pageContext.request.contextPath}"+data[k].menuurl+"><a href='javascript:;'>"+data[k].menuname+"</a> <span>></span></li>";
					 }
					 $('.navlist ul').html( str );
				} 
			});
			
			var searUrl=$('html').context.URL.split('?'),
	  	 	    id=searUrl[1].split('=')[1],counts,str='';
	  	 	    data=id.split(',');
	  	 	     for(var k=0;k<data.length;k++){
	  				 str+="<li><input style='margin:0,0,0,20%' type='checkbox' name='account' value='"+data[k]+"' /><span>"+data[k]+"</span></li>";
				 }
				  $('.accountmany ul').html(str);
				  $('.accountmany li input').prop('checked',true);
		    //点击左侧li，进行切换；
		    $('.navlist li:eq(0)').addClass('licolor')
		 	$('.navlist li').click(function(){
		  	  var s=$(this).attr('href');
		  	  $(this).addClass('licolor').siblings().removeClass('licolor');
		  	  $('#listiframe').attr('src', s );
		    });
		</script> 
	</body>
</html>
