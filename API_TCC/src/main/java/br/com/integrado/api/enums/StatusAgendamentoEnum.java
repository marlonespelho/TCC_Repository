package br.com.integrado.api.enums;

public enum StatusAgendamentoEnum {
	ABERTO(1), AGENDADO(2), CONFIRMADO(3), CANCELADO(4), ATENDIDO(5), NAO_COMPARECIMENTO(6), INATIVO(7);
	
	private int status;
			
	public int getStatus() {
		return status;
	}
	
	StatusAgendamentoEnum(int status){
		this.status = status;
	}
	
	public static StatusAgendamentoEnum ObterStatusPorId(int id) {
		try {
			for(StatusAgendamentoEnum status : values()) {
				if (status.status == id) {
					return status;
				}
			}
			return null;
		} catch (Exception e) {
			return null;
		}
	}
	
}
