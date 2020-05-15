package cn.sunshine.component.log.common.enums;

import java.util.HashMap;
import java.util.Map;

import cn.sunshine.component.common.enums.BaseEnum;

/**
 * <p>
 * TODO
 * </p>
 *
 * @author wuj
 * @since 2020年4月11日
 */
public enum LogStatusEnum implements BaseEnum<LogStatusEnum, String> {

    
    SUCCESS("1", "成功"), 
	FAIL("0", "失败");
    
    private String code;
	private String msg;

	LogStatusEnum(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	
	
	static Map<String, LogStatusEnum> enumMap = new HashMap<String, LogStatusEnum>();
	static {
		for (LogStatusEnum value : LogStatusEnum.values()) {
			enumMap.put(value.getValue(), value);
		}
	}
	
	public static LogStatusEnum parse(String code) {
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
