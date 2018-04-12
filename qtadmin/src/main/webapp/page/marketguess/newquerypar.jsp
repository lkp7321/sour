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
<title>新增品种(超买超卖)</title>
<link rel="stylesheet" href="<%=basePath%>layui/css/layuichange.css"  media="all">
<link rel="stylesheet" href="<%=basePath%>css/marketguss.css">
<style>
	.layui-table tr.seletr{
		border:1px solid #ccc;
	}
	.layui-table-body{
		overflow-y: scroll;
	}
</style>
<script type="text/javascript" src="<%=basePath%>js/jquery-1.11.3.js"></script>
</head>
<body>
<div class="demoTable">
	  <div class="layui-inline">
		  <table>
		  	<tr>	
		  	  <td><p>品种代码</p></td><td>&nbsp;</td>	  	
		    	<td><input id="procode"/></td>
		      <td>&nbsp;</td>
		      <td><p>品种名称</p></td><td>&nbsp;</td>
		   		 <td><input id="proname"/></td>
		  	  <td>&nbsp;</td>
			  <td><button class="searchbtn layui-btn" data-type="reload">查询</button></td>
			  <td>&nbsp;</td>
		  	</tr>
		  </table>
	  </div>
</div>    
 <table id="demo" lay-filter="test"></table>
 <button class="sur_btn layui-btn layui-btn-mini">确定</button>
