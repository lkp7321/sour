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
		userkey=JSON.parse( sessionStorage.getItem('menu') ).userKey,
		userdata,str='',
		numreg=/^\d+$|^[0]{1}\.?\d+$|^[1-9]+\.?\d+$/,
		pricereg=/^\-?\d+$|^\-?[0]{1}\.?\d+$|^\-?[1-9]+\.?\d+$/;
	 $('.dealer').val( usnm );
	 //显示当前日期
	 $('#d12').val(dialog.today());
	 $('#d13').val(dialog.today());
	//请求货币对；
	 $.ajax({
			url:'/fx/selectCurrencyPair.do',
			type:'post',
			contentType:'application/json',
			data:userkey,
			async:true,
			success:function(data){
				str="";
				if(data.code==00){
					userdata=JSON.parse( data.codeMessage );
					for(var i=0,num=userdata.length;i<num;i++){
						str+='<option cytp='+userdata[i].CYTP+'>'+userdata[i].CYEN+'</option>'
					}
					$('.product1').html( str );
				}else if(data.code==02){
					dialog.choicedata(data.codeMessage,'openregiste','aaa');
				}
				
			}
		});
	 //请求敞口名称；
	$.ajax({
			url:'/fx/selectCkno.do',
			type:'post',
			contentType:'application/json',
			async:true,
			data:userkey,
			success:function(data){
				str="";
				if(data.code==00){
					userdata=JSON.parse( data.codeMessage );
					for(var i=0,num=userdata.length;i<num;i++){
						str+='<option cytp='+userdata[i].CKNO+'>'+userdata[i].CKNM+'</option>'
					}
					$('.product').html( str );
				}else if(data.code==02){
					dialog.choicedata(data.codeMessage,'openregiste','aaa');
				}
				
			}
		});
	 //请求平盘对手；
	 $.ajax({
		url:'/fx/selectPPDS.do',
		type:'post',
		contentType:'application/json',
		async:true,
		data:userkey,
		success:function(data){
			str="";
			if(data.code==00){
				userdata=JSON.parse( data.codeMessage );
				for(var i=0,num=userdata.length;i<num;i++){
					str+='<option ppcd='+userdata[i].PPCD+'>'+userdata[i].PPDS+'</option>'
				}
				$('.flatoppon').html( str);
			}else if(data.code==02){
				dialog.choicedata(data.codeMessage,'openregiste','aaa');
			}
		}
	});
	 //校验左头寸 右头寸 平盘价格；
	 $('.left').blur(function(){
		 $('.right').parent('div').find('r').remove();
		if( $(this).val()=='' ){
			$(this).parent('div').find('r').remove();
			$(this).parent('div').append('<r>平盘左头寸不能为空!</r>');
		}else if( $(this).val()!=''&&!numreg.test( $(this).val() ) ){
			$(this).parent('div').find('r').remove();
			$(this).parent('div').append('<r>平盘左头寸必须为数字!</r>');
		}else{
			$(this).parent('div').find('r').remove();
		}
	 });
	 $('.right').blur(function(){
			if( $(this).val()=='' ){
				$(this).parent('div').find('r').remove();
				$(this).parent('div').append('<r>平盘右头寸不能为空!</r>');
			}else if( $(this).val()!=''&&!pricereg.test( $(this).val() ) ){
				$(this).parent('div').find('r').remove();
				$(this).parent('div').append('<r>平盘右头寸必须为数字!</r>');
			}else{
				$(this).parent('div').find('r').remove();
			}
		 });
	 	$('.price').blur(function(){
	 		$('.right').parent('div').find('r').remove();
			if( $(this).val()=='' ){
				$(this).parent('div').find('r').remove();
				$(this).parent('div').append('<r>平盘价格不能为空!</r>');
			}else if( $(this).val()!=''&&!numreg.test( $(this).val() ) ){
				$(this).parent('div').find('r').remove();
				$(this).parent('div').append('<r>平盘价格必须为数字!</r>');
			}else{
				$(this).parent('div').find('r').remove();
			}
		 });
	 $('.left').change(function(){
		 $('.right').parent('div').find('r').remove();
		 var left=$(this).val(),
		 	 price=$('.price').val(),
		 	 cuurpair=$('.product1 option:selected').text(),
		 	 pp;
		 if( left!=''&&numreg.test(left )&&price!=''&&numreg.test( price)){
			 console.log( cuurpair.match('JPY') )
			 if( cuurpair.exec('JPY') ){
				
				 pp=(left*price*-1).toFixed(0);
			 }else{
				 pp=(left*price*-1).toFixed(2);
			 }
			 $('.right').val( pp );
		 }
	 });
	 $('.price').change(function(){
		 $('.right').parent('div').find('r').remove();
		 var price=$(this).val(),
		 	left=$('.left').val(),
		 	cuurpair=$('.product1 option:selected').text(),
		 	 pp;
		 if( left!=''&&numreg.test(left )&&price!=''&&numreg.test( price)){
			 if( cuurpair.match('JPY') ){
				 pp=(left*price*-1).toFixed(0);
			 }else{
				 pp=(left*price*-1).toFixed(2);
			 }
		 }
		 $('.right').val( pp );
	 });
	//点击重填；
	 $('.regis_cancel').click(function(){
		 $('.left').val('');
		 $('.right').val('');
		 $('.price').val('');
	 });
	//点击登记；
		$('.regis_add').click(function(){
			console.log(0)
			var num=0;
			if( $('.left').val()==''){
				 $('.left').parent('div').find('r').remove();
				 $('.left').parent('div').append('<r>平盘左头寸不能为空!</r>');
			     num++;
			}else{
	 			 $('.left').parent('div').find('r').remove();
	 	    }
			if( $('.right').val()==''){
				 $('.right').parent('div').find('r').remove();
				 $('.right').parent('div').append('<r>平盘右头寸不能为空!</r>');
				 num++;
			}else{
				 $('.right').parent('div').find('r').remove();
		    }
			if( $('.price').val()==''){
				 $('.price').parent('div').find('r').remove();
				 $('.price').parent('div').append('<r>平盘价格不能为空!</r>');
				 num++;
			}else{
				 $('.price').parent('div').find('r').remove();
		    }
			 
		    if(num<=0){
		    	dialog.cancelDate('您确定登记数据吗？','openregiste')
		    }
		})
		//点击确定登记按钮
		$('body',parent.document).on('click','.openregiste .confirm',function(){
			var MoZhangVo={
				 "userKey":userkey,
				 "register":{
    				 "ckno":$('.product option:selected').attr('cytp'),
                     "currpair":$('.product1 option:selected').text(),
 				     "slam":$('.left').val(),
 				     "byam":$('.right').val(),
                     "expc":$('.price').val(),
 				     "trdt":$('#d12').val(),
					 "jsdt":$('#d13').val(),
 				     "dshou":$('.flatoppon option:selected').text(),
 				     "name":$('.dealer').val()
				 }
			}
			 
			$.ajax({
				url:'/fx/registerCK.do',
				type:'post',
				contentType:'application/json',
				data:JSON.stringify(MoZhangVo),
				async:true,
				success:function(data){
					if(data.code==00){
						$('.openregiste .confirm',parent.document).closest('.zhezhao').remove();
						dialog.choicedata(data.codeMessage,'openregiste');
						 
					}else{
						$('.openregiste .confirm',parent.document).closest('.zhezhao').remove();
						dialog.choicedata(data.codeMessage,'openregiste');
					}
				}
			});
		});
		$('body',parent.document).on('click','.openregiste .sure,.openregiste .cancel',function(){
			$(this,parent.document).closest('.zhezhao').remove();
		});
});

