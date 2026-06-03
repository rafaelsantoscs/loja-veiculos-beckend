package iam.solucoesdigitais.baseappjwt.service;

import iam.solucoesdigitais.baseappjwt.model.ConfirmationToken;
import iam.solucoesdigitais.baseappjwt.model.Usuario;
import iam.solucoesdigitais.baseappjwt.repository.ConfirmationTokenRepository;
import iam.solucoesdigitais.baseappjwt.repository.UsuarioRepository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfirmationTokenService {

    @Autowired
    private ConfirmationTokenRepository tokenRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Método usado ao salvar o usuário
    public String gerarTokenParaUsuario(Usuario usuario) {
        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
            token,
            usuario,
            LocalDateTime.now(),
            LocalDateTime.now().plusHours(24)
        );
        tokenRepository.save(confirmationToken);
        return token;
    }

    public String confirmarToken(String token) {
        Optional<ConfirmationToken> optionalToken = tokenRepository.findByToken(token);

        if (optionalToken.isEmpty()) {
            return "Token inválido.";
        }

        ConfirmationToken confirmationToken = optionalToken.get();
        Usuario usuario = confirmationToken.getUsuario();

        if (usuario.getEmailConfirmado()) {
            return "O e-mail já foi confirmado anteriormente.";
        }

        if (confirmationToken.getExpiraEm().isBefore(LocalDateTime.now())) {
            return "O link de confirmação expirou. Solicite um novo.";
        }

        usuario.setEmailConfirmado(true);
        usuarioRepository.save(usuario);
        tokenRepository.delete(confirmationToken);

        return "E-mail confirmado com sucesso!";
    }



}
