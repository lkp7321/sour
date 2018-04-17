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
		ww=$(window).width()-345+"px";
	var userkey=JSON.parse( sessionStorage.getItem('menu') ).userKey;
	$('.boxtop').css('width',ww);
	$('.boxtop').css('height',wh);
	
	//列参数;
    var cols = [
            { title:'市场', name:'mknm',width:60,align:'left' },
            { title:'货币对', name:'exnm',width:60, align:'left'},
            { title:'价格类型', name:'tpnm',width:70, align:'left'},
            { title:'钞汇标志', name:'cxfg',width:70, align:'left'},
            { title:'期限', name:'term',width:70,align:'left'},
            { title:'买入价', name:'neby',width:70, align:'right'},
            { title:'中间价', name:'nemd',width:70, align:'right'},
            { title:'卖出价', name:'nesl',width:70, align:'right'},
            { title:'交易标志', name:'trfg',width:50, align:'left'},
            { title:'错误码', name:'ercn',width:50, align:'left'},
            { title:'更新时间', name:'mdtm',width:100, align:'left'}
    ];
    //市场树和币别对
	$.ajax({
		url:'/fx/price/getMktree.do',
		type:'post',
		contentType:'application/json',
		async:true,
		success:function(data){
			if(data.code==01){
				var str='';
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
				var str='';
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
		var mkid=$(this).find('option:selected').attr('mkid'),
			type=1;
		getlist({
			type:type,
			name:mkid
		});
	});
	$('.acudea').change(function(){
		var mkid=$(this).find('option:selected').attr('mkid'),
			type=2;
		getlist({
			type:type,
			name:mkid
		});
	});

	/*getlist({
		type:'',
		name:''
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
	}*/

})