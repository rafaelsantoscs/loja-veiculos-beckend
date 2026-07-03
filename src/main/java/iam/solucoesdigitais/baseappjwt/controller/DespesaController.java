package iam.solucoesdigitais.baseappjwt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import iam.solucoesdigitais.baseappjwt.dto.DespesaRequestDTO;
import iam.solucoesdigitais.baseappjwt.dto.DespesaResponseDTO;
import iam.solucoesdigitais.baseappjwt.service.DespesaService;

@RestController
@RequestMapping("/despesas")
public class DespesaController {

    @Autowired
    private DespesaService service;

    @PostMapping
    public ResponseEntity<DespesaResponseDTO> cadastrar(@RequestBody DespesaRequestDTO dto) {

        return ResponseEntity.ok(service.cadastrar(dto));
    }

    @GetMapping
    public ResponseEntity<List<DespesaResponseDTO>> listar(
            @RequestParam(required = false) Long veiculoId) {

        return ResponseEntity.ok(service.listar(veiculoId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DespesaResponseDTO> atualizar(
            @PathVariable Long id,
            @RequestBody DespesaRequestDTO dto) {

        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {

        service.excluir(id);

        return ResponseEntity.noContent().build();
    }
}
