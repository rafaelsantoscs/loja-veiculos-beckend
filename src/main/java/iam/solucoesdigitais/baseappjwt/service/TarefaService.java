package iam.solucoesdigitais.baseappjwt.service;

import iam.solucoesdigitais.baseappjwt.dto.SubtarefaDTO;
import iam.solucoesdigitais.baseappjwt.dto.TarefaDTO;
import iam.solucoesdigitais.baseappjwt.model.Subtarefa;
import iam.solucoesdigitais.baseappjwt.model.Tarefa;
import iam.solucoesdigitais.baseappjwt.model.Usuario;
import iam.solucoesdigitais.baseappjwt.repository.SubtarefaRepository;
import iam.solucoesdigitais.baseappjwt.repository.TarefaRepository;
import iam.solucoesdigitais.baseappjwt.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TarefaService {

    @Autowired
    private TarefaRepository tarefaRepository;

    @Autowired
    private SubtarefaRepository subtarefaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Criar nova tarefa
    @Transactional
    public TarefaDTO criarTarefa(TarefaDTO tarefaDTO) {
        Tarefa tarefa = new Tarefa();
        tarefa.setTitulo(tarefaDTO.getTitulo());
        tarefa.setDescricao(tarefaDTO.getDescricao());
        tarefa.setCriadoPor(tarefaDTO.getCriadoPor());
        tarefa.setAtribuidoPara(tarefaDTO.getAtribuidoPara());
        tarefa.setDataPrazo(tarefaDTO.getDataPrazo());
        tarefa.setPrioridade(tarefaDTO.getPrioridade() != null ? tarefaDTO.getPrioridade() : "MEDIA");
        tarefa.setStatus(tarefaDTO.getStatus() != null ? tarefaDTO.getStatus() : "PENDENTE");
        tarefa.setObservacoes(tarefaDTO.getObservacoes());

        // Criar subtarefas se existirem
        if (tarefaDTO.getSubtarefas() != null && !tarefaDTO.getSubtarefas().isEmpty()) {
            for (int i = 0; i < tarefaDTO.getSubtarefas().size(); i++) {
                SubtarefaDTO subDTO = tarefaDTO.getSubtarefas().get(i);
                Subtarefa subtarefa = new Subtarefa();
                subtarefa.setTitulo(subDTO.getTitulo());
                subtarefa.setDescricao(subDTO.getDescricao());
                subtarefa.setOrdem(i);
                subtarefa.setTarefa(tarefa);
                tarefa.getSubtarefas().add(subtarefa);
            }
        }

        tarefa = tarefaRepository.save(tarefa);
        return convertToDTO(tarefa);
    }

    // Atualizar tarefa
    @Transactional
    public TarefaDTO atualizarTarefa(Long id, TarefaDTO tarefaDTO) {
        Tarefa tarefa = tarefaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));

        tarefa.setTitulo(tarefaDTO.getTitulo());
        tarefa.setDescricao(tarefaDTO.getDescricao());
        tarefa.setAtribuidoPara(tarefaDTO.getAtribuidoPara());
        tarefa.setDataPrazo(tarefaDTO.getDataPrazo());
        tarefa.setPrioridade(tarefaDTO.getPrioridade());
        tarefa.setObservacoes(tarefaDTO.getObservacoes());

        tarefa = tarefaRepository.save(tarefa);
        return convertToDTO(tarefa);
    }

    // Atualizar status da tarefa
    @Transactional
    public TarefaDTO atualizarStatus(Long id, String novoStatus) {
        Tarefa tarefa = tarefaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));

        tarefa.setStatus(novoStatus);
        
        if ("CONCLUIDA".equals(novoStatus) && tarefa.getDataConclusao() == null) {
            tarefa.setDataConclusao(LocalDateTime.now());
        } else if (!"CONCLUIDA".equals(novoStatus)) {
            tarefa.setDataConclusao(null);
        }

        tarefa = tarefaRepository.save(tarefa);
        return convertToDTO(tarefa);
    }

    // Marcar subtarefa como concluída
    @Transactional
    public TarefaDTO marcarSubtarefaConcluida(Long tarefaId, Long subtarefaId, String usuario) {
        Subtarefa subtarefa = subtarefaRepository.findById(subtarefaId)
            .orElseThrow(() -> new RuntimeException("Subtarefa não encontrada"));

        if (!subtarefa.getTarefa().getId().equals(tarefaId)) {
            throw new RuntimeException("Subtarefa não pertence a esta tarefa");
        }

        subtarefa.marcarComoConcluida(usuario);
        subtarefaRepository.save(subtarefa);

        // Verificar se todas as subtarefas estão concluídas
        Tarefa tarefa = subtarefa.getTarefa();
        if (tarefa.todasSubtarefasConcluidas()) {
            tarefa.setStatus("EM_ANDAMENTO"); // Pode ser alterado para CONCLUIDA se preferir
        } else if ("PENDENTE".equals(tarefa.getStatus())) {
            tarefa.setStatus("EM_ANDAMENTO");
        }
        
        tarefaRepository.save(tarefa);
        return convertToDTO(tarefa);
    }

    // Desmarcar subtarefa
    @Transactional
    public TarefaDTO desmarcarSubtarefa(Long tarefaId, Long subtarefaId) {
        Subtarefa subtarefa = subtarefaRepository.findById(subtarefaId)
            .orElseThrow(() -> new RuntimeException("Subtarefa não encontrada"));

        if (!subtarefa.getTarefa().getId().equals(tarefaId)) {
            throw new RuntimeException("Subtarefa não pertence a esta tarefa");
        }

        subtarefa.desmarcarConclusao();
        subtarefaRepository.save(subtarefa);

        Tarefa tarefa = subtarefa.getTarefa();
        return convertToDTO(tarefa);
    }

    // Buscar tarefa por ID
    @Transactional(readOnly = true)
    public TarefaDTO buscarPorId(Long id) {
        Tarefa tarefa = tarefaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));
        return convertToDTO(tarefa);
    }

    // Buscar tarefas do usuário
    @Transactional(readOnly = true)
    public List<TarefaDTO> buscarTarefasDoUsuario(String email) {
        List<Tarefa> tarefas = tarefaRepository.findByAtribuidoParaOrderByDataCriacaoDesc(email);
        return tarefas.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // Buscar tarefas criadas pelo usuário (admin)
    @Transactional(readOnly = true)
    public List<TarefaDTO> buscarTarefasCriadasPor(String email) {
        List<Tarefa> tarefas = tarefaRepository.findByCriadoPorOrderByDataCriacaoDesc(email);
        return tarefas.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // Buscar todas as tarefas
    @Transactional(readOnly = true)
    public List<TarefaDTO> buscarTodasTarefas() {
        List<Tarefa> tarefas = tarefaRepository.findAllByOrderByDataCriacaoDesc();
        return tarefas.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // Buscar tarefas por status
    @Transactional(readOnly = true)
    public List<TarefaDTO> buscarPorStatus(String email, String status) {
        List<Tarefa> tarefas = tarefaRepository.findByAtribuidoParaAndStatusOrderByDataCriacaoDesc(email, status);
        return tarefas.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // Buscar tarefas vencidas
    @Transactional(readOnly = true)
    public List<TarefaDTO> buscarTarefasVencidas(String email) {
        List<Tarefa> tarefas = tarefaRepository.findTarefasVencidasByUsuario(email);
        return tarefas.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // Deletar tarefa
    @Transactional
    public void deletarTarefa(Long id) {
        tarefaRepository.deleteById(id);
    }

    // Converter entidade para DTO
    private TarefaDTO convertToDTO(Tarefa tarefa) {
        TarefaDTO dto = new TarefaDTO();
        dto.setId(tarefa.getId());
        dto.setTitulo(tarefa.getTitulo());
        dto.setDescricao(tarefa.getDescricao());
        dto.setCriadoPor(tarefa.getCriadoPor());
        dto.setAtribuidoPara(tarefa.getAtribuidoPara());
        
        // Buscar nome do criador
        usuarioRepository.findByEmail(tarefa.getCriadoPor())
            .ifPresent(usuario -> dto.setCriadoPorNome(usuario.getNome()));
        
        // Processar múltiplos usuários responsáveis
        String[] emails = tarefa.getAtribuidoPara().split(",");
        java.util.List<String> emailsList = new java.util.ArrayList<>();
        java.util.List<String> nomesList = new java.util.ArrayList<>();
        StringBuilder nomesConcat = new StringBuilder();
        
        for (String email : emails) {
            String emailTrim = email.trim();
            emailsList.add(emailTrim);
            usuarioRepository.findByEmail(emailTrim).ifPresent(usuario -> {
                nomesList.add(usuario.getNome());
                if (nomesConcat.length() > 0) {
                    nomesConcat.append(", ");
                }
                nomesConcat.append(usuario.getNome());
            });
        }
        
        dto.setAtribuidoParaEmails(emailsList);
        dto.setAtribuidoParaNomes(nomesList);
        dto.setAtribuidoParaNome(nomesConcat.toString());
        
        dto.setDataCriacao(tarefa.getDataCriacao());
        dto.setDataPrazo(tarefa.getDataPrazo());
        dto.setDataConclusao(tarefa.getDataConclusao());
        dto.setPrioridade(tarefa.getPrioridade());
        dto.setStatus(tarefa.getStatus());
        dto.setObservacoes(tarefa.getObservacoes());
        dto.setProgresso(tarefa.getProgresso());

        if (tarefa.getSubtarefas() != null) {
            dto.setSubtarefas(
                tarefa.getSubtarefas().stream()
                    .map(this::convertSubtarefaToDTO)
                    .collect(Collectors.toList())
            );
        }

        return dto;
    }

    // Converter subtarefa para DTO
    private SubtarefaDTO convertSubtarefaToDTO(Subtarefa subtarefa) {
        SubtarefaDTO dto = new SubtarefaDTO();
        dto.setId(subtarefa.getId());
        dto.setTitulo(subtarefa.getTitulo());
        dto.setDescricao(subtarefa.getDescricao());
        dto.setConcluida(subtarefa.isConcluida());
        dto.setDataConclusao(subtarefa.getDataConclusao());
        dto.setConcluidaPor(subtarefa.getConcluidaPor());
        dto.setOrdem(subtarefa.getOrdem());
        return dto;
    }
}
