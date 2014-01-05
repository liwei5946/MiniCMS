<%@ page language="java" import="java.util.*,cn.edu.hbcit.jsj.software.bean.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
ArrayList list = new ArrayList();
String realPath = request.getRealPath("\\WEB-INF\\db\\hbcitsoftware.mdb");//Access数据库绝对路径
ConnBean cb = new ConnBean();
cb.getConn(realPath);
list = cb.selectLogs();
cb.close();
pageContext.setAttribute("logs",list);
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

<table width="98%" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td class="left_txt">当前位置：登录日志</td>
          </tr>
          <tr>
            <td>
            <table width="100%" cellspacing="0" cellpadding="2" border="1">
              <tr class="maintableth">
                <td width="10%"  class="maintable">ID</td>
                <td width="20%"   class="maintable">登录帐号</td>
                <td width="30%"   class="maintable">IP地址</td>
                <td width="30%"   class="maintable">登录时间</td>
                <td width="10%"   class="maintable">登录状态</td>
              </tr>
<c:forEach var="mynews" items="${pageScope.logs}" varStatus="countItem">
              <tr >
                <td class="maintable" align="center">${mynews.id}</td>
                <td  class="maintable" align="center">${mynews.username}</td>
                <td  class="maintable" align="center">${mynews.ip}</td>
                <td  class="maintable" align="center">${mynews.longtime}</td>
                <td  class="maintable" align="center">${mynews.loginState}</td>
              </tr>
</c:forEach>
            </table></td>
          </tr>
        </table>
        <!--<table width="98%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td>
              <div align="center" style="font-size:12px; color:#333333">
                共${pageScope.pageCount}页&nbsp;
                <c:if test="${pageScope.nowPage == 1}">
                	首页&nbsp;上一页&nbsp;
                </c:if>
                <c:if test="${pageScope.nowPage != 1}">
                    <a href="news_list.jsp?jump=1">首页</a>
                    &nbsp;
                    <a href="news_list.jsp?jump=${pageScope.nowPage - 1}">上一页</a>
                    &nbsp;
                </c:if>
                第${pageScope.nowPage}页&nbsp;
                <c:if test="${pageScope.nowPage == pageScope.pageCount}">
                	下一页&nbsp;尾页
                </c:if>
                <c:if test="${pageScope.nowPage != pageScope.pageCount}">
                    <a href="news_list.jsp?jump=${pageScope.nowPage + 1}">下一页</a>
                    &nbsp;
                    <a href="news_list.jsp?jump=${pageScope.pageCount}">尾页</a> 
                </c:if>
                &nbsp;共${pageScope.maxCount}条记录
                </div>
              
              </td>
            </tr>
            <tr>
              <td height="30" colspan="3">&nbsp;</td>
            </tr>
        </table>-->
</body>
</html>