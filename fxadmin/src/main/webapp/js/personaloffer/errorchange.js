/*系统-错误码*/
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
		$('.boxtop').css('width',ww);
		$('.boxtop').css('height',wh);
		$('#page').css('width',ww);
	var numreg=/^\d+$|^[0]{1}\.\d+$|^[1-9]{1}\.\d+$/,
		listnumtotal,
		errorreg=/^[A-Z]{1,}\d+$/;
		userkey=JSON.parse( sessionStorage.getItem('menu') ).userKey;
	
    var usnm=sessionStorage.getItem('usnm'),
		product=sessionStorage.getItem('product'),
		user={'usnm':usnm,'prod':product},
		userdata;
	var title=$('title').text();
    
    if(title=="错误码管理"){
    	var cols = [
             { title:'错误码', name:'ercd' ,width:250, align:'left' },
             { title:'错误码说明(外部)', name:'ertx' ,width:300, align:'left'},
             { title:'错误显示(内部)', name:'erxs' ,width:300, align:'left'}
         ];
    	thrfn({userKey:userkey,strTxt:$('.errcode').val(),strTxt1:$('.errmark').val(),'pageSize':10,'pageNo':1} );
    	renpage();
    }
    function thrfn(obj){
    	//请求数据; 
        $.ajax({
    		url:'/fx/getMserrors.do',
    		type:'post',
    		contentType:'application/json',
    		data:JSON.stringify(obj),
    		async:false,
    		success:function(data){
    			if(data.code==01){
    				userdata= data.codeMessage;
    				dialog.ren({'cols':cols,'wh':wh,'userdata':userdata.list});
    				listnumtotal=userdata.total;
    				//$('.page').remove();
    				//$('.boxtop').append('<div class="page"><span class="first">首页</span><span class="prev">上一页</span><span class="next">下一页</span><span class="last">末页</span><span class="nopage">第<b class="Nopage">'+userdata.pageNum+'</b>页/共<b class="Totalpage">'+userdata.pages+'</b>页</span><span class="totalpage">共<b class="total">'+userdata.total+'</b>条记录</span></div>')
    			}else if(data.code==00){
    				dialog.ren({'cols':cols,'wh':wh,'userdata':''});
    			}
    		}
    	});
    }
    function thrfn1(obj){
    	//请求数据;
        $.ajax({
    		url:obj.url,
    		type:'post',
    		contentType:'application/json',
    		data:obj.data,
    		async:true,
    		success:function(data){
    			if(data.code==01){
    				userdata= data.codeMessage;
    				dialog.ren({'cols':cols,'wh':wh,'userdata':userdata});
    			}else if(data.code==00){
    				dialog.ren({'cols':cols,'wh':wh,'userdata':''});
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
    		    		thrfn({
    		    			userKey:userkey,
    		    			strTxt:$('.errcode').val(),
    		    			strTxt1:$('.errmark').val(),
    		    			'pageSize':$('.layui-laypage-limits select option:selected').attr('value')*1,
    		    			'pageNo':obj.curr
    		    		});
    		    	}	
    		    }
    		  });
    	});
    }
//点击搜索（错误码 的搜索）;
$('.search').click(function(){
	thrfn({
		userKey:userkey,
		strTxt:$('.errcode').val(),
		strTxt1:$('.errmark').val(),
		'pageSize':$('.layui-laypage-limits select option:selected').attr('value')*1,
		'pageNo':$('.layui-laypage-curr em:last').text()
	});
    renpage();
});
$('body',parent.document).on('click','.errorcode .sure',function(){
	$(this).closest('.zhezhao').remove();
	$(".mmGrid .mmg-bodyWrapper tr ").show();
});
/*----------------快速搜索功能的实现------------------------*/
$('.info_serbtn').click(function(){
	  var txt=$('.info_seript').val();
	  dialog.serchData(txt);
});
//点击添加、修改、删除；
$('.add').click(function(){
	dialog.errorCodeAdd('errorchange','add');
	$('.errorchange .ercd',parent.document).on('blur',function(){  ///错误码+内部说明+外部说明；
		if( $(this).val()==''){
			$(this).closest('div').find('re').remove();
			$(this).closest('div').append('<re>错误码不能为空!</re>');
		}else if(! errorreg.test( $(this).val()) ){
			$(this).closest('div').find('re').remove();
			$(this).closest('div').append('<re>错误码必须是大写字母和数字的组合!</re>');
		}else{
			$(this).closest('div').find('re').remove();
		}
	});
	$('.errorchange .ertx',parent.document).on('blur',function(){
		if( $(this).val()==''){
			$(this).closest('div').find('re').remove();
			$(this).closest('div').append('<re>外部说明不能为空!</re>');
		}else{
			$(this).closest('div').find('re').remove();
		}
	});
	$('.errorchange .erin',parent.document).on('blur',function(){
		if( $(this).val()==''){
			$(this).closest('div').find('re').remove();
			$(this).closest('div').append('<re>内部说明不能为空!</re>');
		}else{
			$(this).closest('div').find('re').remove();
		}
	});
});

