package iam.solucoesdigitais.baseappjwt.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import iam.solucoesdigitais.baseappjwt.dto.AvaliacaoResponseDTO;
import iam.solucoesdigitais.baseappjwt.model.AvaliacaoVeiculo;
import iam.solucoesdigitais.baseappjwt.model.FotoAvaliacao;
import iam.solucoesdigitais.baseappjwt.repository.AvaliacaoVeiculoRepository;
import iam.solucoesdigitais.enums.StatusAvaliacao;

@Service
@Transactional
public class AvaliacaoVeiculoService {

    private static final String SUBPASTA = "avaliacoes";

    @Autowired
    private AvaliacaoVeiculoRepository repository;

    @Autowired
    private StorageService storageService;

    @Autowired
    private EmailService emailService;

    @Value("${loja.email.contato:}")
    private String emailLoja;

    /**
     * Recebe os dados do carro usado + fotos enviadas pelo cliente
     * para a loja avaliar (endpoint público, multipart/form-data).
     */
    @Transactional
    public AvaliacaoResponseDTO criar(
            String nome,
            String email,
            String telefone,
            String marca,
            String modelo,
            String versao,
            Integer anoModelo,
            Long quilometragem,
            BigDecimal valorPretendido,
            String descricao,
            List<MultipartFile> fotos) throws IOException {

        AvaliacaoVeiculo avaliacao = new AvaliacaoVeiculo();
        avaliacao.setNome(nome);
        avaliacao.setEmail(email);
        avaliacao.setTelefone(telefone);
        avaliacao.setMarca(marca);
        avaliacao.setModelo(modelo);
        avaliacao.setVersao(versao);
        avaliacao.setAnoModelo(anoModelo);
        avaliacao.setQuilometragem(quilometragem);
        avaliacao.setValorPretendido(valorPretendido);
        avaliacao.setDescricao(descricao);
        avaliacao.setStatus(StatusAvaliacao.PENDENTE);

        repository.save(avaliacao);

        if (fotos != null) {
            for (MultipartFile file : fotos) {
                if (file == null || file.isEmpty()) continue;

                String nomeArquivo = storageService.salvarArquivo(SUBPASTA, avaliacao.getId(), file);

                FotoAvaliacao foto = new FotoAvaliacao();
                foto.setNomeArquivo(nomeArquivo);
                foto.setAvaliacao(avaliacao);
                avaliacao.getFotos().add(foto);
            }
            repository.save(avaliacao);
        }

        notificarLoja(avaliacao);

        return toResponse(avaliacao);
    }

    public List<AvaliacaoResponseDTO> listar() {

        return repository.findAllByOrderByDataEnvioDesc()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public AvaliacaoResponseDTO buscar(Long id) {

        AvaliacaoVeiculo avaliacao = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Avaliação não encontrada"));

        return toResponse(avaliacao);
    }

    public AvaliacaoResponseDTO atualizarStatus(Long id, StatusAvaliacao status) {

        AvaliacaoVeiculo avaliacao = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Avaliação não encontrada"));

        avaliacao.setStatus(status);

        repository.save(avaliacao);

        return toResponse(avaliacao);
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }

    private void notificarLoja(AvaliacaoVeiculo avaliacao) {

        if (emailLoja == null || emailLoja.trim().isEmpty()) return;

        try {
            StringBuilder corpo = new StringBuilder();
            corpo.append("Nova solicitação de avaliação de veículo usado!\n\n");
            corpo.append("Cliente: ").append(avaliacao.getNome()).append("\n");
            if (avaliacao.getEmail() != null) corpo.append("Email: ").append(avaliacao.getEmail()).append("\n");
            if (avaliacao.getTelefone() != null) corpo.append("Telefone: ").append(avaliacao.getTelefone()).append("\n");
            corpo.append("\nVeículo: ").append(avaliacao.getMarca()).append(" ")
                    .append(avaliacao.getModelo());
            if (avaliacao.getAnoModelo() != null) corpo.append(" ").append(avaliacao.getAnoModelo());
            corpo.append("\n");
            if (avaliacao.getQuilometragem() != null) {
                corpo.append("KM: ").append(avaliacao.getQuilometragem()).append("\n");
            }
            if (avaliacao.getValorPretendido() != null) {
                corpo.append("Valor pretendido: R$ ").append(avaliacao.getValorPretendido()).append("\n");
            }
            if (avaliacao.getDescricao() != null) {
                corpo.append("\nDescrição:\n").append(avaliacao.getDescricao());
            }

            emailService.sendEmail(
                    "Nova avaliação de usado - " + avaliacao.getMarca() + " " + avaliacao.getModelo(),
                    emailLoja,
                    corpo.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private AvaliacaoResponseDTO toResponse(AvaliacaoVeiculo avaliacao) {

        AvaliacaoResponseDTO dto = new AvaliacaoResponseDTO();
        dto.setId(avaliacao.getId());
        dto.setNome(avaliacao.getNome());
        dto.setEmail(avaliacao.getEmail());
        dto.setTelefone(avaliacao.getTelefone());
        dto.setMarca(avaliacao.getMarca());
        dto.setModelo(avaliacao.getModelo());
        dto.setVersao(avaliacao.getVersao());
        dto.setAnoModelo(avaliacao.getAnoModelo());
        dto.setQuilometragem(avaliacao.getQuilometragem());
        dto.setValorPretendido(avaliacao.getValorPretendido());
        dto.setDescricao(avaliacao.getDescricao());
        dto.setStatus(avaliacao.getStatus());
        dto.setDataEnvio(avaliacao.getDataEnvio());

        dto.setFotos(avaliacao.getFotos()
                .stream()
                .map(f -> storageService.montarUrl(SUBPASTA, avaliacao.getId(), f.getNomeArquivo()))
                .collect(Collectors.toList()));

        return dto;
    }
}
