package iam.solucoesdigitais.baseappjwt.repository;

import org.springframework.stereotype.Repository;

import iam.solucoesdigitais.baseappjwt.model.FotoVeiculo;
import iam.solucoesdigitais.baseappjwt.model.Veiculo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface VeiculoRepository extends JpaRepository<Veiculo,Long> {

	List<Veiculo> findByMarca(String marca);

	List<Veiculo> findByModeloContaining(String modelo);
}
