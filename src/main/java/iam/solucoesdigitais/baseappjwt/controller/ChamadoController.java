package iam.solucoesdigitais.baseappjwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import iam.solucoesdigitais.baseappjwt.dto.ChamadoDTO;
import iam.solucoesdigitais.baseappjwt.dto.ChamadoRequestDTO;
import iam.solucoesdigitais.baseappjwt.dto.StatusUpdateDTO;
import iam.solucoesdigitais.baseappjwt.service.ChamadoService;
import iam.solucoesdigitais.enums.StatusChamado;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/chamados")
@CrossOrigin(origins = "*")
public class ChamadoController {
    
    @Autowired
    private ChamadoService chamadoService;
    
    // GET - Listar todos os chamados
    @GetMapping
    public ResponseEntity<List<ChamadoDTO>> listarTodos() {
        try {
            List<ChamadoDTO> chamados = chamadoService.findAll();
            return ResponseEntity.ok(chamados);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // GET - Buscar chamado por ID
    @GetMapping("/{id}")
    public ResponseEntity<ChamadoDTO> buscarPorId(@PathVariable Long id) {
        try {
            ChamadoDTO chamado = chamadoService.findById(id);
            return ResponseEntity.ok(chamado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // GET - Buscar chamado por protocolo
    @GetMapping("/protocolo/{protocolo}")
    public ResponseEntity<ChamadoDTO> buscarPorProtocolo(@PathVariable String protocolo) {
        try {
            ChamadoDTO chamado = chamadoService.findByProtocolo(protocolo);
            return ResponseEntity.ok(chamado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // GET - Buscar chamados filtrados
    @GetMapping("/filtrar")
    public ResponseEntity<List<ChamadoDTO>> filtrar(
            @RequestParam(required = false) StatusChamado status,
            @RequestParam(required = false) Long unidadeId,
            @RequestParam(required = false) String protocolo) {
        
        try {
            List<ChamadoDTO> chamados = chamadoService.findFiltered(status, unidadeId, protocolo);
            return ResponseEntity.ok(chamados);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // GET - Buscar chamados por status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<ChamadoDTO>> buscarPorStatus(@PathVariable StatusChamado status) {
        try {
            List<ChamadoDTO> chamados = chamadoService.findByStatus(status);
            return ResponseEntity.ok(chamados);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // GET - Buscar chamados por unidade
    @GetMapping("/unidade/{unidadeId}")
    public ResponseEntity<List<ChamadoDTO>> buscarPorUnidade(@PathVariable Long unidadeId) {
        try {
            List<ChamadoDTO> chamados = chamadoService.findByUnidadeId(unidadeId);
            return ResponseEntity.ok(chamados);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // GET - Estatísticas dos chamados
    @GetMapping("/estatisticas")
    public ResponseEntity<ChamadoService.EstatisticasChamadosDTO> getEstatisticas() {
        try {
            ChamadoService.EstatisticasChamadosDTO estatisticas = chamadoService.getEstatisticas();
            return ResponseEntity.ok(estatisticas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // POST - Criar novo chamado
    @PostMapping
    public ResponseEntity<?> criar(@Valid @RequestBody ChamadoRequestDTO request) {
        try {
            ChamadoDTO chamado = chamadoService.create(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(chamado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno do servidor");
        }
    }
    
    // PATCH - Atualizar status do chamado
    @PatchMapping("/{id}/status")
    public ResponseEntity<?> atualizarStatus(@PathVariable Long id, @Valid @RequestBody StatusUpdateDTO request) {
        try {
            ChamadoDTO chamado = chamadoService.updateStatus(id, request);
            return ResponseEntity.ok(chamado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno do servidor");
        }
    }
    
    // DELETE - Deletar chamado
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        try {
            chamadoService.delete(id);
            return ResponseEntity.ok().body("Chamado deletado com sucesso");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno do servidor");
        }
    }
}
