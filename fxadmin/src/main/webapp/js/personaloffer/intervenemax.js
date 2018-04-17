/*点差干预上限设置*/
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
        product=sessionStorage.getItem('product'),str,productName,
        loginuser={'usnm':usnm,'prod':product},
        userKey=JSON.parse( sessionStorage.getItem('menu') ).userKey;
    switch(product){
	    case 'P001':productName='个人实盘';break;
	    case 'P002':productName='纸黄金交易管理平台';break;
	    case 'P003':productName='积存金管理平台';break;
	    case 'P004':productName='个人结售汇';break;
	    case 'P007':productName='账户交易';break;
	    case 'P009':productName='保证金前置';break;
	    case 'P998':productName='RV前置';break;
	    case 'P999':productName='报价平台';break;
	}
    if(product=="P999"){
    	 //列参数;
        var cols = [
            { title:'价格类型', name:'tpnm' ,width:100, align:'center' },
            { title:'期限', name:'term' ,width:100, align:'right'},
            { title:'钞汇标志', name:'cxfg' ,width:150, align:'center'},
            { title:'货币对名称', name:'exnm' ,width:100, align:'left'},
            { title:'总控开关', name:'usfg' ,width:100, align:'center'},
            { title:'买干预上限', name:'nbup' ,width:150, align:'right'},
            { title:'卖干预上限', name:'nsup' ,width:80, align:'right'},
        ];
      //请求列表
      getMax();
      //封装请求列表
    	function getMax(){
    		$.ajax({
    	    	url:'/fx/selectCtrlSwh.do',
    	        type:'get',
    	    	contentType:'application/json',
    	    	async:false,
    	    	dataType:'json',
    	    	success:function(data){
    	    		if(data.code==00){
    	    			 ren({'cols':cols,'wh':wh,'userdata':JSON.parse(data.codeMessage),'checked':true});
    	    		}else if(data.code==01){
    	    			 ren({'cols':cols,'wh':wh,'userdata':'','checked':true});
    	    		}else if(data.code==02){
    	    			//查询异常
    	    		}
    	    	  }
    	      });
    		
    	};
    	//添加框弹出
    	$('.add').click(function(){
    		 
    		  dialog.intermaxmodify('intermax');
    		  testNull('buymax','买入价干预上限不能为空'); 
    		  testNull('sallmax','卖入价干预上限不能为空');
    		  //请求价格类型
    		  $.ajax({
  				url:'/fx/curTpnm.do',
  				type:'get',
  				async:false,
  				dataType:'json',
  	 			contentType:'application/json;charset=utf-8',
  	 			success:function(data){
  					var maxdata=JSON.parse(data.codeMessage),str='';
  					if(data.code==00){
  						for(var i in maxdata){
  							str+='<option value='+maxdata[i].TPFG+'>'+maxdata[i].TPNM+'</option>'
  						}
  						$('.exname select',parent.document).html(str);
  					}else if(data.code==01){
  						 
  					}
  				}
  			});
    		  //请求货币对下拉列表
    		  $.ajax({
    				url:'/fx/curExnm.do',
    				type:'get',
    				async:false,
    				dataType:'json',
    	 			contentType:'application/json;charset=utf-8',
    	 			success:function(data){
    					var maxdata=JSON.parse(data.codeMessage),str='';
    					if(data.code==00){
    						for(var i in maxdata){
    							str+='<option value='+maxdata[i].EXCD+'>'+maxdata[i].EXNM+'</option>'
    						}
    						$('.maxname select',parent.document).html(str);
    					}else if(data.code==01){
    						 
    					}
    				}
    			});
    	 }); 
    	 //修改框弹出
    	$('.modify').click(function(){
    		var tpnm=$('.box tr.selected td:eq(1) span').text(),
    		    tpfg=$('.box tr.selected td:eq(2)').attr('tpfg'),
    		    term=$('.box tr.selected td:eq(2) span').text(),
    		    exnm=$('.box tr.selected td:eq(4) span').text(),
    	        nbup=$('.box tr.selected td:eq(6) span').text(),
    	        nsup=$('.box tr.selected td:eq(7) span').text(),
    	        cxfg=$('.box tr.selected td:eq(3) span').text(),
    	        usfg=$('.box tr.selected td:eq(5) span').text();
    		if( $('.box tbody tr').hasClass('selected') ){
    		 //修改弹出框;+获取当前selected的数据;
               dialog.intermaxmodify('intermax');
               testNull('buymax','买入价干预上限不能为空'); 
     		   testNull('sallmax','卖入价干预上限不能为空');
              $('.money input',parent.document).attr({'disabled':'disabled'});
     		  $('.exname option:selected',parent.document).text(tpnm).val(tpfg);
     		  $('.exname select',parent.document).attr({'disabled':'disabled'});
     		  $('.term input',parent.document).val(term).attr({'disabled':'disabled'});
     		  $('.maxname option:selected',parent.document).text(exnm);
     		  $('.maxname select',parent.document).attr({'disabled':'disabled'});
     		  $('.buymax input',parent.document).val(nbup);
     		  $('.sallmax input',parent.document).val(nsup);
     		  //console.log(tpnm)
     		  //console.log(cxfg)
     		  if(usfg=='停止' ){
    				$('.maxcontrol input[data-tit="stop"]',parent.document).prop('checked','checked');
    			}else{
    				$('.maxcontrol input[data-tit="start"]',parent.document).prop('checked','checked');
    		   } 
     		 if(cxfg=='汇' ){
 				$('.money input[data-tit="stop"]',parent.document).prop('checked','checked');
 			}else{
 				$('.money input[data-tit="start"]',parent.document).prop('checked','checked');
 		   } 
    		}else{
    			dialog.choicedata('请先选择一条数据!','intermax');
    		}
    	});
    	//点击添加和修改弹窗保存;
    	$('body',parent.document).on('click','.intermax_save',function(){
    		 var tpfg=$('.exname option:selected',parent.document).val(),
    		     term=$('.term input',parent.document).val(),
    		     cxfg=$('.money input',parent.document).val(), 
    		     excd=$('.maxname option:selected',parent.document).val(),
    	         exnm=$('.maxname option:selected',parent.document).text(),
    	         nbup=$('.buymax input',parent.document).val(),
    	         nsup=$('.sallmax input',parent.document).val(),usfg,PriceVo,url;
    		     testNull('buymax','买入价干预上限不能为空'); 
    		     testNull('sallmax','卖入价干预上限不能为空');
    		 
    		 //数据处理
    	     if($('.maxcontrol input[type="radio"]:checked',parent.document).data('tit')=='start'){
    	    	   usfg=0;
    		 }else{
    		       usfg=1;
    		 }
    	     
    	     if( $('.term input',parent.document).attr('disabled')=='disabled'){
	    		url="upCmmCtrlSwh.do"; 
	    		PriceVo={'userKey':userKey,'cmmCtrl':{'tpfg':tpfg,'term':term,'cxfg':cxfg, 'exnm':exnm,'nbup':nbup,'nsup':nsup,'usfg':usfg}};  
	    	 }else{
	    		url="addCmmCtrlSwh.do";
	    		PriceVo={'userKey':userKey,'cmmCtrl':{'tpfg':tpfg,'term':term,'cxfg':cxfg, 'exnm':exnm,'excd':excd,'nbup':nbup,'nsup':nsup,'usfg':usfg}};  
	    	 }
    	     if(nbup!='' && nsup!=''){
    	    	 $.ajax({
    			    	url:url,
    			        type:'post',
    			    	contentType:'application/json',
    			    	data:JSON.stringify(PriceVo),
    			    	async:false,
    			    	dataType:'json',
    			    	success:function(data){
    			    		if(data.code==00){
    			    			$('.intermax_save',parent.document).closest('.zhezhao').remove();
    			    			dialog.choicedata(data.codeMessage,'intermax');
    			    			getMax({'userKey':userKey});
    			    		}else if(data.code==01){
    			    			dialog.choicedata(data.codeMessage,'intermax');
    			    		} 
    			    	  }
    			  });
    	     }
    	})
    	//删除框弹出
    	$('.del').click(function(){
    		var a=0,arr=[];
    		$('.box tr').each(function(){
    			if( $(this).find('input[type="checkbox"]').prop('checked')==true){
    				a++;
    			}
    		})
    		if(a==0){
    			dialog.choicedata('请先勾选一条数据再进行此操作','intermax');
    		}else{
    			dialog.cancelDate("您确定要删除当前记录吗?","intermax");
    		}
    		 
    	});
    	//删除窗的确定按钮
    	$('body',parent.document).on('click','.intermax .confirm',function(){
    		var arr=[];
    		$('.box tr').each(function(i,v){
 				if( $(v).find('input[type="checkbox"]').prop('checked') ){
 				  arr.push({
 					  'exnm':$(v).find('td').eq(4).find('span').text(),
 					  'tpfg':$(v).find('td').eq(2).attr('tpfg'),
 					  'term':$(v).find('td').eq(2).find('span').text(),
 					  'cxfg':$(v).find('td').eq(3).find('span').text()
 					   
 				  });
 				}
 			});
    		 var PriceVo={'userKey':userKey,'cmmCtrls':arr};
    		 $.ajax({
    		    	url:'/fx/delCmmCtrl.do',
    		        type:'post',
    		    	contentType:'application/json',
    		    	data:JSON.stringify(PriceVo),
    		    	async:false,
    		    	dataType:'json',
    		    	success:function(data){
    		    		if(data.code==00){
    		    			$('.intervenemax .confirm',parent.document).closest('.zhezhao').remove();
    		    			dialog.choicedata(data.codeMessage,'intermax');
    		    			getMax();
    		    		}else if(data.code==01){
    		    			dialog.choicedata(data.codeMessage,'intermax');
    		    		} 
    		    	  }
    		  });
    	});
    	
    	//启用框停用框弹出
    	$('.hands_open,.hands_stop').click(function(){
    		var a=0,arr=[];
    		$('.box tr').each(function(){
    			if( $(this).find('input[type="checkbox"]').prop('checked')==true){
    				a++;
    			}
    		})
    		if(a==0){
    			dialog.choicedata('请先勾选一条数据再进行此操作','intermax');
    		}else{
    			 $('.box tr').each(function(i,v){
    				if( $(v).find('input[type="checkbox"]').prop('checked') ){
    				  if($(v).find('td').eq(3).find('span').text()=='钞'){
    					  var cxfg='2';
    				  }else{
    					  var cxfg='1';
    				  }
    				  arr.push({
    					  'exnm':$(v).find('td').eq(4).find('span').text(),
    					  'tpfg':$(v).find('td').eq(2).attr('tpfg'),
    					  'term':$(v).find('td').eq(2).find('span').text(),
    					  'cxfg':cxfg
    					   
    				  });
    				}
    			});
    			 
    		 	
    		 var usfg=$(this).text(),
    		     PriceVo={'userKey':userKey,'usfg':usfg,'cmmCtrls':arr};	 
    		    $.ajax({
    		    	url:'/fx/updateCmmCtrl.do',
    		        type:'post',
    		    	contentType:'application/json',
    		    	data:JSON.stringify(PriceVo),
    		    	async:false,
    		    	dataType:'json',
    		    	success:function(data){
    		    		if(data.code==00){
    		    			getMax();
    		    			dialog.choicedata(data.codeMessage,'intermax');
    		    		}else if(data.code==01){
    		    			//异常 
    		    		} 
    		    	  }
    		      });  	
    		}
    	});
    	 //关闭弹窗
    	$('body',parent.document).on('click','.intermax .sure',function(){
    		$(this).closest('.zhezhao').remove();
    	})
    	
   
    }else{
    	 //列参数;
        var cols = [
            { title:'总控开关', name:'usfg' ,width:100, align:'center' },
            { title:'货币对名称', name:'exnm' ,width:100, align:'left'},
            { title:'买干预上限', name:'nbup' ,width:150, align:'right'},
            { title:'卖干预上限', name:'nsup' ,width:80, align:'right'},
             
        ];
       
        //请求列表
        getMax({'userKey':userKey});
    	//添加框弹出
    	$('.add').click(function(){
    		 
    		  dialog.intermaxmodify('maxadd');
    		  $('.maxprid input',parent.document).val(productName).attr({'readonly':'true','disabled':'disabled'});
    		  testNull('buymax','买入价干预上限不能为空'); 
    		  testNull('sallmax','卖入价干预上限不能为空');
    		  //请求下拉列表
    		  $.ajax({
    				url:'/fx/getPriceUSD.do',
    				type:'post',
    				async:false,
    				data:JSON.stringify({'userKey':userKey}),
    				dataType:'json',
    	 			contentType:'application/json;charset=utf-8',
    	 			beforeSend:function(){
    	 			     str='';
    	 			},
    				success:function(data){
    					var maxdata=JSON.parse(data.codeMessage);
    					if(data.code==00){
    						for(var i in maxdata){
    							str+='<option value='+maxdata[i].excd+'>'+maxdata[i].exnm+'</option>'
    						}
    						$('.maxname select',parent.document).html(str);
    					}else if(data.code==01){
    						 
    					}
    				}
    			});
    	 }); 
        //修改框弹出
    	$('.modify').click(function(){
    		var usfg=$('.box tr.selected td:eq(1) span').text(),
    		    exnm=$('.box tr.selected td:eq(2) span').text(),
    	        nbup=$('.box tr.selected td:eq(3) span').text(),
    	        nsup=$('.box tr.selected td:eq(4) span').text();
    		if( $('.box tbody tr').hasClass('selected') ){
    		 //修改弹出框;+获取当前selected的数据;
               dialog.intermaxmodify('maxmodify');
               testNull('buymax','买入价干预上限不能为空'); 
     		   testNull('sallmax','卖入价干预上限不能为空');
               $('.maxprid input',parent.document).val(productName).attr({'readonly':'true','disabled':'disabled'});
               //请求下拉列表
     		   $.ajax({
     				url:'/fx/getPriceUSD.do',
     				type:'post',
     				async:false,
     				data:JSON.stringify({'userKey':userKey}),
     				dataType:'json',
     	 			contentType:'application/json;charset=utf-8',
     	 			beforeSend:function(){
     	 			     str='';
     	 			},
     				success:function(data){
     					var maxdata=JSON.parse(data.codeMessage);
     					if(data.code==00){
     						for(var i in maxdata){
     							str+='<option value='+maxdata[i].excd+'>'+maxdata[i].exnm+'</option>'
     						}
     						$('.maxname select',parent.document).html(str);
     					}else if(data.code==01){
     						 
     					}
     				}
     			});
     		  $('.maxname option:selected',parent.document).text(exnm);
     		  $('.maxname select',parent.document).attr({'disabled':'disabled'});
     		  $('.buymax input',parent.document).val(nbup);
     		  $('.sallmax input',parent.document).val(nsup);
     		  if(usfg=='停用' ){
    				$('.maxcontrol input[data-tit="stop"]',parent.document).prop('checked','checked');
    			}else{
    				$('.maxcontrol input[data-tit="start"]',parent.document).prop('checked','checked');
    		   } 
    		}else{
    			dialog.choicedata('请先选择一条数据!','intervenemax');
    		}
    	});
    	
    	//点击添加和修改弹窗保存;
    	$('body',parent.document).on('click','.intermax_save',function(){
    		 var excd=$('.maxname option:selected',parent.document).val(),
    	         exnm=$('.maxname option:selected',parent.document).text(),
    	         nbup=$('.buymax input',parent.document).val(),
    	         nsup=$('.sallmax input',parent.document).val(),usfg,PriceVo;
    		     testNull('buymax','买入价干预上限不能为空'); 
    		     testNull('sallmax','卖入价干预上限不能为空');
    		 
    		 //数据处理
    	     if($('.maxcontrol input[type="radio"]:checked',parent.document).data('tit')=='start'){
    	    	   usfg=0;
    		 }else{
    		       usfg=1;
    		 }
    	     PriceVo={'userKey':userKey,'pdtCtrlSwh':{ 'exnm':exnm,'excd':excd,'nbup':nbup,'nsup':nsup,'usfg':usfg}};  
    	     if(nbup!='' && nsup!=''){
    	    	 $.ajax({
    			    	url:'/fx/saveRole.do',
    			        type:'post',
    			    	contentType:'application/json',
    			    	data:JSON.stringify(PriceVo),
    			    	async:false,
    			    	dataType:'json',
    			    	success:function(data){
    			    		if(data.code==00){
    			    			$('.intermax_save',parent.document).closest('.zhezhao').remove();
    			    			dialog.choicedata(data.codeMessage,'intervenemax');
    			    			getMax({'userKey':userKey});
    			    		}else if(data.code==01){
    			    			dialog.choicedata(data.codeMessage,'intervenemaxerror');
    			    		} 
    			    	  }
    			  });
    	     }
    	})
     
    	//删除框弹出
    	$('.del').click(function(){
    		if( $('.box tbody tr').hasClass('selected') ){
    			 dialog.cancelDate("您确定要删除当前记录吗?","intervenemax");
    		}else{
    			dialog.choicedata('请先选择一条数据!','intervenemax');
    		}
    	});
    	//删除窗的确定按钮
    	$('body',parent.document).on('click','.intervenemax .confirm',function(){
    		 var exnm=$('.box tr.selected td:eq(2) span').text(),
    		     PriceVo={'userKey':userKey,'exnm':exnm};
    		 $.ajax({
    		    	url:'/fx/deleteRole.do',
    		        type:'post',
    		    	contentType:'application/json',
    		    	data:JSON.stringify(PriceVo),
    		    	async:false,
    		    	dataType:'json',
    		    	success:function(data){
    		    		if(data.code==00){
    		    			$('.intervenemax .confirm',parent.document).closest('.zhezhao').remove();
    		    			dialog.choicedata(data.codeMessage,'intervenemax');
    		    			getMax({'userKey':userKey});
    		    		}else if(data.code==01){
    		    			dialog.choicedata(data.codeMessage,'intervenemaxerror');
    		    		} 
    		    	  }
    		  });
    	});
    	
    	
    	
     
    		//启用框停用框弹出
    	$('.hands_open,.hands_stop').click(function(){
    		var a=0,arr=[];
    		$('.box tr').each(function(){
    			if( $(this).find('input[type="checkbox"]').prop('checked')==true){
    				a++;
    			}
    		})
    		if(a==0){
    			dialog.choicedata('请先勾选一条数据再进行此操作','intervenemaxerror');
    		}else{
    			 
    			$('.box tr').each(function(i,v){
    				if( $(v).find('input[type="checkbox"]').prop('checked') ){
    				  arr.push({
    					  'exnm':$(v).find('td').eq(2).find('span').text(),
    			      });
    				}
    			});
    			 
    		 	
    		 var usfg=$(this).text(),
    		     PriceVo={'userKey':userKey,'usfg':usfg,'pdtCtrlSwhs':arr};	 
    		    $.ajax({
    		    	url:'/fx/updateCtrl.do',
    		        type:'post',
    		    	contentType:'application/json',
    		    	data:JSON.stringify(PriceVo),
    		    	async:false,
    		    	dataType:'json',
    		    	success:function(data){
    		    		if(data.code==00){
    		    			getMax({'userKey':userKey});
    		    			dialog.choicedata(data.codeMessage,'intervenemax');
    		    		}else if(data.code==01){
    		    			//异常 
    		    		} 
    		    	  }
    		      });  	
    		}
    	});
     
    	
    	//关闭成功提示窗的  重新请求列表	
    	$('body',parent.document).on('click','.intervenemax .sure',function(){
    		$(this,parent.document).closest('.zhezhao').remove();
    		 
    	})
    	 //关闭修改失败 删除失败提示窗 不 重新请求列表	
    	$('body',parent.document).on('click','.intervenemaxerror .sure',function(){
    		$(this,parent.document).closest('.zhezhao').remove();
    	})
    	
    	
    	
     
    	//封装请求列表
    	function getMax(obj){
    		$.ajax({
    	    	url:'/fx/getPdtCtrlSwhList.do',
    	        type:'post',
    	    	contentType:'application/json',
    	    	data:JSON.stringify(obj),
    	    	async:false,
    	    	dataType:'json',
    	    	success:function(data){
    	    		if(data.code==00){
    	    			 dialog.ren({'cols':cols,'wh':wh,'userdata':JSON.parse(data.codeMessage),'checked':true});
    	    		}else if(data.code==01){
    	    			dialog.ren({'cols':cols,'wh':wh,'userdata':'','checked':true});
    	    		}else if(data.code==02){
    	    			//查询异常
    	    		}
    	    	  }
    	      });
    		
    	};
    }
  
    //删除窗的取消按钮
	$('body',parent.document).on('click','.intervenemax .cancel',function(){
		 $(this,parent.document).closest('.zhezhao').remove();
	});
    //点击修改弹窗的关闭和取消;
	$('body',parent.document).on('click','.intermax_close',function(){
		$('.zhezhao',parent.document).remove();
	})
	$('body',parent.document).on('click','.intermax_cancel',function(){
		$('.zhezhao',parent.document).remove();
	})
	//封装检测不能为空的方法
	function testNull(dom,str){
		  $('.'+dom+' input',parent.document).on('blur',function(){
				if($(this).val()==''||$(this).val()==undefined){
					$('.'+dom+' input',parent.document).closest('div').find('re').remove();
					$('.'+dom+' input',parent.document).closest('div').append('<re>'+str+'</re>');
				 }else if(!/^\+?(0|[1-9][0-9]*)$/.test($(this).val())){
					$('.'+dom+' input',parent.document).closest('div').find('re').remove();
					$('.'+dom+' input',parent.document).closest('div').append('<re>请输入正确的类型</re>');
				 }else{
				    $('.'+dom+' input',parent.document).closest('div').find('re').remove();
				}
			});
		 
	}
	 //table的渲染
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
				horizrailenabled:false
		  });
		   mmg.on('loadSuccess',function(e,data){
			 
			if(obj.userdata.length!=0){
				$('.box').find('tbody tr').each(function(i,v){
					 $(v).find('td').eq('2').attr('tpfg',obj.userdata[i].tpfg);
				 });
			}   
			 
		 }) 
	  }
})