<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<link rel="stylesheet"	href="${pageContext.request.contextPath }/layui/css/layui.css" media="all">
<title>交易日维护</title>
<style>
	.bigbox{
		margin-top:10px;
	}
	.calanchoice{
		width:100%;
		margin-top:15px;
	}
	i.thRemo{
		font-style:normal;
		margin-left:10px;
		font-size:18px;
		cursor:pointer;
	}
	.calanchoice span{
		display:inline-block;
		padding:5px 10px;
		border:1px solid #ccc;
		border-radius:5px;
		margin:5px;
	}
</style>
</head>
<body>
  <div class="bigbox">
	 <div class="layui-input-inline">
        <input type="text" class="layui-input" id="test22" placeholder="请选择日期">
     </div>
     <button class="sue layui-btn layui-btn-normal">添加</button>
     <button class="savbtn layui-btn layui-btn-normal">保存</button>
  </div>
  <div class="calanchoice"></div> 	 
</body>
<script src="${pageContext.request.contextPath }/js/jquery-1.11.3.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath }/layui/layui.all.js" charset="utf-8"></script>
<script>
layui.use('laydate', function(){
	  var laydate = layui.laydate;
	  //常规用法
	  laydate.render({
	    elem: '#test22'
	   ,showBottom: false
	  });
});
//点击确定按钮;
$('.sue').click(function(){
	var txt=$('#test22').val(),
		calslength=$('.calanchoice span').length,arr=[];
	
	if( !txt ){
		parent.layer.msg('请选择日期后再进行添加操作!', {
         time: 1000, //几秒后自动关闭
        });
	}else{ 
		for(var i=0;i<$('.calanchoice span').length;i++){
			arr.push( $('.calanchoice span:eq('+i+') b').text() );
		}
		if(arr.indexOf( txt )==-1 ){
			$('.calanchoice').append('<span class=""><b>'+txt+'</b><i class="thRemo">x</i></span>');
			arr.push( txt );
		}else{
			parent.layer.msg('该日期已经存在,请重新选择日期!', {
	         time: 1000, //几秒后自动关闭
	        });
		}
		$('.layui-input').val('');
	}
});
//点击x;
$('.calanchoice').on('click','.thRemo',function(){
	$(this).closest('span').remove();
});
//点击提交后台代码；
$('.savbtn').on('click',function(){
	var brr=[];
	for(var i=0;i<$('.calanchoice span').length;i++){
		brr.push("{"+JSON.stringify( $('.calanchoice span:eq('+i+') b').text() ) +"}" );
	} 
	layer.confirm('您确定要保存所选日期吗?', function(index){
		$.ajax({
			url : '${pageContext.request.contextPath}/calendar/inserttradcalendar',
			type : 'post',
			dataType : 'json',
			contentType : 'application/json;charset=utf-8',
			async : true,
			data:JSON.stringify({'list':brr}),
			success : function(data) {
				// var data=JSON.parse(data),str;
				
			}
		});
		layer.close(index);
		/* var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
			parent.layer.close(index); //再执行关闭    */
	});	
});

</script>
</html>