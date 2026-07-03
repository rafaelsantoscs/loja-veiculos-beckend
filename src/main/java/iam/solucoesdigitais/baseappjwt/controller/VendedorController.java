package iam.solucoesdigitais.baseappjwt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import iam.solucoesdigitais.baseappjwt.dto.VendedorDTO;
import iam.solucoesdigitais.baseappjwt.service.VendedorService;

@RestController
@RequestMapping("/vendedores")
public class VendedorController {

    @Autowired
    private VendedorService service;

    @PostMapping
    public ResponseEntity<VendedorDTO> cadastrar(@RequestBody VendedorDTO dto) {

        return ResponseEntity.ok(service.cadastrar(dto));
    }

    @GetMapping
    public ResponseEntity<List<VendedorDTO>> listar(
            @RequestParam(required = false, defaultValue = "false") boolean somenteAtivos) {

        return ResponseEntity.ok(service.listar(somenteAtivos));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VendedorDTO> atualizar(
            @PathVariable Long id,
            @RequestBody VendedorDTO dto) {

        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {

        service.excluir(id);

        return ResponseEntity.noContent().build();
    }
}
