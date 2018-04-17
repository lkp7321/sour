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
	var userkey=JSON.parse( sessionStorage.getItem('menu') ).userKey,
		emailreg=/^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/,
		phonereg=/(^(\d{3,4}-)?\d{7,8})$|(1[3|5|7|8]\d{9})/,
		IPreg=/^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/,
		listnumtotal;
	
	//列参数;
    var cols = [
            { title:'申请人', name:'name',width:60,align:'left' },
            { title:'单位', name:'unit',width:60, align:'left'},
            { title:'联系电话', name:'telp',width:60, align:'right'},
            { title:'Email', name:'email',width:60,align:'left'},
            { title:'使用IP', name:'ip',width:60,align:'right'},
            { title:'接口名称', name:'jksm',width:60,align:'left'},
            { title:'状态', name:'stat',width:60,align:'center'}
    ];
    //请求列表；
    getlist({ 'stat':$('.acudeal option:selected').attr('tit'),'pageNo':1,'pageSize':10} );
    renpage();//渲染页；
    function getlist(obj){
    	$.ajax({
    		url:'/fx/price/allLiquid.do',
    		type:'post',
    		contentType:'application/json',
    		data:JSON.stringify( obj ),
    		async:false,
    		success:function(data){
    			if(data.code==01){
    				userdata= data.codeMessage ;
    				ren({'cols':cols,'wh':wh,'userdata':userdata.list});
    				listnumtotal=userdata.total;
    			}else if(data.code==00){
    				ren({'cols':cols,'wh':wh,'userdata':''});
    			}
    		}
    	});
    }
    
	$('.acudeal').change(function(){
		var tit=$('.acudeal option:selected').attr('tit');
		    getlist({ 'stat':tit,'pageNo':1,'pageSize':10} );
		    renpage();
		  if(tit=='0'){
			  $('.modify').removeAttr('disabled');
			  $('.toconfig').attr({'disabled':'disabled'});
		  }else if(tit=='1'){
			  $('.toconfig').removeAttr('disabled');
			  $('.modify').attr({'disabled':'disabled'});
		  }else{			  
			  $('.toconfig').attr({'disabled':'disabled'});
			  $('.modify').attr({'disabled':'disabled'});
		  }
	});
	//点击修改和配置；
	$('.modify').click(function(){
		if( $('.box tbody tr').hasClass('selected') ){
			dialog.pricemodify('priceuses');
			$('.dealnum',parent.document).val( $('.box tr.selected td:eq(0) span').text() );
			$('.asknum',parent.document).val( $('.box tr.selected td:eq(1) span').text() );
			$('.pronum',parent.document).val( $('.box tr.selected td:eq(2) span').text() );
			$('.dealrate',parent.document).val( $('.box tr.selected td:eq(3) span').text() );
			$('.asktype',parent.document).val( $('.box tr.selected td:eq(4) span').text() );
			$('.intname',parent.document).val( $('.box tr.selected td:eq(5) span').text() );
			$('.appID',parent.document).val( $('.box tr.selected td:eq(0)').attr('sqid') );
		}else{
			dialog.choicedata('请先选择一条数据!','priceuse1');
		}
	});
	$('.toconfig').click(function(){
		var str='',str1='',inid;
		if( $('.box tbody tr').hasClass('selected') ){
			dialog.priceconfig('priceuses');
			$.ajax({
	    		url:'/fx/price/getLiquiPtno.do',
	    		type:'post',
	    		contentType:'application/json',
	    		async:true,
	    		success:function(data){
	    			if(data.code==01){
	    				userdata=JSON.parse( data.codeMessage );
	    				for(var i=0;i<userdata.length;i++){
	    					str+="<option pild="+userdata[i].PTID+">"+userdata[i].PTNM+"</option>"
	    				}
	    				$('.choic',parent.document).html( str );
	    			}else if(data.code==02){
	    				dialog.choicedata(data.codeMessage,'priceuse1');
	  	    		}
	    		}
	    	});	
			//配置页面；
			$.ajax({
	    		url:'/fx/price/getOnLiquid.do',
	    		type:'post',
	    		contentType:'application/json',
	    		async:false,
	    		data:JSON.stringify({
	    					'sqid':$('.box tbody tr.selected td:eq(0)').attr('sqid'),
	    					'name':$('.box tr.selected td:eq(0) span').text(),
	    					'unit':$('.box tr.selected td:eq(1) span').text()
	    		}),
	    		success:function(data){
	    			userdata=JSON.parse(data.codeMessage)
	    			if(data.code==01){
	    				inid=userdata.inid;
	    				$('.ID',parent.document).val( userdata.inid );
	    				$('.rppsd',parent.document).val( userdata.pass );
	    			}
	    		}
	    	});	
			$('.rpname',parent.document).val( $('.box tr.selected td:eq(5) span').text() );
			$('.IP',parent.document).val( $('.box tr.selected td:eq(4) span').text() );
			//请求右侧列表；
			$.ajax({
	    		url:'/fx/price/allLiquidJK.do',
	    		type:'post',
	    		contentType:'application/json',
	    		data:inid,
	    		async:true,
	    		success:function(data){
	    			if(data.code==01){
	    				userdata=JSON.parse( data.codeMessage );
	    				for(var i=0;i<userdata.length;i++){
	    					str1+="<li ptid="+userdata[i].ptid+">"+userdata[i].ptnm+'-'+userdata[i].exnm+"</li>"
	    				}
	    				$('.rightt ul',parent.document).html( str1 );
	    			}else if(data.code==02){
	    				dialog.choicedata(data.codeMessage,'priceuse1');
	  	    		}
	    		}
	    	});	
			//更改左侧列表；
			$('.choic',parent.document).on('change',function(){
				var ptid=$(this).find('option:selected').attr('pild'),
					str1='';
				$.ajax({
		    		url:'/fx/price/getLiquidptidJK.do',
		    		type:'post',
		    		contentType:'application/json',
		    		data:ptid,
		    		async:true,
		    		success:function(data){
		    			if(data.code==01){
		    				userdata=JSON.parse( data.codeMessage );
		    				for(var i=0;i<userdata.length;i++){
		    					str1+="<li excd="+userdata[i].excd+">"+userdata[i].exnm+"</li>"
		    				}
		    				$('.cuurname',parent.document).html( str1 );
		    			}else if(data.code==02){
		    				//dialog.choicedata(data.codeMessage,'priceuse1');
		  	    		}
		    		}
		    	});
			});
			//点击添加和删除；
		
		}else{
			dialog.choicedata('请先选择一条数据!','priceuse1');
		}
	});
	//点击添加、删除；
	$('body',parent.document).on('click','.priceuses .add',function(){
		var txt=$('.cuurname li.blu',parent.document).text(),
			ptnm=$('.choic option:selected',parent.document).text();
		if(txt==''){
			dialog.choicedata('请先选择一条数据!','priceuses');
		}else{
			$('.productname',parent.document).append('<li>'+ptnm+'-'+txt+'</li>');
		}
	});	
	$('body',parent.document).on('click','.priceuses .rel',function(){
		//console.log( $('.productname li.blu',parent.document).length )
		if( $('.productname li.blu',parent.document).length==1){
			$('.productname li.blu',parent.document).remove();
		}else{
			dialog.choicedata('请先选择一条数据!','priceuses');
		}
	});	
	$('body',parent.document).on('click','.cuurname li,.productname li',function(){
		$(this).siblings().removeClass('blu');
		$(this).addClass('blu');
	});
	$('body',parent.document).on('click','.cuurname li,.productname li',function(){
		$(this).siblings().removeClass('blu');
		$(this).addClass('blu');
	});
	//点击配置保存；
	$('body',parent.document).on('click','.priceuses .success',function(){
		var ptnmlist=[],configlist=[];
		$('.productname li',parent.document).each(function(i,v){
			if( $(v).length>0){
				configlist.push({
					'inid':$('.ID',parent.document).val(),
					'exnm':$(v).text().split('-')[1]
				});
				ptnmlist.push({
					'ptnm':$(v).text().split('-')[0]
				});
			}
		});
		if( ptnmlist.length>0&&configlist.length>0){
			data={
					userKey:userkey,
					inid:$('.ID',parent.document).val(),
					put_ConfigList:configlist,
					pdtinList:ptnmlist,
					put_Liquid :{
						sqid:$('.box tr.selected td:eq(0)').attr('sqid'),
						jkmc:$('.rpname',parent.document).val(),
						inid:$('.ID',parent.document).val(),
						pass:$('.rppsd',parent.document).val(),
						ip:$('.IP',parent.document).val()
					}
				}	
				$.ajax({
		    		url:'/fx/price/upBuildLiquid.do',
		    		type:'post',
		    		contentType:'application/json',
		    		data:JSON.stringify( data ),
		    		async:true,
		    		success:function(data){
		    			$('.priceuses .success',parent.document).closest('.zhezhao').remove();
		    			if(data.code==01){
		    				dialog.choicedata(data.codeMessage,'priceuses');
		    				getlist({ 'stat':$('.acudeal option:selected').attr('tit'),'pageNo':1,'pageSize':10} );
		    			}else if(data.code==02){
		    				dialog.choicedata(data.codeMessage,'priceuses');
		  	    		}
		    		}
		    	});
		}else{
			dialog.choicedata('请先选择一条数据!','priceuses');
		}
	});
	//点击申请；
	$('.apply').click(function(){
		dialog.priceappcli('priceuse');
		
		$('.lower',parent.document).on('click',function(){
			var val=$('.rtime',parent.document).val();
				val=val-1;
			if(val<=1){
				$('.rtime',parent.document).val( 1);
			}else{
				$('.rtime',parent.document).val( val );
			}
		});
		$('.up',parent.document).on('click',function(){
			var val=$('.rtime',parent.document).val();
				val=val*1+1;
			if(val>=10){
				$('.rtime',parent.document).val( 10 );
			}else{
				$('.rtime',parent.document).val( val );
			}
		});
		$('.rtime',parent.document).on('blur',function(){
			if( $(this).val()==''){
				$(this).val(1);
			}else if( !/^\d+$/.test($(this).val())){
				$(this).val(1);
			}else if($(this).val()>10||$(this).val()<1){
				$(this).val(1);
			}
		});
		$('.dealnum',parent.document).on('blur',function(){
			if( $(this).val()==''){
				$('.dealnum',parent.document).closest('div').find('re').remove();
				$('.dealnum',parent.document).closest('div').append('<re>申请人不能为空!</re>');
			}else if($(this).val()!=''&&$(this).val().length<4){
				$('.dealnum',parent.document).closest('div').find('re').remove();
				$('.dealnum',parent.document).closest('div').append('<re>申请人名称过短!</re>');
			}else{
				$('.dealnum',parent.document).closest('div').find('re').remove();
			}
		});
		$('.asknum',parent.document).on('blur',function(){
			if( $(this).val()==''){
				$('.asknum',parent.document).closest('div').find('re').remove();
				$('.asknum',parent.document).closest('div').append('<re>单位不能为空!</re>');
			}else if($(this).val()!=''&&$(this).val().length<4){
				$('.asknum',parent.document).closest('div').find('re').remove();
				$('.asknum',parent.document).closest('div').append('<re>单位名称过短!</re>');
			}else{
				$('.asknum',parent.document).closest('div').find('re').remove();
			}
		});
		$('.pronum',parent.document).on('blur',function(){  //联系电话
			if( $(this).val()==''){
				$('.pronum',parent.document).closest('div').find('re').remove();
				$('.pronum',parent.document).closest('div').append('<re>联系电话不能为空!</re>');
			}else if($(this).val()!=''&&!phonereg.test( $(this).val() )){
				$('.pronum',parent.document).closest('div').find('re').remove();
				$('.pronum',parent.document).closest('div').append('<re>请输入正确的联系电话!</re>');
			}else{
				$('.pronum',parent.document).closest('div').find('re').remove();
			}
		});
		$('.dealrate',parent.document).on('blur',function(){ //邮箱；
			if( $(this).val()==''){
				$('.dealrate',parent.document).closest('div').find('re').remove();
				$('.dealrate',parent.document).closest('div').append('<re>邮箱不能为空!</re>');
			}else if($(this).val()!=''&&!emailreg.test( $(this).val() )){
				$('.dealrate',parent.document).closest('div').find('re').remove();
				$('.dealrate',parent.document).closest('div').append('<re>请输入正确的邮箱!</re>');
			}else{
				$('.dealrate',parent.document).closest('div').find('re').remove();
			}
			
		});
		$('.asktype',parent.document).on('blur',function(){
			if( $(this).val()==''){
				$('.asktype',parent.document).closest('div').find('re').remove();
				$('.asktype',parent.document).closest('div').append('<re>IP地址不能为空!</re>');
			}else if($(this).val()!=''&&!IPreg.test( $(this).val() )){
				$('.asktype',parent.document).closest('div').find('re').remove();
				$('.asktype',parent.document).closest('div').append('<re>请输入正确的IP地址!</re>');
			}else{
				$('.asktype',parent.document).closest('div').find('re').remove();
			}
		});
	});
	//点击登记和重填；
	$('body',parent.document).on('click','.priceuses .succss',function(){     //审批登记保存
		var data;
		data={
			userKey:userkey,
			put_Liquid:{
				name:$('.dealnum',parent.document).val(),
				unit:$('.asknum',parent.document).val(),
				telp:$('.pronum',parent.document).val(),
				email:$('.dealrate',parent.document).val(),
				ip:$('.asktype',parent.document).val(),
				jkmc:$('.intname',parent.document).val(),
				stat:$('.priceuses input[name="aa"]:checked',parent.document).attr('tit'),
				sqid:$('.appID',parent.document).val()
			}
		}
		  // console.log( data )
			$.ajax({
	    		url:'/fx/price/upLiquid.do',
	    		type:'post',
	    		contentType:'application/json',
	    		data:JSON.stringify( data ),
	    		async:true,
	    		success:function(data){
	    			$('.priceuses .succss',parent.document).closest('.zhezhao').remove();
	    			if(data.code==01){
	    				dialog.choicedata(data.codeMessage,'priceuse1');
	    				getlist({ 'stat':$('.acudeal option:selected').attr('tit'),'pageNo':1,'pageSize':10} );
	    				renpage();
	    			}else if(data.code==02){
	    				dialog.choicedata(data.codeMessage,'priceuse1');
	    			}
	    		}
	    	});
	});
	$('body',parent.document).on('click','.priceuse .succss',function(){
		var data;
		if( $('.dealnum',parent.document).val()==''){
			$('.dealnum',parent.document).closest('div').find('re').remove();
			$('.dealnum',parent.document).closest('div').append('<re>申请人不能为空!</re>');
		}else if($('.dealnum',parent.document).val()!=''&&$('.dealnum',parent.document).val().length<4){
			$('.dealnum',parent.document).closest('div').find('re').remove();
			$('.dealnum',parent.document).closest('div').append('<re>申请人名称过短!</re>');
		}else{
			$('.dealnum',parent.document).closest('div').find('re').remove();
		}
		if( $('.asknum',parent.document).val()==''){
			$('.asknum',parent.document).closest('div').find('re').remove();
			$('.asknum',parent.document).closest('div').append('<re>单位不能为空!</re>');
		}else if($('.asknum',parent.document).val()!=''&&$('.asknum',parent.document).val().length<4){
			$('.asknum',parent.document).closest('div').find('re').remove();
			$('.asknum',parent.document).closest('div').append('<re>单位名称过短!</re>');
		}else{
			$('.asknum',parent.document).closest('div').find('re').remove();
		}
		
		if( $('.pronum',parent.document).val()==''){
			$('.pronum',parent.document).closest('div').find('re').remove();
			$('.pronum',parent.document).closest('div').append('<re>联系电话不能为空!</re>');
		}else if($('.pronum',parent.document).val()!=''&&!phonereg.test( $('.pronum',parent.document).val() )){
			$('.pronum',parent.document).closest('div').find('re').remove();
			$('.pronum',parent.document).closest('div').append('<re>请输入正确的联系电话!</re>');
		}else{
			$('.pronum',parent.document).closest('div').find('re').remove();
		}
		
		if( $('.dealrate',parent.document).val()==''){
			$('.dealrate',parent.document).closest('div').find('re').remove();
			$('.dealrate',parent.document).closest('div').append('<re>邮箱不能为空!</re>');
		}else if($('.dealrate',parent.document).val()!=''&&!emailreg.test( $('.dealrate',parent.document).val() )){
			$('.dealrate',parent.document).closest('div').find('re').remove();
			$('.dealrate',parent.document).closest('div').append('<re>请输入正确的邮箱!</re>');
		}else{
			$('.dealrate',parent.document).closest('div').find('re').remove();
		}
		if( $('.asktype',parent.document).val()==''){
			$('.asktype',parent.document).closest('div').find('re').remove();
			$('.asktype',parent.document).closest('div').append('<re>IP地址不能为空!</re>');
		}else if($('.asktype',parent.document).val()!=''&&!IPreg.test( $('.asktype',parent.document).val() )){
			$('.asktype',parent.document).closest('div').find('re').remove();
			$('.asktype',parent.document).closest('div').append('<re>请输入正确的IP地址!</re>');
		}else{
			$('.asktype',parent.document).closest('div').find('re').remove();
		}
		data={
			userKey:userkey,
			put_Liquid:{
				name:$('.dealnum',parent.document).val(),
				unit:$('.asknum',parent.document).val(),
				telp:$('.pronum',parent.document).val(),
				email:$('.dealrate',parent.document).val(),
				ip:$('.asktype',parent.document).val(),
				jkmc:$('.intname',parent.document).val(),
				jksm:$('.intrmrk',parent.document).val(),
				conu:$('.rtime',parent.document).val()
			}
		}
		if( $('.priceuse re',parent.document).length==0){
			$.ajax({
	    		url:'/fx/price/addLiquid.do',
	    		type:'post',
	    		contentType:'application/json',
	    		data:JSON.stringify( data ),
	    		async:true,
	    		success:function(data){
	    			$('.priceuse .succss',parent.document).closest('.zhezhao').remove();
	    			if(data.code==01){
	    				dialog.choicedata(data.codeMessage,'priceuse');
	    			}else if(data.code==00){
	    				dialog.choicedata(data.codeMessage,'priceuse');
	    			}
	    		}
	    	});
		}
	});
	$('body',parent.document).on('click','.faile',function(){
		$('.dealnum,.asknum,.pronum,.dealrate,.asktype,.intname,.intrmrk',parent.document).val(' ');
		$('.rtime',parent.document).val('1');
	});	
	$('body',parent.document).on('click','.priceuse .close,.priceuse1 .sure,.priceuses .faile,.priceuses .close,.priceuses .sure,.priceuse .sure',function(){
		$(this).closest('.zhezhao').remove();
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
			horizrailenabled:obj.horizrailenabled
	  });
	  if( obj.userdata.length>0){
		  mmg.on('loadSuccess',function(){
			  $('.box tbody tr').each(function(i,v){
				 $(v).find('td').eq('0').attr('sqid',obj.userdata[i].sqid);
				 $(v).find('td').eq('0').attr('inid',obj.userdata[i].inid);
				 $(v).find('td').eq('0').attr('pass',obj.userdata[i].pass);
			  });
		  });
	  }
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
    		    			 'stat':$('.acudeal option:selected').attr('tit'),
    		    			 'pageNo':obj.curr,
    		    			 'pageSize':$('.layui-laypage-limits select option:selected').attr('value')*1
    		    		});
    		    	}	
    		    }
    		  });
    	});
    }
	/*//点击分页;
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
	});*/
})