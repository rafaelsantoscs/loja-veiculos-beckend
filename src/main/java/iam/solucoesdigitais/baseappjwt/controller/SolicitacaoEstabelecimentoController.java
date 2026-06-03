package iam.solucoesdigitais.baseappjwt.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/solicitacoes")
public class SolicitacaoEstabelecimentoController {

    @GetMapping
    public ResponseEntity<Map<String, Object>> listarSolicitacoes(Authentication authentication) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            // Por enquanto, retornando uma lista vazia
            List<Object> solicitacoes = new ArrayList<>();
            
            response.put("success", true);
            response.put("message", "Solicitações carregadas com sucesso!");
            response.put("data", solicitacoes);
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Erro interno do servidor: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}