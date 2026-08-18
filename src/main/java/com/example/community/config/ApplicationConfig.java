package com.example.community.config;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.*;
@Configuration
public class ApplicationConfig {
  @Bean MybatisPlusInterceptor pagination() { var i = new MybatisPlusInterceptor(); i.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL)); return i; }
  @Bean PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }
  @Bean WebMvcConfigurer cors(@Value("${app.cors.allowed-origins}") String[] origins) {
    return new WebMvcConfigurer() { @Override public void addCorsMappings(CorsRegistry r) { r.addMapping("/api/**").allowedOrigins(origins).allowedMethods("GET","POST","PUT","DELETE","OPTIONS"); } };
  }
}
