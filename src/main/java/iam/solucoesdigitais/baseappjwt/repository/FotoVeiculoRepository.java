package iam.solucoesdigitais.baseappjwt.repository;

import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

import iam.solucoesdigitais.baseappjwt.model.FotoVeiculo;

import org.springframework.data.jpa.repository.JpaRepository;
@Repository
public interface FotoVeiculoRepository extends JpaRepository <FotoVeiculo, Long> {

    List<FotoVeiculo> findByVeiculoIdOrderByOrdem(Long veiculoId);

    Optional<FotoVeiculo> findByIdAndVeiculoId(Long fotoId, Long veiculoId);

    Optional<FotoVeiculo> findFirstByVeiculoIdAndPrincipalTrue(Long veiculoId);

    Optional<FotoVeiculo> findFirstByVeiculoIdOrderByOrdem(Long veiculoId);

}
