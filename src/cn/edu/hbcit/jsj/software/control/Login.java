package cn.edu.hbcit.jsj.software.control;

import java.io.IOException;
import java.io.PrintWriter;
import org.apache.log4j.*;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import cn.edu.hbcit.jsj.software.bean.*;
import cn.edu.hbcit.jsj.software.util.*;

/**
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2009</p>
 *
 * <p>Company: 河北工业职业技术学院</p>
 *
 * @author 作者 : liwei5946@gmail.com
 * @version 创建时间：May 11, 2009 11:02:28 PM
 */
public class Login extends HttpServlet {
	protected final Logger log = Logger.getLogger(Login.class.getName());

	/**
	 * Constructor of the object.
	 */
	public Login() {
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

	    request.setCharacterEncoding("utf-8");

	    HttpSession session = request.getSession(false);
	    
	    PasswordEncodeBean md5 = new PasswordEncodeBean();
	    VerifyCodeGenerator vcg = new VerifyCodeGenerator();
	    java.text.SimpleDateFormat formater = new java.text.SimpleDateFormat("yyyy年MM月dd日HH时mm分");
		String logintime = formater.format(new java.util.Date());
		
	    boolean verifyFlag = false;
	    boolean loginFlag = false;
	    boolean logFlag = false;
	    boolean stateFlag = false;
	    
	    String realPath = request.getRealPath("\\WEB-INF\\db\\hbcitsoftware.mdb");//Access数据库绝对路径
		String uname = request.getParameter("username");
		String psw = request.getParameter("password");
		String ip = this.getIpAddr(request);//获取用户IP
		verifyFlag = vcg.check(request);//对验证码进行验证
		
		ConnBean cb = new ConnBean();
		log.debug(realPath);
		log.debug(md5.MD5Encode(psw));
		
		cb.getConn(realPath);
		loginFlag = cb.doLogin(uname, md5.MD5Encode(psw));
		if(verifyFlag==true && loginFlag==true){
			stateFlag = true;
		}
		logFlag = cb.addLogs(uname, ip, logintime, String.valueOf(stateFlag));
		cb.close();
		
		if(session.getAttribute("msg") != null){
			session.removeAttribute("msg");
		}
		
		if(loginFlag == true && verifyFlag==true && logFlag==true){
		      session.setAttribute("USERNAME",uname);
		      log.debug("成功登录！");
		      response.sendRedirect("system/main.jsp");
		    }else if(loginFlag == false && verifyFlag==true && logFlag==true){
		    	log.debug("登录失败，请重试！");
		    	session.setAttribute("msg","用户名或密码错误造成登录失败，请重试！");
		    	response.sendRedirect("system/login.jsp");
		    }else if(loginFlag == true && verifyFlag==false && logFlag==true){
		    	log.debug("登录失败，请重试！");
		    	session.setAttribute("msg","验证码输入错误造成登录失败，请重试！");
		    	response.sendRedirect("system/login.jsp");
		    }else if(logFlag==false){
		    	log.debug("登录失败，请重试！");
		    	session.setAttribute("msg","日志信息写入失败，请检查用户权限及网络情况！");
		    	response.sendRedirect("system/login.jsp");
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
	
	/**
	 * 获得用户真实IP地址
	 * @param request
	 * @return String IP
	 */
	public String getIpAddr(HttpServletRequest request) { 
		String ip = request.getHeader("x-forwarded-for"); 
	    //String ip = request.getRemoteAddr(); 
	    log.debug(ip);
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	        ip = request.getHeader("Proxy-Client-IP"); 
	    } 
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	        ip = request.getHeader("WL-Proxy-Client-IP"); 
	    } 
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	        ip = request.getRemoteAddr(); 
	    } 
	    return ip; 
	}

}
