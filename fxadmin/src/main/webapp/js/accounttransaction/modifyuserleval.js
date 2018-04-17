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
		ww=$(window).width()-250+"px";
	$('.boxtop').css('width',ww);
	$('.boxtop').css('height',wh);
	$('#page').css('width',ww);
	//列参数;
    var cols = [
        { title:'交易账号', name:'TRAC' ,width:140, align:'left' },         
        { title:'客户号', name:'CUNO' ,width:100, align:'left' },        
        { title:'卡号', name:'CUAC' ,width:140, align:'left' },
        { title:'资金账户类型', name:'CATY' ,width:100, align:'center'},
        { title:'签约/解约', name:'REFG' ,width:100, align:'center'},
        { title:'客户等级', name:'CUTY' ,width:100, align:'right'},
        { title:'机构名称', name:'OGNM' ,width:200, align:'left'},
        { title:'签约日期', name:'RGDT' ,width:80, align:'right'},
        { title:'解约日期', name:'CRDT' ,width:80, align:'right'},
        { title:'解约编号', name:'RGID' ,width:150, align:'left'}
    ];
    var juesedata=false,jigoudata=false,jueseda,jigouda,listnumtotal;
    //请求列表
    var userkey=JSON.parse( sessionStorage.getItem('menu') ).userKey,
        cuno=$('.usernum').val(),
        cuac=$('.usernum').val(),
        vo={'userkey':userkey,'cuno':cuno,'cuac':cuac,'pageNo':1,'pageSize':10};
    getList(vo);
    renpage();
    //点击查询请求列表
    $('.query').click(function(){
    	cuno=$.trim($('.usernum').val()),
        cuac=$.trim($('.cardnum').val()),
        vo={'userkey':userkey,'cuno':cuno,'cuac':cuac,'pageNo':1,'pageSize':10};
    	getList(vo);
    	renpage();
    });
 
 
	//点击修改按钮  弹出修改窗
	$('.modify').click(function(){
		 if( $('.box tbody tr').hasClass('selected') ){
			dialog.modifyUserleval('modifyuserleval');
			$('.usernum',parent.document).val($('.box tr.selected td:eq(1) span').text());
			$('.cardnum',parent.document).val($('.box tr.selected td:eq(2) span').text());
			$('.userleval',parent.document).val($('.box tr.selected td:eq(5) span').text());
		  }else{
			dialog.choicedata('请选择一条数据','modifyuserleval');
		}
	});
	 //点击修改弹出框的保存按钮；
	$('body',parent.document).on('click','.preserve',function(){
	    var cuno=$('.usernum',parent.document).val(),
	        cuac=$('.cardnum',parent.document).val(),
	        cuty=$('.userleval',parent.document).val(),
	        trac=$('.box tr.selected td:eq(0) span').text(),
	        addVo={'userKey':userkey,'cuno':cuno,'cuac':cuac,'cuty':cuty,'trac':trac};
	        $.ajax({
				url:'/fx/getUpdate.do',
				type:'post',
				contentType:'application/json',
				data:JSON.stringify(addVo),
				async:true,
				success:function(data){
					 if(data.code==00){
						 $('.preserve',parent.document).closest('.zhezhao').remove();
						 getList(vo);
					 	 dialog.choicedata(data.codeMessage,'modifyuserleval');
					 }else if(data.code==01){
						//异常
						 dialog.choicedata(data.codeMessage,'modifyuserleval');
					}
				}
			}); 
	  });
	 
	 
	 
	//点击请选择一条数据的确定按钮；
	$('body',parent.document).on('click','.modifyuserleval .sure',function(){
		  $(this).closest('.zhezhao').remove();
	});
	//关闭弹窗
	$('body',parent.document).on('click','.discountpub .close,.discountpub .cancel',function(){
		  $(this).closest('.zhezhao').remove();
	});
	
	 
    //封装请求列表
	function getList(obj){
		$.ajax({
    		url:'/fx/changeSearch.do',
    		type:'post',
    		contentType:'application/json',
    		data:JSON.stringify(obj),
    		async:false,
    		success:function(data){
    			if(data.code==00){
       				userdata=JSON.parse( data.codeMessage );
       				dialog.ren({'cols':cols,'wh':wh,'userdata':userdata.list});
       				listnumtotal=userdata.total;
//       			 $('.page').remove();
//    				$('.boxtop').append('<div class="page"><span class="first">首页</span><span class="prev">上一页</span><span class="next">下一页</span><span class="last">末页</span><span class="nopage">第<b class="Nopage">'+userdata.pageNum+'</b>页/共<b class="Totalpage">'+userdata.pages+'</b>页</span><span class="totalpage">共<b class="total">'+userdata.total+'</b>条记录</span></div>')
       			}else if(data.code==01){
       				dialog.ren({'cols':cols,'wh':wh,'userdata':''});
       			}else if(data.code==02){
       				dialog.ren({'cols':cols,'wh':wh,'userdata':''});
       			}
    		}
    	}); 
	}
	//点击分页;
    $('.boxtop').on('click','.page .first',function(){
    	var Nopage=parseInt($('.Nopage').text());
 	    if(Nopage>1){
 	    	Nopage=1;
 	    	cuno=$.trim($('.usernum').val()),
 	        cuac=$.trim($('.cardnum').val()),
 	        vo={'userkey':userkey,'cuno':cuno,'cuac':cuac,'pageNo':Nopage,'pageSize':10};
 	    	getList(vo);
 	     }
 	  
    });
	$('.boxtop').on('click','.prev',function(){
	    var Nopage=parseInt($('.Nopage').text());
	    if(Nopage>1){
	    	Nopage=Nopage-1;
	    	cuno=$.trim($('.usernum').val()),
	        cuac=$.trim($('.cardnum').val()),
	        vo={'userkey':userkey,'cuno':cuno,'cuac':cuac,'pageNo':Nopage,'pageSize':10};
	    	getList(vo); 
	     }
	    
	});
	$('.boxtop').on('click','.next',function(){
		var Nopage=parseInt($('.Nopage').text()),
			Totalpage=parseInt($('.Totalpage').text());
	    if(Nopage<Totalpage){
	    	Nopage=Nopage+1;
	    	cuno=$.trim($('.usernum').val()),
	        cuac=$.trim($('.cardnum').val()),
	        vo={'userkey':userkey,'cuno':cuno,'cuac':cuac,'pageNo':Nopage,'pageSize':10};
	    	getList(vo);
	     }
	      
	});
	$('.boxtop').on('click','.last',function(){
		var Nopage=parseInt($('.Nopage').text()),
		Totalpage=parseInt($('.Totalpage').text());
	    if(Nopage<Totalpage){
	    	Nopage=Totalpage;
	    	cuno=$.trim($('.usernum').val()),
	        cuac=$.trim($('.cardnum').val()),
	        vo={'userkey':userkey,'cuno':cuno,'cuac':cuac,'pageNo':Nopage,'pageSize':10};
	    	getList(vo);
	    }
	     
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
			    		 getList({
		    		    	'userkey':userkey,
		    		    	'cuno':$('.usernum').val(),
		    		    	'cuac':$('.usernum').val(),
			    	    	'pageSize':$('.layui-laypage-limits select option:selected').attr('value')*1,
		    				'pageNo':obj.curr
			    		    });
			    	}	
			    }
			  });
		});
	} 	
	 
	  
})
