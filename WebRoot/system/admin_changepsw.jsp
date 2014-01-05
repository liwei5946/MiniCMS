<%@ page language="java" import="java.util.*,cn.edu.hbcit.jsj.software.bean.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	background-color: #F8F9FA;
}
-->
</style>

<link href="css/skin.css" rel="stylesheet" type="text/css" />
</head>
<body>
<form method="post" action="${pageContext.request.contextPath }/UpdatePassword" name="myform">
<table width="98%" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td class="left_txt">当前位置：更改密码</td>
          </tr>
          <tr>
            <td>
            <table width="100%" cellspacing="0" cellpadding="2" border="0">
              <tr >
                <td width="49%" align="right"  class="maintable">当前用户名：</td>
                <td width="51%"  class="maintable" style="font-weight:bold">&nbsp;&nbsp;&nbsp;&nbsp;${sessionScope.USERNAME}
                <input name="uname" type="hidden" value="${sessionScope.USERNAME}" /></td>
              </tr>
              <tr >
                <td align="right"  class="maintable">旧密码：</td>
                <td  class="maintable"><input name="oldpsw" type="password" /></td>
              </tr>
              <tr >
                <td align="right"  class="maintable">新密码：</td>
                <td  class="maintable"><input name="newpsw" type="password" /></td>
              </tr>
              <tr >
                <td align="right"  class="maintable">确认新密码：</td>
                <td  class="maintable"><input name="againpsw" type="password" /></td>
              </tr>
            </table></td>
          </tr>
        </table>
        <table width="98%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="50%" height="30" align="right"><input type="submit" value="确认修改" name="B1" class="Submit" /></td>
              <td width="6%" height="30" align="right">&nbsp;</td>
              <td width="44%" height="30"><input type="reset" value="取消修改" name="B12" class="Submit" /></td>
            </tr>
            <tr>
              <td height="30" colspan="3">&nbsp;</td>
            </tr>
        </table>
        </form>
</body>
</html>