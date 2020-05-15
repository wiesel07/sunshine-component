package cn.sunshine.common.config;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

import javax.servlet.DispatcherType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import cn.sunshine.common.filter.XssFilter;
import cn.sunshine.component.runtime.web.interceptor.WebInterceptor;

/**
 * <p>
 * WebMvcConfigurer实现类
 * </p>
 *
 * @author wuj
 * @since 2019年6月20日
 */
@Configuration
public class WebConfigurer implements WebMvcConfigurer {

	@Autowired
	List<WebInterceptor> interceptorList;

	// xss 过滤器
	@Bean
	public FilterRegistrationBean<XssFilter> xssFilterRegistration() {
		FilterRegistrationBean<XssFilter> registration = new FilterRegistrationBean<XssFilter>();
		registration.setDispatcherTypes(DispatcherType.REQUEST);
		registration.setFilter(new XssFilter());
		registration.addUrlPatterns("/*");
		registration.setName("xssFilter");
		registration.setOrder(Integer.MAX_VALUE);
		return registration;
	}

     // 注册监听器 同时设置其拦截路径
	@Override
	public void addInterceptors(InterceptorRegistry registry) {

		if (interceptorList != null) {
			for (WebInterceptor interceptor : interceptorList) {
				registry.addInterceptor(interceptor).addPathPatterns(interceptor.patternUrl())
						.order(interceptor.order());
			}
		}
	}

	@Bean
	@Primary
//	@ConditionalOnMissingBean(ObjectMapper.class)
	public ObjectMapper jacksonObjectMapper(Jackson2ObjectMapperBuilder builder) {
		ObjectMapper objectMapper = builder.createXmlMapper(false).build();
		/**
		 * 全局配置序列化返回 JSON 处理解决前端long精度丢失问题
		 */
		SimpleModule module = new SimpleModule();
		module.addSerializer(Long.class, ToStringSerializer.instance);
		module.addSerializer(Long.TYPE, ToStringSerializer.instance);
		module.addSerializer(BigInteger.class, ToStringSerializer.instance);
		objectMapper.registerModule(module);
		objectMapper.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
			@Override
			public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
				gen.writeString("");
			}
		});
		return objectMapper;
	}
}
