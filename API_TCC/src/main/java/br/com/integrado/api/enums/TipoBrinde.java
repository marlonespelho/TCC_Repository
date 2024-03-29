package br.com.integrado.api.enums;

public enum TipoBrinde {
	FIDELIDADE(1), ANIVERSARIO(2);
	
private int tipo;
	
	public int getTipo() {
		return tipo;
	}
	
	TipoBrinde(int tipo){
		this.tipo = tipo;
	}
	
	public static TipoBrinde ObterTipoPorId(int id) {
		try {
			for(TipoBrinde tipo : values()) {
				if (tipo.tipo == id) {
					return tipo;
				}
			}
			return null;
		} catch (Exception e) {
			return null;
		}
	}
}
