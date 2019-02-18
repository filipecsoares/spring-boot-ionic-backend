package com.vendas.domain.enums;

public enum Perfil {
	ADMIN(1, "ROLE_ADMIN"), CLIENTE(2, "ROLE_CLIENTE");

	private int cod;
	private String value;

	private Perfil(int cod, String value) {
		this.cod = cod;
		this.value = value;
	}

	public int getCod() {
		return this.cod;
	}

	public String getValue() {
		return this.value;
	}

	public static Perfil toEnum(Integer cod) {
		if (cod == null) {
			return null;
		}

		for (Perfil estado : Perfil.values()) {
			if (cod.equals(estado.getCod())) {
				return estado;
			}
		}

		throw new IllegalArgumentException("Id inválido: " + cod);
	}
}
