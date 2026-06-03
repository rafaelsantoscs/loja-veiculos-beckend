package iam.solucoesdigitais.baseappjwt.repository;

import iam.solucoesdigitais.baseappjwt.model.Notificacao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificacaoRepository extends JpaRepository<Notificacao, Long> {
    // Você pode adicionar métodos de consulta personalizados aqui se necessário
	List<Notificacao> findByEnviadoPara(String enviadoPara);
	
	List<Notificacao> findByEnviadoParaAndMomentoRecebidoIsNull(String enviadoPara);
	
	@Query("SELECT n FROM Notificacao n WHERE n.momentoRecebido IS NOT NULL")
    Page<Notificacao> findByMomentoRecebidoNotNull(Pageable pageable);
  
	@Query("SELECT n FROM Notificacao n WHERE n.momentoRecebido IS NULL")
    Page<Notificacao> findByMomentoRecebidoIsNull(Pageable pageable);
}