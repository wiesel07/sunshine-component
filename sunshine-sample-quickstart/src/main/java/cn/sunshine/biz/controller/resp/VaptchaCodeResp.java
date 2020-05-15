package cn.sunshine.biz.controller.resp;

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
 * @since 2020年4月11日
 */
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class VaptchaCodeResp {
	
	String img;
	
	String imgKey;

}
