require.config({
	baseUrl:'/fx/js/',
	shim:{
		'jquery.niceScroll': {
			deps: ['jquery'],
			exports: 'jquery.niceScroll'
				},
		'mmGrid':{
			deps:['jquery'],
			exports:'mmGrid'
		},
		'table2excel':{
			deps:['jquery'],
			exports:'table2excel'
		}
	},
	paths:{
		jquery:'js_files/jquery-1.9.1.min',
		mmGrid:'js_files/mmGrid',
		niceScroll:'js_files/jquery.nicescroll.min',
		dialog:'dialog',
		wdatePicker:'./My97DatePicker/WdatePicker'
	}
});
require(['jquery','mmGrid','niceScroll','wdatePicker','dialog'],function($,mmGrid,niceScroll,wdatePicker,dialog){
	var wh=$(window).height()-200+"px",
		ww=$(window).width()-260+"px";
	$('.boxtop').css('width',ww);
	$('.boxtop').css('height',wh);
	$('#page').css('width',ww);
	$('#d1').val(dialog.today());
	$('#d2').val(dialog.today() );
	 
	//列参数;
    var cols = [
        { title:'机构号', name:'OGCD' ,width:60, align:'left' },         
        { title:'机构名称', name:'ORNM' ,width:150, align:'left' },        
        { title:'优惠名称', name:'FVNM' ,width:80, align:'left' },
        { title:'优惠规则', name:'RULE' ,width:300, align:'left'},
        { title:'状态', name:'STAT' ,width:60, align:'center' }
    ];
    var juesedata=false,jigoudata=false,jueseda,jigouda,listnumtotal;
    var userkey=JSON.parse( sessionStorage.getItem('menu') ).userKey;
    //请求机构名称列表
    $.ajax({
		url:'/fx/addFavrule.do',
		type:'post',
		async:false,
		data:userkey,
		dataType:'json',
			contentType:'application/json;charset=utf-8',
			beforeSend:function(){
				str='';
			},
		success:function(data){
			 var collectdata=JSON.parse(data.codeMessage);
			 if(data.code==00){
				 for(var i in collectdata){
					str+='<option value="'+collectdata[i].OGCD+'">'+collectdata[i].ORNM+'</option>'
				}
				$('.eccustomer').html(str);
			}else{
				//异常 
			}
		}
	});
 
     var ornm=$('.eccustomer option:selected').text(),
         vo={'ornm':ornm,'pageNo':1,'pageSize':10};
     getList(vo);
     renpage();
         
     $('.query').click(function(){
	      getList({'ornm':$('.eccustomer option:selected').text(),'pageNo':1,'pageSize':10});
	      renpage();
     });
     
     //点击添加优惠规则
     $('.add').click(function(){
    	 dialog.adddiscountAdd('adddiscountrules');
     });
     //点击添加优惠规则的确认按钮
     $('body',parent.document).on('click','.adddiscountrules .preserve',function(){
    	 var ogcd=$('.ogcd',parent.document).val();
    	 $(this).closest('.zhezhao').remove();
    	 dialog.discountRuleEdit('editdiscountrules','add');
    	 $.ajax({
     		url:'/fx/getSearchInitList.do',
     		type:'post',
     		contentType:'application/json',
     		data:ogcd,
     		async:true,
     		success:function(data){
     			 var collectdata=JSON.parse(data.codeMessage);
     			if(data.code==00){
     				$('.editdiscountrules .ogcd',parent.document).val(ogcd);
     				$('.editdiscountrules .disnum',parent.document).val(collectdata.FVID);
     				$('.editdiscountrules .disname',parent.document).val(collectdata.FVNM);
     				$('.editdiscountrules .maxdis',parent.document).val(collectdata.mymax);
     				
    			}else{
    				//异常 
    			}
     		}
     	}); 
    	 
     });
     //点击添加优惠规则的添加按钮；
 	$('body',parent.document).on('click','.editdiscountrules .disadd',function(){
 		var condition=$('.condition option:selected',parent.document).text();
 		if(condition=="请选择"){
 			dialog.choicedata('请选择条件！','adddiscountrules');
 		}else if(condition=="区间"){
 			dialog.discountRuleEditAdd('discountRuleEditAdd','between'); 
 		}else{
 			dialog.discountRuleEditAdd('discountRuleEditAdd','add'); 
 		}
  });
 	 //点击添加优惠规则的添加的保存按钮；
 	$('body',parent.document).on('click','.discountRuleEditAdd .preserve',function(){
 		var condition=$('.condition option:selected',parent.document).val(),
 		    numvalue=$('.numvalue',parent.document).val(),
 		    dispoint=$('.dispoint',parent.document).val(),
 		    small=$('.small',parent.document).val(),
		    big=$('.big',parent.document).val(),
		    bian=$('.variable',parent.document).val(),
		    str1='set('+bian+')',str;  
 		 if(condition=='between'){
 			 if(small>big){
				$('.big',parent.document).parent('div').find('re').remove();
				$('.big',parent.document).parent('div').append('<re>较小值需要小于较大值</re>');
 			}else{
 				$('.big',parent.document).parent('div').find('re').remove();
				str= '<p><span>'+condition+'</span><span>'+small+'到'+big+'</span><input type="text" class="pointnum" value="'+dispoint+'"/></p>';
	 		    $('.divsmain',parent.document).append(str);
	 		    $('.divsmain p',parent.document).each(function(i,v){
	 	 			 condition=$(v).find('span:eq(0)').text(); 
	 	  			 myvalue=$(v).find('span:eq(1)').text();
	 	  			 mydata=$(v).find('input').val();
	 	  			 str1+=''+condition+'('+myvalue+','+mydata+');'
	 	 		})
	 	 		//str1+=''+condition+'('+small+'到'+big+','+dispoint+')'
	 		    $('textarea',parent.document).html(str1);
 			    $(this).closest('.zhezhao').remove();  
 			}
 		}else{
 			str= '<p><span>'+condition+'</span><span>'+numvalue+'</span><input type="text" class="pointnum" value="'+dispoint+'"/></p>';
 		    $('.divsmain',parent.document).append(str);
 		    $('.divsmain p',parent.document).each(function(i,v){
 	 			 condition=$(v).find('span:eq(0)').text(); 
 	  			 myvalue=$(v).find('span:eq(1)').text();
 	  			 mydata=$(v).find('input').val();
 	  			 str1+=''+condition+'('+myvalue+','+mydata+');'
 	 		})
 	 		//str1+=''+condition+'('+numvalue+','+dispoint+')'
 		    $('textarea',parent.document).html(str1);
 	 		$(this).closest('.zhezhao').remove();  		
 		}
 	});
 	 //点击优惠规则编辑的删除按钮；
 	$('body',parent.document).on('click','.discountpub .disdel',function(){
 		 $('.tabpbgc',parent.document).remove();
 	});
 	 //点击优惠规则编辑的保存按钮；
 	$('body',parent.document).on('click','.editdiscountrules .add .preserve',function(){
 		 var defau=$('.static',parent.document).val(),
 		     bian=$('.variable',parent.document).val(),
 		     maxyh=$('.maxdis',parent.document).val(),
 		     idog=$('.ogcd',parent.document).val(),
 		     yhbm=$('.disnum',parent.document).val(),
 		     yhmc=$('.disname',parent.document).val(),addVo,list,
 		     condition=$('.tabpbgc span:eq(0)',parent.document).text(),
 		     myvalue=$('.tabpbgc span:eq(1)',parent.document).text(),
 		     mydata=$('.tabpbgc input',parent.document).val(),con='';
          $('.divsmain p',parent.document).each(function(i,v){
 			 condition=$(v).find('span:eq(0)').text(); 
  			 myvalue=$(v).find('span:eq(1)').text();
  			 mydata=$(v).find('input').val();
  			 con+=''+condition+'('+myvalue+','+mydata+');'
 		  })
 		     con=con.replace(/到/g,',');
 		     addVo={'userkey':userkey,'defau':defau,'bian':bian,'maxyh':maxyh,'idog':idog,'yhbm':yhbm,'yhmc':yhmc,'con':con};
	    // if($('.divsmain p',parent.document).hasClass('tabpbgc')){
	    	 if(defau==''||bian==''||maxyh==''){
	  			dialog.choicedata('必输项不能为空','adddiscountrules'); 
	  		 }else{
	  			$.ajax({
	  	     		url:'/fx/insertFavrule.do',
	  	     		type:'post',
	  	     		contentType:'application/json',
	  	     		data:JSON.stringify(addVo),
	  	     		async:true,
	  	     		success:function(data){
	  	     			 var collectdata=JSON.parse(data.codeMessage);
	  	     			if(data.code==00){
	  	     				$('.editdiscountrules .add .preserve',parent.document).closest('.zhezhao').remove();
	  	     				dialog.choicedata('添加成功','adddiscountrules'); 
	  	     				getList(vo);
	  	     				renpage();
	  	    			}else{
	  	    				//异常 
	  	    				dialog.choicedata('添加失败','adddiscountrules'); 
	  	    			}
	  	     		}
	  	     	}); 
	  			 
	  		 }
      });
     //点击修改优惠规则
     $('.modify').click(function(){
		 if( $('.box tbody tr').hasClass('selected') ){
			if($('.box tr.selected td:eq(2) span').text()=='最大优惠'){
			   dialog.choicedata('不可单独修改最大优惠','adddiscountrules');
			}else{
			   dialog.discountRuleEdit('editdiscountrules','modify');
			   $('.ogcd',parent.document).val($('.box tr.selected td:eq(0) span').text());
			   $('.disnum',parent.document).val($('.box tr.selected td:eq(2)').attr('fvid'));
			   $('.disname',parent.document).val($('.box tr.selected td:eq(2) span').text());
			   var idog=$('.box tr.selected td:eq(0) span').text(),
	    		   fvid=$('.box tr.selected td:eq(2)').attr('fvid'),str='';
			   $.ajax({
		    		url:'/fx/getFavruleList.do',
		    		type:'post',
		    		contentType:'application/json',
		    		data:JSON.stringify({'idog':idog,'fvid':fvid}),
		    		async:true,
		    		success:function(data){
		    			var userdata=JSON.parse( data.codeMessage);
		    			if(data.code==00){
		    				$('.condition option:selected',parent.document).text(userdata.mYLABLE );
		    				$('.condition option:selected',parent.document).attr('value',userdata.mYLABLE );
		    				$('.maxdis',parent.document).val((userdata.mYMAX).split('(')[1].split(')')[0]);
		    				$('.static',parent.document).val(userdata.mYDEFAULT );
		    				$('.variable',parent.document).val(userdata.mYINT);
		    			}else if(data.code==01){
		       				//获取数据失败 
		       			}else if(data.code==02){
		       			    //获取数据失败  
		       			}
		    		}
		    	}); 
			   $.ajax({
		    		url:'/fx/getLableList.do',
		    		type:'post',
		    		contentType:'application/json',
		    		data:JSON.stringify({'idog':idog,'fvid':fvid}),
		    		async:true,
		    		success:function(data){
		    			var userdata=JSON.parse(data.codeMessage);
		    			 if(data.code==00){
		    				for(var i in userdata){
		    					str+='<p><span>'+userdata[i].mYLABLE+'</span><span>'+userdata[i].mYVALUE+'</span><input type="text" class="pointnum" value="'+userdata[i].mYDATA+'"/></p>';
		 					}
		    				$('.divsmain',parent.document).append(str);
		    			}else if(data.code==01){
		       				//获取数据失败 
		       			}else if(data.code==02){
		       			    //获取数据失败  
		       			}
		    		}
		    	}); 
			   
			   
			}
		 }else{
			dialog.choicedata('请选择一条数据','adddiscountrules');
		}
	});
    //点击修改弹窗里的优惠规则编辑的保存按钮 
    $('body',parent.document).on('click','.editdiscountrules .modify .preserve',function(){
  		 var defau=$('.static',parent.document).val(),
  		     bian=$('.variable',parent.document).val(),
  		     maxyh=$('.maxdis',parent.document).val(),
  		     idog=$('.ogcd',parent.document).val(),
  		     yhbm=$('.disnum',parent.document).val(),
  		     yhmc=$('.disname',parent.document).val(),
  		     tiao=$('.condition option:selected',parent.document).text(),
  		     addVo,list,condition,myvalue,mydata,con='',list=[];
           $('.divsmain p',parent.document).each(function(i,v){
  			 condition=$(v).find('span:eq(0)').text(); 
   			 myvalue=$(v).find('span:eq(1)').text();
   			 mydata=$(v).find('input').val();
   			 con+=''+condition+'('+myvalue+','+mydata+');'
//        	   list.push({
//					  'MYLABLE':$(v).find('span:eq(0)').text(),
//					  'MYVALUE':$(v).find('span:eq(1)').text(),
//					  'MYDATA':$(v).find('input').val()
//				   });
          
           })
  		     con=con.replace(/到/g,',');
//           $.each(list,function(i,v){
//        	    
//           })
  		     addVo={'userkey':userkey,'defau':defau,'bian':bian,'maxyh':maxyh,'idog':idog,'yhbm':yhbm,'yhmc':yhmc,'con':con,'tiao':tiao};
 	   
 	    	 if(defau==''||bian==''||maxyh==''){
 	  			dialog.choicedata('必输项不能为空','adddiscountrules'); 
 	  		 }else{
 	  			$.ajax({
 	  	     		url:'/fx/doUpdateFav.do',
 	  	     		type:'post',
 	  	     		contentType:'application/json',
 	  	     		data:JSON.stringify(addVo),
 	  	     		async:true,
 	  	     		success:function(data){
 	  	     			console.log(data) 
 	  	     			 if(data.code==00){
 	  	     				$('.editdiscountrules .modify .preserve',parent.document).closest('.zhezhao').remove();
 	  	     				dialog.choicedata('修改成功','adddiscountrules'); 
 	  	     				getList(vo);
 	  	     				renpage();
 	  	    			}else{
 	  	    				//异常 
 	  	    				dialog.choicedata('修改失败','adddiscountrules'); 
 	  	    			}
 	  	     		}
 	  	     	}); 
 	  			 
 	  		 }
       }); 
     //点击删除按钮
     $('.dellete').click(function(){
		 if( $('.box tbody tr').hasClass('selected') ){
		    dialog.cancelDate('确认要删除当前记录吗？','discountpub'); 
		 }else{
			dialog.choicedata('请选择一条数据','adddiscountrules');
		}
	});
    //点击删除的确认按钮
    $('body',parent.document).on('click','.discountpub .confirm',function(){
    	var idog=$('.box tr.selected td:eq(0) span').text(),
    		fvid=$('.box tr.selected td:eq(2)').attr('fvid');
    	$.ajax({
    		url:'/fx/getDeleteList.do',
    		type:'post',
    		contentType:'application/json',
    		data:JSON.stringify({'idog':idog,'fvid':fvid}),
    		async:true,
    		success:function(data){
    			if(data.code==00){
    				$('.discountpub .confirm',parent.document).closest('.zhezhao').remove();
    				getList(vo);
    				renpage();
    				dialog.choicedata('删除成功','adddiscountrules'); 
       			}else if(data.code==01){
       				dialog.choicedata('删除异常','adddiscountrules');  
       			}else if(data.code==02){
       				dialog.choicedata('删除异常','adddiscountrules');   
       			}
    		}
    	}); 
	});
     //点击请选择一条数据的确定按钮；
	$('body',parent.document).on('click','.adddiscountrules .sure',function(){
		  $(this).closest('.zhezhao').remove();
	});
	//关闭弹窗
	$('body',parent.document).on('click','.discountpub .close,.discountpub .cancel',function(){
		  $(this).closest('.zhezhao').remove();
		   
	});
	 //P选择的排他处理
    $('body',parent.document).on('click','.divsmain p ',function(){
		 $(this).addClass('tabpbgc').siblings().removeClass('tabpbgc');
    });
	 
    //封装请求列表
	function getList(obj){
		$.ajax({
    		url:'/fx/getValueList.do',
    		type:'post',
    		contentType:'application/json',
    		data:JSON.stringify(obj),
    		async:false,
    		success:function(data){
    			if(data.code==00){
       				userdata=JSON.parse( data.codeMessage );
       				ren({'cols':cols,'wh':wh,'userdata':userdata.list});
       				listnumtotal=userdata.total;
//       			 $('.page').remove();
//    				$('.boxtop').append('<div class="page"><span class="first">首页</span><span class="prev">上一页</span><span class="next">下一页</span><span class="last">末页</span><span class="nopage">第<b class="Nopage">'+userdata.pageNum+'</b>页/共<b class="Totalpage">'+userdata.pages+'</b>页</span><span class="totalpage">共<b class="total">'+userdata.total+'</b>条记录</span></div>')
       			}else if(data.code==01){
       				ren({'cols':cols,'wh':wh,'userdata':''});
       			}else if(data.code==02){
       				ren({'cols':cols,'wh':wh,'userdata':''});
       			}
    		}
    	}); 
	}
	//点击分页;
    $('.boxtop').on('click','.page .first',function(){
    	var Nopage=parseInt($('.Nopage').text());
 	    if(Nopage>1){
 	    	Nopage=1;
 	    	ornm=$('.eccustomer option:selected').text(),
 	        getList({'ornm':ornm,'pageNo':Nopage,'pageSize':10});
 	     }
 	    
    });
	$('.boxtop').on('click','.prev',function(){
		var Nopage=parseInt($('.Nopage').text());
	    if(Nopage>1){
	    	Nopage=Nopage-1;
	    	ornm=$('.eccustomer option:selected').text(),
	        getList({'ornm':ornm,'pageNo':Nopage,'pageSize':10});
	     }
	     
	});
	$('.boxtop').on('click','.next',function(){
		var Nopage=parseInt($('.Nopage').text());
		   Totalpage=parseInt($('.Totalpage').text());
	    if(Nopage<Totalpage){
	    	Nopage=Nopage*1+1;
	    	ornm=$('.eccustomer option:selected').text(),
	        getList({'ornm':ornm,'pageNo':Nopage,'pageSize':10});
	     }
	     
	});
	$('.boxtop').on('click','.last',function(){
		var Nopage=parseInt($('.Nopage').text());
		   Totalpage=parseInt($('.Totalpage').text());
	    if(Nopage<Totalpage){
	    	Nopage=Totalpage;
	    	ornm=$('.eccustomer option:selected').text(),
	        getList({'ornm':ornm,'pageNo':Nopage,'pageSize':10});
	    }
	     
	});
	//封装渲染列表函数
	function ren( obj ){
		$('.boxtop').html('');
    	$('#ascrail2000').remove();
    	$('.boxtop').append('<div class="box"></div>');
		var mmg = $('.box').mmGrid({
				height:obj.wh
				, cols: obj.cols
				,items:obj.userdata
	            , nowrap:true
	            , fullWidthRows:true
	            , checkCol:obj.checked
	            , multiSelect:obj.select
	            ,showBackboard:true
	  	});
		 $(".mmg-bodyWrapper").niceScroll({
				touchbehavior:false,
				cursorcolor:"#666",
				cursoropacitymax:0.7,
				cursorwidth:6,
				background:"#ccc",
				autohidemode:false,
				horizrailenabled:obj.horizrailenabled
		  });
		 mmg.on('loadSuccess',function(){
			$('.box tbody tr').each(function(i,v){
				$(v).find('td:eq(2)').attr('fvid',obj.userdata[i].FVID);
				 
			});
		 });
	}
	function renpage(){
		layui.use(['laypage', 'layer'], function(){
			  var laypage = layui.laypage,layer = layui.layer;
			  //完整功能
			  laypage.render({
			    elem: 'page'
			    ,count:listnumtotal
			    ,theme: '#5a8bff'
			    ,layout: ['count', 'prev', 'page', 'next', 'limit', 'skip']
			    ,jump: function(obj,first){
			    	if(!first){ //点击跳页触发函数自身，并传递当前页：obj.curr
			    		getList({
			    			'ornm':$('.eccustomer option:selected').text(),
			    			'pageSize':$('.layui-laypage-limits select option:selected').attr('value')*1,
	    				    'pageNo':obj.curr
			    			});
			    	}	
			    }
			  });
		});
	} 	
	  
})
