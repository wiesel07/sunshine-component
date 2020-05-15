package cn.sunshine.component.attach.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.sunshine.component.attach.common.util.AtttachHelper;
import cn.sunshine.component.attach.entity.AttachBiz;
import cn.sunshine.component.attach.entity.AttachFile;
import cn.sunshine.component.attach.entity.AttachItem;
import cn.sunshine.component.attach.enums.AttachError;
import cn.sunshine.component.attach.mapper.AttachFileMapper;
import cn.sunshine.component.attach.service.IAttachBizService;
import cn.sunshine.component.attach.service.IAttachFileService;
import cn.sunshine.component.attach.service.IAttachItemService;
import cn.sunshine.component.attach.service.IAttachmentService;
import cn.sunshine.component.common.enums.StattusEnum;
import cn.sunshine.component.common.util.NumberUtilExt;
import cn.sunshine.component.common.util.StrUtilExt;
import cn.sunshine.component.runtime.spring.RuntimeApplicationContext;
import cn.sunshine.component.runtime.web.entity.UserVo;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 附件表 服务实现类
 * </p>
 *
 * @author wuj
 * @since 2020-03-15
 */
@Slf4j
@Service
public class AttachFileServiceImpl extends ServiceImpl<AttachFileMapper, AttachFile> implements IAttachFileService {

	@Autowired
	private IAttachBizService attachBizService;

	@Autowired
	private IAttachItemService attachItemService;

	@Value("${defaultAttachService}")
	private String attachmentServiceName = "localHostAttachService";

	@Override
	public void saveAttachFile(AttachFile fileInfo, InputStream inputStream) {

		// 根据业务类型获取业务附件关系对象
		QueryWrapper<AttachBiz> attachBizQw = new QueryWrapper<AttachBiz>();
		attachBizQw.eq(AttachBiz.BIZ_TYPE, fileInfo.getBizType());
		AttachBiz attachBiz = attachBizService.getOne(attachBizQw);
		
		// 默认的附件信息表
		String targetTable = AtttachHelper.ATTACH_TABLE;
		String uploadType = StrUtilExt.isEmpty(attachmentServiceName, AtttachHelper.UPLOAD_TYPE_LOCALHOST);
		if (attachBiz != null) {
			String tbl = attachBiz.getAttachTbl();
			String type = attachBiz.getUploadType();
			if (StrUtilExt.isNotBlank(tbl)) {
				targetTable = tbl;
			}
			if (StrUtilExt.isNotBlank(type)) {
				uploadType = type;
			}
		}
		if (fileInfo.getFileSize() == 0) {
			try {
				fileInfo.setFileSize(NumberUtilExt.toLong(inputStream.available() + ""));
			} catch (IOException e) {
			}
		}
		// 文件上传
		String filePath = AtttachHelper.getAttachmentService(uploadType).upload(StrUtilExt.toString(fileInfo.getAttachId()),
				inputStream, fileInfo.getBizType(), fileInfo.getContentType());
		// 新增
		fileInfo.setUploadType(uploadType);
		fileInfo.setFilePath(filePath);
		if (AtttachHelper.ATTACH_TABLE.equals(targetTable)) {
			this.baseMapper.insert(fileInfo);
		} else {
			fileInfo.setDelRemark("");
			this.baseMapper.insertAttachFileInfo(fileInfo, targetTable);
		}

	}
	@Override
	@Transactional
	public void delAttachFile(Long fileId, UserVo user) {
		AttachFile attachFile = this.baseMapper.selectById(fileId);
		if(attachFile == null) {
			return ;
		}
		//获取业务表信息
		QueryWrapper<AttachBiz> attachBizQw = new QueryWrapper<AttachBiz>();
		attachBizQw.eq(AttachBiz.BIZ_TYPE, attachFile.getBizType());
		AttachBiz attachBiz = attachBizService.getOne(attachBizQw);
		
		//默认的附件信息表
		String targetTable = AtttachHelper.ATTACH_TABLE;
		if(attachBiz != null){
			String tbl = attachBiz.getAttachTbl();
			if(StrUtilExt.isNotBlank(tbl)){
				targetTable = tbl;
			}			
		}
		// 当前执行物理删除，后续考虑更改为逻辑删除，补充删除操作人、IP等具体信息
		if(AtttachHelper.ATTACH_TABLE.equals(targetTable)){
			this.baseMapper.deleteById(attachFile.getAttachId());
		} else {
			this.baseMapper.deleteAttachFileInfo(attachFile.getAttachId(), targetTable);
		}		
	}
	


