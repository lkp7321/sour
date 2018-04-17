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
	$('.page').css('width',ww);
	  var userkey=JSON.parse( sessionStorage.getItem('menu') ).userKey,prevpage,listnumtotal;
	  var sixnum=/^[a-zA-Z]{6}$/;
	//列参数;
    var cols = [
        { title:'货币对', name:'EXNM' ,width:120, align:'left'},
        { title:'更新时间', name:'MDTM' ,width:120, align:'right'},
        { title:'有效时间', name:'VLTM' ,width:120, align:'right'},
        { title:'买入价', name:'NEBY' ,width:120, align:'right'},
        { title:'买出价', name:'NESL' ,width:120, align:'right'},
        { title:'中间价', name:'NEMD' ,width:120, align:'right'},
        { title:'可交易性', name:'TRFG' ,width:120, align:'center'},
    ];
    //请求货币对
   $.ajax({
		url:'selJshCustExnm.do',
		type:'post',
		contentType:'application/json',
		async:true,
		success:function(data){
			var str='<option exnm="">请选择</option>'
			 if(data.code==00){
			 	 userdata=JSON.parse( data.codeMessage );
			 	 for(var i in userdata){
			 		str+='<option exnm='+userdata[i].EXNM+'>'+userdata[i].EXNM+'</option>'
			 	 }
			 	 $('.sign select').html( str );
			}else{
				//异常
			}
		}
	});
    //请求列表
    var ogcd=sessionStorage.getItem('obj'),
        ogcdVo={'exnm':'','trfg':'','pageSize':10,'pageNo':1,userKey:userkey};
    getOrgnlist(ogcdVo);
    renpage();
    //点击查询请求列表
    $('.qurey').click(function(){
    	 ogcdVo={
		 	'exnm':$('.sign select option:selected').attr('exnm'),
		 	'trfg':$('.currency select option:selected').attr('value'),
		 	'pageSize':$('.layui-laypage-limits select option:selected').attr('value')*1,
		 	'pageNo':1,
		 	userKey:userkey
    	 };
    	 getOrgnlist(ogcdVo);
    	 renpage();
    });
    //封装请求列表
	function getOrgnlist(obj){
		$.ajax({
    		url:'selJshPdtCustPrice.do',
    		type:'post',
    		contentType:'application/json',
    		data:JSON.stringify(obj),
    		async:false,
    		success:function(data){
    			if(data.code==00){
       				userdata=JSON.parse( data.codeMessage );
       				ren({'cols':cols,'wh':wh,'userdata':userdata.list,'checked':false});
       				listnumtotal=userdata.total;
       				/*$('.page').remove();
    				$('.boxtop').append('<div class="page"><span class="first">首页</span><span class="prev">上一页</span><span class="next">下一页</span><span class="last">末页</span><span class="nopage">第<b class="Nopage">'+userdata.pageNum+'</b>页/共<b class="Totalpage">'+userdata.pages+'</b>页</span><span class="totalpage">共<b class="total">'+userdata.total+'</b>条记录</span></div>')
    				*/
    			}else if(data.code==01){
       				ren({'cols':cols,'wh':wh,'userdata':'','checked':false});
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
 		        		 	'exnm':$('.sign select option:selected').attr('exnm'),
		        		 	'trfg':$('.currency select option:selected').attr('value'),
		        		 	 pageNo:obj.curr,
    		    	    	 pageSize:$('.layui-laypage-limits select option:selected').attr('value')*1,
		        		 	 userKey:userkey
    		        	 });
    		    	}	
    		    }
    		  });
    	});
    }
    //点击添加按钮；
	$('.add').click(function(){
		dialog.customfn('custome','add','客户价格');
		 $('.cuurpair',parent.document).blur(function(){
			 if($(this).val()==''||$(this).val()==undefined){
					$('.cuurpair',parent.document).parent('div').find('re').remove();
					$('.cuurpair',parent.document).parent('div').append('<re>货币对不能为空</re>');
				}else if(!sixnum.test( $(this).val() ) ){
					$('.cuurpair',parent.document).parent('div').find('re').remove();
					$('.cuurpair',parent.document).parent('div').append('<re>货币对必须是6位的英文字母</re>');
				}else{
					$('.cuurpair',parent.document).parent('div').find('re').remove();
				} 
		 });
	});
	  
	//点击修改按钮;
	$('.modify').click(function(){
		var a=0,arr=[];
		if( $('.box tr.selected').length>0){
			a++;
		}
		if(a==0){
			dialog.choicedata('请选择一条数据','agentdiscount');
		}else if(a==1){
			dialog.customfn('custome','modify','客户价格');
			//$('.agentnum',parent.document).val( $('.agentnum').val());
			
			$('.cuurpair',parent.document).val($('.box tr.selected').find('td').eq(0).find('span').text());
			if( $('.box tr.selected').find('td').eq(6).find('span').text()=='可交易'){
				$('.gruname',parent.document).prop('checked','checked');
			}else{
				$('.nogruname',parent.document).prop('checked','checked');
			}
		}
		 $('.cuurpair',parent.document).blur(function(){
			 if($(this).val()==''||$(this).val()==undefined){
					$('.cuurpair',parent.document).parent('div').find('re').remove();
					$('.cuurpair',parent.document).parent('div').append('<re>货币对不能为空</re>');
				}else if(!sixnum.test( $(this).val() ) ){
					$('.cuurpair',parent.document).parent('div').find('re').remove();
					$('.cuurpair',parent.document).parent('div').append('<re>货币对必须是6位的英文字母</re>');
				}else{
					$('.cuurpair',parent.document).parent('div').find('re').remove();
				} 
		 });
	 });
	 
	//点击添加修改弹出框的保存按钮；
	$('body',parent.document).on('click','.preserve',function(){
	    var  currency=$('.cuurpair',parent.document).val(),
	        byds=$('.chebox input:checked',parent.document).attr('value'),
	        vo={'userKey':userkey,'exnm':currency,'trfg':byds},arr=[],exist=0;
			 if(currency==''||currency==undefined){
				$('.cuurpair',parent.document).parent('div').find('re').remove();
				$('.cuurpair',parent.document).parent('div').append('<re>货币对不能为空</re>');
			}else if(!sixnum.test( currency ) ){
				$('.cuurpair',parent.document).parent('div').find('re').remove();
				$('.cuurpair',parent.document).parent('div').append('<re>货币对必须是6位的英文字母</re>');
			}else{
				$('.cuurpair',parent.document).parent('div').find('re').remove();
			} 
	   
	    if($(this).text()=="添加"&& $('.custome re',parent.document).length==0 ){
	    	  $.ajax({
					url:'selCustExnmExist.do',
					type:'post',
					contentType:'application/json',
					data:JSON.stringify({exnm:currency}),
					async:true,
					success:function(data){
						 if(data.code==00){
							 exist=0;
						 }else{
							 exist=1;
						 }
					}
				}); 
	    	  if( exist==1){
			      $.ajax({
					url:'insJshPdtCustPrice.do',
					type:'post',
					contentType:'application/json',
					data:JSON.stringify(vo),
					async:true,
					success:function(data){
						 $('.preserve',parent.document).closest('.zhezhao').remove();
						 if(data.code==00){
							 var Nopage=parseInt($('.Nopage').text()),
							 ogcdVo={
				    		 	'exnm':$('.sign select option:selected').attr('exnm'),
				    		 	'trfg':$('.currency select option:selected').attr('value'),
				    		 	'pageSize':$('.layui-laypage-limits select option:selected').attr('value')*1,
				    		 	'pageNo':Nopage,
				    		 	userKey:userkey
				 	    	};
				 	    	getOrgnlist(ogcdVo);
						 	dialog.choicedata(data.codeMessage,'custome');
						 }else{
							//币种已存在
							 dialog.choicedata(data.codeMessage,'custome');
						 }
					}
				}); 
	    	  }else{
	    		  dialog.choicedata('该币种已存在','custome');
	    	  }
	   }else if($(this).text()=="修改" && $('.custome re',parent.document).length==0 ){
		   $.ajax({
				url:'/fx/updJshPdtCustPrice.do',
				type:'post',
				contentType:'application/json',
				data:JSON.stringify(vo),
				async:true,
				success:function(data){
					$('.preserve',parent.document).closest('.zhezhao').remove();
					 if(data.code==00){
						 var Nopage=parseInt($('.Nopage').text()),
						 ogcdVo={
			    		 	'exnm':$('.sign select option:selected').attr('exnm'),
			    		 	'trfg':$('.currency select option:selected').attr('value'),
			    		 	'pageSize':$('.layui-laypage-limits select option:selected').attr('value')*1,
			    		 	'pageNo':Nopage,
			    		 	userKey:userkey
			 	    	};
			 	    	getOrgnlist(ogcdVo);
					 	 dialog.choicedata(data.codeMessage,'custome');
					 }else if(data.code==01){
						//异常
						 dialog.choicedata(data.codeMessage,'custome');
					}
				}
			}); 
		  }
	 });
	$('.del').click(function(){
		var a=0,arr=[];
		if( $('.box tr.selected').length>0){
			a++;
		}
		if(a==0){
			dialog.choicedata('请选择一条数据','custome');
		}else{
			dialog.cancelDate('您确定要删除当前记录吗?','discountpub');
		}
	});
	//点击确认删除按钮
	$('body',parent.document).on('click','.discountpub .confirm',function(){
			var exnm=$('.box tr.selected td:eq(0) span').text();
		  vo={'userkey':userkey,'exnm':exnm};
		   $.ajax({
				url:'/fx/delJshPdtCustPrice.do',
				type:'post',
				contentType:'application/json',
				data:JSON.stringify(vo),
				async:true,
				success:function(data){
					 $('.discountpub .confirm',parent.document).closest('.zhezhao').remove();
					 if(data.code==00){
						 var Nopage=parseInt($('.Nopage').text()),
						 ogcdVo={
			    		 	'exnm':$('.sign select option:selected').attr('exnm'),
			    		 	'trfg':$('.currency select option:selected').attr('value'),
			    		 	'pageSize':$('.layui-laypage-limits select option:selected').attr('value')*1,
			    		 	'pageNo':Nopage,
			    		 	userKey:userkey
			 	    	};
			 	    	getOrgnlist(ogcdVo);
					 	 dialog.choicedata(data.codeMessage,'custome');
					 }else if(data.code==01){
						//异常
						 dialog.choicedata(data.codeMessage,'agentdiscount');
					}
				}
			}); 
	 });
	//渲染函数；
	function ren(obj){
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
				horizrailenabled:true
		  });
		 mmg.on('loadSuccess',function(){	
			 if( $('.box tbody tr').length>0){
				$('.box tbody tr').each(function(i,v){
					if( $(v).find('td:eq(6) span').text()=='1' ){
						//$(v).find('td:eq(6) span').text('不可交易');
						$(v).find('td:eq(6) span').text('不可交易');
					}else{
						$(v).find('td:eq(6) span').text('可交易');
					}
				});
			}
			 setInterval(function(){
				 $.ajax({
			    		url:'selJshPdtCustPrice.do',
			    		type:'post',
			    		contentType:'application/json',
			    		data:JSON.stringify({
			    				'exnm':$('.sign select option:selected').attr('exnm'),
			    			 	'trfg':$('.currency select option:selected').attr('value'),
			    			 	'pageSize':$('.layui-laypage-limits select option:selected').attr('value')*1,
			    			 	'pageNo':$('.layui-laypage-curr em:last').text(),
			    			 	userKey:userkey}),
			    		async:false,
			    		success:function(data){
			    			if(data.code==00){
			       				userdata=JSON.parse( data.codeMessage );
			       				$('.box tbody tr').each(function(i,v){
			       					$(v).find('td:eq(0) span').text( userdata.list[i].EXNM);
			       					$(v).find('td:eq(1) span').text( userdata.list[i].MDTM);
			       					$(v).find('td:eq(2) span').text( userdata.list[i].VLTM);
			       					$(v).find('td:eq(3) span').text( userdata.list[i].NEBY);
			       					$(v).find('td:eq(4) span').text( userdata.list[i].NESL);
			       					$(v).find('td:eq(5) span').text( userdata.list[i].NEMD);
			       					
			       					if(userdata.list[i].TRFG=='1' ){
			    						$(v).find('td:eq(6) span').text('不可交易');
			    					}else{
			    						$(v).find('td:eq(6) span').text('可交易');
			    					}
			       				});
			    			}else if(data.code==01){
			       				ren({'cols':cols,'wh':wh,'userdata':'','checked':false});
			       			}
			    		}
			    	}); 
			 },1000);
		 });
    }
	//点击请选择一条数据的确定按钮；
	$('body',parent.document).on('click','.agentdiscount .sure,.discountpub .cancel',function(){
		  $(this).closest('.zhezhao').remove();
	});
	//关闭弹窗
	$('body',parent.document).on('click','.custome .close,.custome .cancel,.custome .twosure',function(){
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
   
	//点击分页;
    /*$('.boxtop').on('click','.page .first',function(){
    	var Nopage=parseInt($('.Nopage').text());
 	    if(Nopage>1){
 	    	Nopage=1;
 	    	ogcdVo={
    		 	'exnm':$('.sign select option:selected').attr('exnm'),
    		 	'trfg':$('.currency select option:selected').attr('value'),
    		 	'pageSize':10,
    		 	'pageNo':Nopage,
    		 	userKey:userkey
 	    	};
 	    	getOrgnlist(ogcdVo);
 	     }
 	  
    });
	$('.boxtop').on('click','.prev',function(){
	    var Nopage=parseInt($('.Nopage').text());
	    if(Nopage>1){
	    	Nopage=Nopage-1;
	    	ogcdVo={
    		 	'exnm':$('.sign select option:selected').attr('exnm'),
    		 	'trfg':$('.currency select option:selected').attr('value'),
    		 	'pageSize':10,
    		 	'pageNo':Nopage,
    		 	userKey:userkey
 	    	};
 	    	getOrgnlist(ogcdVo);
	     }
	    
	});
	$('.boxtop').on('click','.next',function(){
		var Nopage=parseInt($('.Nopage').text()),
			Totalpage=parseInt($('.Totalpage').text());
	    if(Nopage<Totalpage){
	    	Nopage=Nopage+1;
	    	ogcdVo={
    		 	'exnm':$('.sign select option:selected').attr('exnm'),
    		 	'trfg':$('.currency select option:selected').attr('value'),
    		 	'pageSize':10,
    		 	'pageNo':Nopage,
    		 	userKey:userkey
 	    	};
 	    	getOrgnlist(ogcdVo); 
	     }
	      
	});
	$('.boxtop').on('click','.last',function(){
		var Nopage=parseInt($('.Nopage').text()),
		Totalpage=parseInt($('.Totalpage').text());
	    if(Nopage<Totalpage){
	    	Nopage=Totalpage;
	    	ogcdVo={
    		 	'exnm':$('.sign select option:selected').attr('exnm'),
    		 	'trfg':$('.currency select option:selected').attr('value'),
    		 	'pageSize':10,
    		 	'pageNo':Nopage,
    		 	userKey:userkey
 	    	};
 	    	getOrgnlist(ogcdVo);
	    }
	     
	});*/
	 $('body',parent.document).on('click','.agentmain table tr',function(){
		 $(this).addClass('agentbgc').siblings().removeClass('agentbgc');
     });
})
