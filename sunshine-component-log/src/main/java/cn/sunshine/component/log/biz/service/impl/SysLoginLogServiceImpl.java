package cn.sunshine.component.log.biz.service.impl;

import cn.sunshine.component.log.biz.entity.SysLoginLog;
import cn.sunshine.component.log.biz.mapper.SysLoginLogMapper;
import cn.sunshine.component.log.biz.service.ISysLoginLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 登录日志表 服务实现类
 * </p>
 *
 * @author wuj
 * @since 2020-04-09
 */
@Service
public class SysLoginLogServiceImpl extends ServiceImpl<SysLoginLogMapper, SysLoginLog> implements ISysLoginLogService {

}
