package cn.sunshine.component.log.biz.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 登录日志表
 * </p>
 *
 * @author wuj
 * @since 2020-04-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="SysLoginLog对象", description="登录日志表")
public class SysLoginLog implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "ID")
    @TableId("LOG_ID")
    private Long logId;

    @ApiModelProperty(value = "用户id")
    @TableField("USER_ID")
    private Long userId;

    @ApiModelProperty(value = "用户编码")
    @TableField("USER_CODE")
    private String userCode;

    @ApiModelProperty(value = "用户名称")
    @TableField("USER_NAME")
    private String userName;

    @ApiModelProperty(value = "机构ID")
    @TableField("ORG_ID")
    private String orgId;

    @ApiModelProperty(value = "登录IP")
    @TableField("LOGIN_IP")
    private String loginIp;

    @ApiModelProperty(value = "登录地址")
    @TableField("LOGIN_LOCATION")
    private String loginLocation;

    @ApiModelProperty(value = "登录时间")
    @TableField("LOGIN_TIME")
    private Date loginTime;

    @ApiModelProperty(value = "登出时间")
    @TableField("LOGOUT_TIME")
    private Date logoutTime;

    @ApiModelProperty(value = "状态（0 失败、1 成功）")
    @TableField("STATUS")
    private String status;

    @ApiModelProperty(value = "备注")
    @TableField("REMARK")
    private String remark;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    @TableField(value = "UPDATE_TIME", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;


    public static final String LOG_ID = "LOG_ID";

    public static final String USER_ID = "USER_ID";

    public static final String USER_CODE = "USER_CODE";

    public static final String USER_NAME = "USER_NAME";

    public static final String ORG_ID = "ORG_ID";

    public static final String LOGIN_IP = "LOGIN_IP";

    public static final String LOGIN_LOCATION = "LOGIN_LOCATION";

    public static final String LOGIN_TIME = "LOGIN_TIME";

    public static final String LOGOUT_TIME = "LOGOUT_TIME";

    public static final String STATUS = "STATUS";

    public static final String REMARK = "REMARK";

    public static final String CREATE_TIME = "CREATE_TIME";

    public static final String UPDATE_TIME = "UPDATE_TIME";

}
