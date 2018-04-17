require.config({
	baseUrl:'/fx/js',
	shim:{
		'jquery.niceScroll': {
			deps: ['jquery'],
			exports: 'jquery.niceScroll'},
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
//问题:  数据少一条；
require(['jquery','mmGrid','niceScroll','dialog'],function($,mmGrid,niceScroll,dialog){
	var wh=$(window).height()-190+"px",
		ww=$(window).width()-250+"px";
	$('#page').css('width',ww);
    $('.boxtop').css('width',ww);
    $('.boxtop').css('height',wh);
	
	//列参数
    var cols = [
        { title:'用户名称', name:'usnm' ,width:110, align:'left' },
        { title:'角色', name:'ptnm' ,width:80, align:'left'},
        { title:'机构名称', name:'ognm' ,width:120, align:'left'},
        { title:'真实姓名', name:'cmpt' ,width:80, align:'left'},
        { title:'移动电话', name:'mbtl' ,width:100, align:'left'},
        { title:'电话', name:'telp' ,width:100, align:'left'},
        { title:'传真', name:'ufax' ,width:40, align:'left'},
        { title:'EMAIL', name:'mail' ,width:100, align:'left'},
        { title:'MAC/IP', name:'macip' ,width:40, align:'left'},
        { title:'用户状态', name:'usfg',width:60, align:'left'},
        { title:'备注', name:'rmrk',width:40, align:'left'}
      
    ];
    var usnm=sessionStorage.getItem('usnm'),
		product=sessionStorage.getItem('product'),
		user={'usnm':usnm,'prod':product},
		userdata,listnumtotal;
    var userkey=JSON.parse( sessionStorage.getItem('menu') ).userKey;
  //请求列表数据；
    getlist({
    	'userKey':userkey,
    	'pageNo':1,
    	'pageSize':10
    });
    renpage();
    function getlist( obj ){
    	//请求数据;
        $.ajax({
    		url:'/fx/listUser.do',
    		type:'post',
    		contentType:'application/json',
    		data:JSON.stringify( obj ),
    		async:false,
    		success:function(data){
    			if(data.code==00){
    				userdata= data.codeMessage ;
                    dialog.ren({'cols':cols,'wh':wh,'userdata':userdata.list});
                    listnumtotal=userdata.total;
                  /*  $('.page').remove();
    				$('.boxtop').append('<div class="page"><span class="first">首页</span><span class="prev">上一页</span><span class="next">下一页</span><span class="last">末页</span><span class="nopage">第<b class="Nopage">'+userdata.pageNum+'</b>页/共<b class="Totalpage">'+userdata.pages+'</b>页</span><span class="totalpage">共<b class="total">'+userdata.total+'</b>条记录</span></div>')
    				*/
    			}else if(data.code==01){	
    				dialog.ren({'cols':cols,'wh':wh,'userdata':''});
    			}
    		}
    	});
    }
    //点击复核按钮弹窗
	$('.check_btn').click(function(){
		if( $('.box tbody tr').hasClass('selected') ){
			//复核弹出框;
			dialog.checkDate('userreview');
			$('.checkname input[type="text"]',parent.document).val( $('.box tbody tr.selected td:eq(0) span').text() );
			$('.agentname input[type="text"]',parent.document).val( $('.box tbody tr.selected td:eq(1) span').text() );
			if( $('.box tbody tr.selected td:eq(9) span').text()=='未复核' ){
				$('.userreview .state .chenopass',parent.document).prop('checked','checked');
			}else{
				$('.userreview .state .checkpass',parent.document).prop('checked','checked');
			}
		}else{
			dialog.choicedata('请先选择一条数据!','userreview','aaa');
		}
	 })
	//关闭选择数据提示框;
	$('body',parent.document).on('click','.che_close,.che_cancel',function(){
		$(this).closest('.zhezhao').remove();
	});
	//点击复核一条数据的确定按钮；
	$('body',parent.document).on('click','.che_save',function(){
		var vo,state;
		if($('.state input[type="radio"]:checked',parent.document).attr('value')=='通过'){
			state=1;
		}else{
			state=0;
		}
		vo={'userKey':userkey,'user':{'usnm':$('.box tr.selected td').eq(0).text(),'usfg':state}}
		$.ajax({
				url:'/fx/upUserFg.do',
				type:'post',
				contentType:'application/json',
				data:JSON.stringify(vo),
				async:true,
				success:function(data){
					$('.che_save',parent.document).closest('.zhezhao').remove();
					if(data.code==00){
						dialog.choicedata(data.codeMessage,'userreview','aaa');
						getlist({
					    	'userKey':userkey,
					    	'pageNo':1,
					    	'pageSize':10
					    });
						renpage();
					}else{
						dialog.choicedata(data.codeMessage,'userreview','aaa');
					}
				}
		 });
	});
	//render function 
	function reviewrender(obj){
		$.ajax({
    		url:obj,
    		type:'post',
    		contentType:'application/json',
    		data:userkey,
    		async:false,
    		success:function(data){
    			userdata=JSON.parse( data.codeMessage );
    			if(data.code==00){
    				dialog.ren({'cols':cols,'wh':wh,'userdata':userdata});
    			}else if(data.code==01){
    				dialog.choicedata(data.codeMessage,'userinfo','aaa');
    			}
    			$('.userreview .sure',parent.document).closest('.zhezhao').remove();
    		}
    	});
	}
	$('body',parent.document).on('click','.userreview .sure',function(){
		var a=$('.userreview',parent.document).data('tit'),url;
		if(a=='success'){
			getlist({
		    	'userKey':userkey,
		    	'pageNo':1,
		    	'pageSize':10
		    });
			renpage();
		}else{
			$(this).closest('.userreview').remove();
		}
	});
	//点击分页;
    $('.boxtop').on('click','.first',function(){
    	var Nopage=$('.Nopage').text()*1;
 	    if(Nopage>1){
 	    	Nopage=1;
 	    	getlist({
	        	'userKey':userkey,
	        	'pageNo':Nopage,
	        	'pageSize':10
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
	        	'pageSize':10
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
	        	'pageSize':10
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
	        	'pageSize':10
	        });
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
    		    		getlist({
    		    	    	pageNo:obj.curr,
    		    	    	pageSize:$('.layui-laypage-limits select option:selected').attr('value')*1,
    		    	    	'userKey':userkey
    		    	    });
    		    	}	
    		    }
    		  });
    	});
    }
/*----------------快速搜索功能的实现------------------------*/
	$('.review_serbtn').click(function(){
		 dialog.serchData( $('.review_seript').val() );
    });
});

