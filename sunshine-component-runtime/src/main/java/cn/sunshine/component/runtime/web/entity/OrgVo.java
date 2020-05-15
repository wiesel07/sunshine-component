package cn.sunshine.component.runtime.web.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 * 登录用户当前单位实体对象
 * </p>
 *
 * @author wuj
 * @since 2020-03-16
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class OrgVo implements Serializable {


	private static final long serialVersionUID = -3754442203391556194L;

	private Long orgId;
	
	private String orgName;
	
	private String orgCode;
	
	private Long orgPid;

	private String orgPcode;

	private Integer orgRank;

	private String orgIsleaf;

}
