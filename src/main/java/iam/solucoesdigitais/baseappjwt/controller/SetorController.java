package iam.solucoesdigitais.baseappjwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import iam.solucoesdigitais.baseappjwt.dto.SetorDTO;
import iam.solucoesdigitais.baseappjwt.dto.SetorRequestDTO;
import iam.solucoesdigitais.baseappjwt.service.SetorService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/setores")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})
public class SetorController {
    
    @Autowired
    private SetorService setorService;
    
    // GET - Listar todos os setores
    @GetMapping
    public ResponseEntity<List<SetorDTO>> listarTodos() {
        try {
            List<SetorDTO> setores = setorService.findAll();
            return ResponseEntity.ok(setores);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // GET - Buscar setor por ID
    @GetMapping("/{id}")
    public ResponseEntity<SetorDTO> buscarPorId(@PathVariable Long id) {
        try {
            SetorDTO setor = setorService.findById(id);
            return ResponseEntity.ok(setor);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // GET - Buscar setores por unidade
    @GetMapping("/unidade/{unidadeId}")
    public ResponseEntity<List<SetorDTO>> buscarPorUnidade(@PathVariable Long unidadeId) {
        try {
            List<SetorDTO> setores = setorService.findByUnidadeId(unidadeId);
            return ResponseEntity.ok(setores);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // GET - Buscar setores por nome
    @GetMapping("/buscar")
    public ResponseEntity<List<SetorDTO>> buscarPorNome(@RequestParam String nome) {
        try {
            List<SetorDTO> setores = setorService.findByNome(nome);
            return ResponseEntity.ok(setores);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // POST - Criar novo setor
    @PostMapping
    public ResponseEntity<?> criar(@Valid @RequestBody SetorRequestDTO request) {
        try {
            SetorDTO setor = setorService.create(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(setor);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno do servidor");
        }
    }
    
    // PUT - Atualizar setor
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @Valid @RequestBody SetorRequestDTO request) {
        try {
            SetorDTO setor = setorService.update(id, request);
            return ResponseEntity.ok(setor);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno do servidor");
        }
    }
    
    // DELETE - Deletar setor
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        try {
            setorService.delete(id);
            return ResponseEntity.ok().body("Setor deletado com sucesso");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno do servidor");
        }
    }
}
