package iam.solucoesdigitais.baseappjwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import iam.solucoesdigitais.baseappjwt.model.AvaliacaoVeiculo;

import java.util.List;

@Repository
public interface AvaliacaoVeiculoRepository extends JpaRepository<AvaliacaoVeiculo, Long> {

    List<AvaliacaoVeiculo> findAllByOrderByDataEnvioDesc();
}
