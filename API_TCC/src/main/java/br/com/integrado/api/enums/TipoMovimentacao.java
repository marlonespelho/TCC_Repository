package br.com.integrado.api.enums;

public enum TipoMovimentacao {
	ATENDIMENTO(1), ENTRADA(2), BAIXA(3);
	
private int tipo;
	
	public int getTipo() {
		return tipo;
	}
	
	TipoMovimentacao(int tipo){
		this.tipo = tipo;
	}
	
	public static TipoMovimentacao ObterTipoPorId(int id) {
		try {
			for(TipoMovimentacao tipo : values()) {
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
