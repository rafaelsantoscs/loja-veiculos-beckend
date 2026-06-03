package iam.solucoesdigitais.baseappjwt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import iam.solucoesdigitais.baseappjwt.dto.ChamadoDTO;
import iam.solucoesdigitais.baseappjwt.dto.ChamadoRequestDTO;
import iam.solucoesdigitais.baseappjwt.dto.StatusUpdateDTO;
import iam.solucoesdigitais.baseappjwt.model.Chamado;
import iam.solucoesdigitais.baseappjwt.model.Material;
import iam.solucoesdigitais.baseappjwt.model.Setor;
import iam.solucoesdigitais.baseappjwt.model.Unidade;
import iam.solucoesdigitais.baseappjwt.repository.ChamadoRepository;
import iam.solucoesdigitais.baseappjwt.repository.MaterialRepository;
import iam.solucoesdigitais.baseappjwt.repository.SetorRepository;
import iam.solucoesdigitais.baseappjwt.repository.UnidadeRepository;
import iam.solucoesdigitais.enums.StatusChamado;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChamadoService {
    
    @Autowired
    private ChamadoRepository chamadoRepository;
    
    @Autowired
    private UnidadeRepository unidadeRepository;
    
    @Autowired
    private SetorRepository setorRepository;
    
    @Autowired
    private MaterialRepository materialRepository;
    
    // Gerar protocolo único
    private String gerarProtocolo() {
        LocalDateTime agora = LocalDateTime.now();
        String protocolo = "CH" + agora.format(java.time.format.DateTimeFormatter.ofPattern("ddMMyyyyHHmmss"));
        
        // Garantir que o protocolo é único
        int tentativas = 0;
        while (chamadoRepository.existsByProtocolo(protocolo) && tentativas < 10) {
            protocolo = "CH" + agora.format(java.time.format.DateTimeFormatter.ofPattern("ddMMyyyyHHmmss")) + tentativas;
            tentativas++;
        }
        
        if (tentativas >= 10) {
            throw new RuntimeException("Não foi possível gerar um protocolo único");
        }
        
        return protocolo;
    }
    
    // Listar todos os chamados com relacionamentos
    @Transactional(readOnly = true)
    public List<ChamadoDTO> findAll() {
        return chamadoRepository.findAllWithRelations()
                .stream()
                .map(ChamadoDTO::new)
                .collect(Collectors.toList());
    }
    
    // Buscar chamado por ID
    @Transactional(readOnly = true)
    public ChamadoDTO findById(Long id) {
        Chamado chamado = chamadoRepository.findByIdWithRelations(id)
                .orElseThrow(() -> new RuntimeException("Chamado não encontrado com ID: " + id));
        return new ChamadoDTO(chamado);
    }
    
    // Buscar chamado por protocolo
    @Transactional(readOnly = true)
    public ChamadoDTO findByProtocolo(String protocolo) {
        Chamado chamado = chamadoRepository.findByProtocolo(protocolo)
                .orElseThrow(() -> new RuntimeException("Chamado não encontrado com protocolo: " + protocolo));
        return new ChamadoDTO(chamado);
    }
    
    // Criar novo chamado
    @Transactional
    public ChamadoDTO create(ChamadoRequestDTO request) {
        // Buscar entidades relacionadas
        Unidade unidade = unidadeRepository.findById(request.getUnidadeId())
                .orElseThrow(() -> new RuntimeException("Unidade não encontrada com ID: " + request.getUnidadeId()));
        
        Setor setor = setorRepository.findById(request.getSetorId())
                .orElseThrow(() -> new RuntimeException("Setor não encontrado com ID: " + request.getSetorId()));
        
        Material material = materialRepository.findById(request.getMaterialId())
                .orElseThrow(() -> new RuntimeException("Material não encontrado com ID: " + request.getMaterialId()));
        
        // Validar se setor pertence à unidade
        if (!setor.getUnidade().getId().equals(unidade.getId())) {
            throw new RuntimeException("O setor não pertence à unidade selecionada");
        }
        
        // Validar se material pertence à unidade e setor
        if (!Objects.equals(material.getUnidade().getId(), unidade.getId()) ||
        	    !Objects.equals(material.getSetor().getId(), setor.getId())) {
        	    throw new RuntimeException("O material não pertence à unidade/setor selecionados");
        	}
        
        Chamado chamado = new Chamado();
        chamado.setUsuarioAbertura(request.getUsuarioAbertura());
        chamado.setUnidade(unidade);
        chamado.setSetor(setor);
        chamado.setMaterial(material);
        chamado.setProtocolo(gerarProtocolo());
        chamado.setDescricaoProblema(request.getDescricaoProblema());
        
        Chamado saved = chamadoRepository.save(chamado);
        return new ChamadoDTO(saved);
    }
    
    // Atualizar status do chamado
    @Transactional
    public ChamadoDTO updateStatus(Long id, StatusUpdateDTO request) {
        Chamado chamado = chamadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Chamado não encontrado com ID: " + id));
        
        chamado.setStatus(request.getStatusChamado());
        
        // Se for fechado/finalizado, salvar parecer do suporte
        if (request.getStatusChamado() == StatusChamado.FECHADO || 
            request.getStatusChamado() == StatusChamado.FINALIZADO) {
            chamado.setParecerSuporte(request.getParecerSuporte());
        }
        
        Chamado updated = chamadoRepository.save(chamado);
        return new ChamadoDTO(updated);
    }
    
    // Deletar chamado
    @Transactional
    public void delete(Long id) {
        Chamado chamado = chamadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Chamado não encontrado com ID: " + id));
        
        chamadoRepository.delete(chamado);
    }
    
    // Buscar chamados filtrados
    @Transactional(readOnly = true)
    public List<ChamadoDTO> findFiltered(StatusChamado status, Long unidadeId, String protocolo) {
        return chamadoRepository.findFiltered(status, unidadeId, protocolo)
                .stream()
                .map(ChamadoDTO::new)
                .collect(Collectors.toList());
    }
    
    // Buscar chamados por status
    @Transactional(readOnly = true)
    public List<ChamadoDTO> findByStatus(StatusChamado status) {
        return chamadoRepository.findByStatus(status)
                .stream()
                .map(ChamadoDTO::new)
                .collect(Collectors.toList());
    }
    
    // Buscar chamados por unidade
    @Transactional(readOnly = true)
    public List<ChamadoDTO> findByUnidadeId(Long unidadeId) {
        return chamadoRepository.findByUnidadeId(unidadeId)
                .stream()
                .map(ChamadoDTO::new)
                .collect(Collectors.toList());
    }
    
    // Estatísticas
    @Transactional(readOnly = true)
    public EstatisticasChamadosDTO getEstatisticas() {
        EstatisticasChamadosDTO estatisticas = new EstatisticasChamadosDTO();
        estatisticas.setTotal(chamadoRepository.count());
        estatisticas.setAbertos(chamadoRepository.countByStatus(StatusChamado.ABERTO));
        estatisticas.setEmAtendimento(chamadoRepository.countByStatus(StatusChamado.EM_ATENDIMENTO));
        estatisticas.setFechados(chamadoRepository.countByStatus(StatusChamado.FECHADO));
        estatisticas.setFinalizados(chamadoRepository.countByStatus(StatusChamado.FINALIZADO));
        return estatisticas;
    }
    
    // DTO interno para estatísticas
    public static class EstatisticasChamadosDTO {
        private Long total;
        private Long abertos;
        private Long emAtendimento;
        private Long fechados;
        private Long finalizados;
        
        // Getters e Setters
        public Long getTotal() { return total; }
        public void setTotal(Long total) { this.total = total; }
        
        public Long getAbertos() { return abertos; }
        public void setAbertos(Long abertos) { this.abertos = abertos; }
        
        public Long getEmAtendimento() { return emAtendimento; }
        public void setEmAtendimento(Long emAtendimento) { this.emAtendimento = emAtendimento; }
        
        public Long getFechados() { return fechados; }
        public void setFechados(Long fechados) { this.fechados = fechados; }
        
        public Long getFinalizados() { return finalizados; }
        public void setFinalizados(Long finalizados) { this.finalizados = finalizados; }
    }
}