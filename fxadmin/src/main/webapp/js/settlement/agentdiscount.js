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
	 
	//列参数;
    var cols = [
        { title:'币种', name:'exnm' ,width:120, align:'left' },
        { title:'客户买入外币优惠折扣', name:'byds' ,width:120, align:'right'},
        { title:'客户卖出外币优惠折扣', name:'slds' ,width:120, align:'right'},
        { title:'起始日期', name:'stdt' ,width:120, align:'right'},
        { title:'结束日期', name:'eddt' ,width:120, align:'right'}
    ]
    var userkey=JSON.parse( sessionStorage.getItem('menu') ).userKey,prevpage,listnumtotal;
        
    //根据用户登录信息 获取机构号和机构名称
    $.ajax({
		url:'/fx/getOrgn.do',
		type:'post',
		contentType:'application/json',
		data:userkey,
		async:true,
		success:function(data){
			 if(data.code==00){
			 	 userdata=JSON.parse( data.codeMessage );
			     $('.agentname').val(userdata.exnm);
			     $('.agentnum').val(userdata.ogcd);
			     sessionStorage.obj =userdata.ogcd;
			}else if(data.code==01){
				//异常
			}
		}
	}); 
    //请求列表
    var ogcd=sessionStorage.getItem('obj'),
        ogcdVo={'ogcd':$('.agentnum').val(userdata.ogcd),'pageNo':1,'pageSize':10} 
    getOrgnlist(ogcdVo);
    renpage();
    //点击查询请求列表
    $('.qurey').click(function(){
    	 ogcd=$('.agentnum').val(),
    	 ogcdVo={'ogcd':ogcd,'pageNo':1,'pageSize':10} 
    	 getOrgnlist(ogcdVo);
    	 renpage();
    })
    //点击选择按钮
    $('.choose').click(function(){
    	dialog.agentChoose();
    	var OgcdVo={'ogcdtxt':'','ognmtxt':'','pageNo':1,'pageSize':10};
    	getFavRuleOgcd(OgcdVo);
    })
    //点击选择机构弹窗的查询按钮
    $('body',parent.document).on('click','.agentchoose .qurey',function(){
    	var Ognmtxt=$.trim($('.agentname',parent.document).val()),
            Ogcdtxt=$.trim($('.agentnum',parent.document).val()),
	        OgcdVo={'ogcdtxt':Ogcdtxt,'ognmtxt':Ognmtxt,'pageNo':1,'pageSize':10};
    	getFavRuleOgcd(OgcdVo);
	});
    //点击选择当前机构
    $('body',parent.document).on('click','.agentchoose .currentagent',function(){
    	$('.agentmain table tr',parent.document).each(function(i,v){
			if( $(v).hasClass("agentbgc")){
				//console.log($(v).hasClass("agentbgc"))
			    $('.agentnum').val($(v).find('td').eq(0).text());
				$('.agentname').val($(v).find('td').eq(1).text());
			}
		});
    	 ogcd=$('.agentnum').val(),
	     ogcdVo={'ogcd':ogcd,'pageNo':1,'pageSize':10} 
	     getOrgnlist(ogcdVo); 
    	 renpage();
    	 $(this).closest('.zhezhao').remove();
    });
  //封装点击选择 请求机构
    function getFavRuleOgcd(obj){
    	$.ajax({
			url:'/fx/selectFavRuleOgcd.do',
			type:'post',
			contentType:'application/json',
			data:JSON.stringify(obj),
			async:false,
			success:function(data){
			   if(data.code==00){
					var userdata=JSON.parse( data.codeMessage),
					    str="<tr><td class='ogcd'>机构号</td><td class='ognm'>机构名称</td></tr>",
					    str1='<span class="first">首页</span><span class="prevbtn">上一页</span><span class="nextbtn">下一页</span><span class="last">末页</span><span class="allnumpre">第<i class="nowpage">'+userdata.pageNum+'</i>页/共<i class="allpages">'+userdata.pages+'</i>页</span><span class="allnum">共'+userdata.total+'条记录</span>';
					pagedata=userdata; 
					for(var i in userdata.list){
				       str+="<tr><td class='one'>"+userdata.list[i].ogcd+"</td><td>"+userdata.list[i].ognm+"</td></tr>"
					}
					
					$('.agentbot',parent.document).html(' ').append(str1);
					$('.agentmain table',parent.document).html(' ').append(str);
				}else if(data.code==01){
					 //异常
				}
			}
		});
    }
    //点击添加按钮；
	$('.add').click(function(){
		dialog.discountAdd('agentdiscount','机构优惠','保存');
		$('.agentnum',parent.document).val($('.agentnum').val());
        //请求option
		$.ajax({
			url:'/fx/getAllEXNM.do',
			type:'get',
			async:false,
			dataType:'json',
 			contentType:'application/json;charset=utf-8',
 			beforeSend:function(){
 				str='';
 			},
			success:function(data){
				var collectdata=JSON.parse(data.codeMessage);
				if(data.code==00){
					for(var i in collectdata){
						str+='<option>'+collectdata[i].exnm+'</option>'
					}
					$('.currency',parent.document).html(str);
				}else{
					//异常 
				}
			}
		});
		 $('.buy',parent.document).blur(function(){
			 if($(this).val()==''||$(this).val()==undefined){
					$('.buy',parent.document).parent('div').find('re').remove();
					$('.buy',parent.document).parent('div').append('<re>客户买入外币优惠折扣不能为空</re>');
				}else if(parseInt($(this).val())>100){
					$('.buy',parent.document).parent('div').find('re').remove();
					$('.buy',parent.document).parent('div').append('<re>客户买入外币优惠折扣不能大于100</re>');
				}else{
					$('.buy',parent.document).parent('div').find('re').remove();
				} 
		 })
		 $('.sell',parent.document).blur(function(){
			 if($(this).val()==''||$(this).val()==undefined){
					$('.sell',parent.document).parent('div').find('re').remove();
					$('.sell',parent.document).parent('div').append('<re>客户卖出外币优惠折扣不能为空</re>');
				}else if(parseInt($(this).val())>100){
					$('.sell',parent.document).parent('div').find('re').remove();
					$('.sell',parent.document).parent('div').append('<re>客户卖出外币优惠折扣不能大于100</re>');
				}else{
					$('.sell',parent.document).parent('div').find('re').remove();
				}
		 })
		  $('#d1',parent.document).blur(function(){
			  if($(this).val()==''||$(this).val()==undefined){
					$('#d1',parent.document).parent('div').find('re').remove();
					$('#d1',parent.document).parent('div').append('<re>起始时间不能为空</re>');
				}else{
					$('#d1',parent.document).parent('div').find('re').remove();
				}
		 })
		  $('#d2',parent.document).blur(function(){
			  if($(this).val()==''||$(this).val()==undefined){
					$('#d2',parent.document).parent('div').find('re').remove();
					$('#d2',parent.document).parent('div').append('<re>结束时间不能为空</re>');
				}else{
					$('#d2',parent.document).parent('div').find('re').remove();
				}
		 })
		 
	});
	  
	//点击修改按钮;
	$('.modify').click(function(){
		var a=0,arr=[];
		$('.box tr').each(function(){
			if( $(this).find('input[type="checkbox"]').prop('checked')==true){
				a++;
			}
		})
		if(a==0){
			dialog.choicedata('请选择一条数据','agentdiscount');
		}else if(a==1){
			dialog.discountAdd('agentdiscount','机构优惠','修改');
			$('.agentnum',parent.document).val( $('.agentnum').val());
		    $('.box tr').each(function(i,v){
				if( $(v).find('input[type="checkbox"]').prop('checked') ){
				    $('.currency option',parent.document).text($(v).find('td').eq(1).find('span').text()).parent('.currency').attr('disabled','disabled');
					$('.buy',parent.document).val($(v).find('td').eq(2).find('span').text());
					$('.sell',parent.document).val($(v).find('td').eq(3).find('span').text());
					$('#d1',parent.document).val($(v).find('td').eq(4).find('span').text());
					$('#d2',parent.document).val($(v).find('td').eq(5).find('span').text());
				 }
			});
		}else{
			dialog.discountAdd('agentdiscount','机构优惠修改','修改');
			$('.agentnum',parent.document).val( $('.agentnum').val());
		}
		$('.buy',parent.document).blur(function(){
			 if($(this).val()==''||$(this).val()==undefined){
					$('.buy',parent.document).parent('div').find('re').remove();
					$('.buy',parent.document).parent('div').append('<re>客户买入外币优惠折扣不能为空</re>');
				}else if(parseInt($(this).val())>100){
					$('.buy',parent.document).parent('div').find('re').remove();
					$('.buy',parent.document).parent('div').append('<re>客户买入外币优惠折扣不能大于100</re>');
				}else{
					$('.buy',parent.document).parent('div').find('re').remove();
				} 
		 })
		 $('.sell',parent.document).blur(function(){
			 if($(this).val()==''||$(this).val()==undefined){
					$('.sell',parent.document).parent('div').find('re').remove();
					$('.sell',parent.document).parent('div').append('<re>客户卖出外币优惠折扣不能为空</re>');
				}else if(parseInt($(this).val())>100){
					$('.sell',parent.document).parent('div').find('re').remove();
					$('.sell',parent.document).parent('div').append('<re>客户卖出外币优惠折扣不能大于100</re>');
				}else{
					$('.sell',parent.document).parent('div').find('re').remove();
				}
		 })
		  $('#d1',parent.document).blur(function(){
			  if($(this).val()==''||$(this).val()==undefined){
					$('#d1',parent.document).parent('div').find('re').remove();
					$('#d1',parent.document).parent('div').append('<re>起始时间不能为空</re>');
				}else{
					$('#d1',parent.document).parent('div').find('re').remove();
				}
		 })
		  $('#d2',parent.document).blur(function(){
			  if($(this).val()==''||$(this).val()==undefined){
					$('#d2',parent.document).parent('div').find('re').remove();
					$('#d2',parent.document).parent('div').append('<re>结束时间不能为空</re>');
				}else{
					$('#d2',parent.document).parent('div').find('re').remove();
				}
		 })
	 });
	 
	//点击添加修改弹出框的保存按钮；
	$('body',parent.document).on('click','.preserve',function(){
	    var ogcd=$('.agentnum').val(),
	        currency=$('.currency option:selected',parent.document).text(),
	        byds=$('.buy',parent.document).val(),
	        slds=$('.sell',parent.document).val(),
	        beginDate=$('#d1',parent.document).val(),
	        endDate=$('#d2',parent.document).val(),num=0,
	        vo={'userKey':userkey,'ogcd':ogcd,'currency':currency,'byds':byds,'slds':slds,'beginDate':beginDate,'endDate':endDate},arr=[];
	    //判断客户买入外币优惠折扣不能为空不能大于100
	    if(byds==''||byds==undefined){
			$('.buy',parent.document).parent('div').find('re').remove();
			$('.buy',parent.document).parent('div').append('<re>客户买入外币优惠折扣不能为空</re>');
		}else if(parseInt(byds)>100){
			$('.buy',parent.document).parent('div').find('re').remove();
			$('.buy',parent.document).parent('div').append('<re>客户买入外币优惠折扣不能大于100</re>');
		}else{
			num++;
			$('.buy',parent.document).parent('div').find('re').remove();
		}
	  //判断客户卖出外币优惠折扣不能为空不能大于100
	    if(slds==''||slds==undefined){
			$('.sell',parent.document).parent('div').find('re').remove();
			$('.sell',parent.document).parent('div').append('<re>客户卖出外币优惠折扣不能为空</re>');
		}else if(parseInt(byds)>100){
			$('.sell',parent.document).parent('div').find('re').remove();
			$('.sell',parent.document).parent('div').append('<re>客户卖出外币优惠折扣不能大于100</re>');
		}else{
			num++;
			$('.sell',parent.document).parent('div').find('re').remove();
		}
	  //判断起始日期不能为空
	    if(beginDate==''||beginDate==undefined){
			$('#d1',parent.document).parent('div').find('re').remove();
			$('#d1',parent.document).parent('div').append('<re>起始日期不能为空</re>');
		}else{
			num++;
			$('#d1',parent.document).parent('div').find('re').remove();
		}
	  //判断结束日期不能为空
	    if(endDate==''||endDate==undefined){
			$('#d2',parent.document).parent('div').find('re').remove();
			$('#d2',parent.document).parent('div').append('<re>结束日期不能为空</re>');
		}else{
			num++;
			$('#d2',parent.document).parent('div').find('re').remove();
		}
	  
	    if($(this).text()=="保存"){
		   if(num>=4){
		      $.ajax({
				url:'/fx/addOrgFavRule.do',
				type:'post',
				contentType:'application/json',
				data:JSON.stringify(vo),
				async:true,
				success:function(data){
					 if(data.code==00){
						 $('.preserve',parent.document).closest('.zhezhao').remove();
						 ogcd=$('.agentnum').val(),
					     ogcdVo={'ogcd':ogcd,'pageNo':1,'pageSize':10} 
					     getOrgnlist(ogcdVo); 
						 renpage();
					 	 dialog.choicedata(data.codeMessage,'agentdiscount');
					 }else if(data.code==02){
						//币种已存在
						 dialog.choicedata(data.codeMessage,'agentdiscount');
					 }
				}
			}); 
		   }
	   }else if($(this).text()=="修改"){
		    $('.box tr').each(function(i,v){
				if( $(v).find('input[type="checkbox"]').prop('checked') ){
				  arr.push({
					  'ogcd':$('.agentnum').val(),
					  'exnm':$(v).find('td').eq(1).find('span').text(),
					  'byds':$(v).find('td').eq(2).find('span').text(),
					  'slds':$(v).find('td').eq(3).find('span').text(),
					  'stdt':$(v).find('td').eq(4).find('span').text(),
					  'eddt':$(v).find('td').eq(5).find('span').text()
				  });
				}
			});
		   vo={'userKey':userkey,'ogcd':ogcd,'byds':byds,'slds':slds,'beginDate':beginDate,'endDate':endDate,'chlist':arr};
		  if(num>=4){
		   $.ajax({
				url:'/fx/updateFavRuleByOgcds.do',
				type:'post',
				contentType:'application/json',
				data:JSON.stringify(vo),
				async:true,
				success:function(data){
					 if(data.code==00){
						 $('.preserve',parent.document).closest('.zhezhao').remove();
						 ogcd=$('.agentnum').val(),
					     ogcdVo={'ogcd':ogcd,'pageNo':1,'pageSize':10} 
					     getOrgnlist(ogcdVo); 
						 renpage();
					 	 dialog.choicedata(data.codeMessage,'agentdiscount');
					 }else if(data.code==01){
						//异常
						 dialog.choicedata(data.codeMessage,'agentdiscount');
					}
				}
			}); 
		  }
	   }
	 });
	$('.del').click(function(){
		var a=0,arr=[];
		$('.box tr').each(function(){
			if( $(this).find('input[type="checkbox"]').prop('checked')==true){
				a++;
			}
		})
		if(a==0){
			dialog.choicedata('请选择一条数据','agentdiscount');
		}else{
			dialog.cancelDate('您确定要删除当前记录吗?','discountpub');
		}
	});
	//点击确认删除按钮
	$('body',parent.document).on('click','.discountpub .confirm',function(){
		   var ogcd=$('.agentnum').val(),arr=[],vo;
		   $('.box tr').each(function(i,v){
				if( $(v).find('input[type="checkbox"]').prop('checked') ){
				  arr.push({
					  'exnm':$(v).find('td').eq(1).find('span').text()
				  });
				}
			});
		  vo={'userkey':userkey,'ogcd':ogcd,'chlist':arr};
		   $.ajax({
				url:'/fx/deleteOrgFavrRule.do',
				type:'post',
				contentType:'application/json',
				data:JSON.stringify(vo),
				async:true,
				success:function(data){
					 if(data.code==00){
						 $('.discountpub .confirm',parent.document).closest('.zhezhao').remove();
						 ogcd=$('.agentnum').val(),
					     ogcdVo={'ogcd':ogcd,'pageNo':1,'pageSize':10} 
					     getOrgnlist(ogcdVo); 
						 renpage();
					 	 dialog.choicedata(data.codeMessage,'agentdiscount');
					 }else if(data.code==01){
						//异常
						 dialog.choicedata(data.codeMessage,'agentdiscount');
					}
				}
			}); 
	 });
  
	//点击请选择一条数据的确定按钮；
	$('body',parent.document).on('click','.agentdiscount .sure',function(){
		  $(this).closest('.zhezhao').remove();
	});
	//关闭弹窗
	$('body',parent.document).on('click','.discountpub .close,.discountpub .cancel,.agentchoose .close,.agentchoose .cancel',function(){
		  $(this).closest('.zhezhao').remove();
	});
	
	$('body',parent.document).on('click','#d1,#d2',function(){
		WdatePicker({
			dateFmt:'yyyyMMdd',
			minDate:'%y-%M-%d',
			isShowClear:false,
			isShowToday:false,
			isShowOthers:false,
			isShowOk:false,
			qsEnabled:false,
			position:{left:-302,top:-60}
		 }); 
	});
    //封装请求列表
	function getOrgnlist(obj){
		$.ajax({
    		url:'/fx/getFavruleByOgcd.do',
    		type:'post',
    		contentType:'application/json',
    		data:JSON.stringify(obj),
    		async:false,
    		success:function(data){
    			if(data.code==00){
       				userdata=JSON.parse( data.codeMessage );
       				dialog.ren({'cols':cols,'wh':wh,'userdata':userdata.list,'checked':true});
       				listnumtotal=userdata.total;
       			 /*$('.page').remove();
    				$('.boxtop').append('<div class="page"><span class="first">首页</span><span class="prev">上一页</span><span class="next">下一页</span><span class="last">末页</span><span class="nopage">第<b class="Nopage">'+userdata.pageNum+'</b>页/共<b class="Totalpage">'+userdata.pages+'</b>页</span><span class="totalpage">共<b class="total">'+userdata.total+'</b>条记录</span></div>')
    				*/
    			}else if(data.code==01){
       				dialog.ren({'cols':cols,'wh':wh,'userdata':'','checked':true});
       			}
    		}
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
    			        getOrgnlist({
    			        	'ogcd':$('.agentnum').val(),
    			        	'pageNo':obj.curr,
    			        	'pageSize':$('.layui-laypage-limits select option:selected').attr('value')*1
    			        }); 
    		    	}	
    		    }
    		  });
    	});
    }
	//点击分页;
    /*$('.boxtop').on('click','.page .first',function(){
    	var Nopage=parseInt($('.Nopage').text());
 	    if(Nopage>1){
 	    	Nopage=1;
 	    	ogcd=$('.agentnum').val(),
 	  	    ogcdVo={'ogcd':ogcd,'pageNo':Nopage,'pageSize':10}; console.log(0)
 	        getOrgnlist(ogcdVo); 
 	     }
 	  
    });
	$('.boxtop').on('click','.prev',function(){
	    var Nopage=parseInt($('.Nopage').text());
	    if(Nopage>1){
	    	Nopage=Nopage-1;
	    	ogcd=$('.agentnum').val(),
	        ogcdVo={'ogcd':ogcd,'pageNo':Nopage,'pageSize':10} 
	        getOrgnlist(ogcdVo); 
	     }
	    
	});
	$('.boxtop').on('click','.next',function(){
		var Nopage=parseInt($('.Nopage').text()),
			Totalpage=parseInt($('.Totalpage').text());
	    if(Nopage<Totalpage){
	    	Nopage=Nopage+1;
	    	ogcd=$('.agentnum').val(),
	        ogcdVo={'ogcd':ogcd,'pageNo':Nopage,'pageSize':10} 
	        getOrgnlist(ogcdVo); 
	     }
	      
	});
	$('.boxtop').on('click','.last',function(){
		var Nopage=parseInt($('.Nopage').text()),
		Totalpage=parseInt($('.Totalpage').text());
	    if(Nopage<Totalpage){
	    	Nopage=Totalpage;
	    	ogcd=$('.agentnum').val(),
	        ogcdVo={'ogcd':ogcd,'pageNo':Nopage,'pageSize':10} 
	        getOrgnlist(ogcdVo); 
	    }
	     
	});*/
	//点击弹出框的分页的前一页和后一页 首页 末页；
	$('body',parent.document).on('click','.agentchoose .prevbtn',function(){
		prevpage=$('.nowpage',parent.document).text()-1;
		if( pagedata.hasPreviousPage!=false){
			pagenum=--pagedata.pageNum;
			var Ognmtxt=$('.agentname',parent.document).val(),
	            Ogcdtxt=$('.agentnum',parent.document).val(),
	            OgcdVo={'ogcdtxt':Ogcdtxt,'ognmtxt':Ognmtxt,'pageNo':pagenum,'pageSize':10};
    	        getFavRuleOgcd(OgcdVo);
		}
	});
	
	$('body',parent.document).on('click','.agentchoose .nextbtn',function(){  //点击下一页;
		prevpage=parseInt( $('.nowpage',parent.document).text() )+1;
		if(pagedata.hasNextPage!=false){
			pagenum=++pagedata.pageNum;
			var Ognmtxt=$('.agentname',parent.document).val(),
	        Ogcdtxt=$('.agentnum',parent.document).val(),
            OgcdVo={'ogcdtxt':Ogcdtxt,'ognmtxt':Ognmtxt,'pageNo':pagenum,'pageSize':10};
	        getFavRuleOgcd(OgcdVo);
		}
	}); 
	$('body',parent.document).on('click','.agentchoose .first',function(){   //点击首页的时候不需要初始化;再点击全县新增的时已经初始化;
		if( pagedata.hasPreviousPage!=false){
			pagenum=1;
			var Ognmtxt=$('.agentname',parent.document).val(),
	        Ogcdtxt=$('.agentnum',parent.document).val(),
            OgcdVo={'ogcdtxt':Ogcdtxt,'ognmtxt':Ognmtxt,'pageNo':pagenum,'pageSize':10};
	        getFavRuleOgcd(OgcdVo);
		}
	});
	$('body',parent.document).on('click','.agentchoose .last',function(){
		prevpage=parseInt( $('.allpages',parent.document).text() );
		if(pagedata.hasNextPage!=false){
			pagenum=pagedata.pages;
			var Ognmtxt=$('.agentname',parent.document).val(),
	        Ogcdtxt=$('.agentnum',parent.document).val(),
            OgcdVo={'ogcdtxt':Ogcdtxt,'ognmtxt':Ognmtxt,'pageNo':pagenum,'pageSize':10};
	        getFavRuleOgcd(OgcdVo);
		}
	});
	
	
	 $('body',parent.document).on('click','.agentmain table tr',function(){
		 $(this).addClass('agentbgc').siblings().removeClass('agentbgc');
     });
	
})
