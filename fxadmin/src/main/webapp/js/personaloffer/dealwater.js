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
	var wh=$(window).height()-200+"px",
	ww=$(window).width()-260+"px";;

	$('.boxtop').css('width',ww);
	$('.boxtop').css('height',wh);
	//列参数
    var cols = [
        { title:'交易流水号', name:'lcno' ,width:100, align:'left' },
        { title:'机构', name:'ognm' ,width:150, align:'left'},
        { title:'卡号', name:'cuac' ,width:150, align:'left'},
        { title:'交易日期', name:'trdt' ,width:80, align:'left'},
        { title:'交易时间', name:'trtm' ,width:120, align:'left'},
        { title:'交易类型', name:'trds' ,width:100, align:'left'},
        { title:'买入币种', name:'bynm' ,width:80, align:'left'},
        { title:'卖出币种', name:'slnm' ,width:100, align:'left'},
        { title:'容忍点差', name:'rrdt' ,width:80, align:'left'},
        { title:'买入金额', name:'byam',width:80, align:'left'},
        { title:'成交汇率', name:'slam' ,width:80, align:'left'},
        { title:'卖出金额', name:'expc' ,width:80, align:'left'},
        { title:'发生额（转账）', name:'amut' ,width:80, align:'left'},
        { title:'优惠点数', name:'fvda' ,width:80, align:'left'},
        { title:'分行损益', name:'brsy' ,width:80, align:'left'},
        { title:'折美元金额', name:'usam' ,width:80, align:'left'},
        { title:'交易状态', name:'stcd' ,width:80, align:'left'}
        
    ];
    $('.dealdate #d1').val(dialog.today());
    $('.dealdate #d2').val(dialog.today());
    var num=0;
    //请求机构名称一
    getList('comboxA.do','#comboxA')
    //请求机构名称二
    var comaogcd=$('#comboxA option:selected').val();
    getDeallists('comboxB.do',comaogcd,'#comboxB')
    $('#comboxA').change(function(){
    	comaogcd=$('#comboxA option:selected').val();
    	getDeallists('comboxB.do',comaogcd,'#comboxB')
    })
   
    //请求查询条件
     $.ajax({
    			url:'/fx/getTranCode.do',
    			type:'get',
    			async:false,
    			dataType:'json',
    			success:function(data){
    				 var str='<option value="all">所有</option>';
    				 if(data.code==01){
    					 var data=JSON.parse(data.codeMessage);
    					 for(var i in data){
       					     str+='<option value='+data[i].TRCD+'>'+data[i].TRDS+'</option>'
       				     }
       				     $('#tranCode').html(str); 
    				 }
    			}
     });
    
    //请求币种
    $.ajax({
		url:'/fx/getByExnm.do',
		type:'get',
		async:false,
		dataType:'json',
		success:function(data){
		    var str='<option value="all">所有</option>';
		    if(data.code==01){
		    	var data=JSON.parse(data.codeMessage);
		    	 $.each(data,function(i,v){
				    str+='<option>'+v+'</option>'
				})
			    $('#byExnm').html(str);
		    }
		    
		}
    });
    
    var trancode=$('#tranCode option:selected').val(),
        byexnm=$('#byExnm option:selected').val(),
        comaogcd=$('#comboxA option:selected').val(),
        combogcd=$('#comboxB option:selected').val(),
        strcuac=$('.cardnum').val(),
        trdtbegin=$('.dealdate #d1').val(),
        trdtend=$('.dealdate #d2').val(),
        bsVo={'trancode':trancode,'strcuac':strcuac,'trdtbegin':trdtbegin,'trdtend':trdtend,'byexnm':byexnm,'comaogcd':comaogcd,'combogcd':combogcd}
    getAllTrans();
    $('.search').click(function(){
    	trancode=$('#tranCode option:selected').val(),
        byexnm=$('#byExnm option:selected').val(),
        comaogcd=$('#comboxA option:selected').val(),
        combogcd=$('#comboxB option:selected').val(),
        strcuac=$('.cardnum').val(),
        trdtbegin=$('.dealdate #d1').val(),
        trdtend=$('.dealdate #d2').val(),
        bsVo={'trancode':trancode,'strcuac':strcuac,'trdtbegin':trdtbegin,'trdtend':trdtend,'byexnm':byexnm,'comaogcd':comaogcd,'combogcd':combogcd}
    	getAllTrans();
    })
    //封装请求列表
    function getAllTrans(){
    	 $.ajax({
    		url:'/fx/getAllTransList.do',
    		type:'post',
    		contentType:'application/json',
    		data:JSON.stringify(bsVo),
    		async:true,
    		success:function(data){
    			num++;
    			if(data.code==01){
    				userdata=JSON.parse( data.codeMessage );
    			    dialog.ren({'cols':cols,'wh':wh,'userdata':userdata});
    			}else if(data.code==00){
    				dialog.ren({'cols':cols,'wh':wh,'userdata':''});
    				if(num>1){
    					dialog.choicedata(data.codeMessage,'dealwater');
    				}
    				
    			}
    		}
    	});
    };
    //封装get请求到的option
    function getList(url,dom){
    	 $.ajax({
    			url:url,
    			type:'get',
    			async:false,
    			dataType:'json',
    			success:function(data){
    				 if(data.code==01){
    					var listdata=JSON.parse(data.codeMessage),str;
    					for(var i in listdata){
    						 str+='<option value='+listdata[i].ogcd+'>'+listdata[i].ognm+'</option>'
    					}
    					$(''+dom+'').html(str);
    				  }else if(data.code==02){
    					 //获取机构失败
    				}
    			}
    		});
    }
    //请求列表option
    function getDeallists(url,obj,dom){
    	$.ajax({
			url:url,
			type:'post',
			async:false,
			dataType:'json',
			data:obj,
 			contentType:'application/json;charset=utf-8',
 			beforeSend:function(){
 				 str='<option value="all">所有</option>';
 			},
 			success:function(data){
				 if(data.code==01){
					var listdata=JSON.parse(data.codeMessage);
					for(var i in listdata){
						str+='<option value='+listdata[i].ogcd+'>'+listdata[i].ognm+'</option>'
					}
					$(''+dom+'').html(str);
				   }else if(data.code==02){
					 //获取失败
				}
			}
		});
    }
    $('body',parent.document).on('click','.dealwater .sure',function(){
		$(this).closest('.zhezhao').remove();
	});
/*----------------快速搜索功能的实现------------------------*/
	$('.review_serbtn').click(function(){
		var txt=$('.review_seript').val();
		  dialog.serchData(txt);
    });


})

