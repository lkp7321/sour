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
	/* 样式修改 ，判断利率是否存在 */
	//列参数;
    var cols = [
            { title:'年月', name:'TRDT',width:100,align:'right' },
            { title:'币种', name:'CYEN',width:100, align:'left'},
            { title:'名称', name:'COUT',width:100, align:'left'},
            { title:'利率', name:'PRICE',width:100, align:'right'}
    ];
    //获取产品和用户名；
    var usnm=sessionStorage.getItem('usnm'),
		product=sessionStorage.getItem('product'),
		user={'usnm':usnm,'prod':product},
		userdata;
    var userkey=JSON.parse( sessionStorage.getItem('menu') ).userKey,
    	tit=$('title').text();
    var thrreg=/^[a-zA-Z]{3}$/;
    getlist(
    		$('#selcymsg').val()
    );
    $.ajax({
		url:'/fx/jsh/getDtCyMsg.do',
		type:'get',
		contentType:'application/json',
		async:true,
		success:function(data){
			var str='';
			if(data.code==01){
				userdata= data.codeMessage;
				for(var i in userdata){
					str+='<option value='+userdata[i].CYEN+'>'+userdata[i].CYCN+'</option>'
				}
				$('#selcymsg option').after( str );
			}else {
			}
		}
	});
    $('#selcymsg').change(function(){
    	var obj=$('#selcymsg option:selected').attr('value')
    	getlist(obj);
    });
    $('body',parent.document).on('click','.dtparpublic .close,.dtparpublic .cancel,.custome .twosure',function(){
		  $(this).closest('.zhezhao').remove();
	});
    //点击查询按钮;
    $('.pub_button').click(function(){
    	var obj=$('#selcymsg option:selected').attr('value');
    	getlist(obj);
    });
    function getlist( obj ){
    	$.ajax({
    		url:'/fx/jsh/getListSafePrice.do',
    		type:'post',
    		contentType:'application/json',
    		data:obj,
    		async:true,
    		success:function(data){
    			if(data.code==01){
    				userdata= data.codeMessage;
    				dialog.ren({'cols':cols,'wh':wh,'userdata':data.codeMessage});
    			}else {
    				dialog.ren({'cols':cols,'wh':wh,'userdata':''});
    			}
    		}
    	});
    }
    //添加；
    $('.add').click(function(){
		dialog.ratemanafn('ratemana','add','折美元汇率');
		$.ajax({
			url:'/fx/jsh/getDtCyMsg.do',
			type:'get',
			contentType:'application/json',
			async:true,
			success:function(data){
				var str='';
				if(data.code==01){
					userdata= data.codeMessage;
					for(var i in userdata){
						str+='<option value='+userdata[i].CYEN+'>'+userdata[i].CYCN+'</option>'
					}
					 $('.onesel',parent.document).html( str );
					 $('.namecode',parent.document).text( $('.onesel option:selected',parent.document).attr('value') );
				}else {
				}
			}
		});
		 $('.namecode',parent.document).text( $('.onesel option:selected',parent.document).attr('value') );
	});
    $('body',parent.document).on('change','.onesel',function(){
    	$('.namecode',parent.document).text( $('.onesel option:selected',parent.document).attr('value') );
    });
    $('body',parent.document).on('click','.preserve',function(){
	    var currency=$('.onesel option:selected',parent.document).text(),
	        byds= $('.namecode',parent.document).text(),
	        vo={userKey:userkey,entity:{cyen:byds,cout:currency}};
	    //判断客户买入外币优惠折扣不能为空不能大于100
			/*if(currency==''||currency==undefined){
				$('.cuurpair',parent.document).parent('div').find('re').remove();
				$('.cuurpair',parent.document).parent('div').append('<re>币种不能为空</re>');
			}else if( !thrreg.test( currency) ){
				$('.cuurpair',parent.document).parent('div').find('re').remove();
				$('.cuurpair',parent.document).parent('div').append('<re>币种必须是三位英文</re>');
			}else{
				$('.cuurpair',parent.document).parent('div').find('re').remove();
			} 
			if(byds==''||byds==undefined){
				$('.nam',parent.document).parent('div').find('re').remove();
				$('.nam',parent.document).parent('div').append('<re>名称为空</re>');
			}else{
				$('.nam',parent.document).parent('div').find('re').remove();
			} */
	    
			if( $('.ratemana re',parent.document).length==0){
				$.ajax({
					url:'/fx/jsh/addSafePrice.do',
					type:'post',
					contentType:'application/json',
					data:JSON.stringify(vo),
					async:true,
					success:function(data){
						 $('.preserve',parent.document).closest('.zhezhao').remove();
						 if(data.code==01){
							 var Nopage=parseInt($('.Nopage').text()),
							 ogcdVo={
				    		 	'exnm':$('.sign select option:selected').attr('exnm'),
				    		 	'trfg':$('.currency select option:selected').attr('value'),
				    		 	'pageSize':10,
				    		 	'pageNo':Nopage,
				    		 	userKey:userkey
				 	    	};
				 	    	getOrgnlist(ogcdVo);
						 	 dialog.choicedata(data.codeMessage,'custome');
						 }else{
							//币种已存在
							 dialog.choicedata(data.codeMessage,'custome');
						 }
					}
				}); 
			}
	 });
});