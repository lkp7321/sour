<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title>layui</title>
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
  <link rel="stylesheet" href="<%=basePath%>layui/css/layui.css"  media="all">
  <style >.layui-form-label {width:160px} </style>
  <style >.layui-input {width:220px} </style>
  <style >.layui-form-select {width:220px} </style>    <!-- 对搜索框中的搜索进行宽度设置 -->
  <!-- 注意：如果你直接复制所有代码到本地，上述css路径需要改成你本地的 -->
</head>
<body>
<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
  <legend>账户修改</legend>
</fieldset>
 
<form class="layui-form" action="${pageContext.request.contextPath}/account/updateAccount.do">      <!-- 传入url -->
  <div class="layui-form-item">
    <label class="layui-form-label">投资者账号</label>
    <div class="layui-input-inline">
      <input type="text" name="userid" lay-verify="InvestorAccount|number" autocomplete="off" placeholder="请输入您的账号" class="layui-input userId">
    </div>
  </div>
 <div class="layui-form-item">
 <div class="layui-inline">
      <label class="layui-form-label">期货公司</label>
      <div class="layui-input-inline">
        <select name="ctpbrokerid" lay-verify="required" lay-search="" class="sel_qi">
          <option value="">请直接选择或搜索选择</option>
           <option value="0052">安粮期货次席</option>
          <option value="5090">安信期货</option>
          <option value="0001">宝城期货</option>
          <option value="4080">渤海期货二席</option>
          <option value="z26,4080">渤海期货主席</option>
          <option value="9060">创元期货</option>
          <option value="0177">大地期货</option>
          <option value="6080">大地期货</option>
          <option value="6080">大华期货</option>
          <option value="9999">大有期货</option>
          <option value="8896">大有期货金牛</option>
          <option value="0076">道通期货</option>
          <option value="9030">第一创业</option>
          <option value="6040">东方期货</option>
          <option value="4700">东海期货</option>
          <option value="7070">东航期货二席</option>
          <option value="0099">东华期货二席</option>
          <option value="4900">东吴期货</option>
          <option value="4090">东兴期货</option>
          <option value="00001">东亚期货CTP二席</option>
          <option value="3040">东证期货二席</option>
          <option value="0034">方正中期</option>
          <option value="1025">福能期货</option>
          <option value="7020">格林期货</option>
          <option value="0037">冠通期货</option>          
          <option value="8891">光大金牛资管</option>          
          <option value="6000">光大期货二席</option>       
          <option value="9000">广发期货主席9000</option>
          <option value="3003">广金期货</option>
          <option value="5070">广州期货</option>
          <option value="9050">国都期货</option>
          <option value="2014">国富期货二席</option>
          <option value="2016">国富期货主席</option>
          <option value="0029">国海良时</option>
          <option value="5030">国金二席</option>          
          <option value="5010">国金主席</option>          
          <option value="8890">国联金牛资管</option>          
          <option value="7010">国联期货</option>          
          <option value="0187">国贸期货</option>          
          <option value="7090">国泰君安</option>          
          <option value="4500">国投安信</option>          
          <option value="8030">国信期货</option>          
          <option value="0013">国元海勤</option>          
          <option value="2013">海航东银</option>
          <option value="8000">海通期货</option>
          <option value="9040">海证期货</option>
          <option value="01027">和合期货</option>
          <option value="3010">恒泰期货</option>
          <option value="4600">弘业期货</option>
          <option value="2070">宏源期货二席</option>
          <option value="1080">宏源期货主席</option>
          <option value="6020">华安期货</option>
          <option value="4400">华创期货</option>
          <option value="0176">华金期货</option>
          <option value="0769">华联期货</option>
          <option value="5050">华龙期货</option>
          <option value="8080">华泰长城</option>
          <option value="16333">华西期货</option>
          <option value="10001">华鑫期货主席</option>
          <option value="2000">华元期货</option>
          <option value="0038">混沌天成</option>
          <option value="6868">建信期货次席</option>
          <option value="95533">建信期货主席</option>
          <option value="2090">金海岸期货</option>
          <option value="0268">金汇期货</option>
          <option value="8888">金瑞期货</option>
          <option value="0193">金石期货</option>
          <option value="0028">金信期货CTP二席</option>
          <option value="0089">金元期货</option>
          <option value="0097">津投期货</option>
          <option value="3082">经易期货</option>
          <option value="0118">九州期货</option>
          <option value="7080">鲁证期货</option>
          <option value="7030">美尔雅期货</option>
          <option value="8050">南华期货</option>
          <option value="0126">南证期货</option>
          <option value="5200">平安期货</option>
          <option value="3060">普民期货</option>
          <option value="5100">瑞达期货</option>
          <option value="6050">瑞龙期货</option>
          <option value="0195">山金期货</option>
          <option value="4050">上海金源</option>
          <option value="8070">上海中期</option>
          <option value="88888">申银主席</option>
          <option value="0003">盛达期货</option>
          <option value="3050">时代期货</option>
          <option value="8016">首创期货主席</option>
          <option value="4020">天鸿期货</option>
          <option value="0150">通联期货</option>
          <option value="9099">同信久恒二席</option>
          <option value="0059">万达期货次席</option>
          <option value="4100">五矿期货二席</option>
          <option value="9090">西部期货</option>
          <option value="3497">西南期货</option>
          <option value="6090">新湖期货二席</option>
          <option value="3989">新晟期货</option>
          <option value="0021">鑫鼎盛期货</option>
          <option value="1000">信达期货</option>
          <option value="4200">兴证期货主席</option>
          <option value="5060">一德期货</option>
          <option value="4040">银河期货二席</option>
          <option value="z21,4040">银河期货主席</option>
          <option value="0262">英大期货</option>
          <option value="20000">永安期货</option>
          <option value="7777">长安期货</option>
          <option value="4300">长江期货</option>
          <option value="8060">招商期货</option>
          <option value="0005">招金期货二席</option>
          <option value="9070">浙江新世纪</option>
          <option value="6010">浙商期货</option>
          <option value="4060">中财期货二席</option>
          <option value="0155">中财期货主席</option>
          <option value="5600">中大期货</option>
          <option value="5300">中钢期货</option>
          <option value="8090">中国国际</option>
          <option value="2046">中航期货</option>
          <option value="0274">中辉期货二席</option>
          <option value="7050">中投天琪期货</option>
          <option value="9080">中信建投二席</option>
          <option value="4000">中信新际二席</option>
          <option value="66666">中信主席</option>
          <option value="5040">中银国际二席</option>
        </select>
      </div>
    </div>
    </div>

  <div class="layui-form-item">
    <div class="layui-inline">
      <label class="layui-form-label marginModel">保证金监控中心账户</label>
      <div class="layui-input-inline">
        <input type="text" name="marginnumber"  lay-verify="required|lengthLimt" placeholder="请输入您的保证金监控中心账号" autocomplete="off" class="layui-input marginnumber">
      </div>
    </div>
  </div>
  
  <div class="layui-form-item">
    <div class="layui-inline">
      <label class="layui-form-label">保证金监控中心密码</label>
      <div class="layui-input-inline">
        <input type="password" name="marginpassword" lay-verify="required|marginpass" placeholder="请输入您的保证金监控中心密码" autocomplete="off" class="layui-input marginpassword">
      </div>
    </div>
  </div>
 
  <div class="layui-form-item">
    <label class="layui-form-label">是否参与排名</label>
    <div class="layui-input-block isrank">
      <input type="radio" name="isrank" value="1" title="是" checked="" class="rank">
      <input type="radio" name="isrank" value="0" title="否" class="norank">
    </div>
  </div>
  <div class="layui-form-item">
    <div class="layui-input-block">
      <button class="layui-btn" lay-submit="" lay-filter="demo1">保存并提交</button>
      <button class="layui-btn" lay-submit="" lay-filter="demo2">取消修改</button>
    </div>
  </div>
