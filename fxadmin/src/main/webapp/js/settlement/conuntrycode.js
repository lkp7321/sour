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
	$('.boxtop').css('width',ww);
	$('.boxtop').css('height',wh);
	var listnumtotal;
	//列参数;
    var cols = [
            { title:'国别', name:'name',width:60,align:'left' },
            { title:'民生国别', name:'cmbccout',width:60, align:'left'},
            { title:'外管局国别', name:'cout',width:80, align:'left'},
            { title:'国别描述', name:'copycout',width:60, align:'left'}
    ];
    getlist({
    	fieldName:$('#byExnm option:selected').attr('value'),
    	fieldValue:$('.titi').val(),
    	pageNo:1,    
    	pageSize:10
    });
    renpage();
    //点击查询；
    $('.search').click(function(){
    	getlist({
        	fieldName:$('#byExnm option:selected').attr('value'),
        	fieldValue:$('.titi').val(),
        	pageNo:1,    
        	pageSize:10
        });
    	renpage();
    });
    function getlist( obj ){
    	$.ajax({
    		url:'/fx/queryCountByCondition.do',
    		type:'post',
    		contentType:'application/json',
    		data:JSON.stringify(obj ),
    		async:false,
    		success:function(data){
    			if(data.code==00){
    				userdata=JSON.parse( data.codeMessage );
    				dialog.ren({'cols':cols,'wh':wh,'userdata':userdata.list});
    				listnumtotal=userdata.total;
    				/*$('.page').remove();
    				$('.boxtop').append('<div class="page"><span class="first">首页</span><span class="prev">上一页</span><span class="next">下一页</span><span class="last">末页</span><span class="nopage">第<b class="Nopage">'+userdata.pageNum+'</b>页/共<b class="Totalpage">'+userdata.pages+'</b>页</span><span class="totalpage">共<b class="total">'+userdata.total+'</b>条记录</span></div>');
    			*/
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
    		    ,jump: function(obj,first){
    		    	if(!first){ //点击跳页触发函数自身，并传递当前页：obj.curr
    		    		getlist({
    		            	fieldName:$('#byExnm option:selected').attr('value'),
    		            	fieldValue:$('.titi').val(),
    		            	pageNo:obj.curr,    
    		            	pageSize:$('.layui-laypage-limits select option:selected').attr('value')*1
    		            });
    		    	}	
    		    }
    		  });
    	});
    }
  //点击分页;
    /*$('.boxtop').on('click','.first',function(){
    	var Nopage=$('.Nopage').text()*1;
 	    if(Nopage>1){
 	    	Nopage=1;
 	    	getlist({ 
 	    		fieldName:$('#byExnm option:selected').attr('value'),
 	    		fieldValue:' ',
 	    		'pageNo':Nopage,'pageSize':10
 	    	});
 	    }
    });
	$('.boxtop').on('click','.prev',function(){
	    var Nopage=$('.Nopage').text()*1;
	    if(Nopage>1){
	    	Nopage=Nopage-1;
	    	getlist({ 
 	    		fieldName:$('#byExnm option:selected').attr('value'),
 	    		fieldValue:' ',
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
 	    		fieldName:$('#byExnm option:selected').attr('value'),
 	    		fieldValue:' ',
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
 	    		fieldName:$('#byExnm option:selected').attr('value'),
 	    		fieldValue:'',
 	    		'pageNo':Nopage,
 	    		'pageSize':10
 	    	});
	    }
	});*/
})