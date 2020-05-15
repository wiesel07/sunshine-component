package cn.sunshine.component.attach.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.sunshine.component.attach.service.IAttachFilestoreService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;


/**
 * <p>
 *  文件存储表 前端控制器
 * </p>
 *
 * @author wuj
 * @since 2020-03-15
 */
@Slf4j
@Api(tags = "文件存储表 前端控制器")
@RestController
@RequestMapping("/soccer/bfAttachFilestore")
public class AttachFilestoreController { 
    
    
    @Autowired
	private IAttachFilestoreService bfAttachFilestoreService;

	

}
