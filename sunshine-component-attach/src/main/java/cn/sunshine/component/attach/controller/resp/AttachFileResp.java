package cn.sunshine.component.attach.controller.resp;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;

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
@ApiModel("附件返回结果集")
public class AttachFileResp implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "附件ID")
	@TableId(value = "ATTACH_ID")
	private Long attachId;

	@ApiModelProperty(value = "附件名称")
	private String attachName;

	@ApiModelProperty(value = "文件类型")
	private String contentType;

	@ApiModelProperty(value = "文件大小")
	private Long fileSize;

	@ApiModelProperty(value = "文件路径")
	private String filePath;

//	@ApiModelProperty(value = "业务编码")
//	private String bizId;
//
//	@ApiModelProperty(value = "业务类型")
//	private String bizType;
//
//	@ApiModelProperty(value = "附件项编码")
//	private String itemCode;
//
//	@ApiModelProperty(value = "附件存储方式  本地存储(LocalHostAttachService) 数据库存储(DatabaseAttachService) fastdfs存储(FastDfsAttachService)")
//	private String uploadType;

	@ApiModelProperty(value = "缩略图路径")
	private String thumbPath;

//	@ApiModelProperty(value = "删除说明")
//	private String delRemark;

	@ApiModelProperty(value = "是否备注")
	private String isRemark;

	@ApiModelProperty(value = "备注")
	private String remark;

}
