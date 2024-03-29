package com.algaworks.algafood.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.Pedido;

@Repository
public interface PedidoRepository extends CustomJpaRepository<Pedido, Long> {

	@Query("SELECT p FROM Pedido p JOIN FETCH p.cliente JOIN FETCH p.restaurante r JOIN FETCH r.cozinha")
	List<Pedido> findAll();
	
	Optional<Pedido> findByCodigo(String codigo);
	
}
