package com.example.community.config;

import com.example.community.mapper.UserMapper;
import com.example.community.utils.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final UserMapper userMapper;
    private final ObjectMapper objectMapper;

    @Bean
    UserDetailsService userDetailsService() {
        return username -> {
            Integer id;
            try {
                id = Integer.valueOf(username);
            } catch (NumberFormatException e) {
                throw new UsernameNotFoundException("账号或密码错误");
            }
            var user = userMapper.selectById(id);
            if (user == null) throw new UsernameNotFoundException("账号或密码错误");
            String role = Boolean.TRUE.equals(user.getUsertype()) ? "ROLE_ADMIN" : "ROLE_USER";
            return new org.springframework.security.core.userdetails.User(
                    String.valueOf(user.getUserId()), user.getPassword(), List.of(new SimpleGrantedAuthority(role)));
        };
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http,
                                            @Qualifier("corsConfigurationSource") CorsConfigurationSource corsSource) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsSource))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, "/api/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/users", "/api/users/login", "/api/users/search",
                                "/api/circles/search", "/api/follows/status").permitAll()
                        .requestMatchers("/actuator/health/**", "/actuator/info", "/swagger-ui/**",
                                "/swagger-ui.html", "/api-docs/**").permitAll()
                        .anyRequest().authenticated())
                .httpBasic(basic -> basic.authenticationEntryPoint((request, response, exception) ->
                        writeSecurityError(response, 401, "UNAUTHORIZED", "需要有效的用户凭据")))
                .exceptionHandling(errors -> errors.accessDeniedHandler((request, response, exception) ->
                        writeSecurityError(response, 403, "FORBIDDEN", "没有执行该操作的权限")))
                .build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource(
            @Value("${app.cors.allowed-origins}") String allowedOrigins) {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Arrays.stream(allowedOrigins.split(",")).map(String::trim).filter(s -> !s.isEmpty()).toList());
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("Authorization", "Content-Type", TraceIdFilter.HEADER));
        config.setExposedHeaders(List.of(TraceIdFilter.HEADER));
        config.setAllowCredentials(true);
        config.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", config);
        return source;
    }

    private void writeSecurityError(jakarta.servlet.http.HttpServletResponse response, int status,
                                    String code, String message) throws IOException {
        response.setStatus(status);
        response.setCharacterEncoding(java.nio.charset.StandardCharsets.UTF_8.name());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        objectMapper.writeValue(response.getOutputStream(), ApiResponse.error(code, message, null));
    }
}
