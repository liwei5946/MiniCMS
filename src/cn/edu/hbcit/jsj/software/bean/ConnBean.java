package cn.edu.hbcit.jsj.software.bean;

import java.sql.Clob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.sql.rowset.serial.SerialException;

import org.apache.log4j.Logger;

import cn.edu.hbcit.jsj.software.util.UtilTools;

public class ConnBean {
  private Connection conn; //连接对象
  private Statement stmt; //语句对象
  private PreparedStatement pStatement = null;
  private ResultSet rs; //结果集对象
  private String accessdriver = "sun.jdbc.odbc.JdbcOdbcDriver"; //保存Access驱动程序字符串
  private String accessURL =
      "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ="; //保存Access连接字符串
  protected final Logger log = Logger.getLogger(ConnBean.class.getName());
  private int pageSize = 20;  //默认每页显示的文章数  
  private int showPage = 1;   //默认当前页面显示的页号  
  private int rsCount = 0;   //ResultSet中的文章总数  
  private int pageCount = 0;  //分页后的总页数  

  public ConnBean() {
  }
/**
 * 数据库连接
 * @param dbpath
 * @return Connection
 */
  public Connection getConn(String dbpath) {
    try {
      accessURL = accessURL + dbpath;
      Class.forName(accessdriver);
      conn = DriverManager.getConnection(accessURL,"liwei","13933887882");
      log.debug(accessURL);
    }
    catch (Exception e) {
    	log.error("操作数据库出错，请仔细检查");
    	log.error(e.getMessage());
    }
    return conn;
  }
  
  /** 
   * 关闭数据库连接
   * 
   */
  public void close() {
    try {
      //rs.close();
      //stmt.close();
      conn.close();
    }
    catch (SQLException sqlexception) {
      sqlexception.printStackTrace();
    }
  }

  /**
   * 登录
   * @param userName
   * @param passWord
   * @return boolean
   */
  public boolean doLogin(String userName, String passWord){
    boolean flag = false;
    String un = "";
    String pw = "";
    try{
      stmt = conn.createStatement();
      rs = stmt.executeQuery("SELECT * FROM passport"); //passport为表名，和SQL一样
      while (rs.next()) {
        un = rs.getString("username");
        pw = rs.getString("password");//passport表中三个字段，主键是ID，自增的。username和password是文本类型。
        if(un.equals(userName) && pw.equals(passWord)){
          flag = true;
          break;
        }
      }
    }catch(Exception e){
    	log.error("操作数据库出错，请仔细检查");
    	log.error(e.getMessage());
    }
    return flag;
  }

