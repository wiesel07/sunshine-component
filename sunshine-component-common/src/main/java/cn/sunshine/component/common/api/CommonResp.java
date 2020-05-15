package cn.sunshine.component.common.api;

import java.io.Serializable;

import lombok.Data;

/**
 * <p>
 * 通用返回对象
 * </p>
 *
 * @author wuj
 * @since 2020年3月15日
 */
@Data
public class CommonResp<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 业务错误码 00-成功
	 */
	private String respCode;

	/**
	 * 描述
	 */
	private String respMsg;

	/**
	 * 结果集
	 */
	private T data;

//	protected CommonResp() {
//	}

	public CommonResp() {
		super();
		this.respCode = "00";
		this.respMsg = "请求成功";
	}

	public CommonResp(T data) {
		if (data instanceof IErrorCode) {
			IErrorCode error = (IErrorCode) data;
			this.respCode = error.getCode();
			this.respMsg = String.format("%s", error.getMsg());
			this.data = data;
		} else {
			this.respCode = "00";
			this.respMsg = "请求成功";
			this.data = data;
		}
	}

	public CommonResp(String respCode, String respMsg) {
		this.respCode = respCode;
		this.respMsg = respMsg;
	}

	public CommonResp(String respCode, String respMsg, T data) {
		this.respCode = respCode;
		this.respMsg = respMsg;
		this.data = data;
	}


//	public static <T> CommonResp<T> success() {
//		return new CommonResp<T>(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(), null);
//	}
//	
//	/**
//	 * 成功返回结果
//	 *
//	 * @param data 获取的数据
//	 */
//	public static <T> CommonResp<T> success(T data) {
//		return new CommonResp<T>(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(), data);
//	}
//
//	/**
//	 * 成功返回结果
//	 *
//	 * @param data    获取的数据
//	 * @param message 提示信息
//	 */
//	public static <T> CommonResp<T> success(T data, String message) {
//		return new CommonResp<T>(ApiErrorCode.SUCCESS.getCode(), message, data);
//	}
//
//	/**
//	 * 失败返回结果
//	 * 
//	 * @param errorCode 错误码
//	 */
//	public static <T> CommonResp<T> failed(IErrorCode errorCode) {
//		return new CommonResp<T>(errorCode.getCode(), errorCode.getMsg(), null);
//	}
//
//	/**
//	 * 失败返回结果
//	 * 
//	 * @param message 提示信息
//	 */
//	public static <T> CommonResp<T> failed(String message) {
//		return new CommonResp<T>(ApiErrorCode.FAILED.getCode(), message, null);
//	}
//
//	/**
//	 * 失败返回结果
//	 */
//	public static <T> CommonResp<T> failed() {
//		return failed(ApiErrorCode.FAILED);
//	}

}
