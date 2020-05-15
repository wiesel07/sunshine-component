package cn.sunshine.common.config;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 自定义Mapper接口，用于后续自定义通用方法
 * </p>
 *
 * @author wuj
 * @since 2020年3月15日
 */
public interface MyBaseMapper<T> extends BaseMapper<T> {

	/**
	 * 删除全部
	 */
	Integer deleteAll();
}