  /**
   * 添加新闻
   * @param title
   * @param time
   * @param author
   * @param content
   * @param type
   * @param shorttime
   * @return boolean
   */
  public boolean addNews(String title, String time, String author, String content, int type, String shorttime){
	  boolean flag = false;
	  int state = 0;
	  Clob c = null;
	  try{
		  if(content==null){
			  content="";
		  }
		  c = new javax.sql.rowset.serial.SerialClob(content.toCharArray());
		  String SQL = "INSERT INTO article (title,publishtime,author,content,type,shorttime) VALUES (?,?,?,?,?,?)";
		  pStatement = conn.prepareStatement(SQL);
		  pStatement.setString(1, title);
		  pStatement.setString(2, time);
		  pStatement.setString(3, author);
		  java.io.Reader clobReader = new java.io.StringReader(content);
		  pStatement.setCharacterStream(4, clobReader, content.length());
		  //pStatement.setString(4, content);
		  pStatement.setInt(5, type);
		  pStatement.setString(6, shorttime);
		  state = pStatement.executeUpdate();
		  
		  if(state > 0){
			  flag = true;
		  }
		  
	  }catch(SQLException sqlE){
		  log.error(sqlE);
	  }
	  return flag;
  }
  /**
   * 为首页查询记录
   * @param int titleSize
   * @return ArrayList
   */
  public ArrayList selectNewsForHomepage(int titleSize){
	  ArrayList list = new ArrayList();
	  try{
		  String SQL = "SELECT TOP 7 id,title,shorttime,type FROM (SELECT TOP 7 id,title,shorttime,type FROM article ORDER BY shorttime DESC) ORDER BY id DESC";
		  pStatement = conn.prepareStatement(SQL);
		  rs = pStatement.executeQuery();
		  
		  while (rs.next()) {
		    	DBBean db = new DBBean();
		    	String title = "";
		        db.setNewsId(rs.getInt("id"));
		        
		        title = rs.getString("title");
		        if(title.length() > titleSize){
		        	db.setShortTitle(title.substring(0, titleSize)+"...");//获取指定长度的短标题
		        }else{
		        	db.setShortTitle(title);
		        }
		        
		        db.setTitle(title);//获取原长度标题
		        db.setShorttime(rs.getString("shorttime"));
		        db.setType(rs.getString("type"));
		        
		        list.add(db);
		        log.debug("查询成功！");
		      }
	  }catch(SQLException sqlE){
		  log.error(sqlE);
	  }
	  return list;
  }

/**
 * 内页信息查询
 * @param int titleSize 查询文件标题显示字数
 * @param int curPage 当前页码
 * @param int perPage 每页显示记录的数量
 * @return ArrayList
 */  
  public ArrayList selectNewsForInnerinfojsp(int titleSize, String cPage, int perPage){
	  ArrayList list = new ArrayList();
	  try{
		  //SELECT TOP 10 id,title,shorttime,type FROM article WHERE id NOT IN (SELECT TOP 10 id FROM article ORDER BY id DESC) ORDER BY id DESC
		  //SELECT TOP 5 id,title,shorttime,type FROM article WHERE (id >(SELECT MAX(id) FROM (SELECT TOP 5 id FROM article ORDER BY id)AS lee )) ORDER BY id DESC
		  int curPage = 1;
		  UtilTools ut = new UtilTools();
		  if(ut.isNumeric(cPage)){	//判断cPage是否是整型数据
			  curPage = Integer.parseInt(cPage);
			  log.debug("curPage: "+curPage);
		  }
		  if(curPage>0 && perPage>0){  //检验传递参数是否合法
			  //pageSize = perPage;			  
			  //showPage = curPage;
			  this.setPageSize(perPage);	//每页显示的文章数
			  this.setShowPage(curPage);	//当前页面显示的页号
			  log.debug("getPageSize(): "+this.getPageSize());
			  log.debug("getShowPage(): "+this.getShowPage());
		  }
		  String SQL = "";
		  if(this.getShowPage()==1){	//如果页码是1，则直接执行取前N条记录
			  log.debug("this.getPageSize()"+this.getPageSize());
			  SQL= "SELECT TOP "+String.valueOf(this.getPageSize())+" article.id,title,author,articletype.typename,shorttime,viewcount FROM article INNER JOIN articletype ON articletype.id=article.type ORDER BY article.id DESC";
			  pStatement = conn.prepareStatement(SQL);
			  //pStatement.setString(1, "20");
			  //pStatement.setInt(1, this.getPageSize());
			  rs = pStatement.executeQuery();
		  }else if(this.getShowPage()>1){	//如果页码大于1，则执行分页SQL算法
			  log.debug("this.getPageSize()"+this.getPageSize());
			  SQL= "SELECT TOP "+String.valueOf(this.getPageSize())+" article.id,title,author,articletype.typename,shorttime,viewcount FROM article INNER JOIN articletype ON articletype.id=article.type WHERE article.id NOT IN (SELECT TOP "+String.valueOf((this.getShowPage()-1)*this.getPageSize())+" article.id FROM article ORDER BY article.id DESC) ORDER BY article.id DESC";
			  pStatement = conn.prepareStatement(SQL);
			  //pStatement.setInt(1, this.getPageSize());
			  //pStatement.setInt(2, (this.getShowPage()-1)*this.getPageSize());
			  rs = pStatement.executeQuery();
		  }
		  //pStatement = conn.prepareStatement(SQL);

		  while (rs.next()) {
		    	DBBean db = new DBBean();
		    	String title = "";
		        db.setNewsId(rs.getInt("id"));
		        
		        title = rs.getString("title");
		        if(title.length() > titleSize){
		        	db.setShortTitle(title.substring(0, titleSize)+"...");//获取指定长度的短标题
		        }else{
		        	db.setShortTitle(title);
		        }
		        
		        db.setTitle(title);//获取原长度标题
		        db.setAuthor(rs.getString("author"));
		        db.setShorttime(rs.getString("shorttime"));
		        db.setTypeName(rs.getString("typename"));
		        db.setViewCount(rs.getInt("viewcount"));
		        //db.setType(rs.getString("type"));
		        
		        list.add(db);
		        log.debug("查询成功！");
		      }
	  }catch(SQLException sqlE){
		  log.error(sqlE);
	  }
	  return list;
  }
  
