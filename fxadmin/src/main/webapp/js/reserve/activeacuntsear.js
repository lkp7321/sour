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
		dialog:'dialog'
	}
});
require(['jquery','mmGrid','niceScroll','dialog'],function($,mmGrid,niceScroll,dialog){
	var wh=$(window).height()-190+"px",
		ww=$(window).width()-250+"px";;
		$('.boxtop').css('width',ww);
		$('.boxtop').css('height',wh);
		$('#page').css('width',ww);
	var userkey=JSON.parse( sessionStorage.getItem('menu') ).userKey,str='';
	//列参数;
    var cols = [
        { title:'交易帐号', name:'trac' ,width:100, align:'left' },
        { title:'机构号', name:'ogcd' ,width:100, align:'left'},
        { title:'机构名称', name:'ognm' ,width:150, align:'left'},
        { title:'余额', name:'cblv' ,width:80, align:'right'},
        { title:'计息标志', name:'flag' ,width:150, align:'center'},
        { title:'客户号', name:'cuno' ,width:80, align:'left'},
        { title:'专户名称', name:'cunm' ,width:120, align:'left'}
    ];
    var juesedata=false,jigoudata=false,jueseda,jigouda,listnumtotal;
    //请求下拉列表；
	$.ajax({
		url:'/fx/queryUserOrganForAcc.do',
		type:'post',
		contentType:'application/json',
		data:userkey,
		async:true,
		success:function(data){
			if(data.code==00){
				userdata=JSON.parse( data.codeMessage );
				for( var i=0,num=userdata.length;i<num;i++){
					str='<option leve='+userdata[i].leve+' ogcd='+userdata[i].ognm+'>'+userdata[i].ognm+'</option>'
				}
				$('.fitnme').html(str);
			}
		}
	});
    getlist({
		userKey:userkey,
    	pageNo: 1,
    	pageSize: 10
    });
    function getlist(obj){
    	$.ajax({
    		url:'/fx/getAccInfoListByOrgn.do',
    		type:'post',
    		contentType:'application/json',
    		data:JSON.stringify( obj ),
    		async:false,
    		success:function(data){
    			if(data.code==00){
    				userdata=JSON.parse( data.codeMessage );
    				dialog.ren({'cols':cols,'wh':wh,'userdata':userdata.list});
    				listnumtotal=userdata.total;
//    				$('.page').remove();
//    				$('.boxtop').append('<div class="page"><span class="first">首页</span><span class="prev">上一页</span><span class="next">下一页</span><span class="last">末页</span><span class="nopage">第<b class="Nopage">'+userdata.pageNum+'</b>页/共<b class="Totalpage">'+userdata.pages+'</b>页</span><span class="totalpage">共<b class="total">'+userdata.total+'</b>条记录</span></div>')
    			}else if(data.code==01){
    				dialog.ren({'cols':cols,'wh':wh,'userdata':''});
    			}
    		}
    	});
    }
    //点击查询;
    $('.searc').click(function(){
    	getlist({
    		userKey:userkey,
        	pageNo: 1,
        	pageSize: 10
        });
    });
    renpage();
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
			    		getlist({
			    			userKey:userkey,
			    			'pageSize':$('.layui-laypage-limits select option:selected').attr('value')*1,
	    					'pageNo':orj.curr
			    	    });
			    	}	
			    }
			  });
		});
	}
    
	/*----------------快速搜索功能的实现------------------------*/
	$('.info_serbtn').click(function(){
		  dialog.serchData($('.info_seript').val(),'.mmg-headWrapper');
    });
	//点击分页;
    $('.boxtop').on('click','.first',function(){
    	var Nopage=$('.Nopage').text();
 	    if(Nopage>1){
 	    	Nopage=1;
 	    	getlist({ userKey:userkey,'pageNo':Nopage,'pageSize':10} );
 	    }
    });
	$('.boxtop').on('click','.prev',function(){
	    var Nopage=$('.Nopage').text();
	    if(Nopage>1){
	    	Nopage=Nopage-1;
	    	getlist({ userKey:userkey,'pageNo':Nopage,'pageSize':10} );
	    }
	});
	$('.boxtop').on('click','.next',function(){
		var Nopage=$('.Nopage').text(),
			Totalpage=$('.Totalpage').text();
	    if(Nopage<Totalpage){
	    	Nopage=Nopage*1+1;
	    	getlist({ userKey:userkey,'pageNo':Nopage,'pageSize':10} );
	    }
	});
	$('.boxtop').on('click','.last',function(){
		var Nopage=$('.Nopage').text(),
		Totalpage=$('.Totalpage').text();
	    if(Nopage<Totalpage){
	    	Nopage=Totalpage;
	    	getlist({ userKey:userkey,'pageNo':Nopage,'pageSize':10} );
	    }
	});
})
