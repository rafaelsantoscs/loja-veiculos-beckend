package iam.solucoesdigitais.baseappjwt.security;

import javax.servlet.http.HttpSessionListener;



import iam.solucoesdigitais.baseappjwt.service.ImplementacaoUserDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.core.annotation.Order;
import org.springframework.core.Ordered;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebConfigSecurity extends WebSecurityConfigurerAdapter implements HttpSessionListener {
	
	@Autowired
	private ImplementacaoUserDetailsService implementacaoUserDetailsService;
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		
		http
		.cors() 
        .and()
		.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
		.disable()
		.authorizeRequests()
		.antMatchers("/").permitAll()
		.antMatchers(HttpMethod.GET, "/colaboradores").permitAll()
        .antMatchers("/colaboradores/**").authenticated()
        
        // APIs protegidas que requerem autenticação
        .antMatchers("/api/setores/**").authenticated()
        .antMatchers("/api/unidades/**").authenticated()
        .antMatchers("/api/materiais/**").authenticated()
        .antMatchers("/api/chamados/**").authenticated()
        .antMatchers("/api/denuncias/**").authenticated()
        .antMatchers("/api/estabelecimentos/**").authenticated()
        .antMatchers("/api/responsavel-legal/**").authenticated()
        .antMatchers("/api/responsavel-tecnico/**").authenticated()
        .antMatchers("/api/definicao-porte/**").authenticated()
        .antMatchers("/api/solicitacoes/**").authenticated()
        .antMatchers("/api/slides/**").authenticated()
        
        // APIs parcialmente públicas
        .antMatchers(HttpMethod.GET, "/api/postagens/publicas/**").permitAll()
        .antMatchers(HttpMethod.GET, "/api/postagens/destaque").permitAll()
        .antMatchers(HttpMethod.GET, "/api/postagens/faq").permitAll()
        .antMatchers("/api/postagens/**").authenticated()
        
        // Endpoints de autenticação e recursos públicos
        .antMatchers("/auth/**").permitAll()
        .antMatchers("/password/**").permitAll()
        .antMatchers("/uploads/**").permitAll()
        .antMatchers("/pre-cadastro/**").permitAll()
        
        // Configurações para postagens
        .antMatchers(HttpMethod.GET, "/api/postagens/publicas/**").permitAll()
        .antMatchers(HttpMethod.GET, "/api/postagens/destaque").permitAll()
        .antMatchers(HttpMethod.GET, "/api/postagens/faq").permitAll()
        .antMatchers(HttpMethod.GET, "/api/postagens/faq/destaque").permitAll()
        .antMatchers(HttpMethod.GET, "/api/postagens/categorias").permitAll()
        .antMatchers(HttpMethod.GET, "/api/postagens/{id}").permitAll()
        .antMatchers(HttpMethod.PATCH, "/api/postagens/{id}/visualizar").permitAll()
        .antMatchers("/api/postagens/**").authenticated()
        .antMatchers("/api/anexos/**").authenticated()
		.antMatchers( "/index").permitAll() // Permitir acesso à URL específica
		.antMatchers("/usuarios/salvar-usuario").permitAll() // Permitir acesso a URL específica
		.antMatchers("/usuarios/listar-todos").hasRole("ADMIN")  // Restringe a rota para ROLE_ADMIN
		.antMatchers("/usuarios/validar-email").permitAll() // testando validador de dominio
		.antMatchers("/usuarios/confirmar-email").permitAll() //confirmação de email após criar o usuário
		.antMatchers("/pre-cadastro/**").permitAll() //permitrir precadastro
        .antMatchers("/auth/login").permitAll() // Permite acesso sem autenticação para /auth
        .antMatchers("/uploads/**").permitAll()
        .antMatchers("/password/**").permitAll() // Permite acesso sem autenticação para /auth
        .antMatchers("/usuarios/nome/**").authenticated() // Requer autenticação para /patrimonios
        .antMatchers("/dependente/**").permitAll()
        .antMatchers("/funcionario/**").authenticated()// Requer autenticação para /patrimonios
		.antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
		
		/* redireciona ou da um retorno para index quando desloga*/
		.anyRequest().authenticated().and().logout().logoutSuccessUrl("/index")
		
		/*mapeia o logout do sistema*/
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		
		/*Filtra as requisicoes para login de JWT*/
		.and().addFilterAfter(new JWTLoginFilter("/login", authenticationManager()),
				UsernamePasswordAuthenticationFilter.class)
		
		.addFilterBefore(new JwtApiAutenticacaoFilter(), UsernamePasswordAuthenticationFilter.class);
		 // Adiciona o suporte a CORS
		
	}
	
	
	/*Irá consultar o user no banco com Spring Security
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(implementacaoUserDetailsService).passwordEncoder(new BCryptPasswordEncoder());
		
	}*/
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	    auth.userDetailsService(implementacaoUserDetailsService)
	        .passwordEncoder(new BCryptPasswordEncoder());
	}
	
	

	
	

	/*Ignora alguas URL livre de autenticação*/
	@Override
	public void configure(WebSecurity web) throws Exception {
		//web.ignoring().antMatchers(HttpMethod.GET, "/salvarAcesso", "/deleteAcesso")
		//.antMatchers(HttpMethod.POST, "/salvarAcesso", "/deleteAcesso");
		/*Ingnorando URL no momento para nao autenticar*/
	}
	
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
	        config.addAllowedOrigin("http://192.168.20.39:3000");
	        
	        
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