  /**
   * 内页信息查询（按照文章类型查找）
   * @param int titleSize 标题字数
   * @param String type 新闻类型
   * @param int curPage 当前页码
   * @param int perPage 每页显示记录的数量
   * @return ArrayList
   */  
    public ArrayList selectNewsForInnerobtainjsp(int titleSize, int type, String cPage, int perPage){
  	  ArrayList list = new ArrayList();
  	  //SELECT TOP 5 id,title,shorttime,type FROM article WHERE type='通知公告'AND id NOT IN( SELECT TOP 15 id FROM article WHERE type='通知公告' ORDER BY id DESC ) ORDER BY id DESC
  	  //原sql语句:
  	  //SELECT id,title,shorttime,type FROM article WHERE type=? ORDER BY shorttime DESC
  	  int curPage = 1;
	  UtilTools ut = new UtilTools();
	  if(ut.isNumeric(cPage)){	//判断cPage是否是整型数据
		  curPage = Integer.parseInt(cPage);
		  log.debug("curPage: "+curPage);
	  }
	  if(curPage>0 && perPage>0){  //检验传递参数是否合法
		  //pageSize = perPage;			  
		  //showPage = curPage;
		  this.setPageSize(perPage);//每页显示的文章数
		  this.setShowPage(curPage);//当前页面显示的页号
		  log.debug("getPageSize(): "+this.getPageSize());
		  log.debug("getShowPage(): "+this.getShowPage());
	  }
	  String SQL = "";
	  
  	  try{
  		if(this.getShowPage()==1){	//如果页码是1，则直接执行取前N条记录
  			SQL = "SELECT TOP "+String.valueOf(this.getPageSize())+" id,title,shorttime,type FROM article WHERE type=? ORDER BY id DESC";
    		pStatement = conn.prepareStatement(SQL);
    		pStatement.setInt(1, type);
    		rs = pStatement.executeQuery();
  		}else if(this.getShowPage()>1){	//如果页码大于1，则执行分页算法
  			SQL = "SELECT TOP "+String.valueOf(this.getPageSize())+" id,title,shorttime,type FROM article WHERE type=? AND id NOT IN( SELECT TOP "+String.valueOf((this.getShowPage()-1)*this.getPageSize())+" id FROM article WHERE type=? ORDER BY id DESC ) ORDER BY id DESC";
    		pStatement = conn.prepareStatement(SQL);
    		pStatement.setInt(1, type);
    		pStatement.setInt(2, type);
    		rs = pStatement.executeQuery();
  		}
  		  
  		  
  		  while (rs.next()) {
  		    	DBBean db = new DBBean();
  		    	String title = "";
  		        db.setNewsId(rs.getInt("id"));
  		        
  		        title = rs.getString("title");
  		        if(title.length() > titleSize){
  		        	db.setShortTitle(title.substring(0, titleSize)+"...");//获取指定长度的短标题
  		        }else{
  		        	db.setShortTitle(title);
  		        }
  		        
  		        db.setTitle(title);//获取原长度标题
  		        db.setShorttime(rs.getString("shorttime"));
  		        db.setType(rs.getString("type"));
  		        
  		        list.add(db);
  		        log.debug("查询成功！");
  		      }
  	  }catch(SQLException sqlE){
  		  log.error(sqlE);
  	  }
  	  return list;
    }
  
