package iam.solucoesdigitais.baseappjwt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import iam.solucoesdigitais.baseappjwt.dto.LeadRequestDTO;
import iam.solucoesdigitais.baseappjwt.dto.LeadResponseDTO;
import iam.solucoesdigitais.baseappjwt.dto.LeadUpdateDTO;
import iam.solucoesdigitais.baseappjwt.service.LeadService;
import iam.solucoesdigitais.enums.StatusLead;

@RestController
@RequestMapping("/leads")
public class LeadController {

    @Autowired
    private LeadService service;

    /**
     * Criação pública — usada pelo site quando o cliente demonstra interesse.
     */
    @PostMapping
    public ResponseEntity<LeadResponseDTO> criar(@RequestBody LeadRequestDTO dto) {

        return ResponseEntity.ok(service.criar(dto));
    }

    @GetMapping
    public ResponseEntity<List<LeadResponseDTO>> listar(
            @RequestParam(required = false) StatusLead status) {

        return ResponseEntity.ok(service.listar(status));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LeadResponseDTO> buscar(@PathVariable Long id) {

        return ResponseEntity.ok(service.buscar(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<LeadResponseDTO> atualizar(
            @PathVariable Long id,
            @RequestBody LeadUpdateDTO dto) {

        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {

        service.excluir(id);

        return ResponseEntity.noContent().build();
    }
}
