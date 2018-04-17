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
	var wh=($(window).height()-180-26*2-15*2)/3+"px",
		ww=$(window).width()-260+"px",
		wp=($(window).width()-260-30)+'px';
	$('.boxtop1,.boxtop2,.boxtop3').css('width',ww);
	$('.boxtop2 p,.boxtop3 p').css('width',wp);
	
	//列参数
    var cols = [
        { title:'品种对', name:'exnm' ,width:80, align:'left' },
        { title:'名称', name:'nmex' ,width:140, align:'left'},
        { title:'数量', name:'lamt' ,width:70, align:'left'},
        { title:'金额', name:'ramt' ,width:70, align:'left'},
        { title:'成本汇率', name:'cbhl' ,width:80, align:'left'},
        { title:'浮动损益', name:'fdsy' ,width:80, align:'left'},
        { title:'买入价格', name:'neby' ,width:80, align:'right'},
        { title:'卖出价格', name:'nesl' ,width:80, align:'right'}
    ],
    cols1=[
       { title:'品种对', name:'exnm' ,width:80, align:'left' },
       { title:'名称', name:'nmex' ,width:100, align:'left'},
       { title:'数量', name:'lamt' ,width:150, align:'left'},
       { title:'金额', name:'ramt' ,width:80, align:'left'},
       { title:'成本汇率', name:'cbhl' ,width:120, align:'left'},
       { title:'对冲损益', name:'zcyk' ,width:100, align:'left'},
       { title:'中间价格', name:'nemd' ,width:80, align:'right'}
    ],
    cols2=[
      { title:'产品名称', name:'prnm' ,width:100, align:'left' },
      { title:'品种对', name:'exnm' ,width:100, align:'left'},
      { title:'买入价', name:'neby' ,width:80, align:'right'},
      { title:'中间价', name:'nemd' ,width:80, align:'right'},
      { title:'卖出价', name:'nesl' ,width:80, align:'right'},
      { title:'更新时间', name:'mdtm' ,width:120, align:'left'},
      { title:'市场', name:'mknm' ,width:80, align:'left'},
      { title:'报价状态', name:'stfg' ,width:80, align:'left'}
     ];
    //获取市场报价、总敞口、折算敞口；
    getFn();
    getFn1();
    function getFn(){
    	$.ajax({  
    		url:'/fx/getCkTotalData.do',  //.获取市场报价
    		type:'get',
    		contentType:'application/json',
    		success:function(data){
    			if(data.code==01){
    				userdata=JSON.parse( data.codeMessage );
    				ren1('getCkTotalData.do',userdata);
    			}else if(data.code==00){
    				ren1('getCkTotalData.do','');
    			}
    		}
    	});
    }
  //总敞口高度问题；
	function ren1(obj,obj1){
		$('.boxtop3 .boxp').html(' ');
    	$('#ascrail2000').remove();
    	$('.boxtop3 .boxp').append('<div class="box"></div>');
 
    	$('.boxtop3 .box').mmGrid({
			height:wh
			,cols: cols2
			,items:obj1
	        , nowrap:true
	        ,fullWidthRows:true
	        ,showBackboard:true
	        ,loadingText:'正在载入'
		});
	}
    function getFn1(){  //获取总敞口and折算敞口
    	 $.ajax({
     		url:'/fx/goldCkTotalData1.do',
     		type:'post',
     		contentType:'application/json',
     		success:function(data){
     			if(data.code==01){
     				userdata=JSON.parse( data.codeMessage );
     				ren('goldCkTotalData1.do',userdata);
     			}else if(data.code==00){
     				ren('goldCkTotalData1.do','');
     			}
     		}
     	});
    }
    //获取总浮动损益和人民币总盈利；
    $.ajax({
		url:'/fx/goldCkTotalData2.do',
		type:'post',
		contentType:'application/json',
		async:true,
		success:function(data){
			if(data.code==01){
				userdata=JSON.parse(data.codeMessage);
				$('.totalfloating').text(userdata.zfdsy);
				$('.RMBtotal').text(userdata.rmbzyl);
			}else if(data.code==02){
				dialog.choicedata(data.codeMessage,'total');
			}
		}
	});

	//点击提取损益;
	$('.extractpl').click(function(){
		$('.boxtop2 .box tr').each(function(i,v){
			if(  $(v).find('td').eq(0).find('span').text()==$('.typerm select option:selected').attr('value')  ){
				$(v).siblings().removeClass('aa');
				$(v).addClass('aa');
			}
		});
		 dialog.cancelDate('您确定要更新对外损益吗?','total');
	});
	//点击删除数据 确认+ajax;
	$('body',parent.document).on('click','.total .confirm',function(){
		var obj={
			'exnm':$('.boxtop2 .box tr.aa td:eq(0) span').text(),
			'excd':$('.boxtop2 .box tr.aa td:eq(0)').attr('excd'),
			'nemd':$('.boxtop2 .box tr.aa tr td:eq(0)').attr('nemd'),
			'lamt':$('.boxtop2 .box  tr.aa tr td:eq(2) span').text(),
			'ramt':$('.boxtop2 .box tr.aa td:eq(3) span').text(),
			'cbhl':$('.boxtop2 .box tr.aa td:eq(4) span').text(),
			'zcyk':$('.boxtop2 .box tr.aa td:eq(5) span').text()
		}
		getex( obj );
	});
	function getex( obj ){
		    var tqsy=obj.ramt-(-1)*obj.lamt*obj.nemd,
		    	ramt=(-1)*obj.lamt*obj.nemd;
			$.ajax({
				url:'/fx/upgoldCkto.do',
				data:JSON.stringify({
					exnm:obj.exnm,
					excd:obj.excd,
					nemd:obj.nemd,
					zcyk:tqsy,
					lamt:obj.lamt,
					bramt:obj.ramt,
					bzcyk:obj.zcyk,
					bcbhl:obj.cbhl,
					ramt:(-1)*obj.lamt*obj.nemd
				}),
				type:'post',
				contentType:'application/json',
				async:true,
				success:function(data){
					$('.total .confirm',parent.document).closest('.zhezhao').remove();
					if(data.code==01){
						dialog.choicedata(data.codeMessage,'total');
						  getFn();
						  getFn1();
					}else if(data.code==02){
						dialog.choicedata(data.codeMessage,'total');
					}
				}
			});
	}
	
	function ren(obj,obj1){
		/*if( obj=='getCkTotalData.do' ){
			$('.boxtop3 .box').remove();
	    	$('#ascrail2000').remove();
	    	$('.boxtop3').append('<div class="box"></div>');
	    	
			var mmg3 = $('.boxtop3 .box').mmGrid({
				height:'200'
				,cols: cols2
				,items:obj1
		        , nowrap:true
		        ,fullWidthRows:true
		        ,showBackboard:true
		        ,loadingText:'正在载入'
			});
		}else if(obj=='goldCkTotalData1.do'){*/
			
			$('.boxtop1 .boxp').html(' ');
			$('.boxtop2 .boxp').html(' ');
	    	$('#ascrail2000').remove();
	    	$('.boxtop1 .boxp').append('<div class="box"></div>');
	    	$('.boxtop2 .boxp ').append('<div class="box"></div>');
	    	
			var mmg2 = $('.boxtop2 .box').mmGrid({
				height:wh
				,cols: cols1
				,items:obj1.slice(7,10)
		        , nowrap:true
		        ,fullWidthRows:true
		        ,showBackboard:true
		        ,loadingText:'正在载入'
			});
			var mmg = $('.boxtop1 .box').mmGrid({
				height:wh
				,cols: cols
				,items:obj1.slice(0,7)
		        , nowrap:true
		        ,fullWidthRows:true
		        ,showBackboard:true
		        ,loadingText:'正在载入'
			});
			mmg2.on('loadSuccess',function(){
				var a=obj1.slice(7,10);
				$('.boxtop2 .box tbody tr').each(function(i,v){
					$(v).find('td').eq(0).attr('excd',a[i].excd);
					$(v).find('td').eq(0).attr('nemd',a[i].nemd);
					$(v).find('td').eq(0).attr('tqsy',a[i].tsy);
				});
			});
		//}
		$(".boxtop1 .mmg-bodyWrapper,.boxtop2 .mmg-bodyWrapper,.boxtop3 .mmg-bodyWrapper").niceScroll({
			touchbehavior:false,
			cursorcolor:"#666",
			cursoropacitymax:0.7,
			cursorwidth:6,
			background:"#ccc",
			autohidemode:false,
			horizrailenabled:true
	  });
	}
	//选择一条数据的确定按钮;
	$('body',parent.document).on('click','.total .twosure,.total .cancel',function(){
		$(this).closest('.zhezhao').remove();
	});
});