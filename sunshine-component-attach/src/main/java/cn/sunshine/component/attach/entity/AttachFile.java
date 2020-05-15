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
 *  附件表
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
@TableName("attach_file")
@ApiModel("附件表")
public class  AttachFile implements Serializable{

    private static final long serialVersionUID = 1L; 
 
    
    @ApiModelProperty(value ="附件ID")
    @TableId(value = "ATTACH_ID")
    private Long attachId;
    
    @ApiModelProperty(value ="附件名称")
    private String attachName;
    
    @ApiModelProperty(value ="文件类型")
    private String contentType;
    
    @ApiModelProperty(value ="文件大小")
    private Long fileSize;
    
    @ApiModelProperty(value ="文件路径")
    private String filePath;
    
    @ApiModelProperty(value ="业务编码")
    private String bizId;
    
    @ApiModelProperty(value ="业务类型")
    private String bizType;
    
    @ApiModelProperty(value ="附件项编码")
    private String itemCode;
    
    @ApiModelProperty(value ="附件存储方式  本地存储(LocalHostAttachService) 数据库存储(DatabaseAttachService) fastdfs存储(FastDfsAttachService)")
    private String uploadType;
    
    @ApiModelProperty(value ="缩略图路径")
    private String thumbPath;
    
    @ApiModelProperty(value ="删除说明")
    private String delRemark;
    
    @ApiModelProperty(value ="是否备注")
    private String isRemark;
    
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
    
    public static final String ATTACH_ID = "ATTACH_ID";
    public static final String ATTACH_NAME = "ATTACH_NAME";
    public static final String CONTENT_TYPE = "CONTENT_TYPE";
    public static final String FILE_SIZE = "FILE_SIZE";
    public static final String FILE_PATH = "FILE_PATH";
    public static final String BIZ_ID = "BIZ_ID";
    public static final String BIZ_TYPE = "BIZ_TYPE";
    public static final String ITEM_CODE = "ITEM_CODE";
    public static final String UPLOAD_TYPE = "UPLOAD_TYPE";
    public static final String THUMB_PATH = "THUMB_PATH";
    public static final String DEL_REMARK = "DEL_REMARK";
    public static final String IS_REMARK = "IS_REMARK";
    public static final String REMARK = "REMARK";
    public static final String CREATE_BY = "CREATE_BY";
    public static final String CREATE_TIME = "CREATE_TIME";
    public static final String UPDATE_TIME = "UPDATE_TIME";


}
