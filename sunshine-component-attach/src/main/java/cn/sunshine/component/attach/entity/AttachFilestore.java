package cn.sunshine.component.attach.entity;

import java.io.Serializable;
import java.util.Date;

import org.apache.ibatis.type.JdbcType;

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
 *  文件存储表
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
@TableName("attach_filestore")
@ApiModel("文件存储表")
public class  AttachFilestore implements Serializable {

    private static final long serialVersionUID = 1L; 
 
    
    @ApiModelProperty(value ="附件ID")
    @TableId(value = "ATTACH_ID")
    private Long attachId;
    
    @ApiModelProperty(value ="内容")
    @TableField(value="CONTENT",jdbcType = JdbcType.BLOB)
    private byte[] content;
    
    
    @ApiModelProperty(value ="创建者")
    private String createBy;
    
    @ApiModelProperty(value ="创建时间",hidden=true)
	@TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private Date createTime;
    
    @ApiModelProperty(value ="修改时间",hidden=true)
	@TableField(value = "UPDATE_TIME", fill = FieldFill.UPDATE)
    private Date updateTime;
    
    public static final String ATTACH_ID = "ATTACH_ID";
    public static final String CONTENT = "CONTENT";
    public static final String CREATE_BY = "CREATE_BY";
    public static final String CREATE_TIME = "CREATE_TIME";
    public static final String UPDATE_TIME = "UPDATE_TIME";
    

 
}
