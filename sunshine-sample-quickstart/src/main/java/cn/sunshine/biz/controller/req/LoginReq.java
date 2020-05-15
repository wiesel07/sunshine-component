package cn.sunshine.biz.controller.req;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
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
@NoArgsConstructor
@AllArgsConstructor
public class LoginReq {

	@NotBlank(message= "登录账号不能为空")
	String userCode;
	
	@NotBlank(message = "登录密码不能为空")
	String password;
	
	/**
	 * 验证码 可为空
	 */
	String vaptchaCode;
}
