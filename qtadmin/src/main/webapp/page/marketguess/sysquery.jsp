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
<title>新增品种(价格测算)</title>
<link rel="stylesheet" href="<%=basePath%>layui/css/layuichange.css"  media="all">
<link rel="stylesheet" href="<%=basePath%>css/marketguss.css">
<style>
	.layui-table tr.seletr{
		border:1px solid #ccc;
	}
	.layui-table-view {
		position: relative;
		margin: 10px 0;
		overflow: hidden;
		overflow-y: scroll;
	}
	.layui-table-body {
		position: relative;
		margin-right: -1px
	}
	.addifr div{
		width:50%;
		display:inline-block;
	}
	select.sele{
		width:125px;
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
  layui.use(['table','layer'], function(){
	  var table = layui.table,layer=layui.layer;
	  var numreg=/^\d+$|^[0]{1}\.\d+$|^[1-9]{1}\d?\.{1}\d+$/;
	  render_fn();
	  function render_fn( ){
		  layui.use(['table','layer'], function(){
			  var table = layui.table,
			      layer=layui.layer;
		  	  table.render({
			    elem: '#demo'
			    ,height:200
			    ,url:'/qtadmin/market/queryProducts'//数据接口
			    ,cols: [[ //表头
			       {field: 'productId', title: '品种代码', width:150,align:'center'}
			      ,{field: 'productName', title: '品种名称', width:150,align:'center'}
			    ]]
		 	    ,skin: 'row' //表格风格		    
			    ,even: true
			    ,id: 'testReload'
			    ,where:{
			    	proId:document.getElementById("procode").value,
			    	proName:document.getElementById("procode").value
			    }
			 });
		  });
	   }   
	  //点击查询按钮;
	  $('.searchbtn').click(function(){
			procode = document.getElementById("procode").value;
		    proname = document.getElementById("proname").value;
		  	render_onefn( procode,proname);
	  });
	  //点击搜索，使用历史价格查询中的搜索；
	  function render_onefn(procode,proname){
		  table.render({
		    elem: '#demo'
		    ,height: 200//点击搜索后的table高度；
		    ,url: '/qtadmin/market/queryProducts' //数据接口
		    ,page: true //开启分页
		    ,cols: [[ //表头
		      {field: 'productId', title: '品种代码', width:150,align:'center'}
		      ,{field: 'productName', title: '品种名称', width:150,align:'center'}
		    ]]
	 	    ,skin: 'row' //表格风格		    
		    ,even: true
		    ,page: false //是否显示分页
		    ,limits: [5, 10, 15]
		    ,limit:30 //每页默认显示的数量
		    ,id: 'testReload'
    	    ,where:{
		    	proId:procode,
		    	proName:proname
		    }
		 }); 
		  $('.layui-table-body').on('click','tr',function(){
			 $(this).siblings().removeClass('seletr');
			 $(this).addClass('seletr');
		  });
	  }
	  //点击新增品种中的tr;
	  $('.layui-table-body').on('click','tr',function(){
		 $(this).siblings().removeClass('seletr');
		 $(this).addClass('seletr');
	  });
	 //点击确定
	 $('.sur_btn').click(function(){
		 if($('.layui-table-body tr.seletr').length>0 ){
			 var indexid=$('.layui-table-body tr:last',parent.document).data('index')+1,
			 	year=new Date().getFullYear(),
			 	productName=$('.layui-table-body tr.seletr td:eq(0) div').text(),
			 	productId=$('.layui-table-body tr.seletr td:eq(1) div').text();
			 
			 layer.confirm('确定保存数据吗?', function(index){
					 layer.close(index);
		  			   var str='<div class="addifr"><div><span>品种代码:</span><input type="text" class="pro_Code" value='+productName+' disabled="disabled"></div>'+
				 		'<div><span>品种名称:</span><input type="text" class="pro_Name" value='+productId+' disabled="disabled"></div>'+
				   		 '<div><span>年份:</span><input type="text" class="Year" value='+year+' disabled="disabled"></div>'+
				  		 '<div><span>价格类型:</span><select class="sele"><option valu="0">指数</option><option valu="1">主连</option></select></div>'+
						 '<div><span>最高价:</span><input type="text" class="high_Pri" placeholder="请输入最高价"/></div>'+
						 '<div><span>最低价:</span><input type="text" class="min_Pri" placeholder="请输入最低价"/></div>'+
						 '<div><span>最小变动价位:</span><input type="text" class="min_Chapri" placeholder="请输入最小变动价位"/></div>'+
						 '<div style="opacity:0;"><span>历史价:</span><input type="text" class="his_Price" placeholder="请输入历史价"  value="0"/></div></div>'
					 	layer.open({
						   btn: ['确认','取消'],
						   title: '新增品种',
						   area:['100%','100%'],
						   content:str,
						   yes:function( index,layero ){
							   var selector=$(layero).selector;
						  	   productid=$(selector+' .pro_Code').val(),
						  	   productname=$(selector+' .pro_Name').val(),
						  	   year=$(selector+' .Year').val(),
						  	   pricetype=$('.sele option:selected').attr('valu'),//
						  	   histortyprice=$(selector+' .his_Price').val(),
						  	   highestprice=$(selector+' .high_Pri').val(),
						  	   lowestprice=$(selector+' .min_Pri').val(),
						  	   pricetick=$(selector+' .min_Chapri').val(),
						  	   list=[],wrongnum=0;
					  	  	if( !numreg.test(histortyprice) ){
					  	  		wrongnum++;
					  	  		parent.layer.open({
		    	    			  title: '提示',
		    	    			  content:'历史价必须是数字!'
		    	    			});
					  	  	}else if( !numreg.test(highestprice)){
					  	  		wrongnum++;
					  	  		parent.layer.open({
		    	    			  title: '提示',
		    	    			  content:'最高价必须是数字!'
		    	    			}); 
					  	  	}else if( !numreg.test(lowestprice)){
				  	  			wrongnum++;
				  	  			parent.layer.open({
		    	    			  title: '提示',
		    	    			  content:'最低价必须是数字!'
	    	    				});
				  	  		}else if( !numreg.test(pricetick) ){
				  	  			wrongnum++;
				  	  			parent.layer.open({
	    	    				  title: '提示',
	    	    				  content:'最小变动价位必须是数字!'
	    	    				});
				  	  		}else{
					  	  		list.push({
			    					productid:productid,
			    					productname:productname,
			    					year:year,
			    					pricetype:pricetype,
			    					histortyprice:histortyprice,
			    					highestprice:highestprice,
			    					lowestprice:lowestprice,
			    					pricetick:pricetick
			    			    });
				  	  		}
					  	  	//console.log( list );
						     //请求保存；
						     if( wrongnum==0){
						    	 $.ajax({
						    		 	url:'/qtadmin/market/insertprice',
						    			type:'post',
						    			contentType: "application/json",
						    			data:JSON.stringify({
						    				'list':list
						    			}),
						    			success:function(data){
						    				var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
											parent.layer.close(index); //再执行关闭   
											
											$('#listiframe',parent.document).attr('src',$('#listiframe',parent.document).attr('src'));
											parent.layer.open({
					    	    			  title: '提示',
					    	    			  content:'添加成功!'
					    	    			});
						    			},
						    			error:function(data){  
						    				parent.layer.open({
					    	    			  title: '提示',
					    	    			  content:'添加失败!'
					    	    			});
						    			}
						      		});
						     }
						   },
						   btn2:function(index,layero){ 
						   }
						});   
				  });
		  			 
		 }else{
			 layer.open({
			  title: '提示',
			  content:'请选择一条数据!'
			});     
		 }
	 }); 
		 
  });
   
  </script>
</html>