package iam.solucoesdigitais.baseappjwt.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import iam.solucoesdigitais.baseappjwt.dto.LeadRequestDTO;
import iam.solucoesdigitais.baseappjwt.dto.LeadResponseDTO;
import iam.solucoesdigitais.baseappjwt.dto.LeadUpdateDTO;
import iam.solucoesdigitais.baseappjwt.model.Lead;
import iam.solucoesdigitais.baseappjwt.model.Veiculo;
import iam.solucoesdigitais.baseappjwt.model.Vendedor;
import iam.solucoesdigitais.baseappjwt.repository.LeadRepository;
import iam.solucoesdigitais.baseappjwt.repository.VeiculoRepository;
import iam.solucoesdigitais.baseappjwt.repository.VendedorRepository;
import iam.solucoesdigitais.enums.OrigemLead;
import iam.solucoesdigitais.enums.StatusLead;

@Service
@Transactional
public class LeadService {

    @Autowired
    private LeadRepository repository;

    @Autowired
    private VeiculoRepository veiculoRepository;

    @Autowired
    private VendedorRepository vendedorRepository;

    @Autowired
    private EmailService emailService;

    @Value("${loja.email.contato:}")
    private String emailLoja;

    /**
     * Cria um lead (chamado pela página pública quando o cliente demonstra
     * interesse: WhatsApp, e-mail, proposta, visita ou financiamento).
     */
    public LeadResponseDTO criar(LeadRequestDTO dto) {

        Lead lead = new Lead();
        lead.setNome(dto.getNome());
        lead.setEmail(dto.getEmail());
        lead.setTelefone(dto.getTelefone());
        lead.setMensagem(dto.getMensagem());
        lead.setOrigem(dto.getOrigem() == null ? OrigemLead.OUTRO : dto.getOrigem());
        lead.setStatus(StatusLead.NOVO);

        if (dto.getVeiculoId() != null) {
            Veiculo veiculo = veiculoRepository.findById(dto.getVeiculoId())
                    .orElse(null);
            lead.setVeiculo(veiculo);
        }

        repository.save(lead);

        notificarLojaPorEmail(lead);

        return toResponse(lead);
    }

    public List<LeadResponseDTO> listar(StatusLead status) {

        List<Lead> leads = status == null
                ? repository.findAllByOrderByDataCriacaoDesc()
                : repository.findByStatusOrderByDataCriacaoDesc(status);

        return leads.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public LeadResponseDTO buscar(Long id) {

        Lead lead = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lead não encontrado"));

        return toResponse(lead);
    }

    /**
     * Atualiza status e/ou vendedor responsável.
     */
    public LeadResponseDTO atualizar(Long id, LeadUpdateDTO dto) {

        Lead lead = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lead não encontrado"));

        if (dto.getStatus() != null) {
            lead.setStatus(dto.getStatus());
        }

        if (dto.getVendedorId() != null) {
            Vendedor vendedor = vendedorRepository.findById(dto.getVendedorId())
                    .orElseThrow(() -> new RuntimeException("Vendedor não encontrado"));
            lead.setVendedor(vendedor);
        }

        repository.save(lead);

        return toResponse(lead);
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }

    private void notificarLojaPorEmail(Lead lead) {

        if (emailLoja == null || emailLoja.trim().isEmpty()) return;

        try {
            StringBuilder corpo = new StringBuilder();
            corpo.append("Novo lead recebido!\n\n");
            corpo.append("Nome: ").append(lead.getNome()).append("\n");
            if (lead.getEmail() != null) corpo.append("Email: ").append(lead.getEmail()).append("\n");
            if (lead.getTelefone() != null) corpo.append("Telefone: ").append(lead.getTelefone()).append("\n");
            corpo.append("Origem: ").append(lead.getOrigem()).append("\n");
            if (lead.getVeiculo() != null) {
                corpo.append("Veículo de interesse: ")
                        .append(lead.getVeiculo().getMarca()).append(" ")
                        .append(lead.getVeiculo().getModelo()).append(" ")
                        .append(lead.getVeiculo().getAnoModelo()).append("\n");
            }
            if (lead.getMensagem() != null) {
                corpo.append("\nMensagem:\n").append(lead.getMensagem());
            }

            emailService.sendEmail("Novo lead - " + lead.getNome(), emailLoja, corpo.toString());
        } catch (Exception e) {
            // Falha no e-mail não pode impedir o registro do lead
            e.printStackTrace();
        }
    }

    private LeadResponseDTO toResponse(Lead lead) {

        LeadResponseDTO dto = new LeadResponseDTO();
        dto.setId(lead.getId());
        dto.setNome(lead.getNome());
        dto.setEmail(lead.getEmail());
        dto.setTelefone(lead.getTelefone());
        dto.setMensagem(lead.getMensagem());
        dto.setOrigem(lead.getOrigem());
        dto.setStatus(lead.getStatus());
        dto.setDataCriacao(lead.getDataCriacao());
        dto.setDataAtualizacao(lead.getDataAtualizacao());

        if (lead.getVeiculo() != null) {
            Veiculo v = lead.getVeiculo();
            dto.setVeiculoId(v.getId());
            dto.setVeiculoDescricao(v.getMarca() + " " + v.getModelo() + " " + v.getAnoModelo());
        }

        if (lead.getVendedor() != null) {
            dto.setVendedorId(lead.getVendedor().getId());
            dto.setVendedorNome(lead.getVendedor().getNome());
        }

        return dto;
    }
}
