package iam.solucoesdigitais.baseappjwt.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import iam.solucoesdigitais.baseappjwt.dto.FotoVeiculoResponseDTO;
import iam.solucoesdigitais.baseappjwt.service.FotoVeiculoService;

@RestController
@RequestMapping("/veiculos")
public class FotoVeiculoController {

    @Autowired
    private FotoVeiculoService fotoVeiculoService;

    /**
     * Upload de uma foto
     */
    @PostMapping("/{veiculoId}/fotos")
    public ResponseEntity<FotoVeiculoResponseDTO> uploadFoto(
            @PathVariable Long veiculoId,
            @RequestParam("file") MultipartFile file) throws IOException {

        return ResponseEntity.ok(
                fotoVeiculoService.upload(veiculoId, file));
    }

    /**
     * Listar fotos do veículo
     */
    @GetMapping("/{veiculoId}/fotos")
    public ResponseEntity<List<FotoVeiculoResponseDTO>> listarFotos(
            @PathVariable Long veiculoId) {

        return ResponseEntity.ok(
                fotoVeiculoService.listar(veiculoId));
    }

    /**
     * Excluir uma foto
     */
    @DeleteMapping("/{veiculoId}/fotos/{fotoId}")
    public ResponseEntity<Void> excluirFoto(
            @PathVariable Long veiculoId,
            @PathVariable Long fotoId) throws IOException {

        fotoVeiculoService.excluir(fotoId,veiculoId);

        return ResponseEntity.noContent().build();
    }

    /**
     * Definir foto principal
     */
    @PatchMapping("/{veiculoId}/fotos/{fotoId}/principal")
    public ResponseEntity<Void> definirPrincipal(
            @PathVariable Long veiculoId,
            @PathVariable Long fotoId) {

        fotoVeiculoService.definirPrincipal(fotoId, veiculoId);

        return ResponseEntity.ok().build();
    }

}