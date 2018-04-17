require.config({
	baseUrl:'/fx/js/',
	shim:{
		'jquery.niceScroll': {
			deps: ['jquery'],
			exports: 'jquery.niceScroll'
		},
		'mmGrid':{
			deps:['jquery'],
			exports:'mmGrid'
		},
		'page':{
			deps:['jquery'],
			exports:'page'
		}
	},
	paths:{
		'jquery':'js_files/jquery-1.9.1.min',
		'mmGrid':'js_files/mmGrid',
		'niceScroll':'js_files/jquery.nicescroll.min',
		'dialog':'dialog',
		'page':'page'
	}
});
require(['jquery','mmGrid','niceScroll','dialog','page'],function($,mmGrid,niceScroll,dialog,page){
	var wh=$(window).height()-190+"px",
	    ww=$(window).width()-260+"px";

    $('.boxtop').css('width',ww);
    $('.boxtop').css('height',wh);
	
	//列参数
    var cols = [
        { title:'编号', name:'ptid' ,width:200, align:'left' },
        { title:'角色', name:'ptnm' ,width:150, align:'left'},
        { title:'使用', name:'usfg' ,width:150, align:'center'},
        { title:'描述', name:'remk' ,width:200, align:'left'}
    ];
    var usnm=sessionStorage.getItem('usnm'),
		product=sessionStorage.getItem('product'),
		loginuser={'usnm':usnm,'prod':product},
		userdata,cheurl='',pagedata,pagenum,checkedid,checkedcheck={},prevpage;
    var userkey=JSON.parse( sessionStorage.getItem('menu') ).userKey;	
    //请求渲染数据;
    getlis();
    function getlis(){
    	 $.ajax({
 			url:'/fx/roleList.do',
 			type:'post',
 			contentType:'application/json',
 			data:userkey,
 			async:true,
 			success:function(data){
 				if(data.code==00){
 					userdata=JSON.parse( data.codeMessage );
 					dialog.ren({'cols':cols,'wh':wh,'userdata':userdata});
 				}else if(data.code==02){
 					dialog.ren({'cols':cols,'wh':wh,'userdata':''});
 				}
	 		}
	 	});
    }
    //点击添加按钮；
	$('.add').click(function(){
		dialog.rolemanaadd('add');
		$('.numb',parent.document).blur(function(){
			if( $(this).val()=='' ){
				$('.numb',parent.document).parent('p').append('<re>编号不能为空</re>');
			}else{
				$('.numb',parent.document).parent('p').find('re').remove();
			}
		});
		$('.rolename',parent.document).blur(function(){
			if( $(this).val()=='' ){
				$('.rolename',parent.document).parent('p').append('<re>角色名称不能为空</re>');
			}else{
				$('.rolename',parent.document).parent('p').find('re').remove();
			}
		});
		$('.desc',parent.document).blur(function(){
			if( $(this).val()=='' ){
				$('.desc',parent.document).parent('p').append('<re>描述不能为空</re>');
			}else{
				$('.desc',parent.document).parent('p').find('re').remove();
			}
		});
	});
	//点击修改按钮;
	$('.modify').click(function(){
		if( !$('.box tr').hasClass('selected') ){
			dialog.choicedata('请选择一条数据','rolemana','aaa');
		}else{
			dialog.rolemanaadd('modify');
			/*--------获取数据,赋值给弹出框-----------*/
			$('.numb',parent.document).val( $('.box tr.selected td:eq(0) span').text()),
			$('.rolename',parent.document).val($('.box tr.selected td:eq(1) span').text());
			$('.desc',parent.document).val($('.box tr.selected td:eq(3) span').text());
			
			if($('.box tr.selected td:eq(2) span').text()=='启用'){
				choicestate='usestart';
			}else{
				choicestate='usestop';
			}
			$('.cheradio input[type="radio"]',parent.document).each(function(i,v){
				if( $(v).data('tit')==choicestate){
					$(v).prop('checked','checked');
					$(v).siblings().prop('checked','');
				}
			});
		}
	});
	
	//点击请选择一条数据的确定按钮；
	$('body',parent.document).on('click','.rolemana .sure',function(){
		$(this).closest('.rolemana').remove();
	});
	//点击添加修改弹出框的确认按钮；
	$('body',parent.document).on('click','.rolemanasav',function(){
		var numb=$('.numb',parent.document).val(),
			rolename=$('.rolename',parent.document).val(),
			radi=$('.cheradio input:checked',parent.document).data('tit'),
			desc=$('.desc',parent.document).val(),rad,url,
			$this=$(this);
		if(radi=='usestart'){
			rad=0;
		}else{
			rad=1;
		}
		var vo={'userKey':userkey,'role':{'ptid':numb,'ptnm':rolename,'usfg':rad,'remk':desc}}
	
	  	if(numb==''){
	  		$('.numb',parent.document).parent('p').find('re').remove();
	  		$('.numb',parent.document).parent('p').append('<re>编号不能为空</re>');
	  	}
	  	if(rolename==''){
	  		$('.rolename',parent.document).parent('p').find('re').remove();
	  		$('.rolename',parent.document).parent('p').append('<re>角色名称不能为空</re>');
	  	}
	  	if(desc==''){
	  		$('.desc',parent.document).parent('p').find('re').remove();
	  		$('.desc',parent.document).parent('p').append('<re>描述不能为空</re>');
	  	}
	  	if( !$('.pubhead',parent.document).hasClass('add') ){
	  		url='editrole.do';
	  	}else{
	  		url='addrole.do';
	  	}
	  	//没有错误信息
	  	if( $('re',parent.document).length==0 ){
	  		$.ajax({
				url:url,
				type:'post',
				contentType:'application/json',
				data:JSON.stringify(vo),
				async:false,
				success:function(data){
					$('.rolemanasav',parent.document).closest('.zhezhao').remove();
					if(data.code==00){
						dialog.choicedata(data.codeMessage,'rolemana','success');
						getlis();
					}else if(data.code==01){
						dialog.choicedata(data.codeMessage,'rolemana','aaa');
					}
				}
			});
	  	}
	});
	$('.del').click(function(){
		//删除按钮;
		if( !$('.box tr').hasClass('selected') ){
			dialog.choicedata('请选择一条数据','rolemana','aaa');
		}else{
			dialog.cancelDate('您确定要删除当前记录吗?','rolemana','del');
		}
	});
	/*------------------------点击删除重置----------------------------*/
	$('body',parent.document).on('click','.rolemana .confirm',function(){
		var vo,url,list=[],mnid,mamn,sbmn,tipmsg,cheurl; 
		if( $(this).parents('.dia').data('tit')=='del' ){
			//点击确认+ajax;
			vo={'userKey':userkey,'role':{'ptid':$('.box tr.selected td:eq(0) span').text()}};
			$.ajax({
				url:'/fx/deleterole.do',
				type:'post',
				contentType:'application/json',
				data:JSON.stringify(vo),
				async:true,
				success:function(data){
					$('.rolemana .confirm',parent.document).closest('.zhezhao').remove();
					if(data.code==00){
						$('.box tbody tr.selected').remove();
						dialog.choicedata(data.codeMessage,'rolemana','aaa');
						getlis();
					}else if(data.code==01){
						dialog.choicedata(data.codeMessage,'rolemana','aaa');
					}
				}
			});
		}else if($(this).parents('.dia').data('tit')=='choicecen'){  //////权限新增、删除；
			if( $('.dia .dia_text',parent.document).text()=='您确定要添加当前选择的数据吗?' ){
				url='addNr.do';
				cheurl='addlistNr.do';
			}else{
				url='delNr.do';  ///权限删除按钮；
				cheurl='dellistNr.do';
			}
			/*$('.cer tbody tr input[type="checkbox"]',parent.document).each(function(i,v){
				if( $(this).prop('checked') ){
					mnid=$(this).parents('tr').find('td').eq(1).text(),
					mamn=$(this).parents('tr').find('td').eq(2).text(),
					sbmn=$(this).parents('tr').find('td').eq(3).text();
					list.push({'mnid':mnid,'mamn':mamn,sbmn:sbmn});
				}
			});*/
			console.log( checkedcheck )
			for(var i in checkedcheck){
				for( var j=0,num=checkedcheck[i].length;j<num;j++){
					if( checkedcheck[i]!=''){    //判断，如果checkedcheck 这个二维数组的一级不是空，就遍历；
						list.push({
								'mnid':checkedcheck[i][j].split(',')[0],
								'mamn':checkedcheck[i][j].split(',')[1],
								'sbmn':checkedcheck[i][j].split(',')[2]
						});
					}
				}
			}
			vo={'userKey':userkey,'role':{'ptid':checkedid},'list':list};
			$.ajax({
				url:url,
				type:'post',
				async:true,
				contentType:'application/json',
				data:JSON.stringify(vo),
				success:function(data){
					if(data.code=='00'){
						$('.rolemana .confirm',parent.document).closest('.zhezhao').remove();
						changeurl(cheurl,null);  //在数据重置完之后，再次请求数据;
						dialog.choicedata(data.codeMessage,'rolemana','aaa');
					}else{
						dialog.choicedata(data.codeMessage,'rolemana','aaa');
					}
				},
				complete:function(){
					for(var i=1,num=$('.allpages',parent.document).text();i<=num;i++){ //根据返回的总页数,初始化这个对象；
						checkedcheck[i]=[];
					}
					console.log( checkedcheck );
				}
			});
		}else{
			$('.rolemana .confirm',parent.document).closest('.zhezhao').remove();
		}
	});
	
	$('body',parent.document).on('click','.rolemanacance,.rolemana .cancel,.rolemanaclose',function(){
		$(this).closest('.zhezhao').remove();
	});
	/*----------------快速搜索功能的实现------------------------*/
	$('.mana_serbtn').click(function(){
		  dialog.serchData( $('.mana_seript').val());
    });
	//点击权限新增;
	$('.poweradd').click(function(){
		if( $('.box tr').hasClass('selected') ){
			checkedid=$('.box tr.selected td:eq(0) span').text();
			dialog.poweradd('rolemana','add');
			cheurl='addlistNr.do';
			changeurl(cheurl,null);
			for(var i=1,num=$('.allpages',parent.document).text();i<=num;i++){ //根据返回的总页数,初始化这个对象；
				checkedcheck[i]=[];
			}
		}else{
			dialog.choicedata('请选择一条数据','rolemana','aaa');
		}
	});
	//删除权限;
	$('.powerdel').click(function(){
		if( $('.box tr').hasClass('selected') ){
			checkedid=$('.box tr.selected td:eq(0) span').text();
			dialog.poweradd('rolemana','del');
			cheurl='dellistNr.do';
			changeurl(cheurl,null);
			for(var i=1,num=$('.allpages',parent.document).text();i<=num;i++){ //根据返回的总页数,初始化这个对象；
				checkedcheck[i]=[];
			}
		}else{
			dialog.choicedata('请选择一条数据','rolemana','aaa');
		}
	});
	//查看权限;
	$('.seepower').click(function(){
		if( $('.box tr').hasClass('selected') ){
			checkedid=$('.box tr.selected td:eq(0) span').text();
			dialog.poweradd('rolemana','see');
			cheurl='sellistNr.do';
			changeurl(cheurl,null);
			for(var i=1,num=$('.allpages',parent.document).text();i<=num;i++){ //根据返回的总页数,初始化这个对象；
				checkedcheck[i]=[];
			}
		}else{
			dialog.choicedata('请选择一条数据','rolemana','aaa');
		}
	});
	//点击权限新增---确定按钮;
	$('body',parent.document).on('click','.rolemana .addsav',function(){
		if($(this).text()=='全选'){
			$('body .cer tbody tr:not(".lockhead")',parent.document).each(function(i,v){
				$(v).find('input[type="checkbox"]').prop('checked','checked');
				
				//if( checkedcheck[$('.nowpage',parent.document).text()].indexOf( $(v).find('td').eq(1).text() )==-1 ){
					checkedcheck[$('.nowpage',parent.document).text()].push( $(v).find('td').eq(1).text()+','+$(v).find('td').eq(2).text()+','+$(v).find('td').eq(3).text());
				//}
			});
			$(this).text('全不选');
		}else{
			$('body .cer tbody tr',parent.document).each(function(i,v){
				$(v).find('input[type="checkbox"]').prop('checked','');
			});
			$(this).text('全选');
			checkedcheck[$('.nowpage',parent.document).text()]=[];
		}
	});
	
	//根据添加、删除、查看判断；
	function changeurl(obj,obj1){
		console.log( checkedcheck );
		var vo={'userKey':userkey,'role':{'ptid':checkedid},'pageNum':obj1,'pageSize':10};
		$.ajax({
			url:obj,
			type:'post',
			contentType:'application/json',
			data:JSON.stringify(vo),
			async:false,
			success:function(data){
				//$('.zhezhao',parent.document).remove();
				if(data.code==00){
					var userdata=data.codeMessage,
						str='<tr class="lockhead"><th>修改</th></tr>',
						ischecked;
						pagedata=userdata;
					for(var i in userdata.list){
						if(userdata.list[i].rofg=='0'){
							if(obj=='sellistNr.do'){
								ischecked="<input type='checkbox' disabled/>";
							}else{
								ischecked="<input type='checkbox'/>";
							}
						}else{//权限新增  权限删除；
							if(obj=='sellistNr.do'){
								ischecked="<input type='checkbox' checked='checked' disabled/>";
							}else{
								ischecked="<input type='checkbox'/>";
							}
						}
						str+="<tr><td>"+ischecked+"</td><td>"+userdata.list[i].mnid+"</td><td>"+userdata.list[i].mamn+"</td><td>"+userdata.list[i].sbmn+"</td></tr>"
					}
					str1='<span class="first">首页</span><span class="prevbtn">上一页</span><span class="nextbtn">下一页</span><span class="last">末页</span><span class="allnumpre">第<i class="nowpage">'+userdata.pageNum+'</i>页/共<i class="allpages">'+userdata.pages+'</i>页</span><span class="allnum">共'+userdata.total+'条记录</span>',
					$('.pageTest',parent.document).html(' ').append(str1);
					$('.rolemana .cer tbody',parent.document).html(' ').append(str);
				}else if(data.code==01){
					dialog.choicedata(userdata.codeMessage,'rolemana','aaa');
				}
			}
		});
	}
	
	//点击前一页和后一页 首页 末页；
	$('body',parent.document).on('click','.rolemana .prevbtn',function(){
		prevpage=$('.nowpage',parent.document).text()-1;
		if( pagedata.hasPreviousPage!=false){
			pagenum=--pagedata.pageNum;
			if($('.pubhead',parent.document).data('tit')=='see'){
				changeurl('sellistNr.do',pagenum);
			}else if($('.pubhead',parent.document).data('tit')=='add'){
				changeurl('addlistNr.do',pagenum);
			}else{
				changeurl('dellistNr.do',pagenum);
			}
			checkischecked(prevpage,$('.rolemana .pubhead',parent.document).data('tit'));
		}
	});
	
	$('body',parent.document).on('click','.rolemana .nextbtn',function(){  //点击下一页;
		prevpage=parseInt( $('.nowpage',parent.document).text() )+1;
		if(pagedata.hasNextPage!=false){
			pagenum=++pagedata.pageNum;
			if($('.pubhead',parent.document).data('tit')=='see'){
				changeurl('sellistNr.do',pagenum);
			}else if($('.pubhead',parent.document).data('tit')=='add'){
				changeurl('addlistNr.do',pagenum);
			}else{
				changeurl('dellistNr.do',pagenum);
			}
			checkischecked(prevpage,$('.rolemana .pubhead',parent.document).data('tit'));
		}
	}); 
	$('body',parent.document).on('click','.rolemana .first',function(){   //点击首页的时候不需要初始化;再点击全县新增的时已经初始化;
		if( pagedata.hasPreviousPage!=false){
			pagenum=1;
			if($('.pubhead',parent.document).data('tit')=='see'){
				changeurl('sellistNr.do',pagenum);
			}else if($('.pubhead',parent.document).data('tit')=='add'){
				changeurl('addlistNr.do',pagenum);
			}else{
				changeurl('dellistNr.do',pagenum);
			}
			checkischecked(1,$('.rolemana .pubhead',parent.document).data('tit') );
		}
	});
	$('body',parent.document).on('click','.rolemana .last',function(){
		prevpage=parseInt( $('.allpages',parent.document).text() );
		if(pagedata.hasNextPage!=false){
			pagenum=pagedata.pages;
			if($('.pubhead',parent.document).data('tit')=='see'){
				changeurl('sellistNr.do',pagenum);
			}else if($('.pubhead',parent.document).data('tit')=='add'){
				changeurl('addlistNr.do',pagenum);
			}else{
				changeurl('dellistNr.do',pagenum);
			}
			checkischecked(prevpage,$('.rolemana .pubhead',parent.document).data('tit'));
		}
	});
	//点击权限修改的增加按钮;
	$('body',parent.document).on('click','.addrole',function(){
		var tit=$('body .pubhead',parent.document).data('tit'),url,list=[],mnid,mamn,sbmn,tipmsg,
			wrongnum=0;
		if(tit=='add'){
			tipmsg='您确定要添加当前选择的数据吗?';
		}else if(tit=='del'){
			tipmsg='您确定要删除当前选择的数据吗?';
		}
		for(var i in checkedcheck){
			for(var j=0,num=checkedcheck[i].length;j<num;j++){
				if( checkedcheck[i][j]==''){
					
				}else{
					wrongnum++;
				}
			}
		}
		if( wrongnum>0 ){
			dialog.cancelDate(tipmsg,'rolemana','choicecen');
		}else{
			dialog.choicedata('请先勾选数据!','rolemana','aaa');
		}
	});
	//关闭新增框;
	$('body',parent.document).on('click','.rolemana .addrolecance,.rolemana .close',function(){
		$('.rolemana',parent.document).remove();
	});
	//判断全选全不选;
	$('body',parent.document).on('click','.cer tbody tr input[type="checkbox"]',function(i){
		var trlength=$('.cer tbody tr',parent.document).length-1,
			checkedlength=0;
			if( !$(this).prop('checked') ){
				checkedlength--;
				$(this).prop('checked','');
				for(var i in checkedcheck[$('.nowpage',parent.document).text()]){
					if( checkedcheck[$('.nowpage',parent.document).text()][i].split(',')[0]==$(this).parents('tr').find('td').eq(1).text() ){
						checkedcheck[$('.nowpage',parent.document).text()].splice(i,1);
					}
				}
			}else{
				checkedlength++;
				$(this).prop('checked','checked');
				if( checkedcheck[$('.nowpage',parent.document).text()].indexOf( $(this).parents('tr').find('td').eq(1).text() )==-1){
					checkedcheck[$('.nowpage',parent.document).text()].push( $(this).parents('tr').find('td').eq(1).text()+","+$(this).parents('tr').find('td').eq(2).text()+','+$(this).parents('tr').find('td').eq(3).text() );
				}
			}
			checkedlength=$('.cer tbody tr input[type="checkbox"]:checked',parent.document).length;
			if(checkedlength==trlength){
				$('.addsav',parent.document).text('全不选');
			}else{
				$('.addsav',parent.document).text('全选');
			}
	});
//	function ren(obj){
//		$('.mmGrid').remove();
//    	$('body').append('<div class="box"></div>');
//    	var mmg = $('.box').mmGrid({
//			width:'75%'
//			,height:"73%"
//			, cols: cols
//			,items:obj
//	        , nowrap:true
//	        , fullWidthRows:true
//	        ,showBackboard:true
//		});
//	}
	//权限新增中的点击分页可以保存选中状态;
	function checkischecked(obj,obj1){
		if(obj1!='see'){
			if( checkedcheck[obj].length>0){
				$('body .cer tbody tr:not(".lockhead")',parent.document).each(function(i,v){
					for(var j in checkedcheck[obj]){
						if( $(v).find('td').eq(1).text()==checkedcheck[obj][j].split(',')[0] ){
							$(v).find('input[type="checkbox"]').prop('checked','checked');
						}
					}
				});
			}
			if( checkedcheck[obj].length==$('body .cer tbody tr:not(".lockhead")',parent.document).length ){
				$('.rolemana .addsav',parent.document).text('全不选');
			}else{
				$('.rolemana .addsav',parent.document).text('全选');
			}
		}
	}
/*	function rolemanareder(obj){
		$.ajax({
			url:obj,
			type:'post',
			contentType:'application/json',
			data:userkey,
			async:true,
			success:function(data){
				$('.rolemana .sure',parent.document).closest('.rolemana').remove();
				if(data.code==00){
					//ren( JSON.parse( data.codeMessage) );
					dialog.ren({'cols':cols,'wh':wh,'userdata':JSON.parse( data.codeMessage)});
				}else if(data.code==02){
					dialog.choicedata(data.codeMessage,'rolemana','aaa');
				}
			}
		});
	}
    $(".mmg-bodyWrapper").niceScroll({
		touchbehavior:false,
		cursorcolor:"#666",
		cursoropacitymax:0.7,
		cursorwidth:6,
		background:"#ccc",
		autohidemode:false,
		horizrailenabled:false
    });*/
});
