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
	//列参数;
    var cols = [
            { title:'币种英文名', name:'CYEN',width:100,align:'left' },
            { title:'币种代码', name:'CYTP',width:100, align:'left'},
            { title:'币种中文名', name:'CYCN',width:100, align:'left'},
            { title:'状态', name:'USFG',width:100, align:'center'},
            { title:'描述', name:'REMK',width:100, align:'left'}
    ];
    //获取产品和用户名；
    var usnm=sessionStorage.getItem('usnm'),
		product=sessionStorage.getItem('product'),
		user={'usnm':usnm,'prod':product},
		userdata;
    var userkey=JSON.parse( sessionStorage.getItem('menu') ).userKey,
    	tit=$('title').text();
    var forreg=/^[a-zA-Z]{3}$/,
    	numreg=/^[0-9]{2}$/,
		thrreg=/^[\u4e00-\u9fa5]{0,10}$/;
    getlist();
    /*-添加-*/
    $('.add').click(function(){
		dialog.cuurfn('cymsg','add','币种管理');
		
		$('.codenum',parent.document).blur(function(){
			 if($(this).val()==''||$(this).val()==undefined){
					$('.codenum',parent.document).parent('div').find('re').remove();
					$('.codenum',parent.document).parent('div').append('<re>币种英文名不能为空</re>');
				}else if( !forreg.test( $(this).val() ) ){
					$('.codenum',parent.document).parent('div').find('re').remove();
					$('.codenum',parent.document).parent('div').append('<re>币种英文名必须为3位的英文</re>');
				}else{
					$('.codenum',parent.document).parent('div').find('re').remove();
				} 
		 });
		$('.clasnum',parent.document).blur(function(){
			 if($(this).val()==''||$(this).val()==undefined){
					$('.clasnum',parent.document).parent('div').find('re').remove();
					$('.clasnum',parent.document).parent('div').append('<re>币种代码不能为空</re>');
				}else if( !numreg.test( $(this).val() ) ){
					$('.clasnum',parent.document).parent('div').find('re').remove();
					$('.clasnum',parent.document).parent('div').append('<re>币种代码必须为两位的纯数字</re>');
				}else{
					$('.clasnum',parent.document).parent('div').find('re').remove();
				} 
		 });
		$('.sysstat',parent.document).blur(function(){
			 if($(this).val()==''||$(this).val()==undefined){
					$('.sysstat',parent.document).parent('div').find('re').remove();
					$('.sysstat',parent.document).parent('div').append('<re>币种中文名不能为空</re>');
				}else if( !thrreg.test( $(this).val() ) ){
					$('.sysstat',parent.document).parent('div').find('re').remove();
					$('.sysstat',parent.document).parent('div').append('<re>币种中文名必须为十位的汉字</re>');
				}else{
					$('.sysstat',parent.document).parent('div').find('re').remove();
				} 
		 });
	});
    /*-修改-*/
    $('.modify').click(function(){
		var a=0,arr=[];
		if( $('.box tr.selected').length>0 ){
			a++;
		}
		if(a==0){
			dialog.choicedata('请选择一条数据','cymsg');
		}else{
			dialog.cuurfn('cymsg','modify','币种管理');
			
			$('.codenum',parent.document).val( $('.box tr.selected').find('td').eq(0).find('span').text() );
			$('.clasnum',parent.document).val( $('.box tr.selected').find('td').eq(1).find('span').text());
			$('.sysstat',parent.document).val( $('.box tr.selected').find('td').eq(2).find('span').text());
			$('.remk',parent.document).val(	$('.box tr.selected').find('td').eq(4).find('span').text());
			
			$('.che_btn input',parent.document).removeProp('checked');
			if( $('.box tr.selected').find('td').eq(3).find('span').text()=='启用'){
				$('.stat',parent.document).prop('checked','checked');
			}else{
				$('.end',parent.document).prop('checked','checked');
			}
			
			$('.codenum',parent.document).blur(function(){
				 if($(this).val()==''||$(this).val()==undefined){
						$('.codenum',parent.document).parent('div').find('re').remove();
						$('.codenum',parent.document).parent('div').append('<re>币种英文名不能为空</re>');
					}else if( !forreg.test( $(this).val() ) ){
						$('.codenum',parent.document).parent('div').find('re').remove();
						$('.codenum',parent.document).parent('div').append('<re>币种英文名必须为3位的英文</re>');
					}else{
						$('.codenum',parent.document).parent('div').find('re').remove();
					} 
			 });
			$('.clasnum',parent.document).blur(function(){
				 if($(this).val()==''||$(this).val()==undefined){
						$('.clasnum',parent.document).parent('div').find('re').remove();
						$('.clasnum',parent.document).parent('div').append('<re>币种代码不能为空</re>');
					}else if( !numreg.test( $(this).val() ) ){
						$('.clasnum',parent.document).parent('div').find('re').remove();
						$('.clasnum',parent.document).parent('div').append('<re>币种代码必须为两位的纯数字</re>');
					}else{
						$('.clasnum',parent.document).parent('div').find('re').remove();
					} 
			 });
			$('.sysstat',parent.document).blur(function(){
				 if($(this).val()==''||$(this).val()==undefined){
						$('.sysstat',parent.document).parent('div').find('re').remove();
						$('.sysstat',parent.document).parent('div').append('<re>币种中文名不能为空</re>');
					}else if( !thrreg.test( $(this).val() ) ){
						$('.sysstat',parent.document).parent('div').find('re').remove();
						$('.sysstat',parent.document).parent('div').append('<re>币种中文名必须为十位的汉字</re>');
					}else{
						$('.sysstat',parent.document).parent('div').find('re').remove();
					} 
			 });
		}
	 });
    /*添加修改保存*/
  //保存添加和修改弹出框；
    $('body',parent.document).on('click','.cymsg .preserve',function(){
    	var txt=$(this).text(),url;
    	var codenum=$('.codenum',parent.document).val(),
    		clasnum=$('.clasnum',parent.document).val(),
    		sysstat=$('.sysstat',parent.document).val();
    	if( txt=='添加'){
    		url='/fx/jsh/addDtCyMsg.do';
    	}else if(txt=='修改'){
    		url='/fx/jsh/modifyDtCyMsg.do';
    	}
		 if(codenum==''||codenum==undefined){
			$('.codenum',parent.document).parent('div').find('re').remove();
			$('.codenum',parent.document).parent('div').append('<re>币种英文名不能为空</re>');
		}else if( !forreg.test( codenum ) ){
			$('.codenum',parent.document).parent('div').find('re').remove();
			$('.codenum',parent.document).parent('div').append('<re>币种英文名必须为3位的英文</re>');
		}else{
			$('.codenum',parent.document).parent('div').find('re').remove();
		} 
		 if(clasnum==''||clasnum==undefined){
			$('.clasnum',parent.document).parent('div').find('re').remove();
			$('.clasnum',parent.document).parent('div').append('<re>币种代码不能为空</re>');
		}else if( !numreg.test( clasnum ) ){
			$('.clasnum',parent.document).parent('div').find('re').remove();
			$('.clasnum',parent.document).parent('div').append('<re>币种代码必须为两位的纯数字</re>');
		}else{
			$('.clasnum',parent.document).parent('div').find('re').remove();
		} 
		 if(sysstat==''||sysstat==undefined){
			$('.sysstat',parent.document).parent('div').find('re').remove();
			$('.sysstat',parent.document).parent('div').append('<re>币种中文名不能为空</re>');
		}else if( !thrreg.test( sysstat ) ){
			$('.sysstat',parent.document).parent('div').find('re').remove();
			$('.sysstat',parent.document).parent('div').append('<re>币种中文名必须为十位的汉字</re>');
		}else{
			$('.sysstat',parent.document).parent('div').find('re').remove();
		} 
		 //判断弹出框中没有re，即输入完全符合规则；
		 if( $('.cymsg re',parent.document).length==0 ){
	    	var obj={
	    		userKey:userkey,
		    		entity:{
		    			cyen:$('.codenum',parent.document).val(),
		    			cytp:$('.clasnum',parent.document).val(),
			    		cycn:$('.sysstat',parent.document).val(),
			    		remk:$('.remk',parent.document).val(),
			    		usfg:$('.che_btn input:checked',parent.document).attr('vlue')
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
							dialog.choicedata(data.codeMessage,'cymsg','aaa');
						}else{
							dialog.choicedata(data.codeMessage,'cymsg','aaa');
						}
					}
				});
		 }
    });
    
    /* 删除 */
    $('body',parent.document).on('click','.cuurencymana .twosure,.cymsg .twosure,.cymsg .close,.cymsg .cancel',function(){
		$(this).closest('.zhezhao').remove();
	});
    $('.del').click(function(){
    	var stat=$('.box tr.selected').find('td').eq(3).find('span').text();
    	if( $('.box tbody tr').hasClass('selected') ){
			//删除弹出框;
    		if( stat==0){
    			dialog.choicedata('请修改该条数据的状态再删除!','cuurencymana','aaa');
    		}else{
    			dialog.cancelDate('您确定要删除当前记录吗？','cuurencymana','dia_delete');
    		}
			
		 }else{
			dialog.choicedata('请先选择一条数据!','cuurencymana','aaa');
		}
    });
    //点击添加修改弹出框中的x、取消按钮；
	$('body',parent.document).on('click','.cuurencymana .sure,.cuurencymana .cancel',function(){
		$(this).closest('.zhezhao').remove();
	});
	//点击删除数据 确认+ajax;
	$('body',parent.document).on('click','.cuurencymana .confirm',function(){
		var tit=$(this).parents('.dia').data('tit');
		if(tit=='dia_delete'){
			delorreset('/fx/jsh/removeDtCyMsg.do','deletesucc');
		}		
	});
	function getlist(){//渲染fn；
    	$.ajax({
    		url:'/fx/jsh/getDtCyMsg.do',
    		type:'post',
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
	function delorreset(obj,obj1){
		var cymsg={
			'userKey':userkey,
			'entity':{
				'cyen':$('.box tr.selected td').eq(0).text(),
				'cytp':$('.box tr.selected td').eq(1).text()
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
				$('.cuurencymana .confirm',parent.document).closest('.zhezhao').remove();
				if(data.code==01){
					if(obj1=='deletesucc'){
						getlist();
					}
					dialog.choicedata(data.codeMessage,'cuurencymana','aaa');
				}else{
					dialog.choicedata(data.codeMessage,'cuurencymana','aaa');
				}
			}
		});
	}
});