package project.livestreaming.core.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsMvcConfig implements WebMvcConfigurer {

    /**
     * 모든 우리의 컨트롤러 경로에 대해
     * .allowedOrigins("http://localhost:3000")
     * 허용
     * Spring Security가 처리하지 않는 요청에 대해서도 CORS 정책이 적용
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {

        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000");

    }
}
