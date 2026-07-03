package iam.solucoesdigitais.baseappjwt.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import iam.solucoesdigitais.baseappjwt.service.RelatorioService;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private RelatorioService service;

    @GetMapping("/resumo")
    public ResponseEntity<Map<String, Object>> resumo() {

        return ResponseEntity.ok(service.resumo());
    }

    @GetMapping("/vendas-por-mes")
    public ResponseEntity<List<Map<String, Object>>> vendasPorMes(
            @RequestParam(required = false, defaultValue = "12") int meses) {

        return ResponseEntity.ok(service.vendasPorMes(meses));
    }

    @GetMapping("/vendas-por-vendedor")
    public ResponseEntity<List<Map<String, Object>>> vendasPorVendedor() {

        return ResponseEntity.ok(service.vendasPorVendedor());
    }

    @GetMapping("/mais-visualizados")
    public ResponseEntity<List<Map<String, Object>>> maisVisualizados(
            @RequestParam(required = false, defaultValue = "10") int limite) {

        return ResponseEntity.ok(service.maisVisualizados(limite));
    }

    @GetMapping("/fluxo-caixa")
    public ResponseEntity<Map<String, Object>> fluxoCaixa(
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {

        return ResponseEntity.ok(service.fluxoCaixa(inicio, fim));
    }

    @GetMapping("/estoque-analitico")
    public ResponseEntity<List<Map<String, Object>>> estoqueAnalitico() {

        return ResponseEntity.ok(service.estoqueAnalitico());
    }

    @GetMapping("/giro")
    public ResponseEntity<Map<String, Object>> giro() {

        return ResponseEntity.ok(service.relatorioGiro());
    }
}
