package com.algaworks.algafood.domain.repository;

import java.math.BigDecimal;
import java.util.List;

import com.algaworks.algafood.domain.model.Restaurante;

public interface RestauranteRepositoryQueries {

	List<Restaurante> buscarPorNomeETaxaFreteEntre(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal);

	List<Restaurante> buscarPorNomeEComFreteGratis(String nome);
	
}