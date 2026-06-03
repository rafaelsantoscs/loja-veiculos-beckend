package iam.solucoesdigitais.baseappjwt.security;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import iam.solucoesdigitais.baseappjwt.ApplicationContextLoad;
import iam.solucoesdigitais.baseappjwt.model.Usuario;
import iam.solucoesdigitais.baseappjwt.repository.UsuarioRepository;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;


/*Criar a autenticação e retonar também a autenticação JWT*/
@Service
@Component
public class JWTTokenAutenticacaoService {
	
	/*Chave de senha para juntar com o JWT*/
	private static final String SECRET_KEY = "Kinho147!@#$";
	
	private static final String TOKEN_PREFIX = "Bearer";
	
	private static final String HEADER_STRING = "Authorization";
	
	public String generateToken(UserDetails userDetails) {
	    List<String> roles = userDetails.getAuthorities().stream()
	        .map(GrantedAuthority::getAuthority)
	        .collect(Collectors.toList());

	    return Jwts.builder()
	        .setSubject(userDetails.getUsername())
	        .claim("roles", roles)  // Aqui você adiciona as roles ao token
	        .setIssuedAt(new Date(System.currentTimeMillis()))
//	        .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))  10 HORAS
	        .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 5))  // 5 DIAS
	        .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
	        .compact();
	}
    // Adiciona o token à resposta HTTP
    public void addAuthentication(HttpServletResponse response, String username) throws Exception {
    	
    	// Busca o usuário pelo username
        Usuario usuario = ApplicationContextLoad
            .getApplicationContext()
            .getBean(UsuarioRepository.class)
            .findUserByUsername(username);
        if (usuario != null) {
	        String token = TOKEN_PREFIX + " " + generateToken(usuario); // Aqui passamos o objeto usuario (que implementa UserDetails)
	        
	        // Adiciona o token no header da resposta
	        response.addHeader(HEADER_STRING, token);
	        liberacaoCors(response);
	
	        // Adiciona o token no corpo da resposta (para testes com Postman)
	        response.getWriter().write("{\"Authorization\": \"" + token + "\"}");
        } else {
            throw new Exception("Usuário não encontrado.");
        }
    }
    
    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public String extractUsername(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    private boolean isTokenExpired(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getExpiration()
                .before(new Date());
    }
	/*Retorna o usuário validado com token ou caso nao seja valido retona null*/
	public Authentication getAuthetication(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		String token = request.getHeader(HEADER_STRING);
		
		try {
		
		if (token != null) {
			
			String tokenLimpo = token.replace(TOKEN_PREFIX, "").trim();
			
			/*Faz a validacao do token do usuário na requisicao e obtem o USER*/
			String user = Jwts.parser().
					setSigningKey(SECRET_KEY)
					.parseClaimsJws(tokenLimpo)
					.getBody().getSubject(); /*ADMIN ou Alex*/
			
			if (user != null) {
				
				Usuario usuario = ApplicationContextLoad.
						getApplicationContext().
						getBean(UsuarioRepository.class).findUserByUsername(user);
				
				if (usuario != null) {
					return new UsernamePasswordAuthenticationToken(
							usuario.getUsername(),
							usuario.getPassword(), 
							usuario.getAuthorities());
				}
				
			}
			
		}
		
		}catch (SignatureException e) {
			response.getWriter().write("Token está inválido.");

		}catch (ExpiredJwtException e) {
			response.getWriter().write("Token está expirado, efetue o login novamente.");
		}
		finally {
			liberacaoCors(response);
		}
		
		return null;
	}
	
	
	/*Fazendo liberação contra erro de COrs no navegador*/
	private void liberacaoCors(HttpServletResponse response) {
		
		if (response.getHeader("Access-Control-Allow-Origin") == null) {
			response.addHeader("Access-Control-Allow-Origin", "*");
		}
		
		if (response.getHeader("Access-Control-Allow-Headers") == null) {
			response.addHeader("Access-Control-Allow-Headers", "*");
		}
		
		if (response.getHeader("Access-Control-Request-Headers") == null) {
			response.addHeader("Access-Control-Request-Headers", "*");
		}
		
		if (response.getHeader("Access-Control-Allow-Methods") == null) {
			response.addHeader("Access-Control-Allow-Methods", "*");
		}
	}

}
