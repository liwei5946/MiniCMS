<%@ page language="java" import="java.util.*,cn.edu.hbcit.jsj.software.bean.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
ArrayList list1 = new ArrayList();

String realPath = request.getRealPath("\\WEB-INF\\db\\hbcitsoftware.mdb");//Access数据库绝对路径
ConnBean cb = new ConnBean();
cb.getConn(realPath);
list1 = cb.selectGalleryThumb("6", 1);//相册1(专业风采)的缩略图查询4张
cb.close();

pageContext.setAttribute("thumb1",list1);
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
        <link rel="stylesheet" href="inc/styles/jquery.lightbox-0.4.css" type="text/css"/>
		<link rel="stylesheet" href="style.css" type="text/css"/>
		
        <!-- Javascript Library (jQuery) & Plugins -->
		<script type="text/javascript" src="inc/js/lib/jquery-1.4.2.min.js"></script>
		<script type="text/javascript" src="inc/js/lib/easing.js"></script>
		<script type="text/javascript" src="inc/js/lib/jcarousel.js"></script>
		<script type="text/javascript" src="inc/js/portfolious.js"></script>
        <script type="text/javascript" src="inc/js/jquery.lightbox-0.4.pack.js"></script>
		<!-- / fim dos arquivos utilizados pelo jQuery lightBox plugin -->
    <script type="text/javascript">
    $(function() {
        $('#gallery a').lightBox();
    });
    </script>
    <style type="text/css">
	/* jQuery lightBox plugin - Gallery style */
	#gallery {
		padding: 2px;
		
	}
	#gallery ul { list-style: none; }
	#gallery ul li { display:inline; }
	#gallery ul img {
		border: 1px solid #3e3e3e;
		border-width: 5px 5px 10px;
	}
	#gallery ul a:hover img {
		border: 1px solid #FF3300;
		border-width: 5px 5px 10px;
		color: #fff;
	}
	#gallery ul a:hover { color: #fff; }
	</style>
		<!-- IE Conditionals -->
		<!--[if IE 6]>
			<link rel="stylesheet" href="inc/ie6.css" type="text/css" />
			<script src="inc/js/lib/DD_belatedPNG.js"></script>
			<script>DD_belatedPNG.fix('h1 a, .shade, a.home-read-more-button, #featured-images, span.glare, .sub, #header fieldset label, a#monofactor span');</script>
		<![endif]-->
		
		<!--[if IE 7]>
			<link rel="stylesheet" href="inc/ie7.css" type="text/css" />
		<![endif]-->
		
	</head>
	
	
