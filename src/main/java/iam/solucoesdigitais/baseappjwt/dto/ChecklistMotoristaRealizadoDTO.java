package iam.solucoesdigitais.baseappjwt.dto;

import java.util.Date;
import java.util.List;

public class ChecklistMotoristaRealizadoDTO {
    private Long id;
    private String registradoPor;
    private String recebidoPor;
    private Date momentoFinalizado;
    private String cpf;
    private String status;
    private String tipo;
    private String tipoVeiculo;
    private String placa;
    private String observacoes;
    private String obsGestor;
    private Integer totalItensFaltantes;
    private Date dataCriacao;
    
    private List<ItemChecklistMotoristaDTO> itens;
	    public ChecklistMotoristaRealizadoDTO() {}
	    
		public ChecklistMotoristaRealizadoDTO(
				Long id, 
				String registradoPor, 
				String recebidoPor, 
				Date momentoFinalizado, 
				String cpf, 
				String status, 
				String tipo,
				String tipoVeiculo, 
				String placa, 
				String observacoes, 
				String obsGestor, 
				Integer totalItensFaltantes, 
				Date dataCriacao,
				List<ItemChecklistMotoristaDTO> itens) {
			this.id = id;
			this.registradoPor = registradoPor;
			this.recebidoPor = recebidoPor;
			this.momentoFinalizado = momentoFinalizado;
			this.cpf = cpf;
			this.status = status;
			this.tipo = tipo;
			this.tipoVeiculo = tipoVeiculo;
			this.placa = placa;
			this.observacoes = observacoes;
			this.obsGestor = obsGestor;
			this.totalItensFaltantes = totalItensFaltantes;
			this.dataCriacao = dataCriacao;
			this.itens = itens;
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getRegistradoPor() {
			return registradoPor;
		}

		public void setRegistradoPor(String registradoPor) {
			this.registradoPor = registradoPor;
		}

		public String getCpf() {
			return cpf;
		}

		public void setCpf(String cpf) {
			this.cpf = cpf;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public String getTipo() {
			return tipo;
		}

		public void setTipo(String tipo) {
			this.tipo = tipo;
		}

		public String getTipoVeiculo() {
			return tipoVeiculo;
		}

		public void setTipoVeiculo(String tipoVeiculo) {
			this.tipoVeiculo = tipoVeiculo;
		}

		public String getPlaca() {
			return placa;
		}

		public void setPlaca(String placa) {
			this.placa = placa;
		}

		public String getObservacoes() {
			return observacoes;
		}

		public void setObservacoes(String observacoes) {
			this.observacoes = observacoes;
		}
		
		public String getObsGestor() {
			return obsGestor;
		}

		public void setObsGestor(String obsGestor) {
			this.obsGestor = obsGestor;
		}

		public Integer getTotalItensFaltantes() {
			return totalItensFaltantes;
		}

		public void setTotalItensFaltantes(Integer totalItensFaltantes) {
			this.totalItensFaltantes = totalItensFaltantes;
		}

		public Date getDataCriacao() {
			return dataCriacao;
		}

		public void setDataCriacao(Date dataCriacao) {
			this.dataCriacao = dataCriacao;
		}

		public List<ItemChecklistMotoristaDTO> getItens() {
			return itens;
		}

		public void setItens(List<ItemChecklistMotoristaDTO> itens) {
			this.itens = itens;
		}

		public String getRecebidoPor() {
			return recebidoPor;
		}

		public void setRecebidoPor(String recebidoPor) {
			this.recebidoPor = recebidoPor;
		}

		public Date getMomentoFinalizado() {
			return momentoFinalizado;
		}

		public void setMomentoFinalizado(Date momentoFinalizado) {
			this.momentoFinalizado = momentoFinalizado;
		}
		
		
		
}
