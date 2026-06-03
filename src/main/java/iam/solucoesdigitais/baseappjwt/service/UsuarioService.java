package iam.solucoesdigitais.baseappjwt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import iam.solucoesdigitais.baseappjwt.exception.CampoDuplicadoException;
import iam.solucoesdigitais.baseappjwt.model.Usuario;
import iam.solucoesdigitais.baseappjwt.repository.UsuarioRepository;
import iam.solucoesdigitais.baseappjwt.util.EmailValidatorService;

import java.util.List;
import java.util.Optional;
import iam.solucoesdigitais.baseappjwt.model.ConfirmationToken;
import iam.solucoesdigitais.baseappjwt.service.ConfirmationTokenService;
import iam.solucoesdigitais.baseappjwt.service.EmailService;


@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private EmailValidatorService emailValidatorService;
    
    @Autowired
    private ConfirmationTokenService confirmationTokenService;

    @Autowired
    private EmailService emailService;

    
    // Buscar todos os usuários
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    // Buscar usuário por ID
    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }
    
    public Optional<Usuario> buscarPorCpf(String cpf) {
        return usuarioRepository.findByCpf(cpf);
    }
    
    public List<Usuario> buscarPorNome(String nome) {
        return usuarioRepository.findByNomeContaining(nome);
    }
    
    public List<Usuario> buscarPorRoles(List<String> roles) {
        return usuarioRepository.findByRolesIn(roles);
    }

    // Método para salvar ou atualizar usuário
    public Usuario salvarUsuario(Usuario usuario) {
        if (usuario.getUsername() != null && usuarioRepository.findUserByUsername(usuario.getUsername()) != null) {
            throw new CampoDuplicadoException("Nome de Login já existe.");
        }

        if (usuario.getEmail() != null && usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
            throw new CampoDuplicadoException("Email já existe.");
        }
        
//        if (!emailValidatorService.isEmailDomainValid(usuario.getEmail())) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O domínio do e-mail não é válido ou não está configurado para receber mensagens.");
//        }

        if (usuario.getCpf() != null && usuarioRepository.findByCpf(usuario.getCpf()).isPresent()) {
            throw new CampoDuplicadoException("CPF já existe.");
        }

       
        if (usuario.getPassword() != null) {
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        }

        
        // Garante que o campo será sempre false até que o usuário confirme
        usuario.setEmailConfirmado(false); 
        
        // Salva o usuário
        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        // Gera e salva o token
        String token = confirmationTokenService.gerarTokenParaUsuario(usuarioSalvo);

        // Monta o link
        //String link = "http://localhost:3000/confirmar-email?token=" + token;
        String link = "https://frotavsa.iamtec.org/confirmar-email?token=" + token;

        // Envia e-mail
        emailService.sendEmail(
            "Confirmação de E-mail",
            usuarioSalvo.getEmail(),
            "Olá, " + usuarioSalvo.getNome() + "!\n\nClique no link abaixo para confirmar seu e-mail:\n" + link
        );
        
    
       
        
        return usuarioRepository.save(usuario);
    }

    // Deletar usuário por ID
    public void deletarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

 // Buscar usuário por login
    public Optional<Usuario> buscarPorLogin(String login) {
        return Optional.ofNullable(usuarioRepository.findUserByUsername(login));
    }
    
    // Buscar usuário por email
    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }
    
    public Usuario atualizarUsuarioParcial(Long id, Usuario usuarioDados) {
        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        // Atualiza apenas os campos não nulos
        if (usuarioDados.getUsername() != null && !usuarioDados.getUsername().equals(usuarioExistente.getUsername())) {
            if (usuarioRepository.findUserByUsername(usuarioDados.getUsername()) != null) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Nome de Login já existe.");
            }
            usuarioExistente.setUsername(usuarioDados.getUsername());
        }

        if (usuarioDados.getEmail() != null && !usuarioDados.getEmail().equals(usuarioExistente.getEmail())) {
            if (usuarioRepository.findByEmail(usuarioDados.getEmail()).isPresent()) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Email já existe.");
            }
            usuarioExistente.setEmail(usuarioDados.getEmail());
        }

        if (usuarioDados.getCpf() != null && !usuarioDados.getCpf().equals(usuarioExistente.getCpf())) {
            if (usuarioRepository.findByCpf(usuarioDados.getCpf()).isPresent()) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "CPF já existe.");
            }
            usuarioExistente.setCpf(usuarioDados.getCpf());
        }

        if (usuarioDados.getTelefone() != null) {
            usuarioExistente.setTelefone(usuarioDados.getTelefone());
        }
        
        if (usuarioDados.getNome() != null) {
            usuarioExistente.setNome(usuarioDados.getNome());
        }
        
//        if (usuarioDados.getNome() != null) {
//            usuarioExistente.setNome(usuarioDados.getNome());
//        }

        if (usuarioDados.getRoles() != null) {
            usuarioExistente.setRoles(usuarioDados.getRoles());
        }

        return usuarioRepository.save(usuarioExistente);
    }
    
    public Usuario atualizarRolesUsuario(Long id, List<String> novosRoles) {
        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        // Validação básica dos roles
        if (novosRoles == null || novosRoles.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Lista de roles não pode ser vazia");
        }

        // Aqui você pode adicionar validações adicionais dos roles se necessário
        // Por exemplo, verificar se os roles são válidos no seu sistema
        
        usuarioExistente.setRoles(novosRoles);
        return usuarioRepository.save(usuarioExistente);
    }
    
    public Usuario atualizarImagemUsuario(Usuario usuario) {
        // Certifica-se de que apenas o campo imagemUrl será atualizado.
        Optional<Usuario> usuarioExistente = usuarioRepository.findById(usuario.getId());
        if (usuarioExistente.isPresent()) {
            Usuario existente = usuarioExistente.get();
            existente.setImagemUrl(usuario.getImagemUrl()); // Atualiza apenas o campo imagemUrl
            return usuarioRepository.save(existente); // Salva no banco de dados
        } else {
            throw new IllegalArgumentException("Usuário não encontrado");
        }
    }
    
    public Optional<Usuario> buscarPorUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }


    public void alterarSenha(Long id, String senhaAtual, String novaSenha) {
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

        if (!passwordEncoder.matches(senhaAtual, usuario.getPassword())) {
            throw new RuntimeException("Senha atual incorreta.");
        }

        usuario.setPassword(passwordEncoder.encode(novaSenha));
        usuarioRepository.save(usuario);
    }

}
