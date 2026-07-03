package iam.solucoesdigitais.baseappjwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import iam.solucoesdigitais.baseappjwt.model.Despesa;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DespesaRepository extends JpaRepository<Despesa, Long> {

    List<Despesa> findAllByOrderByDataDesc();

    List<Despesa> findByDataBetween(LocalDate inicio, LocalDate fim);

    List<Despesa> findByVeiculoId(Long veiculoId);
}
