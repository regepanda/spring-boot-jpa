package com.jpa.Interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import com.jpa.util.RequestWrapper;

/**
 * 项目拦截器
 * 在com.jpa.Interceptor.JpaInterceptor配置类中注入
 * @author lili
 *
 */
@Component
public class JpaInterceptor implements HandlerInterceptor {
	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		System.out.println("3333333");
		RequestWrapper requestWrapper = new RequestWrapper(request);
        String body = requestWrapper.getBody();
        System.out.println(body);
        return true;
    }
 
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }
 
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
