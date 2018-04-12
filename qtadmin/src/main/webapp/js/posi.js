$(function(){ 
	  var selectData=[],result;  
		 getSelectData();
		 if(selectData.length==0){ //没有选中账户；
	    	 $('#canvasDiv').html('');
	    	 $('#canvasDiv1').html('');
	     }else{
	    	 getSurvyValue(selectData);
	     }
		 //选择账户；
		 $(':checkbox',window.parent.document).change(function(){
		     selectData=[];
		     getSelectData();
		     if(selectData.length==0){
		    	 $('#canvasDiv').html('');    
		    	 $('#canvasDiv1').html('');
		     }else{
		    	 getSurvyValue(selectData);
		    	 getheight();
		     }
		     
	     });
	 //选中账户，进行数据渲染等;
     function getSurvyValue(obj){      ///为什么高度不对；
         $.ajax({  
             type : 'post',  //传输类型  
             async : false,  //同步执行 
             url : '/qtadmin/basic/spaceinfo', 
             data:{"account":obj},  //后加       	
             success : function(data) {
            	  result = JSON.parse( data );
            	
            	  if( result.s1.length==0&&result.s2.length==0){
            		  $('#canvasDiv').text('该账户暂无数据!').css({'text-align':'center'});   
            	  }else{
            		  getData(result);
            	  }
            	  
            	  $(window.parent.document).find("#listiframe").load(function() {
          			getheight();
                  });
	          },  
             error : function(errorMsg) {  
            	 alert("加载数据失败!");
             }  
      }); 
    };
    function getData(result){
      	var data1,data2,data3,data4,data5,cumux=[],cumux1=[];
      	
     	 data1 =result['s1'].map(function(item) {
			return item.date.slice(0,4)+'-'+item.date.slice(4,6)+'-'+item.date.slice(6,8);
		});
		 data2 = result['s1'].map(function (item) {
		 	data2number = Number(item.space);
		 	data2float = data2number.toFixed(2);
		 	return data2float;
		}); //每天仓位日期和数据;
		data3 =result['s2'].map(function(item) {
			return item.date1.slice(0,4)+'-'+item.date1.slice(4,6)+'-'+item.date1.slice(6,8);
		});
		data4 = result['s2'].map(function (item) {
			data4number = Number(item.value1);    //精度控制，精确到小数点后两位
			data4float = data4number.toFixed(2);
	    	return data4float;
		});
		data5 = result['s2'].map(function (item) {
			data5number = Number(item.value2);
			data5float = data5number.toFixed(2);
	    	//return item.value2;
	    	return data5float;
		});///每天持仓日期和时间；
		
		//第一张图中的最大最小值,根据最大最小计算纵轴区间；
		var min_values=data2[0],max_values=0,first_scalespace;
		for(var i=0;i<data2.length;i++){
			if( min_values>data2[i]){
				min_values= data2[i]*1;
			}else{
				min_values= min_values*1;
			}
		}
		for(var j=0;j<data2.length;j++){
			if( max_values<data2[j]){
				max_values=data2[j]*1;
			}
		}
		first_scalespace=( ( (max_values-min_values)/2 ).toFixed(6) )*1;
		
		//根据点数来取横坐标,
		var scale_x=parseInt( (data1.length-2)/5),
			scale_x1=parseInt( (data3.length-2)/5 );
		var  a =[0,1,2,3,4];  //定义横坐标
		for( var i in a ) {		//第一张图的坐标 cumux
			cumux.push( data1[a[i]*(scale_x+1)] );
		}
		cumux[5]=data1[data1.length-1];

		var  a =[0,1,2,3,4]; //定义横坐标
		for( var i in a ) {		//第一张图的坐标 cumux
			cumux1.push( data3[a[i]*(scale_x1+1)]);
		}
		cumux1[5]=data3[data3.length-1];

		 //日期的显示
		var chart = new iChart.LineBasic2D({
			render : 'canvasDiv',
			data:[ {
         		name : '仓位',
         		value:data2,
         		color:'#0d8ecf',
         		line_width:2
           }],
			align:'center',
			title : {
				text:'每天仓位',
				//fontsize:24,
				//color:'#f7f7f7'
			},
			//subtitle : {
				//text:'每天仓位信息',   //可去除该子标题
				//color:'#f1f1f1'
			//},
			//footnote : {
				//text:'数据来源：模拟数据',   //可删除底部信息
				//color:'#f1f1f1'
			//},
			width : 900,     //整个图标的宽
			height : 400,    //整个图标的高
			background_color :'#f2f2f2',
			shadow_blur : 4,
			offsetx:10,
			shadow_offsetx : 0,
			shadow_offsety : 2,
			//background_color:'#383e46',
			//shadow:true,
			//shadow_color : '#20262f',
			animation : true,//开启过渡动画
			animation_duration:600,//600ms完成动画
			tip:{
				enable:true,
				shadow:true,
				listeners:{
					 //tip:提示框对象、name:数据名称、value:数据值、text:当前文本、i:数据点的索引
					parseText:function(tip,name,value,text,i){
						if( /^\d+\.\d{1}$/.test(value) ){
							value+='0';
						}
						return "<span style='color:#005268;font-size:12px;'>"+data1[i]+" 仓位:<br/>"+
						"</span><span style='color:#005268;font-size:12px;'>"+value+"</span>";
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
				point_size:8,
				intersection:false
			},
			legend : {
				enable : true,
				row:1,//设置在一行上显示，与column配合使用
				column : 'max',
				valign:'top',
				sign:'bar',
				background_color:null,//设置透明背景
				offsetx:-20,//设置x轴偏移，满足位置需要
				border : true
			},
			coordinate:{
				width:740,    //坐标系的宽
				height:260,   //坐标系的高
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
				/* 	 start_scale:-20,    // 开始刻度和终止刻度不给出的话，默认取最大和最小值
					 end_scale:20, */
					 scale_size:4,
					  start_scale:min_values,
					  end_scale:max_values, 
					  //scale_space: first_scalespace,
					  decimalsnum:2,
					 label : {color:'#000',fontsize:11},
					 scale_color:'#9f9f9f'
				},{
					 position:'bottom',	
					 label : {color:'#000',fontsize:11},
					 labels:cumux
				}]
			}
		});
		//利用自定义组件构造左侧说明文本
		chart.plugin(new iChart.Custom({
				drawFn:function(){
					//计算位置
					var coo = chart.getCoordinate(),
						x = coo.get('originx'),
						y = coo.get('originy'),
						w = coo.width,
						h = coo.height;
					//在左上侧的位置，渲染一个单位的文字
					chart.target.textAlign('start')
					.textBaseline('bottom')
					.textFont('600 11px 微软雅黑')
					.fillText(' 仓位',x-40,y-12,false,'#9d987a')
					.textBaseline('top')
					//.fillText('(时间)',x+w+12,y+h+10,false,'#9d987a');
				}
			}));
			//开始画图
			chart.draw();
		 //以下为另一张图标所在区域
		//var labels = ["2012-08-01","2012-08-02","2012-08-03","2012-08-04","2012-08-05","2012-08-06"];
		//var labels = data3;
		var line = new iChart.LineBasic2D({
			render : 'canvasDiv1',
			data: [
		         	{
		         		name : '多单',
			         	value:data4,
		         		color:'#0d8ecf',
		         		line_width:2
		         	},
		         	{
		         		name : '空单',
		         		value:data5,
		         		color:'#ef7707',
		         		line_width:2
		         	}
		    ],
			align:'bottom',
			title : '每天持仓',
			width : 900,
			height : 400,
			background_color :'#f2f2f2',
			offsetx:10,
			animation : true,//开启过渡动画
			animation_duration:600,//600ms完成动画
			tip:{
				enable:true,
				shadow:true,
		        listeners:{
			 		//tip:提示框对象、name:数据名称、value:数据值、text:当前文本、i:数据点的索引
					parseText:function(tip,name,value,text,i){
						if( /^\d+\.\d{1}$/.test(value) ){
							value+='0';
						}else if( /^\d+$/.test(value) ){
							value+='.00';
						}
						return "<span style='color:#005268;font-size:12px;'>"+data3[i]+name+"仓位:<br/>"+
						"</span><span style='color:#005268;font-size:12px;'>"+value+"</span>";
					}
				}
			},
			legend : {
				enable : true,
				row:1,//设置在一行上显示，与column配合使用
				column : 'max',
				valign:'top',
				sign:'bar',
				background_color:null,//设置透明背景
				offsetx:-80,//设置x轴偏移，满足位置需要
				offsety:-30,
				border : true
			},
			crosshair:{
				enable:true,
				line_color:'#62bce9'
			},
			sub_option : {
				label:false,
				point_hollow : false,
				intersection:false
			},
			coordinate:{
				width:740,
				height:260,
				axis:{
					color:'#9f9f9f',
					width:[0,0,2,2]
				},
				offsety:-30,
				grids:{
					vertical:{
						way:'share_alike',
				 		value:5
					}
				},
				scale:[{
					 position:'left',	
					 /*  start_scale:0,
					 end_scale:100, 
					 scale_space:10,
					 scale_size:2, */
					  scale_color:'#9f9f9f'
				},{
					 position:'bottom',	
					 scale_space:30,
					 labels:cumux1
				}]
				
			}
		});	
			//利用自定义组件构造左侧说明文本
			line.plugin(new iChart.Custom({
					drawFn:function(){
						//计算位置
						var coo = line.getCoordinate(),
							x = coo.get('originx'),
							y = coo.get('originy'),
							w = coo.width,
							h = coo.height;
						//在左上侧的位置，渲染一个单位的文字
						line.target.textAlign('start')
						.textBaseline('bottom')
						.textFont('600 11px 微软雅黑')
						.fillText(' 仓位',x-40,y-12,false,'#9d987a')
						.textBaseline('top')
						//.fillText('(时间)',x+w+12,y+h+10,false,'#9d987a');
						
					}
			}));
		//开始画图
		line.draw();
		$(window.parent.document).find("#listiframe").load(function() {
			getheight();
        });
	};
	function getheight(){
	     var main=window.parent.document.getElementById("listiframe");
	     var thisheight = document.getElementsByTagName('body')[0].offsetHeight  + 10;
	    // console.log( thisheight );
	     main.style.height=thisheight+'px';
	}
	//获取选中的账户值			
	function getSelectData(){
  		$('.accountmany li',window.parent.document).each(function(i,e){
  			  var sel=$(e).find('input').prop('checked');
  			  if(sel==true){
  				   selectData.push($(e).find('input').val());	
  			  }
  	    });
 	 }
});
	