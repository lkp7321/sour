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
		wdatePicker:'./My97DatePicker/WdatePicker',
		dialog:'dialog'
	}
});
require(['jquery','mmGrid','niceScroll','wdatePicker','dialog'],function($,mmGrid,niceScroll,wdatePicker,dialog){
	var wh=$(window).height()-190+"px",
		ww=$(window).width()-260+"px",
		tit=$('title').text();
	var userkey=JSON.parse( sessionStorage.getItem('menu') ).userKey;
	$('.boxtop').css('width',ww);
	$('.boxtop').css('height',wh);
	var str='<option mkid="all" class="al">所有</option>';
	$('.showEndt').text( dialog.today() );
	//列参数;
    var cols=[
		{ title:'星期', name:'week',width:60,align:'right' },
		{ title:'开始时间', name:'beginTime',width:60, align:'right'},
		{ title:'结束时间', name:'endTime',width:60, align:'right'},
		{ title:'标志', name:'flag',width:60, align:'center'}
    ];
    $.ajax({
		url:'/fx/price/getpriceProd.do',
		contentType:'application/json',
		async:false,
		success:function(data){
			var str="";
			if(data.code==01){
				userdata=JSON.parse( data.codeMessage );
				for(var i=0,num=userdata.length;i<num;i++){
					str+='<option ptid='+userdata[i].PTID+'>'+userdata[i].PTNM+'</option>'
				}
				$('.markname1 select').html(str);
			}else{
			
			}
		}
	});
    getlist({
    	userKey:userkey,
    	prod:$('.markname1 select option:selected').attr('ptid'),
    	time:dialog.today()
    });
    function getlist( obj ){
		$.ajax({
			url:'/fx/price/showPriceSet.do',
			contentType:'application/json',
			async:true,
			type:'post',
			data:JSON.stringify(obj),
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
    $('.searc').click(function(){
    	var ti;
    	if( $('#d13').val()==''){
    		ti=$('.showEndt').text();
    	}else{
    		ti=$('#d13').val();
    	}
	  getlist({
    	userKey:userkey,
    	prod:$('.markname1 select option:selected').attr('ptid'),
    	time:ti
     });
    });
    $('.liveon').click(function(){
    	$.ajax({
			url:'/fx/socketInfo.do',
			contentType:'application/json',
			async:true,
			success:function(data){
				dialog.choicedata('发送请求!','primachshow');
			}
    	});
    });

	$('body',parent.document).on('click','.primachshow .sure',function(){
		//关闭选择一条数据;
	   $(this).closest('.zhezhao').remove();
	});
})