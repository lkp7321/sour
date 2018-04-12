<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!-- 持仓偏好图表 -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="en">
<head>
	<meta charset="UTF-8" />
	<title>持仓偏好</title>
	<link rel="stylesheet" href="../../css/demo.css" type="text/css"/>
	<meta name="keywords" content="keyword1,keyword2,keyword3">
    <meta name="description" content="this is my page">
    <meta name="content-type" content="text/html; charset=UTF-8">
    <script type="text/javascript" src="../../js/jquery-1.11.3.js"></script>
	 
</head>
<body >
	<div id='canvasDiv'></div>
</body>
<script type="text/javascript" src="../../js/ichart.1.2.1.min.js"></script>
<script>
	$(function(){  
	    var selectData=[];
	    getSelectData();
	    if(selectData.length==0){
	    	 $('#canvasDiv').html('');
	     }else{
	    	 getSurvyValue(selectData);
	     }
		 
		$(':checkbox',window.parent.document).change(function(){
		     selectData=[];
		     getSelectData();
		     if(selectData.length==0){
		    	 $('#canvasDiv').html('');
		     }else{
		    	 getSurvyValue(selectData);
		     }
		     getheight();
		});
		function getSurvyValue(obj){
			 $.ajax({  
                 type : 'post',  //传输类型  
                 async : false,  //同步执行  
                 url : '/qtadmin/basic/prefer', 
                 data:{"account":obj},
                 success : function(data) {
			          
		          //result=data;
             		result = JSON.parse(data);
             		if( result==''){
             			$('#canvasDiv').text('该账户暂无数据!').css('text-align','center');
             		}else{
             			var chart = new iChart.Pie3D({
        					render : 'canvasDiv',
        					title:{
        						text:'持仓偏好',
        						color:'#000',
        						height:40,
        						border:{//主标题下边的分割线
        							enable:false,
        							width:[0,0,1,0],
        							color:'#343b3e'
        						}
        					},
        					color_factor:'0.01',
        					mutex:true,
        					padding:'2 50',//底部的padding值
        					footnote:{//底部的注释
        						text:'注：某品种的百分比=该品种的持仓市值÷所有品种的持仓市值',
        						color:'red',
        						height:35,
        						border:{//底部的
        							enable:false,
        							width:[2,0,0,0],
        							color:'#343b3e'
        						}
        					},
        					width : 945,
        					height : 515,
        					data:result,
        					shadow:true,
        					animation:true,//是否设置动画
        					tip:{//鼠标滑过的提示
        							enable :true
        					},
        					/*shadow_color:'#15353a',*/
        					shadow_blur:8,
        					background_color :'#f2f2f2',
        					gradient:false,//关闭背景渐变效果
        					color_factor:0.28,
        					gradient_mode:'RadialGradientOutIn',//背景渐变的样式
        					showpercent:true,
        					decimalsnum:2,//百分比小数点精度(小数点后几位)
        					legend:{		//设置提示图标颜色；(导航图标相关设置,可设置边框，字体颜色等)
        						enable:false, //右边导航的不显示
        						padding:30,
        						color:'#0030a0',
        						border:{
        							width:[0,0,0,1],
        							color:'#343b3e'
        						},
        						background_color : '#ffffff',
        					},
        					sub_option:{
        						offsetx:-40,
        						border:{
        							enable:false
        						},
        						label : {
        							/*background_color:'#fefefe',*/
        							sign:false,//设置禁用label的小图标
        							line_height:10,
        							padding:4,
        							border:{
        								enable:true,
        								radius : 4,//圆角设置
        								color:'#e0e5e8'
        							},
        							fontsize:11,
        							fontweight:600,
        							color : '#444444'
        						}
        					},
        					border:{
        						width:[0,0,0,0],
        						color:'#1e2223'
        					}
        				});
        					chart.bound(0);
             		}
				},  
                   error : function(errorMsg) {  
                  	 alert("加载数据失败！");
         				//window.location.href="/qtadmin/page/empty.jsp";   
                   }  
                 });
			 $(window.parent.document).find("#listiframe").load(function() {
				  getheight();
		     });
         }
		function getheight(){
		     var main=window.parent.document.getElementById("listiframe");
		     var thisheight = document.getElementsByTagName('body')[0].offsetHeight  + 10;
		     main.style.height=thisheight+'px';
		}
		function getSelectData(){
			$('.accountmany li',window.parent.document).each(function(i,e){
				   var sel=$(e).find('input').prop('checked');
				  if(sel==true){
					   selectData.push($(e).find('input').val());		
				  }
		    });
		}

  }); 

</script>
</html>