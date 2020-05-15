package cn.sunshine.component.upms.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
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
 *  用户角色关联表
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
@TableName("upms_user_role")
@ApiModel("用户角色关联表")
public class  UpmsUserRole implements Serializable{

    private static final long serialVersionUID = 1L; 
 
    
    @ApiModelProperty(value ="用户ID")
    private Long userId;
    
    @ApiModelProperty(value ="角色ID")
    @TableId(value = "ROLE_ID")
    private Long roleId;
    
    public static final String USER_ID = "USER_ID";
    public static final String ROLE_ID = "ROLE_ID";

  
}
