package iam.solucoesdigitais.baseappjwt.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import iam.solucoesdigitais.baseappjwt.dto.VendedorDTO;
import iam.solucoesdigitais.baseappjwt.model.Vendedor;
import iam.solucoesdigitais.baseappjwt.repository.VendedorRepository;

@Service
public class VendedorService {

    @Autowired
    private VendedorRepository repository;

    public VendedorDTO cadastrar(VendedorDTO dto) {

        Vendedor vendedor = new Vendedor();
        aplicar(vendedor, dto);

        repository.save(vendedor);

        return toResponse(vendedor);
    }

    public VendedorDTO atualizar(Long id, VendedorDTO dto) {

        Vendedor vendedor = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vendedor não encontrado"));

        aplicar(vendedor, dto);

        repository.save(vendedor);

        return toResponse(vendedor);
    }

    public List<VendedorDTO> listar(boolean somenteAtivos) {

        List<Vendedor> vendedores = somenteAtivos
                ? repository.findByAtivoTrueOrderByNome()
                : repository.findAll();

        return vendedores.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }

    private void aplicar(Vendedor vendedor, VendedorDTO dto) {
        vendedor.setNome(dto.getNome());
        vendedor.setEmail(dto.getEmail());
        vendedor.setTelefone(dto.getTelefone());
        vendedor.setPercentualComissao(
                dto.getPercentualComissao() == null ? BigDecimal.ZERO : dto.getPercentualComissao());
        vendedor.setAtivo(dto.getAtivo() == null ? Boolean.TRUE : dto.getAtivo());
    }

    private VendedorDTO toResponse(Vendedor vendedor) {
        VendedorDTO dto = new VendedorDTO();
        dto.setId(vendedor.getId());
        dto.setNome(vendedor.getNome());
        dto.setEmail(vendedor.getEmail());
        dto.setTelefone(vendedor.getTelefone());
        dto.setPercentualComissao(vendedor.getPercentualComissao());
        dto.setAtivo(vendedor.getAtivo());
        return dto;
    }
}
