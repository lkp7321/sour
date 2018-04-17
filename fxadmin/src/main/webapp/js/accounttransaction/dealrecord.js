/*账户交易-交易流水查询*/
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
		WdatePicker:'My97DatePicker/WdatePicker'

	}
});
require(['jquery','mmGrid','niceScroll','dialog','table2excel','WdatePicker'],function($,mmGrid,niceScroll,dialog,table2excel,WdatePicker){
	var wh=$(window).height()-190+"px",
		ww=$(window).width()-260+"px";
	$('.boxtop').css('width',ww);
	$('.boxtop').css('height',wh);
	var userdata='',
		userkey=JSON.parse( sessionStorage.getItem('menu') ).userKey,
		tit=$('title').text(),str='',
		product=sessionStorage.getItem('product');
	var cols = [
		        { title:'本地流水', name:'lcno' ,width:80, align:'left' },
		        { title:'交易日期', name:'trdt' ,width:110, align:'left'},
		        { title:'交易时间', name:'trtm' ,width:110, align:'left'},
		        { title:'交易代码', name:'trcd' ,width:100, align:'left'},
		        { title:'客户号（签约类登记）', name:'cuno' ,width:100, align:'left'},
		        { title:'交易账号', name:'trac' ,width:100, align:'left'},
		        { title:'保证金币种', name:'cyen' ,width:100, align:'left'},
		        { title:'订单号', name:'tick' ,width:100, align:'left'},
		        { title:'开仓日期', name:'opdt' ,width:80, align:'left'},
		        { title:'客户上送汇率', name:'uspc' ,width:80, align:'left'},
		        { title:'容忍点差', name:'rrdt' ,width:100, align:'left'},
		        { title:'买入金额', name:'byam' ,width:100, align:'left'},
		        { title:'成交汇率', name:'expc' ,width:100, align:'left'},
		        { title:'卖出金额', name:'slam' ,width:100, align:'left'},
		        { title:'盈亏金额', name:'pram' ,width:100, align:'left'},
		        { title:'折币金额', name:'usam' ,width:80, align:'left'},
		        { title:'交易币种对客户买入价', name:'tcby' ,width:80, align:'right'},
		        { title:'交易币种对客户卖出价', name:'tcsl' ,width:100, align:'right'},
		        { title:'拆盘币种1对USD客户买入价', name:'elcb' ,width:80, align:'right'},
		        { title:'拆盘币种1对USD客户卖出价', name:'elcs' ,width:80, align:'right'},
		        { title:'拆盘币种2对USD客户买入价', name:'ercb' ,width:80, align:'right'},
		        { title:'拆盘币种2对USD客户卖出价', name:'ercs' ,width:80, align:'right'},
		        { title:'折盘币别对1', name:'exna' ,width:100, align:'left'},
		        { title:'折盘币别对2', name:'exnb' ,width:100, align:'left'},
		        { title:'渠道流水号', name:'trsn' ,width:100, align:'left'},
		        { title:'账务机构号', name:'ognm' ,width:100, align:'left'},
		        { title:'账务机构', name:'ogcd' ,width:100, align:'left'},
		        { title:'上级机构号', name:'ogcdup' ,width:80, align:'left'},
		        { title:'上级机构机构', name:'ognmup' ,width:80, align:'left'},
		        { title:'买卖方向', name:'bsfg' ,width:100, align:'left'},
		        { title:'交易品种', name:'exnm' ,width:100, align:'left'},
		        { title:'交易渠道', name:'chnl' ,width:100, align:'left'},
		        { title:'保证金', name:'marg' ,width:100, align:'left'},
		        { title:'银行中收', name:'bkpt' ,width:100, align:'left'}
		    ]  
	dialog.ren({'cols':cols,'wh':wh,'userdata':''}); 
    $('.comquery').click(function(){
    	$(this).attr('che','che');
    	$('.spequery').removeAttr('che');
    	var startDate=$('#d1').val(),
            endDate=$('#d2').val(),
            multiple=$(this).val(),
            vo={'prod':product,'startDate':startDate,'endDate':endDate,'multiple':multiple,'pageNo':1,'pageSize':10};
        getList(vo);
    })
    $('.spequery').click(function(){
    	$(this).attr('che','che');
    	$('.comquery').removeAttr('che');
    	var startDate=$('#d1').val(),
            endDate=$('#d2').val(),
            multiple=$(this).val(),
            vo={'prod':product,'startDate':startDate,'endDate':endDate,'multiple':multiple,'pageNo':1,'pageSize':10};
        getList(vo);
    });
    //封装请求列表
	function getList(obj){
		$.ajax({
    		url:'/fx/accex/getTranlistInfoFromView.do',
    		type:'post',
    		contentType:'application/json',
    		data:JSON.stringify(obj),
    		async:true,
    		success:function(data){
    			if(data.code==00){
       				userdata=data.codeMessage ;
       				dialog.ren({'cols':cols,'wh':wh,'userdata':userdata.list});
       			    $('.page').remove();
    				$('.boxtop').append('<div class="page"><span class="first">首页</span><span class="prev">上一页</span><span class="next">下一页</span><span class="last">末页</span><span class="nopage">第<b class="Nopage">'+userdata.pageNum+'</b>页/共<b class="Totalpage">'+userdata.pages+'</b>页</span><span class="totalpage">共<b class="total">'+userdata.total+'</b>条记录</span></div>')
       			}else{
       				//无数据
       				dialog.ren({'cols':cols,'wh':wh,'userdata':''});
       			}
    		}
    	}); 
	}
	//导出excel;
	$('#logon').click(function(){
		var multiple;
			multiple=$('.comquery').attr('che')?$('.comquery').val():$('.spequery').val();
		  $('#fornm input').remove();
		  var str='<input type="text" name = "userKey" value='+userkey+'><input type="text" name = "tableName" value='+tit+'>'+
		  		  '<input type="text" name = "prod" value='+product+'><input type="text" name = "multiple" value='+multiple+'>'+
		  		  '<input type="text" name = "startDate" value='+$('#d1').val()+'><input type="text" name = "endDate" value='+$('#d2').val()+'>';
		  for(var i in cols){
			  str+='<input type="text" name ="tableList['+i+'].name" value='+cols[i].title+'><input type="text" name ="tableList['+i+'].value" value='+cols[i].name+'>'
		  }
		  $('#fornm').append( str );
		  $('#fornm').submit();
   });
    /*----------------快速搜索功能的实现------------------------*/
	$('.info_serbtn').click(function(){
		  dialog.serchData($('.info_seript').val(),'.mmg-headWrapper');
    });
  //点击分页;
    $('.boxtop').on('click','.page .first',function(){
    	var Nopage=$('.Nopage').text();
 	    if(Nopage>1){
 	    	Nopage=1;
 	     }
 	   ogcd=$('.agentnum').val(),
 	   multiple=$('.comquery').attr('che')?$('.comquery').val():$('.spequery').val(),
 	   ogcdVo={ 'prod':product,'startDate':$('#d1').val(),'endDate':$('#d2').val(),'multiple':multiple,'pageSize':10,'pageNo':Nopage,'pageSize':10} 
 	   getList(ogcdVo); 
    });
	$('.boxtop').on('click','.prev',function(){
	    var Nopage=$('.Nopage').text();
	    if(Nopage>1){
	    	Nopage=Nopage-1;
	     }
	    ogcd=$('.agentnum').val(),
	    multiple=$('.comquery').attr('che')?$('.comquery').val():$('.spequery').val(),
	    ogcdVo={ 'prod':product,'startDate':$('#d1').val(),'endDate':$('#d2').val(),'multiple':multiple,'pageSize':10,'pageNo':Nopage,'pageSize':10} 
	    getList(ogcdVo); 
	});
	$('.boxtop').on('click','.next',function(){
		var Nopage=$('.Nopage').text(),
			Totalpage=$('.Totalpage').text();
	    if(Nopage<Totalpage){
	    	Nopage=Nopage*1+1;
	     }
	    multiple=$('.comquery').attr('che')?$('.comquery').val():$('.spequery').val();
	    ogcd=$('.agentnum').val(),
        //ogcdVo={'ogcd':ogcd,'pageNo':Nopage,'pageSize':10} 
	    ogcdVo={ 'prod':product,'startDate':$('#d1').val(),'endDate':$('#d2').val(),'multiple':multiple,'pageSize':10,'pageNo':Nopage,'pageSize':10} 
	    getList(ogcdVo); 
	});
	$('.boxtop').on('click','.last',function(){
		var Nopage=$('.Nopage').text(),
		Totalpage=$('.Totalpage').text();
	    if(Nopage<Totalpage){
	    	Nopage=Totalpage;
	    }
	    ogcd=$('.agentnum').val(),
	    multiple=$('.comquery').attr('che')?$('.comquery').val():$('.spequery').val(),
        ogcdVo={ 'prod':product,'startDate':$('#d1').val(),'endDate':$('#d2').val(),'multiple':multiple,'pageSize':10,'pageNo':Nopage,'pageSize':10} 
	    getList(ogcdVo); 
	});
        
})
