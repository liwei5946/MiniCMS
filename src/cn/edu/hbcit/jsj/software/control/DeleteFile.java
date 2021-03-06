package cn.edu.hbcit.jsj.software.control;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import cn.edu.hbcit.jsj.software.bean.*;

/**
 * <p>Description: 删除文件附件</p>
 *
 * <p>Copyright: Copyright (c) 2009</p>
 *
 * <p>Company: 河北工业职业技术学院</p>
 *
 * @author 作者 : liwei5946@gmail.com
 * @version 创建时间：May 26, 2009 12:17:19 PM
 */
public class DeleteFile extends HttpServlet {
	protected final Logger log = Logger.getLogger(DeleteFile.class.getName());
	/**
	 * Constructor of the object.
	 */
	public DeleteFile() {
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
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doPost(request, response);
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

		response.setContentType("text/html");
		request.setCharacterEncoding("GB2312");
		boolean fileFlag = false;
		boolean dbFlag = false;
		FileOperate fo = new FileOperate();
		String fileName[] = request.getParameterValues("delfile");
		
		for(int i=0; i<fileName.length ; i++){
			fileFlag = fo.deleteFile(request.getRealPath(fileName[i]));
			log.debug(fileName[i]+"文件删除状态:"+fileFlag);
			if(fileFlag){
				String realPath = request.getRealPath("\\WEB-INF\\db\\hbcitsoftware.mdb");//Access数据库绝对路径
				ConnBean cb = new ConnBean();
				cb.getConn(realPath);
				dbFlag = cb.deleteFileInfo4DB(fileName[i]);
				cb.close();
				log.debug(fileName[i]+"数据库删除状态:"+dbFlag);
			}
		}
		response.sendRedirect("system/news_filemanage.jsp");
		
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
