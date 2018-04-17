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
	$('.page').css('width',ww);
	//列参数;
    var cols = [
            { title:'柜员ID', name:'TELLERID',width:100,align:'left' },
            { title:'机构号', name:'OGCD',width:100, align:'left'},
            { title:'请求方机构代号', name:'BHID',width:100, align:'left'},
            { title:'交易柜员', name:'TRTL',width:100, align:'left'},
            { title:'接口密码', name:'PASS',width:100,align:'left'},
            { title:'柜员类型', name:'TLTP',width:100, align:'center'},
            { title:'交易渠道', name:'CHNL',width:100, align:'left'}
    ];
    //获取产品和用户名；
    var usnm=sessionStorage.getItem('usnm'),
		product=sessionStorage.getItem('product'),
		user={'usnm':usnm,'prod':product},listnumtotal,
		userdata;
    var userkey=JSON.parse( sessionStorage.getItem('menu') ).userKey,
    	
    	tit=$('title').text();
    
    getlist({
    	pageNo:1,
    	pageSize:10,
    	entity:{
			tellerId:$('#tellerId').val()
    	}
    });
    renpage();
    //点击查询按钮;
    $('.wgInfo').click(function(){
    	var Nopage=$('.Nopage').text()*1;
    	var obj={
			tellerId:$('#tellerId').val()
    	}
    	getlist({
	    	pageNo:Nopage,
	    	pageSize:10,
	    	entity:obj
	    });
    	renpage();
    });
    function getlist( obj ){
    	$.ajax({
    		url:'/fx/jsh/getListSafeInfo.do',
    		type:'post',
    		contentType:'application/json',
    		data:JSON.stringify( obj ),
    		async:false,
    		success:function(data){
    			if(data.code==01){
    				userdata= data.codeMessage;
    				dialog.ren({'cols':cols,'wh':wh,'userdata':userdata.list});
    				listnumtotal=userdata.total;
    				/*$('.page').remove();
    				$('.boxtop').append('<div class="page"><span class="first">首页</span><span class="prev">上一页</span><span class="next">下一页</span><span class="last">末页</span><span class="nopage">第<b class="Nopage">'+userdata.pageNum+'</b>页/共<b class="Totalpage">'+userdata.pages+'</b>页</span><span class="totalpage">共<b class="total">'+userdata.total+'</b>条记录</span></div>')
    				*/
    			}else {
    				dialog.ren({'cols':cols,'wh':wh,'userdata':''});
    			}
    		}
    	});
    }
    //点击添加和修改按钮  
    $('.add').click(function(){
    	dialog.tellerAdd('tellerManage','add');
    	 $('.ogcd',parent.document).blur(function(){
			 if($(this).val()==''||$(this).val()==undefined){
				$('.ogcd',parent.document).parent('div').find('re').remove();
				$('.ogcd',parent.document).parent('div').append('<re>机构号不能为空！</re>');
			}else if(!/^\d{0,12}$/.test($(this).val())){
				$('.ogcd',parent.document).parent('div').find('re').remove();
				$('.ogcd',parent.document).parent('div').append('<re>机构号为数字，且最大长度为12位！</re>');
			}else{
				$('.ogcd',parent.document).parent('div').find('re').remove();
			} 
		 })
		  $('.trtl',parent.document).blur(function(){
			 if($(this).val()==''||$(this).val()==undefined){
				$('.trtl',parent.document).parent('div').find('re').remove();
				$('.trtl',parent.document).parent('div').append('<re>交易柜员不能为空！</re>');
			}else{
				$('.trtl',parent.document).parent('div').find('re').remove();
			} 
		 })
		  $('.pass',parent.document).blur(function(){
			 if($(this).val()==''||$(this).val()==undefined){
				$('.pass',parent.document).parent('div').find('re').remove();
				$('.pass',parent.document).parent('div').append('<re>密码不能为空！</re>');
			}else{
				$('.pass',parent.document).parent('div').find('re').remove();
			} 
		 })
		 $('.tltp',parent.document).blur(function(){
			 if($(this).val()==''||$(this).val()==undefined){
				$('.tltp',parent.document).parent('div').find('re').remove();
				$('.tltp',parent.document).parent('div').append('<re>柜员类型不能为空！</re>');
			}else if(!/^\d{0,1}$/.test($(this).val())){
				$('.tltp',parent.document).parent('div').find('re').remove();
				$('.tltp',parent.document).parent('div').append('<re>机构号为数字，最大长度为1位！</re>');
			}else{
				$('.tltp',parent.document).parent('div').find('re').remove();
			} 
		 })
		 $('.bhid',parent.document).blur(function(){
			 if($(this).val()==''||$(this).val()==undefined){
				$('.bhid',parent.document).parent('div').find('re').remove();
				$('.bhid',parent.document).parent('div').append('<re>请求放机构代码不能为空！</re>');
			}else{
				$('.bhid',parent.document).parent('div').find('re').remove();
			} 
		 })
		 $('.chnl',parent.document).blur(function(){
			 if($(this).val()==''||$(this).val()==undefined){
				$('.chnl',parent.document).parent('div').find('re').remove();
				$('.chnl',parent.document).parent('div').append('<re>交易渠道不能为空！</re>');
			}else{
				$('.chnl',parent.document).parent('div').find('re').remove();
			} 
		 })
    })
     $('.modify').click(function(){
    	
    	 if( $('.box tbody tr').hasClass('selected') ){
 			//修改弹出框;
    		 dialog.tellerAdd('tellerManage','modify');
 			$('.ogcd',parent.document).val($('.box tr.selected td:eq(1) span').text());
 			$('.trtl',parent.document).val($('.box tr.selected td:eq(3) span').text()).attr('disabled','disabled');
 			$('.pass',parent.document).val($('.box tr.selected td:eq(4) span').text());
 			$('.tltp',parent.document).val($('.box tr.selected td:eq(5) span').text());
 			$('.bhid',parent.document).val($('.box tr.selected td:eq(2) span').text());
 			$('.chnl',parent.document).val($('.box tr.selected td:eq(6) span').text());
 			$('.ogcd',parent.document).blur(function(){
 				 if($(this).val()==''||$(this).val()==undefined){
 					$('.ogcd',parent.document).parent('div').find('re').remove();
 					$('.ogcd',parent.document).parent('div').append('<re>机构号不能为空！</re>');
 				}else if(!/^\d{0,12}$/.test($(this).val())){
 					$('.ogcd',parent.document).parent('div').find('re').remove();
 					$('.ogcd',parent.document).parent('div').append('<re>机构号为数字，且最大长度为12位！</re>');
 				}else{
 					$('.ogcd',parent.document).parent('div').find('re').remove();
 				} 
 			 })
 			  $('.pass',parent.document).blur(function(){
 				 if($(this).val()==''||$(this).val()==undefined){
 					$('.pass',parent.document).parent('div').find('re').remove();
 					$('.pass',parent.document).parent('div').append('<re>密码不能为空！</re>');
 				}else{
 					$('.pass',parent.document).parent('div').find('re').remove();
 				} 
 			 })
 			 $('.tltp',parent.document).blur(function(){
 				 if($(this).val()==''||$(this).val()==undefined){
 					$('.tltp',parent.document).parent('div').find('re').remove();
 					$('.tltp',parent.document).parent('div').append('<re>柜员类型不能为空！</re>');
 				}else if(!/^\d{0,1}$/.test($(this).val())){
 					$('.tltp',parent.document).parent('div').find('re').remove();
 					$('.tltp',parent.document).parent('div').append('<re>机构号为数字，最大长度为1位！</re>');
 				}else{
 					$('.tltp',parent.document).parent('div').find('re').remove();
 				} 
 			 })
 			 $('.bhid',parent.document).blur(function(){
 				 if($(this).val()==''||$(this).val()==undefined){
 					$('.bhid',parent.document).parent('div').find('re').remove();
 					$('.bhid',parent.document).parent('div').append('<re>请求方机构代码不能为空！</re>');
 				}else{
 					$('.bhid',parent.document).parent('div').find('re').remove();
 				} 
 			 })
 			 $('.chnl',parent.document).blur(function(){
 				 if($(this).val()==''||$(this).val()==undefined){
 					$('.chnl',parent.document).parent('div').find('re').remove();
 					$('.chnl',parent.document).parent('div').append('<re>交易渠道不能为空！</re>');
 				}else{
 					$('.chnl',parent.document).parent('div').find('re').remove();
 				} 
 			 })
    	 }else{
 			dialog.choicedata('请先选择一条数据!','differentCountries','aaa');
 		}
    })
     $('body',parent.document).on('click','.preserve',function(){
		var text=$(this).text(),url,telVo,num=0,
		    tellerId=$('.box tr.selected td:eq(0) span').text(),
		    ogcd=$('.ogcd',parent.document).val(),
		    trtl=$('.trtl',parent.document).val(),
		    pass=$('.pass',parent.document).val(),
		    tltp=$('.tltp',parent.document).val(),
		    bhid=$('.bhid',parent.document).val(),
		    chnl=$('.chnl',parent.document).val();
		if(ogcd==''||ogcd==undefined){
			 $('.ogcd',parent.document).parent('div').find('re').remove();
			 $('.ogcd',parent.document).parent('div').append('<re>机构号不能为空！</re>');
		}else if(!/^\d{0,12}$/.test(ogcd)){
			$('.ogcd',parent.document).parent('div').find('re').remove();
			$('.ogcd',parent.document).parent('div').append('<re>机构号为数字，且最大长度为12位！</re>');
		}else{
			num++;
			$('.ogcd',parent.document).parent('div').find('re').remove();
		}
		if(trtl==''||trtl==undefined){
			 $('.trtl',parent.document).parent('div').find('re').remove();
			 $('.trtl',parent.document).parent('div').append('<re>交易柜员不能为空！</re>');
		}else{
			num++;
			$('.trtl',parent.document).parent('div').find('re').remove();
		}
		if(pass==''||pass==undefined){
			 $('.pass',parent.document).parent('div').find('re').remove();
			 $('.pass',parent.document).parent('div').append('<re>密码不能为空！</re>');
		}else{
			num++;
			$('.pass',parent.document).parent('div').find('re').remove();
		}
		if(tltp==''||tltp==undefined){
			 $('.tltp',parent.document).parent('div').find('re').remove();
			 $('.tltp',parent.document).parent('div').append('<re>柜员类型不能为空！</re>');
		}else if(!/^\d{0,12}$/.test(tltp)){
			$('.tltp',parent.document).parent('div').find('re').remove();
			$('.tltp',parent.document).parent('div').append('<re>柜员类型为数字，且最大长度为12位！</re>');
		}else{
			num++;
			$('.tltp',parent.document).parent('div').find('re').remove();
		}
		if(bhid==''||bhid==undefined){
			 $('.bhid',parent.document).parent('div').find('re').remove();
			 $('.bhid',parent.document).parent('div').append('<re>请求方机构代码不能为空！</re>');
		}else{
			num++;
			$('.bhid',parent.document).parent('div').find('re').remove();
		}
		if(chnl==''||chnl==undefined){
			 $('.chnl',parent.document).parent('div').find('re').remove();
			 $('.chnl',parent.document).parent('div').append('<re>交易渠道不能为空！</re>');
		}else{
			num++;
			$('.chnl',parent.document).parent('div').find('re').remove();
		}
		 
		if(text=='保存'){
			url='/fx/jsh/addSafeInfo.do';
			telVo={
				     'userKey':userkey,
				     'entity':{
				    	 'ogcd':ogcd,
				    	 'trtl':trtl,
				    	 'pass':pass,
				    	 'tltp':tltp,
				    	 'bhid':bhid,
				    	 'chnl':chnl
				     }
		   };
		}else{
			url='/fx/jsh/updateListSafeInfo.do';
			telVo={
				     'userKey':userkey,
				     'entity':{
				    	 'tellerId':tellerId,
				    	 'ogcd':ogcd,
				    	 'trtl':trtl,
				    	 'pass':pass,
				    	 'tltp':tltp,
				    	 'bhid':bhid,
				    	 'chnl':chnl
				     }
		   };
		}
		if(num>=6){
			$.ajax({
				url:url,
				type:'post',
				async:true,
				contentType:'application/json',
	    		data:JSON.stringify(telVo),
				success:function(data){
					if(data.code==01){
						$('.preserve',parent.document).closest('.zhezhao').remove(); 
						dialog.choicedata(data.codeMessage,'differentCountries','aaa');
						getlist({
					    	pageNo:1,
					    	pageSize:10,
					    	entity:{
					    		tellerId:$('#tellerId').val()
					    	}
					    });
						renpage();
					}else{
						dialog.choicedata(data.codeMessage,'differentCountries','aaa');
					}
				}
			});
		}
	 }); 
   
	 /* 删除 */
    $('body',parent.document).on('click','.twosure,.close,.cancel',function(){
		$(this).closest('.zhezhao').remove();
	});
    $('.del').click(function(){
    	if( $('.box tbody tr').hasClass('selected') ){
			//删除弹出框;
			dialog.cancelDate('您确定要删除当前记录吗？','tellerManage','dia_delete');
		 }else{
			dialog.choicedata('请先选择一条数据!','tellerManage','aaa');
		}
    });
    //点击添加修改弹出框中的x、取消按钮；
	$('body',parent.document).on('click','.tellerManage .sure',function(){
		$(this).closest('.zhezhao').remove();
	});
	//点击删除数据 确认+ajax;
	$('body',parent.document).on('click','.tellerManage .confirm',function(){
		var tit=$(this).parents('.dia').data('tit');
		if(tit=='dia_delete'){
			delorreset('/fx/jsh/removeSafeInfo.do','deletesucc');
		}		
	});
	function delorreset(obj,obj1){
		var cymsg={
				'userKey':userkey,
				'entity':{
					'tellerId':$('.box tr.selected td').eq(0).text()
					}
		};
		$.ajax({
			url:obj,
			type:'post',
			async:true,
			contentType:'application/json',
    		data:JSON.stringify(cymsg),
			success:function(data){
				//this指向；
				$('.tellerManage .confirm',parent.document).closest('.zhezhao').remove();
				if(data.code==01){
					if(obj1=='deletesucc'){
						 getlist({
					    	pageNo:1,
					    	pageSize:10,
					    	entity:{
					    		tellerId:$('#tellerId').val()
					    	}
					    });
						 renpage();
					}
					dialog.choicedata(data.codeMessage,'tellerManage','aaa');
				}else{
					dialog.choicedata(data.codeMessage,'tellerManage','aaa');
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
    		    			pageNo:obj.curr,
 					    	pageSize:$('.layui-laypage-limits select option:selected').attr('value')*1,
					    	entity:{
					    		tellerId:$('#tellerId').val()
					    	}
					    });
    		    	}	
    		    }
    		  });
    	});
    }
	 //点击分页;
    $('.boxtop').on('click','.first',function(){
    	var Nopage=$('.Nopage').text()*1;
 	    if(Nopage>1){
 	    	Nopage=1;
 	    	getlist({
	 	       	pageNo:Nopage,
	 	       	pageSize:10,
	 	       	entity:{
	 	       		tellerId:$('#tellerId').val()
	 	       	}
 	       });
 	    }
    });
	$('.boxtop').on('click','.prev',function(){
	    var Nopage=$('.Nopage').text()*1;
	    if(Nopage>1){
	    	Nopage=Nopage-1;
	    	getlist({
	 	       	pageNo:Nopage,
	 	       	pageSize:10,
	 	       	entity:{
	 	       		tellerId:$('#tellerId').val()
	 	       	}
 	       });
	    }
	});
	$('.boxtop').on('click','.next',function(){
		var Nopage=$('.Nopage').text()*1,
			Totalpage=$('.Totalpage').text()*1;
	    if(Nopage<Totalpage){
	    	Nopage=Nopage*1+1;
	    	getlist({
	 	       	pageNo:Nopage,
	 	       	pageSize:10,
	 	       	entity:{
	 	       		tellerId:$('#tellerId').val()
	 	       	}
 	       });
	    }
	});
	$('.boxtop').on('click','.last',function(){
		var Nopage=$('.Nopage').text()*1,
		Totalpage=$('.Totalpage').text()*1;
	    if(Nopage<Totalpage){
	    	Nopage=Totalpage;
	    	getlist({
	 	       	pageNo:Nopage,
	 	       	pageSize:10,
	 	       	entity:{
	 	       		tellerId:$('#tellerId').val()
	 	       	}
 	       });
	    }
	});
});
