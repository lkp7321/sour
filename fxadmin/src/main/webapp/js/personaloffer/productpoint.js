/*产品点差查询*/
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
		ww=$(window).width()-260+"px";;
		$('.boxtop').css('width',ww);
		$('.boxtop').css('height',wh);
	//列参数;
    var cols = [
            { title:'价格类型', name:'tpfg',width:60,align:'center' },
            { title:'期限', name:'term',width:60, align:'right'},
            { title:'货币对名称', name:'exnm',width:100, align:'left'},
            { title:'报价模式', name:'prtp',width:60, align:'center'},   		//报价模式  文字；
            { title:'总分行买入点差', name:'bhbd',width:100,align:'right'},
            { title:'总分行卖出点差', name:'bhsd',width:100, align:'right'},
            { title:'总行对客户买入点差', name:'cubd',width:100, align:'right'},
            { title:'总行对客户卖出点差', name:'cusd',width:100, align:'right'},
            { title:'价格生命周期(秒)', name:'qtcy',width:100, align:'right'}
    ];
    //获取产品和用户名；
    var usnm=sessionStorage.getItem('usnm'),
		product=sessionStorage.getItem('product'),
		user={'usnm':usnm,'prod':product},userdata,
		userKey=JSON.parse( sessionStorage.getItem('menu') ).userKey,url;
   
    if(product=="P999"){
    	 url="getPointList.do" ; 
    	//请求产品列表
		  $.ajax({
			url:'/fx/pdtCom.do',
			type:'get',
			async:false,
			dataType:'json',
 			contentType:'application/json;charset=utf-8',
 			success:function(data){
				var maxdata=JSON.parse(data.codeMessage),str='';
				if(data.code==00){
					for(var i in maxdata){
						str+='<option value='+maxdata[i].PTID+'>'+maxdata[i].PTNM+'</option>'
					}
					$('.cen li').html( '<select class="product">'+str+'</select>');
				}else if(data.code==01){
					 
				}
			}
		});
		
		var prod=$('.product option:selected').val();
		//请求列表数据；
    	getList({"userKey":userKey,"prod":prod},url)
    	$('.product').change(function(){
    		prod=$('.product option:selected').val();
    		//请求列表数据；
        	getList({"userKey":userKey,"prod":prod},url)
    	})
    	
		  
    }else{
    	url="getPointList.do";
    	//请求列表数据；
    	getList({"userKey":userKey},url)
    }
    
    function getList(obj,url){
    	$.ajax({
    		url:url,
    		type:'post',
    		contentType:'application/json',
    		data:JSON.stringify(obj),
    		async:false,
    		success:function(data){
    			if(data.code==00){
    				userdata=JSON.parse( data.codeMessage );
    				dialog.ren({'cols':cols,'wh':wh,'userdata':userdata});
    			}else if(data.code==01||data.code==02){
    				dialog.ren({'cols':cols,'wh':wh,'userdata':''});
    			}
    		}
    	});
    }
    
	
	
});
