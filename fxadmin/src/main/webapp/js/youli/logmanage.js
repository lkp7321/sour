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
	    ww=$(window).width()-260+"px";
	$('.boxtop').css('width',ww);
	$('.boxtop').css('height',wh);
	$('.page').css('width',ww);
	$('#d1').val(dialog.today());
	//列参数;
    var cols = [
        { title:'操作日期', name:'rzdt' ,width:80, align:'right' },
        { title:'操作时间', name:'rzsj' ,width:100, align:'right'},
        { title:'操作用户', name:'usem' ,width:120, align:'left'},
        { title:'操作模块', name:'tymo' ,width:120, align:'left' },
        { title:'参数说明', name:'remk' ,width:120, align:'left' },
        { title:'操作描述', name:'vold' ,width:180, align:'left' },
        { title:'操作结果', name:'vnew' ,width:120, align:'left' },
    ];
    var userkey=JSON.parse( sessionStorage.getItem('menu') ).userKey,prevpage,
        usem=sessionStorage.getItem('usnm'),
        listnumtotal,
        remk=$('.opename').val(),
        rzdt=$('#d1').val(),
        logVo={'entity':{ 'usem':usem,'remk':remk,'rzdt':rzdt},'pageNo':1,'pageSize':10,};
        getList(logVo);
        renpage();
    
    //点击查询请求列表
    $('.qurey').click(function(){
    	remk=$('.opename').val(),
        rzdt=$('#d1').val(),
    	logVo={'entity':{'usem':usem,'remk':remk,'rzdt':rzdt},'pageNo':1,'pageSize':10,}
    	getList(logVo);
    	renpage();
    })
	 
    //封装请求列表
	function getList(obj){
		$.ajax({
    		url:'/fx/jsh/getJshErrorCodeList.do',
    		type:'post',
    		contentType:'application/json',
    		data:JSON.stringify(obj),
    		async:false,
    		success:function(data){
    			if(data.code==01){
       				userdata= data.codeMessage;
       				dialog.ren({'cols':cols,'wh':wh,'userdata':userdata.list});
       				listnumtotal=userdata.total;
//       			$('.page').remove();
//    				$('.boxtop').append('<div class="page"><span class="first">首页</span><span class="prev">上一页</span><span class="next">下一页</span><span class="last">末页</span><span class="nopage">第<b class="Nopage">'+userdata.pageNum+'</b>页/共<b class="Totalpage">'+userdata.pages+'</b>页</span><span class="totalpage">共<b class="total">'+userdata.total+'</b>条记录</span></div>')
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
   		        	getList({
   	        			'entity':{
   	        				'usem':usem,
   	        				'remk':$('.opename').val(),
   	        				'rzdt':$('#d1').val()
   	        			},
   	        			'pageNo':obj.curr,
   	        			'pageSize':$('.layui-laypage-limits select option:selected').attr('value')*1,
   		        	});
                 }
   		    }
   		  });
   	});
    }
   
	/*//点击分页;
    $('.boxtop').on('click','.page .first',function(){
    	var Nopage=parseInt($('.Nopage').text());
 	    if(Nopage>1){
 	    	Nopage=1;
 	    	remk=$('.opename').val(),
 	        rzdt=$('#d1').val(),
 	    	logVo={'entity':{'usem':usem,'remk':remk,'rzdt':rzdt},'pageNo':Nopage,'pageSize':10,}
 	    	getList(logVo); 
        } 
    });
	$('.boxtop').on('click','.prev',function(){
	    var Nopage=parseInt($('.Nopage').text());
	    if(Nopage>1){
	    	Nopage=Nopage-1;
	    	remk=$('.opename').val(),
	        rzdt=$('#d1').val(),
	    	logVo={'entity':{'usem':usem,'remk':remk,'rzdt':rzdt},'pageNo':Nopage,'pageSize':10,}
 	    	getList(logVo); 
	    }
	});
	$('.boxtop').on('click','.next',function(){
		var Nopage=parseInt($('.Nopage').text()),
			Totalpage=parseInt($('.Totalpage').text());
	    if(Nopage<Totalpage){
	    	Nopage=Nopage+1;
	    	remk=$('.opename').val(),
	        rzdt=$('#d1').val(),
	    	logVo={'entity':{'usem':usem,'remk':remk,'rzdt':rzdt},'pageNo':Nopage,'pageSize':10,}
 	    	getList(logVo); 
	     }
	});
	$('.boxtop').on('click','.last',function(){
		var Nopage=parseInt($('.Nopage').text()),
		Totalpage=parseInt($('.Totalpage').text());
	    if(Nopage<Totalpage){
	    	Nopage=Totalpage;
	    	remk=$('.opename').val(),
	        rzdt=$('#d1').val(),
	    	logVo={'entity':{'usem':usem,'remk':remk,'rzdt':rzdt},'pageNo':Nopage,'pageSize':10,}
 	    	getList(logVo); 
	    }
	});*/
})
