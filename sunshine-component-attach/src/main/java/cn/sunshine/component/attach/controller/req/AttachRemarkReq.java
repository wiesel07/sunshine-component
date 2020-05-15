package cn.sunshine.component.attach.controller.req;

import javax.validation.constraints.NotEmpty;

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
@ApiModel("附件备注新增请求对象")
public class AttachRemarkReq {

	@ApiModelProperty(value = "业务编码")
	@NotEmpty(message = "业务ID为空")
	private String bizId;

	@ApiModelProperty(value = "业务类型")
	@NotEmpty(message = "业务类型为空")
	private String bizType;

	@ApiModelProperty(value = "附件项编码")
	@NotEmpty(message = "附件项为空")
	private String itemCode;

	@ApiModelProperty(value = "备注")
	private String remark;
}
