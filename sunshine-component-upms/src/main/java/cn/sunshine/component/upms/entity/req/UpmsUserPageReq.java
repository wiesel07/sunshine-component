package cn.sunshine.component.upms.entity.req;

import java.util.Date;

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
 *  用户信息表 分页请求对象
 * </p>
 *
 * @author wuj
 * @since 2020-03-15
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@ApiModel("用户信息表 分页请求对象")
public class UpmsUserPageReq extends PageReq<UpmsUserPageReq>  {

    
    
    @ApiModelProperty(value ="用户账号")
    private String userCode;
    
    @ApiModelProperty(value ="用户名称")
    private String userName;
    
    @ApiModelProperty(value ="用户类型")
    private String userType;
    
    @ApiModelProperty(value ="登录密码 MD5(用户名+#+密码)")
    private String password;
    
    @ApiModelProperty(value ="用户状态 （0 未激活、1 正常 、2 锁定 、3 停用 、9 注销  说明：未激活状态时， 登录后需要强制修改密码）")
    private String status;
    
    @ApiModelProperty(value ="状态为锁定时的解锁时间")
    private String unlockTime;
    
    @ApiModelProperty(value ="手机号码")
    private String mobileNo;
    
    @ApiModelProperty(value ="联系方式")
    private String contactTel;
    
    @ApiModelProperty(value ="电子邮箱")
    private String email;
    
    @ApiModelProperty(value ="最近登录时间")
    private Date lastestLogin;
    
    @ApiModelProperty(value ="最近登出时间")
    private Date lastestLogout;
    
    @ApiModelProperty(value ="登录IP地址")
    private String loginIp;
    
    @ApiModelProperty(value ="登录次数")
    private Integer loginCnt;
    
    @ApiModelProperty(value ="头像地址")
    private String imgUrl;
    
    @ApiModelProperty(value ="备注")
    private String remark;
    
    
    @ApiModelProperty(value ="创建者")
    private String createBy;
    
    
	
}
