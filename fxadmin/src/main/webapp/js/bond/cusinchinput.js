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
		ww=$(window).width()-475+"px";
		$('.boxtop').css('width',ww);
	    $('.currencypairs').css('height',wh);
		$('.boxtop').css('height',wh);
		$('#d1').val( dialog.today);
	//列参数;
    var cols = [
            { title:'币别对', name:'exnm',width:80,align:'left' },
            { title:'并账日期', name:'bzdt',width:80,align:'left' },
            { title:'左头寸', name:'left',width:100, align:'left'},
            { title:'右头寸', name:'righ',width:100, align:'left'},
            { title:'左并账金额', name:'lbzam',width:100, align:'left'},
            { title:'右并账金额', name:'rbzam',width:100, align:'left'},
            { title:'状态', name:'stcd',width:60, align:'left'},
            { title:'本地流水号', name:'lcno',width:80, align:'left'},
            { title:'渠道流水号', name:'trsn',width:200, align:'left'}   
    ];
    var currencypairs=['AUDCAD','AUDJPY','AUDUSD','CADJPY','CHFJPY','EURAUD','EURCAD','EURCHF','EURGBP','EURJPY','EURUSD','GBPAUD','GBPCAD','GBPCHF','GBPJPY','GBPUSD','USDCAD','USDCHF','USDJPY','XAUUSD'] 
    var html='';
    $.each(currencypairs,function(i,e){
        html+='<tr><td>'+e+'</td><td>左头寸:<input type="text" value="0"></input></td><td>右头寸:<input type="text" value="0"></input></td></tr>'
    	$('.currencypairs table').html(html);
    });
    $(".currencypairs").niceScroll({
		touchbehavior:false,
		cursorcolor:"#666",
		cursoropacitymax:0.7,
		cursorwidth:6,
		background:"#ccc",
		autohidemode:false,
		horizrailenabled:"horizrailenabled"
   });
    var startDate=$('#d1').val(),
        userKey=JSON.parse( sessionStorage.getItem('menu') ).userKey;
//    
    $('.query').click(function(){
    	startDate=$('#d1').val();
    	getBailBalanceList(startDate) 
    });
    $('.luru').click(function(){
       getBailBalanceList(startDate) 
       var n=0,m=0,arr=[],KondorVo;
	   $('.table tr').each(function(i,e){
		   var left=$.trim($(this).find('td').eq('1').find('input').val()),
		       right=$.trim($(this).find('td').eq('2').find('input').val());
		   if(left==''||right==''){
			   n++;
		   }else if(!/^[0-9]*$/.test(left)||!/^[0-9]*$/.test(right)){
			   n++;
		   }else if(left*right>0){
			   m++;
 	       }else{
 	    	   arr.push({
 	    		  'exnm':$(this).find('td').eq('0').text(),
 	    		  'left':left,
 	    		  'righ':right
 	    	   })
 	       }
	    })
	    KondorVo={'userKey':userKey,'startDate':startDate,'bailTouCunBeans':arr}
	    if(n>0){
	    	dialog.choicedata('左右头寸不能为空且只能为数字','cusinchinput')
	    }else if(m>0){
	        dialog.choicedata('左右头寸不能同时为正数和负数','cusinchinput')
	    }else{
	    	  //发送请求
	    	$.ajax({
	    		url:'/fx/toucunluru.do',
	    		type:'post',
	    		contentType:'application/json',
	    		data:JSON.stringify(KondorVo),
	    		async:true,
	    		success:function(data){
	    			if(data.code==00){
	    				dialog.choicedata(data.codeMessage,'cusinchinput') 
	    			}else{
	    				dialog.choicedata(data.codeMessage,'cusinchinput')  
	    			}
	    			
	    		}
	    	}); 
	    	   
	    }
    
    
    });
   
    
    //请求列表数据；
    function getBailBalanceList(obj){
    	$.ajax({
    		url:'/fx/queryBailTouCunList.do',
    		type:'post',
    		contentType:'application/json',
    		//data:JSON.stringify(obj),
    		data:obj,
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
    $('body',parent.document).on('click','.cusinchinput .sure',function(){
		$(this).closest('.zhezhao').remove();
	});
//    function judgeVal(ipt1Val,ipt2Val){
//    	 ipt1Val != "" && ipt2Val != "" ? ipt1Val > 0 ? ipt2Val > 0 ? console.log("同正") : console.log("符合") : ipt2Val < 0 ? console.log("同负") : console.log("符合") : console.log("内容不能为空")
//    	
//    }
	
});
