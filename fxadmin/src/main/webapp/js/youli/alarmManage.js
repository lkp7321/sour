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
		ww=$(window).width()-250+"px";
	$('.boxtop').css('width',ww);
	$('.boxtop').css('height',wh);
	$('.page').css('width',ww);
	//列参数;
    var cols = [
            { title:'渠道', name:'channel',width:100,align:'left' },
            { title:'产品编号', name:'productcode',width:100, align:'left'},
            { title:'原渠道日期', name:'originatechanneldate',width:100, align:'right'},
            { title:'原渠道流水号', name:'originatechannelserialno',width:100, align:'left'},
            { title:'发起机构号', name:'originatepartyid',width:100, align:'left'},
            { title:'发起柜员号', name:'originatetellerid',width:100, align:'left'},
            { title:'后台操作状态', name:'backtype',width:100, align:'center'},
            { title:'本地操作状态', name:'localtype',width:100, align:'center'},
            { title:'圈存编号', name:'frid',width:100, align:'left'},
            { title:'本地流水号', name:'lcno',width:100, align:'left'},
            { title:'本地交易日期', name:'trdt',width:100, align:'right'},
            { title:'交易码', name:'trcd',width:100, align:'left'},  //trdt  scno;
            { title:'错误码', name:'error',width:100, align:'center'},
            { title:'处理标记', name:'flag',width:100, align:'left'},
            { title:'资金来源账号', name:'soac',width:100, align:'left'},
            { title:'签约机构', name:'ogcd',width:100, align:'left'},
            { title:'交易行为编码', name:'optp',width:100, align:'left'},
            { title:'被冲销流水号', name:'olno',width:100, align:'left'},
            { title:'产品编码', name:'pdld',width:100, align:'left'},
            { title:'操作', name:'lll',width:100, align:'center'}
    ];
    //获取产品和用户名；
    var usnm=sessionStorage.getItem('usnm'),
		product=sessionStorage.getItem('product'),
		user={'usnm':usnm,'prod':product},
		userdata;
    var userkey=JSON.parse( sessionStorage.getItem('menu') ).userKey,
    	listnumtotal,
    	tit=$('title').text();
    $('#d1').val( dialog.today() );
    getlist({
    	pageNo:1,
    	pageSize:10,
    	trdt:$('#d1').val(),
        lcno:$('.opename').val()
    });
    renpage();
    function getlist( obj ){
    	$.ajax({
    		url:'/fx/selecterror.do',
    		type:'post',
    		dataType:'html',
    		data:obj,
    		async:false,
    		success:function(data){
    			//alert(data);
    			
    			var data1 = JSON.parse(data);
    			if(data1.code==01){
    				userdata= data1.codeMessage;
    			//	console.log( userdata )
    				ren({'cols':cols,'wh':wh,'userdata':userdata.list});
    				listnumtotal=userdata.total;
    				/*$('.page').remove();
    				$('.boxtop').append('<div class="page"><span class="first">首页</span><span class="prev">上一页</span><span class="next">下一页</span><span class="last">末页</span><span class="nopage">第<b class="Nopage">'+userdata.pageNum+'</b>页/共<b class="Totalpage">'+userdata.pages+'</b>页</span><span class="totalpage">共<b class="total">'+userdata.total+'</b>条记录</span></div>')
    				*/
    			}else {
    				ren({'cols':cols,'wh':wh,'userdata':''});
    			}
    		}
    	});
    }
	function ren(obj){
    	$('.boxtop').html('');
    	$('#ascrail2000').remove();
    	$('.boxtop').append('<div class="box"></div>');
		var mmg = $('.box').mmGrid({
				height:obj.wh
				, cols: obj.cols
				,items:obj.userdata
	            , nowrap:true
	            , fullWidthRows:true
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
		 mmg.on('loadSuccess',function(){
			 if( $('.box tbody tr').length>0){
				$('.box tbody tr').each(function(i,v){
					$(v).find('td:last span').html('<button class="chongxiao" valu='+i+'>冲销</button>');
					var txt=$(v).find('td:eq(13) span').text()==1?'已操作':'未操作';
					$(v).find('td:eq(13) span').text(txt);
				});
			}
		 });
    }
	$('body',parent.document).on('click','.alarmmana .close,.alarmmana .twosure',function(){
		$(this).closest('.zhezhao').remove();
		//$('.zhezhao',parent.document).remove();
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
    		    		getlist({
    		    	    	pageNo:obj.curr,
    		    	    	pageSize:$('.layui-laypage-limits select option:selected').attr('value')*1,
    		    	    	trdt:$('#d1').val(),
		    		        lcno:$('.opename').val()
    		    	    });
    		    	}	
    		    }
    		  });
    	});
    }
	//点击查询；
	$('.alarmqurey').click(function(){
		 getlist({
	    	pageNo:1,
	    	pageSize:10,
	    	trdt:$('#d1').val(),
	        lcno:$('.opename').val()
	    });
	});
	//点击冲销；
	$('.boxtop').on('click','.chongxiao',function(){
		var txt=$(this).closest('tr').find('td:eq(13) span').text();
		var i_index=$(this).attr('valu'),user_data;
		if( txt!='已操作'){
			vo={
					bhid:"0214",
				    chnl:"1103"
			   };
				$.ajax({
		    		url:'getLoginOgcd.do',
		    		type:'post',
		    		contentType:'application/json',
		    		data:JSON.stringify(vo),
		    		async:false,
		    		success:function(data){
		    			if(data.code==00){
		    				user_data= data.codeMessage;
		    				forfx( user_data,i_index );
		    			}else {
		    				dialog.choicedata(data.codeMessage,'alarmmana','aaa');
		    			}
		    		}
		    	});
		}else{
			dialog.choicedata('您不能执行此操作!','alarmmana','aaa');	
		}
	});
	function forfx( user_data,i_index ){
		user_data=JSON.parse( user_data );
		var SELF_NUM=$('.box tbody tr:eq('+i_index+')').find('td:eq(3) span').text();
		$.ajax({
    		url:'forfxsaInfoin.do',
    		type:'post',
    		contentType:'application/json',
    		data:JSON.stringify({
    				common_org_code:user_data.ogcd,
    				common_user_code:user_data.trtl,
    				password:user_data.pass,
    				//REFNO:4,
    				bank_self_num:SELF_NUM,
    				cancel_reason:'06',
    				cancel_remark:'核心记账失败'
    		}),
    		async:true,
    		success:function(data){
    			if(data.code==01){
    				uperror(i_index );
    			}else {
    				dialog.choicedata(data.codeMessage,'alarmmana','aaa');
    			}
    		}
    	});
	}
	function uperror( i_index ){
			var trsn=$('.box tbody tr:eq('+i_index+')').find('td:eq(3) span').text();
			$.ajax({
	    		url:'upErrorTranlist.do',
	    		type:'post',
	    		contentType:'application/json',
	    		data:trsn,
	    		async:true,
	    		success:function(data){
	    			if(data.code==01){
	    				dialog.choicedata('更新成功!','alarmmana','aaa');
	    				 getlist({
    				    	pageNo:1,
    				    	pageSize:10,
    				    	trdt:$('#d1').val(),
    				        lcno:$('.opename').val()
    				    });
	    				renpage();
	    			}else{
	    				dialog.choicedata(data.codeMessage,'alarmmana','aaa');
	    			}
	    		}
	    	});
	}
	//导出excel;
	  $('#logon').click(function(){
		  $('#fornm input').remove();
		  var str='<input type="text" name = "tableName" value='+tit+'>';
		  for(var i in cols){
			  if(i < cols.length-1){
				  str+='<input type="text" name ="tableList['+i+'].name" value='+cols[i].title+'><input type="text" name ="tableList['+i+'].value" value='+cols[i].name+'>' 
			  }
		  }
		  $('#fornm').append( str );
		  $('#fornm').submit();
 });
});