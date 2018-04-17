/*
 * 个人实盘-分行优惠设置 &&纸黄金-分行优惠设置&&P004-结售汇-分行优惠设置；
 * */
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
	$('.boxtop').css('width',ww);
	$('.boxtop').css('height',wh);
	var usnm=sessionStorage.getItem('usnm'),
		product=sessionStorage.getItem('product'),
		userkey=JSON.parse( sessionStorage.getItem('menu') ).userKey,
		user={'usnm':usnm,'prod':product},
		listarr=[],branchdiscount,listnumtotal,
		userdata,str=''; 
	if( product!='P004'){
		$('.inital').show();
	}
	//列参数;
    var cols = [
        { title:'机构号', name:'ogcd',width:130, align:'left' },
        { title:'机构名称', name:'ognm' ,width:150, align:'left'},
        { title:'优惠名称', name:'fvnm' ,width:120, align:'left'},
        { title:'使用标志', name:'stat' ,width:100, align:'center'}
    ],cols1=[
             { title:'机构名称', name:'ognm' ,width:80, align:'left' },
             { title:'优惠编码', name:'fvid' ,width:100, align:'left'},
             { title:'优惠名称', name:'fvnm' ,width:150, align:'left'},
             { title:'使用标志', name:'stat' ,width:80, align:'center'}
    ];
    getlo();
    /*//获取机构名称；
    $.ajax({
    	url:'/fx/queryMaxpavpoint.do',
    	type:'post',
		async:false,
		contentType:'application/json',
		data:userkey,
		success:function(data){
			str='<option ogcd="all">所有</option>';
			if(data.code==02){
				dialog.choicedata('获取机构失败!','branchdiscount','aaa');   							//提示弹出框名称修改
			}else if(data.code==01){
				userdata=data.codeMessage;
				for(var i in userdata){
					str+='<option ogcd='+userdata[i].OGCD+'>'+userdata[i].OGNM+'</option>'
				}
				$('.long').html( str );
			}
		}
    });*/
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
    				if( data.codeMessage.leve=='2'){
    					$('.maxpreferential .fourtul').hide();
    					$('.maxpreferential .cer').hide();
    					
    					$('.inital,.enable,.disable,.rulemodify').prop('disabled','disabled');
    					$('.inital,.enable,.disable,.rulemodify').css('background','#ccc');
    					
    				}else if(data.codeMessage.leve==''||!data.codeMessage.leve){
    					dialog.choicedata('判断用户所属机构等级失败!','maxprefer');
    					$('.maxpreferential .cer').hide();
    					
    					$('.inital,.enable,.disable,.rulemodify').prop('disabled','disabled');
    					$('.inital,.enable,.disable,.rulemodify').css('background','#ccc');
    				}else{
    					 //获取机构名称；
    				    $.ajax({
    				    	url:'/fx/queryMaxpavpoint.do',
    				    	type:'post',
    						async:false,
    						data:JSON.stringify({
    							'userKey':userkey,
    							'levelTy':data.codeMessage.leve
    						}),
    						contentType:'application/json',
    						success:function(data){
    							str='<option ogcd="all">所有</option>';
    							if(data.code==02){
    								dialog.choicedata('获取机构失败!','maxprefer','aaa');
    								dialog.ren({'cols':cols,'wh':wh,'userdata':''});
    							}else if(data.code==01){
    								userdata=data.codeMessage;
    								for(var i in userdata){
    									str+='<option ogcd='+userdata[i].OGCD+'>'+userdata[i].OGNM+'</option>'
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
    //更改机构；
    $('.long').change(function(){
    	getlist( {'ogcd':$('.long option:selected').attr('ogcd'),'userKey':userkey,'pageNo':$('.Nopage').text(),'pageSize':10} );
    	renpage();
    });
	getlist( {'ogcd':$('.long option:selected').attr('ogcd'),'userKey':userkey,'pageNo':1,'pageSize':10} );
	renpage();
    //渲染函数；
	function getlist(obj){
		var url;
		if( product=="P004"){
			url='pere/selfavrule.do';
			cols2=cols1;
		}else if( product=='P002'){
			url='gold/selecFavrule.do';
			cols2=cols;
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
					ren({'cols':cols2,'wh':wh,'userdata':'','checked':true});
				}else if(data.code==01){
					userdata= data.codeMessage ;
					ren({'cols':cols2,'wh':wh,'userdata':userdata.list,'checked':true});
					listnumtotal=userdata.total;
					/*$('.page').remove();
    				$('.boxtop').append('<div class="page"><span class="first">首页</span><span class="prev">上一页</span><span class="next">下一页</span><span class="last">末页</span><span class="nopage">第<b class="Nopage">'+userdata.pageNum+'</b>页/共<b class="Totalpage">'+userdata.pages+'</b>页</span><span class="totalpage">共<b class="total">'+userdata.total+'</b>条记录</span></div>')
    				*/
				}else{
					ren({'cols':cols2,'wh':wh,'userdata':'','checked':true});
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
    		    		getlist({
    		    			'ogcd':$('.long option:selected').attr('ogcd'),
    		    			'userKey':userkey,
    		    			'pageNo':obj.curr,
    		    			'pageSize':$('.layui-laypage-limits select option:selected').attr('value')*1
    		    		});
    		    	}	
    		    }
    		  });
    	});
    }
	//点击初始优惠规则；
	$('.inital').click(function(){
		var ogcd=$('.long option:selected').attr('ogcd'),
			txt=$('.long option:selected').text(),
			len=$('.box tbody tr').length,url,
			data;
		if( len>1){
			dialog.choicedata(txt+'规则已经初始!','branchdiscount','aaa'); 
		}else{
			if( product=='P004'){
				url='pere/';						;////???????? 结售汇的初始化优惠规则；
			}else if( product=='P002'){	
				url='gold/insertFavrule.do';	
				data={
					'ognm':txt,
					'ogcd':ogcd,
					'userKey':userkey	
				}
			}else{
				url='inisFav.do';
				data={
					'ogcd':ogcd,
					'userKey':userkey	
				}
			}
			$.ajax({
		    	url:url,
		    	type:'post',
				async:true,
				contentType:'application/json',
				data:JSON.stringify( data ),
				success:function(data){
					if(data.code==02){
						dialog.choicedata(data.codeMessage,'branchdiscount','aaa');   							
					}else if(data.code==01){
						dialog.choicedata(data.codeMessage,'branchdiscount','aaa'); 
						getlist( {'ogcd':$('.long option:selected').attr('ogcd'),'userKey':userkey,'pageNo':$('.Nopage').text(),'pageSize':10} );				    }
						renpage();
				}
		    });
		}
	});
	//点击启用和停用、规则修改；
	$('.enable').click(function(){
		var arr=[];
		$('.box tr').each(function(i,v){
			if( $(v).find('input').prop('checked')==true){
				if( product=='P004'){
					arr.push({
						'ognm':$(v).find('td').eq(1).find('span').text(),
						'fvid':$(v).find('td').eq(1).attr('fvid'),
						'fvnm':$(v).find('td').eq(3).find('span').text(),
						'ogcd':$(v).find('td').eq(1).attr('ogcd'),
					})
				}else{
					arr.push({
						'ogcd':$(v).find('td').eq(1).find('span').text(),
						'fvid':$(v).find('td').eq(1).attr('fvid')
					})
				}
			}
		});
		if( arr.length>0){
			if( product=='P004'){
				startstopfn({'url':'pere/openChannel.do','data':arr});
			}else if( product=='P002'){
				startstopfn({'url':'gold/openFavrule.do','data':arr});
			}else{
				startstopfn({'url':'openBegin.do','data':arr});
			}
		}else{
			dialog.choicedata('请您先勾选一条数据!','branchdiscount','aaa'); 
		}
	});
	$('.disable').click(function(){
		var arr=[];
		$('.box tr').each(function(i,v){
			if( $(v).find('input').prop('checked')==true){
				if( product=='P004'){
					arr.push({
						'ognm':$(v).find('td').eq(1).find('span').text(),
						'fvid':$(v).find('td').eq(1).attr('fvid'),
						'fvnm':$(v).find('td').eq(3).find('span').text(),
						'ogcd':$(v).find('td').eq(1).attr('ogcd'),
					})
				}else{
					arr.push({
						'ogcd':$(v).find('td').eq(1).find('span').text(),
						'fvid':$(v).find('td').eq(1).attr('fvid')
					})
				}
			}
		});
		if( arr.length>0){
			if( product=='P004'){
				startstopfn({'url':'pere/closeChannel.do','data':arr});
			}else if( product=='P002'){
				startstopfn({'url':'gold/stopFavrule.do','data':arr});
			}else{
				startstopfn({'url':'stopEnd.do','data':arr});
			}
			
		}else{
			dialog.choicedata('请您先勾选一条数据!','branchdiscount','aaa'); 
		}
	});
	var numreg=/^\d+$/;
	//点击规则修改；
	$('.rulemodify').click(function(){			//P001,P002，P004；
		var url,obj3;
	  if( $('.box tbody tr').hasClass('selected') ){
		var fndatam,
			fvid=$('.box tr.selected td:eq(1)').attr('fvid'),
			rule=$('.box tr.selected td:eq(1)').attr('rule');
		if( product=="P002"){
			if( fvid=="F01"){		//渠道
				$.ajax({	
		    		url:'/fx/gold/comboxData.do',
		    		type:'post',
		    		contentType:'application/json',
		    		async:false,
		    		success:function(data){
		    			if(data.code==01){
		    				obj3=data.codeMessage;
		    			}else{
		    			}
		    		}
		    	});
			}else if(fvid=="F04"){			//客户
				$.ajax({	
		    		url:'/fx/gold/custboxData.do',
		    		type:'post',
		    		contentType:'application/json',
		    		async:false,
		    		success:function(data){
		    			if(data.code==01){
		    				obj3=data.codeMessage;
		    			}else{
		    			}
		    		}
		    	});
			}
		}else if( product=="P004"){		//结售汇的渠道；
			if( fvid=='F04'){
				$.ajax({
		    		url:'/fx/pere/initCustfavrule.do',
		    		type:'post',
		    		contentType:'application/json',
		    		async:false,
		    		success:function(data){
		    			if(data.code==01){
		    				obj3=data.codeMessage;
		    			}else{
		    			}
		    		}
		    	});
			}
		}
		  if( product=='P004'){		//个人结售汇；
			  url='pere/initfavrule.do';
			  obdt={
					fvid:fvid,
					rule:rule	  
			  }
		  }else if( product=='P002' ){
			  url='gold/onSelFavrule.do';
			  obdt={
					fvid:fvid,
					rule:rule	  
			  }
		  }else{
			  url='onSelFavrule.do';
			  obdt={
					userKey:userkey,
					fvid:fvid,
					rule:rule	  
			  }
		  }
		$.ajax({
	    	url:url,
	    	type:'post',
			async:true,
			contentType:'application/json',
			data:JSON.stringify(obdt),
			success:function(data){
				str='';
				if(data.code==01){
					if( product=='P002'){
						obj3=obj3;
						branchdiscount=data.codeMessage;
						listarr=data.codeMessage;		
					}else if( product=='P004'){
						obj3=obj3;
						branchdiscount=data.codeMessage;
						listarr=data.codeMessage;		
					}else{
						fndatam= JSON.parse( data.codeMessage ) ;
						branchdiscount=JSON.parse( data.codeMessage );
						listarr=fndatam.box2;		
						obj3=fndatam.box1;
					}
					dialog.branchdisfn('branchdiscount',fvid,obj3);	
					if( product=='P004'){
						$('.branchdiscount .branchorg',parent.document).val( $('.box tr.selected td:eq(1)').attr('ogcd') );
					}else{
						$('.branchdiscount .branchorg',parent.document).val( $('.box tr.selected td:eq(1) span').text() );
					}
					//listarr初始化为请求回来的数据；	
					if( product=='P004'){
						branchdiscount= data.codeMessage;
						listarr=branchdiscount;
						if( branchdiscount.length>10){
							for(var i=0,num=10;i<num;i++){
								str+='<tr><td>'+branchdiscount[i].myLabel+'</td><td contenteditable="true">'+branchdiscount[i].myData+'</td></tr>'
							}
						}else{
							for(var i=0,num=branchdiscount.length;i<num;i++){
								str+='<tr><td>'+branchdiscount[i].myLabel+'</td><td contenteditable="true">'+branchdiscount[i].myData+'</td></tr>'
							}
						}
					}else if( product=="P002"){
						branchdiscount= data.codeMessage;
						listarr=branchdiscount;
						if( branchdiscount.length>10){
							for(var i=0,num=10;i<num;i++){
								str+='<tr><td>'+branchdiscount[i].myLabel+'</td><td >'+branchdiscount[i].myValue+'</td><td contenteditable="true">'+branchdiscount[i].myData+'</td></tr>'
							}
						}else{
							for(var i=0,num=branchdiscount.length;i<num;i++){
								str+='<tr><td>'+branchdiscount[i].myLabel+'</td><td>'+branchdiscount[i].myValue+'</td><td contenteditable="true">'+branchdiscount[i].myData+'</td></tr>'
							}
						}
					}else{
						branchdiscount=JSON.parse( data.codeMessage ).box2;
						listarr=branchdiscount;
						if( branchdiscount.length>10){
							for(var i=0,num=10;i<num;i++){
								str+='<tr><td>'+branchdiscount[i].myLabel+'</td><td >'+branchdiscount[i].myValue+'</td><td contenteditable="true">'+branchdiscount[i].myData+'</td></tr>'
							}
						}else{
							for(var i=0,num=branchdiscount.length;i<num;i++){
								str+='<tr><td>'+branchdiscount[i].myLabel+'</td><td>'+branchdiscount[i].myValue+'</td><td contenteditable="true">'+branchdiscount[i].myData+'</td></tr>'
							}
						}
					}
					$('.branchdiscount table tbody',parent.document).html( str );
					$('.page',parent.document).html(' ');
    				$('.branchdiscount .cer',parent.document).append('<div class="page"><span class="first">首页</span><span class="prev">上一页</span><span class="next">下一页</span><span class="last">末页</span><span class="nopage">第<b class="Nopage">'+1+'</b>页/共<b class="Totalpage">'+Math.ceil(branchdiscount.length/10)+'</b>页</span><span class="totalpage">共<b class="total">'+branchdiscount.length+'</b>条记录</span></div>');
					
    				//在优惠点数进行更改，对listarr进行更改；
    				$('tbody tr td',parent.document).on('blur',function(){
    					if( product=='P004'){
    						   var  label=$('.branchdiscount table tbody tr.bgc td:eq(0)',parent.document).text(),
    								dat=$('.branchdiscount table tbody tr.bgc td:eq(1)',parent.document).text();
    						for(var i=0;i<listarr.length;i++){
    							if( listarr[i].myLabel==label  ){
    								listarr[i].myData=dat;
    							}
    						}
    					}else{
    					   var  label=$('.branchdiscount table tbody tr.bgc td:eq(0)',parent.document).text(),
    						dat=$('.branchdiscount table tbody tr.bgc td:eq(2)',parent.document).text(),
    						valu=$('.branchdiscount table tbody tr.bgc td:eq(1)',parent.document).text();
    						for(var i=0;i<listarr.length;i++){
    							if( listarr[i].myLabel==label &&listarr[i].myValue==valu){
    								listarr[i].myData=dat ;
    							}
    						}
    					}
    					console.log( listarr )
    				});
				}else{
					dialog.branchdisfn('branchdiscount',fvid,'');
				}
			}
		});	
		
		//修改值失焦；
		$('.branchdiscount .changeval',parent.document).blur(function(){
			if(  numreg.test( $(this).val() ) ){
				$(this).parent('p').find('r').remove();
			}else{
				$(this).parent('p').find('r').remove();
				$('.branchdiscount .changeval',parent.document).parent('p').append('<r>修改值必须为整数</r>');
			}
		});
		
		}else{
			dialog.choicedata('请先选择一条数据!','branchdiscount','aaa');
		}
	});
	$('body',parent.document).on('keypress','.branchdiscount .tiaojian1',function(){
		$(this).val( $(this).val().trim() );
	});
	$('body',parent.document).on('blur','.branchdiscount .tiaojian1',function(){
		var t1=$(this).val();
		if(t1==''){  
			$('.tiaojian1',parent.document).css({'border':'1px solid red'});
		}else if( t1!=''&&!numreg.test(t1)){
			$('.tiaojian1',parent.document).css({'border':'1px solid red'});
		}else{
			$('.tiaojian1',parent.document).css({'border':'1px solid #666'});
		}
	});
	
	//点击分页；
	$('body',parent.document).on('click','.branchdiscount .first',function(){//首页；
		  var str='';
		  var nopage=$('.branchdiscount .Nopage',parent.document).text(),brr=[];
		  	  
		  	  $('.branchdiscount .Nopage',parent.document).text('1');
		  	  brr=listarr.slice(0,10);
		  	  
		  if( listarr.length>0){
			  if( product=='P004'){
				  for(var i=0,num=brr.length;i<num;i++){
						str+='<tr><td>'+brr[i].myLabel+'</td><td contenteditable="true">'+brr[i].myData+'</td></tr>'
					}
			  }else{
				  for(var i=0,num=brr.length;i<num;i++){
						str+='<tr><td>'+brr[i].myLabel+'</td><td>'+brr[i].myValue+'</td><td contenteditable="true">'+brr[i].myData+'</td></tr>'
				  }
			  }
			  $('.branchdiscount table tbody',parent.document).html( str );
			  $('tbody tr td',parent.document).on('blur',function(){	
					if( product=='P004'){
						   var  label=$('.branchdiscount table tbody tr.bgc td:eq(0)',parent.document).text(),
								dat=$('.branchdiscount table tbody tr.bgc td:eq(1)',parent.document).text();
						for(var i=0;i<listarr.length;i++){
							if( listarr[i].myLabel==label  ){
								listarr[i].myData=dat;
							}
						}
					}else{
					   var  label=$('.branchdiscount table tbody tr.bgc td:eq(0)',parent.document).text(),
						dat=$('.branchdiscount table tbody tr.bgc td:eq(2)',parent.document).text(),
						valu=$('.branchdiscount table tbody tr.bgc td:eq(1)',parent.document).text();
						for(var i=0;i<listarr.length;i++){
							if( listarr[i].myLabel==label &&listarr[i].myValue==valu){
								listarr[i].myData=dat ;
							}
						}
					}
					console.log( listarr )
				});
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
	  		 if( product=='P004'){
	  			 for(var i=0,num=brr.length;i<num;i++){
						str+='<tr><td>'+brr[i].myLabel+'</td><td contenteditable="true">'+brr[i].myData+'</td></tr>'
					}
			  }else{
				  for(var i=0,num=brr.length;i<num;i++){
						str+='<tr><td>'+brr[i].myLabel+'</td><td>'+brr[i].myValue+'</td><td contenteditable="true">'+brr[i].myData+'</td></tr>'
				  }
			  }
	 		  $('.branchdiscount table tbody',parent.document).html( str );
	 		 $('tbody tr td',parent.document).on('blur',function(){	
					if( product=='P004'){
						   var  label=$('.branchdiscount table tbody tr.bgc td:eq(0)',parent.document).text(),
								dat=$('.branchdiscount table tbody tr.bgc td:eq(1)',parent.document).text();
						for(var i=0;i<listarr.length;i++){
							if( listarr[i].myLabel==label  ){
								listarr[i].myData=dat;
							}
						}
					}else{
					   var  label=$('.branchdiscount table tbody tr.bgc td:eq(0)',parent.document).text(),
						dat=$('.branchdiscount table tbody tr.bgc td:eq(2)',parent.document).text(),
						valu=$('.branchdiscount table tbody tr.bgc td:eq(1)',parent.document).text();
						for(var i=0;i<listarr.length;i++){
							if( listarr[i].myLabel==label &&listarr[i].myValue==valu){
								listarr[i].myData=dat ;
							}
						}
					}
					console.log( listarr )
				});
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
			  if( product=='P004'){
				  for(var i=0,num=brr.length;i<num;i++){
						str+='<tr><td>'+brr[i].myLabel+'</td><td contenteditable="true">'+brr[i].myData+'</td></tr>'
					}
			  }else{
				  for(var i=0,num=brr.length;i<num;i++){
						str+='<tr><td>'+brr[i].myLabel+'</td><td>'+brr[i].myValue+'</td><td contenteditable="true">'+brr[i].myData+'</td></tr>'
				  }
			  }
			  $('.branchdiscount table tbody',parent.document).html( str );
			  
			  $('tbody tr td',parent.document).on('blur',function(){	
					if( product=='P004'){
						   var  label=$('.branchdiscount table tbody tr.bgc td:eq(0)',parent.document).text(),
								dat=$('.branchdiscount table tbody tr.bgc td:eq(1)',parent.document).text();
						for(var i=0;i<listarr.length;i++){
							if( listarr[i].myLabel==label  ){
								listarr[i].myData=dat;
							}
						}
					}else{
					   var  label=$('.branchdiscount table tbody tr.bgc td:eq(0)',parent.document).text(),
						dat=$('.branchdiscount table tbody tr.bgc td:eq(2)',parent.document).text(),
						valu=$('.branchdiscount table tbody tr.bgc td:eq(1)',parent.document).text();
						for(var i=0;i<listarr.length;i++){
							if( listarr[i].myLabel==label &&listarr[i].myValue==valu){
								listarr[i].myData=dat ;
							}
						}
					}
					console.log( listarr )
				});
		  }
	  });
	  $('body',parent.document).on('click','.branchdiscount .last',function(){//末页；
		  var str='';
		  var nopage=$('.branchdiscount .Nopage',parent.document).text(),brr=[],
		  	  Totalpage=$('.Totalpage',parent.document).text();
		  
		  brr=listarr.slice( (Totalpage-1)*10,10*Totalpage );
		  $('.branchdiscount .Nopage',parent.document).text( Totalpage );
		  
		  if( listarr.length>0){
			  if( product=='P004'){
				  for(var i=0,num=brr.length;i<num;i++){
						str+='<tr><td>'+brr[i].myLabel+'</td><td contenteditable="true">'+brr[i].myData+'</td></tr>'
					}
			  }else{
				  for(var i=0,num=brr.length;i<num;i++){
						str+='<tr><td>'+brr[i].myLabel+'</td><td>'+brr[i].myValue+'</td><td contenteditable="true">'+brr[i].myData+'</td></tr>'
				  }
			  }
			  $('.branchdiscount table tbody',parent.document).html( str );
			  
			  $('tbody tr td',parent.document).on('blur',function(){	
					if( product=='P004'){
						   var  label=$('.branchdiscount table tbody tr.bgc td:eq(0)',parent.document).text(),
								dat=$('.branchdiscount table tbody tr.bgc td:eq(1)',parent.document).text();
						for(var i=0;i<listarr.length;i++){
							if( listarr[i].myLabel==label  ){
								listarr[i].myData=dat;
							}
						}
					}else{
					   var  label=$('.branchdiscount table tbody tr.bgc td:eq(0)',parent.document).text(),
						dat=$('.branchdiscount table tbody tr.bgc td:eq(2)',parent.document).text(),
						valu=$('.branchdiscount table tbody tr.bgc td:eq(1)',parent.document).text();
						for(var i=0;i<listarr.length;i++){
							if( listarr[i].myLabel==label &&listarr[i].myValue==valu){
								listarr[i].myData=dat ;
							}
						}
					}
					console.log( listarr )
				});
		  }
	  });
	//点击添加 ；
	$('body',parent.document).on('click','.branchdiscount .add',function(){			//可以在点击保存的时候再最后获取，但是因为有分页，所以只能在添加和修改的时候手动增减listarr；
		if( !$('.add',parent.document).hasClass('dissab') ){
		var fvid=$('.box tr.selected td:eq(1)').attr('fvid'),arr=[],
			changeval=$('.branchdiscount .changeval',parent.document).val();
		$('.branchdiscount tbody tr',parent.document).each(function(i,v){
			if(arr.indexOf($(v).find('td').eq(0).text())==-1){
				arr.push( $(v).find('td').eq(0).text() );
			}
		});
		if( fvid=='F01'||fvid=='F04'){
			var optiontxt=$('.branchdiscount .condition option:selected',parent.document).text(),
				cuty=$('.branchdiscount .condition option:selected',parent.document).attr('cuty');
			if(arr.indexOf(optiontxt)!=-1){
				dialog.choicedata('该条件已经存在,请直接修改值!','branchdiscount','aaa'); 
			}else{
				if( numreg.test(changeval) ){
					if( product=='P004'){
						$('.branchdiscount table tbody',parent.document).append('<tr><td>'+optiontxt+'</td><td contenteditable="true">'+changeval+'</td></tr>');
						listarr.push({
							myLabel:optiontxt,
							myData:changeval
						});
					}else{
						$('.branchdiscount table tbody',parent.document).append('<tr><td>'+optiontxt+'</td><td>'+cuty+'</td><td contenteditable="true">'+changeval+'</td></tr>');
						listarr.push({
							myLabel:optiontxt,
							myData:changeval,
							myValue:cuty
						});
					}
						$('tbody tr td',parent.document).on('blur',function(){	
							if( product=='P004'){
								   var  label=$('.branchdiscount table tbody tr.bgc td:eq(0)',parent.document).text(),
										dat=$('.branchdiscount table tbody tr.bgc td:eq(1)',parent.document).text();
								for(var i=0;i<listarr.length;i++){
									if( listarr[i].myLabel==label  ){
										listarr[i].myData=dat;
									}
								}
							}else{
							   var  label=$('.branchdiscount table tbody tr.bgc td:eq(0)',parent.document).text(),
								dat=$('.branchdiscount table tbody tr.bgc td:eq(2)',parent.document).text(),
								valu=$('.branchdiscount table tbody tr.bgc td:eq(1)',parent.document).text();
								for(var i=0;i<listarr.length;i++){
									if( listarr[i].myLabel==label &&listarr[i].myValue==valu){
										listarr[i].myData=dat ;
									}
								}
							}
							console.log( listarr )
						});
				}else{
					$('.branchdiscount .changeval',parent.document).parent('p').find('r').remove();
					$('.branchdiscount .changeval',parent.document).parent('p').append('<r>修改值必须为整数</r>');
				}
			}
		}else if(fvid=='F03'){
			var t1=$('.branchdiscount .tiaojian1',parent.document).val(),
				t2=$('.branchdiscount .tiaojian2',parent.document).val(),tt,
				wronnum=0;
			if(t1==''){  
				$('.tiaojian1',parent.document).css({'border':'1px solid red'});
				wronnum++;
			}else if( t1!=''&&!numreg.test(t1)){
				$('.tiaojian1',parent.document).css({'border':'1px solid red'});
				wronnum++;
			}else{
				$('.tiaojian1',parent.document).css({'border':'1px solid #666'});
			}
			if(t2==''){
				t2='∞';
				$('.tiaojian2',parent.document).css({'border':'1px solid #666'});
			}else if( t2!=''&&!numreg.test(t2) ){
				$('.tiaojian2',parent.document).css({'border':'1px solid red'});
				wronnum++;
			}else{
				$('.tiaojian2',parent.document).css({'border':'1px solid #666'});
			}
			if( wronnum==0 ){
				tt=t1+','+t2;
				if(arr.indexOf(tt)!=-1){
					dialog.choicedata('该条件已经存在,请直接修改值!','branchdiscount','aaa'); 
				}else{
					str='';
					if( numreg.test(changeval) ){
						listarr.unshift({"myData":changeval,"myLabel":tt,"myValue":tt});
						if( listarr.length>10){
							if( product=='P004'){
								for(var i=0,num=10;i<num;i++){
									str+='<tr><td>'+listarr[i].myLabel+'</td><td contenteditable="true">'+listarr[i].myData+'</td></tr>'
								}
								
							}else{
								for(var i=0,num=10;i<num;i++){
									str+='<tr><td>'+listarr[i].myLabel+'</td><td contenteditable="true">'+listarr[i].myValue+'</td><td contenteditable="true">'+listarr[i].myData+'</td></tr>'
								}
							}
						}else{
							if( product=='P004'){
								for(var i=0,num=listarr.length;i<num;i++){
									str+='<tr><td>'+listarr[i].myLabel+'</td><td contenteditable="true">'+listarr[i].myData+'</td></tr>'
								}
								
							}else{
								for(var i=0,num=listarr.length;i<num;i++){
									str+='<tr><td>'+listarr[i].myLabel+'</td><td contenteditable="true">'+listarr[i].myValue+'</td><td contenteditable="true">'+listarr[i].myData+'</td></tr>'
								}
							}
						}
						$('.branchdiscount table tbody',parent.document).html( str );
						$('.page',parent.document).html(' ');
	    				$('.branchdiscount .cer',parent.document).append('<div class="page"><span class="first">首页</span><span class="prev">上一页</span><span class="next">下一页</span><span class="last">末页</span><span class="nopage">第<b class="Nopage">'+1+'</b>页/共<b class="Totalpage">'+Math.ceil(listarr.length/10)+'</b>页</span><span class="totalpage">共<b class="total">'+listarr.length+'</b>条记录</span></div>');
	    				$('tbody tr td',parent.document).on('blur',function(){	
		    					if( product=='P004'){
		    						   var  label=$('.branchdiscount table tbody tr.bgc td:eq(0)',parent.document).text(),
		    								dat=$('.branchdiscount table tbody tr.bgc td:eq(1)',parent.document).text();
		    						for(var i=0;i<listarr.length;i++){
		    							if( listarr[i].myLabel==label  ){
		    								listarr[i].myData=dat;
		    							}
		    						}
		    					}else{
		    					   var  label=$('.branchdiscount table tbody tr.bgc td:eq(0)',parent.document).text(),
		    						dat=$('.branchdiscount table tbody tr.bgc td:eq(2)',parent.document).text(),
		    						valu=$('.branchdiscount table tbody tr.bgc td:eq(1)',parent.document).text();
		    						for(var i=0;i<listarr.length;i++){
		    							if( listarr[i].myLabel==label &&listarr[i].myValue==valu){
		    								listarr[i].myData=dat ;
		    							}
		    						}
		    					}
		    					console.log( listarr )
		    				});
					}else{
						$('.branchdiscount .changeval',parent.document).parent('p').find('r').remove();
						$('.branchdiscount .changeval',parent.document).parent('p').append('<r>修改值必须为整数</r>');
					}
				}
			}			
		}
		
		}else{
			return ;
		}
	});
	//在td-优惠点数进行更改，再进行
	$('body',parent.document).on('hover','.branchdiscount tbody tr',function(){
		$(this).addClass('bgcd');
		$(this).siblings().removeClass('bgcd');
	});
	$('body',parent.document).on('click','.branchdiscount tbody tr',function(){
		$(this).addClass('bgc');
		$(this).siblings().removeClass('bgc');
	});
	//点击删除；
	$('body',parent.document).on('click','.branchdiscount .remove',function(){
		if( !$('.remove',parent.document).hasClass('dissab') ){
			if(  $('.branchdiscount table tbody tr',parent.document).hasClass('bgc') ){
				dialog.cancelDate('您确定要删除当前记录吗？','branchdiscount','dia_delete');
			}else{
				dialog.choicedata('请先选择一条数据!','branchdiscount','aaa'); 
			}
		}else{
			return;
		}
		
	});
	//点击保存;
	$('body',parent.document).on('click','.branchdiscount .succss',function(){
		var fvid=$('.box tr.selected td:eq(1)').attr('fvid');
		
		var numtime=0,a,
			fvid=$('.box tr.selected td:eq(1)').attr('fvid'),ogcd,	
			arr=[],wrongval,url,fvdat;
		for(var i=0,num=listarr.length;i<num;i++){
			if( !numreg.test( listarr[i].myData ) ){
				numtime++;
				wrongval=listarr[i].myData;
				return ;
			}else{
				arr.push({
					'myData':listarr[i].myData,
					'myLabel':listarr[i].myLabel,
					'myValue':listarr[i].myValue
				});
			}
		}
		if( product=='P004'){
			url='pere/savefavrule.do';
			ogcd=$('.box tr.selected td:eq(1)').attr('ogcd');
			fvdat={
				'fvid':fvid,
				'ogcd':ogcd,
				'favourList':arr,
				'userKey':userkey
			}
		}else if( product=='P002' ){
			url='gold/saveFavruleRule.do';
			ogcd=$('.box tr.selected td:eq(1) span').text();
			fvdat={
				'fvid':fvid,
				'ogcd':ogcd,
				'favourList':arr,
				'userKey':userkey
			}
		}else{
			url='onSaveFavrule.do';
			ogcd=$('.box tr.selected td:eq(1) span').text();
			fvdat={
				'fvid':fvid,
				'comaogcd':ogcd,
				'favourList':arr,
				'userKey':userkey
			}
		}
		if(numtime==0){
			$.ajax({
		    	url:url,
		    	type:'post',
				async:true,
				contentType:'application/json',
				data:JSON.stringify(fvdat),
				success:function(data){
					$('.branchdiscount .succss',parent.document).closest('.zhezhao').remove();
					if(data.code==01){
						dialog.choicedata(data.codeMessage,'branchdiscount','aaa'); 
						getlist( {'ogcd':$('.long option:selected').attr('ogcd'),'userKey':userkey,'pageNo':$('.Nopage').text(),'pageSize':10} );					
						renpage();
					}else{
						dialog.choicedata(data.codeMessage,'branchdiscount','aaa'); 
					}
				}
			});	
		}else{
			dialog.choicedata('"'+wrongval+'"'+'不符合要求!','branchdiscount','aaa'); 
		}
	});
	////0------------------------
	$('body',parent.document).on('click','.branchdiscount .close,.branchdiscount .cance',function(){
		$(this).closest('.zhezhao').remove();
	});
	//点击确认删除框;
	$('body',parent.document).on('click','.branchdiscount .confirm',function(){		//~????????
		var str='';
		$('.branchdiscount .confirm',parent.document).closest('.zhezhao').remove();
		if( product=='P004'){
			   var  label=$('.branchdiscount table tbody tr.bgc td:eq(0)',parent.document).text(),
					dat=$('.branchdiscount table tbody tr.bgc td:eq(1)',parent.document).text();
			for(var i=0;i<listarr.length;i++){
				
				if( listarr[i].myLabel==label  &&listarr[i].myData==dat){
					listarr.splice(i,1 );
					$('.branchdiscount table tbody tr.bgc',parent.document).remove();
				}
			}
			if( listarr.length>10){
				for(var i=0,num=10;i<num;i++){
					str+='<tr><td>'+listarr[i].myLabel+'</td><td>'+listarr[i].myData+'</td></tr>'
				}
			}else{
				for(var i=0,num=listarr.length;i<num;i++){
					str+='<tr><td>'+listarr[i].myLabel+'</td><td>'+listarr[i].myData+'</td></tr>'
				}
			}
		}else{
		   var  label=$('.branchdiscount table tbody tr.bgc td:eq(0)',parent.document).text(),
			dat=$('.branchdiscount table tbody tr.bgc td:eq(2)',parent.document).text(),
			valu=$('.branchdiscount table tbody tr.bgc td:eq(1)',parent.document).text();
			
			for(var i=0;i<listarr.length;i++){
				if( listarr[i].myLabel==label && listarr[i].myData==dat &&listarr[i].myValue==valu){
					listarr.splice(i,1 );
					$('.branchdiscount table tbody tr.bgc',parent.document).remove();
				}
			}
			if( listarr.length>10){
				for(var i=0,num=10;i<num;i++){
					str+='<tr><td>'+listarr[i].myLabel+'</td><td>'+listarr[i].myValue+'</td><td>'+listarr[i].myData+'</td></tr>'
				}
			}else{
				for(var i=0,num=listarr.length;i<num;i++){
					str+='<tr><td>'+listarr[i].myLabel+'</td><td>'+listarr[i].myValue+'</td><td>'+listarr[i].myData+'</td></tr>'
				}
			}
		}
		$('.branchdiscount table tbody',parent.document).html( str );
		$('.page',parent.document).html(' ');
		$('.branchdiscount .cer',parent.document).append('<div class="page"><span class="first">首页</span><span class="prev">上一页</span><span class="next">下一页</span><span class="last">末页</span><span class="nopage">第<b class="Nopage">'+1+'</b>页/共<b class="Totalpage">'+Math.ceil(listarr.length/10)+'</b>页</span><span class="totalpage">共<b class="total">'+listarr.length+'</b>条记录</span></div>');
	});
	$('body',parent.document).on('click','.branchdiscount .cancel',function(){
		$(this).closest('.zhezhao').remove();
	});
	function startstopfn(obj){
		if( product=='P004'){
			$.ajax({
		    	url:obj.url,
		    	type:'post',
				async:true,
				contentType:'application/json',
				data:JSON.stringify({'userKey':userkey,chlist:obj.data}),
				success:function(data){
					if(data.code==02){
						dialog.choicedata(data.codeMessage,'branchdiscount','aaa');   							
					}else if(data.code==01){
						dialog.choicedata(data.codeMessage,'branchdiscount','aaa'); 
						getlist( {'ogcd':$('.long option:selected').attr('ogcd'),'userKey':userkey,'pageNo':$('.Nopage').text(),'pageSize':10} );				
						renpage();					
					}
				}
		    });
		}else{
			$.ajax({
		    	url:obj.url,
		    	type:'post',
				async:true,
				contentType:'application/json',
				data:JSON.stringify({'userKey':userkey,favruleList:obj.data}),
				success:function(data){
					if(data.code==02){
						dialog.choicedata(data.codeMessage,'branchdiscount','aaa');   							
					}else if(data.code==01){
						dialog.choicedata(data.codeMessage,'branchdiscount','aaa'); 
						getlist( {'ogcd':$('.long option:selected').attr('ogcd'),'userKey':userkey,'pageNo':$('.Nopage').text(),'pageSize':10} );				
						renpage();		
					}
				}
		    });
		}
	}
	$('body',parent.document).on('click','.dealparamter .sure',function(){
		var tit=$('.dealparamter',parent.document).data('tit');
		if( tit=='aaa'){
			$(this).closest('.zhezhao').remove();
		}else{
			$('.zhezhao',parent.document).remove();
		}
	});
	$('body',parent.document).on('click','.branchdiscount .sure',function(){
		var tit=$('.branchdiscount .sure',parent.document).closest('.branchdiscount').data('tit');
		if( tit=='aaa'){
			$(this).closest('.zhezhao').remove();
		}else{
			$('.zhezhao',parent.document).remove();
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
		 if( obj.userdata==''){
			 
		 }else{
			 mmg.on('loadSuccess',function(e,data){
				 $('.box').find('tbody tr').each(function(i,v){
					 $(v).find('td').eq('1').attr('fvid',obj.userdata[i].fvid);
					 $(v).find('td').eq('1').attr('rule',obj.userdata[i].rule);
					 $(v).find('td').eq('1').attr('ogcd',obj.userdata[i].ogcd);
				 });
			 });
		 }
	}
	//点击本页的分页；
	//点击分页;
    /*$('.boxtop').on('click','.first',function(){
    	var Nopage=$('.Nopage').text()*1;
 	    if(Nopage>1){
 	    	Nopage=1;
 	    	getlist( {'ogcd':$('.long option:selected').attr('ogcd'),'userKey':userkey,'pageNo':Nopage,'pageSize':10} );
 	    }
    });
	$('.boxtop').on('click','.prev',function(){
	    var Nopage=$('.Nopage').text()*1;
	    if(Nopage>1){
	    	Nopage=Nopage-1;
	    	getlist( {'ogcd':$('.long option:selected').attr('ogcd'),'userKey':userkey,'pageNo':Nopage,'pageSize':10} );
	    }
	});
	$('.boxtop').on('click','.next',function(){
		var Nopage=$('.Nopage').text()*1,
			Totalpage=$('.Totalpage').text()*1;
	    if(Nopage<Totalpage){
	    	Nopage=Nopage*1+1;
	    	getlist( {'ogcd':$('.long option:selected').attr('ogcd'),'userKey':userkey,'pageNo':Nopage,'pageSize':10} );
	    }
	});
	$('.boxtop').on('click','.last',function(){
		var Nopage=$('.Nopage').text()*1,
		Totalpage=$('.Totalpage').text()*1;
	    if(Nopage<Totalpage){
	    	Nopage=Totalpage;
	    	getlist( {'ogcd':$('.long option:selected').attr('ogcd'),'userKey':userkey,'pageNo':Nopage,'pageSize':10} );
	    }
	});*/
	/*----------------快速搜索功能的实现------------------------*/
	$('.info_serbtn').click(function(){
		 dialog.serchData($('.info_seript').val(),'.mmg-headWrapper');
	});
});

