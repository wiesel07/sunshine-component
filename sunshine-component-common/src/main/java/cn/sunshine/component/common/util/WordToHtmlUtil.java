package cn.sunshine.component.common.util;

import java.io.InputStream;

import com.aspose.words.Document;
import com.aspose.words.FontSettings;
import com.aspose.words.HtmlSaveOptions;
import com.aspose.words.License;
import com.aspose.words.PdfSaveOptions;

/**
 * <p>
 * TODO
 * </p>
 *
 * @author wuj
 * @since 2020年3月16日
 */
public class WordToHtmlUtil {

	 public static void toHtml(String fileName, String targetFileName){
		  try {
			setLicense();
			Document doc = new Document(fileName);
			HtmlSaveOptions options = new HtmlSaveOptions();
			doc.save(targetFileName, options);
	      } catch(Exception e){
	    	  throw new IllegalArgumentException("Word转换异常", e);
	      }
	  }
	  
	  /**
	   * 
	   * <p>函数名称：   toHtml     </p>
	   * <p>功能说明： word 转 html
	   *
	   * </p>
	   *<p>参数说明：</p>
	   * @param input
	   * @param targetFileName
	   *
	   * @date   创建时间：2017年8月28日
	   * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	   */
	  public static void toHtml(InputStream input, String targetFileName){
		  try {
			    setLicense();
				Document doc = new Document(input);
				HtmlSaveOptions options = new HtmlSaveOptions();
				
				doc.save(targetFileName, options);
		      } catch(Exception e){
		    	  throw new IllegalArgumentException("Word转换异常", e);
		      }
	  }
	  
	  public static void toPDF(InputStream input, String targetFileName) {
		  try {
			  setLicense();
			  Document doc = new Document(input);
			  PdfSaveOptions options = new PdfSaveOptions();
			  doc.save(targetFileName, options);
		  } catch(Exception e){
	    	  throw new IllegalArgumentException("Word转换异常", e);
	      }
	  }
	  
	  public static void toPDF(String fileName, String targetFileName){
		  try {
			setLicense();
			Document doc = new Document(fileName);
			PdfSaveOptions options = new PdfSaveOptions();
			doc.save(targetFileName, options);
	      } catch(Exception e){
	    	  throw new IllegalArgumentException("Word转换异常", e);
	      }
	  }
	  
	  private static void setLicense() throws Exception{
		  License aposeLic = new License();
		  aposeLic.setLicense(WordToHtmlUtil.class.getClassLoader().getResourceAsStream("aposelicense.xml"));
		  if (System.getProperty("os.name").toLowerCase().indexOf("windows") == -1		
				  && System.getProperty("os.name").toLowerCase().indexOf("mac") == -1) {
			  // 在linux下加载字体库				
			  FontSettings.setFontsFolder("//usr//share//fonts", true);
		  }

	  }
	  
	  public static void main(String[] args) throws Exception {
		  //加载licence
		  setLicense();
		  String dataDir = "E:\\我的任务\\厦门资产\\";
		  // Load the document. 
//	      Document doc = new Document(dataDir + "系统界面统一规范.doc");
	//
//	      HtmlSaveOptions options = new HtmlSaveOptions();
	//
//	      //HtmlSaveOptions.ExportRoundtripInformation property specifies 
//	      //whether to write the roundtrip information when saving to HTML, MHTML or EPUB. 
//	      //Default value is true for HTML and false for MHTML and EPUB. 
//	      options.setExportRoundtripInformation(true);
//	      doc.save(dataDir + "ExportRoundtripInformation_out_1.html", options);
	//
//	      doc = new Document(dataDir + "ExportRoundtripInformation_out_1.html");

	      //Save the document Docx file format 
//	      doc.save(dataDir + "TestFile_out_1.pdf", SaveFormat.PDF);
	      toPDF(dataDir + "技术部分.doc", dataDir + "TestFile_out_1.pdf");
			//ExEnd:ConvertDocumentToHtmlWithRoundtrip 
	      System.out.println("Document converted to html with roundtrip informations successfully.");
	  } 
}
