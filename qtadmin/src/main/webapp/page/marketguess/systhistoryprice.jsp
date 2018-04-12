<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
 <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>历史价格</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" href="<%=basePath%>layui/css/layuichange.css"  media="all">
	<link rel="stylesheet" href="<%=basePath%>css/marketguss.css">
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
		  	  <td><p>价格类型</p></td><td>&nbsp;</td>
		  		<td style="border-style: none;">
		  		 <select id="protype" >
		  		 	 <option value="-1">请选择</option>
					<option value="0">指数</option>
					<option value="1">主连</option>
				 </select></td>	
			  <td>&nbsp;&nbsp;&nbsp;</td>
			  <td><button class="searchbtn layui-btn" data-type="reload">查询</button></td>
			  <td>&nbsp;</td>
			  <td><button class="addbprobtn layui-btn" data-type="reload">新增品种</button></td>
		  	</tr>
		  </table>
		  </div>
		</div>    
    <table id="demo" lay-filter="test"></table>
    <button class="sur_btn layui-btn layui-btn-mini">确定</button>
    <script type="text/html" id="barDemo">
  		<a class="layui-btn layui-btn-mini" lay-event="detail">查看</a>
		<c:if test="${sessionScope.role == 1}" >
			<a class="layui-btn layui-btn-mini" lay-event="edit" id="edit">编辑</a>
  			<a class="layui-btn layui-btn-danger layui-btn-mini" lay-event="del" id="del">删除</a>
	   </c:if>
	</script>
  </body>
  <script src="<%=basePath%>layui/layui.all.js" charset="utf-8"></script>
  <script type="text/javascript">
  layui.use(['table','layer'], function(){
	  var table = layui.table,
	      layer=layui.layer;
	  var procode = document.getElementById("procode").value;
	  var proname = document.getElementById("proname").value;
	  var protype = $("#protype option:selected").val();
	  	render_fn({
	  		procode:procode,
	  		proname:proname,
	  		protype:protype
	  	});
	  //监听工具条
	  table.on('tool(test)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
		    var data = obj.data; //获得当前行数据
		    var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
		    var tr = obj.tr; //获得当前行 tr 的DOM对象
	    if(layEvent === 'detail'){ //查看  ✔
	    	var id=$(this).closest('tr').find('td:eq(0) div').text();
	    	parent.layer.open({
   				area: ['50%', '50%'],
   		    	type: 2,
   		    	title:'查看品种',
   		    	content: ['/qtadmin/page/marketguess/seeprice.jsp?id='+id,'no'] 
   		 	 });
	    }else if(layEvent === 'del'){ 	//删除✔
	    	  	id=$(this).closest('tr').find('td:eq(0) div').text();
		    	parent.layer.confirm('确定要删除吗', function(index){
		         $.ajax({
	    		 	url:'/qtadmin/market/deleteprice',
	    			type:'post',
	    			data:{
	    				'proId':id
	    			},
	    			success:function(data){
	    				 obj.del(); //删除对应行（tr）的DOM结构，并更新缓存
					     parent.layer.close(index);
	    			},
	    			error:function(data){		
	    				alert('删除失败!');
	    			}
	      		});
		      });
	    } else if(layEvent === 'edit'){ //编辑
	    	var id=obj.data.productid,
	    		pricetick=obj.data.pricetick,
	    		name=$(this).closest('tr').find('td:eq(1) div').text();
	    	 parent.layer.open({
	    		area: ['50%', '50%'],
    		    type: 2,
    		    title:'修改品种',
    		    content: ['/qtadmin/page/marketguess/updateprice.jsp?id='+id+'&pricetick='+pricetick+'&name='+encodeURI( name ),'no'] //注意，如果str是object，那么需要字符拼接。
    		  });
	    }
	  });
	   function render_fn( obj ){
		   table.render({
			    elem: '#demo'
			    ,height: 315
			    ,url: '/qtadmin/market/querypricelogin?proId='+obj.procode+'&proName='+obj.proname+'&priceType='+obj.protype //数据接口
			    ,page: true //开启分页
			    ,cols: [[ //表头
			      {field: 'productid', title: '品种代码', width:90,align:'center'}
			      ,{field: 'productname', title: '品种名称', width:150,align:'center'}
			      ,{field: 'year', title: '年份', width:65,align:'center'} 
			      ,{field: 'pricetype', title: '价格类型', width: 90,align:'center'}
			     // ,{field: 'histortyprice', title: '历史价', width: 100, sort: true,align:'center'}
			      ,{field: 'highestprice', title: '最高价', width: 100, sort: true,align:'center'}
			      ,{field: 'lowestprice', title: '最低价', width: 100, sort: true,align:'center'}
			      ,{field: 'pricetick', title: '最小变动价位', width: 120, sort: true,align:'center'}
			      ,{field:'right', title: '操作', width:200,toolbar:"#barDemo",align:'center'}
			    ]]
		 	    ,skin: 'row' //表格风格		    
			    ,even: true
			    ,page: true //是否显示分页
			    ,limits: [5, 10, 15]
			    ,limit: 5 //每页默认显示的数量
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
	 //点击确定保存；
	  /* $('.sur_btn').click(function(){
		  	var obj={
	  			productid:$(selector+' .pro_Code').val(),
		  	    productname:$(selector+' .pro_Name').val(),
		  	    year:$(selector+' .Year').val(),
		  	    pricetype:$(selector+' .pri_Type').val(),
		  	    histortyprice:$(selector+' .his_Price').val(),
		  	    highestprice:$(selector+' .high_Pri').val(),
		  	    lowestprice:$(selector+' .min_Pri').val(),
		  	    pricetick:$(selector+' .min_Chapri').val()
		  	}
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
	  }); */
  });
  //添加按钮;	
  $('.addbprobtn').click(function(){
	  parent.layer.open({
				area: ['50%', '50%'],
		    	type: 2,
		    	title:'新增品种',
		    	content: ['/qtadmin/page/marketguess/sysquery.jsp','no'] 
		 });
}); 
  </script>
</html>
