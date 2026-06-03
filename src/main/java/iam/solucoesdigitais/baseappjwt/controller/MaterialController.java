package iam.solucoesdigitais.baseappjwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import iam.solucoesdigitais.baseappjwt.dto.MaterialDTO; // ✅ CORRETO
import iam.solucoesdigitais.baseappjwt.dto.MaterialRequestDTO;
import iam.solucoesdigitais.baseappjwt.dto.StatusUpdateDTO;
import iam.solucoesdigitais.baseappjwt.service.MaterialService;
import iam.solucoesdigitais.enums.StatusMaterial;
import iam.solucoesdigitais.enums.TipoMaterial;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/materiais")
@CrossOrigin(origins = "*")
public class MaterialController {
    
    @Autowired
    private MaterialService materialService;
    
 // GET - Listar todos os materiais
    @GetMapping
    public ResponseEntity<List<MaterialDTO>> listarTodos() {
        try {
            // ✅ materialService.findAll() retorna List<MaterialDTO>
            List<MaterialDTO> materiais = materialService.findAll();
            return ResponseEntity.ok(materiais);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // ... resto do código permanece igual

    
    // GET - Buscar material por ID
    @GetMapping("/{id}")
    public ResponseEntity<MaterialDTO> buscarPorId(@PathVariable Long id) {
        try {
            MaterialDTO material = materialService.findById(id);
            return ResponseEntity.ok(material);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // GET - Buscar material por tombamento
    @GetMapping("/tombamento/{tombamento}")
    public ResponseEntity<MaterialDTO> buscarPorTombamento(@PathVariable String tombamento) {
        try {
            MaterialDTO material = materialService.findByTombamento(tombamento);
            return ResponseEntity.ok(material);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // GET - Buscar materiais filtrados
    @GetMapping("/filtrar")
    public ResponseEntity<List<MaterialDTO>> filtrar(
            @RequestParam(required = false) TipoMaterial tipo,
            @RequestParam(required = false) Long unidadeId,
            @RequestParam(required = false) StatusMaterial status,
            @RequestParam(required = false) String tombamento) {
        
        try {
            List<MaterialDTO> materiais = materialService.findFiltered(tipo, unidadeId, status, tombamento);
            return ResponseEntity.ok(materiais);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // GET - Buscar materiais por tipo
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<MaterialDTO>> buscarPorTipo(@PathVariable TipoMaterial tipo) {
        try {
            List<MaterialDTO> materiais = materialService.findByTipo(tipo);
            return ResponseEntity.ok(materiais);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // GET - Buscar materiais por status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<MaterialDTO>> buscarPorStatus(@PathVariable StatusMaterial status) {
        try {
            List<MaterialDTO> materiais = materialService.findByStatus(status);
            return ResponseEntity.ok(materiais);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // GET - Buscar materiais por unidade e setor
    @GetMapping("/unidade/{unidadeId}/setor/{setorId}")
    public ResponseEntity<List<MaterialDTO>> buscarPorUnidadeESetor(
            @PathVariable Long unidadeId,
            @PathVariable Long setorId) {
        try {
            List<MaterialDTO> materiais = materialService.findByUnidadeIdAndSetorId(unidadeId, setorId);
            return ResponseEntity.ok(materiais);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // GET - Buscar materiais por unidade
    @GetMapping("/unidade/{unidadeId}")
    public ResponseEntity<List<MaterialDTO>> buscarPorUnidade(@PathVariable Long unidadeId) {
        try {
            List<MaterialDTO> materiais = materialService.findByUnidadeId(unidadeId);
            return ResponseEntity.ok(materiais);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // POST - Criar novo material
    @PostMapping
    public ResponseEntity<?> criar(@Valid @RequestBody MaterialRequestDTO request) {
        try {
            MaterialDTO material = materialService.create(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(material);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno do servidor");
        }
    }
    
    // PUT - Atualizar material
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @Valid @RequestBody MaterialRequestDTO request) {
        try {
            MaterialDTO material = materialService.update(id, request);
            return ResponseEntity.ok(material);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno do servidor");
        }
    }
    
    // PATCH - Atualizar status do material
    @PatchMapping("/{id}/status")
    public ResponseEntity<?> atualizarStatus(@PathVariable Long id, @Valid @RequestBody StatusUpdateDTO request) {
        try {
            System.out.println("=== DEBUG ATUALIZAR STATUS ===");
            System.out.println("Material ID: " + id);
            System.out.println("Request recebido: " + request);
            System.out.println("Status Material: " + request.getStatusMaterial());
            System.out.println("Status Chamado: " + request.getStatusChamado());
            System.out.println("Parecer: " + request.getParecerSuporte());
            
            MaterialDTO material = materialService.updateStatus(id, request);
            return ResponseEntity.ok(material);
        } catch (RuntimeException e) {
            System.out.println("Erro RuntimeException: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro Exception: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno do servidor");
        }
    }
    
    // DELETE - Deletar material
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        try {
            materialService.delete(id);
            return ResponseEntity.ok().body("Material deletado com sucesso");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno do servidor");
        }
    }
}