</form>
          
<script src="<%=basePath%>layui/layui.js" charset="utf-8"></script>
<script src="<%=basePath%>js/jquery-1.11.3.js" charset="utf-8"></script>
<!-- 注意：如果你直接复制所有代码到本地，上述js路径需要改成你本地的 -->
<script>
	<!--如下为使用ajax请求数据 -->
  $(function () {
           /* $("#btnget").click(function () {
                $.ajax({
                    type: "post",        //type：(string)请求方式，POST或GET        
                    dataType: "json",    //dataType：(string)预期返回的数据类型。xml,html,json,text等
                    url: '${pageContext.request.contextPath}/account/getOneAccount.do?userid='+id,  //url：(string)发送请求的地址，可以是服务器页面也可以是WebService动作。
                    success: function (msg) {
                        var str = "";
                        for (i in msg) {
                            str += "<tr><td>" + msg[i].id + "</td><td>" + msg[i].name + "</td><td>"
                            + msg[i].cla + "</td><td>" + msg[i].sex + "</td><td>" + msg[i].tel + "</td></tr>";
                        }
                        $("tbody").append(str);
                    }
                });
            }); */
        });
 //获取session并赋值给页面；
 var data=JSON.parse( sessionStorage.getItem('data') );
    $('.userId').val( data.userid );
    $('.uspsd').val( data.userpassword);
    $('.sel_qi option[value='+data.ctpbrokerid+']').attr('selected','selected');
    $('.layui-select-title input').val( $('.sel_qi option:selected').text() );
    if( data.isrank==1){
    	 $('.isrank .rank').prop('checked','checked');
    }else{
    	$('.isrank .norank').prop('checked','checked');
    }
    $('.marginnumber').val( data.marginnumber );
    $('.marginpassword').val( data.marginpassword );
    
