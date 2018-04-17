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
	$.ajax({
		url:'/fx/selstrancodes.do',
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
		'curDate':dialog.today(),
		'toDate':dialog.today(),
		'tranCode':$('.deal select option:selected ').attr('trcd')
	});
    //请求列表数据；
	function getlist( obj ){
		$.ajax({
			url:'/fx/bailExceptionDatas.do',
			type:'post',
			contentType:'application/json',
			data:JSON.stringify( obj ),
			async:true,
			success:function(data){
				if(data.code==01){
					userdata=JSON.parse( data.codeMessage );
					dialog.ren({'cols':cols,'wh':wh,'userdata':userdata,'checked':true});
				}else{
					dialog.ren({'cols':cols,'wh':wh,'userdata':'','checked':true});
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
			'curDate':t1,
			'toDate':t2,
			'tranCode':$('.deal select option:selected ').attr('trcd')
		});
	});
	//点击恢复初始；
	$('.remvoestrart').click(function(){
		var checkednum=0,arr=[];
		$('.box tody tr').each(function(i,v){
			if( $(v).find('input[type="checkbox"]').prop('checked') ){
				checkednum++;
				arr.push( {
					'lcno':$(v).find('td:eq(1) span').text(),
					'trdt':$(v).find('td:eq(2) span').text(),
					'trcd':$(v).find('td:eq(4) span').text()
				});
			}
		});
		if( checkednum>0){
			$.ajax({
				url:'/fx/updateInitialize.do',
				type:'post',
				contentType:'application/json',
				data:JSON.stringify({
					userKey:userkey,
					tranCode:$('.deal select option:selected ').attr('trcd'),
					bailExceList:arr
				} ),
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
		}else{
			dialog.choicedata('请先勾选一条数据!','sysparset');
		}
	});
	$('body',parent.document).on('click','.sysparset .sure,.sysparset .close',function(){
		$(this).closest('.zhezhao').remove();
	});
});
