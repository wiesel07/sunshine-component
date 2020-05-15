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
import cn.sunshine.component.upms.entity.req.UpmsUserPageReq;
import cn.sunshine.component.upms.entity.req.UpmsUserReq;
import cn.sunshine.component.upms.entity.resp.UpmsUserPageResp;
import cn.sunshine.component.upms.entity.resp.UpmsUserResp;
import cn.sunshine.component.upms.service.IUpmsUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;


/**
 * <p>
 *  用户信息表 前端控制器
 * </p>
 *
 * @author wuj
 * @since 2020-03-15
 */
@Slf4j
@Api(tags = "用户信息表 前端控制器")
@RestController
@RequestMapping("/soccer/upmsUser")
public class UpmsUserController { 
    
    
    @Autowired
	private IUpmsUserService upmsUserService;

	
	@ApiOperation(value = "获取列表(分页)", notes = "获取列表(分页)")
	@GetMapping(CommonUrlConstant.QUERY_PAGE)
	public CommonResp<PageResp<UpmsUserPageResp>> queryPage(@Valid UpmsUserPageReq upmsUserPageReq) {
		log.info("获取列表(分页)(upmsUserPageReq={})", upmsUserPageReq);
		
		PageResp<UpmsUserPageResp> pageResp = upmsUserService.queryPage(upmsUserPageReq);
		// 返回结果集组
		return new CommonResp<>(pageResp);
	}

	@ApiOperation(value = "新增", notes = "新增")
	@PostMapping(CommonUrlConstant.ADD)
	public CommonResp<String> add(@Valid @RequestBody UpmsUserReq upmsUserReq) {
		log.info("新增(upmsUserReq={})", upmsUserReq);

		upmsUserService.doCreate(upmsUserReq);

		return new CommonResp<String>();
	}

	@ApiOperation(value = "修改", notes = "修改")
	@PutMapping(CommonUrlConstant.UPDATE)
	public CommonResp<String> update(@Valid @RequestBody UpmsUserReq upmsUserReq) {
		log.info("修改(upmsUserReq={})", upmsUserReq);

		upmsUserService.doUpdate(upmsUserReq);

		return new CommonResp<String>();
	}
	
	
	@ApiOperation(value = "删除", notes = "删除")
	@DeleteMapping(CommonUrlConstant.REMOVE)
	public CommonResp<String> remove(@Valid @RequestBody OperParam operParam) {
		log.info("删除(operParam={})", operParam);

		upmsUserService.doRemove(Long.valueOf(operParam.getId()));

		return new CommonResp<String>();
	}
	
	@ApiOperation(value = "根据ID查看详情", notes = "根据ID查看详情")
	@GetMapping(CommonUrlConstant.DETAIL)
	public CommonResp<UpmsUserResp> detail(@Valid OperParam operParam) {
		log.info("根据ID查看详情(operParam={})", operParam);

		UpmsUserResp upmsUserResp = upmsUserService.getDetail(Long.valueOf(operParam.getId()));

		return new CommonResp<UpmsUserResp>(upmsUserResp);
	}

}
