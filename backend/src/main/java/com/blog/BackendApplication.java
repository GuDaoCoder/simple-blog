package com.blog;

import com.blog.common.util.IpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.util.Arrays;

@Slf4j
@ConfigurationPropertiesScan
@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext application = SpringApplication.run(BackendApplication.class, args);
		Environment env = application.getEnvironment();
		if (Arrays.asList(env.getActiveProfiles()).contains("local")) {
			String message = """
					\n----------------------------------------------------------
						Application '{}' is running!:
						Local: 		http://localhost:{}
						Doc: 	http://{}:{}/doc.html
					----------------------------------------------------------\n
					""";
			log.info(message, env.getProperty("spring.application.name"), env.getProperty("server.port"),
					IpUtil.getLocalHostAddress(), env.getProperty("server.port"));
		}
	}

}