$('.modify').click(function(){
	 if( $('.box tbody tr').hasClass('selected') ){
		 dialog.errorCodeAdd('errorchange','modify');
			$('.errorchange .ercd',parent.document).val( $('.box tr.selected td:eq(0) span').text() );
			$('.errorchange .ertx',parent.document).val( $('.box tr.selected td:eq(1) span').text() );
			$('.errorchange .erin',parent.document).val( $('.box tr.selected td:eq(2) span').text() );
			
			$('.errorchange .ercd',parent.document).on('blur',function(){  ///错误码+内部说明+外部说明；
				if( $(this).val()==''){
					$(this).closest('div').find('re').remove();
					$(this).closest('div').append('<re>错误码不能为空!</re>');
				}else if(! errorreg.test( $(this).val()) ){
					$(this).closest('div').find('re').remove();
					$(this).closest('div').append('<re>错误码必须是大写字母和数字的组合!</re>');
				}else{
					$(this).closest('div').find('re').remove();
				}
			});
			$('.errorchange .ertx',parent.document).on('blur',function(){
				if( $(this).val()==''){
					$(this).closest('div').find('re').remove();
					$(this).closest('div').append('<re>外部说明不能为空!</re>');
				}else{
					$(this).closest('div').find('re').remove();
				}
			});
			$('.errorchange .erin',parent.document).on('blur',function(){
				if( $(this).val()==''){
					$(this).closest('div').find('re').remove();
					$(this).closest('div').append('<re>内部说明不能为空!</re>');
				}else{
					$(this).closest('div').find('re').remove();
				}
			});
	 }else{
		dialog.choicedata('请先选择一条数据!','errorchange');
	}
});
//点击保存；
$('body',parent.document).on('click','.errorchange .preserve',function(){
	var ercd=$('.ercd',parent.document).val(),
		ertx=$('.ertx',parent.document).val(),
		erin=$('.erin',parent.document).val();//内部说明

	 if(ercd==''){
		$('.errorchange .ercd',parent.document).closest('div').find('re').remove();
		$('.errorchange .ercd',parent.document).closest('div').append('<re>错误码不能为空!</re>');
	}else if( !errorreg.test(ercd) ){
		$('.errorchange .ercd',parent.document).closest('div').find('re').remove();
		$('.errorchange .ercd',parent.document).closest('div').append('<re>错误码必须是大写字母和数字的组合!</re>');
	}
	 if(ertx=='' ){
		$('.errorchange .ertx',parent.document).closest('div').find('re').remove();
		$('.errorchange .ertx',parent.document).closest('div').append('<re>外部说明不能为空!</re>');
	}
	 if( erin=='' ){
		$('.errorchange .erin',parent.document).closest('div').find('re').remove();
		$('.errorchange .erin',parent.document).closest('div').append('<re>内部说明不能为空!</re>');
	}
	 if( $('.errorchange re',parent.document).length==0 ){
		$('.errorchange',parent.document).closest('div').find('re').remove();
		
		var txt=$('.pubhead span',parent.document).text();
		if( txt=='错误码管理-添加'){
			url='/fx/addMserrors.do';
		}else{
			url='/fx/updateMserrors.do';
		}
		$.ajax({
    		url:url,
    		type:'post',
    		dataType:'html',
    		async:false,
    		data:{
    			ercd:ercd,
    			ertx:ertx,
    			erin:erin,
    			userKey:userkey
    		},
    		success:function(data){
    			var da=JSON.parse(data);
    				$('.errorchange .preserve',parent.document).closest('.zhezhao').remove();
					if(da.code=='01'){
						dialog.choicedata(da.codeMessage,'errorchange');
						thrfn({userKey:userkey,strTxt:$('.errcode').val(),strTxt1:$('.errmark').val(),pageNo:$('.layui-laypage-curr em:last').text(),pageSize:$('.layui-laypage-limits select option:selected').attr('value')*1} );
						renpage();
					}else{
						dialog.choicedata(da.codeMessage,'errorchange');	
					}
    		}
    	});
	}
});
$('.del').click(function(){
	if( $('.box tbody tr').hasClass('selected') ){
		//删除弹出框;
		dialog.cancelDate('您确定要删除当前记录吗？','errorchange');
	 }else{
		dialog.choicedata('请先选择一条数据!','errorchange');
	}
});
//点击删除数据 确认+ajax;
$('body',parent.document).on('click','.errorchange .confirm',function(){
	var exnm=$('.box tr.selected td:eq(0) span').text();
	$.ajax({
		url:'/fx/deleteMserrors.do',
		type:'post',
		async:true,
		dataType:'html',
		data:{
			'userKey':userkey,
			'ercd':exnm
		},
		success:function(data){//this指向；
			var da=JSON.parse( data );
			$('.errorchange .confirm',parent.document).closest('.zhezhao').remove();
			if(da.code==01){
				dialog.choicedata(da.codeMessage,'errorchange','aaa');
				$('.box tr.selected').remove();
				thrfn({userKey:userkey,strTxt:$('.errcode').val(),strTxt1:$('.errmark').val(),pageNo:$('.layui-laypage-curr em:last').text(),pageSize:$('.layui-laypage-limits select option:selected').attr('value')*1} );
				renpage();
			}else{
				dialog.choicedata(da.codeMessage,'errorchange','aaa');
			}
		}
	});	
});	
//点击添加修改弹出框中的x、取消按钮；
$('body',parent.document).on('click','.errorchange .close,.errorchange .sure,.errorchange .faile,.errorchange .cancel',function(){
	$(this).closest('.zhezhao').remove();
});
});

