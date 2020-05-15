package cn.sunshine.component.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * 字段处理工具类
 * </p>
 *
 * @author wuj
 * @since 2020年4月2日
 */
public class ColumnUtil {

	/**
	 * 下划线转驼峰 UNDERLINE_TO_CAMEL---> underlineToCamel
	 * 
	 * @param column
	 * @return
	 *
	 */
	public static String underlineToCamel(String column) {
		StringBuilder sb = new StringBuilder();

		boolean nextUpperCase = false;
		for (int i = 0; i < column.length(); i++) {
			char c = column.charAt(i);
			if (c == '_') {
				if (sb.length() > 0) {
					nextUpperCase = true;
				}
			} else {
				if (nextUpperCase) {
					sb.append(Character.toUpperCase(c));
					nextUpperCase = false;
				} else {
					sb.append(Character.toLowerCase(c));
				}
			}
		}
		return sb.toString();
	}

	/**
	 * 驼峰转下划线
	 * 
	 * @param line
	 * @return
	 *
	 */
	public static String camelUnderline(String line) {
		if (line == null || "".equals(line)) {
			return "";
		}
		line = String.valueOf(line.charAt(0)).toUpperCase().concat(line.substring(1));
		StringBuffer sb = new StringBuffer();
		Pattern pattern = Pattern.compile("[A-Z]([a-z\\d]+)?");
		Matcher matcher = pattern.matcher(line);
		while (matcher.find()) {
			String word = matcher.group();
			sb.append(word.toUpperCase());
			sb.append(matcher.end() == line.length() ? "" : "_");
		}
		return sb.toString();
	}
}
