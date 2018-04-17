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
    var usnm=sessionStorage.getItem('usnm'),
		product=sessionStorage.getItem('product'),
		user={'usnm':usnm,'prod':product},
		userKey=JSON.parse( sessionStorage.getItem('menu') ).userKey,
		userdata,str='',
		numreg=/^\d+$|^[0]{1}\.?\d+$|^[1-9]+\.?\d+$/,
		pricereg=/^\-?\d+$|^\-?[0]{1}\.?\d+$|^\-?[1-9]+\.?\d+$/;
	 $('.dealer').val( usnm );
	 if(product=="P002"){
		 $('.product').html('<option value="0">结售汇交易</option><option value="1">境外平盘</option><option value="2">金交所平盘</option>')
	 }else if(product=="P003"){
		 $('.product').html('<option value="2">金交所平盘</option>');
	 } 
	 //显示当前日期
	 $('#d12').val(dialog.today());
	 
	 
	var combox=$('.product option:selected').val(),
	    MoZhangVo={'userKey':userKey,'combox':combox};
	    getMoney(MoZhangVo);
	//根据敞口名称请求 买入卖出金额
	$('.product').change(function(){
		combox=$('.product option:selected').val();
		MoZhangVo={'userKey':userKey,'combox':combox};
		getMoney(MoZhangVo);
	
	})
	//封装请求买入卖出金额的方法
    function getMoney(obj){
    	$.ajax({
			url:'/fx/onchange.do',
			type:'post',
			contentType:'application/json',
			async:true,
			data:JSON.stringify(obj),
			success:function(data){
				str="";
				if(data.code==00){
					userdata=JSON.parse( data.codeMessage );
					for(var i=0,num=userdata.length;i<num;i++){
						str+='<option cytp='+userdata[i].cytp+'>'+userdata[i].cyen+'</option>'
					}
					$('.product1,.product2').html( str );
				}else if(data.code==02){
					//dialog.choicedata(data.codeMessage,'userinfo','aaa');
				}
				
			}
		});
    	
    }
	//当买入卖出币种发生变化的时候清空买入卖出金额
	$('.product1,.product2,.product').change(function(){
		 $('.left').val('');
		 $('.right').val('');
		 $('.price').val('');
		//清空买入卖出金额自定义属性 
	   $('.left,.price').removeAttr('direction');
    })	 
				 
	 //校验买入金额 
	 $('.left').blur(function(){
		var bexnm=$('.product1 option:selected').text(),
	        sexnm=$('.product2 option:selected').text(),
	        MoZhangVo={'userKey':userKey,'bexnm':bexnm,'sexnm':sexnm},
	        url="SelectSamt.do",
	        samt=$('.price').val(),
	        pc=$('.right').val(),
	        direction=$(this).attr('direction');
		 $('.right').parent('div').find('r').remove();
		if( $(this).val()=='' ){
			$(this).parent('div').find('r').remove();
			$(this).parent('div').append('<r>买入金额不能为空!</r>');
			if(samt!='' && pc!=''){
			     if(direction=='left'){
			    	 var cR=(pc*samt).toFixed(2);
				    $(this).val(cR);
				}else if(direction=='right'){
					var cR=(samt/pc).toFixed(2);
					$(this).val(cR);
				}
			}
		 }else if( $(this).val()!=''&&!numreg.test( $(this).val() ) ){
			$(this).parent('div').find('r').remove();
			$(this).parent('div').append('<r>买入金额必须为数字!</r>');
		}else{
			 
			$(this).parent('div').find('r').remove();
			checkCurrency(url,MoZhangVo);
			
		}
	 });
	//校验成交汇率
	 $('.right').blur(function(){
		   if( $(this).val()=='' ){
				$(this).parent('div').find('r').remove();
				$(this).parent('div').append('<r>成交汇率不能为空!</r>');
			}else if( $(this).val()!=''&&!pricereg.test( $(this).val() ) ){
				$(this).parent('div').find('r').remove();
				$(this).parent('div').append('<r>成交汇率必须为数字!</r>');
			}else{
				$(this).parent('div').find('r').remove();
				
			}
	 });
	//校验卖出金额
 	$('.price').blur(function(){
 		var bexnm=$('.product1 option:selected').text(),
        sexnm=$('.product2 option:selected').text(),
        MoZhangVo={'userKey':userKey,'bexnm':bexnm,'sexnm':sexnm},
        url="selectBamt.do",
        bamt=$('.left').val(),
        pc=$('.right').val(),
        direction=$(this).attr('direction');
 		$('.right').parent('div').find('r').remove();
		if( $(this).val()=='' ){
			$(this).parent('div').find('r').remove();
			$(this).parent('div').append('<r>卖出金额不能为空!</r>');
			if(bamt!='' && pc!=''){
			     if(direction=='left'){
			    	 var cR=(pc*bamt).toFixed(2);
				    $(this).val(cR);
				}else if(direction=='right'){
					var cR=(bamt/pc).toFixed(2);
					$(this).val(cR);
				}
			}
			
			
		}else if( $(this).val()!=''&&!numreg.test( $(this).val() ) ){
			$(this).parent('div').find('r').remove();
			$(this).parent('div').append('<r>卖出金额必须为数字!</r>');
		}else{
			$(this).parent('div').find('r').remove();
			checkCurrency(url,MoZhangVo);
		}
	 });
 	//校验平盘对手
 	$('.bottom').blur(function(){
 		$('.right').parent('div').find('r').remove();
		if( $(this).val()=='' ){
			$(this).parent('div').find('r').remove();
			$(this).parent('div').append('<r>平盘对手不能为空!</r>');
		}else{
			$(this).parent('div').find('r').remove();
		}
	 });
	 $('.product3,.divcalan i,.bottom').click(function(){
		 var left=$('.left').val(),
	         price=$('.price').val(),pp,
	         direction=$(this).attr('direction');
		     if(direction=='left'){
		    	 if( left!=''&&numreg.test(left )&&price!=''&&numreg.test( price)){
					    pp=(price/left).toFixed(3);
				 }
				 $('.right').val(pp);
		     }else{
		    	 if( left!=''&&numreg.test(left )&&price!=''&&numreg.test( price)){
					    pp=(left/price).toFixed(3);
				 }
				 $('.right').val(pp);
		     }
	  })
	//点击重填；
	 $('.regis_cancel').click(function(){
		 $('.left').val('');
		 $('.right').val('');
		 $('.price').val('');
	 });
	//点击登记；
	$('.regis_add').click(function(){
		var num=0;
		if( $('.left').val()==''){
			 $('.left').parent('div').find('r').remove();
			 $('.left').parent('div').append('<r>买入金额不能为空!</r>');
		     num++;
		}else{
 			 $('.left').parent('div').find('r').remove();
 	    }
		if( $('.right').val()==''){
			 $('.right').parent('div').find('r').remove();
			 $('.right').parent('div').append('<r>成交汇率不能为空!</r>');
			 num++;
		}else{
			 $('.right').parent('div').find('r').remove();
	    }
		if( $('.price').val()==''){
			 $('.price').parent('div').find('r').remove();
			 $('.price').parent('div').append('<r>卖出金额不能为空!</r>');
			 num++;
		}else{
			 $('.price').parent('div').find('r').remove();
	    }
		if( $('.bottom').val()==''){
			 $('.bottom').closest('div').find('r').remove();
			 $('.bottom').closest('div').append('<r>平盘对手不能为空!</r>');
			 num++;
		}else{
			 $('.bottom').closest('div').find('r').remove();
	    }
	    if(num<=0){
	    	dialog.cancelDate('您确定登记数据吗？','pagegoldregister')
	    }
	})
	//点击确定登记按钮
	$('body',parent.document).on('click','.pagegoldregister .confirm',function(){
		var MoZhangVo={
			 "userKey":userKey,
			 "register":{
				 "ckno":$('.product option:selected').val(),
			     "slnm":$('.product2 option:selected').text(),
			     "bynm":$('.product1 option:selected').text(),
			     "slam":$('.price').val(),
			     "byam":$('.left').val(),
			     "expc":$('.right').val(),
			     "cxfg":$('.product3 option:selected').val(),
			     "jsdt":$('#d12').val(),
			     "dshou":$('.bottom').val(),
			     "name":$('.dealer').val
			 }
		}
		$.ajax({
			url:'/fx/goldregisterCK.do',
			type:'post',
			contentType:'application/json',
			data:JSON.stringify(MoZhangVo),
			async:true,
			success:function(data){
				if(data.code==00){
					$('.pagegoldregister .confirm',parent.document).closest('.zhezhao').remove();
					dialog.choicedata(data.codeMessage,'pagegoldregister');
					 
				}else{
					$('.pagegoldregister .confirm',parent.document).closest('.zhezhao').remove();
					dialog.choicedata(data.codeMessage,'pagegoldregister');
				}
			}
		});
	});
	//封装校验货币对存不存在的方法
    function checkCurrency(url,obj){
    	$.ajax({
    		url:url,
    		type:'post',
    		contentType:'application/json',
    		data:JSON.stringify(obj),
    		async:true,
    		success:function(data){
    			 if(data.code==00){
    				  if(data.codeMessage=='left'){
						$('.right').focus(function(){
							 var left=$('.left').val(),
							     price=$('.price').val(),pp;
							 if( left!=''&&numreg.test(left )&&price!=''&&numreg.test( price)){
								  pp=(left/price).toFixed(3);
							 }
							 $(this).val(pp);
						 });
    					
    					//根据返回值left right给买入卖出金额添加自定义属性 用来后来判断买入卖出金额和成交汇率三者之前的关系
						//$('.left,.price').removeAttr('direction'); 
						if(url=='SelectSamt.do'){
	       					$('.left').attr('direction',data.codeMessage) 
	       				 }else if(url=='selectBamt.do'){
	       					 $('.price').attr('direction',data.codeMessage) 
	       				 }
				  }else if(data.codeMessage=='right'){
						 $('.right').focus(function(){
							 //$('.right').parent('div').find('r').remove();
							 var left=$('.left').val(),
							     price=$('.price').val(),pp;
							 if( left!=''&&numreg.test(left )&&price!=''&&numreg.test( price)){
								    pp=(price/left).toFixed(3);
							 }
							 $(this).val(pp);
						 });
					 
						//根据返回值left right给买入卖出金额添加自定义属性 用来后来判断买入卖出金额和成交汇率三者之前的关系
						// $('.left,.price').removeAttr('direction'); 
						 if(url=='SelectSamt.do'){
	       					$('.left').attr('direction',data.codeMessage) 
	       				 }else if(url=='selectBamt.do'){
	       					 $('.price').attr('direction',data.codeMessage) 
	       				 }
				 }
		    				 
				}else if(data.code==01){
					 //币种不存在
					dialog.choicedata('买入币种卖出不存在','pagegoldregister'); 
				}
    		}
    	});
    }
    
 
    
    $('body',parent.document).on('click','.pagegoldregister .sure,.pagegoldregister .cancel',function(){
		$(this,parent.document).closest('.zhezhao').remove();
	});
});

