<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>每周盈亏</title>
<meta charset="utf-8">
<link rel="stylesheet" href="../../css/demo.css" type="text/css"/>
<script type="text/javascript" src="../../js/jquery-1.11.3.js"></script>
<script type="text/javascript" src="../../js/ichart.1.2.1.min.js"></script>
<style type="text/css">
	 
</style>
</head>
<script type="text/javascript">
	var wdata = new Array();
	var wvalue = new Array(),maxWvalue,minWvalue,aveWvalue;

	var mdata = new Array();
	var mvalue = new Array(),maxMvalue,minMvalue,aveMvalue;
	$(function() {
		var selectData=[];
		getSelectData(); 
		if(selectData.length==0){
	       $('#canvasDiv').html('');
	       $('#canval').html('');
	    }else{
	    	 getSurvyValue(selectData);
	    }
		 
		$(':checkbox', window.parent.document).change(function() {
			selectData = [];
			getSelectData();
			if(selectData.length==0){
		    	 $('#canvasDiv').html('');
		    	 $('#canval').html('');
		     }else{
		    	 getSurvyValue(selectData);
		     }
			getheight();
		});
		function getheight(){
	 	     var main=window.parent.document.getElementById("listiframe");
	 	     var thisheight = document.getElementsByTagName('body')[0].offsetHeight  + 10;
	 	     main.style.height=thisheight+'px';
		}
		function getSurvyValue(obj) {
			$.ajax({
				type : 'post', //传输类型  
				async : false, //同步执行  
				url : '/qtadmin/basic/profit',
				data:{"account":obj},
				success : function(data) {
					result = data;
					wvalue=[];
					mvalue=[];
					wdata=[];
					mdata=[];
					var res = JSON.parse(data);
					if( res.week.length==0&&res.month.length==0){
						$('#canvasDiv').text('该账户暂无数据!').css({'text-align':'center'});  
					}else{
						for (i in res.week) {
							//获取每周权益的数据
							if( res.week[i].weekvalue !='0.0'){
								wvalue.push({'name':res.week[i].weekcount,'value':parseFloat(res.week[i].weekvalue).toFixed(2)})
								wdata.push(res.week[i].weekvalue);
							}
						}
						wdata.sort(sortNumber); 
						maxWvalue=(wdata[wdata.length-1]*1).toFixed();
						minWvalue=(wdata[0]*1).toFixed();
						aveWvalue=((maxWvalue-minWvalue)/5).toFixed();
						 
						for(i in wvalue){
						   if(wvalue[i].value>0){
							   wvalue[i].color='#DC5044' 
						   }else if(wvalue[i].value<0){
							   wvalue[i].color='#17A05E'
						   }else{
							   wvalue[i].color='#8D9DC2'
						   }
						}
	                     
						for (i in res.month) {
							//获取每月权益的数据
							mvalue.push({'name':res.month[i].monthcount,'value':parseFloat(res.month[i].monthvalue).toFixed(2)})
							mdata.push(res.month[i].monthvalue);
						}
						mdata.sort(sortNumber); 
						maxMvalue=(mdata[mdata.length-1]*1).toFixed();
						minMvalue=(mdata[0]*1).toFixed();
						aveMvalue=((maxMvalue-minMvalue)/5).toFixed();
						for(i in mvalue){
							   if(mvalue[i].value>0){
								   mvalue[i].color='#DC5044' 
							   }else if(mvalue[i].value<0){
								   mvalue[i].color='#17A05E'
							   }else{
								   mvalue[i].color='#8D9DC2'
							   }
						}
					 
						drawWeek(wvalue);
						drawMoon(mvalue);
					}
					$(window.parent.document).find("#listiframe").load(function() {
		                  var main = $(window.parent.document).find("#listiframe");
		                  var thisheight = $(document).height() + 10;
		                  main.height(thisheight);
		            });
               },
				error : function(errorMsg) {
					 
				}
			});
		}
		//获取多账户的勾选值
		function getSelectData(){
			$('.accountmany li',window.parent.document).each(function(i,e){
				   var sel=$(e).find('input').prop('checked');
				  if(sel==true){
					   selectData.push($(e).find('input').val());		
				  }
		    })
		}
		//排序用到的函数
		function sortNumber(a,b){
		    return a - b
		}
		function drawWeek(obj){
			var chart=new iChart.Column3D({
				render : 'canvasDiv',
				data: obj,
				tip:{
					enable :true,
					showType:'Option follow',
					listeners:{
						 //tip:提示框对象、name:数据名称、value:数据值、text:当前文本、i:数据点的索引
						parseText:function(tip,name,value,text,i){
							if(/^\-?\d+$/.test(value)){
								value+='.00';
							}
							return "<div style='font-weight:600'>"+name+"<br />"+value+"</div>";				
						 }
					}
				},
				animation:true,//是否设置动画
				title : '每周盈亏',
				width : 900,
				height : 400,
				//align:'left',
				offsetx:10,
				legend : {
					enable : false
				},
				sub_option:{
					label:false
				},
				footnote : {
					text : '注：每周盈亏为每周所有平仓盈亏之和',
					color : '#ff0000',
					fontsize : 11,
					padding : '50'
				},
				coordinate:{//坐标系的配置项
					background_color : '#f2f2f2',
					color_factor : 0.14,
					width:800,
					height:400,
					scale:[{
						 position:'left',	
						 start_scale:parseFloat(minWvalue),
						 end_scale:parseFloat(maxWvalue),
						 scale_space:aveWvalue,
						 listeners:{
							parseText:function(t,x,y){
								//return {text:t+"%"}
							}
						}
					}]
				}
			});
			//利用自定义组件构造左侧说明文本
			chart.plugin(new iChart.Custom({
				drawFn : function() {
					//计算位置
					var coo = chart.getCoordinate(),
						x = coo.get('originx'),
						y = coo.get('originy');
					//在左上侧的位置，渲染一个单位的文字
					chart.target.textAlign('start')
						.textBaseline('bottom')
						.textFont('600 11px Verdana')
						.fillText('盈亏(元)', x - 40, y - 28, false, '#6d869f');
				}
			}));
			chart.draw();
		}
		function drawMoon(obj){
			var chart=new iChart.Column3D({
				render : 'canval',
				data: obj,
				tip:{
					enable :true,
					showType:'Option follow',
					listeners:{
						 //tip:提示框对象、name:数据名称、value:数据值、text:当前文本、i:数据点的索引
						parseText:function(tip,name,value,text,i){
							if(/^\-?\d+$/.test(value)){
								value+='.00';
							}
							return "<div style='font-weight:600'>"+name+"<br />"+value+"</div>";				
						 }
					}
				
				},
				animation:true,//是否设置动画
				title : '每月盈亏',
				width : 900,
				height : 400,
				//align:'left',
				//column_width:30,
				offsetx:10,
				legend : {
					enable : false
				},
				sub_option:{
					label:false
				},
				footnote : {
					text : '注：每月盈亏为每月所有平仓盈亏之和',
					color : '#ff0000',
					fontsize : 11,
					padding : '5 0'
				},
				coordinate:{//坐标系的配置项
					background_color : '#f2f2f2',
					color_factor : 0.14,
					width:800,
					height:400,				
					scale:[{
						 position:'left',	
						 start_scale:parseFloat(minMvalue),
						 end_scale:parseFloat(maxMvalue),
						 scale_space:aveMvalue,
						 listeners:{
							parseText:function(t,x,y){
								//return {text:t+"%"}
							}
						}
					}]
				}
			});
			//利用自定义组件构造左侧说明文本
			chart.draw();
		}
	});
</script>
<body>
	<div id="canvasDiv"></div>
	<div id="canval"></div>
</body>
</html>