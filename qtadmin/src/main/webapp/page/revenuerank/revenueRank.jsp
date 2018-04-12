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
<title>总排名</title>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<link rel="stylesheet" href="${pageContext.request.contextPath }/layui/css/layui.css" media="all">
<style>
.layui-form-switch i {
    position: absolute;
    left: 1px;
    top: 2px;
}
.layui-form-switch em{
    position: absolute;
    right: 0px;
    top: 0;
    font-size:10px;
}
.layui-form-onswitch i {
	left: 25px;
	top: 2px;
    background-color: #fff;
}
.layui-form-onswitch em {
	left: 1px;
    right: auto;
    color: #fff!important;
}
</style>
<!-- 注意：如果你直接复制所有代码到本地，上述css路径需要改成你本地的 -->
</head>
<body>
	<table class="layui-hide" id="test" lay-filter="test"></table>
	<script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath }/layui/layui.all.js" charset="utf-8"></script>
	<script type="text/html" id="titleTpl">
  			<a href="javascript:;">{{d.LAY_TABLE_INDEX+1}}</a>
	</script>
	<script>
	   $(function() {
		    var ren_tab,table;
			//请求下拉列表
			var roleData;
			$.ajax({
				url : '${pageContext.request.contextPath}/revenue/revenuerank.do',
				type : 'post',
				async : false,
				success:function(datae ) {
					roleData =JSON.parse( datae );
				}
			});
			//console.log(roleData  )
			layui.use('table', function() {
				 table = layui.table;
				//展示已知数据
				table.render({
					elem : '#test',
					data : roleData, 
					height:400,
					cols : [ [ //标题栏
					     { 
							templet: '#titleTpl',
							title : '总排名',
						    width:80,
						    align:'center'
					     },
						 {
							field : 'username',
							title : '交易者',
							width : 80
						}
						, {
							field : 'allNet',
							title : '累计净值',
							width : 95,
							align:'center',
							sort : true
						}
						, {
							field : 'customerRights',
							title : '客户权益',
							align : 'center',
							width : 150,
							sort : true
						}
						, {
							field : 'allNetPor',
							title : '累计净利润',
							align : 'center',
							width : 130,
							sort : true
						}
						, {
							field : 'allOperateFee',
							title : '累计手续费',
							align : 'center',
							width : 150,
							sort : true
						}
						, {
							field : 'allRate',
							title : '累计收益率',
							align : 'center',
							width : 150,
						}
						, {
							field : 'bigRetracement',
							title : '最大回撤',
							align : 'center',
							width : 150,
							sort : true
						}, {
							field : 'winRate',
							title : '胜率',
							align : 'center',
							width : 150,
							sort : true
						}
					] ],
					//id:'test',//设置该id值
					//,skin: 'line' //表格风格
					even : true
					,page: true //是否显示分页
					,limits: [10, 20, 30]
					,limit: 10 //每页默认显示的数量
					,done:function( res,cuur,count){
						//console.log( res.data[0].accounts );
						$('.layui-table tbody tr').each(function(i,v){
							$(v).find('td:eq(0)').attr( 'count',res.data[i].accounts );
						});
					}
				});
				table.on('sort(test)', function(obj){ 
					$('.layui-table-body tr').each(function(i,v){
						$(v).find('td:eq(0) a').text( i+1 );
					});
				});
				//点击tr；
				$('.layui-table').on('click','tr',function(){
					var count= $(this).find('td:eq(0)').attr('count');
					parent.layer.open({
					  type: 2, 
					  title:'收益排名',
					  area:['91%','82%'],
					  content:"${pageContext.request.contextPath }/page/revenuerank/revenuelayer.jsp?count="+count//这里content是一个URL，如果你不想让iframe出现滚动条，你还可以content: ['http://sentsin.com', 'no']
					}); 
				});
			});
		});
	</script>

</body>
</html>
