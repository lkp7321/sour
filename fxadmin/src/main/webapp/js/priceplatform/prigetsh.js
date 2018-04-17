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
		WdatePicker:'./My97DatePicker/WdatePicker',
		dialog:'dialog'
	}
});
require(['jquery','mmGrid','niceScroll','WdatePicker','dialog'],function($,mmGrid,niceScroll,WdatePicker,dialog){
	var wh=$(window).height()-190+"px",
		ww=$(window).width()-260+"px";
		$('.boxtop').css('width',ww);
		$('.boxtop').css('height',wh);
	var str="",
		userkey=JSON.parse( sessionStorage.getItem('menu') ).userKey;
	//列参数;
    var cols = [
            { title:'星期', name:'week',width:60,align:'right' },
            { title:'开始时间', name:'beginTime',width:60, align:'right'},
            { title:'结束时间', name:'endTime',width:60, align:'right'},
            { title:'标志', name:'flag',width:60,align:'center'}
    ];
    $('.showEndt').text( dialog.today() );
    
	//获取市场名称；
	$.ajax({
		url:'/fx/price/getMarketinfoList.do',
		contentType:'application/json',
		async:false,
		success:function(data){
			if(data.code==01){
				userdata=JSON.parse( data.codeMessage );
				for(var i in userdata){
				 	str+='<option mkid='+userdata[i].MKID+'>'+userdata[i].MKNM+'</option>'
				}
				$('.markname select').html( str );
			}else if(data.code==02){
			}
		}
	});
	getcuur(  $('.markname select option:selected').attr('mkid') );
	
    $('.markname select').change(function(){
		var mkid=$(this).find('option:selected').attr('mkid');
		if( mkid){
			getcuur( mkid );
		}else{
			$('.cuurpair select').html( '<option>所有</option>' );
		}
	});
	//获取货币对；
	function getcuur(obj){
		var str="";
		$.ajax({
			url:'/fx/price/getBiBieDuiLists.do',
			contentType:'application/json',
			async:true,
			type:"post",
			data:obj,
			success:function(data){
				if(data.code==01){
					userdata=JSON.parse( data.codeMessage );
					for(var i in userdata){
					 	str+='<option>'+userdata[i]+'</option>'
					}
					$('.cuurpair select').html( str );
				}else if(data.code==02){
					
				}
				
			}
		});
	}
	getlist({
		userKey:userkey,
		mkid:$('.markname select option:selected').attr('mkid'),
		exnm:$('.cuurpair select option:selected').text(),
		time:$('.showEndt').text()
	});
	//点击查询按钮;
	$('.searc').click(function(){
		var ti;
		if( $('#d13').val()==''){
			ti=$('.showEndt').text();
		}else{
			ti=$('#d13').val();
		}
		var data={
			userKey:userkey,
			mkid:$('.markname select option:selected').attr('mkid'),
			exnm:$('.cuurpair select option:selected').text(),
			time:ti
		};
		getlist( data );
	});
	//查询页面数据
	function getlist( obj ){
		$.ajax({
			url:'/fx/price/showPriceRec.do',
			type:'post',
			contentType:'application/json',
			data:JSON.stringify(obj),
			async:true,
			success:function(data){
				if(data.code==01){
					userdata=JSON.parse( data.codeMessage );
					dialog.ren({'cols':cols,'wh':wh,'userdata':userdata});
				}else if(data.code==00){
					dialog.ren({'cols':cols,'wh':wh,'userdata':''});
				}
			}
		});
	}
	
	//？？？？生效按钮
	$('.liveon').click(function(){
		$.ajax({
			url:'/fx/SendSocketPdtInfo.do',
			type:'get',
			async:false,
			dataType:'json',
			contentType:'application/json;charset=utf-8',
			success:function(data){
			    if(data.code==01){
			    	dialog.choicedata('发送消息','calendarshow');
				}else{
				}
			}
		});
	});
	$('body',parent.document).on('click','.calendarshow .sure',function(){
		$(this).closest('.zhezhao').remove();
	});
})