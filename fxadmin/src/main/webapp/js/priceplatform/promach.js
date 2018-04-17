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
	var userkey=JSON.parse( sessionStorage.getItem('menu') ).userKey,
		numreg=/^[0-9a-zA-Z]+$/;
	$('.boxtop').css('width',ww);
	$('.boxtop').css('height',wh);
	
	//列参数;
    var cols = [
            { title:'产品编号', name:'ptid',width:60,align:'left' },
            { title:'产品类型', name:'transactflag',width:60, align:'left'},
            { title:'总分行价格池', name:'banckflag',width:80, align:'center'},
            { title:'客户价格池', name:'custflag',width:60, align:'center'},
            { title:'是否填写历史价格表', name:'hisflag',width:60,align:'center'},
            { title:'开通状态', name:'usfg',width:60,align:'center'},
            { title:'产品编码-手报', name:'hcid',width:60,align:'left'},
            { title:'备注', name:'prcd',width:60,align:'left'},
    ];
    getlist();
    function getlist(){
    	$.ajax({
    		url:'/fx/price/allPmodPrice.do',
    		type:'post',
    		contentType:'application/json',
    		async:true,
    		success:function(data){
    			if(data.code==01){
    				userdata=JSON.parse( data.codeMessage );
    				dialog.ren({'cols':cols,'wh':wh,'userdata':userdata});
    			}else{
    				dialog.ren({'cols':cols,'wh':wh,'userdata':''});
    			}
    		}
    	});
    }
    //点击添加、修改、删除按钮；
    $('.add').click(function(){
    	dialog.promachadd('promach','add');
    	$('.pronum',parent.document).on('blur',function(){
			if( $('.pronum',parent.document).val()=='' ){
	    		$('.pronum',parent.document).closest('div').find('re').remove();
	    		$('.pronum',parent.document).closest('div').append('<re>产品编号不能为空!</re>');
	    	}else if( !numreg.test( $('.pronum',parent.document).val() ) ){
	    		$('.pronum',parent.document).closest('div').find('re').remove();
	    		$('.pronum',parent.document).closest('div').append('<re>产品编号必须是字母和数字的组合!</re>');
	    	}else{
	    		$('.pronum',parent.document).closest('div').find('re').remove();
	    	}
		});
    });
    $('.modify').click(function(){
		//根据 add中的数据,把角色和机构都渲染出来
		if( $('.box tbody tr').hasClass('selected') ){
			dialog.promachadd('promach','modify');
			
			$('.pronum',parent.document).val( $('.box tr.selected td:eq(0) span').text() );
			$('.proexnm',parent.document).val( $('.box tr.selected td:eq(6) span').text() );
			$('.rmrk',parent.document).val( $('.box tr.selected td:eq(7) span').text() );
			
			if( $('.box tr.selected td:eq(1) span').text()=='非加工'){
				$('.promach input.r1',parent.document).prop('checked','checked');
			}else{
				$('.promach input.r2',parent.document).prop('checked','checked');
			}
			
			if( $('.box tr.selected td:eq(2) span').text()=='停用'){
				$('.promach input.r11',parent.document).prop('checked','checked');
			}else{
				$('.promach input.r22',parent.document).prop('checked','checked');
			}
			if( $('.box tr.selected td:eq(3) span').text()=='停用'){
				$('.promach input.rr1',parent.document).prop('checked','checked');
			}else{
				$('.promach input.rr2',parent.document).prop('checked','checked');
			}
			if( $('.box tr.selected td:eq(4) span').text()=='停用'){
				$('.promach input.re1',parent.document).prop('checked','checked');
			}else{
				$('.promach input.re2',parent.document).prop('checked','checked');
			}
			if( $('.box tr.selected td:eq(5) span').text()=='开通'){
				$('.promach input.rw1',parent.document).prop('checked','checked');
			}else{
				$('.promach input.rw2',parent.document).prop('checked','checked');
			}
			$('.pronum',parent.document).on('blur',function(){
				if( $('.pronum',parent.document).val()=='' ){
		    		$('.pronum',parent.document).closest('div').find('re').remove();
		    		$('.pronum',parent.document).closest('div').append('<re>产品编号不能为空!</re>');
		    	}else if( !numreg.test( $('.pronum',parent.document).val() ) ){
		    		$('.pronum',parent.document).closest('div').find('re').remove();
		    		$('.pronum',parent.document).closest('div').append('<re>产品编号必须是字母和数字的组合!</re>');
		    	}else{
		    		$('.pronum',parent.document).closest('div').find('re').remove();
		    	}
			});
			
		}else{
			dialog.choicedata('请先选择一条数据!','promach');
		}
    });
    $('.del').click(function(){
		if( $('.box tbody tr').hasClass('selected') ){
			//删除弹出框;
			dialog.cancelDate('您确定要删除当前记录吗？','promach','dia_delete');
		 }else{
			dialog.choicedata('请先选择一条数据!','promach');
		}
	});
    $('body',parent.document).on('click','.promach .close,.promach .sure,.promach .cancel',function(){
		$(this).closest('.zhezhao').remove();
	});
    //保存添加和修改；
    $('body',parent.document).on('click','.promach .preserve',function(){
    	
    	if( $('.pronum',parent.document).val()=='' ){
    		$('.pronum',parent.document).closest('div').find('re').remove();
    		$('.pronum',parent.document).closest('div').append('<re>产品编号不能为空!</re>');
    	}else if( !numreg.test( $('.pronum',parent.document).val() ) ){
    		$('.pronum',parent.document).closest('div').find('re').remove();
    		$('.pronum',parent.document).closest('div').append('<re>产品编号必须是字母和数字的组合!</re>');
    	}else{
    		$('.pronum',parent.document).closest('div').find('re').remove();
    	}
    	var dat={
    			userKey:userkey,
    			bean:{
	    			prcd:$('.rmrk',parent.document).val(),
	    			hcid:$('.proexnm',parent.document).val(),
	    			hisflag:$('.promach input[name="r4"]:checked',parent.document).attr('tit'),//0,1:停用，使用 历史价格
	    			transactflag:$('.promach input[name="r1"]:checked',parent.document).attr('tit'),//0，1：非加工，加工
	    			putflag:"1",
	    			usfg:$('.promach input[name="r5"]:checked',parent.document).attr('tit'),//开通状态 0，1：开通，关闭
	    			ptid:$('.pronum',parent.document).val(),
	    			custflag:$('.promach input[name="r3"]:checked',parent.document).attr('tit'),//0,1:停用，使用 客户价格池
	    			banckflag:$('.promach input[name="r2"]:checked',parent.document).attr('tit')//0,1:停用，使用 总分行价格池
    			}
    	};
    	if( $('.promach re',parent.document).length==0 ){
    		$.ajax({
        		url:'/fx/price/savePmodPrice.do',
        		type:'post',
        		contentType:'application/json',
        		async:true,
        		data:JSON.stringify( dat),
        		success:function(data){
        			$('.promach .preserve',parent.document).closest('.zhezhao').remove();
        			if(data.code==01){
        				dialog.choicedata(data.codeMessage,'promach');
        				getlist();
        			}else{
        				dialog.choicedata(data.codeMessage,'promach');
        			}
        		}
        	});
    	}
    });
    //点击确认删除；
    $('body',parent.document).on('click','.promach .confirm',function(){
		$.ajax({
    		url:'/fx/price/delPmodPrice.do',
    		type:'post',
    		contentType:'application/json',
    		async:true,
    		data:JSON.stringify({
    			userKey:userkey,
    			ptid:$('.box tr.selected td:eq(0) span').text()
    		}),
    		success:function(data){
    			$('.promach .confirm',parent.document).closest('.zhezhao').remove();
    			if(data.code==01){
    				dialog.choicedata(data.codeMessage,'promach');
    				$('.box tr.selected').remove();
    			}else{
    				dialog.choicedata(data.codeMessage,'promach');
    			}
    		}
    	});
	});
    
})
