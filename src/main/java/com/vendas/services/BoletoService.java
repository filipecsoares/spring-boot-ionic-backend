package com.vendas.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.vendas.domain.PagamentoComBoleto;

@Service
public class BoletoService {

	public void preencherPagamentoComBoleto(PagamentoComBoleto pagamento, Date instanteDoPedido) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(instanteDoPedido);
		calendar.add(Calendar.DAY_OF_MONTH, 7);
		pagamento.setDataPagamento(calendar.getTime());
	}
}
