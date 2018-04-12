<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<base href="<%=basePath%>">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
		<title>个人资料</title>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>/css/reset.css"/>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>/css/userData.css"/>
	</head>
	<body>
	    <div class='remindBox'>
	         <div class='rehead'>提示信息</div>
	         <div class='rebody'>
	           <span>资料修改成功</span>
	            <button class='remindsure'>确定</button>
	         </div>
	          
	   </div>
		<div class="information">
			<ul>
				<li data-index = "one" class='licolor'>基本资料</li>
				<li data-index = "two">联系方式</li>
				<li data-index = "three">教育情况</li>
				<li data-index = "four">工作情况</li>
				<li data-index = "five">个人信息</li>
			</ul>
			<div class="informain">
				<div class="one basic">
					<p class="bot_line">
						<span>用户名</span>
						<input type="text" name="" value="${sessionScope.name}" class="username" readonly="true" disabled="disabled"/>
						<!--<a href="#">查看</a>  -->
						
					</p>
					<p class="bot_line">
						<span>真实姓名</span>
						<input type="text" name="" class="truename" />
						<!--<select name="" id="" class="perchoose">
							<option value="">公开</option>
							<option value="">好友可见</option>
							<option value="">保密</option>
						</select>  -->
						
					</p>
					<p class="bot_line">
						<span>性别</span>
						<lable><input type="radio" name="gender" id="" value="male"  checked="true"/>男</lable>
						<lable><input type="radio" name="gender" id="" value="female" />女</lable> 
						<lable><input type="radio" name="gender" id="" value="secrecy" />保密</lable>
						 
					</p>
					<p class="">
						<span>居住地</span>
						<select name="" id="" class="residence" >
							<option did="0" value="">--省份--</option>
							<option did="1" value="北京市">北京市</option>
							<option did="2" value="天津市">天津市</option>
							<option did="3" value="河北省">河北省</option>
							<option did="4" value="山西省">山西省</option>
							<option did="5" value="内蒙古自治区">内蒙古自治区</option>
							<option did="6" value="辽宁省">辽宁省</option>
							<option did="7" value="吉林省">吉林省</option>
							<option did="8" value="黑龙江省">黑龙江省</option>
							<option did="9" value="上海市">上海市</option>
							<option did="10" value="江苏省">江苏省</option>
							<option did="11" value="浙江省">浙江省</option>
							<option did="12" value="安徽省">安徽省</option>
							<option did="13" value="福建省">福建省</option>
							<option did="14" value="江西省">江西省</option>
							<option did="15" value="山东省">山东省</option>
							<option did="16" value="河南省">河南省</option>
							<option did="17" value="湖北省">湖北省</option>
							<option did="18" value="湖南省">湖南省</option>
							<option did="19" value="广东省">广东省</option>
							<option did="20" value="广西壮族自治区">广西壮族自治区</option>
							<option did="21" value="海南省">海南省</option>
							<option did="22" value="重庆市">重庆市</option>
							<option did="23" value="四川省">四川省</option>
							<option did="24" value="贵州省">贵州省</option>
							<option did="25" value="云南省">云南省</option>
							<option did="26" value="西藏自治区">西藏自治区</option>
							<option did="27" value="陕西省">陕西省</option>
							<option did="28" value="甘肃省">甘肃省</option>
							<option did="29" value="青海省">青海省</option>
							<option did="30" value="宁夏回族自治区">宁夏回族自治区</option>
							<option did="31" value="新疆维吾尔自治区">新疆维吾尔自治区</option>
							<option did="32" value="台湾省">台湾省</option>
							<option did="33" value="香港特别行政区">香港特别行政区</option>
							<option did="34" value="澳门特别行政区">澳门特别行政区</option>
							<option did="35" value="海外">海外</option>
							<option did="36" value="其他">其他</option>
							 
						</select>
					</p>
					<p class="">
						<button class='save'>保存</button>
					</p>
				</div>
				<div class="two contact">
					<p class="bot_line"> 
						<span>用户名</span>
						<input type="text" class='username' readonly='true' disabled='disabled'/>
					</p>
					<p class="bot_line p_qq">
						<span>QQ</span>
						<input type="text" name=""  class="qq" />
						<re class="remind"></re>
						<!-- <select name="" id="" class="perchoose">
							<option value="">公开</option>
							<option value="">好友可见</option>
							<option value="">保密</option>
						</select> -->
					</p>
					 
					<p class="bot_line p_phone">
						<span>手机</span>
						<input type="text" name="" class='phonenumber' />
						<re class="remind"></re>
						<!-- <select name="" id="" class="perchoose">
							<option value="">公开</option>
							<option value="">好友可见</option>
							<option value="">保密</option>
						</select> -->
					</p>
					<p class="p_email">
						<span>Email</span>
						<input type="email" class='email'/>
						<re class="remind"></re>
					</p>
					<p class="">
						<button class='save'>保存</button>
					</p>
				</div>
				<div class="three education">
					<p class="bot_line">
						 <span>用户名</span>
						<input type="text" class='username' readonly='true' disabled='disabled' />
					</p>
					<p class=" ">
						<span>学历</span>
						<select name="" class="education">
							<option value="">硕士</option>
							<option value="">本科</option>
							<option value="">专科</option>
							<option value="">博士</option>
							<option value="">中学</option>
							<option value="">小学</option>
							<option value="">其他</option>
						 </select>
						<!-- <select name="" id="" class="perchoose">
							<option value="">公开</option>
							<option value="">好友可见</option>
							<option value="">保密</option>
						</select> -->
					</p>
					<p class="">
						<button class='save'>保存</button>
					</p>
				</div>
				<div class="four work">
					<p class="bot_line"> 
						<span>用户名</span>
						<input type="text" class='username' readonly='true' disabled='disabled' />
						 
					</p>
					<p class="bot_line">
						<span>职位</span>
						<input type="text" class='position' />
						<!-- <select name="" id="" class="perchoose">
							<option value="">公开</option>
							<option value="">好友可见</option>
							<option value="">保密</option>
						</select> -->
					</p>
					 
					<p class=" ">
						<span>职业</span>
						<input type="text" class='profession' />
						<!-- <select name="" id="" class="perchoose">
							<option value="">公开</option>
							<option value="">好友可见</option>
							<option value="">保密</option>
						</select> -->
					</p>
					<p class="">
						<button class='save'>保存</button>
					</p>
				</div>
				<div class="five person">
					<p class="bot_line"> 
						<span>用户名</span>
						<input type="text" class='username' readonly='true' disabled='disabled' />
						 
					</p>
					<p class="bot_line p_card">
						<span>证件号</span>
						<input type="text" name="" id="" class="certificateno" />
						<re class="remind"></re>
						<!-- <select name="" id="" class="perchoose">
							<option value="">公开</option>
							<option value="">好友可见</option>
							<option value="">保密</option>
						</select> -->
					</p>
					 
					<p class="bot_line">
						<span>证件类型</span>
						<select name="" id="" class="certificatetype">
							<option value="idcard">身份证</option>
							<option value="passport">护照</option>
							<option value="driverlicense">驾驶证</option>
						 </select>
						<!-- <select name="" id="" class="perchoose">
							<option value="">公开</option>
							<option value="">好友可见</option>
							<option value="">保密</option>
						</select> -->
					</p>
					<p class="bot_line personality">
						<span>个性签名</span>
						<textarea name="styleName" rows="3" cols="80" class='signature'></textarea>
					</p>
					<p class="timeZone"> 
						<span>时区</span>
						<select name="" class="timezone">
							<option value="999">使用系统默认</option>
							<option value="-12">(GMT -12:00) 埃尼威托克岛, 夸贾林环礁</option>
							<option value="-11">(GMT -11:00) 中途岛, 萨摩亚群岛</option>
							<option value="-10">(GMT -10:00) 夏威夷</option>
							<option value="-9">(GMT -09:00) 阿拉斯加</option>
							<option value="-8">(GMT -08:00) 太平洋时间(美国和加拿大), 提华纳</option>
							<option value="-7">(GMT -07:00) 山区时间(美国和加拿大), 亚利桑那</option>
							<option value="-6">(GMT -06:00) 中部时间(美国和加拿大), 墨西哥城</option>
							<option value="-5">(GMT -05:00) 东部时间(美国和加拿大), 波哥大, 利马, 基多</option>
							<option value="-4">(GMT -04:00) 大西洋时间(加拿大), 加拉加斯, 拉巴斯</option>
							<option value="-3.5">(GMT -03:30) 纽芬兰</option>
							<option value="-3">(GMT -03:00) 巴西利亚, 布宜诺斯艾利斯, 乔治敦, 福克兰群岛</option>
							<option value="-2">(GMT -02:00) 中大西洋, 阿森松群岛, 圣赫勒拿岛</option>
							<option value="-1">(GMT -01:00) 亚速群岛, 佛得角群岛 [格林尼治标准时间] 都柏林, 伦敦, 里斯本, 卡萨布兰卡</option>
							<option value="0">(GMT) 卡萨布兰卡，都柏林，爱丁堡，伦敦，里斯本，蒙罗维亚</option>
							<option value="1">(GMT +01:00) 柏林, 布鲁塞尔, 哥本哈根, 马德里, 巴黎, 罗马</option>
							<option value="2">(GMT +02:00) 赫尔辛基, 加里宁格勒, 南非, 华沙</option>
							<option value="3">(GMT +03:00) 巴格达, 利雅得, 莫斯科, 奈洛比</option>
							<option value="3.5">(GMT +03:30) 德黑兰</option>
							<option value="4">(GMT +04:00) 阿布扎比, 巴库, 马斯喀特, 特比利斯</option>
							<option value="4.5">(GMT +04:30) 坎布尔</option>
							<option value="5">(GMT +05:00) 叶卡特琳堡, 伊斯兰堡, 卡拉奇, 塔什干</option>
							<option value="5.5">(GMT +05:30) 孟买, 加尔各答, 马德拉斯, 新德里</option>
							<option value="5.75">(GMT +05:45) 加德满都</option>
							<option value="6">(GMT +06:00) 阿拉木图, 科伦坡, 达卡, 新西伯利亚</option>
							<option value="6.5">(GMT +06:30) 仰光</option>
							<option value="7">(GMT +07:00) 曼谷, 河内, 雅加达</option>
							<option value="8">(GMT +08:00) 北京, 香港, 帕斯, 新加坡, 台北</option>
							<option value="9">(GMT +09:00) 大阪, 札幌, 首尔, 东京, 雅库茨克</option>
							<option value="9.5">(GMT +09:30) 阿德莱德, 达尔文</option>
							<option value="10">(GMT +10:00) 堪培拉, 关岛, 墨尔本, 悉尼, 海参崴</option>
							<option value="11">(GMT +11:00) 马加丹, 新喀里多尼亚, 所罗门群岛</option>
							<option value="12">(GMT +12:00) 奥克兰, 惠灵顿, 斐济, 马绍尔群岛</option>
						</select>
						<i class="nowTime">当前时间：<span></span></i>
						<i class="timeTip">如果发现当前显示的时间与您本地时间相差几个小时，那么您需要更改自己的时区设置</i>
						
					</p>
					<p class="">
						<button class='save'>保存</button>
					</p>
				</div>
			</div>
		
		</div>
		<script src="<%=basePath%>/js/jquery-1.11.3.js" type="text/javascript"></script>
		<script src="<%=basePath%>/js/regExpManger.js" type="text/javascript"></script>
		<script src="<%=basePath%>/js/userData.js" type="text/javascript"></script>
		 
	</body>
</html>