  /**
   * 查询文章正文
   * @param id 文章id
   * @return ArrayList
   */
  public ArrayList selectNewsContext(int id){
	  ArrayList list = new ArrayList();
	  //int articleId = Integer.parseInt(id);
	  Clob myClob;
	  try{
		  String SQL = "SELECT title,publishtime,author,content,articletype.typename as type FROM article INNER JOIN articletype ON article.type=articletype.id WHERE article.id = ? ";
		  pStatement = conn.prepareStatement(SQL);
		  pStatement.setInt(1, id);
		  rs = pStatement.executeQuery();
		  
		  while (rs.next()) {
		    	DBBean db = new DBBean();
		    	UtilTools ut = new UtilTools();
		        db.setTitle(rs.getString("title"));
		        db.setLongtime(rs.getString("publishtime"));
		        db.setAuthor(rs.getString("author"));
//		        myClob = rs.getClob("content");
//		        db.setContent(myClob.getSubString(1, (int) myClob.length()));
		        db.setContent(ut.accessdbMemoToString(rs.getBinaryStream("content")).toString());
		        db.setType(rs.getString("type"));
		        
		        list.add(db);
		        log.debug("查询成功！");
		      }
	  }catch(SQLException sqlE){
		  log.error(sqlE);
	  }
	  return list;
  }
  /**
   * 检索相册缩略图
   * @param count 检索缩略图数量
   * @param type 相册类型
   * @return
   */
  public ArrayList selectGalleryThumb(String count, int type){
	  ArrayList list = new ArrayList();
	  //int articleId = Integer.parseInt(id);
	  Clob myClob;
	  try{
		  String SQL = "SELECT TOP " + count + " title,thumb,imagepath FROM gallery INNER JOIN gallerytype ON gallery.type=gallerytype.id WHERE gallery.type = ? ORDER BY gallery.publishtime DESC";
		  pStatement = conn.prepareStatement(SQL);
		  pStatement.setInt(1, type);
		  rs = pStatement.executeQuery();
		  
		  while (rs.next()) {
		    	DBBean db = new DBBean();

		        db.setTitle(rs.getString("title"));
		        db.setThumb(rs.getString("thumb"));
		        db.setFilename(rs.getString("imagepath"));
		        
		        list.add(db);
		      }
	  }catch(SQLException sqlE){
		  log.error(sqlE);
	  }
	  return list;
  }
  
  /**
   * 删除文章
   * @param id
   * @return boolean
   */
  public boolean deleteNews(String id){
	  boolean flag = false;
	  int articleId = Integer.parseInt(id);
	  int state = 0;
	  try{
		  String SQL = "DELETE FROM article WHERE id=?";
		  pStatement = conn.prepareStatement(SQL);
		  pStatement.setInt(1, articleId);
		  state = pStatement.executeUpdate();
		  
		  if(state > 0){
			  flag = true;
		  }
		  
	  }catch(SQLException sqlE){
		  log.error(sqlE);
	  }
	  return flag;
  }
  /**
   * 更新密码
   * @param username
   * @param newPassword
   * @return
   */
  public boolean updatePassword(String username, String newPassword){
	  boolean flag = false;
	  int state = 0;
	  try{
		  String SQL = "UPDATE passport SET password=? WHERE username=?";
		  pStatement = conn.prepareStatement(SQL);
		  pStatement.setString(1, newPassword);
		  pStatement.setString(2, username);
		  state = pStatement.executeUpdate();
		  
		  if(state > 0){
			  flag = true;
		  }
	  }catch(SQLException sqlE){
		  log.error(sqlE);
	  }
	  return flag;
  }
  
  /**
   * 点击量+1
   * @param username
   * @param newPassword
   * @return
   */
  public boolean updateViewCount(int newsId){
	  boolean flag = false;
	  int state = 0;
	  try{
		  String SQL = "UPDATE article SET viewcount  = viewcount + 1 WHERE id = ?";
		  pStatement = conn.prepareStatement(SQL);
		  pStatement.setInt(1, newsId);
		  state = pStatement.executeUpdate();
		  
		  if(state > 0){
			  flag = true;
		  }
	  }catch(SQLException sqlE){
		  log.error(sqlE);
	  }
	  return flag;
  }
  /**
   * 添加登录日志
   * @param username
   * @param ip
   * @param logintime
   * @param loginstate
   * @return boolean
   */
  public boolean addLogs(String username, String ip, String logintime, String loginstate){
	  boolean flag = false;
	  int state = 0;
	  try{
		  String SQL = "INSERT INTO log (username,ip,logintime,state) VALUES (?,?,?,?)";
		  pStatement = conn.prepareStatement(SQL);
		  pStatement.setString(1, username);
		  pStatement.setString(2, ip);
		  pStatement.setString(3, logintime);
		  pStatement.setString(4, loginstate);
		  state = pStatement.executeUpdate();
		  
		  if(state > 0){
			  flag = true;
		  }
		  
	  }catch(SQLException sqlE){
		  log.error(sqlE);
	  }
	  return flag;
  }
  
