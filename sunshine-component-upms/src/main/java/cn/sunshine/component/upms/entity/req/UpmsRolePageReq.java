package cn.sunshine.component.upms.entity.req;

import cn.sunshine.component.common.domain.PageReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
 /**
 * <p>
 *  角色信息表 分页请求对象
 * </p>
 *
 * @author wuj
 * @since 2020-03-15
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@ApiModel("角色信息表 分页请求对象")
public class UpmsRolePageReq extends PageReq<UpmsRolePageReq>  {

    
    
    @ApiModelProperty(value ="角色编码")
    private String roleCode;
    
    @ApiModelProperty(value ="角色名称")
    private String roleName;
    
    @ApiModelProperty(value ="角色状态（0 正常、1停用）")
    private String status;
    
    @ApiModelProperty(value ="数据范围（1 所有数据权限、2 自定义数据权限、3 本部门数据权限、4 本部门及以下数据权限）")
    private String dataScope;
    
    @ApiModelProperty(value ="显示顺序")
    private Integer sortNo;
    
    @ApiModelProperty(value ="备注")
    private String remark;
    
    
    @ApiModelProperty(value ="创建者")
    private String createBy;
    
	
}
