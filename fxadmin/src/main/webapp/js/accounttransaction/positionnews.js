/*账户交易=签约信息查询*/
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
		table2excel:'js_files/excel',
		WdatePicker:'./My97DatePicker/WdatePicker'

	}
});
require(['jquery','mmGrid','niceScroll','dialog','table2excel','WdatePicker'],function($,mmGrid,niceScroll,dialog,table2excel,WdatePicker){
	var wh=$(window).height()-200+"px",
		ww=$(window).width()-250+"px";
	$('.boxtop').css('width',ww);
	$('.boxtop').css('height',wh);
	var userdata='',
		tit=$('title').text(),str='',
		product=sessionStorage.getItem('product'),
		listnumtotal,
		text=$('.head span:eq(1)').text().substr(5),
		userkey=JSON.parse( sessionStorage.getItem('menu') ).userKey;
	 $('#d1,#d2').val( dialog.today() );
	 
	if(text=='账户签约信息查询 '){
		//列参数;
	    var cols = [
	        { title:'交易账号', name:'trac' ,width:80, align:'left' },
	        { title:'客户号', name:'cuno' ,width:110, align:'left'},
	        { title:'卡号', name:'cuac' ,width:110, align:'left'},
	        { title:'客户类型', name:'caty' ,width:100, align:'center'},
	        { title:'签约标识', name:'refg' ,width:100, align:'center'},
	        { title:'通知手机号', name:'tlno' ,width:100, align:'left'},
	        { title:'机构号', name:'ogcd' ,width:100, align:'left'},
	        { title:'机构名称', name:'ognm' ,width:100, align:'left'},
	        { title:'上级机构号', name:'upcd' ,width:80, align:'left'},
	        { title:'上级机构名称', name:'upnm' ,width:80, align:'left'},
	        { title:'签约机构', name:'rgog' ,width:100, align:'left'},
	        { title:'签约柜员', name:'rgtl' ,width:100, align:'left'},
	        { title:'签约日期', name:'rgdt' ,width:100, align:'right'},
	        { title:'签约时间', name:'rgtm' ,width:100, align:'right'},
	        { title:'解约日期', name:'crdt' ,width:100, align:'right'},
	        { title:'解约时间', name:'crtm' ,width:80, align:'right'},
	        { title:'签约修改日期', name:'mddt' ,width:80, align:'right'},
	        { title:'签约修改时间', name:'mdtm' ,width:100, align:'right'},
	        { title:'签约渠道', name:'rgch' ,width:80, align:'left'},
	        { title:'签约编号', name:'rgid' ,width:80, align:'left'}
	    ]
		var url='accex/searchRegMsgList.do';
	}else if(text=='持仓信息查询'){
		var cols = [
		        { title:'持仓编号', name:'TICK' ,width:80, align:'left' },
		        { title:'开仓日期', name:'OPDT' ,width:80, align:'right'},
		        { title:'开仓时间', name:'OPTM' ,width:80, align:'right'},
		        { title:'交易账号', name:'TRAC' ,width:120, align:'left'},
		        { title:'保证金币种', name:'CYEN' ,width:100, align:'left'},
		        { title:'开仓类型', name:'OPTP' ,width:100, align:'center'},
		        { title:'交易品种', name:'EXNM' ,width:100, align:'left'},
		        { title:'买卖方向', name:'BSFG' ,width:100, align:'left'},
		        { title:'交易数量', name:'AMUT' ,width:80, align:'right'},
		        { title:'容忍点差', name:'RRDC' ,width:80, align:'right'},
		        { title:'占用保证金', name:'MARG' ,width:100, align:'right'},
		        { title:'开仓成交价', name:'OEPC' ,width:100, align:'right'},
		        { title:'平仓类型', name:'OPTP' ,width:100, align:'center'},
		        { title:'平仓日期', name:'EXNM' ,width:100, align:'right'},
		        { title:'平仓时间', name:'BSFG' ,width:100, align:'right'},
		        { title:'平仓成交价', name:'AMUT' ,width:80, align:'right'},
		        { title:'平仓损益', name:'RRDC' ,width:80, align:'right'},
		        { title:'状态', name:'MARG' ,width:100, align:'center'},
		        { title:'开仓左币种左头寸', name:'OEPC' ,width:100, align:'right'},
		        { title:'开仓左币种右头寸', name:'MARG' ,width:100, align:'right'},
		        { title:'折美元金额', name:'OEPC' ,width:100, align:'right'},
		        { title:'开仓右币种左头寸', name:'OPTP' ,width:100, align:'right'},
		        { title:'开仓右币种右头寸', name:'EXNM' ,width:100, align:'right'},
		        { title:'平仓流水号', name:'BSFG' ,width:100, align:'left'}
		    ]
		var  url='accex/getOrderListFromVie.do';
	 }else if(text=='营销查询'){
		//列参数;
	    var cols = [
	        { title:'ID', name:'ppid' ,width:80, align:'left' },
	        { title:'日期', name:'trdt' ,width:110, align:'right'},
	        { title:'账号', name:'trac' ,width:110, align:'left'},
	        { title:'卡号', name:'cuac' ,width:100, align:'left'},
	        { title:'姓名', name:'name' ,width:100, align:'left'},
	        { title:'币种', name:'cyen' ,width:100, align:'left'},
	        { title:'金额', name:'amut' ,width:100, align:'right'}
	    ]
	   var  url='accex/selectPromotion.do';
	}else if(text=='贵金属交易流水查询'){
		//列参数;
	    var cols = [
	        { title:'本地流水', name:'lcno' ,width:80, align:'left' },
	        { title:'交易日期', name:'trdt' ,width:110, align:'right'},
	        { title:'交易时间', name:'trtm' ,width:110, align:'right'},
	        { title:'交易代码', name:'trcd' ,width:100, align:'left'},
	        { title:'客户号（签约类登记）', name:'cuno' ,width:180, align:'left'},
	        { title:'交易账号', name:'trac' ,width:100, align:'left'},
	        { title:'保证金币种', name:'cyen' ,width:100, align:'left'},
	        { title:'订单号', name:'tick' ,width:100, align:'left'},
	        { title:'开仓日期', name:'opdt' ,width:80, align:'right'},
	        { title:'客户上送汇率', name:'uspc' ,width:80, align:'right'},
	        { title:'容忍点差', name:'rrdt' ,width:100, align:'right'},
	        { title:'买入金额', name:'byam' ,width:100, align:'right'},
	        { title:'成交汇率', name:'expc' ,width:100, align:'right'},
	        { title:'卖出金额', name:'slam' ,width:100, align:'right'},
	        { title:'盈亏金额', name:'pram' ,width:100, align:'right'},
	        { title:'折币金额', name:'usam' ,width:80, align:'right'},
	        { title:'交易币种对客户买入价', name:'tcby' ,width:80, align:'right'},
	        { title:'交易币种对客户卖出价', name:'tcsl' ,width:100, align:'right'},
	        { title:'拆盘币种1对USD客户买入价', name:'elcb' ,width:80, align:'right'},
	        { title:'拆盘币种1对USD客户卖出价', name:'elcs' ,width:80, align:'right'},
	        { title:'拆盘币种2对USD客户买入价', name:'ercb' ,width:80, align:'right'},
	        { title:'拆盘币种2对USD客户卖出价', name:'ercs' ,width:80, align:'right'},
	        { title:'折盘币别对1', name:'exna' ,width:100, align:'left'},
	        { title:'折盘币别对2', name:'exnb' ,width:100, align:'left'},
	        { title:'渠道流水号', name:'trsn' ,width:100, align:'left'},
	        { title:'买卖方向', name:'bsfg' ,width:100, align:'left'},
	        { title:'交易品种', name:'exnm' ,width:100, align:'left'},
	        { title:'交易渠道', name:'chnl' ,width:100, align:'left'},
	        { title:'保证金', name:'marg' ,width:100, align:'right'}
	   ]
	   var  url='accex/getGJSTranlist.do';
	}
    var  startDate=$('#d1').val(),
         endDate=$('#d2').val(),
         vo={'prod':product,'startDate':startDate,'endDate':endDate,'pageNo':1,'pageSize':10};
         if(text=='持仓信息查询'){
             vo={'userKey':userkey,'prod':product,'strateDate':startDate,'endDate':endDate,'pageNo':1,'pageSize':10};	
         }else if( text=='营销查询'){
        	 vo={'userKey':userkey,'strateDate':startDate,'endDate':endDate,'pageNo':1,'pageSize':10};	
         }else if(text=='贵金属交易流水查询'){
         	vo={'prod':product,'startDate':startDate,'endDate':endDate,'pageNo':1,'pageSize':10};
         }
         getList(vo,url);
         renpage();
    $('.query').click(function(){
    	startDate=$('#d1').val();
        endDate=$('#d2').val();
        vo={'prod':product,'startDate':startDate,'endDate':endDate,'pageNo':1,'pageSize':10};
        if(text=='持仓信息查询'){
           vo={'userKey':userkey,'prod':product,'strateDate':startDate,'endDate':endDate,'pageNo':1,'pageSize':10};	
        }else if( text=='营销查询'){
       	 vo={'userKey':userkey,'strateDate':startDate,'endDate':endDate,'pageNo':1,'pageSize':10};	
        }else if(text=='贵金属交易流水查询'){
        	vo={'prod':product,'startDate':startDate,'endDate':endDate,'pageNo':1,'pageSize':10};
        }
        getList(vo,url);
        renpage();
    })

    
    //封装请求列表
	function getList(obj,url){
		$.ajax({
    		url:url,
    		type:'post',
    		contentType:'application/json',
    		data:JSON.stringify(obj),
    		async:false,
    		success:function(data){
    			if(data.code==00){
       				userdata= data.codeMessage ;
       				dialog.ren({'cols':cols,'wh':wh,'userdata':userdata.list});
       				listnumtotal=userdata.total;
       				/*$('.page').remove();
    				$('.boxtop').append('<div class="page"><span class="first">首页</span><span class="prev">上一页</span><span class="next">下一页</span><span class="last">末页</span><span class="nopage">第<b class="Nopage">'+userdata.pageNum+'</b>页/共<b class="Totalpage">'+userdata.pages+'</b>页</span><span class="totalpage">共<b class="total">'+userdata.total+'</b>条记录</span></div>')
    				*/
    			}else if(data.code==01){
       				//无数据
       				dialog.ren({'cols':cols,'wh':wh,'userdata':''});
       			}else if(data.code==02){
       				//异常
       				dialog.ren({'cols':cols,'wh':wh,'userdata':''});
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
    		    		var startDate,endDate,vo;
    		    		
    		    		startDate=$('#d1').val();
    		            endDate=$('#d2').val();
    		            vo={'prod':product,'startDate':startDate,'endDate':endDate,'pageNo':obj.curr,'pageSize':$('.layui-laypage-limits select option:selected').attr('value')*1};
    		            if(text=='持仓信息查询'){
    		               vo={'userKey':userkey,'prod':product,'strateDate':startDate,'endDate':endDate,'pageNo':obj.curr,'pageSize':$('.layui-laypage-limits select option:selected').attr('value')*1};	
    		            }else if( text=='营销查询'){
    		           	 vo={'userKey':userkey,'strateDate':startDate,'endDate':endDate,'pageNo':obj.curr,'pageSize':$('.layui-laypage-limits select option:selected').attr('value')*1};	
    		            }else if(text=='贵金属交易流水查询'){
    		            	vo={'prod':product,'startDate':startDate,'endDate':endDate,'pageNo':obj.curr,'pageSize':$('.layui-laypage-limits select option:selected').attr('value')*1};
    		            }
    		            getList(vo,url);
    		    	}	
    		    }
    		  });
    	});
    }
    $('#logon').click(function(){
 
    	 $('#fornm input').remove();
    	 if( text=='营销查询'){
    		  var str='<input type="text" name = "userKey" value='+userkey+'><input type="text" name = "tableName" value='+tit+'>'+
	  		  '<input type="text" name = "endDate" value='+$('#d2').val()+'>'+
	  		  '<input type="text" name = "strateDate" value='+$('#d1').val()+'>';
    	 }else if( text=='持仓信息查询'){
    		  var str='<input type="text" name = "userKey" value='+userkey+'><input type="text" name = "tableName" value='+tit+'>'+
	  		  '<input type="text" name = "prod" value='+'P007'+'><input type="text" name = "endDate" value='+$('#d2').val()+'>'+
	  		  '<input type="text" name = "strateDate" value='+$('#d1').val()+'>';
    	 }else if( text=='账户签约信息查询 '){
    		 var str='<input type="text" name = "userKey" value='+userkey+'><input type="text" name = "tableName" value='+tit+'>'+
   		  		  '<input type="text" name = "prod" value='+'P007'+'><input type="text" name = "endDate" value='+$('#d2').val()+'>'+
   		  	      '<input type="text" name = "strateDate" value='+$('#d1').val()+'>';
    	 }else if(text=='贵金属交易流水查询'){
    		 var str='<input type="text" name = "userKey" value='+userkey+'><input type="text" name = "tableName" value='+tit+'>'+
		  		  '<input type="text" name = "prod" value='+'P007'+'><input type="text" name = "endDate" value='+$('#d2').val()+'>'+
		  	      '<input type="text" name = "startDate" value='+$('#d1').val()+'>';
         }else{
    		  var str='<input type="text" name = "userKey" value='+userkey+'><input type="text" name = "tableName" value='+tit+'>'+
	  		  '<input type="text" name = "endDate" value='+$('#d2').val()+'>'+
	  		  '<input type="text" name = "startDate" value='+$('#d1').val()+'>';
    	 }
		  for(var i in cols){
			  str+='<input type="text" name ="tableList['+i+'].name" value='+cols[i].title+'><input type="text" name ="tableList['+i+'].value" value='+cols[i].name+'>'
		  }
		  $('#fornm').append( str );
		  //$('#fornm').submit();
	});
    /*----------------快速搜索功能的实现------------------------*/
	$('.info_serbtn').click(function(){
		  dialog.serchData($('.info_seript').val(),'.mmg-headWrapper');
    });
  //点击分页;
   /* $('.boxtop').on('click','.page .first',function(){
    	var Nopage=$('.Nopage').text()*1;
 	    if(Nopage>1){
 	    	Nopage=1;
 	    	startDate=$('#d1').val();
 	       endDate=$('#d2').val();
 	       
 	       if(text=='持仓信息查询'){
 	           vo={'userKey':userkey,'prod':product,'strateDate':startDate,'endDate':endDate,'pageNo':Nopage,'pageSize':10};	
 	        }else if( text=='营销查询'){
 	       	 vo={'userKey':userkey,'strateDate':startDate,'endDate':endDate,'pageNo':Nopage,'pageSize':10};	
 	        }else if(text=='贵金属交易流水查询'){
 	        	vo={'prod':product,'startDate':startDate,'endDate':endDate,'pageNo':Nopage,'pageSize':10};
 	        }else{
 	        	vo={'prod':product,'startDate':startDate,'endDate':endDate,'pageNo':Nopage,'pageSize':10};
 	        }
 	       getList(vo,url);
 	     }
    });
	$('.boxtop').on('click','.prev',function(){
	    var Nopage=$('.Nopage').text()*1;
	    if(Nopage>1){
	    	Nopage=Nopage-1;
	    	startDate=$('#d1').val();
	        endDate=$('#d2').val();
	        //vo={'prod':product,'startDate':startDate,'endDate':endDate,'pageNo':Nopage,'pageSize':10};
	        if(text=='持仓信息查询'){
	 	           vo={'userKey':userkey,'prod':product,'strateDate':startDate,'endDate':endDate,'pageNo':Nopage,'pageSize':10};	
	 	        }else if( text=='营销查询'){
	 	       	 vo={'userKey':userkey,'strateDate':startDate,'endDate':endDate,'pageNo':Nopage,'pageSize':10};	
	 	        }else if(text=='贵金属交易流水查询'){
	 	        	vo={'prod':product,'startDate':startDate,'endDate':endDate,'pageNo':Nopage,'pageSize':10};
	 	        }else{
	 	        	vo={'prod':product,'startDate':startDate,'endDate':endDate,'pageNo':Nopage,'pageSize':10};
	 	        }
	        getList(vo,url); 
	     }
	});
	$('.boxtop').on('click','.next',function(){
		var Nopage=$('.Nopage').text()*1,
			Totalpage=$('.Totalpage').text()*1;
		 
	    if(Nopage<Totalpage){
	    	 Nopage=Nopage+1;
	    	 startDate=$('#d1').val();
	         endDate=$('#d2').val();
	         	if(text=='持仓信息查询'){
	 	           vo={'userKey':userkey,'prod':product,'strateDate':startDate,'endDate':endDate,'pageNo':Nopage,'pageSize':10};	
	 	        }else if( text=='营销查询'){
	 	       	 vo={'userKey':userkey,'strateDate':startDate,'endDate':endDate,'pageNo':Nopage,'pageSize':10};	
	 	        }else if(text=='贵金属交易流水查询'){
	 	        	vo={'prod':product,'startDate':startDate,'endDate':endDate,'pageNo':Nopage,'pageSize':10};
	 	        }else{
	 	        	vo={'prod':product,'startDate':startDate,'endDate':endDate,'pageNo':Nopage,'pageSize':10};
	 	        }
	         getList(vo,url);
	     }
	});
	$('.boxtop').on('click','.last',function(){
		var Nopage=$('.Nopage').text()*1,
		Totalpage=$('.Totalpage').text()*1;
	    if(Nopage<Totalpage){
	    	Nopage=Totalpage;
    	 startDate=$('#d1').val();
         endDate=$('#d2').val();
         	if(text=='持仓信息查询'){
	           vo={'userKey':userkey,'prod':product,'strateDate':startDate,'endDate':endDate,'pageNo':Nopage,'pageSize':10};	
	        }else if( text=='营销查询'){
	       	 vo={'userKey':userkey,'strateDate':startDate,'endDate':endDate,'pageNo':Nopage,'pageSize':10};	
	        }else if(text=='贵金属交易流水查询'){
	        	vo={'prod':product,'startDate':startDate,'endDate':endDate,'pageNo':Nopage,'pageSize':10};
	        }else{
	        	vo={'prod':product,'startDate':startDate,'endDate':endDate,'pageNo':Nopage,'pageSize':10};
	        }
         getList(vo,url);
	    }
	});*/
        
})
