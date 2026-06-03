package iam.solucoesdigitais.baseappjwt.controller;

import iam.solucoesdigitais.baseappjwt.dto.TarefaDTO;
import iam.solucoesdigitais.baseappjwt.service.TarefaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tarefas")
@CrossOrigin(origins = "*")
public class TarefaController {

    @Autowired
    private TarefaService tarefaService;

    // Criar nova tarefa
    @PostMapping
    public ResponseEntity<TarefaDTO> criarTarefa(@RequestBody TarefaDTO tarefaDTO) {
        try {
            TarefaDTO novaTarefa = tarefaService.criarTarefa(tarefaDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(novaTarefa);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Atualizar tarefa
    @PutMapping("/{id}")
    public ResponseEntity<TarefaDTO> atualizarTarefa(
            @PathVariable Long id,
            @RequestBody TarefaDTO tarefaDTO) {
        try {
            TarefaDTO tarefaAtualizada = tarefaService.atualizarTarefa(id, tarefaDTO);
            return ResponseEntity.ok(tarefaAtualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Atualizar status da tarefa
    @PatchMapping("/{id}/status")
    public ResponseEntity<TarefaDTO> atualizarStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> payload) {
        try {
            String novoStatus = payload.get("status");
            TarefaDTO tarefaAtualizada = tarefaService.atualizarStatus(id, novoStatus);
            return ResponseEntity.ok(tarefaAtualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Marcar subtarefa como concluída
    @PatchMapping("/{tarefaId}/subtarefas/{subtarefaId}/concluir")
    public ResponseEntity<TarefaDTO> marcarSubtarefaConcluida(
            @PathVariable Long tarefaId,
            @PathVariable Long subtarefaId,
            @RequestBody Map<String, String> payload) {
        try {
            String usuario = payload.get("usuario");
            TarefaDTO tarefaAtualizada = tarefaService.marcarSubtarefaConcluida(tarefaId, subtarefaId, usuario);
            return ResponseEntity.ok(tarefaAtualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Desmarcar subtarefa
    @PatchMapping("/{tarefaId}/subtarefas/{subtarefaId}/desmarcar")
    public ResponseEntity<TarefaDTO> desmarcarSubtarefa(
            @PathVariable Long tarefaId,
            @PathVariable Long subtarefaId) {
        try {
            TarefaDTO tarefaAtualizada = tarefaService.desmarcarSubtarefa(tarefaId, subtarefaId);
            return ResponseEntity.ok(tarefaAtualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Buscar tarefa por ID
    @GetMapping("/{id}")
    public ResponseEntity<TarefaDTO> buscarTarefa(@PathVariable Long id) {
        try {
            TarefaDTO tarefa = tarefaService.buscarPorId(id);
            return ResponseEntity.ok(tarefa);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Buscar tarefas do usuário
    @GetMapping("/usuario")
    public ResponseEntity<List<TarefaDTO>> buscarTarefasDoUsuario(@RequestParam String email) {
        List<TarefaDTO> tarefas = tarefaService.buscarTarefasDoUsuario(email);
        return ResponseEntity.ok(tarefas);
    }

    // Buscar tarefas criadas pelo usuário (admin)
    @GetMapping("/criadas-por")
    public ResponseEntity<List<TarefaDTO>> buscarTarefasCriadasPor(@RequestParam String email) {
        List<TarefaDTO> tarefas = tarefaService.buscarTarefasCriadasPor(email);
        return ResponseEntity.ok(tarefas);
    }

    // Buscar todas as tarefas
    @GetMapping
    public ResponseEntity<List<TarefaDTO>> buscarTodasTarefas() {
        List<TarefaDTO> tarefas = tarefaService.buscarTodasTarefas();
        return ResponseEntity.ok(tarefas);
    }

    // Buscar tarefas por status
    @GetMapping("/usuario/status")
    public ResponseEntity<List<TarefaDTO>> buscarPorStatus(
            @RequestParam String email,
            @RequestParam String status) {
        List<TarefaDTO> tarefas = tarefaService.buscarPorStatus(email, status);
        return ResponseEntity.ok(tarefas);
    }

    // Buscar tarefas vencidas
    @GetMapping("/usuario/vencidas")
    public ResponseEntity<List<TarefaDTO>> buscarTarefasVencidas(@RequestParam String email) {
        List<TarefaDTO> tarefas = tarefaService.buscarTarefasVencidas(email);
        return ResponseEntity.ok(tarefas);
    }

    // Deletar tarefa
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarTarefa(@PathVariable Long id) {
        try {
            tarefaService.deletarTarefa(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
