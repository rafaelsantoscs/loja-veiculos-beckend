package iam.solucoesdigitais.baseappjwt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.core.annotation.Order;
import org.springframework.core.Ordered;

@Configuration
public class CorsConfig {
	




    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        
        // Permitir credenciais
        config.setAllowCredentials(true);
        
        // Origens permitidas
        config.addAllowedOrigin("http://localhost:3000");
        config.addAllowedOrigin("http://192.168.20.222:3000");
        config.addAllowedOrigin("http://145.223.92.20:3001");
        config.addAllowedOrigin("http://localhost:3005");
        config.addAllowedOrigin("http://147.79.81.216:3005");
        config.addAllowedOrigin("http://srv798564.hstgr.cloud:3005");
        config.addAllowedOrigin("https://visa.iamtec.org");
        config.addAllowedOrigin("https://api-visa.iamtec.org");
        
        // Headers permitidos
        config.addAllowedHeader("*");
        
        // Métodos permitidos
        config.addAllowedMethod("GET");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("PATCH");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("OPTIONS");
        
        // Headers expostos
        config.addExposedHeader("Authorization");
        config.addExposedHeader("Access-Control-Allow-Origin");
        config.addExposedHeader("Access-Control-Allow-Credentials");
        config.addExposedHeader("Content-Disposition");
        
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    



 }
}
