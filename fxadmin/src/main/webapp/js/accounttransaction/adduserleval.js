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
	//列参数;
    var cols = [
        { title:'客户等级', name:'CUTY' ,width:140, align:'right' },         
        { title:'等级名称', name:'TYNM' ,width:100, align:'center' },        
        { title:'属性', name:'GSTP' ,width:140, align:'right' },
        { title:'BP映射', name:'BPVL' ,width:100, align:'left'}
    ];
    var juesedata=false,jigoudata=false,jueseda,jigouda,listnumtotal;
    //请求列表
    var userkey=JSON.parse( sessionStorage.getItem('menu') ).userKey,
        product=sessionStorage.getItem('product'),
        gstp=$('.levalname option:selected').val(),
        vo={'userKey':userkey,'ptid':product,'gstp':gstp,'pageNo':1,'pageSize':10};
    getList(vo);
    renpage();
    //点击查询请求列表
    $('.query').click(function(){
    	gstp=$('.levalname option:selected').val();
        vo={'userKey':userkey,'ptid':product,'gstp':gstp,'pageNo':1,'pageSize':10};
    	getList(vo);
    	renpage();
    })
   
 
	//点击添加按钮  弹出添加窗
	$('.addmain,.adddefined').click(function(){
	    if($(this).text()=='添加核心等级'){
		   dialog.addUserleval('adduserleval','添加核心等级');
		}else{
		   dialog.addUserleval('adduserleval','添加自定义等级');
		}   
	    $.ajax({
    		url:'/fx/selMaxCuty.do',
    		type:'post',
    		contentType:'application/json',
    		data:$(this).text().substr(2),
    		async:true,
    		success:function(data){
    			if(data.code==00){
    			   $('.maxleval',parent.document).val(data.codeMessage); 
       			}else if(data.code==02){
       				 
       			}
    		}
    	}); 
	    
	    
		
	 
	});
	 //点击添加弹出框的保存按钮；
	$('body',parent.document).on('click','.preserve',function(){
	    var bpvl=$('.maxleval',parent.document).val(),
	        bp=$('.bp',parent.document).val(),
	        text=$(this).parents('.zhezhao').find('.pubhead span').text();
	        addVo={'bpvl':bpvl,'bp':bp,'ptid':product}
	      if(text=='添加核心等级'){
	    	   var url='addCoreCuty.do';
	    	 
	       }else{
	    	   var url='addDefineCuty.do';
	       }
	       $.ajax({
				url:url,
				type:'post',
				contentType:'application/json',
				data:JSON.stringify(addVo),
				async:true,
				success:function(data){
					 if(data.code==00){
						 $('.preserve',parent.document).closest('.zhezhao').remove();
						 gstp=$('.levalname option:selected').val();
					     vo={'userKey':userkey,'ptid':product,'gstp':gstp,'pageNo':1,'pageSize':10};
					      getList(vo);
						 dialog.choicedata(data.codeMessage,'adduserleval');
					 }else if(data.code==01){
						//异常
						 dialog.choicedata(data.codeMessage,'adduserleval');
					}
				}
			}); 
	    
	    

	  });
	 
	 
	 
	//点击请选择一条数据的确定按钮；
	$('body',parent.document).on('click','.adduserleval .sure',function(){
		  $(this).closest('.zhezhao').remove();
	});
	//关闭弹窗
	$('body',parent.document).on('click','.discountpub .close,.discountpub .cancel',function(){
		  $(this).closest('.zhezhao').remove();
		   
	});
	
	 
    //封装请求列表
	function getList(obj){
		$.ajax({
    		url:'/fx/getSearchList.do',
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
    	var Nopage=$('.Nopage').text();
 	    if(Nopage>1){
 	    	Nopage=1;
 	     }
 	   ogcd=$('.agentnum').val(),
 	   ogcdVo={'ogcd':ogcd,'pageNo':Nopage,'pageSize':10} 
       getOrgnlist(ogcdVo); 
    });
	$('.boxtop').on('click','.prev',function(){
	    var Nopage=$('.Nopage').text();
	    if(Nopage>1){
	    	Nopage=Nopage-1;
	     }
	    ogcd=$('.agentnum').val(),
        ogcdVo={'ogcd':ogcd,'pageNo':Nopage,'pageSize':10} 
        getOrgnlist(ogcdVo); 
	});
	$('.boxtop').on('click','.next',function(){
		var Nopage=$('.Nopage').text(),
			Totalpage=$('.Totalpage').text();
	    if(Nopage<Totalpage){
	    	Nopage=Nopage*1+1;
	     }
	    ogcd=$('.agentnum').val(),
        ogcdVo={'ogcd':ogcd,'pageNo':Nopage,'pageSize':10} 
        getOrgnlist(ogcdVo); 
	});
	$('.boxtop').on('click','.last',function(){
		var Nopage=$('.Nopage').text(),
		Totalpage=$('.Totalpage').text();
	    if(Nopage<Totalpage){
	    	Nopage=Totalpage;
	    }
	    ogcd=$('.agentnum').val(),
        ogcdVo={'ogcd':ogcd,'pageNo':Nopage,'pageSize':10} 
        getOrgnlist(ogcdVo); 
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
			    	    	'userKey':userkey,
			    	    	'ptid':product,
			    	    	'gstp':$('.levalname option:selected').val(),
			    	    	'pageSize':$('.layui-laypage-limits select option:selected').attr('value')*1,
		    				'pageNo':obj.curr
			    		    });
			    	}	
			    }
			  });
		});
	} 	
	 
	  
})
