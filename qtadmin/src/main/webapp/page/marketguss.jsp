<%@ page language="java" import="java.util.*"  pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta content="text/html; utf-8">
<title>市场分析</title>
<link rel="stylesheet" href='<%=basePath%>css/reset.css'>
<link rel="stylesheet" href="<%=basePath%>css/style.css" />
<link rel="stylesheet" href="<%=basePath%>layui/css/layuichange.css"  media="all">
</head>
<body>
<!--导航部分-->
<div class="navs">
	<div class="nav_wrap" id='mark_wrap'>
		<div class="w_1180"> 
			<iframe src="<%=basePath%>page/head.jsp" id="frame" border="0" width="100%" height="130" frameborder="0" scrolling="no" allowTransparency="true"></iframe>
		</div>
	</div>
</div>
<!-- 页面主体部分-->
<div class="main_body">
	<p class="mark_guss"><span>市场分析</span></p>
	<div class="mark_main">
		<ul>
			<li class="sel_li" data-src="<%=basePath%>page/marketguess/historyprice.jsp"><a>历史价查询</a><span>></span></li>
			<c:if test="${sessionScope.name !=undefined}" >
			 	<li data-src="<%=basePath%>page/marketguess/obosmonit.jsp"><a>超买/卖监控</a><span>></span></li>
			</c:if>
		</ul>
		<div class="mark_data">
			<iframe id="change_src" src="<%=basePath%>page/marketguess/historyprice.jsp" frameborder="no" width='100%' height='500'></iframe>
		</div>
	</div>
</div>
<div class="footer">
		     <div class="footerline"></div>
		     <div class="footer_cont"> 
					<iframe src="<%=basePath%>page/footer.jsp" border="0" width="100%" height="100%" frameborder="0" scrolling="no" ></iframe>
			 </div>
			 
</div>
</body>
<script src='<%=basePath%>/js/jquery-1.9.1.min.js'></script>
<script src="<%=basePath%>layui/layui.all.js" charset="utf-8"></script>
<script type="text/javascript">
     //点击左侧list;
     $('.mark_main ul li').click(function(){
    	 $(this).siblings().removeClass('sel_li');
    	 $(this).addClass('sel_li');
    	 $('#change_src').attr( 'src',$(this).data('src') );
     });
     //判断是否有前台的session  存储;
     var data=sessionStorage.getItem('seardata'),
     	 username='${sessionScope.name}';
     if( data ){
    	 var pinzhong=data.split('-')[0],
    	 	 heyue=data.split('-')[1],
    	 	 userid=data.split('-')[2],
    	 	 obj=data.split('-')[3];
    	 $('#change_src').attr('src','<%=basePath%>page/marketguess/obosmonit.jsp' );
		 $('.mark_main ul li').siblings().removeClass('sel_li')
		 $('.mark_main ul li:eq(1)').addClass('sel_li');
    	 $.ajax({ 
   		 	url:'<%=basePath%>common/getParamDetail.do',
   			type:'post',
   			data:{'productname':pinzhong,'instrumentid':heyue,'userid':userid,'result':obj},
   			async:true,
   			success:function(data){
   				//请求回来的地址；
   				var page=JSON.parse(data);
   				sessionStorage.setItem('searda',page);
				  layer.open({
					area: ['60%', '400px'],
			    	type:2,
			    	title:'参数设置',
			    	content: ['/qtadmin/page/marketguess/setPara.jsp?userId='+username,'no']
			 	  });  
   			}
    	}); 
     }
</script>
</html>