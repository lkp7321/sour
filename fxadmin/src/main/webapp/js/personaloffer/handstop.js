/*实盘-手工停牌 && p999-手工停牌*/
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
require(['jquery','mmGrid','niceScroll','dialog'],function($,mmGrid,niceScroll,dialog){
	var wh=$(window).height()-190+"px",
	    ww=$(window).width()-250+"px";

      $('.boxtop').css('width',ww);
      $('.boxtop').css('height',wh);
	
	//列参数;
    var cols = [
        { title:'停牌标志', name:'stfg' ,width:100, align:'center' },
        { title:'停牌器ID', name:'stid' ,width:100, align:'left'},
        { title:'价格类型', name:'tpnm' ,width:150, align:'center'},
        { title:'期限', name:'term' ,width:80, align:'right'},
        { title:'停牌器名称', name:'stnm' ,width:120, align:'left'},
        { title:'货币对名称', name:'exnm' ,width:100, align:'left'},
    ];
    var usnm=sessionStorage.getItem('usnm'),
        product=sessionStorage.getItem('product'),str,
        loginuser={'usnm':usnm,'prod':product},
        userKey=JSON.parse( sessionStorage.getItem('menu') ).userKey;
    //报价平台  产品管理 手工停牌
    if( product=='P999'){
//---------------------------不明来路的接口---------------------------------------------
//    	 str='<option value="所有" selected="selected">所有</option>';
//		 $.ajax({
//				url:'/fx/price/getMarketinfoList.do',
//				type:'post',
//				async:true,
//				dataType:'json',
//				data:userKey,
//				contentType:'application/json;charset=utf-8',
//				success:function(data){
//					var handdata=JSON.parse( data.codeMessage );
//					if(data.code==01){
//						for(var i in handdata){
//							str+='<option value='+handdata[i].MKID +'>'+handdata[i].MKNM+'</option>'
//						}
//						$('.marketlist select').html(str);
//					}else{
//						 
//					}
//				}
//			});
   
        //请求产品列表
	    $.ajax({
			url:'/fx/pdtCom.do',
			type:'get',
			async:false,
			dataType:'json',
		    contentType:'application/json;charset=utf-8',
			success:function(data){
				var handdata=JSON.parse( data.codeMessage ),str='';
				if(data.code==00){
					for(var i in handdata){
						str+='<option value='+handdata[i].PTID +'>'+handdata[i].PTNM+'</option>'
					}
					$('.product').html('<select onmousedown="if(this.options.length>6){this.size=7}" onblur="this.size=0" onchange="this.size=0">'+str+'</select>');
				}else if(data.code==01){
					 
				}
			}
		});
	    var prod=$('.product select option:selected').text(),
	        ptid=$('.product select option:selected').val(),
	        excd=$('.currencylist select option:selected').val();
	    //请求数据列表
	    getHandstop({'userKey':userKey,'prod':ptid},'selectCmmStoper.do');
	    //根据产品请求币种
	    getExnmsList({'userKey':userKey,'prod':prod});
        //根据产品的变化请求币种和列表
	    $('.product select').change(function(){
	    	prod=$('.product select option:selected').text();
	    	ptid=$('.product select option:selected').val();
	    	getExnmsList({'userKey':userKey,'prod':prod});
	    	getHandstop({'userKey':userKey,'prod':ptid},'selectCmmStoper.do');
	    })
	    //根据币种的变化请求列表
	    $('.currencylist select').change(function(){
	    	excd=$('.currencylist select option:selected').val(); 
	    	if(excd=='所有'){
	    		getHandstop({'userKey':userKey,'prod':ptid},'selectCmmStoper.do');
	    	}else{
	    		getHandstop({'userKey':userKey,'prod':ptid,'excd':excd},'selectHandcaftStop.do');
	    	 
	    	}
       })
         
	    
    }else{
    	//请求币种列表
    	getExnmsList({'userKey':userKey});
    	 //请求列表
        getHandstop({'userKey':userKey},'selectCmmStoper.do');
      //根据币种的选中值的改变请求列表
        $('.currencylist select').change(function(){
        	var excd=$(".currencylist select option:selected").val();
        	if(excd=='所有'){
        		getHandstop({'userKey':userKey},'selectCmmStoper.do');
        	}else{
        		getHandstop({'userKey':userKey,'excd':excd},'selectHandcaftStop.do');
        	}
        })
    }
	//封装请求币种列表
    function getExnmsList(obj){
	   $.ajax({
			url:'/fx/queryExnms.do',
			type:'post',
			async:false,
			dataType:'json',
			data:JSON.stringify(obj),
			contentType:'application/json;charset=utf-8',
			beforeSend:function(){
			     str='<option value="所有" selected="selected">所有</option>';
			},
			success:function(data){
				var handdata=JSON.parse( data.codeMessage );
				if(data.code==00){
					for(var i in handdata){
						str+='<option value='+handdata[i].cytp +'>'+handdata[i].cyen+'</option>'
					}
					$('.currencylist select').html(str);
				}else if(data.code==01){
					 
				}
			}
		});
    }
 
   
    
   //启用框停用框弹出
	$('.hands_open,.hands_stop').click(function(){
		var a=0,arr=[],priceVo,
		    prod=$('.product select option:selected').val();
		$('.box tr').each(function(){
			if( $(this).find('input[type="checkbox"]').prop('checked')==true){
				a++;
			}
		})
		if(a==0){
			dialog.choicedata('请先勾选一条数据！','handstop');
		}else{
			 $('.box tr').each(function(i,v){
				 if( $(v).find('input[type="checkbox"]').prop('checked') ){
				  arr.push({
					  'stid':$(v).find('td').eq(2).find('span').text(),
					  'exnm':$(v).find('td').eq(6).find('span').text()
			      });
				}
			});
		  var usfg=$(this).text(),url;
		   if(usfg=='开牌'){
			   url='openChannel.do'
		   }else{
			   url='closeChannel.do'
		   }
		   if(product=='P999'){
			   priceVo={'userKey':userKey,'prod':prod,'pdtStopers':arr}; 
		   }else{
			   priceVo={'userKey':userKey,'pdtStopers':arr}; 
		   }
		    	 
		    $.ajax({
		    	url:url,
		        type:'post',
		    	contentType:'application/json',
		    	data:JSON.stringify(priceVo),
		    	async:false,
		    	dataType:'json',
		    	success:function(data){
		    		if(data.code==00){
		    			excd=$(".currencylist select option:selected").val();
		    			if(product=="P999"){
		    				if(excd=='所有'){
		    		    		getHandstop({'userKey':userKey,'prod':ptid},'selectCmmStoper.do');
		    		    	}else{
		    		    		getHandstop({'userKey':userKey,'prod':ptid,'excd':excd},'selectHandcaftStop.do');
		    		    	 
		    		    	}
		    			}else{
		    				if(excd=='所有'){
			            		getHandstop({'userKey':userKey},'selectCmmStoper.do');
			            	}else{
			            		getHandstop({'userKey':userKey,'excd':excd},'selectHandcaftStop.do');
			            	}	
		    			}
		    			
		    			dialog.choicedata(data.codeMessage,'handstop');
		    		}else if(data.code==01){
		    			//异常 
		    		} 
		    	  }
		      });  	
		}
	});
  
	
	
	$('body',parent.document).on('click','.handstop .sure',function(){
		//关闭选择一条数据;
	   $('.zhezhao',parent.document).remove();
	});
	//封装请求列表
	function getHandstop(obj,url){
		$.ajax({
	    	url:url,
	        type:'post',
	        data:JSON.stringify(obj),
	    	contentType:'application/json',
	    	async:false,
	    	dataType:'json',
	    	success:function(data){
	    		if(data.code==00){
	    			 dialog.ren({'cols':cols,'wh':wh,'userdata':JSON.parse(data.codeMessage),'checked':true});
	    		}else if(data.code==01){
	    			dialog.ren({'cols':cols,'wh':wh,'userdata':'','checked':true});
	    		}else if(data.code==02){
	    			//查询异常
	    		}
	    	  }
	      });
		
	};
	
 
 
})