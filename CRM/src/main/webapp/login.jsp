<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>">
<meta charset="UTF-8">
<title>
	
        FORP-咨询行业管理平台
    
</title>
    
    
    <script type="text/javascript" src="js/jquery.js"></script>
    <script type="text/javascript" src="js/jquery.boxy.js"></script>
    <script type="text/javascript" src="js/ShowModalDiv.js"></script>
    <script type="text/javascript" src="js/forp.js"></script>
    <script type="text/javascript" src="js/SysDialogLibrary.js"></script>
    <script type="text/javascript">
        function show() {
            if (document.all.DivDisplay.style.display == "none") {
                document.all.DivDisplay.style.display = "block";
            }
            else {
                document.all.DivDisplay.style.display = "none";
            }
        }
        function getResult() {
            new dialog().Sys_SelActivateFrameDialog(null, '', function () {
                __doPostBack('linkActivate'.replace(/\_/g, "$"), '');
            });
            return false;
        }
        $(function () {
            $("#divIos").hide();
            $("#divAndroid").hide();
            $("#Ios").click(function (e) {
                e.stopPropagation();
                $("#divIos").show();
                $(this).addClass("div1_mo");
                $("#divAndroid").hide();
            });
            $("#Android").click(function (e) {
                e.stopPropagation();
                $("#divAndroid").show();
                $(this).addClass("div1_mo");
                $("#divIos").hide();
            });
            $("#close_Ios").click(function (e) {
                e.stopPropagation();
                $("#divIos").hide();
            });
            $("#close_Android").click(function (e) {
                e.stopPropagation();
                $("#divAndroid").hide();
            });
        });
    </script>

<link rel="stylesheet" type="text/css" href="css/index.css" media="all">
</head>
<body>
    
    <form name="form1" method="post" action="login" id="form1">
<div>
<input name="__EVENTTARGET" id="__EVENTTARGET" value="" type="hidden">
<input name="__EVENTARGUMENT" id="__EVENTARGUMENT" value="" type="hidden">
<input name="__VIEWSTATE" id="__VIEWSTATE" value="/wEPDwUJNTY1MDk4MDkxD2QWAgICD2QWCmYPDxYCHgRUZXh0BR1GT1JQLeWSqOivouihjOS4mueuoeeQhuW5s+WPsGRkAgEPDxYCHwAFjAE8YSBocmVmPWh0dHA6Ly93d3cuZm9ycC5jbi8gdGFyZ2V0PV9ibGFuaz5GT1JQ5Lqn5ZOB56uZ54K5PC9hPiB8IDxhIGhyZWY9aHR0cDovL3d3dy53ZXN0ZXJhc29mdC5jb20vIHRhcmdldD1fYmxhbms+6KW/6L2v6IKh5Lu95a6Y5pa5572RPC9hPmRkAgYPFgIeB1Zpc2libGVoZAIHDw9kFgQeC29ubW91c2VvdmVyBRp0aGlzLmNsYXNzTmFtZT0nYnV0aG92ZXInOx4Kb25tb3VzZW91dAUUdGhpcy5jbGFzc05hbWU9J2J1dCdkAgkPD2QWAh4HT25DbGljawUScmV0dXJuIGdldFJlc3VsdCgpZGTOKoh9Bx75uqV7ijDaGX48g1gEpJbBr247jqKWOhua8Q==" type="hidden">
</div>

<script type="text/javascript">
//<![CDATA[
var theForm = document.forms['form1'];
if (!theForm) {
    theForm = document.form1;
}
function __doPostBack(eventTarget, eventArgument) {
    if (!theForm.onsubmit || (theForm.onsubmit() != false)) {
        theForm.__EVENTTARGET.value = eventTarget;
        theForm.__EVENTARGUMENT.value = eventArgument;
        theForm.submit();
    }
}
//]]>
</script>


<div>

	<input name="__VIEWSTATEGENERATOR" id="__VIEWSTATEGENERATOR" value="EBC4D36E" type="hidden">
	<input name="__EVENTVALIDATION" id="__EVENTVALIDATION" value="/wEdAAhfj9zfK6g8zI6kY/ExiM4DjGS8a1LZxUHx28Q/1kjxcFLGRQ0bypxf/FlHsKE5/852NvjHOkq5wKoqN6Aim8WGsXm5XV3aUNO6rm1TVMGfrXDiTb8KmNPfCzC5gRuajB18q5/TQFxRU6IPCOw0j6AGU/o1287wOMuMKU25mvNOKsisZhbbGCtyU/AX4ix+7NxNrUXp2mfONvh26RWvuxFm" type="hidden">
