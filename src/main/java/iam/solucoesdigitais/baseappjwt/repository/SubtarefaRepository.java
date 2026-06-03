package iam.solucoesdigitais.baseappjwt.repository;

import iam.solucoesdigitais.baseappjwt.model.Subtarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubtarefaRepository extends JpaRepository<Subtarefa, Long> {
    
    // Buscar subtarefas de uma tarefa específica
    List<Subtarefa> findByTarefaIdOrderByOrdemAsc(Long tarefaId);
    
    // Buscar subtarefas concluídas de uma tarefa
    List<Subtarefa> findByTarefaIdAndConcluidaOrderByOrdemAsc(Long tarefaId, boolean concluida);
    
    // Contar subtarefas concluídas de uma tarefa
    @Query("SELECT COUNT(s) FROM Subtarefa s WHERE s.tarefa.id = :tarefaId AND s.concluida = true")
    Long countConcluidasByTarefaId(@Param("tarefaId") Long tarefaId);
    
    // Contar total de subtarefas de uma tarefa
    Long countByTarefaId(Long tarefaId);
}
