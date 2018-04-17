require.config({
	baseUrl:'/fx/js/',
	shim:{
		'jquery.niceScroll': {
			deps: ['jquery'],
			exports: 'jquery.niceScroll'},
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
	//列参数
    var cols = [
        { title:'编号', name:'ptid' ,width:200, align:'left' },
        { title:'角色', name:'ptnm' ,width:150, align:'left'},
        { title:'使用', name:'usfg',width:150, align:'center'},
        { title:'状态', name:'rofg' ,width:200, align:'left'},
        { title:'描述', name:'remk' ,width:200, align:'left'}
    ];
    var usnm=sessionStorage.getItem('usnm'),
		product=sessionStorage.getItem('product'),
		loginuser={'usnm':usnm,'prod':product},
		checkedid;
    var userkey=JSON.parse( sessionStorage.getItem('menu') ).userKey,
    	checkedcheck={};
    //问题：弹窗对应提示的文本化；
    getlist();
    function getlist(){
		 $.ajax({
			url:'/fx/roleList.do',
			type:'post',
			contentType:'application/json',
			data:userkey,
			async:false,
			success:function(data){
				if(data.code==00){
					var userdata=JSON.parse( data.codeMessage );
	                dialog.ren({'cols':cols,'wh':wh,'userdata':userdata});
				}else if(data.code==02){
					 dialog.ren({'cols':cols,'wh':wh,'userdata':''});
				}
			}
		});
    }
	/*----------------复核按钮的弹窗------------------------*/
	$('.audi_review').click(function(){
		if( $('.box tbody tr').hasClass('selected') ){
			//修改弹出框;+获取当前selected的数据;
			checkedid=$('.box tr.selected td:eq(0) span').text();
			dialog.poweradd('roleauditing','review');
			changeurl('fglistNr.do',$('.allpages',parent.document).text()*1);
			for(var i=1,num=$('.allpages',parent.document).text();i<=num;i++){ //根据返回的总页数,初始化这个对象；
				checkedcheck[i]=[];
			}
		}else{
			dialog.choicedata('请先选择一条数据!','roleauditing','aaa');
		}
	})
	$('body',parent.document).on('click','.roleauditing .sure',function(){
		//关闭选择一条数据弹框;
		var a=$('.userinfo',parent.document).data('tit'),url='';
		$(this).closest('.roleauditing').remove();
	});
	//点击通过未通过；
	$('body',parent.document).on('click','.passrole',function(){
		if( $('.roleauditing .cer tbody tr input[type="checkbox"]:checked',parent.document).length>0 ){
			dialog.cancelDate('您确定要复核当前选择的数据吗?','roleauditing','pass');
		}else{
			dialog.choicedata('请先勾选数据!','roleauditing','aaa');
		}
	});
	$('body',parent.document).on('click','.nopassrole',function(){
		if( $('.roleauditing .cer tbody tr input[type="checkbox"]:checked',parent.document).length>0 ){
			dialog.cancelDate('您确定要复核当前选择的数据吗?','roleauditing','nopass');
		}else{
			dialog.choicedata('请先勾选数据!','roleauditing','aaa');
		}
	});
	//点击确定按钮；
	$('body',parent.document).on('click','.confirm',function(){
		var vo,url,mnid,mamn,sbmn,tipmsg,cheurl; 
		if( $(this).parents('.dia').data('tit')=='pass' ){
			url='roleNrlrgtFg.do';
			passornopass( url);
		}else if($(this).parents('.dia').data('tit')=='nopass'){
			url='roleNrNo.do';
			passornopass( url);
		}
		$('.roleauditing .confirm',parent.document).closest('.zhezhao').remove();
	});
	//点击权限新增确定按钮;
	$('body',parent.document).on('click','.roleauditing .addsav',function(){
		if($(this).text()=='全选'){
			$('body .cer tbody tr:not(".lockhead")',parent.document).each(function(i,v){
				$(v).find('input[type="checkbox"]').prop('checked','checked');
			
				if( checkedcheck[$('.nowpage',parent.document).text()].indexOf( $(v).find('td').eq(1).text() )==-1 ){
					checkedcheck[$('.nowpage',parent.document).text()].push( $(v).find('td').eq(1).text()+','+$(v).find('td').eq(2).text()+','+$(v).find('td').eq(3).text());
				}
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
	//判断全选全不选;
	$('body',parent.document).on('click','.roleauditing .cer tbody tr input[type="checkbox"]',function(i){
		var trlength=$('.roleauditing .cer tbody tr',parent.document).length-1,
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
			checkedlength=$('.roleauditing .cer tbody tr input[type="checkbox"]:checked',parent.document).length;
			if(checkedlength==trlength){
				$('.roleauditing .addsav',parent.document).text('全不选');
			}else{
				$('.roleauditing .addsav',parent.document).text('全选');
			}
	});
	function passornopass(obj){
		var list=[];
		//截取checklist;
		for(var i in checkedcheck){
			for( var j=0,num=checkedcheck[i].length;j<num;j++){
				if( checkedcheck[j]!=''){
					list.push({
							'mnid':checkedcheck[i][j].split(',')[0],
							'mamn':checkedcheck[i][j].split(',')[1],
							'sbmn':checkedcheck[i][j].split(',')[2]
					});
				}
			}
		}
		/*$('.roleauditing .cer tbody tr input[type="checkbox"]',parent.document).each(function(i,v){
			if( $(this).prop('checked') ){
				mnid=$(this).parents('tr').find('td').eq(1).text(),
				mamn=$(this).parents('tr').find('td').eq(2).text(),
				sbmn=$(this).parents('tr').find('td').eq(3).text();
				list.push({'mnid':mnid,'mamn':mamn,sbmn:sbmn});
			}
		});*/
		vo={'userKey':userkey,'role':{'ptid':checkedid},'list':list};
		$.ajax({
			url:obj,
			type:'post',
			async:true,
			contentType:'application/json',
			data:JSON.stringify(vo),
			success:function(data){
				if(data.code=='00'){
					$('.roleauditing .confirm',parent.document).closest('.zhezhao').remove();
					changeurl('fglistNr.do',null);
					dialog.choicedata(data.codeMessage,'roleauditing','aaa');
				}else{
					dialog.choicedata(data.codeMessage,'roleauditing','aaa');
				}
			},
			complete:function(){
				for(var i=1,num=$('.allpages',parent.document).text();i<=num;i++){ //根据返回的总页数,初始化这个对象；
					checkedcheck[i]=[];
				}
				//console.log( checkedcheck );
			}
		});
	}
	$('body',parent.document).on('click','.close,.addrolecance,.cancel',function(){
		$(this).closest('.zhezhao').remove();
	});
	/*----------------快速搜索功能的实现------------------------*/
	$('.audi_serbtn').click(function(){
		  dialog.serchData( $('.audi_seript').val() );
    });
    
  //点击前一页和后一页 首页 末页；
	/*$('body',parent.document).on('click','.roleauditing .prevbtn',function(){
		if( pagedata.hasPreviousPage!=false){
			pagenum=--pagedata.pageNum;
			changeurl('fglistNr.do',pagenum);
		}
	});
	$('body',parent.document).on('click','.roleauditing .nextbtn',function(){
		if(pagedata.hasNextPage!=false){
			pagenum=++pagedata.pageNum;
			changeurl('fglistNr.do',pagenum);
		}
	});
	$('body',parent.document).on('click','.roleauditing .first',function(){
		if( pagedata.hasPreviousPage!=false){
			pagenum=1;
			changeurl('fglistNr.do',pagenum);
		}
	});
	$('body',parent.document).on('click','.roleauditing .last',function(){
		if(pagedata.hasNextPage!=false){
			pagenum=pagedata.pages;
			changeurl('fglistNr.do',pagenum);
		}
	});*/
	//点击前一页和后一页 首页 末页；
	$('body',parent.document).on('click','.roleauditing .prevbtn',function(){
		prevpage=$('.nowpage',parent.document).text()-1;
		if( pagedata.hasPreviousPage!=false){
			pagenum=--pagedata.pageNum;
			changeurl('fglistNr.do',pagenum);
			checkischecked(prevpage,$('.roleauditing .pubhead',parent.document).data('tit'));
		}
	});
	
	$('body',parent.document).on('click','.roleauditing .nextbtn',function(){  //点击下一页;
		prevpage=parseInt( $('.nowpage',parent.document).text() )+1;
		if(pagedata.hasNextPage!=false){
			pagenum=++pagedata.pageNum;
			changeurl('fglistNr.do',pagenum);
			checkischecked(prevpage,$('.roleauditing .pubhead',parent.document).data('tit'));
		}
	}); 
	$('body',parent.document).on('click','.roleauditing .first',function(){  
		if( pagedata.hasPreviousPage!=false){
			pagenum=1;
			changeurl('fglistNr.do',pagenum);
			checkischecked(1,$('.roleauditing .pubhead',parent.document).data('tit') );
		}
	});
	$('body',parent.document).on('click','.roleauditing .last',function(){
		prevpage=parseInt( $('.allpages',parent.document).text() );
		if(pagedata.hasNextPage!=false){
			pagenum=pagedata.pages;
			changeurl('fglistNr.do',$('.allpages',parent.document).text()*1);
			checkischecked(prevpage,$('.roleauditing .pubhead',parent.document).data('tit'));
		}
	});
	//权限新增中的点击分页可以保存选中状态;
	function checkischecked(obj,obj1){
		//console.log( checkedcheck )
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
				$('.roleauditing .addsav',parent.document).text('全不选');
			}else{
				$('.roleauditing .addsav',parent.document).text('全选');
			}
		}
	}
  //根据添加、删除、查看判断；
	function changeurl(obj,obj1){
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
				
						if(userdata.list[i].selected==false){
							ischecked="<input type='checkbox'/>";
						}else{
							ischecked="<input type='checkbox' checked='checked'/>";
						}
						str+="<tr><td>"+ischecked+"</td><td>"+userdata.list[i].mnid+"</td><td>"+userdata.list[i].mamn+"</td><td>"+userdata.list[i].sbmn+"</td></tr>"
					}
					str1='<span class="first">首页</span><span class="prevbtn">上一页</span><span class="nextbtn">下一页</span><span class="last">末页</span><span class="allnumpre">第<i class="nowpage">'+userdata.pageNum+'</i>页/共<i class="allpages">'+userdata.pages+'</i>页</span><span class="allnum">共'+userdata.total+'条记录</span>',
					$('.pageTest',parent.document).html(' ').append(str1);
					$('.roleauditing .cer tbody',parent.document).html(' ').append(str);
				}else if(data.code==01){
					dialog.choicedata(userdata.codeMessage,'roleauditing','aaa');
				}
			}
		});
	}
})
