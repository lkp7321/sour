<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>
	<title>TopMenu</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <script language="javascript" src="script/jquery.js"></script>
    <script language="javascript" src="script/pageCommon.js" charset="utf-8"></script>
    <script language="javascript" src="script/PageUtils.js" charset="utf-8"></script>
    <link type="text/css" rel="stylesheet" href="style/blue/pageCommon.css" />
	<LINK href="style/blue/top.css" type=text/css rel=stylesheet>
	
	<script type="text/javascript">
	</script>
	<style type="text/css">
		#messageArea{
			color: white;
			font-size: 14px;
			font-weight: bold;
		}
	</style>
</head>

<body CLASS=PageBody leftmargin=0 topmargin=0 marginwidth=0 marginheight=0>
	
	<div id="Head1">
		<div id="Logo">
        	<iframe name="autoRefashion" src="" width="0" height="0"></iframe>
			<a id="msgLink" href="javascript:void(0)"></a>
            <font color="#0000CC" style="color:#F1F9FE; font-size:28px; font-family:Arial Black, Arial">ShenLan OA</font> 
			<!--<img border="0" src="css/blue/images/logo.png" />-->
        </div>
		<div id="Head1Right">
			<div id="Head1Right_UserName">
                <img border="0" width="13" height="14" src="style/images/top/user.gif" /> 您好，<b>${user.name }</b>
			</div>
			<div id="Head1Right_UserDept"></div>
			<div id="Head1Right_UserSetup">
            	<a href="javascript:void(0)"><img border="0" width="13" height="14" src="style/images/top/user_setup.gif" /> 个人设置</a>
			</div>
			<div id="Head1Right_Time">
				</div>
		</div>
        <div id="Head1Right_SystemButton">
            <a href="user_logout.action" target="_parent">
                <img width="78" height="20" alt="退出系统" src="style/blue/images/top/logout.gif" />
            </a>
        </div>
        <div id="Head1Right_Button">
            <a target="desktop" href="javascript:void(0)"><img width="65" height="20" alt="显示桌面" src="style/blue/images/top/desktop.gif" /></a>
        </div>
	</div>
    
    <div id="Head2">
        <div id="Head2_Awoke">
            <ul id="AwokeNum">
                <li><a target="desktop" href="javascript:void(0)"><img border="0" width="11" height="13" src="style/images/top/msg.gif" /> 消息<span id="msg"></span></a></li>
                <li class="Line"></li>
                <li><a target="desktop" href="javascript:void(0)"><img border="0" width="16" height="11" src="style/images/top/mail.gif" /> 邮件<span id="mail"></span></a></li>
                <li class="Line"></li>
                
                <!-- 是否有待审批文档的提示1 -->
                <li><a href="formFlowAction_myTaskList.action" target="right">
                		<img border="0" width="12" height="14" src="style/images/top/wait.gif" /> 
                		待办事项（<span id="wait" class="taskListSize">0</span>）
                	</a>
                </li>
                <li class="Line"></li>
                
                <!-- 是否有待审批文档的提示2 -->
                <li id="messageArea"></li>
            </ul>
        </div>
        
        <div id="Head2_FunctionList" style="text-align: left">
        	<a href="javascript: window.parent.right.location.reload(true);">刷新</a>
        </div>
    </div>

</body>

</html>
