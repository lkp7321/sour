/*非结售汇的客户价格监控*/ 
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
	var wh=$(window).height()-190+"px",
		ww=$(window).width()-260+"px";
	var product=sessionStorage.getItem('product'),
		userkey=JSON.parse( sessionStorage.getItem('menu') ).userKey;
    $('.boxtop').css('width',ww);
	$('.boxtop').css('height',wh);
	//列参数
    var cols = [
        { title:'货币对名称', name:'exnm' ,width:100, align:'left' },
        { title:'买入价', name:'neby',width:100, align:'right'},
        { title:'中间价', name:'nemd',width:100, align:'right'},
        { title:'卖出价', name:'nesl',width:120, align:'right'},
        { title:'更新时间', name:'mdtm',width:150, align:'right'},
        { title:'报价状态', name:'stfg',width:100, align:'center'},
        { title:'报价来源', name:'mknm',width:80, align:'left'},
        { title:'错误码', name:'ercd',width:80, align:'center'},
        { title:'交易标志', name:'trfg',width:80, align:'center'}
    ];
    if( product!='P004'){
    	$('.refreshbtn').css('display','inline-block');
    	$.ajax({
    		url:'/fx/getBrnchCom.do',
    		type:'post',
    		contentType:'application/json',
    		async:false,
    		data:userkey,
    		success:function(data){
    			var str='';
    			 if(data.code==01){
    				 userdata=data.codeMessage;
    				 for(var i=0,num=userdata.length;i<num;i++){
    					 str+='<option ptid='+userdata[i].PTID+'>'+userdata[i].PTNM+'</option>'
    				 }
    				$('.produ').html( str ).css({'display':'inline-block'});
    			}else{
    				dialog.choicedata(data.codeMessage,'cuspriexp');
    			}
            }
    	});
    }
    if(product=="P004"){
		 getAllpdtlist( product );
	 }else{
		  getAllpdtlist( $('.produ option:selected').attr('ptid') );
	 }
     $('.produ').change(function(){
    	 if(product=="P004"){
    		 getAllpdtlist( product );
    	 }else{
    		 getAllpdtlist( $('.produ option:selected').attr('ptid') );
    	 }
    });
     $('.refreshbtn').click(function(){
		dialog.cancelDate('您确定要刷新产品校验器吗?','cuspriexp');
	});
	$('body',parent.document).on('click','.cuspriexp .confirm',function(){
		$.ajax({
    		url:'/fx/refshcustPricestats.do',
    		type:'post',
    		contentType:'application/json',
    		async:true,
    		data:JSON.stringify({
    			'userKey':userkey,
    			'ptid':$('.produ option:selected').attr('ptid')
    		}),
    		success:function(data){
    			$('.cuspriexp .confirm',parent.document).closest('.zhezhao').remove();
    			 if(data.code==02){
    				 dialog.choicedata(data.codeMessage,'cuspriexp');
    				 
    			}else{
    				dialog.choicedata(data.codeMessage,'cuspriexp');
    			}
            }
    	});
	});  
     $('body',parent.document).on('click','.cuspriexp .sure,.cuspriexp .cancel',function(){
		//关闭选择一条数据;
		 $(this).closest('.zhezhao').remove(); 
	 });
    
  //封装请求列表
    function getAllpdtlist(obj){
    	$.ajax({
    		url:'/fx/getAllpdtlist.do',
    		type:'post',
    		contentType:'application/json',
    		async:true,
    		data:obj,
    		success:function(data){
    			 if(data.code==01){
    				 userdata=data.codeMessage;
    				 ren({'cols':cols,'wh':wh,'userdata':userdata});
    			}else if(data.code==00){
    				ren({'cols':cols,'wh':wh,'userdata':''});
    			}
            }
    	});
        
    };
    function ren(obj){
    	$('.boxtop').html('');
    	$('#ascrail2000').remove();
    	$('.boxtop').append('<div class="box"></div>');
		var mmg = $('.box').mmGrid({
				height:obj.wh
				, cols: obj.cols
				,items:obj.userdata
	            , nowrap:true
	            , fullWidthRows:true
	            , checkCol:obj.checked
	            , multiSelect:obj.select
	            ,showBackboard:true
	  	});
		 $(".mmg-bodyWrapper").niceScroll({
				touchbehavior:false,
				cursorcolor:"#666",
				cursoropacitymax:0.7,
				cursorwidth:6,
				background:"#ccc",
				autohidemode:false,
				horizrailenabled:obj.horizrailenabled
		  });
    }
    //搜索功能；	 
    $('.unus_serbtn').click(function(){
		  var txt=$('.unus_seript').val();
		  dialog.serchData(txt,'.mmg-bodyWrapper');
  });
    
})

