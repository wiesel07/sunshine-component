package cn.sunshine.component.runtime.constant;

import cn.sunshine.component.redis.constant.RedisConstant;

/**
 * <p>
 * TODO
 * </p>
 *
 * @author wuj
 * @since 2020年4月2日
 */
public class  RuntimeConstant implements RedisConstant{
	
	public static final String SESSION_TOKEN_PREFIX = CACHE_NAMESPACE + "TOKEN:";

}
