<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">
<meta charset="utf-8">
<title>账户管理</title>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<link rel="stylesheet" href="<%=basePath%>layui/css/layui.css" media="all">
<style>
	.layui-table-body tr.sele_tr{background:#ccc;}
</style>
</head>
<body>	
	<!--增加、修改、删除按钮  -->
	<a href="${pageContext.request.contextPath}/page/useraccount/accountAdd.jsp"> <button class="layui-btn layui-btn-sm addbtn">   <i class="layui-icon"></i>增加</button>    </a>
	<a href="javascript:;">  <button class="layui-btn layui-btn-sm remotebtn">  <i class="layui-icon"></i>修改 </button>   </a>
	<button class="layui-btn layui-btn-sm removebtn"> <i class="layui-icon"></i>删除</button>
	
	<table class="layui-hide" id="test"></table>
	<!--增加、修改、删除按钮  -->
	<script src="<%=basePath%>js/jquery-1.11.3.js" charset="utf-8"></script>
	<script src="<%=basePath%>layui/layui.js" charset="utf-8"></script>
	<!-- 注意：如果你直接复制所有代码到本地，上述js路径需要改成你本地的 -->
	<script type="text/html" id="indexTpl">
    	{{d.LAY_TABLE_INDEX+1}}
	</script>
	<!-- 显示期货公司代码的字段 -->
	<script type="text/html" id="titleTpl">
       {{#  if(d.ctpbrokerid == 'z66,9999'){ }}
         TBSimNow
       {{#  }else if(d.ctpbrokerid == '0052'){ }} 
		  安粮期货次席
	   {{#  }else if(d.ctpbrokerid == '5090'){ }} 
		  安信期货
		{{#  }else if(d.ctpbrokerid == '0001'){ }} 
		  宝城期货
	   {{#  }else if(d.ctpbrokerid == '4080'){ }} 
		  渤海期货二席
	   {{#  }else if(d.ctpbrokerid == 'z26,4080'){ }} 
		  渤海期货主席
	   {{#  }else if(d.ctpbrokerid == 9060){ }} 
		 创元期货
	   {{#  }else if(d.ctpbrokerid == '0177'){ }} 
		  大地期货
	   {{#  }else if(d.ctpbrokerid == 6080){ }} 
		  大华期货
	   {{#  }else if(d.ctpbrokerid == 9999){ }} 
		  大有期货
	   {{#  }else if(d.ctpbrokerid == 8896){ }} 
		  大有期货金牛
	   {{#  }else if(d.ctpbrokerid == '0076'){ }} 
		  道通期货
	   {{#  }else if(d.ctpbrokerid == 9030){ }} 
		  第一创业
	   {{#  }else if(d.ctpbrokerid == 6040){ }} 
		  东方期货
	   {{#  }else if(d.ctpbrokerid == 4700){ }} 
		  东海期货
	   {{#  }else if(d.ctpbrokerid == 7070){ }} 
		  东航期货二席
	   {{#  }else if(d.ctpbrokerid == '0099'){ }} 
		  东华期货二席
	   {{#  }else if(d.ctpbrokerid == 4900){ }} 
		  东吴期货
	   {{#  }else if(d.ctpbrokerid == 4090){ }} 
		  东兴期货
	   {{#  }else if(d.ctpbrokerid == '00001'){ }} 
		  东亚期货CTP二席
	   {{#  }else if(d.ctpbrokerid == 3040){ }} 
		  东证期货二席
	   {{#  }else if(d.ctpbrokerid == '0034'){ }} 
		  方正中期
	   {{#  }else if(d.ctpbrokerid == 1025){ }} 
		  福能期货
	   {{#  }else if(d.ctpbrokerid == 7020){ }} 
		  格林期货
	   {{#  }else if(d.ctpbrokerid == '0037'){ }} 
		  冠通期货
	   {{#  }else if(d.ctpbrokerid == 8891){ }} 
		  光大金牛资管
	   {{#  }else if(d.ctpbrokerid == 6000){ }} 
		  光大期货二席
	   {{#  }else if(d.ctpbrokerid == 9000){ }} 
		  广发期货主席9000
	   {{#  }else if(d.ctpbrokerid == 3003){ }} 
		  广金期货
	   {{#  }else if(d.ctpbrokerid == 5070){ }} 
		  广州期货
	   {{#  }else if(d.ctpbrokerid == 9050){ }} 
		  国都期货
	   {{#  }else if(d.ctpbrokerid == 2014){ }} 
		  国富期货二席
	   {{#  }else if(d.ctpbrokerid == 2016){ }} 
		  国富期货主席
	   {{#  }else if(d.ctpbrokerid == '0029'){ }} 
		  国海良时
	   {{#  }else if(d.ctpbrokerid == 5030){ }} 
		  国金二席
	   {{#  }else if(d.ctpbrokerid == 5010){ }} 
		  国金主席
	   {{#  }else if(d.ctpbrokerid == 8890){ }} 
		  国联金牛资管
	   {{#  }else if(d.ctpbrokerid == 7010){ }} 
		  国联期货
	   {{#  }else if(d.ctpbrokerid == '0187'){ }} 
		  国贸期货
	   {{#  }else if(d.ctpbrokerid == 7090){ }} 
		  国泰君安
	   {{#  }else if(d.ctpbrokerid == 4500){ }} 
		  国投安信
	   {{#  }else if(d.ctpbrokerid == 8030){ }} 
		  国信期货
	   {{#  }else if(d.ctpbrokerid == '0013'){ }} 
		  国元海勤
	   {{#  }else if(d.ctpbrokerid == 2013){ }} 
		  海航东银
	   {{#  }else if(d.ctpbrokerid == 8000){ }} 
		  海通期货
	   {{#  }else if(d.ctpbrokerid == 9040){ }} 
		  海证期货
	   {{#  }else if(d.ctpbrokerid == '01027'){ }} 
		  和合期货
	   {{#  }else if(d.ctpbrokerid == 3010){ }} 
		  恒泰期货
	   {{#  }else if(d.ctpbrokerid == 4600){ }} 
		  弘业期货
	   {{#  }else if(d.ctpbrokerid == 2070){ }} 
		  宏源期货二席
	   {{#  }else if(d.ctpbrokerid == 1080){ }} 
		  宏源期货主席
	   {{#  }else if(d.ctpbrokerid == 6020){ }} 
		  华安期货
	   {{#  }else if(d.ctpbrokerid == 4400){ }} 
		  华创期货
	   {{#  }else if(d.ctpbrokerid == '0176'){ }} 
		  华金期货
	   {{#  }else if(d.ctpbrokerid == '0769'){ }} 
		  华联期货
	   {{#  }else if(d.ctpbrokerid == '5050'){ }} 
		  华龙期货
	   {{#  }else if(d.ctpbrokerid == 8080){ }} 
		  华泰长城
	   {{#  }else if(d.ctpbrokerid == 16333){ }} 
		  华西期货
	   {{#  }else if(d.ctpbrokerid == 10001){ }} 
		  华鑫期货主席
	   {{#  }else if(d.ctpbrokerid == 2000){ }} 
		  华元期货
	   {{#  }else if(d.ctpbrokerid == '0038'){ }} 
		  混沌天成
	   {{#  }else if(d.ctpbrokerid == 6868){ }} 
		  建信期货次席
	   {{#  }else if(d.ctpbrokerid == 95533){ }} 
		  建信期货主席
	   {{#  }else if(d.ctpbrokerid == 2090){ }} 
		  金海岸期货
	   {{#  }else if(d.ctpbrokerid == '0268'){ }} 
		  金汇期货
	   {{#  }else if(d.ctpbrokerid == 8888){ }} 
		  金瑞期货
	   {{#  }else if(d.ctpbrokerid == '0193'){ }} 
		  金石期货
	   {{#  }else if(d.ctpbrokerid == '0028'){ }} 
		  金信期货CTP二席
	   {{#  }else if(d.ctpbrokerid == '0089'){ }} 
		  金元期货
	   {{#  }else if(d.ctpbrokerid == '0097'){ }} 
		  津投期货
	   {{#  }else if(d.ctpbrokerid == 3082){ }} 
		  经易期货
	   {{#  }else if(d.ctpbrokerid == '0118'){ }} 
		  九州期货
	   {{#  }else if(d.ctpbrokerid == 7080){ }} 
		  鲁证期货
	   {{#  }else if(d.ctpbrokerid == 7030){ }} 
		  美尔雅期货
	   {{#  }else if(d.ctpbrokerid == 8050){ }} 
		  南华期货
	   {{#  }else if(d.ctpbrokerid == '0126'){ }} 
		  南证期货
	   {{#  }else if(d.ctpbrokerid == 5200){ }} 
		  平安期货
	   {{#  }else if(d.ctpbrokerid == '3060'){ }} 
		  普民期货
	   {{#  }else if(d.ctpbrokerid == 5100){ }} 
		  瑞达期货
	   {{#  }else if(d.ctpbrokerid == '6050'){ }} 
		  瑞龙期货
	   {{#  }else if(d.ctpbrokerid == '0195'){ }} 
		  山金期货
	   {{#  }else if(d.ctpbrokerid == 4050){ }} 
		  上海金源
	   {{#  }else if(d.ctpbrokerid == 8070){ }} 
		  上海中期
	   {{#  }else if(d.ctpbrokerid == 88888){ }} 
		  申银主席
	   {{#  }else if(d.ctpbrokerid == '0003'){ }} 
		  盛达期货
	   {{#  }else if(d.ctpbrokerid == 3050){ }} 
		  时代期货
	   {{#  }else if(d.ctpbrokerid == 8016){ }} 
		  首创期货主席
	   {{#  }else if(d.ctpbrokerid == 4020){ }} 
		  天鸿期货
	   {{#  }else if(d.ctpbrokerid == '0150'){ }} 
		  通联期货
	   {{#  }else if(d.ctpbrokerid == 9099){ }} 
		  同信久恒二席
	   {{#  }else if(d.ctpbrokerid == '0059'){ }} 
		  万达期货次席
	   {{#  }else if(d.ctpbrokerid == 4100){ }} 
		  五矿期货二席
	   {{#  }else if(d.ctpbrokerid == 9090){ }} 
		  西部期货
	   {{#  }else if(d.ctpbrokerid == 3497){ }} 
		  西南期货
	   {{#  }else if(d.ctpbrokerid == 6090){ }} 
		  新湖期货二席
	   {{#  }else if(d.ctpbrokerid == 3989){ }} 
		  新晟期货
	   {{#  }else if(d.ctpbrokerid == '0021'){ }} 
		  鑫鼎盛期货
	   {{#  }else if(d.ctpbrokerid == 1000){ }} 
		  信达期货
	   {{#  }else if(d.ctpbrokerid == 4200){ }} 
		  兴证期货主席
	   {{#  }else if(d.ctpbrokerid == 5060){ }} 
		  一德期货
	   {{#  }else if(d.ctpbrokerid == 4040){ }} 
		  银河期货二席
	   {{#  }else if(d.ctpbrokerid == 'z21,4040'){ }} 
		 银河期货主席 
	   {{#  }else if(d.ctpbrokerid == '0262'){ }} 
		 英大期货 
	   {{#  }else if(d.ctpbrokerid == 20000){ }} 
		永安期货  
	   {{#  }else if(d.ctpbrokerid == 7777){ }} 
		 长安期货 
	   {{#  }else if(d.ctpbrokerid == 4300){ }} 
		长江期货  
	   {{#  }else if(d.ctpbrokerid == 8060){ }} 
		 招商期货 
	   {{#  }else if(d.ctpbrokerid == '0005'){ }} 
		招金期货二席  
	   {{#  }else if(d.ctpbrokerid == 9070){ }} 
		 浙江新世纪 
	   {{#  }else if(d.ctpbrokerid == 6010){ }} 
		浙商期货  
	   {{#  }else if(d.ctpbrokerid == 4060){ }} 
		 中财期货二席 
	   {{#  }else if(d.ctpbrokerid == '0155'){ }} 
		中财期货主席 
	   {{#  }else if(d.ctpbrokerid == 5600){ }} 
		中大期货  
	   {{#  }else if(d.ctpbrokerid == 5300){ }} 
		中钢期货  
	   {{#  }else if(d.ctpbrokerid == 8090){ }} 
		中国国际  
	   {{#  }else if(d.ctpbrokerid == 2046){ }} 
		中航期货  
	   {{#  }else if(d.ctpbrokerid == '0274'){ }} 
		中辉期货二席  
	   {{#  }else if(d.ctpbrokerid == 7050){ }} 
		中投天琪期货  
	   {{#  }else if(d.ctpbrokerid == 9080){ }} 
		中信建投二席  
	   {{#  }else if(d.ctpbrokerid == 4000){ }} 
		中信新际二席  
	   {{#  }else if(d.ctpbrokerid == 66666){ }} 
		中信主席  
	   {{#  }else if(d.ctpbrokerid == 5040){ }} 
		中银国际二席  
	   {{#  } }} 
	</script> 
	<!-- 显示是否参与排名的字段 -->
	<script type="text/html" id="rankTpl">
       {{#  if(d.isrank == 0){ }}
                               否
	   {{#  } else if(d.isrank == 1){ }} 
		      是
	   {{#  } }} 	      
	</script> 
	<script>
		var da_ta;
		layui.use('table', function() {
			var table = layui.table;
			table.render({
				elem : '#test',
				width:756,
				url : '${pageContext.request.contextPath}/account/getAccount.do',
				cellMinWidth : 120 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
				,cols : [ [ {
					field : 'id',
					width : 80,
					templet: '#indexTpl',
					title : '序号',
					align: 'center'
				}, {
					field : 'userid',
					width : 140,
					height : 100,
					title : '投资者账号',
					align: 'center'
				},{
					field : 'ctpbrokerid',
					width : 240,
					templet: '#titleTpl',
					align: 'center',
					title : '期货公司'
				}, {
					field : 'marginnumber',
					width : 170,
					height : 100,
					align: 'center',
					title : '保证金监控中心账户'
				}, {
					field : 'isrank',
					width : 120,
					templet: '#rankTpl',
					align: 'center',
					title : '是否参与排名'
				} ] ]
				,done:function( res, curr, count){
					da_ta=res.data;
				}
			});
			$('.layui-table-body').on('click','tr',function(){
				$(this).siblings().removeClass('sele_tr');
				$(this).addClass('sele_tr');
			})
			$('.remotebtn').click(function(){
				if( $('.layui-table-body tr.sele_tr').length>0){
				
			      var userid=$('.sele_tr td:eq(1) div').text();
			      var index=$('.layui-table-body tr.sele_tr').index();
				 sessionStorage.setItem("data",JSON.stringify( da_ta[index ] ) );
				window.open('${pageContext.request.contextPath}/page/useraccount/accountUpdate.jsp?userid='+userid,"_self");
					
				}else{
					layer.open({
					  title: '提示'
					  ,content: '请先选择一条数据!'
					});     
				}
			});
			$('.removebtn').click(function(){
				var userid=$('.sele_tr td:eq(1) div').text();
				if( $('.layui-table-body tr.sele_tr').length>0){
					layer.confirm('您确定要删除吗？', function(index){
			         	 $.ajax({
		    		 	 url : '${pageContext.request.contextPath}/account/deleteAccount.do',
		    			type:'post',
		    			data:{
		    				'userid':userid
		    			},
		    			success:function(data){
		    				 $('tr.sele_tr').remove();
						     layer.close(index);
		    			},
		    			error:function(data){		
		    				alert('删除失败!');
		    			}
		      		}); 
			      });
				}else{
					layer.open({
					  title: '提示'
					  ,content: '请先选择一条数据!'
					});     
				}
			});
			
		});
		
	</script>
</html>