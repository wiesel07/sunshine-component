 package cn.sunshine.common.upms.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户信息表
 * </p>
 *
 * @author wuj
 * @since 2020-03-15
 */
@Data
@Accessors(chain = true)
public class UpmsUser implements Serializable{

    private static final long serialVersionUID=1L;

    /**
     * 用户ID
     */
    @TableId("USER_ID")
    private Long userId;

    /**
     * 用户账号
     */
    @TableField("USER_CODE")
    private String userCode;

    /**
     * 用户名称
     */
    @TableField("USER_NAME")
    private String userName;

    /**
     * 用户类型
     */
    @TableField("USER_TYPE")
    private String userType;

    /**
     * 登录密码 MD5(用户名+#+密码)
     */
    @TableField("PASSWORD")
    private String password;

    /**
     * 用户状态 （0 未激活、1 正常 、2 锁定 、3 停用 、9 注销  说明：未激活状态时， 登录后需要强制修改密码）
     */
    @TableField("STATUS")
    private String status;

    /**
     * 状态为锁定时的解锁时间
     */
    @TableField("UNLOCK_TIME")
    private String unlockTime;

    /**
     * 手机号码
     */
    @TableField("MOBILE_NO")
    private String mobileNo;

    /**
     * 联系方式
     */
    @TableField("CONTACT_TEL")
    private String contactTel;

    /**
     * 电子邮箱
     */
    @TableField("EMAIL")
    private String email;

    /**
     * 最近登录时间
     */
    @TableField("LASTEST_LOGIN")
    private Date lastestLogin;

    /**
     * 最近登出时间
     */
    @TableField("LASTEST_LOGOUT")
    private  Date lastestLogout;

    /**
     * 登录IP地址
     */
    @TableField("LOGIN_IP")
    private String loginIp;

    /**
     * 登录次数
     */
    @TableField("LOGIN_CNT")
    private Integer loginCnt;

    /**
     * 头像地址
     */
    @TableField("IMG_URL")
    private String imgUrl;

    /**
     * 备注
     */
    @TableField("REMARK")
    private String remark;

    /**
     * 逻辑删除（0 未删除 、1 已删除）
     */
    @TableField("DEL_FLAG")
    private String delFlag;

    /**
     * 创建者
     */
    @TableField("CREATE_BY")
    private String createBy;

    /**
     * 创建时间
     */
    @TableField("CREATE_TIME")
    private  Date createTime;

    /**
     * 修改时间
     */
    @TableField("UPDATE_TIME")
    private  Date updateTime;


}
