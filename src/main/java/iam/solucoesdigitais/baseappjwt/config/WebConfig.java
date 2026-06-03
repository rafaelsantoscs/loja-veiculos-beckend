package iam.solucoesdigitais.baseappjwt.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${app.upload.dir:uploads/postagens}")
    private String uploadDir;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Override
    public void addResourceHandlers(@org.springframework.lang.NonNull ResourceHandlerRegistry registry) {
        // Configurar acesso aos uploads de postagens
        String uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize().toString();
        
        registry.addResourceHandler("/uploads/postagens/**")
                .addResourceLocations("file:" + uploadPath + "/");

        // Manter configuração existente para outros uploads
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:uploads/");
    }
}
