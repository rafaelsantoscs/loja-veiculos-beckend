package iam.solucoesdigitais.baseappjwt.service;

import iam.solucoesdigitais.baseappjwt.model.Notificacao;
import iam.solucoesdigitais.baseappjwt.repository.NotificacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificacaoService {

    private final NotificacaoRepository notificacaoRepository;
    
    @Autowired
    public NotificacaoService(NotificacaoRepository notificacaoRepository) {
        this.notificacaoRepository = notificacaoRepository;
    }

    public List<Notificacao> listarTodos() {
        return notificacaoRepository.findAll();
    }

    public Optional<Notificacao> buscarPorId(Long id) {
        return notificacaoRepository.findById(id);
    }

    public Notificacao salvar(Notificacao notificacao) {
        return notificacaoRepository.save(notificacao);
    }

    public Notificacao atualizar(Long id, Notificacao notificacaoAtualizada) {
        return notificacaoRepository.findById(id)
                .map(notificacao -> {
                    notificacao.setMensagem(notificacaoAtualizada.getMensagem());
                    notificacao.setEnviadoPara(notificacaoAtualizada.getEnviadoPara());
                    notificacao.setNomeCompleto(notificacaoAtualizada.getNomeCompleto()); // Adicionado
                    return notificacaoRepository.save(notificacao);
                })
                .orElseGet(() -> {
                    notificacaoAtualizada.setId(id);
                    return notificacaoRepository.save(notificacaoAtualizada);
                });
    }

    public void deletar(Long id) {
        notificacaoRepository.deleteById(id);
    }
    
    public List<Notificacao> buscarPorDestinatario(String destinatario) {
        return notificacaoRepository.findByEnviadoPara(destinatario);
    }
    
    public List<Notificacao> buscarPendentesPorDestinatario(String destinatario) {
        return notificacaoRepository.findByEnviadoParaAndMomentoRecebidoIsNull(destinatario);
    }
    
    public Page<Notificacao> buscarRecebidas(Pageable pageable) {
        return notificacaoRepository.findByMomentoRecebidoNotNull(pageable);
    }
    
    public Page<Notificacao> buscarAReceber(Pageable pageable) {
        return notificacaoRepository.findByMomentoRecebidoIsNull(pageable);
    }

    public Notificacao marcarComoRecebida(Long id, String momentoRecebido) {
        return notificacaoRepository.findById(id)
                .map(notificacao -> {
                    notificacao.setMomentoRecebido(momentoRecebido);
                    return notificacaoRepository.save(notificacao);
                })
                .orElseThrow(() -> new RuntimeException("Notificação não encontrada"));
    }
}