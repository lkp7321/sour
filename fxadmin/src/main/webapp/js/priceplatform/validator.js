/*校验器设置 &&报价平台*/
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
	var wh=$(window).height()-190+"px",
		ww=$(window).width()-260+"px";
	var userkey=JSON.parse( sessionStorage.getItem('menu') ).userKey;
	$('.boxtop').css('width',ww);
	$('.boxtop').css('height',wh);
	$('#page').css('width',ww);
	var juesedata=false,jigoudata=false,jueseda,jigouda,listnumtotal;
  $.ajax({
		url:'/fx/price/getMarketinfoList.do',
		type:'post',
		contentType:'application/json',
		async:false,
		success:function(data){
			var str='<option mkid="all">所有</option>'
			if(data.code==01){
				userdata= JSON.parse( data.codeMessage );
				for( var i in userdata){
					str+='<option mkid='+userdata[i].MKID+'>'+userdata[i].MKNM+'</option>'
				}
				$('.markname select').html( str );
				getcuur( $('.markname select option:selected').attr('mkid'));
			}else{
				
			}
		}
	});
  $('.markname select').change(function(){
	  getcuur( $('.markname select option:selected').attr('mkid') );
	  getl();
	  renpage();
  });
  $('.cuurpair select').change(function(){
	  getl();
	  renpage();
  });
  function getcuur( obj ){
	  if( obj=='all'){
		  $('.cuurpair select').html( '<option>所有</option>' );
	  }else{
		  $.ajax({
				url:'/fx/price/getBiBieDuiLists.do',
				type:'post',
				contentType:'application/json',
				data:obj,
				async:true,
				success:function(data){
					var str='<option value="all">所有</option>'
					if(data.code==01){
						userdata= JSON.parse( data.codeMessage );
						for( var i in userdata){
							str+='<option>'+userdata[i]+'</option>'
						}
						$('.cuurpair select').html( str );
					}else{
						
					}
					
				}
			});
	  }
  }
	//列参数;
    var cols = [
            { title:'有效标志', name:'usfg',width:60,align:'center' },
            { title:'市场名称', name:'mknm',width:60, align:'left'},
            { title:'价格类型', name:'tpnm',width:80, align:'center'},
            { title:'期限', name:'term',width:60, align:'right'},
            { title:'货币对名称', name:'exnm',width:60,align:'left'},
            { title:'钞汇标志', name:'cxfg',width:60,align:'center'},
            { title:'买入价上限', name:'mxmd',width:60,align:'right'},
            { title:'买入价下限', name:'mimd',width:60,align:'right'},
            { title:'单边浮动范围', name:'mxdp',width:60,align:'right'} 
    ];
    getlist({
    	'mkid':'all',
    	'exnm':'所有',
    	'pageSize':10,
    	'pageNo':1
    });
    renpage();
    function getlist( obj ){
    	$.ajax({
    		url:'/fx/price/getJiaoYanQiList.do',
    		type:'post',
    		contentType:'application/json',
    		data:JSON.stringify( obj ),
    		async:false,
    		success:function(data){
    			if(data.code==01){
    				userdata= data.codeMessage;
    				ren({'cols':cols,'wh':wh,'userdata':userdata.list,'checked':'true'});
    				listnumtotal=userdata.total;
//    				$('.page').remove();
//    				$('.boxtop').append('<div class="page"><span class="first">首页</span><span class="prev">上一页</span><span class="next">下一页</span><span class="last">末页</span><span class="nopage">第<b class="Nopage">'+userdata.pageNum+'</b>页/共<b class="Totalpage">'+userdata.pages+'</b>页</span><span class="totalpage">共<b class="total">'+userdata.total+'</b>条记录</span></div>')
    			}else{
    				ren({'cols':cols,'wh':wh,'userdata':'','checked':'true'});
    			}
    		}
    	});
    }
    //点击启用、停用；
    $('.staruse,.stopuse').click(function(){
    	console.log(  $(this).text()=='启用'  );
    	if( $(this).text()=='启用'){
    		usfg=1;
    	}else{
    		usfg=0;
    	}
    	var checkednum=0,
    	arr=[];
    	$('.box tr').each(function(i,v){
    		if( $(v).find('input').prop('checked') ){
    			checkednum++;
    			arr.push({
    					usfg:usfg,
    					mkid:$(v).find('td:eq(1)').attr('mkid'),
    					exnm:$(v).find('td:eq(5) span').text()
    			});
    		}
    	});
    	if( checkednum>0){
    		getfn(arr);
    	}else{
    		dialog.choicedata('请先勾选一条数据再进行此操作!','validator');
    	}
    });
    function getfn( obj ){
    	$.ajax({
    		url:'/fx/price/opencloseJiaoyanqi.do',
    		type:'post',
    		contentType:'application/json',
    		data:JSON.stringify({
    			'userKey':userkey,
    			'chlist':obj
    		}),
    		async:true,
    		success:function(data){
    			if(data.code==01){
    				dialog.choicedata(data.codeMessage,'validator');
    				getl();
    			}else{
    				dialog.choicedata(data.codeMessage,'validator');
    			}
    		}
    	});
    }
    function renpage(){
    	layui.use(['laypage', 'layer'], function(){
    		  var laypage = layui.laypage,layer = layui.layer;
    		  //完整功能
    		  laypage.render({
    		    elem: 'page'
    		    ,count:listnumtotal
    		    ,theme: '#5a8bff'
    		    ,layout: ['count', 'prev', 'page', 'next', 'limit', 'skip']
    		    ,jump: function(obj,first){
    		    	if(!first){ //点击跳页触发函数自身，并传递当前页：obj.curr
    		    		var exnm;
			        	if( $('.cuurpair select option:selected').text()=='所有' ){
			    			exnm='所有';
			    		}else{
			    			exnm=$('.cuurpair select option:selected').text();
			    		}
    		    		getlist({
    		    	    	mkid:$('.markname select option:selected').attr('mkid'),
    		    	    	exnm:exnm,
    		    	    	pageNo:obj.curr,
    		    	    	pageSize:$('.layui-laypage-limits select option:selected').attr('value')*1
    		    	    });
    		    	}	
    		    }
    		  });
    	});
    }
    function ren( obj ){
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
					$(v).find('td:eq(1)').attr('mkid',obj.userdata[i].mkid);
					$(v).find('td:eq(1)').attr('usfg',obj.userdata[i].usfg);
					
					$(v).find('td:eq(1)').attr('mxbp',obj.userdata[i].mxbp);
					$(v).find('td:eq(1)').attr('mxct',obj.userdata[i].mxct);
					$(v).find('td:eq(1)').attr('mxud',obj.userdata[i].mxud);
					$(v).find('td:eq(1)').attr('excd',obj.userdata[i].excd);
					$(v).find('td:eq(1)').attr('cxfg',obj.userdata[i].cxfg);
					$(v).find('td:eq(1)').attr('tpfg',obj.userdata[i].tpfg);
					$(v).find('td:eq(1)').attr('mdmd',obj.userdata[i].mdmd);
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
    $('body',parent.document).on('click','.validator .sure,.validator .price_cancel,.validator .close',function(){
		$(this).closest('.zhezhao').remove();
	});
    $('.modify').click(function(){
    	if( $('.box tbody tr').hasClass('selected') ){
    		dialog.priceModify1('validator');
    		$('.five',parent.document).show();
    		
    		$('.hbdname span',parent.document).text( $('.box tr.selected td:eq(5) span').text() );
    		$('.productname span',parent.document).text( $('.box tr.selected td:eq(4) span').text() );
    		$('.privetype span',parent.document).text( $('.box tr.selected td:eq(3) span').text() );
    		$('.chnote span',parent.document).text( $('.box tr.selected td:eq(2) span').text() );
    		
    		$('.suspe',parent.document).val( $('.box tr.selected td:eq(1)').attr('mdmd') );
    		$('.suspen',parent.document).val( $('.box tr.selected td:eq(9) span').text() );
    	}else{
    		dialog.choicedata('请先选择一条数据!','validator','aaa');
    	}
    });
    $('body',parent.document).on('click','.validator .price_sav',function(){
    	if( $('.box tr.selected td:eq(1)').attr('cxfg')=='钞'){
    		cxfg='2';
    	}else{
    		cxfg='1';
    	}
    	var da={
    			userKey:userkey,
    			cmmbean:{
	    			mdmd:$('.suspe',parent.document).val(),//买入价
	    			mxdp:$('.suspen',parent.document).val(),//单边浮动范围
	    			mxbp:$('.box tr.selected td:eq(1)').attr('mxbp'),
	    			mxct:$('.box tr.selected td:eq(1)').attr('mxct'),
	    			mxud:$('.box tr.selected td:eq(1)').attr('mxud'),
	    			exnm:$('.hbdname span',parent.document).text(),
	    			usfg:$('.box tr.selected td:eq(1)').attr('usfg'),
	    			mkid:$('.box tr.selected td:eq(1)').attr('mkid'),
	    			mknm:$('.box tr.selected td:eq(2) span').text(),
	    			tpfg:$('.box tr.selected td:eq(1)').attr('tpfg'),
	    			term:$('.productname span',parent.document).text(),
	    			excd:$('.box tr.selected td:eq(1)').attr('excd'),
	    			cxfg:cxfg
    			}
    	}
    	$.ajax({
    		url:'/fx/price/upJiaoyanqi.do',
    		data:JSON.stringify(da),    
    		type:'post',
    		contentType:'application/json',
    		success:function(data){
    			$('.validator .price_sav',parent.document).closest('.zhezhao').remove();
    			if( data.code=='01'){
    				dialog.choicedata(data.codeMessage,'validator');
    				getl();
    			}else{
    				dialog.choicedata(data.codeMessage,'validator');
    			}
    		}
    	})
    });
    function getl(){
    	var exnm,mkid;
    	if( $('.cuurpair select option:selected').text()=='所有' ){
			exnm='所有';
		}else{
			exnm=$('.cuurpair select option:selected').text();
		}
    	mkid=$('.markname select option:selected').attr('mkid');	
		 getlist({
	    	'mkid':mkid,
	    	'exnm':exnm,
	    	'pageSize':10,
	    	'pageNo':1
	    });
    }
  //点击分页;
    $('.boxtop').on('click','.first',function(){
    	var Nopage=$('.Nopage').text()*1,exnm;
    	if( $('.cuurpair select option:selected').text()=='所有' ){
			exnm='所有';
		}else{
			exnm=$('.cuurpair select option:selected').text();
		}
 	    if(Nopage>1){
 	    	Nopage=1;
 	    	getlist({
 	    		'mkid':$('.markname select option:selected').attr('mkid'),
 		    	'exnm':exnm,
 		    	'pageSize':10,
 		    	'pageNo':Nopage
	        });
 	    }
    });
	$('.boxtop').on('click','.prev',function(){
	    var Nopage=$('.Nopage').text()*1,exnm;
	    if( $('.cuurpair select option:selected').text()=='所有' ){
			exnm='所有';
		}else{
			exnm=$('.cuurpair select option:selected').text();
		}
	    if(Nopage>1){
	    	Nopage=Nopage-1;
	    	getlist({
 	    		'mkid':$('.markname select option:selected').attr('mkid'),
 		    	'exnm':exnm,
 		    	'pageSize':10,
 		    	'pageNo':Nopage
	        });
	    }
	});
	$('.boxtop').on('click','.next',function(){
		var Nopage=$('.Nopage').text()*1,
			Totalpage=$('.Totalpage').text()*1,exnm;
		if( $('.cuurpair select option:selected').text()=='所有' ){
			exnm='所有';
		}else{
			exnm=$('.cuurpair select option:selected').text();
		}
	    if(Nopage<Totalpage){
	    	Nopage=Nopage*1+1;
	    	getlist({
 	    		'mkid':$('.markname select option:selected').attr('mkid'),
 		    	'exnm':exnm,
 		    	'pageSize':10,
 		    	'pageNo':Nopage
	        });
	    }
	});
	$('.boxtop').on('click','.last',function(){
		var Nopage=$('.Nopage').text()*1,
		Totalpage=$('.Totalpage').text()*1,exnm;
		if( $('.cuurpair select option:selected').text()=='所有' ){
			exnm='所有';
		}else{
			exnm=$('.cuurpair select option:selected').text();
		}
	    if(Nopage<Totalpage){
	    	Nopage=Totalpage;
	    	getlist({
 	    		'mkid':$('.markname select option:selected').attr('mkid'),
 		    	'exnm':exnm,
 		    	'pageSize':10,
 		    	'pageNo':Nopage
	        });
	    }
	});
})