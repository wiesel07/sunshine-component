package cn.sunshine.component.runtime.web.session;

import java.util.List;
import java.util.Map;

import cn.sunshine.component.runtime.web.entity.OrgVo;
import cn.sunshine.component.runtime.web.entity.RoleVo;
import cn.sunshine.component.runtime.web.entity.UserVo;
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
 * @since 2020年4月1日
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommonSession {

	/**
	 * token
	 */
	String sid;

	/**
	 * 用户信息
	 */
	UserVo user;

	/**
	 * 当前用户角色信息
	 */
	List<RoleVo> roleList;

	/**
	 * 当前用户单位信息
	 */
	OrgVo orgVo;
	

	boolean login;

	String ip;

	/**
	 * 登录时间
	 */
	String loginTime;
	
	/**
	 * 额外的属性值
	 */
	Map<String,String> extParams;
}
