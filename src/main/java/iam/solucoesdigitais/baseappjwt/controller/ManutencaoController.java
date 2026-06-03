package iam.solucoesdigitais.baseappjwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import iam.solucoesdigitais.baseappjwt.dto.FecharManutencaoRequestDTO;
import iam.solucoesdigitais.baseappjwt.dto.ManutencaoDTO;
import iam.solucoesdigitais.baseappjwt.dto.ManutencaoRequestDTO;
import iam.solucoesdigitais.baseappjwt.service.ManutencaoService;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import java.util.List;

@RestController
@RequestMapping("/api/manutencoes")
@CrossOrigin(origins = "*")
public class ManutencaoController {
    
    @Autowired
    private ManutencaoService manutencaoService;
    
    // GET - Listar todas as manutenções
    @GetMapping
    public ResponseEntity<List<ManutencaoDTO>> listarTodas() {
        try {
            List<ManutencaoDTO> manutencoes = manutencaoService.findAll();
            return ResponseEntity.ok(manutencoes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // GET - Buscar manutenção por ID
    @GetMapping("/{id}")
    public ResponseEntity<ManutencaoDTO> buscarPorId(@PathVariable Long id) {
        try {
            ManutencaoDTO manutencao = manutencaoService.findById(id);
            return ResponseEntity.ok(manutencao);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // GET - Buscar manutenções por material
    @GetMapping("/material/{materialId}")
    public ResponseEntity<List<ManutencaoDTO>> buscarPorMaterial(@PathVariable Long materialId) {
        try {
            List<ManutencaoDTO> manutencoes = manutencaoService.findByMaterialId(materialId);
            return ResponseEntity.ok(manutencoes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // GET - Buscar manutenções abertas
    @GetMapping("/abertas")
    public ResponseEntity<List<ManutencaoDTO>> buscarAbertas() {
        try {
            List<ManutencaoDTO> manutencoes = manutencaoService.findAbertas();
            return ResponseEntity.ok(manutencoes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // GET - Buscar manutenções fechadas
    @GetMapping("/fechadas")
    public ResponseEntity<List<ManutencaoDTO>> buscarFechadas() {
        try {
            List<ManutencaoDTO> manutencoes = manutencaoService.findFechadas();
            return ResponseEntity.ok(manutencoes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // GET - Estatísticas das manutenções
    @GetMapping("/estatisticas")
    public ResponseEntity<ManutencaoService.EstatisticasManutencaoDTO> getEstatisticas() {
        try {
            ManutencaoService.EstatisticasManutencaoDTO estatisticas = manutencaoService.getEstatisticas();
            return ResponseEntity.ok(estatisticas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // POST - Abrir nova manutenção (usando DTO)
    @PostMapping("/abrir")
    public ResponseEntity<?> abrirManutencao(@Valid @RequestBody ManutencaoRequestDTO request) {
        try {
            ManutencaoDTO manutencao = manutencaoService.abrirManutencao(
                request.getMaterialId(), 
                request.getDescricaoProblema(), 
                request.getTecnico(),
                request.getChamadoId()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(manutencao);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno do servidor");
        }
    }
    
 // PATCH - Fechar manutenção (usando DTO)
    @PatchMapping("/{id}/fechar")
    public ResponseEntity<?> fecharManutencao(
            @PathVariable Long id, 
            @Valid @RequestBody FecharManutencaoRequestDTO request) {
        
        try {
            ManutencaoDTO manutencao = manutencaoService.fecharManutencao(id, request.getDescricaoSolucao());
            return ResponseEntity.ok(manutencao);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno do servidor");
        }
    }
}