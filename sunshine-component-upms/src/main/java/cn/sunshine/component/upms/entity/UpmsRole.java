package cn.sunshine.component.upms.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


 /**
 * <p>
 *  角色信息表
 * </p>
 *
 * @author wuj
 * @since 2020-03-15
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("upms_role")
@ApiModel("角色信息表")
public class  UpmsRole implements Serializable
{

    private static final long serialVersionUID = 1L; 
    
    @ApiModelProperty(value ="角色ID")
    @TableId(value = "ROLE_ID")
    private Long roleId;
    
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
    
    @ApiModelProperty(value ="逻辑删除（0 未删除 、1 已删除）",hidden=true)
    @TableField("DEL_FLAG")
    @TableLogic
    private String delFlag;
    
    @ApiModelProperty(value ="创建者")
    private String createBy;
    
    @ApiModelProperty(value ="创建时间",hidden=true)
	@TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private Date createTime;
    
    @ApiModelProperty(value ="修改时间",hidden=true)
	@TableField(value = "UPDATE_TIME", fill = FieldFill.UPDATE)
    private Date updateTime;
    
    public static final String ROLE_ID = "ROLE_ID";
    public static final String ROLE_CODE = "ROLE_CODE";
    public static final String ROLE_NAME = "ROLE_NAME";
    public static final String STATUS = "STATUS";
    public static final String DATA_SCOPE = "DATA_SCOPE";
    public static final String SORT_NO = "SORT_NO";
    public static final String REMARK = "REMARK";
    public static final String DEL_FLAG = "DEL_FLAG";
    public static final String CREATE_BY = "CREATE_BY";
    public static final String CREATE_TIME = "CREATE_TIME";
    public static final String UPDATE_TIME = "UPDATE_TIME";

}
