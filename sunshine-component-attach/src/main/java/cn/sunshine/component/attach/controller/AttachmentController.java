package cn.sunshine.component.attach.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import cn.hutool.core.date.DatePattern;
import cn.sunshine.component.attach.common.util.AtttachHelper;
import cn.sunshine.component.attach.controller.req.AttachRemarkReq;
import cn.sunshine.component.attach.controller.resp.AttachFileResp;
import cn.sunshine.component.attach.controller.resp.AttachItemResp;
import cn.sunshine.component.attach.entity.AttachFile;
import cn.sunshine.component.attach.entity.AttachItem;
import cn.sunshine.component.attach.enums.AttachError;
import cn.sunshine.component.attach.service.IAttachFileService;
import cn.sunshine.component.attach.service.IAttachItemService;
import cn.sunshine.component.common.api.CommonResp;
import cn.sunshine.component.common.util.BeanUtilExt;
import cn.sunshine.component.common.util.DateUtilExt;
import cn.sunshine.component.common.util.ExcelToHtmlUtil;
import cn.sunshine.component.common.util.FileUtilExt;
import cn.sunshine.component.common.util.IOUtilExt;
import cn.sunshine.component.common.util.StrUtilExt;
import cn.sunshine.component.common.util.WordToHtmlUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 附件统一处理前端控制器
 * </p>
 *
 * @author wuj
 * @since 2020年3月16日
 */

@Slf4j
@Api(tags = "附件统一处理接口")
@RequestMapping("/attachment")
@RestController
public class AttachmentController {

	@Autowired
	IAttachFileService attachFileService;
	
	@Autowired
	IAttachItemService attachItemService;

//	@Autowired
//	IAttachRemarkService remarkService;
	
	@Value("${static.resources.location}")
	String staticResourcesLocation;
	
	
	@GetMapping(value = "/getAttachFiles")
	@ApiOperation(value = "获取附件项以及已上传的附件", notes = "根据业务ID、业务类型获取附件项以及已上传的附件")
	public CommonResp<List<AttachItemResp>>  getAttachFiles(String bizId,String bizType) {
		log.info("获取附件模块(billId={},bizType={})", bizId,bizType);
		
		// 参数合法性校验
		if (StrUtilExt.isEmpty(bizId)) {
			throw AttachError.CHECK_PARAMS_VALID.toException("业务ID为空");
		}
		if (StrUtilExt.isEmpty(bizType)) {
			throw AttachError.CHECK_PARAMS_VALID.toException("业务类型为空");
		}
		
		// 根据bizType去获取附件项列表
		QueryWrapper<AttachItem> itemQw= new QueryWrapper<>();
		itemQw.eq(AttachItem.BIZ_TYPE, bizType);
		itemQw.orderByAsc(AttachItem.SORT_NO);
		List<AttachItem> itemList = attachItemService.list(itemQw);
		// 转换成返回对象
		List<AttachItemResp> attachItemRespList= new ArrayList<>();
		for(AttachItem attachItem : itemList) {
			AttachItemResp attachItemResp = new AttachItemResp();
			BeanUtilExt.copyProperties(attachItem, attachItemResp);
			attachItemRespList.add(attachItemResp);
		}
		
		
		//根据billId，bizType去获取已上传的附件列表
		QueryWrapper<AttachFile> fileQw= new QueryWrapper<>();
		fileQw.eq(AttachFile.BIZ_ID, bizId);
		fileQw.eq(AttachItem.BIZ_TYPE, bizType);
		List<AttachFile> fileList = attachFileService.list(fileQw);
		
		for(AttachItemResp attachItemResp : attachItemRespList) {
			List<AttachFileResp> attachFileList = attachItemResp.getAttachFileGridList();
			String remarkStr="";
			if(attachFileList==null) {
				attachFileList = new ArrayList<>();
			}
			for(AttachFile attachFile : fileList) {
				AttachFileResp attachFileResp = new AttachFileResp();
				if(attachItemResp.getItemCode().equals(attachFile.getItemCode())) {
					//判断附件是否只是一个附件项的备注
					if("0".equals(attachFile.getIsRemark())) {
						remarkStr=attachFile.getRemark();
						attachItemResp.setRemark(remarkStr);
					}else {
						BeanUtilExt.copyProperties(attachFile, attachFileResp);
						attachFileList.add(attachFileResp);
					}
				}
			}
			attachItemResp.setAttachFileGridList(attachFileList);
		}
		
		return new CommonResp<>(attachItemRespList);
	}
	