  /**
   * 查找系统日志
   * @return ArrayList
   */
  public ArrayList selectLogs(){
	  ArrayList list = new ArrayList();
	  try{
		  String SQL = "SELECT TOP 50 id,username,ip,logintime,state FROM log ORDER BY id DESC";
		  pStatement = conn.prepareStatement(SQL);
		  rs = pStatement.executeQuery();
		  
		  while (rs.next()) {
		    	DBBean db = new DBBean();

		        db.setId(rs.getInt("id"));
		        db.setUsername(rs.getString("username"));
		        db.setIp(rs.getString("ip"));
		        db.setLongtime(rs.getString("logintime"));
		        db.setLoginState(rs.getString("state"));
		        
		        list.add(db);
		        log.debug("查询成功！");
		      }
	  }catch(SQLException sqlE){
		  log.error(sqlE);
	  }
	  return list;
  }
  
  /**
   * 将文件附件信息存入数据库
   * @param fileName
   * @param oldName
   * @param uploadTime
   * @param fileSize
   * @param uploader
   * @return boolean
   */
  public boolean addFileInfo2DB(String fileName, String oldName, String uploadTime, String fileSize, String uploader){
	  boolean flag = false;
	  int state = 0;
	  try{
		  String SQL = "INSERT INTO filemanage (filename,oldname,uploadtime,filesize,uploader) VALUES (?,?,?,?,?)";
		  pStatement = conn.prepareStatement(SQL);
		  pStatement.setString(1, fileName);
		  pStatement.setString(2, oldName);
		  pStatement.setString(3, uploadTime);
		  pStatement.setString(4, fileSize);
		  pStatement.setString(5, uploader);

		  state = pStatement.executeUpdate();
		  
		  if(state > 0){
			  flag = true;
		  }
		  
	  }catch(SQLException sqlE){
		  log.error(sqlE);
	  }
	  return flag;
  }
  
  /**
   * 将图片附件信息存入数据库
   * @param fileName
   * @param oldName
   * @param uploadTime
   * @param fileSize
   * @param uploader
   * @return boolean
   */
  public boolean addImgInfo2DB(String fileName, String oldName, String uploadTime, String fileSize, String uploader){
	  boolean flag = false;
	  int state = 0;
	  try{
		  String SQL = "INSERT INTO imagemanage (filename,oldname,uploadtime,filesize,uploader) VALUES (?,?,?,?,?)";
		  pStatement = conn.prepareStatement(SQL);
		  pStatement.setString(1, fileName);
		  pStatement.setString(2, oldName);
		  pStatement.setString(3, uploadTime);
		  pStatement.setString(4, fileSize);
		  pStatement.setString(5, uploader);

		  state = pStatement.executeUpdate();
		  
		  if(state > 0){
			  flag = true;
		  }
		  
	  }catch(SQLException sqlE){
		  log.error(sqlE);
	  }
	  return flag;
  }
  /**
   * 将相册图片信息存入数据库
   * @param title
   * @param publishtime
   * @param author
   * @param content
   * @param imagepath
   * @param thumb
   * @param type
   * @param shorttime
   * @return
   */
  public boolean addGallery2DB(String title, String publishtime, String author, String content, String imagepath, String thumb, int type, String shorttime){
	  boolean flag = false;
	  int state = 0;
	  try{
		  String SQL = "INSERT INTO gallery (title,publishtime,author,content,imagepath,thumb,type,shorttime) VALUES (?,?,?,?,?,?,?,?)";
		  pStatement = conn.prepareStatement(SQL);
		  pStatement.setString(1, title);
		  pStatement.setString(2, publishtime);
		  pStatement.setString(3, author);
		  //数据量大的字段，使用clob
		  java.io.Reader clobReader = new java.io.StringReader(content);
		  pStatement.setCharacterStream(4, clobReader, content.length());
		  pStatement.setString(5, imagepath);
		  pStatement.setString(6, thumb);
		  pStatement.setInt(7, type);
		  pStatement.setString(8, shorttime);

		  state = pStatement.executeUpdate();
		  
		  if(state > 0){
			  flag = true;
		  }
		  
	  }catch(SQLException sqlE){
		  log.error(sqlE);
	  }
	  return flag;
  }
  /**
   * 查询文件附件信息
   * @return ArrayList
   */
  public ArrayList selectFileInfo(){
  	  ArrayList list = new ArrayList();
  	  try{
  		  String SQL = "SELECT * FROM filemanage ORDER BY id DESC";
  		  pStatement = conn.prepareStatement(SQL);
  		  rs = pStatement.executeQuery();
  		  
  		  while (rs.next()) {
  		    	DBBean db = new DBBean();

  		    	db.setId(rs.getInt("id"));
  		        db.setFilename(rs.getString("filename"));
  		        db.setOldname(rs.getString("oldname"));
  		        db.setLongtime(rs.getString("uploadtime"));
  		        db.setFilesize(rs.getString("filesize"));
  		        db.setAuthor(rs.getString("uploader"));
  		        list.add(db);
  		        log.debug("查询成功！");
  		      }
  	  }catch(SQLException sqlE){
  		  log.error(sqlE);
  	  }
  	  return list;
    }
  
