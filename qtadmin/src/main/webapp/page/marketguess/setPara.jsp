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
<title>参数设置</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">    
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link rel="stylesheet" href="<%=basePath%>layui/css/layuichange.css"  media="all">
<script type="text/javascript" src="<%=basePath%>js/jquery-1.11.3.js"></script>
<style>
	.setpara{
		margin-top:5px;
		text-align:right;
		padding-right: 21px;
	}
	.setpara .addbtn,.setpara .remo{
		height:26px;
		line-height:26px;
	}
	.surebtnpar{
		text-align:center;
	}
	.surebtnpar .surebtn{
		height:32px;
		line-height:32px;
	}
	.layui-table-view {
	    height: 256px;
	    overflow-y: scroll;
	    overflow-x: hidden;
   }
   .layui-table tr.seletrr{
		border:1px solid #ccc;
	}
	.layui-table-tips{
		display:none;
	}
</style>
</head>
<body>
<!-- 合约操作&&权重操作 -->
 <script type="text/html" id="barDemo">
  	<a class="layui-btn layui-btn-mini" lay-event="opera">操作</a>
</script>
 <script type="text/html" id="barDemo1">
  	<a class="layui-btn layui-btn-mini" lay-event="opera1">操作</a>
</script>
<div class="setpara">
 	<button class="layui-btn layui-btn-mini addbtn" id="add_btn">新增</button>
 	<button class="layui-btn layui-btn-mini remo">删除</button>
 </div> 
  <table id="demo" lay-filter="test"></table>
  <div class="surebtnpar">
  	<button class="surebtn layui-btn">确定</button>
  </div> 
