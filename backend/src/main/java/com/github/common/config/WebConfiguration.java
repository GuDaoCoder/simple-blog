package com.github.common.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.stp.StpUtil;
import com.github.common.interceptor.UserDetailInterceptor;
import com.github.common.properties.SecurityProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web相关配置
 *
 * @author Gudao
 * @since 2024/7/28
 */
@RequiredArgsConstructor
@Configuration
public class WebConfiguration implements WebMvcConfigurer {

	private final SecurityProperties securityProperties;

	private final UserDetailInterceptor userDetailInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 注册 Sa-Token 拦截器
		registry.addInterceptor(new SaInterceptor(handle -> StpUtil.checkLogin()))
			.addPathPatterns("/**")
			.excludePathPatterns(securityProperties.getWhiteUrls().toArray(new String[0]));
		// 用户拦截器
		registry.addInterceptor(userDetailInterceptor)
			.addPathPatterns("/**")
			.excludePathPatterns(securityProperties.getWhiteUrls().toArray(new String[0]));
	}

	/**
	 * 跨域配置
	 * @return CorsFilter
	 **/
	@Bean
	public CorsFilter corsFilter() {
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		// 设置允许访问源地址
		config.addAllowedOriginPattern("*");
		// 设置允许访问源请求头
		config.addAllowedHeader("*");
		// 设置允许访问源请求方法
		config.addAllowedMethod("*");
		// 设置有效期1800秒
		config.setMaxAge(1800L);
		// 添加映射路径，拦截一切请求
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		// 返回新的CorsFilter
		return new CorsFilter(source);
	}

}
