/*系统参数设置*/
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
	var userkey=JSON.parse( sessionStorage.getItem('menu') ).userKey,
		arr=[];
	$('.boxtop').css('width',ww);
	$('.boxtop').css('height',wh);
	
	//列参数;
    var cols = [
            { title:'参数编号', name:'paid',width:60,align:'left' },
            { title:'参数说明', name:'remk',width:60, align:'left'},
            { title:'参数值', name:'valu',width:80, align:'right'},
            { title:'参数状态', name:'stat',width:60, align:'center'}
    ];
    getlist();
    function getlist(){
    	$.ajax({
    		url:'/fx/AllBailSysPrice.do',
    		type:'get',
    		contentType:'application/json',
    		async:true,
    		success:function(data){
    			if(data.code==01){
    				userdata= data.codeMessage ;
    				dialog.ren({'cols':cols,'wh':wh,'userdata':userdata});
    			}else{
    				dialog.ren({'cols':cols,'wh':wh,'userdata':''});
    			}
    		}
    	});
    }
	$('.box tr').each(function(i,v){
		arr.push( $(v).find('td:eq(0) span').text() );
	});
    
    $('.modify').click(function(){
    	if( $('.box tbody tr').hasClass('selected') ){
    		dialog.mt4add1('sysparset1','modify');
    		
    		$('.pronum',parent.document).val( $('.box tr.selected td:eq(0) span').text() );
			$('.gruname',parent.document).val( $('.box tr.selected td:eq(1) span').text() );
			$('.rmrk',parent.document).val( $('.box tr.selected td:eq(2) span').text() );
			
			if( $('.box tr.selected td:eq(3) span').text()=='启用'){
				$('.sysparset1 input.rr1',parent.document).prop('checked','checked');
			}else{
				$('.sysparset1 input.rr2',parent.document).prop('checked','checked');
			}
	    	$('.rmrk',parent.document).on('blur',function(){
	    		if( $('.rmrk',parent.document).val()=='' ){
	    			$('.rmrk',parent.document).closest('div').find('re').remove();
	    			$('.rmrk',parent.document).closest('div').append('<re>参数值不能为空!</re>');
	    		}else if( !/^[0-9]+$/.test( $('.rmrk',parent.document).val() )){
	    			$('.rmrk',parent.document).closest('div').find('re').remove();
	    			$('.rmrk',parent.document).closest('div').append('<re>参数值必须为非负整数!</re>');
	    		}else if($('.box tr.selected td:eq(1) span').text()=='出金限制比例'&&  Number( $('.rmrk',parent.document).val() )<=0){
	    			$('.rmrk',parent.document).closest('div').find('re').remove();
	    			$('.rmrk',parent.document).closest('div').append('<re>参数值必须为0到1之间的数字(包含1)!</re>');
	    		}else if($('.box tr.selected td:eq(1) span').text()=='出金限制比例'&&Number( $('.rmrk',parent.document).val())>1 ){
	    	    	$('.rmrk',parent.document).closest('div').find('re').remove();
	    			$('.rmrk',parent.document).closest('div').append('<re>参数值必须为0到1之间的数字(包含1)!</re>');
	    		}else{
	    			$('.rmrk',parent.document).closest('div').find('re').remove();
	    		}   
	    	});    	
			
    	}else{
    		dialog.choicedata('请先选择一条数据!','sysparset1');
    	}
    });
    $('body',parent.document).on('click','.sysparset1 .sure,.sysparset1 .close,.sysparset1 .cancel',function(){
		$(this).closest('.zhezhao').remove();
	});
    //添加  修改 保存；
    $('body',parent.document).on('click','.sysparset1 .preserve',function(){
    	var da;
    	da={
			userKey:userkey,
			bean:{
				paid:$('.pronum',parent.document).val(),//paid
				remk:$('.gruname',parent.document).val(),//remk
				valu:$('.rmrk',parent.document).val(),
				stat:$('.sysparset1 input[name="r3"]:checked',parent.document).attr('tit')
			} 
    	};
		if( $('.rmrk',parent.document).val()=='' ){
			$('.rmrk',parent.document).closest('div').find('re').remove();
			$('.rmrk',parent.document).closest('div').append('<re>参数值不能为空!</re>');
		}else if( !/^[0-9]+$/.test( $('.rmrk',parent.document).val() )){
			$('.rmrk',parent.document).closest('div').find('re').remove();
			$('.rmrk',parent.document).closest('div').append('<re>参数值必须为非负整数!</re>');
		}else if($('.box tr.selected td:eq(1) span').text()=='出金限制比例'&&  Number( $('.rmrk',parent.document).val() )<=0){
			$('.rmrk',parent.document).closest('div').find('re').remove();
			$('.rmrk',parent.document).closest('div').append('<re>参数值必须为0到1之间的数字(包含1)!</re>');
		}else if($('.box tr.selected td:eq(1) span').text()=='出金限制比例'&&Number( $('.rmrk',parent.document).val())>1 ){
	    	$('.rmrk',parent.document).closest('div').find('re').remove();
			$('.rmrk',parent.document).closest('div').append('<re>参数值必须为0到1之间的数字(包含1)!</re>');
		}else{
			$('.rmrk',parent.document).closest('div').find('re').remove();
		}   	
    	console.log( da );
    	if( $('.sysparset1 re',parent.document).length==0 ){
    		$.ajax({
        		url:'/fx/upBailSysPrice.do',
        		type:'post',
        		contentType:'application/json',
        		async:true,
        		data:JSON.stringify( da ),
        		success:function(data){
        			$('.sysparset1 .preserve',parent.document).closest('.zhezhao').remove();
        			if(data.code==01){
        				dialog.choicedata(data.codeMessage,'sysparset1');
        				getlist();
        			}else{
        				dialog.choicedata(data.codeMessage,'sysparset1');
        			}
        		}
        	});
    	}
    });
    
    
})