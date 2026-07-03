package iam.solucoesdigitais.baseappjwt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import iam.solucoesdigitais.baseappjwt.dto.VeiculoRequestDTO;
import iam.solucoesdigitais.baseappjwt.dto.VeiculoResponseDTO;
import iam.solucoesdigitais.baseappjwt.dto.VeiculoStatusDTO;
import iam.solucoesdigitais.baseappjwt.service.VeiculoService;

@RestController
@RequestMapping("/veiculos")
public class VeiculoController {

    @Autowired
    private VeiculoService service;

    @PostMapping
    public ResponseEntity<VeiculoResponseDTO> cadastrar(
            @RequestBody VeiculoRequestDTO dto) {

        return ResponseEntity.ok(service.cadastrar(dto));
    }

    @GetMapping
    public ResponseEntity<List<VeiculoResponseDTO>> listar() {

        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VeiculoResponseDTO> buscar(
            @PathVariable Long id) {

        return ResponseEntity.ok(service.buscar(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(
            @PathVariable Long id) {

        service.excluir(id);

        return ResponseEntity.noContent().build();
    }
    
    
    @PutMapping("/{id}")
    public ResponseEntity<VeiculoResponseDTO> atualizar(
            @PathVariable Long id,
            @RequestBody VeiculoRequestDTO dto) {

        return ResponseEntity.ok(
                service.atualizar(id, dto)
        );
    }
    
    @PatchMapping("/{id}/status")
    public ResponseEntity<VeiculoResponseDTO> atualizarStatus(
            @PathVariable Long id,
            @RequestBody VeiculoStatusDTO dto) {

        return ResponseEntity.ok(
                service.atualizarStatus(id, dto)
        );
    }

    /**
     * Registra uma visualização do veículo (público — página externa).
     */
    @PatchMapping("/{id}/visualizar")
    public ResponseEntity<Void> visualizar(@PathVariable Long id) {

        service.registrarVisualizacao(id);

        return ResponseEntity.noContent().build();
    }
}