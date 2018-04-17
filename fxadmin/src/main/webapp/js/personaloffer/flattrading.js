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
	ww=$(window).width()-345+"px";
    
	$('.boxtop').css('width',ww);
	$('.boxtop').css('height',wh);
	$('#d1').val(dialog.today());
	var spanText=$('.head span:eq(1)').text().substr(5);
    if(spanText=="清算明细查询"){
    	 var cols = [
    	             { title:'平盘流水号', name:'ppno' ,width:150, align:'left' },
    	             { title:'产品代码', name:'prcd' ,width:90, align:'left'},
    	             { title:'敞口编码', name:'ckno' ,width:90, align:'left'},
    	             { title:'买卖方向', name:'bsfx' ,width:90, align:'left'},
    	             { title:'交易日期', name:'trdt' ,width:100, align:'left'},
    	             { title:'交易请求时间', name:'trtm' ,width:100, align:'left'},
    	             { title:'交割日期', name:'jgdt' ,width:100, align:'left'},
    	             { title:'卖出币种', name:'bycy' ,width:100, align:'left'},
    	             { title:'买入币种', name:'bysl' ,width:90, align:'left'},
    	             { title:'成本卖金额', name:'cbsl',width:90, align:'left'},
    	             { title:'成本买金额', name:'cbby' ,width:90, align:'left'},
    	             { title:'交易卖金额', name:'samt' ,width:100, align:'left' },
    	             { title:'交易买金额', name:'bamt' ,width:100, align:'left'},
    	             { title:'盈亏金额', name:'ykam' ,width:90, align:'left'},
    	             { title:'交易策略', name:'jycl' ,width:90, align:'left'},
    	             { title:'交易对手', name:'mkno' ,width:90, align:'left'},
    	             { title:'交易方式', name:'trty' ,width:100, align:'left'},
    	             { title:'交易对手流水号', name:'dsno' ,width:150, align:'left'},
    	             { title:'交易回复时间', name:'ydtm' ,width:100, align:'left'},
    	             { title:'成本汇率', name:'cbhl' ,width:90, align:'left'},
    	             { title:'成交汇率', name:'expc',width:90, align:'left'},
    	             { title:'价格序号', name:'refe' ,width:280, align:'left'},
    	             { title:'起息日期', name:'qxdt',width:100, align:'left'},
    	             { title:'交易状态', name:'stat' ,width:100, align:'left'}
    	         ];
    	var MoZhangVo={'trdt':'','jgdt':''};
    	getselectList("selectList.do",MoZhangVo);
    	 
        //点击查询 请求数据
        $('.openquery').click(function(){
        	var dt=$('#product option:selected').text();
        	    date=$('#d1').val();
        	if(dt=="交易日期"){
        		MoZhangVo ={'trdt':date,'jgdt':''}
        	}else if(dt=="交割日期"){
        		MoZhangVo ={'trdt':'','jgdt':date}
        	}
        	getselectList("selectList.do",MoZhangVo);
        	
         });
        
    }else if(spanText=="清算汇总查询"){
    	 var cols = [
    	             { title:'交易日期', name:'trdt' ,width:120, align:'left' },
    	             { title:'交割日期', name:'jgdt' ,width:80, align:'left'},
    	             { title:'币种', name:'ccyt' ,width:80, align:'left'},
    	             { title:'轧差金额', name:'toam' ,width:80, align:'left'},
    	             { title:'买入金额', name:'byam' ,width:80, align:'left'},
    	             { title:'卖出金额', name:'slam' ,width:100, align:'left'}
    	          ];
    	var MoZhangVo ={'trdt':'','combox':'1'};
    	getselectList("selectTrdt.do",MoZhangVo);
    	$('.openquery').click(function(){
         	var dt=$('#product option:selected').val();
         	    date=$('#d1').val();
         	   MoZhangVo ={'trdt':date,'combox':dt}
         	 getselectList("selectTrdt.do",MoZhangVo);
         	
        });
    
    }
    //封装请求列表
    function getselectList(url,obj){
    	 $.ajax({
    		url:url,
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
   
 })

