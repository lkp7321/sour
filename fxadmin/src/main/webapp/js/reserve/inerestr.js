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
	var wh=$(window).height()-200+"px",
		ww=$(window).width()-260+"px";;
		$('.boxtop').css('width',ww);
		$('.boxtop').css('height',wh);
		$('#page').css('width',ww);
	var userkey=JSON.parse( sessionStorage.getItem('menu') ).userKey;
	//列参数;
    var cols = [
        { title:'利率产品代码', name:'pdtp' ,width:100, align:'left' },
        { title:'产品描述', name:'desp' ,width:100, align:'left'},
        { title:'产品期限', name:'pdtm' ,width:150, align:'right'},
        { title:'计息方式', name:'pdbr' ,width:80, align:'right'},
        { title:'利率', name:'rate' ,width:120, align:'right'},
        { title:'起存克数', name:'mint' ,width:100, align:'right' },
        { title:'更新日期', name:'updt' ,width:100, align:'right'},
        { title:'类型', name:'jxtp' ,width:150, align:'center'},
        { title:'份额', name:'gain' ,width:150, align:'right'}
    ];
    var juesedata=false,jigoudata=false,jueseda,jigouda,listnumtotal;
    var numeng=/[A-Za-z0-9]+/,
    	numreg=/^\d+$/,
    	dotreg=/^\d+$|^[0]{1}\.{1}\d+|[1-9]+\.{1}\d+$/;

  //请求列表数据；
    getlist({
    	userKey:userkey,
    	pageNo: 1,
    	pageSize: 10
    });
    function getlist( obj ){
    	$.ajax({
    		url:'/fx/getRateList.do',
    		type:'post',
    		contentType:'application/json',
    		data:JSON.stringify( obj ),
    		async:false,
    		success:function(data){
    			if(data.code==00){
    				userdata=JSON.parse( data.codeMessage );
    				dialog.ren({'cols':cols,'wh':wh,'userdata':userdata.list});
    				listnumtotal=userdata.total;
//    				$('.page').remove();
//    				$('.boxtop').append('<div class="page"><span class="first">首页</span><span class="prev">上一页</span><span class="next">下一页</span><span class="last">末页</span><span class="nopage">第<b class="Nopage">'+userdata.pageNum+'</b>页/共<b class="Totalpage">'+userdata.pages+'</b>页</span><span class="totalpage">共<b class="total">'+userdata.total+'</b>条记录</span></div>')
    			}else if(data.code==01){
    				dialog.ren({'cols':cols,'wh':wh,'userdata':''});
    			}
    		}
    	});
    }
    //点击查询按钮
    $('.interestr .serbtn').click(function(){
    	getlist({
		 	userKey:userkey,
	    	pageNo: 1,
	    	pageSize: 10
	    });
    });
    renpage();
    function renpage(){
		layui.use(['laypage', 'layer'], function(){
			  var laypage = layui.laypage,layer = layui.layer;
			  //完整功能
			  laypage.render({
			    elem: 'page'
			    ,count:listnumtotal
			    ,theme: '#5a8bff'
			    ,layout: ['count', 'prev', 'page', 'next', 'limit', 'skip']
			    ,jump: function(orj,first){
			    	if(!first){ //点击跳页触发函数自身，并传递当前页：obj.curr
			    		getlist({
			    	    	userKey:userkey,
			    	    	'pageSize':$('.layui-laypage-limits select option:selected').attr('value')*1,
	    					'pageNo':orj.curr
	    					}
			    		);
			    	}	
			    }
			  });
		});
	}
    //点击add按钮
    $('.interestr .add').click(function(){
    	dialog.productrate('interestr','add');
    	//对页面数据进行输入验证；
    	$('.intrapro',parent.document).on('blur',function(){
 
    		if( $(this).val()==''){
    			$('.intrapro',parent.document).closest('p').find('re').remove();
    			$('.intrapro',parent.document).closest('p').append('<re>利率产品代码不能为空!</re>');
    		}else if($(this).val()!=''&& !numeng.test( $(this).val() ) ){
    			console.log(2)
    			$('.intrapro',parent.document).closest('p').find('re').remove();
    			$('.intrapro',parent.document).closest('p').append('<re>利率产品代码必须是字母和数字的组合!</re>');
    		}else{
    			$('.intrapro',parent.document).closest('p').find('re').remove();
    		}
    	});
		$('.proterm',parent.document).on('blur',function(){
    		if( $(this).val()==''){
    			$('.proterm',parent.document).closest('p').find('re').remove();
    			$('.proterm',parent.document).closest('p').append('<re>产品期限不能为空!</re>');
    		}else if( $(this).val()!=''&&!dotreg.test( $(this).val() ) ){
    			$('.proterm',parent.document).closest('p').find('re').remove();
    			$('.proterm',parent.document).closest('p').append('<re>产品期限必须是数字!</re>');
    		}else{
    			$('.proterm',parent.document).closest('p').find('re').remove();
    		}
    	});	
		$('.intercode',parent.document).on('blur',function(){
    		if( $(this).val()==''){
    			$('.intercode',parent.document).closest('p').find('re').remove();
    			$('.intercode',parent.document).closest('p').append('<re>计息方式不能为空!</re>');
    		}else if( $(this).val()!=''&&!dotreg.test( $(this).val() ) ){
    			$('.intercode',parent.document).closest('p').find('re').remove();
    			$('.intercode',parent.document).closest('p').append('<re>计息方式必须是数字!</re>');
    		}else{
    			$('.intercode',parent.document).closest('p').find('re').remove();
    		}
    	});	
		$('.stargams',parent.document).on('blur',function(){
    		if( $(this).val()==''){
    			$('.stargams',parent.document).closest('p').find('re').remove();
    			$('.stargams',parent.document).closest('p').append('<re>起始克数不能为空!</re>');
    		}else if( $(this).val()!=''&&!dotreg.test( $(this).val() ) ){
    			$('.stargams',parent.document).closest('p').find('re').remove();
    			$('.stargams',parent.document).closest('p').append('<re>起始克数必须是数字!</re>');
    		}else{
    			$('.stargams',parent.document).closest('p').find('re').remove();
    		}
    	});	
		$('.interrate',parent.document).on('blur',function(){
			if( $(this).prop('disabled') ){
				
			}else{
				if( $(this).val()==''){
	    			$('.interrate',parent.document).closest('p').find('re').remove();
	    			$('.interrate',parent.document).closest('p').append('<re>利率不能为空!</re>');
	    		}else if( $(this).val()!=''&&!dotreg.test( $(this).val() ) ){
	    			$('.interrate',parent.document).closest('p').find('re').remove();
	    			$('.interrate',parent.document).closest('p').append('<re>利率必须是数字!</re>');
	    		}else{
	    			$('.interrate',parent.document).closest('p').find('re').remove();
	    		}
			}
    	});	
		$('.share',parent.document).on('blur',function(){
			if(  $(this).prop('disabled') ){
				
			}else{
				if( $(this).val()==''){
	    			$('.share',parent.document).closest('p').find('re').remove();
	    			$('.share',parent.document).closest('p').append('<re>份额不能为空!</re>');
	    		}else if( $(this).val()!=''&&!dotreg.test( $(this).val() ) ){
	    			$('.share',parent.document).closest('p').find('re').remove();
	    			$('.share',parent.document).closest('p').append('<re>份额必须是数字!</re>');
	    		}else{
	    			$('.share',parent.document).closest('p').find('re').remove();
	    		}
			}
    	});	
		$('.prormrk',parent.document).on('blur',function(){
    		if( $(this).val()==''){
    			$('.prormrk',parent.document).closest('p').find('re').remove();
    			$('.prormrk',parent.document).closest('p').append('<re>产品描述不能为空!</re>');
    		}else{
    			$('.prormrk',parent.document).closest('p').find('re').remove();
    		}
    	});	
    });
    $('body',parent.document).on('click','.type1',function(){
		var tit=$(this).find('option:selected').attr('tit');
		if( tit=='a'){
			$('.share',parent.document).prop('disabled','disabled');
			$('.interrate',parent.document).prop('disabled','disabled');
		}else if(tit=='G'){
			$('.interrate',parent.document).closest('p').find('re').remove();
			$('.share',parent.document).removeProp('disabled');
			$('.interrate',parent.document).prop('disabled','disabled');
			
		}else if( tit=="D"){
			$('.interrate',parent.document).removeProp('disabled');
			$('.share',parent.document).prop('disabled','disabled');
			$('.share',parent.document).closest('p').find('re').remove();
		}
	});
    //点击查询；
    $('li.serbtn span').click(function(){
    	
    });
    //点击保存按钮；
    $('body',parent.document).on('click','.interestr .save',function(){
    	//console.log(   )
    	var data,
    		tit=$('.pubhead',parent.document).attr('tit'),
    		url;
		if( $('.intrapro',parent.document).val()==''){
			$('.intrapro',parent.document).closest('p').find('re').remove();
			$('.intrapro',parent.document).closest('p').append('<re>利率产品代码不能为空!</re>');
		}else if($('.intrapro',parent.document).val()!=''&&!numeng.test($('.intrapro',parent.document).val() ) ){
			$('.intrapro',parent.document).closest('p').find('re').remove();
			$('.intrapro',parent.document).closest('p').append('<re>利率产品代码必须是字母和数字的组合!</re>');
		}else{
			$('.intrapro',parent.document).closest('p').find('re').remove();
		}
		if( $('.proterm',parent.document).val()==''){
			$('.proterm',parent.document).closest('p').find('re').remove();
			$('.proterm',parent.document).closest('p').append('<re>产品期限不能为空!</re>');
		}else if( $('.proterm',parent.document).val()!=''&&!dotreg.test( $('.proterm',parent.document).val() ) ){
			$('.proterm',parent.document).closest('p').find('re').remove();
			$('.proterm',parent.document).closest('p').append('<re>产品期限必须是数字!</re>');
		}else{
			$('.proterm',parent.document).closest('p').find('re').remove();
		}
		if( $('.intercode',parent.document).val()==''){
			$('.intercode',parent.document).closest('p').find('re').remove();
			$('.intercode',parent.document).closest('p').append('<re>计息方式不能为空!</re>');
		}else if( $('.intercode',parent.document).val()!=''&&!dotreg.test( $('.intercode',parent.document).val() ) ){
			$('.intercode',parent.document).closest('p').find('re').remove();
			$('.intercode',parent.document).closest('p').append('<re>计息方式必须是数字!</re>');
		}else{
			$('.intercode',parent.document).closest('p').find('re').remove();
		}
		if( $('.stargams',parent.document).val()==''){
			$('.stargams',parent.document).closest('p').find('re').remove();
			$('.stargams',parent.document).closest('p').append('<re>起始克数不能为空!</re>');
		}else if( $('.stargams',parent.document).val()!=''&&!dotreg.test( $('.stargams',parent.document).val() ) ){
			$('.stargams',parent.document).closest('p').find('re').remove();
			$('.stargams',parent.document).closest('p').append('<re>起始克数必须是数字!</re>');
		}else{
			$('.stargams',parent.document).closest('p').find('re').remove();
		}
		if( $('.interrate',parent.document).prop('disabled') ){
			
		}else{
			if( $('.interrate',parent.document).val()==''){
				$('.interrate',parent.document).closest('p').find('re').remove();
				$('.interrate',parent.document).closest('p').append('<re>利率不能为空!</re>');
			}else if( $('.interrate',parent.document).val()!=''&&!dotreg.test( $('.interrate',parent.document).val() ) ){
				$('.interrate',parent.document).closest('p').find('re').remove();
				$('.interrate',parent.document).closest('p').append('<re>利率必须是数字!</re>');
			}else{
				$('.interrate',parent.document).closest('p').find('re').remove();
			}
		}
		
		if( $('.share',parent.document).prop('disabled') ){
			
		}else{
			if( $('.share',parent.document).val()==''){
				$('.share',parent.document).closest('p').find('re').remove();
				$('.share',parent.document).closest('p').append('<re>份额不能为空!</re>');
			}else if($('.share',parent.document).val()!=''&&!dotreg.test( $('.share',parent.document).val() ) ){
				$('.share',parent.document).closest('p').find('re').remove();
				$('.share',parent.document).closest('p').append('<re>份额必须是数字!</re>');
			}else{
				$('.share',parent.document).closest('p').find('re').remove();
			}
		}
		if( $('.prormrk',parent.document).val()==''){
			$('.prormrk',parent.document).closest('p').find('re').remove();
			$('.prormrk',parent.document).closest('p').append('<re>产品描述不能为空!</re>');
		}else{
			$('.prormrk',parent.document).closest('p').find('re').remove();
		}
 
    	if( tit=='add'){
    		url='doRateInsert.do';
    		data = {
				userKey: userkey,
				pdtp : $('.intrapro',parent.document).val(),
				desp : $('.prormrk',parent.document).val(),
				gain :$('.share',parent.document).val(),
				jxtp :$('.type1 option:selected',parent.document).attr('tit'),
				mint :$('.stargams',parent.document).val(),
				pdbr : $('.intercode',parent.document).val(),
				pdtm : $('.proterm',parent.document).val(),
				rate : $('.interrate',parent.document).val()
			}
    	}else if(tit=='modify'){
    		url='doRateUpdate.do';
    		data = {
				userKey:userkey,
				pdtp : $('.intrapro',parent.document).val(),
				gain :$('.share',parent.document).val(),
				mint : $('.stargams',parent.document).val(),
				rate :$('.interrate',parent.document).val()
			}
    	}
    	console.log(data)
    	if($('.type1 option:selected',parent.document).attr('tit')=='a'){
    		dialog.choicedata('请先选择类型!','inerestr');
    	}else{
    		if( $('.interestr re',parent.document).length==0){
        		$.ajax({
            		url:url,
            		type:'post',
            		contentType:'application/json',
            		data:JSON.stringify( data ),
            		async:true,
            		success:function(data){
            			$('.interestr .save',parent.document).closest('.zhezhao').remove();
            			if(data.code==00){
            				dialog.choicedata(data.codeMessage,'inerestr');
            				getlist({
            			    	userKey:userkey,
            			    	pageNo: 1,
            			    	pageSize: 10
            			    });
            			}else if(data.code==01){
            				dialog.choicedata(data.codeMessage,'inerestr');
            			}
            		}
            	});
        	}
    	}
    });
    //点击修改按钮
    $('.interestr .modify').click(function(){
    	if( $('.box tbody tr').hasClass('selected') ){
    		//console.log($('.box tr.selected td:eq(4) span').text())
    		dialog.productrate('interestr','modify');
    		$('.intrapro',parent.document).val( $('.box tr.selected td:eq(0) span').text() );
    		$('.prormrk',parent.document).val( $('.box tr.selected td:eq(1) span').text() );
    		$('.proterm',parent.document).val( $('.box tr.selected td:eq(2) span').text() );
    		$('.intercode',parent.document).val( $('.box tr.selected td:eq(3) span').text() );
    		$('.stargams',parent.document).val( $('.box tr.selected td:eq(5) span').text() );
    		$('.type2',parent.document).val( $('.box tr.selected td:eq(7) span').text() );
    		$('.interrate',parent.document).val( $('.box tr.selected td:eq(4) span').text() );
    		$('.share',parent.document).val( $('.box tr.selected td:eq(8) span').text() );
    		
    		if( $('.box tr.selected td:eq(7) span').text()=='利率'){
    			$('.share',parent.document).prop('disabled','true');
    			$('.interrate',parent.document).removeProp('disabled');
    		}else{
    			$('.interrate',parent.document).prop('disabled','true');
    			$('.share',parent.document).removeProp('disabled');
    		}
    		$('.share',parent.document).on('blur',function(){
    			if(  $(this).prop('disabled') ){
    			}else{
    				if( $(this).val()==''){
    	    			$('.share',parent.document).closest('p').find('re').remove();
    	    			$('.share',parent.document).closest('p').append('<re>份额不能为空!</re>');
    	    		}else if( $(this).val()!=''&&!dotreg.test( $(this).val() ) ){
    	    			$('.share',parent.document).closest('p').find('re').remove();
    	    			$('.share',parent.document).closest('p').append('<re>份额必须是数字!</re>');
    	    		}else{
    	    			$('.share',parent.document).closest('p').find('re').remove();
    	    		}
    			}
        	});	
    		$('.interrate',parent.document).on('blur',function(){
    			if( $(this).prop('disabled') ){
    			}else{
    				if( $(this).val()==''){
    	    			$('.interrate',parent.document).closest('p').find('re').remove();
    	    			$('.interrate',parent.document).closest('p').append('<re>利率不能为空!</re>');
    	    		}else if( $(this).val()!=''&&!dotreg.test( $(this).val() ) ){
    	    			$('.interrate',parent.document).closest('p').find('re').remove();
    	    			$('.interrate',parent.document).closest('p').append('<re>利率必须是数字!</re>');
    	    		}else{
    	    			$('.interrate',parent.document).closest('p').find('re').remove();
    	    		}
    			}
        	});	
    		$('.stargams',parent.document).on('blur',function(){
        		if( $(this).val()==''){
        			$('.stargams',parent.document).closest('p').find('re').remove();
        			$('.stargams',parent.document).closest('p').append('<re>起始克数不能为空!</re>');
        		}else if( $(this).val()!=''&&!dotreg.test( $(this).val() ) ){
        			$('.stargams',parent.document).closest('p').find('re').remove();
        			$('.stargams',parent.document).closest('p').append('<re>起始克数必须是数字!</re>');
        		}else{
        			$('.stargams',parent.document).closest('p').find('re').remove();
        		}
        	});	
		}else{ 
			dialog.choicedata('请先选择一条数据!','inerestr');
		}
    });
	$('body',parent.document).on('click','.inerestr .sure,.interestr .cance,.interestr .close',function(){
		$(this).closest('.zhezhao').remove();
	});
	/*----------------快速搜索功能的实现------------------------*/
	$('.info_serbtn').click(function(){
		  dialog.serchData($('.info_seript').val(),'.mmg-headWrapper');
    });
	
	//点击分页;
    $('.boxtop').on('click','.first',function(){
    	var Nopage=$('.Nopage').text();
 	    if(Nopage>1){
 	    	Nopage=1;
 	    	getlist({ 'stat':$('.acudeal option:selected').attr('tit'),'pageNo':Nopage,'pageSize':10} );
 	    }
    });
	$('.boxtop').on('click','.prev',function(){
	    var Nopage=$('.Nopage').text();
	    if(Nopage>1){
	    	Nopage=Nopage-1;
	    	getlist({ 'stat':$('.acudeal option:selected').attr('tit'),'pageNo':Nopage,'pageSize':10} );
	    }
	});
	$('.boxtop').on('click','.next',function(){
		var Nopage=$('.Nopage').text(),
			Totalpage=$('.Totalpage').text();
	    if(Nopage<Totalpage){
	    	Nopage=Nopage*1+1;
	    	getlist({ 'stat':$('.acudeal option:selected').attr('tit'),'pageNo':Nopage,'pageSize':10} );
	    }
	});
	$('.boxtop').on('click','.last',function(){
		var Nopage=$('.Nopage').text(),
		Totalpage=$('.Totalpage').text();
	    if(Nopage<Totalpage){
	    	Nopage=Totalpage;
	    	getlist({ 'stat':$('.acudeal option:selected').attr('tit'),'pageNo':Nopage,'pageSize':10} );
	    }
	});
})
