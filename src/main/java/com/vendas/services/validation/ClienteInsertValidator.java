package com.vendas.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.vendas.domain.enums.TipoCliente;
import com.vendas.dto.ClienteNewDTO;
import com.vendas.resources.exceptions.FieldMessage;
import com.vendas.services.validation.utils.BR;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {

	@Override
	public void initialize(ClienteInsert constraintAnnotation) {

	}

	@Override
	public boolean isValid(ClienteNewDTO value, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();

		if (value.getTipo().equals(TipoCliente.PESSOAFISICA.getCod()) && !BR.isValidCPF(value.getCpfOuCnpj())) {
			list.add(new FieldMessage("cpfOuCnpj", "CPF inválido"));
		}
		if (value.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod()) && !BR.isValidCNPJ(value.getCpfOuCnpj())) {
			list.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido"));
		}

		for (FieldMessage fm : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(fm.getMessage()).addPropertyNode(fm.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}

}
