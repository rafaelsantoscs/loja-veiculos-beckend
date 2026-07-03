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
import org.springframework.web.client.RestTemplate;

import iam.solucoesdigitais.baseappjwt.dto.GoogleAuthRequest;
import iam.solucoesdigitais.baseappjwt.dto.LoginResponse;
import iam.solucoesdigitais.baseappjwt.model.Usuario;
import iam.solucoesdigitais.baseappjwt.repository.UsuarioRepository;
import iam.solucoesdigitais.baseappjwt.security.JWTTokenAutenticacaoService;
import iam.solucoesdigitais.baseappjwt.service.UsuarioService;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JWTTokenAutenticacaoService jwtTokenService;
    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;
    private final UsuarioRepository usuarioRepository;

    public AuthController(JWTTokenAutenticacaoService jwtTokenService,
                          UsuarioService usuarioService,
                          PasswordEncoder passwordEncoder,
                          UsuarioRepository usuarioRepository) {
        this.jwtTokenService = jwtTokenService;
        this.usuarioService = usuarioService;
        this.passwordEncoder = passwordEncoder;
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario usuario) {
        try {
            Optional<Usuario> usuarioEncontradoOpt = usuarioService.buscarPorLogin(usuario.getUsername());

            if (!usuarioEncontradoOpt.isPresent()) {
                usuarioEncontradoOpt = usuarioService.buscarPorEmail(usuario.getUsername());
            }

            if (usuarioEncontradoOpt.isPresent()) {

                Usuario usuarioEncontrado = usuarioEncontradoOpt.get();


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


    @PostMapping("/google")
    public ResponseEntity<?> googleLogin(@RequestBody GoogleAuthRequest request) {
        try {
            if (request.getCredential() == null || request.getCredential().isBlank()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Credential Google ausente");
            }

            // Verifica o token com o endpoint público do Google
            RestTemplate restTemplate = new RestTemplate();
            String tokenInfoUrl = "https://oauth2.googleapis.com/tokeninfo?id_token=" + request.getCredential();

            ResponseEntity<Map> tokenInfo;
            try {
                tokenInfo = restTemplate.getForEntity(tokenInfoUrl, Map.class);
            } catch (Exception ex) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token Google inválido");
            }

            if (tokenInfo.getStatusCode() != HttpStatus.OK || tokenInfo.getBody() == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token Google inválido");
            }

            Map<String, Object> payload = tokenInfo.getBody();
            String email    = (String) payload.get("email");
            String nome     = (String) payload.get("name");
            String picture  = (String) payload.get("picture");
            String googleId = (String) payload.get("sub");

            if (email == null || googleId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Dados insuficientes do Google");
            }

            // Busca usuário existente por googleId ou email
            Optional<Usuario> usuarioOpt = usuarioRepository.findByGoogleId(googleId);
            if (!usuarioOpt.isPresent()) {
                usuarioOpt = usuarioService.buscarPorEmail(email);
            }

            Usuario usuario;
            if (usuarioOpt.isPresent()) {
                usuario = usuarioOpt.get();
                // Vincula googleId e atualiza foto se ainda não estiver definido
                boolean atualizar = false;
                if (usuario.getGoogleId() == null) {
                    usuario.setGoogleId(googleId);
                    atualizar = true;
                }
                if (picture != null && (usuario.getImagemUrl() == null || usuario.getImagemUrl().isBlank())) {
                    usuario.setImagemUrl(picture);
                    atualizar = true;
                }
                if (atualizar) {
                    usuarioRepository.save(usuario);
                }
            } else {
                // Gera username único a partir do email
                String baseUsername = email.split("@")[0].replaceAll("[^a-zA-Z0-9]", "");
                if (baseUsername.isEmpty()) baseUsername = "usuario";
                String username = baseUsername;
                int suffix = 1;
                while (usuarioService.buscarPorLogin(username).isPresent()) {
                    username = baseUsername + suffix++;
                }

                Usuario novoUsuario = new Usuario();
                novoUsuario.setEmail(email);
                novoUsuario.setNome(nome != null ? nome : email.split("@")[0]);
                novoUsuario.setGoogleId(googleId);
                novoUsuario.setImagemUrl(picture);
                novoUsuario.setUsername(username);
                novoUsuario.setPassword(passwordEncoder.encode(UUID.randomUUID().toString()));
                novoUsuario.setTermo("GOOGLE_OAUTH");
                novoUsuario.setRoles(List.of("ROLE_USUARIO"));

                usuario = usuarioService.salvarUsuarioGoogle(novoUsuario);
            }

            String token = jwtTokenService.generateToken(usuario);
            LoginResponse loginResponse = new LoginResponse(
                    token,
                    usuario.getNome(),
                    usuario.getUsername(),
                    usuario.getCpf(),
                    usuario.getEmail(),
                    usuario.getTelefone(),
                    usuario.getImagemUrl(),
                    usuario.getId()
            );
            return ResponseEntity.ok(loginResponse);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao autenticar com Google: " + e.getMessage());
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
