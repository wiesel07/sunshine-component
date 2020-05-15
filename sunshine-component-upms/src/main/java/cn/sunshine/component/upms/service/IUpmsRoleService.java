package cn.sunshine.component.upms.service;

import com.baomidou.mybatisplus.extension.service.IService;

import cn.sunshine.component.common.domain.PageResp;
import cn.sunshine.component.upms.entity.UpmsRole;
import cn.sunshine.component.upms.entity.req.UpmsRolePageReq;
import cn.sunshine.component.upms.entity.req.UpmsRoleReq;
import cn.sunshine.component.upms.entity.resp.UpmsRolePageResp;
import cn.sunshine.component.upms.entity.resp.UpmsRoleResp; 

 /**
 * <p>
 *  角色信息表 服务类
 * </p>
 *
 * @author wuj
 * @since 2020-03-15
 */
public interface IUpmsRoleService extends IService<UpmsRole> {


	/**
	 * 
	 * @Title: queryPage
	 * @Description:分页查询
	 * @param upmsRolePageReq
	 * @return
	 *
	 * @date   创建时间：2019年6月17日
	 * @author 作者：wuj
	 */
	PageResp<UpmsRolePageResp> queryPage(UpmsRolePageReq upmsRolePageReq);

    /**
	 * 
	 * @Title: doCreate  
	 * @Description:新增
	 * @param upmsRoleReq
	 *
	 * @date   创建时间：2020-03-15
	 * @author 作者： wuj
	 */
	void doCreate(UpmsRoleReq upmsRoleReq);
	
	/**
	 * 
	 * @Title: doUpdate  
	 * @Description:修改
	 * @param upmsRoleReq
	 *
	 * @date   创建时间：2020-03-15
	 * @author 作者： wuj
	 */
	void doUpdate(UpmsRoleReq upmsRoleReq);
	
	/**
	 * 
	 * @Title: doRemove  
	 * @Description:删除
	 * @param id
	 *
	 * @date   创建时间：2020-03-15
	 * @author 作者： wuj
	 */
	void doRemove(Long id);
	
		/**
	 * 
	 * @Title: getDetail
	 * @Description:根据ID获取详情
	 * @param id
	 *
	 * @date   创建时间：2020-03-15
	 * @author 作者：wuj
	 */
	UpmsRoleResp getDetail(Long id);
} 
