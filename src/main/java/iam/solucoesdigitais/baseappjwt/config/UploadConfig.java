package iam.solucoesdigitais.baseappjwt.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UploadConfig {

    @Value("${app.upload.veiculos-dir}")
    private String uploadVeiculosDir;

    @Value("${app.upload.base-url}")
    private String baseUrl;

    public String getUploadVeiculosDir() {
        return uploadVeiculosDir;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

}