	@GetMapping(value = "/getAttachFileList")
	@ApiOperation(value = "根据业务ID、业务类型获取已上传附件列表")
	public List<AttachFile> getAttachFileList(String bizId,String bizType) {
		QueryWrapper<AttachFile> fileQw= new QueryWrapper<>();
		fileQw.eq(AttachFile.BIZ_ID, bizId);
		fileQw.eq(AttachItem.BIZ_TYPE, bizType);
		List<AttachFile> fileList = attachFileService.list(fileQw);
		return fileList;
	}
	
//	@GetMapping(value = "/getAttachCounts")
//	@ResponseBody
//	@ApiOperation(value = "根据单据ID业务类型", notes = "根据单据ID，业务类型获取附件数量")
//	public CommonResp<Map<String, Long>>  getAttachCounts(String[] billIds,String bizType) {
//		Map<String, Long> map =new HashMap<>();
//		for (String billId : billIds) {
//			//根据billId，bizType去获取已上传的附件列表
//			long num = attachFileService.queryAttachCounts(billId,bizType);
//			map.put(billId, num);
//        }
//		return new CommonResp<>(map);
//	}
	


	@PostMapping(value = "/upload")
	@ApiImplicitParam(
			name = "file[]", value = "文件", required = true,dataType = "MultipartFile",allowMultiple = true)
	@ApiOperation(value = "附件上传", notes = "附件上传")
	public CommonResp<AttachFile> upload(@RequestParam (value="file[]",required = true) MultipartFile file, 
			String billId, String bizType,String itemCode, HttpServletResponse response) throws IOException {
		if (file.isEmpty()) {
			throw AttachError.ATTACH_NOT_EMPTY.toException();
		}
		try {
			
			AttachFile fileInfo = new AttachFile();
			fileInfo.setAttachName(file.getOriginalFilename());
			fileInfo.setFileSize(file.getSize());
			fileInfo.setContentType(FileUtilExt.extName(file.getOriginalFilename()));
			fileInfo.setBizType(bizType);
			fileInfo.setBizId(billId);
			fileInfo.setItemCode(itemCode);
			attachFileService.saveAttachFile(fileInfo, file.getInputStream());
			response.setContentType("text/html");
//			response.getOutputStream().write(JsonUtilsExt.toJsonString(fileInfo).getBytes("utf-8"));
			return new CommonResp<>(fileInfo);
		} catch (Exception e) {
			log.error("上传文件异常", e);
			response.getOutputStream().write("上传失败！".getBytes());
		}
	     return null;
	}
	
	@PostMapping(value = "/insertRemark")
	@ResponseBody
	@ApiOperation(value = "附件备注说明", notes = "附件备注说明")
	public CommonResp<String> insertRemark(@Valid @RequestBody List<AttachRemarkReq> remarkList) {
		for(AttachRemarkReq req : remarkList) {

			try {
				//删除旧的备注
				attachFileService.deleteRemark(req.getBizId(),req.getBizType(),req.getItemCode());
				//插入新的备注
				attachFileService.saveRemarkAsAttachFile(req.getBizId(),req.getBizType(),req.getItemCode(),req.getRemark());
			} catch (Exception e) {
				log.error("上传文件异常", e);
				return new CommonResp<>("上传文件异常");
			}
		}
		return new CommonResp<>("上传成功");
	}

	@PostMapping(value = "/delete")
	@ApiOperation(value = "附件删除", notes = "附件删除")
	public CommonResp<String> delete(Long attachId) {
		if (attachId != null) {
			attachFileService.delAttachFile(attachId,null);
		}
		return new CommonResp<>("上传成功");
	}

