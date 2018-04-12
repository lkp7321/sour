$(function(){
	//图片轮播；
	var imgs=$('#ban li').length,
		imgdom=$('#ban li'),
		img_wid=$('#ban li').width(),
		i=0;
	var change_ban=setInterval(function(){
		i++;
		if( i>imgs-1){
			i=0;
		}
		$('#ban li').fadeOut(function(){
			$(this).css({
				'zIndex':1
			});
		});
		$('#ban li').eq(i).fadeIn(function(){
			$(this).animate(
				{
				 'zIndex':2
				},'normal');
		});
		$('.bann_point .show_point').removeClass('show_point');
		$('.bann_point i').eq(i).addClass('show_point');
	},3000);
	//点击banner下的两个点
	$('.bann_point i').click(function(){
		i=$(this).index();
		
		$('#ban li').fadeOut(function(){
			$(this).css({
				'zIndex':1
			});
		});
		$('#ban li').eq(i).fadeIn(function(){
			$(this).animate(
			{
			 'zIndex':2
			},'normal');
		});
		$('.bann_point .show_point').removeClass('show_point');
		$('.bann_point i').eq(i).addClass('show_point');
	});
	//点击li_list;
	/*$('.li_list li').click(function(){
		var txt=$(this).find('a').text();
		$(this).siblings().removeClass('check_li')
		$(this).addClass('check_li');*/
		/*window.location.href='zaixian.html';*/
		/*if( txt=='在线教程'){
			clearInterval( change_ban );
			$('#ban').css('height','auto');
			$('#ban li').css({
				'opacity':0,
				'zIndex':1
			});
			$('#ban li').eq(1).animate(
				{
				 'opacity':1,
				 'zIndex':2
				},'fast');
			$('.bann_point').hide();
		}else if(txt=='首页'){
			clearInterval( change_ban );
			$('#ban').css('height','auto');
			change_ban=setInterval(function(){
				i++;
				if( i>imgs-1){
					i=0;
				}
				$('#ban li').css({
					'opacity':0,
					'zIndex':1
				});
				$('#ban li').eq(i).animate(
					{
					 'opacity':1,
					 'zIndex':2
					},'normal');
				$('.bann_point .show_point').removeClass('show_point');
				$('.bann_point i').eq(i).addClass('show_point');
			},3000);
		}else{
			clearInterval( change_ban );
			$('#ban').css('height',80+'px');
			$('#ban li').css({
				'opacity':0,
				'zIndex':1
			});
			$('#ban li').eq(0).animate(
				{
				 'opacity':1,
				 'zIndex':2
				},'fast');
		}*/
	//});
})
