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
		ww=$(window).width()-260+"px";
	
		$('.boxtop').css('width',ww);
		$('.boxtop').css('height',wh);
	//列参数;
    var cols = [
        { title:'客户级别', name:'cuty' ,width:400, align:'right' },
        { title:'级别名称', name:'tynm' ,width:400, align:'left'}
    ];
    var usnm=sessionStorage.getItem('usnm'),
	    product=sessionStorage.getItem('product'),
	    loginuser={'usnm':usnm,'prod':product},
	    userKey=JSON.parse( sessionStorage.getItem('menu') ).userKey,
	    userdata;
    //请求列表
    getCustLevel(userKey);
    function getCustLevel(obj){
    	$.ajax({
    		url:'/fx/getCustLevel.do',
    	    type:'post',
    		contentType:'application/json',
    		data:obj,
    		async:false,
    		success:function(data){
    			if(data.code==00){
    				userdata=JSON.parse( data.codeMessage );
    				dialog.ren({'cols':cols,'wh':wh,'userdata':userdata});
    			}else if(data.code==02){
    				dialog.choicedata(data.codeMessage,'customer',null);
    			}
    		  }
          });
    }
    /*添加*/
	$('.add').click(function(){
		dialog.customeradd('customer','add');
		 $('.customerlevel',parent.document).on('blur',function(){
			 console.log( $(this).val()=='');
			if($(this).val()==''||$(this).val()==undefined){
				$('.customerlevel',parent.document).parent('p').find('re').remove();
				$('.customerlevel',parent.document).parent('p').append('<re>客户级别不能为空</re>');
			}else{
				$('.customerlevel',parent.document).parent('p').find('re').remove();
			}
		});
		$('.levelname',parent.document).on('blur',function(){
			if($(this).val()==''||$(this).val()==undefined){
				$('.levelname',parent.document).parent('p').find('re').remove();
				$('.levelname',parent.document).parent('p').append('<re>级别名称不能为空</re>');
			}else{
				$('.levelname',parent.document).parent('p').find('re').remove();
			}
		});		
		 
	 });
	//点击添加弹窗框的确认按钮
	$('body',parent.document).on('click','.customer .add .sav',function(){
		    var customerlevel=$('.customerlevel',parent.document).val(),
				levelname=$('.levelname',parent.document).val(),num=0,
			    CustVo ={ 'custLevel':{ 'cuty':customerlevel, 'tynm':levelname},
						    'userKey':userKey
						};
		    	console.log( customerlevel );
				if(customerlevel==''||customerlevel==undefined){
					$('.customerlevel',parent.document).parent('p').find('re').remove();
					$('.customerlevel',parent.document).parent('p').append('<re>客户级别不能为空</re>');
				}else{
					num++;
				}
				if(levelname==''||levelname==undefined){
					$('.levelname',parent.document).parent('p').find('re').remove();
					$('.levelname',parent.document).parent('p').append('<re>级别名称不能为空</re>');
				}else{
					num++;
				}
				if(num>=2){
					 $.ajax({
						url: 'CustLevelAdd.do',
						type:'post',
						contentType:'application/json',
						data:JSON.stringify(CustVo),
						async:false,
						dataType:'json',
						success:function(data){
						     if(data.code==00){
						    	 $('.customer .add .sav',parent.document).closest('.zhezhao').remove();
						    	$('.customer .confirm',parent.document).closest('.zhezhao').remove();
						    	getCustLevel(userKey);
						    	dialog.choicedata(data.codeMessage,'customer');
							 }else if(data.code==01){
								dialog.choicedata(data.codeMessage,'customererror');
							}
						}
					});
					
				} 
				
	});
    //点击添加弹窗匡的取消和关闭按钮
	$('body',parent.document).on('click','.customer .cancel',function(){
		 $('.zhezhao',parent.document).remove();
	});
	$('body',parent.document).on('click','.customer .close',function(){
		$('.zhezhao',parent.document).remove();
	});
	/*修改*/
	$('.modify').click(function(){
		var cuty=$('.box tr.selected td:eq(0) span').text(),
	        tynm=$('.box tr.selected td:eq(1) span').text();
		if( $('.box tr').hasClass('selected') ){
			 dialog.customeradd('customer','change');
			 $('.customerlevel',parent.document).val(cuty).attr('readonly','true');
			 $('.levelname',parent.document).val(tynm);
			 
			 $('.levelname',parent.document).on('blur',function(){
					if($(this).val()==''||$(this).val()==undefined){
						$('.levelname',parent.document).parent('p').find('re').remove();
						$('.levelname',parent.document).parent('p').append('<re>级别名称不能为空</re>');
					}else{
						$('.levelname',parent.document).parent('p').find('re').remove();
					}
				});		
		}else{
			dialog.choicedata('请先选择一条数据!','customererror');
		}
	});
	//点击修改框的确认按钮
	$('body',parent.document).on('click','.customer .change .sav',function(){
		    var customerlevel=$('.customerlevel',parent.document).val(),
		    levelname=$('.levelname',parent.document).val(),num=0,
			    CustVo ={ 'custLevel':{ 'cuty':customerlevel, 'tynm':levelname},
						    'userKey':userKey
						};
				
				if(customerlevel==''||customerlevel==undefined){
					$('.customerlevel',parent.document).parent('p').append('<re>客户级别不能为空</re>');
				}else{
					num++;
				}
				if(levelname==''||levelname==undefined){
					$('.levelname',parent.document).parent('p').append('<re>级别名称不能为空</re>');
				}else{
					num++;
				}
				if(num>=2){
					 $.ajax({
						url: 'CustLevelUpdate.do',
						type:'post',
						contentType:'application/json',
						data:JSON.stringify(CustVo),
						async:false,
						success:function(data){
							 if(data.code==00){
								$('.customer .change .sav',parent.document).closest('.zhezhao').remove();
								getCustLevel(userKey);
								dialog.choicedata(data.codeMessage,'customer',null);
							}else if(data.code==02){
								dialog.choicedata(data.codeMessage,'customererror',null);
							}
						}
					});
					
				} 
	});
	 $('.del').click(function(){
		if( $('.box tr').hasClass('selected') ){
			dialog.cancelDate('您确定要删除当前记录吗?','customer');
		}else{
			dialog.choicedata('请先选择一条数据!','customererror');
		}
	});
	 //点击删除弹出框的确认按钮 
	$('body',parent.document).on('click','.customer .confirm',function(){
		//删除数据;
		//$('.box tr.selected').remove();
		 var cuty=$('.box tr.selected td:eq(0) span').text(),
		     tynm=$('.box tr.selected td:eq(1) span').text(),
		     CustVo ={ 'custLevel':{ 'cuty':cuty, 'tynm':tynm},
					    'userKey':userKey
					};
		 
	    $.ajax({
			url: 'CustLevelDelete.do',
			type:'post',
			contentType:'application/json',
			data:JSON.stringify(CustVo),
			async:false,
			success:function(data){
				$('.customer .confirm',parent.document).closest('.zhezhao').remove();
				  if(data.code==00){
					 $('.customer .add .sav',parent.document).closest('.zhezhao').remove();
					 getCustLevel(userKey);
					 dialog.choicedata(data.codeMessage,'customer',null);
				 }else if(data.code==01){
					dialog.choicedata(data.codeMessage,'customer',null);
				 }
			}
		});
	});
	//关闭选择一条数据,重新请求列表;
	$('body',parent.document).on('click','.customer .sure',function(){
		//关闭选择一条数据;
		$('.customer .sure',parent.document).closest('.zhezhao').remove(); 
		getCustomer();
	});
	//关闭选择一条数据,不重新请求列表;
	$('body',parent.document).on('click','.customererror .sure',function(){
		//关闭选择一条数据;
		$('.customererror .sure',parent.document).closest('.zhezhao').remove(); 
	 });
    /*----------------快速搜索功能的实现-----------*/
	$('.info_serbtn').click(function(){
		dialog.serchData($('.info_seript').val(),'.mmg-headWrapper');
    });
	function getCustomer(obj){
		 $.ajax({
				url:'/fx/getCustLevel.do',
			    type:'get',
				contentType:'application/json',
				async:false,
				success:function(data){
					if(data.code==00){
						userdata=JSON.parse( data.codeMessage );
						dialog.ren({'cols':cols,'wh':wh,'userdata':userdata});
					}else if(data.code==02){
						dialog.choicedata(data.codeMessage,'customer',null);
					}
				  }
		      });
			
	}
	/*function ren(obj){
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
    }*/
	
});
