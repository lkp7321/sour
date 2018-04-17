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
	$('#page').css('width',ww);
	//列参数;
    var cols = [
            { title:'用户名称', name:'usnm',width:55,align:'left' },
            { title:'角色', name:'ptnm',width:60, align:'left'},
            { title:'机构名称', name:'ognm',width:80, align:'left'},
            { title:'真实姓名', name:'cmpt',width:60, align:'left'},
            { title:'移动电话', name:'mbtl',width:60,align:'left'},
            { title:'电话', name:'telp',width:60, align:'left'},
            { title:'传真', name:'ufax',width:30, align:'left'},
            { title:'EMAIL', name:'mail',width:40, align:'left'},
            { title:'MAC/IP', name:'macip',width:30, align:'left'},
            { title:'用户状态', name:'usfg',width:40, align:'left'},
            { title:'备注', name:'rmrk',width:30, align:'left'}
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
		emailreg = /^(\w)+(\.\w+)*@(\w)+((\.\w+)+)$/,
		renamereg=/^[a-zA-Z0-9]{2,}|[\u4e00-\u9fa5]{2,}$/,
		psdreg=/^.{6,20}$/,
		repeat=/(.)\1\1/;
    	//点击修改添加按钮
    var juesedata=false,jigoudata=false,jueseda,jigouda,listnumtotal;
   
    //请求列表数据；
    getlist({
    	'userKey':userkey,
    	'pageNo':1,
    	'pageSize':10
    });
    renpage();
    function getlist( obj ){
    	$.ajax({
    		url:'/fx/listUser.do',
    		type:'post',
    		contentType:'application/json',
    		data:JSON.stringify( obj ),
    		async:false,
    		success:function(data){
    			if(data.code==00){
    				userdata= data.codeMessage ;
    				dialog.ren({'cols':cols,'wh':wh,'userdata':userdata.list});
    				listnumtotal=userdata.total;
    			}else if(data.code==01){
    				dialog.ren({'cols':cols,'wh':wh,'userdata':''});
    			}
    			
    		}
    	});
    }
	$('.add').click(function(){
		var loginuser=user,str,str1;
		dialog.add('user');
		if(juesedata==true){
			//juesedata=true;
			renjuese({'obj':'.juese select','data':jueseda});
		}else{
			//请求角色；
			$.ajax({
				url:'/fx/toInitRole.do',
				type:'ost',
				async:true,
				data:userkey,
	 			dataType:'json',
	 			contentType:'application/json;charset=utf-8',
				success:function(data){
					var roledata=JSON.parse(data.codeMessage);
						jueseda=roledata;
					if(data.code==00){
						juesedata=true;
						renjuese({'obj':'.juese select','data':roledata});
						/*for(var i in roledata){
							str+='<option value='+roledata[i].ptid+'>'+roledata[i].ptnm+'</option>'
						}
						$('.juese select',parent.document).html(str);*/
					}else{
						juesedata=false;
					}
				}
			});
		}
		
		if(jigoudata==true){
			//jigoudata=true;
			renjigou({'obj':'.six .sel_mo select','data':jigouda});
		}else{
			//请求机构
			$.ajax({
				url:'/fx/toInitOG.do',
				type:'post',
				async:true,
				data:userkey,
	 			dataType:'json',
	 			contentType:'application/json;charset=utf-8',
				success:function(data){
					var roledata=JSON.parse(data.codeMessage);
					jigouda=roledata;
					if(data.code==00){
						jigoudata=true;
						renjigou({'obj':'.six .sel_mo select','data':roledata});
						/*for(var i in roledata){
							str1+='<option value='+roledata[i].ogcd+'>'+roledata[i].ognm+'</option>'
						}
						$('.six .sel_mo select',parent.document).html(str1);*/
					}else{
						jigoudata=false;
					}
				}
			});
		}
		
		//个人信息-添加-信息判断;
		$('.uername',parent.document).blur(function(){
			checkusername( $(this).val(),'uername');
		});
		$('.psd',parent.document).blur(function(){
			checkpsd( $(this).val(),'psd');
		});
		$('.realname',parent.document).blur(function(){
			checkrealname( $(this).val(),'realname' );
		});
		$('.surepsd',parent.document).blur(function(){
			checksurepsd($(this).val(),'surepsd' );
		});
		$('.phone',parent.document).blur(function(){
			checkphone($(this).val(),'phone');
		});
		$('.tel',parent.document).blur(function(){
			checktel( $(this).val(),'tel');
		});
		$('.email',parent.document).blur(function(){
			checkemail( $(this).val(),'email' );
		});
	});
	function renjuese(obj){
		str1='<option>请选择</option>';
		for(var i in obj.data){
			str1+='<option value='+obj.data[i].ptid+'>'+obj.data[i].ptnm+'</option>'
		}
		$(obj.obj,parent.document).html(str1);
	}
	function renjigou(obj){
		str1='<option>请选择</option>';
		for(var i in obj.data){
			str1+='<option value='+obj.data[i].ogcd+'>'+obj.data[i].ognm+'</option>'
		}
		$(obj.obj,parent.document).html(str1);
	}
	//获取角色和机构的时候，根据后台提示
	$('body',parent.document).on('click','.juese select',function(){
		if(juesedata=false){
			dialog.choicedata(jueseda,'userinfo','aaa');
		}
	});
	$('body',parent.document).on('click','.six .sel_mo select',function(){
		if(jigoudata=false){
			dialog.choicedata(jigouda,'userinfo','aaa');
		}
	});
	///点击修改；
	$('.modify').click(function(){
		var str='',str1='',checkName;
		//根据 add中的数据,把角色和机构都渲染出来
		if( $('.box tbody tr').hasClass('selected') ){
			 checkName=$('.box tr.selected td:eq(0) span').text();
			 if( checkName=='Administrator'){  ///------如果是超级管理员
				 dialog.choicedata('该用户是超级管理员,您不能对该用户进行操作!','userIfo');
			 }else{
				dialog.add('change');
				//个人信息-修改-信息判断;
				$('.cmpt',parent.document).blur(function(){
					checkrealname( $(this).val(),'cmpt' );
				});
				$('.mbtl',parent.document).blur(function(){
					checkphone($(this).val(),'mbtl');
				});
				$('.telp',parent.document).blur(function(){
					checktel( $(this).val(),'telp');
				});
				$('.mail',parent.document).blur(function(){
					checkemail( $(this).val(),'mail');
				});
				if(juesedata==true){
					renjuese({'obj':'.sel_mo1 select','data':jueseda});
					$('.sel_mo1 select option',parent.document).each(function(i,v){
						if($(v).text()==$('.box tr.selected td:eq(1) span').text() ){
							$(v).attr('selected','selected');
						}
					});
				}else{
					//请求角色；
					$.ajax({
						url:'/fx/toInitRole.do',
						type:'post',
						async:true,
						data:userkey,
			 			dataType:'json',
			 			contentType:'application/json;charset=utf-8',
						success:function(data){
							var roledata=JSON.parse(data.codeMessage);
							jueseda=roledata;
							if(data.code==00){
								juesedata=true;
								renjuese({'obj':'.sel_mo1 select','data':roledata});
								/*for(var i in roledata){
									str+='<option value='+roledata[i].ptid+'>'+roledata[i].ptnm+'</option>'
								}
								$('.sel_mo1 select',parent.document).html(str);*/
							}else{
								juesedata=false;
							}
						},
						complete:function(){
							//根据选中的,更改selected
							$('.sel_mo1 select option',parent.document).each(function(i,v){
								if($(v).text()==$('.box tr.selected td:eq(1) span').text() ){
									$(v).attr('selected','selected');
								}
							});
						}
					});
				}
				if(jigoudata==true){
					renjigou({'obj':'.six .sel_mo select','data':jigouda});
					$('.six .sel_mo select option',parent.document).each(function(i,v){
						if($(v).text()==$('.box tr.selected td:eq(2) span').text() ){
							$(v).attr('selected','selected');
						}
					});
				}else{
					//请求机构
					$.ajax({
						url:'/fx/toInitOG.do',
						type:'post',
						async:true,
						data:userkey,
			 			dataType:'json',
			 			contentType:'application/json;charset=utf-8',
						success:function(data){
							var roledata=JSON.parse(data.codeMessage);
							jigouda=roledata;
							if(data.code==00){
								jigoudata=true;
								renjigou({'obj':'.six .sel_mo select','data':roledata});
								/*for(var i in roledata){
									str1+='<option value='+roledata[i].ogcd+'>'+roledata[i].ognm+'</option>'
								}
								$('.six .sel_mo select',parent.document).html(str1);*/
							}else{
								jigoudata=false;
							}
						},
						complete:function(){
							$('.six .sel_mo select option',parent.document).each(function(i,v){
								if($(v).text()==$('.box tr.selected td:eq(2) span').text() ){
									$(v).attr('selected','selected');
								}
							});
						}
					});
				}
				
				//修改弹出框;+获取当前selected的数据;
				$('.usnm',parent.document).text( $('.box tr.selected td:eq(0) span').text() ); //用户名
				$('.cmpt',parent.document).val( $('.box tr.selected td:eq(3) span').text() );//真实姓名	
				
				$('.ufax',parent.document).val( $('.box tr.selected td:eq(6) span').text() );//传真
				$('.macip',parent.document).val($('.box tr.selected td:eq(8) span').text() );
				$('.mail',parent.document).val( $('.box tr.selected td:eq(7) span').text() ); 
				$('.telp',parent.document).val( $('.box tr.selected td:eq(5) span').text() );
				$('.mbtl',parent.document).val( $('.box tr.selected td:eq(4) span').text() );
				$('.rmrk',parent.document).val( $('.box tr.selected td:eq(10) span').text() );
			 }
		}else{
			dialog.choicedata('请先选择一条数据!','userinfo','aaa');
		}
	});
	$('.del').click(function(){
		if( $('.box tbody tr').hasClass('selected') ){
			//删除弹出框;
			dialog.cancelDate('您确定要删除当前记录吗？','userinfo','dia_delete');
		 }else{
			dialog.choicedata('请先选择一条数据!','userinfo','aaa');
		}
	});
	$('.resetpsd').click(function(){
		if( $('.box tbody tr').hasClass('selected') ){
			//确认重置弹出框;
            dialog.cancelDate('您确定要重置该用户的密码吗？','userinfo','dia_res');
		}else{
			dialog.choicedata('请先选择一条数据!','userinfo','aaa');
		}
	});
	//点击删除数据 确认+ajax;
	$('body',parent.document).on('click','.userinfo .confirm',function(){
		var tit=$(this).parents('.dia').data('tit');
		if(tit=='dia_delete'){
			delorreset('deleteUser.do','deletesucc');
		}else if(tit=='dia_res'){
			delorreset('comitPswd.do','aaa');
		}		
	});
	//问题  document is null;
	function delorreset(obj,obj1){
		var userVo={'userKey':userkey,'user':{'usnm':$('.box tr.selected td').eq(0).text()}};
		$.ajax({
			url:obj,
			type:'post',
			async:true,
			contentType:'application/json',
    		data:JSON.stringify(userVo),
			success:function(data){
				if(data.code==00){
					//this指向；
					$('.userinfo .confirm',parent.document).closest('.zhezhao').remove();
					if(obj1=='deletesucc'){
						$('.box tbody tr.selected').remove();
					}
					dialog.choicedata(data.codeMessage,'userinfo','aaa');
				}else{
					dialog.choicedata(data.codeMessage,'userinfo','aaa');
				}
			}
		});
	}
	$('body',parent.document).on('click','.userinfoadd .userinfosav',function(){		
		//点击添加弹出框确定按钮;
		var username=$('.uername',parent.document).val(),
			psd=$('.psd',parent.document).val(),
			realname=$('.realname',parent.document).val(),
			surpsd=$('.surepsd',parent.document).val(),
			phone=$('.phone',parent.document).val(),
			ufax=$('.ufax',parent.document).val(),
			juese=$('.juese select option:selected',parent.document).attr('value'),
			tel=$('.tel',parent.document).val(),
			email=$('.email',parent.document).val(),
			jigou=$('.sel_mo select option:selected',parent.document).attr('value'),
			rmrk=$('.rmrk',parent.document).val(),
			ip=$('.macip',parent.document).val(),
			ufax=$('.fax',parent.document).val(),
			user={'usnm':username,'cmpt':realname,'pswd':psd,'ufax':ufax,'macip':ip,'mail':email,'telp':tel,'mbtl':phone,'rmrk':rmrk,'cstn':jigou,'uspt':juese};
			
			checkusername(username,'uername');
			checkpsd(psd,'psd');
			checkrealname(realname,'realname');
			checksurepsd(surpsd,'surepsd');
			checkphone(phone,'phone');
			checktel(tel,'tel');
			checkemail(email,'email');
			
		if($('.cer re',parent.document).length<=0 ){
		   $.ajax({
				url:'/fx/addUser.do',
				type:'post',
				async:false,
				data:JSON.stringify( {'userKey':userkey,'user':user }),
         		dataType:'json',
         		contentType:'application/json;charset=utf-8',
				success:function(data){
					$('.userinfosav',parent.document).closest('.zhezhao').remove();
					if(data.code==00){
						//render2( 'listUser.do' );
						dialog.choicedata(data.codeMessage+'!','userinfo','aaa');
						getlist({
					    	'userKey':userkey,
					    	'pageNo':1,
					    	'pageSize':10
					    });
						renpage();
					}else if(data.code==01){
						dialog.choicedata(data.codeMessage+'!','userinfo','aaa');
					}
				}
			});
		} 
	});
	//选择一条数据的确定按钮;
	$('body',parent.document).on('click','.userinfo .twosure',function(){
		var a=$('.userinfo',parent.document).data('tit'),url='';
		if( a=='success'){   //success是成功，需要再次请求列表数据;
			
		}/*else if(a=='deletesucc'){
			$(this).closest('.userinfo').remove();
		}*/else{
			$(this).closest('.userinfo').remove();
		}
	});
	function render2(obj){
		$.ajax({
    		url:obj,
    		type:'post',
    		contentType:'application/json',
    		data:userkey,
    		async:false,
    		success:function(data){
    			userdata=JSON.parse( data.codeMessage );
    			if(data.code==00){
    				dialog.ren( {'cols':cols,'wh':wh,'userdata':userdata} );
    			}else if(data.code==01){
    				dialog.choicedata(data.codeMessage,'userinfo','aaa');
    			}
    			$('.userinfo .sure',parent.document).closest('.zhezhao').remove();
    		}
    	});
	}
	/*------------------------添加弹出框------------------------------*/
	//点击添加修改弹出框中的x、取消按钮；
	$('body',parent.document).on('click','.userinfoadd .close,.userinfochange .close,.userinfocance,.userinfo .cancel,.userIfo .sure',function(){
		$(this).closest('.zhezhao').remove();
		//$('.zhezhao',parent.document).remove();
	});
	//点击修改中的保存按钮
	$('body',parent.document).on('click','.useraddfosav',function(){
		var usnm=$('.usnm',parent.document).text(), //用户名
			cmpt=$('.cmpt',parent.document).val(),//真实姓名	
			juese=$('.sel_mo1 select option:selected',parent.document).attr('value'),
			jigou=$('.six .sel_mo select option:selected',parent.document).attr('value'),
			ufax=$('.ufax',parent.document).val(),//传真
			macip=$('.macip',parent.document).val(),
			mail=$('.mail',parent.document).val(), 
			mbtl=$('.mbtl',parent.document).val(),
			telp=$('.telp',parent.document).val(),
			rmrk=$('.rmrk',parent.document).val(),
			user={'usnm':usnm,'cmpt':cmpt,'ufax':ufax,'macip':macip,'mail':mail,'telp':telp,'mbtl':mbtl,'rmrk':rmrk,'cstn':jigou,'uspt':juese};
		
			checkrealname(cmpt);
			checktel(telp);
			checkphone(mbtl);
			checkemail(mail);
		if($('.cer re',parent.document).length<=0 ){	
			$.ajax({
				url:'/fx/editUser.do',
				type:'post',
				async:false,
				data:JSON.stringify( {'userKey':userkey,'user':user }),
         		dataType:'json',
         		contentType:'application/json;charset=utf-8',
				success:function(data){
					$('.useraddfosav',parent.document).closest('.zhezhao').remove();
					if(data.code==00){
						//render2( 'listUser.do' );
						dialog.choicedata(data.codeMessage+'!','userinfo','aaa');
						getlist({
					    	'userKey':userkey,
					    	'pageNo':1,
					    	'pageSize':10
					    });
						renpage();
					}else if(data.code==01){
						dialog.choicedata(data.codeMessage+'!','userinfo','aaa');
					}
				}
			});
		}
	});
	$('.sel_mo1',parent.document).click(function(){
		$(this).find('ul').slideToggle(300);
	});
	
	$('.sel_mo1 ul li',parent.document).click(function(e){
		var evt=e||window.event;
		if( evt.stopPropagation ){
			evt.stopPropagation();
		}else{
			evt.cancelBubble=true;
		}
		$('.sel_mo1 h6 span',parent.document).text( $(this).text());
		$('.sel_mo1 ul',parent.document).slideUp(300);
		
	});
	/*----------------快速搜索功能的实现------------------------*/
	$('.info_serbtn').click(function(){
		  var txt=$('.info_seript').val();
		  dialog.serchData(txt);
    });
	/*-----------------封装检查方法------------------------*/
	function checkusername(obj,obj1){
		var vo;
		if(obj==''||obj==undefined){
			$('.'+obj1,parent.document).parent('p').find('re').remove();
			$('.'+obj1,parent.document).parent('p').append('<re>用户名不能为空!</re>');
		}else if(obj!=''){
			vo={'userKey':userkey,'user':{'usnm':obj}};
			$.ajax({
				url:'/fx/isUser.do',
				type:'post',
				contentType:'application/json',
	    		data:JSON.stringify(vo),
				success:function(data){
					if( data.code==00){
						$('.'+obj1,parent.document).parent('p').find('re').remove();
					}else{
						$('.'+obj1,parent.document).parent('p').find('re').remove();
						$('.'+obj1,parent.document).parent('p').append('<re>'+data.codeMessage+'</re>');
					}
				}
			});
		}
	}
	function checkrealname(obj,obj1){
		if( obj==''||obj==undefined){
			$('.'+obj1,parent.document).parent('p').find('re').remove();
			$('.'+obj1,parent.document).parent('p').append('<re>真实姓名不能为空!</re>');
		}else if(obj!=''&&!renamereg.test( obj )){
			$('.'+obj1,parent.document).parent('p').find('re').remove();
			$('.'+obj1,parent.document).parent('p').append('<re>请输入至少两位的字符!</re>');
		}else{
			$('.'+obj1,parent.document).parent('p').find('re').remove();
		}
	}
	function checksurepsd(obj,obj1){
		var psd=$('.psd',parent.document).val();
		if( obj==''||obj==undefined){
			$('.'+obj1,parent.document).parent('p').find('re').remove();
			$('.'+obj1,parent.document).parent('p').append('<re>确认密码不能为空!</re>');
		}else if( obj!=''&&!psdreg.test(obj) ){
			$('.'+obj1,parent.document).parent('p').find('re').remove();
			$('.'+obj1,parent.document).parent('p').append('<re>请输入至少6位的密码!</re>');
		}/*else if( obj!=''&&!repeatpsd.test(obj) ){
			$('.'+obj1,parent.document).parent('p').find('re').remove();
			$('.'+obj1,parent.document).parent('p').append('<re>不能有3位连续字符!</re>');
		}*/else if(psd!=obj){
			$('.'+obj1,parent.document).parent('p').find('re').remove();
			$('.'+obj1,parent.document).parent('p').append('<re>两次输入密码不一致!</re>');
		}else{
			$('.'+obj1,parent.document).parent('p').find('re').remove();
		}
	}
	function checkemail(obj,obj1){
		if( obj==''||obj==undefined){
			$('.'+obj1,parent.document).parent('p').find('re').remove();
			$('.'+obj1,parent.document).parent('p').append('<re>邮箱不能为空!</re>');
		}else if(obj!=''&&!emailreg.test( obj )){
			$('.'+obj1,parent.document).parent('p').find('re').remove();
			$('.'+obj1,parent.document).parent('p').append('<re>请输入正确的邮箱!</re>');
		}else{
			$('.'+obj1,parent.document).parent('p').find('re').remove();
		}
	}
	function checktel(obj,obj1){
		if( obj==''||obj==undefined){
			$('.'+obj1,parent.document).parent('p').find('re').remove();
			$('.'+obj1,parent.document).parent('p').append('<re>固定电话不能为空!</re>');
		}else if(obj!=''&&!telreg.test( obj )){
			$('.'+obj1,parent.document).parent('p').find('re').remove();
			$('.'+obj1,parent.document).parent('p').append('<re>请输入正确的固定电话!</re>');
		}else{
			$('.'+obj1,parent.document).parent('p').find('re').remove();
		}
	}
	function checkphone(obj,obj1){
		if( obj==''||obj==undefined){
			$('.'+obj1,parent.document).parent('p').find('re').remove();
			$('.'+obj1,parent.document).parent('p').append('<re>移动电话不能为空!</re>');
		}else if(obj!=''&& !phonereg.test(obj ) ){
			$('.'+obj1,parent.document).parent('p').find('re').remove();
			$('.'+obj1,parent.document).parent('p').append('<re>请输入正确的移动电话!</re>');
		}else{
			$('.'+obj1,parent.document).parent('p').find('re').remove();
		}
	}
 
	function checkpsd(obj,obj1){
		if( obj==''||obj==undefined){
			$('.'+obj1,parent.document).parent('p').find('re').remove();
            $('.'+obj1,parent.document).parent('p').append('<re>密码不能为空!</re>');
		}else if( ! /^.{6,20}$/.test(obj)){
            $('.'+obj1,parent.document).parent('p').find('re').remove();
			$('.'+obj1,parent.document).parent('p').append('<re>新密码的长度为6到20位</re>');  
		}else if( ! /^(?![^A-Za-z]+$)(?![^0-9]+$)[\x21-x7e]{6,20}$/.test(obj) ){
            $('.'+obj1,parent.document).parent('p').find('re').remove();
			$('.'+obj1,parent.document).parent('p').append('<re>新密码不能为纯数字和纯字母组成</re>');  	 	     
		}else{
			$('.'+obj1,parent.document).parent('p').find('re').remove();
		}
 
	 }
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
    		    	    	userKey:userkey
    		    	    });
    		    	}	
    		    }
    		  });
    	});
    }
	 //导出excel;
	  $('#logon').click(function(){
		  $('#fornm input').remove();
		  var str='<input type="text" name = "userKey" value='+userkey+'><input type="text" name = "tableName" value='+tit+'>';
		  for(var i in cols){
			  str+='<input type="text" name ="tableList['+i+'].name" value='+cols[i].title+'><input type="text" name ="tableList['+i+'].value" value='+cols[i].name+'>'
		  }
		  $('#fornm').append( str );
		  $('#fornm').submit();
     });
	  
	    //点击分页;
	    $('.boxtop').on('click','.first',function(){
	    	var Nopage=$('.Nopage').text()*1;
	 	    if(Nopage>1){
	 	    	Nopage=1;
	 	    	getlist({
		        	'userKey':userkey,
		        	'pageNo':Nopage,
		        	'pageSize':10
		        });
	 	    }
	    });
		$('.boxtop').on('click','.prev',function(){
		    var Nopage=$('.Nopage').text()*1;
		    if(Nopage>1){
		    	Nopage=Nopage-1;
		    	getlist({
		        	'userKey':userkey,
		        	'pageNo':Nopage,
		        	'pageSize':10
		        });
		    }
		});
		$('.boxtop').on('click','.next',function(){
			var Nopage=$('.Nopage').text()*1,
				Totalpage=$('.Totalpage').text()*1;
		    if(Nopage<Totalpage){
		    	Nopage=Nopage*1+1;
		    	getlist({
		        	'userKey':userkey,
		        	'pageNo':Nopage,
		        	'pageSize':10
		        });
		    }
		});
		$('.boxtop').on('click','.last',function(){
			var Nopage=$('.Nopage').text()*1,
			Totalpage=$('.Totalpage').text()*1;
		    if(Nopage<Totalpage){
		    	Nopage=Totalpage;
		    	getlist({
		        	'userKey':userkey,
		        	'pageNo':Nopage,
		        	'pageSize':10
		        });
		    }
		});
});
