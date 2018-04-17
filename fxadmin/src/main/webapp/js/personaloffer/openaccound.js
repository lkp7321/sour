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
		ww=$(window).width()-250+"px";
    $('.boxtop').css('width',ww);
	$('.boxtop').css('height',wh);
	$('#d12').val(dialog.today());
	$('#d13').val(dialog.today());
	//列参数
    var cols = [
        { title:'手工登记流水号', name:'sgno' ,width:150, align:'left' },
        { title:'登记日期', name:'apdt' ,width:100, align:'right'},
        { title:'登记时间', name:'aptm' ,width:120, align:'right'},
        { title:'交易员', name:'name' ,width:120, align:'left'},
        { title:'敞口编号', name:'ckno' ,width:120, align:'left'},
        { title:'买入币种', name:'bynm' ,width:100, align:'left'},
        { title:'卖出币种', name:'slnm' ,width:90, align:'left'},
        { title:'买入金额', name:'byam' ,width:100, align:'right'},
        { title:'卖出金额', name:'slam' ,width:90, align:'right'},
        { title:'成交汇率', name:'expc' ,width:90, align:'right'},
        { title:'成本汇率', name:'cbhl',width:90, align:'right'},
        { title:'盈利金额', name:'plam' ,width:90, align:'right'},
        { title:'结算日期', name:'jsdt',width:90, align:'right'},
        { title:'记录状态', name:'stcd' ,width:90, align:'center'}
    ];
    var usnm=sessionStorage.getItem('usnm'),
	    product=sessionStorage.getItem('product'),
	    dateApdt=$('#d12').val(),
	    dataEndInput=$('#d13').val(),
	    userKey=JSON.parse( sessionStorage.getItem('menu') ).userKey,
	    MoZhangVo={'userKey':userKey,'dateApdt':dateApdt,'dataEndInput':dataEndInput};
    getAllMxDetail(MoZhangVo);
    //点击查询
    $('.openquery').click(function(){
    	    dateApdt=$('#d12').val(),
    	    dataEndInput=$('#d13').val(),
    	    MoZhangVo={'userKey':userKey,'dateApdt':dateApdt,'dataEndInput':dataEndInput};
    	getAllMxDetail(MoZhangVo);
    })
    
     $('body',parent.document).on('click','.openaccound .sure',function(){
		//关闭选择一条数据;
		 $(this).closest('.zhezhao').remove(); 
	 });
    
  //封装请求列表
    function getAllMxDetail(obj){
       $.ajax({
    		url:'/fx/selectMxDetail.do',
    		type:'post',
    		contentType:'application/json',
    		data:JSON.stringify(obj),
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
    //抹账
    $('.mozhang').click(function(){
    	 if( $('.box tbody tr').hasClass('selected') ){
    		 dialog.cancelDate('你确定要抹账吗？','openaccound')
    		
    	}else{
			dialog.choicedata('请先选择一条数据!','openaccound');
		}
    })
    $('body',parent.document).on('click','.openaccound .confirm',function(){
    	 $.ajax({
	    		url:'/fx/mozhang.do',
	    		type:'post',
	    		contentType:'application/json',
	    		data:JSON.stringify({
	    			"userKey":userKey,
	    			"mxdetail" :{
	    			    "sgno": $('.box tr.selected td:eq(0) span').text(),
	    			    "apdt": $('.box tr.selected td:eq(1) span').text()
	    			}
	    		 }),
	    		async:true,
	    		success:function(data){
	    			 if(data.code==00){
	    				 $('.openaccound .confirm',parent.document).closest('.zhezhao').remove();
	    				 dialog.choicedata(data.codeMessage,'openaccound');
	    				 
	    			}else if(data.code==01){
	    				$('.openaccound .confirm',parent.document).closest('.zhezhao').remove();
	    				dialog.choicedata(data.codeMessage,'openaccound');
	    			}
	    		}
	    	});
	});
    $('body',parent.document).on('click','.openaccound .sure,.openaccound .cancel',function(){
		$(this,parent.document).closest('.zhezhao').remove();
	});

})

