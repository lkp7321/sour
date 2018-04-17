/*系统-机构管理*/
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
		ww=$(window).width()-250+"px";;

		$('.boxtop').css('width',ww);
		$('.boxtop').css('height',wh);
		$('#page').css('width',ww);
		
	 var numreg=/^[0-9a-zA-Z]{4}$/;
	 var usnm=sessionStorage.getItem('usnm'),
		 product=sessionStorage.getItem('product'),
		 userkey=JSON.parse( sessionStorage.getItem('menu') ).userKey,
		 user={'usnm':usnm,'prod':product},listnumtotal,
		 userdata;
	//列参数;
    var cols = [
        { title:'机构号', name:'ogcd' ,width:100, align:'left' },
        { title:'机构名称', name:'ognm' ,width:200, align:'left'},
        { title:'使用标志', name:'usfg' ,width:100, align:'center'},
        { title:'等级标志', name:'leve' ,width:80, align:'right'},
        { title:'上级机构号', name:'ogup' ,width:100, align:'left'},
        { title:'自定义一级分行编号', name:'orgn' ,width:100, align:'left'}
    ];
   //渲染页面数据；
    renlist({
		'userKey':userkey,
		'pageNo':1,
		'pageSize':10
	});
    renpage();
    
    function renlist( obj ){
      $.ajax({
			url:'/fx/getAllOgcd.do',
			type:'post',
			contentType:'application/json',
			data:JSON.stringify( obj ),
			async:false,
			success:function(data){
				if(data.code==01){
					userdata= data.codeMessage ;
					dialog.ren({'cols':cols,'wh':wh,'userdata':userdata.list});
					listnumtotal=userdata.total;
					/*$('.page').remove();
    				$('.boxtop').append('<div class="page"><span class="first">首页</span><span class="prev">上一页</span><span class="next">下一页</span><span class="last">末页</span><span class="nopage">第<b class="Nopage">'+userdata.pageNum+'</b>页/共<b class="Totalpage">'+userdata.pages+'</b>页</span><span class="totalpage">共<b class="total">'+userdata.total+'</b>条记录</span></div>')
					 */
				}else if(data.code==00){
					dialog.ren({'cols':cols,'wh':wh,'userdata':''});
				}
			}
		});
    }
	//添加
	$('.add').click(function(){
		dialog.outfitadd('outfit','add');
		$('.outfit .outfitnum',parent.document).blur(function(){
			if( $(this).val()==''){
				$(this).parent('p').find('re').remove();
				$(this).parent('p').append('<re>请输入机构编号</re>');
			}else if( !numreg.test( $(this).val() ) ){
				$(this).parent('p').find('re').remove();
				$(this).parent('p').append('<re>请输入4位的编号</re>');
			}else{
				$(this).parent('p').find('re').remove();
			}
		});
		$('.outfit .outname',parent.document).blur(function(){
			if( $(this).val()==''){
				$(this).parent('p').find('re').remove();
				$(this).parent('p').append('<re>请输入机构名称</re>');
			}else{
				$(this).parent('p').find('re').remove();
			}
		});
	 });
	//请求弹窗的下拉列表
	$('body',parent.document).on('change','.grademark',function(){
		$('.outfit .tsmall',parent.document).html(' ');
		 $('.outfit .custom',parent.document).val(' ');
		 
		 var leve=$('.outfit .grademark option:selected',parent.document).val(),
	          sysVo={'userKey':userkey,'leve':leve};
		if( leve!='请选择'){
		//请求option
			$.ajax({
				url:'/fx/getAdOrgan.do',
				type:'post',
				async:false,
				dataType:'json',
				data:JSON.stringify(sysVo),
	 			contentType:'application/json;charset=utf-8',
				success:function(data){
					str='';
					 if(data.code==01){
						var listdata=JSON.parse( data.codeMessage );
						for(var i in listdata){
							str+='<option value='+listdata[i].ogcd+'>'+listdata[i].ognm+'</option>'
						}
						$('.outfit .tsmall',parent.document).html(str);
					    //根据点击上级机构编号  显示自定义分行编号
					   // $('body',parent.document).on('change','.tsmall',function(){
					    	var ogcd=$('.tsmall option:selected',parent.document).val();
					    	$('.custom',parent.document).val(ogcd);
					    //});
					  }else if(data.code==02){
						dialog.choicedata(data.codeMessage,'outfiterror');
					}
				}
			});
	  }
	});
	$('body',parent.document).on('change','.tsmall',function(){
    	var ogcd=$('.tsmall option:selected',parent.document).val();
    	$('.custom',parent.document).val(ogcd);
    });
	$('.modify').click(function(){
		if( $('.box tbody tr').hasClass('selected') ){
			//修改弹出框;+获取当前selected的数据;
			var leve=$('.box tr.selected td:eq(3) span').text();
			if(leve!=0 && leve!=1){
				dialog.outfitadd('outfit','change');
				$('.outfit .outname').blur(function(){
					if( $(this).val()==''){
						$(this).parent('p').find('re').remove();
						$(this).parent('p').append('<re>请输入机构编号</re>');
					}else if( !numreg.test( $(this).val() ) ){
						$(this).parent('p').find('re').remove();
						$(this).parent('p').append('<re>请输入4位的编号</re>');
					}
				});
				checkradio($('.box tr.selected td:eq(10) span').text() );
				var ogcd=$('.box tr.selected td:eq(0) span').text(),
				    ognm=$('.box tr.selected td:eq(1) span').text(),
				    usfg=$('.box tr.selected td:eq(2) span').text(),
				    leve=$('.box tr.selected td:eq(3) span').text(),
				    orgn=$('.box tr.selected td:eq(5) span').text(),
				    sysVo={'userKey':userkey,'leve':leve};
				
				    $('.outfit .outfitnum',parent.document).val(ogcd).attr('disabled',true);
				    $('.outfit .outname',parent.document).val(ognm);
				    if(usfg=='开启'){
				    	$('.outfit .cenr input[type="radio"][value="0"]',parent.document).prop("checked",true); 
				    }else{
				    	$('.outfit .cenr input[type="radio"][value="1"]',parent.document).prop("checked",true);
				    }
				    $('.outfit .grademark',parent.document).val(leve).attr('disabled',true);
				    $('.custom ',parent.document).val(orgn).attr('disabled',true);
				  //请求option
					$.ajax({
						url:'/fx/getAdOrgan.do',
						type:'post',
						async:false,
						dataType:'json',
						data:JSON.stringify(sysVo),
			 			contentType:'application/json;charset=utf-8',
						success:function(data){
							str='';
							 if(data.code==01){
								 var listdata=JSON.parse( data.codeMessage );
								for(var i in listdata){
									str+='<option value='+listdata[i].ogcd+'>'+listdata[i].ognm+'</option>'
								}
								$('.outfit .tsmall',parent.document).html(str);
								 //根据点击上级机构编号  显示自定义分行编号
							    $('body',parent.document).on('change','.tsmall',function(){
							    	var ogcd=$('.tsmall option:selected',parent.document).val();
							    	$('.custom',parent.document).val(ogcd);
							    });
							  }else if(data.code==02){
								dialog.choicedata(data.codeMessage,'outfit');
							}
						},
						complete:function(){
							$('.tsmall option',parent.document).each(function(i,v){
								if($(v).val()==orgn){
						    		 $(this).attr("selected", "selected");
						    	 }
							});
						}
					});
			}else{
				dialog.choicedata('您不能修改当前机构信息!','outfit');
			}
		}else{
			dialog.choicedata('请先选择一条数据!','outfit');
		}
	});	
	//机构删除；
	$('.del').click(function(){
		if( $('.box tbody tr').hasClass('selected') ){
		    dialog.cancelDate('您确定要删除所选机构吗?','outfitcancel');
		 }else{
			dialog.choicedata('请先选择一条数据!','outfit');
		}
	});
	 $('body',parent.document).on('click','.outfitcancel .confirm',function(){
	    	var ogcd=$('.box tr.selected td:eq(0) span').text(),
	        sysVo={'userKey':userkey,'ogcd':ogcd};
	    $.ajax({
			url:'/fx/delOrgan.do',
			type:'post',
			contentType:'application/json',
			data:JSON.stringify(sysVo),
			async:true,
			success:function(data){
				$('.outfitcancel .confirm',parent.document).closest('.zhezhao').remove();
				if(data.code==01){
					dialog.choicedata(data.codeMessage,'outfit'); 
					renlist({
						'userKey':userkey,
						'pageNo':1,
						'pageSize':10
					});
					renpage();
				}else if(data.code==02){
					dialog.choicedata(data.codeMessage,'outfit'); 
				}else if(data.code==03){
					dialog.choicedata(data.codeMessage,'outfit'); 
				}
			}
		  });
     });
	//合并机构
	$('.merge').click(function(){
		if( $('.box tbody tr').hasClass('selected') ){
			var leve=$('.box tr.selected td:eq(3) span').text();
			if(leve!=0 && leve!=1){
				dialog.cancelDate('您确定要合并机构吗？','outfitthree');
			}else{
				dialog.choicedata('该机构不允许合并!','outfit');
			}   
		 }else{
			dialog.choicedata('请先选择一条数据!','outfit');
		}
	});
	$('body',parent.document).on('click','.outfitthree .confirm',function(){
		$('.outfitthree .confirm',parent.document).closest('.zhezhao').remove();
		//点击确定合并弹出合并弹窗
		dialog.outfitMerge('outfit','outfitmerge'); 
		//获取被撤机构的值
		//console.log($('.box tr.selected td:eq(1) span').text());
	    $('.removedagent',parent.document).val($('.box tr.selected td:eq(1) span').text());
		var ogcd=$('.box tr.selected td:eq(0) span').text(),
		    ogup=$('.box tr.selected td:eq(4) span').text(),
		    sysVo={'userKey':userkey,'trdOgcd':{'ogcd':ogcd,'ogup':ogup}};
	    //获取合并机构列表信息
		 $.ajax({
			url:'/fx/getHeOrgan.do',
			type:'post',
			async:false,
			dataType:'json',
			data:JSON.stringify(sysVo),
 			contentType:'application/json;charset=utf-8',
 			beforeSend:function(){
 				str='<option>请选择</option>';
 			},
			success:function(data){
				 if(data.code==01){
					var listdata=JSON.parse( data.codeMessage );
					for(var i in listdata){
						str+='<option value='+listdata[i].ogcd+'>'+listdata[i].ognm+'</option>'
					}
					$('.outfit .mergeagent',parent.document).html(str);				  
					//根据点击上级机构编号  显示自定义分行编号				    
					$('body',parent.document).on('change','.tsmall',function(){				    
					    var ogcd=$('.tsmall option:selected',parent.document).val();
				    	$('.custom',parent.document).val(ogcd);
				    })
				  }else if(data.code==02){
					dialog.choicedata(data.codeMessage,'outfiterror');
				}
			}
		});
		
	});
	//点击合并弹窗的保存按钮
	$('body',parent.document).on('click','.outfitmerge .merge',function(){
		 var newogcd=$('.mergeagent option:selected',parent.document).val(),
		     oldogcd=$('.box tr.selected td:eq(0) span').text(),
		     sysVo={'userKey':userkey,'newogcd':newogcd,'oldogcd':oldogcd};
		 $.ajax({
				url:'/fx/heOrgan.do',
				type:'post',
				contentType:'application/json',
				data:JSON.stringify(sysVo),
				
				async:true,
				success:function(data){
					$('.outfitmerge .merge',parent.document).closest('.zhezhao').remove();
					if(data.code==01){
						dialog.choicedata(data.codeMessage,'outfit'); 
						renlist({
							'userKey':userkey,
							'pageNo':1,
							'pageSize':10
						});
						renpage();
					}else if(data.code==02){
						dialog.choicedata(data.codeMessage,'outfiterror'); 
					}
				}
			});
		 
    });
	$('body',parent.document).on('click','.outfitthree .sure',function(){
		$(this).closest('.zhezhao').remove();
	});
	//保存按钮;
	$('body',parent.document).on('click','.outfit .sav',function(){
		var pubtxt=$('.outfit .top span',parent.document).text(),
		    ogcd=$('.outfit .outfitnum',parent.document).val(),
			ognm=$('.outfit .outname',parent.document).val(),
			usfg=$('.outfit .cenr input[name="a"]:checked',parent.document).attr('value'),
			leve=$('.outfit .grademark option:selected',parent.document).val(),
			ogup=$('.custom',parent.document).val(),
			orgn=$('.tsmall option:selected',parent.document).val(),url;
			
		 if(pubtxt=='机构管理--修改'){
		    url='modifyOgancd.do';
		    leve=$('.grademark',parent.document).val();	
		}else{
			url='addOgancd.do';
		}
		var sysVo={'userKey:':userkey,  'trdOgcd':{  'ogcd':ogcd, 'ognm':ognm,  'usfg':usfg,  'leve':leve, 'ogup':ogup,  'orgn':orgn }};
		
		if( $('.outfit .outfitnum',parent.document).val()==''){
			$('.outfit .outfitnum',parent.document).parent('p').find('re').remove();
			$('.outfit .outfitnum',parent.document).parent('p').append('<re>请输入机构编号</re>');
		}else if( !numreg.test( $('.outfit .outfitnum',parent.document).val() ) ){
			$('.outfit .outfitnum',parent.document).parent('p').find('re').remove();
			$('.outfit .outfitnum',parent.document).parent('p').append('<re>请输入4位的编号</re>');
		}else{
			$('.outfit .outfitnum',parent.document).parent('p').find('re').remove();
		}
		if($('.outfit .outname',parent.document).val()==''){
			$('.outfit .outname',parent.document).parent('p').find('re').remove();
			$('.outfit .outname',parent.document).parent('p').append('<re>请输入机构名称</re>');
		}else{
			$('.outfit .outname',parent.document).parent('p').find('re').remove();
		}
		
		if($('.outfit re',parent.document).length==0){
			if( $('.grademark option:selected',parent.document).text()!='请选择'&&$('.grademark option:selected',parent.document).text()!='一级机构'){
				if( $('.tsmall option',parent.document).length>0&&$('.tsmall option:selected',parent.document).text()!='请选择'){
					$.ajax({
						url:url,
						type:'post',
						contentType:'application/json',
						data:JSON.stringify(sysVo),
						async:true,
						success:function(data){
							$('.outfit .sav',parent.document).closest('.zhezhao').remove();
							if(data.code==01){
								dialog.choicedata(data.codeMessage,'outfit'); 
								renlist({
									'userKey':userkey,
									'pageNo':1,
									'pageSize':10
								});
								renpage();
							}else if(data.code==02){
								dialog.choicedata(data.codeMessage,'outfiterror'); 
							}
						}
					});
				}else{
					dialog.choicedata('请先选择上级机构编号!','outfit');
				}
			}else{
				dialog.choicedata('请重新选择等级标志!','outfit');
			}
		}
	});
	$('body',parent.document).on('click','.outfitthree .cancel,.outfitcancel .cancel,.outfit .cancel,.outfit .sure,.outfit .close',function(){
		$(this).closest('.zhezhao').remove();
	});
	$('body',parent.document).on('click','.outfiterror .sure',function(){
		$(this).closest('.zhezhao').remove();
	});
	//点击分页;
   /* $('.boxtop').on('click','.first',function(){
    	var Nopage=$('.Nopage').text()*1;
 	    if(Nopage>1){
 	    	Nopage=1;
 	    	 renlist({
 	    		'userKey':userkey,
 	    		'pageNo':Nopage*1,
 	    		'pageSize':10
 	    	});
 	    }
    });
	$('.boxtop').on('click','.prev',function(){
	    var Nopage=$('.Nopage').text()*1;
	    if(Nopage>1){
	    	Nopage=Nopage-1;
	    	 renlist({
 	    		'userKey':userkey,
 	    		'pageNo':Nopage*1,
 	    		'pageSize':10
 	    	});	    
	    }
	});
	$('.boxtop').on('click','.next',function(){
		var Nopage=$('.Nopage').text()*1,
			Totalpage=$('.Totalpage').text()*1;
	    if(Nopage<Totalpage){
	    	Nopage=Nopage*1+1;
	    	 renlist({
 	    		'userKey':userkey,
 	    		'pageNo':Nopage*1,
 	    		'pageSize':10
 	    	});	    
	    }
	});
	$('.boxtop').on('click','.last',function(){
		var Nopage=$('.Nopage').text()*1,
		Totalpage=$('.Totalpage').text()*1;
	    if(Nopage<Totalpage){
	    	Nopage=Totalpage;
	    	 renlist({
 	    		'userKey':userkey,
 	    		'pageNo':Nopage*1,
 	    		'pageSize':10
 	    	});
 	    }
	});*/
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
    		    		renlist({
    		    			'userKey':userkey,
    		    			'pageNo':obj.curr,
    		    			'pageSize':$('.layui-laypage-limits select option:selected').attr('value')*1
    		    		});
    		    	}	
    		    }
    		  });
    	});
    }
	/*----------------快速搜索功能的实现------------------------*/
	$('.info_serbtn').click(function(){
		dialog.serchData( $('.info_seript').val() );
		renpage();
    });
	//根据用户界面判断弹出层中的是否选中；  更改这块的内容;
	function checkradio(obj){
		if( obj=='正常'){
			$('.startoutfit',parent.document).prop('checked','checked');
		}else{
			$('.nostartout',parent.document).prop('checked','checked');
		}
	}

})