</div>
        <div id="loginMain">
            <div id="logintop">
                <div id="logoImg">
                    <span id="labName">FORP-咨询行业管理平台</span>
                </div>
                <div id="menu">
                    <span id="labLink"><a data-sb-indepth="true" href="default.html" target="_blank">FORP产品站点</a> | <a data-sb-indepth="true" href="default_001.html" target="_blank">西软股份官方网</a></span>
                    <input name="hidActivate" id="hidActivate" type="hidden">
                </div>
            </div>
            <div id="loginbody">
                <div id="login">
                    <div class="input">
                        <p>
                            <span>用户名：</span><input name="account" id="txtAccount" autocomplete="off" type="text">
                        </p>
                        <p>
                            <span>密&nbsp;码：</span><input name="password" id="txtPassword" type="password">
                        </p>
                       <br/>
                       <span style="color:red;">${msg }</span>
                       </br>
                    </div>
                    <div class="divbut">
                        <input name="btnOk" value="" onclick="return GetET99SN();" id="btnOk" class="but" onmouseover="this.className='buthover';" onmouseout="this.className='but'" type="submit">
                    </div>
                    <div class="msg">
                        <span id="labSoftNote"></span>
                    </div>
                </div>
            </div>
            <!--footer-->
            <div class="footer">
                <div class="mod">
                    <div class="pic" id="Ios">
                        <div class="img1"></div>
                        <div class="text">iPhone/iPad</div>
                    </div>
                    <div class="pic">
                        <div></div>
                        <div></div>
                    </div>
                    <div class="pic" id="Android">
                        <div class="img2"></div>
                        <div class="text">Android</div>
                    </div>
                </div>

            </div>
            <!--footer-->
            <!--weixin lay-->
            <div id="divIos" class="div3" style="width: 100%; height: 100%; background: transparent url(&quot;images/tc_bg.png&quot;) repeat scroll 0% 0%; z-index: 999; position: absolute; top: 0px; left: 0px; display: none;">
                <div class="div3_b">
                    <div class="div3-top">
                        <span class="title">手机客户端下载</span>
                        <span class="exit" id="close_Ios">
                            <img alt="关闭" src="images/exit.png"></span>
                    </div>
                    <div class="div3-txt">
                        <h3>第一步：下载</h3>
                    </div>
                    <div class="div3-txt">
                        <h3>第二步：设置服务器</h3>
                    </div>
                    <div class="div3-type">
                        <div class="dt-pic">
                            <img alt="ios" src="images/apple.png">
                        </div>
                        <div class="dt-p">
                            <h3>Ios版下载</h3>
                            <p>支持iPhone\ipad</p>
                        </div>
                    </div>
                    <div class="div3-wxm">
                        <img alt="" src="images/fwq.jpg" height="95px">

                    </div>
                    <div class="div3-wxm">
                        <div class="dw-pic">
                            <img alt="" src="iamges/iosweixinm.jpg">
                        </div>
                        <div class="dw-p">
                            <p>用微信【扫一扫】二维码手机直接下载安装</p>
                        </div>
                    </div>
                    <div class="div3-wxm">
                        <div class="dw-pic">
                            <img alt="" src="AppServerUrlQrCode.dat">
                        </div>
                        <div class="dw-p">
                            <p>【扫一扫】获取服务器地址</p>
                        </div>
                    </div>
                </div>
            </div>
            <div id="divAndroid" class="div3" style="width: 100%; height: 100%; background: transparent url(&quot;../images/tc_bg_001.png&quot;) repeat scroll 0% 0%; z-index: 999; position: absolute; top: 0px; left: 0px; display: none;">
                <div class="div3_b">
                    <div class="div3-top">
                        <span class="title">手机客户端下载</span>
                        <span class="exit" id="close_Android">
                            <img alt="关闭" src="images/exit.png"></span>
                    </div>
                    <div class="div3-txt">
                        <h3>第一步：下载安装包</h3>
                    </div>
                    <div class="div3-txt">
                        <h3>第二步：设置服务器</h3>
                    </div>
                    <div class="div3-type">
                        <div class="dt-pic">
                            <img alt="Android" src="images/android.png">
                        </div>
                        <div class="dt-p">
                            <h3>Android版下载</h3>
                            <p>支持Android2.1及以上版本</p>
                        </div>
                    </div>
                    <div class="div3-wxm">
                        <img alt="服务器地址" src="images/fwq.jpg" height="95px">
                    </div>
                    <div class="div3-wxm">
                        <div class="dw-pic">
                            <img alt="" src="images/androidweixinm.jpg">
                        </div>
                        <div class="dw-p">
                            <p>【扫一扫】二维码手机直接下载安装</p>
                        </div>
                    </div>
                    <div class="div3-wxm">
                        <div class="dw-pic">
                            <img alt="" src="AppServerUrlQrCode.dat">
                        </div>
                        <div class="dw-p">
                            <p>【扫一扫】获取服务器地址</p>
                        </div>
                    </div>
                </div>
            </div>
            <!--weixin lay-->
            <div id="loginbom">
                演示账号：销售 sunhui 项目部 duwengang  财务 liuwei<a onclick="return getResult();" id="linkActivate" href="javascript:__doPostBack('linkActivate','')">系统信息</a>
                <br>
                Copyright 1998-2012Westerasoft.,Ltd
            <div>
                <span id="lblMsg"></span><input name="hidSN" id="hidSN" type="hidden">
            </div>
            </div>
        </div>
    </form>
    <script language="javascript" type="text/javascript">
        function GetET99SN() {
            var pid = '823066AB';
            try {
                ET99.FindToken(pid);
            }
            catch (e) {
                document.getElementById("hidSN").value = "";
            }
            try {
                ET99.OpenToken(pid, 1);
            }
            catch (e) {
                document.getElementById("hidSN").value = "";
            }
            try {
                document.getElementById("hidSN").value = ET99.GetSN();
            }
            catch (e) {
                document.getElementById("hidSN").value = "";
            }
            try {
                ET99.CloseToken();
            }
            catch (e) {

            }
        }
    </script>




</body></html>

