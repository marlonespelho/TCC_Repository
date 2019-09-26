package br.com.integrado.api.enums;

public enum PerfilUsuario {
	ROLE_ADMIN(1), ROLE_USUARIO(2);
	
	private int perfilEnum;
	
	PerfilUsuario(int perfil) {
		this.perfilEnum = perfil;
	}
	
	public int getPerfilEnum() {
		return perfilEnum;
	}

	public static PerfilUsuario obterPerfilPeloId(Integer id) {
		try {
			for(PerfilUsuario perfil : values()) {
				if (perfil.perfilEnum == id) {
					return perfil;
				}
			}
			return null;
		} catch (Exception e) {
			return null;
		}
	}
}