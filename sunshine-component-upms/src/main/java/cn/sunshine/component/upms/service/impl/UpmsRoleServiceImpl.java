package cn.sunshine.component.upms.service.impl;


import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.sunshine.component.common.domain.PageResp;
import cn.sunshine.component.common.util.BeanUtilExt;
import cn.sunshine.component.common.util.Toolkit;
import cn.sunshine.component.upms.entity.UpmsRole;
import cn.sunshine.component.upms.entity.req.UpmsRolePageReq;
import cn.sunshine.component.upms.entity.req.UpmsRoleReq;
import cn.sunshine.component.upms.entity.resp.UpmsRolePageResp;
import cn.sunshine.component.upms.entity.resp.UpmsRoleResp;
import cn.sunshine.component.upms.mapper.UpmsRoleMapper;
import cn.sunshine.component.upms.service.IUpmsRoleService;

 /**
 * <p>
 *  角色信息表 服务实现类
 * </p>
 *
 * @author wuj
 * @since 2020-03-15
 */
@Service
public class UpmsRoleServiceImpl extends ServiceImpl<UpmsRoleMapper, UpmsRole> implements IUpmsRoleService {


    @SuppressWarnings("unchecked")
	@Override
	public PageResp<UpmsRolePageResp> queryPage(UpmsRolePageReq upmsRolePageReq) {
		IPage<UpmsRole> page = new Page<UpmsRole>(upmsRolePageReq.getPageNo(), upmsRolePageReq.getPageSize());

		QueryWrapper<UpmsRole> qw = new QueryWrapper<UpmsRole>();
	
		page = this.baseMapper.selectPage(page, qw);
		return Toolkit.converter.copyPage(page, UpmsRolePageResp.class);
	}

	@Override
    public void doCreate(UpmsRoleReq upmsRoleReq){
    
       UpmsRole upmsRole = new UpmsRole();
		BeanUtilExt.copyProperties(upmsRoleReq, upmsRole);
		this.baseMapper.insert(upmsRole);
    }
    
    
    @Override
    public void doUpdate(UpmsRoleReq upmsRoleReq){
    
        UpmsRole upmsRole = new UpmsRole();
		BeanUtilExt.copyProperties(upmsRoleReq, upmsRole);
		this.baseMapper.updateById(upmsRole);
    }
    
    
    @Override
    public void doRemove(Long id){
    
       this.baseMapper.deleteById(id);
    }
    
    @SuppressWarnings("unchecked")
	@Override
	public UpmsRoleResp getDetail(Long id) {
		
		UpmsRole upmsRole= this.baseMapper.selectById(id);
		return (UpmsRoleResp) Toolkit.converter.copyObject(upmsRole, UpmsRoleResp.class);
	}
}
