/*产品管理 -货币对配置*/
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
            { title:'价格类型', name:'tpnm',width:60,align:'left' },
            { title:'期限', name:'term',width:60, align:'right'},
            { title:'货币对名称', name:'exnm',width:80, align:'left'},
            { title:'货币对编号', name:'excd',width:80, align:'left'},
            { title:'货币对序号', name:'exse',width:80, align:'right'},
            { title:'钞汇标志', name:'cxfg',width:60, align:'center'}
    ];
    //请求下拉列表；
    $.ajax({
		url:'/fx/price/prodExnmPrice.do',
		type:'get',
		async:false,
		dataType:'json',
			contentType:'application/json;charset=utf-8',
			success:function(data){
			var maxdata=JSON.parse(data.codeMessage),str='';
			if(data.code==01){
				for(var i in maxdata){
					str+='<option mkid='+maxdata[i].PTID+'>'+maxdata[i].PTNM+'</option>'
				}
				$('.dat select').html( str );
			}else{
				 
			}
		}
	});
    
    //请求页面数据列表；
    getlist(  $('.dat select option:selected').attr('mkid') ); 
    function getlist(obj){
    		dialog.ren({'cols':cols,'wh':wh,'userdata':''});
    		$.ajax({
        		url:'/fx/price/prodExnmAllPrice.do',
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