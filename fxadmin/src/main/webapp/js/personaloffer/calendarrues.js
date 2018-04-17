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
		wdatePicker:'./My97DatePicker/WdatePicker'
	}
});
require(['jquery','mmGrid','niceScroll','wdatePicker','dialog'],function($,mmGrid,niceScroll,wdatePicker,dialog){
	var wh=$(window).height()-200+"px",
        ww=$(window).width()-260+"px";
	$('.boxtop').css('width',ww);
	$('.boxtop').css('height',wh);
	$('#page').css('width',ww);
	var userkey=JSON.parse( sessionStorage.getItem('menu') ).userKey,listnumtotal;
	//列参数
    var cols = [
        { title:'规则编号', name:'calendarID' ,width:80, align:'left' },
        { title:'规则名称', name:'calendarName' ,width:100, align:'left'},
        { title:'开始日期', name:'beginDate' ,width:80, align:'right'},
        { title:'结束日期', name:'endDate' ,width:80, align:'right'},
        { title:'开始星期', name:'beginWeek' ,width:80, align:'left'},
        { title:'结束星期', name:'endWeek' ,width:80, align:'left'},
        { title:'是否全天', name:'quanflag' ,width:60, align:'center'},
        { title:'开始时间', name:'beginTime' ,width:80, align:'right'},
        { title:'结束时间', name:'endTime' ,width:80, align:'right'},
        { title:'规则等级', name:'levelName' ,width:120, align:'left'},
        { title:'开/闭盘', name:'flag' ,width:80, align:'center'}
    ];
    var bsVo={'pageNo':1,'pageSize':10};
    getAllTradeCalendar(bsVo); 
    renpage();
    $('.add').click(function(){
    	dialog.calendarruleadd('calendarrules','add');
    	$('#d1',parent.document).val(dialog.today());
    	$('#d2',parent.document).val(dialog.today());
    	
    	//根据规则等级 判断日期和星期是否被禁用
    	$('.short',parent.document).change(function(){
    		 var levelType=$('.short option:selected',parent.document).text();
	   		if(levelType=='每周每日'){
	   			$('#d1,#d2',parent.document).attr("disabled","disabled"); 
	   	    }else{
	   	    	$('#d1,#d2',parent.document).removeAttr("disabled"); 
	   	    }
	   	    if(levelType=='年度特殊日'){
	   			$('.beginWeek .tsmall,.endWeek .tsmall',parent.document).attr("disabled","disabled"); 
	   	    }else{
	   			$('.beginWeek .tsmall,.endWeek .tsmall',parent.document).removeAttr("disabled"); 
	   	    }
	   	    if(levelType=='请选择'){
	   			dialog.choicedata('请选择规则等级!','calendarruleserror');
	   		}
        })
     });
    
     $('.modify').click(function(){
    	if( $('.box tbody tr').hasClass('selected') ){
    		dialog.calendarruleadd('calendarrules','modify');
    		var levelType=$('.box tr.selected td:eq(9) span').text().substr(2),
    		    calendarName=$('.box tr.selected td:eq(1) span').text(),
    		    beginDate=$('.box tr.selected td:eq(2) span').text(),
    		    endDate=$('.box tr.selected td:eq(3) span').text(),
    		    beginTime=$('.box tr.selected td:eq(7) span').text(),
    		    endTime=$('.box tr.selected td:eq(8) span').text(),
    		    flag=$('.box tr.selected td:eq(10) span').text(),
    		    beginWeek=$('.box tr.selected td:eq(4) span').text(),
    		    endWeek=$('.box tr.selected td:eq(5) span').text(),
    		    isquantian=$('.box tr.selected td:eq(6) span').text();
    		    //根据规则等级 判断日期和星期是否被禁用 
    		    if(levelType=='每周每日'){
	    			$('#d1,#d2',parent.document).attr("disabled","disabled"); 
	    	    }else{
	    	    	$('#d1,#d2',parent.document).removeAttr("disabled"); 
	    	    }
	    	    if(levelType=='年度特殊日'){
	    			$('.beginWeek .tsmall,.endWeek .tsmall',parent.document).attr("disabled","disabled"); 
	    	    }else{
	    			$('.beginWeek .tsmall,.endWeek .tsmall',parent.document).removeAttr("disabled"); 
	    	    }
	    	    if(isquantian=="是"){
		  		     $('.beginTime,.endTime',parent.document).attr("disabled","disabled"); 
		  		  }else{
		  		     $('.beginTime,.endTime',parent.document).removeAttr("disabled"); 
		  	    }
    		    $('#d1',parent.document).val(beginDate);
        	    $('#d2',parent.document).val(endDate);
        	    $('.beginTime',parent.document).val(beginTime);
        	    $('.endTime',parent.document).val(endTime);
        	    $('.calendarName',parent.document).val(calendarName);
        	    $('.short option',parent.document).each(function(i,v){
					 if($(v).text()==levelType){
			    		 $(this).attr("selected","selected")
			    		 $(this).parent('.short').attr("disabled","disabled");
			    	 }
				})
				$('.beginWeek .tsmall option',parent.document).each(function(i,v){
				 
					 if($(v).text()==beginWeek){
			    		 $(this).attr("selected", "selected")
			    	 }
				})
				$('.endWeek .tsmall option',parent.document).each(function(i,v){
					 if($(v).text()==endWeek){
			    		 $(this).attr("selected", "selected")
			    	 }
				})
				
				if(flag=='开盘'){
			    	$('.flag input[type="radio"][value="0"]',parent.document).prop("checked",true); 
			    }else{
			    	$('.flag input[type="radio"][value="1"]',parent.document).prop("checked",true);
			    }
        	    if(isquantian=='否'){
			    	$('.isquantian input[type="radio"][value="0"]',parent.document).prop("checked",true); 
			    }else{
			    	$('.isquantian input[type="radio"][value="1"]',parent.document).prop("checked",true);
			    }
        }else{
    		dialog.choicedata('请选择一条数据!','calendarrules');
    	}
    });
     // 判断是否为全天禁用开始结束时间
     $('body',parent.document).on('click','.isquantian input[name="aa"]',function(){
    	 var isquantian=$('.isquantian input[name="aa"]:checked',parent.document).val();
    	 console.log(isquantian)
  		 if(isquantian==1){
  		     $('.beginTime,.endTime',parent.document).attr("disabled","disabled"); 
  		  }else{
  		     $('.beginTime,.endTime',parent.document).removeAttr("disabled"); 
  		 }
    	 
     })
     $('body',parent.document).on('click','.calendarrules .sav',function(){
     	 var calendarID=$('.box tr.selected td:eq(0) span').text(),
     	     beginWeek=$('.beginWeek .tsmall option:selected',parent.document).val(),
     	     endWeek=$('.endWeek .tsmall option:selected',parent.document).val(),
     	     beginDate=$('.beginDate',parent.document).val(), 
     	     endDate=$('.endDate',parent.document).val(),
     	     calendarName=$('.calendarName',parent.document).val(),
     	     beginTime=$('.beginTime',parent.document).val(),
     	     endTime=$('.endTime',parent.document).val(),
     	     flag=$('.flag input[name="bb"]:checked',parent.document).val(),
     	     isquantian=$('.isquantian input[name="aa"]:checked',parent.document).val(),
     	     levelType=$('.short option:selected ',parent.document).val(),
     	     typelevel=$('.short option:selected ',parent.document).text(),url,calVo;
     	     $('.short',parent.document).change(function(){
   		         typelevel=$('.short option:selected',parent.document).text();
     	     })
     	  
    	 if($(this).text()=='提交'){
     		url='saveCalendarRule.do';
     		if(typelevel=="每周每日"){
       		     calVo={
       		    	'userKey':userkey,
       		    	'calVo':{
       		    	 'beginWeek':beginWeek,"endWeek":endWeek,"beginDate":"","endDate":"","calendarName":calendarName,"beginTime":beginTime,"endTime":endTime,"flag":flag,"isquantian":isquantian,"levelType":levelType
       		     	} 
       		     }
       		     if(isquantian==1){
       		    	calVo={'userKey':userkey,'calVo':{'beginWeek':beginWeek,"endWeek":endWeek,"beginDate":"","endDate":"","calendarName":calendarName,"beginTime":"","endTime":"","flag":flag,"isquantian":isquantian,"levelType":levelType}} 
    	         }
        	 }else if(typelevel=="年度特殊日"){
       		     calVo={'userKey':userkey,'calVo':{'beginWeek':"","endWeek":"","beginDate":beginDate,"endDate":endDate,"calendarName":calendarName,"beginTime":beginTime,"endTime":endTime,"flag":flag,"isquantian":isquantian,"levelType":levelType}}
       		     if(isquantian==1){
     		    	calVo={'userKey':userkey,'calVo':{'beginWeek':"","endWeek":"","beginDate":beginDate,"endDate":endDate,"calendarName":calendarName,"beginTime":"","endTime":"","flag":flag,"isquantian":isquantian,"levelType":levelType} }
    	         }
        	 } 
     		
     		
     	 }else if($(this).text()=='更改'){
     	    url='updateCalendarRules.do';
	     	   if(typelevel=="每周每日"){
	     		     calVo={	     		    	
	     		        'userKey':userkey,
	     		    	'calVo':{
	     		    		'calendarID':calendarID,'beginWeek':beginWeek,"endWeek":endWeek,"beginDate":"","endDate":"","calendarName":calendarName,"beginTime":beginTime,"endTime":endTime,"flag":flag,"isquantian":isquantian,"levelType":levelType
	     		    	}
	     		     } 
	     		     if(isquantian==1){
	     		    	calVo={
	     		    			'userKey':userkey,
	     		    			'calVo':{
	     		    				'calendarID':calendarID,'beginWeek':beginWeek,"endWeek":endWeek,"beginDate":"","endDate":"","calendarName":calendarName,"beginTime":"","endTime":"","flag":flag,"isquantian":isquantian,"levelType":levelType} 
	     		    			}
	     		    	}
	      	 }else if(typelevel=="年度特殊日"){
	     		     calVo={
	     		    		 'userKey':userkey,calVo:{'calendarID':calendarID,'beginWeek':"","endWeek":"","beginDate":beginDate,"endDate":endDate,"calendarName":calendarName,"beginTime":beginTime,"endTime":endTime,"flag":flag,"isquantian":isquantian,"levelType":levelType}};
	     		     if(isquantian==1){
	   		    	calVo={'userKey':userkey,'calVo':{'calendarID':calendarID,'beginWeek':"","endWeek":"","beginDate":beginDate,"endDate":endDate,"calendarName":calendarName,"beginTime":"","endTime":"","flag":flag,"isquantian":isquantian,"levelType":levelType}};
	  	         }
	      	 } 
     	 }
    	
    	 if(calendarName){
    		 if(typelevel=="请选择"){
    			 dialog.choicedata('请选择规则等级!','calendarruleserror');
    		 }else{
    			 if(isquantian==0 && comptime(beginTime,endTime)==false){
    				 dialog.choicedata('终止时间不能小于起始时间!','calendarruleserror');
    			 }else{
    				 $.ajax({
    	     	     		url:url,
    	     	     		type:'post',
    	     	     		contentType:'application/json',
    	     	     		data:JSON.stringify(calVo),
    	     	     		async:true,
    	     	     		success:function(data){
    	     	     			if(data.code==01){
    	     	     				$('.calendarrules .sav',parent.document).closest('.zhezhao').remove();
    	     	     				getAllTradeCalendar(bsVo);
    	     	     				dialog.choicedata(data.codeMessage,'calendarrules');
    	     	     			}else if(data.code==02){
    	     	     				dialog.choicedata(data.codeMessage,'calendarruleserror');
    	     	     			}
    	     	     		}
    	     	     	});  
    			 }
    		 }
    	 }else{
    		  dialog.choicedata('规则名称不能为空!','calendarruleserror');
    	  }
    	 
     });
    $('.delete').click(function(){
    	if( $('.box tbody tr').hasClass('selected') ){
    		 dialog.cancelDate('您确定要删除当前记录吗?','calendarrules');
    		 $('.calendarrules .confirm',parent.document).click(function(){
    			 var calendarId=$('.box tr.selected td:eq(0) span').text()
    			 $.ajax({
    			   		url:'/fx/delCalendarRule.do',
    			   		type:'post',
    			   		contentType:'application/json',
    			   		data:JSON.stringify(
    			   			{
    			   				'userKey':userkey,
    			   				'calendarID':calendarId
    			   			}
    			   		),
    			   		async:true,
    			   		success:function(data){
    			   		    if(data.code==01){
    			   		    	$('.calendarrules .confirm',parent.document).closest('.zhezhao').remove();
    			   		        getAllTradeCalendar(bsVo); 
    			   		    	dialog.choicedata(data.codeMessage,'calendarrules');
    			   			}else if(data.code==02){
    			   				dialog.choicedata(data.codeMessage,'calendarrules');
    			   			}
    			   		}
    			   	});
    		 })
    	 }else{
    	   dialog.choicedata('请选择一条数据!','calendarrules');
    	}
    });
    $('body',parent.document).on('click','.calendarruleserror .sure',function(){
		$(this).closest('.zhezhao').remove();
	});
    $('body',parent.document).on('click','.calendarrules .sure,.close,.cance,.cancel',function(){
    	$('.zhezhao',parent.document).remove();
    });
    
	$('body',parent.document).on('click','#d241,#d242',function(){
		WdatePicker({
			dateFmt:'HH:mm:ss',
			isShowClear:false,
			isShowToday:false,
			isShowOthers:false,
			isShowOk:false,
			qsEnabled:false,
			position:{left:-240,top:-60}
		 }); 
	});
    $('body',parent.document).on('click','#d1,#d2',function(){
		WdatePicker({
			dateFmt:'yyyyMMdd',
			minDate:'%y-%M-%d',
			isShowClear:false,
			isShowToday:false,
			isShowOthers:false,
			isShowOk:false,
			qsEnabled:false,
			position:{left:-240,top:-60}
		 }); 
	});
    //判断截止时间不能小于开始时间 
    function comptime(obj,obj1) {
	    var beginTime = obj;
	    var endTime = obj1;
	    beginTime = 2017+ '-' + 10 + '-' + 27+' '+beginTime;
	    endTime =  2017+ '-' + 10 + '-' + 27+' '+endTime;
	    var a = (Date.parse(endTime) - Date.parse(beginTime)) / 3600 / 1000;
	    
	  	if (a < 0||a==0) {
	  		return false;
	  		//dialog.choicedasta('截止时间应大于起始时间!','quoteparameter');
	    } else if (a > 0) {
	        return true;
	    }
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
    		    		getAllTradeCalendar({
    		    			'pageNo':obj.curr,
    		    			'pageSize':$('.layui-laypage-limits select option:selected').attr('value')*1
    		    		});
    		    	}	
    		    }
    		  });
    	});
    }
    
    //封装请求主列表数据
   function getAllTradeCalendar(obj){
   	 $.ajax({
   		url:'/fx/getAllTradeCalendar.do',
   		type:'post',
   		contentType:'application/json',
   		data:JSON.stringify(obj),
   		async:false,
   		success:function(data){
   		    if(data.code==01){
   				userdata= data.codeMessage;
   			    dialog.ren({'cols':cols,'wh':wh,'userdata':userdata.list});
   				listnumtotal=userdata.total;
   			   /* $('.page').remove();
   			    $('.boxtop').append('<div class="page"><span class="first">首页</span><span class="prev">上一页</span><span class="next">下一页</span><span class="last">末页</span><span class="nopage">第<b class="Nopage">'+userdata.pageNum+'</b>页/共<b class="Totalpage">'+userdata.pages+'</b>页</span><span class="totalpage">共<b class="total">'+userdata.total+'</b>条记录</span></div>')
   			*/
   		    }else if(data.code==00){
   				dialog.ren({'cols':cols,'wh':wh,'userdata':''});
   			}
   		}
   	});
       
   };
/*----------------快速搜索功能的实现------------------------*/
	$('.info_serbtn').click(function(){
		  var txt=$('.info_seript').val();
		  dialog.serchData(txt);
    });


})

