/*
 * MT4用户组设置*/
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
	var userkey=JSON.parse( sessionStorage.getItem('menu') ).userKey,
		arr=[];
	$('.boxtop').css('width',ww);
	$('.boxtop').css('height',wh);
	
	//列参数;
    var cols = [
            { title:'组号', name:'gpty',width:60,align:'left' },
            { title:'组名', name:'name',width:60, align:'left'},
            { title:'描述', name:'note',width:80, align:'left'},
            { title:'使用状态', name:'usfg',width:60, align:'center'}
    ];
    getlist();
    function getlist(){
    	$.ajax({
    		url:'/fx/queryBailMt4Para.do',
    		type:'get',
    		contentType:'application/json',
    		async:true,
    		success:function(data){
    			if(data.code==01){
    				userdata= data.codeMessage ;
    				dialog.ren({'cols':cols,'wh':wh,'userdata':userdata});
    			}else{
    				dialog.ren({'cols':cols,'wh':wh,'userdata':''});
    			}
    		}
    	});
    }
	$('.box tr').each(function(i,v){
		arr.push( $(v).find('td:eq(0) span').text() );
	});
    $('.add').click(function(){
    	dialog.mt4add('mt4','add');
    	$('.pronum',parent.document).on('blur',function(){
    		if( $('.pronum',parent.document).val()=='' ){
    			$('.pronum',parent.document).closest('div').find('re').remove();
    			$('.pronum',parent.document).closest('div').append('<re>组号不能为空!</re>');
    		}else if( !/[0-9A-Za-z]+/.test(  $('.pronum',parent.document).val() ) ){
    			
    			$('.pronum',parent.document).closest('div').find('re').remove();
    			$('.pronum',parent.document).closest('div').append('<re>组号必须是字母和数字组合</re>');
    		}else if( arr.indexOf($('.pronum',parent.document).val())!=-1){
    			$('.pronum',parent.document).closest('div').find('re').remove();
    			$('.pronum',parent.document).closest('div').append('<re>该组名已经存在,请重新输入!</re>');
    		}else{
    			$('.pronum',parent.document).closest('div').find('re').remove();
    		}
    	});
    	$('.gruname',parent.document).on('blur',function(){
    		if( $('.gruname',parent.document).val()=='' ){
    			$('.gruname',parent.document).closest('div').find('re').remove();
    			$('.gruname',parent.document).closest('div').append('<re>组名不能为空!</re>');
    		}else{
    			$('.gruname',parent.document).closest('div').find('re').remove();
    		}
    	});
    	$('.rmrk',parent.document).on('blur',function(){
    		if( $('.rmrk',parent.document).val()=='' ){
    			$('.rmrk',parent.document).closest('div').find('re').remove();
    			$('.rmrk',parent.document).closest('div').append('<re>描述不能为空!</re>');
    		}else{
    			$('.rmrk',parent.document).closest('div').find('re').remove();
    		}
    	});    	
    });
    $('.modify').click(function(){
    	if( $('.box tbody tr').hasClass('selected') ){
    		dialog.mt4add('mt4','modify');
    		
    		$('.pronum',parent.document).val( $('.box tr.selected td:eq(0) span').text() );
			$('.gruname',parent.document).val( $('.box tr.selected td:eq(1) span').text() );
			$('.rmrk',parent.document).val( $('.box tr.selected td:eq(2) span').text() );
			
			if( $('.box tr.selected td:eq(3) span').text()=='启用'){
				$('.mt4 input.rr1',parent.document).prop('checked','checked');
			}else{
				$('.mt4 input.rr2',parent.document).prop('checked','checked');
			}
			
			$('.pronum',parent.document).on('blur',function(){
	    		if( $('.pronum',parent.document).val()=='' ){
	    			$('.pronum',parent.document).closest('div').find('re').remove();
	    			$('.pronum',parent.document).closest('div').append('<re>组号不能为空!</re>');
	    		}else if( !/[0-9A-Za-z]+/.test(  $('.pronum',parent.document).val() ) ){
	    			
	    			$('.pronum',parent.document).closest('div').find('re').remove();
	    			$('.pronum',parent.document).closest('div').append('<re>组号必须是字母和数字组合</re>');
	    		}else if( arr.indexOf($('.pronum',parent.document).val())!=-1){
	    			$('.pronum',parent.document).closest('div').find('re').remove();
	    			$('.pronum',parent.document).closest('div').append('<re>该组名已经存在,请重新输入!</re>');
	    		}else{
	    			$('.pronum',parent.document).closest('div').find('re').remove();
	    		}
	    	});
	    	$('.gruname',parent.document).on('blur',function(){
	    		if( $('.gruname',parent.document).val()=='' ){
	    			$('.gruname',parent.document).closest('div').find('re').remove();
	    			$('.gruname',parent.document).closest('div').append('<re>组名不能为空!</re>');
	    		}else{
	    			$('.gruname',parent.document).closest('div').find('re').remove();
	    		}
	    	});
	    	$('.rmrk',parent.document).on('blur',function(){
	    		if( $('.rmrk',parent.document).val()=='' ){
	    			$('.rmrk',parent.document).closest('div').find('re').remove();
	    			$('.rmrk',parent.document).closest('div').append('<re>描述不能为空!</re>');
	    		}else{
	    			$('.rmrk',parent.document).closest('div').find('re').remove();
	    		}
	    	});    	
			
    	}else{
    		dialog.choicedata('请先选择一条数据!','mt4');
    	}
    });
    $('body',parent.document).on('click','.mt4 .sure,.mt4 .close,.mt4 .cancel',function(){
		$(this).closest('.zhezhao').remove();
	});
    //添加  修改 保存；
    $('body',parent.document).on('click','.mt4 .preserve',function(){
    	var url,tit=$('.pubhead span',parent.document).text();
    	if( tit=='产品参数-添加'){
    		url='insinsertPtpara.do';
    	}else{
    		url='upUpdatePtpara.do';
    	}
    	da={
			userKey:userkey,
			cmmbean:{
				gpty:$('.pronum',parent.document).val(),//paid
				name:$('.gruname',parent.document).val(),//remk
				note:$('.rmrk',parent.document).val(),
				usfg:$('.mt4 input[name="r3"]:checked',parent.document).attr('tit')//stat
			} 
    	}
		if( $('.pronum',parent.document).val()=='' ){
			$('.pronum',parent.document).closest('div').find('re').remove();
			$('.pronum',parent.document).closest('div').append('<re>组号不能为空!</re>');
		}else if( !/[0-9A-Za-z]+/.test(  $('.pronum',parent.document).val() ) ){
			
			$('.pronum',parent.document).closest('div').find('re').remove();
			$('.pronum',parent.document).closest('div').append('<re>组号必须是字母和数字组合</re>');
		}else if( arr.indexOf($('.pronum',parent.document).val())!=-1){
			$('.pronum',parent.document).closest('div').find('re').remove();
			$('.pronum',parent.document).closest('div').append('<re>该组名已经存在,请重新输入!</re>');
		}else{
			$('.pronum',parent.document).closest('div').find('re').remove();
		}
		if( $('.gruname',parent.document).val()=='' ){
			$('.gruname',parent.document).closest('div').find('re').remove();
			$('.gruname',parent.document).closest('div').append('<re>组名不能为空!</re>');
		}else{
			$('.gruname',parent.document).closest('div').find('re').remove();
		}
		if( $('.rmrk',parent.document).val()=='' ){
			$('.rmrk',parent.document).closest('div').find('re').remove();
			$('.rmrk',parent.document).closest('div').append('<re>描述不能为空!</re>');
		}else{
			$('.rmrk',parent.document).closest('div').find('re').remove();
		}   	
    	console.log( da );
    	if( $('.mt4 re',parent.document).length==0 ){
    		$.ajax({
        		url:url,
        		type:'post',
        		contentType:'application/json',
        		async:true,
        		data:JSON.stringify( da ),
        		success:function(data){
        			$('.mt4 .preserve',parent.document).closest('.zhezhao').remove();
        			if(data.code==01){
        				dialog.choicedata(data.codeMessage,'mt4');
        				getlist();
        			}else{
        				dialog.choicedata(data.codeMessage,'mt4');
        			}
        		}
        	});
    	}
    });
    
    
})