package com.vendas.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.vendas.domain.Cliente;
import com.vendas.dto.ClienteDTO;
import com.vendas.repositories.ClienteRepository;
import com.vendas.resources.exceptions.FieldMessage;

public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO> {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private ClienteRepository repository;

	@Override
	public void initialize(ClienteUpdate constraintAnnotation) {

	}

	@Override
	public boolean isValid(ClienteDTO value, ConstraintValidatorContext context) {
		@SuppressWarnings("unchecked")
		Map<String, String> map = (Map<String, String>) request
				.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		Long urlId = Long.parseLong(map.get("id"));

		List<FieldMessage> list = new ArrayList<>();

		Cliente aux = repository.findByEmail(value.getEmail());
		if (aux != null && !urlId.equals(aux.getId())) {
			list.add(new FieldMessage("email", "Email já existente"));
		}

		for (FieldMessage fm : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(fm.getMessage()).addPropertyNode(fm.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}

}
