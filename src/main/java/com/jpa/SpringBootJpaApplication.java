package com.jpa;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.apache.commons.io.IOUtils;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.*;

@SpringBootApplication //会扫描本包下面的所有的bean
@EnableJpaAuditing
@Configuration
@EnableTransactionManagement
public class SpringBootJpaApplication extends WebMvcConfigurationSupport {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootJpaApplication.class, args);
	}

	/**
	 * 配置Cors
	 */
	@Override
	protected void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins("*").allowedMethods("*").allowedHeaders("*").allowCredentials(true).maxAge(3600);
		super.addCorsMappings(registry);
	}

	/**======================================配置提交数据Trim操作===================================*/
	private class ParamsFilter implements Filter {
		@Override
		public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
				throws IOException, ServletException {
			// TODO Auto-generated method stub
			ParameterRequestWrapper parmsRequest = new ParameterRequestWrapper((HttpServletRequest) request);
			chain.doFilter(parmsRequest, response);
		}
	}
	private class ParameterRequestWrapper extends HttpServletRequestWrapper {
		private Map<String, String[]> params = new HashMap<String, String[]>();

		public ParameterRequestWrapper(HttpServletRequest request) {
			// 将request交给父类，以便于调用对应方法的时候，将其输出，其实父亲类的实现方式和第一种new的方式类似
			super(request);
			// 将参数表，赋予给当前的Map以便于持有request中的参数
			Map<String, String[]> requestMap = request.getParameterMap();
			this.params.putAll(requestMap);
			this.modifyParameterValues();
		}

		/**
		 * 重写getInputStream方法 post类型的请求参数必须通过流才能获取到值
		 */
		@Override
		public ServletInputStream getInputStream() throws IOException {
			// 非json类型，直接返回
			if (!super.getHeader(HttpHeaders.CONTENT_TYPE).equalsIgnoreCase(MediaType.APPLICATION_JSON_VALUE)) {
				return super.getInputStream();
			}
			// 为空，直接返回
			String json = IOUtils.toString(super.getInputStream(), "utf-8");
			if (StringUtils.isEmpty(json)) {
				return super.getInputStream();
			}
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> map = mapper.readValue(json, Map.class);
			ByteArrayInputStream bis = new ByteArrayInputStream(mapper.writeValueAsString(map).getBytes("utf-8"));
			return new MyServletInputStream(bis);
		}

		/**
		 * 将parameter的值去除空格后重写回去
		 */
		public void modifyParameterValues() {
			Set<String> set = params.keySet();
			Iterator<String> it = set.iterator();
			while (it.hasNext()) {
				String key = (String) it.next();
				String[] values = params.get(key);
				values[0] = values[0].trim();
				params.put(key, values);
			}
		}

		/**
		 * 重写getParameter 参数从当前类中的map获取
		 */
		@Override
		public String getParameter(String name) {
			String[] values = params.get(name);
			if (values == null || values.length == 0) {
				return null;
			}
			return values[0];
		}

		/**
		 * 重写getParameterValues
		 */
		public String[] getParameterValues(String name) {// 同上
			return params.get(name);
		}

		private class MyServletInputStream extends ServletInputStream {
			private ByteArrayInputStream bis;

			public MyServletInputStream(ByteArrayInputStream bis) {
				this.bis = bis;
			}

			@Override
			public boolean isFinished() {
				return true;
			}

			@Override
			public boolean isReady() {
				return true;
			}

			@Override
			public void setReadListener(ReadListener listener) {

			}

			@Override
			public int read() throws IOException {
				return bis.read();
			}
		}
	}
	/**======================================配置提交数据Trim操作结束===================================*/


	@Override
	protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		MappingJackson2HttpMessageConverter converter = mappingJackson2HttpMessageConverter();
		converters.add(converter);
		super.configureMessageConverters(converters);
	}


	public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
		ObjectMapper objectMapper = new ObjectMapper();
		Hibernate5Module hibernate5Module = new Hibernate5Module();
		hibernate5Module.configure(Hibernate5Module.Feature.USE_TRANSIENT_ANNOTATION, false);
		objectMapper.registerModule(hibernate5Module);
		objectMapper.setTimeZone(TimeZone.getTimeZone("Asia/Chongqing"));
		MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter(objectMapper);
		return jsonConverter;
	}

}
