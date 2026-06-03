package iam.solucoesdigitais.baseappjwt.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import iam.solucoesdigitais.baseappjwt.model.Manutencao;
import iam.solucoesdigitais.enums.StatusChamado;
import iam.solucoesdigitais.enums.StatusMaterial;
import iam.solucoesdigitais.enums.TipoMaterial;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/enums")
@CrossOrigin(origins = "*")
public class EnumController {
    
    @GetMapping("/tipos-material")
    public ResponseEntity<List<EnumDTO>> getTiposMaterial() {
        List<EnumDTO> tipos = Arrays.stream(TipoMaterial.values())
                .map(tipo -> new EnumDTO(tipo.name(), tipo.toString()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(tipos);
    }
    
    @GetMapping("/status-material")
    public ResponseEntity<List<EnumDTO>> getStatusMaterial() {
        List<EnumDTO> status = Arrays.stream(StatusMaterial.values())
                .map(s -> new EnumDTO(s.name(), s.toString()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(status);
    }
    
    @GetMapping("/status-chamado")
    public ResponseEntity<List<EnumDTO>> getStatusChamado() {
        List<EnumDTO> status = Arrays.stream(StatusChamado.values())
                .map(s -> new EnumDTO(s.name(), s.toString()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(status);
    }
    
    @GetMapping("/status-manutencao")
    public ResponseEntity<List<EnumDTO>> getStatusManutencao() {
        List<EnumDTO> status = Arrays.stream(Manutencao.StatusManutencao.values())
                .map(s -> new EnumDTO(s.name(), s.toString()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(status);
    }
    
    // DTO interno para retornar enums
    public static class EnumDTO {
        private String valor;
        private String descricao;
        
        public EnumDTO(String valor, String descricao) {
            this.valor = valor;
            this.descricao = descricao;
        }
        
        // Getters
        public String getValor() { return valor; }
        public String getDescricao() { return descricao; }
    }
}
