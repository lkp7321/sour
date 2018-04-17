/*系統-币种 &&系统-货币对&&系统-错误码*/
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
	    ww=$(window).width()-260+"px";
		$('.boxtop').css('width',ww);
		$('.boxtop').css('height',wh);
	var numreg=/^\d+$|^[0]{1}\.\d+$|^[1-9]{1}\.\d+$/,
		userkey=JSON.parse( sessionStorage.getItem('menu') ).userKey;
    var usnm=sessionStorage.getItem('usnm'),
		product=sessionStorage.getItem('product'),
		user={'usnm':usnm,'prod':product},
		userdata;
	var title=$('title').text();

    
    if(title=='货币对管理'){
    	 var cols = [
             { title:'货币对中文名称', name:'excn' ,width:150, align:'left' },
             { title:'货币对编号', name:'excd' ,width:150, align:'left'},
             { title:'货币对名称', name:'exnm' ,width:150, align:'left'},
             { title:'货币对类型', name:'extp' ,width:150, align:'center'},
             { title:'价格位点数', name:'pion' ,width:80, align:'right'}
         ];
    	 getcuur( cols );
    	//thrfn({url:'/fx/getAllCurrmsg.do',data:user});
    }else if(title=="错误码管理"){
    	var cols = [
             { title:'错误码', name:'ercd' ,width:250, align:'left' },
             { title:'错误码说明(外部)', name:'ertx' ,width:300, align:'left'},
             { title:'错误显示(内部)', name:'erxs' ,width:300, align:'left'}
         ];
    	thrfn({userKey:userkey,strTxt:$('.errcode').val(),strTxt1:$('.errmark').val(),'pageSize':10,'pageNo':1} );
    }else if(title=='币种管理'){
    	var cols = [
	         { title:'货币代码', name:'cytp' ,width:100, align:'left' },
	         { title:'货币英文名称', name:'cyen' ,width:100, align:'left'},
	         { title:'货币中文名称', name:'cycn' ,width:150, align:'left'},
	         { title:'启用标志', name:'usfg' ,width:150, align:'center'},
	         { title:'备注', name:'remk' ,width:100, align:'left'}
	     ];
    	thrfn1({url:'/fx/getAllCytp.do',data:userkey});
    }else if( title=="品种对管理"){
    	var cols = [
	             { title:'品种对名称', name:'exnm' ,width:150, align:'left' },
	             { title:'品种对编号', name:'excd' ,width:150, align:'left'},
	             { title:'品种中文名称', name:'excn' ,width:150, align:'left'},
	             { title:'品种对类型', name:'extp' ,width:150, align:'center'},
	             { title:'价格位点数', name:'pion' ,width:100, align:'right'}
	         ];
    	   thrfn1({url:'/fx/getAllCurrmsg.do',data:userkey});
    }
    function getcuur( obj ){
    	 $.ajax({
     		url:'/fx/getAllCurrmsg.do',
     		type:'post',
     		contentType:'application/json',
     		async:true,
     		data:userkey,
     		success:function(data){
     			if(data.code==01){
     				userdata= data.codeMessage;
     				dialog.ren({'cols':obj,'wh':wh,'userdata':userdata});
     			}else if(data.code==00){
     				dialog.ren({'cols':obj,'wh':wh,'userdata':''});
     			}
     		}
     	});
    }
    function thrfn(obj){
    	//请求数据; 
        $.ajax({
    		url:'/fx/getMserrors.do',
    		type:'post',
    		contentType:'application/json',
    		data:JSON.stringify(obj),
    		async:true,
    		success:function(data){
    			if(data.code==01){
    				userdata= data.codeMessage;
    				dialog.ren({'cols':cols,'wh':wh,'userdata':userdata.list});
    				$('.page').remove();
    				$('.boxtop').append('<div class="page"><span class="first">首页</span><span class="prev">上一页</span><span class="next">下一页</span><span class="last">末页</span><span class="nopage">第<b class="Nopage">'+userdata.pageNum+'</b>页/共<b class="Totalpage">'+userdata.pages+'</b>页</span><span class="totalpage">共<b class="total">'+userdata.total+'</b>条记录</span></div>')
    			}else if(data.code==00){
    				dialog.ren({'cols':cols,'wh':wh,'userdata':'','pageSize':10,'pageNo':1});
    			}
    		}
    	});
    }
	//点击分页;
    $('.boxtop').on('click','.first',function(){
    	var Nopage=$('.Nopage').text();
 	    if(Nopage>1){
 	    	Nopage=1;
 	    	thrfn({ userKey:userkey,strTxt:$('.errcode').val(),strTxt1:$('.errmark').val(),'pageNo':Nopage,'pageSize':10} );
 	    }
    });
	$('.boxtop').on('click','.prev',function(){
	    var Nopage=$('.Nopage').text();
	    if(Nopage>1){
	    	Nopage=Nopage-1;
	    	thrfn({ userKey:userkey,strTxt:$('.errcode').val(),strTxt1:$('.errmark').val(),'pageNo':Nopage,'pageSize':10} );
	    }
	});
	$('.boxtop').on('click','.next',function(){
		var Nopage=$('.Nopage').text(),
			Totalpage=$('.Totalpage').text();
	    if(Nopage<Totalpage){
	    	Nopage=Nopage*1+1;
	    	thrfn({ userKey:userkey,strTxt:$('.errcode').val(),strTxt1:$('.errmark').val(),'pageNo':Nopage,'pageSize':10} );
	    }
	});
	$('.boxtop').on('click','.last',function(){
		var Nopage=$('.Nopage').text(),
		Totalpage=$('.Totalpage').text();
	    if(Nopage<Totalpage){
	    	Nopage=Totalpage;
	    	thrfn({userKey:userkey,strTxt:$('.errcode').val(),strTxt1:$('.errmark').val(),'pageNo':Nopage,'pageSize':10} );
	    }
	});

    function thrfn1(obj){
    	//请求数据;
        $.ajax({
    		url:obj.url,
    		type:'post',
    		contentType:'application/json',
    		data:obj.data,
    		async:true,
    		success:function(data){
    			if(data.code==01){
    				userdata= data.codeMessage;
    				dialog.ren({'cols':cols,'wh':wh,'userdata':userdata});
    			}else if(data.code==00){
    				dialog.ren({'cols':cols,'wh':wh,'userdata':''});
    			}
    		}
    	});
    }
