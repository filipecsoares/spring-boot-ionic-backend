package com.vendas.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import com.vendas.domain.Pedido;

public abstract class AbstractEmailService implements EmailService {

	@Value("default.sender")
	private String sender;

	@Override
	public void sendOrderConfirmationEmail(Pedido pedido) {
		SimpleMailMessage smm = prepareSimpleMailMessageFromPedido(pedido);
		sendEmail(smm);
	}

	protected SimpleMailMessage prepareSimpleMailMessageFromPedido(Pedido pedido) {
		SimpleMailMessage smm = new SimpleMailMessage();
		smm.setTo(pedido.getCliente().getEmail());
		smm.setFrom(sender);
		smm.setSubject("Pedido confirmado! Código: " + pedido.getId());
		smm.setSentDate(new Date(System.currentTimeMillis()));
		smm.setText(pedido.toString());
		return smm;
	}
}
