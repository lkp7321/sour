/*告警事件查询*/
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
		WdatePicker:'./My97DatePicker/WdatePicker',
		dialog:'dialog'
	}
});
require(['jquery','mmGrid','niceScroll','WdatePicker','dialog'],function($,mmGrid,niceScroll,WdatePicker,dialog){
	var wh=$(window).height()-190+"px",
		ww=$(window).width()-260+"px";
	$('.boxtop').css('width',ww);
	$('.boxtop').css('height',wh);
	$('#page').css('width',ww);
	var userkey=JSON.parse( sessionStorage.getItem('menu') ).userKey;
	$('#d1,#d2').val( dialog.today() );
		//列参数;
    var cols = [
            { title:'发生日期', name:'aldt',width:40,align:'right' },
            { title:'发生时间', name:'alti',width:50, align:'right'},
            { title:'报警状态', name:'stat',width:50, align:'center'},
            { title:'报警日期', name:'chdt',width:50,align:'right'},
            { title:'报警时间', name:'chti',width:50,align:'right'},
            { title:'错误代码', name:'ercd',width:50,align:'left'},
            { title:'报警信息', name:'alms',width:120,align:'left'},
    ];
    var juesedata=false,jigoudata=false,jueseda,jigouda,listnumtotal;
    //请求下拉列表；
    $.ajax({
		url:'/fx/price/alarmEventBox.do',
		type:'post',
		contentType:'application/json',
		async:false,
		success:function(data){
			var str='';
			if(data.code==01){
				userdata=data.codeMessage;
				for(var i=0,num=userdata.length;i<num;i++){
					str+='<option ertx='+userdata[i].ERTX+'>'+userdata[i].ERCD+'</option>'
				}
				$('.acudeal').html( str );
			}else if(data.code==00){
				
			}
		}
	});
    //请求列表；
    getlist({
    	betime:$('#d1').val(),
    	ercd:$('.acudeal option:selected').text(),
    	pageNo:1,
    	pageSize:10
    });
    renpage();
    function getlist(obj){
    	$.ajax({
    		url:'/fx/price/alarmEventList.do',
    		type:'post',
    		contentType:'application/json',
    		data:JSON.stringify( obj ),
    		async:false,
    		success:function(data){
    			if(data.code==01){
    				userdata = data.codeMessage;
    				dialog.ren({'cols':cols,'wh':wh,'userdata':userdata.list});
    				listnumtotal=userdata.total;
    			}else if(data.code==00){
    				dialog.ren({'cols':cols,'wh':wh,'userdata':''});
    			}
    		}
    	});
    }
    //改变错误代码；
    $('.acudeal').click(function(){
    	var ertx=$(this).find('option:selected').attr('ertx'),
    		txt=$(this).find('option:selected').text();
    	$('.cl1 .tit').text( ertx );
    });
	$('.sear').click(function(){ //点击查询
		 getlist({
	    	betime:$('#d1').val(),
	    	ercd:$('.acudeal option:selected').text(),
	    	pageNo:1,
	    	pageSize:10
	    });
		 renpage();
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
    		    		    	betime:$('#d1').val(),
    		    		    	ercd:$('.acudeal option:selected').text(),
    		    		    	pageNo:obj.curr,
        		    	    	pageSize:$('.layui-laypage-limits select option:selected').attr('value')*1
    		    		    });
    		    	}	
    		    }
    		  });
    	});
    }
})