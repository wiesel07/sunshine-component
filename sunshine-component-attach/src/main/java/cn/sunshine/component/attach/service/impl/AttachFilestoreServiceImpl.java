package cn.sunshine.component.attach.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.sunshine.component.attach.entity.AttachFilestore;
import cn.sunshine.component.attach.mapper.AttachFilestoreMapper;
import cn.sunshine.component.attach.service.IAttachFilestoreService;

 /**
 * <p>
 *  文件存储表 服务实现类
 * </p>
 *
 * @author wuj
 * @since 2020-03-15
 */
@Service
public class AttachFilestoreServiceImpl extends ServiceImpl<AttachFilestoreMapper, AttachFilestore> implements IAttachFilestoreService {

	@Override
	public void saveFiles(String attachId, byte[] content) {
		this.baseMapper.saveFiles(attachId, content);
	}

}
