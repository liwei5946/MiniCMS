<%@ page language="java" import="java.util.*,cn.edu.hbcit.jsj.software.bean.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
ArrayList list1 = new ArrayList();
ArrayList list2 = new ArrayList();
ArrayList list3 = new ArrayList();
ArrayList list4 = new ArrayList();
ArrayList list5 = new ArrayList();
ArrayList list6 = new ArrayList();
ArrayList list7 = new ArrayList();
String realPath = request.getRealPath("\\WEB-INF\\db\\hbcitsoftware.mdb");//Access数据库绝对路径
ConnBean cb = new ConnBean();
cb.getConn(realPath);
list1 = cb.selectNewsForInnerobtainjsp(26, 1, "1", 8);//标题字数|新闻类型|当前页码|每页显示记录的数量
list2 = cb.selectNewsForInnerobtainjsp(26, 2, "1", 8);
list3 = cb.selectNewsForInnerobtainjsp(26, 3, "1", 6);
cb.close();
pageContext.setAttribute("news1",list1);
pageContext.setAttribute("news2",list2);
pageContext.setAttribute("news3",list3);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>                                                                                                                                              
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="keywords" content="河北工业职业技术学院,河北省,计算机技术系,软件技术专业"/>
		<meta name="author" content="李玮"/>
		<title>软件技术专业 - 河北工业职业技术学院计算机技术系</title>
		<link rel="icon" href="images/logo_hbcit.png"/>
		<link rel="shortcut icon" href="images/logo_hbcit.png"/>
		<!-- general styles -->
		<link rel="stylesheet" href="inc/styles//reset.css" type="text/css" />
		<link rel="stylesheet" href="style.css" type="text/css"/>
		
        <!-- Javascript Library (jQuery) & Plugins -->
		<script type="text/javascript" src="inc/js/lib/jquery-1.4.2.min.js"></script>
		<script type="text/javascript" src="inc/js/lib/easing.js"></script>
		<script type="text/javascript" src="inc/js/lib/jcarousel.js"></script>
		<script type="text/javascript" src="inc/js/portfolious.js"></script>
		
		<!-- IE Conditionals -->
		<!--[if IE 6]>
			<link rel="stylesheet" href="inc/styles/ie6.css" type="text/css" />
			<script src="inc/js/lib/DD_belatedPNG.js"></script>
			<script>DD_belatedPNG.fix('h1 a, .shade, a.home-read-more-button, #featured-images, span.glare, .sub, #header fieldset label, a#monofactor span');</script>
		<![endif]-->
		
		<!--[if IE 7]>
			<link rel="stylesheet" href="inc/styles/ie7.css" type="text/css" />
		<![endif]-->
		
	</head>
	
	
<body>
<div id="container-wrap">
	<div id="container">
		
		<div id="header">
			<h1><a href="index.html">Portfolious</a></h1><!-- logo -->
			
			<ul><!-- main navigation -->
				<li class="active"><a href="index.html"><span>首页</span></a></li>
				<li><a href="page-template.html"><span>专业介绍</span></a></li>
				<li><a href="portfolio-listing.html"><span>新闻资讯</span></a></li>
				<li><a href="blog-listing.html"><span>通知公告</span></a></li>
				<li><a href="contact.html"><span>专业风采</span></a></li>
			</ul>
			
			<fieldset>   
					<legend title="Search">Search</legend>
                    
						<form name="search" action="" method="get">
							<label><input type="text" name="s" id="q" onblur="if (this.value == '') {this.value = 'Search';}"  onfocus="if (this.value == 'Search') {this.value = '';}" value="Search" /></label>
                            <!--
							<input type="submit" name="Search" class="hidden" />		
                             -->   			
						</form>		
                    					
			</fieldset>
			<span class="clearfix"></span>  	
		</div><!-- end of #header -->
        <!--
        <div class="copyrights">制作<a href="http://www.hbcit.edu.cn/">河北工业职业技术学院</a></div>
		-->
		<div id="featured">
			<div id="featured-info">
				<ul>
					<li>
						<h3><a href="portfolio-item.html">Droid Site - XHTML &amp; CSS</a></h3>
						<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. </p>

						<p>Ut enim ad <strong>minim veniam</strong>, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.</p>
						<a id="home-read-more-button" href="#">Read More</a>
					</li>
				</ul>				
			</div><!-- end of #featured-info -->
			<ul id="home-featured-nav">
				<li class="prev"><a href="#"><span>Previous</span></a></li>
				<li class="next"><a href="#"><span>Next</span></a></li>
			</ul>
			
			<div id="featured-images">
				<span class="glare"></span>
				<div id="images">
				<ul>
					<li><img src="images/home-dummy-featured-image.jpg" alt="" /></li>
                    <li><img src="images/home-dummy-featured-image01.jpg" alt="" /></li>
				</ul>
				</div>
			</div><!-- end of #featured-images -->
			<span class="clearfix"></span>
		</div><!-- end of #featured -->
		
		<div id="content">
			<div id="sidebar-home">
				<ul>
					<li>
						<h3>软件技术专业介绍</h3>
						<img class="shade" src="images/heading-shade.png" alt="" />
						<ul>
							<li>
								<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;河北工业职业技术学院软件技术专业是“河北省教学改革示范专业”，以服务于河北省及京津地区的中小型企业及相关行业的信息技术发展为宗旨，以培养“懂流程、善开发、能测试、会维护”的具有良好职业道德和职业素养的高技能人才为己任，以校企合一、培养学生实践能力为特色。</p>
