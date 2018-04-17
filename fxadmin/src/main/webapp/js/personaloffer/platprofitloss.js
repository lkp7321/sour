require.config({
	baseUrl:'/fx/js/',
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
		WdatePicker:'My97DatePicker/WdatePicker'
	}
});
require(['jquery','mmGrid','niceScroll','dialog','WdatePicker'],function($,mmGrid,niceScroll,dialog,WdatePicker){
	var wh=$(window).height()-190+"px",
	    ww=$(window).width()-260+"px";

    $('.boxtop').css('width',ww);
    $('.boxtop').css('height',wh);
    
	//列参数;
    var cols = [
        { title:'交易日期', name:'trdt',width:160, align:'right' },
        { title:'货币对', name:'exnm',width:160, align:'left'},
        { title:'左头寸', name:'total_lamt',width:160, align:'right' },
        { title:'右头寸', name:'total_ramt',width:160, align:'right'},
        { title:'自动平盘损益', name:'ausy',width:100, align:'right' },
        { title:'手工平盘损益', name:'sgsy',width:100, align:'right'}
    ];
    dialog.getnowTime('#d1','#d2');
    
     var usnm=sessionStorage.getItem('usnm'),
         product=sessionStorage.getItem('product'),
         loginuser={'usnm':usnm,'prod':product},
	     sartDate= $('#d1').val(),
	     endDate= $('#d2').val(),
	     ckvo,
	     userkey=JSON.parse( sessionStorage.getItem('menu') ).userKey,
		 tit=$('title').text();
     	 ckvo = { 'userKey':userkey, 'sartDate':sartDate, 'endDate':endDate};
     
     //请求列表     
    $.ajax({
		url:'/fx/listPpsy.do',
	    type:'post',
		contentType:'application/json',
		data:JSON.stringify(ckvo),
		async:true,
		dataType:'json',
		success:function(data){
			if(data.code==00){
				dialog.ren({'cols':cols,'wh':wh,'userdata':''});  
			 }else if(data.code==01){
				dialog.ren({'cols':cols,'wh':wh,'userdata':JSON.parse(data.codeMessage)});  
			}
		  }
      });
    //请求汇总数据
     getPpTosy('listPpTosy.do');
     
    //点击查询按钮请求数据
     $('.plat_serch').click(function(){
		   ckvo = { 'userKey':userkey, 'sartDate':$('#d1').val(), 'endDate':$('#d2').val()};

		   $.ajax({
				url:'/fx/listPpsy.do',
			    type:'post',
				contentType:'application/json',
				data:JSON.stringify(ckvo),
				async:true,
				dataType:'json',
				success:function(data){
					if(data.code==00){
						dialog.ren({'cols':cols,'wh':wh,'userdata':''});  
					}else if(data.code==01){
						dialog.ren({'cols':cols,'wh':wh,'userdata':JSON.parse(data.codeMessage)});  
					}
				  }
		      });
		   //请求汇总数据
		   getPpTosy('listPpTosy.do');
		   
	  });
   //请求列表没有数据的提示窗的确认按钮
 	$('body',parent.document).on('click','.platprofitloss .sure',function(){
 		//关闭选择一条数据;
 		$(this,parent.document).closest('.zhezhao').remove(); 
 		 
 	}); 
  //封装请求列表
	function getPlat(obj){
		 $.ajax({
			url:obj,
		    type:'post',
			contentType:'application/json',
			data:JSON.stringify(ckvo),
			async:true,
			dataType:'json',
			success:function(data){
				if(data.code==00){
					//无数据
					dialog.ren({'cols':cols,'wh':wh,'userdata':''});  
				}else if(data.code==01){
					dialog.ren({'cols':cols,'wh':wh,'userdata':JSON.parse(data.codeMessage)});  
				}
			  }
	      });
	}
	//请求汇总数据的封装
	function getPpTosy(obj){
		 ckvo = { 'userkey':userkey, 'sartDate':$('#d1').val(), 'endDate':$('#d2').val()};
		$.ajax({
			url:obj,
		    type:'post',
			contentType:'application/json',
			data:JSON.stringify(ckvo),
			async:true,
			dataType:'json',
			success:function(data){
				if(data.code==00){
					
				 }else if(data.code==01){
					$('#selfnum').val(data.codeMessage.totapp);
					$('#autonum').val(data.codeMessage.autoppsy);
					$('#handnum').val(data.codeMessage.handppsy);
				}
			  }
	      });
	}
	//导出excel;
	 $('#logon').click(function(){
		 $('#fornm input').remove();
		  var str='<input type="text" name = "userKey" value='+userkey+'><input type="text" name = "tableName" value='+tit+'><input type="text" name = "endDate" value='+endDate+'><input type="text" name = "sartDate" value='+sartDate+'>';
		  for(var i in cols){
			  str+='<input type="text" name ="tableList['+i+'].name" value='+cols[i].title+'><input type="text" name ="tableList['+i+'].value" value='+cols[i].name+'>'
		  }
		  $('#fornm').append( str );
		  $('#fornm').submit();
   });
	
	 
})
