package iam.solucoesdigitais.baseappjwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EntityScan(basePackages = "iam.solucoesdigitais.baseappjwt.model")
@ComponentScan(basePackages = {"iam.solucoesdigitais.*"})
@EnableJpaRepositories(basePackages = {"iam.solucoesdigitais.baseappjwt.repository"})
@EnableTransactionManagement
@SpringBootApplication
public class BaseAppJwtApplication {

	public static void main(String[] args) {
		SpringApplication.run(BaseAppJwtApplication.class, args);
	}

}
