<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>多空盈亏</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<style type="text/css">
* {
	margin: 0;
	padding: 0;
}

.main {
	width: 100%;
	height: 100%;
	position: absolute;
}

.quarter-div {
	width: 50%;
	/* 	height: 50%; */
	float: left;
}
</style>
<script src="<%=basePath%>js/ichart.1.2.1.min.js"></script>
<script src="<%=basePath%>js/jquery-1.11.3.js"></script>
</head>
<body>
<div class="main_par">
	<h2 style="text-align: center">多空盈亏</h2>
	<div class="main">
		<div id="longChart" class="quarter-div"></div>
		<div id="shortChart" class="quarter-div"></div>
		<div id="profitChart" class="quarter-div"></div>
		<div id="lossChart" class="quarter-div"></div>
		<span style="color: red; text-align: center; float:left">注：多空盈亏为多单和空单的平仓盈亏</span>
	</div>
</div>
</body>
<script type="text/javascript">
$(function() {
		var result, selectData = [];
		getSelectData();
		getAccount(selectData);
		
		//点击父页面的复选框；
	  	$(':checkbox',window.parent.document).change(function(){
	  	     selectData=[];
	  	     getSelectData();
	  	     getAccount(selectData);
	  	     
	  	   var main = $(window.parent.document).find("#listiframe");
			var thisheight = $(document).height() + 10;
			main.height(thisheight);
	  	});
		
		function getAccount(obj) {
			 if( selectData.length==0){
					$('.main_par h2,.main_par span').hide();
					$('.quarter-div').html('');
			}else{
					$('.main_par h2,.main_par span').show();
	
				$.ajax({
					type : 'post', //传输类型  
					async : false, //同步执行  
					url : '/qtadmin/basic/dk',
					data : {
						"account" : obj
					},
					success : function(data) {
						if( data ){
							result = JSON.parse(data);
							getData();
						}else{
							$('.main_par').text('当前账户暂无数据!').css('text-align','center');
						}
						$(window.parent.document).find("#listiframe").load(function() {
							var main = $(window.parent.document).find("#listiframe");
							var thisheight = $(document).height() + 10;
							main.height(thisheight);
						}); 
					},
					error : function(errorMsg) {
						alert("加载数据失败！");
						window.location.href = "/qtadmin/page/empty.jsp";
					}
				});
			}
		}
		function getData() {
			var data1 = result.map(function(item) {
				return item[0];
			});
			for (k in data1) {
				if (data1[k].name == "多单盈利") {
					data1[k].color = "#61a0a8";
				} else if (data1[k].name == "多单亏损") {
					data1[k].color = "#d48265";
				}
			}
			var data2 = result.map(function(item) {
				return item[1];
			});
			for (k in data2) {
				if (data2[k].name == "空单亏损") {
					data2[k].color = "#8fad38";
				} else if (data2[k].name == "空单盈利") {
					data2[k].color = "#91c7ae";
				}
			}

			var data3 = result.map(function(item) {
				return item[2];
			});
			for (k in data3) {
				if (data3[k].name == "空单盈利") {
					data3[k].color = "#91c7ae";
				} else if (data3[k].name == "多单盈利") {
					data3[k].color = "#61a0a8";
				}
			}
			var data4 = result.map(function(item) {
				return item[3];
			});
			for (k in data4) {
				if (data4[k].name == "多单亏损") {
					data4[k].color = "#d48265";
				} else if (data4[k].name == "空单亏损") {
					data4[k].color = "#8fad38";
				}
			}
			//多单盈利   VS 多单亏损
			var longChart = new iChart.Pie3D({
				render : 'longChart',
				color_factor : '0.01',
				mutex : true,
				padding : '2 10',
				width : 606,
				height : 280,
				data : data1,
				shadow : true,
				animation : true,//是否设置动画
				tip : {//鼠标滑过的提示
					enable : true
				},
				shadow_blur : 8,
				background_color : '#f2f2f2',
				gradient : false,//关闭渐变效果
				color_factor : 0.28,
				gradient_mode : 'RadialGradientOutIn',
				showpercent : true,
				decimalsnum : 2,
				/* legend : { //设置提示图标颜色；(导航图标相关设置,可设置边框，字体颜色等)
					enable : true,
					padding : 10,
					color : '#0030a0',
					background_color : null,//透明背景
					align:'left',
					fontsize:14,
					border:{
						width:[0,1,0,0],
						color:'#343b3e'
					},
					offsety:-80				
				}, */
				sub_option : {
					offsetx : -40,
					border : {
						enable : false
					},
					label : {
						sign : false,//设置禁用label的小图标
						line_height : 10,
						padding : 4,
						border : {
							enable : true,
							radius : 4,//圆角设置
							color : '#e0e5e8'
						},
						fontsize : 11,
						fontweight : 600,
						color : '#444444'
					}
				},
				border : {
					width : [ 0, 0, 0, 0 ],
					color : '#1e2223'
				}
			});

			longChart.bound(0);

			//空单亏损   VS 空单盈利
			var shortChart = new iChart.Pie3D({
				render : 'shortChart',
				color_factor : '0.01',
				mutex : true,
				padding : '2 10',
				width : 606,
				height : 280,
				data : data2,
				shadow : true,
				animation : true,//是否设置动画
				tip : {//鼠标滑过的提示
					enable : true
				},
				shadow_blur : 8,
				background_color : '#f2f2f2',
				gradient : false,//关闭渐变效果
				color_factor : 0.28,
				gradient_mode : 'RadialGradientOutIn',
				showpercent : true,
				decimalsnum : 2,
				sub_option : {
					offsetx : -40,
					border : {
						enable : false
					},
					label : {
						sign : false,//设置禁用label的小图标
						line_height : 10,
						padding : 4,
						border : {
							enable : true,
							radius : 4,//圆角设置
							color : '#e0e5e8'
						},
						fontsize : 11,
						fontweight : 600,
						color : '#444444'
					}
				},
				border : {
					width : [ 0, 0, 0, 0 ],
					color : '#1e2223'
				}
			});
			shortChart.bound(0);
			//空单盈利   VS 多单盈利
			var profitChart = new iChart.Pie3D({
				render : 'profitChart',
				color_factor : '0.01',
				mutex : true,
				padding : '2 10',
				width : 606,
				height : 280,
				data : data3,
				shadow : true,
				animation : true,//是否设置动画
				tip : {//鼠标滑过的提示
					enable : true
				},
				shadow_blur : 8,
				background_color : '#f2f2f2',
				gradient : false,//关闭渐变效果
				color_factor : 0.28,
				gradient_mode : 'RadialGradientOutIn',
				showpercent : true,
				decimalsnum : 2,
				sub_option : {
					offsetx : -40,
					border : {
						enable : false
					},
					label : {
						sign : false,//设置禁用label的小图标
						line_height : 10,
						padding : 4,
						border : {
							enable : true,
							radius : 4,//圆角设置
							color : '#e0e5e8'
						},
						fontsize : 11,
						fontweight : 600,
						color : '#444444'
					}
				},
				border : {
					width : [ 0, 0, 0, 0 ],
					color : '#1e2223'
				}
			});

			profitChart.bound(0);

			//多单亏损   VS 空单亏损
			var lossChart = new iChart.Pie3D({
				render : 'lossChart',
				color_factor : '0.01',
				mutex : true,
				padding : '2 10',
				width : 606,
				height : 280,
				data : data4,
				shadow : true,
				animation : true,//是否设置动画
				tip : {//鼠标滑过的提示
					enable : true
				},
				shadow_blur : 8,
				background_color : '#f2f2f2',
				gradient : false,//关闭渐变效果
				color_factor : 0.28,
				gradient_mode : 'RadialGradientOutIn',
				showpercent : true,
				decimalsnum : 2,
				sub_option : {
					offsetx : -40,
					border : {
						enable : false
					},
					label : {
						sign : false,//设置禁用label的小图标
						line_height : 10,
						padding : 4,
						border : {
							enable : true,
							radius : 4,//圆角设置
							color : '#e0e5e8'
						},
						fontsize : 11,
						fontweight : 600,
						color : '#444444'
					}
				},
				border : {
					width : [ 0, 0, 0, 0 ],
					color : '#1e2223'
				}
			});
			lossChart.bound(0);
		}
		
		function getSelectData() {
			$('.accountmany li', window.parent.document).each(function(i, e) {
				var sel = $(e).find('input').prop('checked');
				if (sel == true) {
					selectData.push($(e).find('input').val());
				}
			})
		}
	});
</script>
</html>
