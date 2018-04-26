<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s"  uri="/struts-tags" %>
<html>
<head>
	<title>您已退出ShenLan OA系统</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <script language="javascript" src="script/jquery.js"></script>
    <script language="javascript" src="script/pageCommon.js" charset="utf-8"></script>
    <script language="javascript" src="script/PageUtils.js" charset="utf-8"></script>
    <link type="text/css" rel="stylesheet" href="style/blue/pageCommon.css" />
	<link href="style/blue/logout.css" rel="stylesheet" type="text/css">
</head>

<body>
	<table border=0 cellspacing=0 cellpadding=0 width=100% height=100%>
		<tr>
			<td align=center>
				<div id=Logout>
					<div id=AwokeMsg>
                        <img id=LogoutImg src="style/blue/images/logout/logout.gif" border=0>
                        <img id=LogoutTitle src="style/blue/images/logout/logout1.gif" border=0>
                    </div>
					<div id=LogoutOperate>
                        <img src="style/blue/images/logout/logout2.gif" border=0> 
                        <a href="user_loginUI.action">重新进入系统</a> 
                        <img src="style/blue/images/logout/logout3.gif" border=0> 
                        <a href="javascript: window.open('','_self');window.close();">关闭当前窗口</a>
                    </div>
				</div>
			</td>
		</tr>
	</table>
</body>
</html>
