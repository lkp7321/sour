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
	var wh=$(window).height()-210+"px",
		ww=$(window).width()-250+"px";
	$('.page').css('width',ww);
	$('.boxtop').css('width',ww);
	$('.boxtop').css('height',wh);
	//列参数;
    var cols = [
            { title:'错误码', name:'ERCD',width:100,align:'left' },
            { title:'内部说明', name:'ERIN',width:100, align:'left'},
            { title:'外部说明', name:'ERTX',width:100, align:'left'}
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
			ercd:$('#ercd').val()
    	}
    });
    //点击查询按钮;
    $('.errorcode').click(function(){
    	var Nopage=$('.Nopage').text()*1;
    	var obj={
    			ercd:$('#ercd').val()
    	}
    	getlist({
	    	pageNo:1,
	    	pageSize:10,
	    	entity:obj
	    });
    });
    
    //点击添加和修改
    $('.add').click(function(){
    	dialog.errorCodeAdd('errorcode','add')
        $('.ercd',parent.document).blur(function(){
			 if($(this).val()==''||$(this).val()==undefined){
				$('.ercd',parent.document).parent('div').find('re').remove();
				$('.ercd',parent.document).parent('div').append('<re>错误码不能为空！</re>');
			}else{
				$('.ercd',parent.document).parent('div').find('re').remove();
			} 
		})
		 
    })
   
     $('.modify').click(function(){
	
		 if( $('.box tbody tr').hasClass('selected') ){
			//修改弹出框;
			 dialog.errorCodeAdd('errorcode','modify')
			$('.ercd',parent.document).val($('.box tr.selected td:eq(0) span').text()).attr('disabled','disabled');
			$('.ertx',parent.document).val($('.box tr.selected td:eq(1) span').text());
			$('.erin',parent.document).val($('.box tr.selected td:eq(2) span').text());
			 
		 }else{
			dialog.choicedata('请先选择一条数据!','errorcode','aaa');
		}
    })
    $('body',parent.document).on('click','.preserve',function(){
		var text=$(this).text(),url,
		    ercd=$('.ercd',parent.document).val(),
		    ertx=$('.ertx',parent.document).val(),
		    erin=$('.erin',parent.document).val(),
		    errVo={
				     'userKey':userkey,
				     'entity':{
				    	 'ercd':ercd,
				    	 'ertx':ertx,
				    	 'erin':erin,
				     }
				
		   };
		/* if(trtl==''||trtl==undefined){
			 $('.trtl',parent.document).parent('div').find('re').remove();
			 $('.trtl',parent.document).parent('div').append('<re>交易柜员不能为空！</re>');
		}else{
			num++;
			$('.trtl',parent.document).parent('div').find('re').remove();
		}*/
	 
		if(text=='保存'){
			url='/fx/jsh/addErrorCodes.do';
		 
		}else{
			url='/fx/jsh/modifyErrorCodes.do';
			
		}
			$.ajax({
				url:url,
				type:'post',
				async:true,
				contentType:'application/json',
	    		data:JSON.stringify(errVo),
				success:function(data){
					if(data.code==01){
						$('.preserve',parent.document).closest('.zhezhao').remove(); 
						dialog.choicedata(data.codeMessage,'errorcode','aaa');
						getlist({
					    	pageNo:1,
					    	pageSize:10,
					    	entity:{
					    		ercd:$('#ercd').val()
					    	}
					    });
					}else{
						dialog.choicedata(data.codeMessage,'errorcode','aaa');
					}
				}
			});
	  }); 
    
    $('body',parent.document).on('click','.sure,.cancel,.close',function(){
		$(this).closest('.zhezhao').remove();
	});
    function getlist( objk ){
    	$.ajax({
    		url:'/fx/jsh/getErrorCodeList.do',
    		type:'post',
    		contentType:'application/json',
    		data:JSON.stringify( objk ),
    		async:false,
    		success:function(data){
    			if(data.code==01){
    				userdata= data.codeMessage;
    				dialog.ren({'cols':cols,'wh':wh,'userdata':userdata.list});
    				listnumtotal=userdata.total;
    				//renfn(objk );
    				//$('.page').remove();
    				///$('.boxtop').append('<div class="page"><span class="first">首页</span><span class="prev">上一页</span><span class="next">下一页</span><span class="last">末页</span><span class="nopage">第<b class="Nopage">'+userdata.pageNum+'</b>页/共<b class="Totalpage">'+userdata.pages+'</b>页</span><span class="totalpage">共<b class="total">'+userdata.total+'</b>条记录</span></div>')
    			}else {
    				dialog.ren({'cols':cols,'wh':wh,'userdata':''});
    			}
    		}
    	});
    }
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
            	    	entity:{
            	    		ercd:$('#ercd').val()
            	    	}
            	    });
               }
		    }
		  });
	});
    
    //点击分页;
    $('.boxtop').on('click','.first',function(){
    	var Nopage=$('.Nopage').text()*1;
 	    if(Nopage>1){
 	    	Nopage=1;
 	    	getlist({
	 	       	pageNo:Nopage,
	 	       	pageSize:10,
	 	       	entity:{
	 	       		ercd:$('#ercd').val()
	 	       	}
 	       });
 	    }
    });
	$('.boxtop').on('click','.prev',function(){
	    var Nopage=$('.Nopage').text()*1;
	    if(Nopage>1){
	    	Nopage=Nopage-1;
	    	getlist({
	 	       	pageNo:Nopage,
	 	       	pageSize:10,
	 	       	entity:{
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
	 	       		ercd:$('#ercd').val()
	 	       	}
 	       });
	    }
	});
});