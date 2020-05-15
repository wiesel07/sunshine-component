package cn.sunshine.component.upms.service;

import com.baomidou.mybatisplus.extension.service.IService;

import cn.sunshine.component.common.domain.PageResp;
import cn.sunshine.component.upms.entity.UpmsUser;
import cn.sunshine.component.upms.entity.req.UpmsUserPageReq;
import cn.sunshine.component.upms.entity.req.UpmsUserReq;
import cn.sunshine.component.upms.entity.resp.UpmsUserPageResp;
import cn.sunshine.component.upms.entity.resp.UpmsUserResp; 

 /**
 * <p>
 *  用户信息表 服务类
 * </p>
 *
 * @author wuj
 * @since 2020-03-15
 */
public interface IUpmsUserService extends IService<UpmsUser> {


	/**
	 * 
	 * @Title: queryPage
	 * @Description:分页查询
	 * @param upmsUserPageReq
	 * @return
	 *
	 * @date   创建时间：2019年6月17日
	 * @author 作者：wuj
	 */
	PageResp<UpmsUserPageResp> queryPage(UpmsUserPageReq upmsUserPageReq);

    /**
	 * 
	 * @Title: doCreate  
	 * @Description:新增
	 * @param upmsUserReq
	 *
	 * @date   创建时间：2020-03-15
	 * @author 作者： wuj
	 */
	void doCreate(UpmsUserReq upmsUserReq);
	
	/**
	 * 
	 * @Title: doUpdate  
	 * @Description:修改
	 * @param upmsUserReq
	 *
	 * @date   创建时间：2020-03-15
	 * @author 作者： wuj
	 */
	void doUpdate(UpmsUserReq upmsUserReq);
	
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
	UpmsUserResp getDetail(Long id);
} 
