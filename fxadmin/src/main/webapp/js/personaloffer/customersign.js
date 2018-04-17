/*实盘-客户签约信息*/
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
    ww=$(window).width()-260+"px",
    num=0,
    userkey=JSON.parse( sessionStorage.getItem('menu') ).userKey,
	tit=$('title').text(),
    product=sessionStorage.getItem('product');
$('.boxtop').css('width',ww);
$('.boxtop').css('height',wh);
$('#page').css('width',ww);
//列参数
var cols = [
	{ title:'卡号', name:'cuac' ,width:80, align:'left'},
	{ title:'客户号', name:'cuno' ,width:80, align:'left'},
	{ title:'交易账号', name:'trac' ,width:100, align:'left'},
	{ title:'账户类型', name:'caty' ,width:100, align:'center'},
	{ title:'签约标志', name:'refg' ,width:100, align:'center'},
	{ title:'签约编号', name:'rgid' ,width:80, align:'left'},
	{ title:'客户标识', name:'cuty' ,width:80, align:'center'},
	{ title:'开户机构', name:'ognm' ,width:80, align:'left'},
	{ title:'签约机构', name:'rgnm' ,width:80, align:'left'},
	{ title:'签约柜员', name:'rgtl',width:80, align:'left'},
	{ title:'签约日期', name:'rgdt' ,width:80, align:'right'},
	{ title:'签约时间', name:'rgtm' ,width:80, align:'right'},
	{ title:'解约日期', name:'crdt' ,width:80, align:'right'},
	{ title:'解约时间', name:'crtm' ,width:80, align:'right'}
];
var juesedata=false,jigoudata=false,jueseda,jigouda,listnumtotal;
//请求机构1
$.ajax({
	url:'/fx/comboxA.do',
	type:'get',
	async:false,
	dataType:'json',
	success:function(data){
		 if(data.code==01){
			var listdata=data.codeMessage,str;
			for(var i in listdata){
				 str+='<option value='+listdata[i].ogcd+'>'+listdata[i].ognm+'</option>'
			}
			$('.longone').html(str);
			var comaogcd=$('.longone option:selected').val();
				getComboxB( comaogcd );
		  }else if(data.code==02){
			 //获取机构失败
		}
	}
});
//请求机构2
$('.longone').change(function(){
	comaogcd=$('.longone option:selected').val();
	getComboxB( comaogcd);
}); 
function getComboxB( obj ){
	$.ajax({
		url:'/fx/comboxB.do',
		type:'post',
		async:false,
		dataType:'json',
		data:obj,
			contentType:'application/json;charset=utf-8',
			beforeSend:function(){
				 str='<option value="all">所有</option>';
			},
			success:function(data){
			 if(data.code==01){
				var listdata=data.codeMessage;
				for(var i in listdata){
					str+='<option value='+listdata[i].ogcd+'>'+listdata[i].ognm+'</option>'
				}
				$('.longtwo').html(str);
			   }else if(data.code==02){
				 //获取失败
			}
		}
	});
} 
var strcuno=$('.customersign .strcuno').val(),
    strcuac=$('.customersign .strcuac').val(),
    comaogcd=$('.customersign .longone option:selected').val(),
    combogcd=$('.customersign .longtwo option:selected').val();
//if(product=='P001'){ 
	bsVo={'userKey':userkey,'strcuno':strcuno,'strcuac':strcuac,'comaogcd':comaogcd,'combogcd':combogcd,'pageSize':10,'pageNo':1};
	 getRegmsgInfo('getRegmsgInfo.do',bsVo);
/*}else if( product=='P003'){
	 bsVo={'strcuno':strcuno,'strcuac':strcuac,'comaogcd':comaogcd,'combogcd':combogcd,'pageNo':1,'pageSize':10};
	 getRegmsgInfo('queryRegmsgInfoList.do',bsVo);
}*/
	 renpage('getRegmsgInfo.do');
$('.serch').click(function(){
	//if(product=='P001'){ 
		url='getRegmsgInfo.do';
		strcuno=$('.customersign .strcuno').val(),
	    strcuac=$('.customersign .strcuac').val(),
	    comaogcd=$('.customersign .longone option:selected').val(),
	    combogcd=$('.customersign .longtwo option:selected').val(),
	    bsVo={'userKey':userkey,'strcuno':strcuno,'strcuac':strcuac,'comaogcd':comaogcd,'combogcd':combogcd,'pageSize':10,'pageNo':1};
	//}/*else if(product=="P003"){
		/*url='queryRegmsgInfoList.do';
		strcuno=$('.customersign .strcuno').val(),
	    strcuac=$('.customersign .strcuac').val(),
	    comaogcd=$('.customersign .longone option:selected').val(),
	    combogcd=$('.customersign .longtwo option:selected').val(),
	    bsVo={'prod':product,'strcuno':strcuno,'strcuac':strcuac,'comaogcd':comaogcd,'combogcd':combogcd,'pageNo':1,'pageSize':10};*/
	//}*/
	getRegmsgInfo(url,bsVo);
	renpage('getRegmsgInfo.do');
});

