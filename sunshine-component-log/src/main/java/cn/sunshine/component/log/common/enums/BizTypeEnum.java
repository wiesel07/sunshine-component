package cn.sunshine.component.log.common.enums;

import java.util.HashMap;
import java.util.Map;

import cn.sunshine.component.common.enums.BaseEnum;

/**
 * <p>
 * 业务类型标志位
 * </p>
 *
 * @author wuj
 * @since 2020年4月9日
 */
public enum BizTypeEnum implements BaseEnum<BizTypeEnum, String> {

    
  //  0 其它、1 新增、 2 修改、3 删除 、4 授权、5 导出、6 导入、7  强退、8 生成代码、9 清空数据、10 上传、11 下载
	QUERY("1","查询"),
	INSERT("2","新增"),
	UPDATE("3","修改"),
	DELETE("4","删除"),
	GRANT("5","授权"),
	EXPORT("6"," 导出"),
	IMPORT("7","导入"),
	FORCE("8","强退"),
	GENCODE("9","生成代码"),
	CLEAN("10","清空"),
	UPLOAD("11","上传"),
	DOWNLOAD("12","下载"),
	OTHER("13","其他")
	;
	
    private String code;
	private String msg;
    
    BizTypeEnum(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	
	
	static Map<String, BizTypeEnum> enumMap = new HashMap<String, BizTypeEnum>();
	static {
		for (BizTypeEnum value : BizTypeEnum.values()) {
			enumMap.put(value.getValue(), value);
		}
	}
	
	public static BizTypeEnum parse(String code) {
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
