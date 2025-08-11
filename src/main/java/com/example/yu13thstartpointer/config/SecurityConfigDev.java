package com.example.yu13thstartpointer.config;

// SecurityConfig.java


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.*;

import java.util.List;

@Configuration
public class SecurityConfigDev {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // ✅ CORS (아래의 corsConfigurationSource() 사용)
                .cors(Customizer.withDefaults())

                // ❗ CSRF: 세션 기반이면 원칙적으로 활성 유지 권장(폼/쿠키 보호).
                //    Swagger/SPA 호출 편의상 임시로 비활성화하는 팀도 있음.
                .csrf(csrf -> csrf.disable())

                // ✅ 세션 정책: STATELESS가 아니어야 세션이 생성/유지됩니다.
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/users/login", "/api/v1/users/sign-up").permitAll()
                        .anyRequest().permitAll() // 필요시 인증 규칙 조정
                );

        return http.build();
    }

    /** ✅ CORS 설정: 허용 도메인/메서드 지정 + allowCredentials(true) */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration cfg = new CorsConfiguration();

        // 배포 시 정확한 오리진으로 제한하세요.
        cfg.setAllowedOriginPatterns(List.of(
                "http://localhost:3000",
                "http://127.0.0.1:3000",
                "https://your-frontend.example.com",
                "http://localhost:8080" // Swagger UI가 같은 서버면 자동 same-origin
        ));

        cfg.setAllowedMethods(List.of("GET","POST","PUT","PATCH","DELETE","OPTIONS"));
        cfg.setAllowedHeaders(List.of("Content-Type", "Authorization", "X-Requested-With"));
        cfg.setExposedHeaders(List.of("Set-Cookie")); // 선택: 클라이언트에서 읽어야 할 헤더
        cfg.setAllowCredentials(true); // ✅ 크로스도메인 쿠키 허용

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", cfg);
        return source;
    }
}
