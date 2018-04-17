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
});
require(['jquery','mmGrid','niceScroll','dialog'],function($,mmGrid,niceScroll,dialog){
	
	var wh=$(window).height()-190+"px",
	ww=$(window).width()-250+"px";;
	var listdata,str='',str1='';
	$('.boxtop').css('width',ww);
	$('.boxtop').css('height',wh);
	$(window).resize(function(){
		$('.boxtop').css('width',$(window).width()-300+'px');
		$('.boxtop').css('height',$(window).height()-300+'px');
	});
	var usnm=sessionStorage.getItem('usnm'),
		product=sessionStorage.getItem('product'),
		user={'usnm':usnm,'prod':product},
		prod,firstsele,lastsele,usdlist,
		userkey=JSON.parse( sessionStorage.getItem('menu') ).userKey;;
	var numreg=/^\d+$/,
		_sreg=/\s?/;
	//列参数;
    var cols = [
        { title:'敞口名称', name:'cknm' ,width:160,lockWidth:true, align:'left' },
        { title:'敞口编号', name:'ckno' ,width:80,lockWidth:true, align:'left'},
        { title:'级别', name:'leve' ,width:80,lockWidth:true, align:'right'},
        { title:'状态', name:'usfg' ,width:80,lockWidth:true, align:'center'},
        { title:'规则描述', name:'ruhy' ,width:150, align:'left'},
        { title:'规则表达式', name:'rule' ,width:200, align:'left'}
    ];
    oprurender();
    function oprurender(){
    	$.ajax({
        	url:'/fx/selck_rulet.do',
        	type:'post',
    		contentType:'application/json',
    		data:userkey,
    		success:function(data){
    			if( data.code=='00'){
    				 //当前没有数据；
    			}else if(data.code=='01'){
    				dialog.ren({'cols':cols,'wh':wh,'userdata':data.codeMessage});
    			}
    		}
        });
    }
	var mmg = $('.box').mmGrid({
			 cols: cols
            , nowrap:true
            , fullWidthRows:true
            ,showBackboard:true
       
  	});
    $(".mmg-bodyWrapper").niceScroll({
			touchbehavior:false,
			cursorcolor:"#666",
			cursoropacitymax:0.7,
			cursorwidth:6,
			background:"#ccc",
			autohidemode:false,
			horizrailenabled:false
	});
	switch(product){
		case 'P001':prod='外汇实盘交易平台';break;
		case 'P002':prod='纸黄金交易管理平台';break;
		case 'P003':prod='积存金管理平台';break;
		case 'P004':prod='个人结售汇';break;
		case 'P007':prod='账户交易';break;
		case 'P009':prod='保证金前置';break;
		case 'P998':prod='RV前置';break;
		case 'P999':prod='报价平台';break;
	}
	$.ajax({
		url:'/fx/selck_Dictionary.do',
		type:'post',
		async:true,
		contentType:'application/json',
		data:userkey,
		success:function(data){
			if( data.code=='00'){
				 //当前没有数据；
			}else if(data.code=='01'){
				listdata= data.codeMessage;
				firstsele=listdata;
			}
		},
		complete:function(){
			//添加和修改控制面板中的请求；
			$.ajax({
				url:'/fx/selTrdCustType.do',
				type:'post',
				async:false,
				contentType:'application/json',
				data:userkey,
				success:function(data){
					if( data.code=='00'){
						 //当前没有数据；
					}else if(data.code=='01'){
						listdata=data.codeMessage;
						lastsele=listdata;
					}
				}
			});
			$.ajax({
				url:'/fx/selckUsd.do',
				type:'post',
				async:false,
				contentType:'application/json',
				data:userkey,
				success:function(data){
					if( data.code=='00'){
						 //当前没有数据；
					}else if(data.code=='01'){
						listdata= data.codeMessage;
						usdlist=listdata;
					}
				}
			});	
		}
	});
	//点击添加；
	$('.add').click(function(){
		str='';
		dialog.openadd('openrule','add');
		
		$('.produname',parent.document).val(prod);
		
		for(var i=0,num=firstsele.length;i<num;i++){
			str+='<option value='+firstsele[i].zcen+'>'+firstsele[i].zdhy+'</option>'
		}
		$('.openrule .firstsel',parent.document).html(str);
		
		changelast( lastsele,0);
		
		$('.opennumber',parent.document).blur(function(){
			var _val=$(this).val().trim();
			if(_val==''){
				$(this).closest('p').find('re').remove();
				$(this).closest('p').append('<re>请输入敞口编号</re>');
			}else if(_val!=''&&!numreg.test(_val) ){
				$(this).closest('p').find('re').remove();
				$(this).closest('p').append('<re>请输入数字</re>');
			}else{
				$(this).closest('p').find('re').remove();
			}
		});
		$('.openname',parent.document).blur(function(){
			var _val=$(this).val().trim();
			if(_val==''){
				$(this).closest('p').find('re').remove();
				$(this).closest('p').append('<re>请输入敞口名称</re>');
			}else{
				$(this).closest('p').find('re').remove();
			}
		});
		//敞口级别
		$('.openlevel',parent.document).blur(function(){
			if( _sreg.test( $(this).val() ) ){
				$(this).val('0');
			}else if( $(this).val()>100||$(this).val()<0 ){
				$(this).val('0');
			}
		});
		//点击lower和up
		$('.up',parent.document).click(function(){
			var _val=$('.openlevel',parent.document).val();
			if(_val>=100){
				_val=_val;
			}else{
				_val++;
			}  
			$('.openlevel',parent.document).val( _val );
		});
		$('.lower',parent.document).click(function(){
			var _val=$('.openlevel',parent.document).val();
			if(_val<=0){
				_val=_val;
			}else{
				--_val ;
			} 
			$('.openlevel',parent.document).val( _val );
		});
		//改变帐号；
		$('.firstsel',parent.document).change(function(){
			var txt=$(this).find('option:selected').text();
			if(txt=='账号'){
				changelast( lastsele,0);
				$('.equal',parent.document).hide();
				$('#val',parent.document).hide();
				$('.lastsel',parent.document).show();
				$('.equalto',parent.document).show();
			}else if(txt=='币种对简称'){
				changelast( usdlist ,1);
				$('.equal',parent.document).hide();
				$('#val',parent.document).hide();
				$('.lastsel',parent.document).show();
				$('.equalto',parent.document).show();
			}else if(txt=='损益金额'||txt=='折美元金额'){
				$('.equal',parent.document).css('display','inline-block');
				$('#val',parent.document).show();
				$('.lastsel',parent.document).hide();
				$('.equalto',parent.document).hide();
				$('#val',parent.document).blur(function(){
					if( numreg.test( $(this).val() )){
						$(this).css({'border':'1px solid #5a8bff'});
					}else{
						$(this).css({'border':'1px solid red'});
					}
				});
			}
		});
		//点击修改控制中的删除；
		$('.remove',parent.document).on('click',function(){
			if( $('.five li.bg',parent.document).length>0 ){
				$('.five li.bg',parent.document).remove();
			}else{
				dialog.choicedata('请先选择一条数据!','openrule');
			}
		});
		//点击增加按钮;
		$('.increate',parent.document).click(function(){
			var openname=$('.openname',parent.document).val(),
			    opennumber=$('.opennumber',parent.document).val();
			
			if(opennumber==''){
				$('.opennumber',parent.document).closest('p').find('re').remove();
				$('.opennumber',parent.document).closest('p').append('<re>请输入敞口编号</re>');
			}else if(opennumber!=''&&!numreg.test(opennumber) ){
				$('.opennumber',parent.document).closest('p').find('re').remove();
				$('.opennumber',parent.document).closest('p').append('<re>请输入数字</re>');
			}else{
				$('.opennumber',parent.document).closest('p').find('re').remove();
			}
			
			if(openname==''){
				$('.openname',parent.document).closest('p').find('re').remove();
				$('.openname',parent.document).closest('p').append('<re>请输入敞口名称</re>');
			}else{
				$('.openname',parent.document).closest('p').find('re').remove();
			}
			
			if( $('.openrule .cenr re',parent.document).length==0){
				addbtn();
			}
		});
		
	});
	//点击保存按钮；
	$('body',parent.document).on('click','.openrule .sav',function(){
		var openname=$('.openname',parent.document).val(),
        opennumber=$('.opennumber',parent.document).val(),
        _val=$('#val',parent.document).val(),
        _valnum=1;
	   tit=$('.openrule .pubhead',parent.document).data('tit');
		if(openname==''){
			$('.openname',parent.document).closest('p').find('re').remove();
			$('.openname',parent.document).closest('p').append('<re>请输入敞口名称</re>');
		}else{
			$('.openname',parent.document).closest('p').find('re').remove();
		}
		
		if(opennumber==''){
			$('.opennumber',parent.document).closest('p').find('re').remove();
			$('.opennumber',parent.document).closest('p').append('<re>请输入敞口编号</re>');
		}else if(opennumber!=''&&!numreg.test(opennumber) ){
			$('.opennumber',parent.document).closest('p').find('re').remove();
			$('.opennumber',parent.document).closest('p').append('<re>请输入数字</re>');
		}else{
			$('.opennumber',parent.document).closest('p').find('re').remove();
		}

		if($('#val',parent.document).css('display')=='block'&&_val==''){
			_valnum=0;
			$("#val",parent.document).css({'border':'1px  solid red'});
		}else{
			_valnum=1;
			$("#val",parent.document).css({'border':'1px  solid rgba(0,0,0,0.3)'});
		}
		if( $('.openrule re',parent.document).length==0 && _valnum==1 ){
				if($('.llist li',parent.document).length<=0){
					dialog.choicedata('请添加规则!','openrule');
				}else{
					if(tit=='add'){
						savbtn('insCkRulet.do');
					}else{
						savbtn('upCkRulet.do');
					}
				}
		}
	});
	//点击修改，数据；
	$('.modify').click(function(){
		if( $('.box tbody tr').hasClass('selected') ){
			str='';
			dialog.openadd('openrule','change');
	
			$('.produname',parent.document).val(prod);
			$('.opennumber',parent.document).val( $('.box tr.selected td:eq(1) span').text() );
			$('.openname',parent.document).val( $('.box tr.selected td:eq(0) span').text());
			$('.openlevel',parent.document).val( $('.box tr.selected td:eq(2) span').text() );
			
			if( $('.box tr.selected td:eq(3) span').text()=='停用' ){
				$('.usestate input[data-tit="stop"]',parent.document).prop('checked','checked');
			}else if( $('.box tr.selected td:eq(3) span').text()=='开启' ){
				$('.usestate input[data-tit="start"]',parent.document).prop('checked','checked');
			}else{
				$('.usestate input[data-tit="end"]',parent.document).prop('checked','checked');
			}
			//修改   获取数据，进行截取，显示
			var a=$('.box tr.selected td:eq(5) span').text().split('&&');
			for(var i=0,num=a.length;i<num;i++){
				$('.llist',parent.document).append('<li>'+a[i]+'</li>');
			}
			var b=$('.box tr.selected td:eq(4) span').text().split('&&');
			for(var i=0,num=b.length;i<num;i++){
				$('.rlist',parent.document).append('<li>'+b[i]+'</li>');
			}
			
			for(var i=0,num=firstsele.length;i<num;i++){
				str+='<option value='+firstsele[i].zcen+'>'+firstsele[i].zdhy+'</option>'
			}
			$('.openrule .firstsel',parent.document).html(str);
			
			changelast( lastsele,0);
			
			$('.firstsel',parent.document).change(function(){
				var txt=$(this).find('option:selected').text();
				if(txt=='账号'){
					changelast( lastsele,0);
					$('.equal',parent.document).hide();
					$('#val',parent.document).hide();
					$('.lastsel',parent.document).show();
					$('.equalto',parent.document).show();
				}else if(txt=='币种对简称'){
					changelast( usdlist ,1);
					$('.equal',parent.document).hide();
					$('#val',parent.document).hide();
					$('.lastsel',parent.document).show();
					$('.equalto',parent.document).show();
				}else if(txt=='损益金额'||txt=='折美元金额'){
					$('.equal',parent.document).css('display','inline-block');
					$('#val',parent.document).show();
					$('.lastsel',parent.document).hide();
					$('.equalto',parent.document).hide();
					$('#val',parent.document).blur(function(){
						if( numreg.test( $(this).val() )){
							$(this).css({'border':'1px solid rgba(0,0,0,0.3)'});
						}else{
							$(this).css({'border':'1px solid red'});
						}
					});
				}
			});
			//#val 失焦；
			$('body',parent.doucment).on('blur','#val',function(){
				if( numreg.test( $(this).val() )){
					$(this).css({'border':'rgba(0,0,0,0.3)'});
				}else{
					$(this).css({'border':'red'});
				}
			});
			//敞口名称；
			$('.openname',parent.document).blur(function(){
				var _val=$(this).val().trim();
				if(_val==''){
					$(this).closest('p').find('re').remove();
					$(this).closest('p').append('<re>请输入敞口名称</re>');
				}else{
					$(this).closest('p').find('re').remove();
				}
			});
			//敞口级别;
			$('.openlevel',parent.document).blur(function(){
				if( _sreg.test( $(this).val() ) ){
					$(this).val('0');
				}else if( $(this).val()>100||$(this).val()<0 ){
					$(this).val('0');
				}
			});
			//点击lower和up
			$('.up',parent.document).click(function(){
				var _val=$('.openlevel',parent.document).val();
				if(_val>=100){
					_val=_val;
				}else{
					_val++;
				}  
				$('.openlevel',parent.document).val( _val );
			});
			$('.lower',parent.document).click(function(){
				var _val=$('.openlevel',parent.document).val();
				if(_val<=0){
					_val=_val;
				}else{
					--_val ;
				} 
				$('.openlevel',parent.document).val( _val );
			});
			
			$('.llist li,.rlist li',parent.document).on('click',function(){
				$('.llist li',parent.document).siblings().removeClass('bg');
				$('.rlist li',parent.document).siblings().removeClass('bg');

				$('.rlist li',parent.document).eq( $(this).index() ).addClass('bg');
				$('.llist li',parent.document).eq( $(this).index() ).addClass('bg');
			});
			//点击修改面板中的增加；
			$('.increate',parent.document).on('click',function(){
				addbtn();
			});
			
			//点击修改控制中的删除；
			$('.remove',parent.document).on('click',function(){
				if( $('.five li.bg',parent.document).length>0 ){
					$('.five li.bg',parent.document).remove();
				}else{
					dialog.choicedata('请先选择一条数据!','openrule');
				}
			});
		}else{
			dialog.choicedata('请先选择一条数据!','openrule');
		}
	});
	
	function addbtn(){ //点击添加按钮，控制显示；
		var checktxt=$('.firstsel option:selected',parent.document).text(),
		value=$('.firstsel option:selected',parent.document).attr('value'),
		symbol,brr=[],appentrue="",appenasync=0;
		for(var i=0;i<$('.rlist li',parent.document).length;i++){
			brr.push( $('.rlist li',parent.document).eq(i).text() );
		}
		
		if(checktxt=='账号'){
			appentrue=checktxt+'等于'+$('.lastsel option:selected',parent.document).text();
			if(brr.length==0){
				$('.llist',parent.document).append('<li>'+value+'.equals('+'"'+$('.lastsel option:selected',parent.document).attr('value')+'"'+')'+'</li>');
				$('.rlist',parent.document).append('<li>'+checktxt+'等于'+$('.lastsel option:selected',parent.document).text()+'</li>');
			}else{
				for(var i=0;i<brr.length;i++){
					if(appentrue!=brr[i]){
						appenasync=1;
					}else{
						appenasync=0;
					}
				}
				if(appenasync==1){
					$('.llist',parent.document).append('<li>'+value+'.equals('+'"'+$('.lastsel option:selected',parent.document).attr('value')+'"'+')'+'</li>');
					$('.rlist',parent.document).append('<li>'+checktxt+'等于'+$('.lastsel option:selected',parent.document).text()+'</li>');
				}
			}
			
		}else if(checktxt=='币种对简称'){
			appentrue=checktxt+'等于'+$('.lastsel option:selected',parent.document).text();
			if(brr.length==0){
				$('.llist',parent.document).append('<li>'+value+'.equals('+'"'+$('.lastsel option:selected',parent.document).text()+'"'+')'+'</li>');
				$('.rlist',parent.document).append('<li>'+checktxt+'等于'+$('.lastsel option:selected',parent.document).text()+'</li>');
			}else{
				for(var i=0;i<brr.length;i++){
					if(appentrue!=brr[i]){
						appenasync=1;
					}else{
						appenasync=0;
					}
				}
				if(appenasync==1){
					$('.llist',parent.document).append('<li>'+value+'.equals('+'"'+$('.lastsel option:selected',parent.document).text()+'"'+')'+'</li>');
					$('.rlist',parent.document).append('<li>'+checktxt+'等于'+$('.lastsel option:selected',parent.document).text()+'</li>');
				}
			}
			
		}else if(checktxt=='损益金额'||checktxt=='折美元金额'){
			var _val=$('#val',parent.document).val(),
				appentrue=checktxt+$('.equal option:selected',parent.document).attr('value')+_val;
			if(brr.length==0){
				if(_val!='' ){ 
						$('.llist',parent.document).append('<li>'+value+$('.equal option:selected',parent.document).attr('value')+_val+'</li>');
						$('.rlist',parent.document).append('<li>'+checktxt+$('.equal option:selected',parent.document).attr('value')+_val+'</li>');
				}else{
					$('#val',parent.document).css('border','1px solid red');
				}
			}else{
				if(_val!='' ){ 
					for(var i=0;i<brr.length;i++){
						if(appentrue!=brr[i]){
							appenasync=1;
						}else{
							appenasync=0;
						}
					}
					if(appenasync==1){
						$('.llist',parent.document).append('<li>'+value+$('.equal option:selected',parent.document).attr('value')+_val+'</li>');
						$('.rlist',parent.document).append('<li>'+checktxt+$('.equal option:selected',parent.document).attr('value')+_val+'</li>');
					}
				}else{
					$('#val',parent.document).css('border','1px solid red');
					//$('body',parent.document).append('<span>请输入值</span>');
				}
			}
		}
		$('.llist li,.rlist li',parent.document).on('click',function(){
			$('.llist li',parent.document).siblings().removeClass('bg');
			$('.rlist li',parent.document).siblings().removeClass('bg');

			$('.rlist li',parent.document).eq( $(this).index() ).addClass('bg');
			$('.llist li',parent.document).eq( $(this).index() ).addClass('bg');
		});
	}
	function savbtn(obj){ //savbtn
		var a='',b='',usfg;
		$('.llist li',parent.document).each(function(){
			if($(this).index()>=1){
				a=a+'&&'+$(this).text()
			}else{
				a+=$(this).text()
			}
		});
		$('.rlist li',parent.document).each(function(){
			if($(this).index()>=1){
				b=b+'&&'+$(this).text()
			}else{
				b+=$(this).text()
			}
		});
		 if( $('.usestate input[type="radio"]:checked',parent.document).data('tit')=='stop' ){
			 usfg=1;
		 }else if( $('.usestate input[type="radio"]:checked',parent.document).data('tit')=='start' ){
			 usfg=0;
		 }else{
			 usfg=2;
		 }
		var ckrulet={
			'ckno':$('.opennumber',parent.document).val(),
			'cknm':$('.openname',parent.document).val(),
			'leve':$('.openlevel',parent.document).val(),
			'rcnt':$('.llist li',parent.document).length,
			'rule':a,
			'ruhy':b,
			'usfg':usfg
		};
		$.ajax({
			url:obj,
			type:'post',
			async:false,
			contentType:'application/json',
			data:JSON.stringify({'userKey':userkey,'ckRulet':ckrulet}),
			success:function(data){
				if( data.code=='01'){
					 //当前没有数据；
					$('.openrule .sav',parent.document).closest('.zhezhao').remove();
					dialog.choicedata(data.codeMessage,'openrule','aaa');
					oprurender();
				}else if(data.code=='02'){
					dialog.choicedata(data.codeMessage,'openrule','aaa');
				}
			}
		});
	}
	function changelast(obj,obj1){
		str1='';
		if(obj1==0){
			for(var i=0,num=obj.length;i<num;i++){
				str1+='<option value='+obj[i].apfg+'>'+obj[i].tynm+'</option>'
			}
		}else if(obj1==1){
			for(var i=0,num=obj.length;i<num;i++){
				str1+='<option>'+obj[i]+'</option>'
			}
		}
		$('.openrule .lastsel',parent.document).html(str1);
	}
	$('.levelset').click(function(){
		dialog.openlevel('openrule');
		$.ajax({
			url:'/fx/selLeveCknm.do',
			type:'post',
			async:false,
			contentType:'application/json',
			data:userkey,
			success:function(data){
				if( data.code=='00'){
					 //当前没有数据；
					
				}else if(data.code=='01'){
					var udata=data.codeMessage,str='';
					for(var i in udata){
						str+='<li value='+udata[i].ckno+'>'+udata[i].cknm+'</li>'
					}
					$('.before ul',parent.document).html(str);
					$('.after ul',parent.document).html(str);
					$('.after ul',parent.document).on('click','li',function(){
						$(this).siblings().removeClass('bg');
						$(this).addClass('bg');
					});
				}
			}
		});
		$('.top',parent.document).click(function(){
			var index=$('.after li.bg',parent.document).index();
			if(index>0){
				$('.after ul li.bg',parent.document).insertBefore( $('.after ul li',parent.document).eq(index-1) );
			}
		});
		$('.bott',parent.document).click(function(){
			var index=$('.after ul li.bg',parent.document).index();
			if( index<$('.after ul li',parent.document).length-1 ){
				$('.after ul li.bg',parent.document).insertAfter( $('.after ul li',parent.document).eq(index+1) );
			}
		});
	});
	$('body',parent.document).on('click','.openrule .cancel,.openrule .close,.openrule .levelcance',function(){
		$('.zhezhao',parent.document).remove();
	});
	$('body',parent.document).on('click','.openrule .levelsav',function(){
		var arr=[];
		$('.after ul li',parent.document).each(function(i,v){
			arr.push( {'ckno':$(v).attr('value') });
		});
		$.ajax({
			url:'/fx/upLeveCknm.do',
			type:'post',
			async:false,
			contentType:'application/json',
			data:JSON.stringify({'userKey':userkey,'ruletList':arr}),
			success:function(data){
				$('.openrule .levelsav',parent.document).closest('.zhezhao').remove();
				if( data.code=='02'){
					 //失败
					dialog.choicedata(data.codeMessage,'openrule','aaa');
				}else if(data.code=='01'){
					dialog.choicedata(data.codeMessage,'openrule','aaa');
					oprurender();
				}
			}
		});
		$(this).closest('.zhezhao').remove();
		//$('.zhezhao',parent.document).remove();
	});
	$('body',parent.document).on('click','.openrule .sure',function(){
		$(this).closest('.zhezhao').remove();
		//$('.zhezhao',parent.document).remove();
	});
    /*----------------快速搜索功能的实现------------------------*/
	$('.unus_serbtn').click(function(){
		  dialog.serchData($('.unus_seript').val(),'.mmg-headWrapper');
    });
	
})