//封装请求渲染列表
function getRegmsgInfo(obj,obj1){  	
	 $.ajax({
		url:obj,
		type:'post',
		contentType:'application/json',
		data:JSON.stringify(obj1),
		async:false,
		success:function(data){
			num++;
			if(data.code==01){
				userdata=data.codeMessage;
				//if(product=='P001'){ 
					ren({'cols':cols,'wh':wh,'userdata':userdata.list});
				/*}else if( product=='P003'){
					ren({'cols':cols1,'wh':wh,'userdata':userdata});
				}*/
				/*	
				$('.page').remove();
				$('.boxtop').append('<div class="page"><span class="first">首页</span><span class="prev">上一页</span><span class="next">下一页</span><span class="last">末页</span><span class="nopage">第<b class="Nopage">'+userdata.pageNum+'</b>页/共<b class="Totalpage">'+userdata.pages+'</b>页</span><span class="totalpage">共<b class="total">'+userdata.total+'</b>条记录</span></div>');
				*/
				listnumtotal=userdata.total;
			}else{
				//if(product=='P001'){ 
					ren({'cols':cols,'wh':wh,'userdata':''});
				/*}else if( product=='P003'){
					ren({'cols':cols1,'wh':wh,'userdata':''});
				}*/
				if(num>1){			//这个判断是用来干什么的？？
					dialog.choicedata(data.codeMessage,'entryandexit');
				}
			}
		}
	});
    
};
//渲染列表
function ren(obj){
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
}
//点击分页;
$('.boxtop').on('click','.first',function(){
	var Nopage=$('.Nopage').text()*1;
	    if(Nopage>1){
	    	Nopage=1;
	    	getRegmsgInfo('getRegmsgInfo.do',{
	    	  'userKey':userkey,
	    	  'strcuno':$('.customersign .strcuno').val(),
	    	  'strcuac':$('.customersign .strcuac').val(),
	    	   comaogcd:$('.customersign .longone option:selected').val(),
	    	   combogcd:$('.customersign .longtwo option:selected').val(),
	    	  'pageNo':Nopage,
	    	  'pageSize':10
			});
	    }
});
$('.boxtop').on('click','.prev',function(){
    var Nopage=$('.Nopage').text()*1;
    if(Nopage>1){
    	Nopage=Nopage-1;
    	getRegmsgInfo('getRegmsgInfo.do',{
	    	  'userKey':userkey,
	    	  'strcuno':$('.customersign .strcuno').val(),
	    	  'strcuac':$('.customersign .strcuac').val(),
	    	   comaogcd:$('.customersign .longone option:selected').val(),
	    	   combogcd:$('.customersign .longtwo option:selected').val(),
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
    	getRegmsgInfo('getRegmsgInfo.do',{
	    	  'userKey':userkey,
	    	  'strcuno':$('.customersign .strcuno').val(),
	    	  'strcuac':$('.customersign .strcuac').val(),
	    	   comaogcd:$('.customersign .longone option:selected').val(),
	    	   combogcd:$('.customersign .longtwo option:selected').val(),
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
    	getRegmsgInfo('getRegmsgInfo.do',{
	    	  'userKey':userkey,
	    	  'strcuno':$('.customersign .strcuno').val(),
	    	  'strcuac':$('.customersign .strcuac').val(),
	    	   comaogcd:$('.customersign .longone option:selected').val(),
	    	   combogcd:$('.customersign .longtwo option:selected').val(),
	    	  'pageNo':Nopage,
	    	  'pageSize':10
			});
    }
});
$('body',parent.document).on('click','.entryandexit .sure',function(){
	$(this).closest('.zhezhao').remove();
});

	function renpage(ur){
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
			    		getRegmsgInfo(
			    			ur,
			    			{
			    				'userKey':userkey,
		    			    	'strcuno':$('.customersign .strcuno').val(),
		    			    	'strcuac':$('.customersign .strcuac').val(),
		    			    	'comaogcd':$('.customersign .longone option:selected').val(),
		    			    	'combogcd':$('.customersign .longtwo option:selected').val(),
		    				    'pageSize':$('.layui-laypage-limits select option:selected').attr('value')*1,
		    				    'pageNo':obj.curr
		    				 }
			    		);
			    	}	
			    }
			  });
		});
	}

})

