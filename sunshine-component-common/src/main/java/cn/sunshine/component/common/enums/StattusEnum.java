package cn.sunshine.component.common.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 状态类枚举对象
 * </p>
 *
 * @author wuj
 * @since 2020-03-16
 */
public enum StattusEnum implements BaseEnum<StattusEnum, String> {

	
	ENABLED("1", "启用"), 
	DISABLED("0", "禁用");

	
	private String code;
	private String msg;

	StattusEnum(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	
	
	static Map<String, StattusEnum> enumMap = new HashMap<String, StattusEnum>();
	static {
		for (StattusEnum value : StattusEnum.values()) {
			enumMap.put(value.getValue(), value);
		}
	}
	
	public static StattusEnum parse(String code) {
		return enumMap.get(code);
	}


    @Override
	public boolean isContainKey(String key) {
		return enumMap.containsKey(key);
	}


    @Override
	public String getValue() {
		return this.code;
	}

    @Override
	public String getName() {
		return this.msg;
	}
}