</body>
<script src="<%=basePath%>layui/layui.all.js" charset="utf-8"></script>
 <script type="text/javascript">
 
  var username='${sessionScope.name}';
  layui.use(['table','layer','form'], function(){
	  var table = layui.table,
	  	  form = layui.form;
	  render_fn();
	  function render_fn(){
		  layui.use(['table','layer'], function(){
			  var table = layui.table,
			      layer=layui.layer;
		  	  table.render({
			    elem: '#demo'
			    ,height:'200px'
			    ,url:'/qtadmin/market/productBypricesheet'//数据接口
			    ,cols: [[ //表头
			       {field: 'productId', title: '品种', width:100,align:'center'}
			      ,{field: 'productName', title: '品种名称', width:100,align:'center'}
			      ,{field: 'exchangeNo', title: '交易所', width:100,align:'center'} 
			    ]]
		 	    ,skin: 'row' //表格风格		    
			    ,even: true
			    ,id: 'testReload'
			    ,where:{
			    	proId:document.getElementById("procode").value,
			    	proName:document.getElementById("proname").value
			    }
			 });
		  });
		  //点击新增品种中的tr ( 解决搜索成功后表格tr不可以点击问题 ) ;
		  $('.layui-table-body').on('click','tr',function(){
			 $(this).siblings().removeClass('seletr');
			 $(this).addClass('seletr');
		  });
	   }   
	  //点击查询按钮;
	  $('.searchbtn').click(function(){
			//procode = document.getElementById("procode").value;
		    //proname = document.getElementById("proname").value;
			//protype = $("#protype option:selected").val();
		  render_fn();
	  });
	 
	$('.sur_btn').click(function(){
		 if($('.layui-table-body tr.seletr').length>0 ){
			 var indexid=$('.layui-table-body tr:last',parent.document).data('index')+1,
			 	 arr=[];//判断当前添加的数据是否存在;
			 $('.layui-table-body tbody tr',parent.document).each(function(i,v){
				 arr.push( $(v).find('td:eq(0) div').text() );
			 });
			 if( arr.indexOf( $('.layui-table-body tr.seletr td:eq(0) div').text() )==-1 ){
				 layer.confirm('确定保存数据吗?', function(index){
					 $('.layui-table-body tbody',parent.document).append(
							  '<tr data-index='+indexid+' class="">'+
					  			  '<td data-field="productid" align="center"><div class="layui-table-cell laytable-cell-1-productid">'+$('.layui-table-body tr.seletr td:eq(0) div').text()+'</div></td>'+	
					  			  '<td data-field="proname" align="center"><div class="layui-table-cell laytable-cell-1-proname">'+$('.layui-table-body tr.seletr td:eq(1) div').text()+'</div></td>'+
					  			  '<td data-field="pricetype" align="center"><div class="layui-table-cell laytable-cell-1-pricetype">'+$('.layui-table-body tr.seletr td:eq(2) div').text()+'</div></td>'+
					  			  '<td data-field="bullandbearratio" data-edit="true" align="center"><div class="layui-table-cell laytable-cell-1-bullandbearratio"></div></td>'+
					  			  '<td data-field="longsegment" data-edit="true" align="center"><div class="layui-table-cell laytable-cell-1-longsegment"></div></td>'+
					  			  '<td data-field="shortsegment" data-edit="true" align="center"><div class="layui-table-cell laytable-cell-1-shortsegment"></div></td>'+
					  			  '<td data-field="segmenttype" data-edit="true" align="center"><div class="layui-table-cell laytable-cell-1-segmenttype"></div></td>'+
					  			  '<td data-field="heyue" align="center" data-off="true"><div class="layui-table-cell laytable-cell-1-heyue" contractset=""> <a class="layui-btn layui-btn-mini" lay-event="opera">操作</a> </div></td>'+
					  			  '<td data-field="quanzhong" align="center" data-off="true"><div class="layui-table-cell laytable-cell-1-quanzhong" weights=""> <a class="layui-btn layui-btn-mini" lay-event="opera1">操作</a></div></td></tr>');
				 		if( $('.layui-none',parent.document).length>0){
				 			$('.layui-none',parent.document).hide();
				 		}
					 	layer.close(index);
						var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
						parent.layer.close(index); //再执行关闭   
					});
			 }else{
				 layer.open({
				  title: '提示',
				  content:'该数据已存在!'
				}); 
			 }
		 }else{
			 layer.open({
			   title: '提示',
			   content:'请选择一条数据!' 
			});     
		}
	 });
		  //添加按钮;	
		  /* $('.addbprobtn').click(function(){
			  alert(1)
			  var str='<div>品种代码:<input type="text" class="pro_Code" placeholder="请输入品种代码"/><div>'+
			  		  '<div>品种名称:<input type="text" class="pro_Name" placeholder="请输入品种名称"/><div>'+
			  		  '<div>年份:<input type="text" class="Year" placeholder="请输入年份"/><div>'+
			  		  '<div>价格类型:<input type="text" class="pri_Type" placeholder="请输入价格类型"/><div>'+
			  			'<div>历史价:<input type="text" class="his_Price" placeholder="请输入历史价"/><div>'+
			  			'<div>最高价:<input type="text" class="high_Pri" placeholder="请输入最高价"/><div>'+
			  			'<div>最低价:<input type="text" class="min_Pri" placeholder="请输入最低价"/><div>'+
			  			'<div>最小变动价位:<input type="text" class="min_Chapri" placeholder="请输入最小变动价位"/><div>'
			    layer.open({
				   btn: ['取消', '确认'],
				   title: '新增品种',
				   area:['500px'],
				   content:str,
				   yes:function( index,layero ){	//取消按钮；
					 	layer.close( index );
				   },
				   btn2:function(index,layero){ //点击保存；
					   var selector=$(layero).selector;
					  	   productid=$(selector+' .pro_Code').val(),
					  	   productname=$(selector+' .pro_Name').val(),
					  	   year=$(selector+' .Year').val(),
					  	   pricetype=$(selector+' .pri_Type').val(),
					  	   histortyprice=$(selector+' .his_Price').val(),
					  	   highestprice=$(selector+' .high_Pri').val(),
					  	   lowestprice=$(selector+' .min_Pri').val(),
					  	   pricetick=$(selector+' .min_Chapri').val();
					     //请求保存；
					  	 $.ajax({
			    		 	url:'/qtadmin/market/insertprice',
			    			type:'post',
			    			contentType: "application/json",
			    			data:JSON.stringify({
			    					productid:productid,
			    					productname:productname,
			    					year:year,
			    					pricetype:pricetype,
			    					histortyprice:histortyprice,
			    					highestprice:highestprice,
			    					lowestprice:lowestprice,
			    					pricetick:pricetick
			    			}),
			    			success:function(data){
							     layer.close(index);
			    			},
			    			error:function(data){  
			    				alert('添加失败!');
			    			}
			      		});
				   }
				});   
		  }); */
  });
   
  </script>
</html>