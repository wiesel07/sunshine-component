package cn.sunshine.component.attach.service;

import com.baomidou.mybatisplus.extension.service.IService;

import cn.sunshine.component.attach.entity.AttachFilestore; 

 /**
 * <p>
 *  文件存储表 服务类
 * </p>
 *
 * @author wuj
 * @since 2020-03-15
 */
public interface IAttachFilestoreService extends IService<AttachFilestore> {

	  void saveFiles( String attachId, byte[] content);

} 