	@GetMapping(value = "/download" ,produces = "application/octet-stream")
	@ResponseBody
	@ApiOperation(value = "附件下载", notes = "根据附件id进行文件下载")
	public void download(@RequestParam(value = "attachId")Long attachId, HttpServletResponse response) {
		AttachFile fileInfo = attachFileService.getById(attachId);
		if (fileInfo == null) {
			throw AttachError.ATTACH_NOT_EXISTS.toException();
		}
		String contentType = fileInfo.getContentType();
		if (StrUtilExt.isNotBlank(contentType)) {
			contentType = FileUtilExt.getMimeType(fileInfo.getAttachName());
		}
		if (StrUtilExt.isBlank(contentType)) {
			contentType = "application/octet-stream";
		}
		response.setContentType(contentType);
		response.setCharacterEncoding("UTF-8");
		OutputStream os = null;
		InputStream is = attachFileService.downloadFile(attachId);
		try {
			response.setHeader("Content-disposition",
					"attachment; filename=" + new String(fileInfo.getAttachName().getBytes("GBK"), "ISO-8859-1"));
			response.setHeader("Content-Length", Long.toString(fileInfo.getFileSize()));

			os = response.getOutputStream();
			IOUtilExt.copy(is, os);
		} catch (Exception e) {
			try {
				os.write("文件不存在！".getBytes());
				response.setContentType("text/html;charset=UTF-8");
				response.setHeader("Content-disposition", "attachment; filename="
						+ new String((fileInfo.getAttachName() + "_文件不存在").getBytes("GBK"), "ISO-8859-1"));
				response.setHeader("Content-Length", Integer.toString("文件不存在！".getBytes().length));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				response.flushBuffer();
				is.close();
				os.close();
			} catch (IOException e) {
			}
		}
	}

	/**
	 * 文件下载
	 * 
	 * @param bizId    业务id
	 * @param response 相应对象
	 * @throws
	 */
	@GetMapping(value = "/downloadAll")
	@ApiOperation(value = "附件全部下载", notes = "根据业务ID进行全部附件下载")
	public void downloadAll(String bizId, HttpServletResponse response) {
		QueryWrapper<AttachFile> qw =new QueryWrapper<>();
		qw.eq(AttachFile.BIZ_ID, bizId);
		List<AttachFile> fileInfos = attachFileService.list(qw);
		if (fileInfos.size() == 0)
			throw AttachError.ATTACH_FILE_NOT_EXISTS.toException();
		// 打包此组下面的附件信息 返回一个zip文件包
		response.setContentType(FileUtilExt.getMimeType("xx.zip"));
		response.setHeader("Content-disposition", "attachment; filename=all.zip");
		ZipArchiveOutputStream os = null;
		try {
			os = new ZipArchiveOutputStream(response.getOutputStream());
			for (AttachFile file : fileInfos) {
				InputStream is = attachFileService.downloadFile(file.getAttachId());
				// 当文件不存在是 返回的是null 是否需要保护 直接说明文件为null
				// 如 xxx.jpg 文件不存在.txt 文件内容为空
				if (is == null) {
					os.putArchiveEntry(new ZipArchiveEntry(file.getAttachName() + "_文件不存在"));
					os.closeArchiveEntry();
				} else {
					os.putArchiveEntry(new ZipArchiveEntry(file.getAttachName()));
					IOUtilExt.copy(is, os);
					os.closeArchiveEntry();
					is.close();
				}
			}
			os.flush();
			os.close();
		} catch (IOException e) {
			throw AttachError.ATTACH_DOWNLOAD_FAIL.toException();
		}
	}
	
