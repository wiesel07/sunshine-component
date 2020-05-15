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
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


 /**
 * <p>
 *  资源-附件项配置表-存放每个业务对应的附件项信息及其配置项
 * </p>
 *
 * @author wuj
 * @since 2020-03-15
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
@Accessors(chain = true)
@TableName("attach_item")
@ApiModel("资源-附件项配置表-存放每个业务对应的附件项信息及其配置项")
public class  AttachItem implements Serializable {

    private static final long serialVersionUID = 1L; 
 
    
    @ApiModelProperty(value ="附件项id")
    @TableId(value = "ID")
    private Long id;
    
    @ApiModelProperty(value ="业务类型")
    private String bizType;
    
    @ApiModelProperty(value ="业务名称")
    private String bizName;
    
    @ApiModelProperty(value ="附件项类型")
    private String itemType;
    
    @ApiModelProperty(value ="附件项编码")
    private String itemCode;
    
    @ApiModelProperty(value ="附件项名称")
    private String itemName;
    
    @ApiModelProperty(value ="附件项文件扩展名如：.zip,.jpg,.rar")
    private String itemExts;
    
    @ApiModelProperty(value ="附件项说明")
    private String itemRemark;
    
    @ApiModelProperty(value ="是否必填")
    private String required;
    
    @ApiModelProperty(value ="附件项单个文件最大值 单位b")
    private Integer itemMaxSize;
    
    @ApiModelProperty(value ="状态（0 未启用、1 启用）")
    private String status;
    
    @ApiModelProperty(value ="排序")
    private Integer sortNo;
    
    @ApiModelProperty(value ="级别")
    private Integer grade;
    
    @ApiModelProperty(value ="说明")
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
    public static final String ITEM_TYPE = "ITEM_TYPE";
    public static final String ITEM_CODE = "ITEM_CODE";
    public static final String ITEM_NAME = "ITEM_NAME";
    public static final String ITEM_EXTS = "ITEM_EXTS";
    public static final String ITEM_REMARK = "ITEM_REMARK";
    public static final String REQUIRED = "REQUIRED";
    public static final String ITEM_MAX_SIZE = "ITEM_MAX_SIZE";
    public static final String STATUS = "STATUS";
    public static final String SORT_NO = "SORT_NO";
    public static final String GRADE = "GRADE";
    public static final String REMARK = "REMARK";
    
    public static final String CREATE_BY = "CREATE_BY";
    public static final String CREATE_TIME = "CREATE_TIME";
    public static final String UPDATE_TIME = "UPDATE_TIME";

}