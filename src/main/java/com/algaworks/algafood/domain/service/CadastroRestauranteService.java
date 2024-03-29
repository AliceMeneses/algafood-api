package com.algaworks.algafood.domain.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {

	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private CadastroCozinhaService cadastroCozinha;

	@Autowired
	private CadastroCidadeService cadastroCidade;

	@Autowired
	private CadastroFormaPagamentoService formaPagamentoService;

	@Autowired
	private CadastroUsuarioService cadastroUsuario;

	@Transactional
	public Restaurante salvar(Restaurante restaurante) {
		Long cozinhaId = restaurante.getCozinha().getId();
		Cozinha cozinha = cadastroCozinha.buscarOuFalhar(cozinhaId);
		restaurante.setCozinha(cozinha);

		Long cidadeId = restaurante.getEndereco().getCidade().getId();
		Cidade cidade = cadastroCidade.buscarOuFalhar(cidadeId);
		restaurante.getEndereco().setCidade(cidade);

		return restauranteRepository.save(restaurante);
	}

	@Transactional
	public void ativar(Long id) {
		Restaurante restaurante = buscarOuFalhar(id);

		restaurante.ativar();
	}

	@Transactional
	public void ativar(List<Long> restaurantesIds) {
		try {
			restaurantesIds.forEach(this::ativar);
		} catch (RestauranteNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@Transactional
	public void inativar(Long id) {
		Restaurante restaurante = buscarOuFalhar(id);

		restaurante.inativar();
	}

	@Transactional
	public void inativar(List<Long> restaurantesIds) {
		try {
			restaurantesIds.forEach(this::inativar);
		} catch (RestauranteNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@Transactional
	public void associarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
		Restaurante restaurante = buscarOuFalhar(restauranteId);
		FormaPagamento formaPagamento = formaPagamentoService.buscarOuFalhar(formaPagamentoId);

		restaurante.adicionarFormaPagamento(formaPagamento);
	}

	@Transactional
	public void desassociarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
		Restaurante restaurante = buscarOuFalhar(restauranteId);
		FormaPagamento formaPagamento = formaPagamentoService.buscarOuFalhar(formaPagamentoId);

		restaurante.removerFormaPagamento(formaPagamento);
	}

	@Transactional
	public void abrir(Long restauranteId) {
		Restaurante restaurante = buscarOuFalhar(restauranteId);
		restaurante.abrir();
	}

	@Transactional
	public void fechar(Long restauranteId) {
		Restaurante restaurante = buscarOuFalhar(restauranteId);
		restaurante.fechar();
	}

	@Transactional
	public void desassociarResponsavel(Long restauranteId, Long usuarioId) {
		Restaurante restaurante = buscarOuFalhar(restauranteId);
		Usuario usuario = cadastroUsuario.buscarOuFalhar(usuarioId);

		restaurante.removerResponsavel(usuario);
	}

	@Transactional
	public void associarResponsavel(Long restauranteId, Long usuarioId) {
		Restaurante restaurante = buscarOuFalhar(restauranteId);
		Usuario usuario = cadastroUsuario.buscarOuFalhar(usuarioId);

		restaurante.adicionarResponsavel(usuario);
	}

	public Restaurante buscarOuFalhar(Long id) {
		return restauranteRepository.findById(id).orElseThrow(() -> new RestauranteNaoEncontradoException(id));
	}

	public Restaurante buscarPorIdECarregarCidadeOuFalhar(Long id) {
		return restauranteRepository.consultarPorIdECarregarCidade(id)
				.orElseThrow(() -> new RestauranteNaoEncontradoException(id));

	}

}
