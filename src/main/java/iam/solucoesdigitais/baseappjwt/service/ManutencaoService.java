package iam.solucoesdigitais.baseappjwt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import iam.solucoesdigitais.baseappjwt.dto.ManutencaoDTO;
import iam.solucoesdigitais.baseappjwt.model.Manutencao;
import iam.solucoesdigitais.baseappjwt.model.Material;
import iam.solucoesdigitais.baseappjwt.repository.ManutencaoRepository;
import iam.solucoesdigitais.baseappjwt.repository.MaterialRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ManutencaoService {
    
    @Autowired
    private ManutencaoRepository manutencaoRepository;
    
    @Autowired
    private MaterialRepository materialRepository;
    
    // Listar todas as manutenções com material
    @Transactional(readOnly = true)
    public List<ManutencaoDTO> findAll() {
        return manutencaoRepository.findAllWithMaterial()
                .stream()
                .map(ManutencaoDTO::new)
                .collect(Collectors.toList());
    }
    
    // Buscar manutenção por ID
    @Transactional(readOnly = true)
    public ManutencaoDTO findById(Long id) {
        Manutencao manutencao = manutencaoRepository.findByIdWithMaterial(id)
                .orElseThrow(() -> new RuntimeException("Manutenção não encontrada com ID: " + id));
        return new ManutencaoDTO(manutencao);
    }
    
    // Abrir nova manutenção
    @Transactional
    public ManutencaoDTO abrirManutencao(Long materialId, String descricaoProblema, String tecnico, Long chamadoId) {
        Material material = materialRepository.findById(materialId)
                .orElseThrow(() -> new RuntimeException("Material não encontrado com ID: " + materialId));
        
        Manutencao manutencao = new Manutencao();
        manutencao.setMaterial(material);
        manutencao.setDescricaoProblema(descricaoProblema);
        manutencao.setTecnico(tecnico);
        manutencao.setChamadoId(chamadoId);
        
        Manutencao saved = manutencaoRepository.save(manutencao);
        return new ManutencaoDTO(saved);
    }
    
    // Abrir nova manutenção (método de compatibilidade sem chamadoId)
    @Transactional
    public ManutencaoDTO abrirManutencao(Long materialId, String descricaoProblema, String tecnico) {
        return abrirManutencao(materialId, descricaoProblema, tecnico, null);
    }
    
    // Fechar manutenção
    @Transactional
    public ManutencaoDTO fecharManutencao(Long id, String descricaoSolucao) {
        Manutencao manutencao = manutencaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Manutenção não encontrada com ID: " + id));
        
        if (manutencao.getStatus() == Manutencao.StatusManutencao.FECHADA) {
            throw new RuntimeException("A manutenção já está fechada");
        }
        
        manutencao.fecharManutencao(descricaoSolucao);
        
        Manutencao updated = manutencaoRepository.save(manutencao);
        return new ManutencaoDTO(updated);
    }
    
    // Buscar manutenções por material
    @Transactional(readOnly = true)
    public List<ManutencaoDTO> findByMaterialId(Long materialId) {
        return manutencaoRepository.findByMaterialIdWithMaterial(materialId)
                .stream()
                .map(ManutencaoDTO::new)
                .collect(Collectors.toList());
    }
    
    // Buscar manutenções por status
    @Transactional(readOnly = true)
    public List<ManutencaoDTO> findByStatus(Manutencao.StatusManutencao status) {
        return manutencaoRepository.findByStatus(status)
                .stream()
                .map(ManutencaoDTO::new)
                .collect(Collectors.toList());
    }
    
    // Buscar manutenções abertas
    @Transactional(readOnly = true)
    public List<ManutencaoDTO> findAbertas() {
        return manutencaoRepository.findByStatusOrderByDataAberturaDesc(Manutencao.StatusManutencao.ABERTA)
                .stream()
                .map(ManutencaoDTO::new)
                .collect(Collectors.toList());
    }
    
    // Buscar manutenções fechadas
    @Transactional(readOnly = true)
    public List<ManutencaoDTO> findFechadas() {
        return manutencaoRepository.findByStatusOrderByDataAberturaDesc(Manutencao.StatusManutencao.FECHADA)
                .stream()
                .map(ManutencaoDTO::new)
                .collect(Collectors.toList());
    }
    
    // Estatísticas
    @Transactional(readOnly = true)
    public EstatisticasManutencaoDTO getEstatisticas() {
        EstatisticasManutencaoDTO estatisticas = new EstatisticasManutencaoDTO();
        estatisticas.setTotal(manutencaoRepository.count());
        estatisticas.setAbertas(manutencaoRepository.countByStatus(Manutencao.StatusManutencao.ABERTA));
        estatisticas.setFechadas(manutencaoRepository.countByStatus(Manutencao.StatusManutencao.FECHADA));
        return estatisticas;
    }
    
    // DTO interno para estatísticas
    public static class EstatisticasManutencaoDTO {
        private Long total;
        private Long abertas;
        private Long fechadas;
        
        // Getters e Setters
        public Long getTotal() { return total; }
        public void setTotal(Long total) { this.total = total; }
        
        public Long getAbertas() { return abertas; }
        public void setAbertas(Long abertas) { this.abertas = abertas; }
        
        public Long getFechadas() { return fechadas; }
        public void setFechadas(Long fechadas) { this.fechadas = fechadas; }
    }
}
