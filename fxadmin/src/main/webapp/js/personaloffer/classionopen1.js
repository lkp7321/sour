/*积存金-分类敞口*/
require.config({
	baseUrl:'/fx/js/',
	shim:{
		'jquery.niceScroll': {
			deps: ['jquery'],
			exports: 'jquery.niceScroll'
				},
		'mmGrid':{
			deps:['jquery'],
			exports:'mmGrid'
		},
		'table2excel':{
			deps:['jquery'],
			exports:'table2excel'
		}
	},
	paths:{
		jquery:'js_files/jquery-1.9.1.min',
		mmGrid:'js_files/mmGrid',
		niceScroll:'js_files/jquery.nicescroll.min',
		dialog:'dialog',
		table2excel:'js_files/excel'

	}
});
require(['jquery','mmGrid','niceScroll','dialog','table2excel'],function($,mmGrid,niceScroll,dialog,table2excel){
	var wh=$(window).height()-190+"px",
		ww=$(window).width()-250+"px";
	$('.boxtop').css('width',ww);
	$('.boxtop').css('height',wh);
	var userdata='',
		tit=$('title').text(),str='',
		userkey=JSON.parse( sessionStorage.getItem('menu') ).userKey;
	//列参数;
    var cols = [
            { title:'品种对', name:'exnm' ,width:80, align:'left' },
            { title:'左头寸', name:'lamt' ,width:110, align:'right'},
            { title:'右头寸', name:'ramt' ,width:110, align:'right'},
            { title:'浮动损益', name:'fdsy' ,width:100, align:'right'},
            { title:'轧差损益', name:'zcyk' ,width:100, align:'right'},
            { title:'自动平盘盈亏', name:'ppyk' ,width:100, align:'right'},
            { title:'手工平盘盈亏', name:'sgyk' ,width:100, align:'right'},
            { title:'买入价', name:'price' ,width:100, align:'right'},
            { title:'卖出价', name:'pricer' ,width:80, align:'right'},
            { title:'成本汇率', name:'cbhl' ,width:80, align:'right'}
        ];
    $.ajax({
		url:'/fx/accum/getckno.do',
		type:'post',
		contentType:'application/json',
		async:false,
		success:function(data){
			var str='';
			if(data.code==01){
				userdata=data.codeMessage ;
				for(var i=0,num=userdata.length;i<num;i++){
					str+='<option ckno='+userdata[i].CKNO+'>'+userdata[i].CKNM+'</option>'
				}
				$('.openname').html( str );
			}
		}
	});
	
	getlist({
		'userKey':userkey,
		'ckno':$('.openname option:selected').attr('ckno')
	});
	//更改敞口名称；
	$('.openname').change(function(){
		var ckno=$('.openname option:selected').attr('ckno');
		getlist({
			'userKey':userkey,
			'ckno':ckno
		});
	});
	
    function getlist(obj){
    	$.ajax({
    		url:'/fx/accum/getclassckAccum.do',
    		type:'post',
    		contentType:'application/json',
    		async:true,
    		data:JSON.stringify( obj ),
    		success:function(data){
    			if(data.code==01){
    				userdata= data.codeMessage;
    				ren({'cols':cols,'wh':wh,'userdata':userdata});
    			}else if(data.code==00){
    				ren({'cols':cols,'wh':wh,'userdata':''});
    			}
    		}
    	});
    }
    function ren(obj){
    	$('.boxtop').html('');
    	$('#ascrail2000').remove();
    	$('.boxtop').append('<div class="box"></div>');
		var mmg = $('.box').mmGrid({
				height:obj.wh
				, cols: obj.cols
				,items:obj.userdata
	            , nowrap:true
	            , fullWidthRows:true
	            , checkCol:obj.checked
	            , multiSelect:obj.select
	            ,showBackboard:true
	  	});
		if(obj.userdata.length>0){
			mmg.on('loadSuccess',function(){
				setInterval(function(){
					 $.ajax({
				    		url:'/fx/accum/getclassckAccum.do',
				    		type:'post',
				    		data:JSON.stringify(
				    				{'userKey':userkey,'ckno':$('.openname option:selected').attr('ckno')}),
				    		contentType:'application/json',
				    		async:false,
				    		success:function(data){
				    			if(data.code==01){
				       				userdata=data.codeMessage;
				       				$('.box tbody:last tr').each(function(i,v){
				       					if( userdata[i].udfg*1==1){
				    						$(v).find('span').css('color','#00FF00');
				    					}else if(userdata[i].udfg*1==2){
				    						$(v).find('span').css('color','red');
				    					}else{
				    						$(v).find('span').css('color','#000000');
				    					}
				       					$(v).find('td:eq(0) span').text( userdata[i].exnm);
				       					$(v).find('td:eq(1) span').text( userdata[i].lamt);
				       					$(v).find('td:eq(2) span').text( userdata[i].ramt);
				       					$(v).find('td:eq(3) span').text( userdata[i].fdsy);
				       					$(v).find('td:eq(4) span').text( userdata[i].zcyk);
				       					$(v).find('td:eq(5) span').text( userdata[i].ppyk);
				       					$(v).find('td:eq(6) span').text( userdata[i].sgyk);
				       					$(v).find('td:eq(7) span').text( userdata[i].price);
				       					$(v).find('td:eq(8) span').text( userdata[i].pricer);
				       					$(v).find('td:eq(9) span').text( userdata[i].cbhl);
				       				});
				    			}else if(data.code==00){
				    				ren({'cols':cols,'wh':wh,'userdata':''});
				       			}
				    		}
				    	}); 
				 },1000);
			});
		}
		 $(".mmg-bodyWrapper").niceScroll({
				touchbehavior:false,
				cursorcolor:"#666",
				cursoropacitymax:0.7,
				cursorwidth:6,
				background:"#ccc",
				autohidemode:false,
				horizrailenabled:obj.horizrailenabled
		  });
    }
	$('.toexcel').click(function(){
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
	})
    /*----------------快速搜索功能的实现------------------------*/
	$('.info_serbtn').click(function(){
		  dialog.serchData($('.info_seript').val(),'.mmg-headWrapper');
    });

        
})
