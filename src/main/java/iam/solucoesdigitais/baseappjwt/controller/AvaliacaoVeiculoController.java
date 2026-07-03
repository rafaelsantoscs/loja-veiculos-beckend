package iam.solucoesdigitais.baseappjwt.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import iam.solucoesdigitais.baseappjwt.dto.AvaliacaoResponseDTO;
import iam.solucoesdigitais.baseappjwt.service.AvaliacaoVeiculoService;
import iam.solucoesdigitais.enums.StatusAvaliacao;

@RestController
@RequestMapping("/avaliacoes")
public class AvaliacaoVeiculoController {

    @Autowired
    private AvaliacaoVeiculoService service;

    /**
     * Envio público (multipart): dados do carro usado + fotos.
     */
    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<AvaliacaoResponseDTO> criar(
            @RequestParam String nome,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String telefone,
            @RequestParam String marca,
            @RequestParam String modelo,
            @RequestParam(required = false) String versao,
            @RequestParam(required = false) Integer anoModelo,
            @RequestParam(required = false) Long quilometragem,
            @RequestParam(required = false) BigDecimal valorPretendido,
            @RequestParam(required = false) String descricao,
            @RequestParam(name = "fotos", required = false) List<MultipartFile> fotos)
            throws IOException {

        return ResponseEntity.ok(service.criar(
                nome, email, telefone, marca, modelo, versao,
                anoModelo, quilometragem, valorPretendido, descricao, fotos));
    }

    @GetMapping
    public ResponseEntity<List<AvaliacaoResponseDTO>> listar() {

        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AvaliacaoResponseDTO> buscar(@PathVariable Long id) {

        return ResponseEntity.ok(service.buscar(id));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<AvaliacaoResponseDTO> atualizarStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {

        StatusAvaliacao status = StatusAvaliacao.valueOf(body.get("status"));

        return ResponseEntity.ok(service.atualizarStatus(id, status));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {

        service.excluir(id);

        return ResponseEntity.noContent().build();
    }
}
