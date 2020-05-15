package cn.sunshine.component.attach.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.sunshine.component.attach.service.IAttachItemService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;


/**
 * <p>
 *  资源-附件项配置表-存放每个业务对应的附件项信息及其配置项 前端控制器
 * </p>
 *
 * @author wuj
 * @since 2020-03-15
 */
@Slf4j
@Api(tags = "资源-附件项配置表-存放每个业务对应的附件项信息及其配置项 前端控制器")
@RestController
@RequestMapping("/soccer/bfAttachItem")
public class AttachItemController { 
    
    
    @Autowired
	private IAttachItemService bfAttachItemService;

	

}
