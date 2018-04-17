/*系统管理--币种管理×*/
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
	//列参数
    var usnm=sessionStorage.getItem('usnm'),
		product=sessionStorage.getItem('product'),
		user={'usnm':usnm,'prod':product},
		userkey=JSON.parse( sessionStorage.getItem('menu') ).userKey,
		userdata,
		tit=$('title').text();
    var cols = [
        { title:'品种中文名称', name:'excn' ,width:150, align:'left' },
        { title:'货币对名称', name:'exnm' ,width:150, align:'left'},
        { title:'货币对类型', name:'extp' ,width:150, align:'center'},
        { title:'价格位点数', name:'pion' ,width:150, align:'right'},
        { title:'放大倍率', name:'mult' ,width:100, align:'right'},
        { title:'计算系数', name:'divd' ,width:150, align:'right'},
        { title:'加工步骤', name:'trcd' ,width:100, align:'left'}, 
        { title:'点差精度', name:'expi' ,width:150, align:'right'}
    ],
    cols1=[
		{ title:'品种中文名称', name:'excn' ,width:150, align:'left' },
		{ title:'货币对名称', name:'exnm' ,width:100, align:'left'},
		{ title:'货币对类型', name:'extp' ,width:100, align:'center'},
		{ title:'价格位点数', name:'pion' ,width:150, align:'right'}
    ];
    $('.change1').click(function(){
    	var arr=[],befo,ende;
    	if( $('.box tbody tr').hasClass('selected') ){
    		dialog.cuurencypairs( 'cuurencypair');
    		$('.cuurnum',parent.document).val( $('.box tr.selected td:eq(0)').attr('excd') );
    		$('.chiname',parent.document).val( $('.box tr.selected td:eq(0) span').text() );
    		$('.cuurdot',parent.document).val( $('.box tr.selected td:eq(3) span').text() );
    		$('.bigstat',parent.document).val( $('.box tr.selected td:eq(4) span').text() );
    		$('.comnum',parent.document).val( $('.box tr.selected td:eq(5) span').text() );
    		
    		if( $('.box tr.selected td:eq(2) span').text()=='直盘'){
    			$('.cuurencypair .r1',parent.document).prop('checked','checked');
    		}else{
    			$('.cuurencypair .r2',parent.document).prop('checked','checked');
    		}
    		
    		$('.bigstat',parent.document).on('blur',function(){
    			if( $('.bigstat',parent.document).val()==''){
    				
    	    		$('.bigstat',parent.document).closest('div').find('re').remove();
    	    		$('.bigstat',parent.document).closest('div').append('<re>放大倍率不能为空!</re>');
    	    	}else if( $('.bigstat',parent.document).val()!=''&&!/^\d+$/.test( $('.bigstat',parent.document).val() ) ){
    	    		
    	    		$('.bigstat',parent.document).closest('div').find('re').remove();
    	    		$('.bigstat',parent.document).closest('div').append('<re>放大倍率只能为数字!</re>');
    	    	}else{
    	    		$('.bigstat',parent.document).closest('div').find('re').remove();
    	    	}
    		});
    		$('.comnum',parent.document).on('blur',function(){
    			if( $('.comnum',parent.document).val()==''){
    	    		$('.comnum',parent.document).closest('div').find('re').remove();
    	    		$('.comnum',parent.document).closest('div').append('<re>计算系数不能为空!</re>');
    	    	}else if( !/^\d+$/.test( $('.comnum',parent.document).val() ) ){
    	    		$('.comnum',parent.document).closest('div').find('re').remove();
    	    		$('.comnum',parent.document).closest('div').append('<re>计算系数只能为数字!</re>');
    	    	}else{
    	    		$('.comnum',parent.document).closest('div').find('re').remove();
    	    	}
    		});
    		befo=$('.box tr.selected td:eq(1) span').text().slice(0,3) ;
    		ende=$('.box tr.selected td:eq(1) span').text().slice(3,6) ;
    
    		$.ajax({
    			url:'/fx/price/getexnminit.do',
    			type:'post',
    			async:false,
    			success:function(data){
    				for(var i in data.codeMessage){
    					arr.push( data.codeMessage[i].CYEN);
    				}
    			}
    		});
    		if( arr.indexOf(befo)==-1 ){
    			befo='OAU';
    		}else{
    			befo=befo;
    		}
    		if( arr.indexOf(ende)==-1 ){
    			ende='OAU';
    		}else{
    			ende=ende;
    		}
   
    		$('.cuur1',parent.document).html('<option>'+befo+'</option>');
    		$('.cuur2',parent.document).html('<option>'+ende+'</option>');
    	}else{
    		dialog.choicedata('请先选择一条数据!','cuurencypair');
    	}
    });
    $('body',parent.document).on('click','.preserve',function(){
    	var mult=$('.bigstat',parent.document).val(),
    		divd=$('.comnum',parent.document).val(),
    		exnm=$('.box tr.selected td:eq(1) span').text();
    	if( $('.bigstat',parent.document).val()==''){
    		$('.bigstat',parent.document).closest('div').find('re').remove();
    		$('.bigstat',parent.document).closest('div').append('<re>放大倍率不能为空!</re>');
    	}else if( !/^\d+$/.test( $('.bigstat',parent.document).val() ) ){
    		$('.bigstat',parent.document).closest('div').find('re').remove();
    		$('.bigstat',parent.document).closest('div').append('<re>放大倍率只能为数字!</re>');
    	}else{
    		$('.bigstat',parent.document).find('re').remove();
    	}
    	if( $('.comnum',parent.document).val()==''){
    		$('.comnum',parent.document).closest('div').find('re').remove();
    		$('.comnum',parent.document).closest('div').append('<re>计算系数不能为空!</re>');
    	}else if( !/^\d+$/.test( $('.comnum',parent.document).val() ) ){
    		$('.comnum',parent.document).closest('div').find('re').remove();
    		$('.comnum',parent.document).closest('div').append('<re>计算系数只能为数字!</re>');
    	}else{
    		$('.comnum',parent.document).closest('div').find('re').remove();
    	}
    	if( $('.cuurencypair re',parent.document).length==0 ){
    		 $.ajax({
    	     		url:'/fx/price/upCurrmsg.do',
    	     		type:'post',
    	     		contentType:'application/json',
    	     		async:true,
    	     		data:JSON.stringify({
    	     			userKey:userkey,
    	     			currmsgacc:{
    		     			mult:mult,
    		     			divd:divd,
    		     			exnm:exnm
    	     			}
    	     		}),
    	     		success:function(data){
    	     			$('.cuurencypair .preserve',parent.document).closest('.zhezhao').remove();
    	     			if(data.code==01){
    	     				dialog.choicedata(data.codeMessage,'cuurencypair');
    	     				 if( tit=='报价管理-货币对管理'){
    	     			    	getcuurlist();
    	     			    }else{
    	     			    	getcuurpair();
    	     			    }
    	     			}else{
    	     				dialog.choicedata(data.codeMessage,'cuurencypair');
    	     			}
    	     		}
    	     	});
    	}
    	
    });
    if( tit=='报价管理-货币对管理'){
    	getcuurlist();
    }else{
    	getcuurpair();
    }
    function getcuurpair(){
        $.ajax({
    		url:'/fx/getAllCurrmsg.do',
    		type:'post',
    		contentType:'application/json',
    		async:true,
    		data:userkey,
    		success:function(data){
    			if(data.code==01){
    				userdata=data.codeMessage;
    				ren({'cols':cols1,'wh':wh,'userdata':userdata});
    			}else if(data.code==00){
    				ren({'cols':cols1,'wh':wh,'userdata':''});
    			}
    		}
    	});
    }
   function getcuurlist(){
	   $.ajax({
			url:'/fx/getAllCurrmsgP7.do',
			type:'post',
			contentType:'application/json',
			async:true,
			success:function(data){
				if(data.code==01){
					userdata= data.codeMessage ;
					ren({'cols':cols,'wh':wh,'userdata':userdata});
				}else if(data.code==00){
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
				horizrailenabled:obj.horizrailenabled
		  });
		 mmg.on('loadSuccess',function(){
			 if( $('.box tbody tr').length>0){
				$('.box tbody tr').each(function(i,v){
					$(v).find('td').eq(0).attr('excd',obj.userdata[i].excd);
				});
			}
		 })
   }
/*----------------快速搜索功能的实现------------------------*/
$('.outfit_serbtn').click(function(){
	  dialog.serchData($('.outfit_seript').val(),'.mmg-headWrapper');
});

//点击添加修改弹出框中的x、取消按钮；
$('body',parent.document).on('click','.cuurencypair .close,.cuurencypair .sure,.cuurencypair .cancel',function(){
	$(this).closest('.zhezhao').remove();
});
});

