package cn.sunshine.component.upms.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.sunshine.component.common.api.CommonResp;
import cn.sunshine.component.common.constant.CommonUrlConstant;
import cn.sunshine.component.common.domain.OperParam;
import cn.sunshine.component.common.domain.PageResp;
import cn.sunshine.component.upms.entity.req.UpmsRolePageReq;
import cn.sunshine.component.upms.entity.req.UpmsRoleReq;
import cn.sunshine.component.upms.entity.resp.UpmsRolePageResp;
import cn.sunshine.component.upms.entity.resp.UpmsRoleResp;
import cn.sunshine.component.upms.service.IUpmsRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;


/**
 * <p>
 *  角色信息表 前端控制器
 * </p>
 *
 * @author wuj
 * @since 2020-03-15
 */
@Slf4j
@Api(tags = "角色信息表 前端控制器")
@RestController
@RequestMapping("/soccer/upmsRole")
public class UpmsRoleController { 
    
    
    @Autowired
	private IUpmsRoleService upmsRoleService;

	
	@ApiOperation(value = "获取列表(分页)", notes = "获取列表(分页)")
	@GetMapping(CommonUrlConstant.QUERY_PAGE)
	public CommonResp<PageResp<UpmsRolePageResp>> queryPage(@Valid UpmsRolePageReq upmsRolePageReq) {
		log.info("获取列表(分页)(upmsRolePageReq={})", upmsRolePageReq);
		
		PageResp<UpmsRolePageResp> pageResp = upmsRoleService.queryPage(upmsRolePageReq);
		// 返回结果集组
		return new CommonResp<PageResp<UpmsRolePageResp>>(pageResp);
	}

	@ApiOperation(value = "新增", notes = "新增")
	@PostMapping(CommonUrlConstant.ADD)
	public CommonResp<String> add(@Valid @RequestBody UpmsRoleReq upmsRoleReq) {
		log.info("新增(upmsRoleReq={})", upmsRoleReq);

		upmsRoleService.doCreate(upmsRoleReq);

		return new CommonResp<String>();
	}

	@ApiOperation(value = "修改", notes = "修改")
	@PutMapping(CommonUrlConstant.UPDATE)
	public CommonResp<String> update(@Valid @RequestBody UpmsRoleReq upmsRoleReq) {
		log.info("修改(upmsRoleReq={})", upmsRoleReq);

		upmsRoleService.doUpdate(upmsRoleReq);

		return new CommonResp<String>();
	}
	
	
	@ApiOperation(value = "删除", notes = "删除")
	@DeleteMapping(CommonUrlConstant.REMOVE)
	public CommonResp<String> remove(@Valid @RequestBody OperParam operParam) {
		log.info("删除(operParam={})", operParam);

		upmsRoleService.doRemove(Long.valueOf(operParam.getId()));

		return new CommonResp<String>();
	}
	
	@ApiOperation(value = "根据ID查看详情", notes = "根据ID查看详情")
	@GetMapping(CommonUrlConstant.DETAIL)
	public CommonResp<UpmsRoleResp> detail(@Valid OperParam operParam) {
		log.info("根据ID查看详情(operParam={})", operParam);

		UpmsRoleResp upmsRoleResp = upmsRoleService.getDetail(Long.valueOf(operParam.getId()));

		return new CommonResp<UpmsRoleResp>(upmsRoleResp);
	}

}
