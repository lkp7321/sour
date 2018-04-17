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
		},
		'table2excel':{
			deps:['jquery'],
			exports:'table2excel'
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
	$('.page').css('width',ww);
	  var userkey=JSON.parse( sessionStorage.getItem('menu') ).userKey,prevpage;
	//列参数;
    var cols = [
        { title:'货币对', name:'EXNM' ,width:120, align:'left'},
        { title:'中间价上限', name:'MXMD' ,width:120, align:'right'},
        { title:'中间价', name:'MDMD' ,width:120, align:'right'},
        { title:'中间价下限', name:'MIMD' ,width:120, align:'right'},
        { title:'点差', name:'POINT' ,width:120, align:'right'},
        { title:'偏移量', name:'OFFSET' ,width:120, align:'right'},
        { title:'有效时间(分钟)', name:'VLTIME' ,width:120, align:'right'},
    ];
    var sixreg=/^[a-zA-Z]{6}$/,
    	dotnum=/^[0-9]+$|^[0]{1}\.{1}\d+$|^[1-9]{1}\d+\.{1}\d+$/,
    	onenum=/^\d{1}$/;
    //请求货币对
   $.ajax({
		url:'selChkParaExnm.do',
		type:'post',
		contentType:'application/json',
		async:true,
		success:function(data){
			var str='<option exnm="">请选择</option>'
			 if(data.code==00){
			 	 userdata=JSON.parse( data.codeMessage );
			 	 for(var i in userdata){
			 		str+='<option exnm='+userdata[i].EXNM+'>'+userdata[i].EXNM+'</option>'
			 	 }
			 	 $('.currency select').html( str );
			}else{
				//异常
			}
		}
	});
    //请求列表
    var ogcd=sessionStorage.getItem('obj'),
        ogcdVo={'exnm':'','pageSize':10,'pageNo':1,userKey:userkey},listnumtotal;
    getOrgnlist(ogcdVo);
    renpage();
    //点击查询请求列表
    $('.qurey').click(function(){
    	var ogcdVo;
    	 ogcdVo={
    		 	'exnm':$('.currency select option:selected').attr('exnm'),
    		 	'pageSize':10,
    		 	'pageNo':1,
    		 	userKey:userkey
    	};
    	 getOrgnlist(ogcdVo);
    	 renpage();
    });
    //封装请求列表
	function getOrgnlist(obj){
		$.ajax({	
    		url:'selJshChkPara.do',
    		type:'post',
    		contentType:'application/json',
    		data:JSON.stringify(obj),
    		async:false,
    		success:function(data){
    			if(data.code==00){
       				userdata=JSON.parse( data.codeMessage );
       				dialog.ren({'cols':cols,'wh':wh,'userdata':userdata.list,'checked':false});
       				listnumtotal=userdata.total;
       				/*$('.page').remove();
    				$('.boxtop').append('<div class="page"><span class="first">首页</span><span class="prev">上一页</span><span class="next">下一页</span><span class="last">末页</span><span class="nopage">第<b class="Nopage">'+userdata.pageNum+'</b>页/共<b class="Totalpage">'+userdata.pages+'</b>页</span><span class="totalpage">共<b class="total">'+userdata.total+'</b>条记录</span></div>')
    				*/
    			}else if(data.code==01){
       				dialog.ren({'cols':cols,'wh':wh,'userdata':'','checked':false});
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
    		        	 getOrgnlist({
 		        		 	'exnm':$('.sign select option:selected').attr('exnm'),
		        		 	'trfg':$('.currency select option:selected').attr('value'),
		        		 	'pageSize':10,
		        		 	'pageNo':1,
		        		 	userKey:userkey
    		        	 });
    		    	}	
    		    }
    		  });
    	});
    }
    //点击添加按钮；
	$('.add').click(function(){
		dialog.priceparaadd('pricepara','add','价格参数设置');
		 $('.cuurpair',parent.document).blur(function(){
			 if($(this).val()==''||$(this).val()==undefined){
					$('.cuurpair',parent.document).parent('div').find('re').remove();
					$('.cuurpair',parent.document).parent('div').append('<re>货币对不能为空</re>');
				}else if( !sixreg.test($(this).val() ) ){
					$('.cuurpair',parent.document).parent('div').find('re').remove();
					$('.cuurpair',parent.document).parent('div').append('<re>货币对必须是六位的英文字母</re>');
				}else{
					$('.cuurpair',parent.document).parent('div').find('re').remove();
				} 
		 });
		 $('.cenpri',parent.document).blur(function(){
			 if($(this).val()==''||$(this).val()==undefined){
					$('.cenpri',parent.document).parent('div').find('re').remove();
					$('.cenpri',parent.document).parent('div').append('<re>中间价不能为空</re>');
				}else if(!dotnum.test($(this).val()) ){
					$('.cenpri',parent.document).parent('div').find('re').remove();
					$('.cenpri',parent.document).parent('div').append('<re>中间价必须是合法数字</re>');
				}else{
					$('.cenpri',parent.document).parent('div').find('re').remove();
				} 
		 });
		 $('.valtime',parent.document).blur(function(){
			 if($(this).val()==''||$(this).val()==undefined){
					$('.valtime',parent.document).parent('div').find('re').remove();
					$('.valtime',parent.document).parent('div').append('<re>有效时间不能为空</re>');
				}else if(!dotnum.test($(this).val()) ){
					$('.valtime',parent.document).parent('div').find('re').remove();
					$('.valtime',parent.document).parent('div').append('<re>有效时间必须是一位数字</re>');
				}else{
					$('.valtime',parent.document).parent('div').find('re').remove();
				} 
		 });
		 $('.offset',parent.document).blur(function(){
			 if($(this).val()==''||$(this).val()==undefined){
					$('.offset',parent.document).parent('div').find('re').remove();
					$('.offset',parent.document).parent('div').append('<re>偏移量不能为空</re>');
				}else if(!dotnum.test($(this).val()) ){
					$('.offset',parent.document).parent('div').find('re').remove();
					$('.offset',parent.document).parent('div').append('<re>偏移量必须是一位数字</re>');
				}else{
					$('.offset',parent.document).parent('div').find('re').remove();
				} 
		 });
		 $('.point',parent.document).blur(function(){
			 if($(this).val()==''||$(this).val()==undefined){
					$('.point',parent.document).parent('div').find('re').remove();
					$('.point',parent.document).parent('div').append('<re>点差不能为空</re>');
				}else if(!dotnum.test($(this).val()) ){
					$('.point',parent.document).parent('div').find('re').remove();
					$('.point',parent.document).parent('div').append('<re>点差必须是一位数字</re>');
				}else{
					$('.point',parent.document).parent('div').find('re').remove();
				} 
		 });
	});
	  
	//点击修改按钮;
	$('.modify').click(function(){
		var a=0,arr=[];
		if( $('.box tr.selected').length>0){
			a++;
		}
		if(a==0){
			dialog.choicedata('请选择一条数据','pricepara');
		}else if(a==1){
			dialog.priceparaadd('pricepara','modify','价格参数设置');
			
			$('.cuurpair',parent.document).val( $('.box tr.selected').find('td').eq(0).find('span').text() );
			$('.cenpri',parent.document).val( $('.box tr.selected').find('td').eq(2).find('span').text()  );
			$('.point',parent.document).val( $('.box tr.selected').find('td').eq(4).find('span').text()  );
			$('.offset',parent.document).val( $('.box tr.selected').find('td').eq(5).find('span').text()  );
			$('.valtime',parent.document).val($('.box tr.selected').find('td').eq(6).find('span').text());
		}
		
		$('.cuurpair',parent.document).blur(function(){
			 if($(this).val()==''||$(this).val()==undefined){
					$('.cuurpair',parent.document).parent('div').find('re').remove();
					$('.cuurpair',parent.document).parent('div').append('<re>货币对不能为空</re>');
				}else if(  sixreg.test($(this).val() ) ){
					$('.cuurpair',parent.document).parent('div').find('re').remove();
					$('.cuurpair',parent.document).parent('div').append('<re>货币对必须是六位的英文字母</re>');
				}else{
					$('.cuurpair',parent.document).parent('div').find('re').remove();
				} 
		 });
		 $('.cenpri',parent.document).blur(function(){
			 if($(this).val()==''||$(this).val()==undefined){
					$('.cenpri',parent.document).parent('div').find('re').remove();
					$('.cenpri',parent.document).parent('div').append('<re>中间价不能为空</re>');
				}else if(!dotnum.test($(this).val()) ){
					$('.cenpri',parent.document).parent('div').find('re').remove();
					$('.cenpri',parent.document).parent('div').append('<re>中间价必须是合法数字</re>');
				}else{
					$('.cenpri',parent.document).parent('div').find('re').remove();
				} 
		 });
		 $('.valtime',parent.document).blur(function(){
			 if($(this).val()==''||$(this).val()==undefined){
					$('.valtime',parent.document).parent('div').find('re').remove();
					$('.valtime',parent.document).parent('div').append('<re>有效时间不能为空</re>');
				}else if(!dotnum.test($(this).val()) ){
					$('.valtime',parent.document).parent('div').find('re').remove();
					$('.valtime',parent.document).parent('div').append('<re>有效时间必须是一位数字</re>');
				}else{
					$('.valtime',parent.document).parent('div').find('re').remove();
				} 
		 });
		 $('.offset',parent.document).blur(function(){
			 if($(this).val()==''||$(this).val()==undefined){
					$('.offset',parent.document).parent('div').find('re').remove();
					$('.offset',parent.document).parent('div').append('<re>偏移量不能为空</re>');
				}else if(!dotnum.test($(this).val()) ){
					$('.offset',parent.document).parent('div').find('re').remove();
					$('.offset',parent.document).parent('div').append('<re>偏移量必须是一位数字</re>');
				}else{
					$('.offset',parent.document).parent('div').find('re').remove();
				} 
		 });
		 $('.point',parent.document).blur(function(){
			 if($(this).val()==''||$(this).val()==undefined){
					$('.point',parent.document).parent('div').find('re').remove();
					$('.point',parent.document).parent('div').append('<re>点差不能为空</re>');
				}else if(!dotnum.test($(this).val()) ){
					$('.point',parent.document).parent('div').find('re').remove();
					$('.point',parent.document).parent('div').append('<re>点差必须是一位数字</re>');
				}else{
					$('.point',parent.document).parent('div').find('re').remove();
				} 
		 });
	 });
	 
	//点击添加修改弹出框的保存按钮；
	$('body',parent.document).on('click','.preserve',function(){
	    var cuurpair=$('.cuurpair',parent.document).val(),
	        cenpri=$('.cenpri',parent.document).val(),
	        point=$('.point',parent.document).val(),
	        valtime=$('.valtime',parent.document).val(),
	        offset=$('.offset',parent.document).val(),vo,exist=0;
	        vo={'userKey':userkey,'exnm':cuurpair,'mdmd':cenpri,'point':point,'offset':offset,'vltime':valtime}
	    
			 if(cuurpair==''||cuurpair==undefined){
					$('.cuurpair',parent.document).parent('div').find('re').remove();
					$('.cuurpair',parent.document).parent('div').append('<re>货币对不能为空</re>');
				}else if(  !sixreg.test(cuurpair) ){
					$('.cuurpair',parent.document).parent('div').find('re').remove();
					$('.cuurpair',parent.document).parent('div').append('<re>货币对必须是六位的英文字母</re>');
				}else{
					$('.cuurpair',parent.document).parent('div').find('re').remove();
				} 
			 if(cenpri==''||cenpri==undefined){
					$('.gruname',parent.document).parent('div').find('re').remove();
					$('.gruname',parent.document).parent('div').append('<re>中间价不能为空</re>');
				}else if(!dotnum.test(cenpri) ){
					$('.gruname',parent.document).parent('div').find('re').remove();
					$('.gruname',parent.document).parent('div').append('<re>中间价必须是合法数字</re>');
				}else{
					$('.gruname',parent.document).parent('div').find('re').remove();
				} 
			 if(valtime==''||valtime==undefined){
					$('.valtime',parent.document).parent('div').find('re').remove();
					$('.valtime',parent.document).parent('div').append('<re>有效时间不能为空</re>');
				}else if(!dotnum.test(valtime) ){
					$('.valtime',parent.document).parent('div').find('re').remove();
					$('.valtime',parent.document).parent('div').append('<re>有效时间必须是一位数字</re>');
				}else{
					$('.valtime',parent.document).parent('div').find('re').remove();
				} 
			 if(offset==''||offset==undefined){
					$('.offset',parent.document).parent('div').find('re').remove();
					$('.offset',parent.document).parent('div').append('<re>偏移量不能为空</re>');
				}else if(!dotnum.test( offset ) ){
					$('.offset',parent.document).parent('div').find('re').remove();
					$('.offset',parent.document).parent('div').append('<re>偏移量必须是一位数字</re>');
				}else{
					$('.offset',parent.document).parent('div').find('re').remove();
				} 
			 if(point==''||point==undefined){
					$('.point',parent.document).parent('div').find('re').remove();
					$('.point',parent.document).parent('div').append('<re>点差不能为空</re>');
				}else if(!dotnum.test(point) ){
					$('.point',parent.document).parent('div').find('re').remove();
					$('.point',parent.document).parent('div').append('<re>点差必须是一位数字</re>');
				}else{
					$('.point',parent.document).parent('div').find('re').remove();
				} 
	  
	    if( $(this).text()=="添加" && $('.pricepara re',parent.document).length==0 ){
	    	  //判断当前追加的货币对是否存在；
	    	 $.ajax({
				url:'selChkParaExnmExist.do',
				type:'post',
				contentType:'application/json',
				data:JSON.stringify({exnm:cuurpair}),
				async:true,
				success:function(data){
					 if(data.code==00){
						 exist=0;
					 }else{
						 exist=1;
					 }
				}
			}); 
	    	 if( exist==1){
	    		 $.ajax({
	 				url:'insJshChkPara.do',
	 				type:'post',
	 				contentType:'application/json',
	 				data:JSON.stringify(vo),
	 				async:true,
	 				success:function(data){
	 					 $('.preserve',parent.document).closest('.zhezhao').remove();
	 					 if(data.code==00){
	 						 var Nopage=parseInt($('.Nopage').text()),
	 						 ogcdVo={
	 				    		 	'exnm':$('.sign select option:selected').attr('exnm'),
	 				    		 	'pageSize':10,
	 				    		 	'pageNo':Nopage,
	 				    		 	userKey:userkey
	 				    	};
	 				    	 getOrgnlist(ogcdVo);
	 				    	 renpage();
	 					 	 dialog.choicedata(data.codeMessage,'pricepara');
	 					 }else{
	 						
	 					 }
	 				}
	 			}); 
	    	 }else{
	    		 dialog.choicedata('该币种已存在!','pricepara');
	    	 }
	   }else if($(this).text()=="修改" && $('.pricepara re',parent.document).length==0 ){
		   $.ajax({	
				url:'/fx/updJshChkPara.do',
				type:'post',
				contentType:'application/json',
				data:JSON.stringify(vo),
				async:true,
				success:function(data){
					$('.preserve',parent.document).closest('.zhezhao').remove();
					 if(data.code==00){
						 var Nopage=parseInt($('.Nopage').text());
						 ogcdVo={
				    		 	'exnm':$('.sign select option:selected').attr('exnm'),
				    		 	'pageSize':10,
				    		 	'pageNo':Nopage,
				    		 	userKey:userkey
				    	};
				    	 getOrgnlist(ogcdVo);
				    	 renpage();
					 	 dialog.choicedata(data.codeMessage,'pricepara');
					 }else if(data.code==01){
						//异常
						 dialog.choicedata(data.codeMessage,'pricepara');
					}
				}
			}); 
		  }
	 });
	$('.del').click(function(){
		var a=0,arr=[];
		if( $('.box tr.selected').length>0){
			a++;
		}
		if(a==0){
			dialog.choicedata('请选择一条数据','pricepara');
		}else{
			dialog.cancelDate('您确定要删除当前记录吗?','pricepara');
		}
	});
	//点击确认删除按钮
	$('body',parent.document).on('click','.pricepara .confirm',function(){
			var exnm=$('.box tr.selected td:eq(0) span').text();
		  vo={'userkey':userkey,'exnm':exnm};
		   $.ajax({
				url:'/fx/delJshChkPara.do',
				type:'post',
				contentType:'application/json',
				data:JSON.stringify(vo),
				async:true,
				success:function(data){
					 $('.pricepara .confirm',parent.document).closest('.zhezhao').remove();
					 if(data.code==00){
						 var Nopage=parseInt($('.Nopage').text());
						 ogcdVo={
				    		 	'exnm':$('.sign select option:selected').attr('exnm'),
				    		 	'pageSize':10,
				    		 	'pageNo':Nopage,
				    		 	userKey:userkey
				    	};
				    	 getOrgnlist(ogcdVo);
				    	 renpage();
					 	 dialog.choicedata(data.codeMessage,'pricepara');
					 }else if(data.code==01){
						//异常
						 dialog.choicedata(data.codeMessage,'pricepara');
					}
				}
			}); 
	 });
  
	//点击请选择一条数据的确定按钮；
	$('body',parent.document).on('click','.pricepara .sure,.pricepara .cancel',function(){
		  $(this).closest('.zhezhao').remove();
	});
	//关闭弹窗
	$('body',parent.document).on('click','.pricepara .close,.pricepara .cancel,.pricepara .twosure',function(){
		  $(this).closest('.zhezhao').remove();
	});
   
	//点击分页;
    $('.boxtop').on('click','.page .first',function(){
    	var Nopage=parseInt($('.Nopage').text());
 	    if(Nopage>1){
 	    	Nopage=1;
 	    	ogcdVo={
    		 	'exnm':$('.sign select option:selected').attr('exnm'),
    		 	'trfg':$('.currency select option:selected').attr('value'),
    		 	'pageSize':10,
    		 	'pageNo':Nopage,
    		 	userKey:userkey
 	    	};
 	    	getOrgnlist(ogcdVo);
 	     }
 	  
    });
	$('.boxtop').on('click','.prev',function(){
	    var Nopage=parseInt($('.Nopage').text());
	    if(Nopage>1){
	    	Nopage=Nopage-1;
	    	ogcdVo={
    		 	'exnm':$('.sign select option:selected').attr('exnm'),
    		 	'trfg':$('.currency select option:selected').attr('value'),
    		 	'pageSize':10,
    		 	'pageNo':Nopage,
    		 	userKey:userkey
 	    	};
 	    	getOrgnlist(ogcdVo);
	     }
	    
	});
	$('.boxtop').on('click','.next',function(){
		var Nopage=parseInt($('.Nopage').text()),
			Totalpage=parseInt($('.Totalpage').text());
	    if(Nopage<Totalpage){
	    	Nopage=Nopage+1;
	    	ogcdVo={
    		 	'exnm':$('.sign select option:selected').attr('exnm'),
    		 	'trfg':$('.currency select option:selected').attr('value'),
    		 	'pageSize':10,
    		 	'pageNo':Nopage,
    		 	userKey:userkey
 	    	};
 	    	getOrgnlist(ogcdVo); 
	     }
	      
	});
	$('.boxtop').on('click','.last',function(){
		var Nopage=parseInt($('.Nopage').text()),
		Totalpage=parseInt($('.Totalpage').text());
	    if(Nopage<Totalpage){
	    	Nopage=Totalpage;
	    	ogcdVo={
    		 	'exnm':$('.sign select option:selected').attr('exnm'),
    		 	'trfg':$('.currency select option:selected').attr('value'),
    		 	'pageSize':10,
    		 	'pageNo':Nopage,
    		 	userKey:userkey
 	    	};
 	    	getOrgnlist(ogcdVo);
	    }
	     
	});
	 $('body',parent.document).on('click','.agentmain table tr',function(){
		 $(this).addClass('agentbgc').siblings().removeClass('agentbgc');
     });
	
})
