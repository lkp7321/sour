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
	var wh=$(window).height()-195+"px",
		ww=$(window).width()-260+"px";
	
	$('.boxtop').css('width',ww);
	$('.boxtop').css('height',wh);
	var userkey=JSON.parse( sessionStorage.getItem('menu') ).userKey,
		listnumtotal,
		usnm=sessionStorage.getItem('usnm');
	//列参数;
   var cols = [
        { title:'柜员ID', name:'tellerid',width:80,align:'left' },
        { title:'外管局机构', name:'ogcd',width:80, align:'left'},
        { title:'柜员名称', name:'trtl',width:80, align:'left'},
        { title:'柜员类型', name:'tltp',width:80, align:'center'},
        { title:'柜员机构', name:'ognm',width:150, align:'left'},
        { title:'柜员渠道', name:'chnl',width:80, align:'left'}
    ];
   //bhid 		
   getlist({
	   'trtltxt':'',
		'comdata1':'',
		'bhidp':'',
		'pageNo':1,
		'pageSize':10
   });
   renpage();
  //获取页面列表；
  function getlist( obj ){
	 $.ajax({
		url:'/fx/selcPreMessage.do',
		type:'post',
		contentType:'application/json',
		data:JSON.stringify(obj ),
		async:false,
		success:function(data){
			listnumtotal=JSON.parse( data.codeMessage ).total;
			if(data.code==00){
				userdata=JSON.parse( data.codeMessage );
				ren({'cols':cols,'wh':wh,'userdata':userdata.list});
				/*$('.page').remove();
				$('.boxtop').append('<div class="page"><span class="first">首页</span><span class="prev">上一页</span><span class="next">下一页</span><span class="last">末页</span><span class="nopage">第<b class="Nopage">'+userdata.pageNum+'</b>页/共<b class="Totalpage">'+userdata.pages+'</b>页</span><span class="totalpage">共<b class="total">'+userdata.total+'</b>条记录</span></div>');
				*/
			}else if(data.code==01){
				ren({'cols':cols,'wh':wh,'userdata':''});
			}
		}
	});
 }
  function renpage(){
	  if( listnumtotal ){
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
	  		    		var trtltxt=$('.countfit').attr('tit');
						if( !trtltxt){
							trtltxt='';
						}else{
							trtltxt=trtltxt;
						}
	  		    		getlist({
	  			    		'trtltxt':trtltxt,	
	  						'comdata1':$('.foreign1').val(),  //外管局机构;
	  						'bhidp':$('.countnum').val(),  //柜员号；
	  						'pageNo':obj.curr,
	  						'pageSize':$('.layui-laypage-limits select option:selected').attr('value')*1
	  			    	});
	  		    	}	
	  		    }
	  		  });
	  	});
		  
	  }else{
		  $("#page").html(' ');
	  }
  }
  //点击添加、修改、删除；
  $('.add').click(function(){
	  dialog.electrictellfn('electrictell','add');
	  $.ajax({
		url:'/fx/selectOgcd.do',
		type:'post',
		contentType:'application/json',
		async:true,
		success:function(){
		}
	  });
	  $.ajax({
		url:'/fx/selectTOgcd.do',
		type:'post',
		contentType:'application/json',
		async:true,
		data:JSON.stringify({
			usnm:usnm,
			orgn:'0001',
			pageNo:1,
			pageSize:10
		}),
		success:function(){
			
		}
	  });
	  $.ajax({
		url:'/fx/selectChnlP.do',
		type:'post',
		contentType:'application/json',
		async:true,
		success:function(){
		}
	  });
	  $('.usernum',parent.document).on('blur',function(){
		  if( $(this).val()==''){
			  $(this).closest('div').find('re').remove();
			  $(this).closest('div').append('<re>柜员ID不能为空!</re>');
		  }else{
			  $(this).closest('div').find('re').remove();
		  }
	  });
	  $('.acuntname',parent.document).on('blur',function(){
		  if( $(this).val()==''){
			  $(this).closest('div').find('re').remove();
			  $(this).closest('div').append('<re>柜员名称不能为空!</re>');
		  }else{
			  $(this).closest('div').find('re').remove();
		  }
	  });
	  $('.acuntpsd',parent.document).on('blur',function(){
		  if( $(this).val()==''){
			  $(this).closest('div').find('re').remove();
			  $(this).closest('div').append('<re>柜员密码不能为空!</re>');
		  }else{
			  $(this).closest('div').find('re').remove();
		  }
	  });
	  $('.outfit',parent.document).on('blur',function(){
		  if( $(this).val()==''){
			  $(this).closest('div').find('re').remove();
			  $(this).closest('div').append('<re>外管局机构不能为空!</re>');
		  }else{
			  $(this).closest('div').find('re').remove();
		  }
	  });
  });
  $('.modify').click(function(){
	  if( $('.box tbody tr').hasClass('selected') ){
		  dialog.electrictellfn('electrictell','modify'); 
		  $('.usernum',parent.document).val( $('.box tr.selected td:eq(0) span').text() );
		  $('.acuntname',parent.document).val( $('.box tr.selected td:eq(2) span').text() );
		
		  if( $('.box tr.selected td:eq(3) span').text()=='操作柜员'){
			  $('.electrictell .r1',parent.document).prop('checked','checked');
		  }else{
			  $('.electrictell .r2',parent.document).prop('checked','checked');
		  }
		  $('.acuntpsd',parent.document).val( $('.box tr.selected td:eq(0)').attr('pass') );
		  $('.outfit',parent.document).val( $('.box tr.selected td:eq(1) span').text() );
		  $('.countchannel option',parent.document).each(function(i,v){
			  if( $(v).text()==$('.box tr.selected td:eq(5) span') .text() ){
				  $(v).prop('selected','seleced');
			  }
		  });
		  $('.countfit',parent.document).val( $('.box tr.selected td:eq(4) span').text() );
		  $('.countfit',parent.document).attr('tit', $('.box tr.selected td:eq(0)').attr('bhid') );
		  
		  $('.usernum',parent.document).on('blur',function(){
			  if( $(this).val()==''){
				  $(this).closest('div').find('re').remove();
				  $(this).closest('div').append('<re>柜员ID不能为空!</re>');
			  }else{
				  $(this).closest('div').find('re').remove();
			  }
		  });
		  $('.acuntname',parent.document).on('blur',function(){
			  if( $(this).val()==''){
				  $(this).closest('div').find('re').remove();
				  $(this).closest('div').append('<re>柜员名称不能为空!</re>');
			  }else{
				  $(this).closest('div').find('re').remove();
			  }
		  });
		  $('.acuntpsd',parent.document).on('blur',function(){
			  if( $(this).val()==''){
				  $(this).closest('div').find('re').remove();
				  $(this).closest('div').append('<re>柜员密码不能为空!</re>');
			  }else{
				  $(this).closest('div').find('re').remove();
			  }
		  });
		  $('.outfit',parent.document).on('blur',function(){
			  if( $(this).val()==''){
				  $(this).closest('div').find('re').remove();
				  $(this).closest('div').append('<re>外管局机构不能为空!</re>');
			  }else{
				  $(this).closest('div').find('re').remove();
			  }
		  });
	  }else{
  		dialog.choicedata('请先选择一条数据!','electricter');
	  }
  });
  //点击保存；
  $('body',parent.document).on('click','.electrictell .preserve',function(){
	  var url,tit=$('.pubhead span',parent.document).text();
	  if( tit=='电子柜员-添加'){
		  url='insertEleT.do';
	  }else{
		  url='updateEleT.do';
	  }
	  if( $('.usernum',parent.document).val()==''){
		  $('.usernum',parent.document).closest('div').find('re').remove();
		  $('.usernum',parent.document).closest('div').append('<re>柜员ID不能为空!</re>');
	  }else{
		  $('.usernum',parent.document).closest('div').find('re').remove();
	  }

	  if( $('.acuntname',parent.document).val()==''){
		  $('.acuntname',parent.document).closest('div').find('re').remove();
		  $('.acuntname',parent.document).closest('div').append('<re>柜员名称不能为空!</re>');
	  }else{
		  $('.acuntname',parent.document).closest('div').find('re').remove();
	  }
	  if( $('.acuntpsd',parent.document).val()==''){
		  $('.acuntpsd',parent.document).closest('div').find('re').remove();
		  $('.acuntpsd',parent.document).closest('div').append('<re>柜员密码不能为空!</re>');
	  }else{
		  $('.acuntpsd',parent.document).closest('div').find('re').remove();
	  }
	  if( $('.outfit',parent.document).val()==''){
		  $('.outfit',parent.document).closest('div').find('re').remove();
		  $('.outfit',parent.document).closest('div').append('<re>外管局机构不能为空!</re>');
	  }else{
		  $('.outfit',parent.document).closest('div').find('re').remove();
	  }
	  if( $('.countfit',parent.document).val()==''){
		  $('.countfit',parent.document).closest('div').find('re').remove();
		  $('.countfit',parent.document).closest('div').append('<re>柜员机构不能为空!</re>');
	  }else{
		  $('.countfit',parent.document).closest('div').find('re').remove();
	  }
	  
	  var tellerid=$('.usernum',parent.document).val(),
	  	  acuntname=$('.acuntname',parent.document).val(),
	  	  acuntpsd=$('.acuntpsd',parent.document).val(),
		  	tltp=$('.electrictell input[type="radio"]:checked',parent.document).attr('tit'),
		  	outfit=$('.outfit',parent.document).val(),
		  	chnl=$('.countchannel option:selected',parent.document).attr('tit'),
	  	    ognm=$('.countfit',parent.document).val(),
	  	    bhid=$('.countfit',parent.document).attr('tit');
	  
	  if($('.electrictell re',parent.document).length==0 ){
		  $.ajax({
			url:url,
			type:'post',
			contentType:'application/json',
			async:true,
			data:JSON.stringify({
					ogcd:outfit,
					trtl:acuntname,
					tltp:tltp,
					bhid:bhid,
					chnl:chnl,
					tellerid:tellerid,
					pass:acuntpsd,
					userKey:userkey,
			}),
			success:function(data){
				$('.electrictell .preserve',parent.document).closest('.zhezhao').remove();
				if(data.code==00){	
					dialog.choicedata(data.codeMessage,'electricter');
					var trtltxt=$('.countfit').attr('tit');
					if( !trtltxt){
						trtltxt='';
					}else{
						trtltxt=trtltxt;
					}
					 getlist({
					   'trtltxt':trtltxt,
						'comdata1':'',
						'bhidp':'',
						'pageNo':1,
						'pageSize':10
				   });
					 renpage();
				}else{
					dialog.choicedata(data.codeMessage,'electricter');
				}
			}
		  });
	  }
  });
  $('.del').click(function(){
  	if( $('.box tbody tr').hasClass('selected') ){
  		dialog.cancelDate('您确定要删除当前记录吗？','electricter');
  	}else{
  		dialog.choicedata('请先选择一条数据!','electricter');
  	}
  });
