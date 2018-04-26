<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>
	<title>ShenLan OA</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <script language="javascript" src="script/jquery.js"></script>
    <script language="javascript" src="script/pageCommon.js" charset="utf-8"></script>
    <script language="javascript" src="script/PageUtils.js" charset="utf-8"></script>
    <link type="text/css" rel="stylesheet" href="style/blue/pageCommon.css" />	
	<script type="text/javascript" src="script/jquery_treeview/jquery.cookie.js"></script>
</head>

	<frameset rows="100,*,25" framespacing=0 border=0 frameborder="0">
		<frame noresize name="TopMenu" scrolling="no" src="home_top.action">
		<frameset cols="180,*" id="resize">
			<frame noresize name="menu" scrolling="yes" src="home_left.action">
			<frame noresize name="right" scrolling="yes" src="home_right.action">
		</frameset>
		<frame noresize name="status_bar" scrolling="no" src="home_bottom.action">
	</frameset>

	<noframes><body>
</body>
</noframes></html>



