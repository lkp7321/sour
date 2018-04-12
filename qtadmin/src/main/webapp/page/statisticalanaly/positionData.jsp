
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>

<base href="<%=basePath%>">

<title>持仓信息</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link rel="stylesheet" href="<%=basePath%>layui/css/layui.css"
	media="all">
<script type="text/javascript" src="<%=basePath%>js/jquery-1.11.3.js"></script>
<style type="text/css">
body {
	text-align: center;
}

#div {
	margin: 0 auto;
	/* border:0px solid; */
	width: 90%;
}

table {
	border: 1px solid #F0F9FE;
	border-collapse: collapse;
	width: 100%;
}

th {
	border: 1px solid;
	background-color: #F0F9FE;
}

td {
	text-align: center;
	border: 1px solid;
}
</style>

</head>
<body>
	<div class="demoTable">
		交易日期搜索：
		<div class="layui-inline">
			<table>
				<tr>
					<td style="border-style: none;"><input style="height:30px;"
						class="layui-input" id="demoReload" placeholder="点击选择日期"></td>
					<td style="border-style: none;">—</td>
					<td style="border-style: none;"><input style="height:30px;"
						class="layui-input" id="demoReload2" placeholder="点击选择日期"></td>
				</tr>
			</table>
		</div>
		<button class="layui-btn layui-btn-sm" data-type="reload">搜索</button>
	</div>
	<div id="d2">
		<table id="demo"></table>
	</div>
</body>
<script src="<%=basePath%>layui/layui.all.js" charset="utf-8"></script>
<script type="text/javascript">
	$(function(){
		var date = new Date();
		var month = date.getMonth() + 1;
		var day = date.getDate();
		var nowDate = date.getFullYear() + "-" + (month < 10 ? "0" + month : month)
				+ "-" + (day < 10 ? "0" + day : day);
		date.setDate(date.getDate() - 7);
		var month2 = date.getMonth() + 1;
		var day2 = date.getDate();
		var oldDate = date.getFullYear() + "-"
				+ (month2 < 10 ? "0" + month2 : month2) + "-"
				+ (day2 < 10 ? "0" + day2 : day2);
		$("#demoReload").val(oldDate);
		$("#demoReload2").val(nowDate);

		layui.use('laydate', function() {
			var laydate = layui.laydate;
			var s = laydate.render({
				elem : '#demoReload', //指定元素  	   
				theme : 'grid',
				max : $("#demoReload2").val(),
				done : function(value, date) {
					e.config.min = {
						year : date.year,
						month : date.month - 1,
						date : date.date,
					};
				}
			});
			var e = laydate.render({
				elem : '#demoReload2' //指定元素  	   
				,
				theme : 'grid',
				min : $("#demoReload").val(),
				done : function(value, date) {
					s.config.max = {
						year : date.year,
						month : date.month - 1,
						date : date.date,
					};
				}
			});
		});
		var result,selectData=[],
		beginTime=$("#demoReload").val(),
   	    endTime=$("#demoReload2").val();;
		getSelectData();
		getPositionData(selectData,beginTime,endTime);
		
	   	//点击父页面的复选框；
	  	$(':checkbox',window.parent.document).change(function(){
	  	     selectData=[];
	  	     getSelectData();
	  	     beginTime=$("#demoReload").val(),
	         endTime=$("#demoReload2").val();
	  		 getPositionData(selectData,beginTime,endTime);
	  		 getheight();
	  	});
   	
		function getPositionData( obj,obj1,obj2){
			if( obj.length==0){
				document.getElementsByTagName('body')[0].style.opacity=0;
			} else {
				document.getElementsByTagName('body')[0].style.opacity=1;
				var table = layui.table;
				
				//展示已知数据
				table.render({
					elem : '#demo',
					url : '/qtadmin/basic/pmsg',
					type : 'post',
					height : 500,
					cols : [ [ //标题栏
					{
						field : 'date',
						title : '交易日期',
						width : 120,
						sort : true,
						fixed : 'left'
					}, {
						field : 'cid',
						title : '交易账号',
						width : 130,
						fixed : 'left'
					}, {
						field : 'insid',
						title : '合约代码',
						width : 150
					}, {
						field : 'dir',
						title : '买卖',
						width : 100
					}, {
						field : 'price',
						title : '价格',
						width : 100
					}, {
						field : 'pos',
						title : '持仓量',
						width : 100
					}, {
						field : 'ypos',
						title : '昨仓',
						width : 100
					}, {
						field : 'tpos',
						title : '今仓',
						width : 100
					}, {
						field : 'nz',
						title : '可平量',
						width : 100
					}, {
						field : 'pct',
						title : '持仓均价',
						width : 150
					}, {
						field : 'oct',
						title : '开仓均价',
						width : 150
					}, {
						field : 'act',
						title : '全部均价',
						width : 150
					}, {
						field : 'vm',
						title : '合约乘数',
						width : 100
					}, {
						field : 'hde',
						title : '投保',
						width : 100
					}, {
						field : 'cv',
						title : '平仓量',
						width : 80
					}, {
						field : 'cp',
						title : '平仓盈亏',
						width : 100
					}, {
						field : 'op',
						title : '持仓盈亏',
						width : 100
					}, {
						field : 'pp',
						title : '开仓盈亏',
						width : 150
					}, {
						field : 'cs',
						title : '手续费',
						width : 150
					}, {
						field : 'mg',
						title : '保证金',
						width : 120
					}, {
						field : 'ut',
						title : '更新时间',
						width : 150
					}, {
						field : 'it',
						title : '插入时间',
						width : 150
					}, {
						field : 'cpt',
						title : '逐笔平仓盈亏',
						width : 150
					}, {
						field : 'lsp',
						title : '昨结算价',
						width : 120
					} ] ],
					skin : 'row' //表格风格		    
					,
					text:{
						none:'该账户暂无数据!'
					},
					even : true,
					page : true //是否显示分页
					,
					limits : [ 5, 7, 10 ],
					limit : 10 //每页默认显示的数量
					,
					where : {
						"account" : obj,
						beginTime: obj1,	
			    		endTime: obj2,
					},
					id : 'testReload',
					done:function(res, curr, count){
						console.log( res )	
					}
				});
				var $ = layui.$, active = {
					reload : function() {
						var demoReload = $('#demoReload');
						var demoReload2 = $('#demoReload2');
						//执行重载			    
						table.reload('testReload', {
							page : true,
							where : {
								beginTime: demoReload.val(),	
					    		endTime: demoReload2.val(),
								"account" : obj
							}
						});
					}
				};
				$('.demoTable .layui-btn').on('click', function() {
					var type = $(this).data('type');
					active[type] ? active[type].call(this) : '';
				});
			}
		}
		$(window.parent.document).find("#listiframe").load(function() {
			getheight();
		});
		function getheight(){
	 	     var main=window.parent.document.getElementById("listiframe");
	 	     var thisheight = document.getElementsByTagName('body')[0].offsetHeight  + 10;
	 	     main.style.height=thisheight+'px';
		}
		//获取选中的账户值			
		function getSelectData() {
			$('.accountmany li', window.parent.document).each(function(i, e) {
				var sel = $(e).find('input').prop('checked');
				if (sel == true) {
					selectData.push($(e).find('input').val());
				}
			})
		}
	})
</script>
</html>
