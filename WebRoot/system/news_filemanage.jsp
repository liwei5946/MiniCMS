<%@ page language="java" import="java.util.*,cn.edu.hbcit.jsj.software.bean.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
ArrayList list = new ArrayList();
String realPath = request.getRealPath("\\WEB-INF\\db\\hbcitsoftware.mdb");//Access数据库绝对路径
ConnBean cb = new ConnBean();
cb.getConn(realPath);
list = cb.selectFileInfo();
cb.close();
pageContext.setAttribute("news",list);
%>
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
<form method="post" action="${pageContext.request.contextPath }/DeleteFile" name="myform">
<table width="98%" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td class="left_txt">当前位置：新闻附件管理</td>
          </tr>
          <tr>
            <td>
            <table width="100%" cellspacing="0" cellpadding="2" border="1">
              <tr class="maintableth">
                <td width="10%"  class="maintable">ID</td>
                <td width="10%"   class="maintable">选择</td>
                <td width="30%"   class="maintable">原文件名</td>
                <td width="30%"   class="maintable">上传时间</td>
                <td width="10%"   class="maintable">容量(字节)</td>
                <td width="10%"   class="maintable">上传人</td>
              </tr>
<c:forEach var="mynews" items="${pageScope.news}" varStatus="countItem">
              <tr >
                <td class="maintable" align="center">${mynews.id}</td>
                <td  class="maintable" align="center"><input name="delfile" type="checkbox" value="${mynews.filename}" /></td>
                <td  class="maintable" align="center">${mynews.oldname}</td>
                <td  class="maintable" align="center">${mynews.longtime}</td>
                <td  class="maintable" align="center">${mynews.filesize}</td>
                <td  class="maintable" align="center">${mynews.author}</td>
              </tr>
</c:forEach>
            </table></td>
          </tr>
        </table>
        <table width="98%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="50%" height="30" align="right"><input type="submit" value="确认删除" name="B1" class="Submit" /></td>
              <td width="6%" height="30" align="right">&nbsp;</td>
              <td width="44%" height="30"><input type="reset" value="取      消" name="B12" class="Submit" /></td>
            </tr>
            <tr>
              <td height="30" colspan="3">&nbsp;</td>
            </tr>
        </table>
        </form>
</body>
</html>