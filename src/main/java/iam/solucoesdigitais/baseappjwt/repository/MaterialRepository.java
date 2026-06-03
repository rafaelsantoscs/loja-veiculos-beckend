package iam.solucoesdigitais.baseappjwt.repository;

import iam.solucoesdigitais.baseappjwt.model.Material;
import iam.solucoesdigitais.enums.StatusMaterial;
import iam.solucoesdigitais.enums.TipoMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Long> {
    
    // Buscar material por tombamento
    Optional<Material> findByTombamento(String tombamento);
    
    // Buscar materiais por tipo
    List<Material> findByTipo(TipoMaterial tipo);
    
    // Buscar materiais por status
    List<Material> findByStatus(StatusMaterial status);
    
    // Buscar materiais por unidade
    List<Material> findByUnidadeId(Long unidadeId);
    
    // Buscar materiais por setor
    List<Material> findBySetorId(Long setorId);
    
    // Buscar materiais por cadastradoPor
    List<Material> findByCadastradoPorContainingIgnoreCase(String cadastradoPor);
    
    // Buscar materiais por tombamento (contendo)
    List<Material> findByTombamentoContainingIgnoreCase(String tombamento);
    
    // Buscar materiais por marca (contendo)
    List<Material> findByMarcaContainingIgnoreCase(String marca);
    
    // CORRIGIDO: Buscar materiais com unidade e setor (JOIN FETCH)
    @Query("SELECT m FROM Material m JOIN FETCH m.unidade JOIN FETCH m.setor")
    List<Material> findAllWithUnidadeAndSetor();
    
    // CORRIGIDO: Buscar material por ID com relacionamentos
    @Query("SELECT m FROM Material m JOIN FETCH m.unidade JOIN FETCH m.setor WHERE m.id = :id")
    Optional<Material> findByIdWithUnidadeAndSetor(@Param("id") Long id);
    
    // CORRIGIDO: Buscar materiais filtrados por múltiplos critérios
    @Query("SELECT m FROM Material m JOIN FETCH m.unidade JOIN FETCH m.setor WHERE " +
           "(:tipo IS NULL OR m.tipo = :tipo) AND " +
           "(:unidadeId IS NULL OR m.unidade.id = :unidadeId) AND " +
           "(:status IS NULL OR m.status = :status) AND " +
           "(:tombamento IS NULL OR m.tombamento LIKE %:tombamento%)")
    List<Material> findFiltered(@Param("tipo") TipoMaterial tipo,
                               @Param("unidadeId") Long unidadeId,
                               @Param("status") StatusMaterial status,
                               @Param("tombamento") String tombamento);
    
    // Verificar se tombamento já existe (para validação)
    boolean existsByTombamento(String tombamento);

    // Buscar materiais por unidade e setor
    @Query("SELECT m FROM Material m JOIN FETCH m.unidade JOIN FETCH m.setor WHERE m.unidade.id = :unidadeId AND m.setor.id = :setorId")
    List<Material> findByUnidadeIdAndSetorId(@Param("unidadeId") Long unidadeId, @Param("setorId") Long setorId);

    // Buscar materiais por unidade com relacionamentos
    @Query("SELECT m FROM Material m JOIN FETCH m.unidade JOIN FETCH m.setor WHERE m.unidade.id = :unidadeId")
    List<Material> findByUnidadeIdWithJoins(@Param("unidadeId") Long unidadeId);
}