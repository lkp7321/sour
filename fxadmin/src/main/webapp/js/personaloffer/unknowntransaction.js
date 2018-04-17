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
    $('.boxtop').css('width',ww);
	$('.boxtop').css('height',wh);
	//列参数
    var cols = [
        { title:'本地平盘流水号', name:'seqn' ,width:150, align:'left' },
        { title:'货币对简称', name:'exnm',width:100, align:'left'},
        { title:'敞口编号', name:'ckno',width:100, align:'left'},
        { title:'自动平盘标志', name:'trfl',width:120, align:'left'},
        { title:'买卖方向', name:'bsfx',width:100, align:'left'},
        { title:'交易日期', name:'trdt',width:100, align:'left'},
        { title:'交易请求时间', name:'trtm',width:80, align:'left'},
        { title:'卖出币种', name:'slcy',width:100, align:'left'},
        { title:'买入币种', name:'bycy',width:80, align:'left'},
        { title:'成本卖金额', name:'cbsl',width:80, align:'left'},
        { title:'成本买金额', name:'cbby',width:80, align:'left'},
        { title:'交易卖金额', name:'samt',width:80, align:'left'},
        { title:'交易买金额', name:'bamt',width:80, align:'left'},
        { title:'盈亏金额', name:'ykam',width:80, align:'left'},
        { title:'交易对手', name:'mkno',width:80, align:'left'},
        { title:'交易对手流水号', name:'dsno',width:150, align:'left'},
        { title:'交易回复时间', name:'ydtm',width:80, align:'left'},
        { title:'成本汇率', name:'cbhl',width:80, align:'left'},
        { title:'成交汇率', name:'expc',width:80, align:'left'},
        { title:'价格序号', name:'refe',width:150, align:'left'},
        { title:'补记敞口流水号', name:'lkno' ,width:80, align:'left'}
    ];
    var userKey=JSON.parse( sessionStorage.getItem('menu') ).userKey,userdata;
    //请求列表
    getPPAccount(" ");
    //获取USB联系方式
    getQueryPtpara('C120','#d2')
     //请求我行标识
    getQueryPtpara('C121','#d3')
    //点击查询
    $('.openquery').click(function(){
    	var lcno=$('#d1').val();
    	 
    	if(lcno==""){
    		getPPAccount(" ");
    	}else{
    		getPPAccount(lcno);
    	}
    	 
    })
    
     $('body',parent.document).on('click','.openaccound .sure',function(){
		//关闭选择一条数据;
		 $(this).closest('.zhezhao').remove(); 
	 });
    
  //封装请求列表
    function getPPAccount(obj){
    	$.ajax({
    		url:'/fx/selPPAccount.do',
    		type:'post',
    		contentType:'application/json',
    		data:obj,
    		async:true,
    		success:function(data){
    			 if(data.code==00){
    				 userdata=JSON.parse( data.codeMessage );
     			     ren({'cols':cols,'wh':wh,'userdata':userdata});
    			}else if(data.code==02){
    				 ren({'cols':cols,'wh':wh,'userdata':''});
    			}
    		}
    	});
        
    };
    //封装请求USB联系方式和我行标识
    function getQueryPtpara(obj,dom){
    	$.ajax({
    		url:'/fx/queryPtpara.do',
    		type:'post',
    		contentType:'application/json',
    		data:obj,
    		async:true,
    		success:function(data){
    			if(data.code==00){
    				 
    			   if(data.codeMessage=='null'){
    				   
    				   $(''+dom+'').val('');
    			   }else{
    				   $(''+dom+'').val(data.codeMessage);
    			   }
    				 
    			}else if(data.code==02){
    				 
    			}
    		}
    	});
    }
    //成功处理
    $('.success').click(function(){
    	 if( $('.box tbody tr').hasClass('selected') ){
    		 var MoZhangVo={
	    		"userKey":userKey,
	    		"ppresult" :{
		    		"exnm": $('.box tr.selected td:eq(1) span').text(),
		    		"slcy": $('.box tr.selected td:eq(7) span').text(),
		    		"bamt": $('.box tr.selected td:eq(10) span').text(),
		    		"samt": $('.box tr.selected td:eq(11) span').text(),
		    		"bsfx": $('.box tr.selected td:eq(4) span').text(),
		    		"ppno": $('.box tr.selected td:eq(0)').attr('ppno'),
		    		"seqn":$('.box tr.selected td:eq(0) span').text(),
		    		"dsno":$('.box tr.selected td:eq(15) span').text(),
		    		"expc":$('.box tr.selected td:eq(18) span').text(),
		    		"jgdt":$('.box tr.selected td:eq(1)').attr('jgdt'),
		    		"qxdt":$('.box tr.selected td:eq(2)').attr('qxdt'),
		    		"trfl":$('.box tr.selected td:eq(3) span').text(),
	    		  }
	    		}
    		 
    		 $.ajax({
        		url:'/fx/onSucessManage.do',
        		type:'post',
        		contentType:'application/json',
        		data:JSON.stringify(MoZhangVo),
        		async:true,
        		success:function(data){
        			 if(data.code==00){
        				 dialog.choicedata(data.codeMessage,'unknowntransaction');
        			}else if(data.code==01){
        				dialog.choicedata(data.codeMessage,'unknowntransaction');
        			}
        		}
        	});
    	}else{
			dialog.choicedata('请先选择一条数据!','unknowntransaction');
		}
    })
     //失败处理
    $('.failure').click(function(){
        if( $('.box tbody tr').hasClass('selected') ){
        	 var MoZhangVo={
     	    		"userKey":userKey,
     	    		"ppresult" :{
     		    		"ppno": $('.box tr.selected td:eq(0)').attr('ppno'),
     		    		"seqn":$('.box tr.selected td:eq(0) span').text(),
     		    		"trfl":$('.box tr.selected td:eq(3) span').text()
     	    		  }
     	    		}
         		 
         		 $.ajax({
             		url:'/fx/onFaultManage.do',
             		type:'post',
             		contentType:'application/json',
             		data:JSON.stringify(MoZhangVo),
             		async:true,
             		success:function(data){
             			 if(data.code==00){
             				 dialog.choicedata(data.codeMessage,'unknowntransaction');
             			}else if(data.code==01){
             				dialog.choicedata(data.codeMessage,'unknowntransaction');
             			}
             		}
             	});
    	}else{
			dialog.choicedata('请先选择一条数据!','unknowntransaction');
		}
    })
   $('body',parent.document).on('click','.unknowntransaction .sure',function(){
			//关闭选择一条数据;
		   $(this).closest('.zhezhao').remove();
	 });
    //table的渲染
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
				horizrailenabled:false
		  });
		   mmg.on('loadSuccess',function(e,data){
			   $('.box').find('tbody tr').each(function(i,v){
				 $(v).find('td').eq('0').attr('ppno',obj.userdata[i].ppno);
				 $(v).find('td').eq('1').attr('jgdt',obj.userdata[i].jqdt);
				 $(v).find('td').eq('2').attr('qxdt',obj.userdata[i].qxdt);
			 });
			 $('.box tbody tr').each(function(i,v){
				 $(v).find('span').css('color','red');
			 });
		 }) 
		 
	  }

})

