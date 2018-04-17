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
	var wh=$(window).height()-190+"px",
		ww=$(window).width()-260+"px",
		userkey=JSON.parse( sessionStorage.getItem('menu') ).userKey,
		tit=$('title').text();
	$('.boxtop').css('width',ww);
	$('.boxtop').css('height',wh);
	var userdata='',
		tit=$('title').text(),str='',listnumtotal,
		product=sessionStorage.getItem('product');
	
		$('#d1,#d2').val( dialog.today()  );
	//列参数;
    var cols = [
        { title:'交易日期', name:'trdt' ,width:80, align:'right' },
        { title:'交易时间', name:'trtm' ,width:110, align:'right'},
        { title:'修改/撤销日期', name:'rrdt' ,width:110, align:'right'},
        { title:'机构名称', name:'ognm' ,width:100, align:'left'},
        { title:'操作柜员', name:'trtl' ,width:100, align:'left'},
        { title:'客户号', name:'cuno' ,width:100, align:'left'},
        { title:'客户姓名', name:'cunm' ,width:100, align:'left'},
        { title:'证件类型', name:'idtp' ,width:100, align:'center'},
        { title:'证件号码', name:'idno' ,width:100, align:'left'},
        { title:'国别', name:'cont' ,width:80, align:'left'},
        { title:'交易类型', name:'trtp' ,width:80, align:'center'},
        { title:'交易项目代码', name:'extr' ,width:100, align:'left'},
        { title:'资金来源类型', name:'csof' ,width:100, align:'left'},
        { title:'资金来源账号', name:'soac' ,width:100, align:'left'},
        { title:'资金去向类型', name:'ctof' ,width:100, align:'left'},
        { title:'资金去向账号', name:'toac' ,width:100, align:'left'},
        { title:'买入币种', name:'bynm' ,width:80, align:'left'},
        { title:'买入金额', name:'byam' ,width:80, align:'right'},
        { title:'卖出币种', name:'slnm' ,width:100, align:'left'},
        { title:'卖出金额', name:'slam' ,width:100, align:'right'},
        { title:'成交汇率', name:'expc' ,width:100, align:'right'},
        { title:'钞汇标志', name:'cxfg' ,width:100, align:'center'},
        { title:'优惠点数', name:'fvda' ,width:80, align:'right'},
        { title:'原交易流水', name:'lcno' ,width:80, align:'left'},
        { title:'操作', name:'stcd' ,width:80, align:'left'}
    ]
    //请求列表数据；
    getlist({   	
    	userKey:userkey,
    	trdtbegin:$('#d1').val(),
    	trdtend:$('#d2').val(),
    	pageNo:1,
    	pageSzie:10
    });
    renpage();
    function getlist( obj ){
    	$.ajax({
    		url:'/fx/pere/selRemoveRule.do',
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
    		    		getlist({   	
    		    	    	userKey:userkey,
    		    	    	trdtbegin:$('#d1').val(),
    		    	    	trdtend:$('#d2').val(),
    		    	    	pageNo:obj.curr,
    		    	    	pageSzie:$('.layui-laypage-limits select option:selected').attr('value')*1
    		    	    });
    		    	}	
    		    }
    		  });
    	});
    }
	$('#logon').click(function(){
		  $('#fornm input').remove();
		  var str='<input type="text" name = "userKey" value='+userkey+'><input type="text" name = "tableName" value='+tit+'>'+
		  		  '<input type="text" name = "trdtbegin" value='+$('#d1').val()+'><input type="text" name = "trdtend" value='+$('#d2').val()+'>';
		  for(var i in cols){
			  str+='<input type="text" name ="tableList['+i+'].name" value='+cols[i].title+'><input type="text" name ="tableList['+i+'].value" value='+cols[i].name+'>'
		  }
		  $('#fornm').append( str );
		  $('#fornm').submit();
	});
	//点击搜索功能；
	$('#sear').click(function(){
		getlist({   	
	    	userKey:userkey,
	    	trdtbegin:$('#d1').val(),
	    	trdtend:$('#d2').val(),
	    	pageNo:1,
	    	pageSzie:$('.layui-laypage-limits select option:selected').attr('value')*1
	    });
	})
	//点击分页;
    /*$('.boxtop').on('click','.first',function(){
    	var Nopage=$('.Nopage').text()*1;
 	    if(Nopage>1){
 	    	Nopage=1;
 	    	getlist({
	        	'userKey':userkey,
	        	'pageNo':Nopage,
	        	'pageSize':10,
	        	'trdtbegin':$('#d1').val(),
	        	'trdtend':$('#d2').val(),
	        });
 	    }
    });
	$('.boxtop').on('click','.prev',function(){
	    var Nopage=$('.Nopage').text()*1;
	    if(Nopage>1){
	    	Nopage=Nopage-1;
	    	getlist({
	        	'userKey':userkey,
	        	'pageNo':Nopage,
	        	'pageSize':10,
	        	'trdtbegin':$('#d1').val(),
	        	'trdtend':$('#d2').val(),
	        });
	    }
	});
	$('.boxtop').on('click','.next',function(){
		var Nopage=$('.Nopage').text()*1,
			Totalpage=$('.Totalpage').text()*1;
	    if(Nopage<Totalpage){
	    	Nopage=Nopage*1+1;
	    	getlist({
	        	'userKey':userkey,
	        	'pageNo':Nopage,
	        	'pageSize':10,
	        	'trdtbegin':$('#d1').val(),
	        	'trdtend':$('#d2').val(),
	        });
	    }
	});
	$('.boxtop').on('click','.last',function(){
		var Nopage=$('.Nopage').text()*1,
		Totalpage=$('.Totalpage').text()*1;
	    if(Nopage<Totalpage){
	    	Nopage=Totalpage;
	    	getlist({
	        	'userKey':userkey,
	        	'pageNo':Nopage,
	        	'pageSize':10,
	        	'trdtbegin':$('#d1').val(),
	        	'trdtend':$('#d2').val(),
	        });
	    }
	});*/
    /*----------------快速搜索功能的实现------------------------*/
	$('.info_serbtn').click(function(){
		 // dialog.serchData($('.info_seript').val(),'.mmg-headWrapper');
    });

        
})
