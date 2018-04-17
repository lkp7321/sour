/*保证金-交易流水查询*/
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
		ww=$(window).width()-260+"px";
		$('.boxtop').css('width',ww);
		$('.boxtop').css('height',wh);
		$('.d1,.d2').text( dialog.today);
	//列参数;
    var cols = [
            { title:'本地流水号', name:'lcno',width:120,align:'left' },
            { title:'客户号', name:'cuno',width:80, align:'left'},
            { title:'卡号', name:'cuac',width:80, align:'left'},   
            { title:'交易账号', name:'acco',width:80, align:'left'},
            { title:'交易日期', name:'trdt',width:80, align:'right'},
            { title:'交易时间', name:'trtm',width:100, align:'right'},
            { title:'交易类型', name:'trtm',width:100, align:'center'},
            { title:'MT4用户组', name:'cxfg',width:80, align:'left'},
            { title:'折美元金额', name:'usam',width:80, align:'right'},
            { title:'交易状态', name:'stcd',width:80, align:'center'}
    ];
	$.ajax({
		url:'/fx/AllBailtranCodePrice.do',
		type:'post',
		contentType:'application/json',
		async:false,
		success:function(data){
			var str='';
			if(data.code==01){
				userdata=data.codeMessage;
				for(var i=0,num=userdata.length;i<num;i++){
					str+='<option trcd='+userdata[i].TRCD+'>'+userdata[i].TRDS+'</option>'
				}
				$('.sel').html( str );
			}else{
			}
		}
	});	
	getlist({
		'trdtbegin':dialog.today(),
		'trdtend':dialog.today(),
		'acno':$('.cardnum').val(),
		'strcuac':$('.tradacc').val(),
		'trcd':$('.deal select option:selected ').attr('trcd')
	});
    //请求列表数据；
	function getlist( obj ){
		$.ajax({
			url:'/fx/AllBailtransPrice.do',
			type:'post',
			contentType:'application/json',
			data:JSON.stringify( obj ),
			async:true,
			success:function(data){
				if(data.code==01){
					userdata=JSON.parse( data.codeMessage );
					dialog.ren({'cols':cols,'wh':wh,'userdata':userdata});
					$('.page').remove();
					$('.boxtop').append('<div class="page"><span class="first">首页</span><span class="prev">上一页</span><span class="next">下一页</span><span class="last">末页</span><span class="nopage">第<b class="Nopage">'+userdata.pageNum+'</b>页/共<b class="Totalpage">'+userdata.pages+'</b>页</span><span class="totalpage">共<b class="total">'+userdata.total+'</b>条记录</span></div>')
				}else{
					dialog.ren({'cols':cols,'wh':wh,'userdata':''});
				}
			}
		});
	}
	//点击查询;
	$('.query').click(function(){
		var t1,t2;
		if( !$('#d1').val() ){
			t1=$('.d1').text();
		}else{
			t1=$('#d1').val();
		}
		if( !$('#d2').val() ){
			t2=$('.d2').text();
		}else{
			t2=$('#d2').val();
		}
		getlist({
			'trdtbegin':t1,
			'trdtend':t2,
			'acno':$('.cardnum').val(),
			'strcuac':$('.tradacc').val(),
			'trcd':$('.deal select option:selected ').attr('trcd')
		});
	});
	$('body',parent.document).on('click','.sysparset .sure,.sysparset .close',function(){
		$(this).closest('.zhezhao').remove();
	});
});
