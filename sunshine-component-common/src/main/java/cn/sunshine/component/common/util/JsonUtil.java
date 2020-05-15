package cn.sunshine.component.common.util;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * TODO
 * </p>
 *
 * @author wuj
 * @since 2020年4月2日
 */
@Slf4j
public class JsonUtil {

	/**
	 * 实体转换成json字符串
	 * 
	 * @param obj
	 * @return
	 *
	 */
	public static String toJsonString(Object obj) {
		return JSON.toJSONString(obj);
	}

	/**
	 * json字符串转实体
	 * 
	 * @param <T>
	 * @param jsonString
	 * @param clazz
	 * @return
	 *
	 */
	public static <T> T fromJson(String jsonString, Class<T> clazz) {
		return JSON.parseObject(jsonString, clazz);
	}
	
	/**
	 * 
	 * @Title: printObject  
	 * @Description:将对象以json格式输出
	 * @param obj
	 * @param isPrettyFormat
	 *
	 * @date   创建时间：2019年6月5日
	 * @author 作者：wuj
	 */
	public static void printObject(Object obj) {
		printObject(obj, true);
	}

	
	public static void printObject(Object obj, boolean isPrettyFormat) {
		String json = "";
		if (isPrettyFormat) {
			json = JSONObject.toJSONString(obj,
					new SerializerFeature[] { SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue,
							SerializerFeature.WriteNullStringAsEmpty, SerializerFeature.WriteNullBooleanAsFalse,
							SerializerFeature.WriteNullListAsEmpty, SerializerFeature.WriteNullNumberAsZero });
		} else {
			json = JSONObject.toJSONString(obj,
					new SerializerFeature[] { SerializerFeature.WriteMapNullValue,
							SerializerFeature.WriteNullStringAsEmpty, SerializerFeature.WriteNullBooleanAsFalse,
							SerializerFeature.WriteNullListAsEmpty, SerializerFeature.WriteNullNumberAsZero });
		}
		log.info(json);
	}

	public static void printObject(Object obj, String format) {
		log.info(String.format(format + "%s", toJsonString(obj)));
	}
	
	
	@SuppressWarnings("rawtypes")
	public static Map<String, Object> json2Map(String json,boolean camel) {
		    Map map = JSON.parseObject(json, Map.class);
		    Map<String,Object> resultMap = MapUtilExt.newHashMap();
		    for(Object key : map.keySet()) {
		    	if(camel) {
		    	  resultMap.put(ColumnUtil.underlineToCamel(StrUtilExt.toString(key)), map.get(key));
		    	} else {
		    		resultMap.put(StrUtilExt.toString(key), map.get(key));
		    	}
		    }
		    return resultMap;
	 }
	  
	  public static Map<String, Object> json2Map(String json){
		  return json2Map(json, false);
	  }
	  
	  public static List<Object> json2List(String json){
		  return JSON.parseArray(json, Object.class);
	  }	
}