	 /**
	  * 
	  * <p>函数名称：getAttachmentPreviewPath        </p>
	  * <p>功能说明：在线预览附件
	  *
	  * </p>
	  *<p>参数说明：</p>
	  * @param
	  * @return Map flag = 1,可在线预览，返回path 有值（可访问的临时文件url）
	  *             flag = 0,不能在线预览，前端直接下载
	  *             flag = -1,文件不存在
	  *             flag = 9,转换异常 也按下载处理
	  *
	  * @date   创建时间：2017年8月23日
	  * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	  */
	 @GetMapping(value = "/preview")
	 @ApiOperation(value = "在线预览附件", notes = "在线预览附件")
	 @ResponseBody
	 public CommonResp<Map<String,String>> getAttachmentPreviewPath(String attachId){
		 Map<String,String> result = new HashMap<String, String>();
	   	 result.put("flag", "0");
	   	 result.put("path", "");
	   	 result.put("id", attachId);    	 
	 	AttachFile fileInfo = attachFileService.getById(attachId);
         if(fileInfo == null){
	       	 //文件不存在
	       	 result.put("flag", "-1");
         } else {
	       	 //加载附件 到一个临时文件中 （定时动态删除）//        	         	 
	       	 //在支持类型中的，直接返回临时文件 否则直接返回id 前端下载
	       	 //获取类型
	       	 String contentType = fileInfo.getContentType();        	 
	       	 String tempFile = AtttachHelper.TEMP_FOLDER + DateUtilExt.format(new Date(), DatePattern.PURE_DATE_PATTERN) + File.separator + fileInfo.getAttachId() + "." + contentType;
	       	 String filePath = staticResourcesLocation  + File.separator  + tempFile;
	       	 // 生成临时文件 返回地址
			 InputStream input = attachFileService.downloadFile(fileInfo.getAttachId());
			 if(input == null){
				 result.put("flag", "-1");
				 return new CommonResp<>(result);
			 }
	       	 try{
					switch (contentType.toUpperCase()) {
					case "BMP":
					case "JPG":
					case "JPEG":
					case "PNG":
					case "GIF":
					case "PDF":
					case "HTML":
						//判断是否已经存在 存在直接返回了
						if(!FileUtilExt.exist(filePath)){
							FileUtilExt.writeFromStream(input,new File(filePath));
						}
				    	result.put("flag", "1");
						result.put("path", tempFile);
						break; 
				    //暂时关闭word在线预览功能
					case "DOC":
					case "DOCX":
						//保存目录 可能存在图片 故需要再创建一级目录
						tempFile = AtttachHelper.TEMP_FOLDER + DateUtilExt.format(new Date(), DatePattern.PURE_DATE_PATTERN) + File.separator  + fileInfo.getAttachId() +  fileInfo.getAttachId() + ".html";
			        	filePath = staticResourcesLocation  + File.separator  + tempFile;
			        	//判断是否已经存在 存在直接返回了
						if(!FileUtilExt.exist(filePath)){
						   WordToHtmlUtil.toHtml(input, filePath);
//						   WordToHtmlUtilExt.toPDF(input, filePath);
						}
				    	result.put("flag", "1");
						result.put("path", tempFile);
						break;
					case "XLS":
					case "XLSX":
	                    //转换成HTML 然后返回前端 暂时未实现
						//判断是否已经存在 存在直接返回了
						//替换后缀
						tempFile = StrUtilExt.replace(tempFile, "." + contentType, ".html");
						filePath = StrUtilExt.replace(filePath, "." + contentType, ".html");
						if(!FileUtilExt.exist(filePath)){
						   ExcelToHtmlUtil.toHtml(input, filePath);
						}
				    	result.put("flag", "1");
						result.put("path", tempFile);
					default:
						break;
					}
	   		 } catch(Exception ioException){
	   			 log.error("文件预览异常", ioException);
	           	 result.put("flag", "9");
	           	 result.put("path", "");            	 
	   			 result.put("msg", StrUtilExt.toString(ioException.getMessage(),"文件预览异常，请直接下载！"));
	   			 //删除文件
	   			FileUtilExt.del(new File(filePath));
	   		 }
        }
        //替换路径path
        result.put("path", StrUtilExt.replace(result.get("path").toString(), "\\", "/"));
		return new CommonResp<>(result);
	}
	 
	 
//	@GetMapping(value = "/getPreviewUrlList")
//	@ResponseBody
//	@ApiOperation(value = "获取卡片附件的预览地址列表", notes = "根据assetId,bizType获取卡片附件的预览地址列表")
//	public CommonResp<List<Map<String,String>>> getPreviewUrlList(String assetId,String bizType) {
//		//根据assetId，bizType去获取已上传的附件列表
//		List<AttachFile> fileList = attachFileService.queryAttachFiles(assetId,bizType);
//		List<Map<String,String>> urlList=new ArrayList<Map<String,String>>();
//		for(AttachFile attachFile:fileList) {
//			Map<String,String> result = new HashMap<String, String>();
//			String pathUrl="";
//			long attachId=attachFile.getAttachId();
//			AttachFile fileInfo = attachFileService.queryOneAttachFile(attachId);
//		    if(fileInfo != null){
//		    	String contentType = fileInfo.getContentType();        	 
//		       	String tempFile = AtttachHelper.TEMP_FOLDER + DateUtilsExt.format(new Date(), DatePattern.PURE_DATE_PATTERN) + File.separator + fileInfo.getAttachId() + "." + contentType;
//		       	String filePath = staticResourcesLocation  + File.separator  + tempFile;
//				InputStream input = attachFileService.downloadFile(fileInfo.getAttachId());
//				if(input != null){
//					try{
//						switch (contentType.toUpperCase()) {
//						case "BMP":
//						case "JPG":
//						case "JPEG":
//						case "PNG":
//						case "GIF":
//						case "PDF":
//						case "HTML":
//							if(!FileUtilsExt.exist(filePath)){
//							   FileUtils.copyInputStreamToFile(input,new File(filePath));
//							}
//							pathUrl=tempFile;
//							break; 
//						case "DOC":
//						case "DOCX":
//							tempFile = AtttachHelper.TEMP_FOLDER + DateUtilsExt.format(new Date(), DatePattern.PURE_DATE_PATTERN) + File.separator  + fileInfo.getAttachId() + File.separator + fileInfo.getAttachId() + ".html";
//				        	filePath = staticResourcesLocation  + File.separator  + tempFile;
//							if(!FileUtilsExt.exist(filePath)){
//							   WordToHtmlUtilExt.toHtml(input, filePath);
//							}
//							pathUrl=tempFile;
//							break;
//						case "XLS":
//						case "XLSX":
//							tempFile = StringUtilsExt.replace(tempFile, "." + contentType, ".html");
//							filePath = StringUtilsExt.replace(filePath, "." + contentType, ".html");
//							if(!FileUtilsExt.exist(filePath)){
//							   ExcelToHtmlUtilExt.toHtml(input, filePath);
//							}
//							pathUrl=tempFile;
//						default:
//							break;
//						}
//			   		 } catch(Exception ioException){
//			   			 log.error("文件预览异常", ioException);
//			   		 }
//				}
//            }
//		    result.put("attchId", fileInfo.getAttachId().toString());
//		    result.put("path", StringUtilsExt.replace(pathUrl, "\\", "/"));
//		    urlList.add(result);
//		}
//		return new CommonResp<>(urlList);
//	}


