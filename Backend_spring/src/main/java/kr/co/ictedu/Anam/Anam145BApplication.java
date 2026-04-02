package kr.co.ictedu.Anam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class Anam145BApplication {
	public static void main(String[] args) {
		SpringApplication.run(Anam145BApplication.class, args);
	}
	@Bean
	public WebMvcConfigurer crosConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
				.allowedOrigins("http://localhost:3000","http://192.168.0.XX:3000")		// 프론트 Origin 작성 요망
				.allowedHeaders("*")
				.allowCredentials(true)
				.allowedMethods("*").maxAge(3600);
			}
		};
	}
}
