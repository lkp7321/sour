/*特殊账户收集*/
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
	ww=$(window).width()-250+"px";;

    $('.boxtop').css('width',ww);
    $('.boxtop').css('height',wh);
	//列参数;
    var cols = [
            { title:'特殊账号类型', name:'tynm' ,width:80, align:'left' },
            { title:'卡号', name:'cuno' ,width:80, align:'left'},
            { title:'状态', name:'stat' ,width:80, align:'center'}
    ];
    var usnm=sessionStorage.getItem('usnm'),
        product=sessionStorage.getItem('product'),
        loginuser={'usnm':usnm,'prod':product},
        userkey=JSON.parse( sessionStorage.getItem('menu') ).userKey,
        ckvo = { 'userKey':userkey,'cuno':''};
 
 //请求列表
 getCollect({ 'userKey':userkey,'cuno':''});      
 //点击查询按钮请求列表
 $('.collect_serbtn').click(function(){
 	   getCollect({ 'userKey':userkey,'cuno':$('.collect_seript').val() });
 }); 
	var card=/\d{1,}/;
	/*---------------点击修改添加修改删除按钮---------------*/
	$('.add').click(function(){
		dialog.accountcollectadd('accountcollect','add');
		//请求option
		$.ajax({
			url:'/fx/listTynm.do',
			type:'post',
			async:true,
			data:userkey,
 			contentType:'application/json;charset=utf-8',
			success:function(data){
				var collectdata=data.codeMessage,str='';
				if(data.code==01){
					for(var i in collectdata){
						str+='<option value='+collectdata[i].apfg+'>'+collectdata[i].tynm+'</option>'
					}
					$('.accountclass select',parent.document).html(str);
				}else{
					 
				}
			}
		});
		$('.accountcollect .card',parent.document).blur(function(){
			if($(this).val()==''||$(this).val()==undefined){
				$(this).parent('p').find('re').remove();
				$(this).parent('p').append('<re>卡号不能为空</re>');
			}else if( $(this).val()!=''&&$(this).val()!=undefined&& !card.test($(this).val() ) ){
				$(this).parent('p').find('re').remove();
				$(this).parent('p').append('<re>请输入正确的卡号</re>');
			}else{
				$(this).parent('p').find('re').remove();
			}
		})
	});
	//点击添加窗的保存按钮 
	$('body',parent.document).on('click','.accountcollect .add .save',function(){
		var apfg=$('.accountcollect option:selected',parent.document).val(),
		    cuno=$('.card',parent.document).val(),num=0,ckvo,stat;
		 if($('.accountstate input[type="radio"]:checked',parent.document).data('tit')=='start'){
			   stat=0;
		    }else{
			   stat=1;
		    }
		ckvo = {'userKey':userkey,'trdSpcut':{ 'apfg':apfg,'cuno':cuno, 'stat':stat}};
		 
		//判断卡号;
		 if( cuno==''||cuno==undefined){
			$('.accountcollect .card',parent.document).parent('p').find('re').remove();
			$('.accountcollect .card',parent.document).parent('p').append('<re>类型编号不能为空</re>');
		}else if( cuno!='' && cuno!=undefined && !card.test(cuno)  ){
			$('.accountcollect .card',parent.document).parent('p').find('re').remove();
			$('.accountcollect .card',parent.document).parent('p').append('<re>请输入正确的类型编号</re>');
		}else{
			num++;
			$('.accountcollect .card',parent.document).parent('p').find('re').remove();
		}
		 if(num>=1){
			$.ajax({
		    	url:'/fx/insertSpcut.do',
		        type:'post',
		    	contentType:'application/json',
		    	data:JSON.stringify(ckvo),
		    	async:false,
		    	dataType:'json',
		    	success:function(data){
		    		$('.add .save',parent.document).closest('.zhezhao').remove();
		    		if(data.code==01){
		    			dialog.choicedata(data.codeMessage,'accountcollect');
		    			getCollect({ 'userKey':userkey,'cuno':$('.collect_seript').val() });
		    		}else if(data.code==02){
		    			dialog.choicedata(data.codeMessage,'accountcollecterror'); 
		    		}
		    	  }
		      });
	     }
	 });
	//点击修改按钮  弹出修改窗
	$('.modify').click(function(){
		var apfg=$('.box tr.selected td:eq(0) span').text(),
	        cuno=$('.box tr.selected td:eq(1) span').text(),
	        stat=$('.box tr.selected td:eq(2) span').text();
		if( $('.box tbody tr').hasClass('selected') ){
			dialog.accountcollectadd('accountcollect','modify');
			$.ajax({
				url:'/fx/listTynm.do',
				type:'post',
				async:true,
				data:userkey,
	 			contentType:'application/json;charset=utf-8',
				success:function(data){
					var collectdata=data.codeMessage,str='';
					if(data.code==01){
						for(var i in collectdata){
							if( collectdata[i].tynm==apfg ){
								str+='<option value='+collectdata[i].apfg+' selected="selected">'+collectdata[i].tynm+'</option>'
							}else{
								str+='<option value='+collectdata[i].apfg+'>'+collectdata[i].tynm+'</option>'
							}
						}
						$('.accountclass select',parent.document).html(str);
					}else{
						 
					}
				}
			});
		    $('.accountclass option:selected',parent.document).text(apfg);
			$('.card',parent.document).val(cuno).attr({'readonly':'true','disabled':'disabled'});
			if(stat=='停用' ){
				$('.accountstate input[data-tit="stop"]',parent.document).prop('checked','checked');
			}else if(stat=='开启' ){
				$('.accountstate input[data-tit="start"]',parent.document).prop('checked','checked');
			} 
			
		}else{
			dialog.choicedata('请先选择一条数据!','accountcollect');
		}
	});
	//点击修改弹出窗的保存按钮
	$('body',parent.document).on('click','.modify .save',function(){
		var apfg=$('.accountclass option:selected',parent.document).attr('value'),
	        cuno=$('.card',parent.document).val(),ckvo,stat;
	    if($('.accountstate input[type="radio"]:checked',parent.document).data('tit')=='start'){
		   stat=0;
	    }else{
		   stat=1;
	    }
	    ckvo = {'userKey':userkey,'trdSpcut':{ 'apfg':apfg,'cuno':cuno, 'stat':stat}};
	    $.ajax({
	    	url:'/fx/upSpcut.do',
	        type:'post',
	    	contentType:'application/json',
	    	data:JSON.stringify(ckvo),
	    	async:false,
	    	dataType:'json',
	    	success:function(data){
	    		$('.modify .save',parent.document).closest('.zhezhao').remove();
	    		if(data.code==01){
	    			dialog.choicedata(data.codeMessage,'accountcollect');
	    			getCollect({ 'userKey':userkey,'cuno':$('.collect_seript').val() });
	    		}else if(data.code==02){
	    			dialog.choicedata(data.codeMessage,'accountcollecterror'); 
	    		}
	    	  }
	      });
		
		
	})
	//点击删除弹出窗
	$('.del').click(function(){
		if( $('.box tbody tr').hasClass('selected') ){
			dialog.cancelDate('您确定要删除当前的记录吗','accountcollect')
		}else{
			dialog.choicedata('请先选择一条数据!','accountcollect');
		}
	});
	//点击删除弹窗的确认按钮
	$('body',parent.document).on('click','.accountcollect .confirm',function(){
	    var cuno=$('.box tr.selected td:eq(1) span').text(),
            ckvo = {'userKey':userkey,'cuno':cuno};
    $.ajax({
    	url:'/fx/delSpcut.do',
        type:'post',
    	contentType:'application/json',
    	data:JSON.stringify(ckvo),
    	async:false,
    	dataType:'json',
    	success:function(data){
    		$('.accountcollect .confirm',parent.document).closest('.zhezhao').remove();
    		if(data.code==01){
    			dialog.choicedata(data.codeMessage,'accountcollect');
    			$('.box tr.selected').remove();
    		}else if(data.code==02){
    			dialog.choicedata(data.codeMessage,'accountcollecterror'); 
    		}
    	  }
      });
	});
	 //关闭修改失败 删除失败提示窗 不 重新请求列表	
	$('body',parent.document).on('click','.accountcollecterror .sure,.accountcollect .cancel,.accountcollect .close,.accountcollect .sure',function(){
		$(this,parent.document).closest('.zhezhao').remove();
	});	
	
    //封装请求列表
	function getCollect(obj){
		  $.ajax({
		    	url:'/fx/listSpcut.do',
		        type:'post',
		    	contentType:'application/json',
		    	data:JSON.stringify(obj),
		    	async:false,
		    	dataType:'json',
		    	success:function(data){
		    		if(data.code==00){
		    			dialog.ren({'cols':cols,'wh':wh,'ww':ww,'userdata':''});
		    		}else if(data.code==01){
		    			dialog.ren({'cols':cols,'wh':wh,'ww':ww,'userdata':data.codeMessage});
		    		}
		    	  }
		      });
	}
	 
 
});

