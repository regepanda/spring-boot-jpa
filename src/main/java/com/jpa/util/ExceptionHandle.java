package com.jpa.util;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 过滤器，全局捕获异常
 * @author lili
 *
 */
@ControllerAdvice
public class ExceptionHandle {
	@ExceptionHandler(value = JpaException.class)
	@ResponseBody
	public ApiResponse handle(Exception e) {
		return ApiResponse.runtimeResponse(e.getMessage());
	}
}
