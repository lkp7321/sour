<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>权益信息</title>
    
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
  <body >
	<div id="main" ></div>
	<div id="main1"></div>
  </body>
<script type="text/javascript">
	$(function(){  
	   var oneData=$('.accountmany li input:eq(0)',window.parent.document).val(),selectData=[];
  	       getSelectData();
		loadData(selectData);
		$(':checkbox',window.parent.document).change(function(){
			 selectData=[];
			 getSelectData();
		     loadData(selectData);		   
		     getheight();
		});
		var result;
		function loadData(selectData) {
			if( selectData.length<=0){
				$('#main,#main1').html('');
				$('.txt_tips').hide();
			}else{ 
				$('.txt_tips').show();
				$.ajax({
					type : 'post', //传输类型  
					async : false, //同步执行  
					url : '/qtadmin/basic/lative',
					data:{"account":selectData},
					success : function(data) {
						/* if(data==""){
							window.location.href="/qtadmin/page/empty.jsp";
						} */
						result =data;
						if( data==''){
							$('#main').text('该账户暂无数据!').css('text-align','center');
						}else{
							getData(result );
						}
						$(window.parent.document).find("#listiframe").load(function() {
							 var main=window.parent.document.getElementById("listiframe");
						     var thisheight = document.getElementsByTagName('body')[0].offsetHeight  + 10;
						     main.style.height=thisheight+'px';
				        });
					},
					error : function(errorMsg) {
						alert("加载数据失败！");
						window.location.href="/qtadmin/page/empty.jsp";
					}
				});
			}
		}
		/*解析json数据*/
		function getData( result) {
			 var cumu=JSON.parse(result).netWorth,  //累计净值
			  	 cumu1=JSON.parse(result).everyCumula,//每日权益
			  	 cumux=[],cumux1=[],values1=[];
			 	 
				 var dates =cumu.map(function(item) {	//第二个日期；
					return item.tradingDay.slice(0,4)+'-'+item.tradingDay.slice(4,6)+'-'+item.tradingDay.slice(6,8);
				});
				 var dates1 =cumu1.map(function(item) {	//第一个日期；
					return item.tradingDay.slice(0,4)+'-'+item.tradingDay.slice(4,6)+'-'+item.tradingDay.slice(6,8);
				});
				for(var i in cumu){
					if(cumu[i].cumulative!='0.0'){
						values1.push( cumu[i].cumulative );
					}
				}
				//console.log(values1)
				var values = cumu1.map(function(item) {
					return item.value;
				});
				
				var first_scale=parseInt( dates.length/6 );
				/* for( var i in dates1 ){
					if( i% 15 ==0){
						cumux1.push( dates1[i]);
					}
				} */
				var scale_x=parseInt( (dates1.length-2)/5);
				var  a =[0,1,2,3,4]; 
				for( var i in a ) {		
					cumux1.push( dates1[a[i]*(scale_x+1)] );
				}
				cumux1[5]=dates1[dates1.length-1];
			
			//取纵坐标最大值和最小值
			var min_values=values[0],max_values=0,first_scalespace,
				min_values1=values1[0],max_values1=0,first_scalespace1;
			for(var i=0;i<values.length;i++){
				if( min_values>values[i]){
					min_values=Math.round( values[i]);
				}else{
					min_values=Math.round( min_values );
				}
			}//Math.ceil;  Math.floor;
			for(var j=0;j<values.length;j++){
				if( max_values<values[j]){
					max_values=Math.round( values[j] );
				}
			}
			//获取第二个图中的最大值、最小值；
			for(var i=0;i<values1.length;i++){
				if( min_values1>values1[i]){
					min_values1= values1[i]*1;
				}else{
					min_values1= min_values1*1;
				}
			}
			for(var j=0;j<values1.length;j++){
				if( max_values1<values1[j]){
					max_values1=values1[j]*1;
				}
			}
			first_scalespace=Math.round( (max_values-min_values)/5 );
			min_values=min_values-first_scalespace;
			max_values=max_values+first_scalespace;
			/* 截取小数点后6位 */
			first_scalespace1=( ( (max_values1-min_values1)/2 ).toFixed(4) )*1;
			min_values1=( min_values1.toFixed(4) )*1-first_scalespace1;
			max_values1= ( max_values1.toFixed(4) )*1+ first_scalespace1;
			var zhedianwid=(900/cumu1.length).toFixed(2);
			var data = [
	         	{
	         		name : '每日权益',
	         		value:values,
	         		color:'#0d8ecf',
	         		line_width:2
	         	}
				 /* 本金 */
	         	/* {
					name : 'amount',
					value:amount,
					color:'#ef7707',
					line_width:2
				}  */
	         ];
					var labels = cumux1;
					var chart = new iChart.LineBasic2D({
					render : 'main',
					data: data,
					align:'center',
					title : {
						text:'每日权益',
						fontsize:24,
						//color:'#f7f7f7'
					},
					width : 900,
					height : 400,
					//offsetx:10,
					offsetx:-5,
					shadow:true,
					shadow_color : '#20262f',
					shadow_blur : 4,
					shadow_offsetx : 0,
					shadow_offsety : 2,
					point_space:zhedianwid,///折点间的距离；
					//background_color:'#383e46',
					background_color :'#f2f2f2',
					tip:{
						enable:true,
						shadow:true,
						move_duration:400,
						border:{
							 enable:true,
							 radius : 5,
							 width:2,
							 color:'#3f8695'
						},
					listeners:{
							 //tip:提示框对象、name:数据名称、value:数据值、text:当前文本、i:数据点的索引
							parseText:function(tip,name,value,text,i){
								//return value;
								//判断如果小数点后只有一位，自动补0；
								if( /^\d+\.\d{1}$/.test( value) ){
									value=value+'0';
								}
								return "<div style='font-weight:600'>"+
										dates1[i]+'权益'+value
										"</div>";				
							}
						}
					},
					footnote : {
						text : '注：每日权益 = 静态权益 + 浮动盈亏',
						color : '#ff0000',
						fontsize : 11,
						padding : '5 0'
					},
					/*  tipMocker:function(tips,i){///？？
				
						return "<div style='font-weight:600'>"+
							dates[i]+'权益'+tips[0]
							"</div>";
					},  */
					crosshair:{
						enable:true,
						line_color:'#62bce9'
					},
					sub_option : {
						label:false,
						hollow_inside:false,
						point_size:2,
						smooth:true
					},
					coordinate:{
						width:740,//840
						height:260,
						gridlinesVisible:false,
						grid_color:'#cccccc',
						axis:{
							color:'#cccccc',
							width:[0,0,2,2]
						}, 
						 grids:{
							vertical:{
								way:'share_alike',
						 		value:5
							} 
						}, 
						scale:[{
							 position:'left',	
							 start_scale:min_values,
							 end_scale:max_values, 
							 scale_space: first_scalespace,
							 scale_size:2, 
							// label : {color:'#ffffff',fontsize:11},
							 scale_color:'#9f9f9f'
						},{
							 position:'bottom',	
							 //label : {color:'#ffffff',fontsize:11},
							 labels:labels
						  }		
					]
					}
				});
				//利用自定义组件构造左侧说明文本
					chart.plugin(new iChart.Custom({
							drawFn:function(){
								//计算位置
								var coo = chart.getCoordinate(),
									x = coo.get('originx'),
									y = coo.get('originy');
								//在左上侧的位置，渲染一个单位的文字
								chart.target.textAlign('start')
								.textBaseline('bottom')
								.textFont('600 14px Verdana')
								.fillText('权益',x-40,y-28,false,'#6d869f');
								
							}
					}));		
			chart.draw();	
			
			var data = [
			         	{
			         		name : '累计净值',
			         		value:values1,
			         		//color:'#0d8ecf',
			         		color:'#9966cc',
			         		line_width:2
			         	}
			         ];
					var labels = cumux1;
					var chart = new iChart.LineBasic2D({
					render : 'main1',
					data: data,
					align:'center',
					offsetx:-5,//10
					title : {
						text:'累计净值',
						fontsize:24,
						//color:'#f7f7f7'
					},
					width : 900,//1000
					height : 400,
					shadow:true,
					shadow_color : '#20262f',
					shadow_blur : 4,
					shadow_offsetx : 0,
					shadow_offsety : 2,
					//background_color:'#383e46',
					background_color :'#f2f2f2',
					tip:{
						enable:true,
						shadow:true,
						move_duration:400,
						border:{
							 enable:true,
							 radius : 5,
							 width:2,
							 color:'#3f8695'
						},
						listeners:{
							 //tip:提示框对象、name:数据名称、value:数据值、text:当前文本、i:数据点的索引
							parseText:function(tip,name,value,text,i){
								if( /^\d+\.\d{3}$/.test( value) ){
									value=value+'0';
								}else if( /^\d+\.\d{2}$/.test( value) ){
									value=value+'00';
								}
								return "<div style='font-weight:600'>"+
								dates1[i]+'累计净值是'+value
								"</div>";						
							}
						}
					},
					crosshair:{
						enable:true,
						line_color:'#62bce9'
					},
					sub_option : {
						label:false,
						hollow_inside:false,
						point_size:2,
						smooth:true
					},
					footnote : {
						text : '注：累计净值 = 静态权益 ÷ 本金',
						color : '#ff0000',
						fontsize : 11,
						padding : '5 0'
					},
					coordinate:{
						width:740,
						height:260,
						gridlinesVisible:false,
						grid_color:'#cccccc',
						axis:{
							color:'#cccccc',
							width:[0,0,2,2]
						},
						grids:{
							vertical:{
								way:'share_alike',
						 		value:5
							}
						},
						scale:[{
							 position:'left',
							 start_scale:min_values1,
							 end_scale:max_values1, 
							 scale_space: first_scalespace1,
							 scale_size:2,
							 //label : {color:'#ffffff',fontsize:11},
							 scale_color:'#9f9f9f'
						},{
							 position:'bottom',	
							// label : {color:'#ffffff',fontsize:11},
							 labels:labels,
							 scale_share:7
						}]
					}
				});
				
				//利用自定义组件构造左侧说明文本
					chart.plugin(new iChart.Custom({
							drawFn:function(){
								//计算位置
								var coo = chart.getCoordinate(),
									x = coo.get('originx'),
									y = coo.get('originy');
								//在左上侧的位置，渲染一个单位的文字
								chart.target.textAlign('start')
								.textBaseline('bottom')
								.textFont('600 14px Verdana')
								.fillText('净值',x-40,y-28,false,'#6d869f');
								
							}
					}));
			//开始画图
				chart.draw();
			}
			 function getheight(){
			     var main=window.parent.document.getElementById("listiframe");
			     var thisheight = document.getElementsByTagName('body')[0].offsetHeight  + 10;
			     main.style.height=thisheight+'px';
			}
			function getSelectData() {
				$('.accountmany li', window.parent.document).each(function(i, e) {
					var sel = $(e).find('input').prop('checked');
					if (sel == true) {
						selectData.push($(e).find('input').val());
					}
				});
			}
		});
                
</script>
</html>