var id=$('html').context.URL.split('=')[1];
//console.log(id);
layui.use(['form', 'layedit', 'laydate'], function(){
  var form = layui.form
  ,layer = layui.layer
  ,layedit = layui.layedit
  ,laydate = layui.laydate;
  
  //日期
  laydate.render({
    elem: '#date'
  });
  laydate.render({
    elem: '#date1'
  });
  
  //创建一个编辑器
  var editIndex = layedit.build('LAY_demo_editor');
 
  //自定义验证规则
  form.verify({
    InvestorAccount: function(value){
      if(value.length < 6){
        return '投资者账号不得少于六位';
      }
      if(value.length > 10){
      	return '投资者账号不得超过10位';
      }
      
    }
    ,lengthLimt:function(value){
    if(value.length > 22){
      	return '您输入的内容过长，超出了限制！';
      }
    }
    ,pass: [/(.+){6,12}$/, '密码必须6到12位']
    ,marginpass:[/(.+){8,12}$/, '保证金监控中心的密码的长度为8至12位']
    ,content: function(value){
      layedit.sync(editIndex);
    }
    ,number:[/^[0-9]*$/, '必须为数字']
  });
  
  //监听指定开关
  form.on('switch(switchTest)', function(data){
    layer.msg('开关checked：'+ (this.checked ? 'true' : 'false'), {
      offset: '6px'
    });
    layer.tips('温馨提示：请注意开关状态的文字可以随意定义，而不仅仅是ON|OFF', data.othis)
  });
  
  //监听提交
  form.on('submit(demo1)', function(data){
    var code=layer.confirm('您确定要保存修改吗？', function(index){
    	/* console.log( $('.layui-form').serialize() ) */
    	$.get('${pageContext.request.contextPath}/account/updateAccount.do',$('.layui-form').serialize(),function(result){
    		
    		layer.msg( JSON.parse(result).msg,{
    			time: 1500
    		},function(){
    			window.open('${pageContext.request.contextPath}/user/toUserAccount.do',"_self");	
    		});
    		
    	});
   	}); 
    return false;//阻止表单跳转；
  });
   form.on('submit(demo2)', function(data){
   	 layer.confirm('您确定取消修改吗？', function(index){
   		window.open('${pageContext.request.contextPath}/user/toUserAccount.do',"_self");
   	 });
  	 return false;
  });
});
  //提交验证；
   function toVaild(){
	  
	   layer.confirm('您确定要保存修改吗？', function(index){  ///怎么可以让在点击确定按钮后再
  	   		//window.open('${pageContext.request.contextPath}/user/toUserAccount.do',"_self");
  	   	}); 
	  	 //校验
	    /*  var val = document.getElementById("ff").value;
         if(val == "可以提"){
        	 layer.confirm('您确定要保存修改吗？', function(index){  ///怎么可以让在点击确定按钮后再
       	   		window.open('${pageContext.request.contextPath}/user/toUserAccount.do',"_self");
       	   	  return true;
       	   	}); 
            //alert("校验成功，之后进行提交");
            
         }else{
             //alert("校验失败，不进行提交");
              return false;
         }  */
   }
</script>

</body>
</html>