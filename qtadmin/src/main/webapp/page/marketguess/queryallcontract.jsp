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
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>合约查询</title>
<link rel="stylesheet" href="<%=basePath%>layui/css/layuichange.css"  media="all">
<link rel="stylesheet" href="<%=basePath%>css/marketguss.css">
<script type="text/javascript" src="<%=basePath%>js/jquery-1.11.3.js"></script>
<style>
	.layui-table-view {
	position: relative;
	margin: 10px 0;
	overflow: hidden;
	    overflow-y: scroll;
}
	.layui-table-body {
		position: relative;
		/* overflow: hidden; */
		margin-right: -1px
	}
</style>
</head>
<body>
  <table id="demo" lay-filter="test"></table>
  <td><button class="layui-btn sure_btn" id="sure_btn">确定</button></td>
</body>
 <script src="<%=basePath%>layui/layui.all.js" charset="utf-8"></script>
 <script type="text/javascript">
  layui.use(['table','layer'], function(){
	  var table = layui.table,
	      layer=layui.layer;
	  var procode=$('html').context.URL.split('=')[1]
		  parId=$('html').context.URL.split('=')[1].split('&')[0],
		  par_index= $('html').context.URL.split('=')[2];   //上一级页面中的保存权重和合约tr的index;
	  	render_fn( parId );
	  //监听工具条
	  table.on('tool(test)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
		    var data = obj.data; //获得当前行数据
		    var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
		    var tr = obj.tr; //获得当前行 tr 的DOM对象
	    if(layEvent === 'detail'){ //查看  ✔
	    	var id=obj.data.productid;
	    	layer.open({
   				area: ['100%', '200px'],
   		    	type: 2,
   		    	title:'查看品种',
   		    	content: ['/qtadmin/page/marketguess/seeprice.jsp?id='+id,'no'] 
   		 	 });
	    }
	  });
	  function render_fn( obj ){
		   table.render({
			    elem: '#demo'
			    ,height:'200px'
			    ,url: '/qtadmin/market/queryallcontract?proId='+obj//数据接口
			    ,scrollbar:true
			    ,cols: [[ //表头
			      {checkbox:true},
			      {field: 'instrumentid', title: '合约代码', width:120,align:'center'}
			      ,{field: 'instrumentname', title: '合约名称', width:120,align:'center'}
			      ,{field: 'exchangeid', title: '交易所代码', width:120,align:'center'} 
			      ,{field: 'proid', title: '产品代码', width: 120,align:'center'}
			      ,{field: 'protype', title: '产品类别', width: 120, sort: false,align:'center'}
			    ]]
		 	    ,skin: 'row' //表格风格		    
			    ,even: true
			    ,page: false //是否显示分页
			    ,limits: [1, 2, 10]
			    ,limit: 1 //每页默认显示的数量
			    ,id: 'testReload'
			 }); 
	   }   
	  //点击查询按钮; ✔
	  $('.searchbtn').click(function(){
			procode = document.getElementById("procode").value;
		    proname = document.getElementById("proname").value;
			protype = $("#protype option:selected").val();
		  render_fn({
	  		procode:procode,
	  		proname:proname,
	  		protype:protype
	  	});
	  });
  });
//点击保存按钮;
 $('#sure_btn').click(function(){
	 var contractset='',
		queweightpar=parent.document;
	$('.layui-table-body tbody tr').each(function(i,v){
		if( $(v).find('td:eq(0) div.layui-form-checked').length>0 ){
			if( contractset===''){
				contractset+=$(v).find('td:eq(1) div').text()
			}else{
				contractset+=','+$(v).find('td:eq(1) div').text()
			}
		}
	});
	if( contractset==''){
		layer.open({
		  title: '提示',
		  content:'请至少勾选一条数据!'
		});   
	}else{
		layer.confirm('确定要保存合约吗?', function(index){
			$('tbody tr:eq('+par_index+') td:eq(7) div',queweightpar).attr('contractset',contractset);
			layer.close(index);
			var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
				parent.layer.close(index); //再执行关闭   
		});	
	}
 }); 
  </script>
</html>