package project.livestreaming.core.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import project.livestreaming.core.jwt.JWTFilter;
import project.livestreaming.core.jwt.JWTUtil;
import project.livestreaming.core.jwt.LoginFilter;
import project.livestreaming.core.repository.UserRepository;

import java.util.Collections;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    //AuthenticationManager가 인자로 받을 AuthenticationConfiguraion 객체 생성자 주입
    private final AuthenticationConfiguration authenticationConfiguration;
    private final JWTUtil jwtUtil;
    private final ObjectMapper objectMapper;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // cors 설정후 CorsMvcConfig implements WebMvcConfigurer 설정 해줘야함
        http.cors(
                (cors) -> cors
                        .configurationSource(corsConfigurationSource -> {

                            CorsConfiguration config = new CorsConfiguration();

                            // 허용할 서버 포트
                            config.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));
                            // 허용할 메서드
                            config.setAllowedMethods(Collections.singletonList("*"));
                            // 인증 정보를 포함한 CORS 요청
                            config.setAllowCredentials(true);
                            // 허용할 헤더
                            config.setAllowedHeaders(Collections.singletonList("*"));
                            // 허용 시간
                            config.setMaxAge(3600L);
                            // Authorization 헤더 허용
                            config.setExposedHeaders(Collections.singletonList("Authorization"));

                            return config;
                        })
        );

        //csrf disable
        http.csrf(
                AbstractHttpConfigurer::disable
        );

        //From 로그인 방식 disable
        http.formLogin(
                AbstractHttpConfigurer::disable
        );

        //http basic 인증 방식 disable
        http.httpBasic(
                AbstractHttpConfigurer::disable
        );

        http.headers(
                (headers) -> headers
                        .frameOptions(
                                HeadersConfigurer.FrameOptionsConfig::sameOrigin)
        );

        //경로별 인가 작업
        http.authorizeHttpRequests(
                (auth) -> auth
                        .requestMatchers(
                                "/h2-console/**",
                                "/",
                                "/api/v1/user/join",
                                "/login",
                                "/api/v1/reissue"
                        ).permitAll()
                        .requestMatchers("/api/v1/admin").hasAuthority("ADMIN")
                        .anyRequest().authenticated()
        );

        //로그인 검증 필터 추가 UsernamePasswordAuthenticationFilter 자리에 커스텀 필터 대체
        http.addFilterAt(
                new LoginFilter(
                        authenticationManager(authenticationConfiguration),jwtUtil,objectMapper), UsernamePasswordAuthenticationFilter.class
        );

        // JWTFILTER 추가 로그인 필터 앞에 넣기
        http.addFilterBefore(
               new JWTFilter(jwtUtil), LoginFilter.class
        );

        //세션 설정
        http.sessionManagement(
                (session) -> session
                        .sessionCreationPolicy(STATELESS)
        );

        return http.build();
    }

    //AuthenticationManager Bean 등록
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {

        return configuration.getAuthenticationManager();
    }


    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // 정적 리소스 spring security 대상에서 제외
        return (web) ->
                web
                        .ignoring()
                        .requestMatchers(
                                PathRequest.toStaticResources().atCommonLocations()
                        );
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
