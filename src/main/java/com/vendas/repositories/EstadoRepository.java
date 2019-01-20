package com.vendas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vendas.domain.Estado;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Long> {

}
