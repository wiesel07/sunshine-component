package cn.sunshine.component.common.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import cn.sunshine.component.common.util.excel.ToHtml;

/**
 * <p>
 * TODO
 * </p>
 *
 * @author wuj
 * @since 2020年3月16日
 */
public class ExcelToHtmlUtil {

	  /**
	    * 
	    * <p>函数名称：        </p>
	    * <p>功能说明：
	    *
	    * </p>
	    *<p>参数说明：</p>
	    * @param sourceFile 源文件
	    * @param targetPath 目标文件路径
	    *
	    * @date   创建时间：2017年8月27日
	    * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	    */
	   public static void toHtml(String sourceFile,String targetPath){
			try {
				 createFile(new File(targetPath));
				 ToHtml toHtml = ToHtml.create(sourceFile, getPrintWriter(targetPath));
				 toHtml.setCompleteHTML(true);
				 toHtml.setOutpath(targetPath);
		         toHtml.printPage();
			} catch (IOException e) {
				throw new IllegalArgumentException("Excel转换异常", e);
			}
	   }
	   
	   /**
	    * 
	    * <p>函数名称：toHtml        </p>
	    * <p>功能说明：excel 转 html
	    *
	    * </p>
	    *<p>参数说明：</p>
	    * @param in 输入流
	    * @param targetPath 输出路径
	    *
	    * @date   创建时间：2017年8月27日
	    * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	    */
	   public static void toHtml(InputStream in, String targetPath){
		   try {
			   //创建文件
			   createFile(new File(targetPath));
			   ToHtml toHtml = ToHtml.create(in, getPrintWriter(targetPath));
			   toHtml.setCompleteHTML(true);
			   toHtml.setOutpath(targetPath);		   
		       toHtml.printPage();
		   } catch (IOException e) {
			    e.printStackTrace();
				throw new IllegalArgumentException("Excel转换异常", e);
		   }
	   }
	   
	   private static PrintWriter getPrintWriter(String fileName) throws UnsupportedEncodingException, FileNotFoundException{
		   return new PrintWriter(new OutputStreamWriter(new FileOutputStream(new File(fileName)), "utf-8"));
	   }
	   
	   private static void createFile(File file) throws IOException{
		   if (file.exists()) {
	           if (file.isDirectory()) {
	               throw new IOException("File '" + file + "' exists but is a directory");
	           }
	           if (file.canWrite() == false) {
	               throw new IOException("File '" + file + "' cannot be written to");
	           }
	       } else {
	           File parent = file.getParentFile();
	           if (parent != null) {
	               if (!parent.mkdirs() && !parent.isDirectory()) {
	                   throw new IOException("Directory '" + parent + "' could not be created");
	               }
	           }
	       }
	   }
}
