package br.com.integrado.api.enums;

public enum StatusAtendimento {
	ABERTO(1), CONSOLIDADO(2);
	
	private int status;
	
	public int getStatus() {
		return status;
	}
	
	StatusAtendimento(int status){
		this.status = status;
	}
	
	public static StatusAtendimento ObterStatusPorId(int id) {
		try {
			for(StatusAtendimento status : values()) {
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
