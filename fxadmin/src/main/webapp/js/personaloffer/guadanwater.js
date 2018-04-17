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
	/*var wh=$(window).height()-190+"px",
		ww=$(window).width()-345+"px";*/
	var wh=$(window).height()-190+"px",
		ww=$(window).width()-260+"px";
	$('.boxtop').css('width',ww);
	$('.boxtop').css('height',wh);
	
	var usnm=sessionStorage.getItem('usnm'),
		product=sessionStorage.getItem('product'),
		user={'usnm':usnm,'prod':product},
		listnumtotal,
		userkey=JSON.parse( sessionStorage.getItem('menu') ).userKey,
		tit=$('title').text(),
		userdata,str=''; 
	$('.showtime2').text( getDate() );
	$('.showtime1').text( getDate() );
	//列参数;
    var cols = [
        { title:'挂单合同流水号', name:'rqno',width:100, align:'left' },
        { title:'机构', name:'ognm' ,width:100, align:'left'},
        { title:'卡号', name:'cuac' ,width:60, align:'left'},
        { title:'申请日期', name:'trdt' ,width:60, align:'right'},
        { title:'挂单类型', name:'odtp' ,width:60, align:'center'},
        { title:'交易方向', name:'bsfg' ,width:60, align:'left'},
        { title:'申请时间', name:'trtm' ,width:60, align:'right'},
        { title:'有效截止日期', name:'vddt' ,width:60, align:'right'},
        { title:'成交日期', name:'prdt' ,width:60, align:'right'},
        { title:'成交时间', name:'prtm',width:100, align:'right'},
        { title:'买入币种', name:'bynm' ,width:100, align:'left'},
        { title:'卖出币种', name:'slnm' ,width:100, align:'left'},
        { title:'钞汇标志', name:'cxfg' ,width:60, align:'center'},
        { title:'止盈汇率', name:'akpc' ,width:60, align:'right'},
        { title:'止损汇率', name:'zspc' ,width:60, align:'right'},
        { title:'买入金额', name:'byam' ,width:60, align:'right'},
        { title:'卖出金额', name:'slam' ,width:80, align:'right'},
        { title:'优惠点数', name:'fvda' ,width:80, align:'right'},
        { title:'成交汇率', name:'expc' ,width:80, align:'right'},
        { title:'盈亏金额', name:'pram' ,width:80, align:'right'},
        { title:'撤单流水号', name:'cono' ,width:100, align:'left'},
        { title:'渠道类型', name:'qdtp' ,width:100, align:'center'},
        { title:'挂单状态', name:'stcd' ,width:30, align:'center'}
    ];
    //获取机构名称；
    $.ajax({
    	url:'/fx/comboxA.do',
    	type:'post',
    	data:userkey,
		async:false,
		success:function(data){
			str='';
			if(data.code==02){
				dialog.choicedata('获取机构失败!','userinfo','aaa');
			}else if(data.code==01){
				userdata=data.codeMessage;
				for(var i in userdata){
					str+='<option leve='+userdata[i].leve+' ogup='+userdata[i].ogup+' ogcd='+userdata[i].ogcd+'>'+userdata[i].ognm+'</option>'
				}
				$('.long').html( str );
				getFn( $('.long option:eq(0)').attr('ogcd') );
			}
		}
    });
    $('.long').change(function(){
    	getFn( $('.long option:selected').attr('ogcd') );
    });
    function getFn(obj){
    	$.ajax({
        	url:'/fx/comboxB.do',
        	type:'post',
    		async:false,
    		contentType:'application/json',
    		data:obj,
    		success:function(data){
    			str='<option ogcd="all">所有</option>';
    			if(data.code==02){
    				dialog.choicedata('获取机构失败!','guadanwater','aaa');
    			}else if(data.code==01){
    				userdata=data.codeMessage;
    				for(var i in userdata){
    					str+='<option leve='+userdata[i].leve+' ogup='+userdata[i].ogup+' ogcd='+userdata[i].ogcd+'>'+userdata[i].ognm+'</option>'
    				}
    				$('.long1').html( str );
    			}
    		}
        });
    }
   getlist({
	   userKey:userkey,
	   pageNo:1,
	   pageSize:10,
	   strcuac:"",
	   trdtbegin:$('.showtime1').text(),
	   trdtend:$('.showtime2').text(),
	   comaogcd:$('.long option:selected').attr('ogcd'),
	   combogcd:$('.long1 option:selected').attr('ogcd')
    });
   renpage();
    //渲染函数；
	function getlist(obj){
	  $.ajax({
			url:'/fx/getAllPreodrList.do',
			type:'post',
			contentType:'application/json',
			data:JSON.stringify({
					userKey:obj.userKey,
					pageNo:obj.pageNo,
			    	pageSize:obj.pageSize,
			    	strcuac:obj.strcuac,
			    	trdtbegin:obj.trdtbegin,
			    	trdtend:obj.trdtend,
			    	comaogcd:obj.comaogcd,
			    	combogcd:obj.combogcd
			}),
			async:false,
			success:function(data){
				if(data.code==00){
					dialog.ren({'cols':cols,'wh':wh,'userdata':''});
					}else if(data.code==01){
						userdata=data.codeMessage ;
						dialog.ren({'cols':cols,'wh':wh,'userdata':userdata});
						listnumtotal=userdata.total;
					/*$('.page').remove();
    				$('.boxtop').append('<div class="page"><span class="first">首页</span><span class="prev">上一页</span><span class="next">下一页</span><span class="last">末页</span><span class="nopage">第<b class="Nopage">'+userdata.pageNum+'</b>页/共<b class="Totalpage">'+userdata.pages+'</b>页</span><span class="totalpage">共<b class="total">'+userdata.total+'</b>条记录</span></div>')
				*/
				}
			}
		});
  }
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
    		    		getlist({
		    			   userKey:userkey,
		    			   pageNo:obj.curr,
		    			   pageSize:$('.layui-laypage-limits select option:selected').attr('value')*1,
		    			   strcuac:$('.cardNum input').val(),
		    			   trdtbegin:$('.showtime1').text(),
		    			   trdtend:$('.showtime2').text(),
		    			   comaogcd:$('.long option:selected').attr('ogcd'),
		    			   ogcdcombogcd:$('.long1 option:selected').attr('ogcd')
		    		    });
    		    	}	
    		    }
    		  });
    	});
    }
   //点击查询按钮；
	$('.bott button').click(function(){
		var startime,endtime;
		if( $('#d1').val()==''){
			startime=$('.showtime1').text();
		}else{
			startime=$('#d1').val();
		}
		if( $('#d2').val()==''){
			endtime=$('.showtime2').text();
		}else{
			endtime=$('#d2').val();
		}
		 getlist({
			userKey:userkey,
			pageNo:1,
			pageSize:10,
			strcuac:$('.cardNum input').val(),
 			trdtbegin:startime,
 			trdtend:endtime,
 			comaogcd:$('.long option:selected').attr('ogcd'),
 			ogcdcombogcd:$('.long1 option:selected').attr('ogcd')
 		});
		 renpage()
	});
    //日期函数；
    getDate();
	function getDate(){
		var time=new Date(),
			year,month,day;
		year=time.getFullYear(),
		month=time.getMonth()+1,
		day=time.getDate();
		if( month<10){
			month='0'+month;
		}
		if( day<10){
			day='0'+day;
		}
		time=''+year+month+day;
		return time;
	}
	 $('body',parent.document).on('click','.guadanwater .sure',function(){
			$(this).closest('.zhezhao').remove();
		});
	 
	//导出excel;
	 $('#logon').click(function(){
		 $('#fornm input').remove();
		  var str='<input type="text" name = "userKey" value='+userkey+'><input type="text" name = "tableName" value='+tit+'>'+
		  		'<input type="text" name = "strcuac" value='+$('.cardNum input').val()+'><input type="text" name = "trdtbegin" value='+$('.showtime1').text()+'>'+
		  		'<input type="text" name = "trdtend" value='+$('.showtime2').text()+'><input type="text" name = "comaogcd" value='+$('.long option:selected').attr('ogcd')+'>'+
		  		'<input type="text" name = "combogcd" value='+$('.long1 option:selected').attr('ogcd')+'>';
		  for(var i in cols){
			  str+='<input type="text" name ="tableList['+i+'].name" value='+cols[i].title+'><input type="text" name ="tableList['+i+'].value" value='+cols[i].name+'>'
		  }
		  $('#fornm').append( str );
		  $('#fornm').submit();
     });
	//点击分页;
    /*$('.boxtop').on('click','.first',function(){
    	var Nopage=$('.Nopage').text()*1;
 	    if(Nopage>1){
 	    	Nopage=1;
 	    	 getlist({
    		   userKey:userkey,
    		   pageNo:Nopage,
    		   pageSize:10,
    		   strcuac:$('.cardNum input').val(),
    		   trdtbegin:$('.showtime1').text(),
    		   trdtend:$('.showtime2').text(),
    		   comaogcd:$('.long option:selected').attr('ogcd'),
    		   ogcdcombogcd:$('.long1 option:selected').attr('ogcd')
    	    });
 	    }
    });
	$('.boxtop').on('click','.prev',function(){
	    var Nopage=$('.Nopage').text()*1;
	    if(Nopage>1){
	    	Nopage=Nopage-1;
	    	getlist({
    		   userKey:userkey,
    		   pageNo:Nopage,
    		   pageSize:10,
    		   strcuac:$('.cardNum input').val(),
    		   trdtbegin:$('.showtime1').text(),
    		   trdtend:$('.showtime2').text(),
    		   comaogcd:$('.long option:selected').attr('ogcd'),
    		   ogcdcombogcd:$('.long1 option:selected').attr('ogcd')
    	    });
	    }
	});
	$('.boxtop').on('click','.next',function(){
		var Nopage=$('.Nopage').text()*1,
			Totalpage=$('.Totalpage').text()*1;
	    if(Nopage<Totalpage){
	    	Nopage=Nopage*1+1;
	    	getlist({
    		   userKey:userkey,
    		   pageNo:Nopage,
    		   pageSize:10,
    		   strcuac:$('.cardNum input').val(),
    		   trdtbegin:$('.showtime1').text(),
    		   trdtend:$('.showtime2').text(),
    		   comaogcd:$('.long option:selected').attr('ogcd'),
    		   ogcdcombogcd:$('.long1 option:selected').attr('ogcd')
    	    });
	    }
	});
	$('.boxtop').on('click','.last',function(){
		var Nopage=$('.Nopage').text()*1,
		Totalpage=$('.Totalpage').text()*1;
	    if(Nopage<Totalpage){
	    	Nopage=Totalpage;
	    	getlist({
    		   userKey:userkey,
    		   pageNo:Nopage,
    		   pageSize:10,
    		   strcuac:$('.cardNum input').val(),
    		   trdtbegin:$('.showtime1').text(),
    		   trdtend:$('.showtime2').text(),
    		   comaogcd:$('.long option:selected').attr('ogcd'),
    		   ogcdcombogcd:$('.long1 option:selected').attr('ogcd')
    	    });
	    }
	});*/
})

