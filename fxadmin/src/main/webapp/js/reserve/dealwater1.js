/*结售汇-交易流水查询*/
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
		wdatepicker:'./My97DatePicker/WdatePicker',
		dialog:'dialog'
	}
});
require(['jquery','mmGrid','niceScroll','wdatepicker','dialog'],function($,mmGrid,niceScroll,wdatepicker,dialog){
	var wh=$(window).height()-230+"px",
		ww=$(window).width()-260+"px",
		userkey=JSON.parse( sessionStorage.getItem('menu') ).userKey,
		listnumtotal,
		tile=$('title').text();
		$('.boxtop').css('width',ww);
		$('.boxtop').css('height',wh);
		$('#d1,#d2').val( dialog.today);
	//列参数;
    var cols = [
            { title:'交易流水号', name:'lcno',width:120,align:'left' },
            { title:'交易渠道', name:'chnl',width:80, align:'left'},
            { title:'交易日期', name:'trdt',width:80, align:'right'},   
            { title:'交易时间', name:'trtm',width:80, align:'right'},
            { title:'交易类型', name:'trtp',width:80, align:'center'},
            { title:'交易方式', name:'trcd',width:100, align:'center'},
            { title:'客户号', name:'cuno',width:100, align:'left'},
            { title:'客户姓名', name:'cunm',width:80, align:'left'},
            { title:'客户类型', name:'cutp',width:80, align:'center'},
            { title:'客户等级', name:'culv',width:80, align:'right'},
            { title:'证件类型', name:'idtp',width:80, align:'center'},
            { title:'证件号码', name:'idno',width:100, align:'left'},
            { title:'机构', name:'ognm',width:100, align:'left'},
            { title:'买入币种', name:'bynm',width:80, align:'left'},
            { title:'买入金额', name:'byam',width:80, align:'right'},
            { title:'卖出币种', name:'slnm',width:80, align:'left'},
            { title:'卖出金额', name:'slam',width:80, align:'right'},
            { title:'成交汇率', name:'expc',width:100, align:'right'},
            { title:'客户上送汇率', name:'uspc',width:100, align:'right'},
            { title:'折美元金额', name:'usam',width:80, align:'right'},
            { title:'交易状态', name:'stcd',width:80, align:'center'},
            { title:'资金来源类型', name:'csof',width:80, align:'left'},
            { title:'资金来源帐号', name:'soac',width:100, align:'left'},
            { title:'资金去向类型', name:'ctof',width:80, align:'left'},
            { title:'资金去向帐号', name:'toac',width:80, align:'left'},
            { title:'结售汇项目', name:'extp',width:80, align:'left'},
            { title:'交易项目代码', name:'extr',width:80, align:'left'},
            { title:'额度标志', name:'vlfg',width:100, align:'left'},
            { title:'优惠点数', name:'fvda',width:100, align:'right'},
            { title:'分行损益', name:'brsy',width:80, align:'right'},
            { title:'冻结标志', name:'pftp',width:80, align:'center'},
            { title:'冻结金额', name:'fram',width:80, align:'right'},
            { title:'冲销流水', name:'olno',width:80, align:'left'},
            { title:'结售汇参号', name:'fxid',width:100, align:'left'},
    ];
    getList('comboxA.do','#comboxA');  
    
    $('#comboxA').change(function(){
    	comaogcd=$('#comboxA option:selected').attr('value');
    	getDeallists('comboxB.do',comaogcd,'#comboxB');
    });
	getlist({
		'trdtbegin':$('#d1').val(),
		'trdtend':$('#d1').val(),
		'strcuac':$('.cardnum').val(),
		strIdno:$('.policard').val(),
		 pageNo:1,
		 pageSize:10,
		 strlcno:$('.cashnum').val(),	
		comaogcd:$('#comboxA option:selected').attr('value'),
		combogcd:$('#comboxB option:selected').attr('value'),
		com1:$('.deal option:selected').attr('value'),
		com2:'all',
		comtrtp:$('#dealtype option:selected').attr('value'),
		comchnl:$('#tranCode1 option:selected').attr('value')
	});
	renpage();
    //请求列表数据；
	function getlist( obj ){
		$.ajax({
			url:'/fx/getAllTransListP04.do',
			type:'post',
			contentType:'application/json',
			data:JSON.stringify( obj ),
			async:false,
			success:function(data){
				if(data.code==01){
					userdata= data.codeMessage ;
					dialog.ren({'cols':cols,'wh':wh,'userdata':userdata.list});
					listnumtotal=userdata.total;
					/*$('.page').remove();
					$('.boxtop').append('<div class="page"><span class="first">首页</span><span class="prev">上一页</span><span class="next">下一页</span><span class="last">末页</span><span class="nopage">第<b class="Nopage">'+userdata.pageNum+'</b>页/共<b class="Totalpage">'+userdata.pages+'</b>页</span><span class="totalpage">共<b class="total">'+userdata.total+'</b>条记录</span></div>')
					*/
				}else{
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
    		    		var com2;
    		    		if( !$('.dealty option:selected').attr('value') ){
    		    			com2='all';
    		    		}else{
    		    			com2=$('.dealty option:selected').attr('value');
    		    		}
    		    		getlist({
    		    			'trdtbegin':$('#d1').val(),
    		    			'trdtend':$('#d1').val(),
    		    			'strcuac':$('.cardnum').val(),
    		    			strIdno:$('.policard').val(),
    		    			 pageNo:obj.curr,
    		    			 pageSize:$('.layui-laypage-limits select option:selected').attr('value')*1,
    		    			 strlcno:$('.cashnum').val(),	
    		    			comaogcd:$('#comboxA option:selected').attr('value'),
    		    			combogcd:$('#comboxB option:selected').attr('value'),
    		    			com1:$('.deal option:selected').attr('value'),
    		    			com2:com2,
    		    			comtrtp:$('#dealtype option:selected').attr('value'),
    		    			comchnl:$('#tranCode1 option:selected').attr('value')
    		    		});
    		    	}	
    		    }
    		  });
    	});
    }
	//点击交易，获取交易类型；
	$('.deal').change(function(){
		if( $(this).find('option:selected').text()=='交易' ){
			$.ajax({
    			url:'/fx/getTranCode.do',
    			type:'post',
    			async:true,
    			data:userkey,
    			contentType:'application/json;charset=utf-8',
    			dataType:'json',
    			success:function(data){
    				 if(data.code==01){
    					var listdata= data.codeMessage,str='';
    					for(var i in listdata){
    						 str+='<option value='+listdata[i].TRCD+'>'+listdata[i].TRDS+'</option>'
    					}
    					$('.dealty').html(str).show();
    				  }else{
    					 //获取机构失败
    				}
    			}
    		});
		}else{
			$('.dealty').hide();
		}
	});
	//点击查询;
	$('.search').click(function(){
		var com2;
		if( !$('.dealty option:selected').attr('value') ){
			com2='all'
		}else{
			com2=$('.dealty option:selected').attr('value');
		}
		getlist({
    		'trdtbegin':$('#d1').val(),
    		'trdtend':$('#d1').val(),
    		'strcuac':$('.cardnum').val(),
    		 strIdno:$('.policard').val(),
    		 pageNo:Number( $('.Nopage').text() ),
    		 pageSize:10,
    		 strlcno:$('.cashnum').val(),	
    		comaogcd:$('#comboxA option:selected').attr('value'),
    		combogcd:$('#comboxB option:selected').attr('value'),
    		com1:$('.deal option:selected').attr('value'),
    		com2:com2,
    		comtrtp:$('#dealtype option:selected').attr('value'),
    		comchnl:$('#tranCode1 option:selected').attr('value')
    	});
		renpage();
	});
	$('body',parent.document).on('click','.sysparset .sure,.sysparset .close',function(){
		$(this).closest('.zhezhao').remove();
	});
	//封装get请求到的option
    function getList(url,dom){
    	 $.ajax({
    			url:url,
    			type:'get',
    			async:false,
    			data:userkey,
    			dataType:'json',
    			success:function(data){
    				 if(data.code==01){
    					var listdata= data.codeMessage,str;
    					for(var i in listdata){
    						 str+='<option value='+listdata[i].ogcd+'>'+listdata[i].ognm+'</option>'
    					}
    					$(''+dom+'').html(str);
    					 var comaogcd=$('#comboxA option:selected').attr('value');
    					 getDeallists('comboxB.do',comaogcd,'#comboxB');
    				  }else if(data.code==02){
    					 //获取机构失败
    				}
    			}
    		});
    }
    //请求列表option
    function getDeallists(url,obj,dom){
    	$.ajax({
			url:url,
			type:'post',
			async:false,
			dataType:'json',
			data:obj,
 			contentType:'application/json;charset=utf-8',
 			beforeSend:function(){
 				 str='<option value="all">所有</option>';
 			},
 			success:function(data){
				 if(data.code==01){
					var listdata=data.codeMessage;
					for(var i in listdata){
						str+='<option value='+listdata[i].ogcd+'>'+listdata[i].ognm+'</option>'
					}
					$(''+dom+'').html(str);
				   }else{
					 //获取失败
				}
			}
		});
    }
  //点击分页;
    /*$('.boxtop').on('click','.first',function(){
    	var Nopage=$('.Nopage').text()*1;
 	    if(Nopage>1){
 	    	Nopage=1;
 	    	getlist({
	    		'trdtbegin':$('#d1').val(),
	    		'trdtend':$('#d1').val(),
	    		'strcuac':$('.cardnum').val(),
	    		strIdno:$('.policard').val(),
	    		 pageNo:Nopage,
	    		 pageSize:10,
	    		 strlcno:$('.cashnum').val(),	
	    		comaogcd:$('#comboxA option:selected').attr('value'),
	    		combogcd:$('#comboxB option:selected').attr('value'),
	    		com1:$('#deal option:selected').attr('value'),
	    		com2:$('#dealty option:selected').attr('value'),
	    		comtrtp:$('#dealtype option:selected').attr('value'),
	    		comchnl:$('#tranCode1 option:selected').attr('value')
	    	});
 	    }
    });
	$('.boxtop').on('click','.prev',function(){
	    var Nopage=$('.Nopage').text()*1;
	    if(Nopage>1){
	    	Nopage=Nopage-1;
	    	getlist({
	    		'trdtbegin':$('#d1').val(),
	    		'trdtend':$('#d1').val(),
	    		'strcuac':$('.cardnum').val(),
	    		strIdno:$('.policard').val(),
	    		 pageNo:Nopage,
	    		 pageSize:10,
	    		 strlcno:$('.cashnum').val(),	
	    		comaogcd:$('#comboxA option:selected').attr('value'),
	    		combogcd:$('#comboxB option:selected').attr('value'),
	    		com1:$('#deal option:selected').attr('value'),
	    		com2:$('#dealty option:selected').attr('value'),
	    		comtrtp:$('#dealtype option:selected').attr('value'),
	    		comchnl:$('#tranCode1 option:selected').attr('value')
	    	});
	    }
	});
	$('.boxtop').on('click','.next',function(){
		var Nopage=$('.Nopage').text()*1,
			Totalpage=$('.Totalpage').text()*1;
	    if(Nopage<Totalpage){
	    	Nopage=Nopage*1+1;
	    	getlist({
	    		'trdtbegin':$('#d1').val(),
	    		'trdtend':$('#d1').val(),
	    		'strcuac':$('.cardnum').val(),
	    		strIdno:$('.policard').val(),
	    		 pageNo:Nopage,
	    		 pageSize:10,
	    		 strlcno:$('.cashnum').val(),	
	    		comaogcd:$('#comboxA option:selected').attr('value'),
	    		combogcd:$('#comboxB option:selected').attr('value'),
	    		com1:$('#deal option:selected').attr('value'),
	    		com2:$('#dealty option:selected').attr('value'),
	    		comtrtp:$('#dealtype option:selected').attr('value'),
	    		comchnl:$('#tranCode1 option:selected').attr('value')
	    	});
	    }
	});
	$('.boxtop').on('click','.last',function(){
		var Nopage=$('.Nopage').text()*1,
		Totalpage=$('.Totalpage').text()*1;
	    if(Nopage<Totalpage){
	    	Nopage=Totalpage;
	    	getlist({
	    		'trdtbegin':$('#d1').val(),
	    		'trdtend':$('#d2').val(),
	    		'strcuac':$('.cardnum').val(),
	    		strIdno:$('.policard').val(),
	    		 pageNo:Nopage,
	    		 pageSize:10,
	    		 strlcno:$('.cashnum').val(),	
	    		comaogcd:$('#comboxA option:selected').attr('value'),
	    		combogcd:$('#comboxB option:selected').attr('value'),
	    		com1:$('#deal option:selected').attr('value'),
	    		com2:$('#dealty option:selected').attr('value'),
	    		comtrtp:$('#dealtype option:selected').attr('value'),
	    		comchnl:$('#tranCode1 option:selected').attr('value')
	    	});
	    }
	});*/
	 $('#logon').click(function(){
		  $('#fornm input').remove();
		  var str='<input type="text" name = "userKey" value='+userkey+'><input type="text" name = "tableName" value='+tile+'>'+
		  		  '<input type="text" name = "com1" value='+$('#deal option:selected').attr('value')+'><input type="text" name = "com2" value='+$('#dealty option:selected').attr('value')+'>'+
		  		  '<input type="text" name = "comtrtp" value='+$('#dealtype option:selected').attr('value')+'><input type="text" name = "comchnl" value='+$('#tranCode1 option:selected').attr('value')+'>'+
		  		  '<input type="text" name = "comaogcd" value='+$('#comboxA option:selected').attr('value')+'><input type="text" name = "trdtbegin" value='+$('#d1').val()+'>'+
		  		  '<input type="text" name = "combogcd" value='+$('#comboxB option:selected').attr('value')+'><input type="text" name = "trdtend" value='+$('#d2').val()+'>'+
		  		  '<input type="text" name = "strlcno" value='+$('.cashnum').val()+'><input type="text" name = "strcuac" value='+$('.cardnum').val()+'>'+
		  		  '<input type="text" name = "strIdno" value='+$('.policard').val()+'>';
		  for(var i in cols){
			  str+='<input type="text" name ="tableList['+i+'].name" value='+cols[i].title+'><input type="text" name ="tableList['+i+'].value" value='+cols[i].name+'>'
		  }
		  $('#fornm').append( str );
		  $('#fornm').submit();
    });
});
