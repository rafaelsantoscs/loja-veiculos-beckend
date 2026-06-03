package iam.solucoesdigitais.baseappjwt.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import iam.solucoesdigitais.baseappjwt.model.PasswordResetToken;
import iam.solucoesdigitais.baseappjwt.model.Usuario;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    Optional<PasswordResetToken> findByToken(String token);
    Optional<PasswordResetToken> findByUser(Usuario user);
}
