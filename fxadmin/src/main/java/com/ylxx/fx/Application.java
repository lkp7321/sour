package com.ylxx.fx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ImportResource;

@ImportResource("classpath:application-context.xml")
@SpringBootApplication
//@EnableScheduling
public class Application  extends SpringBootServletInitializer /* implements EmbeddedServletContainerCustomizer */{

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}
	
	public static void main(String[] args) throws Exception {
		SpringApplication.run(Application.class, args);
	}
	
	/*@Override
	public void customize(ConfigurableEmbeddedServletContainer container) {
		container.setPort(8281);
	}*/
}
