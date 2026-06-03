package iam.solucoesdigitais.baseappjwt.repository;

import iam.solucoesdigitais.baseappjwt.model.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
    
    // Buscar tarefas atribuídas a um usuário específico (suporta múltiplos usuários separados por vírgula)
    @Query("SELECT t FROM Tarefa t WHERE CONCAT(',', t.atribuidoPara, ',') LIKE CONCAT('%,', :email, ',%') ORDER BY t.dataCriacao DESC")
    List<Tarefa> findByAtribuidoParaOrderByDataCriacaoDesc(@Param("email") String email);
    
    // Buscar tarefas criadas por um usuário
    List<Tarefa> findByCriadoPorOrderByDataCriacaoDesc(String criadoPor);
    
    // Buscar tarefas por status
    List<Tarefa> findByStatusOrderByDataCriacaoDesc(String status);
    
    // Buscar tarefas atribuídas a um usuário com status específico (suporta múltiplos)
    @Query("SELECT t FROM Tarefa t WHERE CONCAT(',', t.atribuidoPara, ',') LIKE CONCAT('%,', :email, ',%') AND t.status = :status ORDER BY t.dataCriacao DESC")
    List<Tarefa> findByAtribuidoParaAndStatusOrderByDataCriacaoDesc(@Param("email") String email, @Param("status") String status);
    
    // Buscar tarefas por prioridade
    List<Tarefa> findByPrioridadeOrderByDataCriacaoDesc(String prioridade);
    
    // Buscar tarefas atribuídas a um usuário por prioridade (suporta múltiplos)
    @Query("SELECT t FROM Tarefa t WHERE CONCAT(',', t.atribuidoPara, ',') LIKE CONCAT('%,', :email, ',%') AND t.prioridade = :prioridade ORDER BY t.dataCriacao DESC")
    List<Tarefa> findByAtribuidoParaAndPrioridadeOrderByDataCriacaoDesc(@Param("email") String email, @Param("prioridade") String prioridade);
    
    // Buscar todas as tarefas ordenadas por data de criação (mais recentes primeiro)
    List<Tarefa> findAllByOrderByDataCriacaoDesc();
    
    // Contar tarefas por status para um usuário (suporta múltiplos)
    @Query("SELECT COUNT(t) FROM Tarefa t WHERE CONCAT(',', t.atribuidoPara, ',') LIKE CONCAT('%,', :email, ',%') AND t.status = :status")
    Long countByAtribuidoParaAndStatus(@Param("email") String email, @Param("status") String status);
    
    // Buscar tarefas vencidas (prazo vencido e não concluídas) (suporta múltiplos)
    @Query("SELECT t FROM Tarefa t WHERE CONCAT(',', t.atribuidoPara, ',') LIKE CONCAT('%,', :email, ',%') AND t.dataPrazo < CURRENT_TIMESTAMP AND t.status != 'CONCLUIDA' AND t.status != 'CANCELADA' ORDER BY t.dataPrazo ASC")
    List<Tarefa> findTarefasVencidasByUsuario(@Param("email") String email);
}
