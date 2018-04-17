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
		wdatepicker:'./My97DatePicker/WdatePicker',
		dialog:'dialog'
	}
});
require(['jquery','mmGrid','niceScroll','wdatepicker','dialog'],function($,mmGrid,niceScroll,wdatepicker,dialog){
	var wh=$(window).height()-190+"px",
		ww=$(window).width()-345+"px";
	$('.boxtop').css('width',ww);
	$('.boxtop').css('height',wh);
	$('.d1,.d2').text( dialog.today);
	//列参数;
    var cols = [
            { title:'请求流水号', name:'usnm',width:120,align:'left' },
            { title:'请求日期', name:'ptnm',width:80, align:'left'},
            { title:'请求时间', name:'ognm',width:100, align:'left'},
            { title:'DOWNLOADKEY', name:'cmpt',width:120, align:'left'},
            { title:'交易状态', name:'mbtl',width:120,align:'left'},
            { title:'交易日期', name:'telp',width:80, align:'left'},
            { title:'TYPEOFEVENT', name:'ufax',width:80, align:'left'},
            { title:'交易注释', name:'mail',width:80, align:'left'},
            { title:'支付开始日期', name:'macip',width:80, align:'left'},
            { title:'支付结束日期', name:'usfg',width:80, align:'left'},
            { title:'支付的金额', name:'rmrk',width:80, align:'left'},
            { title:'现金流类型', name:'rmrk',width:80, align:'left'},
            { title:'期限方式', name:'rmrk',width:80, align:'left'},
            { title:'使用方式', name:'rmrk',width:80, align:'left'},
            { title:'用户短名', name:'rmrk',width:80, align:'left'},
            { title:'交易对手短名', name:'rmrk',width:80, align:'left'},
            { title:'交易Folder短名', name:'rmrk',width:80, align:'left'},
            { title:'货币短名', name:'rmrk',width:80, align:'left'},
            { title:'处理日期', name:'rmrk',width:80, align:'left'},
            { title:'错误时间', name:'rmrk',width:80, align:'left'},
            { title:'错误代码', name:'rmrk',width:80, align:'left'},
            { title:'错误信息', name:'rmrk',width:80, align:'left'},
    ];
    dialog.ren({'cols':cols,'wh':wh,'userdata':''});
    
    
    //原列表中怎么会有两个日期；
    //获取产品和用户名；
    /*var usnm=sessionStorage.getItem('usnm'),
		product=sessionStorage.getItem('product'),
		user={'usnm':usnm,'prod':product},
		userdata;*/
    //请求列表数据；
	/*$.ajax({
		url:'/fx/listUser.do',
		type:'post',
		contentType:'application/json',
		data:JSON.stringify(user),
		async:true,
		success:function(data){
			if(data.code==00){
				userdata=JSON.parse( data.codeMessage );
				dialog.ren({'cols':cols,'wh':wh,'userdata':userdata});
			}else if(data.code==01){
				
			}
			
		}
	}); */
});
