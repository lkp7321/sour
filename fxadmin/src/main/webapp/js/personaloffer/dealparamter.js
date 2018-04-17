/*
 * 实盘-交易参数 */
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
	var usnm=sessionStorage.getItem('usnm'),
		product=sessionStorage.getItem('product'),
		userkey=JSON.parse( sessionStorage.getItem('menu') ).userKey,
		user={'usnm':usnm,'prod':product},
		userdata,str=''; 
	var numreg=/^\d+$|^[1-9]+\.\d{2}$|^[0]{1}\.\d{2}$/;
	//列参数;
    var cols = [
        { title:'参数编号', name:'paid' ,width:80, align:'left' },
        { title:'参数说明', name:'remk' ,width:80, align:'left'},
        { title:'参数值', name:'valu' ,width:150, align:'right'},
        { title:'参数状态', name:'stat' ,width:80, align:'center'}
    ];
    var juesedata=false,jigoudata=false,jueseda,jigouda,listnumtotal;
    getlist({'userKey':userkey,'pageNo':1,'pageSize':10});
    renpage();
    //请求列表数据；
    function getlist(obj){
    	$.ajax({
    		url:'/fx/getAllPtpara.do',
    		type:'post',
    		contentType:'application/json',
    		async:false,
    		data:JSON.stringify(obj),
    		success:function(data){
    			if(data.code==01){
    				userdata= data.codeMessage;
    				dialog.ren({'cols':cols,'wh':wh,'userdata':userdata.list});
    				listnumtotal=userdata.total;
    			}else if(data.code==00){
    				dialog.ren({'cols':cols,'wh':wh,'userdata':''});
    			}	
    		}
    	});
    }
    
	/*修改*/
	$('.change').click(function(){
		if( $('.box tr').hasClass('selected') ){
			dialog.dealparamteradd('dealparamters');
			$('.dealparamters .paranum',parent.document).val( $('.box tr.selected td:eq(0) span').text() );
			$('.dealparamters .pararmrk',parent.document).val( $('.box tr.selected td:eq(1) span').text() );
			$('.dealparamters .paravalu',parent.document).val( $('.box tr.selected td:eq(2) span').text() );
			if( $('.box tr.selected td:eq(3) span').text()=="启用" ){
				$('.dealparamters .parastatu .start',parent.document).prop('checked','checked');
			}else{
				$('.dealparamters .parastatu .stop',parent.document).prop('checked','checked');
			}
			$('.dealparamters .paravalu',parent.document).on('blur',function(){
				var paraval=$(this).val();
				if(paraval=='' ){
					$('.dealparamters .formtip',parent.document).remove();
					$('.dealparamters .paravalu',parent.document).parent('p').append('<i class="formtip">参数值不可以为空!</i>');
				}else if( paraval!=''&&!numreg.test( paraval ) ){
					$('.dealparamters .formtip',parent.document).remove();
					$('.dealparamters .paravalu',parent.document).parent('p').append('<i class="formtip">参数值必须为整数或两位小数!</i>');
				}else{
					$('.dealparamters .formtip',parent.document).remove();
				}
			});
		}else{
			dialog.choicedata('请先选择一条数据!','dealparamter','aaa');
		}
	});
	$('body',parent.document).on('click','.dealparamters .save',function(){
		var paraval=$('.dealparamters .paravalu',parent.document).val();
		if(paraval=='' ){
			$('.dealparamters .formtip',parent.document).remove();
			$('.dealparamters .paravalu',parent.document).parent('p').append('<i class="formtip">参数值不可以为空!</i>');
		}else if( paraval!=''&&!numreg.test( paraval ) ){
			$('.dealparamters .formtip',parent.document).remove();
			$('.dealparamters .paravalu',parent.document).parent('p').append('<i class="formtip">参数值必须为整数或两位小数!</i>');
		}else{
			$('.dealparamters .formtip',parent.document).remove();
		}
		
		if( $('.dealparamters .formtip',parent.document).length==0 ){
			var paid=$('.box tr.selected td:eq(0) span').text(),
				remk=$('.box tr.selected td:eq(1) span').text(),
				valu=$('.dealparamters .paravalu',parent.document).val(),
				stat=$('.dealparamters .parastatu input[type="radio"]:checked',parent.document).attr('tit');
			$.ajax({
				url:'/fx/upPtpara.do',
				type:'post',
				contentType:'application/json',
				async:true,
				data:JSON.stringify({
					'userKey':userkey,
					'ptpara':{
						paid:paid,
						remk:remk,
						valu:valu,
						stat:stat
					}}),
				success:function(data){
					$(this).closest('.zhezhao').remove();
					if(data.code==01){
						dialog.choicedata(data.codeMessage,'dealparamter','success');
						getlist();
					}else if(data.code==02){
						dialog.choicedata(data.codeMessage,'dealparamter','aaa');
					}	
				}
			});
		}
	});
	$('body',parent.document).on('click','.dealparamters .cancel,.dealparamters .close',function(){
		$(this).closest('.zhezhao').remove();
	});
	$('body',parent.document).on('click','.dealparamter .sure',function(){
		var tit=$('.dealparamter',parent.document).data('tit');
		if( tit=='aaa'){
			$(this).closest('.zhezhao').remove();
		}else{
			$('.zhezhao',parent.document).remove();
		}
	});
    /*----------------快速搜索功能的实现-----------*/
	$('.info_serbtn').click(function(){
		  dialog.serchData($('.info_seript').val());
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
    		    	    	userKey:userkey
    		    	    });
    		    	}	
    		    }
    		  });
    	});
    }
	
})
