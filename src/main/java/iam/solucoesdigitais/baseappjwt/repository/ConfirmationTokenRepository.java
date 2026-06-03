package iam.solucoesdigitais.baseappjwt.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import iam.solucoesdigitais.baseappjwt.model.ConfirmationToken;

public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {
    Optional<ConfirmationToken> findByToken(String token);
}
