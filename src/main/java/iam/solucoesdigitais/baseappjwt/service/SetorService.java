package iam.solucoesdigitais.baseappjwt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import iam.solucoesdigitais.baseappjwt.dto.SetorDTO;
import iam.solucoesdigitais.baseappjwt.dto.SetorRequestDTO;
import iam.solucoesdigitais.baseappjwt.model.Setor;
import iam.solucoesdigitais.baseappjwt.model.Unidade;
import iam.solucoesdigitais.baseappjwt.repository.SetorRepository;
import iam.solucoesdigitais.baseappjwt.repository.UnidadeRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SetorService {
    
    @Autowired
    private SetorRepository setorRepository;
    
    @Autowired
    private UnidadeRepository unidadeRepository;
    
    // Listar todos os setores com unidade
    @Transactional(readOnly = true)
    public List<SetorDTO> findAll() {
        return setorRepository.findAllWithUnidade()
                .stream()
                .map(SetorDTO::new)
                .collect(Collectors.toList());
    }
    
    // Buscar setor por ID
    @Transactional(readOnly = true)
    public SetorDTO findById(Long id) {
        Setor setor = setorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Setor não encontrado com ID: " + id));
        return new SetorDTO(setor);
    }
    
    // Criar novo setor
    @Transactional
    public SetorDTO create(SetorRequestDTO request) {
        // Buscar unidade
        Unidade unidade = unidadeRepository.findById(request.getUnidadeId())
                .orElseThrow(() -> new RuntimeException("Unidade não encontrada com ID: " + request.getUnidadeId()));
        
        // Validar se já existe setor com o mesmo nome na unidade
        if (setorRepository.existsByNomeAndUnidadeId(request.getNome(), request.getUnidadeId())) {
            throw new RuntimeException("Já existe um setor com o nome '" + request.getNome() + "' na unidade " + unidade.getNome());
        }
        
        Setor setor = new Setor();
        setor.setNome(request.getNome());
        setor.setUnidade(unidade);
        
        Setor saved = setorRepository.save(setor);
        return new SetorDTO(saved);
    }
    
    // Atualizar setor
    @Transactional
    public SetorDTO update(Long id, SetorRequestDTO request) {
        Setor setor = setorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Setor não encontrado com ID: " + id));
        
        Unidade unidade = unidadeRepository.findById(request.getUnidadeId())
                .orElseThrow(() -> new RuntimeException("Unidade não encontrada com ID: " + request.getUnidadeId()));
        
        // Validar se o novo nome já existe na unidade (exceto para o próprio setor)
        if (!setor.getNome().equals(request.getNome()) && 
            setorRepository.existsByNomeAndUnidadeId(request.getNome(), request.getUnidadeId())) {
            throw new RuntimeException("Já existe um setor com o nome '" + request.getNome() + "' na unidade " + unidade.getNome());
        }
        
        setor.setNome(request.getNome());
        setor.setUnidade(unidade);
        
        Setor updated = setorRepository.save(setor);
        return new SetorDTO(updated);
    }
    
    // Deletar setor
    @Transactional
    public void delete(Long id) {
        Setor setor = setorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Setor não encontrado com ID: " + id));
        
        // Validar se existem materiais ou chamados vinculados
        if (!setor.getMateriais().isEmpty()) {
            throw new RuntimeException("Não é possível excluir o setor. Existem materiais vinculados.");
        }
        
        if (!setor.getChamados().isEmpty()) {
            throw new RuntimeException("Não é possível excluir o setor. Existem chamados vinculados.");
        }
        
        setorRepository.delete(setor);
    }
    
    // Buscar setores por unidade
    @Transactional(readOnly = true)
    public List<SetorDTO> findByUnidadeId(Long unidadeId) {
        return setorRepository.findByUnidadeIdWithUnidade(unidadeId)
                .stream()
                .map(SetorDTO::new)
                .collect(Collectors.toList());
    }
    
    // Buscar setores por nome
    @Transactional(readOnly = true)
    public List<SetorDTO> findByNome(String nome) {
        return setorRepository.findByNomeContainingIgnoreCase(nome)
                .stream()
                .map(SetorDTO::new)
                .collect(Collectors.toList());
    }
}
