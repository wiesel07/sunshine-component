package cn.sunshine.biz.controller.resp;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * TODO
 * </p>
 *
 * @author wuj
 * @since 2020年4月11日
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("登录响应模型")
public class LoginResp implements Serializable{

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "用户ID")
	Long userId;

	@ApiModelProperty(value = "用户编码")
	String userCode;

	@ApiModelProperty(value = "用户名称")
	String userName;

	@ApiModelProperty(value = "用户类型")
	String userType;

	@ApiModelProperty(value = "是否需要修改密码")
	boolean modifiedPassword;

	
//	@ApiModelProperty(value = "菜单树")
//	List<MenuTree> menuTrees;
}
