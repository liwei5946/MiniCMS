package cn.edu.hbcit.jsj.software.control;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.*;

import org.apache.log4j.Logger;
import com.jspsmart.upload.*;

import cn.edu.hbcit.jsj.software.bean.*;
import cn.edu.hbcit.jsj.software.util.UtilTools;
import cn.edu.hbcit.jsj.software.util.ImageZipUtil;;

/**
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2009</p>
 *
 * <p>Company: 河北工业职业技术学院</p>
 *
 * @author 作者 : liwei5946@gmail.com
 * @version 创建时间：May 12, 2009 12:53:19 AM
 */
public class AddGallery extends HttpServlet {
	private ServletConfig config;
	protected final Logger log = Logger.getLogger(AddGallery.class.getName());

	/**
	 * Constructor of the object.
	 */
	public AddGallery() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession(false);
		boolean flag = false;
//		boolean file2DBFlag = false;
//		boolean img2DBFlag = false;
		String realPath = "";
		String realPathImg = "";
		String realPathThumb = "";
//		String realPathAccessories = "";
		String randomName = "";
		String nameImg = "";
		String nameThumb = "";
//		String nameAccessories = "";
		
//		String html01 = "<p align='center'><IMG  src='";
//		String html02 = "' ></p><!--此图从数据库提取-->";
		
//		String html03 = "<p align='left'>附件下载：<a class='accessories' href='";
//		String html04 = "' target='_blank'>";
//		String html05 = "</a></p>";
//		String title = request.getParameter("title");
//		String time = request.getParameter("mytime");
//		String author = request.getParameter("myusername");
//		String content = request.getParameter("content");
//		String type = request.getParameter("type");
//		String shortTime = request.getParameter("shortTime");
		
		String title = "";
		String time = "";
		String author = "";
		String content = "";
		String type = "";
		String shortTime = "";
		
