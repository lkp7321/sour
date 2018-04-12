<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>概况</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" href="<%=basePath%>layui/css/layui.css"  media="all">
	<script src="<%=basePath%>js/jquery-1.11.3.js"></script>
	<script src="<%=basePath%>js/handlebars.js"></script>
    <style type="text/css">
    
	    body,div,span,tr,td,table{
	    	margin: 0px;
	    	padding:0px;
	    }
  		body{
			text-align: center;
		}
		#d{
  			height: 526px;
  			width: 945px;
  		}
  		td{
  			height:19.8%;
  			width:5.4%;
  			text-align: center;
  		}
  		
  	</style>
  </head>
  
  <body>
  <div id="d">
    <!--  <span style="font-size:20px; font-weight: bold; text-align: center;">概况</span></br> -->
    <div id="div01">
    		<table  class="layui-table" id='tableList'>
    			
    		</table>
    	</div>
  </div>
  </body>
  <!--<td style="color:#498EFC">{{anp}}元</td>  -->
  <script id="table-template" type="text/x-handlebars-template">
		<tr>
    		<td>交易笔数（开仓、平仓）</td>
    		<td style="color:#498EFC;font-size:"16px">{{a}}笔</td>
    		<td >交易周期 </td>
    		<td style="color:#498EFC">{{c}}天</td>
    	</tr>
    	<tr>
    		<td>盈利笔数（平仓）</td>
    		<td style="color:#498EFC">{{p}}笔</td>
    		<td >亏损笔数（平仓）</td>
    		<td style="color:#498EFC">{{d}}笔</td>
    	</tr>
    	<tr>
    		<td>胜算</td>
    		<td style="color:#498EFC">{{pr}}</td>
    		<td >盈亏比 </td>
    		<td style="color:#498EFC">{{pdr}}</td>
    	</tr>
    	<tr>
    		<td>累计手续费</td>
			{{#compare acp 0}}
			<td style="color: #FC5353;">{{acp}}元</td>
			{{else}}
			<td style="color: green;">{{acp}}元</td> 		
			{{/compare}}
    	<td >累计净利润</td>
			{{#compare anp 0}}
			<td style="color: #FC5353;">{{anp}}元</td>
			{{else}}
			<td style="color: green;">{{anp}}元</td> 		
			{{/compare}}
    	</tr>
    	<tr>
    		<td>平均每笔盈利</td>
			{{#compare ap 0}}
			<td style="color: #FC5353;">{{ap}}元</td>
			{{else}}
			<td style="color: green;">{{ap}}元</td> 		
			{{/compare}}		
    		<td >平均每笔亏损</td>
			{{#compare ad 0}}
			<td style="color: #FC5353;">{{ad}}元</td>
			{{else}}
			<td style="color: green;">{{ad}}元</td> 		
			{{/compare}}
    	</tr>
    	<tr>
    		<td>平均每笔费用</td>
			{{#compare ae 0}}
				<td style="color: #FC5353;">{{ae}}元</td>
			{{else}}
				<td style="color: green;">{{ae}}元</td> 		
			{{/compare}}
    		<td >费用占比</td>
    		<td style="color:#498EFC">{{ep}}</td>
    	</tr>
    	<tr>
    		<td>最大连续亏损次数</td>
    		<td style="color:#498EFC">{{mda}}次</td>
    		<td >最大连续盈利次数</td>
    		<td style="color:#498EFC">{{mpa}}次</td>
    	</tr>
    	<tr>
    		<td>最大盈利率</td>
    		<td style="color:#498EFC">{{mpr}}</td>
    		<td >最大亏损率</td>
    		<td style="color:#498EFC">{{mdr}}</td>
    	</tr>		
	</script>
  <script type="text/javascript">
  	   $(function(){
  	    var result,selectData=[];
   	    getSelectData();
   	    getSurvyValue(selectData);
 		$(':checkbox',window.parent.document).change(function(){
 		     selectData=[];
 		     getSelectData();
 		     getSurvyValue(selectData);
 		     getheight();
 		}) ;
 		function getSurvyValue(obj){
 		    $.ajax({
 			type : 'post', //传输类型  
 			async : false, //同步执行  
 			/* url : '/qtadmin/basic/survey.do', */
 			url: '${pageContext.request.contextPath}/basic/survey.do',
 			data:{"account":obj},
 			success : function(data) {
 				result = JSON.parse( data );
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
 			       
 			},
 			error : function(errorMsg) {
 				//console.log( errorMsg );
 				////window.location.href="/qtadmin/page/empty.jsp";  
 			}
 		});
 	 }
 	  	//获取多选框的账户值  
   	    function getSelectData(){
 	  		$('.accountmany li',window.parent.document).each(function(i,e){
 	  			   var sel=$(e).find('input').prop('checked');
 	  			  if(sel==true){
 	  				   selectData.push($(e).find('input').val());		
 	  			  }
 	  	    });
 	  	  }
 	  	  $(window.parent.document).find("#listiframe").load(function() {
 	  		 getheight();
         });  
  
  	     //测试iframe高度问题
  });
  	   function getheight(){
	     var main=window.parent.document.getElementById("listiframe");
	     var thisheight = document.getElementsByTagName('body')[0].offsetHeight  + 10;
	     main.style.height=thisheight+'px';  
	  }  
  	  
  	 
  	 
	</script>
</html>
