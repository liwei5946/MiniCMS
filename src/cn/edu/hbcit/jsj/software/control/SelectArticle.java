package cn.edu.hbcit.jsj.software.control;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import cn.edu.hbcit.jsj.software.bean.ConnBean;
import cn.edu.hbcit.jsj.software.util.UtilTools;

/**
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2009</p>
 *
 * <p>Company: 河北工业职业技术学院</p>
 *
 * @author 作者 : liwei5946@gmail.com
 * @version 创建时间：May 14, 2009 12:18:18 PM
 */
public class SelectArticle extends HttpServlet {

	protected final Logger log = Logger.getLogger(SelectArticle.class.getName());
	/**
	 * Constructor of the object.
	 */
	public SelectArticle() {
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
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession(false);
		ArrayList list = new ArrayList();
		//boolean loginFlag = false;
		String id = "1";
		id = request.getParameter("aid");
		UtilTools ut = new UtilTools();
		if(ut.isNumeric(id)){ //id是整数，才可继续浏览，否则有sql注入等危险
			boolean flag = false;
			String realPath = request.getRealPath("\\WEB-INF\\db\\hbcitsoftware.mdb");//Access数据库绝对路径
		    ConnBean cb = new ConnBean();
			log.debug(realPath);
			cb.getConn(realPath);
			list = cb.selectNewsContext(Integer.parseInt(id));
			flag = cb.updateViewCount(Integer.parseInt(id));
			cb.close();
			if(!list.isEmpty()){
				request.setAttribute("article", list);
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/article.jsp");
				rd.forward(request, response);
//				response.sendRedirect("../article.jsp");	
			}else{
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/404.jsp");
				rd.forward(request, response);
//				response.sendRedirect("../404.html");	
			}
		}else{
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/404.jsp");
			rd.forward(request, response);
		}
	    
		
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
