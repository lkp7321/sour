<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang='en'>
<head>
<meta charset="UTF-8">
<title></title>
<link rel="stylesheet" href='<%=basePath%>css/reset.css'>
<link rel="stylesheet" href="<%=basePath%>css/style.css" />
</head>
<body>
	 
	   <div class="footer_cont_main">
	    <div class="footer_main">
	       <div>
	          <div class="footer_logo">
	            <img src="<%=basePath%>img/Yllogo.png" />
	            <span>亚联（天津）信息技术有限责任公司</span>
	          </div>
	          <div class="synopsis">
	             本公司成立于2013年10月18日，由亚洲金融合作联盟（三亚）(AFCA）联合民生加银资产管理有限公司发起成立。公司秉承“科技改变生活”的理念，引进、研发并推动发展金融行业先进的信息技术，以提高金融行业信息技术水平，促进并带动金融行业信息技术的长足发展为目标，创新科技、超越常规理念，力争提供行业内最优秀的信息技术服务。是银行等金融行业最可信赖的科技信息综合服务提供商。
	          </div>
	       </div>
	       <div class="contact">
	         <ul>
	          <li class="textsize">联系我们</li>
	          <li><img src="<%=basePath%>img/address.png" /><span>地址：北京市大兴区荣京东街大族广场T2座16层</span></li>
	          <li><img src="<%=basePath%>img/phone.png" /><span>电话：010-51641588</span></li>
	          <li><img src="<%=basePath%>img/email.png" /><span>邮箱：010-51641588</span></li>
	         </ul>
	         
	       </div>
	       <div class="focusour">
	         <ul>
	          <li class="textsize">关注我们</li>
	          <li><img src="<%=basePath%>img/yalian.jpg" height="160" width="160"/></li>
	         </ul>
	         
	       </div>
	    </div>
	    <div class="footer_text">
	       <p>Copyright © 2013-2016 AFCAT.com.cn All Rights Reserved. 亚联（天津）信息技术有限责任公司 版权所有</p>
	       <p>津ICP备14007047号</p>
	       
	    </div> 
	      
	   </div>
	 
	 
</body>
<script src='<%=basePath%>js/jquery-1.9.1.min.js'></script>
<script>
	 
 </script>
</html>
