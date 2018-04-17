require.config({
	baseUrl:'/fx/js',
	shim:{
		'jquery.niceScroll': {
			deps: ['jquery'],
			exports: 'jquery.niceScroll'
		},
		'mmGrid':{
			deps:['jquery'],
			exports:'mmGrid'
		}
	},
	paths:{
		jquery:'js_files/jquery-1.9.1.min',
		mmGrid:'js_files/mmGrid',
		niceScroll:'js_files/jquery.nicescroll.min',
		dialog:'dialog',
		WdatePicker:'./My97DatePicker/WdatePicker'
	}
});
require(['jquery','mmGrid','niceScroll','dialog','WdatePicker'],function($,mmGrid,niceScroll,dialog,WdatePicker){
	var wh=$(window).height()-190+"px",
		ww=$(window).width()-250+"px";
	$('.boxtop').css('width',ww);
	$('.boxtop').css('height',wh);
	$('.page').css('width',ww);
	$('#d1').val( dialog.today() );
	//列参数;
    var cols = [
            { title:'本地成交流水', name:'lcno',width:100,align:'left' },
            { title:'交易日期', name:'trdt',width:100, align:'right'},  ///
            { title:'交易时间', name:'trtm',width:100, align:'right'},
            { title:'交易代码', name:'trcd',width:100, align:'left'},
            { title:'客户号', name:'cuno',width:100, align:'left'},    ///
            { title:'卡号/折号', name:'cuac',width:100, align:'center'},
            { title:'交易账号', name:'trac',width:100, align:'left'},
            { title:'币种英文简称', name:'cyen',width:100, align:'left'},
            { title:'资金账户类型', name:'caty',width:100, align:'center'},
            { title:'（内部）交易类型', name:'trtp',width:100, align:'center'},
            { title:'买入币种', name:'bycy',width:100, align:'left'},
            { title:'买入币种英文名', name:'bynm',width:100, align:'left'},
            { title:'卖出币种', name:'slcy',width:100, align:'left'},
            { title:'卖出币种英文名', name:'slnm',width:100, align:'left'},
            { title:'客户上送汇率', name:'uspc',width:100, align:'right'},
            { title:'交易类型', name:'trty',width:100, align:'center'},
            { title:'买入金额', name:'byam',width:100, align:'right'},
            { title:'成交汇率', name:'expc',width:100, align:'right'},
            { title:'卖出金额', name:'slam',width:100, align:'right'},
            { title:'盈亏金额/折美元', name:'pram',width:100, align:'right'},
            { title:'发生额（转账）', name:'amut',width:100, align:'right'},
            { title:'币种1折美元（分行价）', name:'busd',width:100, align:'right'},
            { title:'右币种折美元（分行价）', name:'susd',width:100, align:'right'},
            { title:'折美元金额', name:'usam',width:100, align:'right'},
            { title:'被冲交易流水', name:'olno',width:100, align:'left'},
            { title:'交易币别对客户买入价', name:'tcby',width:100, align:'right'},
            { title:'交易币别对客户卖出价', name:'tcsl',width:100, align:'right'},
            { title:'交易币别对分行买入价', name:'tbby',width:100, align:'right'},
            { title:'交易币别对分行卖出价', name:'tbsl',width:100, align:'right'},
            { title:'币别对A', name:'exna',width:100, align:'left'},
            { title:'币别对B', name:'exnb',width:100, align:'left'},
            { title:'交易方式', name:'trfg',width:100, align:'left'},
            { title:'记录状态', name:'stcd',width:100, align:'center'},
            { title:'错误码', name:'ercd',width:100, align:'center'},		///
            { title:'参数值', name:'culv',width:100, align:'right'},
            { title:'人名币金额', name:'cyam',width:100, align:'right'},
            { title:'外管局购汇参号', name:'fxid',width:100, align:'left'},
            { title:'结售汇类型', name:'exfg',width:100, align:'left'},
            { title:'结售汇项目代码', name:'exid',width:100, align:'left'},
            { title:'结售汇客户类型', name:'cutp',width:100, align:'left'},
            { title:'结售汇交易项目代码', name:'extr',width:100, align:'left'},
            { title:'售汇种类', name:'pftp',width:100, align:'left'},
            { title:'冻结金额', name:'fram',width:100, align:'left'},
            { title:'冻结编号', name:'frid',width:100, align:'left'},
            { title:'是否检查最大最小值', name:'vlfg',width:100, align:'left'},
            { title:'资金来源类型', name:'cof',width:100, align:'left'},
            { title:'资金来源账号', name:'soac',width:100, align:'left'},
            { title:'资金去向类型', name:'ctof',width:100, align:'left'},
            { title:'资金去向账号', name:'toac',width:100, align:'left'},
            { title:'结汇参号', name:'sfid',width:100, align:'left'},
            { title:'标准价', name:'bspc',width:100, align:'left'},
            { title:'渠道流水号', name:'trsn',width:100, align:'left'}, ///
            { title:'交易柜员', name:'trtl',width:100, align:'left'},
            { title:'交易金额', name:'tram',width:100, align:'right'},
            { title:'客户购/结汇外币名称', name:'fonm',width:100, align:'left'},
            { title:'客户购/结汇本币名称', name:'cynm',width:100, align:'left'},
            { title:'机构号', name:'ogcd',width:100, align:'left'},
            { title:'支付平台订单号', name:'pitr',width:100, align:'left'},
            { title:'支付平台订单号', name:'pitl',width:100, align:'left'},
            { title:'服务编码', name:'sevd',width:100, align:'left'},
            { title:'系统ID', name:'idcd',width:100, align:'left'},
            { title:'交易标识', name:'cros',width:100, align:'left'},
            { title:'后台记账日期', name:'todt',width:100, align:'left'},
            { title:'证件类型', name:'idtp',width:100, align:'left'},
            { title:'证件号码', name:'idno',width:100, align:'left'},
            { title:'客户中文姓名', name:'cunm',width:100, align:'left'},
            { title:'外币金额', name:'foam',width:100, align:'right'},
            { title:'交易运行时间', name:'dealtime',width:100, align:'right'},
            { title:'产品编码', name:'pdld',width:100, align:'left'},
            { title:'行为编码', name:'optp',width:100, align:'left'},
            { title:'调用后台流水', name:'bksn',width:100, align:'left'},
            { title:'状态标识码', name:'status_code',width:100, align:'center'}
            ];
    //获取产品和用户名；
    var usnm=sessionStorage.getItem('usnm'),
		product=sessionStorage.getItem('product'),
		user={'usnm':usnm,'prod':product},
		userdata;
    var userkey=JSON.parse( sessionStorage.getItem('menu') ).userKey,
    	tit=$('title').text(),listnumtotal;
    getlist({
    	pageNo:1,
    	pageSize:10,
    	entity:{
       		trdt:$('#d1').val(),
       		trsn:$('.trsn input').val(),
       		cuno:$('.cuno input').val(),
       		ercd:$('.ercd input').val()
       	}
    });
    renpage();
    //点击查询按钮，进行查询数据；
    $('.pub_button').click(function(){
    	var trdt=$('#d1').val(),
    		trsn=$('.trsn input').val(),
    		cuno=$('.cuno input').val(),
    		ercd=$('.ercd input').val();
    	getlist({
        	pageNo:1,
        	pageSize:10,
        	entity:{
	       		trdt:trdt,
	       		trsn:trsn,
	       		cuno:cuno,
	       		ercd:ercd
	       	}
        });
    	renpage();
    });
    function getlist( obj ){
    	$.ajax({
    		url:'/fx/selecttranlist.do',
    		type:'post',
    		data:obj,
    		async:false,
    		success:function(data){
    			var data1 = JSON.parse(data);
    			if(data1.code==01){
    				userdata= data1.codeMessage;
    				dialog.ren({'cols':cols,'wh':wh,'userdata':userdata.list});
    				listnumtotal=userdata.total;
    				/*$('.page').remove();
    				$('.boxtop').append('<div class="page"><span class="first">首页</span><span class="prev">上一页</span><span class="next">下一页</span><span class="last">末页</span><span class="nopage">第<b class="Nopage">'+userdata.pageNum+'</b>页/共<b class="Totalpage">'+userdata.pages+'</b>页</span><span class="totalpage">共<b class="total">'+userdata.total+'</b>条记录</span></div>')
    			*/}else {
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
  		    	if( !first){
  	    		   getlist({
  	                	pageNo:obj.curr,
  	                	pageSize:$('.layui-laypage-limits select option:selected').attr('value')*1,
  	                	entity:{
            	       		trdt:$('#d1').val(),
            	       		trsn:$('.trsn input').val(),
            	       		cuno:$('.cuno input').val(),
            	       		ercd:$('.ercd input').val()
            	       	}
  	                });
  		    	}
  		    }
  		  });
    	});
    }
  //导出excel;
	  $('#logon').click(function(){
		  $('#fornm input').remove();
		  var str='<input type="text" name = "tableName" value='+tit+'>'+
		  		  '<input type="text" name = "trdt" value='+$('#d1').val()+'>'+
		  		  '<input type="text" name = "trsn" value='+$('.trsn input').val()+'>'+
		  		  '<input type="text" name = "cuno" value='+$('.cuno input').val()+'>'+
		  		  '<input type="text" name = "ercd" value='+$('.ercd input').val()+'>';
		  for(var i in cols){
			  str+='<input type="text" name ="tableList['+i+'].name" value='+cols[i].title+'><input type="text" name ="tableList['+i+'].value" value='+cols[i].name+'>'
		  }
		  $('#fornm').append( str );
		  $('#fornm').submit();
 });
    
    /*$('.boxtop').on('click','.prev',function(){
	    var Nopage=$('.Nopage').text()*1;
	    if(Nopage>1){
	    	Nopage=Nopage-1;
	    	getlist({
	    		pageNo:Nopage,
	 	       	pageSize:10,
	 	       	entity:{
	 	       		trdt:$('#trdt').val(),
	 	       		trsn:$('#trsn').val(),
	 	       		cuno:$('#cuno').val(),
	 	       		ercd:$('#ercd').val()
	 	       	}
 	       });
	    }
	});
	$('.boxtop').on('click','.next',function(){
		var Nopage=$('.Nopage').text()*1,
			Totalpage=$('.Totalpage').text()*1;
	    if(Nopage<Totalpage){
	    	Nopage=Nopage*1+1;
	    	getlist({
	 	       	pageNo:Nopage,
	 	       	pageSize:10,
	 	       	entity:{
	 	       		trdt:$('#trdt').val(),
	 	       		trsn:$('#trsn').val(),
	 	       		cuno:$('#cuno').val(),
	 	       		ercd:$('#ercd').val()
	 	       	}
 	       });
	    }
	});
	$('.boxtop').on('click','.last',function(){
		var Nopage=$('.Nopage').text()*1,
		Totalpage=$('.Totalpage').text()*1;
	    if(Nopage<Totalpage){
	    	Nopage=Totalpage;
	    	getlist({
	    		pageNo:Nopage,
	 	       	pageSize:10,
	 	       	entity:{
	 	       		trdt:$('#trdt').val(),
	 	       		trsn:$('#trsn').val(),
	 	       		cuno:$('#cuno').val(),
	 	       		ercd:$('#ercd').val()
	 	       	}
 	       });
	    }
	});*/
    
    
});