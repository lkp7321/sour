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
	var wh=$(window).height()-190+"px",
	ww=$(window).width()-260+"px";;

	$('.boxtop').css('width',ww);
	$('.boxtop').css('height',wh);
	
	var usnm=sessionStorage.getItem('usnm'),
		product=sessionStorage.getItem('product'),
		userkey=JSON.parse( sessionStorage.getItem('menu') ).userKey,
		tit=$('title').text();
	
	    dialog.getnowTime('#d12','#d13');
	//列参数
    var cols = [
        { title:'敞口流水号', name:'lkno' ,width:100, align:'left' },
        { title:'敞口请求流水号', name:'lcno' ,width:100, align:'left'},
        { title:'请求顺序号', name:'seqn' ,width:100, align:'left'},
        { title:'交易日期', name:'trdt' ,width:150, align:'right'},
        { title:'交易时间', name:'trtm' ,width:80, align:'right'},
        { title:'交易类型', name:'trty' ,width:120, align:'center'},
        { title:'敞口编号', name:'ckno' ,width:100, align:'left'},
        { title:'货币对', name:'exnm' ,width:80, align:'left'},
        { title:'左头寸', name:'total_lamt' ,width:80, align:'right'},
        { title:'右头寸', name:'total_ramt' ,width:80, align:'right'},
        { title:'成交汇率', name:'cbpc',width:80, align:'right'},
        { title:'交易来源', name:'cksn' ,width:80, align:'left'},
        { title:'交易员', name:'opno' ,width:80, align:'left'},
        { title:'状态', name:'stcd',width:80, align:'center'},
    ];
    //请求货币对名称
    $.ajax({
		   url:'/fx/selckUsd.do',
			type:'post',
			contentType:'application/json',
			data:userkey,
			async:false,
			success:function(data){
				var str='<option>所有</option>',listdata;
				if( data.code==00){
					 //当前没有数据；
					
				}else if(data.code==01){
					listdata=data.codeMessage;
					for(var i=0,num=listdata.length;i<num;i++){
						str+='<option>'+listdata[i]+'</option>'
					}
					$('.moneypair').html(str);
				}
			}
	 });
    openwaterRen(  {"userKey":userkey,'sartDate':$('.showThist').text(),'endDate':$('.showEndt').text(),'strExnm':'','lkno':''},0);
    function openwaterRen(obj,obj1){
    	//请求列表数据'
    	 $.ajax({
    			url:'/fx/selckAll.do',
    			type:'post',
    			contentType:'application/json',
    			data:JSON.stringify(obj),
    			async:true,
    			success:function(data){
    				if(data.code=='01'){
    					dialog.ren({'wh':wh,'cols':cols,'userdata':JSON.parse( data.codeMessage ) });
    				}else if(data.code=='00'){
    					dialog.ren({'cols':cols,'wh':wh,'userdata':''});
    				}
    			}
    		});
    }
    $('body',parent.document).on('click','.openwater .twosure',function(){
    	$(this).closest('.zhezhao').remove();
    });
   //点击查询按钮；
    $('.bot_bot button').click(function(){
    	var d12=$('#d12').val(),d13=$('#d13').val(),
    		moneypair=$('.moneypair option:selected').text(),
    		opennum=$('.openwanum').val();
    	if( moneypair=='所有'){
    		moneypair='';
    	}
    	openwaterRen(  {"userKey":userkey,'sartDate':d12,'endDate':d13,'strExnm':moneypair,'lkno':opennum},1);
    });
    
  //导出excel;
    $('#logon').click(function(){
    	var d12=$('#d12').val(),
    		d13=$('#d13').val(),
    		moneypair=$('.moneypair option:selected').text(),
    		opennum=$('.openwanum').val();
    	if( moneypair=='所有'){
    		moneypair='';
    	}
    	
    	 $('#fornm input').remove();	
		  var str='<input type="text" name = "userKey" value='+userkey+'>'+
		  		  '<input type="text" name = "tableName" value='+tit+'>'+
		  		  '<input type="text" name = "sartDate" value='+d12+'>'+
		  		  '<input type="text" name = "endDate" value='+d13+'>'+
		  		  '<input type="text" name = "strExnm" value='+moneypair+'>'+
		  		  '<input type="text" name = "lkno" value='+opennum+'>';
		  for(var i in cols){
			  str+='<input type="text" name ="tableList['+i+'].name" value='+cols[i].title+'><input type="text" name ="tableList['+i+'].value" value='+cols[i].name+'>'
		  }
		  $('#fornm').append( str );
		  $('#fornm').submit();
   });

})

