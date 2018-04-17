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
	var wh=$(window).height()-190+"px",
		ww=$(window).width()-260+"px";
		$('.boxtop').css('width',ww);
		$('.boxtop').css('height',wh);
		$('#d1').val( dialog.today);
	//列参数;
    var cols = [
            { title:'并账日期', name:'bzdt',width:120,align:'right' },
            { title:'并账损益', name:'bzam',width:80, align:'right'},
            { title:'已平仓损益', name:'pacm',width:100, align:'right'},
            { title:'未平仓损益', name:'kcam',width:120, align:'right'},
            { title:'利息', name:'tllx',width:80, align:'right'},
            { title:'红利', name:'comm',width:80, align:'right'},
            { title:'本地流水号', name:'lcno',width:80, align:'left'},
            { title:'渠道流水号', name:'trsn',width:80, align:'left'},
            { title:'状态', name:'stcd',width:80, align:'center'}
            
    ];
    var userKey=JSON.parse( sessionStorage.getItem('menu') ).userKey,userdata;
    dialog.ren({'cols':cols,'wh':wh,'userdata':''}); 
    $('.query').click(function(){
    	var startDate=$('#d1').val(),
    	    tradeCode=$('.chestate option:selected').val(),
    	    KondorVo={'startDate':startDate,'tradeCode':tradeCode}
    	if(tradeCode=='All'){
    		dialog.choicedata('请选择交易类型','balanrev');
    	}else{
    		getBailBalanceList(KondorVo);
    	}
    	
    	
    });
    
    
    //请求列表数据；
    function getBailBalanceList(obj){
    	$.ajax({
    		url:'/fx/queryBailBalanceBZList.do',
    		type:'post',
    		contentType:'application/json',
    		data:JSON.stringify(obj),
    		async:true,
    		success:function(data){
    			 if(data.code==00){
    				userdata=JSON.parse( data.codeMessage );
    				for(k in userdata){
    					if(userdata[k].flag==1){
    						$('.bzhang').removeAttr('disabled')
    					}else{
    						$('.bzhang').attr('disabled','disabled')
    					}
    				}
    				 
    				dialog.ren({'cols':cols,'wh':wh,'userdata':userdata});
    			}else{
    				dialog.ren({'cols':cols,'wh':wh,'userdata':''});
    			}
    			
    		}
    	}); 
    
    }
     
    //点击并账
    $('.bzhang').click(function(){
    	var bzdt=$('#d1').val(),
	        trid=$('.chestate option:selected').val();
    	$.ajax({
     		url:'/fx/balanceBZSave.do',
     		type:'post',
     		contentType:'application/json',
     		data:JSON.stringify({'userKey':userKey,'bailBalanceBean':{'bzdt':bzdt,'trid':trid}}),
     		async:true,
     		success:function(data){
     			if(data.code==00){
     				getBailBalanceList({'startDate':bzdt,'tradeCode':trid});
     				dialog.choicedata(data.codeMessage,'balanrev');
     			}else{
     				dialog.choicedata(data.codeMessage,'balanrev');
     			}
     			
     		}
     	}); 
    })
    $('body',parent.document).on('click','.balanrev .sure',function(){
		//关闭选择一条数据;
	    $(this).closest('.zhezhao').remove();
	});
 
	
});
