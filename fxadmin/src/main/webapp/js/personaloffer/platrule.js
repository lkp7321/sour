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
	ww=$(window).width()-250+"px";;

	$('.boxtop').css('width',ww);
	$('.boxtop').css('height',wh);
	$(window).resize(function(){
		$('.boxtop').css('width',$(window).width()-300+'px');
		$('.boxtop').css('height',$(window).height()-300+'px');
	});
	var usnm=sessionStorage.getItem('usnm'),
		product=sessionStorage.getItem('product'),
		userkey=JSON.parse( sessionStorage.getItem('menu') ).userKey,
		listnumtotal,
		user={'usnm':usnm,'prod':product},
		numreg=/^\d+$/;
	
	$.ajax({
		url:'/fx/cklis.do',
		type:'post',
		contentType:'application/json',
		data:userkey,
		async:false,
		success:function(data){
			var str='<option vas="">所有</option>',listdata;
			if( data.code==00){
				 //当前没有数据；
			}else if(data.code==01){
				listdata=data.codeMessage;
				for(var i=0,num=listdata.length;i<num;i++){
					str+='<option vas='+listdata[i].ckno+'>'+listdata[i].prcd+'</option>';
				}
				$('.classionopen').html(str);
			}
		}
	});
	//列参数;
    var cols = [
        { title:'敞口名称', name:'prcd' ,width:80, align:'left' },
        { title:'货币对', name:'exnm' ,width:80, align:'left'},
        { title:'平盘方式', name:'ppfs' ,width:60, align:'center'},
        { title:'达限方式', name:'dxfs' ,width:80, align:'left'},
        { title:'达限金额', name:'dxje' ,width:60, align:'right'},
        { title:'平盘余额', name:'ppye' ,width:60, align:'right'},
        { title:'止盈点数', name:'zybl' ,width:60, align:'right'},
        { title:'止损点数', name:'zsbl' ,width:60, align:'right'},
        { title:'止盈金额', name:'zyam' ,width:60, align:'right'},
        { title:'止损金额', name:'zsam' ,width:60, align:'right'},
        { title:'重复次数', name:'cont',width:60, align:'right'},
        { title:'可用状态', name:'ckfg' ,width:80, align:'center'}
    ];
    //获取数据；
    $.ajax({
    	url:'/fx/listPpcon.do',
    	type:'post',
		contentType:'application/json',
		data:JSON.stringify({'userKey':userkey,'ckno':'','pageNo':1,'pageSize':10}),
		async:false,
		success:function(data){
			var userdata= data.codeMessage;
			if( data.code=='00'){
				 //当前没有数据；
				ren({'userdata':'','cols':cols,'wh':wh,'checked':true});
			}else if(data.code=='01'){
				ren({'userdata':userdata.list,'cols':cols,'wh':wh,'checked':true});
				listnumtotal=userdata.total;
			}
		}
    });
    renpage();
    $(".mmg-bodyWrapper").niceScroll({
			touchbehavior:false,
			cursorcolor:"#666",
			cursoropacitymax:0.7,
			cursorwidth:6,
			background:"#ccc",
			autohidemode:false,
			horizrailenabled:false
	});
	$('.buttons .modify').click(function(){
		if( $('.box tr.selected').length>=1){
			dialog.platruleadd('platrule');
			
			$('.platopenname',parent.document).val( $('tr.selected td:eq(1) span').text() );
			$('.moneypa',parent.document).val( $('tr.selected td:eq(2) span').text() );
			
			$('.endmoney',parent.document).val( $('tr.selected td:eq(5) span').text() );
			$('.platmoney',parent.document).val( $('tr.selected td:eq(6) span').text() );
			
			$('.endbeatdot',parent.document).val( $('tr.selected td:eq(7) span').text() );
			$('.endlossdot',parent.document).val( $('tr.selected td:eq(8) span').text() );
			
			$('.endbeatmon',parent.document).val( $('tr.selected td:eq(9) span').text() );
			$('.endlossmon',parent.document).val( $('tr.selected td:eq(10) span').text() );
			$('.repeatnum',parent.document).val( $('tr.selected td:eq(11) span').text() );
			//平盘方式  金额达限 可用状态
			if( $('tr.selected td:eq(3) span').text()=='自动' ){
				$('.plattype input[data-tit="auto"]',parent.document).prop('checked','checked');
			}else{
				$('.plattype input[data-tit="hand"]',parent.document).prop('checked','checked');
			}
			
			if( $('tr.selected td:eq(12) span').text()=='启用' ){
				$('.couuse input[data-tit="start"]',parent.document).prop('checked','checked');
			}else{
				$('.couuse input[data-tit="stop"]',parent.document).prop('checked','checked');
			}
			$('.endlimit option',parent.document).each(function(i,v){
				if( $(v).text()==$('tr.selected td:eq(4) span').text() ){
					$(v).attr('selected','selected');
				}
			});
			//input[type="number"];
			$('.up',parent.document).click(function(){
				var _val=$(this).closest('p').find('input[type="text"]').val();
				if(_val>=100){
				    _val=_val;
				}else{
					_val=++_val;
				}
				$(this).closest('p').find('input[type="text"]').val(_val);
			});
			$('.lower',parent.document).click(function(){
				var _val=$(this).closest('p').find('input[type="text"]').val();
				if(_val<=0){
					_val=_val;
				}else{
					_val=--_val;
				}
				$(this).closest('p').find('input[type="text"]').val(_val);
			});
			$('.endbeatdot,.endlossdot,.repeatnum',parent.document).blur(function(){
				var _val=$(this).val();
				if(_val<=0||_val>=100||!numreg.test(_val) ){
					$(this).val('0');
				}
			});
		}else{
			dialog.choicedata('请先选择一条数据','platrule');
		}
	});
	$('body',parent.document).on('click','.platrule .sav',function(){
		var a,b;
		//值需要获取;
		//平盘方式  金额达限 可用状态
		if( $('.plattype input[name="aa"]:checked',parent.document).data('tit')=='auto'){
			a='02';
		}else{
			a='01';
		}
		if($('.couuse input[name="bb"]:checked',parent.document).data('tit')=='start'){
			b=0
		}else{
			b=1;   //还有一个注销；
		}
		var ppcontro={
			'ckno':$('.box tr.selected td:eq(1)').attr('ckno'),
			'exnm':$('.moneypa',parent.document).val(),
		    'ppfs':a,
			'dxfs':$('.endlimit option:selected',parent.document).attr('value'),
			'dxje':$('.endmoney',parent.document).val(),
			'ppye':$('.platmoney',parent.document).val(),
			'zybl':$('.endbeatdot',parent.document).val(),
			'zsbl':$('.endlossdot',parent.document).val(),
			'zyam':$('.endbeatmon',parent.document).val(),
			'zsam':$('.endlossmon',parent.document).val(),
			'ckfg':b,
			'cont':$('.repeatnum',parent.document).val()
		}
		$.ajax({
			url:'/fx/upPpcon.do',
			type:'post',
			contentType:'application/json',
			data:JSON.stringify({'userKey':userkey,'ppcontro':ppcontro}),
			async:false,
			success:function(data){
				if(data.code==02){
					dialog.choicedata(data.codeMessage,'platrule','aaa');
				}else if(data.code==01){
					dialog.choicedata(data.codeMessage,'platrule','aaa');
					//render('listPpcon.do', '');
					render('/fx/listPpcon.do',{'ckno':'','pageNo':1,'pageSize':$('.layui-laypage-limits select option:selected').attr('value')*1});
					renpage();
				}
			}
		});
	});
	function render(obj, obj1){
		$.ajax({
    		url:obj,
    		type:'post',
    		contentType:'application/json',
    		data:JSON.stringify({'userKey':userkey,'ckno':obj1.ckno,'pageNo':obj1.pageNo,'pageSize':obj1.pageSize}),
    		async:false,
    		success:function(data){
    			if(data.code==01){
    				var userdata= data.codeMessage;
    				ren( {'cols':cols,'wh':wh,'userdata':userdata.list,'checked':true} );
    				listnumtotal=userdata.total;
    			}else if(data.code==00){
    				//没有数据；
    				ren( {'cols':cols,'wh':wh,'userdata':'','checked':true} );
    			}
    			$('.platrule .sav',parent.document).closest('.zhezhao').remove();
    		}
    	});
	}
	//手工平盘
	$('.buttons .handplat').click(function(){
		var a=0,arr=[];
		$('.box tr').each(function(){
			if( $(this).find('input[type="checkbox"]').prop('checked')==true){
				a++;
			}
		})
		if(a==0){
			dialog.choicedata('请先勾选一条数据再进行此操作','platrule');
		}else{
			//console.log( $('.box tr input[type="checkbox"]:checked').length )
			$('.box tr').each(function(i,v){
				if( $(v).find('input[type="checkbox"]').prop('checked') ){
				  arr.push({
					  'ckno':$(v).find('td').eq(1).attr("ckno"),
					  'exnm': $(v).find('td').eq(2).find('span').text() ,
					  'ppfs':'01'
					  });
				}
			});
			handautoplat( arr );
		}
	});
	//自动平盘;
	$('.buttons .autoplat').click(function(){
		var a=0,arr=[];
		$('.box tr').each(function(){
			if( $(this).find('input[type="checkbox"]').prop('checked')==true){
				a++;
			}
		})
		if(a==0){
			dialog.choicedata('请先勾选一条数据再进行此操作','platrule');
		}else{
			$('.box tr').each(function(i,v){
				if( $(v).find('input[type="checkbox"]').prop('checked') ){
				  arr.push({
					  'ckno':$(v).find('td').eq(1).attr("ckno"),
					  'exnm': $(v).find('td').eq(2).find('span').text() ,
					  'ppfs':'02'
					  });
				}
			});
			handautoplat( arr );			
		}
	});
	//手工平盘  自动平盘；
	function handautoplat(obj){
		$.ajax({
			url:'/fx/upPplist.do',
			type:'post',
			contentType:'application/json',
			data:JSON.stringify({'userKey':userkey,'listppcon':obj}),
			async:false,
			success:function(data){
				if(data.code==01){
					dialog.choicedata(data.codeMessage,'platrule','aaa');
					//render('listPpcon.do', '');
					render('/fx/listPpcon.do',{'ckno':'','pageNo':1,'pageSize':$('.layui-laypage-limits select option:selected').attr('value')*1});
					renpage();
				}else if(data.code==02){
					dialog.choicedata(data.codeMessage,'platrule','aaa');
				}
			}
		});
	}
	$('body',parent.document).on('click','.platrule .sure',function(){
		var tit=$('.platrule').data('tit');
		if(tit=='aaa'){
			$(this).closest('.zhezhao').remove();
		}else{
			
		}
		$('.zhezhao',parent.document).remove();
		$('.box tr').each(function(){
			$(this).find('input[type="checkbox"]').prop('checked','');
			$(this).removeClass('selected');
		})
	});
	
	$('body',parent.document).on('click','.platrule .cancel',function(){
		$('.zhezhao',parent.document).remove();
	});
	$('body',parent.document).on('click','.platrule .close',function(){
		$('.zhezhao',parent.document).remove();
	});
	//点击下拉列表；
	$('.classionopen').change(function(){ 
		/*render('/fx/listPpcon.do', $('.classionopen option:selected').attr("vas"));*/
		render('/fx/listPpcon.do',{'ckno':$('.classionopen option:selected').attr("vas"),'pageNo':1,'pageSize':$('.layui-laypage-limits select option:selected').attr('value')*1});
		renpage();
	});
	//封装渲染列表函数
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
		 mmg.on('loadSuccess',function(){
			$('.box tbody tr').each(function(i,v){
				$(v).find('td:eq(1)').attr('ckno',obj.userdata[i].ckno);
				 
			});
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
    		    		/*getlist({
    		    	    	pageNo:obj.curr,
    		    	    	pageSize:$('.layui-laypage-limits select option:selected').attr('value')*1,
    		    	    	userKey:userkey
    		    	    });*/
    		    		
    		    		render('/fx/listPpcon.do',{'ckno':$('.classionopen option:selected').attr("vas"),'pageNo':obj.curr,'pageSize':$('.layui-laypage-limits select option:selected').attr('value')*1});
    		    	}	
    		    }
    		  });
    	});
    }
})

