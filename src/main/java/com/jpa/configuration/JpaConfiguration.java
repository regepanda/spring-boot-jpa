package com.jpa.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.jpa.Interceptor.JpaInterceptor;
import com.jpa.filter.JpaFilter;

@Configuration
public class JpaConfiguration implements WebMvcConfigurer {
	@Autowired
	private JpaInterceptor jpaInterceptor;
	
	@Autowired
	private JpaFilter jpaFilter;
	
	/**
	 * 加入我们自定义的拦截器
	 */
	@Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 添加一个拦截器，拦截以/test为前缀的url路径
		registry.addInterceptor(jpaInterceptor).addPathPatterns("/*");
    }
	
	/**
	 * 加入我们自定义的过滤器
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean
    public FilterRegistrationBean filterRegistration() {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        // 添加我们写好的过滤器
		filterRegistrationBean.setFilter(jpaFilter);
        // 设置过滤器的URL模式
		filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;
	}
}
