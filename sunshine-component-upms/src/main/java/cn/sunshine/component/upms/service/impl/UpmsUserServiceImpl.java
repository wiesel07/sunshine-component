package cn.sunshine.component.upms.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.sunshine.component.common.domain.PageResp;
import cn.sunshine.component.common.util.BeanUtilExt;
import cn.sunshine.component.common.util.Toolkit;
import cn.sunshine.component.upms.entity.UpmsUser;
import cn.sunshine.component.upms.entity.req.UpmsUserPageReq;
import cn.sunshine.component.upms.entity.req.UpmsUserReq;
import cn.sunshine.component.upms.entity.resp.UpmsUserPageResp;
import cn.sunshine.component.upms.entity.resp.UpmsUserResp;
import cn.sunshine.component.upms.mapper.UpmsUserMapper;
import cn.sunshine.component.upms.service.IUpmsUserService;

 /**
 * <p>
 *  用户信息表 服务实现类
 * </p>
 *
 * @author wuj
 * @since 2020-03-15
 */
@Service
public class UpmsUserServiceImpl extends ServiceImpl<UpmsUserMapper, UpmsUser> implements IUpmsUserService {


    @SuppressWarnings("unchecked")
	@Override
	public PageResp<UpmsUserPageResp> queryPage(UpmsUserPageReq upmsUserPageReq) {
		IPage<UpmsUser> page = new Page<>(upmsUserPageReq.getPageNo(), upmsUserPageReq.getPageSize());

		QueryWrapper<UpmsUser> qw = new QueryWrapper<>();
	
		page = this.baseMapper.selectPage(page, qw);
		return Toolkit.converter.copyPage(page, UpmsUserPageResp.class);
	}

	@Override
    public void doCreate(UpmsUserReq upmsUserReq){
    
       UpmsUser upmsUser = new UpmsUser();
		BeanUtilExt.copyProperties(upmsUserReq, upmsUser);
		this.baseMapper.insert(upmsUser);
    }
    
    
    @Override
    public void doUpdate(UpmsUserReq upmsUserReq){
    
        UpmsUser upmsUser = new UpmsUser();
		BeanUtilExt.copyProperties(upmsUserReq, upmsUser);
		this.baseMapper.updateById(upmsUser);
    }
    
    
    @Override
    public void doRemove(Long id){
    
       this.baseMapper.deleteById(id);
    }
    
    @SuppressWarnings("unchecked")
	@Override
	public UpmsUserResp getDetail(Long id) {
		
		UpmsUser upmsUser= this.baseMapper.selectById(id);
		return (UpmsUserResp) Toolkit.converter.copyObject(upmsUser, UpmsUserResp.class);
	}
}
