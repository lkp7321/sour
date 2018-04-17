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
		wdatePicker:'./My97DatePicker/WdatePicker'
	}
});
require(['jquery','mmGrid','niceScroll','wdatePicker','dialog'],function($,mmGrid,niceScroll,wdatePicker,dialog){
	var wh=$(window).height()-200+"px",
		ww=$(window).width()-250+"px";
	$('.boxtop').css('width',ww);
	$('.boxtop').css('height',wh);
	$('#page').css('width',ww);
	$('#d1').val(dialog.today());
	$('#d2').val(dialog.today() );
	//列参数;
    var cols = [
        { title:'商户号', name:'shno' ,width:80, align:'left' },        
        { title:'文件日期', name:'fldt' ,width:80, align:'left' },
        { title:'总笔数', name:'total' ,width:60, align:'left'},
        { title:'人民币净金额', name:'jrmb' ,width:100, align:'left' },        
        { title:'转账方向', name:'dirc' ,width:120, align:'left' },
        { title:'净申购KAJJ', name:'jkau' ,width:100, align:'left'},
        { title:'业务手工中止标识', name:'mafl' ,width:120, align:'left'},
        { title:'实际执行日期', name:'trdt' ,width:100, align:'left' },        
        { title:'实际执行时间', name:'trtm' ,width:100, align:'left' },
        { title:'交易状态', name:'stcd' ,width:100, align:'left'},
        { title:'错误码', name:'ercd' ,width:100, align:'left' },        
        { title:'错误信息', name:'ermg' ,width:100, align:'left' },
        { title:'对方手账号', name:'shac' ,width:100, align:'left'} 
    ];
    var juesedata=false,jigoudata=false,jueseda,jigouda,listnumtotal;
    //请求电商商户列表
    $.ajax({
		url:'/fx/queryShnoInfo.do',
		type:'get',
		async:false,
		dataType:'json',
			contentType:'application/json;charset=utf-8',
			beforeSend:function(){
				str='<option value="all">全部</option>';
			},
		success:function(data){
			 var collectdata=JSON.parse(data.codeMessage);
			 
			 if(data.code==00){
				 for(var i in collectdata){
					str+='<option value="'+collectdata[i].SHNO+'">'+collectdata[i].CUNM+'</option>'
				}
				$('.eccustomer').html(str);
			}else{
				//异常 
			}
		}
	});
 
     var  trdtbegin=$('#d1').val(),
          trdtend=$('#d2').val(),
          shno=$('.eccustomer option:selected').val(),
          dirc=$('.transfer option:selected').val(),
          vo={'trdtbegin':trdtbegin,'trdtend':trdtend,'shno':shno,'dirc':dirc,'pageNo':1,'pageSize':10};
     getList(vo);
     renpage();
     $('.query').click(function(){
    	 trdtbegin=$('#d1').val(),
         trdtend=$('#d2').val(),
         shno=$('.eccustomer option:selected').val(),
         dirc=$('.transfer option:selected').val(),
         vo={'trdtbegin':trdtbegin,'trdtend':trdtend,'shno':shno,'dirc':dirc,'pageNo':1,'pageSize':10};
        getList(vo);
    	 renpage();
     });
     $('.boxtop').on('click','.box tr',function(){
    	 if($(this).find('td:eq(9) span').text()!=='0-初始状态'){
    		 dialog.choicedata('不是初始状态不能手动中止','ecbusiness');
    	 }else{
    		 if($(this).find('td:eq(6) span').text()=='0-未手工中止'){
        		 $('.end').removeAttr('disabled')
        		 $('.start').attr('disabled','disabled')
        	 }else{
        		 $('.end').attr('disabled','disabled')
        		 $('.start').removeAttr('disabled')
        	 } 
    	 }
 
     })
    //点击手动中止
    $('.end,.start').click(function(){
    	var shno=$('.box tr.selected td:eq(0) span').text(),
    	    fldt=$('.box tr.selected td:eq(1) span').text(),
    	    mafl=$('.box tr.selected td:eq(6) span').text(),
    	    endVo={'shno':shno,'fldt':fldt,'mafl':mafl},
    	    text=$(this).text();
    	$.ajax({
    		url:'/fx/stopOrStrat.do',
    		type:'post',
    		contentType:'application/json',
    		data:JSON.stringify(endVo),
    		async:true,
    		success:function(data){
    			if(data.code==00){
    				dialog.choicedata(data.codeMessage,'ecbusiness');
    				if(text=='手动中止'){
    				    $('.box tr.selected td:eq(6) span').text('1-手工中止');
    				    $('.end').attr('disabled','disabled')
    				}else if(text=='手动启动'){
    					$('.box tr.selected td:eq(6) span').text('0-未手工中止');
    					 $('.start').attr('disabled','disabled')
    				}
    				 $('.box tr.selected').removeClass('selected')
       			}else if(data.code=01){
       				dialog.choicedata(data.codeMessage,'ecbusiness'); 
       			} 
    		}
    	}); 
    	
    })	
    	 
     
     
     
     //点击请选择一条数据的确定按钮；
	$('body',parent.document).on('click','.ecbusiness .sure',function(){
		  $(this).closest('.zhezhao').remove();
	});
	 
	
	 
    //封装请求列表
	function getList(obj){
		$.ajax({
    		url:'/fx/selectTranfer.do',
    		type:'post',
    		contentType:'application/json',
    		data:JSON.stringify(obj),
    		async:false,
    		success:function(data){
    			if(data.code==00){
       				userdata=JSON.parse( data.codeMessage );
       				dialog.ren({'cols':cols,'wh':wh,'userdata':userdata.list});
       				listnumtotal=userdata.total;
//       			 $('.page').remove();
//    				$('.boxtop').append('<div class="page"><span class="first">首页</span><span class="prev">上一页</span><span class="next">下一页</span><span class="last">末页</span><span class="nopage">第<b class="Nopage">'+userdata.pageNum+'</b>页/共<b class="Totalpage">'+userdata.pages+'</b>页</span><span class="totalpage">共<b class="total">'+userdata.total+'</b>条记录</span></div>')
       			}else if(data.code==01){
       				dialog.ren({'cols':cols,'wh':wh,'userdata':''});
       			}else if(data.code==02){
       				dialog.ren({'cols':cols,'wh':wh,'userdata':''});
       			}
    		}
    	}); 
	}
	//点击分页;
    $('.boxtop').on('click','.page .first',function(){
    	var Nopage=parseInt($('.Nopage').text());
 	    if(Nopage>1){
 	    	Nopage=1;
 	    	trdtbegin=$('#d1').val(),
 	         trdtend=$('#d2').val(),
 	         shno=$('.eccustomer option:selected').val(),
 	         dirc=$('.transfer option:selected').val(),
 	         vo={'trdtbegin':trdtbegin,'trdtend':trdtend,'shno':shno,'dirc':dirc,'pageNo':Nopage,'pageSize':10};
 	        getList(vo);
 	     }
 	   
    });
	$('.boxtop').on('click','.prev',function(){
		var Nopage=parseInt($('.Nopage').text());
	    if(Nopage>1){
	    	Nopage=Nopage-1;
	    	trdtbegin=$('#d1').val(),
	         trdtend=$('#d2').val(),
	         shno=$('.eccustomer option:selected').val(),
	         dirc=$('.transfer option:selected').val(),
	         vo={'trdtbegin':trdtbegin,'trdtend':trdtend,'shno':shno,'dirc':dirc,'pageNo':Nopage,'pageSize':10};
	        getList(vo);
	     }
	     
	});
	$('.boxtop').on('click','.next',function(){
		var Nopage=parseInt($('.Nopage').text());
		   Totalpage=parseInt($('.Totalpage').text());
	    if(Nopage<Totalpage){
	    	Nopage=Nopage*1+1;
	    	trdtbegin=$('#d1').val(),
	         trdtend=$('#d2').val(),
	         shno=$('.eccustomer option:selected').val(),
	         dirc=$('.transfer option:selected').val(),
	         vo={'trdtbegin':trdtbegin,'trdtend':trdtend,'shno':shno,'dirc':dirc,'pageNo':Nopage,'pageSize':10};
	        getList(vo);
	     }
	     
	});
	$('.boxtop').on('click','.last',function(){
		var Nopage=parseInt($('.Nopage').text());
		   Totalpage=parseInt($('.Totalpage').text());
	    if(Nopage<Totalpage){
	    	Nopage=Totalpage;
	    	trdtbegin=$('#d1').val(),
	         trdtend=$('#d2').val(),
	         shno=$('.eccustomer option:selected').val(),
	         dirc=$('.transfer option:selected').val(),
	         vo={'trdtbegin':trdtbegin,'trdtend':trdtend,'shno':shno,'dirc':dirc,'pageNo':Nopage,'pageSize':10};
	        getList(vo);
	    }
	     
	});
	 
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
			    		getList({
			    			'trdtbegin':$('#d1').val(),
			    			'trdtend':$('#d2').val(),
			    			'shno':$('.eccustomer option:selected').val(),
			    			'dirc':$('.transfer option:selected').val(),
			    			'pageSize':$('.layui-laypage-limits select option:selected').attr('value')*1,
	    				    'pageNo':obj.curr
			    			});
			    	}	
			    }
			  });
		});
	} 	
	 
	  
})
