package cn.sunshine.component.runtime.web.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 * TODO
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
public class RoleVo implements Serializable{
	
	private static final long serialVersionUID = -9134462580511665594L;

	private String roleId;
	
	private String roleCode;

	private String roleName;
	
	private  String roleType;
    
    private String dataScope;

}
