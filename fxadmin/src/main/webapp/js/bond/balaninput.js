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
		wdatepicker:'./My97DatePicker/WdatePicker',
		dialog:'dialog'
	}
});
require(['jquery','mmGrid','niceScroll','wdatepicker','dialog'],function($,mmGrid,niceScroll,wdatepicker,dialog){
	var wh=$(window).height()-370+"px",
		ww=$(window).width()-100+"px";
		$('.boxtop').css('width',ww);
		$('.boxtop').css('height',wh);
		$('.profitlossbox').css('width',ww);
		$('#d1').val( dialog.today);
	//列参数;
    var cols = [
            { title:'并账日期', name:'bzdt',width:80,align:'left' },
            { title:'并账损益', name:'bzam',width:80, align:'left'},
            { title:'已平仓损益', name:'pacm',width:100, align:'left'},
            { title:'未平仓损益', name:'kcam',width:120, align:'left'},
            { title:'利息', name:'tllx',width:80, align:'left'},
            { title:'红利', name:'comm',width:80, align:'left'},
            { title:'本地流水号', name:'lcno',width:80, align:'left'},
            { title:'渠道流水号', name:'trsn',width:200, align:'left'},
            { title:'状态', name:'stcd',width:60, align:'left'}
    ];
     
    var userKey=JSON.parse( sessionStorage.getItem('menu') ).userKey;
    $('.balaninput .chestate').change(function(){
    	var text=$('.chestate option:selected').text();
    	if(text=="损益并账录入"){
    		$('.interest input,.interestb button').attr('disabled','disabled')
    		$('.profitloss input,.interestb button').removeAttr('disabled')
    	}else{
    		$('.interest input,.interestb button').removeAttr('disabled')
    		$('.profitloss input,.interestb button').attr('disabled','disabled')
    	}
    	
    });
    $('.query').click(function(){
    	var startDate=$('#d1').val(),
    	    tradeCode=$('.chestate option:selected').val(), 
    	    KondorVo={'startDate':startDate,'tradeCode':tradeCode}
    	if(tradeCode=='All'){
    		dialog.choicedata('请选择交易类型','balaninput');
    	}else{
    		getBailBalanceList(KondorVo);
    	}
    	
    	
    });
    
    
    //请求列表数据；
    function getBailBalanceList(obj){
    	$.ajax({
    		url:'/fx/queryBailBalanceList.do',
    		type:'post',
    		contentType:'application/json',
    		data:JSON.stringify(obj),
    		async:true,
    		success:function(data){
    			if(data.code==00){
    				userdata=JSON.parse( data.codeMessage );
    				dialog.ren({'cols':cols,'wh':wh,'userdata':userdata});
    			}else if(data.code==02){
    				
    			}
    			
    		}
    	}); 
    
    }
    //点击录入
    $('.enter').click(function(){
    	var bzdt=$('#d1').val(),
    	    trid=$('.chestate option:selected').val(),
    	    pacm=$('.already').val(),
    	    kcam=$('.notsy').val(), 
    	    tllx=$('.notlx').val(),
    	    comm=$('.redlx').val(),enterVo;
    	    if(trid=='M5715'){
    	    	//判断已平仓损益
    	        testVlue('.already','已平盘损益')
    	        //判断未平仓损益
    	        testVlue('.notsy','未平仓损益')
    	        //判断未平仓利息
    	        testVlue('.notlx','未平仓利息')
    	        enterVo={'userKey':userKey,'bailBalanceBean':{'bzdt':bzdt,'trid':trid,'pacm':pacm,'kcam':kcam,'tllx':tllx}}
    	    }else{
    	    	//判断红利
   	            testVlue('.redlx','红利')
    	    	enterVo={'userKey':userKey,'bailBalanceBean':{'bzdt':bzdt,'trid':trid,'comm':comm}}
    	    }
    	    if(trid=='All'){
        		dialog.choicedata('请选择交易类型','balaninput');
        	}else if($('.profitloss p re').length==0){
        		 $.ajax({
             		url:'/fx/sunyiluru.do',
             		type:'post',
             		contentType:'application/json',
             		data:JSON.stringify(enterVo),
             		async:true,
             		success:function(data){
             			if(data.code==00){
             				getBailBalanceList({'startDate':bzdt,'tradeCode':trid});
             				dialog.choicedata(data.codeMessage,'balaninput');
             			}else{
             				dialog.choicedata(data.codeMessage,'balaninput');
             			}
             			
             		}
             	}); 
        	}
    	   
    	    
    })
    //失焦判断/^[0-9]*$/.test()只能为数字
//    $('.already').blur(function(){
//    	if($(this).text()==''||$(this).text()==undefined){
//    		$(this).closest('p').append('<re>损益不能为空！</re>')
//    	}else if(/^[0-9]*$/.test($(this).text())){
//    		$(this).closest('p').find('re').remove();
//    		$(this).closest('p').append('<re>损益不能为空！</re>')
//    	}
//    })
    //判断已平仓损益
    testVlue('.already','已平盘损益')
    //判断未平仓损益
    testVlue('.notsy','未平仓损益')
    //判断未平仓利息
    testVlue('.notlx','未平仓利息')
    //判断红利
     testVlue('.redlx','红利')
    function testVlue(dom,str){
    	$(''+dom+'').blur(function(){
    		console.log()
        	if($(this).val()==''||$(this).val()==undefined){
        		$(this).closest('p').find('re').remove();
        		$(this).closest('p').append('<re>'+str+'不能为空！</re>')
        	}else if(!/^[0-9]*$/.test($(this).val())){
        		$(this).closest('p').find('re').remove();
        		$(this).closest('p').append('<re>'+str+'只能为数字！</re>')
        	}else{
        		$(this).closest('p').find('re').remove();
        	}
        })
    }
    $('body',parent.document).on('click','.balaninput .sure',function(){
		//关闭选择一条数据;
	    $(this).closest('.zhezhao').remove();
	});
});
