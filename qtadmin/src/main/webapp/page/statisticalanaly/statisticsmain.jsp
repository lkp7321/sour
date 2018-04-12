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
		<title>账户统计</title>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>/css/reset.css"/>
		<link rel="stylesheet" href="<%=basePath%>/css/style.css" /> 
		<link rel="stylesheet" type="text/css" href="<%=basePath%>/css/statisticsmain.css"/>
	</head>
	
	<body>
	    <div class="navcontent">
			 <div class="navHeader">
			 	<iframe src="<%=basePath%>page/head.jsp" border="0" width="100%" height="130" frameborder="0" scrolling="no" allowTransparency="true"></iframe>
			 </div>	
		</div>
		<div class="accountstatistics">
		       <div class="header">
			  	<span>账户统计</span>
			  </div> 
			  <div class="statcont">
			  	  <div class="navlist">
			  	  	 <ul>
                        <c:forEach items="${menu}" var="children">
                        
							 <!--	<dd id="/qtadmin${children.menuurl}">${children.menuname}</dd>  -->
								<li href="${pageContext.request.contextPath}${children.menuurl}"><a href="javascript:;">${children.menuname}</a> <span>></span></li>
					  	</c:forEach>
			  	  	 </ul>
			  	  </div>
			  	  <div class="contright">
			  	  	   <div class="accountmany">
		  	  	         <ul></ul>
		  	  	       
			  	  	   </div>
			  	  	   <div class="acountbody">
			  	  	   	  <iframe id='listiframe' name="listiframe" src="${pageContext.request.contextPath}/user/tosurvy.do" width="945px" class="listiframe"  border="0" frameborder="0" scrolling="no" ></iframe>
			  	  	   </div>
			  	  </div>
			  </div>
		</div>
	    <div class="footer">
		     <div class="footerline"></div>
		     <div class="footer_cont"> 
					<iframe src="<%=basePath%>page/footer.jsp" border="0" width="100%" height="100%" frameborder="0" scrolling="no" ></iframe>
			 </div>
		</div> 
		<script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript"></script>
		<%-- <script src="<%=basePath%>js/statisticsmain.js"></script> --%>
		<script>
		   $(function(){
			   var selectData=[],str='';
				// h=$('.acountbody').height();
				  

				 //左侧nav的点击事件
				  $('.navlist li:eq(0)').addClass('licolor')
				  $('.navlist li').click(function(){
				  	  var s=$(this).attr('href'),
				  	  	  txt=$(this).find('a').text(),
				  	  	  Theight;
				  	  $(this).addClass('licolor').siblings().removeClass('licolor')
				  	  $('#listiframe').attr('src', s );
				  	 
				  	/*  console.log( $('.acountbody').height() )
				  	 setTimeout(function(){
				  		 $('.statcont').height($('.acountbody').height()+50);
				  	 },1000) */
				  });
				 $.ajax({
				  		url:'${pageContext.request.contextPath}/user/getuseraccount.do',
				  		type:'post',
				  		dataType:'json',
				  		contentType:'application/json;charset=utf-8',
				  		async: false,
				  		before:function(){
				  		},
				  		success:function(data){
				  			  if(data.length==0){
				  				   str='<li style="width:100%"><a href="${pageContext.request.contextPath}/user/toUserManage.do?account=1">您还没有账户，马上去账户管理添加账户！</a></li>'
				  			  }else{
				  				for(k in data){
					  				 str+="<li><input style='margin:0,0,0,20%' type='checkbox' name='account' value='"+data[k]+"' /><span>"+data[k]+"</span></li>";
					  			  } 
				  			  }
				  			  $('.accountmany ul').html(str);
				  			  $('.accountmany li input:eq(0)').prop('checked',true)
				  		}
				  	});
		   })
		</script>
	</body>
</html>
