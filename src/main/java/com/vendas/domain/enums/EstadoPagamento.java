package com.vendas.domain.enums;

public enum EstadoPagamento {
	PENDENTE(1, "Pendente"), QUITADO(2, "Quitado"), CANCELADO(3, "Cancelado");

	private int cod;
	private String value;

	private EstadoPagamento(int cod, String value) {
		this.cod = cod;
		this.value = value;
	}

	public int getCod() {
		return this.cod;
	}

	public String getValue() {
		return this.value;
	}

	public static EstadoPagamento toEnum(Integer cod) {
		if (cod == null) {
			return null;
		}

		for (EstadoPagamento estado : EstadoPagamento.values()) {
			if (cod.equals(estado.getCod())) {
				return estado;
			}
		}

		throw new IllegalArgumentException("Id inválido: " + cod);
	}
}
