package com.jpa.util;

import java.util.List;
import org.springframework.validation.ObjectError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 返回的数据格式
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse {
	/**
	 * 错误代码，0表示没错误
	 */
	private int code = 0;
	
	/**
	 * 错误信息提示
	 */
	private String msg = null;
	
	
	/**
	 * 返回的错误类型
	 */
	private Object data = null;
	
	/**
	 * 用户未登录CODE
	 */
	private static final int NO_LOGIN_CODE = 50001;
	/**
	 * 用户没权限CODE
	 */
	private static final int NO_AUTH_CODE = 50002;
	/**
	 * 系统错误
	 */
	private static final int RUNTIME_CODE = 50003;
	
	/**
	 * 验证错误
	 */
	private static final int VALIDATE_CODE = 50004;
	
	private static final int AUTH_CODE = 50005;

	public ApiResponse(Object data) {
		this(0, null, data);
	}
	
	public ApiResponse(int code, String msg) {
		this(code, msg, null);
	}
	
	public static ApiResponse noLoingResponse() {
		return new ApiResponse(NO_LOGIN_CODE, "请先登录");
	}
	
	public static ApiResponse notAuthResponse() {
		return new ApiResponse(NO_AUTH_CODE, "对不起，您无权访问");
	}
	
	public static ApiResponse runtimeResponse(String msg) {
		return new ApiResponse(RUNTIME_CODE, msg);
	}
	
	public static ApiResponse validateErrResponse(List<ObjectError> errs) {
		StringBuffer msg = new StringBuffer();
		for (ObjectError err: errs) {
			msg.append(err.getDefaultMessage() + "\r\n");
		}
		return new ApiResponse(VALIDATE_CODE, msg.toString());
	}
	public static ApiResponse authErrResponse(String msg) {
		return new ApiResponse(AUTH_CODE, msg);
	}
}
