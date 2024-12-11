package cn.newbeedaly.springnative.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.List;

@Configuration
public class GlobalCorsConfig {

    private static final List<String> ORIGINS = Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS");
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        // 放行原始域
        config.addAllowedOriginPattern("*");
        // 是否发送 Cookie
        config.setAllowCredentials(true);
        // 放行请求方式
        config.setAllowedMethods(ORIGINS);
        // 放行原始请求头部信息
        config.addAllowedHeader("*");
        // 暴露头部信息
        config.addExposedHeader("*");
        // 映射路径
        UrlBasedCorsConfigurationSource corsConfigurationSource = new UrlBasedCorsConfigurationSource();
        corsConfigurationSource.registerCorsConfiguration("/**", config);
        return new CorsFilter(corsConfigurationSource);
    }
}
