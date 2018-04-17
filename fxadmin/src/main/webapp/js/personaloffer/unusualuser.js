require.config({
	baseUrl:'/fx/js/',
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
		niceScroll:'./js_files/jquery.nicescroll.min',
		dialog:'dialog'
	}
});
require(['jquery','mmGrid','niceScroll','dialog'],function($,mmGrid,niceScroll,dialog){
	var wh=$(window).height()-190+"px",
    ww=$(window).width()-260+"px";
	
    $('.boxtop').css('width',ww);
    $('.boxtop').css('height',wh);
   
	//列参数;
	var cols = [
        { title:'用户名称', name:'usnm',width:100, align:'left' },
        { title:'角色', name:'ptnm' ,width:100, align:'left'},
        { title:'机构名称', name:'ognm',width:150, align:'left'},
        { title:'真实姓名', name:'cmpt' ,width:80, align:'left'},
        { title:'失败次数', name:'ecount',width:80, align:'right'}
    ];
    var usnm=sessionStorage.getItem('usnm'),
		product=sessionStorage.getItem('product'),
		loginuser={'usnm':usnm,'prod':product},
		userdata;
    var userkey=JSON.parse( sessionStorage.getItem('menu') ).userKey;
    $.ajax({
    	url:'/fx/getErrorUser.do',
    	type:'post',
    	contentType:'application/json',
    	data:userkey,
    	async:false,
    	success:function(data){
    		if(data.code==00){
    			userdata=JSON.parse( data.codeMessage );
    			dialog.ren({'cols':cols,'wh':wh,'userdata':userdata});
    		}else if(data.code==02){
    			dialog.ren({'cols':cols,'wh':wh,'userdata':''});
    		}
    		
    	}
    });
 
	$('.unus_clear').click(function(){
		//清楚所有数据;
		$.ajax({
			url:'/fx/deleteErrors.do',
			type:'post',
	    	contentType:'application/json',
	    	data:userkey,
	    	async:false,
	    	success:function(data){
	    		if(data.code==00){
	    			$('.mmGrid').remove();
	    			//$('body').append('<p class="nounusual">暂无登录异常用户!</p>');
	    			dialog.ren({'cols':cols,'wh':wh,'userdata':''});
	    		}else{
	    			dialog.choicedata(data.codeMessage,'unusualuser');
	    		}
	    	}
		});
	})
    /*----------------快速搜索功能的实现------------------------*/
	$('.unus_serbtn').click(function(){
		  dialog.serchData($('.unus_seript').val());
    });
	
})
