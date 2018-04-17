/*调整日配置信息查询*/
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
		'table2excel':{
			deps:['jquery'],
			exports:'table2excel'
		}
	},
	paths:{
		jquery:'js_files/jquery-1.9.1.min',
		mmGrid:'js_files/mmGrid',
		niceScroll:'js_files/jquery.nicescroll.min',
		dialog:'dialog',
		table2excel:'js_files/excel',
		'wdatepicker':'My97DatePicker/WdatePicker'

	}
});
require(['jquery','mmGrid','niceScroll','dialog','wdatepicker','table2excel'],function($,mmGrid,niceScroll,dialog,wdatepicker,table2excel){
	var wh=$(window).height()-190+"px",
		ww=$(window).width()-260+"px";
	$('.boxtop').css('width',ww);
	$('.boxtop').css('height',wh);
	
	var userdata='',
		tit=$('title').text(),str='',
		product=sessionStorage.getItem('product'),
		text=$('.head span:eq(1)').text().substr(5),url,
		userkey=JSON.parse( sessionStorage.getItem('menu') ).userKey,listnumtotal,
		chedata;
	
	if(text=='调整日信息查询'){
		$('#d1,#d2').val( dialog.today() );
		//列参数;
	    var cols = [
	        { title:'交易币种', name:'EXNM' ,width:80, align:'left' },
	        { title:'调整日期', name:'FEMD' ,width:110, align:'right'},
	        { title:'调整状态', name:'STAT' ,width:110, align:'center'}
	    ]
		url='accex/selOilAdjustList.do';
	}else if(text=='账户余额查询'){
		var cols = [
			        { title:'交易账号', name:'trac' ,width:80, align:'left' },
			        { title:'客户号', name:'cuno' ,width:110, align:'left'},
			        { title:'卡号', name:'cuac' ,width:110, align:'left'},
			        { title:'客户类型', name:'caty' ,width:100, align:'center'},
			        { title:'签约标识', name:'refg' ,width:100, align:'center'},
			        { title:'通知手机号', name:'tlno' ,width:100, align:'left'},
			        { title:'机构号', name:'ogcd' ,width:100, align:'left'},
			        { title:'机构名称', name:'ognm' ,width:100, align:'left'},
			        { title:'上级机构号', name:'upcd' ,width:80, align:'left'},
			        { title:'上级机构名称', name:'upnm' ,width:80, align:'left'},
			        { title:'签约机构', name:'rgog' ,width:100, align:'left'},
			        { title:'签约柜员', name:'rgtl' ,width:100, align:'left'},
			        { title:'签约日期', name:'rgdt' ,width:100, align:'right'},
			        { title:'签约时间', name:'rgtm' ,width:100, align:'right'},
			        { title:'解约日期', name:'crdt' ,width:100, align:'right'},
			        { title:'解约时间', name:'crtm' ,width:80, align:'right'},
			        { title:'签约修改日期', name:'mddt' ,width:80, align:'right'},
			        { title:'签约修改时间', name:'mdtm' ,width:100, align:'right'},
			        { title:'签约渠道', name:'rgch' ,width:80, align:'left'},
			        { title:'签约编号', name:'rgid' ,width:80, align:'left'}
			    ]
		 url='getOrderListFromVie.do'
	} 
	if( $('.cuur select option').length>0 ){
		
	}else{
		$.ajax({
			url:'/fx/accex/selCurrencyPair.do',
			type:'post',
			contentType:'application/json',
			data:userkey,
			async:false,
			success:function(data){
				var str='';
				if(data.code==01){
	   				for(var i in data.codeMessage){
	   					str+='<option>'+data.codeMessage[i].EXNM+'</option>'
	   				}
	   				$('.cuur select').html( str );
				}else{
	   				
	   			}
			}
		}); 
	}
	
    getList({'exnm':$('.cuur select option:selected').text(),'beginDate':$('#d1').val(),'endDate':$('#d2').val(),'pageNo':1,'pageSize':10});
    renpage();
    $('.query').click(function(){
       // vo={'prod':product,'startDate':startDate,'endDate':endDate,'pageNo':1,'pageSize':10};
        if(text=='调整日信息查询'){
        	getList({'exnm':$('.cuur select option:selected').text(),'beginDate':$('#d1').val(),'endDate':$('#d2').val(),'pageNo':1,'pageSize':10});
        	renpage();
        }
       // getList(vo,url);
    })
    //封装请求列表
	function getList(obj){
		$.ajax({
    		url:url,
    		type:'post',
    		contentType:'application/json',
    		data:JSON.stringify(obj),
    		async:false,
    		success:function(data){
    			if(data.code==01){
       				userdata=data.codeMessage ;
       				ren({'cols':cols,'wh':wh,'userdata':userdata.list});
       				chedata=userdata.list;
       				listnumtotal=userdata.total;
       				/*$('.page').remove();
    				$('.boxtop').append('<div class="page"><span class="first">首页</span><span class="prev">上一页</span><span class="next">下一页</span><span class="last">末页</span><span class="nopage">第<b class="Nopage">'+userdata.pageNum+'</b>页/共<b class="Totalpage">'+userdata.pages+'</b>页</span><span class="totalpage">共<b class="total">'+userdata.total+'</b>条记录</span></div>')
    				*/
    			}else{
       				//无数据
       				dialog.ren({'cols':cols,'wh':wh,'userdata':''});
       			}
    		}
    	}); 
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
    		    		 getList({
    		    			 'exnm':$('.cuur select option:selected').text(),
    		    			 'beginDate':$('#d1').val(),
    		    			 'endDate':$('#d2').val(),
    		    			 'pageNo':obj.curr,
    		    			 'pageSize':$('.layui-laypage-limits select option:selected').attr('value')*1
    		    		 });
    		    	}	
    		    }
    		  });
    	});
    }
    function ren( obj ){
    	$('.boxtop').html('');
    	$('#ascrail2000').remove();
    	$('.boxtop').append('<div class="box"></div>');
		var mmg = $('.box').mmGrid({
				height:obj.wh
				, cols: obj.cols
				,items:obj.userdata
	            , nowrap:true
	            , fullWidthRows:true
	            , checkCol:obj.checked
	            , multiSelect:obj.select
	            ,showBackboard:true
	  	});
		 $(".mmg-bodyWrapper").niceScroll({
				touchbehavior:false,
				cursorcolor:"#666",
				cursoropacitymax:0.7,
				cursorwidth:6,
				background:"#ccc",
				autohidemode:false,
				horizrailenabled:true
		  });
		/* mmg.on('loadSuccess',function(){
			 if( obj.userdata.length>0){
				 $('.box tr').each(function(i,v){
					 

						
					 $(v).find('td').attr('femd',obj.userdata[i].FEMD),
					 $(v).find('td').attr('ostp',obj.userdata[i].OSTP),
					 $(v).find('td').attr('femd',obj.userdata[i].femd),
					 $(v).find('td').attr('femd',obj.userdata[i].femd),
					 $(v).find('td').attr('femd',obj.userdata[i].femd),
					 $(v).find('td').attr('femd',obj.userdata[i].femd),
					 $(v).find('td').attr('femd',obj.userdata[i].femd),
					 $(v).find('td').attr('femd',obj.userdata[i].femd),
					 $(v).find('td').attr('femd',obj.userdata[i].femd),
					 $(v).find('td').attr('femd',obj.userdata[i].femd),
					 $(v).find('td').attr('femd',obj.userdata[i].femd),
					 $(v).find('td').attr('femd',obj.userdata[i].femd),
					 $(v).find('td').attr('femd',obj.userdata[i].femd),
					 
					 femd:$('.adjustday',parent.document).val(),
	    			ostp:$('.ljpri input',parent.document).val(),
	    			osbs:$('.jslsbid input',parent.document).val(),
	    			osas:$('.jslaask input',parent.document).val(),
	    			nstp:$('.nextjspri input',parent.document).val(),
	    			nrbs:$('.nextjslsbid input',parent.document).val(),
	    			nsas:$('.nextjslsask input',parent.document).val(),
	    			orbs:$('.lamabiddot input',parent.document).val(),
	    			oras:$('.lamaaskdot input',parent.document).val(),
	    			nsbs:$('.nebiddot input',parent.document).val(),
	    			nras:$('.nemaaskdot input',parent.document).val()
				 });
			 }
		 })*/
    }
    $('.toexcel').click(function(){
		if( $('.box tbody tr').length>1 ){
			$(".mmg-body").table2excel({
				exclude: ".noExl",
				name: "Excel Document Name",
				filename: tit + new Date().toISOString().replace(/[\-\:\.]/g, ""),
				fileext: ".xls",
				exclude_img: true,
				exclude_links: true,
				exclude_inputs: true
			});
		}
	});
    //添加；
    $('.pub_add').click(function(){
    	dialog.adjustadd('adjustmentday','add');
    	$( '.dealcuur',parent.document ).html( $('.cuur select').html() );
    	
    	//上期结算机构价格-失焦，自动触发计算；
    	$('.ljpri input',parent.document).on('blur',function(){
    		var txt=$(this).val(),
    			txt1=$('.jslsbid input',parent.document).val(),
    			txt2=$('.jslaask input',parent.document).val();
    		if( txt==''){
    			$(this).closest('p').find('re').remove();
    			$(this).closest('p').append('<re>上期结算价格不能为空!</re>');
    		}else if( txt!=''&&!/^[0]\.{1}\d+|\d+$/.test( txt)){		//表示小数和整数；
    			$(this).closest('p').find('re').remove();
    			$(this).closest('p').append('<re>上期结算价格必须为数字!</re>');
    		}else{
    			$(this).closest('p').find('re').remove();
    			$('.chanjslsbid,.chanjslsask',parent.document).css('display','inline-block');
    			if( txt1==''){
    				$('.chanjslsbid input',parent.document).val( (txt-txt1/100).toFixed(2) );
    			}else if( txt<txt1/100 ){
    				$('.jslsbid input',parent.document).closest('p').find('re').remove();
    				$('.jslsbid input',parent.document).closest('p').append('<re>点差过大!</re>');
    			}else if(txt1!=''&&/^\d+$/.test(txt1) ){
    				$('.chanjslsbid input',parent.document).val( (txt-txt1/100).toFixed(2) );
    			}
    			if( txt2==''){
    				$('.chanjslsask input',parent.document).val( (txt*1+txt1/100).toFixed(2) );
    			}else if(  txt<txt2/100 ){
    				$('.jslaask input',parent.document).closest('p').find('re').remove();
    				$('.jslaask input',parent.document).closest('p').append('<re>点差过大!</re>');
    			}else if(txt1!=''&&/^\d+$/.test(txt1) ){
    				$('.chanjslsask input',parent.document).val( (txt*1+txt1/100).toFixed(2) );
    			}
    		}
    	});
    	//上期结算价格bid点差 失焦；
		$('.jslsbid input',parent.document).on('blur',function(){
			var txt=$('.ljpri input',parent.document).val(),
				txt1=$(this).val();
			if( txt1==''){
				$(this).closest('p').find('re').remove();
				$(this).closest('p').append('<re>上期结算价格bid点差不能为空!</re>');
			}else if( txt1!=''&&!/^\d+$/.test( txt1 )){
				$(this).closest('p').find('re').remove();
				$(this).closest('p').append('<re>上期结算价格bid点差只能是整字!</re>');
			}else{
				$(this).closest('p').find('re').remove();
				if(txt==''||!/^[0]\.{1}\d+|\d+$/.test( txt) ){
					
				}else if( txt<txt1/100 ){
					$('.jslsbid input',parent.document).closest('p').find('re').remove();
    				$('.jslsbid input',parent.document).closest('p').append('<re>点差过大!</re>');
				}else{
					$('.chanjslsbid input',parent.document).val( (txt-txt1/100).toFixed(2) );
				}
			}
		});
		//上期结算价格ask点差 失焦
		$('.jslaask input',parent.document).on('blur',function(){
			var txt=$('.ljpri input',parent.document).val(),
				txt1=$(this).val();
			if( txt1==''){
				$(this).closest('p').find('re').remove();
				$(this).closest('p').append('<re>上期结算价格ask点差不能为空!</re>');
			}else if( txt1!=''&&!/^\d+$/.test( txt1 )){
				$(this).closest('p').find('re').remove();
				$(this).closest('p').append('<re>上期结算价格ask点差只能是整字!</re>');
			}else{
				$(this).closest('p').find('re').remove();
				if(txt==''||!/^[0]\.{1}\d+|\d+$/.test( txt) ){
					
				}else if(  txt<txt1/100 ){
    				$('.jslaask input',parent.document).closest('p').find('re').remove();
    				$('.jslaask input',parent.document).closest('p').append('<re>点差过大!</re>');
    			}else{
					$('.chanjslsask input',parent.document).val( (txt*1+txt1/100).toFixed(2) );
				}
			}
		});
		//下期结算价格-失焦；
		$('.nextjspri input',parent.document).on('blur',function(){
    		var txt=$(this).val(),
    			txt1=$('.nextjslsbid input',parent.document).val(),
    			txt2=$('.nextjslsask input',parent.document).val();
    		if( txt==''){
    			$(this).closest('p').find('re').remove();
    			$(this).closest('p').append('<re>下期结算价格不能为空!</re>');
    		}else if( txt!=''&&!/^[0]\.{1}\d+|\d+$/.test( txt)){		//表示小数和整数；
    			$(this).closest('p').find('re').remove();
    			$(this).closest('p').append('<re>下期结算价格必须为数字!</re>');
    		}else{
    			$(this).closest('p').find('re').remove();
    			$('.chanjslsbidpri,.chanjslsaskpri',parent.document).css('display','inline-block');
    			if( txt1==''){
    				$('.chanjslsbidpri input',parent.document).val( (txt-txt1/100).toFixed(2) );
    			}else if( txt<txt1/100 ){
    				$('.nextjslsbid',parent.document).find('re').remove();
    				$('.nextjslsbid',parent.document).append('<re>点差太大!</re>');
    			}else if(txt1!=''&&/^\d+$/.test(txt1) ){
    				$('.chanjslsbidpri input',parent.document).val( (txt-txt1/100).toFixed(2) );
    			}
    			
    			if( txt2==''){
    				$('.chanjslsaskpri input',parent.document).val( (txt*1+txt1/100).toFixed(2) );
    			}else if( txt<txt2/100 ){
    				$('.nextjslsask',parent.document).find('re').remove();
    				$('.nextjslsask',parent.document).append('<re>点差太大!</re>');
    			}else if(txt1!=''&&/^\d+$/.test(txt1) ){
    				$('.chanjslsaskpri input',parent.document).val( (txt*1+txt1/100).toFixed(2) );
    			}
    		}
    	});
		//下期结算价格bid点差 失焦；
		$('.nextjslsbid input',parent.document).on('blur',function(){
			var txt=$('.nextjspri input',parent.document).val(),
				txt1=$(this).val();
			if( txt1==''){
				$(this).closest('p').find('re').remove();
				$(this).closest('p').append('<re>下期结算价格bid点差不能为空!</re>');
			}else if( txt1!=''&&!/^\d+$/.test( txt1 )){
				$(this).closest('p').find('re').remove();
				$(this).closest('p').append('<re>下期结算价格bid点差只能是整字!</re>');
			}else{
				$(this).closest('p').find('re').remove();
				if(txt==''||!/^[0]\.{1}\d+|\d+$/.test( txt) ){
					
				}else if( txt<txt1/100 ){
					$('.nextjslsbid',parent.document).find('re').remove();
    				$('.nextjslsbid',parent.document).append('<re>点差太大!</re>');
				}else{
					$('.chanjslsbidpri input',parent.document).val( (txt-txt1/100).toFixed(2) );
				}
			}
		});
		//下期结算价格ask点差 失焦
		$('.nextjslsask input',parent.document).on('blur',function(){
			var txt=$('.nextjspri input',parent.document).val(),
				txt1=$(this).val();
			if( txt1==''){
				$(this).closest('p').find('re').remove();
				$(this).closest('p').append('<re>下期结算价格ask点差不能为空!</re>');
			}else if( txt1!=''&&!/^\d+$/.test( txt1 )){
				$(this).closest('p').find('re').remove();
				$(this).closest('p').append('<re>下期结算价格ask点差只能是整字!</re>');
			}else{
				$(this).closest('p').find('re').remove();
				if(txt==''||!/^[0]\.{1}\d+|\d+$/.test( txt) ){
					
				}else if( txt<txt1/100 ){
					$('.nextjslsask',parent.document).find('re').remove();
    				$('.nextjslsask',parent.document).append('<re>点差太大!</re>');
				}else{
					$('.chanjslsaskpri input',parent.document).val( (txt*1+txt1/100).toFixed(2) );
				}
			}
		});
		//上期市场bid，上期市场ask，下期市场价格bid，下期ask;
		$('.lamabiddot input,.lamaaskdot input,.nebiddot input,.nemaaskdot input',parent.document).on('blur',function(){
			var txt=$(this).closest('p').find('span').text().replace(/\:/,'')
			if( $(this).val()==''){
				 $(this).closest('p').find('re').remove();
				 $(this).closest('p').append('<re>'+txt+'不能为空!</re>');
			}else if(!/^\d$/.test( $(this).val() )){
				 $(this).closest('p').find('re').remove();
				 $(this).closest('p').append('<re>'+txt+'必须是数字!</re>');
			}else{
				$(this).closest('p').find('re').remove();
			}
		});
    });
    //点击取消，关闭；
    $('body',parent.document).on('click','.sav_cancel,.close',function(){
    	$(this).closest('.zhezhao').remove();
    });
    //点击日期，显示日期插件；
    $('body',parent.document).on('click','.adjustday',function(){
    	WdatePicker({
    			dateFmt:'yyyyMMdd',
    			isShowClear:false,
    			isShowToday:false,
    			isShowOthers:false,
    			isShowOk:false,
    			qsEnabled:false,
    			position:{left:-302,top:-60},
    			onpicking:function(){
    				$('.adjustda',parent.document).closest('p').find('re').remove();
    			}
    	});
    	
    });
    //添加保存；
    $('body',parent.document).on('click','.add_sav',function(){
    	var obj={
    			exnm:$('.dealcuur option:selected',parent.document).text(),
    			femd:$('.adjustday',parent.document).val(),
    			ostp:$('.ljpri input',parent.document).val(),
    			osbs:$('.jslsbid input',parent.document).val(),
    			osas:$('.jslaask input',parent.document).val(),
    			nstp:$('.nextjspri input',parent.document).val(),
    			nrbs:$('.nextjslsbid input',parent.document).val(),
    			nsas:$('.nextjslsask input',parent.document).val(),
    			orbs:$('.lamabiddot input',parent.document).val(),
    			oras:$('.lamaaskdot input',parent.document).val(),
    			nsbs:$('.nebiddot input',parent.document).val(),
    			nras:$('.nemaaskdot input',parent.document).val(),
    			stat:'W'
    	}
    	if( $('.dealcuur option',parent.document).length==0){
    		$('.dealcuur',parent.document).find('re').remove();
    		$('.dealcuur',parent.document).closest('p').append('<re>交易币种获取失败!</re>');
    	}else{
    		$('.dealcuur',parent.document).find('re').remove();
    	}
    	if( $('.adjustday',parent.document).val()==''){
    		$('.adjustday',parent.document).find('re').remove();
    		$('.adjustday',parent.document).closest('p').append('<re>请选择调整日!</re>');
    	}else{
    		$('.adjustday',parent.document).find('re').remove();
    	}
    	if( $('.ljpri input',parent.document).val()=='' ){
    		$('.ljpri input',parent.document).closest('p').find('re').remove();
    		$('.ljpri input',parent.document).closest('p').append('<re>上期结算价格不能为空!</re>');
    	}else if( $('.ljpri input',parent.document).val()!=''&&!/^[0]\.{1}\d+|\d+$/.test( $('.ljpri input',parent.document).val())){		
    		$('.ljpri input',parent.document).closest('p').find('re').remove();
    		$('.ljpri input',parent.document).closest('p').append('<re>上期结算价格必须为数字!</re>');
		}else{
			$('.ljpri',parent.document).find('re').remove();
		}
    	
    	if( $('.jslsbid input',parent.document).val()=='' ){
    		$('.jslsbid input',parent.document).closest('p').find('re').remove();
    		$('.jslsbid input',parent.document).closest('p').append('<re>上期结算价格bid点差不能为空!</re>');
    	}else if( $('.jslsbid input',parent.document).val()!=''&&!/^\d+$/.test( $('.jslsbid input',parent.document).val())){		
    		$('.jslsbid input',parent.document).closest('p').find('re').remove();
    		$('.jslsbid input',parent.document).closest('p').append('<re>上期结算价格bid点差不能为空!</re>');
		}else if(  $('.ljpri input',parent.document).val()<$('.jslsbid input',parent.document).val()/100 ){
			$('.jslsbid input',parent.document).closest('p').find('re').remove();
    		$('.jslsbid input',parent.document).closest('p').append('<re>点差过大!</re>');
		}else{
			$('.jslsbid',parent.document).find('re').remove();
		}
    	if( $('.jslaask input',parent.document).val()=='' ){
    		$('.jslaask input',parent.document).closest('p').find('re').remove();
    		$('.jslaask input',parent.document).closest('p').append('<re>上期结算价格ask点差不能为空!</re>');
    	}else if( $('.jslaask input',parent.document).val()!=''&&!/^\d+$/.test( $('.jslaask input',parent.document).val())){
    		$('.jslaask input',parent.document).closest('p').find('re').remove();
    		$('.jslaask input',parent.document).closest('p').append('<re>上期结算价格ask点差不能为空!</re>');
		}else if( $('.ljpri input',parent.document).val()<$('.jslaask input',parent.document).val()/100){
			$('.jslaask input',parent.document).closest('p').find('re').remove();
    		$('.jslaask input',parent.document).closest('p').append('<re>点差过大!</re>');
		}else{
			$('.jslaask',parent.document).find('re').remove();
		}
    	
    	if( $('.nextjspri input',parent.document).val()=='' ){
    		$('.nextjspri input',parent.document).closest('p').find('re').remove();
    		$('.nextjspri input',parent.document).closest('p').append('<re>下期结算价格不能为空!</re>');
    	}else if( $('.nextjspri input',parent.document).val()!=''&&!/^[0]\.{1}\d+|\d+$/.test( $('.nextjspri input',parent.document).val())){		
    		$('.nextjspri input',parent.document).closest('p').find('re').remove();
    		$('.nextjspri input',parent.document).closest('p').append('<re>下期结算价格必须为数字!</re>');
		}else{
			$('.nextjspri',parent.document).find('re').remove();
		}
    	
    	
    	if( $('.nextjslsbid input',parent.document).val()=='' ){
    		$('.nextjslsbid input',parent.document).closest('p').find('re').remove();
    		$('.nextjslsbid input',parent.document).closest('p').append('<re>下期结算价格bid点差不能为空!</re>');
    	}else if( $('.nextjslsbid input',parent.document).val()!=''&&!/^\d+$/.test( $('.nextjslsbid input',parent.document).val())){
    		$('.nextjslsbid input',parent.document).closest('p').find('re').remove();
    		$('.nextjslsbid input',parent.document).closest('p').append('<re>下期结算价格bid点差不能为空!</re>');
		}else if( $('.nextjspri input',parent.document).val()<$('.nextjslsbid input',parent.document).val()/100){
			$('.nextjslsbid input',parent.document).closest('p').find('re').remove();
    		$('.nextjslsbid input',parent.document).closest('p').append('<re>点差过大!</re>');
		}else{
			$('.nextjslsbid input',parent.document).closest('p').find('re').remove();
		}
    	if( $('.nextjslsask input',parent.document).val()=='' ){
    		$('.nextjslsask input',parent.document).closest('p').find('re').remove();
    		$('.nextjslsask input',parent.document).closest('p').append('<re>下期结算价格ask点差不能为空!</re>');
    	}else if( $('.nextjslsask input',parent.document).val()!=''&&!/^\d+$/.test( $('.nextjslsask input',parent.document).val())){
    		$('.nextjslsask input',parent.document).closest('p').find('re').remove();
    		$('.nextjslsask input',parent.document).closest('p').append('<re>下期结算价格ask点差不能为空!</re>');
		}else if( $('.nextjspri input',parent.document).val()<$('.nextjslsask input',parent.document).val()/100){
			$('.nextjslsask input',parent.document).closest('p').find('re').remove();
    		$('.nextjslsask input',parent.document).closest('p').append('<re>点差过大!</re>');
		}else{
			$('.nextjslsbid',parent.document).remove();
		}
    	//
    	if( $('.lamabiddot input',parent.document).val()=='' ){
    		$('.lamabiddot input',parent.document).closest('p').find('re').remove();
    		$('.lamabiddot input',parent.document).closest('p').append('<re>上期市场价格bid点差不能为空!</re>');
    	}else if( $('.lamabiddot input',parent.document).val()!=''&&!/^\d+$/.test( $('.lamabiddot input',parent.document).val())){
    		$('.lamabiddot input',parent.document).closest('p').find('re').remove();
    		$('.lamabiddot input',parent.document).closest('p').append('<re>上期市场价格bid点差不能为空!</re>');
		}else{
			$('.lamabiddot',parent.document).find('re').remove();
		}
    	
    	if( $('.lamaaskdot input',parent.document).val()=='' ){
    		$('.lamaaskdot input',parent.document).closest('p').find('re').remove();
    		$('.lamaaskdot input',parent.document).closest('p').append('<re>上期市场价格ask点差不能为空!</re>');
    	}else if( $('.lamaaskdot input',parent.document).val()!=''&&!/^\d+$/.test( $('.lamaaskdot input',parent.document).val())){
    		$('.lamaaskdot input',parent.document).closest('p').find('re').remove();
    		$('.lamaaskdot input',parent.document).closest('p').append('<re>上期市场价格ask点差不能为空!</re>');
		}else{
			$('.lamaaskdot',parent.document).find('re').remove();
		}
    	if( $('.nebiddot input',parent.document).val()=='' ){
    		$('.nebiddot input',parent.document).closest('p').find('re').remove();
    		$('.nebiddot input',parent.document).closest('p').append('<re>下期市场价格bid点差不能为空!</re>');
    	}else if( $('.nebiddot input',parent.document).val()!=''&&!/^\d+$/.test( $('.nebiddot input',parent.document).val())){
    		$('.nebiddot input',parent.document).closest('p').find('re').remove();
    		$('.nebiddot input',parent.document).closest('p').append('<re>下期市场价格bid点差不能为空!</re>');
		}else{
			$('.nebiddot',parent.document).find('re').remove();
		}
    	if( $('.nemaaskdot input',parent.document).val()=='' ){
    		$('.nemaaskdot input',parent.document).closest('p').find('re').remove();
    		$('.nemaaskdot input',parent.document).closest('p').append('<re>下期市场价格ask点差不能为空!</re>');
    	}else if( $('.nemaaskdot input',parent.document).val()!=''&&!/^\d+$/.test( $('.nemaaskdot input',parent.document).val())){
    		$('.nemaaskdot input',parent.document).closest('p').find('re').remove();
    		$('.nemaaskdot input',parent.document).closest('p').append('<re>下期市场价格ask点差不能为空!</re>');
		}else{
			$('.nemaaskdot',parent.document).find('re').remove();
		}
    	
    	if( $('.nextjspri input',parent.document).val()<$('.nextjslsbid input',parent.document).val()/100){
			$('.nextjslsbid input',parent.document).closest('p').find('re').remove();
    		$('.nextjslsbid input',parent.document).closest('p').append('<re>点差过大!</re>');
		}
    	 if( $('.nextjspri input',parent.document).val()<$('.nextjslsask input',parent.document).val()/100){
 			$('.nextjslsask input',parent.document).closest('p').find('re').remove();
     		$('.nextjslsask input',parent.document).closest('p').append('<re>点差过大!</re>');
 		}
    	 
    	 if(  $('.ljpri input',parent.document).val()<$('.jslsbid input',parent.document).val()/100 ){
 			$('.jslsbid input',parent.document).closest('p').find('re').remove();
     		$('.jslsbid input',parent.document).closest('p').append('<re>点差过大!</re>');
 		}
    	if( $('.ljpri input',parent.document).val()<$('.jslaask input',parent.document).val()/100){
 			$('.jslaask input',parent.document).closest('p').find('re').remove();
     		$('.jslaask input',parent.document).closest('p').append('<re>点差过大!</re>');
 		}
    	if( $('.adjustmentday re',parent.document).length==0 ){
    		$.ajax({
        		url:'/fx/accex/addAdjust.do',
        		type:'post',
        		contentType:'application/json',
        		data:JSON.stringify(obj),
        		async:true,
        		success:function(data){
        			$('.add_sav',parent.document).closest('.zhezhao').remove();
        			if(data.code==01){
           				userdata=data.codeMessage ;
           				dialog.choicedata(data.codeMessage,'adjustmentday');
           				getList({'exnm':$('.cuur select option:selected').text(),'beginDate':$('#d1').val(),'endDate':$('#d2').val(),'pageNo':1,'pageSize':10});
           				renpage();
        			}else{
        				dialog.choicedata(data.codeMessage,'adjustmentday');
           			}
        		}
        	}); 
    	}
    });   
    $('body',parent.document).on('click','.adjustmentday .sure',function(){
		$(this).closest('.zhezhao').remove();
	});
    /*----------------快速搜索功能的实现------------------------*/
	$('.info_serbtn').click(function(){
		  dialog.serchData($('.info_seript').val(),'.mmg-headWrapper');
    });
	$('.boxtop').on('dblclick','tr',function(){
		var ind=$(this).index();
		dialog.adjustadd('adjustmentday','modify');
    	$( '.dealcuur',parent.document ).html( $('.cuur select').html() );
    	$('.dealcuur option',parent.document).each(function(i,v){
    		if( $(v).text()== chedata[0].EXNM){
    			 $(v).prop('selected','selected');
    		}
    	});
    	$('.hid',parent.document).css('display','inline-block');
    	$('.adjustday',parent.document).val( chedata[ind].FEMD);
    	$('.ljpri input',parent.document).val( chedata[ind].OSTP );
		$('.adjustday',parent.document).val(chedata[ind].FEMD);
		$('.jslsbid input',parent.document).val( chedata[ind].OSBS)
		$('.jslaask input',parent.document).val( chedata[ind].OSAS);
		$('.nextjspri input',parent.document).val( chedata[ind].NSTP);
		$('.nextjslsbid input',parent.document).val( chedata[ind].NSBS);
		$('.nextjslsask input',parent.document).val( chedata[ind].NSAS);
		$('.lamabiddot input',parent.document).val( chedata[ind].ORBS);
		$('.lamaaskdot input',parent.document).val( chedata[ind].ORAS);
		
		$('.nebiddot input',parent.document).val( chedata[ind].NRBS);
		$('.nemaaskdot input',parent.document).val( chedata[ind].NRAS);
		
		$('.chanjslsbid input',parent.document).val( (chedata[ind].OSTP-chedata[ind].OSBS/100).toFixed(2) );
		$('.chanjslsask input',parent.document).val( (chedata[ind].OSTP+chedata[ind].OSAS/100).toFixed(2) );
		$('.chanjslsbidpri input',parent.document).val( (chedata[ind].NSTP-chedata[ind].NSBS/100).toFixed(2) );
		$('.chanjslsaskpri input',parent.document).val( (chedata[ind].NSTP+chedata[ind].NSAS/100 ).toFixed(2) );
		
		//上期结算机构价格-失焦，自动触发计算；
    	$('.ljpri input',parent.document).on('blur',function(){
    		var txt=$(this).val(),
    			txt1=$('.jslsbid input',parent.document).val(),
    			txt2=$('.jslaask input',parent.document).val();
    		if( txt==''){
    			$(this).closest('p').find('re').remove();
    			$(this).closest('p').append('<re>上期结算价格不能为空!</re>');
    		}else if( txt!=''&&!/^[0]\.{1}\d+|\d+$/.test( txt)){		//表示小数和整数；
    			$(this).closest('p').find('re').remove();
    			$(this).closest('p').append('<re>上期结算价格必须为数字!</re>');
    		}else{
    			$(this).closest('p').find('re').remove();
    			$('.chanjslsbid,.chanjslsask',parent.document).css('display','inline-block');
    			if( txt1==''){
    				$('.chanjslsbid input',parent.document).val( (txt-txt1/100).toFixed(2) );
    			}else if( txt<txt1/100){
    				$('.jslsbid',parent.document).find('re').remove();
    				$('.jslsbid',parent.document).append('<re>点差过大!</re>');
    			}else if(txt1!=''&&/^\d+$/.test(txt1) ){
    				$('.chanjslsbid input',parent.document).val( (txt-txt1/100).toFixed(2) );
    			}
    			if( txt2==''){
    				$('.chanjslsask input',parent.document).val( (txt*1+txt1/100).toFixed(2) );
    			}else if(txt<txt2/100 ){
    				$('.jslaask',parent.document).find('re').remove();
    				$('.jslaask',parent.document).append('<re>点差过大!</re>');
    			}else if(txt1!=''&&/^\d+$/.test(txt1) ){
    				$('.chanjslsask input',parent.document).val( (txt*1+txt1/100).toFixed(2) );
    			}
    		}
    	});
    	//上期结算价格bid点差 失焦；
		$('.jslsbid input',parent.document).on('blur',function(){
			var txt=$('.ljpri input',parent.document).val(),
				txt1=$(this).val();
			if( txt1==''){
				$(this).closest('p').find('re').remove();
				$(this).closest('p').append('<re>上期结算价格bid点差不能为空!</re>');
			}else if( txt1!=''&&!/^\d+$/.test( txt1 )){
				$(this).closest('p').find('re').remove();
				$(this).closest('p').append('<re>上期结算价格bid点差只能是整字!</re>');
			}else{
				$(this).closest('p').find('re').remove();
				if(txt==''||!/^[0]\.{1}\d+|\d+$/.test( txt) ){
					
				}else if( txt<txt1/100 ){
					$('.jslsbid',parent.document).find('re').remove();
    				$('.jslsbid',parent.document).append('<re>点差过大!</re>');
				}else{
					$('.chanjslsbid input',parent.document).val( (txt-txt1/100).toFixed(2) );
				}
			}
		});
		//上期结算价格ask点差 失焦
		$('.jslaask input',parent.document).on('blur',function(){
			var txt=$('.ljpri input',parent.document).val(),
				txt1=$(this).val();
			if( txt1==''){
				$(this).closest('p').find('re').remove();
				$(this).closest('p').append('<re>上期结算价格ask点差不能为空!</re>');
			}else if( txt1!=''&&!/^\d+$/.test( txt1 )){
				$(this).closest('p').find('re').remove();
				$(this).closest('p').append('<re>上期结算价格ask点差只能是整字!</re>');
			}else{
				$(this).closest('p').find('re').remove();
				if(txt==''||!/^[0]\.{1}\d+|\d+$/.test( txt) ){
					
				}else if( txt<txt1/100 ){
					$('.jslaask',parent.document).find('re').remove();
    				$('.jslaask',parent.document).append('<re>点差过大!</re>');
				}else{
					$('.chanjslsask input',parent.document).val( (txt*1+txt1/100).toFixed(2) );
				}
			}
		});
		//下期结算价格-失焦；
		$('.nextjspri input',parent.document).on('blur',function(){
    		var txt=$(this).val(),
    			txt1=$('.nextjslsbid input',parent.document).val(),
    			txt2=$('.nextjslsask input',parent.document).val();
    		if( txt==''){
    			$(this).closest('p').find('re').remove();
    			$(this).closest('p').append('<re>下期结算价格不能为空!</re>');
    		}else if( txt!=''&&!/^[0]\.{1}\d+|\d+$/.test( txt)){		//表示小数和整数；
    			$(this).closest('p').find('re').remove();
    			$(this).closest('p').append('<re>下期结算价格必须为数字!</re>');
    		}else{
    			$(this).closest('p').find('re').remove();
    			$('.chanjslsbidpri,.chanjslsaskpri',parent.document).css('display','inline-block');
    			if( txt1==''){
    				$('.chanjslsbidpri input',parent.document).val( (txt-txt1/100).toFixed(2) );
    			}else if( txt<txt1/100 ){
    				$('.nextjslsbid',parent.document).find('re').remove();
    				$('.nextjslsbid',parent.document).append('<re>点差过大!</re>');
    			}else if(txt1!=''&&/^\d+$/.test(txt1) ){
    				$('.chanjslsbidpri input',parent.document).val( (txt-txt1/100).toFixed(2) );
    			}
    			
    			if( txt2==''){
    				$('.chanjslsaskpri input',parent.document).val( (txt*1+txt1/100).toFixed(2) );
    			}else if( txt<txt2/100 ){
    				$('.nextjslsask',parent.document).find('re').remove();
    				$('.nextjslsask',parent.document).append('<re>点差过大!</re>');
    			}else if(txt1!=''&&/^\d+$/.test(txt1) ){
    				$('.chanjslsaskpri input',parent.document).val( (txt*1+txt1/100).toFixed(2) );
    			}
    		}
    	});
		//下期结算价格bid点差 失焦；
		$('.nextjslsbid input',parent.document).on('blur',function(){
			var txt=$('.nextjspri input',parent.document).val(),
				txt1=$(this).val();
			if( txt1==''){
				$(this).closest('p').find('re').remove();
				$(this).closest('p').append('<re>下期结算价格bid点差不能为空!</re>');
			}else if( txt1!=''&&!/^\d+$/.test( txt1 )){
				$(this).closest('p').find('re').remove();
				$(this).closest('p').append('<re>下期结算价格bid点差只能是整字!</re>');
			}else{
				$(this).closest('p').find('re').remove();
				if(txt==''||!/^[0]\.{1}\d+|\d+$/.test( txt) ){
					
				}else if( txt<txt1/100 ){
    				$('.nextjslsbid',parent.document).find('re').remove();
    				$('.nextjslsbid',parent.document).append('<re>点差过大!</re>');
    			}else{
					$('.chanjslsbidpri input',parent.document).val( (txt-txt1/100).toFixed(2) );
				}
			}
		});
		//下期结算价格ask点差 失焦
		$('.nextjslsask input',parent.document).on('blur',function(){
			var txt=$('.nextjspri input',parent.document).val(),
				txt1=$(this).val();
			if( txt1==''){
				$(this).closest('p').find('re').remove();
				$(this).closest('p').append('<re>下期结算价格ask点差不能为空!</re>');
			}else if( txt1!=''&&!/^\d+$/.test( txt1 )){
				$(this).closest('p').find('re').remove();
				$(this).closest('p').append('<re>下期结算价格ask点差只能是整字!</re>');
			}else{
				$(this).closest('p').find('re').remove();
				if(txt==''||!/^[0]\.{1}\d+|\d+$/.test( txt) ){
					
				}else if( txt<txt1/100 ){
    				$('.nextjslsask',parent.document).find('re').remove();
    				$('.nextjslsask',parent.document).append('<re>点差过大!</re>');
    			}else{
					$('.chanjslsaskpri input',parent.document).val( (txt*1+txt1/100).toFixed(2) );
				}
			}
		});
		//上期市场bid，上期市场ask，下期市场价格bid，下期ask;
		$('.lamabiddot input,.lamaaskdot input,.nebiddot input,.nemaaskdot input',parent.document).on('blur',function(){
			var txt=$(this).closest('p').find('span').text().replace(/\:/,'')
			if( $(this).val()==''){
				 $(this).closest('p').find('re').remove();
				 $(this).closest('p').append('<re>'+txt+'不能为空!</re>');
			}else if( !/^\d$/.test( $(this).val() )){
				 $(this).closest('p').find('re').remove();
				 $(this).closest('p').append('<re>'+txt+'必须是数字!</re>');
			}else{
				$(this).closest('p').find('re').remove();
			}
		});
	});
	//点击修改 ；
	$('body',parent.document).on('click','.mod_sav',function(){
		var obj={
    			exnm:$('.dealcuur option:selected',parent.document).text(),
    			femd:$('.adjustday',parent.document).val(),
    			ostp:$('.ljpri input',parent.document).val(),
    			osbs:$('.jslsbid input',parent.document).val(),
    			osas:$('.jslaask input',parent.document).val(),
    			nstp:$('.nextjspri input',parent.document).val(),
    			nrbs:$('.nextjslsbid input',parent.document).val(),
    			nsas:$('.nextjslsask input',parent.document).val(),
    			orbs:$('.lamabiddot input',parent.document).val(),
    			oras:$('.lamaaskdot input',parent.document).val(),
    			nsbs:$('.nebiddot input',parent.document).val(),
    			nras:$('.nemaaskdot input',parent.document).val()
    	}
		if( $('.dealcuur option',parent.document).length==0){
    		$('.dealcuur',parent.document).find('re').remove();
    		$('.dealcuur',parent.document).closest('p').append('<re>交易币种获取失败!</re>');
    	}else{
    		$('.dealcuur',parent.document).find('re').remove();
    	}
    	if( $('.ljpri input',parent.document).val()=='' ){
    		$('.ljpri input',parent.document).closest('p').find('re').remove();
    		$('.ljpri input',parent.document).closest('p').append('<re>上期结算价格不能为空!</re>');
    	}else if( $('.ljpri input',parent.document).val()!=''&&!/^[0]\.{1}\d+|\d+$/.test( $('.ljpri input',parent.document).val())){		
    		$('.ljpri input',parent.document).closest('p').find('re').remove();
    		$('.ljpri input',parent.document).closest('p').append('<re>上期结算价格必须为数字!</re>');
		}else{
			$('.ljpri input',parent.document).closest('p').find('re').remove();
		}
    	
    	if( $('.jslsbid input',parent.document).val()=='' ){
    		$('.jslsbid input',parent.document).closest('p').find('re').remove();
    		$('.jslsbid input',parent.document).closest('p').append('<re>上期结算价格bid点差不能为空!</re>');
    	}else if( $('.jslsbid input',parent.document).val()!=''&&!/^\d+$/.test( $('.jslsbid input',parent.document).val())){		
    		$('.jslsbid input',parent.document).closest('p').find('re').remove();
    		$('.jslsbid input',parent.document).closest('p').append('<re>上期结算价格bid点差不能为空!</re>');
		}else{
			$('.jslsbid input',parent.document).closest('p').find('re').remove();
		}
    	if( $('.jslaask input',parent.document).val()=='' ){
    		$('.jslaask input',parent.document).closest('p').find('re').remove();
    		$('.jslaask input',parent.document).closest('p').append('<re>上期结算价格ask点差不能为空!</re>');
    	}else if( $('.jslaask input',parent.document).val()!=''&&!/^\d+$/.test( $('.jslaask input',parent.document).val())){
    		$('.jslaask input',parent.document).closest('p').find('re').remove();
    		$('.jslaask input',parent.document).closest('p').append('<re>上期结算价格ask点差不能为空!</re>');
		}else{
			$('.jslaask input',parent.document).closest('p').find('re').remove();
		}
    	
    	if( $('.nextjspri input',parent.document).val()=='' ){
    		$('.nextjspri input',parent.document).closest('p').find('re').remove();
    		$('.nextjspri input',parent.document).closest('p').append('<re>下期结算价格不能为空!</re>');
    	}else if( $('.nextjspri input',parent.document).val()!=''&&!/^[0]\.{1}\d+|\d+$/.test( $('.nextjspri input',parent.document).val())){		
    		$('.nextjspri input',parent.document).closest('p').find('re').remove();
    		$('.nextjspri input',parent.document).closest('p').append('<re>下期结算价格必须为数字!</re>');
		}else{
			$('.nextjspri input',parent.document).closest('p').find('re').remove();
		}
    	if( $('.nextjslsbid input',parent.document).val()=='' ){
    		$('.nextjslsbid input',parent.document).closest('p').find('re').remove();
    		$('.nextjslsbid input',parent.document).closest('p').append('<re>下期结算价格bid点差不能为空!</re>');
    	}else if( $('.nextjslsbid input',parent.document).val()!=''&&!/^\d+$/.test( $('.nextjslsbid input',parent.document).val())){
    		$('.nextjslsbid input',parent.document).closest('p').find('re').remove();
    		$('.nextjslsbid input',parent.document).closest('p').append('<re>下期结算价格bid点差不能为空!</re>');
		}else{
			$('.nextjslsbid input',parent.document).closest('p').find('re').remove();
		}
    	if( $('.nextjslsask input',parent.document).val()=='' ){
    		$('.nextjslsask input',parent.document).closest('p').find('re').remove();
    		$('.nextjslsask input',parent.document).closest('p').append('<re>下期结算价格ask点差不能为空!</re>');
    	}else if( $('.nextjslsask input',parent.document).val()!=''&&!/^\d+$/.test( $('.nextjslsask input',parent.document).val())){
    		$('.nextjslsask input',parent.document).closest('p').find('re').remove();
    		$('.nextjslsask input',parent.document).closest('p').append('<re>下期结算价格ask点差不能为空!</re>');
		}else{
			$('.nextjslsask input',parent.document).closest('p').find('re').remove();
		}
    	//
    	
    	if( $('.lamabiddot input',parent.document).val()=='' ){
    		$('.lamabiddot input',parent.document).closest('p').find('re').remove();
    		$('.lamabiddot input',parent.document).closest('p').append('<re>上期市场价格bid点差不能为空!</re>');
    	}else if( !/^\d+$/.test( $('.lamabiddot input',parent.document).val() ) ){
    		$('.lamabiddot input',parent.document).closest('p').find('re').remove();
    		$('.lamabiddot input',parent.document).closest('p').append('<re>上期市场价格bid点差必须为整数!</re>');
		}else{
			$('.lamabiddot',parent.document).find('re').remove();
		}
    	
    	if( $('.lamaaskdot input',parent.document).val()=='' ){
    		$('.lamaaskdot input',parent.document).closest('p').find('re').remove();
    		$('.lamaaskdot input',parent.document).closest('p').append('<re>上期市场价格ask点差不能为空!</re>');
    	}else if( $('.lamaaskdot input',parent.document).val()!=''&&!/^\d+$/.test( $('.lamaaskdot input',parent.document).val())){
    		$('.lamaaskdot input',parent.document).closest('p').find('re').remove();
    		$('.lamaaskdot input',parent.document).closest('p').append('<re>上期市场价格ask点差不能为空!</re>');
		}else{
			$('.lamaaskdot input',parent.document).closest('p').find('re').remove();
		}
    	if( $('.nebiddot input',parent.document).val()=='' ){
    		$('.nebiddot input',parent.document).closest('p').find('re').remove();
    		$('.nebiddot input',parent.document).closest('p').append('<re>下期市场价格bid点差不能为空!</re>');
    	}else if( $('.nebiddot input',parent.document).val()!=''&&!/^\d+$/.test( $('.nebiddot input',parent.document).val())){
    		$('.nebiddot input',parent.document).closest('p').find('re').remove();
    		$('.nebiddot input',parent.document).closest('p').append('<re>下期市场价格bid点差不能为空!</re>');
		}else{
			$('.nebiddot input',parent.document).closest('p').find('re').remove();
		}
    	if( $('.nemaaskdot input',parent.document).val()=='' ){
    		$('.nemaaskdot input',parent.document).closest('p').find('re').remove();
    		$('.nemaaskdot input',parent.document).closest('p').append('<re>下期市场价格ask点差不能为空!</re>');
    	}else if( $('.nemaaskdot input',parent.document).val()!=''&&!/^\d+$/.test( $('.nemaaskdot input',parent.document).val())){
    		$('.nemaaskdot input',parent.document).closest('p').find('re').remove();
    		$('.nemaaskdot input',parent.document).closest('p').append('<re>下期市场价格ask点差不能为空!</re>');
		}else{
			$('.nemaaskdot input',parent.document).closest('p').find('re').remove();
		}
    	if( $('.nextjspri input',parent.document).val()<$('.nextjslsbid input',parent.document).val()/100){
			$('.nextjslsbid input',parent.document).closest('p').find('re').remove();
    		$('.nextjslsbid input',parent.document).closest('p').append('<re>点差过大!</re>');
		}
    	 if( $('.nextjspri input',parent.document).val()<$('.nextjslsask input',parent.document).val()/100){
 			$('.nextjslsask input',parent.document).closest('p').find('re').remove();
     		$('.nextjslsask input',parent.document).closest('p').append('<re>点差过大!</re>');
 		}
    	 
    	 if(  $('.ljpri input',parent.document).val()<$('.jslsbid input',parent.document).val()/100 ){
 			$('.jslsbid input',parent.document).closest('p').find('re').remove();
     		$('.jslsbid input',parent.document).closest('p').append('<re>点差过大!</re>');
 		}
    	if( $('.ljpri input',parent.document).val()<$('.jslaask input',parent.document).val()/100){
 			$('.jslaask input',parent.document).closest('p').find('re').remove();
     		$('.jslaask input',parent.document).closest('p').append('<re>点差过大!</re>');
 		}
    	
    	if( $('.adjustmentday re',parent.document).length==0){
    		$.ajax({
        		url:'/fx/accex/updateAdjust.do',
        		type:'post',
        		contentType:'application/json',
        		data:JSON.stringify(obj),
        		async:true,
        		success:function(data){
        			$('.mod_sav',parent.document).closest('.zhezhao').remove();
        			if(data.code==01){
           				userdata=data.codeMessage ;
           				dialog.choicedata(data.codeMessage,'adjustmentday');
           				getList({'exnm':$('.cuur select option:selected').text(),'beginDate':$('#d1').val(),'endDate':$('#d2').val(),'pageNo':1,'pageSize':10});
           				renpage();
        			}else{
        				dialog.choicedata(data.codeMessage,'adjustmentday');
           			}
        		}
        	}); 
    	}
	});
	//点击删除；
	$('body',parent.document).on('click','.rem_sav',function(){
		var obj={
    			exnm:$('.dealcuur option:selected',parent.document).text(),
    			femd:$('.adjustday',parent.document).val()
    	}
    	$.ajax({
    		url:'/fx/accex/deleteAdjustByKey.do',
    		type:'post',
    		contentType:'application/json',
    		data:JSON.stringify(obj),
    		async:true,
    		success:function(data){
    			$('.rem_sav',parent.document).closest('.zhezhao').remove();
    			if(data.code==01){
       				userdata=data.codeMessage ;
       				dialog.choicedata(data.codeMessage,'adjustmentday');
       				getList({'exnm':$('.cuur select option:selected').text(),'beginDate':$('#d1').val(),'endDate':$('#d2').val(),'pageNo':1,'pageSize':10});
       				renpage();
    			}else{
    				dialog.choicedata(data.codeMessage,'adjustmentday');
       			}
    		}
    	}); 
	});
	//点击分页;
    /*$('.boxtop').on('click','.first',function(){
    	var Nopage=$('.Nopage').text()*1;
 	    if(Nopage>1){
 	    	Nopage=1;
 	    	getList({'exnm':$('.cuur select option:selected').text(),'beginDate':$('#d1').val(),'endDate':$('#d2').val(),'pageNo':Nopage,'pageSize':10}); 
 	    }
    });
	$('.boxtop').on('click','.prev',function(){
	    var Nopage=$('.Nopage').text()*1;
	    if(Nopage>1){
	    	Nopage=Nopage-1;
	    	getList({'exnm':$('.cuur select option:selected').text(),'beginDate':$('#d1').val(),'endDate':$('#d2').val(),'pageNo':Nopage,'pageSize':10}); 
	    }
	});
	$('.boxtop').on('click','.next',function(){
		var Nopage=$('.Nopage').text()*1,
			Totalpage=$('.Totalpage').text()*1;
	    if(Nopage<Totalpage){
	    	Nopage=Nopage*1+1;
	    	getList({'exnm':$('.cuur select option:selected').text(),'beginDate':$('#d1').val(),'endDate':$('#d2').val(),'pageNo':Nopage,'pageSize':10}); 
	    }
	});
	$('.boxtop').on('click','.last',function(){
		var Nopage=$('.Nopage').text()*1,
		Totalpage=$('.Totalpage').text()*1;
	    if(Nopage<Totalpage){
	    	Nopage=Totalpage;
	    	getList({'exnm':$('.cuur select option:selected').text(),'beginDate':$('#d1').val(),'endDate':$('#d2').val(),'pageNo':Nopage,'pageSize':10}); 
	    }
	});   */
	
})
