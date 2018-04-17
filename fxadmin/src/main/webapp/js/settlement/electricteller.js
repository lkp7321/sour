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
		ww=$(window).width()-260+"px";
	var userkey=JSON.parse( sessionStorage.getItem('menu') ).userKey,listnumtotal;
	$('.boxtop').css('width',ww);
	$('.boxtop').css('height',wh);

	 var cols = [
	             { title:'客户号', name:'CUNO',width:60,align:'right' },
	             { title:'结汇优惠', name:'FVJH',width:60, align:'right'},
	             { title:'购汇优惠', name:'FVGH',width:80, align:'right'},
	             { title:'机构名', name:'OGNM',width:60, align:'left'},
	             { title:'状态', name:'STAT',width:80, align:'center'}
	     ];
	 //点击查询按钮；
	 $('.search').click(function(){
		 getlist({
			 "cuno":$('.cusnum').val(),
			 "pageNo":$('.Nopage').text(),
			 "pageSize":10,
			 "ogcd":$('.fitna').attr('tit'),
			 "stat":$('.stat input[name="aa"]:checked').attr('tit')
		 });
		 renpage();
		 $('.fitna').val(' ');
		 $('.fitna').removeAttr('tit');
	 });
	 //请求列表；
	 getlist({
		 "cuno":$('.cusnum').val(),
		 "pageNo":1,
		 "pageSize":10,
		 "ogcd":'',
		 "stat":$('.stat input[name="aa"]:checked').attr('tit')
	 });
	 renpage();
	 function getlist( obj ){
		 var ogcd;
		 if(!obj.ogcd ){
			 ogcd='';
		 }else{
			 ogcd=obj.ogcd;
		 }
		 $.ajax({
			url:'/fx/selcCustFavList.do',
			type:'post',
			contentType:'application/json',
			data:JSON.stringify({
				 "cuno":obj.cuno,
				 "pageNo":obj.pageNo,
				 "pageSize":obj.pageSize,
				 "ogcd":ogcd,
				 "stat":obj.stat
			}),
			async:false,
			success:function(data){
				
				if(data.code==00){
					userdata=JSON.parse( data.codeMessage );
					ren({'cols':cols,'wh':wh,'userdata':userdata.list});
					listnumtotal=JSON.parse( data.codeMessage ).total;
					/*$('.page').remove();
					$('.boxtop').append('<div class="page"><span class="first">首页</span><span class="prev">上一页</span><span class="next">下一页</span><span class="last">末页</span><span class="nopage">第<b class="Nopage">'+userdata.pageNum+'</b>页/共<b class="Totalpage">'+userdata.pages+'</b>页</span><span class="totalpage">共<b class="total">'+userdata.total+'</b>条记录</span></div>');
				*/
				}else if(data.code==01){
					ren({'cols':cols,'wh':wh,'userdata':''});
					listnumtotal='';
				}
			}
		});
	 }
	 function renpage(){
		 if( listnumtotal ){
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
	    		    			 "cuno":$('.cusnum').val(),
	    		    			 "pageNo":obj.curr,
	    		    			 "pageSize":$('.layui-laypage-limits select option:selected').attr('value')*1,
	    		    			 "ogcd":$('.fitna').attr('tit'),
	    		    			 "stat":$('.stat input[name="aa"]:checked').attr('tit')
	    		    		 });
	    		    	}	
	    		    }
	    		  });
	    	});
		 }else{
			 $('#page').html('');
		 }
	    	
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
					horizrailenabled:obj.horizrailenabled
			  });
			 mmg.on('loadSuccess',function(){
				 if( obj.userdata.length>0){
					 $('.box tbody tr').each(function(i,v){
							$(v).find('td:eq(0)').attr('tit',obj.userdata[i].OGCD);
						});
				 }
			 });
	 }
    //添加、修改、删除
    $('.add').click(function(){
    	dialog.electricadd('electricter','add');
    	//失焦，判断客户号是否存在；
    	$('.counterem',parent.document).on('blur',function(){
    		if( $(this).val()==''){
    			$(this).closest('div').find('re').remove();
    			$(this).closest('div').append('<re>客户号不能为空!</re>');
    		}else{
    			$.ajax({
    				url:'/fx/selCunoExist.do',
    				type:'post',
    				contentType:'application/json',
    				data:JSON.stringify({
    					"userKey":userkey,
    					"cuno":$('.counterem',parent.document).val()
    				}),
    				async:true,
    				success:function(data){
    					if(data.code==01){
    						$('.counterem',parent.document).closest('div').find('re').remove();
    						$('.counterem',parent.document).attr('tit','OK');
    					}else{
    						$('.counterem',parent.document).closest('div').find('re').remove();
    						$('.counterem',parent.document).closest('div').append('<re>请更换客户号再尝试!</re>');
       					}
    				}
    			});
    		}
    	});
    	//判断结汇和购汇不能为空；
    	$('.jhyh',parent.document).on('blur',function(){
    		if( $(this).val()==''){
    			$(this).closest('div').find('re').remove();
    			$(this).closest('div').append('<re>结汇优惠不能为空!</re>');
    		}else if( $(this).val()!=''&&!/^\d+$/.test( $(this).val() )){
    			$(this).closest('div').find('re').remove();
    			$(this).closest('div').append('<re>结汇优惠必须是数字!</re>');
    		}else{
    			$(this).closest('div').find('re').remove();
    		}
    	});
    	$('.ghyh',parent.document).on('blur',function(){
    		if( $(this).val()==''){
    			$(this).closest('div').find('re').remove();
    			$(this).closest('div').append('<re>购汇优惠不能为空!</re>');
    		}else if( $(this).val()!=''&&!/^\d+$/.test( $(this).val() )){
    			$(this).closest('div').find('re').remove();
    			$(this).closest('div').append('<re>购汇优惠必须是数字!</re>');
    		}else{
    			$(this).closest('div').find('re').remove();
    		}
    	});
    });
    //点击选择按钮;
    $('body',parent.document).on('click','.choice',function(){
    	dialog.electrictfn('electrict');
    });
    $('.modify').click(function(){
    	if( $('.box tbody tr').hasClass('selected') ){
    		dialog.electricadd('electricter','modify');
    		$('.counterem',parent.document).val( $('.box tr.selected td:eq(0) span').text() );
    		$('.jhyh',parent.document).val( $('.box tr.selected td:eq(1) span').text() );
    		$('.ghyh',parent.document).val( $('.box tr.selected td:eq(2) span').text() );
    		$('.outfit',parent.document).val( $('.box tr.selected td:eq(3) span').text() );
    		$('.outfit',parent.document).attr('tit',$('.box tr.selected td:eq(0)').attr('tit'));
    		if( $('.box tr.selected td:eq(4) span').text()=='启用'){
    			$('.stat .chec',parent.document).prop('checked','checked');
    		}else{
    			$('.stat .chec1',parent.document).prop('checked','checked');
    		}
    	}else{
    		dialog.choicedata('请先选择一条数据!','electricter');
    	}    	
    });
    $('body',parent.document).on('click','.electricter .success',function(){
    	var fvjh=$('.jhyh',parent.document).val(),
    		fvgh=$('.ghyh',parent.document).val(),
    		stat=$('.stat input[name="aa"]:checked',parent.document).attr('tit'),
    		ogcd=$('.outfit',parent.document).attr('tit'),
    		cuno=$('.counterem',parent.document).val(),
    		obj={
	    		"fvjh":fvjh,
				"fvgh":fvgh,
				"stat":stat,
				"ogcd":ogcd,
				"cuno":cuno
    		}
    	if(cuno==''){			//客户号
    		$('.counterem',parent.document).closest('div').find('re').remove();
    		$('.counterem',parent.document).closest('div').append('<re>客户号不能为空!</re>');
    	}else if( $('.counterem',parent.document).attr('tit')=='OK' ){
    		$('.counterem',parent.document).closest('div').find('re').remove();
    	}
    	if(fvjh==''){			//结汇优惠
    		$('.jhyh',parent.document).closest('div').find('re').remove();
    		$('.jhyh',parent.document).closest('div').append('<re>结汇优惠不能为空!</re>');
    	}else{
    		$('.jhyh',parent.document).closest('div').find('re').remove();
    	}
    	if(fvgh==''){			//购汇优惠
    		$('.ghyh',parent.document).closest('div').find('re').remove();
    		$('.ghyh',parent.document).closest('div').append('<re>购汇优惠不能为空!</re>');
    	}else{
    		$('.ghyh',parent.document).closest('div').find('re').remove();
    	}
    	console.log( obj );
    	if( $('.electricter re',parent.document).length==0){
    		admostep( obj );
    	}
    });
    function admostep(obj){
    	var url,tit=$('.pubhead span',parent.document).text();
    	if( tit=='电子柜员-修改' ){
    		url='updCustFav.do';
    	}else{
    		url='insCustFav.do';
    	}
    	$.ajax({
			url:url,
			type:'post',
			contentType:'application/json',
			data:JSON.stringify({
				"userKey":userkey,
				"fvjh":obj.fvjh,
				"fvgh":obj.fvgh,
				"stat":obj.stat,
				"ogcd":obj.ogcd,
				"cuno":obj.cuno
			}),
			async:true,
			success:function(data){
				$('.electricter .success',parent.document).closest('.zhezhao').remove();
				if(data.code==00){
					dialog.choicedata(data.codeMessage,'electricter');
					 getlist({
						 "cuno":$('.cusnum').val(),
						 "pageNo":$('.Nopage').text(),
						 "pageSize":10,
						 "ogcd":$('.fitna').attr('tit'),
						 "stat":$('.stat input[name="aa"]:checked').attr('tit')
					 });
					 renpage();
				}else if(data.code==01){
					dialog.choicedata(data.codeMessage,'electricter');
				}	
			}
		});
    }
    $('.del').click(function(){
    	if( $('.box tbody tr').hasClass('selected') ){
    		dialog.cancelDate('您确定要删除当前记录吗？','electricter');
    	}else{
    		dialog.choicedata('请先选择一条数据!','electricter');
    	}
    });
    $('body',parent.document).on('click','.electricter .confirm',function(){
    	removedata(	$('.box tr.selected td:eq(0) span').text() );
	});
    function removedata(obj ){
    	$.ajax({
			url:'/fx/deleteCustFav.do',
			type:'post',
			async:true,
			data:JSON.stringify({
 				'userKey':userkey,
 				'cuno':obj
 			}),
 			dataType:'JSON',
 			contentType:'application/json;charset=utf-8',
			success:function(data){
				$('.electricter .confirm',parent.document).closest('.zhezhao').remove();
				if(data.code==00){	
					dialog.choicedata(data.codeMessage,'electricter');
					$('.box tr.selected').remove();
					getlist({
						 "cuno":$('.cusnum').val(),
						 "pageNo":$('.Nopage').text(),
						 "pageSize":10,
						 "ogcd":$('.fitna').val(),
						 "stat":$('.stat input[name="aa"]:checked').attr('tit')
					 });
					renpage();
				}else{
					dialog.choicedata(data.codeMessage,'electricter');
				}
			}
		});
    }
    $('body',parent.document).on('click','.electrict .close,.electricter .close,.electricter .sure,.electricter .cancel',function(){
		$(this).closest('.zhezhao').remove();
	});
    $('body',parent.document).on('click','.agentchoose .close',function(){
		$(this).closest('.discountpub').remove();
	});
    
    /*-------------------------------选择按钮弹出部分---------------------------------------*/
   //点击选择按钮
    $('.choice').click(function(){		
    	dialog.agentChoose();
    });
    //点击选择机构弹窗的查询按钮
    $('body',parent.document).on('click','.agentchoose .qurey',function(){
    	var Ognmtxt=$.trim($('.agentname',parent.document).val()),
            Ogcdtxt=$.trim($('.agentnum',parent.document).val()),
	        OgcdVo={'ogcd':Ogcdtxt,'ognm':Ognmtxt,'pageNo':1,'pageSize':10};
    	getFavRuleOgcd(OgcdVo);
	});
    //点击选择当前机构
    $('body',parent.document).on('click','.agentchoose .currentagent',function(){			
		var num=0;
    	$('.agentmain table tr',parent.document).each(function(i,v){
			if( $(v).hasClass("agentbgc")){
				num++;	
			}
		});
    	if( num==0){
			dialog.choicedata('请选择一条数据!','electricter');
		}else{
			if( $('.currentagent',parent.document).parents('.agentchoose').hasClass('zhezhao') ){
				$('.fitna').val(	$('.agentmain table tr.agentbgc td:eq(1)',parent.document).text() );
				$('.fitna').attr('tit',$('.agentmain table tr.agentbgc td:eq(0)',parent.document).text() );
			}else{
				$('.electricter .outfit',parent.document).val( $('.agentmain table tr.agentbgc td:eq(1)',parent.document).text() );
				$('.electricter .outfit',parent.document).attr('tit',$('.agentmain table tr.agentbgc td:eq(0)',parent.document).text() );
			}
			$('.agentchoose .currentagent',parent.document).closest('.agentchoose').remove();
		}
    	 /*ogcd=$('.agentnum').val(),							//客户等级优惠选择；
	     ogcdVo={'ogcd':ogcd,'pageNo':1,'pageSize':10} 
	     getOrgnlist(ogcdVo); 	
    	 $(this).closest('.zhezhao').remove();*/
    });
  //封装点击选择 请求机构
    function getFavRuleOgcd(obj){
    	$.ajax({
			url:'/fx/selectETBhid.do',
			type:'post',
			contentType:'application/json',
			data:JSON.stringify(obj),
			async:false,
			success:function(data){
			   if(data.code==00){
					var userdata=JSON.parse( data.codeMessage),
					    str="<tr><td class='ogcd'>机构号</td><td class='ognm'>机构名称</td></tr>",
					    str1='<span class="first">首页</span><span class="prevbtn">上一页</span><span class="nextbtn">下一页</span><span class="last">末页</span><span class="allnumpre">第<i class="nowpage">'+userdata.pageNum+'</i>页/共<i class="allpages">'+userdata.pages+'</i>页</span><span class="allnum">共'+userdata.total+'条记录</span>';
					pagedata=userdata; 
					for(var i in userdata.list){
				       str+="<tr><td class='one'>"+userdata.list[i].OGCD+"</td><td>"+userdata.list[i].OGNM+"</td></tr>"
					}
					
					$('.agentbot',parent.document).html(' ').append(str1);
					$('.agentmain table',parent.document).html(' ').append(str);
				}else if(data.code==01){
					 //异常
				}
			}
		});
    }
  //点击弹出框的分页的前一页和后一页 首页 末页；
	$('body',parent.document).on('click','.agentchoose .prevbtn',function(){
		prevpage=$('.nowpage',parent.document).text()-1;
		if( pagedata.hasPreviousPage!=false){
			pagenum=--pagedata.pageNum;
			var Ognmtxt=$('.agentname',parent.document).val(),
	            Ogcdtxt=$('.agentnum',parent.document).val(),
	            OgcdVo={'ogcd':Ogcdtxt,'ognm':Ognmtxt,'pageNo':pagenum,'pageSize':10};
    	        getFavRuleOgcd(OgcdVo);
		}
	});
	
	$('body',parent.document).on('click','.agentchoose .nextbtn',function(){  //点击下一页;
		prevpage=parseInt( $('.nowpage',parent.document).text() )+1;
		if(pagedata.hasNextPage!=false){
			pagenum=++pagedata.pageNum;
			var Ognmtxt=$('.agentname',parent.document).val(),
	        Ogcdtxt=$('.agentnum',parent.document).val(),
            OgcdVo={'ogcd':Ogcdtxt,'ognm':Ognmtxt,'pageNo':pagenum,'pageSize':10};
	        getFavRuleOgcd(OgcdVo);
		}
	}); 
	$('body',parent.document).on('click','.agentchoose .first',function(){   //点击首页的时候不需要初始化;再点击全县新增的时已经初始化;
		if( pagedata.hasPreviousPage!=false){
			pagenum=1;
			var Ognmtxt=$('.agentname',parent.document).val(),
	        Ogcdtxt=$('.agentnum',parent.document).val(),
            OgcdVo={'ogcd':Ogcdtxt,'ognm':Ognmtxt,'pageNo':pagenum,'pageSize':10};
	        getFavRuleOgcd(OgcdVo);
		}
	});
	$('body',parent.document).on('click','.agentchoose .last',function(){
		prevpage=parseInt( $('.allpages',parent.document).text() );
		if(pagedata.hasNextPage!=false){
			pagenum=pagedata.pages;
			var Ognmtxt=$('.agentname',parent.document).val(),
	        Ogcdtxt=$('.agentnum',parent.document).val(),
            OgcdVo={'ogcd':Ogcdtxt,'ognm':Ognmtxt,'pageNo':pagenum,'pageSize':10};
	        getFavRuleOgcd(OgcdVo);
		}
	});
	//封装请求列表
	function getOrgnlist(obj){
		$.ajax({
    		url:'/fx/getFavruleByOgcd.do',
    		type:'post',
    		contentType:'application/json',
    		data:JSON.stringify(obj),
    		async:true,
    		success:function(data){
    			if(data.code==00){
       				userdata=JSON.parse( data.codeMessage );
       				dialog.ren({'cols':cols,'wh':wh,'userdata':userdata.list,'checked':true});
       			 $('.page').remove();
    				$('.boxtop').append('<div class="page"><span class="first">首页</span><span class="prev">上一页</span><span class="next">下一页</span><span class="last">末页</span><span class="nopage">第<b class="Nopage">'+userdata.pageNum+'</b>页/共<b class="Totalpage">'+userdata.pages+'</b>页</span><span class="totalpage">共<b class="total">'+userdata.total+'</b>条记录</span></div>')
       			}else if(data.code==01){
       				dialog.ren({'cols':cols,'wh':wh,'userdata':'','checked':true});
       			}
    		}
    	}); 
	}
	$('body',parent.document).on('click','.agentmain table tr',function(){
		 $(this).addClass('agentbgc').siblings().removeClass('agentbgc');
    });
	
})