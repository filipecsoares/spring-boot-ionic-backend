package com.vendas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.vendas.domain.Categoria;
import com.vendas.repositories.CategoriaRepository;
import com.vendas.services.exceptions.DataIntegrityException;
import com.vendas.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repository;

	public Categoria findById(Long id) {
		return repository.findById(id).orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo:" + Categoria.class.getName()));
	}

	public Categoria insert(Categoria categoria) {
		return repository.save(categoria);
	}

	public Categoria update(Categoria categoria) {
		findById(categoria.getId());
		return repository.save(categoria);
	}

	public void delete(Long id) {
		findById(id);
		try {
			repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma categoria que possui produtos");
		}
	}
}
