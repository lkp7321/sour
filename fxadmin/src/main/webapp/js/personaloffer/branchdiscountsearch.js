/*
 * 分行优惠查询----结售汇-分行优惠查询*/
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
		dialog:'dialog',
		WdatePicker:'./My97DatePicker/WdatePicker'
	}
});
require(['jquery','mmGrid','niceScroll','dialog','WdatePicker'],function($,mmGrid,niceScroll,dialog,WdatePicker){
	var wh=$(window).height()-190+"px",
		ww=$(window).width()-250+"px";
	$('.boxtop').css('width',ww);
	$('.boxtop').css('height',wh);
	var usnm=sessionStorage.getItem('usnm'),
		product=sessionStorage.getItem('product'),
		userkey=JSON.parse( sessionStorage.getItem('menu') ).userKey,
		listnumtotal,
		tit=$('title').text(),
		user={'usnm':usnm,'prod':product},
		listarr=[],
		branchdiscount,str=''; 
	//列参数;
    var cols = [
        { title:'机构号', name:'ogcd' ,width:80, align:'left' },
        { title:'机构名称', name:'ognm' ,width:100, align:'left'},
        { title:'优惠名称', name:'fvnm' ,width:150, align:'left'},
        { title:'使用标志', name:'stat' ,width:80, align:'center'}
    ],cols1=[
             { title:'机构名称', name:'ognm' ,width:80, align:'left' },
             { title:'优惠编码', name:'fvid' ,width:100, align:'left'},
             { title:'优惠名称', name:'fvnm' ,width:150, align:'left'},
             { title:'使用标志', name:'stat' ,width:80, align:'center'}
    ];
    getlo();
    function getlo(){
    	$.ajax({
        	url:'/fx/getuserOgcd.do',
        	type:'post',
    		async:false,
    		data:userkey,
    		contentType:'application/json',
    		success:function(data){
    			if(data.code==02){
    				
    			}else if(data.code==01){
    				if( data.codeMessage.leve=='2'||data.codeMessage.leve==''||!data.codeMessage.leve){
    				}else{
    					//获取机构名称；
    				    $.ajax({
    				    	url:'/fx/queryMaxpavpoint.do',
    				    	type:'post',
    						async:false,
    						contentType:'application/json',
    						data:JSON.stringify({
    							'userKey':userkey,
    							'levelTy':data.codeMessage.leve
    						}),
    						success:function(data){
    							str='<option ogcd="all">所有</option>';
    							if(data.code==02){
    								dialog.choicedata(data.codeMessage,'branchdiscount','aaa');
    							}else if(data.code==01){
    								branchdiscount=data.codeMessage;
    								for(var i in branchdiscount){
    									str+='<option ogcd='+branchdiscount[i].OGCD+'>'+branchdiscount[i].OGNM+'</option>'
    								}
    								$('.long').html( str );
    							}
    						}
    				    });
    				}
    		 }
    		}
        });
    }
    getlist({'userKey':userkey,'ogcd':$('.long option:selected').attr('ogcd'),'pageNo':1,'pageSize':10});
    renpage();
    $('.long').change(function(){
	   getlist({'userKey':userkey,'ogcd':$('.long option:selected').attr('ogcd'),'pageNo':1,'pageSize':10});
	   renpage();
    });
    //渲染函数；
	function getlist(obj){
		var url,cols2;
		if( product=="P004"){
			url='pere/selfavrule.do';
			cols2=cols1;
		}else{
			url='getFavrulelist.do';
			cols2=cols;
		}
	  $.ajax({
		url:url,
		type:'post',
		contentType:'application/json',
		data:JSON.stringify( obj ),
		async:false,
		success:function(data){
			if(data.code==00){
				ren({'cols':cols2,'wh':wh,'userdata':''});
			}else if(data.code==01){
				branchdiscount= data.codeMessage;
				ren({'cols':cols2,'wh':wh,'userdata':branchdiscount.list});
				listnumtotal=branchdiscount.total;
				/*$('.page').remove();
				$('.boxtop').append('<div class="page"><span class="first">首页</span><span class="prev">上一页</span><span class="next">下一页</span><span class="last">末页</span><span class="nopage">第<b class="Nopage">'+branchdiscount.pageNum+'</b>页/共<b class="Totalpage">'+branchdiscount.pages+'</b>页</span><span class="totalpage">共<b class="total">'+branchdiscount.total+'</b>条记录</span></div>')
				*/
			}else{
				ren({'cols':cols2,'wh':wh,'userdata':''});
			}
		}
	});
  }
  //点击规则查看；
  $('.rulesee').click(function(){
	  if( $('.box tbody tr').hasClass('selected') ){
		  	dialog.branchsearfn('branchdiscount');
		    getPage({'fvid':$('.box tr.selected td:eq(0)').attr('fvid'),'rule':$('.box tr.selected td:eq(0)').attr('rule')});
	  }else{
		  dialog.choicedata('请您先选择一条数据!','branchdiscount','aaa');
	  }
  });
  function getPage(obj){
	  str='';
	  var url='',obdt;
	  if( product=='P004'){
		  url='pere/initfavrule.do';
		  obdt={
					fvid:obj.fvid,
					rule:obj.rule	  
			  }
	  }else{
		  url='onSelFavrule.do';
		  obdt={
				userKey:userkey,
				fvid:obj.fvid,
				rule:obj.rule	  
		  }
	  }
	  $.ajax({
			url:url,
			type:'post',
			contentType:'application/json',
			data:JSON.stringify( obdt ),
			async:true,
			success:function(data){
				if(data.code==02){
					dialog.choicedata(data.codeMessage,'branchdiscount','aaa');
				}else if(data.code==01){
					if( product=='P004'){
						branchdiscount= data.codeMessage;
						listarr=branchdiscount;
						if( branchdiscount.length>10){
							for(var i=0,num=10;i<num;i++){
								str+='<tr><td>'+branchdiscount[i].myLabel+'</td><td>'+branchdiscount[i].myData+'</td></tr>'
							}
						}else{
							for(var i=0,num=branchdiscount.length;i<num;i++){
								str+='<tr><td>'+branchdiscount[i].myLabel+'</td><td>'+branchdiscount[i].myData+'</td></tr>'
							}
						}
					}else{
						branchdiscount=JSON.parse( data.codeMessage ).box2;
						listarr=branchdiscount;
						if( branchdiscount.length>10){
							for(var i=0,num=10;i<num;i++){
								str+='<tr><td>'+branchdiscount[i].myLabel+'</td><td>'+branchdiscount[i].myValue+'</td><td>'+branchdiscount[i].myData+'</td></tr>'
							}
						}else{
							for(var i=0,num=branchdiscount.length;i<num;i++){
								str+='<tr><td>'+branchdiscount[i].myLabel+'</td><td>'+branchdiscount[i].myValue+'</td><td>'+branchdiscount[i].myData+'</td></tr>'
							}
						}
					}
					$('.page',parent.document).remove();
    				$('.branchdiscount .cer',parent.document).append('<div class="page"><span class="first">首页</span><span class="prev">上一页</span><span class="next">下一页</span><span class="last">末页</span><span class="nopage">第<b class="Nopage">'+1+'</b>页/共<b class="Totalpage">'+Math.ceil(branchdiscount.length/10)+'</b>页</span><span class="totalpage">共<b class="total">'+branchdiscount.length+'</b>条记录</span></div>');
					$('.branchdiscount table tbody',parent.document).html( str );
				}
			}
		});
  }
  $('body',parent.document).on('click','.branchdiscount .first',function(){//首页；
	  var str='';
	  var nopage=$('.branchdiscount .Nopage',parent.document).text(),brr=[];
	  	  
	  	  $('.branchdiscount .Nopage',parent.document).text('1');
	  	  brr=listarr.slice(0,10);
	  if( listarr.length>0){
		  if( product=='P004' ){
			  for(var i=0,num=10;i<num;i++){
					str+='<tr><td>'+branchdiscount[i].myLabel+'</td><td>'+branchdiscount[i].myData+'</td></tr>'
				}
		  }else{
			  for(var i=0,num=brr.length;i<num;i++){
					str+='<tr><td>'+brr[i].myLabel+'</td><td>'+brr[i].myValue+'</td><td>'+brr[i].myData+'</td></tr>'
			  }
		  }
		  $('.branchdiscount table tbody',parent.document).html( str );
	  }
  });
  $('body',parent.document).on('click','.branchdiscount .prev',function(){//上一页；3,4
	  var str='';
	  var nopage=$('.branchdiscount .Nopage',parent.document).text()*1,brr=[];
	  
  	  if( nopage-1<=1){
  		 brr=listarr.slice(0,10);
  		$('.branchdiscount .Nopage',parent.document).text('1');
  	  }else{
  		 brr=listarr.slice( (nopage-1-1)*10,10*(nopage-1) );
		 $('.branchdiscount .Nopage',parent.document).text( nopage-1 );
  	  }	
  	  if( listarr.length>0){
  		  if( product=='P004' ){
			  for(var i=0,num=10;i<num;i++){
					str+='<tr><td>'+branchdiscount[i].myLabel+'</td><td>'+branchdiscount[i].myData+'</td></tr>'
				}
		  }else{
			  for(var i=0,num=brr.length;i<num;i++){
					str+='<tr><td>'+brr[i].myLabel+'</td><td>'+brr[i].myValue+'</td><td>'+brr[i].myData+'</td></tr>'
			  }
		  }
 		 /* for(var i=0,num=brr.length;i<num;i++){
 				str+='<tr><td>'+brr[i].myLabel+'</td><td>'+brr[i].myValue+'</td><td>'+brr[i].myData+'</td></tr>'
 		  }*/
 		  $('.branchdiscount table tbody',parent.document).html( str );
 	  }
  });
  $('body',parent.document).on('click','.branchdiscount .next',function(){//下一页；1-2
	  var str='';
	  var nopage=$('.branchdiscount .Nopage',parent.document).text()*1,brr=[],
	  	  Totalpage=$('.Totalpage',parent.document).text(); 
	  
	  if( nopage+1>=Totalpage*1){
		  brr=listarr.slice( (Totalpage-1)*10,10*Totalpage );
		  $('.branchdiscount .Nopage',parent.document).text( Totalpage );
	  }else{
		  brr=listarr.slice( (nopage+1-1)*10,10*(nopage+1) );
		  $('.branchdiscount .Nopage',parent.document).text( nopage+1 );
	  }
	 
	  if( listarr.length>0){
		  if( product=='P004' ){
			  for(var i=0,num=10;i<num;i++){
					str+='<tr><td>'+branchdiscount[i].myLabel+'</td><td>'+branchdiscount[i].myData+'</td></tr>'
				}
		  }else{
			  for(var i=0,num=brr.length;i<num;i++){
					str+='<tr><td>'+brr[i].myLabel+'</td><td>'+brr[i].myValue+'</td><td>'+brr[i].myData+'</td></tr>'
			  }
		  }
		  /*for(var i=0,num=brr.length;i<num;i++){
				str+='<tr><td>'+brr[i].myLabel+'</td><td>'+brr[i].myValue+'</td><td>'+brr[i].myData+'</td></tr>'
		  }*/
		  $('.branchdiscount table tbody',parent.document).html( str );
	  }
  });
  $('body',parent.document).on('click','.branchdiscount .last',function(){//末页；
	  var str='';
	  var nopage=$('.branchdiscount .Nopage',parent.document).text(),brr=[],
	  	  Totalpage=$('.Totalpage',parent.document).text();
	  
	  brr=listarr.slice( (Totalpage-1)*10,10*Totalpage );
	  $('.branchdiscount .Nopage',parent.document).text( Totalpage );
	  
	  if( listarr.length>0){
		  if( product=='P004' ){
			  for(var i=0,num=10;i<num;i++){
					str+='<tr><td>'+branchdiscount[i].myLabel+'</td><td>'+branchdiscount[i].myData+'</td></tr>'
				}
		  }else{
			  for(var i=0,num=brr.length;i<num;i++){
					str+='<tr><td>'+brr[i].myLabel+'</td><td>'+brr[i].myValue+'</td><td>'+brr[i].myData+'</td></tr>'
			  }
		  }
		 /* for(var i=0,num=brr.length;i<num;i++){
				str+='<tr><td>'+brr[i].myLabel+'</td><td>'+brr[i].myValue+'</td><td>'+brr[i].myData+'</td></tr>'
		  }*/
		  $('.branchdiscount table tbody',parent.document).html( str );
	  }
  });
  
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
		 $(".mmg-bodyWrapper").niceScroll({
				touchbehavior:false,
				cursorcolor:"#666",
				cursoropacitymax:0.7,
				cursorwidth:6,
				background:"#ccc",
				autohidemode:false,
				horizrailenabled:obj.horizrailenabled
		  });
		 if( obj.userdata.length>0){
			 mmg.on('loadSuccess',function(){
					$('.box tbody tr').each(function(i,v){
						$(v).find('td').eq(0).attr('rule',obj.userdata[i].rule);
						$(v).find('td').eq(0).attr('fvid',obj.userdata[i].fvid);
					});
			});
		 }
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
  		    		 getlist({
  		    			 'userKey':userkey,
  		    			 'ogcd':$('.long option:selected').attr('ogcd'),
  		    			 'pageNo':obj.curr,
  		    			 'pageSize':$('.layui-laypage-limits select option:selected').attr('value')*1
  		    		});
  		    	}	
  		    }
  		  });
  	});
  }
  $('body',parent.document).on('click','.branchdiscount .close,.branchdiscount .sure',function(){
	  $(this).closest('.zhezhao').remove();
  });
	/*----------------快速搜索功能的实现------------------------*/
	$('.info_serbtn').click(function(){
		 dialog.serchData($('.info_seript').val(),'.mmg-headWrapper');
    });
	
	//点击分页;
    /*$('.boxtop').on('click','.first',function(){
    	var Nopage=$('.Nopage').text()*1;
 	    if(Nopage>1){
 	    	Nopage=1;
 	    	getlist({'userKey':userkey,'ogcd':$('.long option:selected').attr('ogcd'),'pageNo':Nopage,'pageSize':10});
 	    }
    });
	$('.boxtop').on('click','.prev',function(){
	    var Nopage=$('.Nopage').text()*1;
	    if(Nopage>1){
	    	Nopage=Nopage-1;
	    	getlist({'userKey':userkey,'ogcd':$('.long option:selected').attr('ogcd'),'pageNo':Nopage,'pageSize':10});
	    }
	});
	$('.boxtop').on('click','.next',function(){
		var Nopage=$('.Nopage').text()*1,
			Totalpage=$('.Totalpage').text()*1;
	    if(Nopage<Totalpage){
	    	Nopage=Nopage*1+1;
	    	getlist({'userKey':userkey,'ogcd':$('.long option:selected').attr('ogcd'),'pageNo':Nopage,'pageSize':10});
	    }
	});
	$('.boxtop').on('click','.last',function(){
		var Nopage=$('.Nopage').text()*1,
		Totalpage=$('.Totalpage').text()*1;
	    if(Nopage<Totalpage){
	    	Nopage=Totalpage;
	    	getlist({'userKey':userkey,'ogcd':$('.long option:selected').attr('ogcd'),'pageNo':Nopage,'pageSize':10});
	    }
	});*/
})