<body>
<div id="container-wrap" class="sub"><!-- sub page background override -->
	<div id="container">
		
		<div id="header">
			<h1><a href="#">Portfolious</a></h1><!-- logo -->
			
			<ul><!-- main navigation -->
				<li class="active"><a href="index.jsp"><span>首页</span></a></li>
				<li><a href="page-template.html"><span>专业介绍</span></a></li>
				<li><a href="portfolio-listing.html"><span>新闻资讯</span></a></li>
				<li><a href="blog-listing.html"><span>通知公告</span></a></li>
				<li><a href="contact.html"><span>专业风采</span></a></li>
			</ul>
			
			<fieldset>   
					<legend title="Search">Search</legend>
						<form name="search" action="" method="get">
							<label><input type="text" name="s" id="q" onblur="if (this.value == '') {this.value = 'Search';}"  onfocus="if (this.value == 'Search') {this.value = '';}" value="Search" /></label>
							<!--<input type="submit" name="Search" class="hidden" />	-->			
						</form>								
			</fieldset>
			<span class="clearfix"></span>  	
		</div><!-- end of #header -->
		
		<div id="content" class="sub">
			<div id="sidebar">
				<ul>
					<li>
						<h3>常用链接</h3>
						<img class="shade" src="images/heading-shade.png" alt="" />
						<ul class="cats">
							<li class="active"><a href="http://www.hbcit.edu.cn" target="_blank"><span>学院首页</span></a></li>
							<li><a href="http://jsjx.hbcit.edu.cn/" target="_blank"><span>计算机系</span></a></li>
							<li><a href="http://jw.hbcit.edu.cn/" target="_blank"><span>教务系统</span></a></li>
                            <li><a href="http://222.30.178.10/" target="_blank"><span>学院内网</span></a></li>
                            <li><a href="http://jpk.hbcit.edu.cn/" target="_blank"><span>精品课程</span></a></li>
                            <li><a href="http://oa.hbcit.edu.cn/lkoa5/" target="_blank"><span>办公OA</span></a></li>
						</ul><!-- end of inner list -->
						<span class="clearfix"></span>
					</li>					
					
					<li class="twitter">
						<h3>官方微信</h3>
						<img class="shade" src="images/heading-shade.png" alt="" />
						<div class="tweets"><ul>
							<li>
								<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;软件技术专业开设了微信平台。关心软件专业的同学可以通过扫描二维码来关注软件技术专业的官方微信。我们将及时发布重要通知、教学动态、行业趋势等资讯。也希望同学们通过微信平台与我们多沟通交流，度过愉快充实的大学时光。</p>
							</li>
                            <li>
								<div align="center"><img src="images/weixin.jpg" alt="" height="86" /></div>
							</li>
						</ul>
						</div><!-- end of inner list -->
				<!--		<p><a href="#" class="bt arrowedbt"><span>Follow Us!</span></a></p>		-->				
						<ul class="twitter-nav">
							<li class="prev"><a href="#"><span>Previous</span></a></li>
							<li class="next"><a href="#"><span>Next</span></a></li>
						</ul>
						<span class="clearfix"></span>
					</li>
					
					
					<li class="flickr sideflickr">
						<h3>专业风采</h3>
						<img class="shade" src="images/heading-shade.png" alt="" />
						<ul>
							<li>
                            <div id="gallery">
								<c:forEach var="mynews" items="${pageScope.thumb1}" varStatus="countItem">
								<a href="${mynews.filename }" title="${mynews.title }"><img src="${mynews.thumb }" alt="${mynews.title }" /></a>
								</c:forEach>
                            </div>
								<span class="clearfix"></span>
							</li>
						</ul><!-- end of inner list -->
					</li>
					
				</ul><!-- end of sidebar list -->
			</div><!-- end of #sidebar-->
			<div id="wide-column">
				<h2>404 Error Page</h2>
				<img class="shade" src="images/heading-shade.png" alt="" />
				<div id="posts" class="blog" style="height:350px;">
					<div class="each-post postcontent" style="height:350px;">			        	
			        	<div class="the-post">
							
            <div align="center">
              <!--中栏内容填入此处以下 begin-->
              <TABLE border=0 align="center" cellPadding=0 cellSpacing=0>
  <TBODY>
    <TR>
      <TD width=8 height=8><IMG height=8 src="img/border/a_1.gif" 

            width=8 border=0></TD>
      <TD align=right background="img/border/b_1.gif" height=8><IMG 

            height=8 src="img/border/c_1.gif" width=34 border=0></TD>
      <TD width=8 height=8><IMG height=8 src="img/border/d_1.gif" 

            width=8 border=0></TD>
    </TR>
    <TR>
      <TD vAlign=top width=8 background="img/border/a_3.gif"><IMG 

            height=32 src="img/border/a_2.gif" width=8 border=0></TD>
      <TD bgColor=#ffffff><table border="0" cellpadding="6" cellspacing="0">
        <tr>
          <td><img src="img/404.jpg" width="300" height="289" border="0"></td>
        </tr>
        <tr>
          <td align="center"><div align="center"><strong>您访问的页面不存在！请检查网址是否正确。
                <br/>
            (错误代码:404)          </strong></div></td>
        </tr>
      </table></TD>
      <TD vAlign="bottom" width=8 background="img/border/d_2.gif"><IMG 

            height=24 src="img/border/d_3.gif" width=8 border=0></TD>
    </TR>
    <TR>
      <TD width=8 height=8><IMG height=8 src="img/border/a_4.gif" 

            width=8 border=0></TD>
      <TD align=left background="img/border/c_4.gif" height=8><IMG 

            height=8 src="img/border/b_4.gif" width=25 border=0></TD>
      <TD width=8 height=8><IMG height=8 src="img/border/d_4.gif" 

            width=8 border=0></TD>
    </TR>
  </TBODY>
</TABLE>
       	  <!--中栏内容填入此处以上 end--></div>

							
						</div><!--end of .the-post -->
						
					</div><!-- end of each post -->
					
				</div><!-- end of #posts -->
			</div><!-- end of #wide-column -->
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