package cn.sunshine.component.attach.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.sunshine.component.attach.service.IAttachFileService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;


/**
 * <p>
 *  附件表 前端控制器
 * </p>
 *
 * @author wuj
 * @since 2020-03-15
 */
@Slf4j
@Api(tags = "附件表 前端控制器")
@RestController
@RequestMapping("/soccer/bfAttachFile")
public class AttachFileController { 
    
    
    @Autowired
	private IAttachFileService bfAttachFileService;

//	
//	@ApiOperation(value = "获取列表(分页)", notes = "获取列表(分页)")
//	@GetMapping(CommonUrlConstant.QUERY_PAGE)
//	public R<PageResp<BfAttachFilePageResp>> queryPage(@Valid BfAttachFilePageReq bfAttachFilePageReq) {
//		log.info("获取列表(分页)(bfAttachFilePageReq={})", bfAttachFilePageReq);
//		
//		PageResp<BfAttachFilePageResp> pageResp = bfAttachFileService.queryPage(bfAttachFilePageReq);
//		// 返回结果集组
//		return new R<>(pageResp);
//	}


}
