/*异常交易监控*/
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
		ww=$(window).width()-260+"px",
		tit=$('title').text();
		$('.boxtop').css('width',ww);
		$('.boxtop').css('height',wh);
		$('#d1,#d2').val( dialog.today() );
	//列参数;
    var cols = [
            { title:'本地流水号', name:'lcno',width:120,align:'left' },
            { title:'交易日期', name:'trdt',width:80, align:'right'},
            { title:'交易时间', name:'trtm',width:100, align:'right'},
            { title:'交易代码', name:'trcd',width:100, align:'left'},
            { title:'交易码', name:'trds',width:100, align:'left'},
            { title:'客户号', name:'cuno',width:80, align:'left'},
            { title:'卡号', name:'cuac',width:80, align:'left'},
            { title:'钞汇标志', name:'cxfg',width:80, align:'center'},
            { title:'交易账号', name:'acco',width:80, align:'left'},
            { title:'折美元金额', name:'usam',width:80, align:'right'},
            { title:'状态', name:'stcd',width:80, align:'center'},
            { title:'错误码', name:'ercd',width:80, align:'center'},
    ];
	getlist({
		'curDate':dialog.today(),
		'toDate':dialog.today()
	});
    //请求列表数据；
	function getlist( obj ){
		$.ajax({
			url:'/fx/bailExceptionData.do',
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
	//点击查询;
	$('.query').click(function(){
		var t1=$('#d1').val(),t2=$('#d2').val(),ti;
		clearInterval( ti );
		if( /\d+/.test( $('.cardnum').val() )){
			ti=setInterval(function(){
				getlist({
					'curDate':t1,
					'toDate':t2
				});
			},$('.cardnum').val()*1000);
		}
	});
	//点击恢复初始；
	$('.excel').click(function(){
		if( $('.box tbody tr').length>1 ){
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
	});
	$('body',parent.document).on('click','.sysparset .sure,.sysparset .close',function(){
		$(this).closest('.zhezhao').remove();
	});
});
