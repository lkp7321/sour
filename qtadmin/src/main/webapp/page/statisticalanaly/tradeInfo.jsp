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
    <title>成交信息</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" href="../../layui/css/layui.css"  media="all">
	<script type="text/javascript" src="../../js/jquery-1.11.3.js"></script>
	  <style type="text/css">
  		body{
  			text-align: center;
  		}
  		#div{
  			margin:0 auto;
  			/* border:0px solid; */
  			width:90%;
  			
  		}
  		table{
  			border:1px solid #F0F9FE;
  			border-collapse: collapse;
  			width: 100%;
  		}
  		th{
  			border:1px solid;
  			background-color:#F0F9FE;
  		}
  		td{
  			text-align:center;
  			border:1px solid;
  		}
  		
  	</style>  

  </head>    
   <body> 
	 <div class="demoTable">
		 交易日期：
		  <div class="layui-inline">
		  <table>
		  	<tr>		  	
		    	<td style="border-style: none;"><input style="height:30px;" class="layui-input" id="demoReload" placeholder="点击选择日期"></td>
		   		 <td style="border-style: none;">—</td>
		  		<td style="border-style: none;"><input style="height:30px;" class="layui-input" id="demoReload2" placeholder="点击选择日期"></td>	    
		  	</tr>
		  </table>  
		  </div>
		  <button class="layui-btn layui-btn-sm" data-type="reload">搜索</button>
		</div>    
 	 <table id="demo"></table>
  </body> 
  	<script src="../../layui/layui.all.js" charset="utf-8"></script>
  	
    <script type="text/javascript">
    
   

   $(function(){
	   
	    var date=new Date();
	    var month=date.getMonth()+1;
	    var day=date.getDate(); 
	    var nowDate = date.getFullYear() + "-" + (month<10?"0"+month:month) + "-" +(day<10?"0"+day:day);   
	    date.setMonth(date.getMonth()+1-6);
	    var month2=date.getMonth();
	    var day2=date.getDate(); 
	    var oldDate=date.getFullYear() + "-" + (month2<10?"0"+month2:month2) + "-" +(day2<10?"0"+day2:day2);
	    $("#demoReload").val(oldDate);
	    $("#demoReload2").val(nowDate);
	    layui.use('laydate', function(){
		  	 var laydate = layui.laydate;  
		  	 var s= laydate.render({
		  	    elem: '#demoReload' //指定元素  	   
		  	     ,theme: 'grid'
		  	     ,max: $("#demoReload2").val()
		  	     ,done: function(value, date){
		  	    	 e.config.min={
		  	    			year: date.year,
		  	    			month: date.month - 1,
		  	    			date: date.date,
		  	    	 };
		  	     }
		  	  });
	       var e= laydate.render({
	    	    elem: '#demoReload2' //指定元素  	   
	    	     ,theme: 'grid'
	    	     ,min:$("#demoReload").val()
	    	     ,done: function(value, date){
	    	    	 s.config.max={
	    	    			year: date.year,
	   	    			month: date.month - 1,
	   	    			date: date.date,
	    	    	 };
	    	      }
	    	  });
	  	});
	   
	   
	   
		var selectData=[],
	    beginTime=$("#demoReload").val(),
	    endTime=$("#demoReload2").val();
		getSelectData();		 
		getData(selectData,beginTime,endTime);
	   	
	  	$(':checkbox',window.parent.document).change(function(){
	  		beginTime=$("#demoReload").val();
	        endTime=$("#demoReload2").val();
	        selectData=[];
	  	    getSelectData();
	  	    getData(selectData,beginTime,endTime);
	  		 
	  		 var main = $(window.parent.document).find("#listiframe");
			 var thisheight = $(document).height() + 10;
			 main.height(thisheight);
	  	});
	  	//获取选中的账户值			
		function getSelectData() {
			$('.accountmany li', window.parent.document).each(function(i, e) {
				var sel = $(e).find('input').prop('checked');
				if (sel == true) {
					selectData.push($(e).find('input').val());
				}
			})
		} 
       
	   
		 
		function getData( obj,obj1,boj2){
			if( obj.length==0){
				document.getElementsByTagName('body')[0].style.opacity=0;
			} else {
				document.getElementsByTagName('body')[0].style.opacity=1;
				var table = layui.table;
			     
			  //展示已知数据
			  table.render({
			     elem: '#demo'
			    ,url: '/qtadmin/basic/allMsg'
			    //,data:{"account":selectData,'beginTime':beginTime,'endTime':endTime}
			    ,height: 500
			    ,cols: [[ //标题栏
			      {field: 'jybh', title: '交易编号', width: 120,sort:true,fixed:'left'}
			      ,{field: 'hybh', title: '合约编号', width: 130,align:'center'}
			      ,{field: 'jysbm', title: '交易所编码', width: 150,align:'center'}
			      ,{field: 'mmfx', title: '买卖方向', width: 100,align:'center'}
			      ,{field: 'p', title: '价格', width: 100,align:'center'}
			      ,{field: 'kp', title: '开平标识', width: 100,align:'center'}
			      ,{field: 'tb', title: '投保', width: 70,align:'center'}
			      ,{field: 'jyd', title: '交易日期', width: 100,align:'center'}
			      ,{field: 'jyt', title: '交易时间', width: 100,align:'center'}
			      ,{field: 'oid', title: '委托订单编号', width: 220,align:'center'}
			      ,{field: 'sid', title: '系统编号', width: 150,align:'center'}
			      ,{field: 'jydt', title: '交易日期时间', width: 200,align:'center'}
			      ,{field: 'ut', title: '数据更新时间', width: 200,align:'center'}
			      ,{field: 'v', title: '手数', width: 100,align:'center'}
			      ,{field: 'kccj', title: '开仓成交号', width: 120,align:'center'}
			      ,{field: 'yk', title: '盈亏', width: 80,align:'center'}
			    ]] 
			    ,skin: 'row' //表格风格		    
			    ,even: true
			    ,page: true //是否显示分页
			    ,limits: [5, 7, 10]
			    ,limit: 10 //每页默认显示的数量
			    ,where: {
						  "account" : obj,
						  "beginTime":obj1,
						  "endTime":boj2,
						}
			    ,id: 'testReload'
			  });
			  
			  var active = {
					    reload: function(){
					      var demoReload = $('#demoReload');
					      var demoReload2 = $('#demoReload2');
					      //执行重载			    
					      table.reload('testReload', {
					    	  page: true
					    	 ,where: {				   
					    		 beginTime: demoReload.val(),	
					    		 endTime: demoReload2.val()	,
					    		 "account" : obj
					          }
					      });		      
					    }
					 };			  
					 $('.demoTable .layui-btn').on('click', function(){
					   var type = $(this).data('type');
					   active[type] ? active[type].call(this) : '';
					 });
					 
				 }
			 }
			 $(window.parent.document).find("#listiframe").load(function() {
				var main = $(window.parent.document).find("#listiframe");
				var thisheight = $(document).height() + 10;
				main.height(thisheight);
			});
	});

    </script>
</html>