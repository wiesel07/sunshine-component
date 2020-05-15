package cn.sunshine.component.common.domain;

import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
* <p>
* TODO
* </p>
*
* @author wuj
* @since 2019年11月15日
*/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("Excel数据导入请求参数")
public class ExportParam {

	
	@ApiModelProperty(value = "Excel文件路径", required = true)
	@NotEmpty(message = "Excel文件路径")
	private String path;
	
	@ApiModelProperty(value = "Excel工作表下标，默认从0开始")
	private Integer index;
}
