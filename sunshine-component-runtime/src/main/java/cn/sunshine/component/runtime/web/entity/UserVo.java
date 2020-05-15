package cn.sunshine.component.runtime.web.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 登录用户实体对象
 * </p>
 *
 * @author wuj
 * @since 2020-03-16
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserVo implements Serializable{

static final long serialVersionUID = 2967189585084634082L;
	
	Long userId;

	String userName;

	String userCode;

	//手机号码
	private String phoneNo;

	//联系电话
	private String contactTel;
	
	/**
	 * 类型0 超级管理员 1 管理员 2 普通操作员
	 */
	private String type;
	
	private  OrgVo orgVo;
	
    /** 角色对象 */
    private List<RoleVo> roles;

    /** 角色组 */
    private Long[] roleIds;
	
	@Builder.Default
	Map<String,Object> properties = new HashMap<>();
	
	void addProperties(Map<String,Object> properties) {
		properties.putAll(properties);
	}
	
	
	
}
