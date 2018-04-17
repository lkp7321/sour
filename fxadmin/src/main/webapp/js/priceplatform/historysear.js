/*历史价格查询*/
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
		ww=$(window).width()-260+"px";
	$('.boxtop').css('width',ww);
	$('.boxtop').css('height',wh);
	//列参数;
    var cols = [
            { title:'买入价格', name:'buyPrice',width:60,align:'right' },
            { title:'卖出价格', name:'sellPrice',width:60, align:'right'},
            { title:'更新时间', name:'updateTime',width:80, align:'left'}
    ];
    dialog.ren({'cols':cols,'wh':wh,'userdata':''});
    //查询产品和货币对；
    $.ajax({
		url:'/fx/price/priceHisProd.do',
		type:'post',
		contentType:'application/json',
		async:true,
		success:function(data){
			var str='<option>请选择</option>';
			if(data.code==01){
				userdata= data.codeMessage ;
				for(var i in userdata){
					str+='<option id='+userdata[i].id+'>'+userdata[i].name+'</option>'
				}
				$('.sele1').html( str );
			}else{
				
			}
			
		}
	});
    $('.sele1').change(function(){
    	var seleId=$(this).find('option:selected').attr('id');
    	
    	if( seleId=='P004'){
    		$('.tp').hide();
    	}else{
    		$('.tp').show();
    	}
    	$.ajax({
    		url:'/fx/price/priceHisExnm.do',
    		type:'post',
    		contentType:'application/json',
    		data:seleId,
    		async:true,
    		success:function(data){
    			var str='';
    			if(data.code==01){
    				userdata= data.codeMessage ;
    				for(var i in userdata){
    					str+='<option exnmId='+userdata[i].exnmId+'>'+userdata[i].exnmName+'</option>'
    				}
    				$('.sele2').html( str );
    				}else{
    				
    			}
    			
    		}
    	});
    });
    //点击查询；
    $('.quoquery').click(function(){
    	var pro=$('.sele1 option:selected').attr('id'),
    		txt=$('.sele1 option:selected').text(),url;
    	if( txt=='请选择'){
    		dialog.choicedata('请选择产品!','historysear');
    	}else if($('#d1').val()==''){
    		dialog.choicedata('请选择开始日期!','historysear');
    	}else if( $('#d2').val()=='' ){
    		dialog.choicedata('请选择结束日期!','historysear');
    	}else{
    		if( pro=='P004'){
    			url='price/priceHisList.do';
    			obj={
    			    prod:pro, 
    			    exnm:$('.sele2 option:selected').attr('exnmId'),
    			    begintime:$('#d1').val(),
    			    endtime:$('#d2').val()
    			}
    		}else{
    			url='price/priceHisList1.do';
    			obj={
    			    prod:pro, 
    			    exnm:$('.sele2 option:selected').attr('exnmId'),
    			    begintime:$('#d1').val(),
    			    endtime:$('#d2').val(),
    			    type:$('.tp option:selected').attr('tit')
    			}
    		}
    		getlist(url,obj);
    	}
    });
    function getlist(url,obj ){
    	console.log( obj )
    	$.ajax({
    		url:url,
    		type:'post',
    		contentType:'application/json',
    		data:JSON.stringify( obj ),
    		async:true,
    		success:function(data){
    			if(data.code==01){
    				userdata=data.codeMessage;
    				dialog.ren({'cols':cols,'wh':wh,'userdata':userdata});
    			}else{
    				dialog.ren({'cols':cols,'wh':wh,'userdata':''});
    			}
    		}
    	});
    }
    $('body',parent.document).on('click','.historysear .sure',function(){
		$(this).closest('.zhezhao').remove();
	});
})