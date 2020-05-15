package cn.sunshine.component.attach.common.util;

import java.io.File;

import cn.sunshine.component.attach.service.IAttachmentService;
import lombok.experimental.UtilityClass;

/**
 * <p>
 * 附件帮助类
 * </p>
 *
 * @author wuj
 * @since 2020年3月15日
 */
@UtilityClass
public class AtttachHelper {

	public final String ATTACH_TABLE = "ATTACH_FILE";
	public final String UPLOAD_TYPE_LOCALHOST = "localHostAttachService";
	public final String UPLOAD_TYPE_DATABASE = "databaseAttachService";
	public final String UPLOAD_TYPE_FASTDFS = "fastDfsAttachService";

	// 预览时临时存放的目录 会定时清空
	public final String TEMP_FOLDER = "temp" + File.separator + "attachment" + File.separator;

	public IAttachmentService getAttachmentService(String type) {
//		if(AppContext.getAppContext().getBeanContext().containsBeanDefinition(type)) {
//			AttachmentService service = AppContext.getAppContext().lookup(type, AttachmentService.class);
//			if(service != null) {
//				return service;
//			}
//		}

		throw new IllegalArgumentException("未知的附件处理服务类: " + type);
	}

}
