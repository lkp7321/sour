/*积存金-签约流水查询--✔*/
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
	var wh=$(window).height()-230+"px",
		ww=$(window).width()-250+"px";
	var product=sessionStorage.getItem('product'),
		userkey=JSON.parse( sessionStorage.getItem('menu') ).userKey,
		tile=$('title').text();
	$('.boxtop').css('width',ww);
	$('.boxtop').css('height',wh);
	$('#page').css('width',ww);
	//列参数
    var  cols=[
		{ title:'交易流水号', name:'LCNO' ,width:100, align:'left' },
		{ title:'机构', name:'OGNM' ,width:150, align:'left'},
		{ title:'卡号', name:'CUAC' ,width:150, align:'left'},
		{ title:'客户号', name:'CUNO' ,width:150, align:'left'},
		{ title:'交易日期', name:'TRDT' ,width:80, align:'right'},
		{ title:'交易时间', name:'TRTM' ,width:120, align:'right'},
		{ title:'交易状态', name:'STCD' ,width:120, align:'center'},
		{ title:'交易类型', name:'TRFG' ,width:100, align:'center'},
		{ title:'错误码', name:'ERCD' ,width:80, align:'left'}
    ];
    var juesedata=false,jigoudata=false,jueseda,jigouda,listnumtotal;
    $('.dealdate #d1').val(dialog.today());
    $('.dealdate #d2').val(dialog.today());
    
    var num=0;
    //请求机构名称一
    getList('comboxA.do','#comboxA');  
    $('#comboxA').change(function(){
    	comaogcd=$('#comboxA option:selected').attr('value');
    	getDeallists('comboxB.do',comaogcd,'#comboxB');
    });
    $('#tranCode').click(function(){
    	var valt=$(this).find('option:selected').attr('value');
    	if(valt==2){
    		$('#tranCode1').show();
    	}else{
    		$('#tranCode1').hide();
    	}
    });
    //请求买币种
    $.ajax({
		url:'/fx/getByExnm.do',
		type:'post',
		async:true,
		contentType:'application/json',
		dataType:'json',
		data:userkey,
		success:function(data){
		    var str='<option value="all">所有</option>';
		    if(data.code==01){
		    	var data= data.codeMessage;
		    	 $.each(data,function(i,v){
				    str+='<option>'+v+'</option>'
				})
			    $('#byExnm').html(str);
		    }
		}
    });
   var 	comdata3='',
   		comdata1=$('#tranCode option:selected').attr('value'),
        byexnm=$('#byExnm option:selected').val(),
        comaogcd=$('#comboxA option:selected').val(),
        combogcd=$('#comboxB option:selected').val(),
        strcuac=$('.cardnum').val(),
        trdtbegin=$('.dealdate #d1').val(),
        trdtend=$('.dealdate #d2').val(),
        strcuno=$('.cusnum').val();
   		
		bsVo={'comdata1':comdata1,'comdata3':comdata3,'strcuno':strcuno,'strcuac':strcuac,'trdtbegin':trdtbegin,'trdtend':trdtend,'comaogcd':comaogcd,'combogcd':combogcd,'pageNo':1,'pageSize':10}
   		getAllTrans( bsVo);
   		//点击分页;
   	    $('.boxtop').on('click','.first',function(){
   	    	var Nopage=$('.Nopage').text()*1;
   	 	    if(Nopage>1){
   	 	    	Nopage=1;
   	 	    	bsVo={'comdata1':comdata1,'comdata3':comdata3,'strcuno':strcuno,'strcuac':strcuac,'trdtbegin':trdtbegin,'trdtend':trdtend,'comaogcd':comaogcd,'combogcd':combogcd,'pageNo':1,'pageSize':10}
   	 	    	getAllTrans( bsVo);
   	 	    }
   	    });
   		$('.boxtop').on('click','.prev',function(){
   		    var Nopage=$('.Nopage').text()*1;
   		    if(Nopage>1){
   		    	Nopage=Nopage-1;
	   			bsVo={'comdata1':comdata1,'comdata3':comdata3,'strcuno':strcuno,'strcuac':strcuac,'trdtbegin':trdtbegin,'trdtend':trdtend,'comaogcd':comaogcd,'combogcd':combogcd,'pageNo':1,'pageSize':10}
	   			getAllTrans( bsVo);
   		    }
   		});
   		$('.boxtop').on('click','.next',function(){
   			var Nopage=$('.Nopage').text()*1,
   				Totalpage=$('.Totalpage').text()*1;
   		    if(Nopage<Totalpage){
   		    	Nopage=Nopage*1+1;
   		    	bsVo={'comdata1':comdata1,'comdata3':comdata3,'strcuno':strcuno,'strcuac':strcuac,'trdtbegin':trdtbegin,'trdtend':trdtend,'comaogcd':comaogcd,'combogcd':combogcd,'pageNo':1,'pageSize':10}
   		    	getAllTrans( bsVo);
   		    }
   		});
   		$('.boxtop').on('click','.last',function(){
   			var Nopage=$('.Nopage').text()*1,
   			Totalpage=$('.Totalpage').text()*1;
   		    if(Nopage<Totalpage){
   		    	Nopage=Totalpage;
   		    	  bsVo={'comdata1':comdata1,'comdata3':comdata3,'strcuno':strcuno,'strcuac':strcuac,'trdtbegin':trdtbegin,'trdtend':trdtend,'comaogcd':comaogcd,'combogcd':combogcd,'pageNo':1,'pageSize':10}
   		    	  getAllTrans( bsVo);
   		    }
   		});
    $('.search').click(function(){    	
    	comdata1=$('#tranCode option:selected').attr('value'),
    	comdata3=$('#tranCode1 option:selected').attr('value'),
        byexnm=$('#byExnm option:selected').val(),  //
        comaogcd=$('#comboxA option:selected').val(),
        combogcd=$('#comboxB option:selected').val(),
        strcuac=$('.cardnum').val(),
        trdtbegin=$('.dealdate #d1').val(),
        trdtend=$('.dealdate #d2').val(),
        strcuno=$('.cusnum').val();
	    bsVo={'comdata1':comdata1,'comdata3':comdata3,'strcuno':strcuno,'strcuac':strcuac,'trdtbegin':trdtbegin,'trdtend':trdtend,'comaogcd':comaogcd,'combogcd':combogcd,'pageNo':1,'pageSize':10}
    	getAllTrans( bsVo );
	    renpage();
    })
    //封装请求列表
    function getAllTrans( obj ){
    	 $.ajax({
    		url:'/fx/accum/selectAccumuRegTrade.do',
    		type:'post',
    		contentType:'application/json',
    		data:JSON.stringify(obj ),
    		async:false,
    		success:function(data){
    			num++;
    			if(data.code==01){
    				userdata= data.codeMessage;
    			    dialog.ren({'cols':cols,'wh':wh,'userdata':userdata.list});
    			    listnumtotal=userdata.total;
//    			    $('.page').remove();
//    			    $('.boxtop').append('<div class="page"><span class="first">首页</span><span class="prev">上一页</span><span class="next">下一页</span><span class="last">末页</span><span class="nopage">第<b class="Nopage">'+userdata.pageNum+'</b>页/共<b class="Totalpage">'+userdata.pages+'</b>页</span><span class="totalpage">共<b class="total">'+userdata.total+'</b>条记录</span></div>');
    			}else{
    				dialog.ren({'cols':cols,'wh':wh,'userdata':''});
    				if(num>1){
    					dialog.choicedata(data.codeMessage,'dealwater');
    				}
    			}
    		}
    	});
    };
    renpage();
    //封装get请求到的option
    function getList(url,dom){
    	 $.ajax({
    			url:url,
    			type:'get',
    			async:false,
    			data:userkey,
    			dataType:'json',
    			success:function(data){
    				 if(data.code==01){
    					var listdata=data.codeMessage,str;
    					var str='';
    					for(var i in listdata){
    						 str+='<option value='+listdata[i].ogcd+'>'+listdata[i].ognm+'</option>'
    					}
    					$(''+dom+'').html(str);
    					 var comaogcd=$('#comboxA option:selected').attr('value');
    					 getDeallists('comboxB.do',comaogcd,'#comboxB');
    				  }else{
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
			async:true,
			dataType:'json',
			data:obj,
 			contentType:'application/json;charset=utf-8',
 			beforeSend:function(){
 				 str='<option value="all">所有</option>';
 			},
 			success:function(data){
				 if(data.code==01){
					var listdata=data.codeMessage;
					for(var i in listdata){
						str+='<option value='+listdata[i].ogcd+'>'+listdata[i].ognm+'</option>'
					}
					$(''+dom+'').html(str);
				   }else{
					 //获取失败
				}
			}
		});
    }
    $('body',parent.document).on('click','.dealwater .sure',function(){
		$(this).closest('.zhezhao').remove();
	});
	//导出excel;
	 $('#logon').click(function(){
		 $('#fornm input').remove();
		  var str='<input type="text" name = "userKey" value='+userkey+'><input type="text" name = "tableName" value='+tile+'>'+
		  		'<input type="text" name = "trancode" value='+$('#tranCode option:selected').attr('trcd')+'><input type="text" name = "trdtbegin" value='+$('.showtime1').text()+'>'+
		  		'<input type="text" name = "trdtend" value='+$('.showtime2').text()+'><input type="text" name = "comaogcd" value='+$('#comboxA option:selected').val()+'>'+
		  		'<input type="text" name = "combogcd" value='+$('#comboxB option:selected').val()+'><input type="text" name = "byexnm" value='+$('#byExnm option:selected').val()+'>'+
		  		'<input type="text" name = "strcuac" value='+$('.cardnum').val()+'>';
		  for(var i in cols){
			  str+='<input type="text" name ="tableList['+i+'].name" value='+cols[i].title+'><input type="text" name ="tableList['+i+'].value" value='+cols[i].name+'>'
		  }
		  $('#fornm').append( str );
		  $('#fornm').submit();
    });
	 function renpage(){
			layui.use(['laypage', 'layer'], function(){
				  var laypage = layui.laypage,layer = layui.layer;
				  //完整功能
				  laypage.render({
				    elem: 'page'
				    ,count:listnumtotal
				    ,theme: '#5a8bff'
				    ,layout: ['count', 'prev', 'page', 'next', 'limit', 'skip']
				    ,jump: function(obj,first){
				    	if(!first){ //点击跳页触发函数自身，并传递当前页：obj.curr
				    		getAllTrans({
				    			'comdata1':$('#tranCode option:selected').attr('value'),
				    			'comdata3':$('#tranCode1 option:selected').attr('value'),
				    			'strcuno':$('.cusnum').val(),
				    			'strcuac':$('.cardnum').val(),
				    			'trdtbegin':$('.dealdate #d1').val(),
				    			'trdtend':$('.dealdate #d2').val(),
				    			'comaogcd':$('#comboxA option:selected').val(),
				    			'combogcd':$('#comboxB option:selected').val(),
				    			'pageSize':$('.layui-laypage-limits select option:selected').attr('value')*1,
		    				    'pageNo':obj.curr
				    			}		
				    		);
				    	}	
				    }
				  });
			});
		}
})



