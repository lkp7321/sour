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
		ww=$(window).width()-270+"px";
	$('.boxtop').css('width',ww);
	$('.boxtop').css('height',wh);
	
	$('.Toda').text(dialog.today );
	$('.Toda1').text(dialog.today );
	//获取产品和用户名；
    var product=sessionStorage.getItem('product'),
        userKey=JSON.parse( sessionStorage.getItem('menu') ).userKey,
		userdata,sT,eT;
	//列参数
    var cols = [
        { title:'流水号', name:'ppno' ,width:150, align:'left' },
        { title:'货币对', name:'exnm' ,width:100, align:'left'},
        { title:'敞口编号', name:'ckno' ,width:100, align:'left'},
        { title:'方向', name:'bsfx' ,width:80, align:'left'},
        { title:'平盘类型', name:'trfl' ,width:100, align:'left'},
        { title:'交易日期', name:'trdt' ,width:120, align:'left'},
        { title:'交易时间', name:'trtm' ,width:120, align:'left'},
        { title:'卖出币种', name:'slcy' ,width:100, align:'left'},
        { title:'买入币种', name:'bycy' ,width:100, align:'left'},
        { title:'卖金额', name:'samt',width:100, align:'left'},
        { title:'买金额', name:'bamt',width:100, align:'left'},
        { title:'盈亏金额', name:'ykam' ,width:80, align:'left'},
        { title:'交易策略', name:'jycl' ,width:80, align:'left'},
        { title:'对手流水', name:'dsno' ,width:150, align:'left'},
        { title:'回复时间', name:'ydtm' ,width:120, align:'left'},
        { title:'成本汇率', name:'cbhl' ,width:100, align:'left'},
        { title:'成交汇率', name:'expc' ,width:100, align:'left'},
        { title:'交割日期', name:'jgdt' ,width:100, align:'left'},
        { title:'交易状态', name:'stat' ,width:100, align:'left'},
        { title:'补记敞口流水号', name:'lkno' ,width:120, align:'left'}
    ];
    //查询货币对列表；
    $.ajax({
		url:'/fx/selUSDEXNM.do',
		type:'post',
		contentType:'application/json',
		data:userKey,
		async:true,
		success:function(data){
			if(data.code==00){
				userdata=JSON.parse( data.codeMessage );
				var str="<option>所有</option>"
				for(var i=0,num=userdata.length;i<num;i++){
					str+='<option>'+userdata[i]+'</option>'
				}
				$('#product').html( str );
			}else if(data.code==02){
				dialog.choicedata('获取数据对失败!','userinfo','aaa');
			}
			
		}
	});
    getlist({
    	userKey : userKey,
    	sartDate : $('.Toda').text(),
    	endDate : $('.Toda1').text(),
    	strExnm : '',
    	strStat : ''
    });
    //点击查询；
    $('.openquery').click(function(){
    	var st,et,strExnm,strStat;
    	if( $('#d12').val()==''){
    		st=$('.Toda').text();
    	}else{
    		 st=$('#d12').val();
    	}
    	if( $('#d13').val()==''){
    		et=$('.Toda1').text();
    	}else{
    		 et=$('#d13').val();
    	}
    	if( $('.product option:selected').text()=='所有'){
    		strExnm='';
    	}else{
    		strExnm=$('.product option:selected').text();
    	}
    	if( $('.product1 option:selected').attr('value')=='所有'){
    		strStat='';
    	}else{
    		strStat=$('.product1 option:selected').attr('value');
    	}
    	
    	getlist({
    		userKey : userKey,
        	sartDate :st,
        	endDate : et,
        	strExnm : strExnm,
        	strStat : strStat
        });
    });
    //请求列表数据；
    function getlist( obj ){
    	$.ajax({
    		url:'/fx/selAllList.do',
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
    }
})

