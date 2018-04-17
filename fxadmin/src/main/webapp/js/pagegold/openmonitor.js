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
		ww=$(window).width()-260+"px";
	$('.boxtop').css('width',ww);
	$('.boxtop').css('height',wh);
	var userdata='',
		tit=$('title').text(),str='';
	//列参数;
	//{\"cbhl\":\"0.0000\",\"cknm\":\"\",\"excn\":\"美元人民币\",\"exnm\":\"USDRMB\",\"fdsy\":\"0.0000\",\"lamt\":0.0,\"neby\":6.2368,\"nesl\":6.2368,\"ppyk\":\"0\",\"prcd\":\"\",\"price\":0.0,\"pricer\":0.0,\"ramt\":0.0,\"sgyk\":\"0\",\"toyk\":0.0,\"tryk\":\"0\",\"zcyk\":\"0\"}
    var cols = [
        { title:'品种对', name:'exnm' ,width:80, align:'left' },
        { title:'数量', name:'lamt' ,width:110, align:'left'},
        { title:'金额', name:'ramt' ,width:110, align:'right'},
        { title:'对冲损益', name:'fdsy' ,width:100, align:'right'},
        { title:'自动平盘盈亏', name:'ppyk' ,width:100, align:'right'},
        { title:'手工平盘盈亏', name:'sgyk' ,width:100, align:'right'},
        { title:'浮动损益', name:'cbhl' ,width:80, align:'right'},
        { title:'买入价', name:'price' ,width:100, align:'right'},
        { title:'卖出价', name:'pricer' ,width:80, align:'right'}
    ],
    cols1=[
          { title:'品种对', name:'exnm' ,width:80, align:'left' },
          { title:'数量', name:'lamt' ,width:110, align:'left'},
          { title:'金额', name:'ramt' ,width:110, align:'right'},
          { title:'对冲损益', name:'zcyk' ,width:100, align:'right'},
          { title:'自动平盘盈亏', name:'ppyk' ,width:100, align:'right'},
          { title:'手工平盘盈亏', name:'sgyk' ,width:100, align:'right'},
          { title:'浮动损益', name:'fdsy' ,width:80, align:'right'},
          { title:'买入价', name:'neby' ,width:100, align:'right'},
          { title:'卖出价', name:'nesl' ,width:80, align:'right'},
          { title:'成本汇率', name:'cbhl' ,width:80, align:'rights'}
      ];
    
	if(tit=='分类敞口'){
		$.ajax({
			url:'/fx/getCknoTree.do',
			type:'post',
			contentType:'application/json',
			async:true,
			success:function(data){
				if(data.code==01){
					userdata=JSON.parse(data.codeMessage );
					for(var i=0,num=userdata.length;i<num;i++){
						str+='<option ckno='+userdata[i].CKNO+'>'+userdata[i].CKNM+'</option>'
					}
					$('.openname').html( str );
				}
			}
		});
		ren({'cols':cols,'wh':wh,'userdata':''});
	}else{
		getlist('');
	}
	//更改敞口名称；
	$('.openname').change(function(){
		var ckno=$('.openname option:selected').attr('ckno');
		getlist(ckno);
	});

    function getlist(obj){
    	var url;
    	if(tit=='敞口监控'){
    		url='allGoldwebPrice.do';
    		cols=cols1;
    	}else if(tit=='总敞口监控'){
    		url='getCktotalMonitor.do';
    		cols=cols;
    	}
    	$.ajax({
    		url:url,
    		type:'post',
    		contentType:'application/json',
    		async:true,
    		data:obj,
    		success:function(data){
    			if(data.code==01){
    				userdata=JSON.parse( data.codeMessage );
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
				$('.box tbody tr').each(function(i,v){
					if( obj.userdata[i].udfg==1){
						$(v).find('span').css('color','#00ff21');
					}else if(obj.userdata[i].udfg==2){
						$(v).find('span').css('color','red');
					}
					$(v).find('td').eq(0).attr('udfg',obj.userdata[i].udfg);
				});
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
