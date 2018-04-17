/*
 * 对账交易复核
 * */
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
	var userdata,str='',
	    userKey=JSON.parse( sessionStorage.getItem('menu') ).userKey;
	//列参数;
    var cols = [
        { title:'行内流水号', name:'seqn',width:150, align:'left' },
        { title:'买币种', name:'bycy' ,width:100, align:'left'},
        { title:'买金额', name:'bamt' ,width:100, align:'left'},
        { title:'卖币种', name:'slcy' ,width:100, align:'left'},
        { title:'卖金额', name:'samt' ,width:100, align:'left'},
        { title:'成交汇率', name:'expc' ,width:100, align:'left'},
        { title:'平盘对手', name:'mkno' ,width:100, align:'left'},
        { title:'平盘日期', name:'trdt' ,width:100, align:'left'},
        { title:'平盘时间', name:'trtm' ,width:100, align:'left'},
        { title:'平盘成交时间', name:'ydtm' ,width:90, align:'left'},
        { title:'交易状态', name:'stat' ,width:100, align:'left'}
    ];
    getlist(" ");
    //请求列表数据；
    function getlist(obj){
    	$.ajax({
    		url:'/fx/ConditionList.do',
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
    }
    //点击查询
    $('.openquery').click(function(){
    	if( $('#d1').val()==''){
    		 getlist(" ");
    	}else{
    		getlist( $('#d1').val() );
    	}
    });
    function ren( obj ){
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
		 mmg.on('loadSuccess',function(){
			 $('.box tr').each(function(i,v){
				if( $(v).find('td').eq('10').text()=='手工调账-V'){
					$(v).find('span').css('color','red');
				}else{
					$(v).find('span').css('color','#333');
				}
			 })
		 });
    }
    //点击对账；
    $('.checkbill').click(function(){
    	if( $('.box tbody tr').hasClass('selected') ){
    		dialog.flatdealwater('reconcili');
    		var inlinenum=$('.box tr.selected td:eq(0) span').text(),
    			byeincuur=$('.box tr.selected td:eq(1) span').text(),
    			byeinmoney=$('.box tr.selected td:eq(2) span').text(),
    			sailcuur=$('.box tr.selected td:eq(3) span').text(),
    			sailmoney=$('.box tr.selected td:eq(4) span').text(),
    			dealrate=$('.box tr.selected td:eq(5) span').text(),
    			flatopponent=$('.box tr.selected td:eq(6) span').text(),
    			flatdata=$('.box tr.selected td:eq(7) span').text(),
    			flattime=$('.box tr.selected td:eq(8) span').text(),
    			flatdealdata=$('.box tr.selected td:eq(9) span').text(),
    			dealstate=$('.box tr.selected td:eq(10) span').text();
    		
    			$('.inlinenum',parent.document).val( inlinenum );
    			$('.byeincuur',parent.document).val( byeincuur );
    			$('.byeinmoney',parent.document).val( byeinmoney );
    			$('.sailcuur',parent.document).val( sailcuur );
    			$('.sailmoney',parent.document).val( sailmoney );
    			$('.dealrate',parent.document).val( dealrate );
    			$('.flatopponent',parent.document).val( flatopponent );
    			$('.flatdata',parent.document).val( flatdata );
    			$('.flattime',parent.document).val( flattime );
    			$('.flatdealdata',parent.document).val( flatdealdata );
    			$('.dealstate',parent.document).val( dealstate );
    			
		 }else{
			dialog.choicedata('请先选择一条数据!','reconcili','aaa');
		}    	
    });
    //点击成功；
    $('body',parent.document).on('click','.succss',function(){
    	succfail({url:'/fx/success.do',seqn:$('.box tr.selected td:eq(0) span').text() });
    });
    //点击失败；
    $('body',parent.document).on('click','.faile',function(){
    	succfail({url:'/fx/unsuccess.do',seqn:$('.box tr.selected td:eq(0) span').text() });
    });
    //成功或失败
    function succfail(obj){
        var usnm=sessionStorage.getItem('usnm'),
		product=sessionStorage.getItem('product');
    	$.ajax({
    		url:obj.url,
    		type:'post',
    		contentType:'application/json',
    		data:JSON.stringify({
    			userKey :userKey,
    			seqn :obj.seqn
    		}),
    		async:true,
    		success:function(data){
    			if(data.code==00){
    				dialog.choicedata(data.codeMessage,'reconcilir','success');
    				if( $('#d1').val()==''){
    		    		 getlist(" ");
    		    	}else{
    		    		getlist( $('#d1').val() );
    		    	}
    			}else if(data.code==02){
    				dialog.choicedata(data.codeMessage,'reconcili','aaa');
    			}
    		}
    	});
    }
    //点击确认；
    $('body',parent.document).on('click','.reconcili .sure,.reconcili .close',function(){
    	$(this).closest('.zhezhao').remove();
    });
    $('body',parent.document).on('click','.reconcilir .sure',function(){
    	$('.zhezhao',parent.document).remove();
    });
	/*----------------快速搜索功能的实现------------------------*/
	/*$('.openquery').click(function(){
		dialog.serchData($('.info_seript').val(),'.mmg-headWrapper');
	});*/
	
})

