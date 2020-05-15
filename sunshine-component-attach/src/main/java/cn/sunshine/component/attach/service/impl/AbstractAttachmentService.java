package cn.sunshine.component.attach.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

import cn.sunshine.component.attach.enums.AttachError;
import cn.sunshine.component.attach.service.IAttachmentService;
import cn.sunshine.component.common.exception.SunshineException;
import cn.sunshine.component.common.util.FileUtilExt;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * TODO
 * </p>
 *
 * @author wuj
 * @since 2020年3月16日
 */
@Slf4j
public abstract class AbstractAttachmentService implements IAttachmentService{

	@Override
	public String upload(String attachId, MultipartFile multipartFile, String bizType, String suffix)
			throws SunshineException {
		InputStream inputStream = null;
		try {
			inputStream = multipartFile.getInputStream();
		} catch (IOException e) {
			log.error("上传文件[" + multipartFile.getOriginalFilename() + "]异常。");
			throw AttachError.ATTACH_FILE_UPLOAD_EXCEPTION.toException(e.getMessage());
		}
		return this.upload(attachId, inputStream, bizType, suffix);
	}

	@Override
	public String upload(String attachId, InputStream inputStream, String bizType, String suffix)
			throws SunshineException {
		return doUpload(attachId, inputStream, bizType, suffix);
	}

	@Override
	public String upload(String attachId, File file, String bizType, String suffix) throws SunshineException {
		InputStream inputStream=	FileUtilExt.getInputStream(file);		
		return this.upload(attachId, inputStream, bizType, suffix);
	}

	@Override
	public boolean delete(String filePath, Long attachId) {
		return doDelete(filePath, attachId);
	}

	@Override
	public InputStream dowload(String filePath, Long attachId) {
		return this.doDowload(filePath, attachId);
	}

	@Override
	public String getType() {
	
		return null;
	}

	@Override
	public String getPath() {
		
		return null;
	}
	
	public abstract String doUpload(String attachId, InputStream inputStream,
			String bizType, String suffix);
	
	public abstract boolean doDelete(String filePath, Long attachId);
	
	public abstract InputStream doDowload(String filePath, Long attachId);
	
	public abstract String doGetType();
	
	public abstract String doGetPath();

}
