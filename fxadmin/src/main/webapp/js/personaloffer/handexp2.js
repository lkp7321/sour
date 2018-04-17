/*报价平台-账户交易-价格监控*/
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
		},
		"template":{
			"deps":['jquery'],
			'exports':'template' 
		}
	},
	paths:{
		jquery:'js_files/jquery-1.9.1.min',
		mmGrid:'js_files/mmGrid',
		template:'js_files/template',
		niceScroll:'js_files/jquery.nicescroll.min',
		dialog:'dialog'
	}
});
require(['jquery','mmGrid','niceScroll','dialog','template'],function($,mmGrid,niceScroll,dialog,template){
	var wh=$(window).height()-190+"px",
		ww=$(window).width()-270+"px";
	var product=sessionStorage.getItem('product'),
		tit=$('title').text();
	$('.boxtop,.thea').css('width',ww);
	$('.boxtop').css('height',wh);
	
	if(tit=='产品管理-分行价格监控'){
		$.ajax({
			url:'/fx/getBrnchCom.do',
			type:'post',
			contentType:'application/json',
			async:true,
			success:function(data){
				if(data.code==01){
					var str='';
					userdata=JSON.parse( data.codeMessage );
					for( var i in userdata){
						str+='<option ptid='+userdata[i].PTID+'>'+userdata[i].PTNM+'</option>'
					}
					$('.prodsel').html( str );
					getlist1( $('.prodsel option:selected').attr('ptid') );
				}else if(data.code==00){

				}
			}
		});
	}else if( tit=='分行价格监控' ){
		getlist1( product );
		
	}else if( tit=='账户交易-价格监控' ){		//账户交易价格监控
		getlist1( 'P007' );
	}
	$('.prodsel').change(function(){
		getlist1( $('.prodsel option:selected').attr('ptid') );
	});
    //请求列表数据；
   function getlist1( obj ){
	   setInterval(function(){
		   $.ajax({
				url:'/fx/getBrnchprice.do',
				type:'post',
				contentType:'application/json',
				async:true,
				data:obj,
				success:function(data){
					var str;
					if(data.code==01){
						userdata=data.codeMessage;
						
						if( $('.box tbody tr').length>0){
							
						}else{
							$('.box tbody').html( template($('.tem').html(),userdata ) );
							
						}
						$('.tablepa tbody tr').each(function(i,v){
							$(v).find('td:eq(0)').html( userdata[i].exnm );			
							$(v).find('td:eq(1)').html( userdata[i].neby );				
							$(v).find('td:eq(2)').html( userdata[i].nemd );				
							$(v).find('td:eq(3)').html( userdata[i].nesl );				
							$(v).find('td:eq(4)').html( userdata[i].mdtm );				
							$(v).find('td:eq(5)').html( userdata[i].stfg );				
							$(v).find('td:eq(6)').html( userdata[i].mknm );				
							$(v).find('td:eq(7)').html( userdata[i].ercd );				
							$(v).find('td:eq(8)').html( userdata[i].trfg );				
						});
						$('.boxtop').niceScroll({
							touchbehavior:false,
							cursorcolor:"#666",
							cursoropacitymax:0.7,
							cursorwidth:6,
							background:"#ccc",
							autohidemode:false,
							horizrailenabled:true
						});	
						
					}else if(data.code==00){
						
					}
				}
			});
	  },1000);
   }
  $('.unus_serbtn').click(function(){
	  var txt=$('.unus_seript').val();
	  if($.trim(txt)!=""){
          $(".box tbody tr ").hide().filter(":contains('"+txt+"')").show();
       }else{
          $(".box tbody tr ").show();
       }
  });
  $('table.box tbody').on('click','tr',function(){
	   	if( $(this).hasClass('selected') ){
			 $(this).removeClass('selected');
		}else{
			 $(this).addClass('selected');	
			 $(this).siblings().removeClass('selected');
		}
  });
})

