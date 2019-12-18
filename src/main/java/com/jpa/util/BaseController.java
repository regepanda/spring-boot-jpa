package com.jpa.util;

import javax.validation.ValidationException;

public abstract class BaseController {
	protected interface ApiRun {
		ApiResponse run() throws Exception;
	}
	
	protected ApiResponse runApi(ApiRun apiRun) throws Exception {
		ApiResponse apiResponse = null;
		try {
			apiResponse = apiRun.run();
		} catch (JpaException e) {
			apiResponse = ApiResponse.runtimeResponse(e.getMessage());
		} catch (ValidationException e) {
			apiResponse = ApiResponse.runtimeResponse(e.getMessage());
		}
		return apiResponse;
	}
}
