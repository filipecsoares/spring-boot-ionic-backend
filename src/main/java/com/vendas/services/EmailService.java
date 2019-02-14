package com.vendas.services;

import org.springframework.mail.SimpleMailMessage;

import com.vendas.domain.Pedido;

public interface EmailService {

	void sendOrderConfirmationEmail(Pedido pedido);

	void sendEmail(SimpleMailMessage msg);
}
