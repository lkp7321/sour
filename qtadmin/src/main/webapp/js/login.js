require.config({
	baseUrl:"/qtadmin/js",
    paths: {
        jquery: 'jquery-1.9.1.min'
    }
});
 
require(['jquery'], function($) {
	// 点击删除按钮清空input内容
    $('.icon-del').click(function(){
        $('.input').val('');
   });
   $('.reset').click(function(){
        $('.input').val('');
   });
});


/*$(function(){
   // 点击删除按钮清空input内容
   $('.icon-del').click(function(){
        $('.input').val('');
   })
   $('.reset').click(function(){
        $('.input').val('');
   })
   // $("option").click(function(){  
   //       $("#product").removeAttr("size");  
   //       $("#product").blur();  
   //       this.attr("selected","");  
   //  });  
  
   //  $("#product").focus(function(){  
   //          $("#product").attr("size","3");  
   //   })  



})
*/