		SmartUpload su = new SmartUpload(); //实例化jspSmartUpload
		su.initialize(config, request, response);// 初始化SmartUpload
		su.setAllowedFilesList("gif,jpg,jpeg,png"); //允许上传的文件类型(中间用,隔开)
		su.setMaxFileSize(100 * 1024 * 1024); //声明上传文件最大容量
		log.debug("Uploading...");
		try{
			su.upload();
			com.jspsmart.upload.File file = su.getFiles().getFile(0);
//			com.jspsmart.upload.File accessories = su.getFiles().getFile(1);
			
			title = su.getRequest().getParameter("title");
			//title = new String(su.getRequest().getParameter("title").getBytes("GBK"),"UTF-8");
			time = su.getRequest().getParameter("mytime");
			author = su.getRequest().getParameter("myusername");
			content = su.getRequest().getParameter("content");
			type = su.getRequest().getParameter("type");
			//shortTime = su.getRequest().getParameter("shortTime");
			if(time == null || time.equals("")){//用户未选择发布时间，默认以服务器当前时间为准
				java.text.SimpleDateFormat formater = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				time = formater.format(new java.util.Date());
			}
			shortTime = time.substring(0, 10);
			FileOperate fo = new FileOperate();
			
			if(!file.isMissing()){
				realPath = request.getRealPath("/")+"gallery\\";//存放图片附件
				randomName = fo.generateRandomFilename();//生成随机文件名
				nameImg = randomName + file.getFileExt();//文件名=生成随机文件名+原扩展名
				nameThumb = "thumb_" + randomName + file.getFileExt();//缩略图文件名=thumb_+生成随机文件名+原扩展名
				realPathImg = realPath + nameImg;//绝对路径+文件名
				realPathThumb = realPath + nameThumb;
				log.debug("图片文件的当前路径是：" + realPathImg);
				log.debug("缩略图的当前路径是：" + realPathThumb);
				file.saveAs(realPathImg, su.SAVE_PHYSICAL);
				log.debug("上传文件大小："+file.getSize());
				//content += ("gallery/" + nameImg);
				
//				String realPathDB = request.getRealPath("\\WEB-INF\\db\\hbcitsoftware.mdb");//Access数据库绝对路径
				/*
				 * 生成80*80缩略图
				 */
				ImageZipUtil zip = new ImageZipUtil();
				String zipFlag = "";
				File oldFile = new File(realPathImg);
				File newFile = new File(realPathThumb);
				zipFlag = zip.zipWidthHeightImageFile(oldFile, newFile, 80, 80, 100);
				log.debug("ImageZipUtil生成缩略图："+zipFlag);
				
//				ConnBean cb = new ConnBean();
//				cb.getConn(realPathDB);
//				img2DBFlag = cb.addImgInfo2DB("Uploads/" + nameImg, file.getFileName(), time, String.valueOf(file.getSize()), author);
//				img2DBFlag = cb.addGallery2DB(title, time, author, content, "gallery/" + nameImg, "gallery/" + nameThumb, Integer.parseInt(type), shortTime);
//				cb.close();
//				log.debug("图片文件信息存入数据库操作：" + img2DBFlag);
				//fo.imageWatermark("http://jsjx.hbcit.edu.cn", request.getRealPath("Uploads/" + nameImg), 190, 10);
				//fo.imageWatermark(request.getRealPath("img/xiaohui.png"), request.getRealPath("Uploads/" + nameImg),74,74);
			}else{
				log.error("相册中必须上传一张图片!");
				session.setAttribute("msg", "相册添加失败，请选择一张照片！");
				response.sendRedirect("system/error.jsp");
			}
			
//			if(!accessories.isMissing()){
//				realPathAccessories = request.getRealPath("/")+"userfiles\\";//存放文件附件
//				nameAccessories = fo.generateRandomFilename()+accessories.getFileExt();//生成随机文件名+原扩展名
//				realPathAccessories += nameAccessories;//绝对路径+文件名
//				log.debug("当前路径是：" + realPathAccessories);
//				accessories.saveAs(realPathAccessories, su.SAVE_PHYSICAL);
//				log.debug("上传文件大小："+accessories.getSize());
//				content += (html03 + "userfiles/" + nameAccessories + html04 + accessories.getFileName() + html05);
//				
//				String realPath = request.getRealPath("\\WEB-INF\\db\\hbcitsoftware.mdb");//Access数据库绝对路径
//				ConnBean cb = new ConnBean();
//				cb.getConn(realPath);
//				file2DBFlag = cb.addFileInfo2DB("userfiles/" + nameAccessories, accessories.getFileName(), time, String.valueOf(accessories.getSize()), author); 
//				cb.close();
//				log.debug("文件附件信息存入数据库操作：" + file2DBFlag);
//			}
			if(title.equals("")||time.equals("")||author.equals("")||type.equals("")||shortTime.equals("")){
				log.error("内容有空项，相册添加失败!");
				session.setAttribute("msg", "相册添加失败，请确认各项不可为空！");
				response.sendRedirect("system/error.jsp");
			}else{
//				log.debug("相册标题："+title);
//				log.debug("相册内容："+content);
				String realPathDB = request.getRealPath("\\WEB-INF\\db\\hbcitsoftware.mdb");//Access数据库绝对路径
				ConnBean cb = new ConnBean();
				cb.getConn(realPathDB);
//				flag = cb.addNews(title, time, author, content,Integer.parseInt(type) , shortTime);
				flag = cb.addGallery2DB(title, time, author, content, "gallery/" + nameImg, "gallery/" + nameThumb, Integer.parseInt(type), shortTime);
				cb.close();
				
				if(flag==true){
					log.debug("相册添加成功!");
					response.sendRedirect("system/news_list.jsp");
				}else{
					log.error("相册添加失败!");
					session.setAttribute("msg", "相册添加失败，请确认操作是否正确！");
					response.sendRedirect("system/error.jsp");
				}
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		
				
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init(ServletConfig config) throws ServletException {
		this.config = config;
	}

}
