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
		ww=$(window).width()-35+"px";
		$('.boxtop').css('width',ww);
		$('.boxtop').css('height',wh);
		$('#d1').val( dialog.today);
	//列参数;
    var cols = [
            { title:'币别对', name:'exnm',width:80,align:'left' },
            { title:'并账日期', name:'bzdt',width:80,align:'right' },
            { title:'左头寸', name:'left',width:80, align:'right'},
            { title:'右头寸', name:'righ',width:80, align:'right'},
            { title:'左并账金额', name:'lbzam',width:90, align:'right'},
            { title:'右并账金额', name:'rbzam',width:90, align:'right'},
            { title:'状态', name:'stcd',width:60, align:'center'},
            { title:'本地流水号', name:'lcno',width:80, align:'left'},
            { title:'渠道流水号', name:'trsn',width:220, align:'left'},
    ];
     
    var startDate=$('#d1').val();
	   getBailBalanceList(startDate) 
    //点击查询
    $('.query').click(function(){
    	startDate=$('#d1').val();
    	getBailBalanceList(startDate) 
    });
    //点击并账
	$('.entering').click(function(){
		 $.ajax({
     		url:'/fx/checkInput.do',
     		type:'post',
     		contentType:'application/json',
     		data:JSON.stringify({'userKey':userKey,'bailTouCunBean':{'bzdt':startDate}}),
     		async:true,
     		success:function(data){
     			if(data.code==00){
     				getBailBalanceList(startDate) 
     				dialog.choicedata(data.codeMessage,'cusinchrev');
     			}else{
     				dialog.choicedata(data.codeMessage,'cusinchrev');
     			}
     			
     		}
     	}); 
		
	})
     $('body',parent.document).on('click','.cusinchrev .sure',function(){
		//关闭选择一条数据;
	    $(this).closest('.zhezhao').remove();
	});
    //请求列表数据；
    function getBailBalanceList(obj){
    	$.ajax({
    		url:'/fx/queryBailTouCunBZList.do',
    		type:'post',
    		contentType:'application/json',
    		//data:JSON.stringify(obj),
    		data:obj,
    		async:true,
    		success:function(data){
    			if(data.code==00){
    				userdata=JSON.parse( data.codeMessage );
    				for(k in userdata){
    					 if(userdata[k].flag==1){
    						$('.entering').removeAttr('disabled')
    					}else{
    						$('.entering').attr('disabled','disabled')
    					}
    				}
    				dialog.ren({'cols':cols,'wh':wh,'userdata':userdata});
    			}else if(data.code==02){
    				
    			}
    			
    		}
    	}); 
    
    }
	
});
