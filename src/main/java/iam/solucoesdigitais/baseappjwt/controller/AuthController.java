package iam.solucoesdigitais.baseappjwt.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import iam.solucoesdigitais.baseappjwt.dto.LoginResponse;
import iam.solucoesdigitais.baseappjwt.model.Usuario;
import iam.solucoesdigitais.baseappjwt.security.JWTTokenAutenticacaoService;
import iam.solucoesdigitais.baseappjwt.service.UsuarioService;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JWTTokenAutenticacaoService jwtTokenService;
    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(JWTTokenAutenticacaoService jwtTokenService, UsuarioService usuarioService, PasswordEncoder passwordEncoder) {
        this.jwtTokenService = jwtTokenService;
        this.usuarioService = usuarioService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario usuario) {
        try {
            Optional<Usuario> usuarioEncontradoOpt = usuarioService.buscarPorLogin(usuario.getUsername());

            if (!usuarioEncontradoOpt.isPresent()) {
                usuarioEncontradoOpt = usuarioService.buscarPorEmail(usuario.getUsername());
            }

            if (usuarioEncontradoOpt.isPresent()) {
                Usuario usuarioEncontrado = usuarioEncontradoOpt.get(); // ← Declara aqui

                // Verifica se a senha está correta
                if (passwordEncoder.matches(usuario.getPassword(), usuarioEncontrado.getPassword())) {
                    String token = jwtTokenService.generateToken(usuarioEncontrado);

                    LoginResponse loginResponse = new LoginResponse(
                            token,
                            usuarioEncontrado.getNome(),
                            usuarioEncontrado.getUsername(),
                            usuarioEncontrado.getCpf(),
                            usuarioEncontrado.getEmail(),
                            usuarioEncontrado.getTelefone(),
                            usuarioEncontrado.getImagemUrl(),
                            usuarioEncontrado.getId()
                    );

                    return ResponseEntity.ok(loginResponse);
                }
            }

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário ou senha inválidos");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao autenticar o usuário");
        }
    }

    
    @GetMapping("/validate-token")
    public ResponseEntity<String> validateToken(@RequestHeader("Authorization") String authorizationHeader) {
        try {
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido");
            }
            
            String token = authorizationHeader.substring(7); // Remove o prefixo "Bearer "
            Optional<Usuario> usuarioOpt = usuarioService.buscarPorLogin(jwtTokenService.extractUsername(token));
            
            if (usuarioOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido");
            }
            
            Usuario usuario = usuarioOpt.get();
            
            // Se Usuario implementar UserDetails, você pode usar diretamente
            // Se não, crie uma implementação de UserDetails para o usuário ou faça a conversão
            UserDetails userDetails = usuario; // Supondo que Usuario implementa UserDetails

            // Validação do token
            if (jwtTokenService.validateToken(token, userDetails)) {
                return ResponseEntity.ok("Token é válido");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido");
        }
    }

}
