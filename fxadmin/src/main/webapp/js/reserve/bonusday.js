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
		wdatePicker:'./My97DatePicker/WdatePicker',
		dialog:'dialog'
	}
});
require(['jquery','mmGrid','niceScroll','wdatePicker','dialog'],function($,mmGrid,niceScroll,wdatePicker,dialog){
	var wh=$(window).height()-200+"px",
		ww=$(window).width()-250+"px";;

	$('.boxtop').css('width',ww);
	$('.boxtop').css('height',wh);
	$('#page').css('width',ww);
	//列参数;
    var cols = [
        { title:'分红日', name:'dddt' ,width:100, align:'right' },
        { title:'更新日期', name:'updt' ,width:100, align:'right'}
    ];
    var juesedata=false,jigoudata=false,jueseda,jigouda,listnumtotal;
    var userkey=JSON.parse( sessionStorage.getItem('menu') ).userKey,
        userVo={'userKey':userkey,'pageNo':1,'pageSize':10}
    //请求列表
    getParaList(userVo);
	renpage();
	//点击添加按钮
    $('.add').click(function(){
		dialog.bonusdAdd('bonusda');
	    $('#d1',parent.document).val(dialog.today());
	});
	//点击添加弹窗的保存
    $('body',parent.document).on('click','.bonusda .preserve',function(){
		 var dddt=$('#d1',parent.document).val(),
		     DivideParaVo={'userKey':userkey,'dddt':dddt};
		 $.ajax({
			url:'/fx/doInsertDividePara.do',
			type:'post',
			contentType:'application/json',
			data:JSON.stringify(DivideParaVo),
			async:true,
			success:function(data){
				 if(data.code==00){
					 $('.bonusda .preserve',parent.document).closest('.zhezhao').remove();
					 getParaList(userVo); 
					 dialog.choicedata(data.codeMessage,'bonusda');
					 renpage();
				 }else if(data.code==01){
					//异常
					 dialog.ren({'cols':cols,'wh':wh,'userdata':''});
				}
			}
		}); 

    });
	//关闭修改弹出框；
	$('body',parent.document).on('click','.bonusda .close,.bonusda .cancel,.sure',function(){
		$(this).closest('.zhezhao').remove();
	}); 
	/*----------------快速搜索功能的实现------------------------*/
	$('.info_serbtn').click(function(){
		  dialog.serchData('.info_seript');
    });
	$('body',parent.document).on('click','#d1',function(){
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
	 //封装请求列表
	function getParaList(obj){
		$.ajax({
    		url:'/fx/getDivideParaList.do',
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
			    ,jump: function(orj,first){
			    	if(!first){ //点击跳页触发函数自身，并传递当前页：obj.curr
			    		getParaList({
			    			'userKey':userkey,
			    			'pageSize':$('.layui-laypage-limits select option:selected').attr('value')*1,
	    					'pageNo':orj.curr
		    			});
			    	}	
			    }
			  });
		});
	}
	//点击分页;
    $('.boxtop').on('click','.first',function(){
    	var Nopage=$('.Nopage').text();
 	    if(Nopage>1){
 	    	Nopage=1;
 	     }
 	   userVo={'userKey':userkey,'pageNo':Nopage,'pageSize':10};
 	   getParaList(userVo);
    });
	$('.boxtop').on('click','.prev',function(){
	    var Nopage=$('.Nopage').text();
	    if(Nopage>1){
	    	Nopage=Nopage-1;
	     }
	    userVo={'userKey':userkey,'pageNo':Nopage,'pageSize':10};
	 	   getParaList(userVo); 
	});
	$('.boxtop').on('click','.next',function(){
		var Nopage=$('.Nopage').text(),
			Totalpage=$('.Totalpage').text();
	    if(Nopage<Totalpage){
	    	Nopage=Nopage*1+1;
	     }
	    userVo={'userKey':userkey,'pageNo':Nopage,'pageSize':10};
	 	   getParaList(userVo);
	});
	$('.boxtop').on('click','.last',function(){
		var Nopage=$('.Nopage').text(),
		Totalpage=$('.Totalpage').text();
	    if(Nopage<Totalpage){
	    	Nopage=Totalpage;
	    }
	    userVo={'userKey':userkey,'pageNo':Nopage,'pageSize':10};
	 	   getParaList(userVo);
	});

})
