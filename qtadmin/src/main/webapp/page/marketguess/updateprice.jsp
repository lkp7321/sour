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
    <title>维护历史价格</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" href="<%=basePath%>layui/css/layuichange.css"  media="all">
	<link rel="stylesheet" href="<%=basePath%>css/marketguss.css">
	<style>
		.btn_righ{
			text-align:right;
			margin:5px 5px 0 0;
		}
		.sele{
			float:left;
			margin-left:5px;
		}
		.layui-table-body {
		    overflow-y: scroll;
		}
	</style>
	<script type="text/javascript" src="<%=basePath%>js/jquery-1.11.3.js"></script>
  </head>
  
  <body> 
  <select class="sele">
  	<option value="0">指数</option>
  	<option value="1">主连</option>
  </select>
  <div class="btn_righ">
   <button class="layui-btn layui-btn-mini add" lay-event='add' >新增</button>
   <button class="layui-btn layui-btn-mini save" lay-event='save'>保存</button>
  </div>
  <table id="demo" lay-filter="test"></table>
  	<script type="text/html" id="barDemo">
  		<a class="layui-btn layui-btn-mini" lay-event="remove">x</a>
	</script>
  </body>
  <script src="<%=basePath%>layui/layui.all.js" charset="utf-8"></script>
  <script type="text/javascript">
  var searUrl=$('html').context.URL.split('&'),
  	  id=searUrl[0].split('=')[1],
  	  proId=id,
  	  pricetick=searUrl[1].split('=')[1],
  	  name=decodeURI( searUrl[2].split('=')[1] ),
  	  regone=/^\d+$|^[0]{1}\.\d+$|^[1-9]{1}\d?\.{1}\d+$/,
  	  datareg=/\d{4}/;
  layui.use(['table','layer'], function(){
	  var table = layui.table,
	      layer=layui.layer;
	  table.render({
		    elem: '#demo'
		    ,url: '/qtadmin/market/selectpricebyidlogin?proId='+id//数据接口
		    ,height:'full-50'
		    ,cols: [[ //表头
		      {field: 'productname', title: '品种名称', width:120,align:'center'}
		      ,{field: 'year', title: '年份', width:72,edit:'text',align:'center'} 
		     // ,{field: 'pricetype', title: '价格类型', width: 100,edit:'text',align:'center'}
		    //  ,{field: 'histortyprice', title: '历史价', width: 72, sort: false,edit:'text',align:'center'}
		      ,{field: 'highestprice', title: '最高价', width: 72, sort: false,edit:'text',align:'center'}
		      ,{field: 'lowestprice', title: '最低价', width: 72, sort: false,edit:'text',align:'center'}
		      ,{field:'right', title: '操作', width:85,toolbar:"#barDemo",align:'center'}
		    ]]
	 	    ,skin: 'row' //表格风格		    
		    ,even: true
		    ,id: 'testReload'
		    ,done:function( res, curr, count ){
		    	if( res.data[0].pricetype=='指数'){
		    		$('.sele option:eq(0)').attr('selected','selected');
		    	}else{
		    		$('.sele option:eq(1)').attr('selected','selected');
		    	}
		    }
		  });
	  //操作导航工具条；
	  table.on('tool(test)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
		    var data = obj.data; //获得当前行数据
		    var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
		    var tr = obj.tr; //获得当前行 tr 的DOM对象
	   		
		   	 if(layEvent === 'remove'){ //查看
		    	var id=obj.data.productname;
		    	//删除数据,只是应该传给后台吗；还是最后同一保存；
		    	layer.confirm('确定要删除吗?', function(index){
		    		obj.del();
			        layer.close(index);
		    	});
		     }
	  });
  });
  //点击添加按钮;
  $('.add').click(function(){ 
	  var index=$('.layui-table-body tr:last').data('index')+1,
	  	   year=new Date().getFullYear(),
	  	   nulnum=0;
	  $('.layui-table-body tr').each(function(i,v){
			if($(v).find('td:eq(1) div').text()==''||$(v).find('td:eq(2) div').text()==''||$(v).find('td:eq(3) div').text()==''||$(v).find('td:eq(4) div').text()==''){
				nulnum++;
				layer.open({
				  title: '提示',
				  content:'请先完善数据再新增!'
				});     
			}
	  });
	  if( nulnum==0 ){
		  $('.layui-table-body tbody').append(
			  '<tr data-index='+index+' class="">'+
			  	'<td data-field="productname" align="center">'+
			  		'<div class="layui-table-cell laytable-cell-1-productname">'+name+'</div>'+
			  	'</td>'+
		  		'<td data-field="year" data-edit="true"  align="center"><div class="layui-table-cell laytable-cell-1-year">'+year+'</div></td>'+
		  		'<td data-field="highestprice" data-edit="true" align="center"><div class="layui-table-cell laytable-cell-1-highestprice"></div>'+
		  		'</td><td data-field="lowestprice" data-edit="true" align="center"><div class="layui-table-cell laytable-cell-1-lowestprice"></div></td>'+
			  	'<td data-field="right" align="center" data-off="true">'+
			  		'<div class="layui-table-cell laytable-cell-1-right">' +
			  		'<a class="layui-btn layui-btn-mini" lay-event="remove">x</a>' +
			  		'</div></td></tr>'
			  );
	  }
  });
  $('.save').click(function(){
	  var list=[],
	  	  wrongnum=0;
	$('.layui-table-body tr').each(function(i,v){
		if($(v).find('td:eq(1) div').text()==''||$(v).find('td:eq(2) div').text()==''||$(v).find('td:eq(3) div').text()==''){ //||$(v).find('td:eq(4) div').text()==''
			wrongnum++;
			layer.open({
			  title: '提示',
			  content:'请输入完整数据!'
			});     
		}else if( !datareg.test($(v).find('td:eq(1) div').text()) ){
			wrongnum++;
			layer.open({
			  title: '提示',
			  content:'请输入合法的年份!'
			});   
		}else if( !regone.test($(v).find('td:eq(2) div').text()) ){
			wrongnum++;
			layer.open({
			  title: '提示',
			  content:'历史价必须是合法整数或小数!'
			});   
		}else if( !regone.test($(v).find('td:eq(3) div').text()) ){
			wrongnum++;
			layer.open({
			  title: '提示',
			  content:'最高价必须是合法整数或小数!'
			});   
		}/* else if( !regone.test($(v).find('td:eq(4) div').text()) ){
			wrongnum++;
			layer.open({
			  title: '提示',
			  content:'最低价必须是合法整数或小数!'
			});   
		} */else{
			var pricetype=$('.sele option:selected').attr('value');
			list.push({
				productname:$(v).find('td:eq(0) div').text(),
				year:$(v).find('td:eq(1) div').text(),
				pricetype:pricetype,
				histortyprice:0,
				highestprice:$(v).find('td:eq(2) div').text(),
				lowestprice:$(v).find('td:eq(3) div').text()
			});
		}
	});
	if( wrongnum==0){
		 $.ajax({
			url:'/qtadmin/market/updatepri',
			type:'post',
			dataType:'JSON',
			contentType: "application/json",
			data:JSON.stringify( {
				'list':list,
				'proId':proId,
				'pricetick':pricetick
			}),
			success:function(data){
				 layer.open({
				  title: '提示',
				  content:'保存成功!'
				 });   
			},
			error:function(data){	//保存失败； 
				console.log('error');
				layer.close();
				/* layer.open({
				  title: '提示',
				  content:'保存失败!'
				 });  */
			}
		});
	}
  });
  </script>
</html>
