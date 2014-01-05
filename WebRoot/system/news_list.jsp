<%@ page language="java" import="java.util.*,cn.edu.hbcit.jsj.software.bean.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
int pageSize = 30;//设置每页显示记录数 
String pageNum = request.getParameter("jump");
if(pageNum == null ||pageNum.equals("")){
	pageNum = "1";
}
ArrayList list = new ArrayList();
String realPath = request.getRealPath("\\WEB-INF\\db\\hbcitsoftware.mdb");//Access数据库绝对路径
ConnBean cb = new ConnBean();
cb.getConn(realPath);
list = cb.selectNewsForInnerinfojsp(20, pageNum, pageSize);
cb.setInnerInfoPageCount();
cb.close();

pageContext.setAttribute("maxCount",new Integer(cb.getRsCount()));
pageContext.setAttribute("pageCount",new Integer(cb.getPageCount()));
pageContext.setAttribute("nowPage",new Integer(cb.getShowPage()));
pageContext.setAttribute("news",list);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<script language="javascript">
			//为zDialog.js设置图片路径
			var IMAGESPATH = '${pageContext.request.contextPath }/system/js/images/';
		</script>
        <script type="text/javascript" src="${pageContext.request.contextPath }/system/js/jquery-1.6.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath }/system/js/zDialog.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath }/system/js/zDrag.js"></script>
        <script type="text/javascript">
		function delSports(aid){
		$.ajax({
			url :"${pageContext.request.contextPath }/DeleteArticle",
			type : 'get',
			data : 'aid='+aid,
			success :function(mm){
					var revalue=mm.replace(/\r\n/g,'');
					if(revalue=="error"){
						Dialog.alert("文章删除失败！",function(){window.location.replace('${pageContext.request.contextPath }/system/news_list.jsp');});
					}
					if(revalue=="success"){
						Dialog.alert("文章删除成功！",function(){window.location.replace('${pageContext.request.contextPath }/system/news_list.jsp') ;});
					}
				}
			});
		}
		</script>
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
            <td class="left_txt">当前位置：新闻列表</td>
          </tr>
          <tr>
            <td>
            <table width="100%" cellspacing="0" cellpadding="2" border="1">
              <tr class="maintableth">
                <td width="5%"  class="maintable">ID</td>
                <td width="8%"   class="maintable">新闻类型</td>
                <td width="56%"   class="maintable">标题</td>
                <td width="8%"   class="maintable">发布时间</td>
                <td width="8%"   class="maintable">作者</td>
                <td width="5%"   class="maintable">点击量</td>
                <td width="10%"   class="maintable">操作</td>
              </tr>
<c:forEach var="mynews" items="${pageScope.news}" varStatus="countItem">
              <tr >
                <td class="maintable" align="center">${mynews.newsId}</td>
                <td  class="maintable" align="center">${mynews.typeName}</td>
                <td  class="maintable">${mynews.title}</td>
                <td  class="maintable" align="center">${mynews.shorttime}</td>
                <td  class="maintable" align="center">${mynews.author}</td>
                <td  class="maintable" align="center">${mynews.viewCount}</td>
                <td  class="maintable" align="center">
                <a href="#" onclick="Dialog.confirm('${mynews.newsId}');">修改</a> | 
                <a href="#" onclick="Dialog.confirm('提示：您确认要删除第“${mynews.newsId}”号文章吗？',function(){delSports(${mynews.newsId});});">删除</a>
                </td>
              </tr>
</c:forEach>
            </table></td>
          </tr>
        </table>
        <table width="98%" border="0" cellspacing="0" cellpadding="0">
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
        </table>
</body>
</html>