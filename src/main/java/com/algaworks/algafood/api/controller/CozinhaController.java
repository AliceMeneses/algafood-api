package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

	@Autowired
	private CadastroCozinhaService cadastroCozinha;

	@Autowired
	private CozinhaRepository repository;

	@GetMapping
	public List<Cozinha> listar() {
		return repository.findAll();
	}

	@GetMapping("/{id}")
	public Cozinha buscar(@PathVariable Long id) {
		return cadastroCozinha.buscarOuFalhar(id);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cozinha adicionar(@RequestBody @Valid Cozinha cozinha) {
		return cadastroCozinha.salvar(cozinha);
	}

	@PutMapping("/{id}")
	public Cozinha atualizar(@PathVariable Long id, @RequestBody @Valid Cozinha cozinha) {
		Cozinha cozinhaAtual = cadastroCozinha.buscarOuFalhar(id);
		BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");
		return cadastroCozinha.salvar(cozinhaAtual);
	}

	@DeleteMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		cadastroCozinha.remover(id);
	}

}
