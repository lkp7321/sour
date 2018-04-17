require.config({
	baseUrl:'/fx/js',
	shim:{
		'mmGrid':{
			deps:['jquery'],
			exports:'mmGrid'
		},
		'dialog':{
			deps:['jquery'],
			exports:'dialog'
		},
		'nicescroll':{
			deps:['jquery'],
			exports:'nicescroll'
		}
	},
	paths:{
		'jquery':'js_files/jquery-1.9.1.min',
		'mmGrid':'js_files/mmGrid',
		'dialog':'dialog',
		'nicescroll':'js_files/jquery.nicescroll.min'
	}
});
require(['jquery','mmGrid','dialog','nicescroll'],function($,mmGrid,dialog,nicescroll){
	var wh=$(window).height()-190+"px",
		ww=$(window).width()-250+"px";
	$('.boxtop').css('width',ww);
	//列参数
    var cols = [
        { title:'品种对名称', name:'exnm' ,width:80, align:'left' },
        { title:'买入价', name:'neby' ,width:70, align:'right'},
        { title:'中间价', name:'nemd' ,width:70, align:'right'},
        { title:'卖出价', name:'nesl' ,width:70, align:'right'},
        { title:'更新时间', name:'mdtm' ,width:120, align:'right'},
        { title:'报价状态', name:'stfg' ,width:80, align:'center'},
        { title:'报价来源', name:'mknm' ,width:80, align:'left'},
        { title:'错误码', name:'ercn' ,width:80, align:'left'},
        { title:'交易标志', name:'trfg' ,width:80, align:'left'},
    ];
   
    getlist();
    //获取市场报价、总敞口、折算敞口；
    function getlist(){
    	var url,tit=$('title').text();
		 if( tit=='总分行价格' ){
		    url='accum/allbrnch.do';//积存金（P003）监控管理：总分行价格
	     }else{
	    	 url='accum/allcust.do';//积存金（P003）监控管理：客户价格
	     }
        $.ajax({
    		url:url,
    		type:'get',
    		contentType:'application/json',
    		success:function(data){
    			if(data.code==01){
    				userdata=data.codeMessage;
    				ren({'cols':cols,'wh':wh,'userdata':userdata},url);
    			}else if(data.code==00){
    				ren({'cols':cols,'wh':wh,'userdata':''},url);
    			}
    		}
    	});
    }
    function ren(obj,url){
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
		 $(".mmg-bodyWrapper").niceScroll({
				touchbehavior:false,
				cursorcolor:"#666",
				cursoropacitymax:0.7,
				cursorwidth:6,
				background:"#ccc",
				autohidemode:false,
				horizrailenabled:true
		  });
		 mmg.on('loadSuccess',function(){	
			 setInterval(function(){
				 $.ajax({
			    		url:'/fx/'+url,
			    		type:'get',
			    		contentType:'application/json',
			    		async:false,
			    		success:function(data){
			    			if(data.code==01){
			       				userdata=data.codeMessage;
			       				$('.box tbody:last tr').each(function(i,v){
			       					if($(v).find('td:eq(2) span').text()*1<userdata[i].nemd*1){
			    						$(v).find('span').css('color','#FF0000');
			    					}else if($(v).find('td:eq(2) span').text()*1>userdata[i].nemd*1){
			    						$(v).find('span').css('color','#00FF00');
			    					}else{
			    						$(v).find('span').css('color','#000000');
			    					}
			       					$(v).find('td:eq(0) span').text( userdata[i].exnm);
			       					$(v).find('td:eq(1) span').text( userdata[i].neby);
			       					$(v).find('td:eq(2) span').text( userdata[i].nemd);
			       					$(v).find('td:eq(3) span').text( userdata[i].nesl);
			       					$(v).find('td:eq(4) span').text( userdata[i].mdtm);
			       					$(v).find('td:eq(5) span').text( userdata[i].stfg);
			       					$(v).find('td:eq(6) span').text( userdata[i].mknm);
			       					$(v).find('td:eq(7) span').text( userdata[i].ercn);
			       					$(v).find('td:eq(8) span').text( userdata[i].trfg);
			       					
			       				});
			    			}else if(data.code==00){
			       				ren({'cols':cols,'wh':wh,'userdata':'','checked':false});
			       			}
			    		}
			    	}); 
			 },1000);
		 });
    }
    
});