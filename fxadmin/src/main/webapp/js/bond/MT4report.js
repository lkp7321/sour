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
		wdatepicker:'./My97DatePicker/WdatePicker',
		dialog:'dialog'
	}
});
require(['jquery','mmGrid','niceScroll','wdatepicker','dialog'],function($,mmGrid,niceScroll,wdatepicker,dialog){
	var wh=$(window).height()-190+"px",
		ww=$(window).width()-60+"px";
		$('.filescont').css('width',ww);
		$('.boxtop').css('height',wh);
		$('#d1').val( dialog.today);
		$('.date').text($('#d1').val())
		$('.files button').hide();
  var userKey=JSON.parse( sessionStorage.getItem('menu') ).userKey,userdata,
      time=$('#d1').val();
     
    $('.query').click(function(){
    	 $.ajax({
      		url:'/fx/balanceQuery.do',
      		type:'post',
      		contentType:'application/json',
      		data:time,
      		async:true,
      		success:function(data){
      			if(data.code==00){
      				var arr=JSON.parse(data.codeMessage);
      				$.each(arr,function(i,v){
      					 if(v=="true"){
      						$('.files').eq(i).find('button').show(); 
      					 }else{
      						$('.files').eq(i).find('button').hide(); 
      					 }
      				})
 
      			}else{
      				//数据异常 
      			}
      			
      		}
      	}); 
    	
    })
 
    $('body',parent.document).on('click','.balanrev .sure',function(){
		//关闭选择一条数据;
	    $(this).closest('.zhezhao').remove();
	});
 
	
});
