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
		ww=$(window).width()-250+"px";
	$('.boxtop').css('width',ww);
	$('.boxtop').css('height',wh);
	$('.page').css('width',ww);
	//列参数;
    var cols = [
            { title:'国别', name:'NAME',width:100,align:'left' },
            { title:'别名', name:'CMBCCOUT',width:100,align:'left' },
            { title:'外管局国别', name:'COUT',width:100, align:'left'},
            { title:'国别备注', name:'COPYCOUT',width:100, align:'left'}
    ];
    var userKey=JSON.parse( sessionStorage.getItem('menu') ).userKey,
    	listnumtotal,
    	tit=$('title').text(),
    	cmbcreg=/^[A-Z]{2,3}$/,
    	coutreg=/^[A-Z]{3}$/,
    	copyreg=/^[a-zA-Z]{1,}$/;
    //请求列表数据；
    getlist({
    	pageNo:1,
    	pageSize:10,
    	entity:{
    		name:$('#name').val()
    	}
    });
    renpage();
    //请求系统状态查询
    function getlist( obj ){
    	$.ajax({
    		url:'/fx/selectcout.do',
    		type:'post',
    		data:JSON.stringify( obj ),
    		contentType:'application/json',
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
    //点击搜索
    $('.info_serbtn').click(function(){
    	var Nopage=$('.Nopage').text()*1;
    	var obj={
    			name:$('#name').val()
    	}
    	getlist({
	    	pageNo:1,
	    	pageSize:10,
	    	entity:obj
	    });
    	renpage();
    });
    /*page*/
    //点击分页;
    /*$('.boxtop').on('click','.first',function(){
    	var Nopage=$('.Nopage').text()*1;
 	    if(Nopage>1){
 	    	Nopage=1;
 	    	getlist({
	 	       	pageNo:Nopage,
	 	       	pageSize:10,
	 	       	entity:{
	 	       		name:$('#name').val()
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
	 	       		name:$('#name').val()
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
	 	       		name:$('#name').val()
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
	 	       		name:$('#name').val()
	 	       	}
 	       });
	    }
	});*/
	/*添加、修改*/
	$('.add').click(function(){
		dialog.diCountriesAdd('differentCountries','add')
		$('.cname',parent.document).blur(function(){
			 if($(this).val()==''||$(this).val()==undefined){
				$('.cname',parent.document).parent('div').find('re').remove();
				$('.cname',parent.document).parent('div').append('<re>国别不能为空!</re>');
			}else{
				$('.cname',parent.document).parent('div').find('re').remove();
			} 
		 });
		 $('.cmbccout',parent.document).blur(function(){//国别
			 if($(this).val()==''||$(this).val()==undefined){
				$('.cmbccout',parent.document).parent('div').find('re').remove();
				$('.cmbccout',parent.document).parent('div').append('<re>别名不能为空!</re>');
			}else if( !cmbcreg.test( $(this).val() ) ){
				$('.cmbccout',parent.document).parent('div').find('re').remove();
				$('.cmbccout',parent.document).parent('div').append('<re>别名必须是两位或三位的大写字母!</re>');
			}else{
				$('.cmbccout',parent.document).parent('div').find('re').remove();
			} 
		 });
		 $('.cout',parent.document).blur(function(){  //外管别名
			 if($(this).val()==''||$(this).val()==undefined){
				$('.cout',parent.document).parent('div').find('re').remove();
				$('.cout',parent.document).parent('div').append('<re>外管别名不能为空!</re>');
			}else if( !coutreg.test($(this).val() ) ){
				$('.cout',parent.document).parent('div').find('re').remove();
				$('.cout',parent.document).parent('div').append('<re>外管别名必须是三位大写的英文字母!</re>');
			}else{
				$('.cout',parent.document).parent('div').find('re').remove();
			} 
		 });
		 $('.copycout',parent.document).blur(function(){//国别备注
			 if($(this).val()==''||$(this).val()==undefined){
				$('.copycout',parent.document).parent('div').find('re').remove();
				$('.copycout',parent.document).parent('div').append('<re>国别备注不能为空!</re>');
			}else if( !copyreg.test( $(this).val() ) ){
				$('.cout',parent.document).parent('div').find('re').remove();
				$('.cout',parent.document).parent('div').append('<re>国别备注名必须是英文字母!</re>');
			}else{
				$('.copycout',parent.document).parent('div').find('re').remove();
			} 
		 })
	})
	$('.modify').click(function(){
		 if( $('.box tbody tr').hasClass('selected') ){
			//修改弹出框;
			dialog.diCountriesAdd('differentCountries','modify')
			$('.cname',parent.document).val($('.box tr.selected td:eq(0) span').text());
			$('.cmbccout',parent.document).val($('.box tr.selected td:eq(1) span').text()).attr('disabled','disabled');
			$('.cout',parent.document).val($('.box tr.selected td:eq(2) span').text());
			$('.copycout',parent.document).val($('.box tr.selected td:eq(3) span').text());
			$('.cname',parent.document).blur(function(){
				 if($(this).val()==''||$(this).val()==undefined){
					$('.cname',parent.document).parent('div').find('re').remove();
					$('.cname',parent.document).parent('div').append('<re>国别不能为空!</re>');
				}else{
					$('.cname',parent.document).parent('div').find('re').remove();
				} 
			 });
			 $('.cout',parent.document).blur(function(){
				 if($(this).val()==''||$(this).val()==undefined){
					$('.cout',parent.document).parent('div').find('re').remove();
					$('.cout',parent.document).parent('div').append('<re>外管别名不能为空!</re>');
				}else if( !coutreg.test($(this).val() ) ){
					$('.cout',parent.document).parent('div').find('re').remove();
					$('.cout',parent.document).parent('div').append('<re>外管别名必须是三位大写的英文字母!</re>');
				}else{
					$('.cout',parent.document).parent('div').find('re').remove();
				} 
			 });
			 $('.copycout',parent.document).blur(function(){
				 if($(this).val()==''||$(this).val()==undefined){
					$('.copycout',parent.document).parent('div').find('re').remove();
					$('.copycout',parent.document).parent('div').append('<re>国别备注不能为空!</re>');
				}else if( !copyreg.test( $(this).val() ) ){
					$('.cout',parent.document).parent('div').find('re').remove();
					$('.cout',parent.document).parent('div').append('<re>国别备注名必须是英文字母!</re>');
				}else{
					$('.copycout',parent.document).parent('div').find('re').remove();
				} 
			 });
		 }else{
			dialog.choicedata('请先选择一条数据!','differentCountries','aaa');
		}
	})
	 $('body',parent.document).on('click','.preserve',function(){
		var text=$(this).text(),url,num=0,
		    name=$('.cname',parent.document).val(),
	   	    cmbccout=$('.cmbccout',parent.document).val(),
	   	    cout=$('.cout',parent.document).val(),
	   	    copycout=$('.copycout',parent.document).val(),
		    dvVo={
			     'userKey':userKey,
			     'entity':{ 'name':name,  'cmbccout':cmbccout,  'cout':cout, 'copycout':copycout }
			};
		if(name==''||name==undefined){
			 $('.cname',parent.document).parent('div').find('re').remove();
			 $('.cname',parent.document).parent('div').append('<re>国别不能为空!</re>');
		}else{
			num++;
			$('.cname',parent.document).parent('div').find('re').remove();
		}
		if(cmbccout==''||cmbccout==undefined){
			 $('.cmbccout',parent.document).parent('div').find('re').remove();
			 $('.cmbccout',parent.document).parent('div').append('<re>别名不能为空!</re>');  ///别名的最大值是3
		}else if( !cmbcreg.test( cmbccout ) ){
			$('.cmbccout',parent.document).parent('div').find('re').remove();
			$('.cmbccout',parent.document).parent('div').append('<re>别名必须是两位或三位的大写字母!</re>');
		}else{
			num++;
			$('.cmbccout',parent.document).parent('div').find('re').remove();
		}
		if(cout==''||cout==undefined){
			 $('.cout',parent.document).parent('div').find('re').remove();
			 $('.cout',parent.document).parent('div').append('<re>外管别名不能为空!</re>');
		}else if( !coutreg.test( cout ) ){
			$('.cout',parent.document).parent('div').find('re').remove();
			$('.cout',parent.document).parent('div').append('<re>外管别名必须是三位大写的英文字母!</re>');
		}else{
			num++;
			$('.cout',parent.document).parent('div').find('re').remove();  //外管别名最大值是3
		}
		if(copycout==''||copycout==undefined){
			 $('.copycout',parent.document).parent('div').find('re').remove();
			 $('.copycout',parent.document).parent('div').append('<re>国别备注不能为空!</re>');
		}else if( !copyreg.test( copycout ) ){
			$('.cout',parent.document).parent('div').find('re').remove();
			$('.cout',parent.document).parent('div').append('<re>国别备注名必须是英文字母!</re>');
		}else{
			num++;
			$('.copycout',parent.document).parent('div').find('re').remove();
		}
		if(text=='保存'){
			url='/fx/insetrcout.do';
		}else{
			url='/fx/updatecout.do';
		}
		if(num>=4){
			$.ajax({
				url:url,
				type:'post',
				async:true,
				contentType:'application/json',
	    		data:JSON.stringify(dvVo),
				success:function(data){
					if(data.code==01){
						$('.preserve',parent.document).closest('.zhezhao').remove(); 
						dialog.choicedata(data.codeMessage,'differentCountries','aaa');
						getlist({
					    	pageNo:1,
					    	pageSize:10,
					    	entity:{
					    		name:$('#name').val()
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
					    		name:$('#name').val()
					    	}
					    });
    		    	}	
    		    }
    		  });
    	});
    }
	/* 删除 */
    $('body',parent.document).on('click','.differentCountries .twosure',function(){
		$(this).closest('.zhezhao').remove();
	});
    $('.del').click(function(){
    	if( $('.box tbody tr').hasClass('selected') ){
			//删除弹出框;
			dialog.cancelDate('您确定要删除当前记录吗？','differentCountries','dia_delete');
		 }else{
			dialog.choicedata('请先选择一条数据!','differentCountries','aaa');
		}
    });
    //点击添加修改弹出框中的x、取消按钮；
	$('body',parent.document).on('click','.differentCountries .close,.differentCountries .cancel',function(){
		$(this).closest('.zhezhao').remove();
	});
	//点击删除数据 确认+ajax;
	$('body',parent.document).on('click','.differentCountries .confirm',function(){
		var tit=$(this).parents('.dia').data('tit');
		if(tit=='dia_delete'){
			delorreset('/fx/deletecout.do','deletesucc');
		}		
	});
	function delorreset(obj,obj1){
		var cymsg={
			'userKey':userKey,
			'entity':{
				'cmbccout':$('.box tr.selected td').eq(1).text()
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
				$('.differentCountries .confirm',parent.document).closest('.zhezhao').remove();
				if(data.code==01){
					if(obj1=='deletesucc'){
						getlist({
					    	pageNo:1,
					    	pageSize:10,
					    	entity:{
					    		name:$('#name').val()
					    	}
					    });
						renpage();
					}
					dialog.choicedata(data.codeMessage,'differentCountries','aaa');
				}else{
					dialog.choicedata(data.codeMessage,'differentCountries','aaa');
				}
			}
		});
	}
});