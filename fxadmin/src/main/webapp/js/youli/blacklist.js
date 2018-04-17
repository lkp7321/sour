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
		wdatePicker:'./My97DatePicker/WdatePicker',
		dialog:'dialog'
	}
});
require(['jquery','mmGrid','niceScroll','wdatePicker','dialog'],function($,mmGrid,niceScroll,wdatePicker,dialog){
	var wh=$(window).height()-190+"px",
		ww=$(window).width()-250+"px";
	$('.boxtop').css('width',ww);
	$('.boxtop').css('height',wh);
	$('.page').css('width',ww);
	//列参数;
    var cols = [
            { title:'名单编号', name:'user_Seqn',width:100,align:'left' },   //未返回；什么是必须，非必须的值（输入验证判断）；
            { title:'交易日期', name:'trdt',width:100, align:'right'},
            { title:'交易时间', name:'trtm',width:100, align:'right'},
            { title:'更新日期', name:'mddt',width:100, align:'right'},
            { title:'更新时间', name:'mdtm',width:100, align:'right'},
            { title:'客户中文姓名', name:'cunm',width:100, align:'left'},
            { title:'客户英文姓名', name:'cuen',width:100, align:'left'},
            { title:'证件种类', name:'idty',width:100, align:'left'},
            { title:'证件号码', name:'idno',width:100, align:'left'},
            { title:'电话', name:'tlno',width:100, align:'left'},
            { title:'手机号', name:'mbno',width:100, align:'left'},
            { title:'传真号', name:'fxno',width:100, align:'left'},
            { title:'地址', name:'addr',width:100, align:'left'},
            { title:'邮编', name:'mlno',width:100, align:'left'},
            { title:'邮箱', name:'mail',width:100, align:'left'},
            { title:'交易柜员', name:'trtl',width:100, align:'left'},
            { title:'身份标识', name:'idst',width:100, align:'center'},
            { title:'新交易标识', name:'ntst',width:100, align:'center'}
    ];
    //获取产品和用户名；
    var usnm=sessionStorage.getItem('usnm'),
		product=sessionStorage.getItem('product'),
		user={'usnm':usnm,'prod':product},
		userdata;
    var userkey=JSON.parse( sessionStorage.getItem('menu') ).userKey,
    	tit=$('title').text();
    var phonereg=/^1[3|4|5|8|7][0-9]\d{8}$/,
		telreg=/(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$/,
		cunmreg=/^[\u4e00-\u9fa5]{1,10}$/,
		shenfenid=/^(\d{15}$|^\d{18}$|^\d{17}(\d|X|x))$/,
		cuenreg=/^[a-zA-z]{1,40}$/,
		ntstreg=/^\d{1,2}$/,
		youbianreg=/^[1-9]\d{5}(?!\d)$/,
		chuanzhenreg= /^[+]{0,1}(\d){1,3}[ ]?([-]?((\d)|[ ]){1,12})+$/,
		trtlreg=/^[0-9a-zA-Z]{1,8}$/,listnumtotal,
		emailreg = /^(\w)+(\.\w+)*@(\w)+((\.\w+)+)$/;
    getlist({
    	pageNo:1,
    	pageSize:10,
    	entity:{
    		cunm:$('#cunm').val(),
			idno:$('#idno').val()
    	}
    });
    renpage();
    //点击查询按钮;
    $('.selectAttention').click(function(){
    	var obj={
    			cunm:$('#cunm').val(),
    			idno:$('#idno').val()
    	}
    	getlist({
	    	pageNo:1,
	    	pageSize:10,
	    	entity:obj
	    });
    	renpage();
    });
    //增加、修改删除；
    //点击添加按钮；
	$('.add').click(function(){
		dialog.blacklistaadd('blacklist','add','行内关注白名单');
		
		 $('.cunm',parent.document).blur(function(){//客户中文名
			 if($(this).val()==''||$(this).val()==undefined){
					$('.cunm',parent.document).parent('div').find('re').remove();
					$('.cunm',parent.document).parent('div').append('<re>客户中文名不能为空</re>');
				}else if(!cunmreg.test( $(this).val() ) ){
					$('.cunm',parent.document).parent('div').find('re').remove();
					$('.cunm',parent.document).parent('div').append('<re>客户中文名必须是10位以内的汉字</re>');
				}else{
					$('.cunm',parent.document).parent('div').find('re').remove();
				} 
		 });
		 $('.cuen',parent.document).blur(function(){//客户英文名
			 if($(this).val()==''||$(this).val()==undefined){
					$('.cuen',parent.document).parent('div').find('re').remove();
					$('.cuen',parent.document).parent('div').append('<re>货币对不能为空</re>');
				}else if(!cuenreg.test( $(this).val() ) ){
					$('.cuen',parent.document).parent('div').find('re').remove();
					$('.cuen',parent.document).parent('div').append('<re>货币对必须是英文字母</re>');
				}else{
					$('.cuen',parent.document).parent('div').find('re').remove();
				} 
		 });
		 $('.idno',parent.document).blur(function(){//证件号码
			   if($(this).val()==''||$(this).val()==undefined){
					$('.idno',parent.document).parent('div').find('re').remove();
					$('.idno',parent.document).parent('div').append('<re>证件号码不能为空</re>');
				}else if(!shenfenid.test( $(this).val() ) ){
					$('.idno',parent.document).parent('div').find('re').remove();
					$('.idno',parent.document).parent('div').append('<re>请输入正确的证件号码</re>');
				}else{
					$('.idno',parent.document).parent('div').find('re').remove();
				} 
		 });
		 $('.tlno',parent.document).blur(function(){//电话号
			   if($(this).val()==''||$(this).val()==undefined){
					$('.tlno',parent.document).parent('div').find('re').remove();
					$('.tlno',parent.document).parent('div').append('<re>电话号不能为空</re>');
				}else if(!telreg.test( $(this).val() ) ){
					$('.tlno',parent.document).parent('div').find('re').remove();
					$('.tlno',parent.document).parent('div').append('<re>请输入正确的电话号</re>');
				}else{
					$('.tlno',parent.document).parent('div').find('re').remove();
				} 
		 });
		 $('.mbno',parent.document).blur(function(){//手机号
			   if($(this).val()==''||$(this).val()==undefined){
					$('.mbno',parent.document).parent('div').find('re').remove();
					$('.mbno',parent.document).parent('div').append('<re>手机号不能为空</re>');
				}else if(!phonereg.test( $(this).val() ) ){
					$('.mbno',parent.document).parent('div').find('re').remove();
					$('.mbno',parent.document).parent('div').append('<re>请输入正确的手机号</re>');
				}else{
					$('.mbno',parent.document).parent('div').find('re').remove();
				} 
		 });
		 $('.fxno',parent.document).blur(function(){//传真号
			   if($(this).val()==''||$(this).val()==undefined){
					$('.fxno',parent.document).parent('div').find('re').remove();
					$('.fxno',parent.document).parent('div').append('<re>传真号不能为空</re>');
				}else if(!chuanzhenreg.test( $(this).val() ) ){
					$('.fxno',parent.document).parent('div').find('re').remove();
					$('.fxno',parent.document).parent('div').append('<re>请输入正确的传真号</re>');
				}else{
					$('.fxno',parent.document).parent('div').find('re').remove();
				} 
		 });
		 $('.mlno',parent.document).blur(function(){//邮编
			   if($(this).val()==''||$(this).val()==undefined){
					$('.mlno',parent.document).parent('div').find('re').remove();
					$('.mlno',parent.document).parent('div').append('<re>邮编不能为空</re>');
				}else if(!youbianreg.test( $(this).val() ) ){
					$('.mlno',parent.document).parent('div').find('re').remove();
					$('.mlno',parent.document).parent('div').append('<re>请输入正确的邮编</re>');
				}else{
					$('.mlno',parent.document).parent('div').find('re').remove();
				} 
		 });
		 $('.addr',parent.document).blur(function(){//地址
			   if($(this).val()==''||$(this).val()==undefined){
					$('.addr',parent.document).parent('div').find('re').remove();
					$('.addr',parent.document).parent('div').append('<re>地址不能为空</re>');
				}else{
					$('.addr',parent.document).parent('div').find('re').remove();
				} 
		 });
		 $('.mail',parent.document).blur(function(){//邮箱
			   if($(this).val()==''||$(this).val()==undefined){
					$('.mail',parent.document).parent('div').find('re').remove();
					$('.mail',parent.document).parent('div').append('<re>邮箱不能为空</re>');
				}else if(!emailreg.test( $(this).val() ) ){
					$('.mail',parent.document).parent('div').find('re').remove();
					$('.mail',parent.document).parent('div').append('<re>请输入正确的邮箱号</re>');
				}else{
					$('.mail',parent.document).parent('div').find('re').remove();
				} 
		 });
		 $('.ntst',parent.document).blur(function(){//新交易标识
			   if($(this).val()==''||$(this).val()==undefined){
					$('.ntst',parent.document).parent('div').find('re').remove();
					$('.ntst',parent.document).parent('div').append('<re>交易标识不能为空</re>');
				}else if(!ntstreg.test( $(this).val() ) ){
					$('.ntst',parent.document).parent('div').find('re').remove();
					$('.ntst',parent.document).parent('div').append('<re>请输入正确的交易标识</re>');
				}else{
					$('.ntst',parent.document).parent('div').find('re').remove();
				} 
		 });
		 $('.idst',parent.document).blur(function(){//身份标识
			   if($(this).val()==''||$(this).val()==undefined){
					$('.idst',parent.document).parent('div').find('re').remove();
					$('.idst',parent.document).parent('div').append('<re>身份标识不能为空</re>');
				}else if(!ntstreg.test( $(this).val() ) ){
					$('.idst',parent.document).parent('div').find('re').remove();
					$('.idst',parent.document).parent('div').append('<re>请输入正确的身份标识</re>');
				}else{
					$('.idst',parent.document).parent('div').find('re').remove();
				} 
		 });
		 $('.trtl',parent.document).blur(function(){//交易柜员
			   if($(this).val()==''||$(this).val()==undefined){
					$('.trtl',parent.document).parent('div').find('re').remove();
					$('.trtl',parent.document).parent('div').append('<re>交易柜员不能为空</re>');
				}else if(!trtlreg.test( $(this).val() ) ){
					$('.trtl',parent.document).parent('div').find('re').remove();
					$('.trtl',parent.document).parent('div').append('<re>请输入正确的交易柜员</re>');
				}else{
					$('.trtl',parent.document).parent('div').find('re').remove();
				} 
		 });
	});
	  
	//点击修改按钮;
	$('.modify').click(function(){
		var a=0,arr=[];
		if( $('.box tr.selected').length>0){
			a++;
		}
		if(a==0){
			dialog.choicedata('请选择一条数据','blacklist');
		}else if(a==1){
			dialog.blacklistaadd('blacklist','modify','行内关注白名单');
			
			$('.user_seqn',parent.document).val($('.box tr.selected').find('td').eq(0).find('span').text());
			$('.cunm',parent.document).val($('.box tr.selected').find('td').eq(5).find('span').text());
			$('.cuen',parent.document).val($('.box tr.selected').find('td').eq(6).find('span').text());
			//$('.idty',parent.document).val($('.box tr.selected').find('td').eq(7).find('span').text());
			$('.idno',parent.document).val($('.box tr.selected').find('td').eq(8).find('span').text());
			$('.trdt',parent.document).val($('.box tr.selected').find('td').eq(1).find('span').text());
			$('.trtm',parent.document).val($('.box tr.selected').find('td').eq(2).find('span').text());
			$('.mddt',parent.document).val($('.box tr.selected').find('td').eq(3).find('span').text());
			$('.mdtm',parent.document).val($('.box tr.selected').find('td').eq(4).find('span').text());
			$('.tlno',parent.document).val($('.box tr.selected').find('td').eq(9).find('span').text());
			$('.mbno',parent.document).val($('.box tr.selected').find('td').eq(10).find('span').text());
			$('.fxno',parent.document).val($('.box tr.selected').find('td').eq(11).find('span').text());
			$('.addr',parent.document).val($('.box tr.selected').find('td').eq(12).find('span').text());
			$('.mlno',parent.document).val($('.box tr.selected').find('td').eq(13).find('span').text());			
			$('.mail',parent.document).val($('.box tr.selected').find('td').eq(14).find('span').text());			
			$('.ntst',parent.document).val($('.box tr.selected').find('td').eq(17).find('span').text());			
			$('.idst',parent.document).val($('.box tr.selected').find('td').eq(16).find('span').text());			
			$('.trtl',parent.document).val($('.box tr.selected').find('td').eq(15).find('span').text());			
		}
		 $('.cunm',parent.document).blur(function(){//客户中文名
			 if($(this).val()==''||$(this).val()==undefined){
					$('.cunm',parent.document).parent('div').find('re').remove();
					$('.cunm',parent.document).parent('div').append('<re>客户中文名不能为空</re>');
				}else if(!cunmreg.test( $(this).val() ) ){
					$('.cunm',parent.document).parent('div').find('re').remove();
					$('.cunm',parent.document).parent('div').append('<re>客户中文名必须是10位以内的汉字</re>');
				}else{
					$('.cunm',parent.document).parent('div').find('re').remove();
				} 
		 });
		 $('.cuen',parent.document).blur(function(){//客户英文名
			 if($(this).val()==''||$(this).val()==undefined){
					$('.cuen',parent.document).parent('div').find('re').remove();
					$('.cuen',parent.document).parent('div').append('<re>货币对不能为空</re>');
				}else if(!cuenreg.test( $(this).val() ) ){
					$('.cuen',parent.document).parent('div').find('re').remove();
					$('.cuen',parent.document).parent('div').append('<re>货币对必须是英文字母</re>');
				}else{
					$('.cuen',parent.document).parent('div').find('re').remove();
				} 
		 });
		 $('.idno',parent.document).blur(function(){//证件号码
			   if($(this).val()==''||$(this).val()==undefined){
					$('.idno',parent.document).parent('div').find('re').remove();
					$('.idno',parent.document).parent('div').append('<re>证件号码不能为空</re>');
				}else if(!shenfenid.test( $(this).val() ) ){
					$('.idno',parent.document).parent('div').find('re').remove();
					$('.idno',parent.document).parent('div').append('<re>请输入正确的证件号码</re>');
				}else{
					$('.idno',parent.document).parent('div').find('re').remove();
				} 
		 });
		 $('.tlno',parent.document).blur(function(){//电话号
			   if($(this).val()==''||$(this).val()==undefined){
					$('.tlno',parent.document).parent('div').find('re').remove();
					$('.tlno',parent.document).parent('div').append('<re>电话号不能为空</re>');
				}else if(!telreg.test( $(this).val() ) ){
					$('.tlno',parent.document).parent('div').find('re').remove();
					$('.tlno',parent.document).parent('div').append('<re>请输入正确的电话号</re>');
				}else{
					$('.tlno',parent.document).parent('div').find('re').remove();
				} 
		 });
		 $('.mbno',parent.document).blur(function(){//手机号
			   if($(this).val()==''||$(this).val()==undefined){
					$('.mbno',parent.document).parent('div').find('re').remove();
					$('.mbno',parent.document).parent('div').append('<re>手机号不能为空</re>');
				}else if(!phonereg.test( $(this).val() ) ){
					$('.mbno',parent.document).parent('div').find('re').remove();
					$('.mbno',parent.document).parent('div').append('<re>请输入正确的手机号</re>');
				}else{
					$('.mbno',parent.document).parent('div').find('re').remove();
				} 
		 });
		 $('.fxno',parent.document).blur(function(){//传真号
			   if($(this).val()==''||$(this).val()==undefined){
					$('.fxno',parent.document).parent('div').find('re').remove();
					$('.fxno',parent.document).parent('div').append('<re>传真号不能为空</re>');
				}else if(!chuanzhenreg.test( $(this).val() ) ){
					$('.fxno',parent.document).parent('div').find('re').remove();
					$('.fxno',parent.document).parent('div').append('<re>请输入正确的传真号</re>');
				}else{
					$('.fxno',parent.document).parent('div').find('re').remove();
				} 
		 });
		 $('.mlno',parent.document).blur(function(){//邮编
			   if($(this).val()==''||$(this).val()==undefined){
					$('.mlno',parent.document).parent('div').find('re').remove();
					$('.mlno',parent.document).parent('div').append('<re>邮编不能为空</re>');
				}else if(!youbianreg.test( $(this).val() ) ){
					$('.mlno',parent.document).parent('div').find('re').remove();
					$('.mlno',parent.document).parent('div').append('<re>请输入正确的邮编</re>');
				}else{
					$('.mlno',parent.document).parent('div').find('re').remove();
				} 
		 });
		 $('.addr',parent.document).blur(function(){//地址
			   if($(this).val()==''||$(this).val()==undefined){
					$('.addr',parent.document).parent('div').find('re').remove();
					$('.addr',parent.document).parent('div').append('<re>地址不能为空</re>');
				}else{
					$('.addr',parent.document).parent('div').find('re').remove();
				} 
		 });
		 $('.mail',parent.document).blur(function(){//邮箱
			   if($(this).val()==''||$(this).val()==undefined){
					$('.mail',parent.document).parent('div').find('re').remove();
					$('.mail',parent.document).parent('div').append('<re>邮箱不能为空</re>');
				}else if(!emailreg.test( $(this).val() ) ){
					$('.mail',parent.document).parent('div').find('re').remove();
					$('.mail',parent.document).parent('div').append('<re>请输入正确的邮箱号</re>');
				}else{
					$('.mail',parent.document).parent('div').find('re').remove();
				} 
		 });
		 $('.ntst',parent.document).blur(function(){//新交易标识
			   if($(this).val()==''||$(this).val()==undefined){
					$('.ntst',parent.document).parent('div').find('re').remove();
					$('.ntst',parent.document).parent('div').append('<re>交易标识不能为空</re>');
				}else if(!ntstreg.test( $(this).val() ) ){
					$('.ntst',parent.document).parent('div').find('re').remove();
					$('.ntst',parent.document).parent('div').append('<re>请输入正确的交易标识</re>');
				}else{
					$('.ntst',parent.document).parent('div').find('re').remove();
				} 
		 });
		 $('.idst',parent.document).blur(function(){//身份标识
			   if($(this).val()==''||$(this).val()==undefined){
					$('.idst',parent.document).parent('div').find('re').remove();
					$('.idst',parent.document).parent('div').append('<re>身份标识不能为空</re>');
				}else if(!ntstreg.test( $(this).val() ) ){
					$('.idst',parent.document).parent('div').find('re').remove();
					$('.idst',parent.document).parent('div').append('<re>请输入正确的身份标识</re>');
				}else{
					$('.idst',parent.document).parent('div').find('re').remove();
				} 
		 });
		 $('.trtl',parent.document).blur(function(){//交易柜员
			   if($(this).val()==''||$(this).val()==undefined){
					$('.trtl',parent.document).parent('div').find('re').remove();
					$('.trtl',parent.document).parent('div').append('<re>交易柜员不能为空</re>');
				}else if(!trtlreg.test( $(this).val() ) ){
					$('.trtl',parent.document).parent('div').find('re').remove();
					$('.trtl',parent.document).parent('div').append('<re>请输入正确的交易柜员</re>');
				}else{
					$('.trtl',parent.document).parent('div').find('re').remove();
				} 
		 });
	 });
	 
	//点击添加修改弹出框的保存按钮；
	$('body',parent.document).on('click','.preserve',function(){
	    var url,entity,
		    trdt=$('.trdt',parent.document).val(),//交易日期
	 		trtm=$('.trtm',parent.document).val(),//交易时间
	 		mddt=$('.mddt',parent.document).val(),//更新日期
	 		mdtm=$('.mdtm',parent.document).val(),//更新时间
	 		cunm=$('.cunm',parent.document).val(),//客户中文名
	 		cuen=$('.cuen',parent.document).val(),//客户英文名
	 		idty=$('.idty option:selected',parent.document).attr('valu'),//证件种类
	 		idno=$('.idno',parent.document).val(),//证件号码
	 		tlno=$('.tlno',parent.document).val(),//电话
	 		mbno=$('.mbno',parent.document).val(),//手机号
	 		fxno=$('.fxno',parent.document).val(),//传真号
	 		addr=$('.addr',parent.document).val(),//地址
	 		mlno=$('.mlno',parent.document).val(),//邮编
	 		mail=$('.mail',parent.document).val(),//邮箱
	 		trtl=$('.trtl',parent.document).val(),//交易柜员
	 		idst=$('.idst',parent.document).val(),//身份标识
	 		ntst=$('.ntst',parent.document).val();//新交易标识;
	    
	    	//客户中文名
			 if(cunm==''||cunm==undefined){
				$('.cunm',parent.document).parent('div').find('re').remove();
				$('.cunm',parent.document).parent('div').append('<re>客户中文名不能为空</re>');
			}else if(!cunmreg.test( cunm ) ){
				$('.cunm',parent.document).parent('div').find('re').remove();
				$('.cunm',parent.document).parent('div').append('<re>客户中文名必须是10位以内的汉字</re>');
			}else{
				$('.cunm',parent.document).parent('div').find('re').remove();
			}
			 //客户英文名
			 if(cuen==''||cuen==undefined){
				$('.cuen',parent.document).parent('div').find('re').remove();
				$('.cuen',parent.document).parent('div').append('<re>货币对不能为空</re>');
			}else if(!cuenreg.test( cuen ) ){
				$('.cuen',parent.document).parent('div').find('re').remove();
				$('.cuen',parent.document).parent('div').append('<re>货币对必须是英文字母</re>');
			}else{
				$('.cuen',parent.document).parent('div').find('re').remove();
			} 
			//证件号码
		   if(idno==''||idno==undefined){
				$('.idno',parent.document).parent('div').find('re').remove();
				$('.idno',parent.document).parent('div').append('<re>证件号码不能为空</re>');
			}else if(!shenfenid.test( idno ) ){
				$('.idno',parent.document).parent('div').find('re').remove();
				$('.idno',parent.document).parent('div').append('<re>请输入正确的证件号码</re>');
			}else{
				$('.idno',parent.document).parent('div').find('re').remove();
			} 
		   //电话号
		   if(tlno==''||tlno==undefined){
				$('.tlno',parent.document).parent('div').find('re').remove();
				$('.tlno',parent.document).parent('div').append('<re>电话号不能为空</re>');
			}else if(!telreg.test( tlno ) ){
				$('.tlno',parent.document).parent('div').find('re').remove();
				$('.tlno',parent.document).parent('div').append('<re>请输入正确的电话号</re>');
			}else{
				$('.tlno',parent.document).parent('div').find('re').remove();
			} 
		   //手机号
		   if(mbno==''||mbno==undefined){
				$('.mbno',parent.document).parent('div').find('re').remove();
				$('.mbno',parent.document).parent('div').append('<re>手机号不能为空</re>');
			}else if(!phonereg.test( mbno ) ){
				$('.mbno',parent.document).parent('div').find('re').remove();
				$('.mbno',parent.document).parent('div').append('<re>请输入正确的手机号</re>');
			}else{
				$('.mbno',parent.document).parent('div').find('re').remove();
			} 
		   //传真号
		   if(fxno==''||fxno==undefined){
				$('.fxno',parent.document).parent('div').find('re').remove();
				$('.fxno',parent.document).parent('div').append('<re>传真号不能为空</re>');
			}else if(!chuanzhenreg.test( fxno ) ){
				$('.fxno',parent.document).parent('div').find('re').remove();
				$('.fxno',parent.document).parent('div').append('<re>请输入正确的传真号</re>');
			}else{
				$('.fxno',parent.document).parent('div').find('re').remove();
			} 
		   //邮编
		   if(mlno==''||mlno==undefined){
				$('.mlno',parent.document).parent('div').find('re').remove();
				$('.mlno',parent.document).parent('div').append('<re>邮编不能为空</re>');
			}else if(!youbianreg.test( mlno ) ){
				$('.mlno',parent.document).parent('div').find('re').remove();
				$('.mlno',parent.document).parent('div').append('<re>请输入正确的邮编</re>');
			}else{
				$('.mlno',parent.document).parent('div').find('re').remove();
			}
		   //地址
		   if(addr==''||addr==undefined){
				$('.addr',parent.document).parent('div').find('re').remove();
				$('.addr',parent.document).parent('div').append('<re>地址不能为空</re>');
			}else{
				$('.addr',parent.document).parent('div').find('re').remove();
			} 
		 //邮箱
		   if(mail==''||mail==undefined){
				$('.mail',parent.document).parent('div').find('re').remove();
				$('.mail',parent.document).parent('div').append('<re>邮箱不能为空</re>');
			}else if(!emailreg.test( mail ) ){
				$('.mail',parent.document).parent('div').find('re').remove();
				$('.mail',parent.document).parent('div').append('<re>请输入正确的邮箱号</re>');
			}else{
				$('.mail',parent.document).parent('div').find('re').remove();
			} 
		//新交易标识
		   if(ntst==''||ntst==undefined){
				$('.ntst',parent.document).parent('div').find('re').remove();
				$('.ntst',parent.document).parent('div').append('<re>交易标识不能为空</re>');
			}else if(!ntstreg.test( ntst ) ){
				$('.ntst',parent.document).parent('div').find('re').remove();
				$('.ntst',parent.document).parent('div').append('<re>请输入正确的交易标识</re>');
			}else{
				$('.ntst',parent.document).parent('div').find('re').remove();
			} 
		  //身份标识
		   if(idst==''||idst==undefined){
				$('.idst',parent.document).parent('div').find('re').remove();
				$('.idst',parent.document).parent('div').append('<re>身份标识不能为空</re>');
			}else if(!ntstreg.test( idst ) ){
				$('.idst',parent.document).parent('div').find('re').remove();
				$('.idst',parent.document).parent('div').append('<re>请输入正确的身份标识</re>');
			}else{
				$('.idst',parent.document).parent('div').find('re').remove();
			} 
		 $('.trtl',parent.document).blur(function(){//交易柜员
			   if($(this).val()==''||$(this).val()==undefined){
					$('.trtl',parent.document).parent('div').find('re').remove();
					$('.trtl',parent.document).parent('div').append('<re>交易柜员不能为空</re>');
				}else if(!trtlreg.test( $(this).val() ) ){
					$('.trtl',parent.document).parent('div').find('re').remove();
					$('.trtl',parent.document).parent('div').append('<re>请输入正确的交易柜员</re>');
				}else{
					$('.trtl',parent.document).parent('div').find('re').remove();
				} 
		 });
	    if($(this).text()=="添加"){
	    	 url='/fx/insertAttentionUser.do';
	    	 entity={
	 	    		trdt:$('.trdt',parent.document).val(),//交易日期
	 	    		trtm:$('.trtm',parent.document).val(),//交易时间
	 	    		mddt:$('.mddt',parent.document).val(),//更新日期
	 	    		mdtm:$('.mdtm',parent.document).val(),//更新时间
	 	    		cunm:$('.cunm',parent.document).val(),//客户中文名
	 	    		cuen:$('.cuen',parent.document).val(),//客户英文名
	 	    		idty:$('.idty option:selected',parent.document).attr('valu'),//证件种类
	 	    		idno:$('.idno',parent.document).val(),//证件号码
	 	    		tlno:$('.tlno',parent.document).val(),//电话
	 	    		mbno:$('.mbno',parent.document).val(),//手机号
	 	    		fxno:$('.fxno',parent.document).val(),//传真号
	 	    		addr:$('.addr',parent.document).val(),//地址
	 	    		mlno:$('.mlno',parent.document).val(),//邮编
	 	    		mail:$('.mail',parent.document).val(),//邮箱
	 	    		trtl:$('.trtl',parent.document).val(),//交易柜员
	 	    		idst:$('.idst',parent.document).val(),//身份标识
	 	    		ntst:$('.ntst',parent.document).val()//新交易标识
	 	        }
	    }else if($(this).text()=="修改"){
	    	 url='/fx/updateAttentionUser.do';
	    	 entity={
			 	user_Seqn:$('.user_seqn',parent.document).val(),
 	    		trdt:$('.trdt',parent.document).val(),//交易日期
 	    		trtm:$('.trtm',parent.document).val(),//交易时间
 	    		mddt:$('.mddt',parent.document).val(),//更新日期
 	    		mdtm:$('.mdtm',parent.document).val(),//更新时间
 	    		cunm:$('.cunm',parent.document).val(),//客户中文名
 	    		cuen:$('.cuen',parent.document).val(),//客户英文名
 	    		idty:$('.idty option:selected',parent.document).attr('valu'),//证件种类
 	    		idno:$('.idno',parent.document).val(),//证件号码
 	    		tlno:$('.tlno',parent.document).val(),//电话
 	    		mbno:$('.mbno',parent.document).val(),//手机号
 	    		fxno:$('.fxno',parent.document).val(),//传真号
 	    		addr:$('.addr',parent.document).val(),//地址
 	    		mlno:$('.mlno',parent.document).val(),//邮编
 	    		mail:$('.mail',parent.document).val(),//邮箱
 	    		trtl:$('.trtl',parent.document).val(),//交易柜员
 	    		idst:$('.idst',parent.document).val(),//身份标识
 	    		ntst:$('.ntst',parent.document).val()//新交易标识
 	        }
	    }
	    if( $('.blacklist re',parent.document).length==0){
	    	$.ajax({
				url:url,
				type:'post',
				contentType:'application/json',
				data:JSON.stringify({
					userkey:userkey,
					entity:entity
				}),
				async:true,
				success:function(data){
					   $('.preserve',parent.document).closest('.zhezhao').remove();
					 if(data.code==01){
						 //var Nopage=parseInt($('.Nopage').text()),
						 getlist({
					    	pageNo:1,
					    	pageSize:10,
					    	entity:{
					    		cunm:$('#cunm').val(),
								idno:$('#idno').val()
					    	}
					    });
						 renpage();
					 	 dialog.choicedata(data.codeMessage,'blacklist');
					 }else if(data.code==01){
						//异常
						 dialog.choicedata(data.codeMessage,'blacklist');
					}
				}
			}); 
	    }
	 });
	$('.del').click(function(){
		var a=0,arr=[];
		if( $('.box tr.selected').length>0){
			a++;
		}
		if(a==0){
			dialog.choicedata('请选择一条数据','blacklist');
		}else{
			dialog.cancelDate('您确定要删除当前记录吗?','blacklist');
		}
	});
	//点击确认删除按钮
	$('body',parent.document).on('click','.blacklist .confirm',function(){
			var exnm=$('.box tr.selected td:eq(0) span').text(),
			    vo={userKey:userkey,entity:{user_Seqn:exnm}};
		   $.ajax({
				url:'/fx/deleteAttentionUser.do',
				type:'post',
				contentType:'application/json',
				data:JSON.stringify(vo),
				async:true,
				success:function(data){
					 $('.blacklist .confirm',parent.document).closest('.zhezhao').remove();
					 if(data.code==01){
						 getlist({
					    	pageNo:1,
					    	pageSize:10,
					    	entity:{
					    		cunm:$('#cunm').val(),
								idno:$('#idno').val()
					    	}
					    });
						 renpage();
					 	dialog.choicedata(data.codeMessage,'blacklist');
					 }else if(data.code==00){
						//异常
						 dialog.choicedata(data.codeMessage,'blacklist');
					}
				}
			}); 
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
 					    		cunm:$('#cunm').val(),
 								idno:$('#idno').val()
 					    	}
 					    });
    		    	}	
    		    }
    		  });
    	});
    }
	$('body',parent.document).on('click','.blacklist .sure,.blacklist .cancel,.blacklist .close,.blacklist .twosure',function(){
		  $(this).closest('.zhezhao').remove();
	});
	/*------------------------------------------------*/
	$('body',parent.document).on('click','#sT,#simg',function(){
		WdatePicker({
			dateFmt:'yyyyMMdd',
			isShowClear:false,
			isShowToday:false,
			isShowOthers:false,
			isShowOk:false,
			qsEnabled:false,
			position:{left:-236,top:-60}
		}); 
	});
	$('body',parent.document).on('click','.trtm',function(){
		WdatePicker({
			dateFmt:'HH:mm:ss',
			isShowClear:false,
			isShowToday:false,
			isShowOthers:false,
			isShowOk:false,
			qsEnabled:false,
			position:{left:-236,top:-60}
		}); 	
	});
	$('body',parent.document).on('click','.mddt',function(){
		WdatePicker({
			dateFmt:'yyyyMMdd',
			isShowClear:false,
			isShowToday:false,
			isShowOthers:false,
			isShowOk:false,
			qsEnabled:false,
			position:{left:-236,top:-60}
		}); 	
	});
	$('body',parent.document).on('click','.mdtm',function(){
		WdatePicker({
			dateFmt:'HH:mm:ss',
			isShowClear:false,
			isShowToday:false,
			isShowOthers:false,
			isShowOk:false,
			qsEnabled:false,
			position:{left:-236,top:-60}
		}); 	
	});
    function getlist( obj ){
    	$.ajax({
    		url:'/fx/selectAttentionUser.do',
    		type:'post',
    		contentType:'application/json',
    		data:JSON.stringify( obj ),
    		async:false,
    		success:function(data){
    			if(data.code==01){
    				userdata= data.codeMessage;
    				listnumtotal=userdata.total;
    				dialog.ren({'cols':cols,'wh':wh,'userdata':data.codeMessage.list});
    				/*$('.page').remove();
    				$('.boxtop').append('<div class="page"><span class="first">首页</span><span class="prev">上一页</span><span class="next">下一页</span><span class="last">末页</span><span class="nopage">第<b class="Nopage">'+userdata.pageNum+'</b>页/共<b class="Totalpage">'+userdata.pages+'</b>页</span><span class="totalpage">共<b class="total">'+userdata.total+'</b>条记录</span></div>')
    				*/
    			}else {
    				dialog.ren({'cols':cols,'wh':wh,'userdata':''});
    			}
    		}
    	});
    }
});