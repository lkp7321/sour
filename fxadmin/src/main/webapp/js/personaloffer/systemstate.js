/*系统状态*/
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
		dialog:'dialog'
    }
})
require(['jquery','mmGrid','niceScroll','dialog'],function($,mmGrid,niceScroll,dialog){
	 var wh=$(window).height()-190+"px",
	    ww=$(window).width()-250+"px";;
	$('.boxtop').css('width',ww);
	$('.boxtop').css('height',wh);
	//列参数;
    var cols = [
        { title:'交易帐号', name:'trac' ,width:150, align:'left' },
        { title:'卡号', name:'cuac' ,width:150, align:'left'},
        { title:'使用状态', name:'usfg' ,width:150, align:'center'}
    ];
    var usnm=sessionStorage.getItem('usnm'),
		product=sessionStorage.getItem('product'),
		userkey=JSON.parse( sessionStorage.getItem('menu') ).userKey,
		user={'usnm':usnm,'prod':product},
		userdata;
    if( product=="P001"){
    	$('.platecontro').show();
    }
    //渲染页面数据；
    renlist();
    function renlist(){
      $.ajax({
			url:'/fx/getAllTesttrac.do',
			type:'post',
			contentType:'application/json',
			data:userkey,
			async:true,
			success:function(data){
				if(data.code==01){
					userdata= data.codeMessage ;
					dialog.ren({'cols':cols,'wh':wh,'userdata':userdata});
				}else if(data.code==00){
					dialog.ren({'cols':cols,'wh':wh,'userdata':''});
				}
			}
		});
    }
    //获取当前页面状态；
    $.ajax({
		url:'/fx/initcon.do',
		type:'post',
		contentType:'application/json',
		data:userkey,
		async:true,
		success:function(data){
				if(data[0]=='开启'){
					$('.allcontro input[value="sta"]').prop('checked','checked');
				}else if(data[0]=='关闭'){
					$('.allcontro input[value="clos"]').prop('checked','checked');
				}else if(data[0]=='测试'){
					$('.allcontro input[value="test"]').prop('checked','checked');
				}
				if(data[1]=='开启'){
					$('.platecontro input[value="platsta"]').prop('checked','checked');
				}else if(data[1]=='关闭'){
					$('.platecontro input[value="platclos"]').prop('checked','checked');
				}		
		}
	});
	//点击添加、修改、删除按钮;
	$('.add').click(function(){
		dialog.systemadd('add','systemstate');
		$('.systemstate .card',parent.document).keyup(function(){
			$('.card',parent.document).val( $('.card',parent.document).val().replace(/[^0-9]/g,'') );
		});
		$('.systemstate .card',parent.document).blur(function(){
				var card=$('.systemstate .card',parent.document).val();
				if(card==''||card==undefined){
					$('.card',parent.document).parents('p').append('<re>请填写卡号</re>');
				}else{
					$('.card',parent.document).parents('p').find('re').remove();
				}
		});
	});
	$('.systemstate .modify').click(function(){
		if( $('.box tbody tr').hasClass('selected') ){
			dialog.systemadd('modify','systemstate',$('.box tr.selected td:eq(1) span').text());
			checkradio( $('.box tr.selected td:eq(2)').text() );			
		}else{
			dialog.choicedata('请先选择一条数据!','systemstate');
		}
		
	});
	$('.systemstate .del').click(function(){
		if( $('.box tbody tr').hasClass('selected') ){
			dialog.cancelDate('您确定要删除当前记录吗?','systemstate');
		}else{
			dialog.choicedata('请先选择一条数据!','systemstate');
		}
	});
	
	//点击添加、修改中的确定按钮;
	$('body',parent.document).on('click','.systemstate .sav',function(){
		var stat=$('.systemstate .box',parent.document).data('stat'),card,choicestate;   //card是卡号，choicedate是选中状态;
			choicestate=$('.systemstate .cen input[name="aa"]:checked',parent.document).data('tit');
			if( choicestate=='star'){
				choicestate=0;
			}else{
				choicestate=1;
			}
			card=$('.systemstate .card',parent.document).val();
				if(stat=='add'){
					if(card==''||!card){
						$('.card',parent.document).parents('p').append('<re>请填写卡号</re>');
						return;
					}else{
						//ajax 操作;
						systemadd({'url':'addTesttrac.do','data':{userKey:userkey,testtrac:{cuac:card,usfg:choicestate } }});
					}
				}else{
					card=$('.systemstate .cardnum',parent.document).text();
					systemadd({'url':'upTesttrac.do','data':{userKey:userkey,testtrac:{cuac:card,usfg:choicestate } }});
				}
				$(this).closest('.zhezhao').remove();
	});
	//添加和修改一条数据中的ajax;
	function systemadd(obj){
		$.ajax({
				url:obj.url,
				type:'post',
				contentType:'application/json',
				data:JSON.stringify(obj.data),
				async:false,
				success:function(data){	
					if(data.code=='01'){
						dialog.choicedata(data.codeMessage,'systemstate');
						renlist();
					}else{
						dialog.choicedata(data.codeMessage,'systemstate');
					}
				}
			});
	}
	/*确认删除*/
	$('body',parent.document).on('click','.systemstate .confirm',function(){
		//调用接口,删除后台数据;
		$.ajax({
			url:'/fx/delTesttrac.do',
			type:'post',
			contentType:'application/json',
			data:JSON.stringify({userKey:userkey,cuac:$('.box tr.selected td:eq(1) span').text()}),
			async:true,
			success:function(data){	
				$('.systemstate .confirm',parent.document).closest('.zhezhao').remove();
				if(data.code=='01'){
					dialog.choicedata(data.codeMessage,'systemstate');
					$('.box tr.selected').remove();
					renlist();
				}else{
					dialog.choicedata(data.codeMessage,'systemstate');
				}
			}
		});
	});
	$('body',parent.document).on('click','.systemstate .cancel,.systemstate .close,.systemstate .sure',function(){
		$(this).closest('.zhezhao').remove();
	});
	//改变当前总控和平盘总控状态；
	$('.systemstate .allcontro input[type="radio"]').click(function(){
		if( $(this).prop('checked')==true){
			var sta=$(this).attr('value');
			if(sta=='sta'){
				sta=0;
			}else if(sta=='clos'){
				sta=1;
			}else if(sta=='test'){
				sta=2;
			}
			changestate ( {'url':'upSyscon.do','data':{userKey:userkey,sysctl:{usfg:sta}}} );
		}
	});
	$('.systemstate .platecontro input[type="radio"]').click(function(){
		if( $(this).prop('checked')==true){
			var platsta=$(this).attr('value');
			if(platsta=='platsta'){
				platsta=0;
			}else if(platsta=='platclos'){
				platsta=1;
			}
			changestate( {'url':'upPpStatcon.do','data':{userKey:userkey,sysctl:{valu:platsta} }});
		}
	});
	function changestate(obj){
		$.ajax({
			url:obj.url,
			type:'post',
			contentType:'application/json',
			data:JSON.stringify(obj.data),
			async:false,
			success:function(data){	
				if(data.code=='01'){
					dialog.choicedata('修改成功!','systemstate');
				}else{
					dialog.choicedata('修改失败!','systemstate');
				}
			}
		});
	}
	//根据用户界面判断弹出层中的是否选中；  更改这块的内容;
	function checkradio(obj){
		if( obj=='启用'){
			$('.start',parent.document).prop('checked','checked');
		}else{
			$('.nostart',parent.document).prop('checked','checked');
		}
	}
});

