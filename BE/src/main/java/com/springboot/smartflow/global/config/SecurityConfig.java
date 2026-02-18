package com.springboot.smartflow.global.config;

import com.springboot.smartflow.global.auth.JwtAuthenticationFilter;
import com.springboot.smartflow.global.auth.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
// [필수 추가] 이 임포트가 없으면 UsernamePasswordAuthenticationFilter.class를 인식 못함
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    
    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                // JWT 사용 시 세션 정책은 무조건 STATELESS
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // 1. 공통 허용 경로
                        .requestMatchers("/api/auth/**", "/error").permitAll()
                        .requestMatchers("/", "/login", "/signup", "/main", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .requestMatchers("/WEB-INF/views/**").permitAll()
                        // 2. 알람 API는 인증 필요 (이제 @AuthenticationPrincipal 사용 가능)
                        .requestMatchers("/api/alarm/**").authenticated() 
                        .anyRequest().authenticated()
                )
                // [필터 등록]
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), 
                                UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(
                PathRequest.toStaticResources().atCommonLocations()
        );
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}