//点击搜索（错误码 的搜索）;
$('.search').click(function(){
    thrfn({userKey:userkey,strTxt:$('.errcode').val(),strTxt1:$('.errmark').val(),'pageSize':10,'pageNo':1} );
});
$('body',parent.document).on('click','.errorcode .sure',function(){
	$(this).closest('.zhezhao').remove();
	$(".mmGrid .mmg-bodyWrapper tr ").show();
});
/*----------------快速搜索功能的实现------------------------*/
$('.info_serbtn').click(function(){
	  var txt=$('.info_seript').val();
	  dialog.serchData(txt);
});
	//点击添加、修改、删除；
	$('.add').click(function(){
		 dialog.cuurpairfn('currpair','add');
		 getflist();
		//价格位点数修改；
		$('.currpair .dealrate',parent.document).on('blur',function(){
			if( $(this).val()==''){
				$(this).closest('div').find('re').remove();
				$(this).closest('div').append('<re>价格位点数不能为空!</re>');
			}else if(! numreg.test( $(this).val()) ){
				$(this).closest('div').find('re').remove();
				$(this).closest('div').append('<re>价格位点数不能为非数字!</re>');
			}else{
				$(this).closest('div').find('re').remove();
			}
		});
	});
	$('.modify').click(function(){
		 if( $('.box tbody tr').hasClass('selected') ){
			 dialog.cuurpairfn('currpair','modify');
			 getflist();
			//价格位点数修改；
			$('.currpair .dealrate',parent.document).on('blur',function(){
				if( $(this).val()==''){
					$(this).closest('div').find('re').remove();
					$(this).closest('div').append('<re>价格位点数不能为空!</re>');
				}else if(! numreg.test( $(this).val()) ){
					$(this).closest('div').find('re').remove();
					$(this).closest('div').append('<re>价格位点数不能为非数字!</re>');
				}else{
					$(this).closest('div').find('re').remove();
				}
			});
			$('.dealrate',parent.document).val( $('.box tr.selected td:eq(4) span').text() );		
			$('.asknum',parent.document).val( $('.box tr.selected td:eq(1) span').text() );
			$('.pronum',parent.document).val( $('.box tr.selected td:eq(2) span').text()  );
			if( $('.box tr.selected td:eq(3) span').text()=='直盘'){
				$('.cuutype input.r1',parent.document).prop('checked','checked');
			}else{
				$('.cuutype input.r2',parent.document).prop('checked','checked');
			}
		 }else{
			dialog.choicedata('请先选择一条数据!','currpair');
		}
	});
	function getflist(){
		var txt=$('.pubhead span',parent.document).text(),
			str='',
			cycn='',cytp='';
		var per=$('.box tr.selected td:eq(0) span').text().slice(0,3),
		 	bef=$('.box tr.selected td:eq(0) span').text().slice(3,6);
		$.ajax({
    		url:'/fx/ExnmCurrmsglist.do',
    		type:'post',
    		contentType:'application/json',
    		async:true,
    		data:userkey,
    		success:function(data){
    			if(data.code==01){
    				userdata= data.codeMessage ;
    				for(var i in userdata){
    						str+='<option cycn='+userdata[i].cycn+' cytp='+userdata[i].cytp+'>'+userdata[i].cyen+'</option>'    					
    				}
    				$('.fir,.seco',parent.document).html(str);
    				
    				if(txt=='品种对-修改'){
    					$('.fir option',parent.document).each(function(i,v){
        					if( $(v).text()==per ){
        						$(v).attr('selected','selected');
        					}
        				});
        				$('.seco option',parent.document).each(function(i,v){
        					if( $(v).text()==bef){
        						$(v).attr('selected','selected');
        					}
        				});
    				}
    			}else{
    				
    			}
    		}
    	});
	}
	//点击保存；
	$('body',parent.document).on('click','.currpair .succss',function(){
		var seco=$('.seco option:selected',parent.document).text(),
			fir=$('.fir option:selected',parent.document).text(),
			pion=$('.currpair .dealrate',parent.document).val();

		if( seco==fir){
			dialog.choicedata('左右币种不能相同,请重新选择!','currpair');
		}else if(pion==''){
			$('.currpair .dealrate',parent.document).closest('div').find('re').remove();
			$('.currpair .dealrate',parent.document).closest('div').append('<re>价格位点数不能为空!</re>');
		}else if(pion!=''&&!numreg.test( pion) ){
			$('.currpair .dealrate',parent.document).closest('div').find('re').remove();
			$('.currpair .dealrate',parent.document).closest('div').append('<re>价格位点数不能为非数字!</re>');
		}else{
			$('.currpair .dealrate',parent.document).closest('div').find('re').remove();
			var txt=$('.pubhead span',parent.document).text();
			if( txt=='品种对-添加'){
				url='addCurrmsg.do';
			}else{
				url='modifyCurrmsg.do';
			}
			var fircycn=$('.fir option:selected',parent.document).attr('cycn'),
				fircytp=$('.fir option:selected',parent.document).attr('cytp'),
				secocycn=$('.seco option:selected',parent.document).attr('cycn'),
				secocytp=$('.seco option:selected',parent.document).attr('cytp');
			$.ajax({
	    		url:url,
	    		type:'post',
	    		contentType:'application/json',
	    		async:true,
	    		data:JSON.stringify({
		    		'userKey':userkey,
		    		'currmsg':{
		    			exnm:fir+seco,
		    			excd:fircytp+secocytp,
		    			excn:fircycn+secocycn,
		    			extp:$('.currpair input[name="aa"]:checked',parent.document).attr('tit'),
		    			pion:pion
		    		}}),
	    		success:function(data){
	    			$('.currpair .succss',parent.document).closest('.zhezhao').remove();
						if(data.code==01){
							dialog.choicedata(data.codeMessage,'currpair');
							thrfn1({url:'/fx/getAllCurrmsg.do',data:userkey});
						}else{
							dialog.choicedata(data.codeMessage,'currpair');	
						}
	    		}
	    	});
		}
	});
	$('body',parent.document).on('change','.fir',function(){
		var fir=$(this).find('option:selected').text(),
			seco=$('.seco option:selected',parent.document).text(),
			fircycn=$(this).find('option:selected').attr('cycn'),
			fircytp=$(this).find('option:selected').attr('cytp'),
			secocycn=$('.seco option:selected',parent.document).attr('cycn'),
			secocytp=$('.seco option:selected',parent.document).attr('cytp');
		if( fir==seco){
			dialog.choicedata('左右币种不能相同,请重新选择!','currpair');
		}else{
			$('.asknum',parent.document).val( fircytp+secocytp);
			$('.pronum',parent.document).val( fircycn+secocycn );
			if(fir=='USD'||seco=="USD"){
				$('.r1',parent.document).prop('checked','checked');
			}else{
				$('.r2',parent.document).prop('checked','checked');
			}
		}
	});
	$('body',parent.document).on('change','.seco',function(){
		var fir=$(this).find('option:selected').text(),
			seco=$('.fir option:selected',parent.document).text(),
			secocycn=$(this).find('option:selected').attr('cycn'),
			secocytp=$(this).find('option:selected').attr('cytp'),
			fircycn=$('.fir option:selected',parent.document).attr('cycn'),
			fircytp=$('.fir option:selected',parent.document).attr('cytp');
		if( fir==seco){
			dialog.choicedata('左右币种不能相同,请重新选择！!','currpair');
		}else{
			$('.asknum',parent.document).val( fircytp+secocytp);
			$('.pronum',parent.document).val( fircycn+secocycn );
			if(fir=='USD'||seco=="USD"){
				$('.r1',parent.document).prop('checked','checked');
			}else{
				$('.r2',parent.document).prop('checked','checked');
			}
		}
	});
	
	$('.delete').click(function(){
		if( $('.box tbody tr').hasClass('selected') ){
			//删除弹出框;
			dialog.cancelDate('您确定要删除当前记录吗？','currpair');
		 }else{
			dialog.choicedata('请先选择一条数据!','currpair');
		}
	});
	//点击删除数据 确认+ajax;
	$('body',parent.document).on('click','.currpair .confirm',function(){
		var exnm=$('.box tr.selected td:eq(0) span').text();
		$.ajax({
			url:'/fx/delCurrmsg.do',
			type:'post',
			async:true,
			contentType:'application/json',
    		data:JSON.stringify({
    			'userKey':userkey,
    			'exnm':exnm
    		}),
			success:function(data){//this指向；
				$('.currpair .confirm',parent.document).closest('.zhezhao').remove();
				if(data.code==01){
					dialog.choicedata(data.codeMessage,'currpair','aaa');
					$('.box tr.selected').remove();
				}else{
					dialog.choicedata(data.codeMessage,'currpair','aaa');
				}
			}
		});	
	});	
	//点击添加修改弹出框中的x、取消按钮；
	$('body',parent.document).on('click','.currpair .close,.currpair .sure,.currpair .faile,.currpair .cancel',function(){
		$(this).closest('.zhezhao').remove();
	});
});

