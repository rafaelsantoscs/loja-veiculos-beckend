package iam.solucoesdigitais.baseappjwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import iam.solucoesdigitais.baseappjwt.model.Venda;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface VendaRepository extends JpaRepository<Venda, Long> {

    List<Venda> findAllByOrderByDataVendaDesc();

    List<Venda> findByDataVendaBetween(LocalDate inicio, LocalDate fim);

    Optional<Venda> findFirstByVeiculoIdOrderByDataVendaDesc(Long veiculoId);
}
