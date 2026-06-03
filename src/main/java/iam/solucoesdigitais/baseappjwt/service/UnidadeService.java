package iam.solucoesdigitais.baseappjwt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import iam.solucoesdigitais.baseappjwt.dto.UnidadeDTO;
import iam.solucoesdigitais.baseappjwt.dto.UnidadeRequestDTO;
import iam.solucoesdigitais.baseappjwt.model.Unidade;
import iam.solucoesdigitais.baseappjwt.repository.UnidadeRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UnidadeService {
    
    @Autowired
    private UnidadeRepository unidadeRepository;
    
    // Listar todas as unidades com setores
    @Transactional(readOnly = true)
    public List<UnidadeDTO> findAll() {
        return unidadeRepository.findAllWithSetores()
                .stream()
                .map(UnidadeDTO::new)
                .collect(Collectors.toList());
    }
    
    // Buscar unidade por ID
    @Transactional(readOnly = true)
    public UnidadeDTO findById(Long id) {
        Unidade unidade = unidadeRepository.findByIdWithSetores(id)
                .orElseThrow(() -> new RuntimeException("Unidade não encontrada com ID: " + id));
        return new UnidadeDTO(unidade);
    }
    
    // Criar nova unidade
    @Transactional
    public UnidadeDTO create(UnidadeRequestDTO request) {
        // Validar se já existe unidade com o mesmo nome
        if (unidadeRepository.existsByNome(request.getNome())) {
            throw new RuntimeException("Já existe uma unidade com o nome: " + request.getNome());
        }
        
        Unidade unidade = new Unidade();
        unidade.setNome(request.getNome());
        
        Unidade saved = unidadeRepository.save(unidade);
        return new UnidadeDTO(saved);
    }
    
    // Atualizar unidade
    @Transactional
    public UnidadeDTO update(Long id, UnidadeRequestDTO request) {
        Unidade unidade = unidadeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Unidade não encontrada com ID: " + id));
        
        // Validar se o novo nome já existe (exceto para a própria unidade)
        if (!unidade.getNome().equals(request.getNome()) && 
            unidadeRepository.existsByNome(request.getNome())) {
            throw new RuntimeException("Já existe uma unidade com o nome: " + request.getNome());
        }
        
        unidade.setNome(request.getNome());
        Unidade updated = unidadeRepository.save(unidade);
        return new UnidadeDTO(updated);
    }
    
    // Deletar unidade
    @Transactional
    public void delete(Long id) {
        Unidade unidade = unidadeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Unidade não encontrada com ID: " + id));
        
        // Validar se existem materiais ou chamados vinculados
        if (!unidade.getMateriais().isEmpty()) {
            throw new RuntimeException("Não é possível excluir a unidade. Existem materiais vinculados.");
        }
        
        if (!unidade.getChamados().isEmpty()) {
            throw new RuntimeException("Não é possível excluir a unidade. Existem chamados vinculados.");
        }
        
        unidadeRepository.delete(unidade);
    }
    
    // Buscar unidades por nome
    @Transactional(readOnly = true)
    public List<UnidadeDTO> findByNome(String nome) {
        return unidadeRepository.findByNomeContainingIgnoreCase(nome)
                .stream()
                .map(UnidadeDTO::new)
                .collect(Collectors.toList());
    }
}