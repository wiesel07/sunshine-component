package cn.sunshine.component.attach.service;

import java.io.InputStream;

import com.baomidou.mybatisplus.extension.service.IService;

import cn.sunshine.component.attach.entity.AttachFile;
import cn.sunshine.component.runtime.web.entity.UserVo;

/**
 * <p>
 * 附件表 服务类
 * </p>
 *
 * @author wuj
 * @since 2020-03-15
 */
public interface IAttachFileService extends IService<AttachFile> {


	
	/**
	 * 保存文件
	 * @param fileInfo
	 * @param inputStream
	 *
	 */
	void saveAttachFile(AttachFile fileInfo, InputStream inputStream);


	/**
	 * 删除附件信息
	 * @param fileId
	 * @param user
	 *
	 */
	void delAttachFile(Long fileId, UserVo user);


	/**
	 *  下载文件
	 * @param id
	 * @return
	 *
	 */
	InputStream downloadFile(Long id);



	/**
	 * <p>
	 * 功能说明： 将备注 当做空附件 进行存储
	 * 
	 * @param fileInfo
	 * @param inputStream
	 */
	void saveRemarkAsAttachFile(String bizId, String bizType, String itemCode, String remark);

	/**
	 * <p>
	 * 功能说明： 删除此附件项已有的备注
	 * 
	 * @param fileInfo
	 * @param inputStream
	 */
	void deleteRemark(String bizId, String bizType, String itemCode);

	/**
	 * 
	 * 功能说明： 根据billId，bizType去获取已上传的附件数量
	 * 
	 * @param billId，bizType
	 * @return
	 */
	long queryAttachCounts(String billId, String bizType);

	/**
	 * 
	 * 功能说明： 根据billId，bizType，itemCode去获取已上传的附件数量
	 * 
	 * @param bizId，bizType，itemCode
	 * @return
	 */
	int queryAttachCounts(String bizId, String bizType, String ItemCode);

	/**
	 * 
	 * 功能说明： 根据billId，bizType进行附件必填项的校验
	 * 
	 * @param bizId，bizType
	 * @return
	 */
	void checkFileRequired(String bizId, String bizType);


}
