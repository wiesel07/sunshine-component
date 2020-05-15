package cn.sunshine.component.common.util;

import cn.hutool.core.util.StrUtil;

/**
 * <p>
 * 字符串工具扩展类
 * </p>
 *
 * @author wuj
 * @since 2019年6月14日
 */

public class StrUtilExt extends StrUtil {

	public static String toString(Object obj,String def){
    	return obj == null ? def : obj.toString();
    }
	
	public static String toString(Object obj) {
		return toString(obj, "");
	}
	
    public static String isEmpty(Object obj,String def){
    	return isEmpty(toString(obj)) ? def : obj.toString();
    }
}



