/*价格源管理- 货币对配置*/
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
	var userkey=JSON.parse( sessionStorage.getItem('menu') ).userKey,str='';
	$('.boxtop').css('width',ww);
	$('.boxtop').css('height',wh);
	
	//列参数;
    var cols = [
            { title:'市场编号', name:'mkid',width:80, align:'left'},   
            { title:'价格类型', name:'tpnm',width:60,align:'center' },
            { title:'期限', name:'term',width:60, align:'right'},
            { title:'货币对名称', name:'exnm',width:80, align:'left'},
            { title:'钞汇标志', name:'cxfg',width:60, align:'center'}
    ];
    //请求下拉列表；
    $.ajax({
		url:'/fx/price/getPdtinfoMap.do',
		type:'post',
		contentType:'application/json',
		async:false,
		success:function(data){
			if(data.code==01){
				userdata=JSON.parse( data.codeMessage );
				for(var i in userdata){
					str+='<option mkid='+userdata[i].MKID+'>'+userdata[i].MKNM+'</option>'
				}
				$('.dat select').html( str );
			}else if(data.code==02){
				
			}
		}
	});
    //请求页面数据列表；
    getlist(  $('.dat select option:selected').attr('mkid') ); 
    function getlist(obj){
    	$.ajax({
    		url:'/fx/price/getCMMPRICE.do',
    		type:'post',
    		contentType:'application/json',
    		data:obj,
    		async:true,
    		success:function(data){
    			if(data.code==01){
    				userdata=JSON.parse( data.codeMessage );
    				dialog.ren({'cols':cols,'wh':wh,'userdata':userdata});
    			}else if(data.code==00){
    				dialog.ren({'cols':cols,'wh':wh,'userdata':''});
    			}
    		}
    	});
    }
    $('.dat').change(function(){
    	var va=$(this).find('option:selected').attr('mkid');
    	getlist( va );
    });
})