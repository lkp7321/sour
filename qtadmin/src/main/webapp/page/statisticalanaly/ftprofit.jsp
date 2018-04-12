<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>品种盈亏</title>
    <meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="../../css/demo.css">
	-->
 	<script src="../../js/ichart.1.2.1.min.js"></script>
	<script src="../../js/jquery-1.11.3.js"></script>
  </head>
  
  <body>
    <div id="main"></div>
    <div id="totalmain"></div>
   <!--  <span style="color: red; text-align: center">注：品种盈亏为每个品种的平仓盈亏之和</span> -->
  </body>
  <script src="${pageContext.request.contextPath }/js/statisticsmain.js"></script>
  <script type="text/javascript">
  	$(function(){  
	   var  selectData=[],result,
	        pvalue,pdata,maxPvalue,minPvalue,avePvalue,
	        tvalue,tdata,maxTvalue,minTvalue,aveTvalue;
	    getSelectData();
	    if(selectData.length==0){
	       $('#main').html('');
	       $('#totalmain').html('');
	    }else{
	    	loadData(selectData);
	    }
		$(':checkbox',parent.document).change(function(){
		     selectData=[];
		     getSelectData();
		     if(selectData.length==0){
		       $('#main').html('');
		       $('#totalmain').html('');
		    }else{
		    	loadData(selectData);
		    }
		      var main = $(window.parent.document).find("#listiframe");
              var thisheight = $(document).height() + 10;
              main.height(thisheight);
		});
	   
		function loadData(selectData) {
			$.ajax({
				type : 'post', //传输类型  
				async : false, //同步执行  
				url : '/qtadmin/basic/ft',
				data:{"account":selectData},
				success : function(data) {
					///console.log( data.psFt);
				    if( data==''){
				    	$('#main').text('该账户暂无数据!').css('text-align','center');
				    }else{
				    	result = JSON.parse(data);
					    pvalue=[];
					    tvalue=[];
					    pdata=[];
					    tdata=[];
				    	 for (i in result.psFt) {
								if(result.psFt[i].psProfit!=0){
									//获取平仓盈亏的数据
									pvalue.push({'name':result.psFt[i].pid,'value':parseFloat(result.psFt[i].psProfit).toFixed(2)}) 
									pdata.push((result.psFt[i].psProfit).toFixed());
								}					 
							}
						    pdata.sort(sortNumber); 
						    maxPvalue=pdata[pdata.length-1]*1;
						    minPvalue=pdata[0]*1;
							avePvalue=(maxPvalue-minPvalue)/5;
							for(i in pvalue){
							   if(pvalue[i].value>0){
								   pvalue[i].color='#DC5044' 
							   }else if(pvalue[i].value<0){
								   pvalue[i].color='#17A05E'
							   }else{
								   pvalue[i].color='#8D9DC2'
							   }
							}
							for (i in result.smFt) {
								if(result.smFt[i].smProfit!=0){
									//获取总盈亏的数据
									tvalue.push({'name':result.smFt[i].pid,'value':result.smFt[i].smProfit.toFixed(2)})
									tdata.push((result.smFt[i].smProfit).toFixed());
								}
							}
							tdata.sort(sortNumber); 
						    maxTvalue=tdata[tdata.length-1]*1;
						    minTvalue=tdata[0]*1;
							aveTvalue=(maxTvalue-minTvalue)/5;
							for(i in tvalue){
								   if(tvalue[i].value>0){
									   tvalue[i].color='#DC5044' 
								   }else if(tvalue[i].value<0){
									   tvalue[i].color='#17A05E'
								   }else{
									   tvalue[i].color='#8D9DC2'
								   }
							}
							drawOffset(pvalue);
							drawTotalOffset(tvalue);
				    }
				    $(window.parent.document).find("#listiframe").load(function() {
		                  var main = $(window.parent.document).find("#listiframe");
		                  var thisheight = $(document).height() + 10;
		                  main.height(thisheight);
		            });	
				},
				error : function(errorMsg) {
					 alert("加载数据失败！");
	    			 window.location.href="/qtadmin/page/empty.jsp";   ///数据加载失败；
				}
			});
		}
		//平仓盈亏	 
		function drawOffset(obj){
			var chart=new iChart.Column3D({
				render : 'main',
				data: obj,
				tip:{
					enable :true,
					listeners:{
						 //tip:提示框对象、name:数据名称、value:数据值、text:当前文本、i:数据点的索引
						parseText:function(tip,name,value,text,i){
							return name+' '+value+'.00';
						}
					}
				},
				animation:true,//是否设置动画
				title : '平仓盈亏',
				width : 900,
				height : 400,
				//align:'left',
				offsetx:10,
				legend : {
					enable : false
				},
				Painter:{
					background_color:'#f00',
				},
				sub_option:{
					label:false
				},
				footnote : {
					text : '注：品种盈亏为每个品种的平仓盈亏之和',
					color : '#ff0000',
					fontsize : 11,
					padding : '5 0'
				},
				coordinate:{//坐标系的配置项
					background_color : '#f2f2f2',
					color_factor : 0.14,
					width:780,
					height:400,
					grids:{
						horizontal:{ way:'share_alike', value:10 }
					},
					scale:[{
						 position:'left',	
						 start_scale:minPvalue,
						 end_scale:maxPvalue,
						 scale_space:avePvalue,
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
		//总盈亏
		function drawTotalOffset(obj){
			var chart=new iChart.Column3D({
				render : 'totalmain',
				data: obj,
				tip:{
					enable :true,
					listeners:{
						 //tip:提示框对象、name:数据名称、value:数据值、text:当前文本、i:数据点的索引
						parseText:function(tip,name,value,text,i){
							return name+' '+value+'.00';
						}
					}
				},
				animation:true,//是否设置动画
				title : '总盈亏',
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
					text : '注：总盈亏为每个品种的总盈亏之和',
					color : '#ff0000',
					fontsize : 11,
					padding : '5 0'
				},
				coordinate:{//坐标系的配置项
					background_color : '#f2f2f2',
					color_factor : 0.14,
					width:780,
					height:400,
					scale:[{
						 position:'left',	
						 start_scale:minTvalue,
						 end_scale:maxTvalue,
						 scale_space:aveTvalue,
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
		//排序用到的函数
		function sortNumber(a,b){
		    return a - b
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
