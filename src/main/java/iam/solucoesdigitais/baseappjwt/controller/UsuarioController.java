package iam.solucoesdigitais.baseappjwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import iam.solucoesdigitais.baseappjwt.dto.AlterarSenhaRequest;
import iam.solucoesdigitais.baseappjwt.exception.CampoDuplicadoException;
import iam.solucoesdigitais.baseappjwt.model.Usuario;
import iam.solucoesdigitais.baseappjwt.service.ConfirmationTokenService;
import iam.solucoesdigitais.baseappjwt.service.UsuarioService;
import iam.solucoesdigitais.baseappjwt.util.EmailValidatorService;


import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.nio.file.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
	

    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private ConfirmationTokenService confirmationTokenService;
    
    @Autowired
    private EmailValidatorService emailValidatorService;
    
    //private static final String UPLOAD_DIR = "src/main/resources/static/uploads/";
    private static final Logger logger = LoggerFactory.getLogger(UsuarioController.class);

    // Listar todos os usuários - Restrito a ROLE_ADMIN
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/listar-todos")
    public ResponseEntity<List<Usuario>> listarTodos() {
        List<Usuario> usuarios = usuarioService.listarTodos();
        return ResponseEntity.ok(usuarios);
    }

    // Buscar usuário por ID
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Long id) {
        Optional<Usuario> usuario = usuarioService.buscarPorId(id);
        return usuario.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }
    
    @PatchMapping("/atualizar-dados/{id}")
    public ResponseEntity<Usuario> atualizarUsuarioParcial(@PathVariable Long id, @RequestBody Usuario usuario) {
        try {
            Usuario usuarioAtualizado = usuarioService.atualizarUsuarioParcial(id, usuario);
            return ResponseEntity.ok(usuarioAtualizado);
        } catch (ResponseStatusException e) {
            logger.error("Erro ao atualizar usuário: " + e.getReason(), e);
            return ResponseEntity.status(e.getStatus()).body(null);
        }
    }
    
    @PatchMapping("/atualizar-roles/{id}")
    public ResponseEntity<Usuario> atualizarRolesUsuario(
            @PathVariable Long id,
            @RequestBody Map<String, List<String>> rolesRequest) {
        try {
            List<String> novosRoles = rolesRequest.get("roles");
            if (novosRoles == null || novosRoles.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            
            Usuario usuarioAtualizado = usuarioService.atualizarRolesUsuario(id, novosRoles);
            return ResponseEntity.ok(usuarioAtualizado);
        } catch (ResponseStatusException e) {
            logger.error("Erro ao atualizar roles do usuário: " + e.getReason(), e);
            return ResponseEntity.status(e.getStatus()).body(null);
        }
    }
    
    // Buscar usuário por CPF
    @GetMapping("/buscar-por-cpf/{cpf}")
    public ResponseEntity<Usuario> buscarPorCpf(@PathVariable String cpf) {
        Optional<Usuario> usuario = usuarioService.buscarPorCpf(cpf);
        return usuario.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/nome/{nome}")
    public List<Usuario> buscarPorNome(@PathVariable String nome) {
        return usuarioService.buscarPorNome(nome);
    }
    
    @GetMapping("/roles")
    public List<Usuario> buscarPorRoles(@RequestParam List<String> roles) {
        return usuarioService.buscarPorRoles(roles);
    }
    
    

    @GetMapping("/validar-email")
    public ResponseEntity<String> validarEmail(@RequestParam String email) {
        boolean valido = emailValidatorService.isEmailDomainValid(email);
        if (valido) {
            return ResponseEntity.ok("Domínio válido para receber e-mails.");
        } else {
            return ResponseEntity.badRequest().body("O domínio do e-mail não é válido.");
        }
    }
    
   

    @GetMapping("/confirmar-email")
    public ResponseEntity<Map<String, String>> confirmarEmail(@RequestParam("token") String token) {
        Map<String, String> resposta = new HashMap<>();

        try {
            String resultado = confirmationTokenService.confirmarToken(token);
            resposta.put("mensagem", resultado);

            return ResponseEntity.ok(resposta); // Sempre 200 com mensagem
        } catch (Exception e) {
            resposta.put("mensagem", "Erro interno ao confirmar e-mail.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resposta);
        }
    }






    
    // Salvar usuário
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/salvar-usuario")
    public ResponseEntity<Object> salvarUsuario(@RequestBody Usuario usuario) {
        try {
            Usuario usuarioSalvo = usuarioService.salvarUsuario(usuario);

            // Cria a URI para o novo recurso
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                            .path("/{id}")
                            .buildAndExpand(usuarioSalvo.getId())
                            .toUri();

            // Retorna 201 Created e a URI do recurso criado
            return ResponseEntity.created(location).body(usuarioSalvo);
        } catch (CampoDuplicadoException e) {
            logger.warn("Campo duplicado: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (ResponseStatusException e) {
            logger.error("Erro ao salvar usuário: " + e.getReason(), e);
            return ResponseEntity.status(e.getStatus()).body(e.getReason());
        } catch (Exception e) {
            logger.error("Erro inesperado ao salvar usuário", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno no servidor");
        }

    }
    
    // Deletar usuário por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id) {
        Optional<Usuario> usuario = usuarioService.buscarPorId(id);
        if (usuario.isPresent()) {
            usuarioService.deletarUsuario(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PatchMapping("/{id}/imagem")
    public ResponseEntity<Usuario> uploadImagem(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        Optional<Usuario> usuarioOpt = usuarioService.buscarPorId(id);
        if (!usuarioOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Usuario usuario = usuarioOpt.get();

        try {
        	 // Define o caminho absoluto externo para uploads no Windows
            Path uploadPath = Paths.get("C:/uploads");
        	
        	// Define o caminho absoluto externo para uploads no Linux PRODUCAO
        	//Path uploadPath = Paths.get("/var/uploads");

            
            if (!Files.exists(uploadPath)) {
                logger.info("Diretório de uploads não existe. Criando diretório: {}", uploadPath.toAbsolutePath());
                Files.createDirectories(uploadPath);
            }

            // Verifica se o diretório foi criado
            if (!Files.isDirectory(uploadPath)) {
                logger.error("Falha ao criar diretório de uploads: {}", uploadPath.toAbsolutePath());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }

            // Salva a imagem no diretório
            String fileName = file.getOriginalFilename();
            Path filePath = uploadPath.resolve(fileName);
            logger.info("Salvando arquivo em: {}", filePath.toAbsolutePath());
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

         // Atualiza o campo imagemUrl com o caminho relativo do arquivo
            String imagemUrl = "/uploads/" + fileName; // Caminho relativo
            usuario.setImagemUrl(imagemUrl);
            Usuario usuarioAtualizado = usuarioService.atualizarImagemUsuario(usuario);
            logger.info("Imagem URL atualizada para o usuário com ID {}: {}", id, imagemUrl);
            return ResponseEntity.ok(usuarioAtualizado);

        } catch (IOException e) {
            logger.error("Erro ao salvar a imagem", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    
    @PutMapping("/alterar-senha")
    public ResponseEntity<?> alterarSenha(
            @RequestBody AlterarSenhaRequest request,
            Authentication authentication
    ) {
        String username = authentication.getName(); // Aqui é o username que o usuário usou no login
        Optional<Usuario> usuarioOpt = usuarioService.buscarPorUsername(username); 

        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não encontrado.");
        }

        Usuario usuarioLogado = usuarioOpt.get();

        try {
            usuarioService.alterarSenha(usuarioLogado.getId(), request.getSenhaAtual(), request.getNovaSenha());
            return ResponseEntity.ok("Senha alterada com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        }
    }

}
