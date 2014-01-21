package cn.edu.hbcit.jsj.software.control;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import cn.edu.hbcit.jsj.software.bean.ConnBean;

/**
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2009</p>
 *
 * <p>Company: 河北工业职业技术学院</p>
 *
 * @author 作者 : liwei5946@gmail.com
 * @version 创建时间：May 14, 2009 10:27:11 PM
 */
public class DeleteArticle extends HttpServlet {

	protected final Logger log = Logger.getLogger(DeleteArticle.class.getName());
	/**
	 * Constructor of the object.
	 */
	public DeleteArticle() {
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

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		request.setCharacterEncoding("utf-8");
		//HttpSession session = request.getSession(false);
		String id = request.getParameter("aid");
		
		if(id.equals("")){
			log.debug("删除失败");
			request.setAttribute("msg", "删除失败,请从正常渠道访问本系统！");
			//response.sendRedirect("system/error.jsp");
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/system/error.jsp");
			rd.forward(request, response);
		}
		
		boolean loginFlag = false;
	    String realPath = request.getRealPath("\\WEB-INF\\db\\hbcitsoftware.mdb");//Access数据库绝对路径
	    ConnBean cb = new ConnBean();
		log.debug(realPath);
		cb.getConn(realPath);
		loginFlag = cb.deleteNews(id);
		cb.close();
		
		if(loginFlag==true){
			log.debug("删除成功");
			out.print("success");
			//response.sendRedirect("system/news_list.jsp");
			//RequestDispatcher rd = getServletContext().getRequestDispatcher("/system/news_list.jsp");
			//rd.forward(request, response);
		}else{
			log.debug("删除失败");
			out.print("error");
			//session.setAttribute("msg", "文章删除失败，请确认操作是否正确！");
			//response.sendRedirect("system/error.jsp");
			//RequestDispatcher rd = getServletContext().getRequestDispatcher("/system/error.jsp");
			//rd.forward(request, response);
		}
		out.flush();
		out.close();
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
		this.doGet(request, response);
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
