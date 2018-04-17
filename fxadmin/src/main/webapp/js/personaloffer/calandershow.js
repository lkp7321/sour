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
	var wh=$(window).height()-200+"px",
		ww=$(window).width()-260+"px",
		userkey=JSON.parse( sessionStorage.getItem('menu') ).userKey;
	$('.boxtop').css('width',ww);
	$('.boxtop').css('height',wh);
	$('.calendarshow #d1').val(dialog.today());
	//列参数
    var cols = [
        { title:'星期', name:'week' ,width:100, align:'right' },
        { title:'开始时间', name:'beginTime' ,width:100, align:'right'},
        { title:'结束时间', name:'endTime' ,width:100, align:'right'},
        { title:'标志', name:'flag' ,width:80, align:'center'}
         
    ];
    //渲染空数据列表
    dialog.ren({'cols':cols,'wh':wh,'userdata':''});
    //请求下拉列表
    $.ajax({
		url:'/fx/allTradeCodeList.do',
		type:'post',
		async:false,
		dataType:'json',
		data:userkey,
		contentType:'application/json;charset=utf-8',
		beforeSend:function(){
				 str='<option value="all">请选择</option>';
		},
		success:function(data){
		 if(data.code==01){
			var listdata=data.codeMessage;
			for(var i in listdata){
				str+='<option value='+listdata[i].trcd+'>'+listdata[i].trds+'</option>'
			}
			$('.small').html(str);
		   }else if(data.code==02){
			 //获取失败
		}
		}
	});
    //点击查询 请求数据
    $('.seabtn').click(function(){
    	var caltime=$('#d1').val(),
    	    tradeCode=$('.small option:selected').val(),
    	    bsVo={'userKey':userkey,'tradeCode':tradeCode,'caltime':caltime};
    	 getCalendarShow(bsVo);
    });
    
    //点击生效 
    $('.livbtn').click(function(){
    	 $.ajax({
    		url:'/fx/SendSocketPdtInfo.do',
    		dataType:'json',
    		contentType:'application/json;charset=utf-8',
    		success:function(data){
    		    if(data.code==01){
    		    	dialog.choicedata('发送消息','calendarshow');
    			}else{
    				//error
    			}
    		}
    	});
    });
    //封装请求列表
    function getCalendarShow(obj){
    	 $.ajax({
    		url:'/fx/getCalendarShow.do',
    		type:'post',
    		contentType:'application/json',
    		data:JSON.stringify(obj),
    		async:true,
    		success:function(data){
    			if(data.code==01){
    				userdata= data.codeMessage ;
    			    dialog.ren({'cols':cols,'wh':wh,'userdata':userdata});
    			}else if(data.code==00){
    				dialog.ren({'cols':cols,'wh':wh,'userdata':''});
    			}
    		}
    	});
    };
    $('body',parent.document).on('click','.calendarshow .sure',function(){
		$(this).closest('.zhezhao').remove();
	});

})

