package iam.solucoesdigitais.baseappjwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import iam.solucoesdigitais.baseappjwt.dto.UnidadeDTO;
import iam.solucoesdigitais.baseappjwt.dto.UnidadeRequestDTO;
import iam.solucoesdigitais.baseappjwt.service.UnidadeService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/unidades")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class UnidadeController {
    
    @Autowired
    private UnidadeService unidadeService;
    
    // GET - Listar todas as unidades
    @GetMapping("/listar-unidades")
    public ResponseEntity<List<UnidadeDTO>> listarTodas() {
        try {
            List<UnidadeDTO> unidades = unidadeService.findAll();
            return ResponseEntity.ok(unidades);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // GET - Buscar unidade por ID
    @GetMapping("/{id}")
    public ResponseEntity<UnidadeDTO> buscarPorId(@PathVariable Long id) {
        try {
            UnidadeDTO unidade = unidadeService.findById(id);
            return ResponseEntity.ok(unidade);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // GET - Buscar unidades por nome
    @GetMapping("/buscar")
    public ResponseEntity<List<UnidadeDTO>> buscarPorNome(@RequestParam String nome) {
        try {
            List<UnidadeDTO> unidades = unidadeService.findByNome(nome);
            return ResponseEntity.ok(unidades);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // POST - Criar nova unidade
    @PostMapping
    public ResponseEntity<?> criar(@Valid @RequestBody UnidadeRequestDTO request) {
        try {
            UnidadeDTO unidade = unidadeService.create(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(unidade);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno do servidor");
        }
    }
    
    // PUT - Atualizar unidade
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @Valid @RequestBody UnidadeRequestDTO request) {
        try {
            UnidadeDTO unidade = unidadeService.update(id, request);
            return ResponseEntity.ok(unidade);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno do servidor");
        }
    }
    
    // DELETE - Deletar unidade
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        try {
            unidadeService.delete(id);
            return ResponseEntity.ok().body("Unidade deletada com sucesso");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno do servidor");
        }
    }
}