</body>
<script src="<%=basePath%>layui/layui.all.js" charset="utf-8"></script>
 <script type="text/javascript">
 var username='${sessionScope.name}',
 	 regone=/^[1-9]{1}\d?\:[1-9]{1}\d?$/,
 	 regtwo=/^\d{1,}$/;
 	 
 	 //$("#ifm").contents().find("#btnOk").click();//jquery 方法1
 	
 layui.use(['table','layer','form'], function(){
	  var table = layui.table,
	  	  form=layui.form,
	      layer=layui.layer,
	      id=$('html').context.URL.split('=')[1];
  	  table.render({
	    elem: '#demo'
	    ,height:'auto'
	    ,url:'/qtadmin/market/paramsetquery?userId='+id//数据接口
	    ,cols: [[ //表头
	       {field: 'productid', title: '品种', width:43,align:'center'}
	      ,{field: 'proname', title: '品种名称', width:86,align:'center'}
	      ,{field: 'pricetype', title: '价格类型', width:86,align:'center'} 
	      ,{field: 'bullandbearratio', title: '多空比', width: 72,edit:'text',align:'center'}
	      ,{field: 'longsegment', title: '多分段', width: 72, sort: false,edit:'text',align:'center'}
	      ,{field: 'shortsegment', title: '空分段', width: 72, sort: false,edit:'text',align:'center'}
	      ,{field: 'segmenttype', title: '分段类型', width: 86, sort: false,edit:'text',align:'center'}
	      ,{field: 'heyue', title: '合约', width: 75, sort: false,toolbar:"#barDemo",align:'center'}
	      ,{field: 'quanzhong',title: '权重', width: 75, sort:false,toolbar:"#barDemo1",align:'center'}
	    ]]
 	    ,skin: 'row' //表格风格		    
	    ,even: true
	    ,id: 'testReload'
	    ,done:function(res, curr, count){
	    	//把合约和权重保存到；
	    	/* layer.closeAll('tips'); */ //关闭所有的tips层
	    	$('tbody tr').each(function(i,v){
	    		$('tbody tr:eq('+i+') td:eq(8) div').attr('weights',res.data[i].weisets);
		    	$('tbody tr:eq('+i+') td:eq(7) div').attr('contractset',res.data[i].contractset);
	    	});
	    	 $('.layui-table-body').on('click','tr',function(){
				 $(this).siblings().removeClass('seletrr');
				 $(this).addClass('seletrr');
			  });
	    }
	 });
  	 //点击操作工具条；
  	 table.on('tool(test)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
		    var data = obj.data; //获得当前行数据
		    var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
		    var tr = obj.tr; //获得当前行 tr 的DOM对象
	    if(layEvent === 'opera'){
	    	var id=$(this ).closest('tr').find('td:eq(0) div').text(),
	    		trindex=$(this ).closest('tr').index(); 
	    	layer.open({
   				area: ['100%', '100%'],
   		    	type: 2,
   		    	title:'合约',
   		    	content: ['/qtadmin/page/marketguess/queryallcontract.jsp?id='+id+'&trindex='+trindex,'no'] 
   		 	 });
	    }else if( layEvent==="opera1"){  //权重修改；
	    	var id=$(this ).closest('tr').find('td:eq(0) div').text(),
	    		trindex=$(this ).closest('tr').index();
	    	layer.open({
   				area: ['100%', '100%'],
   		    	type: 2,
   		    	title:'权重',
   		    	content: ['/qtadmin/page/marketguess/queryweights.jsp?id='+id+'&trindex='+trindex,'no'] 
   		 });
	    }
  	 });
  	 //如果是搜索界面,并且请求的接口返回的名字是newquerypar自动打开增加页面；
  	 if( sessionStorage.getItem('seardata') ){
  		 if( sessionStorage.getItem('searda')=='newquerypar'  ){
  			layer.open({
  	  			area: ['100%', '100%'],
  	  		   	type:2,
  	  		   	title:'新增品种',
  	  		   	shade:false,
  	  		   	content: ['<%=basePath%>page/marketguess/newquerypar.jsp','no'],
  	  		   	success:function(){
  	  		   		sessionStorage.removeItem('seardata');
  	  		   		sessionStorage.removeItem('searda');
  	  		   	}
  	  	    });  
  		 }else if( sessionStorage.getItem("searda").split("[")[0]=='queryallcontract' ){
  			var id=sessionStorage.getItem("searda").split("[")[1].split(']')[0];
  	  		sessionStorage.removeItem('seardata');
   			sessionStorage.removeItem('searda');
  		 }
  	 }
  	 //新增数据；
	 $('#add_btn').click(function(){
		  layer.open({
		  		area: ['100%', '100%'],
 		    	type: 2,
 		    	title:'新增品种',
 		    	content: ['/qtadmin/page/marketguess/newquerypar.jsp','no']
		  
 		    	/* success:function(){
 				 	$('.layui-layer-content #layui-layer-iframe1').contents().find('.sur_btn').click(function(){

 				 		form.render('checkbox');
 				 		console.log( 1 )
 				 	});
 		    	} */
 		 });
	  });
  	 //删除数据；
  	 $('.remo').click(function(){
  		 var remo_list=[];
  		 $('.layui-table-body tr').each(function(i,v){
	  			if(  $(v).hasClass('seletrr') ){
	  				remo_list.push({
	  					productid:$(v).find('td:eq(0) div').text()
	  				});
	  			}
  		}); 
  		if( remo_list.length>0 ){
  			layer.confirm('确定要删除吗?', function(index){
  				$('.layui-table-body tr.seletrr').remove();
  	  			layer.close(index);
  	  		});
  		}else{
  			layer.confirm('请先选择一条数据', function(index){
  	  			layer.close(index);
  	  		});
  		}
  	 });
  	 //参数修改后的确定；
  	 $('.surebtn').click(function(){
  		 var list=[],wrongnum=0;
  		//获取权重和合约；
  		$('tbody tr').each(function(i,v){
  			var sun=$(v).find('td:eq(3) div').text().split(':')[0]*1+$(v).find('td:eq(3) div').text().split(':')[1]*1;
  			if( $(v).find('td:eq(3) div').text()=='' ){
  	  			wrongnum++;
  	  			layer.open({
    			  title: '提示',
    			  content:'多空比不能为空!'
    			});
  	  		}else if( !regone.test( $(v).find('td:eq(3) div').text() ) ){
  	  			wrongnum++;
  	  			layer.open({
    			  title: '提示',
    			  content:'多空比必须是分数!'
    			});
  	  		}else if( sun!=10){
	  	  		wrongnum++;
		  		layer.open({
				  title: '提示',
				  content:'多空分段比之和必须是10!'
				});
  	  		}else if( $(v).find('td:eq(4) div').text()==''){
  	  			wrongnum++;
  	  			layer.open({
    			  title: '提示',
    			  content:'多分段不能为空!'
    			});
  	  		}else if( !regtwo.test( $(v).find('td:eq(4) div').text() ) ){
  	  			wrongnum++;
  	  			layer.open({
  	   			  title: '提示',
  	   			  content:'多分段必须是数字!'
  	   			});
  	  		}else if( $(v).find('td:eq(5) div').text()=='' ){
  	  			wrongnum++;
  	  			layer.open({
    			  title: '提示',
    			  content:'空分段不能为空!'
    			});
  	  		}else if( !regtwo.test( $(v).find('td:eq(5) div').text() ) ){
  	  			wrongnum++;
  	  			layer.open({
  	  			  title: '提示',
  	  			  content:'空分段必须是数字!'
  	  			});
  	  		}else if( $(v).find('td:eq(6) div').text()=='' ){
  	  			wrongnum++;
  	  			layer.open({
    			  title: '提示',
    			  content:'分段类型不能为空!'
    			});
  	  		}else if( !regtwo.test( $(v).find('td:eq(6) div').text() ) ){
  	  			wrongnum++;
  	  			layer.open({
  	  			  title: '提示',
  	  			  content:'请分段类型必须是数字!'
  	  			});
  	  		}else if($(v).find('td:eq(7) div').attr('contractset')==''){
  	  			wrongnum++;
  	  			layer.open({
    			  title: '提示',
    			  content:'请选择合约!'
    			});
  	  		}else if( $(v).find('td:eq(8) div').attr('weights')=='' ){		//判断当权重不存在的时候;
  	  			wrongnum++;
	  	  		layer.open({
	  			  title: '提示',
	  			  content:'请选择权重!'
	  			});
  	  		}else{
	  	  		list.push({
	  				"productid":$(v).find('td:eq(0) div').text(),
	  				"productname":$(v).find('td:eq(1) div').text(),
	  				"pricetype":$(v).find('td:eq(2) div').text(),//
	  				"bullandbearratio":$(v).find('td:eq(3) div').text().split(':')[0]+$(v).find('td:eq(3) div').text().split(':')[1],
	  				"longsegment":$(v).find('td:eq(4) div').text(),
	  				"shortsegment":$(v).find('td:eq(5) div').text(),
	  				"segmenttype":$(v).find('td:eq(6) div').text(),//
	  				"contractset":$(v).find('td:eq(7) div').attr('contractset'),
	  				"weightset":$(v).find('td:eq(8) div').attr('weights')
	  			});
  	  		}
  		});
  		if(wrongnum==0){
  			$.ajax({
  	  			url:'/qtadmin/market/setparameter',
  	  			async:true,
  	  			type:'post',
  	  			contentType: "application/json",
  	  			data:JSON.stringify({
  	  				'list':list,
  	  				'userId':username
  	  			}),
  	  			success:function(data){
	  	  			var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
	  				parent.layer.close(index); //再执行关闭   
	  				
	  	  			parent.layer.open({
	  	 			  title: '提示',
	  	 			  content:'保存成功!'
	  	 			});
	  	  			$('#change_src',parent.document).attr('src',$('#change_src',parent.document).attr('src') );
  	  			},
  	  			error:function(){
  	  				layer.open({
	  	  			  title: '提示',
	  	  			  content:'保存失败!'
	  	  			});
  	  			}
  	  		});
  		}
  	 });
  });	  
</script>
</html>