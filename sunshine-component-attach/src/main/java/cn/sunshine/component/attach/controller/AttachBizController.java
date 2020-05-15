package cn.sunshine.component.attach.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;


/**
 * <p>
 *  业务附件关系表（允许配置附件信息存储在指定表中） 前端控制器
 * </p>
 *
 * @author wuj
 * @since 2020-03-15
 */
@Slf4j
@Api(tags = "业务附件关系表（允许配置附件信息存储在指定表中） 前端控制器")
@RestController
@RequestMapping("/soccer/bfAttachBiz")
public class AttachBizController { 
    
    

	
//	@ApiOperation(value = "根据ID查看详情", notes = "根据ID查看详情")
//	@GetMapping(CommonUrlConstant.DETAIL)
//	public R<BfAttachBizResp> detail(@Valid OperParam operParam) {
//		log.info("根据ID查看详情(operParam={})", operParam);
//
//		BfAttachBizResp bfAttachBizResp = bfAttachBizService.getDetail(Long.valueOf(operParam.getId()));
//
//		return new R<BfAttachBizResp>(bfAttachBizResp);
//	}

}
