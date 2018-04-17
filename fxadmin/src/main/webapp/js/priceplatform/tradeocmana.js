/*
 * 交易开闭市管理
 */
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
	var userkey=JSON.parse( sessionStorage.getItem('menu') ).userKey;
	
	$.ajax({
		url:'/fx/price/prodExnmPrice.do',
		type:'post',
		contentType:'application/json',
		async:true,
		success:function(data){
			var str='<option>请选择</option>';
			if(data.code==01){
				userdata=JSON.parse(data.codeMessage);
				for(var i in userdata){
					str+='<option ptid='+userdata[i].PTID+'>'+userdata[i].PTNM+'</option>'
				}
				$('.numbp select').html(str);
			}else{
				dialog.choicedata(data.codeMessage,'tradeocmana');
			}
		}
	});
	$('.numbp select').change(function(){
		var ptid=$(this).find('option:selected').attr('ptid');
		if(ptid=='P101'||!ptid||ptid=='P004'){
			$('.runstate').val('');
			$('.operator').val('' );
			$('.operatime').val('' );
		}else{
			getda( ptid );
		}
	}); 
	function getda( obj ){
		$.ajax({
			url:'/fx/price/selTradeOnofPrice.do',
			type:'post',
			contentType:'application/json',
			async:true,
			data:obj,
			success:function(data){
				if(data.code==01){
					userdata=JSON.parse( data.codeMessage );
					for(var i=0,num=userdata.length;i<num;i++){
						$('.runstate').val( userdata[i].usfg );
						$('.operator').val( userdata[i].opno );
						$('.operatime').val( userdata[i].trtm );
						if( userdata[i].usfg=='开市'){
							$('.r1').prop('checked','checked');
						}else{
							$('.r2').prop('checked','checked');
						}
					}					
				}else{
					dialog.choicedata(data.codeMessage,'tradeocmana');
				}
			}
		});
	}
	//点击保存；
	$('.sav').click(function(){
		var dat={
				userKey:userkey,
				ptid:$('.numbp select option:selected').attr('ptid'),
				usfg:$('.radip input[name="a"]:checked').attr('tit')
		}
		if( $('.numbp select option:selected').text()=='请选择'){
			dialog.choicedata('请先选择产品名称!','tradeocmana');
		}else{
			$.ajax({
				url:'/fx/price/saveTradeOnofPrice.do',
				type:'post',
				contentType:'application/json',
				async:true,
				data:JSON.stringify( dat ),
				success:function(data){
					if(data.code==01){
						dialog.choicedata(data.codeMessage,'tradeocmana');		
					}else{
						dialog.choicedata(data.codeMessage,'tradeocmana');
					}
				}
			});	
		}
		
	});
	$('body',parent.document).on('click','.tradeocmana .sure',function(){
		$(this).closest('.zhezhao').remove();
	});
})