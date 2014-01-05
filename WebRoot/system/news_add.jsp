<%@ page language="java" import="java.util.*,cn.edu.hbcit.jsj.software.bean.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://fckeditor.net/tags-fckeditor" prefix="FCK" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
ArrayList list = new ArrayList();
String realPath = request.getRealPath("\\WEB-INF\\db\\hbcitsoftware.mdb");//Access数据库绝对路径

ConnBean cb = new ConnBean();
cb.getConn(realPath);
list = cb.selectNewsType();
cb.close();
pageContext.setAttribute("newsType",list);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <script type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
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
<form method="post" action="${pageContext.request.contextPath }/AddNews" name="myform" enctype="multipart/form-data">
<table width="98%" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td class="left_txt">当前位置：新闻发布</td>
          </tr>
          <tr>
            <td>
            <table width="100%" cellspacing="0" cellpadding="2" border="1">
              <tr class="maintableth">
                <td width="11%"  class="maintable" align="right">新闻标题</td>
                <td width="89%"   class="maintable" align="left"><input name="title" id="mytitle" type="text" style="width:400px; margin:5px;" /></td>
              </tr>
              <tr >
                <td align="right"  class="maintable">新闻类型</td>
                <td  class="maintable">
                <select name="type" style="margin:5px;">
                <c:forEach var="mynews" items="${pageScope.newsType}" varStatus="countItem">
                    <option value="${mynews.id}">${mynews.typeName}</option>
                </c:forEach>
              	</select>
                </td>
              </tr>
              <tr >
                <td align="right"  class="maintable">新闻内容</td>
                <td  class="maintable">
                <FCK:editor id="content" basePath="../FCKeditor/" height="500"
                    imageBrowserURL="../filemanager/browser/default/browser.html?Type=Image&Connector=connectors/jsp/connector"
                    linkBrowserURL="../filemanager/browser/default/browser.html?Connector=connectors/jsp/connector"
                    flashBrowserURL="../filemanager/browser/default/browser.html?Type=Flash&Connector=connectors/jsp/connector"
                    imageUploadURL="../filemanager/upload/simpleuploader?Type=Image"
                    linkUploadURL="../filemanager/upload/simpleuploader?Type=File"
                    flashUploadURL="../filemanager/upload/simpleuploader?Type=Flash"
                    toolbarSet="Basic">
                          请输入文章内容
                </FCK:editor>
                
                </td>
              </tr>
              <tr >
                <td align="right"  class="maintable">发布人</td>
                <td  class="maintable">${sessionScope.USERNAME} <input type="hidden" value="${sessionScope.USERNAME}" name="myusername" /></td>
              </tr>

              <tr >
                <td align="right"  class="maintable">发布时间</td>
                <td  class="maintable"><input name="mytime" class="Wdate" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"></td>
              </tr>
              <tr >
                <td align="right"  class="maintable">图片附件</td>
                <td  class="maintable"><input type="file" name="images" accept="image/jpeg"/>(建议图片分辨率为 640×480 px)</td>
              </tr>
              <tr >
                <td align="right"  class="maintable">文件附件</td>
                <td  class="maintable"><input type="file" name="accessories" accept="application/msword"/>
    (限10M内的 .doc/.xls/.ppt/.pdf/.txt/.rar/.zip/.vsd 文件)</td>
              </tr>
              <tr >
                <td align="right"  class="maintable">&nbsp;</td>
                <td  class="maintable">&nbsp;</td>
              </tr>
            </table></td>
          </tr>
        </table>
        <table width="98%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="50%" height="30" align="right"><input type="submit" value="保    存" name="B1" class="Submit" /></td>
              <td width="6%" height="30" align="right">&nbsp;</td>
              <td width="44%" height="30"><input type="reset" value="取    消" name="B12" class="Submit" /></td>
            </tr>
            <tr>
              <td height="30" colspan="3">&nbsp;</td>
            </tr>
        </table>
        </form>
</body>
</html>