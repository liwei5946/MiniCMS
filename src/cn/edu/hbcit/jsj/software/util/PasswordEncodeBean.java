/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cn.edu.hbcit.jsj.software.util;
import java.security.MessageDigest;
/**
 * <p>Description: 密码加密类</p>
 *
 * <p>Copyright: Copyright (c) 2009</p>
 *
 * <p>Company: 河北工业职业技术学院</p>
 *
 * @author 作者 : liwei5946@gmail.com
 * @version 创建时间：Feb 6, 2009 9:20:41 PM
 */
public class PasswordEncodeBean {
  private final static String[] hexDigits = {
        "0", "1", "2", "3", "4", "5", "6", "7",
        "8", "9", "a", "b", "c", "d", "e", "f"};

    /**
     *
     * @param
     * @return 16
     */
    public static String byteArrayToHexString(byte[] b) {
      StringBuffer resultSb = new StringBuffer();
      for (int i = 0; i < b.length; i++) {
        resultSb.append(byteToHexString(b[i]));
      }
      return resultSb.toString();
    }

    private static String byteToHexString(byte b) {
      int n = b;
      if (n < 0)
        n = 256 + n;
      int d1 = n / 16;
      int d2 = n % 16;
      return hexDigits[d1] + hexDigits[d2];
    }
/**
 * 姝ｅ�MD5����规�
 * @param origin
 * @return
 */
    public static String MD5Encode(String origin) {
      String resultString = null;
      try {
        resultString=new String(origin);
        MessageDigest md = MessageDigest.getInstance("MD5");
        resultString=byteArrayToHexString(md.digest(resultString.getBytes()));
      }
      catch (Exception ex) {
      }
      return resultString;
    }

    /**
     * 浣跨�寮��杩��绠�����瀵�����
     * @return <code>String[]</code> ������绗�覆
     * @author Administrator
     * @since 1.0 2005/11/28
     */
    public static String setEncrypt(String str){
        String sn="ziyu"; //瀵��
        int[] snNum=new int[str.length()];
        String result="";
        String temp="";

        for(int i=0,j=0;i<str.length();i++,j++){
            if(j==sn.length())
                j=0;
            snNum[i]=str.charAt(i)^sn.charAt(j);
        }

        for(int k=0;k<str.length();k++){

            if(snNum[k]<10){
                temp="00"+snNum[k];
            }else{
                if(snNum[k]<100){
                    temp="0"+snNum[k];
                }
            }
            result+=temp;
        }
        return result;
    }

    /**
     * 瀵��瑙ｅ�,�界��ㄤ����
     * @return <code>String[]</code> ������绗�覆
     * @author Administrator
     * @since 1.0 2005/11/28
     */
    public static String getEncrypt(String str){
        String sn="ziyu"; //瀵��
        char[] snNum=new char[str.length()/3];
        String result="";

        for(int i=0,j=0;i<str.length()/3;i++,j++){
            if(j==sn.length())
                j=0;
            int n=Integer.parseInt(str.substring(i*3,i*3+3));
            snNum[i]=(char)((char)n^sn.charAt(j));
        }

        for(int k=0;k<str.length()/3;k++){
            result+=snNum[k];
        }
        return result;
    }

}
