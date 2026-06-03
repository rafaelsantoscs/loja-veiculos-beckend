package iam.solucoesdigitais.baseappjwt.repository;

import iam.solucoesdigitais.baseappjwt.model.Chamado;
import iam.solucoesdigitais.enums.StatusChamado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ChamadoRepository extends JpaRepository<Chamado, Long> {
    
    // Buscar chamado por protocolo
    Optional<Chamado> findByProtocolo(String protocolo);
    
    // Buscar chamados por status
    List<Chamado> findByStatus(StatusChamado status);
    
    // Buscar chamados por unidade
    List<Chamado> findByUnidadeId(Long unidadeId);
    
    // Buscar chamados por material
    List<Chamado> findByMaterialId(Long materialId);
    
    // Buscar chamados por usuário que abriu
    List<Chamado> findByUsuarioAberturaContainingIgnoreCase(String usuarioAbertura);
    
    // Buscar chamados por data de abertura (entre datas)
    List<Chamado> findByDataAberturaBetween(LocalDateTime inicio, LocalDateTime fim);
    
    // CORRIGIDO: Buscar chamados com todos os relacionamentos (JOIN FETCH)
    @Query("SELECT c FROM Chamado c JOIN FETCH c.unidade JOIN FETCH c.setor JOIN FETCH c.material")
    List<Chamado> findAllWithRelations();
    
    // CORRIGIDO: Buscar chamado por ID com relacionamentos
    @Query("SELECT c FROM Chamado c JOIN FETCH c.unidade JOIN FETCH c.setor JOIN FETCH c.material WHERE c.id = :id")
    Optional<Chamado> findByIdWithRelations(@Param("id") Long id);
    
    // CORRIGIDO: Buscar chamados filtrados
    @Query("SELECT c FROM Chamado c JOIN FETCH c.unidade JOIN FETCH c.setor JOIN FETCH c.material WHERE " +
           "(:status IS NULL OR c.status = :status) AND " +
           "(:unidadeId IS NULL OR c.unidade.id = :unidadeId) AND " +
           "(:protocolo IS NULL OR c.protocolo LIKE %:protocolo%)")
    List<Chamado> findFiltered(@Param("status") StatusChamado status,
                              @Param("unidadeId") Long unidadeId,
                              @Param("protocolo") String protocolo);
    
    // Contar chamados por status
    @Query("SELECT COUNT(c) FROM Chamado c WHERE c.status = :status")
    Long countByStatus(@Param("status") StatusChamado status);
    
    // Verificar se protocolo já existe
    boolean existsByProtocolo(String protocolo);
}