  /**
   * 删除文件附件信息
   * @param fileName
   * @return boolean
   */
  public boolean deleteFileInfo4DB(String fileName){
	  boolean flag = false;
	  int state = 0;
	  try{
		  String SQL = "DELETE FROM filemanage WHERE filename=?";
		  pStatement = conn.prepareStatement(SQL);
		  pStatement.setString(1, fileName);
		  state = pStatement.executeUpdate();
		  
		  if(state > 0){
			  flag = true;
		  }
		  
	  }catch(SQLException sqlE){
		  log.error(sqlE);
	  }
	  return flag;
  }
  
  /**
   * 查询图片附件信息
   * @return ArrayList
   */
  public ArrayList selectImagesInfo(){
  	  ArrayList list = new ArrayList();
  	  try{
  		  String SQL = "SELECT * FROM imagemanage ORDER BY id DESC";
  		  pStatement = conn.prepareStatement(SQL);
  		  rs = pStatement.executeQuery();
  		  
  		  while (rs.next()) {
  		    	DBBean db = new DBBean();

  		    	db.setId(rs.getInt("id"));
  		        db.setFilename(rs.getString("filename"));
  		        db.setOldname(rs.getString("oldname"));
  		        db.setLongtime(rs.getString("uploadtime"));
  		        db.setFilesize(rs.getString("filesize"));
  		        db.setAuthor(rs.getString("uploader"));
  		        list.add(db);
  		        log.debug("查询成功！");
  		      }
  	  }catch(SQLException sqlE){
  		  log.error(sqlE);
  	  }
  	  return list;
    }
  
  /**
   * 删除图片附件信息
   * @param fileName
   * @return boolean
   */
  public boolean deleteImagesInfo4DB(String fileName){
	  boolean flag = false;
	  int state = 0;
	  try{
		  String SQL = "DELETE FROM imagemanage WHERE filename=?";
		  pStatement = conn.prepareStatement(SQL);
		  pStatement.setString(1, fileName);
		  state = pStatement.executeUpdate();
		  
		  if(state > 0){
			  flag = true;
		  }
		  
	  }catch(SQLException sqlE){
		  log.error(sqlE);
	  }
	  return flag;
  }
  
  /**
   * 内页查询总记录条数、总页数计算
   * @return void
   */
  public void setInnerInfoPageCount(){
  	  int count = 0;
  	  try{
  		  String SQL = "SELECT id FROM article";
  		  pStatement = conn.prepareStatement(SQL);
  		  rs = pStatement.executeQuery();
  		  
  		  while (rs.next()) {
  		    	count++;
  		        log.debug("setInnerInfoPageCount()查询计数成功！");
  		      }
  	  }catch(SQLException sqlE){
  		  log.error(sqlE);
  	  }
  	  this.setRsCount(count);//设置总记录数
  	  if(this.getRsCount() % this.getPageSize() == 0){
  		  //根据总行数计算总页数
  		  this.setPageCount(this.getRsCount() / this.getPageSize());
  	  }else{
  		this.setPageCount(this.getRsCount() / this.getPageSize() + 1);
  	  }
    }
  
