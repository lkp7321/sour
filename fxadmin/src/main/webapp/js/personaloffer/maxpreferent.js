/*个人-最大优惠设置*/
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
		ww=$(window).width()-250+"px";
	$('.boxtop').css('width',ww);
	$('.boxtop').css('height',wh);
	$('#page').css('width',ww);
	var usnm=sessionStorage.getItem('usnm'),
		product=sessionStorage.getItem('product'),
		user={'usnm':usnm,'prod':product},
		userkey=JSON.parse( sessionStorage.getItem('menu') ).userKey,
		userdata,str=''; 
	var numreg=/^\d+$|^[0]{1}\.\d+$|^[1-9]{1}\.\d+$/;
	if( product=='P001'){
		$('.head .t').html( '交易管理>最大优惠&nbsp;&nbsp;&nbsp;&nbsp;' );
	}else if(product=='P002'){
		$('.head .t').html( '参数设置>最大优惠&nbsp;&nbsp;&nbsp;&nbsp;');
		if(usnm=='Administrator' ){
	    	 $('.maxpreferential .fourtul').show();
	     }
	}else if( product=='P003'){
		$('.head .t').html( '参数设置>最大优惠&nbsp;&nbsp;&nbsp;&nbsp;');
		if(usnm=='Administrator' ){
	    	 $('.maxpreferential .fourtul').show();
	     }
	}
	getlo();
	//列参数;
    var cols = [
        { title:'机构号', name:'ogcd' ,width:80, align:'left' },
        { title:'机构名称', name:'ognm' ,width:100, align:'left'},
        { title:'货币对名称', name:'exnm' ,width:80, align:'left'},
        { title:'最大优惠点数', name:'mxfv' ,width:80, align:'right'}
    ];
    var juesedata=false,jigoudata=false,jueseda,jigouda,listnumtotal;
    function getlo(){
    	$.ajax({
        	url:'/fx/getuserOgcd.do',
        	type:'post',
    		async:true,
    		data:userkey,
    		contentType:'application/json',
    		success:function(data){
    			if(data.code==02){
    				
    			}else if(data.code==01){
    				if( data.codeMessage.leve=='2'){//如果是二级用户，就没有其他操作
    					$('.maxpreferential .fourtul').hide();
    					$('.maxpreferential .cer').hide();
    				}else if(data.codeMessage.leve==''||!data.codeMessage.leve){//获取用户机构失败
    					dialog.choicedata('判断用户所属机构等级失败!','maxprefer');
    					$('.maxpreferential .cer').hide();
    				}else{//一级机构时就继续执行
    					 //获取机构名称；
    				    $.ajax({
    				    	url:'/fx/queryMaxpavpoint.do',
    				    	type:'post',
    						async:false,
    						data:JSON.stringify({
    							'userKey':userkey,
    							'levelTy':data.codeMessage.leve
    						}),
    						contentType:'application/json',
    						success:function(data){
    							str='<option ogcd="all">所有</option>';
    							if(data.code==02){
    								dialog.choicedata('获取机构失败!','maxprefer','aaa');
    								dialog.ren({'cols':cols,'wh':wh,'userdata':''});
    							}else if(data.code==01){
    								userdata=data.codeMessage;
    								for(var i in userdata){
    									str+='<option ogcd='+userdata[i].OGCD+'>'+userdata[i].OGNM+'</option>'
    								}
    								$('.long').html( str );
    							}
    						}
    				    });
    				}
    			}
    		}
        });
    }
    getlist( {'combogcd':'all','userKey':userkey,'pageNo':1,'pageSize':10} );
    renpage();
    $('.long').change(function(){
    	//getlist( {'combogcd':$('.long option:selected').attr('ogcd'),'userKey':userkey} );
    	getlist( {'combogcd':$('.long option:selected').attr('ogcd'),'userKey':userkey,'pageNo':1,'pageSize':10} );
    	renpage();
    });
    //渲染函数；
	function getlist(obj){
	  $.ajax({
		url:'/fx/getMaxpavpoint.do',
		type:'post',
		contentType:'application/json',
		data:JSON.stringify(obj),
		async:false,
		success:function(data){
			if(data.code==00){
				dialog.ren({'cols':cols,'wh':wh,'userdata':''});
			}else if(data.code==01){
				userdata= data.codeMessage ;
				ren({'cols':cols,'wh':wh,'userdata':userdata.list});
				listnumtotal=userdata.total;
//				$('.page').remove();
//				$('.boxtop').append('<div class="page"><span class="first">首页</span><span class="prev">上一页</span><span class="next">下一页</span><span class="last">末页</span><span class="nopage">第<b class="Nopage">'+userdata.pageNum+'</b>页/共<b class="Totalpage">'+userdata.pages+'</b>页</span><span class="totalpage">共<b class="total">'+userdata.total+'</b>条记录</span></div>')
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
	    					'combogcd':$('.long option:selected').attr('ogcd'),
	    					'userKey':userkey,
	    					'pageSize':$('.layui-laypage-limits select option:selected').attr('value')*1,
	    				    'pageNo':obj.curr
		    			});
			    	}	
			    }
			  });
		});
	}
	function ren( obj ){
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
			 if( obj.userdata.length>0){
				 $('.box tbody tr').each(function(i,v){
					$(v).find('td:eq(0)').attr('excd',obj.userdata[i].excd);
					$(v).find('td:eq(0)').attr('ogcd',obj.userdata[i].ogcd);
				 });
			 }
		 });
	}
	//点击添加、修改、删除；
	$('.add').click(function(){
		var tit=$('.loog option:selected').text(); 
		if( tit=='所有'){
			dialog.choicedata('请更改机构再进行此操作!','maxprefer');
		}else{
			dialog.maxprefn('maxprefer','add');
			getlist( {'combogcd':$('.long option:selected').attr('ogcd'),'userKey':userkey,'pageNo':$('.Nopage').text(),'pageSize':10} );
			getflist();
			
			$('.fitname',parent.document).val( $('.long option:selected').attr('ogcd') );
			$('.asknum',parent.document).val( $('.long option:selected').text() );
			//最大优惠点数修改；
			$('.maxprefer .dealrate',parent.document).on('blur',function(){

				if( $(this).val()==''){
					$(this).closest('div').find('re').remove();
					$(this).closest('div').append('<re>最大优惠点数不能为空!</re>');
				}else if(! numreg.test( $(this).val()) ){
					$(this).closest('div').find('re').remove();
					$(this).closest('div').append('<re>最大优惠点数不能为非数字!</re>');
				}else{
					$(this).closest('div').find('re').remove();
				}
			});
		}
	});
	$('.modify').click(function(){
		 if( $('.box tbody tr').hasClass('selected') ){
			 dialog.maxprefn('maxprefer','modify');
			 $('.maxprefer .dealrate',parent.document).val( $('.box tr.selected td:eq(3) span').text() );
			 getflist();
				
			$('.fitname',parent.document).val( $('.box tr.selected td:eq(0) span').text() );
			$('.asknum',parent.document).val($('.box tr.selected td:eq(1) span').text());
			 $('span.kagkau',parent.document).text( $('.box tr.selected td:eq(2) span').text() );
			 $('span.kagkau',parent.document).attr('excd',$('.box tr.selected td:eq(0)').attr('excd') );
			//最大优惠点数修改；
			$('.maxprefer .dealrate',parent.document).on('blur',function(){
				if( $(this).val()==''){
					$(this).closest('div').find('re').remove();
					$(this).closest('div').append('<re>最大优惠点数不能为空!</re>');
				}else if(! numreg.test( $(this).val()) ){
					$(this).closest('div').find('re').remove();
					$(this).closest('div').append('<re>最大优惠点数不能为非数字!</re>');
				}else{
					$(this).closest('div').find('re').remove();
				}
			});
		 }else{
			dialog.choicedata('请先选择一条数据!','maxprefer');
		}
	});
	
	//获取弹出框数据；
	function getflist(){
		var str='';
			ogcd=$('.long option:selected').attr('ogcd'),
			ognm=$('.long option:selected').text();
		$.ajax({
    		url:'/fx/selMaxExnm.do',
    		type:'post',
    		contentType:'application/json',
    		async:true,
    		data:JSON.stringify({'ogcd':ogcd,'userKey':userkey}),
    		success:function(data){
    			if(data.code==01){
    				userdata= data.codeMessage;
    				for(var i in userdata){
    					str+='<option excd='+userdata[i].EXCD+'>'+userdata[i].EXNM+'</option>'
    				}
    				$('select.kagkau',parent.document).html(str);
    			}else{
    				dialog.choicedata(data.codeMessage,'maxprefer');
    			}
    		}
    	});
		
	}
	//点击保存；
	$('body',parent.document).on('click','.maxprefer .succss',function(){
		var a=$('.maxprefer .dealrate',parent.document).val(),excd,exnm;
		if( a==''){
			$('.maxprefer .dealrate',parent.document).closest('div').find('re').remove();
			$('.maxprefer .dealrate',parent.document).closest('div').append('<re>最大优惠点数不能为空!</re>');
		}else if( !numreg.test(a) ){
			$('.maxprefer .dealrate',parent.document).closest('div').find('re').remove();
			$('.maxprefer .dealrate',parent.document).closest('div').append('<re>最大优惠点数必须为非数字!</re>');
		}else{
			$('.maxprefer .dealrate',parent.document).closest('div').find('re').remove();
			var txt=$('.pubhead span',parent.document).text();
			if( txt=='最大优惠-添加'){
				url='addMaxPoint.do';
				exnm=$('.kagkau option:selected',parent.document).text(),
				excd=$('.kagkau option:selected',parent.document).attr('excd');
			}else{
				url='upMaxPoint.do';
				exnm=$('.kagkau',parent.document).text(),
				excd=$('.kagkau',parent.document).attr('excd');
			}
			var mxfv=Math.round( $('.dealrate',parent.document).val() ),
				ogcd=$('.fitname',parent.document).val();
			$.ajax({
	    		url:url,
	    		type:'post',
	    		contentType:'application/json',
	    		async:true,
	    		data:JSON.stringify({
	    				userKey:userkey,
	    				maxpoint:{
			    			exnm:exnm,
			    			excd:excd,
			    			ogcd:ogcd,
			    			mxfv:mxfv
			    		}}),
	    		success:function(data){
	    			$('.maxprefer .succss',parent.document).closest('.zhezhao').remove();
						if(data.code==01){
							dialog.choicedata(data.codeMessage,'maxprefer');
							getlist( {'combogcd':$('.long option:selected').attr('ogcd'),'userKey':userkey,'pageNo':$('.Nopage').text(),'pageSize':10} );
							renpage();
						}else{
							dialog.choicedata(data.codeMessage,'maxprefer');	
						}
	    		}
	    	});
		}
	});
	$('.del').click(function(){
		if( $('.box tbody tr').hasClass('selected') ){
			//删除弹出框;
			dialog.cancelDate('您确定要删除当前记录吗？','maxprefer');
		 }else{
			dialog.choicedata('请先选择一条数据!','maxprefer');
		}
	});
	//点击删除数据 确认+ajax;
	$('body',parent.document).on('click','.maxprefer .confirm',function(){
		var ogcd=$('.box tr.selected td:eq(0) span').text(),
			exnm=$('.box tr.selected td:eq(2) span').text();
		$.ajax({
			url:'/fx/deMaxPoint.do',
			type:'post',
			async:true,
			contentType:'application/json',
    		data:JSON.stringify({'maxpoint':{'ogcd':ogcd,'exnm':exnm},'userKey':userkey}),
			success:function(data){//this指向；
				$('.maxprefer .confirm',parent.document).closest('.zhezhao').remove();
				if(data.code==01){
					dialog.choicedata(data.codeMessage,'maxprefer','aaa');
					$('.box tr.selected').remove();
					getlist( {'combogcd':$('.long option:selected').attr('ogcd'),'userKey':userkey,'pageNo':$('.Nopage').text(),'pageSize':10} );
					renpage();
				}else{
					dialog.choicedata(data.codeMessage,'maxprefer','aaa');
				}
			}
		});	
	});	
	 $('body',parent.document).on('click','.maxprefer .sure',function(){
		//关闭选择一条数据;
		 $(this).closest('.zhezhao').remove(); 
	 });
	/*----------------快速搜索功能的实现------------------------*/
	$('.info_serbtn').click(function(){
		  dialog.serchData($('.info_seript').val(),'.mmg-headWrapper');
	});
	//点击添加修改弹出框中的x、取消按钮；
	$('body',parent.document).on('click','.maxprefer .close,.maxprefer .sure,.maxprefer .faile,.maxprefer .cancel',function(){
		$(this).closest('.zhezhao').remove();
	});
	//点击分页;
    $('.boxtop').on('click','.first',function(){
    	var Nopage=$('.Nopage').text()*1;
 	    if(Nopage>1){
 	    	Nopage=1;
 	    	getlist( {'combogcd':$('.long option:selected').attr('ogcd'),'userKey':userkey,'pageNo':Nopage,'pageSize':10} );
 	    }
    });
	$('.boxtop').on('click','.prev',function(){
	    var Nopage=$('.Nopage').text()*1;
	    if(Nopage>1){
	    	Nopage=Nopage-1;
	    	getlist( {'combogcd':$('.long option:selected').attr('ogcd'),'userKey':userkey,'pageNo':Nopage,'pageSize':10} );
	    }
	});
	$('.boxtop').on('click','.next',function(){
		var Nopage=$('.Nopage').text()*1,
			Totalpage=$('.Totalpage').text()*1;
	    if(Nopage<Totalpage){
	    	Nopage=Nopage*1+1;
	    	getlist( {'combogcd':$('.long option:selected').attr('ogcd'),'userKey':userkey,'pageNo':Nopage,'pageSize':10} );
	    }
	});
	$('.boxtop').on('click','.last',function(){
		var Nopage=$('.Nopage').text()*1,
		Totalpage=$('.Totalpage').text()*1;
	    if(Nopage<Totalpage){
	    	Nopage=Totalpage;
	    	getlist( {'combogcd':$('.long option:selected').attr('ogcd'),'userKey':userkey,'pageNo':Nopage,'pageSize':10} );
	    }
	});
})

