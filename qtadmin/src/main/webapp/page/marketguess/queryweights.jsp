<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="<%=basePath%>layui/css/layuichange.css"  media="all">
<link rel="stylesheet" href="<%=basePath%>css/marketguss.css">
<script type="text/javascript" src="<%=basePath%>js/jquery-1.11.3.js"></script>
<title>Insert title here</title>
</head>
<body>
	<table id="demo" lay-filter="test"></table>
	<div class="quewebt">
		<button class="quewebtn layui-btn" id='quewebtn'>确定</button>
	</div>
</body>
<script src="<%=basePath%>layui/layui.all.js" charset="utf-8"></script>
<script>
var id=$('html').context.URL.split('=')[1],
	parId=$('html').context.URL.split('=')[1].split('&')[0],
	par_index= $('html').context.URL.split('=')[2];   //上一级页面中的保存权重和合约tr的index;
var username=sessionStorage.getItem('name');
layui.use(['table','layer'], function(){
	var table = layui.table,
   		layer=layui.layer;
	table.render({
	    elem: '#demo'
	    ,height:'200px'
	    ,url: '/qtadmin/market/queryweights?proId='+parId+'&userId='+username//数据接口
	    ,page: true //开启分页
	    ,cols: [[ //表头
	       {checkbox:true, width:90,align:'center'}
	      ,{field: 'productid', title: '品种', width:90,align:'center'}
	      ,{field: 'year', title: '年份', width:65,align:'center'} 
	      ,{field: 'highprice', title: '最高价格', width: 90,align:'center'}
	      ,{field: 'lowprice', title: '最低价格', width: 100, sort: false,align:'center'}
	      ,{field: 'weight', title: '权重', width: 100, sort: false,edit:'text',align:'center'}
	    ]]
		,skin: 'row' //表格风格		    
	    ,even: true
	    ,page: false //是否显示分
	    ,id: 'testReload'
	 }); 
});

//点击权重中的确定;
$('#quewebtn').click(function(){
	var weightset='',
		queweightpar=parent.document;
	$('.layui-table-body tbody tr').each(function(i,v){
		if( $(v).find('td:eq(0) .layui-form-checkbox').hasClass('layui-form-checked')){
			if( i==$('.layui-table-body tbody tr').length-1){
				weightset+=$(v).find('td:eq(3) div').text()+';'+$(v).find('td:eq(4) div').text()+';'+$(v).find('td:eq(5) div').text()+';'+$(v).find('td:eq(2) div').text()
			}else{
				weightset+=$(v).find('td:eq(3) div').text()+';'+$(v).find('td:eq(4) div').text()+';'+$(v).find('td:eq(5) div').text()+';'+$(v).find('td:eq(2) div').text()+','
			}
		}
		
	});
	if( weightset==''){		
		layer.open({
		  title: '提示',
		  content:'请至少勾选一条数据!'
		});   
	}else{
		layer.confirm('确定要保存权重吗?', function(index){
			$('tbody tr:eq('+par_index+') td:eq(8) div',queweightpar).attr('weights',weightset);
			layer.close(index);
			var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
			parent.layer.close(index); //再执行关闭   
		});
	}
	
});
	 
</script>
</html>