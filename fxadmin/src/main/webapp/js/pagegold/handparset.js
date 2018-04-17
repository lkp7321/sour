/*	积存金-分行优惠设置
 * handparset*/
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
	$('.boxtop').css('height',wh);
	$('#page').css('width',ww);
	var userkey=JSON.parse( sessionStorage.getItem('menu') ).userKey,listarr=[];
	var numreg=/^\d+$/;
	//列参数
    var cols = [
        { title:'机构号', name:'ogcd' ,width:100, align:'left'},
        { title:'机构名称', name:'ognm' ,width:80, align:'left' },
        { title:'优惠名称', name:'fvnm' ,width:150, align:'left'},
        { title:'使用标志', name:'stat' ,width:80, align:'center'}
    ];
    var juesedata=false,jigoudata=false,jueseda,jigouda,listnumtotal;
    getlo();
    renpage();
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
    				}else if(data.codeMessage.leve==''||!data.codeMessage.leve){
    					dialog.choicedata('判断用户所属机构等级失败!','maxprefer');
    					$('.maxpreferential .cer').hide();
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
    								$('.outname select').html( str );
    							}
    						}
    				    });
    				}
    			}
    		}
        });
    }
    getlist({
    	'pageNo':1,
    	'pageSize':10,
    	userKey:userkey,
    	ogcd:$('.outname select option:selected').attr('ogcd')
    });
    $('.outname select').change(function(){
    	getlist({
        	'pageNo':1,
        	'pageSize':10,
        	userKey:userkey,
        	ogcd:$('.outname select option:selected').attr('ogcd')
        });
    	 renpage();
    });
    function getlist(obj){
    	 $.ajax({
 			url:'/fx/accum/selecFavrule.do',
 			type:'post',
 			contentType:'application/json',
 			data:JSON.stringify( obj ),
 			async:false,
 			success:function(data){
 				if(data.code==00){
 					ren({'cols':cols,'wh':wh,'userdata':'','checked':true});
 				}else if(data.code==01){
 					userdata= data.codeMessage ;
 					ren({'cols':cols,'wh':wh,'userdata':userdata.list,'checked':true});
 					listnumtotal=userdata.total;
// 					$('.page').remove();
//     				$('.boxtop').append('<div class="page"><span class="first">首页</span><span class="prev">上一页</span><span class="next">下一页</span><span class="last">末页</span><span class="nopage">第<b class="Nopage">'+userdata.pageNum+'</b>页/共<b class="Totalpage">'+userdata.pages+'</b>页</span><span class="totalpage">共<b class="total">'+userdata.total+'</b>条记录</span></div>')
 				}else{
 					ren({'cols':cols,'wh':wh,'userdata':'','checked':true});
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
			    		getlist(
			    				{
			    					'pageSize':$('.layui-laypage-limits select option:selected').attr('value')*1,
			    				    'pageNo':obj.curr,
			    		        	'userKey':userkey,
			    		        	'ogcd':$('.outname select option:selected').attr('ogcd')
			    		        }
			    		);
			    	}	
			    }
			  });
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
		 $(".mmg-bodyWrapper").niceScroll({
				touchbehavior:false,
				cursorcolor:"#666",
				cursoropacitymax:0.7,
				cursorwidth:6,
				background:"#ccc",
				autohidemode:false,
				horizrailenabled:obj.horizrailenabled
		  });
		 if( obj.userdata==''||!obj.userdata ){
			 
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
	//启用;
	$('.startuse').click(function(){
			//ajax请求;
			var arr=[];
			$('.box tr').each(function(i,v){
				if( $(v).find('input').prop('checked')==true){
					arr.push({
						'ogcd':$(v).find('td').eq(1).find('span').text(),
						'fvid':$(v).find('td').eq(1).attr('fvid'),
						'fvnm':$(v).find('td').eq(3).find('span').text()
					});
				}
			});
			if( arr.length>0){
				startstopfn({'url':'accum/openFavrule.do','data':arr});
			}else{
				dialog.choicedata('请您先勾选一条数据!','handparset','aaa'); 
			}
	});
	//停用；
	$('.stopuse').click(function(){
			var arr=[];
			$('.box tr').each(function(i,v){
				if( $(v).find('input').prop('checked')==true){
					arr.push({
						'ogcd':$(v).find('td').eq(1).find('span').text(),
						'fvid':$(v).find('td').eq(1).attr('fvid'),
						'fvnm':$(v).find('td').eq(3).find('span').text()
					})
				}
			});
			if( arr.length>0){
				startstopfn({'url':'accum/stopFavrule.do','data':arr});
			}else{
				dialog.choicedata('请您先勾选一条数据!','handparset','aaa'); 
			}

	});
	//初始化优惠规则；
	$('.adddisrules').click(function(){
		var ogcd=$('.outname select option:selected').attr('ogcd'),
			ognm=$('.outname select option:selected').text(),
		txt=$('.outname select option:selected').text(),
		len=$('.box tbody tr').length;
		if( len>1){
			dialog.choicedata(txt+'规则已经初始!','handparset','aaa'); 
		}else{
			$.ajax({
		    	url:'/fx/accum/insertFavrule.do',
		    	type:'post',
				async:true,
				contentType:'application/json',
				data:JSON.stringify({
					'ogcd':ogcd,
					'ognm':ognm,
					'userKey':userkey
				}),
				success:function(data){
					if(data.code==02){
						dialog.choicedata(data.codeMessage,'handparset','aaa');   							
					}else if(data.code==01){
						dialog.choicedata(data.codeMessage,'handparset','aaa'); 
						getlist( {'ogcd':$('.outname select option:selected').attr('ogcd'),'userKey':userkey,'pageNo':$('.Nopage').text(),'pageSize':10} );				   
					}
				}
		    });
		}
	});	
	//规则修改;
	$('.rulechange').click(function(){
		if( $('.box tr.selected td:eq(3) span').text()!='临时优惠'&& $('.box tr.selected td:eq(3) span').text()!='最大优惠'){
			var url;
			  if( $('.box tbody tr').hasClass('selected') ){
				var fndatam,
					fvid=$('.box tr.selected td:eq(1)').attr('fvid'),
					rule=$('.box tr.selected td:eq(1)').attr('rule');
				  obdt={
						userKey:userkey,
						fvid:fvid,
						rule:rule	  
				  }
				  if( fvid=="F01"){		//渠道
					$.ajax({	
			    		url:'/fx/accum/comboxData.do',
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
			    		url:'/fx/accum/custboxData.do',
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
				$.ajax({
			    	url:'/fx/accum/onSelFavrule.do',
			    	type:'post',
					async:true,
					contentType:'application/json',
					data:JSON.stringify(obdt),
					success:function(data){
						str='';
						if(data.code==01){
							if( fvid=='F01' ||fvid=="F04"){
								obj3=obj3;
								handparset= data.codeMessage;
								listarr=handparset;
							}else{
								handparset= data.codeMessage;
								listarr=handparset;		
								obj3='';
							}
							dialog.branchdisfn('branchdiscount',fvid,obj3 );
							$('.branchdiscount .branchorg',parent.document).val( $('.box tr.selected td:eq(1) span').text() );
			
							if( handparset.length>10){
								for(var i=0,num=10;i<num;i++){
									str+='<tr><td>'+handparset[i].myLabel+'</td><td >'+handparset[i].myValue+'</td><td contenteditable="true">'+handparset[i].myData+'</td></tr>'
								}
							}else{
								for(var i=0,num=handparset.length;i<num;i++){
									str+='<tr><td>'+handparset[i].myLabel+'</td><td>'+handparset[i].myValue+'</td><td contenteditable="true">'+handparset[i].myData+'</td></tr>'
								}
							}
							$('.branchdiscount table tbody',parent.document).html( str );
							$('.page',parent.document).html(' ');
		    				$('.branchdiscount .cer',parent.document).append('<div class="page"><span class="first">首页</span><span class="prev">上一页</span><span class="next">下一页</span><span class="last">末页</span><span class="nopage">第<b class="Nopage">'+1+'</b>页/共<b class="Totalpage">'+Math.ceil(handparset.length/10)+'</b>页</span><span class="totalpage">共<b class="total">'+handparset.length+'</b>条记录</span></div>');
							
		    				//在优惠点数进行更改，对listarr进行更改；
		    				$('tbody tr td',parent.document).on('blur',function(){
		    					   var  label=$('.branchdiscount table tbody tr.bgc td:eq(0)',parent.document).text(),
		    						dat=$('.branchdiscount table tbody tr.bgc td:eq(2)',parent.document).text(),
		    						valu=$('.branchdiscount table tbody tr.bgc td:eq(1)',parent.document).text();
		    						for(var i=0;i<listarr.length;i++){
		    							if( listarr[i].myLabel==label &&listarr[i].myValue==valu){
		    								listarr[i].myData=dat ;
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
					dialog.choicedata('请先选择一条数据!','handparset');
				}
		}
	});
	//点击添加 ；
	$('body',parent.document).on('click','.branchdiscount .add',function(){
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
					$('.branchdiscount table tbody',parent.document).append('<tr><td>'+optiontxt+'</td><td>'+cuty+'</td><td contenteditable="true">'+changeval+'</td></tr>');
					listarr.push({
						myLabel:optiontxt,
						myData:changeval,
						myValue:cuty
					});
					$('tbody tr td',parent.document).on('blur',function(){	
						var  label=$('.branchdiscount table tbody tr.bgc td:eq(0)',parent.document).text(),
						dat=$('.branchdiscount table tbody tr.bgc td:eq(2)',parent.document).text(),
						valu=$('.branchdiscount table tbody tr.bgc td:eq(1)',parent.document).text();
						for(var i=0;i<listarr.length;i++){
							if( listarr[i].myLabel==label &&listarr[i].myValue==valu){
								listarr[i].myData=dat ;
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
							for(var i=0,num=10;i<num;i++){
								str+='<tr><td>'+listarr[i].myLabel+'</td><td contenteditable="true">'+listarr[i].myValue+'</td><td contenteditable="true">'+listarr[i].myData+'</td></tr>'
							}
						}else{
							for(var i=0,num=listarr.length;i<num;i++){
								str+='<tr><td>'+listarr[i].myLabel+'</td><td contenteditable="true">'+listarr[i].myValue+'</td><td contenteditable="true">'+listarr[i].myData+'</td></tr>'
							}
						}
						$('.branchdiscount table tbody',parent.document).html( str );
						$('.page',parent.document).html(' ');
	    				$('.branchdiscount .cer',parent.document).append('<div class="page"><span class="first">首页</span><span class="prev">上一页</span><span class="next">下一页</span><span class="last">末页</span><span class="nopage">第<b class="Nopage">'+1+'</b>页/共<b class="Totalpage">'+Math.ceil(listarr.length/10)+'</b>页</span><span class="totalpage">共<b class="total">'+listarr.length+'</b>条记录</span></div>');
	    				$('tbody tr td',parent.document).on('blur',function(){	
	    					 var  label=$('.branchdiscount table tbody tr.bgc td:eq(0)',parent.document).text(),
								dat=$('.branchdiscount table tbody tr.bgc td:eq(2)',parent.document).text(),
								valu=$('.branchdiscount table tbody tr.bgc td:eq(1)',parent.document).text();
								for(var i=0;i<listarr.length;i++){
									if( listarr[i].myLabel==label &&listarr[i].myValue==valu){
										listarr[i].myData=dat ;
									}
								}
						});
					}else{
						$('.branchdiscount .changeval',parent.document).parent('p').find('r').remove();
						$('.branchdiscount .changeval',parent.document).parent('p').append('<r>修改值必须为整数</r>');
					}
				}
			}			
		}
	  }else{
		  return;
	  }
	});
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
		var numtime=0,a,
			fvid=$('.box tr.selected td:eq(1)').attr('fvid'),
			arr=[],wrongval,url,fvdat;
		console.log( listarr );
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
			ogcd=$('.branchorg',parent.document).val();
			fvdat={
				'fvid':fvid,
				'ogcd':ogcd,
				'favourList':arr,
				'userKey':userkey
			}
		if(numtime==0){
			$.ajax({
		    	url:'/fx/accum/saveFavruleRule.do',
		    	type:'post',
				async:true,
				contentType:'application/json',
				data:JSON.stringify(fvdat),
				success:function(data){
					$('.branchdiscount .succss',parent.document).closest('.zhezhao').remove();
					if(data.code==01){
						dialog.choicedata(data.codeMessage,'branchdiscount','aaa'); 
						getlist( {'ogcd':$('.outname select option:selected').attr('ogcd'),'userKey':userkey,'pageNo':$('.Nopage').text(),'pageSize':10} );				
					}else{
						dialog.choicedata(data.codeMessage,'branchdiscount','aaa'); 
					}
				}
			});	
		}else{
			dialog.choicedata('"'+wrongval+'"'+'不符合要求!','branchdiscount','aaa'); 
		}
	});
	//点击确认删除框;
	$('body',parent.document).on('click','.branchdiscount .confirm',function(){		//~????????
		var str='';
		$(this).closest('.zhezhao').remove();
		 var  label=$('.branchdiscount table tbody tr.bgc td:eq(0)',parent.document).text(),
			dat=$('.branchdiscount table tbody tr.bgc td:eq(2)',parent.document).text(),
			valu=$('.branchdiscount table tbody tr.bgc td:eq(1)',parent.document).text();
			
			for(var i=0;i<listarr.length;i++){
				if( listarr[i].myLabel==label && listarr[i].myData==dat &&listarr[i].myValue==valu){
					listarr.shift( listarr[i] );
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
		$('.branchdiscount table tbody',parent.document).html( str );
		$('.page',parent.document).html(' ');
		$('.branchdiscount .cer',parent.document).append('<div class="page"><span class="first">首页</span><span class="prev">上一页</span><span class="next">下一页</span><span class="last">末页</span><span class="nopage">第<b class="Nopage">'+1+'</b>页/共<b class="Totalpage">'+Math.ceil(listarr.length/10)+'</b>页</span><span class="totalpage">共<b class="total">'+listarr.length+'</b>条记录</span></div>');
	});
	$('body',parent.document).on('click','.handparset .sure,.branchdiscount .close,.propasav,.branchdiscount .sure,.branchdiscount .cancel',function(){
		$(this).closest('.zhezhao').remove();
	});
	//点击内容分页；
	$('body',parent.document).on('click','.branchdiscount .first',function(){//首页；
		  var str='';
		  var nopage=$('.branchdiscount .Nopage',parent.document).text(),brr=[];
		  	  
		  	  $('.branchdiscount .Nopage',parent.document).text('1');
		  	  brr=listarr.slice(0,10);
		  	  
		  if( listarr.length>0){
			  for(var i=0,num=brr.length;i<num;i++){
					str+='<tr><td>'+brr[i].myLabel+'</td><td>'+brr[i].myValue+'</td><td contenteditable="true">'+brr[i].myData+'</td></tr>'
			  }
			  $('.branchdiscount table tbody',parent.document).html( str );
			  $('tbody tr td',parent.document).on('blur',function(){	
				  var  label=$('.branchdiscount table tbody tr.bgc td:eq(0)',parent.document).text(),
					dat=$('.branchdiscount table tbody tr.bgc td:eq(2)',parent.document).text(),
					valu=$('.branchdiscount table tbody tr.bgc td:eq(1)',parent.document).text();
					for(var i=0;i<listarr.length;i++){
						if( listarr[i].myLabel==label &&listarr[i].myValue==valu){
							listarr[i].myData=dat ;
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
	  		 for(var i=0,num=brr.length;i<num;i++){
					str+='<tr><td>'+brr[i].myLabel+'</td><td>'+brr[i].myValue+'</td><td contenteditable="true">'+brr[i].myData+'</td></tr>'
			  }
	 		  $('.branchdiscount table tbody',parent.document).html( str );
	 		 $('tbody tr td',parent.document).on('blur',function(){	
	 			var  label=$('.branchdiscount table tbody tr.bgc td:eq(0)',parent.document).text(),
				dat=$('.branchdiscount table tbody tr.bgc td:eq(2)',parent.document).text(),
				valu=$('.branchdiscount table tbody tr.bgc td:eq(1)',parent.document).text();
				for(var i=0;i<listarr.length;i++){
					if( listarr[i].myLabel==label &&listarr[i].myValue==valu){
						listarr[i].myData=dat ;
					}
				}
					console.log( listarr )
				});
	 	  }
	  });
	  $('body',parent.document).on('click','.branchdiscount .next',function(){//下一页；1-2
		  console.log( 123 )
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
			  for(var i=0,num=brr.length;i<num;i++){
					str+='<tr><td>'+brr[i].myLabel+'</td><td>'+brr[i].myValue+'</td><td contenteditable="true">'+brr[i].myData+'</td></tr>'
			  }
			  $('.branchdiscount table tbody',parent.document).html( str );
			  $('tbody tr td',parent.document).on('blur',function(){	
				  var  label=$('.branchdiscount table tbody tr.bgc td:eq(0)',parent.document).text(),
					dat=$('.branchdiscount table tbody tr.bgc td:eq(2)',parent.document).text(),
					valu=$('.branchdiscount table tbody tr.bgc td:eq(1)',parent.document).text();
					for(var i=0;i<listarr.length;i++){
						if( listarr[i].myLabel==label &&listarr[i].myValue==valu){
							listarr[i].myData=dat ;
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
			  for(var i=0,num=brr.length;i<num;i++){
					str+='<tr><td>'+brr[i].myLabel+'</td><td>'+brr[i].myValue+'</td><td contenteditable="true">'+brr[i].myData+'</td></tr>'
			  }
			  $('.branchdiscount table tbody',parent.document).html( str );
			  $('tbody tr td',parent.document).on('blur',function(){	
				  var  label=$('.branchdiscount table tbody tr.bgc td:eq(0)',parent.document).text(),
					dat=$('.branchdiscount table tbody tr.bgc td:eq(2)',parent.document).text(),
					valu=$('.branchdiscount table tbody tr.bgc td:eq(1)',parent.document).text();
					for(var i=0;i<listarr.length;i++){
						if( listarr[i].myLabel==label &&listarr[i].myValue==valu){
							listarr[i].myData=dat ;
						}
					}
					console.log( listarr )
				});
		  }
	  });
	//点击分页;
    $('.boxtop').on('click','.first',function(){
    	var Nopage=$('.Nopage').text()*1;
 	    if(Nopage>1){
 	    	Nopage=1;
 	    	getlist( {'ogcd':$('.outname select option:selected').attr('ogcd'),'userKey':userkey,'pageNo':Nopage,'pageSize':10} );
 	    }
    });
	$('.boxtop').on('click','.prev',function(){
	    var Nopage=$('.Nopage').text()*1;
	    if(Nopage>1){
	    	Nopage=Nopage-1;
	    	getlist( {'ogcd':$('.outname select option:selected').attr('ogcd'),'userKey':userkey,'pageNo':Nopage,'pageSize':10} );
	    }
	});
	$('.boxtop').on('click','.next',function(){
		var Nopage=$('.Nopage').text()*1,
			Totalpage=$('.Totalpage').text()*1;
	    if(Nopage<Totalpage){
	    	Nopage=Nopage*1+1;
	    	getlist( {'ogcd':$('.outname select option:selected').attr('ogcd'),'userKey':userkey,'pageNo':Nopage,'pageSize':10} );
	    }
	});
	$('.boxtop').on('click','.last',function(){
		var Nopage=$('.Nopage').text()*1,
		Totalpage=$('.Totalpage').text()*1;
	    if(Nopage<Totalpage){
	    	Nopage=Totalpage;
	    	getlist( {ogcd:$('.outname select option:selected').attr('ogcd'),'userKey':userkey,'pageNo':Nopage,'pageSize':10} );
	    }
	});
	function startstopfn(obj){
		$.ajax({
		    	url:obj.url,
		    	type:'post',
				async:true,
				contentType:'application/json',
				data:JSON.stringify({'userKey':userkey,favruleList:obj.data}),
				success:function(data){
					if(data.code==02){
						dialog.choicedata(data.codeMessage,'handparset','aaa');   							
					}else if(data.code==01){
						dialog.choicedata(data.codeMessage,'handparset','aaa'); 
						getlist( {'ogcd':$('.outname select option:selected').attr('ogcd'),'userKey':userkey,'pageNo':$('.Nopage').text()*1,'pageSize':10} );				
					}
				}
		    });
		}
});
