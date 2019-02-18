package com.vendas.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vendas.domain.Cidade;
import com.vendas.domain.Cliente;
import com.vendas.domain.Endereco;
import com.vendas.domain.enums.TipoCliente;
import com.vendas.dto.ClienteDTO;
import com.vendas.dto.ClienteNewDTO;
import com.vendas.repositories.ClienteRepository;
import com.vendas.repositories.EnderecoRepository;
import com.vendas.services.exceptions.DataIntegrityException;
import com.vendas.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private BCryptPasswordEncoder bpe;

	@Autowired
	private ClienteRepository repository;

	@Autowired
	private EnderecoRepository enderecoRepository;

	public Cliente findById(Long id) {
		return repository.findById(id).orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo:" + Cliente.class.getName()));
	}

	public Cliente update(Cliente objNewValues) {
		Cliente objToSave = findById(objNewValues.getId());
		updateData(objToSave, objNewValues);
		return repository.save(objToSave);
	}

	@Transactional
	public Cliente insert(Cliente cliente) {
		cliente.setId(null);
		Cliente newCliente = repository.save(cliente);
		enderecoRepository.saveAll(cliente.getEnderecos());
		return newCliente;
	}

	public void delete(Long id) {
		findById(id);
		try {
			repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir um cliente porque há pedidos relacionados.");
		}
	}

	public List<Cliente> findAll() {
		return repository.findAll();
	}

	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repository.findAll(pageRequest);
	}

	public Cliente fromDTO(ClienteDTO dto) {
		return new Cliente(dto.getId(), dto.getNome(), dto.getEmail(), null, null, null);
	}

	public Cliente fromDTO(ClienteNewDTO dto) {
		Cliente cliente = new Cliente(null, dto.getNome(), dto.getEmail(), dto.getCpfOuCnpj(),
				TipoCliente.toEnum(dto.getTipo()), bpe.encode(dto.getSenha()));
		Cidade cidade = new Cidade(dto.getCidadeId(), null);
		Endereco endereco = new Endereco(dto.getLogradouro(), dto.getNumero(), dto.getComplemento(), dto.getBairro(),
				dto.getCep(), cliente, cidade);
		cliente.getEnderecos().add(endereco);
		cliente.getTelefones().add(dto.getTelefone1());
		if (dto.getTelefone2() != null) {
			cliente.getTelefones().add(dto.getTelefone2());
		}
		if (dto.getTelefone3() != null) {
			cliente.getTelefones().add(dto.getTelefone3());
		}
		return cliente;
	}

	private void updateData(Cliente objToSave, Cliente objNewValues) {
		objToSave.setNome(objNewValues.getNome());
		objToSave.setEmail(objNewValues.getEmail());
	}
}
