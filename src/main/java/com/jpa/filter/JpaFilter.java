package com.jpa.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import com.jpa.util.RequestWrapper;

@Component
@WebFilter(urlPatterns = "/*",filterName = "channelFilter")
public class JpaFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		System.out.println("2222222");
		ServletRequest requestWrapper = null;
        if(request instanceof HttpServletRequest) {
            requestWrapper = new RequestWrapper((HttpServletRequest) request);
        }
        if(requestWrapper == null) {
        	chain.doFilter(request, response);
        } else {
        	chain.doFilter(requestWrapper, response);
        }
	}
	
	@Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }
	
	@Override
    public void destroy() {

    }
}
