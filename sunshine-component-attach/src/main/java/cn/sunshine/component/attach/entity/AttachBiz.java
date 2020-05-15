package cn.sunshine.component.attach.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
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
 *  业务附件关系表（允许配置附件信息存储在指定表中）
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
@TableName("attach_biz")
@ApiModel("业务附件关系表（允许配置附件信息存储在指定表中）")
public class  AttachBiz implements Serializable {

    private static final long serialVersionUID = 1L; 
 
    
    @ApiModelProperty(value ="ID")
    @TableId(value = "ID")
    private Long id;
    
    @ApiModelProperty(value ="业务类型")
    private String bizType;
    
    @ApiModelProperty(value ="业务名称")
    private String bizName;
    
    @ApiModelProperty(value ="对应附件表")
    private String attachTbl;
    
    @ApiModelProperty(value ="附件存储方式  本地存储(LocalHostAttachService) 数据库存储(DatabaseAttachService) fastdfs存储(FastDfsAttachService)")
    private String uploadType;
    
    @ApiModelProperty(value ="状态（0 未启用、1 启用）")
    private String status;
    
    @ApiModelProperty(value ="备注")
    private String remark;
    
    @ApiModelProperty(value ="创建者")
    private String createBy;
    
    @ApiModelProperty(value ="创建时间",hidden=true)
	@TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private Date createTime;
    
    @ApiModelProperty(value ="修改时间",hidden=true)
	@TableField(value = "UPDATE_TIME", fill = FieldFill.UPDATE)
    private Date updateTime;
    
    public static final String ID = "ID";
    public static final String BIZ_TYPE = "BIZ_TYPE";
    public static final String BIZ_NAME = "BIZ_NAME";
    public static final String ATTACH_TBL = "ATTACH_TBL";
    public static final String UPLOAD_TYPE = "UPLOAD_TYPE";
    public static final String STATUS = "STATUS";
    public static final String REMARK = "REMARK";
    public static final String CREATE_BY = "CREATE_BY";
    public static final String CREATE_TIME = "CREATE_TIME";
    public static final String UPDATE_TIME = "UPDATE_TIME";

}