  /**
   * 内页查询(按栏目类型查询)总记录条数、总页数计算
   * @param int type
   * @return void
   */
  public void setInnerInfoForTypePageCount(int type){
  	  int count = 0;
  	  try{
  		  String SQL = "SELECT id FROM article WHERE type=?";
  		  pStatement = conn.prepareStatement(SQL);
  		  pStatement.setInt(1, type);
  		  rs = pStatement.executeQuery();
  		  
  		  while (rs.next()) {
  		    	count++;
  		        log.debug("setInnerInfoForTypePageCount()查询计数成功！");
  		      }
  	  }catch(SQLException sqlE){
  		  log.error(sqlE);
  	  }
  	  this.setRsCount(count);//设置总记录数
  	  if(this.getRsCount() == 0){//当没有记录时，设置当前页数为1
  		  this.setPageCount(1);
  	  }else if(this.getRsCount() % this.getPageSize() == 0){
  		  //根据总行数计算总页数
  		  this.setPageCount(this.getRsCount() / this.getPageSize());
  	  }else{
  		this.setPageCount(this.getRsCount() / this.getPageSize() + 1);
  	  }
    }
  
	public int getRsCount() {
		return rsCount;
	}
	public void setRsCount(int rsCount) {
		this.rsCount = rsCount;
	}
	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getShowPage() {
		return showPage;
	}
	public void setShowPage(int showPage) {
		this.showPage = showPage;
	}
  
	//以下：2014改版时添加
	  /**
	   * 查询新闻类型
	   * @return ArrayList
	   */
	  public ArrayList selectNewsType(){
		  ArrayList list = new ArrayList();
		  try{
			  String SQL = "SELECT id,typename FROM articletype";
			  pStatement = conn.prepareStatement(SQL);
			  rs = pStatement.executeQuery();
			  
			  while (rs.next()) {
			    	DBBean db = new DBBean();
			    	db.setId(rs.getInt("id"));
			        db.setTypeName(rs.getString("typename"));
			        
			        list.add(db);
			        log.debug("查询成功！");
			      }
		  }catch(SQLException sqlE){
			  log.error(sqlE);
		  }
		  return list;
	  }
	  
	  /**
	   * 查询相册类型
	   * @return ArrayList
	   */
	  public ArrayList selectGalleryType(){
		  ArrayList list = new ArrayList();
		  try{
			  String SQL = "SELECT id,typename FROM gallerytype";
			  pStatement = conn.prepareStatement(SQL);
			  rs = pStatement.executeQuery();
			  
			  while (rs.next()) {
			    	DBBean db = new DBBean();
			    	db.setId(rs.getInt("id"));
			        db.setTypeName(rs.getString("typename"));
			        
			        list.add(db);
			        log.debug("查询成功！");
			      }
		  }catch(SQLException sqlE){
			  log.error(sqlE);
		  }
		  return list;
	  }
	  
	  /**
	   * 相册列表查询
	   * @param String type 新闻类型
	   * @param int curPage 当前页码
	   * @param int perPage 每页显示记录的数量
	   * @return ArrayList
	   */  
	    public ArrayList selectGalleryForInnerobtainjsp(int type, String cPage, int perPage){
	  	  ArrayList list = new ArrayList();
	  	  //SELECT TOP 5 id,title,shorttime,type FROM article WHERE type='通知公告'AND id NOT IN( SELECT TOP 15 id FROM article WHERE type='通知公告' ORDER BY id DESC ) ORDER BY id DESC
	  	  //原sql语句:
	  	  //SELECT id,title,shorttime,type FROM article WHERE type=? ORDER BY shorttime DESC
	  	  int curPage = 1;
		  UtilTools ut = new UtilTools();
		  if(ut.isNumeric(cPage)){	//判断cPage是否是整型数据
			  curPage = Integer.parseInt(cPage);
			  log.debug("curPage: "+curPage);
		  }
		  if(curPage>0 && perPage>0){  //检验传递参数是否合法
			  //pageSize = perPage;			  
			  //showPage = curPage;
			  this.setPageSize(perPage);//每页显示的文章数
			  this.setShowPage(curPage);//当前页面显示的页号
			  log.debug("getPageSize(): "+this.getPageSize());
			  log.debug("getShowPage(): "+this.getShowPage());
		  }
		  String SQL = "";
		  
	  	  try{
	  		if(this.getShowPage()==1){	//如果页码是1，则直接执行取前N条记录
	  			SQL = "SELECT TOP "+String.valueOf(this.getPageSize())+" id,title,content,imagepath FROM gallery WHERE type=? ORDER BY publishtime DESC";
	    		pStatement = conn.prepareStatement(SQL);
	    		pStatement.setInt(1, type);
	    		rs = pStatement.executeQuery();
	  		}else if(this.getShowPage()>1){	//如果页码大于1，则执行分页算法
//	  			SQL = "SELECT TOP "+String.valueOf(this.getPageSize())+" id,title,shorttime,type FROM article WHERE type=? AND id NOT IN( SELECT TOP "+String.valueOf((this.getShowPage()-1)*this.getPageSize())+" id FROM article WHERE type=? ORDER BY id DESC ) ORDER BY id DESC";
	  			SQL = "SELECT TOP "+String.valueOf(this.getPageSize())+" id,title,content,imagepath FROM gallery WHERE type=? AND id NOT IN( SELECT TOP "+String.valueOf((this.getShowPage()-1)*this.getPageSize())+" id FROM gallery WHERE type=? ORDER BY publishtime DESC ) ORDER BY publishtime DESC";
	    		pStatement = conn.prepareStatement(SQL);
	    		pStatement.setInt(1, type);
	    		pStatement.setInt(2, type);
	    		rs = pStatement.executeQuery();
	  		}
	  		  
	  		  
	  		  while (rs.next()) {
	  		    	DBBean db = new DBBean();
	  		        db.setNewsId(rs.getInt("id"));
	  		        db.setTitle(rs.getString("title"));
	  		        db.setContent(rs.getString("content"));
	  		        db.setFilename(rs.getString("imagepath"));
	  		        
	  		        list.add(db);
	  		      }
	  	  }catch(SQLException sqlE){
	  		  log.error(sqlE);
	  	  }
	  	  return list;
	    }
	    
