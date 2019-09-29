package br.com.integrado.api.enums;

public enum StatusAgendamento {
	ABERTO(1), AGENDADO(2), CONFIRMADO(3), CANCELADO(4), ATENDENDO(5),ATENDIDO(6), NAO_COMPARECIMENTO(7), INATIVO(8);
	
	private int status;
			
	public int getStatus() {
		return status;
	}
	
	StatusAgendamento(int status){
		this.status = status;
	}
	
	public static StatusAgendamento ObterStatusPorId(int id) {
		try {
			for(StatusAgendamento status : values()) {
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
