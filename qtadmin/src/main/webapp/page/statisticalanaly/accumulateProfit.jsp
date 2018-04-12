<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>累积盈亏</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	 <script src="<%=basePath%>js/ichart.1.2.1.min.js"></script>
    <script src="<%=basePath%>js/jquery-1.11.3.js"></script> 
  </head>
  
 <body>
    <div id="container" ></div>
 </body>
  <script src="${pageContext.request.contextPath }/js/statisticsmain.js"></script>
  <script type="text/javascript">
   $(function(){  
	  	 var oneData=$('.accountmany li input:eq(0)',window.parent.document).val(),selectData=[];
  	     getSelectData();
  	     if(selectData.length==0){
  	       $('#container').html('');
  	     }else{
  	    	loadData(selectData);
  	     }
		   
		$(':checkbox',window.parent.document).change(function(){
			 selectData=[];
		     getSelectData();
		     if(selectData.length==0){
	  	       $('#container').html('');
	  	     }else{
	  	    	loadData(selectData);
	  	     }
		      var main = $(window.parent.document).find("#listiframe");
              var thisheight = $(document).height() + 10;
              main.height(thisheight);
		});
	   
	    var result;
	    function loadData(selectData) {
				/* if( selectData.length<=0){
					$('#container').html('');
					$('.txt_tips').hide();
				}else{ 
					$('.txt_tips').show(); */
					$.ajax({ 
		             type : 'post',  //传输类型  
		             async : false,  //同步执行  
		             url : '/qtadmin/basic/sum',
		             data:{"account":selectData},          	
		             success : function(data) {
		            /*  if(data==""){
		            	 window.location.href="/qtadmin/page/empty.jsp";
		             } */
		             	 result=data; 
			             if( result==''){
			            	 $('#container').text('该账户暂无数据!').css('text-align','center');
			             }else{
			            	 getData(result);
			             }
		             },  
		             error : function(errorMsg) {  
		            	 alert("加载数据失败！");
		 				 window.location.href="/qtadmin/page/empty.jsp";
		             }  
		         }); 
		    }
	 // }     
		function getData(result){
			var valu=[];
		 	var sum = JSON.parse(result).sum;
		 	var date = sum.map(function (item) {  ///日期；
		    	return item.tradeDays.slice(0,4)+'-'+item.tradeDays.slice(4,6)+'-'+item.tradeDays.slice(6,8);
			});
			var values = sum.map(function (item) {
		   	    return item.sum;
			});
			var values1 = sum.map(function (item) {
		   	 return item.coms;
			});
			
			var scale_x=parseInt( (date.length-2)/5);
			var  a =[0,1,2,3,4]; 
			for( var i in a ) {		
				valu.push( date[a[i]*(scale_x+1)] );
			}
			valu[5]=date[date.length-1];
			
			var min_values=values[0],max_values=0,first_scalespace;			
			for(var i=0;i<values.length;i++){
				if( min_values>values[i]){
					min_values=Math.round( values[i]);
				}else{
					min_values=Math.round( min_values );
				}
			}
			for(var j=0;j<values.length;j++){
				if( max_values<values[j]){
					max_values=Math.round( values[j] );
				}
			}		
			var min_values1=values1[0],max_values1=0;
			for(var i=0;i<values1.length;i++){
				if( min_values1>values1[i]){
					min_values1=Math.round( values1[i]);
				}else{
					min_values1=Math.round( min_values1 );
				}
			}
			for(var j=0;j<values1.length;j++){
				if( max_values1<values1[j]){
					max_values1=Math.round( values1[j] );
				}
			}
			if(min_values<=min_values1){
				min_values=min_values;
			}else{
				min_values=min_values1;
			}
			if(max_values<=max_values1){
				max_values=max_values1;
			}else{
				max_values=max_values;
			}
	
			first_scalespace=Math.round( (max_values-min_values)/5 );
			min_values=min_values-first_scalespace;
			max_values=max_values+first_scalespace;
				var data = [
					         	{
					         		name : '累计盈亏',
					         		value:values,
					         		color:'#aad0db',
					         		line_width:2
					         	},
					         	{
					         		name : '手续费',
					         		value:values1,
					         		color:'#f68f70',
					         		line_width:2
					         	}
					         ];

					var labels = valu;
					var chart = new iChart.LineBasic2D({
						render : 'container',
						data: data,
						align:'center',
						title : '累计盈亏',
						width : 900,
						height : 515,
						background_color:'#F2F2F2',
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
								parseText:function(tips,name,value,text,i){
									if( /^\d+\.\d{1}$/.test( value) ){
										value=value+'0';
									}
									 return "<div style='font-weight:600'>"+date[i]+name+":"+value+"";
								"</div> "			
								}
							}
						},
						/* tipMocker:function(tips,i){
							return "<div style='font-weight:600'>"+
									labels[Math.floor(i/12)]+" "+//日期
									(((i%12)*2<10?"0":"")+(i%12)*2)+":00"+//时间
									"</div>"+tips.join("<br/>");
						}, */
						legend : {
							enable : true,
							row:1,//设置在一行上显示，与column配合使用
							column : 'max',
							valign:'top',
							sign:'bar',
							background_color:null,//设置透明背景
							offsetx:-80,//设置x轴偏移，满足位置需要
							border : true
						},
						crosshair:{
							enable:true,
							line_color:'#62bce9'//十字线的颜色
						},
						sub_option : {
							label:false,
							point_size:2,
							smooth:true
						},
						coordinate:{
							width:740,
							height:260,
							gridlinesVisible:false,
							axis:{
								color:'#dcdcdc',
								width:1
							},
							scale:[{
								 position:'left',	
								 start_scale:min_values,
								 end_scale:max_values,
								 scale_space:first_scalespace,
								 scale_size:2,
								 scale_color:'#9f9f9f'
							},{
								 position:'bottom',	
								 labels:labels
							}]
						},
						footnote : {
							text : '注：累计手续费为每天手续费累加之和;累计盈亏=静态权益-累计手续费-本金;',
							color : '#ff0000',
							fontsize : 11,
							padding : '5 0'
						}
					});
				
				//开始画图
				chart.draw();			
			}
		 	$(window.parent.document).find("#listiframe").load(function() {
					  var main=window.parent.document.getElementById("listiframe");
				     var thisheight = document.getElementsByTagName('body')[0].offsetHeight  + 10;
				     main.style.height=thisheight+'px';
	      	  });
			
			 function getheight(){
			     var main=window.parent.document.getElementById("listiframe");
			     var thisheight = document.getElementsByTagName('body')[0].offsetHeight  + 10;
			    // console.log( thisheight  )
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
