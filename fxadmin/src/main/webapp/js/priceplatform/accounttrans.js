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
            { title:'市场', name:'mknm',width:60,align:'left' },
            { title:'货币对', name:'exnm',width:60, align:'left'},
            { title:'价格类型', name:'tpnm',width:70, align:'center'},
            { title:'钞汇标志', name:'cxfg',width:70, align:'center'},
            { title:'期限', name:'term',width:70,align:'right'},
            { title:'买入价', name:'neby',width:70, align:'right'},
            { title:'中间价', name:'nemd',width:70, align:'right'},
            { title:'卖出价', name:'nesl',width:70, align:'right'},
            { title:'交易标志', name:'trfg',width:50, align:'center'},
            { title:'错误码', name:'ercn',width:50, align:'center'},
            { title:'更新时间', name:'mdtm',width:100, align:'right'}
    ];
    //市场树和币别对
	$.ajax({
		url:'/fx/price/getMktree.do',
		type:'post',
		contentType:'application/json',
		async:true,
		success:function(data){
			if(data.code==01){
				var str='<option>请选择</option>';
				userdata=JSON.parse( data.codeMessage );
				for(var i in userdata){
					str+='<option mkid='+userdata[i].MKID+'>'+userdata[i].MKNM+'</option>'
				}
				$('.acudeal').html( str );
			}else{
				
			}
		}
	});
	$.ajax({
		url:'/fx/price/getExnmtree.do',
		type:'post',
		contentType:'application/json',
		async:true,
		success:function(data){
			if(data.code==01){
				var str='<option>请选择</option>';
				userdata=JSON.parse( data.codeMessage );
				for(var i in userdata){
					str+='<option mkid='+userdata[i].EXNM+'>'+userdata[i].EXCN+'</option>'
				}
				$('.acudea').html( str );
			}else{
				
			}
		}
	});
	$('.acudeal').change(function(){
		if( $(this).find('option:selected').text()=='请选择'){
			dialog.ren({'cols':cols,'wh':wh,'userdata':''});
		}else{
			var mkid=$(this).find('option:selected').attr('mkid'),
				type=1;
			getlist({
				type:type,
				name:mkid
			});
		}
	});
	getlist({
		type:'',
		name:''
	});
	$('.acudea').change(function(){
		if( $(this).find('option:selected').text()=='请选择'){
			dialog.ren({'cols':cols,'wh':wh,'userdata':''});
		}else{
			var mkid=$(this).find('option:selected').attr('mkid'),
			type=2;
			getlist({
				type:type,
				name:mkid
			});
		}
		
	});
	//价格校验器状态；
	$.ajax({
		url:'/fx/getPriceStat.do',
		type:'post',
		contentType:'application/json',
		async:true,
		success:function(data){
			if(data.code==01){
				$('.state').text( data.codeMessage );
			}else{
				
			}
		}
	});
	
	function getlist( obj ){
		$.ajax({
			url:'/fx/price/getffpriceMonitor.do',
			type:'post',
			contentType:'application/json',
			data:JSON.stringify( obj ),
			async:true,
			success:function(data){
				if(data.code==01){
					userdata=JSON.parse( data.codeMessage );
					dialog.ren({'cols':cols,'wh':wh,'userdata':userdata});
				}else{
					dialog.ren({'cols':cols,'wh':wh,'userdata':''});
				}
				
			}
		});
	}
	//刷新价格源校验器，开关价格源校验器；
	$('.pribtn').click(function(){
		var txt=$('.state').text(),
			url;
		if(txt=='关闭'){
			url='openPricestat.do';
		}else{
			url='closePricestat.do';
		}
		$.ajax({
			url:url,
			type:'post',
			contentType:'application/json',
			data:userkey,
			async:true,
			success:function(data){
				if(data.codeMessage=='关闭价格校验器状态成功'){
					$('.state').text( '关闭' );
				}else{
					$('.state').text( '打开' );
				}
			}
		});
	});
	$('.refrebtn').click(function(){
		dialog.cancelDate('您确定要刷新价格源校验器吗?','accounttrans');
	});
	$('body',parent.document).on('click','.accounttrans .confirm',function(){
		$.ajax({
			url:'/fx/refshPricestats.do',
			type:'post',
			contentType:'application/json',
			data:userkey,
			async:true,
			success:function(data){
				$('.accounttrans .confirm',parent.document).closest('.zhezhao').remove();
				if(data.code==01){
					dialog.choicedata(data.codeMessage,'accounttrans');
				}else{
					dialog.choicedata(data.codeMessage,'accounttrans');
				}
			}
		});
	});
	$('body',parent.document).on('click','.accounttrans .close,.accounttrans .cancel,.accounttrans .sure',function(){
		$(this).closest('.zhezhao').remove();
	});
})