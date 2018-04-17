/*pagegold-品种管理*/
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
   //列参数
	var cols = [
	     { title:'品种代码', name:'cytp' ,width:150, align:'left' },
	     { title:'品种英文名称', name:'cyen' ,width:150, align:'left'},
	     { title:'品种中文名称', name:'cycn' ,width:150, align:'left'},
	     { title:'启用标志', name:'usfg' ,width:150, align:'center'},
	     { title:'备注', name:'remk' ,width:100, align:'left'}
	 ];
	var usnm=sessionStorage.getItem('usnm'),
	    product=sessionStorage.getItem('product'),
	    userkey=JSON.parse( sessionStorage.getItem('menu') ).userKey,
	    user={'usnm':usnm,'prod':product};
	//请求列表
	thrfn(userkey);
	//添加弹窗
	$('.add').click(function(){
    	dialog.productsmana('productsmana','add');
    	$('.productsmana .breedcode',parent.document).blur(function(){
			if($(this).val()==''||$(this).val()==undefined){
				$(this).parent('p').find('re').remove();
				$(this).parent('p').append('<re>品种代码不能为空</re>');
			}else if( $(this).val()!=''&&$(this).val()!=undefined&& !/^\d{2,}$/.test($(this).val() ) ){		
				$(this).parent('p').find('re').remove();
				$(this).parent('p').append('<re>品种代码必须为两位数字!</re>');
			}else{
				$(this).parent('p').find('re').remove();
			}
		});
    	$('.productsmana .breedEname',parent.document).blur(function(){
			if($(this).val()==''||$(this).val()==undefined){
				$(this).parent('p').find('re').remove();
				$(this).parent('p').append('<re>品种英文名称不能为空</re>');
			}else if( $(this).val()!=''&&$(this).val()!=undefined&& !/^[A-Za-z]{3}$/.test($(this).val() ) ){	
				$(this).parent('p').find('re').remove();
				$(this).parent('p').append('<re>品种英文名称必须为3位的大小写字母组合!</re>');
			}else{
				$(this).parent('p').find('re').remove();
			}
		});
    	$('.productsmana .breedCname',parent.document).blur(function(){
			if($(this).val()==''||$(this).val()==undefined){
				$(this).parent('p').find('re').remove();
				$(this).parent('p').append('<re>中文名称不能为空</re>');
			}else{
				$(this).parent('p').find('re').remove();
			}
		});
    	
    });
	//修改弹窗
    $('.modify').click(function(){
    	if( $('.box tbody tr').hasClass('selected') ){
    		dialog.productsmana('productsmana','modify');
    		var cytp=$('.box tr.selected td:eq(0)').text(),
    		    cyen=$('.box tr.selected td:eq(1)').text(),
    		    cycn=$('.box tr.selected td:eq(2)').text(),
    		    remk=$('.box tr.selected td:eq(4)').text(),
    		    usfg=$('.box tr.selected td:eq(3)').text();
    		$('.breedcode',parent.document).val(cytp).attr('disabled','disabled');
    		$('.breedEname',parent.document).val(cyen);
   		    $('.breedCname',parent.document).val(cycn);
   		    $('.remarks',parent.document).val(remk);
    		if(usfg=="启用"){
    			$('.cer input[type="radio"][value="0"]',parent.document).attr('checked','checked');
    		}else{
    			$('.cer input[type="radio"][value="1"]',parent.document).attr('checked','checked');
    		}
        	$('.productsmana .breedEname',parent.document).blur(function(){
    			if($(this).val()==''||$(this).val()==undefined){
    				$(this).parent('p').find('re').remove();
    				$(this).parent('p').append('<re>品种英文名称不能为空</re>');
    			}else if( $(this).val()!=''&&$(this).val()!=undefined&& !/^\d{2,}$/.test($(this).val() ) ){	
    				$(this).parent('p').find('re').remove();
    				$(this).parent('p').append('<re>品种英文名称必须为3位的大小写字母组合!</re>');
    			}else{
    				$(this).parent('p').find('re').remove();
    			}
    		});
        	$('.productsmana .breedCname',parent.document).blur(function(){
    			if($(this).val()==''||$(this).val()==undefined){
    				$(this).parent('p').find('re').remove();
    				$(this).parent('p').append('<re>中文名称不能为空</re>');
    			}else{
    				$(this).parent('p').find('re').remove();
    			}
    		});
    	}else{
    		dialog.choicedata('请选择一条数据!','productsmana');
    	}
    });
    //添加修改的保存按钮
	$('body',parent.document).on('click','.productsmana .addsav',function(){
   		var cytp=$('.breedcode',parent.document).val(),
   		    cyen=$('.breedEname',parent.document).val(),
   		    cycn=$('.breedCname',parent.document).val(),
   		    remk=$('.remarks',parent.document).val(),
   		    usfg=$('input[type="radio"]:checked',parent.document).val(),
   		    text=$('.pubhead span',parent.document).text().substr(5),url,
   		    cytpstr={"cytp":cytp,"cyen":cyen,"cycn":cycn,"remk":remk,"usfg":usfg};
   		if(text=="添加"){
   		    url="addCytp.do";
   		}else if (text=="修改"){
   			url="modifyCytp.do";
   		}
		if( cytp==''||cytp==undefined){
			$('.productsmana .breedcode',parent.document).parent('p').find('re').remove();
			$('.productsmana .breedcode',parent.document).parent('p').append('<re>品种代码不能为空</re>');
		}else if( cytp!=''&&cytp!=undefined&& !/^\d{2,}$/.test( cytp ) ){		//测试偶问题
			$('.productsmana .breedcode',parent.document).parent('p').find('re').remove();
			$('.productsmana .breedcode',parent.document).parent('p').append('<re>品种代码必须为两位数字!</re>');
		}else{
			$('.productsmana .breedcode',parent.document).parent('p').find('re').remove();
		}
		if( cyen==''||cyen==undefined){
			$('.productsmana .breedEname',parent.document).parent('p').find('re').remove();
			$('.productsmana .breedEname',parent.document).parent('p').append('<re>品种英文名称不能为空</re>');
		}else if( cyen!=''&&cyen!=undefined&& !/^[A-Za-z]{3}$/.test(cyen ) ){	
			$('.productsmana .breedEname',parent.document).parent('p').find('re').remove();
			$('.productsmana .breedEname',parent.document).parent('p').append('<re>品种英文名称必须为3位的大小写字母组合!</re>');
		}else{
			$('.productsmana .breedEname',parent.document).parent('p').find('re').remove();
		}
		if(cycn==''||cycn==undefined){
			$('.productsmana .breedCname',parent.document).parent('p').find('re').remove();
			$('.productsmana .breedCname',parent.document).parent('p').append('<re>中文名称不能为空</re>');
		}else{
			$('.productsmana .breedCname',parent.document).parent('p').find('re').remove();
		}
		
    	if( $('.productsmana re',parent.document).length==0){
    		$.ajax({
    	 		url:url,
    	 		type:'post',
    	 		contentType:'application/json',
    	 		data:JSON.stringify({
    	 			'userKey':userkey,
    	 			'cytp':cytpstr
    	 		}),
    	 		async:true,
    	 		success:function(data){
    	 			if(data.code==01){
    	 			  $('.productsmana .addsav',parent.document).closest('.zhezhao').remove();
    	 			  thrfn(userkey);
    	 			  dialog.choicedata(data.codeMessage,'productsmana');
    	 			
    	 			}else if(data.code==02){
    	 				 dialog.choicedata(data.codeMessage,'productsmana');
    	 			}
    	 		}
         	});
    	}
   	});
    $('.delete').click(function(){
    	if( $('.box tbody tr').hasClass('selected') ){
    		 dialog.cancelDate('您确定要删除当前记录吗?','productsmana');
    	}else{
    	   dialog.choicedata('请选择一条数据!','productsmana');
    	}
    });
   	$('body',parent.document).on('click','.productsmana .confirm',function(){
   		var cytp=$('.box tr.selected td:eq(0)').text(); 
   		$.ajax({
		 		url:"removeCytp.do",
		 		type:'post',
		 		contentType:'application/json',
		 		data:JSON.stringify({
		 			'userKey':userkey,
		 			'cytpName':cytp
		 		}),
		 		async:true,
		 		success:function(data){
		 			 $('.productsmana .confirm',parent.document).closest('.zhezhao').remove();
		 			if(data.code==01){
		 				 dialog.choicedata(data.codeMessage,'productsmana');
		 				 thrfn(userkey);
		 			}else if(data.code==00){
		 				dialog.choicedata(data.codeMessage,'productsmana');
		 			}
		 		}
	  	});
   	});
   	$('body',parent.document).on('click','.productsmana .addcance,.productsmana .close,.productsmana .cancel,.productsmana .sure',function(){
   		$(this).closest('.zhezhao').remove();
   	});
     function thrfn(obj){
    	//请求数据;
        $.ajax({
    		url:"getAllCytp.do",
    		type:'post',
    		contentType:'application/json',
    		data:obj,
    		async:true,
    		success:function(data){
    			if(data.code==01){
    				userdata= data.codeMessage ;
    				dialog.ren({'cols':cols,'wh':wh,'userdata':userdata});
    			}else if(data.code==00){
    				dialog.ren({'cols':cols,'wh':wh,'userdata':''});
    			}
    		}
    	});
    }

})

