<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>超买  超卖监控</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" href="<%=basePath%>layui/css/layuichange.css"  media="all">
	<script type="text/javascript" src="<%=basePath%>js/jquery-1.11.3.js"></script>
	<style>
		.par_top{
			width:100%;
			text-align:right;
			height: 14px;
		}
		.paraset{
			float:right;
			border:none;
			color:#fff;
			width:85px;
			height:24px;
			line-height:24px;
			background:none;
			background: #498EFC;
			font-size:13px;
			border-radius: 2px;
		}
		#demo{
			width:90%;
			margin:10px auto;
		}
		#demo thead td{
			font-family: .PingFangSC-Regular;
			font-size: 14px;
			color: #646F8B;
		}
		#demo tr td,tableList tr td{
			text-align:center;
		}
	</style>
  </head>
  <body>
  <div class="par_top">
  	<button class="paraset">参数设置</button>
  </div>
  	<table id="demo" lay-filter="test" class="layui-table" lay-skin="row" lay-even>
  		<thead>
  			<td>品种</td>
  			<td>价格类型</td>
  			<td>多空比</td>
  			<td>多分段</td>
  			<td>空分段</td>
  			<td>分段类型</td>
  			<td>合约</td>
  			<td>当前价格</td>
  			<td>所属档位</td>
  		</thead>
  		<tbody id="tableList"></tbody>
  	</table>
  </body>
  <script src="<%=basePath%>layui/layui.all.js" charset="utf-8"></script>
  <script src="<%=basePath%>js/handlebars.js"></script>
  <script id="table-template" type="text/x-handlebars-template">
	{{#each data}}
		<tr>
  			<td>{{productid}}</td>
  			<td>{{pricetype}}</td>
  			<td>{{bullandbearratio}}</td>
  			<td>{{longsegment}}</td>
  			<td>{{shortsegment}}</td>
  			<td>{{segmenttype}}</td>
  			<td>{{contractset}}</td>
  			<td>{{nowprice}}</td>
  			<td>{{belongstalls}}</td>
  		</tr>	
   {{/each}}
  </script>
  <script type="text/javascript">
	  var username='${sessionScope.name}',
	  	  userId,
	  	  id=" ";
	 // setInterval(function(){
		  	$.ajax({
		  		url:'/qtadmin/market/queryparamer?proId=&userId='+username+'&page=1&limit=30',
		  		type:'get',
		  		async:true,
		  		success:function(data){
		  			result = data;
	 				 var myTemplate = Handlebars.compile($("#table-template").html());
	 			        //注册一个比较数字大小的Helper,有options参数，块级Helper
	 			        Handlebars.registerHelper("compare",function(v1,v2,options){
	 		        		 if(v1>v2){
	 			                 //满足添加继续执行
	 			                 return options.fn(this);
	 			              }else{
	 			                 //不满足条件执行{{else}}部分
	 			                 return options.inverse(this);
	 			              } 	
	 			        });
	 			        //将json对象用刚刚注册的Handlebars模版封装，得到最终的html，插入到基础table中。
	 			        $('#tableList').html(myTemplate(result) );
	 			        
		  		},error:function(){
		  			console.log('error');
		  		}
		  	});
	/// },1000);
	  //参数设置;
	  $('.paraset').click(function(){
		   parent.layer.open({
			area: ['60%', '400px'],
	    	type:2,
	    	title:'参数设置',
	    	content: ['/qtadmin/page/marketguess/setPara.jsp?userId='+username,'no'] 
	 	  });  
	  });
  </script>
</html>
