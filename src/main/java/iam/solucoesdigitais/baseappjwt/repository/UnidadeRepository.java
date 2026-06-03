package iam.solucoesdigitais.baseappjwt.repository;

import iam.solucoesdigitais.baseappjwt.model.Unidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UnidadeRepository extends JpaRepository<Unidade, Long> {
    
    // Buscar unidade por nome (exato)
    Optional<Unidade> findByNome(String nome);
    
    // Buscar unidades por nome (contendo)
    List<Unidade> findByNomeContainingIgnoreCase(String nome);
    
    // Verificar se existe unidade com o nome
    boolean existsByNome(String nome);
    
    // CORRIGIDO: Buscar unidades com seus setores (usando JOIN FETCH para evitar N+1)
    @Query("SELECT DISTINCT u FROM Unidade u LEFT JOIN FETCH u.setores")
    List<Unidade> findAllWithSetores();
    
    // CORRIGIDO: Buscar unidade por ID com setores
    @Query("SELECT u FROM Unidade u LEFT JOIN FETCH u.setores WHERE u.id = :id")
    Optional<Unidade> findByIdWithSetores(@Param("id") Long id);
}