/**
* Copyright(C) 2012, 河北工业职业技术学院计算机系2010软件专业.
*
* 模块名称：     
* 子模块名称：   
*
* 备注：
*
* 修改历史：
* 2014-1-21	0.1		李玮		新建
*/
package cn.edu.hbcit.jsj.software.control;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import cn.edu.hbcit.jsj.software.bean.ConnBean;
import cn.edu.hbcit.jsj.software.bean.FileOperate;

/**
 * 删除画廊类
 * 简要说明:
 * @author 李玮
 * @version 1.00  2014-1-21下午08:43:42	新建
 */

public class DeleteGallery extends HttpServlet {

	protected final Logger log = Logger.getLogger(DeleteGallery.class.getName());
	/**
	 * Constructor of the object.
	 */
	public DeleteGallery() {
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
		request.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		String id = request.getParameter("aid");
		boolean dbFlag = false;
		boolean fileFlag = false;
		String fileName[] = new String[2];
		if(id.equals("")){
			log.debug("删除失败");
			request.setAttribute("msg", "删除失败,请从正常渠道访问本系统！");
			//response.sendRedirect("system/error.jsp");
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/system/error.jsp");
			rd.forward(request, response);
		}
		String realPath = request.getRealPath("\\WEB-INF\\db\\hbcitsoftware.mdb");//Access数据库绝对路径
	    ConnBean cb = new ConnBean();
	    FileOperate fo = new FileOperate();
	    cb.getConn(realPath);
	    fileName = cb.selectGalleryFile(id);
	    for(int i=0; i<fileName.length ; i++){
			fileFlag = fo.deleteFile(request.getRealPath(fileName[i]));
			log.debug(fileName[i]+"文件删除状态:"+fileFlag);
		}
	    if(fileFlag){
			dbFlag = cb.deleteGallery(id);//删除数据库中的画廊信息
			log.debug(id + "号记录数据库删除状态:"+dbFlag);
		}
	    cb.close();
	    if(dbFlag==true){
			log.debug("画廊删除成功");
			out.print("success");
		}else{
			log.debug("画廊删除失败");
			out.print("error");
		}
		out.flush();
		out.close();
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
