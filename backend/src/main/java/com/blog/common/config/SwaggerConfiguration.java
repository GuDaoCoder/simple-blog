package com.blog.common.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger文档配置
 *
 * @author Gudao
 * @since 2024/8/14
 */
@Configuration
public class SwaggerConfiguration {

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI().info(new Info().title("博客系统")
			.version("1.0")
			.contact(new Contact().name("Gudao").email("zouzhangpeng@163.com"))
			.description("一个简单的博客系统")
			.termsOfService("http://www.road4code.com"));
	}

}
