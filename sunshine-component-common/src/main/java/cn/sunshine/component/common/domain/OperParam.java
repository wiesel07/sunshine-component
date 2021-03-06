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
 * 操作的请求参数 （主要用于根据ID进行更新或者删除的操作）
 * </p>
 *
 * @author wuj
 * @since 2019年6月14日
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("批量操作的请求参数")
public class OperParam {

	@ApiModelProperty(value = "ID", required = true)
	@NotEmpty(message = "ID为空")
	private String id;
}



