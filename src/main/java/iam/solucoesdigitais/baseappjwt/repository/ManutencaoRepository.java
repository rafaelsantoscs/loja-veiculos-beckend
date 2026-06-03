package iam.solucoesdigitais.baseappjwt.repository;

import iam.solucoesdigitais.baseappjwt.model.Manutencao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ManutencaoRepository extends JpaRepository<Manutencao, Long> {
    
    // Buscar manutenções por material
    List<Manutencao> findByMaterialId(Long materialId);
    
    // Buscar manutenções por técnico
    List<Manutencao> findByTecnicoContainingIgnoreCase(String tecnico);
    
    // Buscar manutenções por status
    List<Manutencao> findByStatus(Manutencao.StatusManutencao status);
    
    // Buscar manutenções abertas
    List<Manutencao> findByStatusOrderByDataAberturaDesc(Manutencao.StatusManutencao status);
    
    // Buscar manutenções por período
    List<Manutencao> findByDataAberturaBetween(LocalDateTime inicio, LocalDateTime fim);
    
    // CORRIGIDO: Buscar todas as manutenções com material (JOIN FETCH)
    @Query("SELECT ma FROM Manutencao ma JOIN FETCH ma.material")
    List<Manutencao> findAllWithMaterial();
    
    // CORRIGIDO: Buscar manutenções por material com relacionamentos
    @Query("SELECT ma FROM Manutencao ma JOIN FETCH ma.material WHERE ma.material.id = :materialId")
    List<Manutencao> findByMaterialIdWithMaterial(@Param("materialId") Long materialId);
    
    // CORRIGIDO: Buscar manutenção por ID com material
    @Query("SELECT ma FROM Manutencao ma JOIN FETCH ma.material WHERE ma.id = :id")
    Optional<Manutencao> findByIdWithMaterial(@Param("id") Long id);
    
    // Contar manutenções abertas
    Long countByStatus(Manutencao.StatusManutencao status);
}