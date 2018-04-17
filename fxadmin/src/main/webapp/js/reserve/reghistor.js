/*积存金-定期申购历史价格查询*/
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
	ww=$(window).width()-250+"px";
	var product=sessionStorage.getItem('product'),
		userkey=JSON.parse( sessionStorage.getItem('menu') ).userKey,
		tit=$('title').text();
	$('.boxtop').css('width',ww);
	$('.boxtop').css('height',wh);
	$('#page').css('width',ww);
	//列参数
    var cols = [
        { title:'交易日期', name:'TRDT' ,width:100, align:'right' },
        { title:'交易时间', name:'TRTM' ,width:100, align:'right' },
        { title:'币别对名称', name:'EXNM' ,width:150, align:'left'},
        { title:'定期申购价格', name:'TCSL' ,width:80, align:'right'},
        { title:'价格序列号', name:'SEQU' ,width:120, align:'right'}
    ];
    var juesedata=false,jigoudata=false,jueseda,jigouda,listnumtotal;
    $('.dealdate #d1,.dealdate #d2').val(dialog.today());
    
    var num=0;

    /*//请求买币种
    $.ajax({
		url:'/fx/selbyexnm.do',
		type:'post',
		async:true,
		dataType:'json',
		data:product,
		success:function(data){
		    var str='<option value="all">所有</option>';
		    if(data.code==00){
		    	var data=JSON.parse(data.codeMessage);
		    	 $.each(data,function(i,v){
				    str+='<option>'+v+'</option>'
				})
			    $('#byExnm').html(str);
		    }
		}
    });*/
   var 	comdata3='',
   		comdata1=$('#tranCode option:selected').attr('value'),
        strcuac=$('.cardnum').val(),
        trdtbegin=$('.dealdate #d1').val(),
        trdtend=$('.dealdate #d2').val();
	    bsVo={'trdtbegin':trdtbegin,'trdtend':trdtend,'pageNo':1,'pageSize':10};
   		getAllTrans( bsVo);
   		renpage();
   
    $('.search').click(function(){    	
    	comdata1=$('#tranCode option:selected').attr('value'),
    	comdata3=$('#tranCode1 option:selected').attr('value'),
        strcuac=$('.cardnum').val(),
        trdtbegin=$('.dealdate #d1').val(),
        trdtend=$('.dealdate #d2').val();
    	bsVo={'trdtbegin':trdtbegin,'trdtend':trdtend,'pageNo':1,'pageSize':10};
    	getAllTrans( bsVo );
    	renpage();
    });
    //封装请求列表;
    function getAllTrans( obj ){
    	 $.ajax({
    		url:'/fx/accum/selectAccumuHisPrice.do',
    		type:'post',
    		contentType:'application/json',
    		data:JSON.stringify(obj ),
    		async:false,
    		success:function(data){
    			num++;
    			if(data.code==00){
    				userdata=data.codeMessage;
    			    dialog.ren({'cols':cols,'wh':wh,'userdata':userdata.list});
    			    listnumtotal=userdata.total;
//    			    $('.page').remove();
//    				$('.boxtop').append('<div class="page"><span class="first">首页</span><span class="prev">上一页</span><span class="next">下一页</span><span class="last">末页</span><span class="nopage">第<b class="Nopage">'+userdata.pageNum+'</b>页/共<b class="Totalpage">'+userdata.pages+'</b>页</span><span class="totalpage">共<b class="total">'+userdata.total+'</b>条记录</span></div>')
    			}else{
    				dialog.ren({'cols':cols,'wh':wh,'userdata':''});
    				if(num>1){
    					dialog.choicedata(data.codeMessage,'dealwater');
    				}
    			}
    		}
    	});
    };
   /* //封装get请求到的option
    function getList(url,dom){
    	 $.ajax({
    			url:url,
    			type:'get',
    			async:false,
    			dataType:'json',
    			success:function(data){
    				 if(data.code==00){
    					var listdata=JSON.parse(data.codeMessage),str;
    					for(var i in listdata){
    						 str+='<option value='+listdata[i].OGCD+'>'+listdata[i].OGNM+'</option>'
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
				 if(data.code==00){
					var listdata=JSON.parse(data.codeMessage);
					for(var i in listdata){
						str+='<option value='+listdata[i].OGCD+'>'+listdata[i].OGNM+'</option>'
					}
					$(''+dom+'').html(str);
				   }else if(data.code==02){
					 //获取失败
				}
			}
		});
    }*/
    $('body',parent.document).on('click','.dealwater .sure',function(){
		$(this).closest('.zhezhao').remove();
	});
    //导出excel;
    $('#logon').click(function(){
	  $('#fornm input').remove();

	  var str='<input type="text" name = "userKey" value='+userkey+'><input type="text" name = "tableName" value='+tit+'>'+
	  		  '<input type="text" name="trdtbegin" value='+$('.dealdate #d1').val()+'><input type="text" name = "trdtend" value='+$('.dealdate #d2').val()+'>';
	  for(var i in cols){
		  str+='<input type="text" name ="tableList['+i+'].name" value='+cols[i].title+'><input type="text" name ="tableList['+i+'].value" value='+cols[i].name+'>'
	  }
	  $('#fornm').append( str );
	  $('#fornm').submit();
   });
/*----------------快速搜索功能的实现------------------------*/
	$('.review_serbtn').click(function(){
		var txt=$('.review_seript').val();
		  dialog.serchData(txt);
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
			       			'trdtbegin':$('.dealdate #d1').val(),
			       			'trdtend':$('.dealdate #d2').val(),
			    	    	'pageSize':$('.layui-laypage-limits select option:selected').attr('value')*1,
			    	    	'pageNo':obj.curr
			    		});
			    	}	
			    }
			  });
		});
	} 	
	//签约流水查询 中查询数据请求接口为404；  不保存;
	
})