//点击选择按钮;
  $('body',parent.document).on('click','.choice',function(){
  	dialog.electrictfn('electrict');
  });
  $('body',parent.document).on('click','.electricter .confirm',function(){
  	 removedata(	$('.box tr.selected td:eq(0) span').text() );
   });
  function removedata(obj ){
  	$.ajax({
			url:'/fx/deleteEleTeller.do',
			type:'post',
			async:true,
			data:JSON.stringify({
				'userKey':userkey,
				'tellerid':obj
			}),
			dataType:'JSON',
			contentType:'application/json;charset=utf-8',
			success:function(data){
				$('.electricter .confirm',parent.document).closest('.zhezhao').remove();
				if(data.code==00){	
					dialog.choicedata(data.codeMessage,'electricter');
					$('.box tr.selected').remove();
					getlist({
						 'trtltxt':$('.countfit').attr('tit'),	
						 'comdata1':$('.foreign1').val(),  //外管局机构;
						 'bhidp':$('.countnum').val(),  //柜员号；
						 'pageNo':$('.Nopage').text()*1,
						 'pageSize':10
					 });
					renpage();
				}else{
					dialog.choicedata(data.codeMessage,'electricter');
				}
			}
		});
  }
  /*-------------------------------选择按钮弹出部分---------------------------------------*/
  //点击选择按钮
   $('.choice').click(function(){		
   	dialog.agentChoose();
   });
   //点击选择机构弹窗的查询按钮
   $('body',parent.document).on('click','.agentchoose .qurey',function(){
   	var Ognmtxt=$.trim($('.agentname',parent.document).val()),
           Ogcdtxt=$.trim($('.agentnum',parent.document).val()),
	        OgcdVo={'ogcd':Ogcdtxt,'ognm':Ognmtxt,'pageNo':1,'pageSize':10};
   	getFavRuleOgcd(OgcdVo);
	});
   //点击选择当前机构
   $('body',parent.document).on('click','.agentchoose .currentagent',function(){			
		var num=0;
		$('.agentmain table tr',parent.document).each(function(i,v){
			if( $(v).hasClass("agentbgc")){
				num++;	
			}
		});
   	if( num==0){
			dialog.choicedata('请选择一条数据!','electricter');
		}else{
			if( $('.currentagent',parent.document).parents('.agentchoose').hasClass('zhezhao') ){
				$('.countfit').val(	$('.agentmain table tr.agentbgc td:eq(1)',parent.document).text() );
				$('.countfit').attr('tit',$('.agentmain table tr.agentbgc td:eq(0)',parent.document).text() );
			}else{
				$('.electrictell .countfit',parent.document).val( $('.agentmain table tr.agentbgc td:eq(1)',parent.document).text() );
				$('.electrictell .countfit',parent.document).attr('tit',$('.agentmain table tr.agentbgc td:eq(0)',parent.document).text() );
				$('.countfit',parent.document).closest('div').find('re').remove();
			}
			$('.agentchoose .currentagent',parent.document).closest('.agentchoose').remove();
		}
   });
 //封装点击选择 请求机构
   function getFavRuleOgcd(obj){
   	$.ajax({
			url:'/fx/selectETBhid.do',
			type:'post',
			contentType:'application/json',
			data:JSON.stringify(obj),
			async:false,
			success:function(data){
			   if(data.code==00){
					var userdata=JSON.parse( data.codeMessage),
					    str="<tr><td class='ogcd'>机构号</td><td class='ognm'>机构名称</td></tr>",
					    str1='<span class="first">首页</span><span class="prevbtn">上一页</span><span class="nextbtn">下一页</span><span class="last">末页</span><span class="allnumpre">第<i class="nowpage">'+userdata.pageNum+'</i>页/共<i class="allpages">'+userdata.pages+'</i>页</span><span class="allnum">共'+userdata.total+'条记录</span>';
					pagedata=userdata; 
					for(var i in userdata.list){
				       str+="<tr><td class='one'>"+userdata.list[i].OGCD+"</td><td>"+userdata.list[i].OGNM+"</td></tr>"
					}
					
					$('.agentbot',parent.document).html(' ').append(str1);
					$('.agentmain table',parent.document).html(' ').append(str);
				}else if(data.code==01){
					 //异常
				}
			}
		});
   }
 //点击弹出框的分页的前一页和后一页 首页 末页；
	$('body',parent.document).on('click','.agentchoose .prevbtn',function(){
		prevpage=$('.nowpage',parent.document).text()-1;
		if( pagedata.hasPreviousPage!=false){
			pagenum=--pagedata.pageNum;
			var Ognmtxt=$('.agentname',parent.document).val(),
	            Ogcdtxt=$('.agentnum',parent.document).val(),
	            OgcdVo={'ogcd':Ogcdtxt,'ognm':Ognmtxt,'pageNo':pagenum,'pageSize':10};
   	        getFavRuleOgcd(OgcdVo);
		}
	});
	
	$('body',parent.document).on('click','.agentchoose .nextbtn',function(){  //点击下一页;
		prevpage=parseInt( $('.nowpage',parent.document).text() )+1;
		if(pagedata.hasNextPage!=false){
			pagenum=++pagedata.pageNum;
			var Ognmtxt=$('.agentname',parent.document).val(),
	        Ogcdtxt=$('.agentnum',parent.document).val(),
           OgcdVo={'ogcd':Ogcdtxt,'ognm':Ognmtxt,'pageNo':pagenum,'pageSize':10};
	        getFavRuleOgcd(OgcdVo);
		}
	}); 
	$('body',parent.document).on('click','.agentchoose .first',function(){   //点击首页的时候不需要初始化;再点击全县新增的时已经初始化;
		if( pagedata.hasPreviousPage!=false){
			pagenum=1;
			var Ognmtxt=$('.agentname',parent.document).val(),
	        Ogcdtxt=$('.agentnum',parent.document).val(),
            OgcdVo={'ogcd':Ogcdtxt,'ognm':Ognmtxt,'pageNo':pagenum,'pageSize':10};
	        getFavRuleOgcd(OgcdVo);
		}
	});
	$('body',parent.document).on('click','.agentchoose .last',function(){
		prevpage=parseInt( $('.allpages',parent.document).text() );
		if(pagedata.hasNextPage!=false){
			pagenum=pagedata.pages;
			var Ognmtxt=$('.agentname',parent.document).val(),
	        Ogcdtxt=$('.agentnum',parent.document).val(),
	        OgcdVo={'ogcd':Ogcdtxt,'ognm':Ognmtxt,'pageNo':pagenum,'pageSize':10};
	        getFavRuleOgcd(OgcdVo);
		}
	});
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
		 $(".mmg-bodyWrapper").niceScroll({
				touchbehavior:false,
				cursorcolor:"#666",
				cursoropacitymax:0.7,
				cursorwidth:6,
				background:"#ccc",
				autohidemode:false,
				horizrailenabled:obj.horizrailenabled
		  });
		 mmg.on('loadSuccess',function(){
			 if( userdata.length>0 ){
				 $('.box tbody tr').each(function(i,v){
					$(v).find('td:eq(0)').attr('tit',obj.userdata[i].ogcd);
					$(v).find('td:eq(0)').attr('bhid',obj.userdata[i].bhid);
					$(v).find('td:eq(0)').attr('pass',obj.userdata[i].pass);
				});
			 }
		 });
	}
	/*//封装请求列表
	function getOrgnlist(obj){
		$.ajax({
   		url:'/fx/getFavruleByOgcd.do',
   		type:'post',
   		contentType:'application/json',
   		data:JSON.stringify(obj),
   		async:true,
   		success:function(data){
   			if(data.code==00){
      				userdata=JSON.parse( data.codeMessage );
      				dialog.ren({'cols':cols,'wh':wh,'userdata':userdata.list,'checked':true});
      			 $('.page').remove();
   				$('.boxtop').append('<div class="page"><span class="first">首页</span><span class="prev">上一页</span><span class="next">下一页</span><span class="last">末页</span><span class="nopage">第<b class="Nopage">'+userdata.pageNum+'</b>页/共<b class="Totalpage">'+userdata.pages+'</b>页</span><span class="totalpage">共<b class="total">'+userdata.total+'</b>条记录</span></div>')
      			}else if(data.code==01){
      				dialog.ren({'cols':cols,'wh':wh,'userdata':'','checked':true});
      			}
   		}
   	});} */
	
	$('body',parent.document).on('click','.agentmain table tr',function(){
		 $(this).addClass('agentbgc').siblings().removeClass('agentbgc');
   });
  /*--------------------------------页面数据列表部分--------------------------------*/
  //点击查询;
  $('.search').click(function(){
	  var tit;
	  if( $('.countfit').attr('tit') ){
		  tit=$('.countfit').attr('tit');
	  }else{
		  tit='';
	  }
	  getlist({
		    'trtltxt':tit,	
			'comdata1':$('.foreign1').val(),  //外管局机构;
			'bhidp':$('.countnum').val(),  //柜员号；
			'pageNo':$('.Nopage').text()*1,
			'pageSize':10
	   });
	  console.log( listnumtotal ) 
	  	renpage();	
	   $('.countfit').val(' ');
	   $('.countfit').removeAttr('tit');
  });
 //点击分页;
 /* $('.boxtop').on('click','.first',function(){
  	var Nopage=$('.Nopage').text()*1,
  		trtltxt;
	    if(Nopage>1){
	    	Nopage=1;
	    	if(	!$('.countfit').attr('tit') ){
	    		trtltxt='';
	    	}
	    	getlist({ 
	    		'trtltxt':trtltxt,	
				'comdata1':$('.foreign1').val(),  //外管局机构;
				'bhidp':$('.countnum').val(),  //柜员号；
				'pageNo':Nopage,
				'pageSize':10
			});
	    }
  });
	$('.boxtop').on('click','.prev',function(){
	    var Nopage=$('.Nopage').text()*1,
	    	trtltxt;
	    if(Nopage>1){
	    	Nopage=Nopage-1;
	    	if(	!$('.countfit').attr('tit') ){
	    		trtltxt='';
	    	}
	    	getlist({ 
	    		'trtltxt':trtltxt,	
				'comdata1':$('.foreign1').val(),  //外管局机构;
				'bhidp':$('.countnum').val(),  //柜员号；
				'pageNo':Nopage,
				'pageSize':10
	    	});
	    }
	});
	$('.boxtop').on('click','.next',function(){
		var Nopage=$('.Nopage').text()*1,
			Totalpage=$('.Totalpage').text()*1,
			trtltxt;
			if(	!$('.countfit').attr('tit') ){
	    		trtltxt='';
	    	}
	    if(Nopage<Totalpage){
	    	Nopage=Nopage*1+1;
	    	getlist({
	    		'trtltxt':trtltxt,	
				'comdata1':$('.foreign1').val(),  //外管局机构;
				'bhidp':$('.countnum').val(),  //柜员号；
				'pageNo':Nopage,
				'pageSize':10
	    	});
	    }
	});
	$('.boxtop').on('click','.last',function(){
		var Nopage=$('.Nopage').text()*1,
		Totalpage=$('.Totalpage').text()*1,
		trtltxt;
		if(	!$('.countfit').attr('tit') ){
    		trtltxt='';
    	}
	    if(Nopage<Totalpage){
	    	Nopage=Totalpage;
	    	getlist({
	    		'trtltxt':trtltxt,	
				'comdata1':$('.foreign1').val(),  //外管局机构;
				'bhidp':$('.countnum').val(),  //柜员号；
				'pageNo':Nopage,
				'pageSize':10
	    	});
	    }
	});*/
	$('body',parent.document).on('click','.electricter .cancel,.electrictell .cancel,.electrictell .sure,.electrictell .close',function(){
		$(this).closest('.zhezhao').remove();
	});
	$('body',parent.document).on('click','.choicedialog .sure',function(){
		$('.choicedialog .sure',parent.document).closest('.choicedialog').remove();
	});
	$('body',parent.document).on('click','.agentchoose .close',function(){
		$('.agentchoose .close',parent.document).closest('.agentchoose').remove();
	});
	
})