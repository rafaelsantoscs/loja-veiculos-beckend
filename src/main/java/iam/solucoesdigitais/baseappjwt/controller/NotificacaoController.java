package iam.solucoesdigitais.baseappjwt.controller;

import iam.solucoesdigitais.baseappjwt.model.Notificacao;
import iam.solucoesdigitais.baseappjwt.service.NotificacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/notificacoes")
public class NotificacaoController {

    private final NotificacaoService notificacaoService;

    @Autowired
    public NotificacaoController(NotificacaoService notificacaoService) {
        this.notificacaoService = notificacaoService;
    }

    @GetMapping
    public List<Notificacao> listarTodas() {
        return notificacaoService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Notificacao> buscarPorId(@PathVariable Long id) {
        Optional<Notificacao> notificacao = notificacaoService.buscarPorId(id);
        return notificacao.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody Notificacao notificacao) {
        try {
            Notificacao notificacaoSalva = notificacaoService.salvar(notificacao);
            return ResponseEntity.ok(notificacaoSalva);
        } catch (Exception e) {
            System.err.println("Erro ao salvar notificação: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Erro ao salvar notificação: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Notificacao> atualizar(@PathVariable Long id, @RequestBody Notificacao notificacao) {
        Notificacao atualizada = notificacaoService.atualizar(id, notificacao);
        return ResponseEntity.ok(atualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        notificacaoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/receber")
    public ResponseEntity<Notificacao> marcarComoRecebida(@PathVariable Long id, @RequestBody Map<String, String> payload) {
        String momentoRecebido = payload.get("momentoRecebido");
        Notificacao notificacao = notificacaoService.marcarComoRecebida(id, momentoRecebido);
        return ResponseEntity.ok(notificacao);
    }

    // Endpoint adicional para buscar notificações por destinatário
    @GetMapping("/destinatario/{destinatario}")
    public List<Notificacao> buscarPorDestinatario(@PathVariable String destinatario) {
        return notificacaoService.buscarPorDestinatario(destinatario);
    }
    
    @GetMapping("/pendentes/destinatario/{destinatario}")
    public List<Notificacao> buscarPendentesPorDestinatario(@PathVariable String destinatario) {
        return notificacaoService.buscarPendentesPorDestinatario(destinatario);
    }
    
    @GetMapping("/a-receber")
    public ResponseEntity<Page<Notificacao>> buscarAReceber(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "3") int size,
        @RequestParam(defaultValue = "momentoCriacao,desc") String sort) {
        
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("momentoCriacao")));
        Page<Notificacao> notificacoes = notificacaoService.buscarAReceber(pageable);
        return ResponseEntity.ok(notificacoes);
    }
    
    @GetMapping("/recebidas")
    public ResponseEntity<Page<Notificacao>> buscarRecebidas(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "3") int size,
        @RequestParam(defaultValue = "momentoRecebido,desc") String sort) {
        
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("momentoRecebido")));
        Page<Notificacao> notificacoes = notificacaoService.buscarRecebidas(pageable);
        return ResponseEntity.ok(notificacoes);
    }
}