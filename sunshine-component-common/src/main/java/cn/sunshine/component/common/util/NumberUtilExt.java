package cn.sunshine.component.common.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.NumberUtil;

/**
 * <p>
 * TODO
 * </p>
 *
 * @author wuj
 * @since 2020年3月15日
 */
public class NumberUtilExt extends NumberUtil{

	
    public static long toLong(final String str) {
        return toLong(str, 0L);
    }
	
    public static long toLong(final String str, final long defaultValue) {
        if (str == null) {
            return defaultValue;
        }
        try {
            return Long.parseLong(str);
        } catch (final NumberFormatException nfe) {
            return defaultValue;
        }
    }
}
