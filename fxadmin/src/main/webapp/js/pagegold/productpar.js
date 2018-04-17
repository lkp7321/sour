/*产品参数*/
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
	//列参数
    var cols = [
        { title:'用户名称', name:'username' ,width:100, align:'left' },
        { title:'角色', name:'role' ,width:100, align:'left'},
        { title:'机构名称', name:'from' ,width:150, align:'left'},
        { title:'真实姓名', name:'realname' ,width:80, align:'left'},
        { title:'移动电话', name:'phone' ,width:120, align:'left'},
        { title:'电话', name:'tel' ,width:100, align:'left'},
        { title:'传真', name:'cz' ,width:80, align:'left'},
        { title:'email', name:'email' ,width:100, align:'left'},
        { title:'mac/ip', name:'mac' ,width:80, align:'left'},
        { title:'备注', name:'beizhu',width:80, align:'left'},
        { title:'用户状态', name:'status' ,width:80, align:'left'}
    ];
	var mmg = $('.box').mmGrid({
			width:'1180px'
			,height:"580px"
			, cols: cols
            , url: '/fx/js/personaloffer/data.json'
            , method: 'get'
            , nowrap:true
            , fullWidthRows:true
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
	$('.productpara .modify').click(function(){
		
		if( $('.box tbody tr').hasClass('selected') ){
    		dialog.productmodify('productpar','modify');
    		choicedata( $('.box tbody tr.selected td:eq(10) span').text() );
    		$('.pararmrk',parent.document).blur(function(){
    			if($(this).val()==''){
    				$(this).closest('p').find('re').remove();
    				$(this).closest('p').append('<re>参数说明不能为空！</re>');
    			}else{
    				$(this).closest('p').find('re').remove();
    			}
    		});
    		$('.paravalue',parent.document).blur(function(){
    			if($(this).val()==''){
    				$(this).closest('p').find('re').remove();
    				$(this).closest('p').append('<re>参数值不能为空！</re>');
    			}else{
    				$(this).closest('p').find('re').remove();
    			}
    		});
    		$('.paravalue',parent.document).keyup(function(){
    			$(this).val( $(this).val().replace(/[^0-9]/g,'') );
    		})
    	}else{
    		dialog.choicedata('请选择一条数据!','productpar');
    	}
	});
	
	$('body',parent.document).on('click','.productclose,.propacance',function(){
		$(this).closest('.zhezhao').remove();
	});
	$('body',parent.document).on('click','.propasav',function(){
		
		$(this).closest('.zhezhao').remove();
	});
	
	function choicedata(obj){
		if(obj=='正常'){
			$('.start',parent.document).attr('checked','checked');
		}else{
			$('.stop',parent.document).attr('checked','checked');
		}
	}
})
