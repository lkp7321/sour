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
	//列参数;
    var cols = [
            { title:'编号', name:'PAID',width:70,align:'left' },
            { title:'分类编号', name:'PASUBID',width:70, align:'left'},
            { title:'参数名称', name:'REMK',width:70, align:'left'},
            { title:'系统参数', name:'VALU',width:100, align:'right'},
            { title:'状态', name:'STAT',width:100,align:'center'}
    ];
    var userkey=JSON.parse( sessionStorage.getItem('menu') ).userKey,
    	tit=$('title').text();
    //请求列表数据；
    getlist();
    //请求系统状态查询
    $.ajax({
		url:'/fx/jsh/getCmmSystem.do',
		type:'get',
		contentType:'application/json',
		async:true,
		success:function(data){
			var stat=data.codeMessage;
			if(data.code==01){
				$('.signout .open').attr({
					'stat':stat.STAT,
					'paid':stat.PAID
				});
				if( stat.VALU==0){
					$('.signout .open').prop('checked','checked');
				}else{
					$('.signout .clos').prop('checked','checked');
				}
			}else {
			}
		}
	});
    //点击系统状态，调整开关状态；
    $('.signout input').click(function(){
    	var valu=$(this).attr('valu'),
    		stat=$('.signout .open').attr('stat'),
    		paid=$('.signout .open').attr('paid');
    	var obj={
    		userKey:userkey,
	    		entity:{
		    		valu:valu,
		    		stat:stat,
		    		paid:paid
	    		}
    		}
    	change_stat(obj);
    });
    function change_stat( obj ){ //修改总控状态
    	$.ajax({
    		url:'/fx/jsh/modifyCmmPtpara.do',
    		type:'post',
    		contentType:'application/json',
    		async:true,
    		data:JSON.stringify( obj ),
    		success:function(data){
    			if(data.code==01){
    				userdata= data.codeMessage;
    				dialog.choicedata('系统总控状态'+data.codeMessage,'dtpara');
    			}else {
    				dialog.choicedata('系统总控状态'+data.codeMessage,'dtpara');
    			}
    		}
    	});
    }
    function getlist( obj ){
    	$.ajax({
    		url:'/fx/jsh/getCmmPtpara.do',
    		type:'get',
    		contentType:'application/json',
    		async:true,
    		success:function(data){
    			if(data.code==01){
    				userdata= data.codeMessage;
    				dialog.ren({'cols':cols,'wh':wh,'userdata':data.codeMessage});
    			}else {
    				dialog.ren({'cols':cols,'wh':wh,'userdata':''});
    			}
    		}
    	});
    }
    $('body',parent.document).on('click','.dtpara .twosure,.dtpara .cancel,.dtpara .close',function(){
		$(this).closest('.zhezhao').remove();
	});
    var forreg=/^[a-zA-Z0-9]{4}$/,
    	thrreg=/^[a-zA-Z0-9]{1,5}$/;
    //点击添加、修改、删除；
    $('.add').click(function(){
    	dialog.dtparadd('dtpara','add','系统参数');
    	
		 $('.codenum',parent.document).blur(function(){
			 if($(this).val()==''||$(this).val()==undefined){
					$('.codenum',parent.document).parent('div').find('re').remove();
					$('.codenum',parent.document).parent('div').append('<re>编号不能为空</re>');
				}else if( !forreg.test( $(this).val() ) ){
					$('.codenum',parent.document).parent('div').find('re').remove();
					$('.codenum',parent.document).parent('div').append('<re>编号必须为4位的数字字母组合</re>');
				}else{
					$('.codenum',parent.document).parent('div').find('re').remove();
				} 
		 });
		 $('.clasnum',parent.document).blur(function(){
			 if($(this).val()==''||$(this).val()==undefined){
					$('.clasnum',parent.document).parent('div').find('re').remove();
					$('.clasnum',parent.document).parent('div').append('<re>分类编号不能为空</re>');
				}else if( !thrreg.test( $(this).val() ) ){
					$('.clasnum',parent.document).parent('div').find('re').remove();
					$('.clasnum',parent.document).parent('div').append('<re>分类编号必须为5位以内的英文组合</re>');
				}else{
					$('.clasnum',parent.document).parent('div').find('re').remove();
				} 
		 });
		 $('.sysstat',parent.document).blur(function(){
			 var thi_val=$(this).val().replace(/[\u4E00-\u9FA5]/g, "***");
			 if($(this).val()==''||$(this).val()==undefined){
				$('.sysstat',parent.document).parent('div').find('re').remove();
				$('.sysstat',parent.document).parent('div').append('<re>参数名称不能为空</re>');
			}else if( thi_val.length>50 ){
				$('.sysstat',parent.document).parent('div').find('re').remove();
				$('.sysstat',parent.document).parent('div').append('<re>参数名称必须为50位以内</re>');
			}else{
				$('.sysstat',parent.document).parent('div').find('re').remove();
			} 
		 });
		 $('.valu',parent.document).blur(function(){
			 if($(this).val()==''||$(this).val()==undefined){
				$('.valu',parent.document).parent('div').find('re').remove();
				$('.valu',parent.document).parent('div').append('<re>系统参数不能为空</re>');
			}else{
				$('.valu',parent.document).parent('div').find('re').remove();
			} 
		 });
    });
    $('.modify').click(function(){
		var a=0,arr=[];
		if( $('.box tr.selected').length>0 ){
			a++;
		}
		if(a==0){
			dialog.choicedata('请选择一条数据','dtpara');
		}else{
			dialog.dtparadd('dtpara','modify','系统参数');
			$('.codenum',parent.document).val( $('.box tr.selected').find('td').eq(0).find('span').text() );
			$('.clasnum',parent.document).val( $('.box tr.selected').find('td').eq(1).find('span').text());
			$('.sysstat',parent.document).val( $('.box tr.selected').find('td').eq(2).find('span').text());
			$('.valu',parent.document).val(	$('.box tr.selected').find('td').eq(3).find('span').text());
			
			$('.che_btn input',parent.document).removeProp('checked');
			if( $('.box tr.selected').find('td').eq(4).find('span').text()=='启用'){
				$('.stat',parent.document).prop('checked','checked');
			}else{
				$('.end',parent.document).prop('checked','checked');
			}
			$('.codenum',parent.document).blur(function(){
				 if($(this).val()==''||$(this).val()==undefined){
						$('.codenum',parent.document).parent('div').find('re').remove();
						$('.codenum',parent.document).parent('div').append('<re>编号不能为空</re>');
					}else if( !forreg.test( $(this).val() ) ){
						$('.codenum',parent.document).parent('div').find('re').remove();
						$('.codenum',parent.document).parent('div').append('<re>编号必须为4位的数字字母组合</re>');
					}else{
						$('.codenum',parent.document).parent('div').find('re').remove();
					} 
			 });
			 $('.clasnum',parent.document).blur(function(){
				 if($(this).val()==''||$(this).val()==undefined){
						$('.clasnum',parent.document).parent('div').find('re').remove();
						$('.clasnum',parent.document).parent('div').append('<re>分类编号不能为空</re>');
					}else if( !thrreg.test( $(this).val() ) ){
						$('.clasnum',parent.document).parent('div').find('re').remove();
						$('.clasnum',parent.document).parent('div').append('<re>分类编号必须为5位以内的英文组合</re>');
					}else{
						$('.clasnum',parent.document).parent('div').find('re').remove();
					} 
			 });
			 $('.sysstat',parent.document).blur(function(){
				 var thi_val=$(this).val().replace(/[\u4E00-\u9FA5]/g, "***");
				 if($(this).val()==''||$(this).val()==undefined){
					$('.sysstat',parent.document).parent('div').find('re').remove();
					$('.sysstat',parent.document).parent('div').append('<re>参数名称不能为空</re>');
				}else if( thi_val.length>50 ){
					$('.sysstat',parent.document).parent('div').find('re').remove();
					$('.sysstat',parent.document).parent('div').append('<re>参数名称必须为50位以内</re>');
				}else{
					$('.sysstat',parent.document).parent('div').find('re').remove();
				} 
			 });
			 $('.valu',parent.document).blur(function(){
				 if($(this).val()==''||$(this).val()==undefined){
					$('.valu',parent.document).parent('div').find('re').remove();
					$('.valu',parent.document).parent('div').append('<re>状态不能为空</re>');
				}else{
					$('.valu',parent.document).parent('div').find('re').remove();
				} 
			 });
		}
	 });
    //保存添加和修改弹出框；
    $('body',parent.document).on('click','.dtpara .preserve',function(){
    	var txt=$(this).text(),url,
    		codenum=$('.codenum',parent.document).val(),
    		clasnum=$('.clasnum',parent.document).val(),
    		sysstat=$('.sysstat',parent.document).val(),
    		valu=$('.valu',parent.document).val();
    	
    	if( txt=='添加'){
    		url='/fx/jsh/addCmmPtpara.do';
    	}else if(txt=='修改'){
    		url='/fx/jsh/modifyCmmPtpara.do';
    	}
	     if(codenum==''||codenum==undefined){
			$('.codenum',parent.document).parent('div').find('re').remove();
			$('.codenum',parent.document).parent('div').append('<re>编号不能为空</re>');
		}else if( !forreg.test( codenum ) ){
			$('.codenum',parent.document).parent('div').find('re').remove();
			$('.codenum',parent.document).parent('div').append('<re>编号必须为4位的数字字母组合</re>');
		}else{
			$('.codenum',parent.document).parent('div').find('re').remove();
		} 
		 if(clasnum==''||clasnum==undefined){
			$('.clasnum',parent.document).parent('div').find('re').remove();
			$('.clasnum',parent.document).parent('div').append('<re>分类编号不能为空</re>');
		}else if( !thrreg.test( clasnum ) ){
			$('.clasnum',parent.document).parent('div').find('re').remove();
			$('.clasnum',parent.document).parent('div').append('<re>分类编号必须为5位以内的英文组合</re>');
		}else{
			$('.clasnum',parent.document).parent('div').find('re').remove();
		} 
		var thi_val=sysstat.replace(/[\u4E00-\u9FA5]/g, "***");
		 if(sysstat==''||sysstat==undefined){
			$('.sysstat',parent.document).parent('div').find('re').remove();
			$('.sysstat',parent.document).parent('div').append('<re>参数名称不能为空</re>');
		}else if( thi_val.length>50 ){
			$('.sysstat',parent.document).parent('div').find('re').remove();
			$('.sysstat',parent.document).parent('div').append('<re>参数名称为50位以内</re>');
		}else{
			$('.sysstat',parent.document).parent('div').find('re').remove();
		} 
		 if(valu==''||valu==undefined){
			$('.valu',parent.document).parent('div').find('re').remove();
			$('.valu',parent.document).parent('div').append('<re>系统参数不能为空</re>');
		}else{
			$('.valu',parent.document).parent('div').find('re').remove();
		} 
		 //判断弹出框中没有re，即输入完全符合规则；
		 if( $('.dtpara re',parent.document).length==0 ){
	    	var obj={
	    		userKey:userkey,
		    		entity:{
			    		paid:$('.codenum',parent.document).val(),
			    		pasubid:$('.clasnum',parent.document).val(),
			    		remk:$('.sysstat',parent.document).val(),
			    		valu:$('.valu',parent.document).val(),
			    		stat:$('.che_btn input:checked',parent.document).attr('vlue')
		    		}
	    		}
			 $.ajax({
					url:url,
					type:'post',
					async:true,
					contentType:'application/json',
		    		data:JSON.stringify(obj ),
					success:function(data){
						//this指向；
						$('.preserve',parent.document).closest('.zhezhao').remove();
						if(data.code==01){
							getlist();
							dialog.choicedata(data.codeMessage,'dtpara','aaa');
						}else{
							dialog.choicedata(data.codeMessage,'dtpara','aaa');
						}
					}
				});
		 }
    });
    $('#del').click(function(){
    	if( $('.box tbody tr').hasClass('selected') ){
    		if(  $('.box tbody tr.selected').find('td:eq(4) span').text()=='停用' ){
    			//删除弹出框;
    			dialog.cancelDate('您确定要删除当前记录吗？','dtpara','dia_delete');
    		}else{
    			dialog.choicedata('状态为启用的不可以删除!','dtpara');
    		}
		 }else{
			dialog.choicedata('请先选择一条数据!','dtpara','aaa');
		}
    });
  //点击添加修改弹出框中的x、取消按钮；
	$('body',parent.document).on('click','.dtpara .sure',function(){
		$(this).closest('.zhezhao').remove();
	});
	//点击删除数据 确认+ajax;
	$('body',parent.document).on('click','.dtpara .confirm',function(){
		var tit=$(this).parents('.dia').data('tit');
		if(tit=='dia_delete'){
			var cmm_Ptpara={'userKey':userkey,'entity':{'paid':$('.box tr.selected td').eq(0).text()}};
			$.ajax({
				url:'/fx/jsh/removeCmmPtpara.do',
				type:'post',
				async:true,
				contentType:'application/json',
	    		data:JSON.stringify(cmm_Ptpara),
				success:function(data){
					$('.dtpara .confirm',parent.document).closest('.zhezhao').remove();
					if(data.code==01){
						$('.box tr.selected').remove();
						dialog.choicedata(data.codeMessage,'dtpara','aaa');
						getlist();
					}else{
						dialog.choicedata(data.codeMessage,'dtpara','aaa');
					}
				}
			});
		}		
	});
});
