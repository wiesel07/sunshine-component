package cn.sunshine.component.log.biz.service.impl;

import cn.sunshine.component.log.biz.entity.SysOperLog;
import cn.sunshine.component.log.biz.mapper.SysOperLogMapper;
import cn.sunshine.component.log.biz.service.ISysOperLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 操作日志表 服务实现类
 * </p>
 *
 * @author wuj
 * @since 2020-04-09
 */
@Service
public class SysOperLogServiceImpl extends ServiceImpl<SysOperLogMapper, SysOperLog> implements ISysOperLogService {

}
