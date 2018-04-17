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
            { title:'币别对', name:'EXNM',width:100,align:'left' },
            { title:'币别对代码', name:'EXCD',width:100, align:'left'},
            { title:'币别对中文名', name:'EXCN',width:100, align:'left'},
            { title:'价格位点数', name:'PION',width:100, align:'right'}
          /*  { title:'价格类型', name:'EXTP',width:100, align:'left'}*/
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
		thrreg=/^[\u4e00-\u9fa5]{0,10}$/,
		onenum=/^[0-9]{1}$/;
    getlist();
    function getlist(){
    	$.ajax({
    		url:'/fx/jsh/getDtCurrMsg.do',
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
    //更改币种对；
    $('body',parent.document).on('change','.onesel',function(){ 
    	$('.clasnum',parent.document).text( $('.onesel option:selected',parent.document).attr('cytp')+'01' );
    });    
    /*添加修改*/
    $('.add').click(function(){
		dialog.cuurmsgfn('cuurmsg','add','货币对管理');
		$.ajax({
			url:'/fx/jsh/getDtCyMsg.do',
			type:'post',
			contentType:'application/json',
			async:true,
			success:function(data){
				var str='';
				if(data.code==01){
					userdata= data.codeMessage;
					for(var i=0,num=userdata.length;i<num;i++){
						str+='<option cytp='+userdata[i].CYTP+'>'+userdata[i].CYEN+'</option>'
					}
					$('.onesel',parent.document).html( str );
					$('.clasnum',parent.document).text( $('.onesel option:selected',parent.document).attr('cytp')+'01' );
				}else {
					
				}
			}
		});
		//请求币种；		
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
		 $('.pricedot',parent.document).blur(function(){
			 if($(this).val()==''||$(this).val()==undefined){
					$('.pricedot',parent.document).parent('div').find('re').remove();
					$('.pricedot',parent.document).parent('div').append('<re>价格位点数不能为空</re>');
				}else if( !onenum.test( $(this).val() ) ){
					$('.pricedot',parent.document).parent('div').find('re').remove();
					$('.pricedot',parent.document).parent('div').append('<re>价格位点数必须为一位数字</re>');
				}else{
					$('.pricedot',parent.document).parent('div').find('re').remove();
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
			dialog.choicedata('请选择一条数据','cuurmsg');
		}else{
			dialog.cuurmsgfn('cuurmsg','modify','货币对种管理');
			
			$('.codenum',parent.document).val( $('.box tr.selected').find('td').eq(0).find('span').text() );
			$('.clasnum',parent.document).text( $('.box tr.selected').find('td').eq(1).find('span').text() );
			$('.sysstat',parent.document).val( $('.box tr.selected').find('td').eq(2).find('span').text());
			$('.pricedot',parent.document).val(	$('.box tr.selected').find('td').eq(3).find('span').text());
			
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
			 $('.pricedot',parent.document).blur(function(){
				 if($(this).val()==''||$(this).val()==undefined){
						$('.pricedot',parent.document).parent('div').find('re').remove();
						$('.pricedot',parent.document).parent('div').append('<re>价格位点数不能为空</re>');
					}else if( !onenum.test( $(this).val() ) ){
						$('.pricedot',parent.document).parent('div').find('re').remove();
						$('.pricedot',parent.document).parent('div').append('<re>价格位点数必须为一位数字</re>');
					}else{
						$('.pricedot',parent.document).parent('div').find('re').remove();
					} 
			 });
		}
	 });
    /*添加修改保存*/
  //保存添加和修改弹出框；
    $('body',parent.document).on('click','.cuurmsg .preserve',function(){
    	var txt=$(this).text(),url;
    	var sysstat=$('.sysstat',parent.document).val(),
    		pricedot=$('.pricedot',parent.document).val(),
    		arr=[],
    		a=false,excd,exnm;
    	if( txt=='添加'){
    		url='/fx/jsh/addCurrMsg.do';
    		$('.box tr').each(function(i,v){
    			arr.push( $(v).find('td:eq(0) span').text() );
    		});
    		if( $('.onesel option',parent.document).length>0 && arr.indexOf( $('.onesel option:selected',parent.document).text()+'RMB' )==-1 ){
    			a=true;
    		}
    		exnm=$('.onesel option:selected',parent.document).text()+'RMB';
			excd=$('.onesel option:selected',parent.document).attr('cytp')+'01';
    	}else if(txt=='修改'){
    		url='/fx/jsh/modifyCurrMsg.do';
    		a=true;
    		exnm =$('.codenum',parent.document).val() ,
			excd=$('.clasnum',parent.document).text();
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
		 if(pricedot==''||pricedot==undefined){
			$('.pricedot',parent.document).parent('div').find('re').remove();
			$('.pricedot',parent.document).parent('div').append('<re>价格位点数不能为空</re>');
		}else if( !onenum.test( pricedot) ){
			$('.pricedot',parent.document).parent('div').find('re').remove();
			$('.pricedot',parent.document).parent('div').append('<re>价格位点数必须为一位数字</re>');
		}else{
			$('.pricedot',parent.document).parent('div').find('re').remove();
		} 
		 //判断弹出框中没有re，即输入完全符合规则；
		 
		 if(  $('.cuurmsg re',parent.document).length==0 && a ){
	    	var obj={
	    		userKey:userkey,
		    		entity:{
	    				exnm :exnm ,
	    				excd:excd,
	    				pion:$('.pricedot',parent.document).val(),
	    				excn:$('.sysstat',parent.document).val(),
	    				extp:''
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
							dialog.choicedata(data.codeMessage,'cuurmsg','aaa');
						}else{
							dialog.choicedata(data.codeMessage,'cuurmsg','aaa');
						}
					}
				});
		 }
    });
    /*添加、修改结束*/
    /* 删除 */
    $('body',parent.document).on('click','.cuurpairmana .twosure,.cuurmsg .twosure,.cuurmsg .close,.cuurmsg .cancel',function(){
		$(this).closest('.zhezhao').remove();
	});
    $('.del').click(function(){
    	if( $('.box tbody tr').hasClass('selected') ){
			//删除弹出框;
			dialog.cancelDate('您确定要删除当前记录吗？','cuurpairmana','dia_delete');
		 }else{
			dialog.choicedata('请先选择一条数据!','cuurpairmana','aaa');
		}
    });
    //点击添加修改弹出框中的x、取消按钮；
	$('body',parent.document).on('click','.cuurpairmana .sure,.cuurpairmana .cancel',function(){
		$(this).closest('.zhezhao').remove();
	});
	//点击删除数据 确认+ajax;
	$('body',parent.document).on('click','.cuurpairmana .confirm',function(){
		var tit=$(this).parents('.dia').data('tit');
		if(tit=='dia_delete'){
			delorreset('/fx/jsh/removeCurrMsg.do','deletesucc');
		}		
	});
	function delorreset(obj,obj1){
		var cuurmsg={
				'userKey':userkey,
				'entity':{
					'exnm':$('.box tr.selected td').eq(0).text(),
					'excd':$('.box tr.selected td').eq(1).text()
					}
		};
		$.ajax({
			url:obj,
			type:'post',
			async:true,
			contentType:'application/json',
    		data:JSON.stringify(cuurmsg),
			success:function(data){
				//this指向；
				$('.cuurpairmana .confirm',parent.document).closest('.zhezhao').remove();
				if(data.code==01){
					if(obj1=='deletesucc'){
						getlist();
					}
					dialog.choicedata(data.codeMessage,'cuurpairmana','aaa');
				}else{
					dialog.choicedata(data.codeMessage,'cuurpairmana','aaa');
				}
			}
		});
	}
});