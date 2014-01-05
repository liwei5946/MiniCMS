package cn.edu.hbcit.jsj.software.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2009</p>
 *
 * <p>Company: 河北工业职业技术学院</p>
 *
 * @author 作者 : liwei5946@gmail.com
 * @version 创建时间：Jun 21, 2009 12:34:21 AM
 */
public class UtilTools {
	/**
	 * 判断字符串是否为整型
	 * @param str
	 * @return
	 */
	public boolean isNumeric(String str)
	{
	Pattern pattern = Pattern.compile("[0-9]*");
	Matcher isNum = pattern.matcher(str);
	if( !isNum.matches() ){
		return false;
	}
		return true;
	} 
	/*
	 * 在access数据库中，将Memo备注型字段里的数据完整取出
	 * @param InputStream
	 * @return StringBuilder
	 */
	public StringBuilder accessdbMemoToString(InputStream in)
	{
		BufferedReader reader=new BufferedReader(new InputStreamReader(in));
		StringBuilder sb=new StringBuilder();
		String line=null;
		try {
		while((line=reader.readLine())!=null){
			sb.append(line);
			}
		} catch (IOException e) {
		e.printStackTrace();
		  }
		return sb;
	}

}
