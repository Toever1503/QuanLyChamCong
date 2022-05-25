package com.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.io.File;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    public static String HOST;
    public static String ROOT_CONTENT_SYS;

    WebConfiguration(@Value("${web-content.root-content}") String ROOT_CONTENT_SYS,
                     @Value("${web-content.host}") String HOST) {
        WebConfiguration.HOST = HOST;
        ROOT_CONTENT_SYS = new File(ROOT_CONTENT_SYS).getAbsolutePath();
        System.out.println(ROOT_CONTENT_SYS);
        WebConfiguration.ROOT_CONTENT_SYS = ROOT_CONTENT_SYS;
        System.out.println(ROOT_CONTENT_SYS);
    }

    @Bean
    public WebMvcConfigurer configurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("10.0.0.109:8081", "10.0.0.109:8080", "10.0.0.83:8080")
                        .allowedOriginPatterns("*.*.*.*:*")
                        .allowCredentials(true)
                        .allowedMethods("GET", "POST", "DELETE", "PUT", "PATCH", "OPTIONS");
            }
        };
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**").addResourceLocations("file:".concat(ROOT_CONTENT_SYS + "/"));
    }
}
