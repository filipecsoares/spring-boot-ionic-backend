package com.vendas.domain.enums;

public enum TipoCliente {
	PESSOAFISICA(1, "Pessoa Física"), PESSOAJURIDICA(2, "Pessoa Jurídica");

	private int cod;
	private String value;

	private TipoCliente(int cod, String value) {
		this.cod = cod;
		this.value = value;
	}

	public int getCod() {
		return this.cod;
	}

	public String getValue() {
		return this.value;
	}

	public static TipoCliente toEnum(Integer cod) {
		if (cod == null) {
			return null;
		}

		for (TipoCliente tipo : TipoCliente.values()) {
			if (cod.equals(tipo.getCod())) {
				return tipo;
			}
		}

		throw new IllegalArgumentException("Id inválido: " + cod);
	}
}
