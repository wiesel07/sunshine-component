package cn.sunshine.common.upms.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.sunshine.common.upms.entity.UpmsUser;
import cn.sunshine.common.upms.mapper.UpmsUserMapper;
import cn.sunshine.common.upms.service.IUpmsUserService;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author wuj
 * @since 2020-03-15
 */
@Service
public class UpmsUserServiceImpl extends ServiceImpl<UpmsUserMapper, UpmsUser> implements IUpmsUserService {

}
