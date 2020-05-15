package cn.sunshine.component.attach.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.sunshine.component.attach.entity.AttachBiz;
import cn.sunshine.component.attach.mapper.AttachBizMapper;
import cn.sunshine.component.attach.service.IAttachBizService;

 /**
 * <p>
 *  业务附件关系表（允许配置附件信息存储在指定表中） 服务实现类
 * </p>
 *
 * @author wuj
 * @since 2020-03-15
 */
@Service
public class AttachBizServiceImpl extends ServiceImpl<AttachBizMapper, AttachBiz> implements IAttachBizService {


}
