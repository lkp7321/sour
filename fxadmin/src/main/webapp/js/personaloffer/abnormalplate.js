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
	ww=$(window).width()-345+"px";
    var num=0;
	$('.boxtop').css('width',ww);
	$('.boxtop').css('height',wh);
	//列参数
    var cols = [
        { title:'请求流水号', name:'ppno' ,width:120, align:'left' },
        { title:'敞口编号', name:'ckno' ,width:80, align:'left'},
        { title:'货币对名称', name:'exnm' ,width:80, align:'left'},
        { title:'请求日期', name:'trdt' ,width:80, align:'left'},
        { title:'请求时间', name:'trtm' ,width:100, align:'left'},
        { title:'买卖标志', name:'bsfx' ,width:80, align:'left'},
        { title:'成本买卖', name:'slam' ,width:100, align:'left'},
        { title:'成本买入', name:'byam' ,width:80, align:'left'},
        { title:'成本汇率', name:'cbhl',width:80, align:'left'},
        { title:'状态', name:'stmp' ,width:80, align:'left'}
     ];
     var usnm=sessionStorage.getItem('usnm'),
	    product=sessionStorage.getItem('product'),
	    userKey=JSON.parse( sessionStorage.getItem('menu') ).userKey;
	 getqueryExprtan(" ");
     $('.openquery').click(function(){
    	 var trac=$('#d1').val();
    	 if(trac==""){
    		 getqueryExprtan(" ");
    	 }else{
    		 getqueryExprtan(trac); 
    	 }
    	 
     })
     $('.handle').click(function(){
    	 var seqn=$('.box tr.selected td:eq(0) span').text(),
    	     MoZhangVo={"userKey":userKey,"seqn":seqn};
    	 if( $('.box tr').hasClass('selected') ){
    		 $.ajax({
    	    		url:'/fx/updateExprtan.do',
    	    		type:'post',
    	    		contentType:'application/json',
    	    		data:JSON.stringify(MoZhangVo),
    	    		async:true,
    	    		success:function(data){
    	    			 if(data.code==00){
    	    				 getqueryExprtan(" ");
    	    			    dialog.choicedata(data.codeMessage,'abnormalplate');
    	    			}else if(data.code==01){
    	    				//异常 
    	    				dialog.choicedata(data.codeMessage,'abnormalplate');
    	    			}
    	    		}
    	    	});
		 }else{
			dialog.choicedata('请先选择一条数据!','abnormalplate');
		}
    	 
     })
    $('body',parent.document).on('click','.abnormalplate .sure',function(){
		//关闭选择一条数据;
		 $(this).closest('.zhezhao').remove(); 
	 });
    //封装请求列表
    function getqueryExprtan(obj){
    	 $.ajax({
    		url:'/fx/queryExprtan.do',
    		type:'post',
    		contentType:'application/json',
    		data:obj,
    		//data:JSON.stringify(obj),
    		async:true,
    		success:function(data){
    			 if(data.code==00){
    				userdata=JSON.parse( data.codeMessage );
    			    dialog.ren({'cols':cols,'wh':wh,'userdata':userdata});
    			}else if(data.code==02){
    				dialog.ren({'cols':cols,'wh':wh,'userdata':''});
    			}
    		}
    	});
        
    };

})