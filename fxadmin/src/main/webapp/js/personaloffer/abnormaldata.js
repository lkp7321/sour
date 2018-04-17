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
		dialog:'dialog',
		WdatePicker:'./My97DatePicker/WdatePicker'
	}
});
require(['jquery','mmGrid','niceScroll','dialog','WdatePicker'],function($,mmGrid,niceScroll,dialog,WdatePicker){
	var wh=$(window).height()-190+"px",
	ww=$(window).width()-260+"px";
    var num=0;
    $('.toda').text( dialog.today() );
	$('.boxtop').css('width',ww);
	$('.boxtop').css('height',wh);
	var ti;
	//列参数
    var cols = [
        { title:'发生日期', name:'aldt' ,width:80, align:'right'},
        { title:'发生时间', name:'alti' ,width:80, align:'right'},
        { title:'告警信息', name:'alms' ,width:200, align:'left'},
        { title:'是否报警', name:'stat' ,width:80, align:'center'},
        { title:'是否处理', name:'dlst' ,width:80, align:'center'}
     ];
    var numreg=/^\d+$/,
    	a=0;
    $('body',parent.document).on('click','.abnormalplate .sure',function(){
		//关闭选择一条数据;
		 $(this).closest('.zhezhao').remove(); 
	});
    $.ajax({
		url:'/fx/getCmmException.do',
		type:'post',
		contentType:'application/json',
		//data:obj,
		data:dialog.today(),
		async:true,
		success:function(data){
			 if(data.code==01){
				userdata=data.codeMessage;
			    dialog.ren({'cols':cols,'wh':wh,'userdata':userdata});
			}else if(data.code==00){
				dialog.ren({'cols':cols,'wh':wh,'userdata':''});
			}
		},
		complete :function(){
			getqueryExprtan( dialog.today(),$('.refresh').val() );
		}
	});
    //封装请求列表
    function getqueryExprtan(obj,obj1){
    	ti=setInterval(function(){
    		$.ajax({
        		url:'/fx/getCmmException.do',
        		type:'post',
        		contentType:'application/json',
        		data:obj,
        		async:true,
        		success:function(data){
        			 if(data.code==01){
        				userdata=data.codeMessage;
        			    dialog.ren({'cols':cols,'wh':wh,'userdata':userdata});
        			}else if(data.code==00){
        				dialog.ren({'cols':cols,'wh':wh,'userdata':''});
        			}
        		}
        	});
    	},parseInt(obj1*1000));
    };
    $('.search').click(function(){
    	var refre=$('.refresh').val(),
    		d12=$('#d12').val();
  
    	if( numreg.test(refre) ){
    		if( d12==''){
    			d12=dialog.today();
    		}else{
    			d12=d12;
    		}
    		 getqueryExprtan( d12,$('.refresh').val() );
    	}else{
    		$('.refresh').val(' ');
    	}
    	
    });
})