	@Override
	public InputStream downloadFile(Long attachId) {
		AttachFile attachFile = this.baseMapper.selectById(attachId);
		if(attachFile != null) {
			String uploadType = AtttachHelper.UPLOAD_TYPE_LOCALHOST;
			if(StrUtilExt.isNotBlank(attachFile.getUploadType())){
				uploadType = attachFile.getUploadType();
			}
	        String pathFile = attachFile.getFilePath();
	        IAttachmentService   attachmentService= RuntimeApplicationContext.getBean(uploadType);
	        return attachmentService.dowload(pathFile, attachId);
		}
		log.warn("未找到对应id[{}]的附件信息。", attachId);
		return null;
	}


	@Override
	public void saveRemarkAsAttachFile(String bizId, String bizType, String itemCode, String remark) {
		AttachFile fileInfo = new AttachFile();
		fileInfo.setBizType(bizType);
		fileInfo.setBizId(bizId);
		fileInfo.setRemark(remark);
		fileInfo.setItemCode(itemCode);
		fileInfo.setIsRemark("0");
		this.baseMapper.insert(fileInfo);
	}

	@Override
	public void deleteRemark(String bizId, String bizType, String itemCode) {
		QueryWrapper<AttachFile> qryWrapper = new QueryWrapper<>();
		qryWrapper.eq(AttachFile.BIZ_ID, bizId);
		qryWrapper.eq(AttachFile.BIZ_TYPE, bizType);
		qryWrapper.eq(AttachFile.ITEM_CODE, itemCode);
		qryWrapper.eq(AttachFile.IS_REMARK, "0");
		this.baseMapper.delete(qryWrapper);

	}

	@Override
	public long queryAttachCounts(String bizId, String bizType) {
		QueryWrapper<AttachFile> qw = new QueryWrapper<>();
		qw.eq(AttachFile.BIZ_ID, bizId);
		qw.eq(AttachFile.BIZ_TYPE, bizType);
		return this.baseMapper.selectCount(qw);
	}

	@Override
	public int queryAttachCounts(String bizId, String bizType, String ItemCode) {
		QueryWrapper<AttachFile> qw = new QueryWrapper<>();
		qw.eq(AttachFile.BIZ_ID, bizId);
		qw.eq(AttachFile.BIZ_TYPE, bizType);
		qw.eq(AttachFile.ITEM_CODE, ItemCode);
		return this.baseMapper.selectCount(qw);
	}
	
	

	@Override
	public void checkFileRequired(String billId, String bizType) {
		boolean flag=true;
		String msg="";
		
		// 根据bizType去获取业务的必填项附件项列表
		QueryWrapper<AttachItem> itemQw = new QueryWrapper<>();
		itemQw.eq(AttachItem.BIZ_TYPE, bizType);
		itemQw.eq(AttachItem.ITEM_TYPE,"-");
		itemQw.eq(AttachItem.STATUS, StattusEnum.ENABLED.getValue());
		itemQw.eq(AttachItem.REQUIRED, 1);
		itemQw.orderByDesc(AttachItem.SORT_NO);
		List<AttachItem> attachItems =attachItemService.list(itemQw);
		
		for(AttachItem attachItem : attachItems) {
			//根据billId,bizType,itemCode去获取已上传的附件数量
			QueryWrapper<AttachFile> fileQw = new QueryWrapper<>();
			fileQw.eq(AttachFile.BIZ_ID, billId);
			fileQw.eq(AttachFile.BIZ_TYPE, bizType);
			fileQw.eq(AttachFile.ITEM_CODE, attachItem.getItemCode());
			int cnt =this.baseMapper.selectCount(fileQw);
			if(cnt==0) {
				flag=false;
				msg+=attachItem.getItemName()+";";
			}
		}
		if(!flag) {
			throw AttachError.ATTACH_ITEM_NOT_UPLOAD.toException(msg);
		}

	}


}
