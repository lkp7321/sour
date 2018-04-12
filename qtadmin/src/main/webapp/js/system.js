$(function(){
	 //左侧nav的点击事件
	  $('.navlist li:eq(0)').addClass('licolor')
	  $('.navlist li').click(function(){
	  	  var s=$(this).attr('href')
	  	  $(this).addClass('licolor').siblings().removeClass('licolor')
	  	  $('#listiframe').attr('src', s );
	  });
})