<img src="images/weixin.jpg" alt="" class="post-thumb" height="129"/>
							  <p><strong>软件专业微信平台</strong> 软件技术专业开设了微信平台。关心软件专业的同学可以通过扫描左侧的二维码来关注软件技术专业的官方微信。我们将及时发布重要通知、教学动态、行业趋势等资讯。也希望同学们通过微信平台与我们多沟通交流，度过愉快充实的大学时光。
                              <a class="bt" href="#"><span>专业详细介绍</span></a>
                              </p>
								
							<!--	<p><a class="bt" href="#"><span>专业详细介绍</span></a></p>-->
							</li>
						</ul><!-- end of inner list -->
					</li>
					<li class="flickr">
						<h3>专业风采</h3>
						<img class="shade" src="images/heading-shade.png" alt="" />
						<ul>
							<li>
								<a href="#"><img src="images/dummy-flickr-1.jpg" alt="" /></a>
								<a href="#"><img src="images/dummy-flickr-2.jpg" alt="" /></a>
								<a href="#"><img src="images/dummy-flickr-3.jpg" alt="" /></a>
								<a href="#"><img src="images/dummy-flickr-4.jpg" alt="" /></a>
								<p><a class="bt-flickr" href="#"><span>See More Images</span></a></p>
								<span class="clearfix"></span>
							</li>
						</ul><!-- end of inner list -->
					</li>
                    <li class="flickr">
						<h3>优秀学子</h3>
						<img class="shade" src="images/heading-shade.png" alt="" />
						<ul>
							<li>
								<a href="#"><img src="images/dummy-flickr-1.jpg" alt="" /></a>
								<a href="#"><img src="images/dummy-flickr-2.jpg" alt="" /></a>
								<a href="#"><img src="images/dummy-flickr-3.jpg" alt="" /></a>
								<a href="#"><img src="images/dummy-flickr-4.jpg" alt="" /></a>
								<p><a class="bt-flickr" href="#"><span>See More Images</span></a></p>
								<span class="clearfix"></span>
							</li>
						</ul><!-- end of inner list -->
					</li>
                    
				</ul><!-- end of sidebar list -->
			</div><!-- end of #sidebar-home -->
			<div id="home-blogposts">
				<h3>新闻资讯</h3>
				<img class="shade" src="images/heading-shade.png" alt="" />
				<div id="posts" class="home">
					<ul>
						<li>
							<div class="postcontent">
								<table width="98%" border="0" align="center">
                                    <c:forEach var="mynews" items="${pageScope.news1}" varStatus="countItem">
                                      <tr>
                                        <td width="80%"><a href="SelectArticle?aid=${mynews.newsId}" title="${mynews.title}" target="_blank">${mynews.shortTitle}</a></td>
                                        <td width="20%" align="right"><div align="right">${mynews.shorttime}</div></td>
                                      </tr>
                                    </c:forEach>
                                  </table>
                                  <div align="right"><a href="list_article.jsp?tID=1&jump=1" class="bt"><span>更多...</span></a></div>
						  </div>
                          
						</li>
					</ul>
				</div>
                
                <h3>通知公告</h3>
				<img class="shade" src="images/heading-shade.png" alt="" />
				<div id="posts" class="home">
					<ul>
						<li>
							<div class="postcontent">
								<table width="98%" border="0" align="center">
                                    <c:forEach var="mynews" items="${pageScope.news2}" varStatus="countItem">
                                      <tr>
                                        <td width="80%"><a href="SelectArticle?aid=${mynews.newsId}" title="${mynews.title}" target="_blank">${mynews.shortTitle}</a></td>
                                        <td width="20%" align="right"><div align="right">${mynews.shorttime}</div></td>
                                      </tr>
                                    </c:forEach>
                                  </table>
                                  <div align="right"><a href="list_article.jsp?tID=2&jump=1" class="bt"><span>更多...</span></a></div>
						  </div>
						</li>
					</ul>
				</div>
                
                <h3>行业动态</h3>
				<img class="shade" src="images/heading-shade.png" alt="" />
				<div id="posts" class="home">
					<ul>
						<li>
							<div class="postcontent">
								<table width="98%" border="0" align="center">
                                    <c:forEach var="mynews" items="${pageScope.news3}" varStatus="countItem">
                                      <tr>
                                        <td width="80%"><a href="SelectArticle?aid=${mynews.newsId}" title="${mynews.title}" target="_blank">${mynews.shortTitle}</a></td>
                                        <td width="20%" align="right"><div align="right">${mynews.shorttime}</div></td>
                                      </tr>
                                    </c:forEach>
                                  </table>
                                  <div align="right"><a href="list_article.jsp?tID=3&jump=1" class="bt"><span>更多...</span></a></div>
						  </div>
						</li>
					</ul>
				</div>
                
<!--					<ul id="home-blogposts-nav">
						<li class="prev"><a href="#"><span>Previous</span></a></li>
						<li class="next"><a href="#"><span>Next</span></a></li>
					</ul>-->
			</div><!-- end of #home-blogposts -->
		<span class="clearfix"></span>
		</div><!-- end of #content -->
		
	</div><!-- end of #container -->
			<div class="push"></div>
</div>
	<div id="footer-wrap">
		<div id="footer">
			<ul id="nav-footer"><!-- footer navigation -->
						<li class="active"><a href="#"><span>石家庄市红旗大街626号</span></a></li>
						<li><a href="#"><span>Copyright &copy; 1996-2015 软件技术专业</span></a></li>
			</ul>
			<a href="http://www.hbcit.edu.cn" id="monofactor" target="_blank">河北工业职业技术学院 &middot; 计算机技术系</a>		
			<span class="clearfix"></span>
		</div><!-- end of #footer -->
        
	</div>

</body>                                                                                                                                         
</html>