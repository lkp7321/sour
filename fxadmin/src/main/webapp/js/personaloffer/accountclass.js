/*特殊账户分类*/
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
        { title:'特殊类型名称', name:'tynm' ,width:180, align:'left' },
        { title:'特殊类型编号', name:'apfg' ,width:120, align:'left'},
        { title:'状态', name:'stat' ,width:120, align:'center'}
    ];
    var usnm=sessionStorage.getItem('usnm'),
        product=sessionStorage.getItem('product'),
        loginuser={'usnm':usnm,'prod':product},
        userkey=JSON.parse( sessionStorage.getItem('menu') ).userKey,
        ckvo = { 'userKey':userkey,'apfg':'' };
 
    //请求列表
    getAccount(ckvo);
    //点击查询按钮请求列表
    $('.class_serbtn').click(function(){
    	getAccount({'userKey':userkey,'apfg':$('.class_seript').val() });
    }); 
    
	var card=/\d{1,}/;
	/*---------------点击修改添加修改删除按钮---------------*/
	$('.add').click(function(){
		dialog.accountclassadd('accountclass','add',product);
		$('.accountclass .card',parent.document).blur(function(){
			if($(this).val()==''||$(this).val()==undefined){
				$(this).parent('p').find('re').remove();
				$(this).parent('p').append('<re>类型编号不能为空</re>');
			}else if( $(this).val()!=''&&$(this).val()!=undefined&& !card.test($(this).val() ) ){
				$(this).parent('p').find('re').remove();
				$(this).parent('p').append('<re>请输入正确的类型编号</re>');
			}else{
				$(this).parent('p').find('re').remove();
			}
		});
		$('.accountclass .typename',parent.document).blur(function(){
			if($(this).val()==''||$(this).val()==undefined){
				$(this).parent('div').find('re').remove();
				$(this).parent('div').append('<re>类型名称不能为空</re>');
			}else{
				$(this).parent('div').find('re').remove();
			}
		})
	});
	//点击添加弹出窗的保存按钮
	$('body',parent.document).on('click','.add .save',function(){
		var tynm=$('.typename',parent.document).val(),
		    apfg=$('.card',parent.document).val(),num=0,ckvo,stat,allwrong;
		 if($('.accountstate input[type="radio"]:checked',parent.document).data('tit')=='start'){
		   stat=0;
	    }else{
		   stat=1;
	    }
		 ckvo = {'userKey':userkey,'trdTynm':{ 'apfg':apfg,'tynm':tynm, 'stat':stat}};
		
		//判断类型编
		 if( $('.card',parent.document).closest('p').css('display')!='none' ){
			if( apfg==''||apfg==undefined){
				$('.accountclass .card',parent.document).parent('p').find('re').remove();
				$('.accountclass .card',parent.document).parent('p').append('<re>类型编号不能为空</re>');
			}else if( apfg!='' && apfg!=undefined && !card.test(apfg)  ){
				$('.accountclass .card',parent.document).parent('p').find('re').remove();
				$('.accountclass .card',parent.document).parent('p').append('<re>请输入正确的类型编号</re>');
			}else{
				num++;
				$('.accountclass .card',parent.document).parent('p').find('re').remove();
			}
		 }
		//判断类型名称;
		if(tynm==''||tynm==undefined){
			$('.accountclass .typename',parent.document).parent('div').find('re').remove();
			$('.accountclass .typename',parent.document).parent('div').append('<re>类型名称不能为空</re>');
		}else{
			num++;
			$('.accountclass .typename',parent.document).parent('div').find('re').remove();
		}
		
		 if( $('.accountclass re',parent.document).length==0){
			$.ajax({
		    	url:'/fx/insertTYNM.do',
		        type:'post',
		    	contentType:'application/json',
		    	data:JSON.stringify(ckvo),
		    	async:true,
		    	dataType:'json',
		    	success:function(data){
		    		$('.add .save',parent.document).closest('.zhezhao').remove();
		    		if(data.code==01){
		    			dialog.choicedata(data.codeMessage,'accountclass');
		    			getAccount({'userKey':userkey,'apfg':$('.class_seript').val() });
		    		}else{
		    			dialog.choicedata(data.codeMessage,'accountclasserror'); 
		    		}
		    	  }
		      });
	     }
	 });
	$('.modify').click(function(){
		var tynm=$('.box tr.selected td:eq(0) span').text(),
		    apfg=$('.box tr.selected td:eq(1) span').text(),
		    stat=$('.box tr.selected td:eq(2) span').text();
		if( $('.box tbody tr').hasClass('selected') ){
			dialog.accountclassadd('accountclass','modify');
			$('.typename',parent.document).val(tynm).attr({'readonly':'true','disabled':'disabled'});
			$('.card',parent.document).val(apfg).attr({'readonly':'true','disabled':'disabled'});
			if(stat=='停用' ){
				$('.accountstate input[data-tit="stop"]',parent.document).prop('checked','checked');
			}else if(stat=='开启' ){
				$('.accountstate input[data-tit="start"]',parent.document).prop('checked','checked');
			} 
		}else{
			dialog.choicedata('请先选择一条数据!','accountclasserror');
		}
	});
	$('body',parent.document).on('click','.modify .save',function(){
		var tynm=$('.typename',parent.document).val(),
	        apfg=$('.card',parent.document).val(),ckvo,stat;
	    if($('.accountstate input[type="radio"]:checked',parent.document).data('tit')=='start'){
		   stat=0;
	    }else{
		   stat=1;
	    }
	    ckvo = {'userKey':userkey,'trdTynm':{ 'apfg':apfg,'tynm':tynm, 'stat':stat}};
	    $.ajax({
	    	url:'/fx/updateTYNM.do',
	        type:'post',
	    	contentType:'application/json',
	    	data:JSON.stringify(ckvo),
	    	async:true,
	    	dataType:'json',
	    	success:function(data){
	    		$('.modify .save',parent.document).closest('.zhezhao').remove();
	    		if(data.code==01){
	    			dialog.choicedata(data.codeMessage,'accountclass');
	    			getAccount({'userKey':userkey,'apfg':$('.class_seript').val() });
	    		}else if(data.code==02){
	    			dialog.choicedata(data.codeMessage,'accountclasserror'); 
	    		}
	    	  }
	      });
	});
	$('.del').click(function(){
		if( $('.box tbody tr').hasClass('selected') ){
			dialog.cancelDate('您确定要删除当前的记录吗','accountclass');
		}else{
			dialog.choicedata('请先选择一条数据!','accountclass');
		}
	});
	//删除成功弹窗的确认按钮
	$('body',parent.document).on('click','.accountclass .confirm',function(){
	    var tynm=$('.box tr.selected td:eq(0) span').text(),
	        apfg=$('.box tr.selected td:eq(1) span').text(),
            ckvo = {'userKey':userkey,'trdTynm':{ 'apfg':apfg,'tynm':tynm}};
    $.ajax({
    	url:'/fx/delTYNM.do',
        type:'post',
    	contentType:'application/json',
    	data:JSON.stringify(ckvo),
    	async:true,
    	dataType:'json',
    	success:function(data){
    		$('.accountclass .confirm',parent.document).closest('.zhezhao').remove();
    		if(data.code==01){
    			dialog.choicedata(data.codeMessage,'accountclass');
    			getAccount({'userKey':userkey,'apfg':$('.class_seript').val() });
    		}else if(data.code==02){
    			dialog.choicedata(data.codeMessage,'accountclasserror'); 
    		}
    	  }
      });
	});
	
	
    //关闭成功提示窗的  重新请求列表	
	$('body',parent.document).on('click','.accountclass .sure,.accountclasserror .sure,.accountclasserror .cancel,.accountclasserror .close,.accountclass .close,.accountclass .cancel',function(){
		$(this,parent.document).closest('.zhezhao').remove();
	});	
	
    //封装请求列表
	function getAccount(obj){
		  $.ajax({
		    	url:'/fx/listTYNM.do',
		        type:'post',
		    	contentType:'application/json',
		    	data:JSON.stringify(obj),
		    	async:false,
		    	dataType:'json',
		    	success:function(data){
		    		if(data.code==00){
		    			dialog.ren({'cols':cols,'wh':wh,'ww':ww,'userdata':''});
		    			dialog.choicedata(data.codeMessage,'accountclasserror'); 
		    		}else if(data.code==01){
		    			//console.log(data.codeMessage));
		    			dialog.ren({'cols':cols,'wh':wh,'ww':ww,'userdata':data.codeMessage});
		    		}
		    	  }
		      });
	}
	
 });

