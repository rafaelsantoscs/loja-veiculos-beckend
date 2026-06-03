package iam.solucoesdigitais.baseappjwt.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class StaticResourceConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Detectar sistema operacional
        String os = System.getProperty("os.name").toLowerCase();
        
        if (os.contains("windows")) {
            // Windows - desenvolvimento
            registry.addResourceHandler("/visa-vsa/uploads/**")
                    .addResourceLocations("file:C:/uploads/");
        } else {
            // Linux - produção
            registry.addResourceHandler("/visa-vsa/uploads/**")
                    .addResourceLocations("file:/var/uploads/");
        }
    }
}