	@GetMapping(value = "/checkFileRequired")
	@ResponseBody
	@ApiOperation(value = "附件必填项的校验", notes = "根据业务ID，业务类型进行附件必填项的校验")
	public void checkFileRequired(String bizId,String bizType) {
		attachFileService.checkFileRequired(bizId,bizType);
	}
	
	
//	@GetMapping(value = "/printExeDownload" ,produces = "application/octet-stream")
//	@NoAuthLogin
//	@ResponseBody
//	@ApiOperation(value = "打印控件下载", notes = "打印控件下载")
//    public void excelDownload(HttpServletRequest request, HttpServletResponse response) {
//		response.setContentType("application/octet-stream");
//		response.setCharacterEncoding("GBK");
//		OutputStream os = null;
//		InputStream is =null;
//		try {
//			File file=new File("/opt/asset/asset-smart/bosssoft-assistant-v1.5.0.exe");
//			is = new FileInputStream(file);
//			response.setHeader("Content-disposition",
//					"attachment; filename=" + new String("bosssoft-assistant-v1.5.0.exe".getBytes("GBK"), "ISO-8859-1"));
//			os = response.getOutputStream();
//			IOUtilsExt.copy(is, os);
//		 } catch (Exception e) {
//		} finally {
//			try {
//				response.flushBuffer();
//				is.close();
//				os.close();
//			} catch (IOException e) {
//			}
//		}
//	    }
}
