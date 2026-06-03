package iam.solucoesdigitais.baseappjwt.repository;

import iam.solucoesdigitais.baseappjwt.model.Setor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SetorRepository extends JpaRepository<Setor, Long> {
    
    // Buscar setor por nome e unidade
    Optional<Setor> findByNomeAndUnidadeId(String nome, Long unidadeId);
    
    // Buscar setores por unidade
    List<Setor> findByUnidadeId(Long unidadeId);
    
    // Buscar setores por nome (contendo)
    List<Setor> findByNomeContainingIgnoreCase(String nome);
    
    // Verificar se existe setor com o nome na unidade
    boolean existsByNomeAndUnidadeId(String nome, Long unidadeId);
    
    // Buscar setores com unidade (JOIN FETCH)
    @Query("SELECT s FROM Setor s JOIN FETCH s.unidade WHERE s.unidade.id = :unidadeId")
    List<Setor> findByUnidadeIdWithUnidade(@Param("unidadeId") Long unidadeId);
    
    // Buscar todos os setores com unidade
    @Query("SELECT s FROM Setor s JOIN FETCH s.unidade")
    List<Setor> findAllWithUnidade();
}
