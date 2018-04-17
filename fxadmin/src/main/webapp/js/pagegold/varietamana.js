require.config({
	baseUrl:'/fx/js',
	shim:{
		'nicescroll':{
			deps:['jquery'],
			exports:'nicescroll'
		},
		'mmGrid':{
			deps:['jquery'],
			exports:'mmGrid'
		},
		'dialog':{
			deps:['jquery'],
			exports:'dialog'
		}
	},
	paths:{
		'jquery':'js_files/jquery-1.9.1.min',
		'mmGrid':'js_files/mmGrid',
		'nicescroll':'js_files/jquery.nicescroll.min',
		'dialog':'dialog'
	}
});
require(['jquery','mmGrid','nicescroll','dialog'],function($,mmGrid,nicescroll,dialog){
	
	var wh=$(window).height()-190+"px",
    ww=$(window).width()-345+"px";
	$('.boxtop').css('width',ww);
	$('.boxtop').css('height',wh);
	//列参数
    var cols = [
        { title:'货币对中文名称', name:'excn' ,width:150, align:'left' },
        { title:'货币对编号', name:'excd' ,width:150, align:'left'},
        { title:'货币对名称', name:'exnm' ,width:150, align:'left'},
        { title:'货币对类型', name:'extp' ,width:150, align:'left'},
        { title:'价格位点数', name:'pion' ,width:100, align:'left'}
    ];
    var usnm=sessionStorage.getItem('usnm'),
    product=sessionStorage.getItem('product'),
    user={'usnm':usnm,'prod':product};
   	//请求列表
    thrfn({url:'/fx/getAllCurrmsg.do',data:user});
 
	$('.add').click(function(){
    	dialog.cuurpairfn('varietymana','add');
    });
	$('.modify').click(function(){
    	if( $('.box tbody tr').hasClass('selected') ){
    		dialog.cuurpairfn('varietymana','modify');
    	}else{
    		dialog.choicedata('请选择一条数据!','varietymana');
    	}
    });
    
    $('body',parent.document).on('click','.varietymana .addsav',function(){
   		var breedcode=$('.breedcode',parent.document).val(),
   			breedEname=$('.breedEname',parent.document).val(),
   			breedCname=$('.breedCname',parent.document).val(),
   			remarks=$('.remarks',parent.document).val(),
   			rad=$('input[type="radio"]:checked',parent.document).data('tit'),
   		    text=$('.pubhead',parent.document).text().substr(5),url;
   		
   		if(text=="添加"){
   		    url="addCytp.do";
   		}else if (text=="修改"){
   			url="modifyCytp.do";
   		}
    });
    $('.delete').click(function(){
    	if( $('.box tbody tr').hasClass('selected') ){
    		 dialog.cancelDate('您确定要删除当前记录吗?','varietymana');
    	}else{
    	   dialog.choicedata('请选择一条数据!','varietymana');
    	}
    });
   
   	
   	$('body',parent.document).on('click','.varietymana .confirm',function(){
   		$('.box tr.selected').remove();
   		//请求后台数据,删除数据库中的内容;
   		$('.zhezhao',parent.document).remove();
   	});
   	$('body',parent.document).on('click','.varietymana .addcance,.varietymana .close,.varietymana .cancel,.varietymana .sure',function(){
   		$('.zhezhao',parent.document).remove();
   	})
   	function thrfn(obj){
    	//请求数据;
        $.ajax({
    		url:obj.url,
    		type:'post',
    		contentType:'application/json',
    		data:JSON.stringify(obj.data),
    		async:true,
    		success:function(data){
    			if(data.code==01){
    				userdata=JSON.parse( data.codeMessage );
    				dialog.ren({'cols':cols,'wh':wh,'userdata':userdata});
    			}else if(data.code==00){
    				
    			}
    		}
    	});
    }
})
