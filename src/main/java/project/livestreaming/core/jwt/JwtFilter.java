package project.livestreaming.core.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import project.livestreaming.core.dto.CustomUserDetails;
import project.livestreaming.core.domain.User;
import project.livestreaming.core.service.JwtService;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 헤더에서 access키에 담긴 토큰을 꺼냄
        String accessToken = request.getHeader("access");

        // 토큰이 없다면 다음 필터로 넘김
        if (accessToken == null) {

            filterChain.doFilter(request, response);

            return;
        }

        // 토큰 만료 여부 확인, 만료시 다음 필터로 넘기지 않음
        try {
            jwtService.isExpired(accessToken);
        } catch (ExpiredJwtException e) {

            //response body
            PrintWriter writer = response.getWriter();
            writer.print("access token expired");

            //response status code
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        // 토큰이 access인지 확인 (발급시 페이로드에 명시)
        String category = jwtService.getCategory(accessToken);

        if (!category.equals("access")) {

            //response body
            PrintWriter writer = response.getWriter();
            writer.print("invalid access token");

            //response status code
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        // username, role 값을 획득
        String username = jwtService.getUsername(accessToken);
        List<String> roles = jwtService.getRole(accessToken);

        //userEntity를 생성하여 값 set
        User user = User.builder()
                .username(username)
                .password("temppassword") // 비밀번호 조회안하기 위해 임시값 설정
                .build();

        //UserDetails에 회원 정보 객체 담기
        CustomUserDetails customUserDetails = new CustomUserDetails(user);

        //권한정보
        Set<SimpleGrantedAuthority> authorities = roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());

        //스프링 시큐리티 인증 토큰 생성
        Authentication authentication = new UsernamePasswordAuthenticationToken(customUserDetails, null, authorities);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);


    }
}
