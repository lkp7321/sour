<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<meta charset="utf-8">
<title>权限管理</title>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<link rel="stylesheet" href="${pageContext.request.contextPath }/layui/css/layui.css" media="all">
<style>
.layui-form-switch i {  position: absolute; left: 1px;   top: 2px; }
.layui-form-switch em{ position: absolute;  right: 0px; top: 0;  font-size:10px; }
.layui-form-onswitch i { left: 25px; top: 2px;   background-color: #fff; }
.layui-form-onswitch em { left: 1px;  right: auto;  color: #fff!important; }
.roleselect{ border: 1px solid #5a8bff; padding: 5px; margin-top: 10px; }
</style>
<!-- 注意：如果你直接复制所有代码到本地，上述css路径需要改成你本地的 -->
</head>
<body>
	<select class='roleselect' name='roleid'></select>

	<table class="layui-hide" id="test" ></table>

	<script type="text/html" id="switchTpl">
  		<input type="checkbox" name="isshow" value="{{d.isshow}}" lay-skin="switch" lay-text="是|否" lay-filter="ShowDemo" {{d.isshow== 1 ? 'checked' : '' }}>
	</script>
	<script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath }/layui/layui.all.js" charset="utf-8"></script>
	<script>
	   $(function() {
		    var ren_tab,table;
			//请求下拉列表
			$.ajax({
				url : '${pageContext.request.contextPath}/user/getallroles.do',
				type : 'get',
				dataType : 'json',
				contentType : 'application/json;charset=utf-8',
				async : false,
				success : function(data) {
					// var data=JSON.parse(data),str;
					var str;
					if (data.length > 0) {
						for (k in data) {
							str += "<option value='" + data[k].roleid + "' >" + data[k].rolename + "</option>";
						}
					}
					$('.roleselect').html(str);
				}
			});
			var roleid = $('.roleselect option:selected').val(),
				roleData;
			$.ajax({
				url : '${pageContext.request.contextPath}/user/getroleMenu.do',
				type : 'post',
				dataType : 'json',
				//contentType : 'application/json;charset=utf-8',
				async : false,
				data : {
					'roleid' : roleid
				},
				success : function(data) {
					roleData = data.data;
				}
			});
			layui.use('table', function() {
				 table = layui.table;
				 form = layui.form;
				var layer = layui.layer;


				//展示已知数据
				ren_tab=table.render({
					elem : '#test',
					cols : [ [ //标题栏
						{
							field : 'menuid',
							title : 'ID',
							width : 100,
							align : 'center',
							sort : true
						}
						, {
							field : 'menuname',
							title : '目录',
							width : 180
						}
						, {
							field : 'menuurl',
							title : '地址',
							width : 300
						}
						, {
							field : 'parentMenu',
							title : '父目录名称',
							align : 'center',
							width : 200
						}
						, {
							field : 'isshow',
							title : '是否显示',
							align : 'center',
							width : 240,
							templet : '#switchTpl',
						}
					] ],
					data : roleData, 
					id:'tests'//设置该id值
					,skin: 'row' //表格风格		    
			    	,even: true
					//,page: true //是否显示分页
					//,limits: [5, 7, 10]
					//,limit: 10 //每页默认显示的数量
				});
				//监听选择器操作
				form.on('switch(ShowDemo)', function(obj) {
					layer.tips(this.value + ' ' + this.name + '：' + obj.elem.checked, this.othis);
					var dated=$('.roleselect option:selected').val();
					 
					var menuid=$(this).closest('tr').find('td:eq(0) div').text();
					var ishow=1;
					if(obj.elem.checked==true){
						isshow=1;
					}else{
						isshow=0;
					}
					$.ajax({
						type : "POST",
						url : "${pageContext.request.contextPath}/user/updatemenu.do",
						data :{"roleid":dated,"menuid":menuid,"isshow":isshow},
						success : function(msg) {
							var data =JSON.parse(msg);
							//alert(data.msg);
							 layer.msg(data.msg,{time:1000});
						}
					}); 
				});
			});
			$('.roleselect').change(function() {
				var roleid = $('.roleselect option:selected').val();
				table.reload('tests', {
				  url: '${pageContext.request.contextPath}/user/getroleMenu.do'
				  ,where: {
					  "roleid":roleid  
				  } //设定异步数据接口的额外参数
				  //,height: 300
				});
			});
		});
	</script>

</body>
</html>
