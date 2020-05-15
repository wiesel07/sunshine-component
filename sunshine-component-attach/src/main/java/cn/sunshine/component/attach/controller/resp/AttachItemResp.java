package cn.sunshine.component.attach.controller.resp;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 * TODO
 * </p>
 *
 * @author wuj
 * @since 2020年3月16日
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ApiModel("附件选项返回结果集")
public class AttachItemResp  implements Serializable{


	private static final long serialVersionUID = 1L;
    
//    @ApiModelProperty(value ="业务类型")
//    private String bizType;
//    
//    @ApiModelProperty(value ="业务名称")
//    private String bizName;
    
 

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
    
//    @ApiModelProperty(value ="状态（0 未启用、1 启用）")
//    private String status;
    
    @ApiModelProperty(value ="排序")
    private Integer sortNo;
    
//    @ApiModelProperty(value ="级别")
//    private Integer grade;
    
    @ApiModelProperty(value ="说明")
    private String remark;
    

    @ApiModelProperty(value ="已上传附件明细")
    private List<AttachFileResp>  attachFileGridList;
}
