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
		dialog:'dialog'
	}
});
//问题:  数据少一条；
require(['jquery','mmGrid','niceScroll','dialog'],function($,mmGrid,niceScroll,dialog){
	var wh=$(window).height()-190+"px",
	    ww=$(window).width()-260+"px";
		$('.boxtop').css('width',ww);
		$('.boxtop').css('height',wh);
       var cols = [
             { title:'货币对', name:'exnm' ,width:100, align:'left' },
             { title:'买入价', name:'neby' ,width:100, align:'right'},
             { title:'中间价', name:'nemd' ,width:100, align:'right'},
             { title:'卖出价', name:'nesl' ,width:100, align:'right'},
             { title:'更新时间', name:'mdtm' ,width:200, align:'right'},
             { title:'报价状态', name:'stfg' ,width:100, align:'center'}
         ];
       var usnm=sessionStorage.getItem('usnm'),
		   product=sessionStorage.getItem('product'),
		   userkey=JSON.parse( sessionStorage.getItem('menu') ).userKey,
		   loginuser={'usnm':usnm,'prod':product},clickNum=0;
      //请求列表
     getAllPrice(userkey);	 
      
     //获取系统状态
     getStat('getSysStat.do','totalstate');
    //获取价格源校验器状态
     getPriceStat(); 
     //刷新价格源
     $('.refpri button').click(function(){
    	 clickNum++;
    	 if(clickNum>0){
    		$('.refplate button').prop('disabled','').css('background','#5a8bff')
         } 
    	 $.ajax({
 	 		url:'/fx/refshPrice.do',
 	 		type:'post',
 	 		contentType:'application/json',
 	 		data:userkey,
 	 		async:false,
 	 		success:function(data){
 	 			if(data.code==01){
	 				 getAllPrice(userkey);
	    			 dialog.choicedata(data.codeMessage,'pricevalidator'); 
	    			 getPriceStat(); 
	 			 }else{
	    			 //刷新失败
	 				 dialog.choicedata(data.codeMessage,'pricevalidator');	
	    	     }  
 	 		}
 	 	});
     }); 
     //刷新外汇实盘 
     $('.refplate button').click(function(){
    	 clickNum++;
    	 if(clickNum>0){
    		$('.star button').prop('disabled','').css('background','#5a8bff')
         } 
    	 $.ajax({
  	 		url:'/fx/refshPtPrice.do',
  	 		type:'post',
  	 		contentType:'application/json',
  	 		data:userkey,
  	 		async:true,
  	 		success:function(data){
  	 			if(data.code==01){
 	 				 getAllPrice(userkey);
 	    			 dialog.choicedata(data.codeMessage,'pricevalidator'); 
 	 			 }else{
 	    			 //刷新失败
 	 				 dialog.choicedata(data.codeMessage,'pricevalidator');	
 	    	     }  
  	 		}
  	 	});
      }); 
      //启动关闭交易
     $('.closepri button,.star button').click(function(){
    	 var btntext=$(this).text(),url;
    	 if(btntext=='关闭交易'){
    		 url='closeJiaoYi.do';
    		 clickNum++;
        	 if(clickNum>0){
        		$('.refplate button').prop('disabled','').css('background','#5a8bff')
             } 
    	 }else{
    		 url='openJiaoYi.do';
    	 }
    	 $.ajax({
  	 		url:url,
  	 		type:'post',
  	 		contentType:'application/json',
  	 		data:userkey,
  	 		async:true,
  	 		success:function(data){
  	 		   //获取系统状态
  	 	       getStat('getSysStat.do','totalstate');
  	 	       //获取价格源校验器状态
  	 	       getPriceStat();  
  	 		}
  	 	});
      }); 
     //封装请求列表
     function getAllPrice(obj){
    	//请求数据;
        $.ajax({
    		url:'/fx/getAllPriceCheck.do',
    		type:'post',
    		contentType:'application/json',
    		data:obj,
    		async:true,
    		success:function(data){
    			 if(data.code==01){
    				userdata=data.codeMessage;
    				dialog.ren({'cols':cols,'wh':wh,'userdata':userdata});
    			}else if(data.code==00){
    				dialog.ren({'cols':cols,'wh':wh,'userdata':''});
    			}
    		}
    	});
    }
   // 封装请求系统总控状态
     function getStat(url,dom){
    	 $.ajax({
    	 		url:url,
    	 		type:'post',
    	 		contentType:'application/json',
    	 		data:userkey,
    	 		async:true,
    	 		success:function(data){
    	 			if(data.code==01){
      	 				$('.'+dom+' span').text(data.codeMessage);
      	 			}else{
      	 			   dialog.choicedata(data.codeMessage,'pricevalidator');	
      	    	    } 
    	 		 }
    	 	});
     }
     //封装获取价格校验器状态
     function getPriceStat(){
    	 $.ajax({
    	   		url:'/fx/getPriceStat.do',
    	   		type:'get',
    	   		contentType:'application/json',
    	   		async:false,
    	   		success:function(data){
    	   			if(data.code==01){
      	 				$('.pricestate span').text(data.codeMessage);
      	 			}else{
      	 			   dialog.choicedata(data.codeMessage,'pricevalidator');	
      	    	    }  
    	   		}
    	 });
     }
	$('body',parent.document).on('click','.pricevalidator .sure',function(){
		$(this).closest('.zhezhao').remove();
	});
});

