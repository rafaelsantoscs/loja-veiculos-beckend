package iam.solucoesdigitais.baseappjwt.dto;

import java.math.BigDecimal;

public class AbastecimentosResponse {
    private int totalAbastecimentosDiesel;
    private int totalAbastecimentosGasolina;
    private int totalAbastecimentosArla;
    private BigDecimal totalValorAbastecimentosDiesel;
    private BigDecimal totalValorAbastecimentosGasolina;
    private BigDecimal totalValorAbastecimentosArla;

    public AbastecimentosResponse(
            int totalAbastecimentosDiesel, int totalAbastecimentosGasolina, int totalAbastecimentosArla,
            BigDecimal totalValorAbastecimentosDiesel, BigDecimal totalValorAbastecimentosGasolina, BigDecimal totalValorAbastecimentosArla) {
        this.totalAbastecimentosDiesel = totalAbastecimentosDiesel;
        this.totalAbastecimentosGasolina = totalAbastecimentosGasolina;
        this.totalAbastecimentosArla = totalAbastecimentosArla;
        this.totalValorAbastecimentosDiesel = totalValorAbastecimentosDiesel;
        this.totalValorAbastecimentosGasolina = totalValorAbastecimentosGasolina;
        this.totalValorAbastecimentosArla = totalValorAbastecimentosArla;
    }

	public int getTotalAbastecimentosDiesel() {
		return totalAbastecimentosDiesel;
	}

	public void setTotalAbastecimentosDiesel(int totalAbastecimentosDiesel) {
		this.totalAbastecimentosDiesel = totalAbastecimentosDiesel;
	}

	public int getTotalAbastecimentosGasolina() {
		return totalAbastecimentosGasolina;
	}

	public void setTotalAbastecimentosGasolina(int totalAbastecimentosGasolina) {
		this.totalAbastecimentosGasolina = totalAbastecimentosGasolina;
	}

	public int getTotalAbastecimentosArla() {
		return totalAbastecimentosArla;
	}

	public void setTotalAbastecimentosArla(int totalAbastecimentosArla) {
		this.totalAbastecimentosArla = totalAbastecimentosArla;
	}

	public BigDecimal getTotalValorAbastecimentosDiesel() {
		return totalValorAbastecimentosDiesel;
	}

	public void setTotalValorAbastecimentosDiesel(BigDecimal totalValorAbastecimentosDiesel) {
		this.totalValorAbastecimentosDiesel = totalValorAbastecimentosDiesel;
	}

	public BigDecimal getTotalValorAbastecimentosGasolina() {
		return totalValorAbastecimentosGasolina;
	}

	public void setTotalValorAbastecimentosGasolina(BigDecimal totalValorAbastecimentosGasolina) {
		this.totalValorAbastecimentosGasolina = totalValorAbastecimentosGasolina;
	}

	public BigDecimal getTotalValorAbastecimentosArla() {
		return totalValorAbastecimentosArla;
	}

	public void setTotalValorAbastecimentosArla(BigDecimal totalValorAbastecimentosArla) {
		this.totalValorAbastecimentosArla = totalValorAbastecimentosArla;
	}
    
	
	
}
