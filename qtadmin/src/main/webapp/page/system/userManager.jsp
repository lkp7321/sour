<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>layui</title>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<link rel="stylesheet"	href="${pageContext.request.contextPath }/layui/css/layui.css" media="all">
<style>
	.layui-form .sel_show {
		display: inline-block;
	}
	.userGrade{
	    margin-top: 5px;
	    padding: 2px 0 8px 0;
	    border: 1px solid #498EFC;
	    width: 100px;
	    margin-right:20px;
	}
	.layui-btn{
	    height: 30px;
        line-height: 30px;
	}
</style>
</head>
<body>
	<script src="${pageContext.request.contextPath }/layui/layui.js" charset="utf-8"></script>
    <span>将选中的用户设置为：</span>
	<select class="userGrade"> </select>
	<div class="layui-btn-group demoTable">
		 <!--<button class="layui-btn" data-type="getCheckData">获取选中行数据</button>
		<button class="layui-btn" data-type="getCheckLength">获取选中数目</button>
		<button class="layui-btn" data-type="isAll">验证是否全选</button> -->
		
		<button class="layui-btn layui-btn-normal" data-type="modifySave">修改保存</button> 
	</div>

	<table class="layui-table"
		lay-data="{width: 980, height:510, url:'${pageContext.request.contextPath }/user/alluser.do', page:true,limits:[10,20,30], id:'idTest'}"
		lay-filter="demo">
		<thead>
			<tr>
				<th lay-data="{checkbox:true, fixed: 'left'}"></th>
				<!-- <th lay-data="{field:'userid', width:300}">ID</th> -->
				<th lay-data="{field:'username', width:180}">用户名</th>
				<th lay-data="{field:'sex', width:180}">性别</th>
				<th lay-data="{field:'truename', width:180}">真实名称</th>
				<th lay-data="{field:'phonenumber', width:180}">联系方式</th>
				<th lay-data="{field:'roleid', width:200}">权限</th>
			</tr>
		</thead>
	</table>
	<script src="${pageContext.request.contextPath }/js/jquery-1.11.3.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath }/layui/layui.all.js" charset="utf-8"></script>
	 
	<script>
		var userName='${sessionScope.name}';
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
				$('.userGrade').html(str);
			}
		});
		
		layui.use('table', function() {
			var table = layui.table;
			var $ = layui.$;
			var layer=layui.layer;
			
			 
			//监听表格复选框选择
			table.on('checkbox(demo)', function(obj) {
				//console.log(obj)
			});
			active = {
				modifySave : function() { //获取选中数据
					var checkStatus = table.checkStatus('idTest'),
						data = checkStatus.data,
						roleid=$('.userGrade option:selected').val();
				       
				        for(k in data){
				        	 
				        	data[k].roleid=roleid;
				        }
				         
				        
					$.ajax({
						url : '${pageContext.request.contextPath}/user/updateUserRole.do',
						type : 'post',
						dataType : 'json',
						async : false,
						data:{'roleList':JSON.stringify(data)},
						success : function(data) {
							//var result=JSON.parse(data);
							layer.msg(data.msg,{time:1000});
							table.reload('idTest', {
							  url: '${pageContext.request.contextPath }/user/alluser.do'
							   
							 // ,where: {
								//  "roleid":roleid  
							  //} //设定异步数据接口的额外参数
							  //,height: 300
							}); 
						}
					});    
				},
				isAll : function() { //验证是否全选
					var checkStatus = table.checkStatus('idTest');
					layer.msg(checkStatus.isAll ? '全选' : '未全选')
				} 
			};
			$('.demoTable .layui-btn').on('click', function() {
				var type = $(this).data('type');
				active[type] ? active[type].call(this) : '';
			});
			//禁用当前用户修改自己的权限
			$('body').on('each','.layui-table tr',function(i,e){
				 
				console.log($(e).find('td:eq(1)'))
				if($(e).find('td:eq(1)')==userName){
					$(e).find('td:eq(1) input').attr('disabled','disabled')
				}
				
			})
		});
		
	 
	</script>
</body>
</html>