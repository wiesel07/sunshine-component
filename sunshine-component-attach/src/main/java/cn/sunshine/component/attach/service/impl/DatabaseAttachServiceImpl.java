package cn.sunshine.component.attach.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.hutool.core.util.IdUtil;
import cn.sunshine.component.attach.common.util.AtttachHelper;
import cn.sunshine.component.attach.entity.AttachFilestore;
import cn.sunshine.component.attach.enums.AttachError;
import cn.sunshine.component.attach.service.IAttachFilestoreService;
import cn.sunshine.component.common.util.IDUtils;
import cn.sunshine.component.common.util.IOUtilExt;
import cn.sunshine.component.common.util.StrUtilExt;

/**
 * <p>
 * TODO
 * </p>
 *
 * @author wuj
 * @since 2020年3月16日
 */
@Service(AtttachHelper.UPLOAD_TYPE_DATABASE)
public class DatabaseAttachServiceImpl extends AbstractAttachmentService{

	@Autowired
	private IAttachFilestoreService attachFilestoreService;

	@Override
	public String doUpload(String attachId, InputStream inputStream,
			String bizType, String suffix) {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			IOUtilExt.copy(inputStream, os);
			os.close();
		} catch (IOException e) {
			throw AttachError.ATTACH_FILE_UPLOAD_EXCEPTION.toException(e.getMessage());
		}
		IdUtil.createSnowflake(12L, 33L).nextId();
		String guidId = StrUtilExt.toString(IDUtils.newID());

		attachFilestoreService.saveFiles(guidId, os.toByteArray());
		return guidId;
	}

	@Override
	public boolean doDelete(String filePath, Long attachId) {
	
		return attachFilestoreService.removeById(attachId);
	}

	@Override
	public InputStream doDowload(String filePath, Long attachId) {
		
		AttachFilestore fileStore = attachFilestoreService.getById(attachId);
		if(fileStore == null){
		  return null;
		}
		return new ByteArrayInputStream(fileStore.getContent());
	}

	@Override
	public String doGetType() {
		
		return AtttachHelper.UPLOAD_TYPE_DATABASE;
	}

	@Override
	public String doGetPath() {
	
		return "";
	}
}
