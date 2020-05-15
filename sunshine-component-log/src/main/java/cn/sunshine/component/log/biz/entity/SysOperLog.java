package cn.sunshine.component.log.biz.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 操作日志表
 * </p>
 *
 * @author wuj
 * @since 2020-04-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="SysOperLog对象", description="操作日志表")
public class SysOperLog implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "用户ID")
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

    @ApiModelProperty(value = "操作描述")
    @TableField("OPER_DESC")
    private String operDesc;

    @ApiModelProperty(value = "执行时间")
    @TableField("TOTAL_TIME")
    private Long totalTime;

    @ApiModelProperty(value = "操作类别（0 其它、1 后台用户、2 手机端用户）")
    @TableField("OPER_TYPE")
    private Integer operType;

    @ApiModelProperty(value = "操作地址")
    @TableField("OPER_LOCATION")
    private String operLocation;

    @ApiModelProperty(value = "请求地址")
    @TableField("REQ_URL")
    private String reqUrl;

    @ApiModelProperty(value = "请求方式")
    @TableField("REQ_METHOD")
    private String reqMethod;

    @ApiModelProperty(value = "业务类型（0 其它、1 新增、 2 修改、3 删除 、4 授权、5 导出、6 导入、7  强退、8 生成代码、9 清空数据、10 上传、11 下载）")
    @TableField("BIZ_TYPE")
    private Integer bizType;

    @ApiModelProperty(value = "开始时间")
    @TableField("START_TIME")
    private Date startTime;

    @ApiModelProperty(value = "结束时间")
    @TableField("END_TIME")
    private Date endTime;

    @ApiModelProperty(value = "操作IP")
    @TableField("OPER_IP")
    private String operIp;

    @ApiModelProperty(value = "状态（0 失败、1  成功）")
    @TableField("STATUS")
    private String status;

    @ApiModelProperty(value = "错误信息")
    @TableField("ERROR_MSG")
    private String errorMsg;

    @ApiModelProperty(value = "请求参数")
    @TableField("REQ_PARAM")
    private String reqParam;

    @ApiModelProperty(value = "备注")
    @TableField("REMARK")
    private String remark;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    @TableField(value = "UPDATE_TIME", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;


    public static final String USER_ID = "USER_ID";

    public static final String USER_CODE = "USER_CODE";

    public static final String USER_NAME = "USER_NAME";

    public static final String ORG_ID = "ORG_ID";

    public static final String OPER_DESC = "OPER_DESC";

    public static final String TOTAL_TIME = "TOTAL_TIME";

    public static final String OPER_TYPE = "OPER_TYPE";

    public static final String OPER_LOCATION = "OPER_LOCATION";

    public static final String REQ_URL = "REQ_URL";

    public static final String REQ_METHOD = "REQ_METHOD";

    public static final String BIZ_TYPE = "BIZ_TYPE";

    public static final String START_TIME = "START_TIME";

    public static final String END_TIME = "END_TIME";

    public static final String OPER_IP = "OPER_IP";

    public static final String STATUS = "STATUS";

    public static final String ERROR_MSG = "ERROR_MSG";

    public static final String REQ_PARAM = "REQ_PARAM";

    public static final String REMARK = "REMARK";

    public static final String CREATE_TIME = "CREATE_TIME";

    public static final String UPDATE_TIME = "UPDATE_TIME";

}
