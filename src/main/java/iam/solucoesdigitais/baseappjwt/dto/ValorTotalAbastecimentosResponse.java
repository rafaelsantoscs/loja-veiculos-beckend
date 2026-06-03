package iam.solucoesdigitais.baseappjwt.dto;

public class ValorTotalAbastecimentosResponse {

	private int valortTotalAbastecimentosDiesel;
	private int valortTotalAbastecimentosGasolina;
	private int valortTotalAbastecimentosArla;

    public ValorTotalAbastecimentosResponse(
    		int valortTotalAbastecimentosDiesel, 
    		int valortTotalAbastecimentosGasolina,
    		int valortTotalAbastecimentosArla
    		) 
    {
        this.valortTotalAbastecimentosDiesel = valortTotalAbastecimentosDiesel;
        this.valortTotalAbastecimentosGasolina = valortTotalAbastecimentosGasolina;
        this.valortTotalAbastecimentosArla = valortTotalAbastecimentosArla;
    }

	public int getValortTotalAbastecimentosDiesel() {
		return valortTotalAbastecimentosDiesel;
	}

	public void setValortTotalAbastecimentosDiesel(int valortTotalAbastecimentosDiesel) {
		this.valortTotalAbastecimentosDiesel = valortTotalAbastecimentosDiesel;
	}

	public int getValortTotalAbastecimentosGasolina() {
		return valortTotalAbastecimentosGasolina;
	}

	public void setValortTotalAbastecimentosGasolina(int valortTotalAbastecimentosGasolina) {
		this.valortTotalAbastecimentosGasolina = valortTotalAbastecimentosGasolina;
	}

	public int getValortTotalAbastecimentosArla() {
		return valortTotalAbastecimentosArla;
	}

	public void setValortTotalAbastecimentosArla(int valortTotalAbastecimentosArla) {
		this.valortTotalAbastecimentosArla = valortTotalAbastecimentosArla;
	}

}