	    /**
	     * 根据新闻类型编号，查询类型名称
	     * @param typeid
	     * @return
	     */
	  	public String selectNewsTypeByTypeId(int typeid) {
	  		String typeName = "";
	  		try {
	  			String SQL = "SELECT typename FROM articletype WHERE id = ? ";
	  			pStatement = conn.prepareStatement(SQL);
	  			pStatement.setInt(1, typeid);
	  			rs = pStatement.executeQuery();

	  			while (rs.next()) {
	  				typeName = rs.getString("typename");
	  				// log.debug("查询成功！");
	  			}
	  		} catch (SQLException sqlE) {
	  			log.error(sqlE);
	  		}
	  		return typeName;
	  	}
	  	
	    /**
	     * 根据相册类型编号，查询类型名称
	     * @param typeid
	     * @return
	     */
	  	public String selectGalleryTypeByTypeId(int typeid) {
	  		String typeName = "";
	  		try {
	  			String SQL = "SELECT typename FROM gallerytype WHERE id = ? ";
	  			pStatement = conn.prepareStatement(SQL);
	  			pStatement.setInt(1, typeid);
	  			rs = pStatement.executeQuery();

	  			while (rs.next()) {
	  				typeName = rs.getString("typename");
	  				// log.debug("查询成功！");
	  			}
	  		} catch (SQLException sqlE) {
	  			log.error(sqlE);
	  		}
	  		return typeName;
	  	}
	  	
	    /**
	     * 内页查询(按栏目类型查询)总记录条数、总页数计算
	     * @param int type
	     * @return void
	     */
	    public void setInnerInfoForTypePageCountByGallery(int type){
	    	  int count = 0;
	    	  try{
	    		  String SQL = "SELECT id FROM gallery WHERE type=?";
	    		  pStatement = conn.prepareStatement(SQL);
	    		  pStatement.setInt(1, type);
	    		  rs = pStatement.executeQuery();
	    		  
	    		  while (rs.next()) {
	    		    	count++;
//	    		        log.debug("setInnerInfoForTypePageCount()查询计数成功！");
	    		      }
	    	  }catch(SQLException sqlE){
	    		  log.error(sqlE);
	    	  }
	    	  this.setRsCount(count);//设置总记录数
	    	  if(this.getRsCount() == 0){//当没有记录时，设置当前页数为1
	    		  this.setPageCount(1);
	    	  }else if(this.getRsCount() % this.getPageSize() == 0){
	    		  //根据总行数计算总页数
	    		  this.setPageCount(this.getRsCount() / this.getPageSize());
	    	  }else{
	    		this.setPageCount(this.getRsCount() / this.getPageSize() + 1);
	    	  }
	      }
}
