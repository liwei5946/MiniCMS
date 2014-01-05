package cn.edu.hbcit.jsj.software.control;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import cn.edu.hbcit.jsj.software.bean.ConnBean;
import cn.edu.hbcit.jsj.software.util.PasswordEncodeBean;

/**
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2009</p>
 *
 * <p>Company: 河北工业职业技术学院</p>
 *
 * @author 作者 : liwei5946@gmail.com
 * @version 创建时间：May 15, 2009 12:01:32 AM
 */
public class UpdatePassword extends HttpServlet {
	protected final Logger log = Logger.getLogger(UpdatePassword.class.getName());
	/**
	 * Constructor of the object.
	 */
	public UpdatePassword() {
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
		HttpSession session = request.getSession(false);
		request.setCharacterEncoding("GB2312");
		String uname = request.getParameter("uname");
		String oldpsw = request.getParameter("oldpsw");
		String newpsw = request.getParameter("newpsw");
		String againpsw = request.getParameter("againpsw");
		PasswordEncodeBean md5 = new PasswordEncodeBean();
		boolean flag = false;
		boolean loginFlag = false;
		if(uname.equals("")||oldpsw.equals("")||newpsw.equals("")||againpsw.equals("")){
			log.error("密码修改失败!");
			session.setAttribute("msg", "密码修改失败，请确认各项不可为空！");
			response.sendRedirect("system/error.jsp");
		}else if(!newpsw.equals(againpsw)){
			log.error("密码修改失败!");
			session.setAttribute("msg", "密码修改失败，请确认两次密码一致！");
			response.sendRedirect("system/error.jsp");
		}else{
			String realPath = request.getRealPath("\\WEB-INF\\db\\hbcitsoftware.mdb");//Access数据库绝对路径
			ConnBean cb = new ConnBean();
		
			cb.getConn(realPath);
			loginFlag = cb.doLogin(uname, md5.MD5Encode(oldpsw));
			
			if(loginFlag){
				flag = cb.updatePassword(uname, md5.MD5Encode(newpsw));
				cb.close();
				
				if(flag==true){
					log.debug("密码修改成功!");
					response.sendRedirect("Logout");
				}else{
					log.error("密码修改失败!");
					session.setAttribute("msg", "密码修改失败，请确认操作是否正确！");
					response.sendRedirect("system/error.jsp");
				}
				
			}else{
				log.error("密码修改失败!");
				cb.close();
				session.setAttribute("msg", "密码修改失败，请确认旧密码是否正确！");
				response.sendRedirect("system/error.jsp");
			}
			
			
			
		}
		
		
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
