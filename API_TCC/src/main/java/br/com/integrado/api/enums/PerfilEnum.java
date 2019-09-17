package br.com.integrado.api.enums;

public enum PerfilEnum {
	ROLE_ADMIN(1), ROLE_USUARIO(2);
	
	private int perfilEnum;
	
	PerfilEnum(int perfil) {
		this.perfilEnum = perfil;
	}
	
	public int getPerfilEnum() {
		return perfilEnum;
	}

	public static PerfilEnum obterPerfilPeloId(Integer id) {
		try {
			for(PerfilEnum perfil : values()) {
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