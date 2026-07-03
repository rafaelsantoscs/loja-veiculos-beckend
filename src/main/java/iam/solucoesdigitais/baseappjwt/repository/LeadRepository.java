package iam.solucoesdigitais.baseappjwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import iam.solucoesdigitais.baseappjwt.model.Lead;
import iam.solucoesdigitais.enums.StatusLead;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LeadRepository extends JpaRepository<Lead, Long> {

    List<Lead> findAllByOrderByDataCriacaoDesc();

    List<Lead> findByStatusOrderByDataCriacaoDesc(StatusLead status);

    long countByDataCriacaoBetween(LocalDateTime inicio, LocalDateTime fim);

    long countByStatus(StatusLead status);

    long countByVeiculoId(Long veiculoId);
}
