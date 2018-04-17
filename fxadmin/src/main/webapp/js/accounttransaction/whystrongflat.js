/*
 * 账户外汇强平；
 * */
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
		dialog:'dialog'
	}
});
require(['jquery','mmGrid','niceScroll','dialog'],function($,mmGrid,niceScroll,dialog){
	var wh=$(window).height()-190+"px",
		ww=$(window).width()-260+"px";
	var userkey=JSON.parse( sessionStorage.getItem('menu') ).userKey,
		listnumtotal,
		product=sessionStorage.getItem('product');
	$('.boxtop').css('width',ww);
	$('.boxtop').css('height',wh);
	//列参数;
    var cols = [
            { title:'交易帐号', name:'trac',width:80,align:'left' },
            { title:'保证金类型', name:'cyen',width:80, align:'left'},
            { title:'持仓数量', name:'amut',width:80, align:'left'},
            { title:'持仓保证金', name:'marg',width:80, align:'left'},
            { title:'浮动盈亏', name:'profit',width:80,align:'left' },
            { title:'风险度', name:'risk',width:80, align:'left'},
            { title:'客户号', name:'cuno',width:80, align:'left'},
            { title:'卡号', name:'cuac',width:80, align:'left'},
            { title:'移动电话', name:'tino',width:80, align:'left'}
    ];
	getlist({
		'prod':product,
		'pageNo':1,
		'pageSize':10,
		'userKey':userkey
	});
	renpage();
	//请求接口；
	function getlist( obj ){
		$.ajax({
			url:'/fx/mandatLiquid.do',
			type:'post',
			contentType:'application/json',
			data:JSON.stringify( obj ),
			async:false,
			success:function(data){
				if(data.code==00){
					userdata=JSON.parse( data.codeMessage );
					dialog.ren({'cols':cols,'wh':wh,'userdata':userdata.list,'checked':true});
					listnumtotal=userdata.total;
					/*$('.page').remove();
					$('.boxtop').append('<div class="page"><span class="first">首页</span><span class="prev">上一页</span><span class="next">下一页</span><span class="last">末页</span><span class="nopage">第<b class="Nopage">'+userdata.pageNum+'</b>页/共<b class="Totalpage">'+userdata.pages+'</b>页</span><span class="totalpage">共<b class="total">'+userdata.total+'</b>条记录</span></div>');
					*/
				}else if(data.code==01){
					dialog.ren({'cols':cols,'wh':wh,'userdata':'','checked':true});
				}
				
			}
		});
		//获取自动强平；
		$.ajax({
			url:'/fx/getAutoForceStat.do',
			type:'post',
			contentType:'application/json',
			data:JSON.stringify({
				"prod":product,
				"userKey":userkey
			}),
			async:true,
			success:function(data){
				if(data.code==00){
					console.log( data.codeMessage=='0' );
					if( data.codeMessage=='0'){
						$('.getstat').text('开');
					}else if(data.codeMessage=='1'){
						$('.getstat').text('关');
					}
				}else if(data.code==01){
					dialog.choicedata(data.codeMessage,'whystrongflat');
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
    		    			'prod':product,
    		    			'pageNo':obj.curr,
    		    			'pageSize':$('.layui-laypage-limits select option:selected').attr('value')*1,
    		    			'userKey':userkey
    		    		});
    		    	}	
    		    }
    		  });
    	});
    }
	//点击强平和自动强平；
	/*$('.kyohei').click(function(){
		if( $('.box tbody tr').hasClass('selected') ){
			$.ajax({
				url:'/fx/mandatLiquidService.do',
				type:'post',
				contentType:'application/json',
				data:JSON.stringify({
					'AdditionalMarginVo':{
						"trac": 交易帐号
						"cyen": 保证金类型
					},
					"pageNo":$('.Nopage').text(),
					"pageSize":10,
					"userKey":userkey
					} 
				}),
				async:true,
				success:function(data){
					if(data.code==00){
						userdata=JSON.parse( data.codeMessage );
						
					}else if(data.code==01){
						
					}
				}
			});
		}else{
			dialog.choicedata('请先选择一条数据!','userinfo','aaa');
		}
	});*/
	$('.autokyohei').click(function(){
		var stat;
		if( $('.getstat').text()=='开'){
			stat=1;
		}else{
			stat=0;
		}
		$.ajax({
			url:'/fx/upAutoForceStat.do',
			type:'post',
			contentType:'application/json',
			data:JSON.stringify({
				"prod":product,
				"stat":stat
			}),
			async:true,
			success:function(data){
				if(data.codeMessage=='强平开关关闭'){
					$('.getstat').text('关');
				}else{
					$('.getstat').text('开');
				}
			}
		});
	});
})