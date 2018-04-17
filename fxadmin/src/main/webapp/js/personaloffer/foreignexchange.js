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
		ww=$(window).width()-260+"px",
		userkey=JSON.parse( sessionStorage.getItem('menu') ).userKey,
		listnumtotal,
		tit=$('title').text();
	$('.boxtop').css('width',ww);
	$('.boxtop').css('height',wh);
	$('.capar #d1').val(dialog.today());
	var num=0;
	//列参数
    var cols = [
        { title:'机构', name:'ognm' ,width:100, align:'left' },
        { title:'交易流水号', name:'lcno' ,width:100, align:'left' },
        { title:'交易日期', name:'trdt' ,width:80, align:'right'},
        { title:'交易时间', name:'trtm' ,width:120, align:'right'},
        { title:'交易代码', name:'trcd' ,width:120, align:'left'},
        { title:'客户号', name:'cuno' ,width:120, align:'left'},
        { title:'客户等级', name:'culv' ,width:100, align:'right'},
        { title:'项目代码', name:'exid' ,width:80, align:'left'},
        { title:'客户类型', name:'cutp' ,width:100, align:'center'},
        { title:'交易项目代码', name:'extr' ,width:80, align:'left'},
        { title:'额度标志', name:'vlfg',width:80, align:'center'},
        { title:'冻结金额', name:'fram' ,width:80, align:'right'},
        { title:'资金来源类型', name:'csof' ,width:80, align:'left'},
        { title:'资金来源账号', name:'soac' ,width:80, align:'left'},
        { title:'资金去向类型', name:'ctof' ,width:80, align:'left'},
        { title:'现金项目代码', name:'cash' ,width:80, align:'left'},
        { title:'卖出币种', name:'slum' ,width:80, align:'left'},
        { title:'买入币种', name:'bynm' ,width:80, align:'left'},
        { title:'卖出金额', name:'slam' ,width:120, align:'right'},
        { title:'成交汇率', name:'expc' ,width:120, align:'right'},
        { title:'买入金额', name:'byam' ,width:120, align:'right'},
        { title:'钞汇标志', name:'cxfg' ,width:120, align:'center'},
        { title:'优惠点数', name:'fvda' ,width:120, align:'right'},
        { title:'分行损益', name:'brsy' ,width:120, align:'right'},
        { title:'交易方式', name:'trfg' ,width:120, align:'center'},
        { title:'记录状态', name:'stcd' ,width:120, align:'center'}
    ];
	//请求机构1
    $.ajax({
		url:'/fx/comboxA.do',
		type:'get',
		async:false,
		dataType:'json',
		success:function(data){
			 if(data.code==01){
				var listdata=data.codeMessage,str;
				for(var i in listdata){
					 str+='<option value='+listdata[i].ogcd+'>'+listdata[i].ognm+'</option>'
				}
				$('.longone').html(str);
			  }else if(data.code==02){
				 //获取机构失败
			}
		}
	});
    
    //请求机构2
    var comaogcd=$('.longone option:selected').val();
    getComboxB(comaogcd);
    $('.longone').change(function(){
    	comaogcd=$('.longone option:selected').val();
    	 getComboxB(comaogcd);
    })
   
    function getComboxB( obj ){
    	$.ajax({
    		url:'/fx/comboxB.do',
    		type:'post',
    		async:false,
    		dataType:'json',
    		data:obj,
    		contentType:'application/json;charset=utf-8',
    	    success:function(data){
    	    	var str='<option value="all">所有</option>';
    			 if(data.code==01){
    				var listdata=data.codeMessage;
    				for(var i in listdata){
    					str+='<option value='+listdata[i].ogcd+'>'+listdata[i].ognm+'</option>'
    				}
    				$('.longtwo').html(str);
    			   }else if(data.code==02){
    				 //获取失败
    			}
    		}
    	});
    }
    
    var curLcno=$('.foreignexchange .curLcno').val(),
        strcuno=$('.foreignexchange .strcuno').val(),
        strsoac=$('.foreignexchange .strsoac').val(),
        vurData=$('.foreignexchange #d1').val(),
        comaogcd=$('.foreignexchange .longone option:selected').val(),
        combogcd=$('.foreignexchange .longtwo option:selected').val(),
        bsVo={'userKey':userkey,'curLcno':curLcno,'strcuno':strcuno,'strsoac':strsoac,'vurData':vurData,'comaogcd':comaogcd,'combogcd':combogcd,'pageNo':1,'pageSize':10};
    	getAllTrans( bsVo );
    	renpage();
    $('.foreignexchange .serch').click(function(){
    	curLcno=$('.foreignexchange .curLcno').val(),
        strcuno=$('.foreignexchange .strcuno').val(),
        strsoac=$('.foreignexchange .strsoac').val(),
        vurData=$('.foreignexchange #d1').val(),
        comaogcd=$('.foreignexchange .longone option:selected').val(),
        combogcd=$('.foreignexchange .longtwo option:selected').val(),
        bsVo={'userKey':userkey,'curLcno':curLcno,'strcuno':strcuno,'strsoac':strsoac,'vurData':vurData,'comaogcd':comaogcd,'combogcd':combogcd,'pageNo':1,'pageSize':10};
    	getAllTrans( bsVo );
    	renpage();
    });
    //封装请求列表
    function getAllTrans( obj ){
    	 $.ajax({
    		url:'/fx/getAllCurrencychan.do',
    		type:'post',
    		contentType:'application/json',
    		data:JSON.stringify(obj),
    		async:false,
    		success:function(data){
    			num++;
    			if(data.code==01){
    				userdata=data.codeMessage ;
    			    ren({'cols':cols,'wh':wh,'userdata':userdata});
    			    listnumtotal=userdata.total;
    			    /*$('.page').remove();
    			    $('.boxtop').append('<div class="page"><span class="first">首页</span><span class="prev">上一页</span><span class="next">下一页</span><span class="last">末页</span><span class="nopage">第<b class="Nopage">'+userdata.pageNum+'</b>页/共<b class="Totalpage">'+userdata.pages+'</b>页</span><span class="totalpage">共<b class="total">'+userdata.total+'</b>条记录</span></div>')
    			    */
    			}else if(data.code==00){
    				ren({'cols':cols,'wh':wh,'userdata':''});
    				if(num>1){
    					dialog.choicedata(data.codeMessage,'foreignexchange');
    				}
    			}
    		}
    	});
    };
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
    		        		'userKey':userkey,
    		        		'curLcno':curLcno,
    		        		'strcuno':strcuno,
    		        		'strsoac':strsoac,
    		        		'vurData':vurData,
    		        		'comaogcd':comaogcd,
    		        		'combogcd':combogcd,
    		        		'pageNo':obj.curr,
    		        		'pageSize':$('.layui-laypage-limits select option:selected').attr('value')*1
    		        	} );
    		    	}	
    		    }
    		  });
    	});
    }
    //渲染列表
    function ren(obj){
		//console.log( obj.userdata )
    	$('.boxtop').html('');
    	$('#ascrail2000').remove();
    	$('.boxtop').append('<div class="box"></div>');
		var mmg = $('.box').mmGrid({
				height:obj.wh
				, cols: obj.cols
				,items:obj.userdata
	            , nowrap:true
	            , fullWidthRows:false
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
				horizrailenabled:true
		  });
    }
    	//点击分页;
	    /*$('.boxtop').on('click','.first',function(){
	    	var Nopage=$('.Nopage').text()*1;
	 	    if(Nopage>1){
	 	    	Nopage=1;
	 	    	 getAllTrans({
		 	    	  'userKey':userkey,
		 	    	  'curLcno':$('.foreignexchange .curLcno').val(),
		 	    	  'strcuno':$('.foreignexchange .strcuno').val(),
		 	    	  'strsoac':$('.foreignexchange .strsoac').val(),
		 	    	  'vurData':$('.foreignexchange #d1').val(),
		 	    	  'comaogcd':$('.foreignexchange .longone option:selected').val(),
		 	    	  'combogcd':$('.foreignexchange .longtwo option:selected').val(),
		 	    	  'pageNo':Nopage,
		 	    	  'pageSize':10
	   				});
	 	    }
	    });
		$('.boxtop').on('click','.prev',function(){
		    var Nopage=$('.Nopage').text()*1;
		    if(Nopage>1){
		    	Nopage=Nopage-1;
		    	 getAllTrans({
	 	    	  'userKey':userkey,
	 	    	  'curLcno':$('.foreignexchange .curLcno').val(),
	 	    	  'strcuno':$('.foreignexchange .strcuno').val(),
	 	    	  'strsoac':$('.foreignexchange .strsoac').val(),
	 	    	  'vurData':$('.foreignexchange #d1').val(),
	 	    	  'comaogcd':$('.foreignexchange .longone option:selected').val(),
	 	    	  'combogcd':$('.foreignexchange .longtwo option:selected').val(),
	 	    	  'pageNo':Nopage,
	 	    	  'pageSize':10
   				});
		    }
		});
		$('.boxtop').on('click','.next',function(){
			var Nopage=$('.Nopage').text()*1,
				Totalpage=$('.Totalpage').text()*1;
		    if(Nopage<Totalpage){
		    	Nopage=Nopage*1+1;
		    	 getAllTrans({
	 	    	  'userKey':userkey,
	 	    	  'curLcno':$('.foreignexchange .curLcno').val(),
	 	    	  'strcuno':$('.foreignexchange .strcuno').val(),
	 	    	  'strsoac':$('.foreignexchange .strsoac').val(),
	 	    	  'vurData':$('.foreignexchange #d1').val(),
	 	    	  'comaogcd':$('.foreignexchange .longone option:selected').val(),
	 	    	  'combogcd':$('.foreignexchange .longtwo option:selected').val(),
	 	    	  'pageNo':Nopage,
	 	    	  'pageSize':10
   				});
		    }
		});
		$('.boxtop').on('click','.last',function(){
			var Nopage=$('.Nopage').text()*1,
			Totalpage=$('.Totalpage').text()*1;
		    if(Nopage<Totalpage){
		    	Nopage=Totalpage;
		    		getAllTrans({
		 	    	  'userKey':userkey,
		 	    	  'curLcno':$('.foreignexchange .curLcno').val(),
		 	    	  'strcuno':$('.foreignexchange .strcuno').val(),
		 	    	  'strsoac':$('.foreignexchange .strsoac').val(),
		 	    	  'vurData':$('.foreignexchange #d1').val(),
		 	    	  'comaogcd':$('.foreignexchange .longone option:selected').val(),
		 	    	  'combogcd':$('.foreignexchange .longtwo option:selected').val(),
		 	    	  'pageNo':Nopage,
		 	    	  'pageSize':10
	   				});
		    }
		});*/
    $('body',parent.document).on('click','.foreignexchange .sure',function(){
		$(this).closest('.zhezhao').remove();
	});
   //导出Excel；
    $('#logon').click(function(){
		 $('#fornm input').remove();
		  var str='<input type="text" name = "userKey" value='+userkey+'><input type="text" name = "tableName" value='+tit+'>'+
		  		  '<input type="text" name = "curLcno" value='+$('.foreignexchange .curLcno').val()+'><input type="text" name = "strcuno" value='+$('.foreignexchange .strcuno').val()+'>'+
		  		  '<input type="text" name = "strsoac" value='+$('.foreignexchange .strsoac').val()+'><input type="text" name = "vurData" value='+$('.foreignexchange #d1').val()+'>'+
		  		  '<input type="text" name = "comaogcd" value='+$('.foreignexchange .longone option:selected').val()+'><input type="text" name = "combogcd" value='+$('.foreignexchange .longtwo option:selected').val()+'>';
		  for(var i in cols){
			  str+='<input type="text" name ="tableList['+i+'].name" value='+cols[i].title+'><input type="text" name ="tableList['+i+'].value" value='+cols[i].name+'>'
		  }
		  $('#fornm').append( str );
		  $('#fornm').submit();
    }); 
})

