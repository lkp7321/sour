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
	    ww=$(window).width()-260+"px";
		$('.boxtop').css('width',ww);
		$('.boxtop').css('height',wh);
		$('#page').css('width',ww);
	$('#d1,#d2').val( dialog.today() );

  //获取产品和用户名；
   var 	userkey=JSON.parse( sessionStorage.getItem('menu') ).userKey,
        product=sessionStorage.getItem('product'),userdata;
   
   if(product=='P999'){
	   $('.quoteoperation .Tit').text('报价平台管理>审计管理');
   }else if( product=='P003' ){
	   $('.quoteoperation .Tit').text('系统管理>日志管理');
   }else{
	   $('.quoteoperation .Tit').text('价格管理>报价操作查询');
   }
   var juesedata=false,jigoudata=false,jueseda,jigouda,listnumtotal;
	if(product=='P999'||product=='P003'){
		var cols = [
				      { title:'日志日期', name:'rzdt' ,width:70, align:'right' },
				      { title:'日志时间', name:'rzsj' ,width:70, align:'right'},
				      { title:'用户名', name:'usem' ,width:60, align:'left'},
				      { title:'菜单', name:'tymo' ,width:80, align:'left'},
				      { title:'操作', name:'remk' ,width:40, align:'left'},
				      { title:'操作日志', name:'vold' ,width:160, align:'left'},
				      { title:'操作结果', name:'vnew' ,width:100, align:'left'}
		         ];
		 renfnLog({
			 'userKey':userkey,
			 'data':$('#d1').val(),
			 'endata':$('#d2').val(),
			 'handle':$('#loghandle').val(),
			 'user':$('.usnm').val(),
			 'pageNo':1,
			 'pageSize':10},cols);
		 renpage();
	}else{
		if(product=='P004'){
			//列参数;
		   var cols = [
                { title:'市场分类', name:'bcfg' ,width:100, align:'left' },       
		        { title:'市场编号', name:'mkid' ,width:100, align:'left' },
		        { title:'价格类型', name:'tpnm' ,width:80, align:'center'},
		        { title:'期限', name:'term' ,width:60, align:'right'},
		        { title:'货币对', name:'exnm' ,width:80, align:'left'},
		        { title:'钞汇标志', name:'cxfg' ,width:40, align:'center'},
		        { title:'买入价', name:'neby' ,width:100, align:'right'},
		        { title:'卖出价', name:'nesl' ,width:100, align:'right'},
		        { title:'中间价', name:'nemd' ,width:100, align:'right'},
		        { title:'操作人', name:'opnm' ,width:100, align:'left'},
		        { title:'操作时间', name:'optm',width:120, align:'right'},
		        { title:'操作动作', name:'opac' ,width:80, align:'left'}
		    ];
		}else{
			//列参数;
		   var cols = [
		        { title:'市场编号', name:'mkid' ,width:100, align:'left' },
		        { title:'价格类型', name:'tpnm' ,width:60, align:'center'},
		        { title:'期限', name:'term' ,width:40, align:'right'},
		        { title:'货币对', name:'exnm' ,width:80, align:'left'},
		        { title:'钞汇标志', name:'cxfg' ,width:40, align:'center'},
		        { title:'买入价', name:'neby' ,width:70, align:'right'},
		        { title:'卖出价', name:'nesl' ,width:70, align:'right'},
		        { title:'中间价', name:'nemd' ,width:70, align:'right'},
		        { title:'操作人', name:'opnm' ,width:100, align:'left'},
		        { title:'操作时间', name:'optm',width:120, align:'right'},
		        { title:'操作动作', name:'opac' ,width:80, align:'left'}
		    ];
		}
		 renfn({'userKey':userkey,'optm':$('#d1').val(),'usnm':''},'HandPriceOperateList.do');
		 renpage();
	}
	//非p003 p004 p9999请求函数；
	function renfn(obj,url){
		 //调用接口,请求数据；
	    $.ajax({
			url:url,
			type:'post',
			contentType:'application/json',
			data:JSON.stringify(obj),
			async:false,
			success:function(data){
				if(data.code==00){
					userdata= data.codeMessage;
					dialog.ren({'cols':cols,'wh':wh,'userdata':userdata.list});
					listnumtotal=userdata.total;
				}else{
					dialog.ren({'cols':cols,'wh':wh,'userdata':''});
				}
			}
		});
	}
	function renfnLog(obj,cols){
		 //调用接口,请求数据；
	    $.ajax({
			url:'/fx/price/mng_logall.do',
			type:'post',
			contentType:'application/json',
			data:JSON.stringify(obj),
			async:false,
			success:function(data){
				if(data.code==00){
					userdata=data.codeMessage;
					dialog.ren({'cols':cols,'wh':wh,'userdata':userdata.list});
					listnumtotal=userdata.total;
				}else{
					dialog.ren({'cols':cols,'wh':wh,'userdata':''});
				}
			}
		});
	}
    $('.quoquery').click(function(){
    	var ti,user,url,vo;
    	if( $('#d1').val()==''){
    		ti='';
    	}else{
    		ti=$('#d1').val();
    	}
    	user=$('.usnm').val();
    	if(product=='P999' || product=='P003'){
    		url='/fx/price/mng_logall.do'; 
    		vo={
    				'userKey':userkey,
    				'data':$('#d1').val(),
    				'endata':$('#d2').val(),
    				'handle':$('#loghandle').val(),
    				'user':$('.usnm').val(),
    				'pageNo':1,
    				'pageSize':10
    				};
    	}else{
    		url='HandPriceOperateList.do';
    		vo={
				 userKey:userkey,
				 optm:ti,//时间
			     usnm:user//输入的用户名
			   }
    	}
    	if( ti==''&&user==''){
    		dialog.choicedata('请输入查询条件!','quoteoperation');
    	}else{
    		$.ajax({
				url:url,
				type:'post',
				contentType:'application/json',
				data:JSON.stringify(vo),
				async:false,
				success:function(data){
					if(url=='HandPriceOperateList.do'){
						if(data.code==00){
							userdata=data.codeMessage ;
							dialog.ren({'cols':cols,'wh':wh,'userdata':userdata.list});
							listnumtotal=userdata.total;
						}else{
							dialog.ren({'cols':cols,'wh':wh,'userdata':userdata.list});
						}
					}else if(url=='/fx/price/mng_logall.do'){
						if(data.code==01){
							userdata=data.codeMessage;
							dialog.ren({'cols':cols,'wh':wh,'userdata':userdata.list});
							listnumtotal=userdata.total;
						}else{
							dialog.ren({'cols':cols,'wh':wh,'userdata':userdata.list});
						}
					}
					
				}
			});
    		renpage();
    	}
    });
    $('body',parent.document).on('click','.quoteoperation .sure',function(){
		$(this).closest('.zhezhao').remove();
	});
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
    		    		renfnLog({
    		    			'userKey':userkey,
    		    			'data':$('#d1').val(),
    		    			'endata':$('#d2').val(),
    		    			'handle':$('#loghandle').val(),
    		    			'user':$('.usnm').val(),
    		    	    	'pageNo':obj.curr,
    		    	    	'pageSize':$('.layui-laypage-limits select option:selected').attr('value')*1,
    		    	    },
    		    	    [
    					      { title:'日志日期', name:'rzdt' ,width:70, align:'center' },
    					      { title:'日志时间', name:'rzsj' ,width:70, align:'center'},
    					      { title:'用户名', name:'usem' ,width:60, align:'center'},
    					      { title:'菜单', name:'tymo' ,width:80, align:'center'},
    					      { title:'操作', name:'remk' ,width:40, align:'center'},
    					      { title:'操作日志', name:'vold' ,width:160, align:'left'},
    					      { title:'操作结果', name:'vnew' ,width:100, align:'center'}
    			         ]
    		    		);
    		    	}	
    		    }
    		  });
    	});
    }
});
