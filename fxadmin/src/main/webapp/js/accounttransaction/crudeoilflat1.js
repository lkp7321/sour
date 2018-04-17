/*
 *原油手工平盘 账户交易
 **/
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
		table2excel:'js_files/excel',
		WdatePicker:'./My97DatePicker/WdatePicker'

	}
});
require(['jquery','mmGrid','niceScroll','dialog','table2excel','WdatePicker'],function($,mmGrid,niceScroll,dialog,table2excel,WdatePicker){
	var wh=$(window).height()-190+"px",
		ww=$(window).width()-260+"px";
	var userkey=JSON.parse( sessionStorage.getItem('menu') ).userKey,
		product=sessionStorage.getItem('product'),userdata='',
		tit=$('title').text();
	$('.boxtop').css('width',ww);
	$('.boxtop').css('height',wh);
	//列参数;
    var cols = [
        { title:'合约', name:'exnm' ,width:120, align:'left' },
        { title:'bid', name:'neby' ,width:100, align:'right'},
        { title:'ask', name:'nesl' ,width:100, align:'right'},
        { title:'settlementtd', name:'settlementtd' ,width:100, align:'left'},
        { title:'fixingtd', name:'fixingtd' ,width:100, align:'left'},
        { title:'amount', name:'amat' ,width:100, align:'left'},
        { title:'YEARCODE', name:'yEARCODE' ,width:100, align:'left'}
    ]
   //请求列表数据；
	$.ajax({
		url:'/fx/oilhandsel.do',
		type:'post',
		contentType:'application/json',
		async:true,
		success:function(data){
			if(data.code==01){
				userdata=data.codeMessage;
				ren({'cols':cols,'wh':wh,'userdata':userdata});
			}else{
				ren({'cols':cols,'wh':wh,'userdata':''});
			}
		}
	});
    $('.rrdot').blur(function(){
    	var txt=$(this).val();
    	/*if( txt=='' ){
    		dialog.choicedata('容忍点差不能为空!','crudeoilflat');
    	}else*/ if(txt!=''&&!/\d/.test(txt)){
    		dialog.choicedata('容忍点差必须是正整数!','crudeoilflat');
    	}
    });
    //contenteditable
    $('.boxtop').on('click','tr',function(){
    	$(this).find('td:eq(5)').prop('contenteditable','true');
    });
    $('.boxtop tr td:eq(5)').blur(function(){
    	var spread=$('.rrdot').val().trim(),point;
    	if( $(this).val()==''){
    		dialog.choicedata('交易金额不能为空!','crudeoilflat');
    	}else if( $(this).val()!=''&&!/\d{1}\.\d{2}/.test( $(this).val() )){
    		dialog.choicedata('交易金额必须是两位以内的小数!','crudeoilflat');
    	}
    });
    $('.boxtop').on('dblclick','tr td:eq(1)',function(){
    	var spread=$('.rrdot').val().trim(),
    		point= $('.boxtop tr td:eq(5)').text(),
    		side,data;
    	console.log( point )
    	/*if( spread=='' ){
    		dialog.choicedata('容忍点差不能为空!','crudeoilflat');
    	}else */
    	if(	!/\d/.test(spread) ){
    		dialog.choicedata('容忍点差必须是正整数!','crudeoilflat');
    	}else if( point==''){
    		dialog.choicedata('交易金额不能为空!','crudeoilflat');
    	}else if(point!=''&&!/\d{1}\.\d{2}/.test( point )){
    		dialog.choicedata('交易金额必须是两位以内的小数!','crudeoilflat');
    	}else{
    		side='2';
    		if( spread==''){
    			point='0';
    		}else{
    			point=spread;
    		}
    		data={
    			amat:$('.boxtop tr.selected td:eq(5)').text(),
    			point:point,
    			side:side,
    			neby:$('.boxtop tr.selected td:eq(1) span').text(),
    			nesl:$('.boxtop tr.selected td:eq(2) span').text(),
    			excd:'',
    			yearcode:$('.boxtop tr.selected td:eq(6) span').text(),
    			settlementtd:$('.boxtop tr.selected td:eq(3) span').text(),
    			fixingtd:$('.boxtop tr.selected td:eq(4) span').text(),
    			exnm:$('.boxtop tr.selected td:eq(0) span').text()
    		}
    		getur( data );
    	}    	
    });
    $('.boxtop').on('dblclick','tr td:eq(2)',function(){
    	var spread=$('.rrdot').val().trim(),
    		point= $('.boxtop tr td:eq(5)').text(),
    		side,data;
    	
    	/*if( spread=='' ){
    		dialog.choicedata('容忍点差不能为空!','crudeoilflat');
    	}else */
    	if(	!/\d/.test(spread) ){
    		dialog.choicedata('容忍点差必须是正整数!','crudeoilflat');
    	}else if( point==''){
    		dialog.choicedata('交易金额不能为空!','crudeoilflat');
    	}else if(point!=''&&!/\d{1}\.\d{2}/.test( point )){
    		dialog.choicedata('交易金额必须是两位以内的小数!','crudeoilflat');
    	}else{
    		side='2';
    		if( spread==''){
    			point='0';
    		}else{
    			point=spread;
    		}
    		data={
    			amat:$('.boxtop tr.selected td:eq(5)').text(),
    			point:point,
    			side:side,
    			neby:$('.boxtop tr.selected td:eq(1) span').text(),
    			nesl:$('.boxtop tr.selected td:eq(2) span').text(),
    			excd:'',
    			yearcode:$('.boxtop tr.selected td:eq(6) span').text(),
    			settlementtd:$('.boxtop tr.selected td:eq(3) span').text(),
    			fixingtd:$('.boxtop tr.selected td:eq(4) span').text(),
    			exnm:$('.boxtop tr.selected td:eq(0) span').text()
    		}
    		getur( data );
    	}    	
    });
    function getur( obj){
    	$.ajax({
    		url:'/fx/oilsendToCkServer.do',
    		type:'post',
    		contentType:'application/json',
    		async:true,
    		data:JSON.stringify({
    			userKey:userkey,
    			price:obj
    		}),
    		success:function(data){
    			if(data.code==01){
    				userdata=data.codeMessage;
    				dialog.choicedata(data.codeMessage,'crudeoilflat');
    			}else{
    				dialog.choicedata(data.codeMessage,'crudeoilflat');
    			}
    		}
    	});
    }
    function ren(obj){
		//console.log( obj.userdata )
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
			 if( obj.userdata>0 ){
				 $('.box tbody tr').each(function(i,v){
					 $(v).find('td:eq(0)').attr('excd',obj.userdata[i].excd);
				 });
			 }
		 });
    }
    $('body',parent.document).on('click','.crudeoilflat .sure',function(){
		$(this).closest('.zhezhao').remove();
	});
	/*$('.toexcel').click(function(){
		if( $('.box tbody tr').length>=1 ){
			$(".mmg-body").table2excel({
				exclude: ".noExl",
				name: "Excel Document Name",
				filename: tit + new Date().toISOString().replace(/[\-\:\.]/g, ""),
				fileext: ".xls",
				exclude_img: true,
				exclude_links: true,
				exclude_inputs: true
			});
		}
	});*/
    /*----------------快速搜索功能的实现------------------------*/
	$('.info_serbtn').click(function(){
		  dialog.serchData($('.info_seript').val(),'.mmg-headWrapper');
    });  
})