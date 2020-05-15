package cn.sunshine.component.attach.service.impl;

import java.io.File;
import java.io.InputStream;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.sunshine.component.attach.common.util.AtttachHelper;
import cn.sunshine.component.common.util.FileUtilExt;
import cn.sunshine.component.common.util.IDUtils;

/**
 * <p>
 * TODO
 * </p>
 *
 * @author wuj
 * @since 2020年3月16日
 */
@Service(AtttachHelper.UPLOAD_TYPE_LOCALHOST)
public class LocalHostAttachServiceImpl extends AbstractAttachmentService{
	
	@Value("${attachment.dir.root}")
	private String rootDir;
	
    private static final String SEPARATOR = File.separator;
	
	private String directory(String bizType){
		// 根据类型获取目录 应用上下文/FileRecv/bizType/年/月/日
		String directory = bizType
				           + SEPARATOR + LocalDateTime.now().getYear() 
				           + SEPARATOR + LocalDateTime.now().getMonth().getValue()
		                   + SEPARATOR + LocalDateTime.now().getDayOfMonth();
		return directory + SEPARATOR;
	}
	
	private String getFileName(String bizType, String suffix){
		String directory = this.directory(bizType)
				           + IDUtils.new32UUID();
		return directory += "." + suffix;
		
	}

	@Override
	public String doUpload(String attachId, InputStream inputStream,
			String bizType, String suffix) {
		//文件路径
		String fileName = this.getFileName(bizType, suffix);
		FileUtilExt.writeFromStream(inputStream,
				new File(doGetPath() + SEPARATOR + fileName));
		return fileName;
	}

	@Override
	public boolean doDelete(String filePath, Long attachId) {
		File file = new File(this.doGetPath() + SEPARATOR + filePath);
		if(file.isFile()){
		   return FileUtilExt.del(file);
		}
		return false;
	}

	@Override
	public InputStream doDowload(String filePath, Long attachId) {
		File file = new File(doGetPath() + SEPARATOR + filePath);
		InputStream inputStream = null;
		inputStream =FileUtilExt.getInputStream(file);
		return inputStream;
	}

	@Override
	public String doGetType() {
		return AtttachHelper.UPLOAD_TYPE_LOCALHOST;
	}

	@Override
	public String doGetPath() {
		return rootDir + SEPARATOR + "FileRecv";
	}
	

}
