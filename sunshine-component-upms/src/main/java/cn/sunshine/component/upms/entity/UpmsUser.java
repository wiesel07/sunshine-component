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
 *  用户信息表
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
@TableName("upms_user")
@ApiModel("用户信息表")
public class  UpmsUser implements Serializable {

    private static final long serialVersionUID = 1L; 
 
    
    @ApiModelProperty(value ="用户ID")
    @TableId(value = "USER_ID")
    private Long userId;
    
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
    
    public static final String USER_ID = "USER_ID";
    public static final String USER_CODE = "USER_CODE";
    public static final String USER_NAME = "USER_NAME";
    public static final String USER_TYPE = "USER_TYPE";
    public static final String PASSWORD = "PASSWORD";
    public static final String STATUS = "STATUS";
    public static final String UNLOCK_TIME = "UNLOCK_TIME";
    public static final String MOBILE_NO = "MOBILE_NO";
    public static final String CONTACT_TEL = "CONTACT_TEL";
    public static final String EMAIL = "EMAIL";
    public static final String LASTEST_LOGIN = "LASTEST_LOGIN";
    public static final String LASTEST_LOGOUT = "LASTEST_LOGOUT";
    public static final String LOGIN_IP = "LOGIN_IP";
    public static final String LOGIN_CNT = "LOGIN_CNT";
    public static final String IMG_URL = "IMG_URL";
    public static final String REMARK = "REMARK";
    public static final String DEL_FLAG = "DEL_FLAG";
    public static final String CREATE_BY = "CREATE_BY";
    public static final String CREATE_TIME = "CREATE_TIME";
    public static final String UPDATE_TIME = "UPDATE_TIME";

}
