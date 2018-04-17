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
	var userkey=JSON.parse( sessionStorage.getItem('menu') ).userKey;
	$('.boxtop').css('width',ww);
	$('.boxtop').css('height',wh);
	
	//列参数;
    var cols = [
            { title:'产品编号', name:'ptid',width:60,align:'left' },
            { title:'产品简码', name:'prnm',width:60, align:'left'},
            { title:'产品名称', name:'ptnm',width:80, align:'left'},
            { title:'价格频率', name:'frqy',width:60, align:'right'},
            { title:'开通状态', name:'usfg',width:60,align:'center'},
    ];
    getlist();
    function getlist( ){
		$.ajax({
			url:'/fx/price/getAllprodPrice.do',
			contentType:'application/json',
			async:true,
			success:function(data){
				if(data.code==01){
					userdata=JSON.parse( data.codeMessage );
					ren({'cols':cols,'wh':wh,'userdata':userdata});
				}else{
					ren({'cols':cols,'wh':wh,'userdata':''});
				}
			}
		});
	}	
    //添加、修改、删除；
    $('.del').click(function(){
		if( $('.box tbody tr').hasClass('selected') ){
			//删除弹出框;
			dialog.cancelDate('您确定要删除当前记录吗？','productset');
		 }else{
			dialog.choicedata('请先选择一条数据!','productset');
		}
	});
    $('.add').click(function(){
    	dialog.prosetfn('productset','add');
    	$('.pronum',parent.document).on('blur',function(){
    		if(	$('.pronum',parent.document).val()=='' ){
        		$('.pronum',parent.document).closest('div').find('re').remove();
        		$('.pronum',parent.document).closest('div').append('<re>产品编号不能为空!</re>');
        	}else if(!/^[P]{1}\d{3}$/.test( $('.pronum',parent.document).val() )){
        		$('.pronum',parent.document).closest('div').find('re').remove();
        		$('.pronum',parent.document).closest('div').append('<re>产品编号输入不合法!</re>');
        	}else{
        		$('.pronum',parent.document).closest('div').find('re').remove();
        	}
    	});
    	
    	$('.cuurnum',parent.document).on('blur',function(){
    		if(	$('.cuurnum',parent.document).val()=='' ){
        		$('.cuurnum',parent.document).closest('div').find('re').remove();
        		$('.cuurnum',parent.document).closest('div').append('<re>货币简码不能为空!</re>');
        	}else if(!/^[a-zA-Z0-9]+$/.test( $('.cuurnum',parent.document).val() )){
        		$('.cuurnum',parent.document).closest('div').find('re').remove();
        		$('.cuurnum',parent.document).closest('div').append('<re>货币简码必须是字母和数字组合!</re>');
        	}else{
        		$('.cuurnum',parent.document).closest('div').find('re').remove();
        	}
    	});
    	
    	$('.prihz',parent.document).on('blur',function(){
    		if(	$('.prihz',parent.document).val()=='' ){
        		$('.prihz',parent.document).closest('div').find('re').remove();
        		$('.prihz',parent.document).closest('div').append('<re>价格频率不能为空!</re>');
        	}else if(!/^\d$/.test( $('.prihz',parent.document).val() )){
        		$('.prihz',parent.document).closest('div').find('re').remove();
        		$('.prihz',parent.document).closest('div').append('<re>价格频率只能是数字!</re>');
        	}else{
        		$('.prihz',parent.document).closest('div').find('re').remove();
        	}
    	});
    	
    	$('.proname',parent.document).on('blur',function(){
    		if(	$('.proname',parent.document).val()=='' ){
        		$('.proname',parent.document).closest('div').find('re').remove();
        		$('.proname',parent.document).closest('div').append('<re>产品名称不能为空!</re>');
        	}else{
        		$('.proname',parent.document).closest('div').find('re').remove();
        	}
    	});
    });
    $('.modify').click(function(){
    	if( $('.box tbody tr').hasClass('selected') ){
    		dialog.prosetfn('productset','modify');
    		$('.pronum',parent.document).val( $('.box tr.selected td:eq(0) span').text() );
    		$('.proname',parent.document).val( $('.box tr.selected td:eq(2) span').text() );
    		$('.prihz',parent.document).val( $('.box tr.selected td:eq(3) span').text() );
    		$('.cuurnum',parent.document).val( $('.box tr.selected td:eq(1) span').text() );
    		if( $('.box tr.selected td:eq(4) span').text()=='开通'  ){
    			$('.productset .r1',parent.document).prop('checked','checked');
    		}else{
    			$('.productset .r2',parent.document).prop('checked','checked');
    		}
    	}else{
    		dialog.choicedata('请先选择一条数据!','productset');
    	}
    });
    
    $('body',parent.document).on('click','.productset .preserve',function(){
    	var date,
    		usfg=$('.productset input[type="radio"]:checked',parent.document).attr('tit'),
    		term=$('.box tr.selected td:eq(0)').attr('term'),
    		cxfg=$('.box tr.selected td:eq(0)').attr('cxfg');
    		
    		if(!term){
    			term='0';
    		}else{
    			term=term;
    		}
    		
    		if(!cxfg){
    			cxfg='2';
    		}else{
    			if(cxfg=='钞'){
    				cxfg='2';
    			}else{
    				cxfg='1';
    			}
    		}
    	date={
    			userKey:userkey,
    			pdtinfo:{
	    			ptid:$('.pronum',parent.document).val(),//编号
	    			prnm:$('.cuurnum',parent.document).val(),//简码
	    			usfg:usfg,//0,开通，1，关闭
	    			qtty:"SPT",//价格类型
	    			ptnm:$('.proname',parent.document).val(),//名称
	    			term:term,//期限
	    			cxfg:cxfg,//钞汇标志
	    			frqy:$('.prihz',parent.document).val()	//价格频率		$('.pronum',parent.document).val()
    			}
    	}

    	if(	$('.pronum',parent.document).val()=='' ){
    		$('.pronum',parent.document).closest('div').find('re').remove();
    		$('.pronum',parent.document).closest('div').append('<re>产品编号不能为空!</re>');
    	}else if(!/^[P]{1}\d{3}$/.test( $('.pronum',parent.document).val() )){
    		$('.pronum',parent.document).closest('div').find('re').remove();
    		$('.pronum',parent.document).closest('div').append('<re>产品编号输入不合法!</re>');
    	}else{
    		$('.pronum',parent.document).closest('div').find('re').remove();
    	}
    	
    	if(	$('.cuurnum',parent.document).val()=='' ){
    		$('.cuurnum',parent.document).closest('div').find('re').remove();
    		$('.cuurnum',parent.document).closest('div').append('<re>货币简码不能为空!</re>');
    	}else if(!/^[a-zA-Z0-9]+$/.test( $('.cuurnum',parent.document).val() )){
    		$('.cuurnum',parent.document).closest('div').find('re').remove();
    		$('.cuurnum',parent.document).closest('div').append('<re>货币简码必须是字母和数字组合!</re>');
    	}else{
    		$('.cuurnum',parent.document).closest('div').find('re').remove();
    	}
    	
    	if(	$('.prihz',parent.document).val()=='' ){
    		$('.prihz',parent.document).closest('div').find('re').remove();
    		$('.prihz',parent.document).closest('div').append('<re>价格频率不能为空!</re>');
    	}else if(!/^\d{1,}$/.test( $('.prihz',parent.document).val() )){
    		$('.prihz',parent.document).closest('div').find('re').remove();
    		$('.prihz',parent.document).closest('div').append('<re>价格频率只能是数字!</re>');
    	}else{
    		$('.prihz',parent.document).closest('div').find('re').remove();
    	}
    	
    	if(	$('.proname',parent.document).val()=='' ){
    		$('.proname',parent.document).closest('div').find('re').remove();
    		$('.proname',parent.document).closest('div').append('<re>产品名称不能为空!</re>');
    	}else{
    		$('.proname',parent.document).closest('div').find('re').remove();
    	}
    	if( $('.productset re',parent.document).length==0 ){
    		$.ajax({
    			url:'/fx/price/savePrice.do',
    			contentType:'application/json',
    			async:true,
    			type:'post',
    			data:JSON.stringify(date),
    			success:function(data){
    				$('.productset .preserve',parent.document).closest('.zhezhao').remove();
    				if(data.code==01){
    					dialog.choicedata(data.codeMessage,'productset');
    					getlist();
    				}else{
    					dialog.choicedata(data.codeMessage,'productset');
    				}
    			}
    		});
    	}
    });
    $('body',parent.document).on('click','.productset .confirm',function(){
    	$.ajax({
			url:'/fx/price/delprodPrice.do',
			contentType:'application/json',
			async:true,
			type:'post',
			data:JSON.stringify({
				userKey:userkey,
	    		ptid:$('.box tr.selected td:eq(0) span').text() ,//产品编号
	    		prnm:$('.box tr.selected td:eq(1) span').text() //产品简码
			}),
			success:function(data){
				$('.productset .confirm',parent.document).closest('.zhezhao').remove();
				if(data.code==01){
					dialog.choicedata(data.codeMessage,'productset');
					getlist();
				}else{
					dialog.choicedata(data.codeMessage,'productset');
				}
			}
		});
	});
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
				horizrailenabled:false
		  });
		 mmg.on('loadSuccess',function(e,data){
			 $('.box').find('tbody tr').each(function(i,v){
				 $(v).find('td').eq(0).attr('term',obj.userdata[i].term);
				 $(v).find('td').eq(0).attr('cxfg',obj.userdata[i].cxfg);				 
			 });
		 });
    }
    $('body',parent.document).on('click','.productset .close,.productset .cancel,.productset .sure',function(){
		$(this).closest('.zhezhao').remove();
	});
})